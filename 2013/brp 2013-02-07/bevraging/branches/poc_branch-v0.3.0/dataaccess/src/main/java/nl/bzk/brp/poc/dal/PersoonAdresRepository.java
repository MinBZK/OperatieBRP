/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.dal;

import nl.bzk.brp.poc.domein.BijhoudingContext;
import nl.bzk.brp.poc.domein.PocPersoonAdres;

/**
 * Repository voor het ophalen en persisteren van {@link nl.bzk.brp.poc.domein.PocPersoonAdres} instanties. Deze
 * Repository houdt hierbij rekening met historie.
 */
public interface PersoonAdresRepository {

    /**
     * Slaat het opgegeven nieuwe adres op.
     *
     * @param context de context van uitvoering.
     * @param pocPersoonAdres het adres dat opgeslagen dient te worden.
     */
    void opslaanNieuwPersoonAdres(BijhoudingContext context, PocPersoonAdres pocPersoonAdres);

    /**
     * Haalt het actuele/huidige adres op voor de opgegeven persoon.
     *
     * @param persoonId id van de persoon waarvoor het adres opgehaald dient te worden.
     * @return het huidige adres van de opgegeven persoon.
     */
    PocPersoonAdres ophalenPersoonAdresVoorPersoon(Long persoonId);

}
