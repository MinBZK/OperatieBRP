/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import java.util.List;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

/**
 *
 */
public interface SoortSynchronisatieBepaler {

    /**
     * Bepaalt de soort synchronisatie op basis van de populatie, categorie dienst en de soort administratieve
     * handeling.
     *
     * @param populatie         de populatie
     * @param soortDienst   de categorie dienst
     * @param soortAdmHandeling de soort adm handeling
     * @return de soort synchronisatie
     */
    SoortSynchronisatie bepaalSoortSynchronisatie(
        final Populatie populatie, final SoortDienst soortDienst, final SoortAdministratieveHandeling
        soortAdmHandeling);

    /**
     * Geeft de soort administratieve handelingen waarin volledigberichten worden geleverd.
     *
     * @return lijst met soort administratieve handelingen
     */
    List<SoortAdministratieveHandeling> geefHandelingenMetVolledigBericht();
}
