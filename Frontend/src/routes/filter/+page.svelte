<!-- DropdownCheckbox.svelte -->
<script>
    import Checkbox from './Checkbox.svelte';
    const params = ['Swimming', 'Weightlifting'];
  
    // Array to track checked state for each checkbox
    let checkedStates = Array(params.length).fill(false);
  
    /**
     * Toggles the checked state of a checkbox at a specific index.
     * @param {number} index - The index of the checkbox in the array.
     * @returns {void}
     */
    function toggleChecked(index) {
      checkedStates[index] = !checkedStates[index];
    }

    /**
   * Prevents event propagation to parent elements.
   * @param {Event} event - The click event.
   * @returns {void}
   */
  function stopPropagation(event) {
    event.stopPropagation();
  }
</script>
  
    <!-- DropdownCheckbox component -->
    <div class="big-container">
        <div tabindex="0" class="collapse collapse-arrow border border-base-300 bg-base-200">
            <div class="collapse-title text-xl font-medium">
                Course Type
            </div>

            
            <div class="collapse-content">
                {#each params as courseType, index (courseType)}
                    <div on:click={stopPropagation}>
                    <Checkbox bind:checked={checkedStates[index]} label={courseType} on:click={() => toggleChecked(index)} />
                    </div>
                {/each}
            </div>
        </div>
    </div>

            {#each params as courseType, index (courseType)}
                <Checkbox bind:checked={checkedStates[index]} label={courseType} on:click={() => toggleChecked(index)} />
            {/each}

  
  <style>
    .big-container {
        width: fit-content;
    }
    .collapse-content {
        display: flex;
        flex-direction: column;
        gap: 5px;
    }
  </style>