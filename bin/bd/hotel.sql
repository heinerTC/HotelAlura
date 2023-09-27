-- Crear la tabla HUESPEDES
CREATE TABLE HUESPEDES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    nacionalidad VARCHAR(100),
    telefono VARCHAR(20),
    reserva_id INT,
    FOREIGN KEY (reserva_id) REFERENCES RESERVAS(id)
);

-- Crear la tabla RESERVAS
CREATE TABLE RESERVAS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fechaEntrada DATE NOT NULL,
    fechaSalida DATE NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    formaPago VARCHAR(50) NOT NULL
);

-- Crear la tabla USUARIOS
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL,
    contrasena VARCHAR(100) NOT NULL
);
