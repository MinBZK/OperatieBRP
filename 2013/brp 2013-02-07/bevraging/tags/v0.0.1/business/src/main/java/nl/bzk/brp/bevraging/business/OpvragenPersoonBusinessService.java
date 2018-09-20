/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business;

import java.math.BigDecimal;

import nl.bzk.brp.bevraging.business.antwoord.Antwoord;
import nl.bzk.brp.bevraging.business.vraag.Vraag;
import nl.bzk.brp.bevraging.domein.Persoon;


/**
 * De business interface voor het opvragen van een Persoon (PUC500).
 */
public interface OpvragenPersoonBusinessService {

    /**
     * Haalt de gegevens op van een {@link Persoon} op basis van het meegeleverde BSN nummer.
     * @param vraag vraag object met daarin het burger service nummer van de gezochte persoon.
     * @return antwoord object met daarin de persoon geidentificeerd met het meegeleverde bsn.
     */
    Antwoord<Persoon> opvragenPersoonOpBasisVanBsn(Vraag<BigDecimal> vraag);

}
