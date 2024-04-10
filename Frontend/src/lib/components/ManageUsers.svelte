<!-- HTML for the "Create Instructor" button and the table -->
<div style="align-self: flex-start; margin-bottom: 10px;">
  <button class="btn" on:click={openModal}>Create Instructor</button>
</div>
<div class="overflow-x-auto">
  <table class="table">
    <thead>
      <tr>
        <th></th>
        <th>User Type</th>
        <th>Name</th>
        <th>Email</th>
      </tr>
    </thead>
    <tbody>
      {#each instructors as instructor, index}
        <tr class="hover">
          <th>{index + 1}</th>
          <td>{instructor.userType}</td>
          <td>{instructor.name}</td>
          <td>{instructor.email}</td>
          <td class="actions">
            <button 
              class="btn-small update-btn"
              on:click={() => openUpdateModal(index)}
              disabled={instructor.userType !== 'INSTRUCTOR'}
              class:disabled={instructor.userType !== 'INSTRUCTOR'}
            >
              Update
            </button>
            {#if instructor.userType !== 'OWNER'}
              <button class="btn-small delete-btn" on:click={() => deleteInstructor(instructor.email)}>Delete</button>
            {/if}
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
</div>


<!-- Modal for Creating a New Instructor -->
{#if modalOpen}
<dialog open class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">Create New Instructor</h3>
    <label class="input input-bordered flex items-center gap-2 mb-4">
      Name:
      <input type="text" bind:value={newName} class="grow" placeholder="Name" />
    </label>
    <label class="input input-bordered flex items-center gap-2 mb-4">
      Email:
      <input type="email" bind:value={newEmail} class="grow" placeholder="Email" />
    </label>
    <label class="input input-bordered flex items-center gap-2">
      Password:
      {#if passwordVisible}
        <input type="text" bind:value={newPassword} class="grow" placeholder="Password" />
      {:else}
        <input type="password" bind:value={newPassword} class="grow" placeholder="Password" />
      {/if}
      <button on:click={togglePassword} class="btn-neutral">
        <IconEye color="black"/>
      </button>
    </label>
    <div class="modal-action">
      <button class="btn btn-custom" on:click={createInstructor}>Create</button>
      <button class="btn btn-custom" on:click={closeModal}>Close</button>
    </div>
  </div>
</dialog>
{/if}

<!-- New Modal for Updating an Instructor's Email -->
{#if updateModalOpen}
<dialog open class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">Update Instructor Details</h3>
    <label class="input input-bordered flex items-center gap-2 mb-4">
      New Name:
      <input type="text" bind:value={updatedName} class="grow" placeholder="Enter new name" />
    </label>
    <label class="input input-bordered flex items-center gap-2 mb-4">
      New Email:
      <input type="email" bind:value={updatedEmail} class="grow" placeholder="Enter new email" />
    </label>
    <label class="input input-bordered flex items-center gap-2">
      New Password:
      {#if passwordVisible}
        <input type="text" bind:value={updatedPassword} class="grow" placeholder="Enter new password" />
      {:else}
        <input type="password" bind:value={updatedPassword} class="grow" placeholder="Enter new password" />
      {/if}
      <button on:click={togglePassword} class="btn-neutral">
        <IconEye color="black"/>
      </button>
    </label>
    <div class="modal-action">
      <button class="btn btn-custom" on:click={saveUpdatedDetails}>Save</button>
      <button class="btn btn-custom" on:click={closeUpdateModal}>Close</button>
    </div>
  </div>
</dialog>
{/if}



<script>
  import { onMount } from 'svelte';
  import axios from 'axios';
  import { IconEye } from "@tabler/icons-svelte";

  let modalOpen = false;
  // Related to creating
  let newName = '';
  let newEmail = '';
  let newPassword = '';
  let passwordVisible = false; // Reactive variable for password visibility

  // Related to updating 
  let updatedName = ''; // New variable for the updated name
  let updatedPassword = ''; // New variable for the updated password

  let updateModalOpen = false;
  let updatedEmail = ''; // To hold the new email value for updating
  let currentInstructorIndex = null; // To identify which instructor is being updated

  // Replace your example instructors array with dynamic fetching
  let instructors = []; // Now dynamically fetched from the backend
  let error; // To hold any errors during fetching

  const AXIOS = axios.create({
    baseURL: 'http://127.0.0.1:8080', // Adjust this to your actual backend URL
    headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });

  onMount(() => {
    AXIOS.get('/accounts/getAll', {
      headers: {
        'userToken': 'asdf' // You'll need to adjust how you handle authentication
      }
    })
    .then(response => {
      instructors = response.data.map(userAccount => ({
        id: userAccount.id, // Adjust these properties based on your UserAccountDTO
        name: userAccount.name,
        userType: userAccount.type,
        email: userAccount.email,
      }));
      console.log(instructors)
    })
    .catch(e => {
      error = e.message;
    });
  });

  // Existing functions for creating an instructor
  function openModal() {
    modalOpen = true;
    newName = '';
    newEmail = '';
    newPassword = '';
    passwordVisible = false;
  }

  function closeModal() {
    modalOpen = false;
  }

  function createInstructor() {
  const userToken = 'asdf'; // Replace with your actual token retrieval logic

  const newInstructorDetails = {
    name: newName,
    email: newEmail,
    password: newPassword, // Ensure you're handling passwords securely!
  };

  AXIOS.post('/accounts/createInstructor', newInstructorDetails, {
    headers: {
      'Content-Type': 'application/json',
      'userToken': userToken, // Include the user token in the request header
    }
  })
  .then(response => {
    console.log(response.data);
    // Optionally, update your local state to include the new instructor
    // instructors = [...instructors, newInstructorDetails];
    closeModal(); // Close the modal after successful creation
    // Clear the form fields
    newName = '';
    newEmail = '';
    newPassword = '';
  })
  .catch(error => {
    console.error('Error creating instructor account:', error);
    // Handle error, show user feedback
  });
}

  function togglePassword() {
    passwordVisible = !passwordVisible;
  }

  // New functions for updating an instructor's email
  function openUpdateModal(index) {
    updateModalOpen = true;
    currentInstructorIndex = index;
    let instructor = instructors[index];
    updatedName = instructor.name;
    updatedEmail = instructor.email;
    updatedPassword = ''; // Assume the actual password isn't available or should be entered anew
    passwordVisible = false; // Ensure the password field starts hidden
  }

  function closeUpdateModal() {
    updateModalOpen = false;
  }

  function saveUpdatedDetails() {
  if (currentInstructorIndex !== null) {
    const userToken = 'asdf'; // Replace this with your actual token retrieval method

    const updatedDetails = {
      name: updatedName,
      email: updatedEmail,
      password: updatedPassword, // Ensure you're handling passwords securely!
    };

    AXIOS.put('/accounts/update', updatedDetails, {
      headers: {
        'Content-Type': 'application/json',
        'userToken': userToken, // Authentication token
      }
    })
    .then(response => {
      console.log(response.data);
      // Update the local instructors array to reflect the change
      let instructor = instructors[currentInstructorIndex];
      instructor.name = updatedName;
      instructor.email = updatedEmail;
      // Don't store the password in the local state for security reasons
      // Trigger a UI update
      instructors = [...instructors];

      closeUpdateModal();
    })
    .catch(error => {
      console.error('Error updating the account:', error);
      // Handle error, possibly updating UI to show the error message
    });
  }
}


  function deleteInstructor(email) {
    const userToken = 'asdf'; // Adjust this to your actual user token management

    AXIOS.delete('/accounts/delete', {
      headers: {
        'userToken': userToken
      },
      params: {
        email: email
      }
    })
    .then(response => {
      console.log(response.data);
      // Update the instructors array to reflect the deletion
      instructors = instructors.filter(instructor => instructor.email !== email);
    })
    .catch(error => {
      console.error('Error deleting the account:', error);
      // Update the UI or state to reflect any error here
    });
  }

</script>


<style>
  .btn-small.disabled {
    background-color: transparent; /* Make the button transparent */
    color: transparent; /* Make the text transparent */
    cursor: default; /* Reset the cursor to default instead of pointer */
    border: none; /* Remove the border if there is one */
    box-shadow: none; /* Remove any shadow effects */
    pointer-events: none; /* Prevents the button from being clickable */
    opacity: 0; /* Make the button fully transparent */
  }

  .actions {
    display: flex;
    justify-content: flex-start; /* Align content to the left */
    align-items: left; /* Vertically center the content */
    gap: 10px; /* Maintain some space between buttons */
  }

  .btn-small {
    padding: 5px 10px; /* Smaller padding for a compact look */
    margin: 0; /* Removed margin to align buttons correctly */
    font-size: 0.8rem; /* Smaller font size for text within the button */
    cursor: pointer; /* Cursor indicates clickable button */
    transition: background-color 0.3s; /* Smooth transition for background color */
  }

  /* Only apply hover styles to buttons that are not disabled */
  .update-btn:not(.disabled):hover {
    background-color: #66bb6a; /* Lighter green on hover */
  }

  .delete-btn:hover {
    background-color: #ef5350; /* Lighter red on hover */
  }

  .update-btn {
    background-color: #4CAF50; /* Green background for update */
    color: white; /* White text */
  }

  .delete-btn {
    background-color: #f44336; /* Red background for delete */
    color: white; /* White text */
  }
</style>





