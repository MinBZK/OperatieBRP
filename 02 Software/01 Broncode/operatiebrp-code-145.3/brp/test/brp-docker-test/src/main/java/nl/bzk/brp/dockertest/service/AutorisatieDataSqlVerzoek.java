/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Verzoek voor het uitvoeren van SQL t.b.v autorisatiedata : partijen, leveringsautorisaties, bijhoudingsautorisaties.
 */
public class AutorisatieDataSqlVerzoek implements Consumer<JdbcTemplate> {

    private static final String[] defaultPaths = {
            "autorisatie_overrides/truncate-docker.sql",
            "autorisatie_overrides/custompartijen.sql",
            "autorisatie_overrides/partij-als-afnemer-aanmerken.sql",
            "autorisatie_overrides/partij-als-bijhouder-aanmerken.sql",
            "autorisatie_overrides/partij-als-minister-aanmerken.sql",
            "autorisatie_overrides/dummy-partijen-voor-bijhouding.sql",
            "autorisatie_overrides/voeg-autorisaties-toe.sql",
            "autorisatie_overrides/update-indag.sql"
    };

    @Override
    public void accept(final JdbcTemplate jdbcTemplate) {
        if (!jdbcTemplate.queryForList("SELECT * from kern.partij where naam = 'Gemeente BRP 3'").isEmpty()) {
            return;
        }
        final List<String> batch = Lists.newLinkedList();
        for (String path : defaultPaths) {
            try (InputStream is = new ClassPathResource(path).getInputStream()) {
                batch.addAll(IOUtils.readLines(is, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new TestclientExceptie(e);
            }
        }
        batch.removeIf("BEGIN;"::equals);
        batch.removeIf("COMMIT;"::equals);

        jdbcTemplate.batchUpdate(batch.toArray(new String[batch.size()]));
    }
}
