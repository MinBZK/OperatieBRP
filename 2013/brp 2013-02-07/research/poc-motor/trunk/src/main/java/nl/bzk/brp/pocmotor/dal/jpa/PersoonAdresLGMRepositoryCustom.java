/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;


/**
 * Repository voor het ophalen en persisteren van {@link PersoonAdres} instanties. Deze
 * Repository houdt hierbij rekening met historie.
 */
public interface PersoonAdresLGMRepositoryCustom {

    /**
     * Slaat het opgegeven nieuwe adres op.
     *
     * @param pocPersoonAdres het adres dat opgeslagen dient te worden.
     * @param datumAanvangGeldigheid Datum dat het nieuwe adres ingaat.
     * @param datumEindeGeldigheid Datum einde geldigheid van het adres.
     * @param registratieTijd Registratie tijd van het nieuwe adres.
     */
    void opslaanNieuwPersoonAdres(PersoonAdres pocPersoonAdres, 
                                  Datum datumAanvangGeldigheid,
                                  Datum datumEindeGeldigheid,
                                  DatumTijd registratieTijd);

    /**
     * Haalt het actuele/huidige adres op voor de opgegeven persoon.
     *
     * @param persoonId id van de persoon waarvoor het adres opgehaald dient te worden.
     * @return het huidige adres van de opgegeven persoon.
     */
    PersoonAdres ophalenPersoonAdresVoorPersoon(Long persoonId);

}
