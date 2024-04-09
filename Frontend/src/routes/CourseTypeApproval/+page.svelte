<script>
    import ListInfiniteScroll from "$lib/components/ListCourseTypeApproval.svelte";
    import ListCourseTypeApproval from "$lib/components/ListInfiniteScroll.svelte";
</script>


<div class="grid-container">
    <div class="component-container component-1">
        <ListCourseTypeApproval />
    </div>
    <div class="component-container component-2">
        <ListInfiniteScroll />
    </div>
</div>


<style>

    .grid-container {
        display: grid;
        grid-template-columns: repeat(3, 1fr); /* Three columns with equal width */
        grid-template-rows: repeat(3, 1fr); /* Three rows with equal height */
        gap: 10px; /* Gap between grid items */
        padding-top: 10px;
        padding-bottom: 10px;
        height: 90%; /* Occupy full height of the main element */
        width: 100%;
    }

    .component-container {
        border: 2px solid #ccc; /* Add border for visualization */
        border-radius: 10px;
        display: flex; /* Use flexbox */
        align-items: center; /* Center vertically */
        width: 100%; /* Ensure component fills up the whole cell horizontally */
        height: 100%; /* Ensure component fills up the whole cell vertically */
    }

    .component-1 {
        grid-column: span 2; /* Span two columns */
        grid-row: span 3; /* Span three rows */
    }

    .component-2 {
        grid-column: 3; /* Third column */
        grid-row: span 3; /* Span three rows */
    }
</style>
