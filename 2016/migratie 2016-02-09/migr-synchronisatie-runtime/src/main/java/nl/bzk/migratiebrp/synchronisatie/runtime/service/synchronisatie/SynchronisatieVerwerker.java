/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;

/**
 * Verwerker van een synchronisatie.
 */
public interface SynchronisatieVerwerker {

    /**
     * Verwerk een synchronisatie.
     * 
     * @param verzoek
     *            synchronisatie verzoek
     * @param loggingBericht
     *            logging bericht
     * @return antwoord
     * @throws SynchronisatieVerwerkerException
     *             bij verwerkings fouten
     */
    SynchroniseerNaarBrpAntwoordBericht verwerk(SynchroniseerNaarBrpVerzoekBericht verzoek, Lo3Bericht loggingBericht)
        throws SynchronisatieVerwerkerException;
}
