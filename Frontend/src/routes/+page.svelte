<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    import Hero from '../assets/homepage.png';
    import Discord from '../assets/discord.png';
    import Nike from '../assets/nike.png';
    import Intel from '../assets/int.png';
    import Yonex from '../assets/yonex.png';
    import VSC from '../assets/vsc.png';
    import Postman from '../assets/postman.png';
    import Github from '../assets/github.png';
    import Ratings from '../assets/ratings.png';
    import { IconClock, IconHome, IconPhone, IconEdit } from '@tabler/icons-svelte';


    const frontendUrl = 'http://localhost:5173';
    const backendUrl = 'http://127.0.0.1:8080';
    let sportCenter;
    let instructors = [];
    let courseTypes = [];
    let errorPerson;

    let updateModalOpen = false;
    let updatedStartHour = '';
    let updatedEndHour = '';
    let updatedAddress = '';
    let updatedPhoneNumber = '';
    let alertMessage = ''; // Holds the success or error message
    let showAlert = false; // Controls the visibility of the alert
    let updatedSportCenter = null;

    // Check if sessionStorage is available
    const isSessionStorageAvailable = typeof sessionStorage !== 'undefined';

    // Access sessionStorage if available
    let userType = null;
    let userToken = null;
    if (isSessionStorageAvailable) {
        userType = sessionStorage.getItem('role');
        userToken = sessionStorage.getItem('token');
    }

    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': frontendUrl }
    });

    onMount(() => {
        AXIOS.get('/getSportCenter')
        .then(response => {
            sportCenter = response.data;
        })
        .catch(e => {
            errorPerson = e;
        });
        AXIOS.get('/accounts/getInstructors')
        .then(response => {
            instructors = response.data
            console.log(instructors);
        })
        .catch(e => {
            errorPerson = e;
        });
        AXIOS.get('/courseTypes/getAllApproved')
        .then(response => {
            courseTypes = response.data;
        })
        .catch(e => {
            errorPerson = e;
        });
    });

    function openUpdateModal() {
        updateModalOpen = true;
        updatedStartHour = sportCenter.openingHour;
        updatedEndHour = sportCenter.closingHour;
        updatedAddress = sportCenter.address;
        updatedPhoneNumber = sportCenter.phoneNumber;
    }

    function closeUpdateModal() {
        updateModalOpen = false;
    }

    function saveUpdatedDetails() {
      const updatedDetails = {
        name: sportCenter.name,
        address: updatedAddress,
        phoneNumber: updatedPhoneNumber,
        openingHour: updatedStartHour,
        closingHour: updatedEndHour
      };

      AXIOS.put('/updateSportCenter', updatedDetails, {
          headers: {
              'userToken': userToken
          }
      })
          .then(response => {
              updatedSportCenter = response.data;
              sportCenter.name = updatedSportCenter.name;
              sportCenter.address = updatedSportCenter.address;
              sportCenter.phoneNumber = updatedSportCenter.phoneNumber;
              sportCenter.openingHour = updatedSportCenter.openingHour;
              sportCenter.closingHour = updatedSportCenter.closingHour;
              closeUpdateModal();
          })
          .catch(error => {
              const message = error.response?.data || "An error occurred while updating the sport center.";
              displayAlert(message);
          });

  }

  function displayAlert(message) {
        alertMessage = message;
        showAlert = true;
        setTimeout(() => {
            showAlert = false;
        }, 2000);
    }
</script>

