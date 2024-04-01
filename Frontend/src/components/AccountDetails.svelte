<script>
    import { IconEye } from "@tabler/icons-svelte"

    let name = 'John Doe';
    let email = 'john.doe@example.com';
    let password = 'hashed_password';
    let passwordVisible = false;
    let modalOpen = false;
  
    function togglePassword() {
      passwordVisible = !passwordVisible;
    }
  
    function openModal() {
      modalOpen = true;
    }
  
    function closeModal() {
      modalOpen = false;
    }
  
    function saveChanges() {
      // Implement your saving logic here
      closeModal();
    }
  </script>
  
<!-- Account Settings Display -->
<div>
    <h1>Account Settings</h1>
    <p>Name: {name}</p>
    <p>Email: {email}</p>
    <label class="input input-bordered flex items-center gap-2 w-full max-w-xs">
        {#if passwordVisible}
        <input type="text" value={password} class="grow" readonly />
      {:else}
        <input type="password" value="hashed_password" class="grow bg-300" readonly />
      {/if}
      <button on:click={togglePassword} class="btn-neutral"><IconEye color="black"/></button>
    </label>
    <button class="btn" on:click={openModal}>Edit Account Details</button>
  </div>
  
  <!-- Modal Component -->
  {#if modalOpen}
  <dialog open class="modal">
    <div class="modal-box w-11/12 max-w-5xl">
      <h3 class="font-bold text-lg">Edit Account Details</h3>
      <label class="input input-bordered flex items-center gap-2 w-full max-w-xs">
        <!-- User Icon for Username -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><!-- Path for user icon --></svg>
        <input type="text" bind:value={name} class="grow" placeholder="Name" />
      </label>
      <label class="input input-bordered flex items-center gap-2 w-full max-w-xs">
        <!-- Email Icon for Email -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><!-- Path for email icon --></svg>
        <input type="email" bind:value={email} class="grow" placeholder="Email" />
      </label>
      <label class="input input-bordered flex items-center gap-2 w-full max-w-xs">
        <!-- Lock Icon for Password -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="w-4 h-4 opacity-70"><!-- Path for lock icon --></svg>
        {#if passwordVisible}
          <input type="text" bind:value={password} class="grow" placeholder="Password" />
        {:else}
          <input type="password" bind:value={password} class="grow" placeholder="Password" />
        {/if}
        <button on:click={togglePassword} class="btn-neutral"><IconEye color="black"/></button>
      </label>
      <div class="modal-action">
        <button class="btn" on:click={saveChanges}>Save</button>
        <button class="btn" on:click={closeModal}>Close</button>
      </div>
    </div>
  </dialog>
{/if}
  