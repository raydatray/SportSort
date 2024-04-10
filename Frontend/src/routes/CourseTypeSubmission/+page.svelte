<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    let proposedCourseTypes = []; // Reactive variable to hold the fetched data
    let instructorProposedCourseTypes = []
    let errorPerson;
    let userToken; // subscribe to alex's store when its up -> LOOK AT DISCORD DMS WITH HIM FOR HOW

    onMount(() => {
      axios.get('/courseTypes/getAll')
        .then(response => {
          proposedCourseTypes = response.data;
        })
        .catch(e => {
            errorPerson = e;
        });

      axios.get('/courseTypes/getProposed')
        .then(response => {
            instructorProposedCourseTypes = response.data;
        })
        .catch(e => {
            errorPerson = e;
        });
    });

    let value = '';

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

    function handleClick(stringVal) {
      axios.post("/courseTypes/create", stringVal, {
        headers: userToken
      })
        .catch(e => {
            errorPerson = e;
        });
      
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
            <th></th>
            <th>Proposed CourseType</th>
            <th>Associated Instructor</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <!-- row 1 -->
          {#each proposedCourseTypes as courseType, index (courseType)}
          <tr class="hover">
            <th>{index+1}</th>
            <th>{courseType.name}</th>
            <th>{courseType.instructor}</th>
            <th>{displayStatus(courseType.approval, courseType.rejected)}</th>
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
        <button class="btn" on:click={handleClick(value)}>Submit CourseType</button>
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
							<th>{courseType.name}</th>
							<th>{displayStatus(courseType.approval, courseType.rejected)}</th>
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
