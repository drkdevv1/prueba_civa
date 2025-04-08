package com.reto.prueba.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SqlDataInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SqlDataInitializer(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Check if tables are empty
        Long marcaCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM marcas", Long.class);
        Long usuarioCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuarios", Long.class);

        if (marcaCount == null || marcaCount == 0) {
            // Insert marca records
            jdbcTemplate.execute("INSERT INTO marcas (nombre) VALUES ('Volvo')");
            jdbcTemplate.execute("INSERT INTO marcas (nombre) VALUES ('Scania')");
            jdbcTemplate.execute("INSERT INTO marcas (nombre) VALUES ('Fiat')");
            jdbcTemplate.execute("INSERT INTO marcas (nombre) VALUES ('Mercedes-Benz')");
            jdbcTemplate.execute("INSERT INTO marcas (nombre) VALUES ('Hyundai')");

            // Insert bus records with current timestamp
            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('101', 'ABC-123', NOW(), 'Bus de dos pisos, 50 asientos, aire acondicionado, WiFi, USB en cada asiento', 1, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('202', 'DEF-456', NOW(), 'Bus de un piso, 35 asientos, aire acondicionado', 2, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('303', 'GHI-789', NOW(), 'Bus pequeño, 25 asientos', 3, false)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('404', 'JKL-012', NOW(), 'Bus de lujo, 30 asientos reclinables, servicio de comida, WiFi', 4, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('505', 'MNO-345', NOW(), 'Bus estándar, 40 asientos, aire acondicionado', 5, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('606', 'PQR-678', NOW(), 'Bus híbrido ecológico, 45 asientos, WiFi, cargadores USB', 1, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('707', 'STU-901', NOW(), 'Bus para turismo, 28 asientos, sistema de audio guía', 4, false)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('808', 'VWX-234', NOW(), 'Bus escolar, 20 asientos, aire acondicionado, cinturones especiales de seguridad', 5, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('909', 'YZA-567', NOW(), 'Bus nocturno con asientos reclinables tipo cama, 25 asientos, WiFi, TV individual', 1, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('110', 'BCD-890', NOW(), 'Bus turístico panorámico, techo descubierto, 35 asientos, sistema de audio guía en 5 idiomas', 4, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('120', 'EFG-123', NOW(), 'Bus eléctrico ecológico, 40 asientos, autonomía de 300km, cargadores USB y WiFi', 2, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('130', 'HIJ-456', NOW(), 'Bus intermunicipal, 45 asientos, bodega amplia, aire acondicionado, baño', 3, false)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('140', 'KLM-789', NOW(), 'Minibus ejecutivo, 15 asientos de cuero, refrigerador, sistema de entretenimiento', 4, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('150', 'NOP-012', NOW(), 'Bus para personas con movilidad reducida, 30 asientos, rampa de acceso, espacios para sillas de ruedas', 5, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('160', 'QRS-345', NOW(), 'Bus biarticulado para transporte masivo, 85 asientos, 4 puertas de acceso', 1, true)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('170', 'TUV-678', NOW(), 'Bus antiguo restaurado para eventos, 30 asientos vintage, decoración retro', 3, false)");

            jdbcTemplate.execute(
                    "INSERT INTO buses (numero_bus, placa, fecha_creacion, caracteristicas, marca_id, activo) " +
                            "VALUES ('180', 'WXY-901', NOW(), 'Bus de larga distancia, 38 asientos reclinables 180°, servicio de catering, WiFi, entretenimiento', 2, true)");

            System.out.println("Database has been initialized with sample data");
        }

        // Insert default user if no users exist
        if (usuarioCount == null || usuarioCount == 0) {
            String encodedPassword = passwordEncoder.encode("password");
            jdbcTemplate.execute(
                    "INSERT INTO usuarios (username, email, password, fecha_creacion, nombre_completo, activo) " +
                            "VALUES ('admin', 'admin@example.com', '" + encodedPassword
                            + "', NOW(), 'Administrador', true)");
            System.out.println("Default user has been created");
        } else {
            System.out.println("Users already exist, skipping initialization");
        }
    }
}