--
-- Dumping data for table prendas
--

INSERT INTO prendas
(prd_descripcion,
prd_precio_base,
prd_tipo_prenda
)
VALUES
('Camisa Celeste',10.24,'CAMISA'),
('Camisa Blanca',100.50,'CAMISA'),
('Saco Vestir',102.40,'SACO'),
('Pantalón Gabardina Beige',1004.00,'PANTALON'),
('Tapado Negro',3234.22,'TAPADO'),
('Tapado de Piel',232.20,'TAPADO'),
('Camisa Gris Claro',165.20,'CAMISA'),
('Camisa Gris',1645.24,'CAMISA'),
('Camisa Gris Oscuro',203.00,'CAMISA'),
('Bufanda',34.45,'CAMISA'),
('Media',9898.00,'TAPADO');

INSERT INTO PRENDAS
(prd_id,
prd_descripcion,
prd_precio_base,
prd_tipo_prenda)
VALUES
(1, 'Camisa Celeste', 10.24, 'CAMISA'),
(2, 'Camisa Blanca', 100.50, 'CAMISA'),
(3, 'Saco Vestir', 102.4, 'SACO'),
(4, 'Pantalon Gabardina Beige', 1004, 'PANTALON');


INSERT INTO CLIENTES
(cli_id,
cli_nombre,
cli_apellido)
VALUES
(1, 'Pepe1', 'Pepito1'),
(2, 'Pepe2', 'Pepito2'),
(3, 'Pepe3', 'Pepito3'),
(4, 'Pepe4', 'Pepito4');
