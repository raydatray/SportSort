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
  
    <div class="bg-base-300 big-container">
        <h2 class="px-1 pt-2 pb-1">{fieldTitle}</h2>
        <div class="content">
            <!-- <input type="date" bind:value={selectedDate} on:change={handleDateChange} min="2024-01-01" max="2025-04-10" /> -->
            <input class="bg-base-200 datepicker" type="date" bind:value={selectedDate} min="2024-01-01" max="2025-04-10" />
        </div>
    </div>
  
  
  
  <style lang="postcss">
    h2 {
        font-size: large;
        font-weight: 500;
    }
    .big-container {
        width: 10rem;
        border-width: 0.125rem;
        border-radius: 0.3125rem;
    }
    .content {
        display: flex;
        flex-direction: column;
        padding-left: 0.3125rem;
        padding-right: 0.3125rem;
        padding-bottom: 0.3125rem;
        gap: 0.3125rem;
    }
    .datepicker {
        padding: 0.125rem;
        border-radius: 0.3125rem;
    }
  </style>