<script>
  const daysOfWeek = [
    'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'
  ];
</script>

<style>
  .checkbox-list {
    display: flex; /* Align items in a horizontal row */
    justify-content: center; /* Center align the checkboxes */
    gap: 10px; /* Add some space between each checkbox */
  }
  .checkbox-container {
    display: flex; /* Use flexbox for alignment */
    flex-direction: column; /* Stack items vertically */
    align-items: center; /* Center-align the content */
  }
  .label-text {
    margin-bottom: 5px; /* Space between label text and checkbox */
  }
</style>

<div class="checkbox-list">
  {#each daysOfWeek as day, index (day)}
    <div class="checkbox-container" key={index}>
      <span class="label-text">{day}</span>
      <input type="checkbox" id={day} class="checkbox" />
    </div>
  {/each}
</div>
