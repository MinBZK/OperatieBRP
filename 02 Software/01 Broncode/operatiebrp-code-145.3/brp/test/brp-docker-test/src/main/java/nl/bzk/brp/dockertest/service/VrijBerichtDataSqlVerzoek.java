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
 *
 */
public final class VrijBerichtDataSqlVerzoek implements Consumer<JdbcTemplate> {

    private static final String DEFAULT_PATH = "autorisatie_overrides/vrijbericht.sql";

    private final String afnemervoorbeeldIP;
    private final Integer afnemervoorbeeldPoort;

    /**
     * Constructor.
     *
     * @param afnemervoorbeeldIP IP van de afnemervoorbeeld docker.
     * @param afnemervoorbeeldPoort poort van afnemervoorbeeld docker.
     */
    VrijBerichtDataSqlVerzoek(final String afnemervoorbeeldIP, final Integer afnemervoorbeeldPoort) {
        this.afnemervoorbeeldIP = afnemervoorbeeldIP;
        this.afnemervoorbeeldPoort = afnemervoorbeeldPoort;
    }

    @Override
    public void accept(final JdbcTemplate jdbcTemplate) {
        final List<String> batch = Lists.newLinkedList();
        try (InputStream is = new ClassPathResource(DEFAULT_PATH).getInputStream()) {
            batch.addAll(IOUtils.readLines(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
        jdbcTemplate.batchUpdate(batch.toArray(new String[batch.size()]));
        //zet afleverpunt
        jdbcTemplate.update(String.format("UPDATE kern.partij SET afleverpuntvrijber = '%s' ",
                String.format("http://%s:%s/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht", afnemervoorbeeldIP, afnemervoorbeeldPoort)));
    }
}