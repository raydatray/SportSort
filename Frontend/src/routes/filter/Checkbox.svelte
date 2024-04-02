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
<input type="checkbox" bind:checked />
{label}
</label>

  
<style>
  /* Styling for the label to display its children inline using flex, and aligning items for consistency */
  .label {
    display: flex;
    align-items: center;
    /* Removes any inherent space between inline or inline-block elements */
    font-size: 0;
  }

  /* Styling for the label text, reducing right margin to bring it closer to the checkbox */
  .label-text {
    margin-left: 2px; /* Reduced space between label text and checkbox */
    font-size: 16px; /* Reset font size after setting it to 0 in .label */
  }

  /* Styling for the checkbox, scaling it down and removing margins */
  .checkbox {
    transform: scale(0.75); /* Scales down the checkbox to make it smaller */
    margin-left: 2px; /* Adjusts space on the left of the checkbox */
  }
</style>