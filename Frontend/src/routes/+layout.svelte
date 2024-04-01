<script>
  import "../app.css";
  import { IconHome, IconSchool, IconHistory, IconTicket, IconSettings, IconLogout } from '@tabler/icons-svelte';
  import Logo from "../assets/logo.png";
</script>

<div class="container" data-theme="north">
  <nav class="menu">
    <ul class="menu-horizontal md:menu-vertical bg-base-200 rounded-box">
      <li class="logo-container">
        <img src={Logo} alt="Logo" class="logo"/>
      </li>
      <li class="spacer1"></li>
      <li><a> <IconHome /> Home </a></li>
      <li><a> <IconSchool /> Course Offerings </a></li>
      <li><a> <IconHistory /> Course History </a></li>
      <li><a> <IconTicket /> Registrations </a></li>
      <li class="spacer bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="spacer2"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li><a> <IconSettings /> Account Settings </a></li>
      <li><a> <IconLogout /> Logout </a></li>
    </ul>
  </nav>
  <div class="content">
    <slot />
    <footer class="p-4 footer bg-base-300 text-base-content">
      <aside>
        <p>Copyright © 2024 - All right reserved by Sports Ltd</p>
      </aside>
    </footer>
  </div>
</div>

<style lang="postcss">
  html, body {
    overflow-x: hidden;
    height: 100%;
    margin: 0;
  }

  .container {
    display: grid;
    grid-template-columns: 240px 1fr;
    min-height: 100vh;
    overflow-x: hidden;
  }

  .menu {
    background-color: rgba(255, 255, 255, 0.05);
    height: 100vh;
    overflow-y: auto;
    margin: 0;
  }

  .logo-container {
    display: flex;
    justify-content: center;
    margin: 1em 0;
  }

  .logo {
    max-height: 100px;
    width: auto;
  }

  ul {
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  .spacer {
    flex-grow: 1;
  }

  .content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    overflow-y: auto;
  }

  .footer {
    margin-top: auto;
  }

  @media (max-width: 768px) {
    .container {
      grid-template-columns: 1fr;
      grid-template-rows: auto 1fr auto;
    }

    .menu {
      grid-row: 3;
      grid-column: 1 / -1;
    }

    .content {
      grid-row: 1 / 2;
      grid-column: 1 / -1;
    }

    .footer {
      position: static;
    }
  }
</style>
