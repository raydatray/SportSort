<script>
  import { onMount } from 'svelte';
  import axios from 'axios';
  import { IconMail } from "@tabler/icons-svelte";

  // Reactive variables for the current user
  let currentUser = {
      name: '',
      email: '',
  };

  let error; // To hold any errors during fetching
  let modalOpen = false;

  // Temporary state to store changes while editing
  let tempEmail = currentUser.email;

  const AXIOS = axios.create({
      baseURL: 'http://127.0.0.1:8080', // Adjust this to your actual backend URL
      headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
  });

  onMount(() => {
      const loggedInToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob3VtYW4iLCJpYXQiOjE3MTI3NzQ4OTIsImV4cCI6MTcxMjgxODA5Mn0._rOD3ythpSAlmuBg1Cx4h2xS8SfuAObzHm44QZFjO3o";
      AXIOS.get('/accounts/getAll', {
          headers: {
              'userToken': 'asdf' // Adjusted to use the stored token
          }
      })
      .then(response => {
          const users = response.data;
          // Assuming users include a property to match with the token, which is not typical for security reasons
          const loggedInUser = users.find(user => user.token === loggedInToken);
          console.log(loggedInToken)
          console.log(loggedInUser)
          if (loggedInUser) {
              currentUser.name = loggedInUser.name;
              currentUser.email = loggedInUser.email;
          }
      })
      .catch(e => {
          error = e.message;
      });
  });
  
  console.log(currentUser)
  // Function to open the modal
  function openModal() {
      modalOpen = true;
      // Set temporary state to current values
      tempEmail = currentUser.email;
  }

  // Function to close the modal without saving
  function closeModal() {
      modalOpen = false;
  }

  // Function to save changes and close the modal
  function saveChanges() {
      // Save temporary state back to permanent state
      currentUser.email = tempEmail;
      // Close modal
      closeModal();
  }

  // Function to handle account deletion
  function deleteAccount() {
      console.log('Account deletion requested.');
      // Placeholder for actual deletion logic
  }
</script>

<!-- Account Settings Display -->
<div class="page-container">
  <h1 class="section-title">Account Settings</h1>
  {#if currentUser}
    <div class="account-detail">Name: {currentUser.name}</div>
    <div class="account-detail">Email: {currentUser.email}</div>
  {:else}
    <div class="account-detail">Name: Loading...</div>
    <div class="account-detail">Email: Loading...</div>
  {/if}
  
  <button class="btn btn-custom" on:click={openModal}>Edit Account Details</button>
  <button class="btn btn-delete" on:click={deleteAccount}>Delete Account</button>
</div>

<!-- Modal Component for Editing Email -->
{#if modalOpen}
<dialog open class="modal">
  <div class="modal-box"> 
      <h3 class="font-bold text-lg">Edit Account Details</h3>
      <label class="input input-bordered flex items-center gap-2 mb-4">
          <IconMail/>
          <input type="email" bind:value={tempEmail} class="grow" placeholder="Email" />
      </label>
      <div class="modal-action">
          <button class="btn btn-custom" on:click={saveChanges}>Save</button>
          <button class="btn btn-custom" on:click={closeModal}>Close</button>
      </div>
  </div>
</dialog>
{/if}


  <style>
    .page-container {
      max-width: 90vw; /* Percentage of the viewport's width */
      margin: 2rem auto; /* Top and bottom margin with auto horizontal */
      padding: 2rem;
    }
  
    .section-title {
      font-size: 1.5rem;
      color: #333;
      margin-bottom: 1rem;
    }
  
    .account-detail {
      margin-bottom: 0.5rem;
      font-size: 1rem;
      color: #555;
    }
  
    .input-label {
      margin-bottom: 1rem;
    }
  
    .btn-custom {
      background-color: #5c6ac4;
      color: white;
      padding: 0.5rem 1rem;
      border: none;
      cursor: pointer;
      margin-top: 1rem;
    }
  
    .btn-custom:hover {
      background-color: #7886d7;
    }
  
    .modal-box {
      width: 80%; /* Dynamic width based on the parent container */
      min-width: 300px; /* Minimum width to ensure usability */
      max-width: 90vw; /* Maximum width as a percentage of the viewport width */
      margin: auto; /* Center the modal */
      padding: 2rem;
      box-sizing: border-box; /* Include padding in the width calculation */
    }
  
    .modal-action {
      margin-top: 0.5rem;
    }
  
    /* Responsive adjustments */
    @media (min-width: 768px) {
      .modal-box {
        width: 50%; /* Larger screens can have a smaller percentage width */
        max-width: 600px; /* Adjust maximum width as appropriate */
      }
    }

    .btn-delete {
        background-color: #ff4b55; /* Red color for delete button */
        color: white;
        padding: 0.5rem 1rem;
        border: none;
        cursor: pointer;
    }

    .btn-delete:hover {
        background-color: #ff6b75; /* Lighter red on hover */
    }
  </style>