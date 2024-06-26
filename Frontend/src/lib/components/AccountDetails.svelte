<script>
  import { onMount } from 'svelte';
  import axios from 'axios';
  import { IconEye, IconMail } from "@tabler/icons-svelte";

  // Reactive variables for the current user
  let currentUser = {
      name: '',
      email: '',
  };

  let passwordVisible = false; // Reactive variable for password visibility
  // Related to updating 
  let tempPassword = ''; // New variable for the updated password

  let error; // To hold any errors during fetching
  let modalOpen = false;

  // Temporary state to store changes while editing
  let tempName = currentUser.name;
  let tempEmail = currentUser.email;

  const AXIOS = axios.create({
      baseURL: 'http://127.0.0.1:8080', // Adjust this to your actual backend URL
      headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
  });

  onMount(() => {
      const loggedInToken =  sessionStorage.getItem('token');
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
  }

  // Function to close the modal without saving
  function closeModal() {
      modalOpen = false;
  }

  // Function to save changes and close the modal
  function saveChanges() {
  // Prepare the request body with the new user details
  const userUpdateDetails = {
    name: tempName,
    email: tempEmail,
    password: tempPassword,
  };

  // The userToken should be retrieved or stored somewhere accessible
  // This is just a placeholder; you need to replace it with the actual token
  const userToken = sessionStorage.getItem('token');


  // Send the PUT request to the backend
  AXIOS.put('/accounts/update', userUpdateDetails, {
    headers: {
      'userToken': userToken, // Include the token in the request headers
    }
  })
  .then(response => {
    // Handle success
    console.log('Account updated successfully:', response.data);
    // Update the currentUser object with the new details
    currentUser.name = tempName;
    currentUser.email = tempEmail;
    // Close the modal
    closeModal();
  })
  .catch(error => {
    // Handle error
    console.error('Error updating account:', error.response ? error.response.data : error.message);
    // Optionally update the UI to show the error
  });
}

  function togglePassword() {
    passwordVisible = !passwordVisible;
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


<!-- Modal Component -->
{#if modalOpen}
<dialog open class="modal">
  <div class="modal-box"> 
    <h3 class="font-bold text-lg">Edit Account Details</h3>
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <input type="email" bind:value={tempName} class="grow" placeholder={currentUser.name} />
    </label>
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <input type="email" bind:value={tempEmail} class="grow" placeholder={currentUser.email} />
    </label>
    <label class="input input-bordered flex items-center gap-2">
      {#if passwordVisible}
        <input type="text" bind:value={tempPassword} class="grow" placeholder="Password" />
      {:else}
        <input type="password" bind:value={tempPassword} class="grow" placeholder="Password" />
      {/if}
      <button on:click={togglePassword} class="btn-neutral"><IconEye color = "black"/></button>
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