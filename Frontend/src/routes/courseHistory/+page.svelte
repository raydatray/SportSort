<script>
  import { onMount } from 'svelte'
  import axios from 'axios';

  onMount(async () => {
      fetch('https://jsonplaceholder.typicode.com/todos/1')
          .then(response => response.json())
          .then(json => console.log(json))
    }
  )

  const courseOfferings = [
      {
          courseOffering: "Introduction to Programming",
          startDate: "2024-04-01",
          endDate: "2024-04-30",
          instructor: "John Doe",
          registeredDate: "2024-03-20",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Web Development Fundamentals",
          startDate: "2024-05-10",
          endDate: "2024-06-10",
          instructor: "Jane Smith",
          registeredDate: "2024-04-25",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Data Science Essentials",
          startDate: "2024-06-15",
          endDate: "2024-07-15",
          instructor: "Michael Johnson",
          registeredDate: "2024-06-01",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Mobile App Development",
          startDate: "2024-08-05",
          endDate: "2024-09-05",
          instructor: "Emily Brown",
          registeredDate: "2024-07-25",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Machine Learning Basics",
          startDate: "2024-09-15",
          endDate: "2024-10-15",
          instructor: "David Wilson",
          registeredDate: "2024-09-01",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Introduction to Cybersecurity",
          startDate: "2024-10-20",
          endDate: "2024-11-20",
          instructor: "Jessica Lee",
          registeredDate: "2024-10-10",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Graphic Design Fundamentals",
          startDate: "2024-12-01",
          endDate: "2025-01-01",
          instructor: "Christopher Taylor",
          registeredDate: "2024-11-20",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Digital Marketing Strategies",
          startDate: "2025-01-15",
          endDate: "2025-02-15",
          instructor: "Amanda Rodriguez",
          registeredDate: "2025-01-05",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Project Management Basics",
          startDate: "2025-03-01",
          endDate: "2025-04-01",
          instructor: "Daniel Martinez",
          registeredDate: "2025-02-20",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "UX/UI Design Principles",
          startDate: "2025-04-10",
          endDate: "2025-05-10",
          instructor: "Olivia Garcia",
          registeredDate: "2025-04-01",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Networking Fundamentals",
          startDate: "2025-06-01",
          endDate: "2025-07-01",
          instructor: "William Thompson",
          registeredDate: "2025-05-20",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Artificial Intelligence Essentials",
          startDate: "2025-07-15",
          endDate: "2025-08-15",
          instructor: "Sophia Scott",
          registeredDate: "2025-07-05",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Cloud Computing Basics",
          startDate: "2025-08-20",
          endDate: "2025-09-20",
          instructor: "Andrew Lewis",
          registeredDate: "2025-08-10",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "E-commerce Strategies",
          startDate: "2025-10-01",
          endDate: "2025-11-01",
          instructor: "Ethan Clark",
          registeredDate: "2025-09-20",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Creative Writing Workshop",
          startDate: "2025-11-15",
          endDate: "2025-12-15",
          instructor: "Mia Hall",
          registeredDate: "2025-11-05",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Photography Basics",
          startDate: "2026-01-01",
          endDate: "2026-02-01",
          instructor: "Lucas Wright",
          registeredDate: "2025-12-20",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Music Production Fundamentals",
          startDate: "2026-02-15",
          endDate: "2026-03-15",
          instructor: "Lily Green",
          registeredDate: "2026-02-05",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Language Learning Techniques",
          startDate: "2026-04-01",
          endDate: "2026-05-01",
          instructor: "Noah King",
          registeredDate: "2026-03-20",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Fitness and Nutrition Basics",
          startDate: "2026-05-15",
          endDate: "2026-06-15",
          instructor: "Isabella Baker",
          registeredDate: "2026-05-05",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Art History Overview",
          startDate: "2026-07-01",
          endDate: "2026-08-01",
          instructor: "Logan Adams",
          registeredDate: "2026-06-20",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Film Making Essentials",
          startDate: "2026-08-15",
          endDate: "2026-09-15",
          instructor: "Natalie Hall",
          registeredDate: "2026-08-05",
          paymentMethod: "Credit Card"
      },
      {
          courseOffering: "Cooking Techniques",
          startDate: "2026-10-01",
          endDate: "2026-11-01",
          instructor: "Henry White",
          registeredDate: "2026-09-20",
          paymentMethod: "PayPal"
      },
      {
          courseOffering: "Interior Design Basics",
          startDate: "2026-11-15",
          endDate: "2026-12-15",
          instructor: "Emma Brown",
          registeredDate: "2026-11-05",
          paymentMethod: "Credit Card"
      }
  ]
</script>

<div class="h-full course-history-page bg-secondary">
  <div class="page-title">
    <h1>COURSE HISTORY</h1>
  </div>
  <div class="page-content bg-base-100 overflow-x-auto">
    <table class="table">
      <thead>
        <tr>
          <th>Course Offering</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Instructor</th>
          <th>Registered Date</th>
          <th>Payment Method</th>
        </tr>
      </thead>
      <tbody>
        {#each courseOfferings as item}
          <tr class="hover">
            <td>{item.courseOffering}</td>
            <td>{item.startDate}</td>
            <td>{item.endDate}</td>
            <td>{item.instructor}</td>
            <td>{item.registeredDate}</td>
            <td>{item.paymentMethod}</td>
          </tr>
        {/each}
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
        max-height: 659px;
    }

    .page-title {
        height: 20%;
        margin-bottom: -3%;
    }

    .page-content {
        display: flex;
        justify-content: center;
        align-content: center;
        width: 100%;
        height: 80%;
    }
</style>