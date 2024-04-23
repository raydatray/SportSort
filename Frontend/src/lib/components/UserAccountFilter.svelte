<script>
    import { createEventDispatcher} from "svelte";

    const userTypes = ['CUSTOMER', 'INSTRUCTOR', 'OWNER']
    const dispatch = createEventDispatcher();

    let userTypeCheckboxes = [];
    function handleFilterChange() {
        let selectedUserTypes = userTypeCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);

        let params = {
            userTypes: selectedUserTypes
        }
        dispatch('filterChange', params);
        console.log(params);
    }
</script>

<div class = "flex col space-y-.5">
    <div class = "join join-vertical">
        <div class = "text-lg font-medium bg-base-200 border-2xl">Filters</div>
        <div class = "collapse collapse-arrow bg-base-200">
            <input type = "checkbox" checked/>
            <div class = "collapse-title text-md font-medium">
                User Types
            </div>
            <div class = "collapse-content">
                {#each userTypes as userType, index}
                    <label class = "form-control">
                        <label class = "label cursor-pointer">
                            <input type = "checkbox" bind:this={userTypeCheckboxes[index]} bind:value={userType} on:change = {handleFilterChange} class = "checkbox"/>
                            <span class = "label-text">{userType}</span>
                        </label>
                    </label>
                {/each}
            </div>
        </div>
    </div>
</div>