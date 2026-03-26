# 📄 API CONTRACT — ReservesApp Backend
Base URL
http://localhost:8080

## 🟣 Rooms API
## ✅ Get all rooms
**Request**

GET /rooms

**Response**

[
{
"id": 1,
"name": "Sala A",
"capacity": 10
},
{
"id": 2,
"name": "Sala B",
"capacity": 20
}
]

## ✅ Create Room
**Request**

POST /rooms

Body
{
"name": "Sala Nueva",
"capacity": 30
}


## ⚠️ **No enviar id — lo genera la BD.**

Response
{
"id": 4,
"name": "Sala Nueva",
"capacity": 30
}

## ⚠️ **CORS**

Backend permite requests desde:

http://localhost:5173


(Vite / React dev server)

## 🧠 Seed Data

El backend carga salas automáticamente desde:

**data.sql**

Para permitir testing inmediato desde frontend.

## 🔥 Estado actual

* Persistencia con H2
* Spring Data JPA
* Arquitectura Controller → Service → Repository
* API funcional

## 🚀 Próximos endpoints (planned)
### **Rooms**

PUT /rooms/{id}

DELETE /rooms/{id}

### **Reservations**

GET /reservations

POST /reservations

### **Users**

CRUD pendiente

## 🟢 Auth API (Login)

Se implementa un endpoint inicial de autenticación para permitir al frontend construir la pantalla de login.

⚠️ Esta implementación es **temporal (fake auth)** y será reemplazada por seguridad real (Spring Security + JWT) en fases posteriores.

---

### ✅ Login

**Request**

POST /auth/login

**Body**

```json
{
  "email": "admin@test.com",
  "password": "1234"
}

```
**Response**
```json
{
  "id": 1,
  "name": "Admin",
  "email": "admin@test.com",
  "role": "ADMIN"
}
```
## ⚠️ Nota dev

La base de datos actual es:

* 👉 H2 en memoria

* Se reinicia al apagar el backend.

* Se migrará a PostgreSQL/MySQL en fases posteriores.