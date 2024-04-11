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

          // Filter out course offerings that aren't over yet
          courseOfferings = courseOfferings.filter(offering => {
              const endDate = new Date(offering.endDate);
              const today = new Date();
              // Compare endDate with today's date
              return endDate < today;
          });

          courseOfferings.forEach(offering => {
              /** @type {Instructor | undefined} */
              const instructor = instructors.find(inst => inst.id === offering.instructorId);
              if (instructor) {
                  offering.instructorId = instructor.name;
              } else {
                  offering.instructorId = "N/A"
              }
          })

          courseOfferings.forEach(offering => {
              /** @type {Room | undefined} */
              const room = rooms.find(r => r.id === offering.roomId);
              if (room) {
                  offering.roomId = room.name;
              } else {
                  offering.roomId = "N/A";
              }
          })

          courseOfferings.forEach(offering => {
              /** @type {CourseType | undefined} */
              const courseType = courseTypes.find(cT => cT.id === offering.courseTypeId);
              if (courseType) {
                  offering.courseTypeId = courseType.courseName;
              } else {
                  offering.courseTypeId = "N/A";
              }
          })

          courseOfferings = courseOfferings;
      } catch (error) {
          console.error('Failed to fetch course offerings or instructors:', error);
      }
  });
</script>

<h1 class="text-2xl font-bold p-2">Course History</h1>
<div class="content">
  <div class="listAll p-2">
    <div class="overflow-x-auto">
      <table class="table">
        <thead>
        <tr>
          <th>Course Offering ID</th>
          <th>Course Instructor</th>
          <th>Course Type</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Room</th>
          <th>Days Offered</th>
        </tr>
        </thead>
        <tbody>
          {#each courseOfferings as courseOffering}
            <tr class="hover">
              <th class="font-normal">{courseOffering.id}</th>
              <th class="font-normal">{courseOffering.instructorId}</th>
              <th class="font-normal">{courseOffering.courseTypeId}</th>
              <th class="font-normal">{courseOffering.startDate}</th>
              <th class="font-normal">{courseOffering.endDate}</th>
              <th class="font-normal">{courseOffering.roomId}</th>
              <th class="font-normal">{courseOffering.daysOffered}</th>
            </tr>
          {/each}
        </tbody>
      </table>
    </div>
  </div>
</div>

<style>

</style>