# tempo_app
Tempo Challenge by Franco Guajardo
This application was made with Spring Boot 2.4.1 using JWT as authentication method and PostgreSQL.

STEP 1, CLONE THE REPO:               https://github.com/Dabugger/tempo_app.git
STEP 2, CLEAN COMPILE AND SKIP TESTS: mvn.cmd clean install -DskipTests
STEP 3, COMPOSE WITH DOCKER:          docker-compose up
STEP 4, MAKE REQUEST WITH POSTMAN

POSTMAN CONTAINS 4 REQUESTS
1-LOCAL HISTORY - RETRIEVES LATEST ENDPOINT REQUESTS WITH PAGINATION.
2-LOCAL SIGNUP - REGISTERS A NEW USER.
3-LOCAL LOGIN - LOGS IN A PREVIOUSLY REGISTERED USER.
4-LOCAL SUM - SUMS 2 NUMBERS AND RETURNS RESULT.
