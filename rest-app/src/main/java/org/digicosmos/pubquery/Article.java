package org.digicosmos.pubquery;

import java.util.Date;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Article {

    public static class Mapper implements RowMapper<Article> {
        @Override public Article map(final ResultSet rs, final StatementContext ctx) throws SQLException {
            return new Article(rs.getInt("pmid"),
                    rs.getString("title"),
                    rs.getDate("date"),
                    rs.getString("authors")
            );
        }

    }

    private Integer pmid;

    private String title;

    private Date date;

    private String authors;

    public Article(Integer pmid, String title, Date date, String authors) {
        this.pmid = pmid;
        this.title = title;
        this.date = date;
        this.authors = authors;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
