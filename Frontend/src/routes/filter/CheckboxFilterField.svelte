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
  <div class="big-container">
      <h2>{fieldTitle}</h2>
      <div class="content">
          {#each params as courseType, index (courseType)}
              <div>
              <!-- <Checkbox bind:checked={currStateVal[index]} label={courseType} on:click={() => toggleChecked(index)} /> -->
                <Checkbox bind:checked={checkedStates[index]} label={courseType} />
              </div>
          {/each}
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