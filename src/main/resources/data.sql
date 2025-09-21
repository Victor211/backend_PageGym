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
(1, 'Administrador', 'Sistema', 'admin@gimnasio.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVMLkxNqyNuVPNEeYjDJHu', NOW(), NOW(), 1);

-- Insertar usuario cliente de ejemplo
-- Contraseña: cliente123 (encriptada con BCrypt)
INSERT IGNORE INTO usuarios (idUSUARIOS, nombres, apellidos, email, password_hash, fecha_creacion, fecha_modificacion, ROLES_idROLES, MEMBRESIAS_idMEMBRESIAS) VALUES
(2, 'Juan', 'Pérez', 'juan.perez@email.com', '$2a$10$8K1p/H9jd7F2LRvyBYgzaOQw5f5Kf5Kf5Kf5Kf5Kf5Kf5Kf5Kf5K', NOW(), NOW(), 2, 1);
