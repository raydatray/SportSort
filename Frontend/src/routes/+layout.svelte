<script>
  import "../app.css";
import { IconLogin, IconHome, IconSchool, IconHistory, IconTicket, IconSettings, IconLogout, IconDoor, IconUsers, IconUserScreen, IconBallFootball } from '@tabler/icons-svelte';
import Logo from "../assets/logo.png";
import axios from "axios";

const backendUrl = 'http://127.0.0.1:8080/';

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
});

// Check if sessionStorage is available
const isSessionStorageAvailable = typeof sessionStorage !== 'undefined';

// Access sessionStorage if available
let userType = null;
if (isSessionStorageAvailable) {
    userType = sessionStorage.getItem('role');
}

function logout() {
    AXIOS.post("/logout", null, {
        headers : {
            'userToken' : sessionStorage.getItem('token')
        }
    })
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('token');
    window.location.reload();
}
</script>

<div class="h-screen grid grid-cols-[240px_1fr] gap-x-1 p-2 mr-2" data-theme="north">
  <nav class="menu justify-items-center">
  <ul class="p-3 m-1 menu-horizontal md:menu-vertical bg-base-300 rounded-box gap-y-0.5">
    <li class="flex-row justify-center">
      <a href="/">
        <img class = "max-h-100px" src={Logo} alt="Logo"/>
      </a>  
    </li>
    <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li> 
    {#if userType === 'CUSTOMER'}\
      <li class = "m-1 "><a href="/customerHomePage" class = "flex items-center space-x-2 p-0.2"> <IconHome /> Home </a></li>
      <li class = "m-1 "><a href="/courseOfferings" class = "flex items-center space-x-2 p-0.2"> <IconSchool /> Course Offerings </a></li>
      <li class = "m-1 "><a href="/courseHistory" class = "flex items-center space-x-2 p-0.2"> <IconHistory /> Course History </a></li>
      <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconTicket /> Registrations </a></li>
      <li class="spacer bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li> 
      <li class = "m-1 "><a href="/AccountSettings" class = "flex items-center space-x-2 p-0.2"> <IconSettings /> Account Settings </a></li>
      <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2" on:click={logout}> <IconLogout /> Logout </a></li>

    {:else if userType === "OWNER"}
      <li class = "m-1 "><a href="/" class = "flex items-center space-x-2 p-0.2"> <IconHome /> Home </a></li>
      <li class = "m-1 "><a href="/courseOfferings" class = "flex items-center space-x-2 p-0.2"> <IconSchool /> Course Offerings </a></li>
      <li class="m-1"><a href="/CourseTypeApproval" class="flex items-center space-x-2 p-0.2"> <IconBallFootball /> Course Types </a></li>
      <li class="m-1"><a href="/ManageUsers" class="flex items-center space-x-2 p-0.2"> <IconUsers /> Manage Users </a></li>
      <li class="m-1"><a href="/ViewRooms" class="flex items-center space-x-2 p-0.2"> <IconDoor /> Rooms </a></li>
      <li class="h-3 spacer3 bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="h-2/5 spacer3 bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li> 
      <li class = "m-1 "><a href="/AccountSettings" class = "flex items-center space-x-2 p-0.2"> <IconSettings /> Account Settings </a></li>
      <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2" on:click={logout}> <IconLogout /> Logout </a></li>

    {:else if userType === "INSTRUCTOR"}
      <li class = "m-1 "><a href="/" class = "flex items-center space-x-2 p-0.2"> <IconHome /> Home </a></li>
      <li class = "m-1 "><a href="/courseOfferings" class = "flex items-center space-x-2 p-0.2"> <IconSchool /> Course Offerings </a></li>
      <li class = "m-1 "><a href="/courseHistory" class = "flex items-center space-x-2 p-0.2"> <IconHistory /> Course History </a></li>
      <li class="m-1"><a href="/CourseTypeSubmission" class="flex items-center space-x-2 p-0.2"> <IconBallFootball /> Course Types </a></li>
      <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2"> <IconTicket /> Registrations </a></li>
      <li class="m-1"><a class="flex items-center space-x-2 p-0.2"> <IconUsers /> Manage Customers </a></li>
      <li class="h-2 spacer3 bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="h-2/5 spacer3 bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li> 
      <li class = "m-1 "><a href="/AccountSettings" class = "flex items-center space-x-2 p-0.2"> <IconSettings /> Account Settings </a></li>
      <li class = "m-1 "><a class = "flex items-center space-x-2 p-0.2" on:click={logout}> <IconLogout /> Logout </a></li>

    {:else}
      <li class = "m-1 "><a href="/guestCourseOfferings" class = "flex items-center space-x-2 p-0.2"> <IconSchool /> Course Offerings </a></li>
      <li class="h-3/4 spacer3 bg-base-200"></li> <!-- This spacer will now push the settings and logout to the bottom -->
      <li class="h-0.5 -ml-px w-full spacer2 bg-base-400 justify-center"></li> 
      <li class = "m-1 "><a href="/login" class = "flex items-center space-x-2 p-0.2"> <IconLogin /> Login </a></li>
    {/if}
  </ul>
</nav>
  <div class="content">
    <slot/>
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
    margin-top: 1%;
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
