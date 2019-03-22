### A Management System of PubMed Articles

This repository hosts the source code of a single-page web app that displays data retrieved from the PubMed/MEDLINE database. 

#### Technology

The technology used to construct this webpage include:

##### Frontend

* JavaScript
* Vue.js
* Vuetify component library
* Chart.js
* Axios

##### Backend

* Java
* Jooby framework
* JDBI3 with `sqlObject` plugin
* Jackson 2

##### Database/Management

* PostgreSQL 11
* PgAdmin 4

##### Downloading/Wrangling Data

* Python with `BioPython` and `pandas` packages

#### Running the app

To try it out yourself, please follow these steps:

1. Create a PostgreSQL database
2. Use the SQL syntax in `db.sql` to create three tables
3. Import the data in the three CSV files to corresponding tables
4. Edit `rest-app/conf/application.conf` with information about your database
5. Make sure you have maven and npm installed
6. Run the backend and frontend with

```bash
git clone https://github.com/digicosmos86/pubquery.git
cd pubquery

# from pubquery folder
cd rest-app
mvn jooby:run
# from pubquery folder
cd web-app
npm run serve
```

Please note that the frontend points to http://localhost:8080/ for data, so please make sure your backend runs on port 8080.

#### Design decisions

##### 1. Database design

The structure of data in this project, though simple, is a classic many-to-many relationship, so the structure (shown below) of the tables reflects this relationship. Specifically, the `articles` and `authors` tables store articles and author information separately, and the `articlesauthors` table stores information on the authors for each article. Authorship (first author, second author) etc. is important in publications, so the `articlesauthors` table has one column `authorplace` to record that information.

![ER Diagram](https://raw.githubusercontent.com/digicosmos86/pubquery/master/relationship%20%diagram.png)

##### 2. Backend

A lightweight java backend is most appropriate for this project. I choose Jooby because its express.js inspired syntax makes it easy to write lightweight REST APIs. It also has mnay powerful plugins such as JDBI which handles complex database queries.

##### 3. Frontend

The choise between `react` and `vue` is a matter of taste and I simply prefer the latter. For uniform and modern aesthetics and functionality, I used the `vuetify` component library for this task. I also used `chart.js` library to build animated charts easily from the data from the backend.

#### Time used

Approximately 16 hours. Getting the data and beating it to shape with Python took the most time, and also a few hours were spent on getting around CORS.