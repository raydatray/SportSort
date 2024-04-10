<script>
    import {onMount, createEventDispatcher} from "svelte";
    import axios from 'axios'

    let instructors = [];
    const dispatch = createEventDispatcher();
    
    onMount(async () => {
        try {
            const getInstructors = await axios.get('http://localhost:8080/accounts/getInstructors');
            instructors = getInstructors.data;
        } catch (error) {
            console.log(error)
        }
    });

    let approvalBoxes = [];
    function mutexApproval(event) {
        approvalBoxes.forEach(box => {
            if (box !== event.target) {
                box.checked = false;
            }
        });
        //console.log(approvalBoxes);
    }

    let rejectedBoxes = [];
    let rejected = false;
    function mutexRejected(event) {
        rejectedBoxes.forEach(box => {
            if (box !== event.target) {
                box.checked = false;
            }
        });
        rejected = rejectedBoxes[1]?.checked;
        //console.log(rejectedBoxes);
    }

    $: {
        approvalBoxes.forEach(box => box.disabled = rejected);
    }

    let instructorCheckboxes = [];
    let approvedParam = null;
    let rejectedParam = null;

    function handleFilterChange() {
        //console.log("deez nuts");
        let selectedInstructors = instructorCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);
        approvedParam = approvalBoxes[0]?.checked ? false : approvalBoxes[1]?.checked ? true : null;
        rejectedParam = rejectedBoxes[0]?.checked ? false : rejectedBoxes[1]?.checked ? true : null;

        let params = {
            instructors: selectedInstructors,
            approved: approvedParam,
            rejected: rejectedParam
        }
        dispatch('filterChange', params);
        //console.log(params);
    }

</script>

<div class = "flex-col space-y-.5">
    <div class = "text-lg font-medium bg-base-200 border-2xl">Filters</div>
    <div class = "collapse collapse-arrow bg-base-200">
        <input type = "checkbox" checked/>
        <div class = "collapse-title text-md font-medium">
            Instructors
        </div>
        <div class = "collapse-content">
            {#each instructors as instructor}
                <div class = "form-control">
                    <label class = "label cursor-pointer">
                        <input type = "checkbox" bind:this={instructorCheckboxes[instructor.id]} bind:value={instructor.id} on:change = {handleFilterChange} class = "checkbox"/>
                        <span class ="label-text">{instructor.name}</span>
                    </label>
                </div>
            {/each}
        </div>
    </div>

    <div class = "collapse collapse-arrow bg-base-200">
        <input type = "checkbox" checked/>
        <div class = "collapse-title text-md font-medium">
            Approval Status
        </div>
        <div class = "collapse-content">
            <div class = "form-control">
                <label class = "label cursor-pointer">
                    <input type = "checkbox" bind:this = {approvalBoxes[0]} on:click={mutexApproval} on:change = {handleFilterChange} class = "checkbox"/>
                    <span class ="label-text">Unapproved</span>
                </label>
            </div>
            <div class = "form-control">
                <label class = "label cursor-pointer">
                    <input type = "checkbox" bind:this = {approvalBoxes[1]} on:click={mutexApproval} on:change = {handleFilterChange} class = "checkbox"/>
                    <span class ="label-text">Approved</span>
                </label>
            </div>
        </div>
    </div>

    <div class = "collapse collapse-arrow bg-base-200">
        <input type = "checkbox" checked/>
        <div class = "collapse-title text-md font-medium">
            Rejected Status
        </div>
        <div class = "collapse-content">
            <div class = "form-control">
                <label class = "label cursor-pointer">
                    <input type = "checkbox" bind:this = {rejectedBoxes[0]} on:click = {mutexRejected} on:change = {handleFilterChange} class = "checkbox"/>
                    <span class ="label-text">Pending</span>
                </label>
            </div>
            <div class = "form-control">
                <label class = "label cursor-pointer">
                    <input type = "checkbox" bind:this = {rejectedBoxes[1]} on:click = {mutexRejected} on:change = {handleFilterChange} class = "checkbox"/>
                    <span class ="label-text">Rejected</span>
                </label>
            </div>
        </div>
    </div>
</div>
