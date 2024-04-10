<script>
  import { onMount } from 'svelte'
  import axios from 'axios';

  /**
   * Array containing course offering objects.
   * @type {Array<{
   *   id: string,
   *   startDate: string,
   *   endDate: string,
   *   courseTypeId: string,
   *   daysOffered: string[],
   *   instructorId: string,
   *   roomId: string
   * }>}
   */
  let courseOfferings = [];

  /**
   * Array containing instructor objects.
   * @type {Array<{
   *   id: string,
   *   name: string,
   * }>}
   */
  let instructors = [];

  /**
   * Array containing room objects.
   * @type {Array<{
   * capacity: string,
   * floorNumber: string,
   * id: string,
   * name: string,
   * roomNumber: string
   * }>}
   */
  let rooms = [];

  /**
   * Array containing course type objects
   * @type {Array<{
   * approvalStatus: string,
   * courseName: string,
   * id: string,
   * rejectedStatus: string,
   * staffAccountId: string
   * }>}
   */
  let courseTypes = [];

  /**
   * @typedef {Object} Instructor
   * @property {string} id - The unique identifier for the instructor.
   * @property {string} name - The name of the instructor.
   */

  /**
   * @typedef {Object} CourseOffering
   * @property {string} id
   * @property {string} startDate
   * @property {string} endDate
   * @property {string}courseTypeId
   * @property {string[]} daysOffered
   * @property {string} instructorId
   * @property {string} roomId
   */

  /**
   * @typedef {Object} Room
   * @property {string} capacity
   * @property {string} floorNumber
   * @property {string} id
   * @property {string} name
   * @property {string} roomNumber
   */

  /**
   * @typedef {Object} CourseType
   * @property {string} approvalStatus
   * @property {string} courseName
   * @property {string} id
   * @property {string} rejectedStatus
   * @property {string} staffAccountId
   */

  const backendUrl = 'http://127.0.0.1:8080/';

  const AXIOS = axios.create({
      baseURL: backendUrl,
      headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
  });

  /**
   * Asynchronously fetches course offerings and their corresponding instructors upon component mount.
   * It first retrieves all course offerings, then fetches all instructors. For each course offering,
   * it replaces the instructorId with the instructor's name, if found.
   *
   * @async
   */
  onMount(async () => {
      try {
          const response = await AXIOS.get('/courseOfferings/getAll');
          /** @type {CourseOffering[]} */
          courseOfferings = response.data;
          // Fetch all instructors
          const response2 = await AXIOS.get('/accounts/getInstructors');
          /** @type {Instructor[]} */
          instructors = response2.data;
          // Fetch all rooms
          const response3 = await AXIOS.get('/rooms/getAll')
          /** @type {Room[]} */
          rooms = response3.data;
          // Fetch all course types
          const response4 = await AXIOS.get('/courseTypes/getAllApproved')
          /** @type {CourseType[]} */
          courseTypes = response4.data;
          console.log(courseTypes);

          courseOfferings.forEach(offering => {
              /** @type {Instructor | undefined} */
              const instructor = instructors.find(inst => inst.id === offering.instructorId);
              if (instructor) {
                  offering.instructorId = instructor.name;
              }
          })

          courseOfferings.forEach(offering => {
              /** @type {Room | undefined} */
              const room = rooms.find(r => r.id === offering.roomId);
              if (room) {
                  offering.roomId = room.name;
              }
          })

          courseOfferings.forEach(offering => {
              /** @type {CourseType | undefined} */
              const courseType = courseTypes.find(cT => cT.id === offering.courseTypeId);
              if (courseType) {
                  offering.courseTypeId = courseType.courseName;
              }
          })

          courseOfferings = courseOfferings;
      } catch (error) {
          console.error('Failed to fetch course offerings or instructors:', error);
      }
  });
</script>

<div class="h-full course-history-page bg-secondary">
  <div class="page-title">
    <h1>COURSE HISTORY</h1>
  </div>
  <div class="page-content bg-base-100 overflow-x-scroll">
    <table class="table">
      <thead>
        <tr>
          <th>Course Offering ID</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Instructor</th>
          <th>Course Type</th>
          <th>Days Offered</th>
          <th>Room</th>
        </tr>
      </thead>
      <tbody style="min-height: 100px;">
        {#each courseOfferings as item}
          <tr class="hover">
            <td>{item.id}</td>
            <td>{item.startDate}</td>
            <td>{item.endDate}</td>
            <td on:click={console.log(item.instructorId)}>{item.instructorId}</td>
            <td>{item.courseTypeId}</td>
            <td>{item.daysOffered}</td>
            <td>{item.roomId}</td>
          </tr>
        {/each}
        {#if courseOfferings.length < 10}
          {#each Array(10 - courseOfferings.length) as _}
            <tr><td colspan="6"></td></tr>
          {/each}
        {/if}
      </tbody>
    </table>
  </div>
</div>

<style>
    h1 {
        font-size: 3rem;
        margin-bottom: 2rem;
        letter-spacing: 0.2rem;
        font-weight: bold;
    }

    .course-history-page {
        border-radius: 10px;
        height: 100%;
        width: 100%;
        margin-bottom: 0.75%;
        padding: 2% 3%;
        display: flex;
        align-items: flex-start;
        justify-content: center;
        flex-direction: column;
    }

    .page-title {
        height: 20%;
        width: 100%;
    }

    .page-content {
        display: flex;
        justify-content: center;
        align-content: center;
        width: 100%;
        height: 80%;
    }

    .table tr {
        height: 40px; /* Set a fixed height for table rows */
    }
</style>