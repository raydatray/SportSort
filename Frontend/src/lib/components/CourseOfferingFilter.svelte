<script>
    import {onMount, createEventDispatcher} from "svelte";
    import axios from 'axios';

    let courseTypes = [];
    let rooms = [];
    let instructors = [];

    const daysOfWeek = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
    const dispatch = createEventDispatcher();

    onMount(async () =>{
        try {
            const getInstructors = await axios.get('http://localhost:8080/accounts/getInstructors');
            instructors = getInstructors.data;

            const getCourseTypes = await axios.get('http://localhost:8080/courseTypes/getAllApproved');
            courseTypes = getCourseTypes.data;
            console.log(courseTypes);

            const getRooms = await axios.get('http://localhost:8080/rooms/getAll')
            rooms = getRooms.data;
        } catch (error) {
            console.log(error)
        }
    })

    let courseTypeCheckboxes = [];
    let roomCheckboxes = [];
    let instructorCheckboxes = [];
    let daysCheckboxes = [];

    let startDate;
    let endDate;
    let startTime;
    let endTime;
    let price = 1000;

    function handleFilterChange() {
        let selectedCourseTypes = courseTypeCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);
        let selectedDays = daysCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);
        let selectedInstructors = instructorCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);
        let selectedRooms = roomCheckboxes.filter(checkbox => checkbox.checked).map(checkbox => checkbox.value);



        let params = {
            hP: price,
            dO: selectedDays,
            iI: selectedInstructors,
            cT: selectedCourseTypes,
            rId: selectedRooms
        }

        if (startDate) params.lD = startDate;
        if (endDate) params.hD = endDate;
        if (startTime) params.lT = startTime;
        if (endTime) params.hT = endTime;

        dispatch('filterChange', params);
        console.log(params);
    }

</script>

<div class = "flex-col space-y-.5">
    <div class = "join join-vertical">
    <div class = "p-4 text-lg font-medium join-item bg-base-200 rounded-3xl">Filters</div>
    <div class = "join-item collapse collapse-arrow bg-base-200">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Course Types
        </div>
        <div class = "collapse-content">
            {#each courseTypes as courseType}
                <label class = "flex form-control">
                    <label class = "justify-start cursor-pointer label gap-x-3">
                        <input type = "checkbox" bind:this={courseTypeCheckboxes[courseType.id]} bind:value={courseType.id} on:change = {handleFilterChange} class = "checkbox"/>
                        <span class = "label-text">{courseType.courseName}</span>
                    </label>
                </label>
            {/each}
        </div>
    </div>

    <div class = "join-item collapse collapse-arrow bg-base-200">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Price
        </div>
        <div class = "collapse-content">
            <form class = "grid justify-center grid-flow-col space-x-5 auto-cols-max" >
                <div>
                    <input type = "range"  min = "0" max = "1000" bind:value={price} on:change = {handleFilterChange} class = "range"/>
                </div>
                <div>
                    <input type = "number"  min = "0" max = "1000" bind:value={price} on:change = {handleFilterChange} class = "w-full max-w-xs input input-bordered input-xs"/>
                </div>
            </form>

        </div>
    </div>

    <div class = "join-item collapse collapse-arrow bg-base-200">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Days Offered
        </div>
        <div class = "collapse-content">
            {#each daysOfWeek as day, index}
                <div class = "form-control">
                    <label class = "justify-start cursor-pointer label gap-x-3">
                        <input type = "checkbox" bind:this={daysCheckboxes[index]} bind:value={day} on:change = {handleFilterChange} class = "checkbox"/>
                        <span class ="label-text">{day}</span>
                    </label>
                </div>
            {/each}
        </div>
    </div>

    <div class = "join-item collapse collapse-arrow bg-base-200">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Course Dates
        </div>
        <div class = "collapse-content">
            <form class = "grid justify-center grid-flow-row space-y-5 auto-rows-max" >
                <div>
                    <input type = "date" bind:value={startDate} on:change={handleFilterChange} class = "w-full max-w-xs input input-bordered input-sm"/>
                </div>
                <div>
                    <input type = "date" bind:value={endTime} on:change={handleFilterChange} class = "w-full max-w-xs input input-bordered input-sm"/>
                </div>
            </form>
        </div>
    </div>

    <div class = "join-item collapse collapse-arrow bg-base-200">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Course Times
        </div>
        <div class = "collapse-content">
            <form class = "grid justify-center grid-flow-row space-y-5 auto-rows-max" >
                <div>
                    <input type = "time" bind:value={startTime} on:change={handleFilterChange} class = "w-full max-w-xs input input-bordered input-sm"/>
                </div>
                <div>
                    <input type = "time" bind:value={endTime} on:change={handleFilterChange} class = "w-full max-w-xs input input-bordered input-sm"/>
                </div>
            </form>
        </div>
    </div>

    <div class = "join-item collapse collapse-arrow bg-base-200">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Instructors
        </div>
        <div class = "collapse-content">
            {#each instructors as instructor}
                <div class = "form-control">
                    <label class = "justify-start cursor-pointer label gap-x-3">
                        <input type = "checkbox" bind:this={instructorCheckboxes[instructor.id]} bind:value={instructor.id} on:change = {handleFilterChange} class = "checkbox"/>
                        <span class ="label-text">{instructor.name}</span>
                    </label>
                </div>
            {/each}
        </div>
    </div>

    <div class = "join-item collapse collapse-arrow bg-base-200 rounded-3xl">
        <input type = "checkbox"/>
        <div class = "font-medium collapse-title text-md">
            Rooms
        </div>
        <div class = "collapse-content">
            {#each rooms as room}
                <label class = "form-control">
                    <label class = "justify-start cursor-pointer label gap-x-3">
                        <input type = "checkbox" bind:this={roomCheckboxes[room.id]} bind:value = {room.id} on:change = {handleFilterChange} class = "checkbox"/>
                        <span class = "label-text">{room.name}</span>
                    </label>
                </label>
            {/each}
        </div>
    </div>
    </div>
</div>