<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Système de contrôle d'accès - Guide d'utilisation</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        .container { max-width:1200px; margin:0 auto; background:#fff; border-radius:12px; box-shadow:0 8px 20px rgba(0,0,0,0.25); padding:2rem; }
        h1 { color:#2a5298; text-align:center; border-bottom:3px solid #2a5298; padding-bottom:1rem; }
        h2 { color:#1e3c72; margin-top:2rem; border-left:4px solid #2a5298; padding-left:1rem; }
        .role-card { 
            background:linear-gradient(135deg,#f5f7fa,#e0e7ff); 
            border-left:4px solid #2a5298; 
            padding:1.5rem; 
            margin:1rem 0; 
            border-radius:8px;
        }
        .role-card h3 { color:#2a5298; margin-top:0; }
        .permissions { list-style:none; padding:0; }
        .permissions li { padding:0.5rem 0; padding-left:1.5rem; position:relative; }
        .permissions li:before { content:"✓"; position:absolute; left:0; color:#27ae60; font-weight:bold; }
        .denied { color:#c0392b; }
        .allowed { color:#27ae60; }
        table { width:100%; border-collapse:collapse; margin:1rem 0; }
        th, td { padding:1rem; border:1px solid #ddd; text-align:left; }
        th { background:#2a5298; color:#fff; }
        tr:nth-child(even) { background:#f9f9f9; }
        .feature-section { margin:2rem 0; }
        .note { background:#fff3cd; border-left:4px solid #ffc107; padding:1rem; margin:1rem 0; }
        .warning { background:#f8d7da; border-left:4px solid #dc3545; padding:1rem; margin:1rem 0; }
        .back-btn { display:inline-block; background:#2a5298; color:#fff; padding:0.75rem 1.5rem; text-decoration:none; border-radius:8px; font-weight:bold; text-align:center; }
        .back-btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<div class="container">
    <h1>📋 Système de contrôle d'accès basé sur les rôles</h1>

    <div class="feature-section">
        <h2>🔐 Hiérarchie des rôles</h2>
        <p>Le système comporte 4 rôles hiérarchisés avec des permissions différentes :</p>

        <div class="role-card">
            <h3>1️⃣ ADMIN - Administrateur</h3>
            <p>Accès complet à toutes les fonctionnalités du système.</p>
            <ul class="permissions">
                <li><strong>Employés :</strong> Voir, ajouter, modifier, supprimer</li>
                <li><strong>Fiches de paie :</strong> Voir, générer, consulter (pour tous les employés)</li>
                <li><strong>Départements :</strong> Voir, ajouter, modifier, supprimer</li>
                <li><strong>Projets :</strong> Voir, ajouter, modifier, supprimer, affecter des employés (tous les projets)</li>
                <li><strong>Rapports :</strong> Accès complet</li>
            </ul>
        </div>

        <div class="role-card">
            <h3>2️⃣ DEPT_HEAD - Chef de département</h3>
            <p>Gestion complète des employés et projets de son département uniquement.</p>
            <ul class="permissions">
                <li><strong>Employés :</strong> Voir, ajouter, modifier, supprimer (de son département)</li>
                <li><strong>Fiches de paie :</strong> Voir, générer (pour les employés de son département)</li>
                <li><strong>Départements :</strong> Voir seulement son département</li>
                <li><strong>Projets :</strong> Voir, ajouter, modifier (pour son département), affecter des employés</li>
                <li><strong>Accès refusé :</strong> Gestion des autres départements, suppression de projets</li>
            </ul>
        </div>

        <div class="role-card">
            <h3>3️⃣ PROJECT_HEAD - Chef de projet</h3>
            <p>Consultation des projets auxquels il est assigné comme chef.</p>
            <ul class="permissions">
                <li><strong>Employés :</strong> Voir uniquement les membres de ses projets</li>
                <li><strong>Fiches de paie :</strong> Accès refusé</li>
                <li><strong>Départements :</strong> Accès refusé</li>
                <li><strong>Projets :</strong> Voir détails, consulter membres (de ses projets uniquement)</li>
                <li><strong>Affectations :</strong> Voir les assignations de ses projets</li>
            </ul>
        </div>

        <div class="role-card">
            <h3>4️⃣ EMPLOYEE - Employé lambda</h3>
            <p>Accès en lecture seule aux projets auxquels il est assigné.</p>
            <ul class="permissions">
                <li><strong>Employés :</strong> Accès refusé</li>
                <li><strong>Fiches de paie :</strong> Accès refusé</li>
                <li><strong>Départements :</strong> Accès refusé</li>
                <li><strong>Projets :</strong> Voir détails des projets auxquels il est assigné</li>
                <li><strong>Affectations :</strong> Aucune (lecture seule)</li>
            </ul>
        </div>
    </div>

    <div class="feature-section">
        <h2>📊 Matrice de permissions</h2>
        <table>
            <tr>
                <th>Fonctionnalité</th>
                <th>ADMIN</th>
                <th>DEPT_HEAD</th>
                <th>PROJECT_HEAD</th>
                <th>EMPLOYEE</th>
            </tr>
            <tr>
                <td><strong>Lister employés</strong></td>
                <td class="allowed">✓ Tous</td>
                <td class="allowed">✓ Département</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Ajouter employé</strong></td>
                <td class="allowed">✓</td>
                <td class="allowed">✓ Son dept</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Supprimer employé</strong></td>
                <td class="allowed">✓</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Lister fiches de paie</strong></td>
                <td class="allowed">✓ Toutes</td>
                <td class="allowed">✓ Son dept</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Générer fiche de paie</strong></td>
                <td class="allowed">✓</td>
                <td class="allowed">✓ Son dept</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Lister projets</strong></td>
                <td class="allowed">✓ Tous</td>
                <td class="allowed">✓ Son dept</td>
                <td class="allowed">✓ Assignés</td>
                <td class="allowed">✓ Assignés</td>
            </tr>
            <tr>
                <td><strong>Voir détails projet</strong></td>
                <td class="allowed">✓</td>
                <td class="allowed">✓ Son dept</td>
                <td class="allowed">✓ Ses projets</td>
                <td class="allowed">✓ Ses projets</td>
            </tr>
            <tr>
                <td><strong>Ajouter projet</strong></td>
                <td class="allowed">✓</td>
                <td class="allowed">✓ Son dept</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Modifier projet</strong></td>
                <td class="allowed">✓</td>
                <td class="allowed">✓ Son dept</td>
                <td class="allowed">✓ Ses projets</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Supprimer projet</strong></td>
                <td class="allowed">✓</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
                <td class="denied">✗</td>
            </tr>
            <tr>
                <td><strong>Affecter employé au projet</strong></td>
                <td class="allowed">✓</td>
                <td class="allowed">✓ Son dept</td>
                <td class="allowed">✓ Ses projets</td>
                <td class="denied">✗</td>
            </tr>
        </table>
    </div>

    <div class="feature-section">
        <h2>🛡️ Sécurité implémentée</h2>
        <div class="note">
            <strong>SecurityFilter :</strong> Toutes les pages et servlets protégées vérifient l'authentification de l'utilisateur. Redirection automatique vers la page de connexion si pas d'authentification.
        </div>
        <div class="note">
            <strong>AccessControl :</strong> Classe utilitaire centralisée qui contient toute la logique de vérification des permissions.
        </div>
        <div class="note">
            <strong>Contrôles au niveau servlet :</strong> Chaque servlet effectue une vérification des droits avant de traiter la requête.
        </div>
        <div class="note">
            <strong>Contrôles au niveau JSP :</strong> Les boutons d'action s'affichent/masquent selon les droits de l'utilisateur.
        </div>
    </div>

    <div class="feature-section">
        <h2>⚙️ Implémentation technique</h2>
        <h3>Classes créées/modifiées :</h3>
        <ul class="permissions">
            <li><strong>AccessControl.java :</strong> Classe utilitaire pour la vérification des droits</li>
            <li><strong>SecurityFilter.java :</strong> Filtre d'authentification global</li>
            <li><strong>AccessDeniedServlet.java :</strong> Servlet pour les accès refusés</li>
            <li><strong>ProjectDetailsServlet.java :</strong> Nouvelle servlet pour consulter les détails d'un projet</li>
            <li><strong>EmployeeListServlet.java :</strong> Mise à jour avec contrôle d'accès</li>
            <li><strong>ProjectsListServlet.java :</strong> Mise à jour avec contrôle d'accès et filtrage</li>
            <li><strong>PayslipListServlet.java :</strong> Mise à jour avec contrôle d'accès</li>
            <li><strong>ProjectCreateServlet.java :</strong> Mise à jour avec contrôle d'accès</li>
            <li><strong>ProjectEditServlet.java :</strong> Mise à jour avec contrôle d'accès</li>
        </ul>
    </div>

    <div class="feature-section">
        <h2>📁 Pages créées/modifiées</h2>
        <ul class="permissions">
            <li><strong>accessDenied.jsp :</strong> Page d'erreur d'accès refusé</li>
            <li><strong>projectDetails.jsp :</strong> Nouvelle page pour consulter les détails d'un projet</li>
            <li><strong>employeesList.jsp :</strong> Modification - affichage conditionnel des boutons</li>
            <li><strong>projectsList.jsp :</strong> Modification - affichage conditionnel, noms de département, lien vers détails</li>
        </ul>
    </div>

    <div class="feature-section">
        <div class="warning">
            <strong>⚠️ Important :</strong> Pour que le système fonctionne correctement, assurez-vous que :
            <ul>
                <li>Chaque utilisateur a un <strong>employee_id</strong> assigné (sauf ADMIN)</li>
                <li>Chaque employé a un <strong>department_id</strong> assigné</li>
                <li>Les chefs de projet doivent avoir le rôle <strong>'Chef de projet'</strong> dans la table project_assignments</li>
                <li>Les chefs de département doivent avoir un role_id = 2 (DEPT_HEAD)</li>
            </ul>
        </div>
    </div>

    <div style="text-align:center; margin-top:2rem;">
        <a href="dashboard.jsp" class="back-btn">← Retour au tableau de bord</a>
    </div>
</div>
</body>
</html>
