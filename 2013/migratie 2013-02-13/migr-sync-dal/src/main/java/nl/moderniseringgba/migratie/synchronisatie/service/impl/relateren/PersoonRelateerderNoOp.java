/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

import org.springframework.stereotype.Component;

/**
 * Implementatie van de PersoonRelateerder interface die geen relaties legt.
 * Alleen bedoeld voor gebruik voor de initiele vulling om performance degradatie gedurende het proces te vermijden.
 */
 @Component
public final class PersoonRelateerderNoOp implements PersoonRelateerder {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Persoon updateRelatiesVanPersoon(final Persoon persoon, final BigDecimal aNummerIstPersoon) {
        if (aNummerIstPersoon != null) {
            throw new IllegalArgumentException("ANummer wijziging wordt niet ondersteund in PersoonRelateerderNoOp");
        }

        LOG.info("Sla relateren over voor persoon met aNummer {}", persoon.getAdministratienummer());

        return persoon;
    }
}
