<script>
  import "../app.css";
  import { IconHome, IconSchool, IconHistory, IconTicket, IconSettings, IconLogout } from '@tabler/icons-svelte';
  import Logo from "../assets/logo.png";
</script>

<div class="h-screen grid grid-cols-[240px_1fr] gap-x-1 p-2" data-theme="north">
  <ul class="p-4 m-2 menu-horizontal md:menu-vertical bg-base-200 rounded-box gap-y-1">
    <li class="flex-row justify-center p-2 m-1">
      <img class = "max-h-100px" src={Logo} alt="Logo"/>
    </li>
    <li class="h-0.5 spacer2 bg-base-300"></li> 
    <li class = "gap-y-.5 m-2"><a class = "flex items-center space-x-2 p-0.2"> <IconHome /> Home </a></li>
    <li class = "gap-y-.5 m-2"><a class = "flex items-center space-x-2 p-0.2"> <IconSchool /> Course Offerings </a></li>
    <li class = "gap-y-.5 m-2"><a class = "flex items-center space-x-2 p-0.2"> <IconHistory /> Course History </a></li>
    <li class = "gap-y-.5 m-2"><a class = "flex items-center space-x-2 p-0.2"> <IconTicket /> Registrations </a></li>
    <li class="spacer bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
    <li class="h-0.5 spacer2 bg-base-300"></li> 
    <li class = "gap-y-.5 m-2"><a class = "flex items-center space-x-2 p-0.2"> <IconSettings /> Account Settings </a></li>
    <li class = "gap-y-.5 m-2"><a class = "flex items-center space-x-2 p-0.2"> <IconLogout /> Logout </a></li>
  </ul>
  <div class="content">
    <slot />
    <footer class="p-4 footer bg-base-300 text-base-content">
      <aside>
        <p>Copyright © 2024 - All right reserved by SportSort Ltd</p>
      </aside>
    </footer>
  </div>
</div>

<style lang="postcss">
  .container {
    display: grid;
    grid-template-columns: 240px 1fr;
    grid-template-areas: "menu content";
    min-height: 100vh;
    overflow-x: hidden;
    width: 100vw; /* Use viewport width to ensure full width coverage */
  }
  
  .menu {
    background-color: rgba(255, 255, 255, 0.05);
    height: 100vh;
    overflow-y: auto;
    margin: 0;
    bottom: 0;
    border-radius: 10px;
  }

  .logo-container {
    display: flex;
    justify-content: center;
    margin: 1em 0;
    margin-top: 8%;
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
    margin-top: 0.75%;
  }

  .footer {
    margin-top: auto;
    margin-bottom: 0.75%;
    border-radius: 10px;
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
