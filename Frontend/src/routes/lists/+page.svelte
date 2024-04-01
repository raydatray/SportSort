<!-- +page.svelte -->
<script>
    // an array of items
    let items = ['Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5', 'Item 6', 'Item 7', 'Item 8',
        'Item 9', 'Item 10', 'Item 11', 'Item 12', 'Item 13', 'Item 14', 'Item 15', 'Item 16', 'Item 17',];
    // variable to store the hovered item
    let hoveredItem = "";
</script>

<div class="list-container">
    <div class="scrollable-list">
        <div class="list-header-bg"> <!-- Background wrapper for list header -->
            <h1 class="list-header">List of Items</h1>
        </div>
        <!-- Render list items -->
        {#each items as item}
            <div class="list-item" on:mouseover={() => hoveredItem = item} on:mouseleave={() => hoveredItem = ""}>{item}</div>
        {/each}
    </div>
    <!-- Display hovered item in a textbox -->
    <div class="hovered-item">
        {#if hoveredItem}
            {hoveredItem}
        {:else}
            <!-- Display something when nothing is hovered over -->
            Item x
        {/if}
    </div>
</div>

<style>
    /* Add CSS styles for the list container */
    .list-container {
        padding-top: 40px; /* Add padding to the top of the container */
        padding-bottom: 40px; /* Add padding to the bottom of the container */
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list {
        max-height: 200px; /* Limit the height of the list */
        max-width: 300px;
        overflow-y: auto; /* Enable vertical scrolling */
        border: 2px solid #ccc;
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
        background-color: lavender;
        border-radius: 5px; /* Rounded corners for the highlight box */
        padding-left: 5px; /* Add left padding to create space */
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        width: 100%; /* Ensure header spans the full width */
    }

    /* Apply background color when hovering over list item */
    .list-item:hover {
        border-radius: 5px; /* Rounded corners for the highlight box */
        font-size: 18px; /* Increase font size */
    }

    /* Style for list header */
    .list-header {
        text-align: center; /* Center the header text horizontally */
        width: 100%; /* Ensure header spans the full width */
    }

    /* Style for hovered item textbox */
    .hovered-item {
        max-width: 300px;
        margin-top: 10px; /* Add margin on top */
        padding: 5px;
        /*background-color: #FFFF;*/
        border: 2px solid #ccc;
        border-radius: 5px; /* Rounded corners */
        text-align: center; /* Center text horizontally */
    }

    .list-header-bg {
        background-color: lightblue; /* Set the background color */
        width: 75%; /* Set the width to occupy 75% of the space */
        margin: 0 auto; /* Center the header horizontally */
        margin-bottom: 10px;
        border-radius: 5px; /* Rounded corners */
    }
</style>

