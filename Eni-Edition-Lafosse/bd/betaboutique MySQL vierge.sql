-- phpMyAdmin SQL Dump
-- version 2.9.1.1
-- http://www.phpmyadmin.net
-- 
-- Serveur: localhost
-- Généré le : Jeudi 15 Janvier 2009 à 10:00
-- Version du serveur: 5.0.27
-- Version de PHP: 5.2.0
-- 
-- Base de données: `betaboutiquejavaee`
-- 

-- --------------------------------------------------------

-- 
-- Structure de la table `administrateur`
-- 

CREATE TABLE `administrateur` (
  `id_administrateur` int(11) NOT NULL auto_increment,
  `nomadministrateur` varchar(50) character set latin1 NOT NULL,
  `prenomadministrateur` varchar(50) character set latin1 NOT NULL,
  `mailadministrateur` varchar(255) character set latin1 NOT NULL,
  `identifiantadministrateur` varchar(50) character set latin1 NOT NULL default '',
  `motdepasseadministrateur` varchar(50) character set latin1 NOT NULL default '',
  PRIMARY KEY  (`id_administrateur`),
  KEY `identifiantadministrateur` (`identifiantadministrateur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `article`
-- 

CREATE TABLE `article` (
  `id_article` int(10) unsigned NOT NULL auto_increment,
  `nomarticle` varchar(255) character set latin1 NOT NULL default '',
  `descriptionarticle` text character set latin1 NOT NULL,
  `prixarticle` double NOT NULL default '0',
  `reductionarticle` int(2) NOT NULL,
  `datearticle` int(11) NOT NULL default '0',
  `photoarticle` varchar(255) character set latin1 NOT NULL default '',
  `vignettearticle` varchar(255) character set latin1 NOT NULL default '',
  `etatarticle` int(1) NOT NULL default '0',
  `id_categorie` int(11) NOT NULL default '0',
  `poidsarticle` int(3) default NULL,
  `stockarticle` int(5) default NULL,
  PRIMARY KEY  (`id_article`),
  KEY `id_categorie` (`id_categorie`),
  KEY `nomarticle` (`nomarticle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=55 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `articlecommande`
-- 

CREATE TABLE `articlecommande` (
  `id_articlecommande` int(10) unsigned NOT NULL auto_increment,
  `referencearticle` int(10) NOT NULL,
  `nomarticle` varchar(255) character set latin1 NOT NULL default '',
  `descriptionarticle` text character set latin1 NOT NULL,
  `prixarticle` double NOT NULL default '0',
  `reductionarticle` int(2) NOT NULL,
  `datearticle` int(11) NOT NULL default '0',
  `photoarticle` varchar(255) character set latin1 NOT NULL default '',
  `vignettearticle` varchar(255) character set latin1 NOT NULL default '',
  `etatarticle` int(1) NOT NULL default '0',
  `id_categorie` int(11) NOT NULL default '0',
  `poidsarticle` int(3) default NULL,
  `stockarticle` int(5) default NULL,
  PRIMARY KEY  (`id_articlecommande`),
  KEY `id_categorie` (`id_categorie`),
  KEY `nomarticle` (`nomarticle`),
  KEY `referencearticle` (`referencearticle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `categorie`
-- 

CREATE TABLE `categorie` (
  `id_categorie` int(10) unsigned NOT NULL auto_increment,
  `nomcategorie` varchar(255) character set latin1 NOT NULL default '',
  PRIMARY KEY  (`id_categorie`),
  KEY `nomcategorie` (`nomcategorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `client`
-- 

CREATE TABLE `client` (
  `id_client` int(10) unsigned NOT NULL auto_increment,
  `identifiantclient` varchar(50) character set latin1 NOT NULL default '',
  `motdepasseclient` varchar(50) character set latin1 NOT NULL default '',
  `nomclient` varchar(50) character set latin1 NOT NULL default '',
  `prenomclient` varchar(50) character set latin1 NOT NULL default '',
  `emailclient` varchar(255) character set latin1 NOT NULL default '',
  `telephoneclient` varchar(20) character set latin1 NOT NULL,
  `adresseclient` varchar(255) character set latin1 NOT NULL default '',
  `villeclient` varchar(255) character set latin1 NOT NULL default '',
  `codepostalclient` varchar(5) character set latin1 NOT NULL,
  `paysclient` varchar(50) character set latin1 NOT NULL default '',
  PRIMARY KEY  (`id_client`),
  KEY `identifiantclient` (`identifiantclient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `clientcommande`
-- 

CREATE TABLE `clientcommande` (
  `id_clientcommande` int(10) unsigned NOT NULL auto_increment,
  `referenceclient` int(10) NOT NULL,
  `identifiantclient` varchar(50) character set latin1 NOT NULL default '',
  `motdepasseclient` varchar(50) character set latin1 NOT NULL default '',
  `nomclient` varchar(50) character set latin1 NOT NULL default '',
  `prenomclient` varchar(50) character set latin1 NOT NULL default '',
  `emailclient` varchar(255) character set latin1 NOT NULL default '',
  `telephoneclient` varchar(20) character set latin1 NOT NULL,
  `adresseclient` varchar(255) character set latin1 NOT NULL default '',
  `villeclient` varchar(255) character set latin1 NOT NULL default '',
  `codepostalclient` varchar(5) character set latin1 NOT NULL,
  `paysclient` varchar(50) character set latin1 NOT NULL default '',
  PRIMARY KEY  (`id_clientcommande`),
  KEY `identifiantclient` (`identifiantclient`),
  KEY `referenceclient` (`referenceclient`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `commande`
-- 

CREATE TABLE `commande` (
  `id_commande` int(10) NOT NULL auto_increment,
  `id_client` int(11) NOT NULL default '0',
  `datecreationcommande` int(11) NOT NULL default '0',
  `datemodificationcommande` int(8) NOT NULL default '0',
  `totalcommande` double NOT NULL default '0',
  `totalpoids` double NOT NULL default '0',
  `etatcommande` int(1) NOT NULL default '0',
  PRIMARY KEY  (`id_commande`),
  KEY `id_client` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `lignecommande`
-- 

CREATE TABLE `lignecommande` (
  `id_lignecommande` int(10) NOT NULL auto_increment,
  `id_article` int(11) NOT NULL default '0',
  `id_commande` int(11) NOT NULL default '0',
  `prixunitaire` double NOT NULL default '0',
  `quantitearticle` int(2) NOT NULL,
  PRIMARY KEY  (`id_lignecommande`),
  KEY `id_commande` (`id_commande`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `notearticle`
-- 

CREATE TABLE `notearticle` (
  `id_article` int(11) NOT NULL default '0',
  `id_client` int(11) NOT NULL default '0',
  `note` int(2) NOT NULL default '0',
  PRIMARY KEY  (`id_article`,`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- 
-- Structure de la table `raccourci`
-- 

CREATE TABLE `raccourci` (
  `id_raccourci` int(10) NOT NULL auto_increment,
  `id_client` int(10) default NULL,
  `nomraccourci` varchar(100) character set latin1 default NULL,
  `urlraccourci` varchar(255) character set latin1 default NULL,
  PRIMARY KEY  (`id_raccourci`),
  KEY `id_client` (`id_client`),
  KEY `nomraccourci` (`nomraccourci`),
  KEY `urlraccourci` (`urlraccourci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=63 ;
