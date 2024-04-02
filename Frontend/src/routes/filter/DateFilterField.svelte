<script>
    /**
    * Represents a container of checkboxes.
    * @typedef {Object} DateFilterField
    * @property {string} fieldTitle - title of the given field
    * @property {Date} selectedDate - value to pass to the api.
    */
    // change to get from backend when finished
    export let selectedDate = new Date();
    
    // label name
    export let fieldTitle = '';

  
  
  </script>
  
    <div class="big-container">
        <h2>{fieldTitle}</h2>
        <div class="content">
            <input type="date" bind:value={selectedDate} min="2024-01-01" max="2025-04-10" />
        </div>
    </div>
  
  
  
  <style>
    h2 {
        font-size: large;
        padding-left: 5px;
        padding-right: 5px;
        padding-top: 5px;
        padding-bottom: 10px;
    }
    .big-container {
        width: 150px;
        border-color: aquamarine;
        border-width: 5px;
    }
    .content {
        display: flex;
        flex-direction: column;
        padding-left: 5px;
        padding-right: 5px;
        padding-bottom: 5px;
        gap: 5px;
    }
  </style>