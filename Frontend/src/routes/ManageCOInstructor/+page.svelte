<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    let userToken = sessionStorage.getItem("token");

    const backendUrl = 'http://127.0.0.1:8080/';
    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });


    let errorType;
    /**
     * An array of items.
     * @type {string}
     */
    let selectedCourse = "";
    /**
     * An array of items.
     * @type {string}
     */
    let startDate = "";
    /**
     * An array of items.
     * @type {string}
     */
    let endDate = "";
    /**
     * An array of items.
     * @type {string[]}
     */
    let startTime = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let endTime = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let sessions = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let daysOffered = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let taughtCourses = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let taughtCoursesStartDate = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let taughtCoursesEndDate = [];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesSessionsStart = [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesSessionsEnd = [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesDaysOffered = [[]];
    /**
     * An array of items.
     * @type {number[]}
     */
    let taughtCoursesID = [];

    /**
     * Handles the check button click event.
     * @param {number} courseTypeId - The type of course to be approved.
     * @returns {Promise<string>} - The ID of the clicked item.
     */
    async function getCourseName(courseTypeId) {
        try {
            const response = await AXIOS.get(`/courseTypes/get?id=${courseTypeId}`, {
                headers: {'userToken': userToken}
            });
            const resp = response.data;
            return resp.courseName;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }

    /**
     * Handles the check button click event.
     * @param {number} courseId - The type of course to be approved.
     * @returns {Promise<string[]>} - The ID of the clicked item.
     */
    async function getSessionsStart(courseId) {
        try {
            const response = await AXIOS.get(`/courseSessions/getByOffering?courseOfferingId=${courseId}`, {});
            const answer = [];
            for (let i = 0; i < response.data.length; i++) {
                answer.push(response.data[i].startTime);
            }
            return answer;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }

    /**
     * Handles the check button click event.
     * @param {number} courseId - The type of course to be approved.
     * @returns {Promise<string[]>} - The ID of the clicked item.
     */
    async function getSessionsEnd(courseId) {
        try {
            const response = await AXIOS.get(`/courseSessions/getByOffering?courseOfferingId=${courseId}`, {});
            const answer = [];
            for (let i = 0; i < response.data.length; i++) {
                answer.push(response.data[i].endTime);
            }
            return answer;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }

    /**
     * Handles the check button click event.
     * @param {string} taughtCourse - The type of course to be approved.
     * @returns {void} - The ID of the clicked item.
     */
    function handleCourseClick(taughtCourse) {
        selectedCourse = taughtCourse;
        const index = taughtCourses.indexOf(taughtCourse);
        startDate = taughtCoursesStartDate[index];
        endDate = taughtCoursesEndDate[index];
        daysOffered = taughtCoursesDaysOffered[index]
        startTime = taughtCoursesSessionsStart[index];
        endTime = taughtCoursesSessionsEnd[index];
    }

    onMount(async () => {
        AXIOS.get('courseOfferings/getByInstructor', {
            headers: {
                'userToken': userToken
            }
        })
            .then(async response => {
                const courses = response.data;
                const coursesList = [];
                const courseListStartDate = [];
                const courseListEndDate = [];
                const coursesListID = [];
                const coursesListDaysOffered = [];
                const coursesListSessionsStart = [];
                const coursesListSessionsEnd = [];


                for (let i = 0; i < courses.length; i++) {
                    const course = courses[i];
                    const courseName = await getCourseName(course.courseTypeId);
                    const courseSessionsStart = await getSessionsStart(course.id);
                    const courseSessionsEnd = await getSessionsEnd(course.id);

                    coursesList.push(courseName);
                    courseListStartDate.push(course.startDate);
                    courseListEndDate.push(course.endDate);
                    coursesListID.push(course.id);
                    coursesListDaysOffered.push(course.daysOffered);
                    coursesListSessionsStart.push(courseSessionsStart);
                    coursesListSessionsEnd.push(courseSessionsEnd);

                }

                taughtCourses = coursesList; // Update items array with the new values
                taughtCoursesStartDate = courseListStartDate;
                taughtCoursesEndDate = courseListEndDate;
                taughtCoursesID = coursesListID;
                taughtCoursesDaysOffered = coursesListDaysOffered;
                taughtCoursesSessionsStart = coursesListSessionsStart;
                taughtCoursesSessionsEnd = coursesListSessionsEnd;

            })
            .catch(e => {
                errorType = e;
            });
    });
</script>

<div class="grid-container">
    <div class="component-container component-1">
        <div class="list-container-left">
            <div class="bg-secondary/20 list-header-left-bg"> <!-- Background wrapper for list header -->
                <h1 class="list-header-left">Taught Courses</h1>
            </div>
            <div class="scrollable-list-left">
                <!-- Render list items -->
                {#each taughtCourses as taughtCourse}
                    <div class="bg-secondary-content/5 list-item-left" on:click={() => handleCourseClick(taughtCourse)}>
                        {taughtCourse}
                    </div>
                {/each}
            </div>
        </div>
    </div>
    <div class="component-container component-2">
        <div class="list-container-left">
            <div class="bg-secondary/20 list-header-left-bg"> <!-- Background wrapper for list header -->
                <h1 class="list-header-left">
                    {#if selectedCourse !== ""}
                        {selectedCourse}
                    {:else}
                        <!-- Display something when nothing is hovered over -->
                        No course selected
                    {/if}
                </h1>
            </div>
            <div class="bg-secondary-content/5 info-blocks">
                Start Date: {startDate}
            </div>
            <div class="bg-secondary-content/5 info-blocks">
                End Date: {endDate}
            </div>
            <div class="bg-secondary-content/5 info-blocks">
                Days Offered: {daysOffered}
            </div>
        </div>
    </div>
    <div class="component-container component-3">
        <div class="list-container-left">
            <div class="bg-secondary/20 list-header-left-bg"> <!-- Background wrapper for list header -->
                <h1 class="list-header-left">
                    {#if selectedCourse !== ""}
                        {selectedCourse} Sessions
                    {:else}
                        <!-- Display something when nothing is hovered over -->
                        No course selected
                    {/if}
                </h1>
            </div>
            <div class="bg-secondary-content/5 info-blocks">
                Start Times: {startTime}
            </div>
            <div class="bg-secondary-content/5 info-blocks">
                End Times: {endTime}
            </div>
        </div>
    </div>
</div>

<style>
    .grid-container {
        display: grid;
        grid-template-columns: repeat(4, 1fr); /* Three columns with equal width */
        grid-template-rows: repeat(4, 1fr); /* Three rows with equal height */
        gap: 10px; /* Gap between grid items */
        padding-top: 0.5vh;
        padding-bottom: 10px;
        height: 100%; /* Occupy full height of the main element */
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
        grid-row: span 4; /* Span three rows */
    }

    .component-2 {
        grid-column: span 2; /* Third column */
        grid-row: span 2; /* Span three rows */
    }

    .component-3 {
        grid-column: span 2; /* Third column */
        grid-row: span 2; /* Span three rows */
    }

    /* Add CSS styles for the list container */
    .list-container-left {
        padding-top: 1%; /* Add padding to the top of the container */
        padding-bottom: 1%; /* Add padding to the bottom of the container */
        width: 100%;
        height: 100%;
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list-left {
        max-height: 60vh; /* Limit the height of the list */
        max-width: 100%;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding-left: 10px;
        padding-right: 10px;
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: flex-start; /* Center items horizontally */
        list-style-type: none; /* Remove list-style (bullets) */
    }

    /* Style for list items */
    .list-item-left {
        margin-bottom: 5px;
        border-radius: 10px; /* Rounded corners for the highlight box */
        padding: 5px; /* Add left padding to create space */
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        width: 100%; /* Ensure header spans the full width */
        display: flex; /* Use flexbox */
        justify-content: space-between; /* Align content on each end */
        align-items: center; /* Center items vertically */
        font-size: 2.1vh;
    }

    /* Apply background color when hovering over list item */
    .list-item-left:hover {
        font-size: 2.4vh; /* Increase font size */
    }

    /* Style for list header */
    .list-header-left {
        font-size: 2.7vh;
        text-align: center; /* Center the header text horizontally */
        width: 100%; /* Ensure header spans the full width */
    }

    .list-header-left-bg {
        width: 75%; /* Set the width to occupy 75% of the space */
        margin: 0 auto; /* Center the header horizontally */
        margin-top: 10px;
        margin-bottom: 10px;
        border-radius: 5px; /* Rounded corners */
    }

    .info-blocks {
        width: 98%;
        border-radius: 10px;
        margin: 5px;
        padding: 5px;
        font-size: 2.1vh;
    }
</style>