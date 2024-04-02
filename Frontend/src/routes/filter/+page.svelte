<!-- DropdownCheckbox.svelte -->
<script>
    import CheckboxFilterField from './CheckboxFilterField.svelte'
    import getCheckedStates from './CheckboxFilterField.svelte'

    import DateFilterField from './DateFilterField.svelte'
    import onClick from 'svelte'
    let courseTypes = ["Swimming", "Weightlifting"]
    const daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    
    let checkedCourseTypes = getCheckedStates();
    let checkedDaysOffered = getCheckedStates();
    let apiString = '/courseOffering/getAll/filter?courseTypes='

    // @TODO FIX THIS
    function updateCheckedStates() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]');
        
        checkboxes.forEach((checkbox, index) => {
            if (checkbox instanceof HTMLInputElement && checkbox.type === 'checkbox') {
            const isChecked = checkbox.checked;
            const label = checkbox.parentNode.querySelector('label').innerText;

            if (label === 'Course Types') {
                checkedCourseTypes[index] = isChecked;
            } else if (label === 'Days Offered') {
                checkedDaysOffered[index] = isChecked;
            }
            }
        });
    }
    /* HOW TF DO I ACCESS INSTANCE VARIABLES?????? */
    // @TODO FINISH THIS
    /**
     * Takes all inputs and returns a string to be sent to the backend
     * @param {void}
     * @return {void} 
    */
    function apiStringBuilder() {
        updateCheckedStates();
        if (checkedCourseTypes != null) {
            checkedCourseTypes.forEach((courseTypeIncluded, index) => {
                if (courseTypeIncluded) {
                    apiString = apiString + courseTypes[index] + ",";
                }
            })
        }

        if (!checkedDaysOffered) {
            apiString = apiString + "&daysOffered="
            checkedDaysOffered.forEach((day) => {
                apiString = apiString + day + ","
            })
        }

        if(!)


    }
    // @TODO FIGURE OUT HOW TO SEND REQUEST TO BACKEND AND SAME FOR COMPONENTS
</script>

<div class="container">
    <CheckboxFilterField fieldTitle="Course Types" />
    <CheckboxFilterField fieldTitle="Days Offered" params={daysOfWeek} />
    <!-- need to do a calendar picker for these two. Idk how-->
    <DateFilterField fieldTitle="Start Date" />
    <DateFilterField fieldTitle="End Date" />
</div>
<!-- @TODO FIGURE THIS SHIT OUT-->
<button on:click={apiStringBuilder}>Apply!</button>

<style>
    .container {
        max-width: fit-content;
        max-height: fit-content;
    }
</style>