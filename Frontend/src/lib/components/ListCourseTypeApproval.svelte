<!-- ListCourseTypeApproval.svelte -->
<script>
    /**
     * An array of items.
     * @type {string[]}
     */
    let items = [
        'Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5', 'Item 6', 'Item 7', 'Item 8',
        'Item 9', 'Item 10', 'Item 11', 'Item 12', 'Item 13', 'Item 14', 'Item 15', 'Item 16', 'Item 17'
    ];

    /**
     * Represents the hovered item.
     * @type {string}
     */
    let hoveredItem = "";

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

</script>

<div class="list-container">
    <div class="bg-secondary/50 list-header-bg"> <!-- Background wrapper for list header -->
        <h1 class="list-header">List of Items</h1>
    </div>
    <div class="scrollable-list">

        <!-- Render list items -->
        {#each items as item}
            <div class="bg-secondary-content/5 list-item" on:mouseover={() => hoveredItem = item} on:mouseleave={() => hoveredItem = ""}>
                {item}
                <div class="buttons">
                    <button class="check-button" on:click={() => { clickedItem = item; clickedButton = 'check' }}>✓</button>
                    <button class="delete-button" on:click={() => { clickedItem = item; clickedButton = 'delete' }}>×</button>
                </div>
            </div>
        {/each}
    </div>
    <!-- Display hovered item in a textbox -->
    <div class="hovered-item">
        {#if hoveredItem}
            Hovered Item: {hoveredItem}
        {:else}
            <!-- Display something when nothing is hovered over -->
            Hovered Item: Item x
        {/if}
    </div>
    <!-- Display clicked item in a textbox -->
    <div class="clicked-item">
        {#if clickedItem}
            Clicked: {clickedItem}, Button: {clickedButton}
        {:else}
            <!-- Display something when no item is clicked -->
            No item clicked
        {/if}
    </div>
</div>

<style>
    /* Add CSS styles for the list container */
    .list-container {
        padding-top: 1%; /* Add padding to the top of the container */
        padding-bottom: 1%; /* Add padding to the bottom of the container */
        width: 100%;
        height: 100%;
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list {
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
    .list-item {
        margin-bottom: 5px;
        border-radius: 5px; /* Rounded corners for the highlight box */
        padding-left: 5px; /* Add left padding to create space */
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        width: 100%; /* Ensure header spans the full width */
        display: flex; /* Use flexbox */
        justify-content: space-between; /* Align content on each end */
        align-items: center; /* Center items vertically */
        font-size: 2.7vh;
    }

    /* Apply background color when hovering over list item */
    .list-item:hover {
        border-radius: 5px; /* Rounded corners for the highlight box */
        font-size: 3.0vh; /* Increase font size */
    }

    /* Style for check and delete buttons */
    .buttons {
        display: flex; /* Use flexbox */
        margin-right: 5px; /* Space between  */
        border: none; /* Remove default border */
        cursor: pointer; /* Change cursor to pointer on hover */
    }

    .check-button {
        padding-left: 8px;
        padding-right: 8px;
        margin-left: 5px; /* Add margin between buttons */
        border-radius: 10px; /* Make button background circular */
    }

    .delete-button {
        padding-left: 9px;
        padding-right: 9px;
        margin-left: 5px;
        font-size: 1.2em; /* Increase font size */
        border-radius: 10px; /* Make button background circular */
    }

    /* Style for check and delete buttons */
    .check-button:hover {
        background-color: lightgreen; /* Change background color to green on hover */
    }

    .delete-button:hover {
        background-color: lightcoral; /* Change background color to red on hover */
    }

    /* Style for list header */
    .list-header {
        font-size: 2.9vh;
        text-align: center; /* Center the header text horizontally */
        width: 100%; /* Ensure header spans the full width */
    }

    /* Style for hovered item textbox */
    .hovered-item {
        margin: 10px; /* Add margin on top */
        padding: 5px;
        /*background-color: #FFFF;*/
        border: 2px solid #ccc;
        border-radius: 5px; /* Rounded corners */
        text-align: center; /* Center text horizontally */
    }

    /* Style for clicked item textbox */
    .clicked-item {
        margin: 10px; /* Add margin on top */
        padding: 5px;
        /*background-color: #FFFF;*/
        border: 2px solid #ccc;
        border-radius: 5px; /* Rounded corners */
        text-align: center; /* Center text horizontally */
    }

    .list-header-bg {

        width: 75%; /* Set the width to occupy 75% of the space */
        margin: 0 auto; /* Center the header horizontally */
        margin-top: 10px;
        margin-bottom: 10px;
        border-radius: 5px; /* Rounded corners */
    }
</style>
