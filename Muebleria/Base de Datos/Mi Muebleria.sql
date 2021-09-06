-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema fabricamuebles
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema fabricamuebles
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fabricamuebles` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `fabricamuebles` ;

-- -----------------------------------------------------
-- Table `fabricamuebles`.`Pieza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Pieza` (
  `tipo` VARCHAR(20) NOT NULL,
  `cantidad_stock` INT NOT NULL,
  PRIMARY KEY (`tipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Asignacion_Precio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Asignacion_Precio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `precio` DECIMAL(10,0) NOT NULL,
  `tipo` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Asignacion_Precio_Pieza_idx` (`tipo` ASC) VISIBLE,
  CONSTRAINT `fk_Asignacion_Precio_Pieza`
    FOREIGN KEY (`tipo`)
    REFERENCES `fabricamuebles`.`Pieza` (`tipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Cliente` (
  `NIT` INT NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `direccion` VARCHAR(60) NOT NULL,
  `municipio` VARCHAR(45) NULL,
  `departamento` VARCHAR(45) NULL,
  PRIMARY KEY (`NIT`),
  UNIQUE INDEX `municipio_UNIQUE` (`municipio` ASC) VISIBLE,
  UNIQUE INDEX `departamento_UNIQUE` (`departamento` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Mueble`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Mueble` (
  `Nombre` VARCHAR(25) NOT NULL,
  `precio` DECIMAL(10,0) NULL DEFAULT NULL,
  PRIMARY KEY (`Nombre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Usuario` (
  `nombre` VARCHAR(50) NOT NULL,
  `tipo` INT NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`nombre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Ensamble_Mueble`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Ensamble_Mueble` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha_ensamble` DATE NOT NULL,
  `precio_ensamble` DECIMAL(10,0) NOT NULL,
  `nombre_mueble` VARCHAR(50) NOT NULL,
  `nombre_usuario` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Ensamble_Mueble_Mueble1_idx` (`nombre_mueble` ASC) VISIBLE,
  INDEX `fk_Ensamble_Mueble_Usuario1_idx` (`nombre_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_Ensamble_Mueble_Mueble`
    FOREIGN KEY (`nombre_mueble`)
    REFERENCES `fabricamuebles`.`Mueble` (`Nombre`),
  CONSTRAINT `fk_Ensamble_Mueble_Usuario`
    FOREIGN KEY (`nombre_usuario`)
    REFERENCES `fabricamuebles`.`Usuario` (`nombre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Compra_Mueble`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Compra_Mueble` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha_compra` DATE NOT NULL,
  `devolucion` TINYINT NOT NULL,
  `NIT_Cliente` INT NULL DEFAULT NULL,
  `id_ensamble` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Compra_Mueble_Cliente1_idx` (`NIT_Cliente` ASC) VISIBLE,
  INDEX `fk_Compra_Mueble_Ensamble_Mueble1_idx` (`id_ensamble` ASC) VISIBLE,
  CONSTRAINT `fk_Compra_Mueble_Cliente1`
    FOREIGN KEY (`NIT_Cliente`)
    REFERENCES `fabricamuebles`.`Cliente` (`NIT`),
  CONSTRAINT `fk_Compra_Mueble_Ensamble_Mueble1`
    FOREIGN KEY (`id_ensamble`)
    REFERENCES `fabricamuebles`.`Ensamble_Mueble` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `fabricamuebles`.`Ensamble_Pieza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fabricamuebles`.`Ensamble_Pieza` (
  `nombre_muebles` VARCHAR(25) NOT NULL,
  `tipo_pieza` VARCHAR(20) NOT NULL,
  `cantidad_pieza` INT NOT NULL,
  PRIMARY KEY (`nombre_muebles`, `tipo_pieza`),
  INDEX `fk_Ensamble_Pieza_Pieza1_idx` (`tipo_pieza` ASC) VISIBLE,
  INDEX `fk_Ensamble_Pieza_Mueble1_idx` (`nombre_muebles` ASC) VISIBLE,
  CONSTRAINT `fk_Ensamble_Pieza_Mueble1`
    FOREIGN KEY (`nombre_muebles`)
    REFERENCES `fabricamuebles`.`Mueble` (`Nombre`),
  CONSTRAINT `fk_Ensamble_Pieza_Pieza1`
    FOREIGN KEY (`tipo_pieza`)
    REFERENCES `fabricamuebles`.`Pieza` (`tipo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
CREATE USER 'Emilio'@'localhost' IDENTIFIED BY 'EmilioMalRod';

GRANT ALL PRIVILEGES ON Muebleria.* TO 'Emilio'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
