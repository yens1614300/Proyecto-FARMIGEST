-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-06-2019 a las 23:37:25
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
  `ApellidoPaterno` varchar(80) NOT NULL DEFAULT '',
  `ApellidoMaterno` varchar(80) NOT NULL DEFAULT '',
  `DNI` varchar(16) NOT NULL DEFAULT '',
  `Sexo` varchar(16) NOT NULL,
  `Celular` varchar(24) NOT NULL DEFAULT '',
  `FechaNacimiento` date NOT NULL,
  `FechaRegistro` date NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `Estado` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `Nombre`, `ApellidoPaterno`, `ApellidoMaterno`, `DNI`, `Sexo`, `Celular`, `FechaNacimiento`, `FechaRegistro`, `idUsuario`, `Estado`) VALUES
(23, 'asd', 'asd', 'asd', '123', 'Masculino', '14455', '2019-06-15', '2019-06-23', 38, 1),
(27, 'sa', 'as', 'as', '71302136', 'Masculino', '125632158', '2019-06-22', '2019-06-23', 39, 1),
(29, 'Yenslin', 'S.A.', '', '8075562', 'Masculino', '995632895', '2019-06-23', '2019-06-23', 1, 1);

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
(17, 1, '322.80', '58.10', '380.90', '2019-06-23', '001-000017', 'Cancelado', 'Entregado'),
(18, 1, '278.30', '50.09', '328.39', '2019-06-23', '001-000018', 'Cancelado', 'Pendiente'),
(19, 1, '378.00', '68.04', '446.04', '2019-06-23', '001-000019', 'Cancelado', 'Pendiente'),
(20, 1, '39.00', '7.02', '46.02', '2019-06-23', '001-000020', 'Cancelado', 'Pendiente');

--
-- Disparadores `compra`
--
DELIMITER $$
CREATE TRIGGER `triggerNewCliente` BEFORE INSERT ON `compra` FOR EACH ROW BEGIN
	
  declare cantidad int;
  set cantidad=0;
  set cantidad=(select COUNT(*) from cliente where idUsuario=new.idUsuario);
  
  if(cantidad=0) then
  	
insert INTO cliente (Nombre,ApellidoPaterno,DNI,Sexo,celular, idUsuario,FechaNacimiento,FechaRegistro) select nombre, apellido,dni,sexo,celular,idUsuario,LEFT(now(),10),LEFT(now(),10) from empleado where idUsuario=new.idUsuario;
     end if;    
    
END
$$
DELIMITER ;

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
(23, 17, 6, 2, 28, '2.60', '72.80', '13.10', '85.90', 1),
(24, 17, 11, 2, 100, '2.50', '250.00', '45.00', '295.00', 1),
(25, 18, 6, 2, 83, '2.60', '215.80', '38.84', '254.64', 1),
(26, 18, 11, 2, 25, '2.50', '62.50', '11.25', '73.75', 1),
(27, 19, 11, 2, 42, '2.50', '105.00', '18.90', '123.90', 1),
(28, 19, 6, 2, 105, '2.60', '273.00', '49.14', '322.14', 1),
(29, 20, 6, 2, 15, '2.60', '39.00', '7.02', '46.02', 1);

--
-- Disparadores `detallecompra`
--
DELIMITER $$
CREATE TRIGGER `updateStock_Ingreso` BEFORE INSERT ON `detallecompra` FOR EACH ROW BEGIN

	declare xcantidad INT;
    declare my_value int; 
    declare num_rows int ; 
    declare done int ;
    declare xdisponible int;
    
    declare my_cursor cursor for SELECT idDetalleIngreso FROM detalleingreso WHERE idProducto=new.idProducto and idSede=new.idSede and cantDisponible>0;
    declare continue handler for not found set done = true; 
    set xcantidad=new.cantidad;
    set done=false;
    set num_rows=0;
    set xdisponible=0;
    
 	open my_cursor; 
 	my_loop: loop
    	set done = false;
		fetch my_cursor into my_value; 
        
        if done then
          leave my_loop;
        end if;
        
        if xcantidad=0 then
          leave my_loop;
        end if;
        
        select  cantDisponible into xdisponible from detalleingreso where idDetalleIngreso=my_value;
    
        if (xdisponible >= xcantidad) then
            update detalleingreso set cantDisponible = cantDisponible-xcantidad,cantEntregada=cantEntregada+xcantidad where idDetalleIngreso=my_value;
            set xcantidad=0;
        else
            update detalleingreso set cantDisponible = 0,cantEntregada=cantEntregada+xdisponible where idDetalleIngreso=my_value;
            set xcantidad=xcantidad-xdisponible;
        end if;
    
    
	end loop my_loop; 
	close my_cursor; 
    
    
