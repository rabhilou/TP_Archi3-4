-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 30 Mars 2017 à 20:56
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `zoo`
--

-- --------------------------------------------------------

--
-- Structure de la table `animal`
--

CREATE TABLE `animal` (
  `id` varchar(255) NOT NULL,
  `name` varchar(20) NOT NULL,
  `species` varchar(255) NOT NULL,
  `id_cage` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal`
--

INSERT INTO `animal` (`id`, `name`, `species`, `id_cage`) VALUES
('73f7b238-a546-4aed-84cc-8049628fe858', 'Mdrrr', 'Chipmunk', 1),
('6417c6f3-546f-4e24-b9bc-cb5c6630948d', 'Mdrrr', 'Chipmunk', 1),
('f057cde6-b326-4ebc-90a5-948b30930033', 'Mdrrr', 'Piranha', 2),
('1c6cef6b-9e47-4b89-923f-35d79acb6613', 'Mdrrr', 'Piranha', 2),
('06bb50c1-bb7c-48e8-902e-2446de425b18', 'Mdrrr', 'Piranha', 2),
('a93d6de5-a52b-4aca-bf3d-4f45cccc6eee', 'Mdrrr', 'Piranha', 2),
('c1baaecc-4251-4cdb-9f53-0073e512e47a', 'Mdrrr', 'Arapaima-gigas', 2),
('2e0cf121-048c-4ba9-b417-99352ff03610', 'Mdrrr', 'Arapaima-gigas', 3);

-- --------------------------------------------------------

--
-- Structure de la table `cage`
--

CREATE TABLE `cage` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `capacity` int(11) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `id_center` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Contenu de la table `cage`
--

INSERT INTO `cage` (`id`, `name`, `capacity`, `longitude`, `latitude`, `id_center`) VALUES
(1, 'usa', 25, 1.2157357, 49.305, 1),
(2, 'amazon', 15, 1.2154067, 49.305142, 1),
(3, 'Rouen', 15, 1.103333, 49.443889, 1),
(4, 'Paris', 15, 2.351828, 48.856578, 1),
(5, 'Somalie', 50, 48.85, 2.333333, 1),
(6, 'Bihorel', 10, 1.116944, 49.455278, 1),
(7, 'Londres', 20, -0.07857, 51.504872, 1),
(8, 'Canada', 20, -80.38333, 43.2, 1),
(9, 'Porto-Vecchio', 20, 9.2627, 41.5895241, 1),
(10, 'Montreux', 20, 6.9113575, 46.4307133, 1),
(11, 'Villers-Bocage', 20, 2.3261, 50.0218, 1);

-- --------------------------------------------------------

--
-- Structure de la table `center`
--

CREATE TABLE `center` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Contenu de la table `center`
--

INSERT INTO `center` (`id`, `name`, `longitude`, `latitude`) VALUES
(1, 'Biotropica', 1.2170602, 49.30494);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `animal`
--
ALTER TABLE `animal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cage_animal` (`id_cage`);

--
-- Index pour la table `cage`
--
ALTER TABLE `cage`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cage_center` (`id_center`);

--
-- Index pour la table `center`
--
ALTER TABLE `center`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `cage`
--
ALTER TABLE `cage`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT pour la table `center`
--
ALTER TABLE `center`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
