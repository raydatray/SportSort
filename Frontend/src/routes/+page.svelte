<script>
    import { onMount } from 'svelte';
    import axios from 'axios';

    const backendUrl = 'http://127.0.0.1:8080/';
    let courseSessions = []; // Reactive variable to hold the fetched data
    let errorPerson;

    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });

    onMount(() => {
        AXIOS.get('/courseSessions/getAll', {
            headers: {
                'userToken' : 'oogabooga'
            }
        })
            .then(response => {
                courseSessions = response.data;
            })
            .catch(e => {
                errorPerson = e;
            });
    });
</script>

<div class="main bg-secondary">
    {#if courseSessions.length > 0}
        <h1>Welcome to SvelteKit</h1>
        {#each courseSessions as session}
            <p>{session}</p> <!-- Replace 'name' with the actual property you want to display from the session object -->
        {/each}
    {:else}
        <h1>Loading or No Data Available</h1>
    {/if}
    {#if errorPerson}
        <p>Error: {errorPerson.message}</p>
    {/if}
</div>

<style>
    .main {
        border-radius: 10px;
        height: 100%;
        margin-bottom: 0.75%;
        padding: 1%;
        right: 0;
    }
</style>