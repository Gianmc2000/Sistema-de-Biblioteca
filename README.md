# Sistema de Gestión de Biblioteca - Arquitectura de Microservicios

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=biblioteca_microservicios&metric=alert_status)](https://sonarcloud.io/dashboard?id=biblioteca_microservicios)

Este proyecto corresponde a la resolución del **Examen Práctico del Módulo de Microservicios con Spring Boot** (TECSUP - Docente: Nike Rodriguez).

El sistema implementa una arquitectura basada en microservicios utilizando el patrón **Maestro → Orquestador → Notificador**, integrando descubrimiento de servicios con Netflix Eureka, comunicación cliente REST balanceada por nombre (`RestClient.Builder` + `@LoadBalanced`), persistencia independiente con JPA + PostgreSQL, aplicación de patrones creacionales, pruebas unitarias y análisis continuo de calidad de código con SonarCloud.

---

## 📋 Tabla de Contenido

- [Arquitectura del Sistema](#-arquitectura-del-sistema)
- [Estructura del Repositorio](#-estructura-del-repositorio)
- [Servicios y Endpoints](#-servicios-y-endpoints)
- [Bases de Datos (Docker Compose / PostgreSQL)](#-bases-de-datos-docker-compose--postgresql)
- [Orden de Arranque](#-orden-de-arranque)
- [Patrones de Diseño Creacionales](#-patrones-de-diseño-creacionales)
- [Pruebas Unitarias](#-pruebas-unitarias)
- [Calidad de Código (SonarCloud)](#-calidad-de-código-sonarcloud)
- [Flujo de Prueba Obligatorio](#-flujo-de-prueba-obligatorio)

---

## 🏗 Arquitectura del Sistema

El sistema consta de 5 componentes en total: **4 microservicios propios** construidos en esta solución y **1 API Gateway central** provisto por la institución.

```
Cliente (vía Gateway del docente con Seguridad JWT)
  │
  ├──► api-gateway (Port 8080)
  │      ├─ lb://libros-service (Port 8081) ───► [Ejemplares, Socios]
  │      └─ lb://prestamos-service (Port 8082)
  │            ├─ lb://libros-service (Valida Socio y Ejemplar)
  │            └─ lb://notificaciones-service (Port 8083)
  │
  └─► Todos los servicios registrados en: eureka-server (Port 8761)
```

### Componentes y Puertos

| Servicio | Puerto | Descripción / Rol | Responsable |
| :--- | :---: | :--- | :--- |
| `eureka-server` | `8761` | Servidor de Descubrimiento de Servicios (Eureka Server) | Desarrollado |
| `api-gateway` | `8080` | Puerta de entrada pública única y control de seguridad | Docente |
| `libros-service` | `8081` | Servicio Maestro: Gestión de Ejemplares y Socios | Desarrollado |
| `prestamos-service` | `8082` | Servicio Orquestador: Validación, Registro y Devolución | Desarrollado |
| `notificaciones-service` | `8083` | Servicio Notificador: Registro y simulación de avisos | Desarrollado |

---

## 📁 Estructura del Repositorio

```text
apellido-nombre-examen-microservicios/
├── eureka-server/
├── libros-service/
├── prestamos-service/
├── notificaciones-service/
├── docker-compose.yml
├── postman/
│   └── Biblioteca.postman_collection.json
├── docs/
│   ├── eureka-dashboard.png
│   └── sonarcloud-quality-gate.png
└── README.md
```

---

## 🛠 Servicios y Endpoints

### 1. `libros-service` (Puerto: `8081`)

#### Recurso Ejemplares (`/api/v1/libros`)
- `POST /api/v1/libros` - Crear un nuevo ejemplar.
- `GET /api/v1/libros` - Listar todos los ejemplares.
- `GET /api/v1/libros/{codigoEjemplar}` - Obtener ejemplar por código (404 si no existe).
- `PUT /api/v1/libros/{codigoEjemplar}` - Editar datos de ejemplar (título, autor, disponibilidad).
- `DELETE /api/v1/libros/{codigoEjemplar}` - Eliminar un ejemplar.
- `PATCH /api/v1/libros/{codigoEjemplar}/disponibilidad` - Actualizar estado de disponibilidad (`true`/`false`).

#### Recurso Socios (`/api/v1/socios`)
- `POST /api/v1/socios` - Registrar un nuevo socio.
- `GET /api/v1/socios` - Listar todos los socios.
- `GET /api/v1/socios/{codigoSocio}` - Buscar socio por código (404 si no existe).
- `PUT /api/v1/socios/{codigoSocio}` - Editar socio (nombre, email, estado activo).
- `DELETE /api/v1/socios/{codigoSocio}` - Eliminar un socio.

### 2. `prestamos-service` (Puerto: `8082`)

#### Recurso Préstamos (`/api/v1/prestamos`)
- `POST /api/v1/prestamos` - Registrar préstamo (orquesta validación de socio y libro, actualiza estado, notifica).
- `GET /api/v1/prestamos` - Listar historial de préstamos.
- `GET /api/v1/prestamos/{id}` - Obtener préstamo por ID.
- `POST /api/v1/prestamos/{id}/devolucion` - Procesar devolución de ejemplar y reactivar disponibilidad.

### 3. `notificaciones-service` (Puerto: `8083`)

#### Recurso Notificaciones (`/api/v1/notificaciones`)
- `POST /api/v1/notificaciones` - Registrar y simular el envío de una notificación.
- `GET /api/v1/notificaciones` - Listar notificaciones emitidas.

---

## 🗄 Bases de Datos (Docker Compose / PostgreSQL)

Cada microservicio gestiona su propia base de datos relacional independiente. Para facilitar el despliegue local de la persistencia, se incluye un archivo `docker-compose.yml` en la raíz del proyecto.

### Archivo `docker-compose.yml`

```yaml
version: '3.8'

services:
  libros-db:
    image: postgres:16-alpine
    container_name: libros-db
    environment:
      POSTGRES_DB: librosdb
      POSTGRES_USER: libros
      POSTGRES_PASSWORD: libros123
    ports:
      - "5432:5432"
    volumes:
      - libros_data:/var/lib/postgresql/data

  prestamos-db:
    image: postgres:16-alpine
    container_name: prestamos-db
    environment:
      POSTGRES_DB: prestamosdb
      POSTGRES_USER: prestamos
      POSTGRES_PASSWORD: prestamos123
    ports:
      - "5433:5432"
    volumes:
      - prestamos_data:/var/lib/postgresql/data

  notif-db:
    image: postgres:16-alpine
    container_name: notif-db
    environment:
      POSTGRES_DB: notificacionesdb
      POSTGRES_USER: notif
      POSTGRES_PASSWORD: notif123
    ports:
      - "5434:5432"
    volumes:
      - notif_data:/var/lib/postgresql/data

volumes:
  libros_data:
  prestamos_data:
  notif_data:
```

### Comandos para Despliegue

```bash
# Iniciar todas las bases de datos PostgreSQL en segundo plano
docker compose up -d

# Detener los contenedores de base de datos
docker compose down

# Alternativa rápida con comandos de Docker individuales (Anexo A):
docker run --name libros-db -e POSTGRES_DB=librosdb -e POSTGRES_USER=libros -e POSTGRES_PASSWORD=libros123 -p 5432:5432 -d postgres:16-alpine
docker run --name prestamos-db -e POSTGRES_DB=prestamosdb -e POSTGRES_USER=prestamos -e POSTGRES_PASSWORD=prestamos123 -p 5433:5432 -d postgres:16-alpine
docker run --name notif-db -e POSTGRES_DB=notificacionesdb -e POSTGRES_USER=notif -e POSTGRES_PASSWORD=notif123 -p 5434:5432 -d postgres:16-alpine
```

---

## 🚀 Orden de Arranque

Para garantizar un inicio correcto y la resolución adecuada del descubrimiento de servicios, inicie las aplicaciones en el siguiente orden:

1. **Bases de Datos PostgreSQL**: Ejecutar `docker compose up -d` en la raíz del repositorio.
2. **`eureka-server`**: Iniciar el servidor Eureka (`http://localhost:8761`). Esperar a que el panel de control responda.
3. **`libros-service`**: Servicio maestro de datos. Al iniciar ejecuta un `CommandLineRunner` que siembra datos de prueba iniciales (socios y ejemplares).
4. **`notificaciones-service`**: Servicio de notificaciones.
5. **`prestamos-service`**: Servicio orquestador central.

---

## 🎨 Patrones de Diseño Creacionales

En cumplimiento con las buenas prácticas de diseño y los requerimientos del examen, se implementaron dos patrones creacionales:

### 1. Factory Method (`MensajeNotificacionFactory`)
- **Ubicación**: `prestamos-service/src/main/java/com/biblioteca/prestamos/factory/MensajeNotificacionFactory.java`
- **Comentario en código**: `// [Patrón: Factory Method]`
- **Justificación de diseño**: En lugar de concatenar cadenas de texto manualmente a lo largo de la capa de servicio para construir el cuerpo de la notificación, el patrón **Factory Method** centraliza la lógica de creación de mensajes según el evento ocurrido (`REGISTRADA`, `RECHAZADA`, `DEVUELTO`). Esto desacopla el servicio de préstamos del formato específico del mensaje, facilita el mantenimiento y permite agregar nuevas plantillas o canales sin alterar la lógica de negocio principal.

### 2. Builder (`PrestamoResponseBuilder`)
- **Ubicación**: `prestamos-service/src/main/java/com/biblioteca/prestamos/dto/PrestamoResponseBuilder.java`
- **Comentario en código**: `// [Patrón: Builder]`
- **Justificación de diseño**: La DTO de respuesta `PrestamoResponse` contiene múltiples campos opcionales que dependen directamente del resultado de la operación (por ejemplo, `motivoRechazo` y `observaciones` solo aplican en rechazos, mientras que `fechaDevolucionReal` solo aplica en devoluciones). Se escribió la clase Builder **manualmente a mano (sin anotación `@Builder` de Lombok)** para garantizar un control explícito en la construcción fluida de respuestas complejas y coherentes según el estado del préstamo.

---

## 🧪 Pruebas Unitarias

El proyecto cuenta con un conjunto de pruebas unitarias desarrolladas con **JUnit 5** y **Mockito**, aislando las capas de persistencia y llamadas HTTP externas mediante Mocks.

### Casos Probados
1. `LibroServiceTest`:
   - Búsqueda exitosa de ejemplar por código.
   - Lanzamiento de `EjemplarNoEncontradoException` (404) cuando el código no existe.
2. `PrestamoServiceTest`:
   - Registro exitoso de préstamo (`REGISTRADA`).
   - Rechazo de préstamo por ejemplar no disponible (`RECHAZADA`).
   - Rechazo de préstamo por socio inactivo (`RECHAZADA`).
   - Rechazo de préstamo por ejemplar inexistente (`RECHAZADA`).
   - Procesamiento de devolución exitoso (`DEVUELTO`).
   - Rechazo de devolución duplicada (`409 Conflict`).
3. `MensajeNotificacionFactoryTest`:
   - Pruebas unitarias puras verificando la correcta construcción del texto del mensaje según los parámetros de entrada.

Para ejecutar la suite completa de pruebas:
```bash
mvn test
```

---

## 📊 Calidad de Código (SonarCloud) (PENDIENTE)

El análisis de código estático se ejecuta de forma automatizada en cada push mediante **GitHub Actions** (`.github/workflows/sonarcloud.yml`).

- **Dashboard SonarCloud**: [Ver Proyecto en SonarCloud](https://sonarcloud.io/dashboard?id=biblioteca_microservicios)
- **Estado del Quality Gate**: **PASSED**

![SonarCloud Quality Gate](docs/sonarcloud-quality-gate.png)

---

## 🧪 Flujo de Prueba Obligatorio

Todos los escenarios han sido probados a través del **API Gateway** (`http://localhost:8080`) utilizando la colección de Postman incluida en `/postman/Biblioteca.postman_collection.json`:

| # | Escenario | Método & Path | Resultado Esperado |
| :---: | :--- | :--- | :--- |
| **1** | Listar ejemplares iniciales | `GET /api/v1/libros` | `200 OK` - Retorna lista sembrada por el `CommandLineRunner`. |
| **2** | Listar socios iniciales | `GET /api/v1/socios` | `200 OK` - Retorna lista de socios sembrados. |
| **3** | Registrar préstamo exitoso | `POST /api/v1/prestamos` | `200 OK` - Estado `REGISTRADA`, ejemplar pasa a `disponible=false`, genera notificación. |
| **4** | Prestar mismo ejemplar | `POST /api/v1/prestamos` | `200 OK` - Estado `RECHAZADA`, motivo `"No disponible"`. |
| **5** | Prestar a socio inactivo | `POST /api/v1/prestamos` | `200 OK` - Estado `RECHAZADA`, motivo `"Socio inactivo"`. |
| **6** | Prestar ejemplar inexistente | `POST /api/v1/prestamos` | `200 OK` - Estado `RECHAZADA`, motivo `"Ejemplar no existe"`. |
| **7** | Registrar devolución | `POST /api/v1/prestamos/{id}/devolucion` | `200 OK` - Estado `DEVUELTO`, ejemplar pasa a `disponible=true`. |
| **8** | Repetir devolución previa | `POST /api/v1/prestamos/{id}/devolucion` | `409 Conflict` - Error controlled indicando préstamo ya devuelto. |
| **9** | Listar notificaciones | `GET /api/v1/notificaciones` | `200 OK` - Muestra la notificación generada en el paso 3. |
