# CY-J2EE
Sans Spring Boot

# Description

Notre projet est une application JEE qui permet à une entreprise de gérer ses ressources humaines, ses départements et ses projets.

Dans cette application, l'administrateur et les responsables ressources humaines peuvent voir la liste des employés, des départements et des projets de l'entreprise et en ajouter, les modifier et les supprimer.

Mais ils peuvent aussi générer des fiches de paie pour les employés et voir les statistiques dans les départements et les projets sour forme de tableaux et de graphiques.

## Installation :
### IntelliJ
1) Télécharger localement les fichiers du projet
2) Ouvrir le projet avec IntelliJ
3) Configurer le lancement du projet :
    1) Cliquer sur "Current File" en haut de la fenêtre IntelliJ
    2) Puis "Edit Configurations..."
    3) "Add new"
    4) "TomCat Server > Local"
    5) Dans l'onglet "Server", mettre l'URL à `http://localhost:####`/CY-J2EE/index.jsp
    6) Dans l'onglet "Deployment" :
        1) Ajoutez un nouvel artifact
        2) En bas de la page, Application context = /CY-J2EE

### Base de Données
1) Lancer Wampserver
2) Lancer MySQL WorkBench
3) Ouvrir un nouvel onglet sql
4) Copier et exécuter le contenu de [conception/JeeDb.sql]

# Compilation et exécution

Lorsque nous compilons et exécutons le programme, nous arrivons sur cette page :

![accueil.jpg](images_ReadME/accueil.png)

Puis on arrive sur la page de connexion :

![connexion_admin.png](images_ReadME/Connexion_admin.png)

Si un administrateur ou Responsble ressources humaines se connecte, il arrive sur le tableau de bord : 

![dashboard.png](images_ReadME/dashboard.png)

Depuis ce tableau de bord, il peut voir la liste des employés, avec ou sans filtre : 

![liste_employes.png](images_ReadME/liste_employes.png)

Il peut voir la liste des fiches de paie, avec ou sans filtre, et les consulter :

![liste_fiches_paie.png](images_ReadME/liste_fiches_paie.png)
![fiche_paie.png](images_ReadME/fiche_paie.png)

Il a accès à la liste des absences de chaque employé : 

![liste_absences.png](images_ReadME/liste_absences.png)

Il voit la liste des départements et les membres de chacun : 

![liste_departements.png](images_ReadME/liste_departements.png)
![membres_departements.png](images_ReadME/membres_departements.png)

Il voit la liste des projets et les membres de chacun : 

![liste_projets.png](images_ReadME/liste_projets.png)
![membres_projets.png](images_ReadME/membres_projets.png)

Il voit les statistiques des employés : 

![statistiques_departements.png](images_ReadME/statistiques_departements.png)
![statistiques_projets.png](images_ReadME/statistiques_projets.png)
![statistiques_grade.png](images_ReadME/statistiques_grade.png)

Mais il peut aussi accéder à son tableau de bord en cliquant sur "vers votre tableau de bord" :

![dashboard_admin_employe.png](images_ReadME/dashboard_admin_employe.png)

Maintenant si un employé se connecte pour la première fois, il devra changer son mot de passe :

![connexion_employe.png](images_ReadME/connexion_employe.png)
![changement_mot_de_passe.png](images_ReadME/changement_mot_de_passe.png)

Et il sera redirigé vers son tableau de bord :

![dashboard_employe.png](images_ReadME/dashboard_employe.png)

# Auteurs

- Esteban ABEHZELE
- Heeshaam FOWDAR
- Emiline LAM
- Mattéo MANRESA
- Younes NEHAD

# Informations

Professeur : Mohamed HADDACHE

Classe : GSI-1 (CY-Tech, Cergy)

Date finale de rendu : Dimanche 30 novembre 2025 avant 22h00
