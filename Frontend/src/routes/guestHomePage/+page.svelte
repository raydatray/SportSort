<script>
    import Calendar from '@event-calendar/core';
    import TimeGrid from '@event-calendar/time-grid';
    import '@event-calendar/core/index.css';

    import axios from 'axios';
    import qs from 'qs';
    import { onMount } from 'svelte';

    import CourseOfferingFilter from "$lib/components/CourseOfferingFilter.svelte";
    import CourseOfferingRegistrationModal from "$lib/components/CourseOfferingRegistrationModal.svelte";

    let courseOfferings = [];
    let courseSessions = new Map();
    let courseOfferingsAsResources = [];
    let courseSessionsAsEvents = [];

    let plugins = [TimeGrid];
    let dataLoaded = false; // Step 1: Define a loading state
    $: options = {
        allDaySlot: false,
        slotMinTime: '07:00:00',
        slotMaxTime: '18:00:00',
        view: 'timeGridWeek',
        events: courseSessionsAsEvents,
        resources: courseOfferingsAsResources
    };

    onMount(async () => {
        try {
            const getOfferings = await axios.get('http://localhost:8080/courseOfferings/getAll');
            courseOfferings = getOfferings.data;

            courseOfferingsAsResources = courseOfferings.map(offering => ({
                id: offering.id,
                title: offering.title // Assuming you replace 'abcd' with offering.title or another property
            }));

            const courseSessionsPromises = courseOfferings.map(async (offering) => {
                const getSessions = await axios.get('http://localhost:8080/courseSessions/getByOffering', {
                    params: {
                        courseOfferingId: offering.id
                    }
                });
                courseSessions.set(offering.id, getSessions.data);

                return getSessions.data.map(session => {
                    let start = new Date(session.date + 'T' + session.startTime);
                    let end = new Date(session.date + 'T' + session.endTime);

                    let startLocal = new Date(start.getTime() - start.getTimezoneOffset() * 60000).toISOString().slice(0, -1);
                    let endLocal = new Date(end.getTime() - end.getTimezoneOffset() * 60000).toISOString().slice(0, -1);

                    return {
                        id: session.id,
                        resourceId: offering.id,
                        start: startLocal,
                        end: endLocal
                    };
                });
            });

            courseSessionsAsEvents = (await Promise.all(courseSessionsPromises)).flat(); // Make sure to flatten the array
            dataLoaded = true; // Step 2: Update the loading state
            console.log(courseSessionsAsEvents);
        } catch (error) {
            console.error(error);
        }
    })

    async function handleFilterChange(event) {
        const params = event.detail;
        console.log(params);

        try {
            const getOfferings = await axios.get('http://localhost:8080/courseOfferings/getAll', {
                params: params,
                paramsSerializer: params => qs.stringify(params, {arrayFormat: 'repeat'})
            });

            // Create a new array instead of modifying the existing one
            courseOfferings = [...getOfferings.data];

            // Create a new array for courseOfferingsAsResources
            courseOfferingsAsResources = courseOfferings.map(offering => ({
                id: offering.id,
                title: offering.title
            }));

            const courseSessionsPromises = courseOfferings.map(async (offering) => {
                const getSessions = await axios.get('http://localhost:8080/courseSessions/getByOffering', {
                    params: {
                        courseOfferingId: offering.id
                    }
                });

                return getSessions.data.map(session => {
                    let start = new Date(session.date + 'T' + session.startTime);
                    let end = new Date(session.date + 'T' + session.endTime);

                    let startLocal = new Date(start.getTime() - start.getTimezoneOffset() * 60000).toISOString().slice(0, -1);
                    let endLocal = new Date(end.getTime() - end.getTimezoneOffset() * 60000).toISOString().slice(0, -1);

                    return {
                        id: session.id,
                        resourceId: offering.id,
                        start: startLocal,
                        end: endLocal
                    };
                });
            });

            // Create a new array for courseSessionsAsEvents
            courseSessionsAsEvents = (await Promise.all(courseSessionsPromises)).flat();
            dataLoaded = true;
            console.log(courseSessionsAsEvents);
        } catch (error) {
            console.error(error);
        }
    }

    let showModal = false;
    let selectedCourseOffering = null;
    let associatedCourseSessions = null;

    function openModal(courseOffering) {
        console.log("u clicked something.");
        selectedCourseOffering = courseOffering;
        associatedCourseSessions = courseSessions.get(courseOffering.id);
        console.log(courseSessions)
        console.log(associatedCourseSessions);
        showModal = true;
    }
</script>
<div>
    <h1 class="p-4 text-lg font-medium bg-base-200 rounded-box">Course Offerings</h1>
    <div class= "grid grid-cols-[min-content_1fr] gap-4 pt-4">
        <CourseOfferingFilter on:filterChange={handleFilterChange}/>
        <div class="grid h-full grid-rows-2 gap-4">
            <div class = "overflow-y-scroll">
                {#if dataLoaded}
                    <Calendar {plugins} {options} />
                {:else}
                    <p>Loading...</p>
                {/if}
            </div>
            <div class = "overflow-x-auto">
                <table class = "table">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Course Type</th>
                            <th>Days Offered</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                    {#each courseOfferings as offering, index}
                            <tr on:click={() => openModal(offering)} class="cursor-pointer">
                                <th>{index + 1}</th>
                                <td>{offering.title}</td>
                                <td>{offering.daysOffered}</td>
                                <td>{offering.startDate}</td>
                                <td>{offering.endDate}</td>
                                <td>{offering.price}</td>
                            </tr>
                        {/each}
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

{#if showModal}
    <CourseOfferingRegistrationModal {selectedCourseOffering} {associatedCourseSessions} onClose={() => showModal = false}/>
{/if}
<style>
    :root {
        --ec-text-color: #333; /* Text color */
        --ec-bg-color: oklch(95.1276% 0.007445 260.731539 /1); /* Background color */
        --ec-border-color: #ddd; /* Border color */
        --ec-accent-color: #007bff; /* Accent color for elements like buttons or active states */
        --ec-button-bg-color: #f8f9fa; /* Button background color */
        --ec-button-border-color: #ddd; /* Button border color */
        --ec-button-text-color: #333; /* Button text color */
        --ec-button-active-bg-color: #007bff; /* Button background color when active/hovered */
        --ec-button-active-border-color: #0056b3; /* Button border color when active/hovered */
        --ec-button-active-text-color: #fff; /* Button text color when active/hovered */
        --ec-today-bg-color: #eaf3ff; /* Background color for today's date */
        --ec-highlight-color: #ffff88; /* Background color for highlighted elements */
        --ec-event-text-color: #333; /* Text color for events */
        --ec-event-bg-color: #a991f7; /* Background color for events */
        --ec-hs: 210, 50; /* Hue-saturation for shadows (used in box-shadow HSLA) */
        --ec-now-indicator-color: #ff0000; /* Color for the current time indicator */
        --ec-popup-bg-color: #f9f9f9; /* Background color for popups */
        --ec-list-day-bg-color: #f0f0f0; /* Background color for list days */
        --ec-bg-event-color: #f0f0f0; /* Background color for background events */
        --ec-bg-event-opacity: 0.5; /* Opacity for background events */
    }

    .calendar-container {
        max-height: 100%; /* Adjust the height as needed */
        overflow-y: scroll; /* This will add a scrollbar when the content overflows */
        border-radius: 1rem;
    }

    /* Ensure internal elements are set to 100% width for responsive scaling */
    :global(.ec) {
        width: 100%;
    }

    /* ... your existing styles ... */
</style>