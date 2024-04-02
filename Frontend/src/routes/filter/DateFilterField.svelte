<script>
    import { createEventDispatcher } from 'svelte';

    //const dispatch = createEventDispatcher();

    // change to get from backend when finished
    export let selectedDate = new Date();
    
    // label name
    export let fieldTitle = '';
    export let key = '';

    /**
     * Handles the change event when a date is selected or changed in a DateFilterField component.
     * @param {Event} event - The change event triggered by the input element.
     * @returns {void}
     */
    /*
    function handleDateChange(event) {
        const inputElement = event.target;
        if (inputElement instanceof HTMLInputElement) {
            const selectedDate = inputElement.value;
            // Emit the selected date to the parent component
            dispatch('dateSelected', { key, date: selectedDate });
        }
    }
    */
  
  </script>
  
    <div class="big-container">
        <h2>{fieldTitle}</h2>
        <div class="content">
            <!-- <input type="date" bind:value={selectedDate} on:change={handleDateChange} min="2024-01-01" max="2025-04-10" /> -->
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