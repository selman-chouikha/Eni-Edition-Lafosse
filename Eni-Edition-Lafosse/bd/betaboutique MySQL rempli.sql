-- phpMyAdmin SQL Dump
-- version 2.9.1.1
-- http://www.phpmyadmin.net
-- 
-- Serveur: localhost
-- G�n�r� le : Jeudi 15 Janvier 2009 � 10:00
-- Version du serveur: 5.0.27
-- Version de PHP: 5.2.0
-- 
-- Base de donn�es: `betaboutiquejavaee`
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

-- 
-- Contenu de la table `administrateur`
-- 

INSERT INTO `administrateur` (`id_administrateur`, `nomadministrateur`, `prenomadministrateur`, `mailadministrateur`, `identifiantadministrateur`, `motdepasseadministrateur`) VALUES 
(1, 'Lafosse', 'J�rome', 'jerome.lafosse@gmail.com', 'admin', 'YAsPVQ24dI8nVg4O83yL6c4Ai8M=');

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

-- 
-- Contenu de la table `article`
-- 

INSERT INTO `article` (`id_article`, `nomarticle`, `descriptionarticle`, `prixarticle`, `reductionarticle`, `datearticle`, `photoarticle`, `vignettearticle`, `etatarticle`, `id_categorie`, `poidsarticle`, `stockarticle`) VALUES 
(1, 'les tontons flingueurs', '# Acteurs : Lino Ventura, Bernard Blier,\r\nFrancis Blanche, Jean Lefebvre, Claude Rich\r\n# R�alisateurs : Georges Lautner\r\n# Format :\r\nPAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe.\r\nPlus d''informations sur les formats DVD.).\r\n# Studio: G.C.T.H.V.\r\n# Date de sortie du DVD : 10\r\nnovembre 2005', 19.99, 0, 20071005, 'tontonsflingueursgrande.jpg', 'tontonsflingueurs.jpg', 0, 3, 100, 200),
(2, 'Le Choc', '# Acteurs : Alain Delon, Catherine Deneuve, Philippe\r\nL�otard, Etienne Chicot, Francois Perrot\r\n# R�alisateurs : Robin Davis\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 1\r\n# Studio: Universal Music\r\n#\r\nDate de sortie du DVD : 16 mai 2005', 18, 15, 20071004, 'lechocgrande.jpg', 'lechoc.jpg', 1, 2, 100, 200),
(4, 'Mari�s deux enfants int�grale saison 11', '# Acteurs : Ed\r\nO''Neill, Katey Sagal\r\n# Format : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas\r\n�tre visualis� en dehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Nombre de\r\ndisques: 3\r\n# Studio: G.C.T.H.V.\r\n# Date de sortie du DVD : 8 ao�t 2007\r\n# Fonctions DVD\r\n:\r\n\r\n# ASIN: B000RO9ZAK', 55, 10, 20070928, 'mariesdeuxenfantsgrande.jpg', 'mariesdeuxenfants.jpg', 1, 6, 100, 200),
(5, 'Columbo: Saison 6 & 7 - Coffret 4 DVD', '# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: ___\r\n# Date de sortie du DVD : 12 avril\r\n2007\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000U57XB4', 29.99, 0, 20071209, 'columbogrande.jpg', 'columbo.jpg', 1, 3, 100, 200),
(6, 'Prison Break, l''int�grale saison 1 - Coffret 6 DVD', '# Acteurs :\r\nWentworth Miller, Dominic Purcell, Peter Stormare, Robin Tunney\r\n# Format : PAL\r\n# R�gion:\r\nR�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: _\r\n# Date de sortie du DVD : 1 janvier\r\n2007\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000PTYTFS', 89, 0, 20070808, 'prisonbreakgrande.jpg', 'prisonbreak.jpg', 1, 6, 100, 200),
(7, 'Starsky & Hutch : L''Int�grale Saison 2', '# Acteurs : Paul\r\nMichael Glaser, David Soul, Bernie Hamilton, Antonio Fargas, Richard Lynch\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 5\r\n# Studio: G.C.T.H.V.\r\n# Date\r\nde sortie du DVD : 5 octobre 2004\r\n# Moyenne des commentaires client : bas� sur 1 commentaire.\r\n(�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n# ASIN: B0002Z7SAI\r\n\r\n# Acteurs : Paul\r\nMichael Glaser, David Soul, Bernie Hamilton, Antonio Fargas, Richard Lynch\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 5\r\n# Studio: G.C.T.H.V.\r\n# Date\r\nde sortie du DVD : 5 octobre 2004\r\n# Moyenne des commentaires client : bas� sur 1 commentaire.\r\n(�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n# ASIN: B0002Z7SAI', 62, 0, 20070908, 'starskyethutchgrande.jpg', 'starskyethutch.jpg', 1, 6, 100, 200),
(8, '24 Heures chrono', 'Jack Bauer est un agent de la Cellule Anti-Terroriste de Los Angeles. A chaque saison de la s�rie, il se retrouve confront� � une mission p�rilleuse qu''il doit remplir en l''espace de 24 heures. Ainsi, chaque saison correspond � 24 heures dans la vie de Bauer (une saison comportant 24 �pisodes et chaque �pisode correspondant th�oriquement � une heure en temps r�el).', 49.9, 0, 20071010, '24heuresgrande.jpg', '24heures.jpg', 1, 6, 100, 200),
(9, 'La Balance', '# Acteurs : Nathalie Baye, Philippe L�otard, Richard\r\nBerry, Christophe Malavoy, Florent Pagny\r\n# R�alisateurs : Bob Swaim\r\n# Format : Anamorphic,\r\nCouleur, Plein �cran, PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en\r\ndehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Rapport de forme : 1.66:1\r\n#\r\nStudio: TF1 Vid�o\r\n# Date de sortie du DVD : 3 octobre 2002\r\n# Moyenne des commentaires client :\r\nbas� sur 2 commentaires. (�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n * � 2 courtsm\r\nZtrages de Bob Swaim\r\n � L''interview du r�alisateur\r\n � 3 sc�nes coment�es\r\n\r\n� Lien internet\r\n � Son 5.1 Arkamys\r\n * Pr�sentation : Jewel Case\r\n\r\n Format\r\nimage :\r\n Widescreen anamorphic - 1.66:1\r\n Pan and scan\r\n\r\n Zone et formats\r\nson :\r\n Zone : Region 2\r\n Couches : Deux couches\r\n Langues et formats sonores :\r\nFran�ais (PCM Mono), Fran�ais (Dolby Digital 5.1)\r\n\r\n# ASIN: B00005MOP7', 22.98, 0, 20071005, 'labalancegrande.jpg', 'labalance.jpg', 1, 2, 100, 200),
(10, 'Police Python 357', '# Acteurs : Yves Montand, Simone Signoret,\r\nFran�ois Perier, Stefania Sandrelli, Mathieu Carri�re\r\n# R�alisateurs : Alain Corneau\r\n# Format\r\n: PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de\r\nl''Europe. Plus d''informations sur les formats DVD.).\r\n# Studio: Studio Canal\r\n# Date de sortie\r\ndu DVD : 19 f�vrier 2007\r\n# Moyenne des commentaires client : bas� sur 1 commentaire. (�crivez un\r\ncommentaire.)\r\n# Fonctions DVD :\r\n\r\n * � Les filmographies\r\n � La bande-annonce\r\n\r\n� Les affiches et photos\r\n � Retour sur Police Python 357 avec Alain Corneau et Fran\Zois\r\nGuerif\r\n * Pr�sentation : Keep Case\r\n\r\n Format image :\r\n Widescreen anamorphic\r\n- 1.66:1\r\n Pan and scan\r\n\r\n Zone et formats son :\r\n Zone : Region 2\r\n\r\nLangues et formats sonores : Fran\Zais (Dolby Digital 2.0 Mono)\r\n\r\n# ASIN: B0001CVJ48', 25.98, 0, 20071002, 'policepython357grande.jpg', 'policepython357.jpg', 1, 2, 100, 200),
(11, 'Le choix des armes', '# Acteurs : Yves Montand, G�rard Depardieu,\r\nCatherine Deneuve, Michel Galabru, G�rard Lanvin\r\n# R�alisateurs : Alain Corneau\r\n# Format :\r\nPAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe.\r\nPlus d''informations sur les formats DVD.).\r\n# Studio: Studio Canal\r\n# Date de sortie du DVD :\r\n19 f�vrier 2007\r\n# Fonctions DVD :\r\n\r\n * � Les filmographies\r\n � La bandeannonce\r\nr\n � Les affiches et photos\r\n � Retour sur le choix des armes avec Alain\r\nCorneau, Richard Anconina et Fran\Zois Guerif\r\n * Pr�sentation : Keep Case\r\n\r\n Format image :\r\n Widescreen anamorphic - 2.35:1\r\n\r\n Zone et formats son :\r\n Zone : Region 2\r\n Langues et formats sonores : Fran\Zais (Dolby Digital 2.0 Surround)\r\n\r\n# ASIN:\r\nB0001BMMMC', 24, 0, 20070608, 'lechoixdesarmesgrande.jpg', 'lechoixdesarmes.jpg', 1, 2, 100, 200),
(12, 'Le Cercle Rouge (The Red Circle)', '# Acteurs : Yves Montand,\r\nAlain Delon, Bourvil, Gian Maria Volont�, Fran�ois Perier\r\n# R�alisateurs : Jean-Pierre\r\nMelville\r\n# Format : Anamorphic, Edition sp�ciale, Sous-titr�, NTSC, Import\r\n# Langue :\r\nFran�ais\r\n# R�gion: R�gion 1 (USA et Canada). Ce DVD ne pourra probablement pas �tre visualis� en\r\nEurope. Plus d''informations sur les formats DVD.\r\n# Rapport de forme : 1.85:1\r\n# Studio:\r\nCriterion\r\n# Date de sortie du DVD : 28 octobre 2003\r\n# Moyenne des commentaires client : bas�\r\nsur 1 commentaire. (�crivez un commentaire.)\r\nSous-titres : Anglais\r\n\r\n# ASIN: B0000BUZKP', 20.99, 0, 20070405, 'lecerclerougegrande.jpg', 'lecerclerouge.jpg', 1, 2, 100, 199),
(13, 'Adieu l''Ami', '# Acteurs : Alain Delon, Catherine Sola, Michel\r\nBarcet, Lisette Lebon, Bernard Fresson\r\n# R�alisateurs : Jean Herman\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 1\r\n# Studio: Universal Music\r\n#\r\nDate de sortie du DVD : 16 mai 2005\r\n# Moyenne des commentaires client : bas� sur 1 commentaire.\r\n(�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n# ASIN: B0007XT50Q', 25, 0, 20071002, 'adieuamigrande.jpg', 'adieuami.jpg', 1, 2, 100, 199),
(14, 'La Menace', '# Acteurs : Yves Montand, Marie Dubois, Carole\r\nLaure, Jean-Fran�ois Balmer, Marc Eyraud\r\n# R�alisateurs : Alain Corneau\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: G.C.T.H.V.\r\n# Date de sortie du DVD : 5 ao�t\r\n2003\r\n# Moyenne des commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n#\r\nFonctions DVD :\r\n\r\n � L''interview de Carole Laure\r\n � Les bandes-annonces\r\n\r\n� Les filmographies\r\n * Pr�sentation : Snap Case\r\n\r\n Format image :\r\n\r\nWidescreen anamorphic - 1.66:1\r\n\r\n Zone et formats son :\r\n Zone : Region 2\r\n\r\nLangues et formats sonores : Fran�ais (Dolby Digital 2.0 Stereo)\r\n\r\n# ASIN: B0000A302N', 25, 0, 20061212, 'lamenacegrande.jpg', 'lamenace.jpg', 1, 2, 100, 200),
(15, 'Garde � vue', '# Acteurs : Lino Ventura, Michel Serrault, Guy\r\nMarchand, Romy Schneider, Michel Such\r\n# R�alisateurs : Claude Miller\r\n# Format : Couleur,\r\nPAL\r\n# Langue : Fran�ais\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis�\r\nen dehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Rapport de forme :\r\n1.66:1\r\n# Nombre de disques: 1\r\n# Studio: TF1 Vid�o\r\n# Date de sortie du DVD : 20 septembre\r\n2000\r\n# Dur�e : 85 minutes\r\n# Moyenne des commentaires client : bas� sur 5 commentaires.\r\n(�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n# ASIN: B00004YRL8', 12, 0, 20070202, 'gardeavuegrande.jpg', 'gardeavue.jpg', 1, 2, 100, 200),
(16, 'S�rie noire', '# Acteurs : Patrick Dewaere, Myriam Boyer, Bernard\r\nBlier, Marie Trintignant, Andreas Katsulas\r\n# R�alisateurs : Alain Corneau\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: Studio Canal\r\n# Date de sortie du DVD : 19\r\nf�vrier 2007\r\n# Moyenne des commentaires client : bas� sur 3 commentaires. (�crivez un\r\ncommentaire.)\r\n# Fonctions DVD :\r\n\r\n * � Les interviews de Alain Corneau et de Marie\r\nTrintignant\r\n � La pr�sentation de Patrick Raynal\r\n � Les filmographies\r\n � La\r\nbandes-annonce\r\n * Pr�sentation : Keep Case\r\n\r\n Format image :\r\n Widescreen\r\nanamorphic - 1.66:1\r\n Pan and scan\r\n\r\n Zone et formats son :\r\n Zone : Region\r\n2\r\n Langues et formats sonores : Fran�ais (Dolby Digital 2.0 Mono)\r\n\r\n# ASIN:\r\nB00008NNLM', 25.98, 0, 20070304, 'serienoiregrande.jpg', 'serienoire.jpg', 1, 2, 100, 200),
(17, 'Big Fish', '# Acteurs : Ewan McGregor, Albert Finney, Jessica\r\nLange, Marion Cotillard, Steve Buscemi\r\n# R�alisateurs : Tim Burton\r\n# Format : PAL\r\n# R�gion:\r\nR�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: _\r\n# Date de sortie du DVD : 1 janvier\r\n2005\r\n# Moyenne des commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n#\r\nFonctions DVD :\r\n\r\n# ASIN: B000F6016M', 7, 0, 20071212, 'bigfishgrande.jpg', 'bigfish.jpg', 1, 3, 100, 200),
(18, 'L''incruste', '# Acteurs : Titoff, Fr�d�ric Diefenthal, Zo�\r\nF�lix, Zinedine Soualem, Agn�s Soral\r\n# R�alisateurs : Corentin Julus, Alexandre Castagnetti\r\n#\r\nFormat : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de\r\nl''Europe. Plus d''informations sur les formats DVD.).\r\n# Studio: _\r\n# Date de sortie du DVD : 1\r\njanvier 2006\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000KN7DAW', 5, 0, 20070208, 'incrustegrande.jpg', 'incruste.jpg', 1, 3, 100, 200),
(19, 'Meilleur espoir feminin', '# Acteurs : Gerard Jugnot, Berenice\r\nBejo, Antoine Dulery, Sabine Haudepin, Chantal Lauby\r\n# R�alisateurs : Gerard Jugnot\r\n# Format :\r\nPAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe.\r\nPlus d''informations sur les formats DVD.).\r\n# Studio: _\r\n# Date de sortie du DVD : 1 janvier\r\n2005\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000CFWGE6', 5, 0, 20070115, 'meilleurespoirefeminingrande.jpg', 'meilleurespoirefeminin.jpg', 1, 3, 100, 200),
(20, 'Les Ripoux', '# Acteurs : Philippe Noiret, Thierry Lhermitte,\r\nR�gine, Grace De Capitani, Claude Brosset\r\n# R�alisateurs : Claude Zidi\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 1\r\n# Studio: Opening\r\n# Date de\r\nsortie du DVD : 1 janvier 2005\r\n# Moyenne des commentaires client : bas� sur 1 commentaire.\r\n(�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n# ASIN: B0007KKRC4', 11.99, 0, 20070205, 'lesripouxgrande.jpg', 'lesripoux.jpg', 1, 3, 100, 200),
(21, 'Paparazzi', '# Acteurs : Vincent Lindon, Patrick Timsit,\r\nCatherine Frot, Nathalie Baye, Isabelle G�linas\r\n# R�alisateurs : Alain Berberian\r\n# Format :\r\nClosed-captioned, Couleur, PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre\r\nvisualis� en dehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Rapport de forme\r\n: 2.35:1\r\n# Studio: Universal Music\r\n# Date de sortie du DVD : 9 juin 2005\r\n# Dur�e : 105\r\nminutes\r\n# Fonctions DVD :\r\n\r\n# ASIN: B00005KJYV', 12, 0, 20071215, 'paparazzigrande.jpg', 'paparazzi.jpg', 1, 3, 100, 200),
(22, 'Fant�mas', '# Acteurs : Jean Marais, Louis de Fun�s, Myl�ne\r\nDemongeot, Robert Dalban, Jacques Dynam\r\n# R�alisateurs : Andr� Hunebelle\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: G.C.T.H.V.\r\n# Date de sortie du DVD : 8\r\nnovembre 2005\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000BNEMQY', 11.6, 0, 20071001, 'fantomasgrande.jpg', 'fantomas.jpg', 1, 3, 100, 200),
(23, 'Le Corniaud', '# Acteurs : Louis De Fun�s, Bourvil, Beba Loncar,\r\nJean Lefebvre, Henri Genes\r\n# R�alisateurs : G�rard Oury\r\n# Format : PAL\r\n# R�gion: R�gion 2\r\n(Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur\r\nles formats DVD.).\r\n# Studio: Universal Music\r\n# Date de sortie du DVD : 6 ao�t 2007\r\n#\r\nMoyenne des commentaires client : bas� sur 4 commentaires. (�crivez un commentaire.)\r\n# Fonctions\r\nDVD :\r\n\r\n * Pr�sentation : Snap Case\r\n\r\n Format image :\r\n Widescreen\r\nanamorphic - 2.35:1\r\n\r\n Zone et formats son :\r\n Zone : Region 2\r\n Langues et\r\nformats sonores : Fran�ais (Dolby Digital 2.0 Mono)\r\n\r\n# ASIN: B0000CARHK', 27.98, 0, 20070105, 'lecorniaudgrande.jpg', 'lecorniaud.jpg', 1, 3, 100, 200),
(24, 'Oscar', '# Acteurs : Louis de Fun�s, Claude Rich, Claude Gensac,\r\nAgathe Natanson, Mario David\r\n# R�alisateurs : Edouard Molinaro\r\n# Format : PAL\r\n# R�gion:\r\nR�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: G.C.T.H.V.\r\n# Date de sortie du DVD : 8\r\nnovembre 2005\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000BNEMQ4', 18, 0, 20061205, 'oscargrande.jpg', 'oscar.jpg', 1, 3, 100, 200),
(25, 'Coffret Le Gendarme', '# Acteurs : Louis de Fun�s, Michel\r\nGalabru, Genevieve Grad, Jean Lefebvre, Christian Marin\r\n# R�alisateurs : Jean Girault\r\n# Format\r\n: PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de\r\nl''Europe. Plus d''informations sur les formats DVD.).\r\n# Studio: TF1 Vid�o\r\n# Date de sortie du\r\nDVD : 14 octobre 2004\r\n# Moyenne des commentaires client : bas� sur 9 commentaires. (�crivez un\r\ncommentaire.)\r\n# Fonctions DVD :\r\n\r\n � DVD 1 : Le Gendarme de St. Tropez\r\n � DVD 2 : Le Gendarme � New York\r\n � DVD 3 : Le Gendarme se marie\r\n � DVD 4 : Le Gendarme en\r\nbalade\r\n � DVD 5 : Le Gendarme et les extra-terrestres\r\n � DVD 6 : Le Gendarme et les\r\ngendarmettes\r\n * Pr�sentation : Box Set\r\n\r\n Format image :\r\n\r\n\r\n Zone et\r\nformats son :\r\n Zone : Region 2\r\n Langues et formats sonores : Fran�ais (Dolby\r\nDigital 5.0), Fran�ais (Dolby Digital 2.0)\r\n\r\n# ASIN: B00005Q4O6', 37, 0, 20071212, 'gendarmegrande.jpg', 'gendarme.jpg', 1, 3, 100, 200),
(26, 'Voyage au Tibet interdit', '# Acteurs : Priscilla Telmon\r\n#\r\nR�alisateurs : Priscilla Telmon, Thierry Robert\r\n# Format : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne\r\npourra probablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats\r\nDVD.).\r\n# Nombre de disques: 1\r\n# Studio: mk2\r\n# Date de sortie du DVD : 13 juin 2007\r\n#\r\nMoyenne des commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n# Fonctions\r\nDVD :\r\n\r\n# ASIN: B000NJM5MY', 12, 0, 20071212, 'voyagetibetgrande.jpg', 'voyagetibet.jpg', 1, 4, 100, 200),
(27, 'World Trade Center, Naissance et mort du plus grand symbole\r\nam�ricain', '# Format : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis�\r\nen dehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Studio: I.D.E\r\n# Date de\r\nsortie du DVD : 5 septembre 2003\r\n# Fonctions DVD :\r\n\r\n# ASIN: B0000CGAW8', 17, 0, 20070406, 'worltradecentergrande.jpg', 'worltradecenter.jpg', 1, 4, 100, 200),
(28, 'Stalingrad', '# Acteurs : Dominique Horwitz, Thomas Kretschmann,\r\nJochen Nickel, Sebastian Rudolph\r\n# Format : Plein �cran, PAL\r\n# R�gion: R�gion 2 (Ce DVD ne\r\npourra probablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats\r\nDVD.).\r\n# Rapport de forme : 1.85:1\r\n# Studio: FRAVIDIS\r\n# Date de sortie du DVD : 20 ao�t\r\n2001\r\n# Moyenne des commentaires client : bas� sur 2 commentaires. (�crivez un commentaire.)\r\n#\r\nFonctions DVD :\r\n\r\n  Pr�sentation : Jewel Case\r\n\r\n Format image :\r\n\r\n1.85:1\r\n Pan and scan (16:9) and (4:3)\r\n\r\n Zone et formats son :\r\n Zone :\r\nRegion 2\r\n Couches : Deux couches\r\n Langues et formats sonores : Fran�ais (Dolby\r\nDigital 2.0)\r\n\r\n# ASIN: B00005O0AK', 22, 0, 20070105, 'stalingradgrande.jpg', 'stalingrad.jpg', 1, 4, 100, 200),
(29, 'Mafalda', '# R�alisateurs : Quino\r\n# Format : PAL\r\n# R�gion:\r\nR�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 1\r\n# Studio: Karma films\r\n# Dur�e\r\n: 80 minutes\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000RGXHT8', 20, 0, 20070115, 'mafaldagrande.jpg', 'mafalda.jpg', 1, 5, 100, 200),
(30, 'Little cars', '# Format : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne\r\npourra probablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats\r\nDVD.).\r\n# Nombre de disques: 1\r\n# Studio: Wysios\r\n# Date de sortie du DVD : 19 septembre\r\n2007\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000V6W1P0', 15, 0, 20070202, 'littlecarsgrande.jpg', 'littlecars.jpg', 1, 5, 100, 200),
(31, 'Alice Au Pays Des Merveilles', '# Format : PAL\r\n# R�gion:\r\nR�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Nombre de disques: 1\r\n# Studio: L.C.J Editions\r\n#\r\nDate de sortie du DVD : 26 ao�t 2006\r\n# Fonctions DVD :\r\n\r\n# ASIN: B000GH2XNY', 29, 0, 20070205, 'alicegrande.jpg', 'alice.jpg', 1, 5, 100, 200),
(32, 'Albator 84 - L''Atlantis de ma jeunesse', '# R�alisateurs :\r\nTomoharu Katsumata\r\n# Format : Couleur, PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement\r\npas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Rapport\r\nde forme : 1.33:1\r\n# Studio: Sony/BMG\r\n# Date de sortie du DVD : 6 f�vrier 2001\r\n# Dur�e : 120\r\nminutes\r\n# Moyenne des commentaires client : bas� sur 2 commentaires. (�crivez un\r\ncommentaire.)\r\n# Fonctions DVD :\r\n\r\n � G�n�riques originaux\r\n � Pr�sentation\r\ndes personnages\r\n � Pr�sentation des vaisseaux\r\n � Pr�sentation des accessoires\r\n\r\n Pr�sentation : Snap Case\r\n\r\n Format image :\r\n 1.33:1\r\n Pan and scan\r\n\r\n\r\nZone et formats son :\r\n Zone : Region 2\r\n Couches : Deux couches\r\n Langues et\r\nformats sonores : Fran�ais (Dolby Digital 2.0 Stereo), Fran�ais (MPEG-2 5.1), Japonais (Dolby\r\nDigital 2.0 Stereo), Japonais (MPEG-2 5.1)\r\n Sous-titres : Fran�ais\r\n\r\n# ASIN:\r\nB000056WDF', 14, 0, 20071212, 'albatorgrande.jpg', 'albator.jpg', 1, 5, 100, 200),
(33, 'Pierre Desproges', '# Acteurs : Pierre Desproges\r\n# Format :\r\nPAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe.\r\nPlus d''informations sur les formats DVD.).\r\n# Studio: Warner Vision France\r\n# Date de sortie du\r\nDVD : 7 d�cembre 2004\r\n# Moyenne des commentaires client : bas� sur 2 commentaires. (�crivez un\r\ncommentaire.)\r\n# Fonctions DVD :\r\n\r\n# ASIN: B00066435Q', 19.94, 0, 20061212, 'desprogesgrande.jpg', 'desproges.jpg', 1, 7, 100, 200),
(34, 'Pierre Palmade : Vous m''avez manqu�', '# Acteurs : Pierre\r\nPalmade\r\n# R�alisateurs : Gilles Amado\r\n# Format : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra\r\nprobablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats\r\nDVD.).\r\n# Studio: Universal Pictures\r\n# Date de sortie du DVD : 2 mars 2004\r\n# Fonctions DVD\r\n:\r\n Pr�sentation : Snap Case\r\n\r\n Format image :\r\n 1.33:1\r\n Pan and\r\nscan\r\n\r\n Zone et formats son :\r\n Zone : Region 2\r\n Langues et formats sonores\r\n: Fran�ais (Dolby Digital 2.0 Stereo)\r\n\r\n# ASIN: B00008DMFN', 11, 0, 20070405, 'pierrepalmadegrande.jpg', 'pierrepalmade.jpg', 1, 7, 100, 200),
(35, 'Elie Semoun : A l''Olympia', '# Acteurs : Elie Semoun\r\n#\r\nR�alisateurs : Michel Bazile\r\n# Format : Couleur, PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra\r\nprobablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats\r\nDVD.).\r\n# Rapport de forme : 1.77:1\r\n# Studio: Universal Pictures\r\n# Date de sortie du DVD : 5\r\nnovembre 2002\r\n# Dur�e : 165 minutes\r\n# Moyenne des commentaires client : bas� sur 3\r\ncommentaires. (�crivez un commentaire.)\r\n# Fonctions DVD :\r\n\r\n  � Plus d''une heure de\r\nbonus\r\n � 3 villes en province\r\n � Les coulisses de l''Olympia\r\n Pr�sentation :\r\nSnap Case\r\n\r\n Format image :\r\n Widescreen anamorphic - 1.77:1\r\n\r\n Zone et\r\nformats son :\r\n Zone : Region 2\r\n Langues et formats sonores : Fran�ais (Dolby\r\nDigital 5.1), Fran�ais (PCM Stereo)\r\n\r\n# ASIN: B00006IWX8', 12.99, 0, 20051010, 'semoungrande.jpg', 'semoun.jpg', 1, 7, 100, 200),
(36, 'Chevallier et Laspal�s : L''Int�grale des Sketches', '# Acteurs :\r\nPhilippe Chevalier, R�gis Laspal�s\r\n# Format : PAL\r\n# R�gion: R�gion 2 (Ce DVD ne pourra\r\nprobablement pas �tre visualis� en dehors de l''Europe. Plus d''informations sur les formats\r\nDVD.).\r\n# Nombre de disques: 4\r\n# Studio: TF1 Vid�o\r\n# Date de sortie du DVD : 9 octobre\r\n2003\r\n# Moyenne des commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n#\r\nFonctions DVD :\r\n\r\n# ASIN: B0000AJGD9', 55, 0, 20061205, 'chevalierlaspallesgrande.jpg', 'chevalierlaspalles.jpg', 1, 7, 100, 200),
(37, 'Les 4 Fantastiques', '# Acteurs : Ioan Gruffudd, Jessica Alba,\r\nChris Evans, Michael Chiklis, Julian McMahon\r\n# R�alisateurs : Tim Story\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: _\r\n# Date de sortie du DVD : 1 janvier\r\n2006\r\n# Moyenne des commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n#\r\nFonctions DVD :\r\n\r\n# ASIN: B000I5Y6KW', 22, 5, 20061212, '4fantastiquesgrande.jpg', '4fantastiques.jpg', 1, 8, 100, 200),
(38, 'The Flash', '# Acteurs : John Wesley Shipp\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: Warner Home Vid�o\r\n# Date de sortie du DVD : 5\r\njuillet 2006\r\n# Moyenne des commentaires client : bas� sur 2 commentaires. (�crivez un\r\ncommentaire.)\r\n# Fonctions DVD :\r\n\r\n Pr�sentation : Box Set\r\n\r\n Format image\r\n:\r\n 1.33:1\r\n Pan and scan (4:3)\r\n\r\n Zone et formats son :\r\n Zone :\r\nRegion 2\r\n Langues et formats sonores : Fran�ais (Dolby Digital 2.0), Anglais (Dolby Digital\r\n2.0)\r\n Sous-titres : Anglais, Fran�ais\r\n\r\n# ASIN: B000FILR4U', 11, 0, 20061212, 'theflashgrande.jpg', 'theflash.jpg', 1, 8, 100, 200),
(39, 'Smallville', '# Acteurs : Tom Welling, John Schneider, Annette\r\nO''Toole, Kristin Kreuk, Michael Rosenbaum\r\n# R�alisateurs : Miles Millar, Alfred Gough\r\n#\r\nFormat : PAL\r\n# Langue : Fran�ais\r\n# R�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre\r\nvisualis� en dehors de l''Europe. Plus d''informations sur les formats DVD.).\r\n# Nombre de\r\ndisques: 6\r\n# Studio: Warner Home Vid�o\r\n# Date de sortie du DVD : 8 novembre 2006\r\n# Moyenne\r\ndes commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n# Fonctions DVD\r\n:\r\n\r\n# ASIN: B000FILR36', 12.99, 0, 20070208, 'smallvillegrande.jpg', 'smallville.jpg', 1, 8, 100, 200),
(40, 'Abyss', '# Acteurs : Ed Harris, Mary Elizabeth Mastrantonio,\r\nMichael Biehn, Leo Burmeister, Todd Graff\r\n# R�alisateurs : James Cameron\r\n# Format : PAL\r\n#\r\nR�gion: R�gion 2 (Ce DVD ne pourra probablement pas �tre visualis� en dehors de l''Europe. Plus\r\nd''informations sur les formats DVD.).\r\n# Studio: _\r\n# Date de sortie du DVD : 19 mai 2004\r\n#\r\nMoyenne des commentaires client : bas� sur 1 commentaire. (�crivez un commentaire.)\r\n# Fonctions\r\nDVD :\r\n\r\n * 25 minutes d''images suppl�mentaires\r\n * Pr�sentation : Snap Case\r\n\r\n\r\nFormat image :\r\n Widescreen anamorphic - 2.35:1\r\n\r\n Zone et formats son :\r\n\r\nZone : Region 2\r\n Langues et formats sonores : Fran�ais (Dolby Digital 5.1), Anglais (Dolby\r\nDigital 2.0 Surround)\r\n Sous-titres : Anglais, Fran�ais, Hollandais, Grec\r\n\r\n# ASIN:\r\nB00024ZJPA', 13, 15, 20061212, 'abyssgrande.jpg', 'abyss.jpg', 1, 8, 100, 200),
(41, '300 - Edition Simple DVD', 'Adapt� du roman graphique de Frank Miller, 300 est un r�cit �pique de la Bataille des Thermopyles, qui opposa en l''an - 480 le roi L�onidas et 300 soldats spartiates � Xerx�s et l''immense arm�e perse. Face � un invincible ennemi, les 300 d�ploy�rent jusqu''� leur dernier souffle un courage surhumain ; leur vaillance et leur h�ro�que sacrifice inspir�rent toute la Gr�ce � se dresser contre la Perse, posant ainsi les premi�res pierres de la d�mocratie.', 20, 0, 20080219, 'grande300.jpg', '300.jpg', 1, 8, 100, 200),
(43, 'Saw I', 'Deux hommes se r�veillent encha�n�s au mur d''une salle de bains. Ils ignorent o� ils sont et ne se connaissent pas. Ils savent juste que l''un doit absolument tuer l''autre, sinon dans moins de huit heures, ils seront ex�cut�s tous les deux...\r\nVoici l''une des situations imagn�es par un machiav�lique ma�tre criminel qui impose � ses victimes des choix auxquels personne ne souhaite jamais �tre confront� un jour. Un d�tective est charg� de l''enqu�te...', 15, 0, 20080222, 'grandesaw.jpg', 'saw.jpg', 1, 10, 100, 200),
(44, 'Saw II', 'Charg� de l''enqu�te autour d''une mort sanglante, l''Inspecteur Eric Mason est persuad� que le crime est l''oeuvre du redoutable Jigsaw, un criminel machiav�lique qui impose � ses victimes des choix auxquels personne ne souhaite jamais �tre confront�. Cette fois-ci, ce ne sont plus deux mais huit personnes qui ont �t� pi�g�es par Jigsaw...', 18.5, 0, 20080222, 'grandesawii.jpg', 'sawii.jpg', 1, 10, 100, 200),
(45, 'Saw III', 'Le Tueur au puzzle a myst�rieusement �chapp� � ceux qui pensaient le tenir.\r\nPendant que la police se d�m�ne pour tenter de remettre la main dessus, le g�nie criminel a d�cid� de reprendre son jeu terrifiant avec l''aide de sa prot�g�e, Amanda...\r\nLe docteur Lynn Denlon et Jeff ne le savent pas encore, mais ils sont les nouveaux pions de la partie qui va commencer...', 20, 0, 20080222, 'grandesawiii.jpg', 'sawiii.jpg', 1, 10, 100, 200),
(46, 'Saw IV', 'Le Tueur au puzzle et sa prot�g�e, Amanda, ont disparu, mais la partie continue. Apr�s le meurtre de l''inspectrice Kerry, deux profileurs chevronn�s du FBI, les agents Strahm et Perez, viennent aider le d�tective Hoffman � r�unir les pi�ces du dernier puzzle macabre laiss� par le Tueur pour essayer, enfin, de comprendre. C''est alors que le commandant du SWAT, Rigg, est enlev�... Forc� de participer au jeu mortel, il n''a que 90 minutes pour triompher d''une s�rie de pi�ges machiav�liques et sauver sa vie.\r\nEn cherchant Rigg � travers la ville, le d�tective Hoffman et les deux profileurs vont d�couvrir des cadavres et des indices qui vont les conduire � l''ex-femme du Tueur, Jill. L''histoire et les v�ritables intentions du Tueur au puzzle vont peu � peu �tre d�voil�es, ainsi que ses plans sinistres pour ses victimes pass�es, pr�sentes... et futures.', 17.9, 0, 20080222, 'grandesawiv.jpg', 'sawiv.jpg', 1, 10, 100, 200),
(47, 'Hostel', 'Deux �tudiants am�ricains, Paxton et Josh, ont d�cid� de d�couvrir l''Europe avec un maximum d''aventures et de sensations fortes. Avec Oli, un Islandais qu''ils ont rencontr� en chemin, ils se retrouvent dans une petite ville de Slovaquie dans ce qu''on leur a d�crit comme le nirvana des vacances de d�bauche : une propri�t� tr�s sp�ciale, pleine de filles aussi belles que faciles...\r\nNatalya et Svetlana sont effectivement tr�s cools... un peu trop, m�me. Paxton et Josh vont vite se rendre compte qu''ils sont tomb�s dans un pi�ge. Ce voyage-l� va les conduire au bout de l''horreur...', 21, 0, 20080222, 'grandehostel.jpg', 'hostel.jpg', 1, 10, 100, 196),
(52, 'Transformers', 'Une guerre sans merci oppose depuis des temps imm�moriaux deux races de robots extraterrestres : les Autobots et les cruels Decepticons. Son enjeu : la ma�trise de l''univers...\r\nDans les premi�res ann�es du 21�me si�cle, le conflit s''�tend � la Terre, et le jeune Sam Witwicky devient, � son insu, l''ultime espoir de l''humanit�. Semblable � des milliers d''adolescents, Sam n''a connu que les soucis de son �ge : le lyc�e, les amis, les voitures, les filles...\r\nEntra�n� avec sa nouvelle copine, Mikaela, au coeur d''un mortel affrontement, il ne tardera pas � comprendre le sens de la devise de la famille Witwicky : "Sans sacrifice, point de victoire !"', 24, 5, 20080224, 'grandetransformers.jpg', 'transformers.jpg', 1, 8, 100, 200),
(53, 'Benjamin Gates et le tr�sor des Templiers', 'Perdu depuis plus de 200 ans, le tr�sor des Chevaliers du Templier fait partie de ces mythiques l�gendes. Ben Gates, arch�ologue et aventurier, se lance � la recherche de ce tr�sor qui a hant� les pens�es de sa famille et de ses descendants depuis des g�n�rations. Mais il n''est pas le seul int�ress�.\r\nGates et son meilleur ami Riley Poole, expert en informatique, partent en exploration sur le continent Arctique et d�couvrent que le premier indice menant au tr�sor est cach� dans le document le mieux gard� au monde, la D�claration d''Ind�pendance.\r\nDans une course contre le temps, ils doivent voler le document si pr�cieux, d�coder la carte cach�e, semer le FBI, et �viter d''�tre tu�s par Ian Howe, un riche aventurier anglais. Et ce n''est que la premi�re �tape de cette chasse au tr�sor.', 25, 0, 20080229, 'grandebenjamingates.jpg', 'benjamingates.jpg', 1, 9, 100, 100),
(54, 'Xmen 2', 'Toujours consid�r�s comme des monstres par une soci�t� qui les rejette, les mutants sont une nouvelle fois au centre des d�bats alors qu''un crime effroyable commis par l''un d''eux relance la pol�mique autour de l''Acte d''Enregistrement des Mutants et le mouvement anti-mutants, dirig� par l''ancien militaire William Stryker.\r\nQuand ce dernier lance une attaque contre l''�cole de mutants du Professeur Charles Xavier, les X-Men se pr�parent � une guerre sans merci pour leur survie, aid�s de Magn�to, r�cemment �vad� de sa cellule de plastique. Parall�lement, Wolverine enqu�te sur son myst�rieux pass�, auquel Stryker, dont on dit qu''il a men� de nombreuses exp�riences sur les mutants, ne serait pas �tranger...', 17.5, 0, 20080229, 'grandexmen2.jpg', 'xmen2.jpg', 1, 8, 100, 100);

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

