<script>
  import { onMount } from 'svelte';
  import axios from 'axios';
  import { IconEye} from "@tabler/icons-svelte";
  import { fade } from 'svelte/transition';
  import { IconPencil, IconTrash } from '@tabler/icons-svelte';

  // Reactive variables for the current user
  let currentUser = {
      name: '',
      email: '',
  };

  let userRole = '';

  let passwordVisible = false; // Reactive variable for password visibility
  // Related to updating 
  let tempPassword = ''; // New variable for the updated password

  let error; // To hold any errors during fetching
  let modalOpen = false;

  // Related to error alert
  let alertMessage = ''; // Holds the success or error message
  let showAlert = false; // Controls the visibility of the alert

  // Temporary state to store changes while editing
  let tempName = currentUser.name;
  let tempEmail = currentUser.email;

  // Define reactive variables and imports
  let holdTimer;
  let holdProgress = 0; // Progress of the button hold from 0 to 100

  // Define the duration to hold the button for deletion
  const holdDuration = 2000; // 2000 milliseconds (2 seconds)

  const AXIOS = axios.create({
      baseURL: 'http://127.0.0.1:8080', // Adjust this to your actual backend URL
      headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
  });

  // Function to open the modal
  function openModal() {
      modalOpen = true;
      // Set temporary state to current values
  }

  // Function to close the modal without saving
  function closeModal() {
      modalOpen = false;
  }

  // Error alert
  function displayAlert(message) {
        alertMessage = message;
        showAlert = true;
        setTimeout(() => {
            showAlert = false;
        }, 2000);
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
    const message = error.response?.data || "An error occurred while creating the instructor.";
    displayAlert(message);
  });
}

  function togglePassword() {
    passwordVisible = !passwordVisible;
  }

  function startHold() {
    holdProgress = 0;
    holdTimer = setInterval(() => {
      holdProgress += 10; // Increase progress
      if (holdProgress >= 100) {
        completeHold();
      }
    }, holdDuration / 10);
  }

  function endHold() {
    clearInterval(holdTimer);
    holdProgress = 0;
  }

  function completeHold() {
    endHold();
    deleteAccount(); // Call your delete account function
  }

  // Function to handle account deletion
  // Function to handle account deletion
  function deleteAccount() {
    const userToken = sessionStorage.getItem('token');
    const userEmail = currentUser.email;

    AXIOS.delete('/accounts/delete', {
      headers: {
        'userToken': userToken
      },
      params: {
        email: userEmail
      }
    })
    .then(response => {
      console.log('Account deleted successfully:', response.data);
      sessionStorage.removeItem('role'); // Handle session clearance and redirection as needed
      sessionStorage.removeItem('token');
      const newURL = window.location.href.replace(window.location.pathname, '/');
      history.replaceState({}, document.title, newURL);
      window.location.reload();
    })
    .catch(error => {
      console.error('Error deleting account:', error.response ? error.response.data : error.message);
      // Optionally update the UI to show the error
    });
  } 

  let paymentModalOpen = false;
  let cardNumber = '';
  let cvv = '';
  let expirationYear = '';
  let expirationMonth = '';

  let paymentInfos = []; // This will hold the payment information data
  let paymentTypes = ['Credit', 'Debit']; // Add payment types array
  let paymentType = 'Credit'; // Set a default payment type


  const months = [{value: 1, name: "January"}, 
  {value: 2, name: "February"},
  {value: 3, name: "March"},
  {value: 4, name: "April"},
  {value: 5, name: "May"},
  {value: 6, name: "June"},
  {value: 7, name: "July"},
  {value: 1, name: "August"},
  {value: 1, name: "September"},
  {value: 1, name: "November"},
  {value: 12, name: "December"}]; // Define all months
  const years = Array.from({length: 10}, (_, i) => new Date().getFullYear() + i); // Next 10 years

  function openPaymentModal() {
    paymentModalOpen = true;
  }

  function closePaymentModal() {
    paymentModalOpen = false;
  }

  function savePaymentInfo() {
    const userToken = sessionStorage.getItem('token');
    const paymentInfo = {
      paymentType: paymentType, // Assume a default or provide a way to select this
      cardNumber,
      cvv: parseInt(cvv),
      expirationYear: parseInt(expirationYear),
      expirationMonth: parseInt(expirationMonth),
    };

    AXIOS.post('/paymentInfo/create', paymentInfo, {
      headers: { 'userToken': userToken }
    })
    .then(response => {
      console.log('Payment info added successfully:', response.data);
      closePaymentModal();
    })
    .catch(error => {
      console.error('Error adding payment info:', error.response ? error.response.data : error.message);
    });
  }

  onMount(() => {
    const loggedInToken = sessionStorage.getItem('token');
    userRole = sessionStorage.getItem('role');

    // Fetch the user account information
    AXIOS.get('/accounts/getAccount', {
        headers: {
            'userToken': loggedInToken // Use the stored token
        }
    })
    .then(response => {
        const user = response.data;
        if (user) {
            currentUser.name = user.name;
            currentUser.email = user.email;
        }
        // Fetch payment info only if the user is a customer
        if (userRole === 'CUSTOMER') {
            return AXIOS.get('/paymentInfo/getAll', {
                headers: { 'userToken': loggedInToken }
            });
        }
    })
    .then(response => {
        // This block will only be executed if the user is a customer
        if (response && response.data) {
            paymentInfos = response.data.map(info => ({
                ...info,
                isDeleting: false,
                deleteProgress: 0
            })); // Add deletion state properties
        }
    })
    .catch(e => {
        error = e.message;
    });
});


