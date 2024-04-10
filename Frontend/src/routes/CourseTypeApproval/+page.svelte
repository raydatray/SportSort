<script>
    import ListInfiniteScroll from "$lib/components/ListCourseTypeApproval.svelte";
    import { onMount } from 'svelte';
    import { writable } from 'svelte/store';
    import axios from 'axios';

    const backendUrl = 'http://127.0.0.1:8080/';
    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });

    /**
     * Represents the clicked item.
     * @type {string}
     */
    let clickedItem = "";

    /**
     * Represents the button that was clicked.
     * @type {string}
     */
    let clickedButton = "";
    /**
     * An array of items.
     * @type {string[]}
     */
    let itemsType = []; // Reactive variable to hold the fetched data
    /**
     * An array of items.
     * @type {number[]}
     */
    let itemsTypeID = []; // Reactive variable to hold the fetched data
    /**
     * An array of items.
     * @type {string[]}
     */
    let approvedTypes = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let approvedTypesID = [];
    let errorType;

    onMount(() => {
        AXIOS.get('/courseTypes/getAll',{
            headers:{
                'userToken': 'wasd'
            }
        })
            .then(response => {
                const types = response.data;
                const updatedItems = [];
                const updatedItemsId = []
                const updatedItems1 = [];
                const updatedItems1Id = []

                for (let i = 0; i < types.length; i++) {
                    const type = types[i];
                    if (type.approvalStatus === false && type.rejectedStatus === false) {
                        updatedItems.push(type.courseName);
                        updatedItemsId.push(type.id);
                    }
                }

                itemsType = updatedItems; // Update items array with the new values
                itemsTypeID = updatedItemsId;

                for (let i = 0; i < types.length; i++) {
                    const type = types[i];
                    if (type.approvalStatus === true) {
                        updatedItems1.push(type.courseName);
                        updatedItems1Id.push(type.id);
                    }
                }

                approvedTypes = updatedItems1; // Update items array with the new values
                approvedTypesID = updatedItems1Id;
            })
            .catch(e => {
                errorType = e;
            });
    });

    /**
     * Gets the ID of the clicked item.
     * @param {string} type - The type of course clicked.
     * @returns {number} - The ID of the clicked item.
     */
    function getClickedID(type) {
        const index = itemsType.indexOf(type);
        console.log(itemsTypeID[index]);
        return itemsTypeID[index];
    }

    /**
     * Handles the check button click event.
     * @param {string} type - The type of course to be approved.
     */
    function handleCheck(type) {
        const id = getClickedID(type);
        AXIOS.put(`/courseTypes/updateApproval?id=${id}`, {}, {
            headers:{
                'userToken': 'wasd'
            }
        })
            .then(response => {
                console.log(getClickedID(type));
                // Remove type and its ID from itemsType and itemsTypeID
                const index = itemsType.indexOf(type);
                if (index !== -1) {
                    itemsType.splice(index, 1);
                    itemsTypeID.splice(index, 1);
                }

                // Add type and its ID to approvedTypes and approvedTypesID
                approvedTypes.push(type);
                approvedTypesID.push(itemsTypeID[index]);

                // Update the UI lists
                itemsType = [...itemsType];
                itemsTypeID = [...itemsTypeID];
                approvedTypes = [...approvedTypes];
                approvedTypesID = [...approvedTypesID];
            })
            .catch(e => {
                errorType = e;
                console.error(e);
            });
    }

</script>


