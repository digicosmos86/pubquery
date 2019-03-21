package org.digicosmos.pubquery;

import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jooby.Jooby;
import org.jooby.handlers.CorsHandler;
import org.jooby.jdbc.Jdbc;
import org.jooby.jdbi.TransactionalRequest;
import org.jooby.json.Jackson;
import org.jooby.jdbi.Jdbi3;

/**
 * @author jooby generator
 */
public class App extends Jooby {

  {
    use("*", new CorsHandler());
    use(new Jackson());
    use(new Jdbc("db"));
    use(new Jdbi3()
      .doWith(jdbi -> {
                jdbi.installPlugin(new SqlObjectPlugin());
      })
      .transactionPerRequest(
                    new TransactionalRequest()
                            .attach(ArticleRepository.class)
                            .attach(CountRepository.class)
            )
    );

    get("/api/articles", req -> {
      /**
       *
       * List articles ordered by publication date.
       *
       * @param max Max page size, useful for paging. Default is <code>20</code>.
       * @return Articles ordered by publication date desc.
       */
        ArticleRepository db = require(ArticleRepository.class);

        int max = req.param("max").intValue(20);

        return db.list(max);
    });

    get("/api/search/:lastName", req -> {
      /**
       *
       * List articles ordered by publication date.
       *
       * @param lastName: The last name of the author searched for.
       * @return Articles ordered by publication date desc.
       */
        ArticleRepository db = require(ArticleRepository.class);

        String lastName = req.param("lastName").value();

        return db.searchByLastName(lastName);
    });

    get("/api/count", req -> {
        CountRepository db = require(CountRepository.class);

        return db.list();
    });
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
