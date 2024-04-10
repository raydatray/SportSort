<script>
    import { onMount } from 'svelte';
    import axios from 'axios';

    const frontendUrl = 'http://localhost:5173';
    const backendUrl = 'http://127.0.0.1:8080';
    let sportCenter;
    let users = [];
    let errorPerson;

    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': frontendUrl }
    });

    onMount(() => {
        AXIOS.get('/getSportCenter')
        .then(response => {
            sportCenter = response.data;
            localStorage.setItem('sportCenter', JSON.stringify(sportCenter));
        })
        .catch(e => {
            errorPerson = e;
        });
        AXIOS.get('/accounts/getAll', {
            headers: {
                'userToken': 'asdf'
            }
        })
        .then(response => {
            // Filter the response to include only users with type 'INSTRUCTOR'
            users = response.data.filter(user => user.type === 'INSTRUCTOR');
            console.log(users);
        })
        .catch(e => {
            errorPerson = e;
        });
    });
</script>

<div class="font-extrabold header">
    {#if sportCenter}
        <h1 class="welcome-text">Welcome To {sportCenter.name}</h1>
    {:else}
        <h1 class="welcome-text">Loading Sport Center...</h1>
    {/if}
</div>
<div class="header2">
    <p class="text-xs welcome-text">Working Out Just Got Better</p>
</div>

<div class="h-0.5 m-4 spacer bg-base-300" />

<div class="dashboard">
    <div class="rounded-lg shadow-lg stat">
        <div class="stat-title">Total Page Views</div>
        <div class="stat-value">89,400</div>
        <div class="stat-desc">21% more than last month</div>
    </div>
  
    <div class="card agenda">
      <!-- Content for Agenda -->
      <h2>Agenda (120 sessions)</h2>
      <!-- Agenda items here -->
      <button>View</button>
    </div>
  
    <div class="overflow-x-auto">
        <table class="table">
            <!-- head -->
            <thead>
            <tr>
                <th></th>
                <th>Name</th>
                <th>Job</th>
                <th>Favorite Color</th>
            </tr>
            </thead>
            <tbody>
            <!-- row 1 -->
            <tr>
                <th>1</th>
                <td>Cy Ganderton</td>
                <td>Quality Control Specialist</td>
                <td>Blue</td>
            </tr>
            <!-- row 2 -->
            <tr class="hover">
                <th>2</th>
                <td>Hart Hagerty</td>
                <td>Desktop Support Technician</td>
                <td>Purple</td>
            </tr>
            <!-- row 3 -->
            <tr>
                <th>3</th>
                <td>Brice Swyre</td>
                <td>Tax Accountant</td>
                <td>Red</td>
            </tr>
            </tbody>
        </table>
    </div>
  
    <div class="card sponsors">
      <!-- Content for Sponsors -->
      <h2>Sponsors</h2>
      <!-- List of sponsors here -->
      <button>View</button>
    </div>
  </div>
  
  <style>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 8px;
        padding: 0 20px; /* Aligns with the grid padding */
    }

    .header2 {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px; /* Spacing between the header and the grid */
        padding: 0 20px; /* Aligns with the grid padding */
    }

    .welcome-text {
        font-size: 24px; /* Or any other size you prefer */
        color: #333;
        margin: 0; /* Removes default margin from the h1 tag */
    }

    .welcome-gif {
        max-height: 100px; /* Set a max-height for your gif to make sure it's not too large */
        margin-left: 20px; /* Adds some space between the text and the gif */
    }
    
    .dashboard {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 20px;
      padding: 20px;
    }
    
    .card {
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
  
    button {
      background-color: #007bff;
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 5px;
      cursor: pointer;
      margin-top: 15px;
    }
  
    button:hover {
      background-color: #0056b3;
    }
  
    /* Add additional styling as needed */
  </style>