-- 
-- Contenu de la table `articlecommande`
-- 


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

-- 
-- Contenu de la table `categorie`
-- 

INSERT INTO `categorie` (`id_categorie`, `nomcategorie`) VALUES 
(1, 'Autre'),
(9, 'Aventure'),
(3, 'Com�die'),
(4, 'Documentaire'),
(8, 'Fantastique'),
(10, 'Horreur'),
(5, 'Jeunesse et Famille'),
(2, 'Policier et Thriller'),
(6, 'S�ries TV'),
(7, 'Spectacles et Humour');

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

-- 
-- Contenu de la table `client`
-- 

INSERT INTO `client` (`id_client`, `identifiantclient`, `motdepasseclient`, `nomclient`, `prenomclient`, `emailclient`, `telephoneclient`, `adresseclient`, `villeclient`, `codepostalclient`, `paysclient`) VALUES 
(3, 'jlafosse', 'cjFWZQxXeNDk30svv+76ZTWTAuU=', 'lafosse', 'jerome', 'jerome.lafosse@gmail.com', '0000000000', '4 rue du haut', 'besancon', '25000', 'france');

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

-- 
-- Contenu de la table `clientcommande`
-- 


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

-- 
-- Contenu de la table `commande`
-- 


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

-- 
-- Contenu de la table `lignecommande`
-- 


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

-- 
-- Contenu de la table `notearticle`
-- 

INSERT INTO `notearticle` (`id_article`, `id_client`, `note`) VALUES 
(27, 1, 4),
(37, 1, 8),
(47, 1, 8),
(53, 1, 10);

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

-- 
-- Contenu de la table `raccourci`
-- 

INSERT INTO `raccourci` (`id_raccourci`, `id_client`, `nomraccourci`, `urlraccourci`) VALUES 
(59, 1, '300', 'http://localhost:8080/betaboutique/gestionArticles?action=consulter&idArticle=41'),
(60, 1, 'Les 4 fantastiques', 'http://localhost:8080/betaboutique/gestionArticles?action=consulter&idArticle=37'),
(62, 3, 'gendarme', 'http://localhost:8080/betaboutiquejavaee/gestionArticles?action=consulter&idArticle=25');
