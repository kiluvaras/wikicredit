# Tasks

> On the refinement APO presented a new task: from now on, we will only work
> with customers that have their own Wikipedia page as a way to ensure they are
> safe to work with.  
> We need to create a new service to load data from Wikipedia and incorporate those
> wiki articles with the rest of our systems.
> Your task is to create an MVP 1 to present it to managers so that we will know
> if we are on the right track.

1. Implement two controllers - one to manage (create and fetch) internal representation of a company data, a second one to load articles from Wikipedia
2. Persist company data and Wikipedia articles in DB tables
3. Provide an endpoint to fetch the report, containing company name and Wikipedia summary


List of companies that can be used as an example: https://en.wikipedia.org/wiki/List_of_companies_of_Latvia

REST endpoint to use: https://en.wikipedia.org/api/rest_v1/page/summary/{company}
(e.g. https://en.wikipedia.org/api/rest_v1/page/summary/Swedbank)

Fields that you need from it: `pageid` and `extract`

Upload the result to your own repository (give read access to mrabar if you will use private GitLab repo).

# Running service

With this repo, we provide you with a base for a Spring Boot service. You may use it, or create a new project on your own.

You are free to modify data classes or project structure in any way you see fit.

Just build with Gradle and run WikiCreditApplication from your IDE or with `gradle :bootRun`.

Service should be available at [localhost:8080](http://localhost:8080/).

Database console web GUI is available at http://localhost:8080/h2-console.
This is an embedded [H2 database](https://www.h2database.com/html/main.html)
which you can access with username = wikicredit and password = secretpass
(defined in `resources/application.properties`). On startup in runs `resources/nothingtoseehere.sql`
to initialize schema and fill sample data.

You can use Swagger UI at http://localhost:8080/swagger-ui.html

The project uses Lombok, so you might need to "Enable annotation processing" in your IDE.