-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-06-2019 a las 07:23:10
-- Versión del servidor: 10.3.15-MariaDB
-- Versión de PHP: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_farmigest`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `ApellidoPaterno` varchar(80) NOT NULL,
  `ApellidoMaterno` varchar(80) NOT NULL,
  `DNI` varchar(16) NOT NULL,
  `Sexo` varchar(16) NOT NULL,
  `Celular` varchar(24) NOT NULL,
  `FechaNacimiento` date NOT NULL,
  `FechaRegistro` date NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `Nombre`, `ApellidoPaterno`, `ApellidoMaterno`, `DNI`, `Sexo`, `Celular`, `FechaNacimiento`, `FechaRegistro`, `idUsuario`, `Estado`) VALUES
(23, 'asd', 'asd', 'asd', '123', 'Masculino', '14455', '2019-06-15', '2019-06-23', 38, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `idCompra` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Monto` decimal(18,2) NOT NULL,
  `Impuesto` decimal(18,2) NOT NULL,
  `MontoTotal` decimal(18,2) NOT NULL,
  `FechaCompra` date NOT NULL,
  `NroDocumento` varchar(20) NOT NULL,
  `EstadoCompra` varchar(50) NOT NULL,
  `EstadoEntrega` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`idCompra`, `idUsuario`, `Monto`, `Impuesto`, `MontoTotal`, `FechaCompra`, `NroDocumento`, `EstadoCompra`, `EstadoEntrega`) VALUES
