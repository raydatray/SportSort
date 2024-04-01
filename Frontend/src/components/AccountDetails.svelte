<script>
    // Imports
    import { IconEye, IconMail } from "@tabler/icons-svelte";
  
    // Reactive variables
    let name = 'John Doe';
    let email = 'john.doe@example.com';
    let password = 'hashed_password';
    let passwordVisible = false;
    let modalOpen = false;
  
    // Temporary state to store changes while editing
    let tempEmail = email;
    let tempPassword = password;
  
    // Function to toggle password visibility
    function togglePassword() {
      passwordVisible = !passwordVisible;
    }
  
    // Function to open the modal
    function openModal() {
      modalOpen = true;
      // Set temporary state to current values
      tempEmail = email;
      tempPassword = password;
    }
  
    // Function to close the modal without saving
    function closeModal() {
      modalOpen = false;
      // Reset temporary state to ensure discarded changes are not shown
      tempEmail = email;
      tempPassword = password;
    }
  
    // Function to save changes and close the modal
    function saveChanges() {
      // Save temporary state back to permanent state
      email = tempEmail;
      password = tempPassword;
      // Close modal
      closeModal();
    }
  </script>
  
  
<!-- Account Settings Display -->
<div class="page-container">
    <h1 class="section-title">Account Settings</h1>
    <div class="account-detail">Name: {name}</div>
    <div class="account-detail">Email: {email}</div>
    <div class="input-label">
      <label class="input input-bordered flex items-center gap-2 w-full">
        {#if passwordVisible}
        <input type="text" value={password} class="grow" readonly />
        {:else}
        <input type="password" value="hashed_password" class="grow bg-300" readonly />
        {/if}
        <button on:click={togglePassword} class="btn-neutral"><IconEye color="black"/></button>
      </label>
    </div>
    <button class="btn btn-custom" on:click={openModal}>Edit Account Details</button>
  </div>
  
  <!-- Modal Component -->
  {#if modalOpen}
  <dialog open class="modal">
    <div class="modal-box"> 
      <h3 class="font-bold text-lg">Edit Account Details</h3>
      <label class="input input-bordered flex items-center gap-2 mb-4">
        <IconMail/>
        <input type="email" bind:value={tempEmail} class="grow" placeholder="Email" />
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
      max-width: 640px; /* Adjust the width as needed */
      margin: 0 auto;
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
      background-color: #5c6ac4; /* Example button color */
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
      margin: auto; /* To make the modal width responsive */
      max-width: 400px;
      padding: 2rem;
    }
  
    .modal-action {
      margin-top: 0.5rem;
    }
  </style>