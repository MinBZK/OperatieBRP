/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.verzoek;

import nl.bzk.brp.poc.domein.BijhoudingContext;

/**
 * Standaard interface voor alle bijhouding verzoek berichten.
 */
public interface BijhoudingVerzoek<T> {

    /**
     * Retourneert de nieuwe situatie.
     *
     * @return de nieuwe situatie.
     */
    T getNieuweSituatie();

    /**
     * Retourneert de voor de bijhouding geldende context. In deze context zijn zaken als aanvang geldigheid opgenomen.
     *
     * @return de voor de bijhouding geldende context.
     */
    BijhoudingContext getBijhoudingContext();

}
