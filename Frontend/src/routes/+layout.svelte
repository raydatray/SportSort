<script>
    import "../app.css";
</script>

<div data-theme= "nord" class="container">
    <ul class="menu menu-horizontal md:menu-vertical bg-base-200 rounded-box">
        <li><a>HOME</a></li>
        <li><a>ABOUT</a></li>
        <li><a>Contact</a></li>
    </ul>
    <div class="content">
        <slot></slot>

        <footer class="footer footer-center p-4 bg-base-300 text-base-content">
          <aside>
            <p>Copyright © 2024 - All right reserved by ACME Industries Ltd</p>
          </aside>
        </footer>
            
    </div>
</div>

<style lang="postcss">
    html, body {
        height: 100%;  /* Ensure full viewport height */
        width: 100%;  /* Ensure full viewport width */
    }

    .container {
        display: grid;
        grid-template-columns: auto 1fr;
        position: absolute;
        left: 0;
        right: 0;
        height: 100vh;  /* Stretch container to full height */
    }

    .menu {
        grid-column: 1;
        height: 100%;  /* Make menu extend full height */
        /* Optionally add a subtle background color or border for visual separation */
        background-color: rgba(255, 255, 255, 0.05);
    }

    .content {
        grid-column: 2;
        height: 100%;  /* Stretch content to full height */
    }

    .footer {
        display: grid;
        position: absolute;
        bottom: 0;
        grid-template-columns: 1fr auto;
        gap: 1rem;
    }

    @media (max-width: 768px) {
        .container {
            grid-template-columns: 1fr;
            grid-template-rows: auto 1fr;
        }

        .menu {
            grid-row: 2;
            width: 100%;
        }

        .content {
            grid-row: 1;
        }
    }
</style>