<script lang="js"> 
	// javascript here


	class CourseSession {
		constructor(instructorName, courseType, date, startTime, endTime) {
			this.instructorName = instructorName;
			this.startTime = startTime;
			this.endTime = endTime;
			this.courseType = courseType;
			this.date = date
		}
	}

	let session1 = new CourseSession("john", "swimming", "2024-04-02", "8:00", "9:00");
	let session2 = new CourseSession("harry", "yoga", "2024-04-03", "10:00", "11:00");
    let session3 = new CourseSession("john", "swimming", "2024-04-04", "8:00", "9:00");
	let session4 = new CourseSession("harry", "yoga", "2024-04-05", "10:00", "11:00");
    let session5 = new CourseSession("john", "swimming", "2024-04-06", "8:00", "9:00");
	let session6 = new CourseSession("harry", "yoga", "2024-04-07", "10:00", "11:00");
    let session7 = new CourseSession("cbum", "weights", "2024-04-08", "1:00", "2:00");

	let courseSessions = [session1, session2, session3, session4, session5, session6, session7];
</script>

<style lang="postcss">
	.card-container {
        grid-template-rows: repeat(auto, minmax(0, 1fr)); 
        row-gap: 0.125rem;
        justify-content: space-evenly;
        place-content: space-evenly;
		display: grid;

        flex-wrap: wrap;
		border-radius: 0.3125rem;
		border-width: 0.125rem;
	}

    .card {
        transform: scale(0.8);
        display: flex;
        margin: 0.25rem;
        min-width: 1fr;
    }
</style>

<div class="card-container bg-secondary grid grid-cols-4"> 
	{#each courseSessions as session, index (session)}
	<div class="card bg-base-100 shadow-xl content-evenly self-stretch">
		<div class="card-body">
			<!-- <div class="card-actions justify-end">
				<button class="btn btn-square btn-sm">
					<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" /></svg>
				</button>
			</div> -->
			<p><strong>Course Type: </strong>{session.courseType}</p>
			<p><strong>Date: </strong> {session.date}</p>
			<p><strong>Start Time: </strong>{session.startTime}</p>
			<p><strong>End Time: </strong>{session.endTime}</p>
			<p><strong>Instructor: </strong>{session.instructorName}</p>
		</div>
	</div>
	{/each}
</div>