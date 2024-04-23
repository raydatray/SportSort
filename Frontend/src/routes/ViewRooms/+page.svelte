<script>
  import { onMount } from 'svelte';
  import { fade } from 'svelte/transition';
  import axios from 'axios';
  import {IconPencil, IconTrash} from '@tabler/icons-svelte';

  /**
   * @typedef {Object} Room
   * @property {string} name
   * @property {number} id
   * @property {number} floorNumber
   * @property {number} roomNumber
   * @property {number} capacity
   */

  /**
   * Array containing course type objects.
   * @type {Array<{
   * name: string,
   * id: number,
   * floorNumber: number,
   * roomNumber: number,
   * capacity: number
   * }>}
   */
  let rooms = [];
  let userToken = sessionStorage.getItem("token"); // subscribe to alex's store when its up -> LOOK AT DISCORD DMS WITH HIM FOR HOW
  let roomRowClicked = false;

  let selectedRoomID = 0;
  let targetRoom;

  let updatedName = '';
  let updatedCapacity = 0;

  let newName = '';
  let newCapacity = 0;
  let newFloorNumber = 0;
  let newRoomNumber = 0;

  const backendUrl = 'http://127.0.0.1:8080/';
  const AXIOS = axios.create({
      baseURL: backendUrl,
      headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
  });

  onMount(async () => {
      await refreshTables();
  });



  // check if a row is clicked so that i know whether to display the update or create panel
  /**
  * @async
  * @function iconClicked
   * @param {number} id - The id associated with the clicked row.
   * @param {number} floorNum - The floor number associated with the clicked row.
   * @param {number} roomNum - The room number associated with the clicked row.
   * @param {string} name - The name associated with the clicked row.
   * @param {number} capacity - The capacity associated with the clicked row.
   * @returns {Promise<void>} A Promise that resolves after processing the click event.
  */
  async function iconClicked (id, floorNum, roomNum, name, capacity) {
    let error;
    roomRowClicked = true;
    selectedRoomID = id;
    return void 0;
  }

  let alertMessage = '';
  let showAlert = false;
  function displayAlert(message) {
      alertMessage = message;
      showAlert = true;
      setTimeout(() => {
          showAlert = false;
      }, 2000);
  }

  async function updateClicked () {
    const roomUpdateCO = {
      id: selectedRoomID,
      name: updatedName,
      capacity: updatedCapacity
    }
    console.log(roomUpdateCO);

    await AXIOS.put("/rooms/update", roomUpdateCO, {
      headers: {'userToken': userToken }
    })
        .catch(error => {
            const message = error.response?.data || "An error occurred while proposing the course type.";
            displayAlert(message);
        });


    await refreshTables();
    roomRowClicked = false;

    return void 0;
  }

  async function refreshTables() {
    await AXIOS.get('/rooms/getAll', {
        // Include the userToken in the request if necessary
        headers: { 'userToken': userToken }
    })
        .then(response => {
            rooms = response.data; // Assuming response.data contains the rooms data
        })
        .catch(error => {
            const message = error.response?.data || "An error occurred while proposing the course type.";
            displayAlert(message);
        });
    console.log(rooms);
  }

  /**
   * @async
   * @function deleteClicked
   * @param {number} id - The id associated with the clicked row.
   * @returns {Promise<void>} A Promise that resolves after processing the click event.
   */
  async function deleteClicked (id) {
    let error;
    const delParam = {
        id: id
    }

    await AXIOS.delete("/rooms/delete", {
        headers: {'userToken': userToken},
        params: delParam
    })
        .catch(error => {
            const message = error.response?.data || "An error occurred while proposing the course type.";
            displayAlert(message);
        });


    await refreshTables();

    return void 0;
  }

  async function createClicked () {
    const roomCO = {
      name: newName,
      floorNumber: newFloorNumber,
      roomNumber: newRoomNumber,
      capacity: newCapacity
    }


    await AXIOS.post("/rooms/create", roomCO, {
      headers: {'usertoken': userToken}
    })
        .catch(error => {
            const message = error.response?.data || "An error occurred while proposing the course type.";
            displayAlert(message);
        });


    await refreshTables();
    newName = '';
    newFloorNumber = 0;
    newRoomNumber = 0;
    newCapacity = 0;

    return void 0;
  }
</script>