(5, 20, '17.50', '3.15', '20.65', '2019-06-10', '001-00005', 'Pendiente', 'Entregado'),
(6, 20, '31.50', '5.67', '37.17', '2019-06-10', '001-00006', 'Pendiente', 'Entregado'),
(7, 20, '98.00', '17.64', '115.64', '2019-06-10', '001-00007', 'Pendiente', 'Entregado'),
(8, 20, '21.00', '3.78', '24.78', '2019-06-11', '001-00008', 'Pendiente', 'Entregado'),
(9, 0, '38.50', '6.93', '45.43', '2019-06-15', '001-00009', 'Pendiente', 'Pendiente'),
(10, 1, '36.00', '6.48', '42.48', '2019-06-16', '001-000010', 'Cancelado', 'Pendiente'),
(11, 1, '364.00', '65.52', '429.52', '2019-06-19', '001-000011', 'Cancelado', 'Entregado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecompra`
--

CREATE TABLE `detallecompra` (
  `idDetalleCompra` int(11) NOT NULL,
  `idCompra` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idSede` int(11) NOT NULL,
  `Cantidad` int(11) NOT NULL,
  `PreUnidad` decimal(18,2) NOT NULL,
  `Monto` decimal(18,2) NOT NULL,
  `Impuesto` decimal(18,2) NOT NULL,
  `MontoTotal` decimal(18,2) NOT NULL,
  `Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detallecompra`
--

INSERT INTO `detallecompra` (`idDetalleCompra`, `idCompra`, `idProducto`, `idSede`, `Cantidad`, `PreUnidad`, `Monto`, `Impuesto`, `MontoTotal`, `Estado`) VALUES
(3, 5, 6, 0, 5, '3.50', '17.50', '3.15', '20.65', 1),
(4, 6, 2, 0, 6, '3.50', '21.00', '3.78', '24.78', 1),
(6, 7, 1, 0, 6, '3.50', '21.00', '3.78', '24.78', 1),
(7, 7, 3, 0, 22, '3.50', '77.00', '13.86', '90.86', 1),
(8, 8, 1, 0, 6, '3.50', '21.00', '3.78', '24.78', 1),
(9, 9, 1, 0, 5, '3.50', '17.50', '3.15', '20.65', 1),
(10, 9, 2, 0, 6, '3.50', '21.00', '3.78', '24.78', 1),
(11, 10, 10, 0, 3, '12.00', '36.00', '6.48', '42.48', 1),
(12, 11, 7, 0, 5, '53.60', '268.00', '48.24', '316.24', 1),
(13, 11, 10, 0, 8, '12.00', '96.00', '17.28', '113.28', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleingreso`
--

CREATE TABLE `detalleingreso` (
  `idDetalleIngreso` int(11) NOT NULL,
  `idIngreso` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idSede` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `cantDisponible` int(11) NOT NULL,
  `cantEntregada` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalleingreso`
--

INSERT INTO `detalleingreso` (`idDetalleIngreso`, `idIngreso`, `idProducto`, `idSede`, `cantidad`, `cantDisponible`, `cantEntregada`) VALUES
(10, 10, 10, 1, 500, 500, 0),
(11, 11, 6, 1, 100, 100, 0),
(12, 12, 7, 2, 5, 5, 0),
(13, 13, 7, 1, 6, 6, 0),
(14, 14, 7, 1, 10, 10, 0),
(15, 14, 10, 1, 30, 30, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallesalida`
--

CREATE TABLE `detallesalida` (
  `idDetalleSalida` int(11) NOT NULL,
  `idSalida` int(11) NOT NULL,
  `idProducto` int(11) NOT NULL,
  `idSede` int(11) NOT NULL,
  `Cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detallesalida`
--

INSERT INTO `detallesalida` (`idDetalleSalida`, `idSalida`, `idProducto`, `idSede`, `Cantidad`) VALUES
(2, 6, 1, 0, 5),
(3, 7, 1, 0, 5),
(4, 8, 1, 0, 5),
(5, 9, 2, 0, 6),
(6, 9, 3, 0, 3),
(8, 10, 1, 0, 6),
(9, 10, 3, 0, 22),
(11, 11, 1, 0, 6),
(12, 12, 1, 0, 6),
(13, 12, 3, 0, 22),
(15, 13, 1, 0, 6),
(16, 14, 7, 0, 5),
(17, 14, 10, 0, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `idEmpleado` int(11) NOT NULL,
  `Nombre` varchar(150) NOT NULL,
  `Apellido` varchar(150) NOT NULL,
  `DNI` varchar(16) NOT NULL,
  `Sexo` varchar(16) NOT NULL,
  `Celular` varchar(24) NOT NULL,
  `Area` varchar(80) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`idEmpleado`, `Nombre`, `Apellido`, `DNI`, `Sexo`, `Celular`, `Area`, `idUsuario`, `Estado`) VALUES
(2, 'Yenslin', 'S.A.', '8075562', 'Masculino', '995632895', 'Sistemas', 1, 1),
(5, 'micael', 'sa', '71302156', 'Masculino', '998515594', 'Sistemas', 37, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingreso`
--

CREATE TABLE `ingreso` (
  `idIngreso` int(11) NOT NULL,
  `nroDocumento` varchar(24) NOT NULL,
  `rucproveedor` varchar(15) NOT NULL,
  `fechaIngreso` date NOT NULL,
  `idEmpleado` int(11) NOT NULL,
  `tipoDocumento` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ingreso`
--

INSERT INTO `ingreso` (`idIngreso`, `nroDocumento`, `rucproveedor`, `fechaIngreso`, `idEmpleado`, `tipoDocumento`) VALUES
(10, 'I001-000010', '12345678945', '2019-06-22', 2, 'Guia Remision'),
(11, 'I001-000011', '12345678952', '2019-06-22', 2, 'Guia Remision'),
(12, 'I001-000012', '54545653125', '2019-06-22', 2, 'Guia Remision'),
(13, 'I001-000013', '54545789546', '2019-06-21', 2, 'Guia Remision'),
(14, 'I001-000014', '12582227845', '2019-06-28', 2, 'Guia Remision');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nivelusuario`
--

CREATE TABLE `nivelusuario` (
  `idNivelUsuario` int(11) NOT NULL,
  `Nivel` varchar(100) NOT NULL,
  `Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `nivelusuario`
--

INSERT INTO `nivelusuario` (`idNivelUsuario`, `Nivel`, `Estado`) VALUES
(1, 'Personal', 1),
(2, 'Cliente', 1),
(3, 'Administrador', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL,
  `idCompra` int(11) NOT NULL,
  `Monto` decimal(18,2) NOT NULL,
  `NumeroTarjeta` varchar(16) NOT NULL,
  `FechaPago` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`idPago`, `idCompra`, `Monto`, `NumeroTarjeta`, `FechaPago`) VALUES
(1, 4, '12.39', '223214', '2019-06-10'),
(2, 5, '20.65', '998515584', '2019-06-10'),
(3, 6, '37.17', '24234234', '2019-06-10'),
(4, 7, '115.64', '123213', '2019-06-10'),
(5, 8, '24.78', '99893', '2019-06-11'),
(6, 9, '45.43', '9854654534', '2019-06-15'),
(7, 10, '42.48', '232', '2019-06-16'),
(8, 11, '429.52', '74156545', '2019-06-19');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `Nombre` varchar(200) NOT NULL,
  `Descripcion` varchar(500) NOT NULL,
  `pre_venta` decimal(18,2) NOT NULL,
  `pre_compra` decimal(18,2) NOT NULL,
  `categoria` varchar(200) NOT NULL,
  `marca` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `Nombre`, `Descripcion`, `pre_venta`, `pre_compra`, `categoria`, `marca`) VALUES
(6, 'diasepan', 'diaddf', '2.60', '0.00', 'cat1', 'ab1'),
(7, 'metanfemina', 'pooo', '25.80', '0.00', 'cat2', 'marca2'),
(10, 'diasepan', 'asd', '12.00', '0.00', 'asd', 'ab12');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `salida`
--

CREATE TABLE `salida` (
  `idSalida` int(11) NOT NULL,
  `idCompra` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idEmpleado` int(11) NOT NULL,
  `FechaSalida` date NOT NULL,
  `NroDocumento` varchar(24) NOT NULL,
  `TipoDocumento` varchar(50) NOT NULL,
  `Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `salida`
--

INSERT INTO `salida` (`idSalida`, `idCompra`, `idCliente`, `idEmpleado`, `FechaSalida`, `NroDocumento`, `TipoDocumento`, `Estado`) VALUES
(6, 5, 1, 2, '2019-06-15', 'S01-00006', 'Guia Remision', 1),
(7, 5, 1, 2, '2019-06-15', 'S01-00007', 'Guia Remision', 1),
(8, 5, 1, 2, '2019-06-15', 'S01-00008', 'Guia Remision', 1),
(9, 6, 1, 2, '2019-06-15', 'S01-00009', 'Guia Remision', 1),
(10, 7, 1, 2, '2019-06-15', 'S01-000010', 'Guia Remision', 1),
(11, 8, 1, 2, '2019-06-15', 'S01-000011', 'Guia Remision', 1),
(12, 7, 1, 2, '2019-06-16', 'S01-000012', 'Guia Remision', 1),
(13, 8, 1, 2, '2019-06-16', 'S01-000013', 'Guia Remision', 1),
(14, 11, 19, 2, '2019-06-19', 'S01-000014', 'Guia Remision', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sede`
--

CREATE TABLE `sede` (
  `idSede` int(11) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Distrito` varchar(200) NOT NULL,
  `Departamento` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sede`
--

INSERT INTO `sede` (`idSede`, `Direccion`, `Distrito`, `Departamento`) VALUES
(1, 'Av lo olivares #365', 'San Miguel', 'Lima'),
(2, 'Av Las Gladiolas #2265', 'Callao', 'Lima');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `logUsuario` varchar(70) NOT NULL,
  `pwdUsuario` varchar(16) NOT NULL,
  `IdNivelUsuario` int(11) NOT NULL,
  `Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `logUsuario`, `pwdUsuario`, `IdNivelUsuario`, `Estado`) VALUES
(1, 'admin', '123', 3, 1),
(37, 'mcael@farmigest.com', '123', 1, 1),
(38, 'ys@gmail.com', '123', 2, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`idCompra`);

--
-- Indices de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  ADD PRIMARY KEY (`idDetalleCompra`);

--
-- Indices de la tabla `detalleingreso`
--
ALTER TABLE `detalleingreso`
  ADD PRIMARY KEY (`idDetalleIngreso`);

--
-- Indices de la tabla `detallesalida`
--
ALTER TABLE `detallesalida`
  ADD PRIMARY KEY (`idDetalleSalida`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`idEmpleado`);

--
-- Indices de la tabla `ingreso`
--
ALTER TABLE `ingreso`
  ADD PRIMARY KEY (`idIngreso`);

--
-- Indices de la tabla `nivelusuario`
--
ALTER TABLE `nivelusuario`
  ADD PRIMARY KEY (`idNivelUsuario`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`);

--
-- Indices de la tabla `salida`
--
ALTER TABLE `salida`
  ADD PRIMARY KEY (`idSalida`);

--
-- Indices de la tabla `sede`
--
ALTER TABLE `sede`
  ADD PRIMARY KEY (`idSede`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `idCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  MODIFY `idDetalleCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `detalleingreso`
--
ALTER TABLE `detalleingreso`
  MODIFY `idDetalleIngreso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `detallesalida`
--
ALTER TABLE `detallesalida`
  MODIFY `idDetalleSalida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `idEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `ingreso`
--
ALTER TABLE `ingreso`
  MODIFY `idIngreso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `nivelusuario`
--
ALTER TABLE `nivelusuario`
  MODIFY `idNivelUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `salida`
--
ALTER TABLE `salida`
  MODIFY `idSalida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `sede`
--
ALTER TABLE `sede`
  MODIFY `idSede` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
