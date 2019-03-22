CREATE TABLE articles (
    pmid INT PRIMARY KEY UNIQUE NOT NULL,
    title TEXT,
    date DATE
);

CREATE TABLE authors (
    authorid SERIAL PRIMARY KEY UNIQUE NOT NULL,
    forename VARCHAR(255),
    lastname VARCHAR(255),
    initials VARCHAR(10)
);

CREATE TABLE articlesauthors (
    articleid INT REFERENCES articles(pmid),
    authorplace INT,
    authorid INT REFERENCES authors(authorid)
);
