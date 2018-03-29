/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service;

import nl.bzk.brp.relateren.service.bericht.RelateerPersoonBericht;

/**
 * Service definities voor relateren.
 */
public interface RelateerService {

    /**
     * Voor elke persoon id in het bericht wordt de daarbij horende persoon gerelateerd.
     * 
     * @param bericht
     *            het bericht
     */
    void verwerkPersoonRelateerBericht(RelateerPersoonBericht bericht);
}
