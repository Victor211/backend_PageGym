-- Insertar roles iniciales
INSERT IGNORE INTO roles (idROLES, desc_rol, estado, fecha_creacion, fecha_modificacion) VALUES
(1, 'ADMINISTRADOR', 1, NOW(), NOW()),
(2, 'CLIENTE', 1, NOW(), NOW());

-- Insertar membresías iniciales
INSERT IGNORE INTO membresias (idMEMBRESIAS, desc_membresia, precio, duracion_dias) VALUES
(1, 'Membresía Básica', 29.99, 30),
(2, 'Membresía Premium', 49.99, 30),
(3, 'Membresía VIP', 79.99, 30),
(4, 'Membresía Anual Básica', 299.99, 365),
(5, 'Membresía Anual Premium', 499.99, 365),
(6, 'Membresía Anual VIP', 799.99, 365);

-- Insertar usuario administrador por defecto
-- Contraseña: admin123 (encriptada con BCrypt)
INSERT IGNORE INTO usuarios (idUSUARIOS, nombres, apellidos, email, password_hash, fecha_creacion, fecha_modificacion, ROLES_idROLES) VALUES
(1, 'Administrador', 'Sistema', 'admin@gimnasio.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', NOW(), NOW(), 1);

-- Insertar usuario cliente de ejemplo
-- Contraseña: cliente123 (encriptada con BCrypt)
INSERT IGNORE INTO usuarios (idUSUARIOS, nombres, apellidos, email, password_hash, fecha_creacion, fecha_modificacion, ROLES_idROLES, MEMBRESIAS_idMEMBRESIAS) VALUES
(2, 'Juan', 'Pérez', 'juan.perez@email.com', '$2a$10$VEjxo0jq2YNzZc.e92F3f.c1lxD4eC1uau4/TXuLeGU0xRI2k6YAa', NOW(), NOW(), 2, 1);
