<!-- DropdownCheckbox.svelte -->
<script>


    import CheckboxFilterField from './CheckboxFilterField.svelte'
    //import getCheckedStates from './CheckboxFilterField.svelte'

    import DateFilterField from './DateFilterField.svelte'

    let courseTypes = ['Swimming', 'Weightlifting']
    const daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

    let checkedCourseTypes;
    let checkedDaysOffered;
    let apiString = '/courseOffering/getAll/filter?courseTypes='

    let startDate;
    let endDate;
    /*
    // @TODO FIX THIS
    function updateCheckedStates() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]');

        checkboxes.forEach((checkbox, index) => {
            if (checkbox instanceof HTMLInputElement && checkbox.type === 'checkbox') {
            const isChecked = checkbox.checked;
            // @ts-ignore
            const label = checkbox.parentNode.querySelector('label').innerText;

            if (label === 'Course Types') {
                checkedCourseTypes[index] = isChecked;
            } else if (label === 'Days Offered') {
                checkedDaysOffered[index] = isChecked;
            }
            }
        });
    }
    */
    /* HOW TF DO I ACCESS INSTANCE VARIABLES?????? */
    // @TODO FINISH THIS

   /*
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
            let allFalse = true;
            let tempString = '';
            checkedDaysOffered.forEach((dayIncluded, index) => {
                if (dayIncluded) {
                    tempString = apiString + daysOfWeek[index] + ",";
                    allFalse = false;
                }
            })
            if (!allFalse) {
                apiString = apiString + "&daysOffered=" + tempString;
            }
        }

        if () {

        }



    }
    */
    // @TODO FIGURE OUT HOW TO SEND REQUEST TO BACKEND AND SAME FOR COMPONENTS
    /**
     * Handles the event when a date is selected from a DateFilterField component.
     * @param {CustomEvent} event - The custom event containing the selected date and key.
     * @returns {void}
     */
    /*
    function handleDateSelected(event) {
        const { key, date } = event.detail;
        if (key === 'date1') {
            startDate = new Date(date);
        } else if (key === 'date2') {
            endDate = new Date(date);
        }
    }
    */
</script>

<div class="container overflow-y-auto">
    <CheckboxFilterField fieldTitle="Course Types" params={courseTypes}/>
    <CheckboxFilterField fieldTitle="Days Offered" params={daysOfWeek} />
    <!-- need to do a calendar picker for these two. Idk how-->

    <!-- <DateFilterField fieldTitle="Start Date" key="start" on:dateSelected={handleDateSelected} />
    <DateFilterField fieldTitle="End Date" key="end" on:dateSelected={handleDateSelected} /> -->

    <DateFilterField fieldTitle="Start Date" key="start" />
    <DateFilterField fieldTitle="End Date" key="end" />
</div>
<!-- @TODO FIGURE THIS SHIT OUT-->
<button class="btn btn-accent-content py-2">Apply Filters</button>

<style lang="postcss">
    .container {
        max-width: fit-content;
        max-height: fit-content;
        border-radius: 0.3125rem;
    }

    button {
        border-radius: 0.3125rem;
        text-align: center;
    }
</style>