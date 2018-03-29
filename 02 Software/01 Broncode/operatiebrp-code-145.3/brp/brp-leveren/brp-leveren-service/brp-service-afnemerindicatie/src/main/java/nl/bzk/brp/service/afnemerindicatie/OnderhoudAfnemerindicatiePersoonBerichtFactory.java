/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Maakt een bericht voor de module OnderhoudAfnemerindicaties.
 */
@FunctionalInterface
public interface OnderhoudAfnemerindicatiePersoonBerichtFactory {

    /**
     * Maak een bericht.
     * @param teLeverenPersoon de te leveren persoon
     * @param autorisatiebundel de autorisatiebundel
     * @param damp datum aanvang materiele periode
     * @param tijdstipRegistratie het tijdstip van registratie
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param berichtReferentie bericht referentie alleen gebruikt voor GBA berichten
     * @return het bericht
     */
    VerwerkPersoonBericht maakBericht(Persoonslijst teLeverenPersoon, Autorisatiebundel autorisatiebundel,
                                      final Integer damp, final ZonedDateTime tijdstipRegistratie,
                                      final SoortAdministratieveHandeling soortAdministratieveHandeling,
                                      final String berichtReferentie);
}