<div class="grid-container">
    <div class="component-container component-1">
        <div class="list-container-type">
            <div class="bg-secondary/20 list-header-approved-bg"> <!-- Background wrapper for list header -->
                <h1 class="list-header-type">Approved Course Types</h1>
            </div>
            <div class="scrollable-list-type">
                <!-- Render list items -->
                {#each approvedTypes as approvedType}
                    <div class="bg-secondary-content/5 list-item-approved">
                        {approvedType}
                    </div>
                {/each}
            </div>
        </div>
    </div>
    <div class="component-container component-2">
        <div class="list-container-type">
            <div class="bg-secondary/20 list-header-type-bg"> <!-- Background wrapper for list header -->
                <h1 class="list-header-type">Course Types to Approve</h1>
            </div>
            <div class="scrollable-list-type">
                <!-- Render list items -->
                {#each itemsType as type}
                    <div class="bg-secondary-content/5 list-item-type">
                        {type}
                        <div class="buttons-type">
                            <button class="check-button-type" on:click={() => { handleCheck(type)}}>✓</button>
                            <button class="delete-button-type" on:click={() => { clickedItem = type; clickedButton = 'delete' }}>×</button>
                        </div>
                    </div>
                {/each}
            </div>
        </div>
    </div>
</div>


<style>

    .grid-container {
        display: grid;
        grid-template-columns: repeat(3, 1fr); /* Three columns with equal width */
        grid-template-rows: repeat(3, 1fr); /* Three rows with equal height */
        gap: 10px; /* Gap between grid items */
        padding-top: 0.5vh;
        padding-bottom: 10px;
        height: 100%; /* Occupy full height of the main element */
        width: 100%;
    }

    .component-container {
        border: 2px solid #ccc; /* Add border for visualization */
        border-radius: 10px;
        display: flex; /* Use flexbox */
        align-items: center; /* Center vertically */
        width: 100%; /* Ensure component fills up the whole cell horizontally */
        height: 100%; /* Ensure component fills up the whole cell vertically */
    }

    .component-1 {
        grid-column: span 2; /* Span two columns */
        grid-row: span 3; /* Span three rows */
    }

    .component-2 {
        grid-column: 3; /* Third column */
        grid-row: span 3; /* Span three rows */
    }

    /* Add CSS styles for the list container */
    .list-container-type {
        padding-top: 1%; /* Add padding to the top of the container */
        padding-bottom: 1%; /* Add padding to the bottom of the container */
        width: 100%;
        height: 100%;
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list-type {
        max-height: 60vh; /* Limit the height of the list */
        max-width: 100%;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding: 10px ;
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: flex-start; /* Center items horizontally */
        list-style-type: none; /* Remove list-style (bullets) */
    }

    /* Style for list items */
    .list-item-type {
        margin-bottom: 5px;
        border-radius: 5px; /* Rounded corners for the highlight box */
        padding-left: 5px; /* Add left padding to create space */
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        width: 100%; /* Ensure header spans the full width */
        display: flex; /* Use flexbox */
        justify-content: space-between; /* Align content on each end */
        align-items: center; /* Center items vertically */
        font-size: 2.1vh;
    }
    .list-item-approved {
        margin-bottom: 5px;
        border-radius: 5px; /* Rounded corners for the highlight box */
        padding-left: 5px; /* Add left padding to create space */
        padding-top: 0.3vh;
        padding-bottom: 0.3vh;
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        width: 100%; /* Ensure header spans the full width */
        display: flex; /* Use flexbox */
        justify-content: space-between; /* Align content on each end */
        align-items: center; /* Center items vertically */
        font-size: 2.1vh;
    }

    /* Apply background color when hovering over list item */
    .list-item-type:hover {
        border-radius: 5px; /* Rounded corners for the highlight box */
        font-size: 2.4vh; /* Increase font size */
    }

    /* Style for check and delete buttons */
    .buttons-type {
        display: flex; /* Use flexbox */
        margin-right: 5px; /* Space between  */
        border: none; /* Remove default border */
        cursor: pointer; /* Change cursor to pointer on hover */
    }

    .check-button-type {
        padding-left: 8px;
        padding-right: 8px;
        margin-left: 5px; /* Add margin between buttons */
        border-radius: 10px; /* Make button background circular */
    }

    .delete-button-type {
        padding-left: 9px;
        padding-right: 9px;
        margin-left: 5px;
        font-size: 1.2em; /* Increase font size */
        border-radius: 10px; /* Make button background circular */
    }

    /* Style for check and delete buttons */
    .check-button-type:hover {
        background-color: lightgreen; /* Change background color to green on hover */
    }

    .delete-button-type:hover {
        background-color: lightcoral; /* Change background color to red on hover */
    }

    /* Style for list header */
    .list-header-type {
        font-size: 2.7vh;
        text-align: center; /* Center the header text horizontally */
        width: 100%; /* Ensure header spans the full width */
    }

    /* Style for clicked item textbox */
    .clicked-item-type {
        margin: 10px; /* Add margin on top */
        padding: 5px;
        /*background-color: #FFFF;*/
        border: 2px solid #ccc;
        border-radius: 5px; /* Rounded corners */
        text-align: center; /* Center text horizontally */
    }

    .list-header-type-bg {
        width: 75%; /* Set the width to occupy 75% of the space */
        margin: 0 auto; /* Center the header horizontally */
        margin-top: 10px;
        margin-bottom: 10px;
        border-radius: 5px; /* Rounded corners */
    }
    .list-header-approved-bg {
        width: 87%; /* Set the width to occupy 75% of the space */
        margin: 0 auto; /* Center the header horizontally */
        margin-top: 6px;
        margin-bottom: 10px;
        border-radius: 5px; /* Rounded corners */
    }
</style>
