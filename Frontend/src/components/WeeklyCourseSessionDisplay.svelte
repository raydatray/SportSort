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
	let session2 = new CourseSession("harry", "yoga", "2024-04-03", "10:00", "12:00");

	let courseSessions = [session1, session2];
</script>

<style lang="postcss">
	.card-container {
		display: flex;
		flex-direction: row;
		border-radius: 5px;
		border-width: 2px;
	}
</style>

<div class="card-container"> 
	{#each courseSessions as session, index (session)}
	<div class="card bg-base-100 shadow-xl">
		<div class="card-body">
			<div class="card-actions justify-end">
				<button class="btn btn-square btn-sm">
					<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" /></svg>
				</button>
			</div>
			<p>Course Type: {session.courseType}</p>
			<p>Date: {session.date}</p>
			<p>Start Time: {session.startTime}</p>
			<p>End Time: {session.endTime}</p>
			<p>Instructor: {session.instructorName}</p>
		</div>
	</div>
	{/each}
</div>