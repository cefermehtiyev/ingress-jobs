# Ingress Jobs - Job Portal Backend

Ingress Jobs is a backend system for a job portal that manages and retrieves job listings with essential details for both job seekers and employers
## Functional Requirements

### Job Listing API

- **GET** `/jobs`: Retrieve all job listings.
- **Filter Jobs** based on:
    - Title
    - Location
    - Job Type
    - Posted Date
    - Industry
- **Sort Jobs** by:
    - Posted Date
- **Load Jobs (Batch)**: Scrape job data from [Djinni](https://djinni.co/jobs/) (last 3 months) with filtering based on:
    - Remote/Worldwide jobs, jobs offering relocation, or jobs in Azerbaijan.

## Non-Functional Requirements

- **Docker**: The infrastructure, including the database and messaging system, is containerized using Docker.
- **Scalability**: The application is designed to be scalable.
- **Logging**: Proper logging is implemented using AOP.

## Technologies Used

- **Java 21**
- **Spring Boot 3.2.2**
- **Docker** for containerization
- **Scraping** from Djinni
- **Pagination** support
- **AOP Logging** for proper logging and tracing

## Setup

### Prerequisites

- **JDK 21**: Make sure to have JDK 21 installed on your machine.
- **Docker**: Docker is required for containerization. You can install Docker from [here](https://www.docker.com/get-started).

### Running the Application

1. **Clone the repository**:
   Clone the project repository using the following command:
   ```bash
   git clone https://github.com/cefermehtiyev/ingress-jobs.git
