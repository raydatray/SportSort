<script>
  import "../app.css";
  import { IconHome, IconSchool, IconHistory, IconTicket, IconSettings, IconLogout } from '@tabler/icons-svelte';
  import Logo from "../assets/logo.png";
</script>

<div class="h-screen grid grid-cols-[240px_1fr] gap-x-2 p-2" data-theme="north">
  <div>
    <nav class="menu h-full justify-items-center">
      <ul class="p-3.5 menu-horizontal md:menu-vertical bg-base-300 rounded-box gap-y-0.5">
        <li class="flex-row justify-center">
          <a href="/">
            <img class = "max-h-100px" src={Logo} alt="Logo"/>
          </a>
        </li>
        <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li>
        <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconHome /> Home </a></li>
        <li class = "m-1 "><a href="/courseOfferings" class = "flex items-center space-x-2 p-0.2"> <IconSchool /> Course Offerings </a></li>
        <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconHistory /> Course History </a></li>
        <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconTicket /> Registrations </a></li>
        <li class="spacer bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
        <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li>
        <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconSettings /> Account Settings </a></li>
        <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconLogout /> Logout </a></li>
      </ul>
    </nav>
  </div>
  <div class="h-full grid grid-rows-[auto_min-content] gap-y-2 overflow-x-scroll p-2.5">
    <slot/>
    <footer class="footer footer-center p-4 bg-base-300 text-base-content rounded-box">
      <aside>
        <p>Copyright © 2024 - All right reserved by SportSort Ltd</p>
      </aside>
    </footer>
  </div>
</div>

<style lang="postcss">

  ul {
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  .spacer {
    flex-grow: 1;
  }

</style>
