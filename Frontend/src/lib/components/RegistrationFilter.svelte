<script>
    import { onMount, createEventDispatcher } from 'svelte';
    import { IconSearch } from '@tabler/icons-svelte';
    import axios from 'axios';

    let courseOfferings = [];

    const dispatch = createEventDispatcher();

    onMount(async () => {
        try {
            const getCourseOfferings = await axios.get('http://localhost:8080/courseOfferings/getAll')
            courseOfferings = getCourseOfferings.data;
        } catch (error) {
            console.error(error);
        }
    });

    let courseOfferingCheckboxes = [];
    let searchEmail = '';
    let startDate;
    let endDate;

    function handleFilterChange() {
        let selectedCourseOfferings = courseOfferingCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);

        let params = {
            l: startDate || null,
            h: endDate || null,
            id: selectedCourseOfferings,
            email: searchEmail || null
        }

        dispatch('filterChange', params);
        console.log(params);

    }


</script>

<div class = "flex-col space-y-.5">
    <div class = "join join-vertical">
        <div class = "text-lg font-medium bg-base-200 border-2xl">Filters</div>

        <div class = "collapse collapse-arrow bg-base-200">
            <input type = "checkbox"/>
            <div class = "collapse-title text-md font-medium">
                Customer Email
            </div>
            <div class = "collapse-content">
                <label class = "input input-bordered flex items-center gap-2">
                    <input type = "text" bind:value={searchEmail} on:input={handleFilterChange} class = "grow" placeholder = "Customer Email"/>
                    <IconSearch/>
                </label>
            </div>
        </div>

        <div class = "collapse collapse-arrow bg-base-200">
            <input type = "checkbox"/>
            <div class = "collapse-title text-md font-medium">
                Course Offerings
            </div>
            <div class = "collapse-content">
                {#each courseOfferings as courseOffering}
                    <label class = "form-control">
                        <label class = "label cursor-pointer">
                            <input type = "checkbox" bind:this={courseOfferingCheckboxes[courseOffering.id]} bind:value={courseOffering.id} on:change = {handleFilterChange} class = "checkbox"/>
                            <span class = "label-text">{courseOffering.id}</span>
                        </label>
                    </label>
                {/each}
            </div>
        </div>


        <div class = "collapse collapse-arrow bg-base-200">
            <input type = "checkbox"/>
            <div class = "collapse-title text-md font-medium">
                Registered Dates
            </div>
            <div class = "collapse-content">
                <form class = "grid grid-flow-col auto-cols-max space-x-5 justify-center" >
                    <div>
                        <input type = "date" bind:value={startDate} on:change={handleFilterChange} class = "input input-bordered input-sm w-full max-w-xs"/>
                    </div>
                    <div>
                        <input type = "date" bind:value={endDate} on:change={handleFilterChange} class = "input input-bordered input-sm w-full max-w-xs"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>