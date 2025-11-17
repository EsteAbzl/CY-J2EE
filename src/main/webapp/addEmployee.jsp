<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un employé</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
        }
        .form-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 500px;
        }
        .form-card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2a5298;
        }
        .form-control {
            border-radius: 8px;
        }
        .btn-primary {
            background-color: #2a5298;
            border: none;
            border-radius: 8px;
            font-weight: bold;
        }
        .btn-primary:hover {
            background-color: #1e3c72;
        }
        .footer {
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }
        .footer a {
            color: #2a5298;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="form-card">
    <h2>Ajouter un employé</h2>
    <form method="post" action="addEmployee">
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom de famille">
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prénom</label>
            <input type="text" class="form-control" id="prenom" name="prenom" placeholder="Prénom">
        </div>
        <!-- Email will be generated automatically as nom.prenom@entreprise.com (hidden field) -->
        <div class="mb-3">
            <label class="form-label">Adresse email (aperçu)</label>
            <input type="text" readonly class="form-control" id="emailPreviewDisplay" placeholder="nom.prenom@entreprise.com">
        </div>
        <input type="hidden" name="email" id="emailHidden">
        <div class="mb-3">
            <label for="poste" class="form-label">Poste</label>
            <input type="text" class="form-control" id="poste" name="poste" placeholder="Intitulé du poste">
        </div>
        <div class="mb-3">
            <label for="departement" class="form-label">Département</label>
            <select class="form-control" id="departement" name="departement">
                <option>Ressources Humaines</option>
                <option>Finance</option>
                <option>Informatique</option>
                <option>Marketing</option>
                <option>Production</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="dateEmbauche" class="form-label">Date d’embauche</label>
            <input type="date" class="form-control" id="dateEmbauche" name="dateEmbauche">
        </div>
        <button type="submit" class="btn btn-primary w-100">Ajouter l’employé</button>
    </form>
    <script>
        // Generate email from nom and prenom fields
        function slugify(s){
            // remove diacritics, keep alphanumerics, replace other chars with dots
            const t = s.normalize('NFD').replace(/\p{Diacritic}/gu, '');
            return t.trim().toLowerCase().replace(/[^a-z0-9]+/g, '.')
                .replace(/^\.|\.$/g, '');
        }

        const nomInput = document.getElementById('nom');
        const prenomInput = document.getElementById('prenom');
        const emailPreviewDisplay = document.getElementById('emailPreviewDisplay');
        const emailHidden = document.getElementById('emailHidden');

        function updateEmail(){
            const nom = nomInput.value || '';
            const prenom = prenomInput.value || '';
            const localPart = slugify(nom) + (prenom ? '.' + slugify(prenom) : '');
            const email = localPart ? localPart + '@entreprise.com' : '';
            if(emailPreviewDisplay) emailPreviewDisplay.value = email;
            if(emailHidden) emailHidden.value = email;
        }

        nomInput.addEventListener('input', updateEmail);
        prenomInput.addEventListener('input', updateEmail);

        // Ensure email is set before submit (in case JS altered or user has autofill)
        document.querySelector('form').addEventListener('submit', function(){ updateEmail(); });
    </script>
    <div class="footer">
        <a href="dashboardRH.jsp">Retour au tableau de bord RH</a>
    </div>
</div>
</body>
</html>
