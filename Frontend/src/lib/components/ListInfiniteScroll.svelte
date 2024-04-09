<script>
    /**
     * Represents an item in the list of courses.
     * @typedef {Object} CourseItem
     * @property {string} courseType - The type of course.
     * @property {string} startDate - The start date of the course.
     */

    /** @type {CourseItem[]} */
    let items = [
        {"courseType": "Swimming", "startDate": "2024-03-05"},
        {"courseType": "Yoga", "startDate": "2024-03-10"},
        {"courseType": "Pilates", "startDate": "2024-03-15"},
        {"courseType": "Soccer", "startDate": "2024-03-20"},
        {"courseType": "Basketball", "startDate": "2024-03-25"},
        {"courseType": "Pickle Ball", "startDate": "2024-03-30"},
        {"courseType": "Tennis", "startDate": "2024-04-01"},
        {"courseType": "Ping Pong", "startDate": "2024-04-05"},
        {"courseType": "Golf", "startDate": "2024-04-10"},
        {"courseType": "Basketball", "startDate": "2024-04-15"},
        {"courseType": "Pickle Ball", "startDate": "2024-04-20"},
        {"courseType": "Tennis", "startDate": "2024-04-25"},
        {"courseType": "Ping Pong", "startDate": "2024-04-30"},
        {"courseType": "Golf", "startDate": "2024-05-05"},
        // Add more items as needed
    ];

    /**
     * Represents a hovered item.
     * @typedef {Object} HoveredItem
     * @property {string} courseType - The type of the hovered course.
     * @property {string} startDate - The start date of the hovered course.
     */

    /** @type {HoveredItem} */
    let hoveredItem = {"courseType": "", "startDate": ""};

    /**
     * Represents the sorting order.
     * @type {string}
     */
    let sortOrder = "ascending";

    /**
     * Sorts the items based on the start date.
     * @returns {void}
     */
    function sortItems() {
        if (sortOrder === "ascending") {
            items = items.sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
        } else {
            items = items.sort((a, b) => new Date(b.startDate).getTime() - new Date(a.startDate).getTime());
        }
    }
</script>

<div class="list-container">
    <!-- Background wrapper for list header -->
    <div class="bg-secondary/50 list-header-bg">
        <h1 class="list-header">List of Courses</h1>
        <!-- Dropdown for sorting -->
    </div>
    <div class="dropdown-container">
        <div class="dropdown dropdown-bottom">
            <div tabindex="0" role="button" class="bg-slate-300 btn m-1">Sort by Date</div>
            <ul tabindex="0" class="dropdown-content z-[1] menu p-2 shadow bg-slate-300 rounded-box w-52">
                <li><a on:click={() => { sortOrder = "ascending"; sortItems(); }}>Ascending</a></li>
                <li><a on:click={() => { sortOrder = "descending"; sortItems(); }}>Descending</a></li>
            </ul>
        </div>
        <div class="sorted-order">
            {#if sortOrder === "ascending"}
                Ascending
            {:else if sortOrder === "descending"}
                Descending
            {:else}
                <!-- Display something when nothing is hovered over -->
                Hovered Item: No course selected
            {/if}
        </div>
    </div>
    <div class="scrollable-list">
        <!-- Render list items -->
        {#each items as item}
            <div class="bg-accent/50 list-item" on:mouseover={() => hoveredItem = item} on:mouseleave={() => hoveredItem = {"courseType": "", "startDate": ""}}>
                <span>{item.courseType}</span> <!-- Display course type -->
                <span>{item.startDate}</span> <!-- Display start date -->
            </div>
        {/each}
    </div>
    <!-- Display hovered item in a textbox -->
    <div class="hovered-item">
        {#if hoveredItem.courseType !== ""}
            Hovered Item -> Course Type: {hoveredItem.courseType}, Start Date: {hoveredItem.startDate}
        {:else}
            <!-- Display something when nothing is hovered over -->
            Hovered Item: No course selected
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
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: center; /* Center items horizontally */
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list {
        max-height: 55vh; /* Limit the height of the list */
        width: 100%;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding: 10px ;
        list-style-type: none; /* Remove list-style (bullets) */
    }

    /* Style for list items */
    .list-item {
        margin-bottom: 5px;
        border-radius: 5px; /* Rounded corners for the highlight box */
        padding: 5px; /* Add padding to the list item */
        transition: background-color 0.4s; /* Smooth transition for hover effect */
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
        border: 2px solid #ccc;
        border-radius: 5px; /* Rounded corners */
        text-align: center; /* Center text horizontally */
    }

    .list-header-bg {
        width: 75%; /* Set the width to occupy 75% of the space */
        margin: 0 auto 10px;
        border-radius: 5px; /* Rounded corners */
        display: flex; /* Use flexbox */
        justify-content: space-between; /* Align content on each end */
        align-items: center; /* Center items vertically */
    }
    .sorted-order{
        padding-top: 16px;
    }
    /* Style for dropdown */
    .dropdown-container {
        width: 100%;
        display: flex;
        justify-content: left;
    }

    .dropdown {
        padding-left: 10px;
        margin-right: 10px;
    }

    .dropdown-content {
        display: none;
        position: absolute;
        min-width: 160px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        z-index: 1;
    }

    .dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
    }

    .dropdown:hover .dropdown-content {
        display: block;
    }
</style>
