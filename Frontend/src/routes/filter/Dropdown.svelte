<script>
    import Checkbox from './Checkbox.svelte';
    const params = ['Swimming', 'Weightlifting']

/**
 * Represents a dropdown component containing checkboxes.
 * @typedef {Object} DropdownCheckbox
 * @property {boolean} isOpen - Flag to track the dropdown's open/closed state.
 * @property {Function} toggleDropdown - Function to toggle the dropdown's visibility.
 */

/** @type {boolean} */
let isOpen = false;

/**
 * Toggles the visibility of the dropdown content.
 * @returns {void}
 */
function toggleDropdown() {
  isOpen = !isOpen;
}

export {}
</script>

<div tabindex="0" class="collapse collapse-arrow border border-base-300 bg-base-200">
    <div class="collapse-title text-xl font-medium">
        Course Type
    </div>
    {#each params as courseType, index (courseType)}
        <div class="collapse-content" key={index}>
            <Checkbox bind:checked={isChecked} label={courseType} />
        </div>
    {/each}
</div>





<!-- DropdownCheckbox.svelte -->
<script>
    import Checkbox from './Checkbox.svelte';
  
    /**
     * Represents a dropdown component containing checkboxes.
     * @typedef {Object} DropdownCheckbox
     * @property {boolean} isOpen - Flag to track the dropdown's open/closed state.
     * @property {Function} toggleDropdown - Function to toggle the dropdown's visibility.
     */
  
    /** @type {boolean} */
    let isOpen = false;
  
    /**
     * Toggles the visibility of the dropdown content.
     * @returns {void}
     */
    function toggleDropdown() {
      isOpen = !isOpen;
    }
  </script>
  
  <!-- DropdownCheckbox component -->
  <div class="dropdown">
    <button class="dropdown-toggle" on:click={toggleDropdown}>
      Toggle Dropdown
    </button>
    {#if isOpen}
      <div class="dropdown-content">
        <Checkbox label="Option 1" checked={false} />
        <Checkbox label="Option 2" checked={false} />
        <Checkbox label="Option 3" checked={false} />
      </div>
    {/if}
  </div>

    <div tabindex="0" class="collapse collapse-arrow border border-base-300 bg-base-200">
        <div class="collapse-title text-xl font-medium">
            Course Type
        </div>
        {#each params as courseType, index (courseType)}
            <div class="collapse-content" key={index}>
                <Checkbox bind:checked={isChecked} label={courseType} />
            </div>
        {/each}
    </div>
  
  <style>
    /* Your CSS styles for dropdown and checkbox */
    /* .dropdown, .dropdown-toggle, .dropdown-content, etc. */
  </style>