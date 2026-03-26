# 📌 Cambios realizados en el Backend (Spring Boot)
## ✅ 1. Integración con base de datos H2

Se ha configurado una base de datos en memoria para el entorno de desarrollo.

### **Archivo modificado:**

**👉 src/main/resources/application.properties**

Se añadieron las siguientes propiedades:

* spring.application.name=prototipo-reservas
* spring.datasource.url=jdbc:h2:mem:reservasdb
* spring.datasource.driverClassName=org.h2.Driver
* spring.datasource.username=sa
* spring.datasource.password=

* spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

* spring.h2.console.enabled=true
* spring.h2.console.path=/h2-console

* spring.jpa.hibernate.ddl-auto=update
* spring.jpa.defer-datasource-initialization=true



**✔ Resultado:**

* El backend ya persiste datos reales

* Las tablas se generan automáticamente con JPA

* Consola accesible en:

   http://localhost:8080/h2-console

## ✅ 2. Creación de entidades JPA

Se definieron las entidades principales del sistema.

## 📁 Nueva carpeta: entity

### ✔ Archivos creados:
**👉 User.java**

Representa los usuarios del sistema.

Campos:

id

name

email

password

**👉 Room.java**

Representa las salas disponibles para reservas.

Campos:

id

name

capacity

**👉 Reservation.java**

Representa las reservas realizadas por los usuarios.

Campos principales:

id

date

time

relación con Room

relación con User

(Las relaciones se ampliarán en siguientes iteraciones)

## ✅ 3. Creación de repositorios JPA

Se implementó el acceso a datos mediante Spring Data JPA.

## 📁 Nueva carpeta: repository

**✔ Interfaces creadas:**

* RoomRepository extends JpaRepository<Room, Long>
* UserRepository extends JpaRepository<User, Long>
* ReservationRepository extends JpaRepository<Reservation, Long>

 **✔ Beneficio:**

Sin escribir SQL manual ahora tenemos de forma automática los métodos:

* findAll()

* save()

* delete()

* findById()



## ✅ 4. Seed de datos iniciales (MUY IMPORTANTE para frontend)

Se añadió un archivo para precargar datos automáticamente.

Archivo creado:

## 👉 src/main/resources/data.sql

INSERT INTO ROOM (name, capacity) VALUES ('Sala A', 10);
INSERT INTO ROOM (name, capacity) VALUES ('Sala B', 10);
INSERT INTO ROOM (name, capacity) VALUES ('Sala C', 10);

**✔ Resultado:**

El frontend puede consumir /rooms sin necesidad de crear datos manualmente.

## ✅ 5. Refactor del Service

Se eliminaron anotaciones incorrectas:

❌ @GetMapping
❌ @PostMapping

(Las anotaciones REST solo deben estar en el Controller)

## ✅ 6. Conexión Controller → Service → Repository

Arquitectura actual:

Controller
↓
Service
↓
Repository (JPA)
↓
H2 Database


Esto sigue buenas prácticas de Spring.

## ✅ 7. Configuración de CORS

Para permitir conexión desde React:

**@CrossOrigin(origins = "http://localhost:5173")**

Ahora frontend puede consumir la API sin errores de navegador.

##  👥 8. Users. Roles disponibles

El sistema define actualmente dos tipos de usuario:

### 👑 ADMIN

Permisos previstos:

* Crear / editar / eliminar salas

* Ver todas las reservas

* Gestionar usuarios

### 👨‍💻 EMPLOYEE

Permisos previstos:

* Consultar salas

* Crear reservas

* Cancelar sus reservas

## 🧠 Seed de usuarios

Usuarios iniciales cargados desde:

**data.sql**

Ejemplo:
```json
INSERT INTO USER (name, email, password, role)
VALUES ('Admin', 'admin@test.com', '1234', 'ADMIN');

INSERT INTO USER (name, email, password, role)
VALUES ('Empleado', 'emp@test.com', '1234', 'EMPLOYEE');
```

⚠️ Passwords en texto plano solo para entorno de desarrollo.

## 🔐 Seguridad (Planned)

La autenticación actual es únicamente para desbloquear el desarrollo frontend.

En futuras iteraciones se podrá implementar:

* Password hashing (BCrypt)

* Spring Security

* JWT

* Authorization por roles


## ✅ 9. Implementación inicial de autenticación (Login)

Se introduce una capa básica de autenticación para permitir al frontend desarrollar la pantalla de login sin bloquear el avance del producto.

⚠️ Esta solución es temporal y será reemplazada por un sistema de seguridad robusto.

---

## 📁 Nuevos componentes creados

#### 👉 AuthController
Gestiona las operaciones de autenticación.

Endpoint disponible:

POST `/auth/login`

---

#### 👉 DTO – LoginRequest
Ubicación:

dto/LoginRequest.java


Se utiliza para encapsular las credenciales enviadas por el cliente.

Campos:
- email
- password

✔ Evita uso de Map  
✔ Mejora tipado  
✔ Sigue buenas prácticas backend

---

### 👉 UserService – método login()

Se implementa lógica de validación básica:

- búsqueda por email y password
- excepción en caso de credenciales inválidas

---

### 👉 UserRepository

Nuevo método:

```java
Optional<User> findByEmailAndPassword(String email, String password);
```
Generado automáticamente por Spring Data JPA.


## 👥 Definición de roles

Se introduce un ENUM para estructurar los tipos de usuario:
```java
public enum Role {
ADMIN,
EMPLOYEE
}
```

+ ✔ Evita strings hardcodeados 
+ ✔ Facilita futura autorización
+ ✔ Mejora diseño del dominio

### 🧠 Objetivo arquitectónico

Permitir:

* desarrollo paralelo frontend/backend

* diseño temprano de pantallas según rol

* evitar bloqueo del equipo

Siguiendo una estrategia profesional de:

👉 Fake Authentication First → Real Security Later


## 📌 Estado actual de la API

**Endpoint disponible:**

### 🟣 Rooms
### ✔ Obtener salas

GET /rooms

### ✔ Crear sala
POST /rooms

Ejemplo JSON:
```json
{
"name": "Sala PRO",
"capacity": 50
}
```
### 🟢 Auth

✔ POST /auth/login

Ejemplo:

{
"email": "admin@test.com",
"password": "1234"
}

### 🔵 Users

🚧 En desarrollo

### 🟠  Reservations

🚧 En desarrollo


## ⚠️ Nota importante para el equipo

Actualmente la BD es H2 en memoria, lo que significa:

### 👉 Se reinicia cada vez que se arranca Spring.

Esto es intencional para el **prototipo**.

En producción se migrará a:

PostgreSQL ✅ (recomendado) o MySQL