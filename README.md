# CY-J2EE
without Spring Boot

## Installation :
### IntelliJ
1) Télécharger localement les fichiers du projet
2) Ouvrir le projet avec IntelliJ
3) Configurer le lancement du projet :
  1) Cliquer sur "Current File" en haut de la fenetre IntelliJ
  2) Puis "Edit Configurations..."
  3) "Add new"
  4) "TomCat Server > Local"
  5) Dans l'onglet "Server", mettre l'URL à `http://localhost:####`/CY-J2EE/Login.jsp
  6) Dans l'onglet "Deployment"
     1) Ajoutez un nouvel artifact
     2) En bas de la page, Application context = /CY-J2EE

### Base de Donnée
1) Lancer Wampserveur
2) Lancer MySQL WorkBench
3) Ouvrir un nouvel onglet sql
4) Copier et executer le contenu de [conception/JeeDb.sql]
