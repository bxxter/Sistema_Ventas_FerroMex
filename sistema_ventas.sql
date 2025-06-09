-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 09-06-2025 a las 13:48:51
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_ventas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`) VALUES
(1, 'Herramientas manuales'),
(2, 'Herramientas eléctricas'),
(3, 'Materiales de construcción'),
(4, 'Ferretería general'),
(5, 'Material eléctrico'),
(6, 'Pinturas'),
(7, 'Fontanería'),
(8, 'Material eléctrico'),
(9, 'Seguridad'),
(10, 'Jardinería'),
(12, 'Papel');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `correo`, `activo`) VALUES
(1, 'Juan', 'santos', 'juan@gmail.com', 1),
(2, 'jose', 'santos', 'santos@gmail.com', 1),
(3, 'maria', 'santos', 'maria@gmail.com', 1),
(4, 'karla', 'perez', 'karla@gmail.com', 1),
(5, 'maria', 'perez', 'mari23@gmail.com', 1),
(6, 'kenia', 'perez', 'keni262@gmail.com', 1),
(7, 'Maria', 'Torres', 'ari63@gmail.com', 1),
(8, 'carlos', 'torres', 'carlos982@gmail.com', 1),
(9, 'fernando', 'canton', 'fern63722@gmail.com', 1),
(10, 'Fernadi', 'Santos', 'fer2625@gmail.com', 0),
(11, 'Fernando', 'Torres', 'fertorr@gmail.com', 1),
(12, 'Azael', 'Chavez', 'azaelzzz690@gmail.com', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`id`, `id_venta`, `id_producto`, `cantidad`, `precio`) VALUES
(1, 10, 13, 1, 180.00),
(2, 11, 13, 1, 180.00),
(3, 12, 13, 1, 180.00),
(4, 13, 12, 1, 75.00),
(5, 14, 13, 1, 180.00),
(6, 15, 12, 1, 75.00),
(7, 16, 13, 1, 180.00),
(8, 17, 12, 1, 75.00),
(9, 17, 13, 1, 180.00),
(10, 17, 43, 1, 2800.00),
(11, 18, 12, 1, 75.00),
(12, 18, 15, 1, 65.00),
(13, 18, 24, 1, 120.00),
(14, 19, 43, 3, 2800.00),
(15, 19, 41, 2, 850.00),
(16, 19, 222, 2, 32.00),
(17, 19, 65, 2, 6500.00),
(18, 20, 12, 1, 75.00),
(19, 20, 41, 1, 850.00),
(20, 21, 12, 1, 75.00),
(21, 22, 56, 1, 2200.00),
(22, 22, 35, 2, 120.00),
(23, 23, 56, 2, 2200.00),
(24, 24, 24, 1, 120.00),
(25, 24, 41, 3, 850.00),
(26, 24, 122, 1, 550.00),
(27, 24, 35, 1, 120.00),
(28, 24, 111, 2, 45.00),
(29, 25, 56, 2, 2200.00),
(30, 25, 90, 2, 55.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `precio`, `stock`, `categoria`, `activo`) VALUES
(2, 'Martillo bola 32 oz', 180.00, 35, 'Herramientas manuales', 0),
(3, 'Destornillador plano 1/4\" x 6\"', 45.00, 100, 'Herramientas manuales', 1),
(4, 'Destornillador Phillips #2 x 6\"', 48.00, 76, 'Herramientas manuales', 1),
(5, 'Juego de destornilladores (6 pzs)', 220.00, 25, 'Herramientas manuales', 1),
(6, 'Alicates de corte diagonal 7\"', 110.00, 55, 'Herramientas manuales', 1),
(7, 'Alicates de punta larga 6\"', 95.00, 42, 'Herramientas manuales', 1),
(8, 'Pinzas de presión 10\"', 150.00, 35, 'Herramientas manuales', 1),
(9, 'Llave ajustable 10\"', 110.00, 40, 'Herramientas manuales', 1),
(10, 'Llave Stillson 14\"', 280.00, 20, 'Herramientas manuales', 1),
(11, 'Juego de llaves combinadas (8 pzs)', 320.00, 15, 'Herramientas manuales', 1),
(12, 'Llave de tubo 12\"', 75.00, 44, 'Herramientas manuales', 1),
(13, 'Serrucho de 20 dientes', 180.00, 2, 'Herramientas manuales', 1),
(14, 'Serrucho de costilla', 210.00, 25, 'Herramientas manuales', 1),
(15, 'Cinta métrica 5m fibra de vidrio', 65.00, 69, 'Herramientas manuales', 1),
(16, 'Cinta métrica 8m metal', 95.00, 40, 'Herramientas manuales', 1),
(17, 'Nivel de burbuja 24\"', 150.00, 25, 'Herramientas manuales', 1),
(18, 'Nivel láser básico', 450.00, 10, 'Herramientas manuales', 1),
(19, 'Escuadra combinada', 85.00, 30, 'Herramientas manuales', 1),
(20, 'Cincel de 1/2\"', 55.00, 45, 'Herramientas manuales', 1),
(21, 'Cortador de vidrio', 65.00, 30, 'Herramientas manuales', 1),
(22, 'Raspador de vidrio', 35.00, 60, 'Herramientas manuales', 1),
(23, 'Cortafríos 10\"', 90.00, 25, 'Herramientas manuales', 1),
(24, 'Barreta mini 12\"', 120.00, 18, 'Herramientas manuales', 1),
(25, 'Barreta pesada 24\"', 180.00, 15, 'Herramientas manuales', 1),
(26, 'Cepillo de alambre', 45.00, 50, 'Herramientas manuales', 1),
(27, 'Limpiador de soldadura', 55.00, 40, 'Herramientas manuales', 1),
(28, 'Saca tornillos (extractor)', 75.00, 30, 'Herramientas manuales', 1),
(29, 'Afilador de cuchillos', 65.00, 40, 'Herramientas manuales', 1),
(30, 'Gato hidráulico 2 ton', 550.00, 8, 'Herramientas manuales', 1),
(31, 'Gato mecánico 1.5 ton', 320.00, 12, 'Herramientas manuales', 1),
(32, 'Torquímetro 1/2\"', 420.00, 10, 'Herramientas manuales', 1),
(33, 'Calibrador vernier 6\"', 280.00, 15, 'Herramientas manuales', 1),
(34, 'Sargento 6\"', 85.00, 40, 'Herramientas manuales', 1),
(35, 'Sargento 12\"', 120.00, 27, 'Herramientas manuales', 1),
(36, 'Prensa \"C\" 4\"', 95.00, 25, 'Herramientas manuales', 1),
(37, 'Formón 1\"', 75.00, 35, 'Herramientas manuales', 1),
(38, 'Gubia curva', 65.00, 30, 'Herramientas manuales', 1),
(39, 'Navaja multiusos', 55.00, 60, 'Herramientas manuales', 1),
(40, 'Kit herramientas 32 pzs', 650.00, 10, 'Herramientas manuales', 1),
(41, 'Taladro inalámbrico 12V', 850.00, 9, 'Herramientas eléctricas', 1),
(42, 'Taladro percutor 1/2\"', 1200.00, 12, 'Herramientas eléctricas', 1),
(43, 'Rotomartillo SDS Plus', 2800.00, 11, 'Herramientas eléctricas', 1),
(44, 'Sierra circular 7-1/4\"', 1500.00, 10, 'Herramientas eléctricas', 1),
(45, 'Sierra caladora 500W', 950.00, 12, 'Herramientas eléctricas', 1),
(46, 'Sierra de mesa 10\"', 4200.00, 5, 'Herramientas eléctricas', 1),
(47, 'Esmeril angular 4-1/2\"', 550.00, 18, 'Herramientas eléctricas', 1),
(48, 'Esmeril de banco 6\"', 850.00, 10, 'Herramientas eléctricas', 1),
(49, 'Lijadora orbital 1/4 hoja', 650.00, 15, 'Herramientas eléctricas', 1),
(50, 'Lijadora de banda 3\"', 1200.00, 8, 'Herramientas eléctricas', 1),
(51, 'Router 1-1/4 HP', 1350.00, 7, 'Herramientas eléctricas', 1),
(52, 'Soldadora inversora 160A', 3200.00, 6, 'Herramientas eléctricas', 1),
(53, 'Pistola de calor 1500W', 450.00, 15, 'Herramientas eléctricas', 1),
(54, 'Pistola para silicón 100W', 180.00, 25, 'Herramientas eléctricas', 1),
(55, 'Compresor 2 HP 8 galones', 2800.00, 7, 'Herramientas eléctricas', 1),
(56, 'Hidrolavadora 1300 PSI', 2200.00, 3, 'Herramientas eléctricas', 1),
(57, 'Pulidora 7\"', 950.00, 9, 'Herramientas eléctricas', 1),
(58, 'Dremel multiusos 3000', 1200.00, 10, 'Herramientas eléctricas', 1),
(59, 'Aspiradora de taller 10L', 850.00, 12, 'Herramientas eléctricas', 1),
(60, 'Generador 3500W', 8500.00, 4, 'Herramientas eléctricas', 1),
(61, 'Bomba sumergible 1/2 HP', 1800.00, 6, 'Herramientas eléctricas', 1),
(62, 'Bomba de agua 1 HP', 2200.00, 5, 'Herramientas eléctricas', 1),
(63, 'Cepillo eléctrico para madera', 950.00, 8, 'Herramientas eléctricas', 1),
(64, 'Cortadora de cerámica', 1500.00, 7, 'Herramientas eléctricas', 1),
(65, 'Martillo demoledor 12 kg', 6500.00, 41, 'Herramientas eléctricas', 1),
(66, 'Cizalla eléctrica', 1800.00, 5, 'Herramientas eléctricas', 1),
(67, 'Grapadora eléctrica', 750.00, 12, 'Herramientas eléctricas', 1),
(68, 'Clavadora neumática', 1200.00, 9, 'Herramientas eléctricas', 1),
(69, 'Kit de brocas para concreto', 350.00, 20, 'Herramientas eléctricas', 1),
(70, 'Disco de corte para metal 4-1/2\"', 45.00, 50, 'Herramientas eléctricas', 1),
(71, 'Tornillo para madera #8 x 1-1/2\" (100 pzs)', 25.00, 40, 'Ferretería general', 1),
(72, 'Tornillo para metal #10 x 1\" (100 pzs)', 30.00, 35, 'Ferretería general', 1),
(73, 'Clavo para concreto 2-1/2\" (kg)', 30.00, 60, 'Ferretería general', 1),
(74, 'Clavo cabeza chata 2\" (kg)', 25.00, 70, 'Ferretería general', 1),
(75, 'Ancla plástica 1/4\" x 2\" (10 pzs)', 18.00, 90, 'Ferretería general', 1),
(76, 'Ancla tornillo 3/8\" x 3\" (5 pzs)', 35.00, 50, 'Ferretería general', 1),
(77, 'Bisagra de bronce 3\" (par)', 45.00, 70, 'Ferretería general', 1),
(78, 'Bisagra invisible 35mm', 55.00, 40, 'Ferretería general', 1),
(79, 'Candado de latón 40mm', 85.00, 45, 'Ferretería general', 1),
(80, 'Candado de acero 50mm', 120.00, 30, 'Ferretería general', 1),
(81, 'Cadena con candado 1m', 120.00, 30, 'Ferretería general', 1),
(82, 'Gancho \"S\" grande (5 pzs)', 22.00, 110, 'Ferretería general', 1),
(83, 'Argolla de acero 2\"', 15.00, 80, 'Ferretería general', 1),
(84, 'Grillete 1/4\"', 18.00, 70, 'Ferretería general', 1),
(85, 'Polea pequeña', 45.00, 40, 'Ferretería general', 1),
(86, 'Rodaja giratoria 2\"', 55.00, 35, 'Ferretería general', 1),
(87, 'Caja de registro plástica', 35.00, 60, 'Ferretería general', 1),
(88, 'Caja de registro metálica', 65.00, 40, 'Ferretería general', 1),
(89, 'Tirafondo 1/4\" x 2\" (25 pzs)', 45.00, 50, 'Ferretería general', 1),
(90, 'Remache pop 3/16\" x 1/4\" (100 pzs)', 55.00, 38, 'Ferretería general', 1),
(91, 'Perno hexagonal 3/8\" x 2\"', 8.00, 150, 'Ferretería general', 1),
(92, 'Tuerca 3/8\"', 2.00, 300, 'Ferretería general', 1),
(93, 'Arandela plana 3/8\"', 1.50, 400, 'Ferretería general', 1),
(94, 'Soldadura estaño 60/40 1mm x 50g', 45.00, 60, 'Ferretería general', 1),
(95, 'Cinta aislante negra', 25.00, 80, 'Ferretería general', 1),
(96, 'Cinta teflón 1/2\"', 15.00, 120, 'Ferretería general', 1),
(97, 'Cinta doble contacto 1\"', 35.00, 70, 'Ferretería general', 1),
(98, 'Silicona transparente 85g', 45.00, 50, 'Ferretería general', 1),
(99, 'Silicona negra 85g', 45.00, 50, 'Ferretería general', 1),
(100, 'Pegamento epoxy 50g', 55.00, 40, 'Ferretería general', 1),
(101, 'Pegamento para PVC 250ml', 35.00, 60, 'Ferretería general', 1),
(102, 'Pegamento instantáneo 20g', 25.00, 90, 'Ferretería general', 1),
(103, 'Cable de acero 1/8\" x 3m', 65.00, 40, 'Ferretería general', 1),
(104, 'Malla ciclónica 1m x 2m', 180.00, 20, 'Ferretería general', 1),
(105, 'Malla sombra 70% 3m x 5m', 350.00, 15, 'Ferretería general', 1),
(106, 'Lámina galvanizada cal 26 1m x 2m', 280.00, 12, 'Ferretería general', 1),
(107, 'Ángulo de aluminio 1\" x 1m', 65.00, 30, 'Ferretería general', 1),
(108, 'Tubo cuadrado 1\" x 1m', 85.00, 25, 'Ferretería general', 1),
(109, 'Varilla roscada 3/8\" x 1m', 45.00, 40, 'Ferretería general', 1),
(110, 'Alambre recocido #18 kg', 35.00, 50, 'Ferretería general', 1),
(111, 'Alambre galvanizado #12 kg', 45.00, 38, 'Ferretería general', 1),
(112, 'Cadena de acero por metro', 55.00, 35, 'Ferretería general', 1),
(113, 'Grapas para cable (100 pzs)', 25.00, 80, 'Ferretería general', 1),
(114, 'Tornillo autorroscante #8 x 1\" (100 pzs)', 30.00, 60, 'Ferretería general', 1),
(115, 'Clavo cabeza de hongo 1-1/2\" (kg)', 28.00, 70, 'Ferretería general', 1),
(116, 'Remache ciego 1/8\" x 1/4\" (100 pzs)', 60.00, 35, 'Ferretería general', 1),
(117, 'Pasador split 1/8\" (100 pzs)', 25.00, 90, 'Ferretería general', 1),
(118, 'Resorte de extensión 2\"', 8.00, 120, 'Ferretería general', 1),
(119, 'Gancho para cortina', 5.00, 200, 'Ferretería general', 1),
(120, 'Soporte para TV 32\"-55\"', 350.00, 15, 'Ferretería general', 1),
(121, 'Cable duplex calibre 14 x 100m', 450.00, 20, 'Material eléctrico', 1),
(122, 'Cable THHN calibre 12 x 100m', 550.00, 14, 'Material eléctrico', 1),
(123, 'Interruptor sencillo', 25.00, 150, 'Material eléctrico', 1),
(124, 'Apagador doble', 45.00, 100, 'Material eléctrico', 1),
(125, 'Toma de corriente 15A', 35.00, 120, 'Material eléctrico', 1),
(126, 'Toma de corriente 20A', 45.00, 80, 'Material eléctrico', 1),
(127, 'Foco LED 9W (60W equivalente)', 35.00, 200, 'Material eléctrico', 1),
(128, 'Foco LED 15W (100W equivalente)', 55.00, 150, 'Material eléctrico', 1),
(129, 'Tubo conduit 3/4\" x 3m', 55.00, 50, 'Material eléctrico', 1),
(130, 'Caja octagonal metálica', 28.00, 80, 'Material eléctrico', 1),
(131, 'Caja rectangular plástica', 22.00, 90, 'Material eléctrico', 1),
(132, 'Breaker 15A 1 polo', 85.00, 40, 'Material eléctrico', 1),
(133, 'Breaker 20A 2 polos', 120.00, 30, 'Material eléctrico', 1),
(134, 'Portalámparas E26', 15.00, 120, 'Material eléctrico', 1),
(135, 'Portalámparas E27', 15.00, 120, 'Material eléctrico', 1),
(136, 'Base para foco de rosca', 12.00, 150, 'Material eléctrico', 1),
(137, 'Conector para cable 2-1/2\"', 18.00, 100, 'Material eléctrico', 1),
(138, 'Curva para conduit 3/4\"', 22.00, 80, 'Material eléctrico', 1),
(139, 'Canaleta 1\" x 2m', 35.00, 60, 'Material eléctrico', 1),
(140, 'Cinta aislante roja', 25.00, 90, 'Material eléctrico', 1),
(141, 'Cinta aislante verde', 25.00, 90, 'Material eléctrico', 1),
(142, 'Caja de paso 4x4', 45.00, 50, 'Material eléctrico', 1),
(143, 'Base para contacto polarizado', 18.00, 120, 'Material eléctrico', 1),
(144, 'Fusible 10A', 12.00, 150, 'Material eléctrico', 1),
(145, 'Fusible 15A', 12.00, 150, 'Material eléctrico', 1),
(146, 'Tapa para caja rectangular', 10.00, 200, 'Material eléctrico', 1),
(147, 'Tapa para caja octagonal', 10.00, 200, 'Material eléctrico', 1),
(148, 'Clavija polarizada', 25.00, 100, 'Material eléctrico', 1),
(149, 'Conector tipo \"T\" para cable', 15.00, 120, 'Material eléctrico', 1),
(150, 'Terminal de ojo 10-12 AWG (10 pzs)', 18.00, 150, 'Material eléctrico', 1),
(151, 'Pintura vinílica blanca 4L', 280.00, 25, 'Pinturas', 1),
(152, 'Pintura esmalte blanco 1L', 95.00, 40, 'Pinturas', 1),
(153, 'Pintura en aerosol negro 400ml', 65.00, 60, 'Pinturas', 1),
(154, 'Pintura en aerosol plateado 400ml', 75.00, 50, 'Pinturas', 1),
(155, 'Brocha de cerda 3\"', 65.00, 60, 'Pinturas', 1),
(156, 'Brocha sintética 2\"', 45.00, 80, 'Pinturas', 1),
(157, 'Rodillo de lana 9\"', 45.00, 50, 'Pinturas', 1),
(158, 'Rodillo de espuma 4\"', 35.00, 70, 'Pinturas', 1),
(159, 'Charola para rodillo', 35.00, 40, 'Pinturas', 1),
(160, 'Thinner 1L', 55.00, 35, 'Pinturas', 1),
(161, 'Aguarrás 1L', 50.00, 40, 'Pinturas', 1),
(162, 'Masilla para madera 250g', 45.00, 30, 'Pinturas', 1),
(163, 'Masilla epóxica 250g', 65.00, 25, 'Pinturas', 1),
(164, 'Lija al agua #220 (5 hojas)', 25.00, 80, 'Pinturas', 1),
(165, 'Lija para madera #80 (10 hojas)', 35.00, 60, 'Pinturas', 1),
(166, 'Removedor de pintura 1L', 75.00, 30, 'Pinturas', 1),
(167, 'Sellador acrílico transparente 1L', 85.00, 25, 'Pinturas', 1),
(168, 'Sellador para madera 1L', 95.00, 20, 'Pinturas', 1),
(169, 'Barniz brillante 1L', 110.00, 18, 'Pinturas', 1),
(170, 'Barniz mate 1L', 110.00, 18, 'Pinturas', 1),
(171, 'Tubo PVC 1/2\" x 3m', 45.00, 40, 'Fontanería', 1),
(172, 'Tubo PVC 3/4\" x 3m', 55.00, 35, 'Fontanería', 1),
(173, 'Cople para PVC 1/2\"', 8.00, 150, 'Fontanería', 1),
(174, 'Cople para PVC 3/4\"', 10.00, 120, 'Fontanería', 1),
(175, 'Válvula de compuerta 1/2\"', 75.00, 30, 'Fontanería', 1),
(176, 'Válvula de esfera 1/2\"', 85.00, 25, 'Fontanería', 1),
(177, 'Llave para lavabo', 65.00, 50, 'Fontanería', 1),
(178, 'Llave para fregadero', 75.00, 40, 'Fontanería', 1),
(179, 'Flexible para tanque 1/2\" x 40cm', 55.00, 60, 'Fontanería', 1),
(180, 'Bote de cemento PVC 250g', 35.00, 45, 'Fontanería', 1),
(181, 'Teflón en rollo', 15.00, 80, 'Fontanería', 1),
(182, 'Junta tórica para llave 1/2\"', 5.00, 200, 'Fontanería', 1),
(183, 'Bomba manual para agua', 120.00, 20, 'Fontanería', 1),
(184, 'Sifón para lavabo', 45.00, 50, 'Fontanería', 1),
(185, 'Trampa \"P\" 1-1/4\"', 55.00, 40, 'Fontanería', 1),
(186, 'Reductor PVC 3/4\" a 1/2\"', 12.00, 100, 'Fontanería', 1),
(187, 'Tee PVC 1/2\"', 10.00, 120, 'Fontanería', 1),
(188, 'Codo 90° PVC 1/2\"', 8.00, 150, 'Fontanería', 1),
(189, 'Tapón PVC 1/2\"', 5.00, 200, 'Fontanería', 1),
(190, 'Manguera para jardín 1/2\" x 15m', 180.00, 25, 'Fontanería', 1),
(191, 'Guantes de carnaza (par)', 65.00, 70, 'Seguridad', 1),
(192, 'Guantes anticorte (par)', 95.00, 50, 'Seguridad', 1),
(193, 'Lentes de seguridad', 45.00, 90, 'Seguridad', 1),
(194, 'Cubrebocas KN95 (3 pzs)', 35.00, 120, 'Seguridad', 1),
(195, 'Casco de seguridad', 95.00, 40, 'Seguridad', 1),
(196, 'Chaleco reflectante', 85.00, 35, 'Seguridad', 1),
(197, 'Extintor 2.5 kg ABC', 450.00, 15, 'Seguridad', 1),
(198, 'Botiquín básico', 280.00, 20, 'Seguridad', 1),
(199, 'Tapa oídos desechables (10 pzs)', 25.00, 100, 'Seguridad', 1),
(200, 'Arnés de seguridad', 350.00, 12, 'Seguridad', 1),
(201, 'Mascarilla para soldar', 120.00, 25, 'Seguridad', 1),
(202, 'Zapato de seguridad', 420.00, 18, 'Seguridad', 1),
(203, 'Señal de \"Peligro\"', 35.00, 60, 'Seguridad', 1),
(204, 'Señal de \"Salida de emergencia\"', 35.00, 60, 'Seguridad', 1),
(205, 'Kit de primeros auxilios', 150.00, 30, 'Seguridad', 1),
(206, 'Manguera 1/2\" x 15m', 180.00, 25, 'Jardinería', 1),
(207, 'Regadera plástica 5L', 75.00, 40, 'Jardinería', 1),
(208, 'Tijeras para podar 8\"', 120.00, 30, 'Jardinería', 1),
(209, 'Pala de jardín', 95.00, 35, 'Jardinería', 1),
(210, 'Rastrillo metálico', 85.00, 30, 'Jardinería', 1),
(211, 'Carretilla 4 pies', 650.00, 10, 'Jardinería', 1),
(212, 'Bomba para fumigar 2L', 150.00, 20, 'Jardinería', 1),
(213, 'Cortasetos 24\"', 220.00, 15, 'Jardinería', 1),
(214, 'Manguera soaker 1/2\" x 10m', 150.00, 25, 'Jardinería', 1),
(221, 'Aspersor estatico', 123.00, 32, 'Jardinería', 1),
(222, 'Martillo', 32.00, 41, 'Herramientas manuales', 1),
(223, 'martilo flexible', 320.00, 45, 'Herramientas manuales', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre`, `descripcion`) VALUES
(1, 'admin', 'Administrador del sistema'),
(2, 'cajero', 'Usuario que realiza ventas'),
(3, 'supervisor', 'Usuario con permisos de supervisión'),
(4, 'Gerente', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `contrasena`, `rol_id`) VALUES
(1, 'Azael', 'azael@gmail.com', '$2a$12$2QttweM8ao1uRfX0ZYrlre8ajdBvggcT02T9HOXiZzcIdROvuG0/u', 1),
(2, 'Jose', 'jose@gmail.com', '$2a$12$vB8xPbeFirWlhF992xNdmu3GqRpCXNtR5Hsz/pQJz.VImAkAdfk/O', 2),
(3, 'Juan', 'juan@gmail.com', '$2a$12$U.pnqY2VmSaHsYrRGIy4du43oX6KxYXDYhjbT2sKMgsIJXQzIj1OS', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `id_cliente`, `fecha`) VALUES
(10, 3, '2025-06-02 05:53:49'),
(11, 3, '2025-06-02 05:54:19'),
(12, 4, '2025-06-02 05:58:29'),
(13, 5, '2025-06-02 06:01:02'),
(14, 6, '2025-06-02 06:10:21'),
(15, 7, '2025-06-02 06:16:06'),
(16, 8, '2025-06-02 06:29:56'),
(17, 9, '2025-06-02 09:09:06'),
(18, 10, '2025-06-04 09:52:50'),
(19, 2, '2025-06-09 04:28:32'),
(20, 2, '2025-06-09 04:31:06'),
(21, 2, '2025-06-09 04:32:37'),
(22, 2, '2025-06-09 04:34:45'),
(23, 2, '2025-06-09 04:46:30'),
(24, 12, '2025-06-09 05:11:45'),
(25, 12, '2025-06-09 05:16:53');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD KEY `rol_id` (`rol_id`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=224;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id`),
  ADD CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
