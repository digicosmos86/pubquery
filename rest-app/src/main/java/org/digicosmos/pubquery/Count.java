package org.digicosmos.pubquery;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Count {

    public static class Mapper implements RowMapper<Count> {
        @Override public Count map(final ResultSet rs, final StatementContext ctx) throws SQLException {
            return new Count(rs.getString("year"),
                    rs.getInt("count")
            );
        }
    }

    private String year;

    private int count;

    public Count(String year, int count) {
        this.year = year;
        this.count = count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
