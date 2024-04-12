<script>
    import {onMount} from 'svelte';
    import axios from 'axios';
    let userToken = sessionStorage.getItem("token");

    const backendUrl = 'http://127.0.0.1:8080/';
    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: {'Access-Control-Allow-Origin': 'http://localhost:5173/'}
    });

    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, '0'); // Month is zero-indexed, so add 1
    let day = String(today.getDate()).padStart(2, '0');
    let currentDate = `${year}-${month}-${day}`;
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
    let selectedType = "";
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
     * @type {number}
     */
    let priceNew = 0;
    /**
     * An array of items.
     * @type {number}
     */
    let floorNumberNew = 0;
    /**
     * An array of items.
     * @type {number}
     */
    let roomNumberNew = 0;
    /**
     * An array of items.
     * @type {number}
     */
    let roomIdNew = 0;
    /**
     * An array of items.
     * @type {number}
     */
    let courseTypeNewId = 0;
    /**
     * An array of items.
     * @type {string}
     */
    let startDateNew = currentDate;
    /**
     * An array of items.
     * @type {string}
     */
    let endDateNew = currentDate;
    /**
     * An array of items.
     * @type {string}
     */
    let courseTypeNew = "";
    /**
     * An array of items.
     * @type {string}
     */
    let newSessionDate = currentDate;
    /**
     * An array of items.
     * @type {string}
     */
    let newSessionStartTime = "12:30:00";
    /**
     * An array of items.
     * @type {string}
     */
    let newSessionEndTime = "12:30:00";
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
    let sessionDate = [];
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
    let daysOfferedNew = [];
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
    let newSessions = [];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesSessionsEnd = [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesSessionsDate = [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesDaysOffered = [[]];
    /**
     * An array of items.
     * @type {string[]}
     */
    let approvedTypes = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let approvedTypesID = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let taughtCoursesID = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let roomCapacities = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let roomsId = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let roomsFloorNumber = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let roomsName = [];

    let monday = daysOfferedNew.includes('MONDAY');
    let tuesday = daysOfferedNew.includes('TUESDAY');
    let wednesday = daysOfferedNew.includes('WEDNESDAY');
    let thursday = daysOfferedNew.includes('THURSDAY');
    let friday = daysOfferedNew.includes('FRIDAY');
    let saturday = daysOfferedNew.includes('SATURDAY');
    let sunday = daysOfferedNew.includes('SUNDAY');

    let showUpdateContentInfo = false;

    function toggleUpdateContentInfo() {
        showUpdateContentInfo = !showUpdateContentInfo;
    }

    /**
     * Handles updating the daysOffered list based on checkbox selection.
     * @param {string} day - The day being updated.
     * @returns {void}
     */
    function updateDaysOffered(day) {
        if (daysOfferedNew.includes(day)) {
            // Remove the day if it's already in the list
            daysOfferedNew = daysOffered.filter(d => d !== day);
        } else {
            // Add the day if it's not in the list
            daysOfferedNew.push(day);
        }
    }

    function resetUpdateContentInfo() {
        showUpdateContentInfo = !showUpdateContentInfo;
        // Reset input fields
        startDateNew = currentDate;
        endDateNew = currentDate;
        priceNew = 0;
        floorNumberNew = 0;
        roomNumberNew = 0;
        // Clear checkboxes
        monday = false;
        tuesday = false;
        wednesday = false;
        thursday = false;
        friday = false;
        saturday = false;
        sunday = false;
        courseTypeNew = "";
        courseTypeNewId = 0;
        newSessionEndTime = "12:30:00";
        newSessionStartTime = "12:30:00";
        newSessionDate = currentDate;
        newSessions = []
        roomIdNew = 0;
        daysOfferedNew = [];

    }

    /**
     * Handles updating the daysOffered list based on checkbox selection.
     * @param {string} newSessionDate
     * @param {string} newSessionStartTime
     * @param {string} newSessionEndTime
     * @returns {void}
     */
    function setSessionInfo(newSessionDate, newSessionStartTime, newSessionEndTime) {
        newSessions.push([newSessionDate, newSessionStartTime, newSessionEndTime]);
        newSessions = newSessions;
    }

    function confirmTester() {
        // Reset input fields
        console.log(startDateNew);
        console.log(endDateNew);
        console.log(priceNew);
        console.log(floorNumberNew);
        console.log(roomNumberNew)
        // Clear checkboxes
        console.log(monday);
        console.log(tuesday);
        console.log(wednesday);
        console.log(thursday);
        console.log(friday);
        console.log(saturday);
        console.log(sunday);
        // Clear dropdown selection
        console.log(courseTypeNew);
        // Clear daysOfferedNew
        console.log(daysOfferedNew);
        console.log(newSessionDate);
        console.log(newSessionStartTime);
        console.log(newSessionEndTime);
        console.log(newSessions)
        console.log(roomIdNew)
        console.log(courseTypeNewId)
    }

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
                //answer.push(" " + (i + 1) + " - " + response.data[i].endTime);
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
    async function getSessionsDate(courseId) {
        try {
            const response = await AXIOS.get(`/courseSessions/getByOffering?courseOfferingId=${courseId}`, {});
            const answer = [];
            for (let i = 0; i < response.data.length; i++) {
                answer.push(response.data[i].date);
                //answer.push(" " + (i + 1) + " - " + response.data[i].endTime);
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
        sessionDate = taughtCoursesSessionsDate[index];
    }

    /*function getRoomId() {
        for (let i = 0; i < roomsId.length; i++) {
            if (roomsFloorNumber[i] === floorNumberNew && roomCapacities[i] === roomNumberNew) {
                roomIdNew = roomsId[i]; // Return the room ID if the floor number matches
            }
        }
        // Return null if no matching room is found
        return null;
    }*/

    function getCourseTypeId() {
        for (let i = 0; i < approvedTypes.length; i++) {
            if (approvedTypes[i] === courseTypeNew) {
                courseTypeNewId = approvedTypesID[i];
            }
        }
        return null;
    }

    function confirmCreation() {
        //getRoomId()
        getCourseTypeId()
        confirmTester()
        const requestData = {
            "startDate": startDateNew,
            "endDate": endDateNew,
            "price": priceNew,
            "daysOffered": daysOfferedNew,
            "roomId": roomIdNew,
            "courseTypeId": courseTypeNewId
        };
        AXIOS.post("/courseOfferings/create", requestData, {
            headers: {
                'userToken': userToken
            }

        })
            .then(response => {
                setTimeout(() => {
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
                            const coursesListSessionsDate = [];


                            for (let i = 0; i < courses.length; i++) {
                                const course = courses[i];
                                const courseName = await getCourseName(course.courseTypeId);
                                const courseSessionsStart = await getSessionsStart(course.id);
                                const courseSessionsEnd = await getSessionsEnd(course.id);
                                const courseSessionsDate = await getSessionsDate(course.id);

                                coursesList.push(courseName);
                                courseListStartDate.push(course.startDate);
                                courseListEndDate.push(course.endDate);
                                coursesListID.push(course.id);
                                coursesListDaysOffered.push(course.daysOffered);
                                coursesListSessionsStart.push(courseSessionsStart);
                                coursesListSessionsEnd.push(courseSessionsEnd);
                                coursesListSessionsDate.push(courseSessionsDate);

                            }

                            taughtCourses = coursesList; // Update items array with the new values
                            taughtCoursesStartDate = courseListStartDate;
                            taughtCoursesEndDate = courseListEndDate;
                            taughtCoursesID = coursesListID;
                            taughtCoursesDaysOffered = coursesListDaysOffered;
                            taughtCoursesSessionsStart = coursesListSessionsStart;
                            taughtCoursesSessionsEnd = coursesListSessionsEnd;
                            taughtCoursesSessionsDate = coursesListSessionsDate;

                            console.log(taughtCoursesID);
                            const id = taughtCoursesID[taughtCoursesID.length - 1];
                            for (let i = 0; i < newSessions.length; i++) {
                                const requestDataSession = {
                                    "date": newSessions[i][0],
                                    "startTime": newSessions[i][1],
                                    "endTime": newSessions[i][2],
                                    "courseOfferingId": id
                                };
                                console.log(requestDataSession)
                                AXIOS.post("courseSessions/createSingle", requestDataSession, {
                                    headers: {
                                        'userToken': userToken
                                    }

                                })
                                    .then(response => {
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
                                                const coursesListSessionsDate = [];


                                                for (let i = 0; i < courses.length; i++) {
                                                    const course = courses[i];
                                                    const courseName = await getCourseName(course.courseTypeId);
                                                    const courseSessionsStart = await getSessionsStart(course.id);
                                                    const courseSessionsEnd = await getSessionsEnd(course.id);
                                                    const courseSessionsDate = await getSessionsDate(course.id);

                                                    coursesList.push(courseName);
                                                    courseListStartDate.push(course.startDate);
                                                    courseListEndDate.push(course.endDate);
                                                    coursesListID.push(course.id);
                                                    coursesListDaysOffered.push(course.daysOffered);
                                                    coursesListSessionsStart.push(courseSessionsStart);
                                                    coursesListSessionsEnd.push(courseSessionsEnd);
                                                    coursesListSessionsDate.push(courseSessionsDate);

                                                }

                                                taughtCourses = coursesList; // Update items array with the new values
                                                taughtCoursesStartDate = courseListStartDate;
                                                taughtCoursesEndDate = courseListEndDate;
                                                taughtCoursesID = coursesListID;
                                                taughtCoursesDaysOffered = coursesListDaysOffered;
                                                taughtCoursesSessionsStart = coursesListSessionsStart;
                                                taughtCoursesSessionsEnd = coursesListSessionsEnd;
                                                taughtCoursesSessionsDate = coursesListSessionsDate;

                                            })
                                            .catch(e => {
                                                errorType = e;
                                            });
                                    })
                                    .catch(e => {
                                        errorType = e;
                                    });
                            }
                            resetUpdateContentInfo();

                        })
                        .catch(e => {
                            errorType = e;
                        });
                }, 10); //  delay
            })
            .catch(e => {
                errorType = e;
            });
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
                const coursesListSessionsDate = [];


                for (let i = 0; i < courses.length; i++) {
                    const course = courses[i];
                    const courseName = await getCourseName(course.courseTypeId);
                    const courseSessionsStart = await getSessionsStart(course.id);
                    const courseSessionsEnd = await getSessionsEnd(course.id);
                    const courseSessionsDate = await getSessionsDate(course.id);

                    coursesList.push(courseName);
                    courseListStartDate.push(course.startDate);
                    courseListEndDate.push(course.endDate);
                    coursesListID.push(course.id);
                    coursesListDaysOffered.push(course.daysOffered);
                    coursesListSessionsStart.push(courseSessionsStart);
                    coursesListSessionsEnd.push(courseSessionsEnd);
                    coursesListSessionsDate.push(courseSessionsDate);

                }

                taughtCourses = coursesList; // Update items array with the new values
                taughtCoursesStartDate = courseListStartDate;
                taughtCoursesEndDate = courseListEndDate;
                taughtCoursesID = coursesListID;
                taughtCoursesDaysOffered = coursesListDaysOffered;
                taughtCoursesSessionsStart = coursesListSessionsStart;
                taughtCoursesSessionsEnd = coursesListSessionsEnd;
                taughtCoursesSessionsDate = coursesListSessionsDate;

            })
            .catch(e => {
                errorType = e;
            });

        AXIOS.get('/courseTypes/getAll', {
            headers: {
                'userToken': userToken
            }
        })
            .then(response => {
                const types = response.data;
                const updatedItems = [];
                const updatedItemsId = []
                const updatedItems1 = [];
                const updatedItems1Id = []

                for (let i = 0; i < types.length; i++) {
                    const type = types[i];
                    if (type.approvalStatus === true) {
                        updatedItems1.push(type.courseName);
                        updatedItems1Id.push(type.id);
                    }
                }

                approvedTypes = updatedItems1; // Update items array with the new values
                approvedTypesID = updatedItems1Id;
            })
            .catch(e => {
                errorType = e;
            });

        AXIOS.get('/rooms/getAll', {
            headers: {
                'userToken': userToken
            }
        })
            .then(response => {
                const rooms = response.data;
                const holderRooms = [];
                const holderRoomsId = []
                const holderFloor = []
                const holderName = []

                for (let i = 0; i < rooms.length; i++) {
                    const room = rooms[i];
                    holderRooms.push(room.capacity);
                    holderRoomsId.push(room.id);
                    holderFloor.push(room.floorNumber)
                    holderName.push(room.name)
                }

                roomCapacities = holderRooms; // Update items array with the new values
                roomsId = holderRoomsId;
                roomsFloorNumber = holderFloor
                roomsName = holderName
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
                <h1 class="list-header-left">Your Courses</h1>
            </div>
            <div class="scrollable-list-left">
                <!-- Render list items -->
                {#each taughtCourses as taughtCourse}
                    <div class="bg-secondary-content/5 list-item-left" on:click={() => handleCourseClick(taughtCourse)}>
                        {taughtCourse}
                    </div>
                {/each}
            </div>
            <div class="button-container">
                <button class="btn btn-sm" on:click={() => {if (!showUpdateContentInfo) toggleUpdateContentInfo()}}>
                    Create New Course
                </button>
            </div>
        </div>
    </div>
    <div class="component-container component-2">
        {#if showUpdateContentInfo}
            <!-- Content shown when update button is clicked -->
            <div class="list-container-left">
                <div class="bg-accent/40 list-header-left-bg"> <!-- Background wrapper for list header -->
                    <h1 class="list-header-left">
                        Create New Course
                    </h1>
                </div>
                <!-- Input fields for Start Date and End Date -->
                <div class="bg-secondary-content/5 input-container">
                    <label for="start-date">Start Date:</label>
                    <input class="bg-secondary-content/5" type="date" id="start-date" name="start-date"
                           bind:value={startDateNew}>
                </div>
                <div class="bg-secondary-content/5 input-container">
                    <label for="end-date">End Date:</label>
                    <input class="bg-secondary-content/5" type="date" id="end-date" name="end-date"
                           bind:value={endDateNew}>
                </div>
                <div class="bg-secondary-content/5 input-container">
                    <label for="price">Price:</label>
                    <input class="bg-secondary-content/5" type="number" id="price" name="price" bind:value={priceNew}
                           min="0">
                </div>
                <!-- Input fields for Floor Number and Room Number -->
                <!-- Dropdown for Room Selection -->
                <div class="bg-secondary-content/5 input-container">
                    <label for="room-dropdown">Room Selection:</label>
                    <select class="bg-secondary-content/5" id="room-dropdown" bind:value={roomIdNew}>
                        {#each roomsId as id, index}
                            <option value={id}>
                                Name: {roomsName[index]} | Floor Number: {roomsFloorNumber[index]} |
                                Capacity: {roomCapacities[index]}
                            </option>
                        {/each}
                    </select>
                </div>
                <!-- Input fields for Days Offered -->
                <div class="bg-secondary-content/5 input-container-checkboxes">
                    <label>Days Offered:</label>
                    <div class="days-offered-container">
                        <label>M <input type="checkbox" class="checkbox checkbox-styling" bind:checked={monday}
                                        on:change={() => updateDaysOffered('MONDAY')}></label>
                        <label>Tu <input type="checkbox" class="checkbox checkbox-styling" bind:checked={tuesday}
                                         on:change={() => updateDaysOffered('TUESDAY')}></label>
                        <label>W <input type="checkbox" class="checkbox checkbox-styling" bind:checked={wednesday}
                                        on:change={() => updateDaysOffered('WEDNESDAY')}></label>
                        <label>Th <input type="checkbox" class="checkbox checkbox-styling" bind:checked={thursday}
                                         on:change={() => updateDaysOffered('THURSDAY')}></label>
                        <label>F <input type="checkbox" class="checkbox checkbox-styling" bind:checked={friday}
                                        on:change={() => updateDaysOffered('FRIDAY')}></label>
                        <label>Sa <input type="checkbox" class="checkbox checkbox-styling" bind:checked={saturday}
                                         on:change={() => updateDaysOffered('SATURDAY')}></label>
                        <label>Su <input type="checkbox" class="checkbox checkbox-styling" bind:checked={sunday}
                                         on:change={() => updateDaysOffered('SUNDAY')}></label>
                    </div>
                </div>
                <!-- Dropdown for Approved Types -->
                <div class="bg-secondary-content/5 input-container">
                    <label for="approved-types-dropdown">Course Type:</label>
                    <select class="bg-secondary-content/5" id="approved-types-dropdown" bind:value={courseTypeNew}>
                        {#each approvedTypes as type}
                            <option value={type}>{type}</option>
                        {/each}
                    </select>
                </div>
            </div>
        {:else}
            <!-- Default content -->
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
        {/if}
    </div>
    <div class="component-container component-3">
        {#if showUpdateContentInfo}
            <!-- Content when update button is clicked -->
            <div class="list-container-left">
                <div class="bg-accent/40 list-header-left-bg">
                    <h1 class="list-header-left">
                        Add Sessions To New Course
                    </h1>
                </div>
                <!-- Input fields for Date, Start Time, and End Time -->
                <div class="center-container">
                    <div class="input-container-session">
                        <label for="session-date">Date:</label>
                        <input class="bg-secondary-content/5" type="date" id="session-date" name="session-date"
                               bind:value={newSessionDate}>
                    </div>
                    <div class="input-container-session">
                        <label for="start-time">Start Time:</label>
                        <input class="bg-secondary-content/5" type="time" id="start-time" name="start-time"
                               bind:value={newSessionStartTime} step="1">
                    </div>
                    <div class="input-container-session">
                        <label for="end-time">End Time:</label>
                        <input class="bg-secondary-content/5" type="time" id="end-time" name="end-time"
                               bind:value={newSessionEndTime} step="1">
                    </div>
                    <div class="input-container-session">
                        <button class="btn btn-sm"
                                on:click={() => setSessionInfo(newSessionDate, newSessionStartTime, newSessionEndTime)}>
                            Confirm Session
                        </button>
                    </div>
                </div>
                <div class="scrollable-list-right-sessions">
                    {#each newSessions as session}
                        <div class="bg-secondary-content/5 info-blocks">
                            <div class="time-info">
                                <span>Date: {session[0]}</span>
                                <span>Start Time: {session[1]}</span>
                                <span>End Time: {session[2]}</span>
                            </div>
                        </div>
                    {/each}
                </div>
                <!-- Button container -->
                <div class="button-container">
                    <button class="bg-green-500/65 hover:bg-green-500/80 btn btn-sm" on:click={() => confirmCreation()}>
                        Confirm
                    </button>
                    <button class=" btn btn-sm bg-red-500/65 hover:bg-red-500/80"
                            on:click={() => resetUpdateContentInfo()}>Cancel
                    </button>
                </div>
            </div>
        {:else}

            <!-- Default content -->
            <div class="list-container-left">
                <div class="bg-secondary/20 list-header-left-bg">
                    <h1 class="list-header-left">
                        {#if selectedCourse !== ""}
                            {selectedCourse} Sessions
                        {:else}
                            No course selected
                        {/if}
                    </h1>
                </div>
                <div class="scrollable-list-right">
                    {#if startTime.length === endTime.length}
                        {#each startTime as time, index}
                            <div class="bg-secondary-content/5 info-blocks">
                                <div class="time-info">
                                    <span>Date: {sessionDate[index]}</span>
                                    <span>Start Time: {time}</span>
                                    <span>End Time: {endTime[index]}</span>
                                </div>
                            </div>
                        {/each}
                    {:else}
                        <div class="bg-secondary-content/5 info-blocks">
                            Error: Start time and end time arrays have different lengths.
                        </div>
                    {/if}
                </div>
            </div>
        {/if}
    </div>
</div>

<style>
    .grid-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr); /* Three columns with equal width */
        grid-template-rows: repeat(7, 1fr); /* Three rows with equal height */
        gap: 5px; /* Gap between grid items */
        padding-top: 0.5vh;
        padding-bottom: 0px;
        height: 90%; /* Occupy full height of the main element */
        width: 100%;
    }

    .component-container {
        border: 1px solid #ccc; /* Add border for visualization */
        border-radius: 10px;
        display: flex; /* Use flexbox */
        align-items: center; /* Center vertically */
        width: 100%; /* Ensure component fills up the whole cell horizontally */
        height: 100%; /* Ensure component fills up the whole cell vertically */
    }

    .component-1 {
        grid-column: span 2; /* Span two columns */
        grid-row: span 7; /* Span three rows */
        position: relative;
    }

    .component-2 {
        grid-column: span 3; /* Third column */
        grid-row: span 4; /* Span three rows */
        position: relative;

    }

    .component-3 {
        grid-column: span 3; /* Third column */
        grid-row: span 3; /* Span three rows */
        position: relative;
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
        margin-bottom: 1vh;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding-left: 10px;
        padding-right: 10px;
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: flex-start; /* Center items horizontally */
        list-style-type: none; /* Remove list-style (bullets) */
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list-right {
        max-height: 70%; /* Limit the height of the list */
        max-width: 100%;
        margin-bottom: 1vh;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding-left: 10px;
        padding-right: 10px;
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: flex-start; /* Center items horizontally */
        list-style-type: none; /* Remove list-style (bullets) */
    }

    .scrollable-list-right-sessions {
        max-height: 45%; /* Limit the height of the list */
        max-width: 100%;
        margin-top: 0.5vh;
        margin-bottom: 0.5vh;
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

    .input-container {
        width: 98%;
        border-radius: 10px;
        margin: 5px;
        padding: 5px;
        font-size: 2.1vh;
        padding-left: 10px;
    }

    .input-container-session {
        border-radius: 10px;
        display: inline-block; /* Display the input containers in a line */
        width: calc(25% - 2%); /* Set width to occupy 1/3 of the available space */
        font-size: 1.5vh;
        text-align: center; /* Reset text alignment to left within the input container */

    }

    .center-container {
        text-align: center; /* Center the child elements horizontally */
    }

    .button-container {
        position: absolute; /* Position the button container */
        bottom: 10px; /* Adjust as needed */
        right: 10px; /* Align with the right edge of the parent container */
        width: auto; /* Allow the button container to adjust its width based on content */
        text-align: right; /* Align the button to the right */
    }

    .btn {
        margin-left: 10px; /* Add margin between the button and other elements */
    }

    .input-container-checkboxes {
        display: flex;
        align-items: center;
        width: 98%;
        border-radius: 10px;
        margin: 5px;
        padding: 5px;
        padding-left: 10px;
        font-size: 2.1vh;
    }

    .days-offered-container {
        display: flex;
        align-items: center;
        margin-left: 10px; /* Adjust margin as needed */
    }

    .checkbox-styling {
        height: 2vh;
    }

    .time-info {
        display: flex;
    }

    .time-info span {
        flex: 1; /* Each span occupies 1/3 of the available space */
        text-align: center; /* Center-align text within each span */
    }
</style>