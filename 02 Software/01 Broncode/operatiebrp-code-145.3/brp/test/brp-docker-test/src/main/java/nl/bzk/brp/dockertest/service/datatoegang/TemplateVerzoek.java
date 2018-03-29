/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.datatoegang;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Een verzoek obv {@link JdbcTemplate}
 */
public interface TemplateVerzoek {

    /**
     * Een niet-transactioneel verzoek.
     *
     * @param jdbcTemplateConsumer de consumer die gebruik maakt van de {@link JdbcTemplate}
     * @return een verzoekresultaat
     */
    void readonly(Consumer<JdbcTemplate> jdbcTemplateConsumer);

    /**
     * Voer een template uit en stream het resultaat.
     *
     * @param jdbcTemplateConsumer de functie op de template
     * @return een stream
     */
    Stream readonlyStream(Function<JdbcTemplate, Collection> jdbcTemplateConsumer);


    /**
     * Een niet-transactioneel verzoek.
     *
     * @param jdbcTemplateConsumer de consumer die gebruik maakt van de {@link JdbcTemplate}
     * @return een verzoekresultaat
     */
    void readwrite(Consumer<JdbcTemplate> jdbcTemplateConsumer);
}