<h1 class="p-2 text-2xl font-bold">Manage Rooms</h1>
<div class="grid grid-col-2">

  <!-- COLUMN 1 -->
  <div class="col-start-1 p-2">
    <div class="hello">
      <h1 class="text-lg">All Rooms</h1>
      <table class="table overflow-y-auto max-h-screen">
        <!-- head -->
        <thead>
          <tr>
            <th></th>
            <th class="font-bold">Room Name</th>
            <th class="font-bold">Floor Number</th>
            <th class="font-bold">Room Number</th>
            <th class="font-bold">Capacity</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <!-- row 1 -->
          {#each rooms as room, index (room)}
          <tr class="hover">
            <th>{index+1}</th>
            <th class="font-normal">{room.name}</th>
            <th class="font-normal">{room.floorNumber}</th>
            <th class="font-normal">{room.roomNumber}</th>
            <th class="font-normal">{room.capacity}</th>
            <th><button on:click={() => iconClicked(room.id, room.floorNumber, room.roomNumber, room.name, room.capacity)}><IconPencil /></button></th>
            <th><button on:click={() => deleteClicked(room.id)}><IconTrash /></button></th>
          </tr>
          {/each}
        </tbody>
      </table>
    </div>
  </div>

  <!-- COLUMN 2 -->
  <div class="col-start-2 p-2">
    {#if roomRowClicked}
      <h1 class="text-lg">Update Room</h1>
      <div class="grid grid-row-2 gap-y-4">
        <div class="grid row-start-1 grid-row-2">
          <label for="textInput" class="text-sm start-row-1">New Name</label>
          <input
            type="text"
            id="textInput"
            class="start-row-2"
            bind:value={updatedName}
          />
        </div>
        <div class="grid row-start-2 grid-row-2">
          <label for="numInput" class="text-sm start-row-1">New Capacity</label>
          <input
            type="number"
            id="numInput"
            class="start-row-2"
            bind:value={updatedCapacity}
          />
        </div>
      </div>
      <div class="flex justify-around py-10">
        <button class="btn btn-success" on:click={updateClicked}>Update Room</button>
      </div>
    {:else}
      <h1 class="text-lg mb-2.5">Create Room</h1>
      <div class="grid grid-row-4 gap-y-4">
        <div class="grid row-start-1 grid-row-2">
          <label for="nameInput" class="text-sm start-row-1">Name</label>
          <input
            type="text"
            id="nameInput"
            class="start-row-2"
            bind:value={newName}
            placeholder="Enter name here"
          />
        </div>
        <div class="grid row-start-2 grid-row-2">
          <label for="capacityInput" class="text-sm start-row-1">Capacity</label>
          <input
            type="number"
            id="capacityInput"
            class="start-row-2"
            bind:value={newCapacity}
            placeholder="0, 1, 2, ..."
          />
        </div>
        <div class="grid row-start-3 grid-row-2">
          <label for="floorNumInput" class="text-sm start-row-1">Floor Number</label>
          <input
            type="number"
            class="start-row-2"
            id="floorNumInput"
            bind:value={newFloorNumber}
            placeholder="0, 1, 2, ..."
          />
        </div>
        <div class="grid row-start-4 grid-row-2">
          <label for="roomNumInput" class="text-sm start-row-1">Room Number</label>
          <input
            type="number"
            id="roomNumInput"
            class="start-row-2"
            bind:value={newRoomNumber}
            placeholder="0, 1, 2, ..."
          />
        </div>
      </div>
      <div class="flex justify-end py-10">
        <button class="btn btn-success" on:click={createClicked}>Create Room</button>
      </div>
    {/if}
  </div>
</div>

{#if showAlert}
  <div role="alert" class="alert alert-error modal-alert absolute w-1/2 left-1/2 -translate-x-1/2 bottom-1/4 p-4 z-50 box-border" in:fade={{ duration: 300 }} out:fade={{ duration: 300 }}>
    <span>{alertMessage}</span>
  </div>
{/if}

<style>
    /*.modal-alert {*/
    /*    position: absolute; !* Position it absolutely to align it with the modal *!*/
    /*    width: 50%; !* Make it as wide as the modal *!*/
    /*    left: 50%; !* Center it horizontally *!*/
    /*    transform: translateX(-50%); !* Adjust horizontal position *!*/
    /*    bottom: 20%; !* Adjust vertical position to be below the modal *!*/
    /*    z-index: 100; !* Ensure it's above the modal background *!*/
    /*    box-sizing: border-box;*/
    /*    padding: 1rem;*/
    /*}*/

</style>


