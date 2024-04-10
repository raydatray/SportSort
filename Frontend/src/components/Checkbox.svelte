<script>
/**
 * Represents a checkbox component.
 * @typedef {Object} Checkbox
 * @property {boolean} checked - The checked state of the checkbox.
 * @property {string} label - The label for the checkbox.
 * @property {Function} toggleCheckbox - Function to toggle the checkbox state.
 */

/** @type {boolean} */
let checked = false;

/** @type {string} */
let label = '';

/**
 * Toggles the checked state of the checkbox.
 * @returns {void}
 */
/*
function toggleCheckbox() {
    checked = !checked;
}
*/
// export { checked, label, toggleCheckbox };
export { checked, label };
</script>
  
<!-- Checkbox component -->
<label class="checkbox-label">
<!-- <input type="checkbox" bind:checked on:click={toggleCheckbox} /> -->
<input class="checkbox checkbox-xs" type="checkbox" bind:checked />
{label}
</label>

  
<style lang="postcss">
  .checkbox {
    /* transform: scale(0.75); */
    align-items: center; 
    margin-left: 0.125rem;
  }

  .checkbox-label {
    align-items: center;
  }
</style>