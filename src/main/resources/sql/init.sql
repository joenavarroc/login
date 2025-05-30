DROP DATABASE IF EXISTS agenda;
CREATE DATABASE agenda;

USE agenda;

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NULL,  -- puede ser NULL para usuarios OAuth
    provider VARCHAR(50) NOT NULL DEFAULT 'local',
    provider_id VARCHAR(100) NULL,
    email VARCHAR(100) NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES usuario(id),
    FOREIGN KEY (updated_by) REFERENCES usuario(id)
);

-- Tabla de contactos
CREATE TABLE contacto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,  
    email VARCHAR(100) NOT NULL,
    usuario_id BIGINT,  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Fecha de última actualización
    deleted_at TIMESTAMP NULL,  -- Marca la fecha de eliminación (en caso de borrado suave)
    is_active BOOLEAN DEFAULT TRUE,  -- Marca el estado del contacto (activo/inactivo)
    created_by BIGINT,  -- ID del usuario que creó el registro
    updated_by BIGINT,  -- ID del usuario que hizo la última modificación
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (created_by) REFERENCES usuario(id),
    FOREIGN KEY (updated_by) REFERENCES usuario(id),
    UNIQUE KEY uk_usuario_nombre_telefono_email (usuario_id, nombre, telefono, email)
);

-- Tabla de eventos con relación a usuarioCREATE TABLE evento (CREATE TABLE evento (
CREATE TABLE evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    tipo ENUM('reunion', 'cumpleanos', 'trabajo', 'personal') NOT NULL,  -- Ejemplo de uso de ENUM
    usuario_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Fecha de última actualización
    deleted_at TIMESTAMP NULL,  -- Fecha de eliminación (en caso de borrado suave)
    created_by BIGINT,  -- ID del usuario que creó el registro
    updated_by BIGINT,  -- ID del usuario que hizo la última modificación
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (created_by) REFERENCES usuario(id),
    FOREIGN KEY (updated_by) REFERENCES usuario(id)
);

-- Insertar registros de ejemplo
INSERT INTO usuario (username, password) VALUES
('juan', '$2a$10$XgykVKYZHukDuNaTzBCQJO0Yue51wrHe7Ac29x/8a8TOjM3sRMMmG'), -- 1234
('ana',  '$2a$10$EoI/f5Id6RaaSW7Z2eq1rOujBWK9cHFuPWaEfJ7G7lK4TsvrHvmbS'); -- 1234

-- Contactos de juan
INSERT INTO contacto (nombre, telefono, email, usuario_id) VALUES
('Carlos Rivera', '555-1111', 'carlos.rivera@example.com', 1),
('Laura Méndez', '555-2222', 'laura.mendez@example.com', 1),
('Pedro Sánchez', '555-3333', 'pedro.sanchez@example.com', 1);

-- Contactos de ana
INSERT INTO contacto (nombre, telefono, email, usuario_id) VALUES
('Lucía Torres', '555-4444', 'lucia.torres@example.com', 2),
('Miguel Rojas', '555-5555', 'miguel.rojas@example.com', 2),
('Elena Vidal', '555-6666', 'elena.vidal@example.com', 2);
-- Contactos de joe
INSERT INTO contacto (nombre, telefono, email, usuario_id) VALUES
('Ariana Gómez', '555-1001', 'ariana.gomez@example.com', 3),
('Bruno Acosta', '555-9999', 'bruno.acosta@example.com', 3),
('Carla Méndez', '555-1002', 'carla.mendez@example.com', 3),
('Diego Herrera', '555-1003', 'diego.herrera@example.com', 3),
('Elena Rivas', '555-1004', 'elena.rivas@example.com', 3),
('Fernando Soto', '555-1005', 'fernando.soto@example.com', 3),
('Gabriela Ruiz', '555-1006', 'gabriela.ruiz@example.com', 3),
('Héctor Morales', '555-1007', 'hector.morales@example.com', 3),
('Isabel Pardo', '555-1008', 'isabel.pardo@example.com', 3),
('Julián Torres', '555-1009', 'julian.torres@example.com', 3),
('Karla Núñez', '555-1010', 'karla.nunez@example.com', 3),
('Laura Ibáñez', '555-1011', 'laura.ibanez@example.com', 3),
('Martín Vargas', '555-1012', 'martin.vargas@example.com', 3),
('Nicolás Peña', '555-1013', 'nicolas.pena@example.com', 3),
('Olivia Cordero', '555-1014', 'olivia.cordero@example.com', 3),
('Pablo Leiva', '555-1015', 'pablo.leiva@example.com', 3),
('Queralt Vives', '555-1016', 'queralt.vives@example.com', 3),
('Raúl Cabrera', '555-1017', 'raul.cabrera@example.com', 3),
('Sofía Márquez', '555-1018', 'sofia.marquez@example.com', 3),
('Tomás Díaz', '555-7777', 'tomas.diaz@example.com', 3),
('Úrsula Ortega', '555-1019', 'ursula.ortega@example.com', 3),
('Valeria Silva', '555-8888', 'valeria.silva@example.com', 3),
('Walter Jiménez', '555-1020', 'walter.jimenez@example.com', 3),
('Ximena Castro', '555-1021', 'ximena.castro@example.com', 3),
('Yago Serrano', '555-1022', 'yago.serrano@example.com', 3),
('Zaira Lozano', '555-1023', 'zaira.lozano@example.com', 3);


-- Eventos de juan (usuario_id = 1)
INSERT INTO evento (titulo, descripcion, fecha, hora, usuario_id) VALUES
('Reunión con cliente', 'Presentación del producto', '2025-05-15', '10:00:00', 1),
('Cita médica', 'Chequeo anual', '2025-05-16', '08:30:00', 1);

-- Eventos de ana (usuario_id = 2)
INSERT INTO evento (titulo, descripcion, fecha, hora, usuario_id) VALUES
('Clases de yoga', 'Sesión semanal', '2025-05-17', '18:00:00', 2),
('Entrevista de trabajo', 'Posición en marketing', '2025-05-18', '09:00:00', 2);

-- Eventos de joe (usuario_id = 3)
INSERT INTO evento (titulo, descripcion, fecha, hora, usuario_id) VALUES
('Taller de programación', 'Curso de Java y Spring Boot', '2025-05-19', '14:00:00', 3),
('Cena familiar', 'Cumpleaños de mamá', '2025-05-20', '20:30:00', 3);

