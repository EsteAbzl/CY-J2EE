DROP DATABASE IF EXISTS cy_j2ee_db;
CREATE DATABASE IF NOT EXISTS cy_j2ee_db;
USE cy_j2ee_db;

DROP TABLE IF EXISTS Departement;
CREATE TABLE IF NOT EXISTS Departement
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nom VARCHAR(32) NOT NULL
);

-- Insert departments (script will be run once)
-- Insert departments (idempotent: only insert if not exists)
INSERT INTO Departement (nom)
SELECT 'Administration' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM Departement WHERE nom = 'Administration');
INSERT INTO Departement (nom)
SELECT 'Ressources Humaines' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM Departement WHERE nom = 'Ressources Humaines');
INSERT INTO Departement (nom)
SELECT 'Finance' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM Departement WHERE nom = 'Finance');
INSERT INTO Departement (nom)
SELECT 'Informatique' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM Departement WHERE nom = 'Informatique');
INSERT INTO Departement (nom)
SELECT 'Marketing' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM Departement WHERE nom = 'Marketing');
INSERT INTO Departement (nom)
SELECT 'Production' FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM Departement WHERE nom = 'Production');

DROP TABLE IF EXISTS Employe;
CREATE TABLE IF NOT EXISTS Employe
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nom VARCHAR(32) NOT NULL,
  prenom VARCHAR(32) NOT NULL,
  email VARCHAR(64) NOT NULL,
  mdp VARCHAR(255) NOT NULL,
  premiere_connexion BOOL NOT NULL DEFAULT FALSE,
  poste VARCHAR(32) NOT NULL,
  grade VARCHAR(32) NOT NULL,
  estArchive BOOL NOT NULL,
  idDepartement INT NOT NULL,
  FOREIGN KEY fk_departement(idDepartement) REFERENCES Departement (id)
);

-- Insert admin user once with SHA2('admin',256) as requested
INSERT INTO Employe (nom, prenom, email, mdp, premiere_connexion, poste, grade, estArchive, idDepartement)
SELECT
  'Admin',
  'Admin',
  'admin',
  SHA2('admin',256),
  FALSE,
  'Administrateur',
  'ADMIN',
  FALSE,
  (SELECT id FROM Departement WHERE nom = 'Administration' LIMIT 1)
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM Employe WHERE email = 'admin');

DROP TABLE IF EXISTS Projet;
CREATE TABLE IF NOT EXISTS Projet(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  avancement VARCHAR(30)
);

DROP TABLE IF EXISTS Salaire_Employe_Date;
CREATE TABLE IF NOT EXISTS Salaire_Employe_Date(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  salaire FLOAT NOT NULL,
  date VARCHAR(16)
);

DROP TABLE IF EXISTS Salaire_extra;
CREATE TABLE IF NOT EXISTS Salaire_extra(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  date VARCHAR(16),
  montant FLOAT NOT NULL
);

DROP TABLE IF EXISTS Role;
CREATE TABLE IF NOT EXISTS Role(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  libelle VARCHAR(32),
  idEmploye INT NOT NULL,
  FOREIGN KEY fk_employe(idEmploye) REFERENCES Employe(id)
);

DROP TABLE IF EXISTS Participer;
CREATE TABLE IF NOT EXISTS Participer(
  idEmploye INT NOT NULL,
  idProjet INT NOT NULL,
  CONSTRAINT pk_Participer PRIMARY KEY (idEmploye, idProjet),
  FOREIGN KEY fk_employe(idEmploye) REFERENCES Employe(id),
  FOREIGN KEY fk_projet(idProjet) REFERENCES Projet(id)
);

DROP TABLE IF EXISTS Recevoir;
CREATE TABLE IF NOT EXISTS Recevoir(
  idEmploye INT NOT NULL,
  idSalaire_Employe_Date INT NOT NULL,
  CONSTRAINT pk_Recevoir PRIMARY KEY (idEmploye, idSalaire_Employe_Date),
  FOREIGN KEY fk_employe(idEmploye) REFERENCES Employe(id),
  FOREIGN KEY fk_salaire_employe_date(idSalaire_Employe_Date) REFERENCES Salaire_Employe_Date(id)
);

DROP TABLE IF EXISTS Obtenir;
CREATE TABLE IF NOT EXISTS Obtenir(
  idEmploye INT NOT NULL,
  idSalaire_extra INT NOT NULL,
  CONSTRAINT pk_Obtenir PRIMARY KEY (idEmploye, idSalaire_extra),
  FOREIGN KEY fk_employe(idEmploye) REFERENCES Employe(id),
  FOREIGN KEY fk_salaire_extra(idSalaire_extra) REFERENCES Salaire_extra(id)
);