// Additional reactive variables for updating payment info
let updatePaymentModalOpen = false;
  let selectedPaymentInfoId = null;
  let selectedPaymentInfoExpirationYear = '';
  let selectedPaymentInfoExpirationMonth = '';

  // Function to open the update payment info modal
  function openUpdatePaymentModal(paymentInfo) {
    selectedPaymentInfoId = paymentInfo.id;
    selectedPaymentInfoExpirationYear = paymentInfo.expirationYear;
    selectedPaymentInfoExpirationMonth = paymentInfo.expirationMonth;
    updatePaymentModalOpen = true;
  }

  // Function to close the update payment info modal
  function closeUpdatePaymentModal() {
    updatePaymentModalOpen = false;
  }

  // Function to handle the update of payment info
  function updatePaymentInfo() {
    const userToken = sessionStorage.getItem('token');
    const paymentInfoUpdate = {
      id: selectedPaymentInfoId,
      expirationYear: parseInt(selectedPaymentInfoExpirationYear, 10),
      expirationMonth: parseInt(selectedPaymentInfoExpirationMonth, 10),
    };

    AXIOS.put('/paymentInfo/update', paymentInfoUpdate, {
      headers: { 'userToken': userToken }
    })
    .then(response => {
      console.log('Payment info updated successfully:', response.data);
      // Refresh the payment infos to reflect the update
      closeUpdatePaymentModal();
    })
    .catch(error => {
      console.error('Error updating payment info:', error.response ? error.response.data : error.message);
    });
  }

  function deletePaymentInfo(paymentInfoId) {
  const userToken = sessionStorage.getItem('token');

  AXIOS.delete(`/paymentInfo/delete?id=${paymentInfoId}`, {
    headers: {
      'userToken': userToken
    }
  })
  .then(response => {
    console.log('Payment info deleted successfully:', response.data);
    // Remove the deleted payment info from the list
    paymentInfos = paymentInfos.filter(info => info.id !== paymentInfoId);
  })
  .catch(error => {
    console.error('Error deleting payment info:', error.response ? error.response.data : error.message);
  });
}

  let activeDeleteTimer;

  function startDelete(paymentInfo) {
    paymentInfo.isDeleting = true;
    paymentInfo.deleteProgress = 0;
    activeDeleteTimer = setInterval(() => {
      if (paymentInfo.deleteProgress < 100) {
        paymentInfo.deleteProgress += 5; // Increment progress
        paymentInfos = paymentInfos.slice(); // Trigger reactivity
      } else {
        completeDelete(paymentInfo);
      }
    }, holdDuration / 20); // Update interval
  }

  function endDelete(paymentInfo) {
    clearInterval(activeDeleteTimer);
    paymentInfo.isDeleting = false;
    paymentInfo.deleteProgress = 0;
    paymentInfos = paymentInfos.slice(); // Reset progress and trigger reactivity
  }

  function completeDelete(paymentInfo) {
    endDelete(paymentInfo);
    deletePaymentInfo(paymentInfo.id);
  }


</script>

