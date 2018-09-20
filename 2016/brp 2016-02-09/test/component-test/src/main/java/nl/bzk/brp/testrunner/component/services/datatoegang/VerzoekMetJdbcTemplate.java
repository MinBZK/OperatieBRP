/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.datatoegang;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 */
public abstract class VerzoekMetJdbcTemplate extends DatabaseVerzoek<JdbcTemplate> {

    @Override
    final void voerUit(final DatabaseVerzoekUitvoerder uitvoerder) {
        uitvoerder.voerUit(this);
    }
}
