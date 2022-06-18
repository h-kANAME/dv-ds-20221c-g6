--
-- Drop Table structure for table prendas
--

DROP TABLE IF EXISTS prendas;


--
-- Table structure for table prendas
--

CREATE TABLE prendas (
 prd_id bigint NOT NULL AUTO_INCREMENT,
 prd_descripcion varchar(255) DEFAULT NULL,
 prd_precio_base decimal(19,2) DEFAULT NULL,
 prd_tipo_prenda varchar(255) DEFAULT NULL,
 PRIMARY KEY (prd_id)
);