<!-- Account Settings Display -->
<div class="page-container">
  <h1 class="section-title">Account Settings</h1>
  {#if currentUser}
  <div class="account-detail">Account Type: {userRole}</div>
    <div class="account-detail">Name: {currentUser.name}</div>
    <div class="account-detail">Email: {currentUser.email}</div>
  {:else}
    <div class="account-detail">Name: Loading...</div>
    <div class="account-detail">Email: Loading...</div>
  {/if}
  
  <div class="button-container">
    <button class="btn btn-custom" on:click={openModal}>Edit Account Details</button>

    {#if userRole !== 'OWNER' && userRole !== 'INSTRUCTOR'}
      <button class="btn btn-delete" on:mousedown={startHold} on:mouseup={endHold} on:mouseleave={endHold} style="--progress: {holdProgress}%;">
        Hold to Delete
      </button>
    {/if}
  </div>  
</div>

{#if userRole === 'CUSTOMER'}
<button class="btn btn-custom" on:click={openPaymentModal}>Add Payment Info</button>
{/if}
<!-- Payment info table -->
{#if userRole === 'CUSTOMER'}
  <div class="overflow-x-auto">
    <table class="table w-full">
      <!-- head -->
      <thead>
        <tr>
          <th>Payment Type</th>
          <th>Card Number</th>
          <th>Expiration Date</th>
        </tr>
      </thead>
      <tbody>
        {#each paymentInfos as paymentInfo, index}
          <tr>
            <td>{paymentInfo.paymentType}</td>
            <td>{paymentInfo.cardNumber}</td>
            <td>{paymentInfo.expirationMonth}/{paymentInfo.expirationYear}</td>
            <td>
              <button class="btn btn-action" on:click={() => openUpdatePaymentModal(paymentInfo)}>
                <IconPencil />
              </button>
              <button class="btn btn-action" on:mousedown={() => startDelete(paymentInfo)} on:mouseup={() => endDelete(paymentInfo)} on:mouseleave={() => endDelete(paymentInfo)} style="--progress: {paymentInfo.deleteProgress}%;">
                <IconTrash />
                <div class="progress-bar" style="width: {paymentInfo.deleteProgress}%;"></div>
              </button>
            </td> 
          </tr>
        {/each}
      </tbody>
    </table>
  </div>
{/if}

{#if paymentModalOpen}
<dialog open class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">Add Payment Info</h3>
    <!-- Payment Type dropdown -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <span>Payment Type</span>
      <select bind:value={paymentType}>
        {#each paymentTypes as type}
          <option value={type}>{type}</option>
        {/each}
      </select>
    </label>
    <!-- Card Number input -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <input type="text" bind:value={cardNumber} placeholder="Card Number" />
    </label>
    <!-- CVV input -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <input type="number" bind:value={cvv} placeholder="CVV" />
    </label>
    <!-- Expiration Year dropdown -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <span>Expiration Year</span>
      <select bind:value={expirationYear}>
        {#each years as year}
          <option value={year}>{year}</option>
        {/each}
      </select>
    </label>
    <!-- Expiration Month dropdown -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <span>Expiration Month</span>
      <select bind:value={expirationMonth}>
        {#each months as month}
          <option value={month.value}>{month.name}</option>
        {/each}
      </select>
    </label>
    <div class="modal-action">
      <button class="btn btn-custom" on:click={savePaymentInfo}>Save</button>
      <button class="btn btn-custom" on:click={closePaymentModal}>Close</button>
    </div>
  </div>
</dialog>
{/if}

<!-- Update Payment Info Modal -->
{#if updatePaymentModalOpen}
<dialog open class="modal">
  <div class="modal-box">
    <h3 class="font-bold text-lg">Update Payment Info</h3>
    <!-- Update Expiration Year dropdown -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <span>Expiration Year</span>
      <select bind:value={selectedPaymentInfoExpirationYear}>
        {#each years as year}
          <option value={year}>{year}</option>
        {/each}
      </select>
    </label>
    <!-- Update Expiration Month dropdown -->
    <label class="input input-bordered flex items-center gap-2 mb-4">
      <span>Expiration Month</span>
      <select bind:value={selectedPaymentInfoExpirationMonth}>
        {#each months as month}
          <option value={month.value}>{month.name}</option>
        {/each}
      </select>
    </label>
    <div class="modal-action">
      <button class="btn btn-custom" on:click={updatePaymentInfo}>Update</button>
      <button class="btn btn-custom" on:click={closeUpdatePaymentModal}>Close</button>
    </div>
  </div>
</dialog>
{/if}


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

{#if showAlert}
  <div role="alert" class="alert alert-error modal-alert" in:fade={{ duration: 300 }} out:fade={{ duration: 300 }}>
    <span>{alertMessage}</span>
  </div>
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
      max-width: 200px;
    }
  
    .btn-custom:hover {
      background-color: #7886d7;
    }
  
    .modal-box {
      width: 80%;
      min-width: 300px;
      max-width: 600px; /* You might adjust this as necessary */
      padding: 2rem;
      box-sizing: border-box;
      background-color: white; /* Optional: for visibility */
      box-shadow: 0 4px 6px rgba(0,0,0,0.1); /* Optional: for better visibility */
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
    position: relative;
    overflow: hidden;
    background-color: #ff4b55;  /* Base red color */
    color: white;
    transition: background-color 0.3s;
  }

    .btn-delete::before {
      content: "";
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      width: var(--progress, 0%);
      background-color: rgba(255, 100, 100, 0.5); /* Light red background that fills up */
      transition: width 0.2s linear;
    }

    .modal-alert {
    position: absolute; /* Position it absolutely to align it with the modal */
    width: 50%; /* Make it as wide as the modal */
    left: 50%; /* Center it horizontally */
    transform: translateX(-50%); /* Adjust horizontal position */
    bottom: 20%; /* Adjust vertical position to be below the modal */
    z-index: 100; /* Ensure it's above the modal background */
    box-sizing: border-box;
    padding: 1rem;
  }

  .btn-action {
    position: relative;
    overflow: hidden;
    background-color: transparent;
    border: none;
    padding: 0.5rem;
    cursor: pointer;
  }

  .btn-action .progress-bar {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 2px;
    background-color: red;
    transition: width 0.1s linear;
  }
    
  </style>