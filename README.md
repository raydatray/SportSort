# Table of Contents

- [Introduction](#introduction)
- [Team Members](#team-members)
- [Project Deliverables](#project-deliverables)
- [Key Design Decisions](#key-design-decisions)

## Introduction <a name="introduction"></a>
This is the repository of Group 15 which consists of Leon Song, Raymond Liu, Houman Azari, Alexander Liu, Joseph Ciaravella and Colin Xiong. The main scope of this web app project is to create an application that supports every required scenario for **every** stakeholder for a sports center activity registration system. All functionality of the system is  accessible via the web frontend for respective stakeholders, without employing external systems or services.

## Team Members <a name="team-members"></a>

| Name              | Role            | Hours Spent (Deliverable 1) | Hours Spent (Deliverable 2) | Hours Spent (Deliverable 2) |
|-------------------|-----------------|-----------------------------|-----------------------------|-----------------------------|
| Leon Song         | Frontend Dev    | 16                          | 22                          | 27                          |
| Houman Azari      | Backend Dev     | 17                          | 21                          | 28                          |
| Joseph Ciaravella | UI/UX Designer  | 16                          | 23                          | 26                          |
| Colin Xiong       | Fullstack Dev   | 16                          | 26                          | 29                          |
| Raymond Liu       | QA Engineer     | 18                          | 27                          | 28                          |
| Alexander Liu     | Project Manager | 20                          | 23                          | 27                          |

## Project Deliverables <a name="project-deliverables"></a>

- **Deliverable 1**: Requirements, Domain Model, and Database Design
- **Deliverable 2**: Backend Services, Behaviour Modelling, and Testing
- **Deliverable 3**: Web Frontend

## Key Design Decisions and Meeting Minutes <a name="key-design-decisions"></a>
For key design decisions and meeting minutes, please refer to our [Project Wiki](../../wiki). Navigate to the appropriate deliverable section for specific information.

## How To Run the Application
- Navigate to the **Sportsregistrationw24Application.java** file and run it. This "starts" the database to make sure it is connected to the frontend.
- Navigate to the terminal, `cd Frontend` and enter `npm run dev` to start the locally hosted website.
-  Click on the link with "localhost" to access the application in your browser.
 <img width="290" alt="image" src="https://github.com/McGill-ECSE321-Winter2024/project-group-15/assets/78813640/78c44e85-73ef-44ef-8f75-eb057121d6f0">

## Deployement Guide
- Server Configuration
    - Ensure that you have a server running a compatible operating system.
    - Install Java Development Kit (JDK) version 17.
    - Install PostgreSQL server using package manager or official PostgreSQL repositories.  
- Database Setup
    - Log in to PostgresSQL using the psql command-line tool in your terminal
    - Create a database for the application
- IDE Application Configuration
    - Using an IDE such as Intellij will allow you to view and modify your local database after connecting it to the IDE
