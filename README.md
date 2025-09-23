# 🏋️‍♂️ Sistema de Gestión de Gimnasio - Backend

Sistema completo para la gestión de un gimnasio desarrollado con **Spring Boot 3.2.0** que incluye autenticación JWT, control de usuarios, membresías, asistencias y pagos.

## 🚀 Características Principales

- **Autenticación y Autorización**: Sistema JWT con roles (Administrador/Cliente)
- **Gestión de Usuarios**: CRUD completo con diferentes niveles de acceso
- **Control de Membresías**: Gestión de planes y precios
- **Sistema de Asistencias**: Registro automático de entrada/salida
- **Gestión de Pagos**: Procesamiento y seguimiento de pagos
- **Documentación API**: Swagger/OpenAPI integrado
- **Seguridad**: Spring Security con encriptación BCrypt

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security 6**
- **Spring Data JPA**
- **JWT (JSON Web Tokens)**
- **MySQL 8.0**
- **Swagger/OpenAPI 3**
- **Maven**
- **Lombok**

## 📋 Requisitos Previos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.6 o superior

## ⚙️ Configuración

### 1. Base de Datos

Crear una base de datos MySQL llamada `gym_db`:

```sql
CREATE DATABASE gym_db;
```

### 2. Configuración de la Aplicación

Editar el archivo `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gym_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: tu_usuario
    password: tu_contraseña
```

### 3. Instalación y Ejecución

```bash
# Clonar el repositorio
git clone <url-del-repositorio>
cd backend_PageGym

# Instalar dependencias
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080/api`

## 📚 Documentación de la API

### Swagger UI
Accede a la documentación interactiva en: `http://localhost:8080/api/swagger-ui.html`

### Usuarios por Defecto

| Email | Contraseña | Rol |
|-------|------------|-----|
| admin@gimnasio.com | admin123 | ADMINISTRADOR |
| juan.perez@email.com | cliente123 | CLIENTE |

## 🔐 Autenticación

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@gimnasio.com",
  "password": "admin123"
}
```

### Respuesta
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "idUsuario": 1,
  "nombres": "Administrador",
  "apellidos": "Sistema",
  "email": "admin@gimnasio.com",
  "rol": "ADMINISTRADOR",
  "membresia": null
}
```

### Uso del Token
Incluir en el header de las peticiones:
```
Authorization: Bearer <token>
```

## 📖 Endpoints Principales

### 👥 Usuarios
- `GET /api/usuarios` - Listar usuarios (Admin)
- `POST /api/usuarios` - Crear usuario (Admin)
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario (Admin)
- `GET /api/usuarios/rol/{rolDesc}` - Usuarios por rol (Admin)

### 💳 Membresías
- `GET /api/membresias` - Listar membresías (Público)
- `POST /api/membresias` - Crear membresía (Admin)
- `PUT /api/membresias/{id}` - Actualizar membresía (Admin)
- `DELETE /api/membresias/{id}` - Eliminar membresía (Admin)
- `GET /api/membresias/precio-rango` - Filtrar por precio

### 📊 Asistencias
- `POST /api/asistencias/entrada` - Registrar entrada
- `POST /api/asistencias/salida` - Registrar salida
- `POST /api/asistencias/procesar` - Auto-detectar entrada/salida
- `GET /api/asistencias/usuario/{id}` - Asistencias por usuario
- `GET /api/asistencias/hoy` - Asistencias del día (Admin)

### 💰 Pagos
- `POST /api/pagos/procesar` - Procesar pago
- `GET /api/pagos/usuario/{id}` - Pagos por usuario
- `GET /api/pagos/ingresos` - Total ingresos (Admin)
- `PUT /api/pagos/{id}/estado` - Actualizar estado (Admin)

### 🔑 Roles
- `GET /api/roles` - Listar roles (Admin)
- `GET /api/roles/activos` - Roles activos (Admin)

## 🏗️ Estructura del Proyecto

```
src/main/java/com/gym/
├── config/           # Configuraciones (Security, Swagger)
├── controller/       # Controladores REST
├── dto/             # Data Transfer Objects
├── entity/          # Entidades JPA
├── repository/      # Repositorios JPA
├── security/        # Configuración JWT y Security
├── service/         # Lógica de negocio
└── GymManagementApplication.java
```

## 🔒 Niveles de Acceso

### Administrador
- Gestión completa de usuarios
- Gestión de membresías
- Visualización de todas las asistencias
- Reportes de pagos e ingresos
- Gestión de roles

### Cliente
- Ver y actualizar su perfil
- Registrar entrada/salida al gimnasio
- Ver sus asistencias
- Procesar pagos de membresías
- Ver sus pagos

## 🚦 Estados y Flujos

### Asistencias
1. **Entrada**: Usuario registra entrada al gimnasio
2. **Salida**: Usuario registra salida del gimnasio
3. **Auto-detección**: Sistema detecta automáticamente si debe registrar entrada o salida

### Pagos
- **PENDIENTE**: Pago iniciado pero no completado
- **COMPLETADO**: Pago procesado exitosamente
- **CANCELADO**: Pago cancelado
- **REEMBOLSADO**: Pago reembolsado

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Ejecutar tests con cobertura
mvn test jacoco:report
```

## 📝 Logs

Los logs se configuran en `application.yml` y incluyen:
- Consultas SQL (modo DEBUG)
- Seguridad Spring Security
- Logs de la aplicación

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## 📞 Soporte

Para soporte técnico o consultas:
- Email: desarrollo@gimnasio.com
- Documentación: `http://localhost:8080/swagger-ui.html`

---

**Desarrollado con ❤️ para la gestión eficiente de gimnasios**
