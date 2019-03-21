package org.digicosmos.pubquery;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(Article.Mapper.class)
public interface ArticleRepository {

    @SqlQuery("select pmid, title, date, authors from\n" +
            "	(select articleid, string_agg(authorname, '; ') as authors from\n" +
            "		(select * from articlesauthors\n" +
            "		 where articleid in (select pmid from articles order by date desc limit :max)) as articleslimit\n" +
            "	left join (select authorid, concat(initials, ' ', lastname) as authorname from authors) as authorsname\n" +
            "	on articleslimit.authorid = authorsname.authorid\n" +
            "	group by articleid) as articlenames\n" +
            "right join (select * from articles order by date desc limit :max) articleslimit1\n" +
            "on articleslimit1.pmid = articlenames.articleid\n" +
            "order by date desc;")
    List<Article> list(int max);

    @SqlQuery("select pmid, title, date, authors from\n" +
            "	(select articleid, string_agg(authorname, '; ') as authors from articlesauthors\n" +
            "	left join\n" +
            "		(select authorid, concat(initials, ' ', lastname) as authorname from authors) as b\n" +
            "	on articlesauthors.authorid = b.authorid\n" +
            "	where articleid in\n" +
            "		(select articlesauthors.articleid from authors\n" +
            "		 right join articlesauthors\n" +
            "		 on authors.authorid = articlesauthors.authorid\n" +
            "		 where upper(authors.lastname) like upper(:lastName))\n" +
            "	group by articleid) as a\n" +
            "inner join articles\n" +
            "on a.articleid = articles.pmid\n" +
            "order by date desc")
    List<Article> searchByLastName(String lastName);

}
