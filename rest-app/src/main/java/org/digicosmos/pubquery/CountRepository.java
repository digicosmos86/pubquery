package org.digicosmos.pubquery;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(Count.Mapper.class)
public interface CountRepository {

    @SqlQuery("select extract(year from date)::text as year, count(pmid)::int from articles group by year order by year;")
    List<Count> list();
}