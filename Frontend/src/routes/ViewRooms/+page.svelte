<script>
  import { onMount } from 'svelte';
  import axios from 'axios';

  let rooms = [];
  let userToken; // subscribe to alex's store when its up -> LOOK AT DISCORD DMS WITH HIM FOR HOW
  let roomRowClicked = false;
  let targetRoom;

  let updatedName = '';
  let updatedCapacity = 0;

  let newName = '';
  let newCapacity = 0;
  let newFloorNumber = 0;
  let newRoomNumber = 0;

  onMount(async () => {
    try {
      const getRooms = await axios.get('/room/getAll')
      rooms = getRooms.data;
    } catch (error) {
      console.log(error);
    }
  });


  // check if a row is clicked so that i know whether to display the update or create panel
  async function rowClicked (floorNum, roomNum, name, capacity) {
    let filteredRooms = [];
    let error;
    /**
     * Represents a set of request data.
     * @typedef {Object} RequestData
     * @property {number} floorNumber - The floor number associated with the request.
     * @property {number} lowCapacity - The minimum capacity associated with the request.
     * @property {number} highCapacity - The minimum capacity associated with the request.
     */

    /**
     * Object containing the request data.
     * @type {RequestData}
     */
    // @TODO check if this has the desired effect
    const requestData = {
      floorNumber: floorNum,
      lowCapacity: capacity,
      highCapacity: capacity
    }
    try {
      const getFilteredRooms = await axios.get('/room/getAll', {
      params: requestData,
      headers: userToken
    })
      filteredRooms = getFilteredRooms.data;
    } catch (error) {
      console.log(error);
    }


    for (let room of filteredRooms) {
      if (room.name === name && room.capacity === capacity && room.floorNumber === floorNum && room.roomNumber === roomNum) {
        targetRoom = room
        roomRowClicked = true;
        break;
      }
    }

    return void 0;
  }

  async function updateClicked () {
    const roomUpdateCO = {
      id: targetRoom.getId(),
      name: updatedName,
      capacity: updatedCapacity
    }

    try {
      await axios.put("/rooms/update", {
        body: roomUpdateCO
      })
    } catch (error) {
      console.log(error);
    }

    return void 0;
  }

  async function createClicked () {
    const roomCO = {
      name: newName,
      floorNumber: newFloorNumber,
      roomNumber: newRoomNumber,
      capacity: newCapacity
    }

    try {
      await axios.post("/rooms/create", {
        headers: userToken,
        body: roomCO
      })
    } catch (error) {
      console.log(error);
    }

    return void 0;
  }
</script>

<h1 class="text-2xl font-bold p-2">Manage Rooms</h1>
<div class="grid grid-col-2">

  <!-- COLUMN 1 -->
  <div class="col-start-1 p-2">
    <div class="overflow-x-auto">
      <h1 class="text-lg">All Rooms</h1>
      <table class="table">
        <!-- head -->
        <thead>
          <tr>
            <th></th>
            <th>Room Name</th>
            <th>Floor Number</th>
            <th>Room Number</th>
            <th>Capacity</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <!-- row 1 -->
          {#each rooms as room, index (room)}
          <tr class="hover" on:click={() => rowClicked(room.floorNumber, room.roomNumber, room.name, room.capacity)}>
            <th>{index+1}</th>
            <th>{room.name}</th>
            <th>{room.floorNumber}</th>
            <th>{room.roomNumber}</th>
            <th>{room.capacity}</th>
            <th></th>
          </tr>
          {/each}
        </tbody>
      </table>
    </div>
  </div>

  <!-- COLUMN 2 -->
  <div class="col-start-2 p-2">
    {#if roomRowClicked}
      <h1 class="text-lg">Update Room:</h1>
      <div class="grid grid-row-2 gap-y-4">
        <div class="row-start-1 grid grid-row-2">
          <label for="textInput" class="text-sm start-row-1">New Name</label>
          <input
            type="text"
            id="textInput"
            class="start-row-2"
            bind:value={updatedName}
          />
        </div>
        <div class="row-start-2 grid grid-row-2">
          <label for="numInput" class="text-sm start-row-1">New Capacity</label>
          <input
            type="number"
            id="numInput"
            class="start-row-2"
            bind:value={updatedCapacity}
          />
        </div>
      </div>
      <div class="flex justify-end py-10">
        <button class="btn" on:click={updateClicked}>Update Room</button>
      </div>
    {:else}
      <h1 class="text-lg mb-2.5">Create Room:</h1>
      <div class="grid grid-row-4 gap-y-4">
        <div class="row-start-1 grid grid-row-2">
          <label for="nameInput" class="text-sm start-row-1">Name</label>
          <input
            type="text"
            id="nameInput"
            class="start-row-2"
            bind:value={newName}
            placeholder="Enter name here"
          />
        </div>
        <div class="row-start-2 grid grid-row-2">
          <label for="capacityInput" class="text-sm start-row-1">Capacity</label>
          <input
            type="number"
            id="capacityInput"
            class="start-row-2"
            bind:value={newCapacity}
            placeholder="0, 1, 2, ..."
          />
        </div>
        <div class="row-start-3 grid grid-row-2">
          <label for="floorNumInput" class="text-sm start-row-1">Floor Number</label>
          <input
            type="number"
            class="start-row-2"
            id="floorNumInput"
            bind:value={newFloorNumber}
            placeholder="0, 1, 2, ..."
          />
        </div>
        <div class="row-start-4 grid grid-row-2">
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
        <button class="btn" on:click={createClicked}>Create Room</button>
      </div>
    {/if}
  </div>
</div>