1-Introduction


L�objectif de ce TP est d�apprendre comment configurer un environnement de d�ploiement afin
de d�ployer un service sur un serveur externe heberg� en Paas et ajouter un syst�me de persistance pour le service RESTFUL d�velopp� au TP1 et TP2.


2- D�scription 

2-1 partie D�eploiement 

-afin de deployer notre service sur le PaaS BlueMix il faut d'abord modifier le     service afin de cr�er une archive war. 

-pour cr�er une archive war on a  utilsee maven qui est demand� au tp.

-on a cr�er un compte gratuit de test sur la platforme BlueMix.

- on a install� Cloud Foundry CLI qui nous permet la gestion de nos deploiments et instances de services.


fichier war: un fichier WAR (pour Web application Archive) est un fichier JAR utilis� pour contenir un ensemble de JavaServer Pages, servlets, classes Java, fichiers XML, et des pages web statiques (HTML, JavaScript�), le tout constituant une application web. Cette archive est utilis�e pour d�ployer une application web sur un serveur d'applications.

2-2 persistance 
On a choisi MYsql comme SGBD

les raisons qui nous ont pouss�s � choisr MYsql:

-B�n�ficier de la puissance des SGBD en terme de normalisation et de performances.

-la documentation disponible ainsi que la simplicit� d�utilisation et d�impl�mentation.

-il est facile � prendre en main, et vu que tout au long de notre cursus on l'a souvent utilis�, c'est pour �a qu'on a opt� pour MYsql.

-vu qu'on a pas a d�vellop� un grand systeme, donc MYsql nous suffira

-vu que generalement c'est les jointures qui sont couteuses alors que dans notre systeme on n'en aura pas besoin donc MYsql fera l'affaire .

A noter qu'on n'a pas pu faire la persistance au niveau de bluemix, par contre en local �a marche.



Remarques: 

-on a cr�er une classe DAO qui contient toutes les methodes de traitements( suppression, ajout, modification ).


-lien pour le bluemix: zoo-manager-saurischian-threepence.eu-gb.mybluemix.net/zoo-manager/animals