{#if updateModalOpen}
<dialog open class="modal">
  <div class="modal-box">
    <h3 class="text-lg font-bold">Update Sport Center Information</h3>
    <label class="flex items-center gap-2 mb-4 input input-bordered">
      New Address:
      <input type="email" bind:value={updatedAddress} class="grow" placeholder="Enter new email" />
    </label>
    <label class="flex items-center gap-2 mb-4 input input-bordered">
      New Phone Number:
      <input type="email" bind:value={updatedPhoneNumber} class="grow" placeholder="Enter new email" />
    </label>
    <label class="flex items-center gap-2 mb-4 input input-bordered">
      New Opening Hour:
      <input type="email" bind:value={updatedStartHour} class="grow" placeholder="Enter new email" />
    </label>
    <label class="flex items-center gap-2 mb-4 input input-bordered">
      New Closing Hour:
      <input type="email" bind:value={updatedEndHour} class="grow" placeholder="Enter new email" />
    </label>
    <div class="modal-action">
      <button class="btn btn-custom" on:click={saveUpdatedDetails}>Save</button>
      <button class="btn btn-custom" on:click={closeUpdateModal}>Close</button>
    </div>
  </div>
</dialog>
{/if}

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

<div class="dashboard">
    <div class="flex-row">
    <div class="p-2 card">
        <div class="m-1 rounded-lg hero bg-base-200">
            <div class="flex flex-col-reverse items-center hero-content lg:flex-row-reverse lg:items-start">
                <img src={Hero} class="rounded-lg shadow-2xl size-4/12" />
                <div class="mt-12">
                    {#if sportCenter}
                        <div class="flex items-center">
                            <h1 class="mb-5 text-5xl font-bold">{sportCenter.name}</h1>
                            {#if userType === 'OWNER'}
                              <button
                                class="btn btn-action"
                                on:click={() => openUpdateModal()}
                              >
                                <IconEdit/>
                              </button>
                            {/if}
                        </div>
                    {:else}
                        <h1 class="mb-5 text-5xl font-bold">Loading...</h1>
                    {/if}
                    <div class="flex flex-col space-y-4">
                        <div class="p-2.5 bg-gray-100 rounded-lg shadow-md">
                            <h1 class="mb-2 text-xl font-bold">Opening Hours </h1>
                            {#if sportCenter}
                                <div class="flex items-center">
                                    <IconClock class="mr-2" />
                                    <p>{sportCenter.openingHour} - {sportCenter.closingHour}</p>
                                </div>
                            {:else}
                                <p> Loading...</p>
                            {/if}
                        </div>
                        <div class="p-2.5 bg-gray-100 rounded-lg shadow-md">
                            <h1 class="mb-2 text-xl font-bold">General Information </h1>
                            {#if sportCenter}
                                <div class="flex items-center">
                                    <IconHome class="mr-2" />
                                    <p>{sportCenter.address}</p>
                                </div>
                                <div class="flex items-center">
                                    <IconPhone class="mr-2" />
                                    <p>{sportCenter.phoneNumber}</p>
                                </div>
                            {:else}
                                <p> Loading...</p>
                            {/if}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card">
        <h1 class="text-xl font-extrabold">
            Instructors
        </h1>
        <div class="size-1/2 stats-container">
            <div class="rounded-lg shadow-lg stat place-items-center">
                <div class="stat-title">Total Instructors</div>
                <div class="stat-value">{instructors.length}</div>
                <div class="stat-desc">Numerous!</div>
            </div>
            <div class="rounded-lg shadow-lg stat place-items-center">
                <div class="mb-1 stat-title">10000 People Rated Us</div>
                <div class="mb-1 stat-value"><img src={Ratings}></div> <!-- Example data -->
                <div class="stat-desc">Satisfaction Garanteed</div>
            </div>
        </div>
        <table class="table overflow-y-scroll">
            <!-- head -->
            <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <!-- Use Svelte's each block to iterate over instructors -->
            {#each instructors as instructor, index}
                <tr>
                    <th>{index + 1}</th>
                    <td>{instructor.name}</td>
                </tr>
            {/each}
            </tbody>
        </table>
    </div>

    </div>
    <div class="flex-row">
        <div class="card courses">
            <h1 class="mb-1 font-extrabold">Courses Offered ({courseTypes.length} sessions)</h1>
            <h2 class="mb-3">Includes all hobbies!</h2>
            <div class="p-4 space-x-4 carousel carousel-center bg-neutral rounded-box">
                {#each courseTypes as courseType}
                    <div class="carousel-item">
                        <h3>{courseType.courseName}</h3>
                    </div>
                {/each}
            </div>
        </div>
  
    <div class="card sponsors">
      <!-- Content for Sponsors -->
      <h1 class="mb-1 font-extrabold">Sponsors</h1>
        <h2 class="mb-3">Thank you to our generous sponsors</h2>
        <div class="p-4 space-x-4 carousel carousel-center bg-neutral rounded-box">
            <div class="carousel-item">
              <img src={Discord} class="rounded-box" />
            </div> 
            <div class="carousel-item">
              <img src={Yonex} class="rounded-box" />
            </div> 
            <div class="carousel-item">
              <img src={Nike} class="rounded-box" />
            </div> 
            <div class="carousel-item">
              <img src={Intel} class="rounded-box" />
            </div> 
            <div class="carousel-item">
              <img src={Postman} class="rounded-box" />
            </div> 
            <div class="carousel-item">
                <img src={VSC} class="rounded-box" />
            </div> 
            <div class="carousel-item">
                <img src={Github} class="rounded-box" />
            </div> 
          </div>
      
    </div>
  </div>
</div>
  
<style>
    .carousel {
        display: flex;
        overflow-x: auto;
    }

    .carousel-item {
        flex: 0 0 auto; /* Do not grow or shrink */
        width: 120px; /* Set a fixed width */
        height: 120px; /* Set height equal to width for square aspect */
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 10px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        background-color: #f3f4f6;
    }

    .carousel-item img {
        max-width: 100%; /* Ensure the image is responsive and fits within its container */
        max-height: 100%; /* Ensure the image height does not exceed the container */
        object-fit: contain; /* Keep the aspect ratio of the image */
    }

    .dashboard {
        display: flex;
        flex-direction: column;
        padding: 20px;
    }

    .flex-row {
        display: flex;
        gap: 20px;
        margin-bottom: 20px;
    }

    .card {
        border-radius: 10px;
        padding: 20px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        background-color: #f3f4f6;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        overflow-x: hidden; /* Prevent horizontal overflow */
        overflow-y: auto; /* Add vertical scrollbar when needed */
    }

    .flex-row:first-child > .card {
        flex: 3 2 0; /* 3/4 of the space for the first card */
    }

    .flex-row:first-child > .card:last-child {
        flex: 2 3 0; /* 1/4 of the space for the second card */
    }

    .flex-row:last-child > .card:first-child {
        flex: 2 1 0; /* 2/5 of the space for the first card in the second row */
    }

    .flex-row:last-child > .card:last-child {
        flex: 3 1 0; /* 3/5 of the space for the second card in the second row */
    }

    .stats-container {
        display: flex;
        justify-content: space-around; /* Or 'space-between' as per your design needs */
        padding: 10px;
        gap: 20px;
    }

    .stat {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        background-color: #ffffff; /* or any color that fits your design */
        width: 100%; /* Ensures equal width distribution if you have more than two stats */
    }
    
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 5px;
        padding: 0 20px; /* Aligns with the grid padding */
    }

    .header2 {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 13px; /* Spacing between the header and the grid */
        padding: 0 20px; /* Aligns with the grid padding */
    }

    .welcome-text {
        font-size: 24px; /* Or any other size you prefer */
        color: #333;
        margin: 0; /* Removes default margin from the h1 tag */
    }

    .table::-webkit-scrollbar {
        display: none; /* For Chrome, Safari, and Opera */
    }
</style>