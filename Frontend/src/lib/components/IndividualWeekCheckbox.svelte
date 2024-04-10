<!-- The form control div acts as a container for your label and checkbox elements -->
<div class="form-control">
  <!-- Label wraps the text and the checkbox to bind them together semantically and for better UX -->
  <label class="label cursor-pointer" style="display: flex; align-items: center;">
    <!-- This is your label text -->
    <span class="label-text">Remember me</span>
    <!-- The checkbox input, already checked by default -->
    <input type="checkbox" checked="checked" class="checkbox" />
  </label>
</div>

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