END
$$
DELIMITER ;

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
(20, 15, 6, 2, 100, 0, 100),
(21, 16, 6, 2, 34, 0, 34),
(22, 16, 11, 2, 456, 289, 167),
(23, 17, 6, 2, 100, 3, 97),
(24, 18, 6, 1, 34, 34, 0);

--
-- Disparadores `detalleingreso`
--
DELIMITER $$
CREATE TRIGGER `updateStock` BEFORE INSERT ON `detalleingreso` FOR EACH ROW BEGIN
	UPDATE producto SET STOCK=STOCK+new.cantidad
    WHERE idProducto=new.idProducto;
END
$$
DELIMITER ;

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
(18, 15, 6, 0, 28),
(19, 15, 11, 0, 100);

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
(15, 'I001-000015', '12345678956', '2019-06-23', 2, 'Guia Remision'),
(16, 'I001-000016', '56325636256', '2019-06-26', 2, 'Guia Remision'),
(17, 'I001-000017', '65556235624', '2019-06-23', 2, 'Guia Remision'),
(18, 'I001-000018', '12334567876', '2019-06-06', 2, 'Guia Remision');

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
(8, 11, '429.52', '74156545', '2019-06-19'),
(9, 12, '33.51', '34353646', '2019-06-23'),
(10, 13, '865.41', '234342', '2019-06-23'),
(11, 14, '306.80', '23443243', '2019-06-23'),
(12, 15, '1360.54', '2312321', '2019-06-23'),
(13, 16, '2063.11', '2423', '2019-06-23'),
(14, 17, '380.90', '1313123', '2019-06-23'),
(15, 18, '328.39', '231231', '2019-06-23'),
(16, 19, '446.04', '12313231', '2019-06-23'),
(17, 20, '46.02', '3453435', '2019-06-23');

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
  `stock` int(11) NOT NULL,
  `categoria` varchar(200) NOT NULL,
  `marca` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `Nombre`, `Descripcion`, `pre_venta`, `pre_compra`, `stock`, `categoria`, `marca`) VALUES
(6, 'diasepan', 'diaddf', '2.60', '0.00', 46, 'cat1', 'ab1'),
(7, 'metanfemina', 'pooo', '25.80', '0.00', 0, 'cat2', 'marca2'),
(10, 'diasepan', 'asd', '12.00', '0.00', 0, 'asd', 'ab12'),
(11, 'diasepan', 'asd', '2.50', '0.00', 456, 'gggg', 'ABB');

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
(15, 17, 29, 2, '2019-06-23', 'S01-000015', 'Guia Remision', 1);

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
(38, 'ys@gmail.com', '123', 2, 1),
(39, 'ysa@gmai', '123', 2, 1);

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
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `idCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  MODIFY `idDetalleCompra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `detalleingreso`
--
ALTER TABLE `detalleingreso`
  MODIFY `idDetalleIngreso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `detallesalida`
--
ALTER TABLE `detallesalida`
  MODIFY `idDetalleSalida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `idEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `ingreso`
--
ALTER TABLE `ingreso`
  MODIFY `idIngreso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `nivelusuario`
--
ALTER TABLE `nivelusuario`
  MODIFY `idNivelUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `salida`
--
ALTER TABLE `salida`
  MODIFY `idSalida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `sede`
--
ALTER TABLE `sede`
  MODIFY `idSede` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
