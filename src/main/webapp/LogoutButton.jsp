<header>
    <form action="LogoutServlet" method="get" style="position:absolute; top:15px; right:20px;">
        <button type="submit" class="logout-btn">Se deconnecter</button>
    </form>
</header>

<style>
    .logout-btn {
        background:#27ae60;   /* même vert que tes autres boutons */
        color:#fff;
        border:none;
        padding:8px 16px;
        border-radius:4px;
        font-weight:bold;
        cursor:pointer;
        transition: background 0.3s ease;
    }
    .logout-btn:hover {
        background:#219150;   /* vert plus foncé au survol */
    }
</style>
