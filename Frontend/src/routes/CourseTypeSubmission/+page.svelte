<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    /**
     * @typedef {Object} CourseType
     * @property {boolean} approvalStatus
     * @property {string} courseName
     * @property {number} id
     * @property {boolean} rejectedStatus
     * @property {number} staffAccountId
     * @property {string} staffName
     */

    /**
     * Array containing course type objects.
     * @type {Array<{
     * approvalStatus: boolean,
     * courseName: string,
     * id: number,
     * rejectedStatus: boolean,
     * staffAccountId: number,
     * staffName: string
     * }>}
     */
    let proposedCourseTypes = []; // Reactive variable to hold the fetched data

    /**
     * Array containing course offering objects.
     * @type {Array<{
     * approvalStatus: boolean,
     * courseName: string,
     * id: number,
     * rejectedStatus: boolean,
     * staffAccountId: number,
     * staffName: string
     * }>}
     */
    let instructorProposedCourseTypes = []

    /**
     * @typedef {Object} Instructor
     * @property {string} id
     * @property {string} name
     */
    let instructor;

    /**
     * Array containing instructor objects.
     * @type {Array<{
     *   id: string,
     *   name: string,
     * }>}
     */
    let instructors = [];

    let createdCT;

    let errorPerson;
    let userToken = 'wasd'; // subscribe to alex's store when its up -> LOOK AT DISCORD DMS WITH HIM FOR HOW
    let value = '';
    let staffAccID = 1;

    const AXIOS = axios.create({
        baseURL: 'http://127.0.0.1:8080/', // Adjust this to your actual backend URL
        headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });

    async function refreshTables() {
        await AXIOS.get('/courseTypes/getAll', {
            headers: {
                'userToken': userToken
            }
        })
            .then(response => {
                proposedCourseTypes = response.data;
            })
            .catch(e => {
                errorPerson = e;
            });
        console.log(proposedCourseTypes);
        const reqHeader = {
            'userToken': userToken
        }

        await AXIOS.get('/courseTypes/getProposed', {
            headers: reqHeader
        })
            .then(response => {
                instructorProposedCourseTypes = response.data;
            })
            .catch(e => {
                errorPerson = e;
            });
    }


    onMount(async () => {
        refreshTables();
    });



    /**
     * Determines the status based on the approval and denial flags.
     * @param {boolean} approvedBool - A boolean indicating whether the request is approved.
     * @param {boolean} deniedBool - A boolean indicating whether the request is denied.
     * @returns {string} The status of the request.
     */
    function displayStatus(approvedBool, deniedBool) {
      if (!approvedBool && !deniedBool) {
        return "Under Review";
      }
      else if (approvedBool && !deniedBool) {
        return "Approved";
      }
      else if (!approvedBool && deniedBool) {
        return "Denied";
      }
      else {
        return "Hmm... Something went wrong";
      }

    }

    async function handleClick() {
      const reqHead = {
          'userToken': userToken
      }
      const reqParam = {
          'courseTypeName': value
      }
        try {
          const response = await AXIOS.post("/courseTypes/create", null, {
              headers: reqHead,
              params: reqParam
          });

          createdCT = response.data;
      } catch(error) {
          errorPerson = error;
      }

      refreshTables();

      console.log(proposedCourseTypes);
      
      return void 0;
    }

</script>

<h1 class="text-2xl font-bold p-2">Course Type Management</h1>
<div class="content">
  <div class="listAll p-2">
    <h1 class="text-lg">All Course Types</h1>
    <div class="overflow-x-auto">
      <table class="table">
        <!-- head -->
        <thead>
          <tr>
<!--            <th></th>-->
            <th>Proposed CourseType</th>
            <th>Associated Instructor ID</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <!-- row 1 -->
          {#each proposedCourseTypes as courseType}<!--{#each proposedCourseTypes as courseType, index (courseType)}-->
          <tr class="hover">
<!--            <th>{index+1}</th>-->
            <th>{courseType.courseName}</th>
            <th>{courseType.staffAccountId}</th>
            <th>{displayStatus(courseType.approvalStatus, courseType.rejectedStatus)}</th>
          </tr>
          {/each}
        </tbody>
      </table>
    </div>
  </div>
  <div class="last-col">
    <div class="newCourseType">
      <label for="textInput" class="text-lg">Insert New CourseType:</label>
      <input
        type="text"
        id="textInput"
        bind:value={value}
        placeholder="Enter CourseType here"
      />
      <div class="flex justify-end mt-2">
        <button class="btn" on:click={handleClick}>Submit CourseType</button>
      </div>
    </div>
    <div class="listInstructor">
      <h1 class="text-lg">Your Course Types</h1>
      <div class="overflow-x-auto">
				<table class="table">
					<!-- head -->
					<thead>
						<tr>
							<th></th>
							<th>Proposed CourseType</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<!-- row 1 -->
						{#each instructorProposedCourseTypes as courseType, index (courseType)}
						<tr class="hover">
							<th>{index+1}</th>
							<th>{courseType.courseName}</th>
							<th>{displayStatus(courseType.approvalStatus, courseType.rejectedStatus)}</th>
						</tr>
						{/each}
					</tbody>
				</table>
      </div>
    </div>
  </div>
</div>


<style>
    /* Add styling for the component */

    /* .container {
      display: grid;
      grid-template-rows: auto 1fr;
      grid-template-areas:
        "PageHeading"
        "content";
    } */

    .content {
      display: grid;
      gap: 1rem;
      /* grid-area: "content"; */
      grid-template-columns: 2fr 1fr;
      /* grid-template-rows:   1fr 1fr 1fr; */
      grid-template-areas:
        /* "PageHeading PageHeading PageHeading"  */
        "listAll listAll newCourseType"
        "listAll listAll listInstructor"
        "listAll listAll listInstructor";
      height: 70%;
    }
    .last-col {
      display: grid;
      grid-template-rows: auto 1fr;
    }

    h1 {
      grid-area: "PageHeading";
    }

    .newCourseType {
      display: flex;
      flex-direction: column;
      margin-bottom: 0.625rem; /* Add margin to separate multiple instances of the component */
      padding: 1rem;
      grid-area: "newCourseType";
    }

    .listInstructor {
      grid-area: "listInstructor";
      /* overflow-y: scroll; */
      height: 70%;
      margin-bottom: 0.625rem; /* Add margin to separate multiple instances of the component */
      padding: 1rem;
    }

    .listAll {
      grid-area: "listAll";
    }
    
    label {
      margin-bottom: 0.3125rem; /* Add space between label and input */
    }
    
    input, button {
      padding: 0.3125rem; /* Add padding to input and button */
      margin-bottom: 0.3125rem; /* Add space between input and button */
    }
  </style>
