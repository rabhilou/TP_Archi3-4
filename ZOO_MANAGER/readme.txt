1-Introduction


L’objectif de ce TP est d’apprendre comment configurer un environnement de déploiement afin
de déployer un service sur un serveur externe hebergé en Paas et ajouter un système de persistance pour le service RESTFUL développé au TP1 et TP2.


2- Déscription 

2-1 partie Déeploiement 

-afin de deployer notre service sur le PaaS BlueMix il faut d'abord modifier le     service afin de créer une archive war. 

-pour créer une archive war on a  utilsee maven qui est demandé au tp.

-on a créer un compte gratuit de test sur la platforme BlueMix.

- on a installé Cloud Foundry CLI qui nous permet la gestion de nos deploiments et instances de services.


fichier war: un fichier WAR (pour Web application Archive) est un fichier JAR utilisé pour contenir un ensemble de JavaServer Pages, servlets, classes Java, fichiers XML, et des pages web statiques (HTML, JavaScript…), le tout constituant une application web. Cette archive est utilisée pour déployer une application web sur un serveur d'applications.

2-2 persistance 
On a choisi MYsql comme SGBD

les raisons qui nous ont poussés à choisr MYsql:

-Bénéficier de la puissance des SGBD en terme de normalisation et de performances.

-la documentation disponible ainsi que la simplicité d’utilisation et d’implémentation.

-il est facile à prendre en main, et vu que tout au long de notre cursus on l'a souvent utilisé, c'est pour ça qu'on a opté pour MYsql.

-vu qu'on a pas a dévellopé un grand systeme, donc MYsql nous suffira

-vu que generalement c'est les jointures qui sont couteuses alors que dans notre systeme on n'en aura pas besoin donc MYsql fera l'affaire .

A noter qu'on n'a pas pu faire la persistance au niveau de bluemix, par contre en local ça marche.



Remarques: 

-on a créer une classe DAO qui contient toutes les methodes de traitements( suppression, ajout, modification ).


-lien pour le bluemix: zoo-manager-saurischian-threepence.eu-gb.mybluemix.net/zoo-manager/animals




