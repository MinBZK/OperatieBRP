/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.business;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementatie van busniess logica voor het relateren.
 */
@Component
public final class RelateerPersoonImpl implements RelateerPersoon {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean relateerOpBasisVanID(final Long id) {
        LOGGER.info("releteer persoon met id " + id);
        return false;
    }
}
