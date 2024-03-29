<script>
    import "../app.css";
</script>

<div class = "container">
    <ul class = "menu menu-horizontal md:menu-vertical bg-base-200 rounded-box">
        <li><a>HOME</a></li>
        <li><a>ABOUT</a></li>
        <li><a>Contact</a></li>
    </ul>
    <div class = "content">
        <slot></slot>
        <footer class="footer p-10 bg-neutral text-neutral-content">
            <nav>
                <h6 class="footer-title">Services</h6>
                <a class="link link-hover">Branding</a>
                <a class="link link-hover">Design</a>
                <a class="link link-hover">Marketing</a>
                <a class="link link-hover">Advertisement</a>
            </nav>
            <nav>
                <h6 class="footer-title">Company</h6>
                <a class="link link-hover">About us</a>
                <a class="link link-hover">Contact</a>
                <a class="link link-hover">Jobs</a>
                <a class="link link-hover">Press kit</a>
            </nav>
            <nav>
                <h6 class="footer-title">Legal</h6>
                <a class="link link-hover">Terms of use</a>
                <a class="link link-hover">Privacy policy</a>
                <a class="link link-hover">Cookie policy</a>
            </nav>
        </footer>
    </div>
</div>

<style lang = "postcss">
    .container {
        display: grid;
        grid-template-areas:
            "menu content";
        grid-template-columns: auto 1fr;
    }

    .menu {
        grid-area: menu;
    }

    .content {
        grid-area: content;
    }


    @media (max-width: 768px) {
        .container{
            display: grid;
            grid-template-areas:
                "content"
                "menu";
            grid-template-rows: 1fr 1fr;
        }
    }

</style>