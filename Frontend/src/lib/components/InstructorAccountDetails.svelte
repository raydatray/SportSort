<script>
  // Imports
  import { IconEye } from "@tabler/icons-svelte";

  // Reactive variables
  let name = 'Whatsup Doe';
  let email = 'john.doe@example.com';
  let password = 'hashed_password';
  let passwordVisible = false;
  let modalOpen = false;

  // Temporary state to store changes while editing
  let tempPassword = password;

  // Function to toggle password visibility
  function togglePassword() {
    passwordVisible = !passwordVisible;
  }

  // Function to open the modal
  function openModal() {
    modalOpen = true;
    // Set temporary state to current values
    tempPassword = password;
  }

  // Function to close the modal without saving
  function closeModal() {
    modalOpen = false;
  }

  // Function to save changes and close the modal
  function saveChanges() {
    // Save temporary state back to permanent state
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

<!-- Modal Component for Password Change -->
{#if modalOpen}
<dialog open class="modal">
  <div class="modal-box"> 
    <h3 class="font-bold text-lg">Edit Account Password</h3>
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
</style>