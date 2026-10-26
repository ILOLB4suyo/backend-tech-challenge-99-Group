
## Asignment

Implementasi Java Spring Boot untuk assignment backend berbasis tiga aplikasi terpisah:

- Listing Service: menyimpan dan mengambil listing properti.
- User Service: menyimpan dan mengambil data pengguna.
- Public API: menyediakan API untuk klien eksternal dan menggabungkan data dari kedua service internal.

## Kebutuhan yang dipakai:

- Java 17
- Spring Boot 3.1.5
- Gradle Wrapper 8.3
- Spring Web dan Spring Data JPA
- H2 in-memory database
- Flyway database migration

## Konfigurasi port

Listing Service: `http://localhost:8081`
User Service: `http://localhost:8082`
Public API: `http://localhost:8080`

Public API mengakses Listing Service dan User Service melalui HTTP REST API. Public API tidak mengakses database service internal secara langsung.

## Persiapan

Jalankan setiap service di terminal terpisah.

### 1. Listing Service

cd .\listing-service
.\gradlew.bat bootRun

Service berjalan di port `8081`.

### 2. User Service

cd .\user-service
.\gradlew.bat bootRun

Service berjalan di port `8082`.

### 3. Public API

cd .\public-api
.\gradlew.bat bootRun

Public API berjalan di port `8080`. Jalankan kedua service internal terlebih dahulu, kemudian Public API.

Database yang dikonfigurasi saat ini menggunakan H2 in memory. Data uji akan hilang ketika service terkait dihentikan atau direstart. Buat ulang data untuk pengujian setelah restart.

## API Listing Service

### `GET /listings`

Mengambil daftar listing, diurutkan dari yang terbaru. Parameter query:

| Parameter | Default | Keterangan |
| `pageNum` | 1 | Nomor halaman, dimulai dari 1 |
| `pageSize` | 10 | Jumlah listing per halaman |
| `userId` | — | Opsional; hanya listing milik user tersebut |

Contoh:

GET http://localhost:8081/listings?pageNum=1&pageSize=10


### `POST /listings`

Content-Type: `application/x-www-form-urlencoded`

Parameter wajib:

- `userId` — ID pengguna
- `listingType` — `rent` atau `sale` mau 
- `price` — harga lebih besar dari nol

Contoh:

$body = @{ userId = 1; listingType = "rent"; price = 4500 }
Invoke-RestMethod -Uri "http://localhost:8081/listings" -Method Post -ContentType "application/x-www-form-urlencoded" -Body $body


## API User Service

### `GET /users`

Mengambil daftar pengguna, diurutkan berdasarkan waktu pembuatan terbaru.

Parameter query:

| Parameter | Default | Keterangan |
| `pageNum` | 1 | Nomor halaman, dimulai dari 1 |
| `pageSize` | 10 | Jumlah pengguna per halaman |

Contoh:

GET http://localhost:8082/users?pageNum=1&pageSize=10


### `GET /users/{id}`

Mengambil pengguna berdasarkan ID.

GET http://localhost:8082/users/1


### `POST /users`

Content Type: `application/x-www-form-urlencoded`

Parameter wajib: `name`.

Contoh:

$body = @{ name = "Leo Test" }
Invoke-RestMethod -Uri "http://localhost:8082/users" -Method Post -ContentType "application/x-www-form-urlencoded" -Body $body


## Public API

### `GET /public-api/listings`

Mengambil listing dari Listing Service, kemudian mengambil data pemilik dari User Service dan menggabungkannya di response. Listing diurutkan dari yang terbaru.

Parameter query:

| Parameter | Default | Keterangan |
| `pageNum` | 1 | Nomor halaman |
| `pageSize` | 10 | Jumlah listing per halaman |
| `userId` | — | Opsional; filter listing berdasarkan pemilik |

Contoh:

GET http://localhost:8080/public-api/listings?pageNum=1&pageSize=10
GET http://localhost:8080/public-api/listings?pageNum=1&pageSize=10&userId=1

Response listing publik menyertakan `id`, `listingType`, `price`, timestamp, serta objek `user` dengan informasi pemilik.

### `POST /public-api/users`

Content Type: `application/json`

Request body:

{
  "name": "Leo Public API"
}


Public API meneruskan permintaan ke User Service dengan format `application/x-www-form-urlencoded`.

### `POST /public-api/listings`

Content Type: `application/json`

Request body:

{
  "userId": 1,
  "listingType": "rent",
  "price": 4500
}


Validasi menolak `userId` yang tidak positif, `price` yang tidak positif, dan `listingType` selain `rent` atau `sale`.


## Catatan

- Karena database H2 bersifat in-memory, data tidak bertahan setelah aplikasi dihentikan.
