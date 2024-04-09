<!-- DropdownCheckbox.svelte -->
<script>
  import { writable } from 'svelte/store'
  import Checkbox from './Checkbox.svelte';


  // change to get from backend when finished
  export let params = ['Swimming', 'Weightlifting'];

  // Array to track checked state for each checkbox
  // export let checkedStates = writable(Array(params.length).fill(false));
  export let checkedStates = Array(params.length).fill(false);
  export let fieldTitle = '';
  //export let currStateVal = getCheckedStates();

  //export let instance = new CheckboxFilterField(fieldTitle, params);

/**
 * Toggles the checked state of a checkbox at a specific index.
 * @param {number} index - The index of the checkbox in the array.
 * @returns {void} A Promise that resolves with an array representing the updated checked states of checkboxes.
 */
/*
 export function toggleChecked(index) {
  checkedStates.update(values => {
    let updatedValues = [...values];
    updatedValues[index] = !updatedValues[index];
    return updatedValues;
  });
}
*/
/**
 * Get the checked states of all checkboxes.
 * @returns {Array<boolean>} An array representing the checked states of checkboxes.
 */
/*
 export function getCheckedStates() {
  /**
   * @type {boolean[]}
   */
  /*
  let checkedValues = [];

  // Subscribe to checkedStates updates
  checkedStates.subscribe(values => {
    // Clear existing values
    checkedValues = [];

    // Add only boolean values to checkedValues
    values.forEach(value => {
      if (typeof value === 'boolean') {
        checkedValues.push(value);
      }
    });
  });

  return checkedValues;
}
*/
</script>

  <!-- DropdownCheckbox component -->
  <div class="bg-base-300 big-container">
      <h2 class="px-1 pt-2 pb-1">{fieldTitle}</h2>
      <div class="content">
          {#each params as courseType, index (courseType)}
              <div>
              <!-- <Checkbox bind:checked={currStateVal[index]} label={courseType} on:click={() => toggleChecked(index)} /> -->
                <Checkbox bind:checked={checkedStates[index]} label={courseType} />
              </div>
          {/each}
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
</style>