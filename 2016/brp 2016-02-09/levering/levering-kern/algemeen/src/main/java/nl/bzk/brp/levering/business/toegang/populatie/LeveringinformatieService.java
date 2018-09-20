/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import java.util.List;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;

/**
 * De interface voor de levering autorisatie service. Deze service biedt de mogelijkheid tot het verkrijgen van leveringautorisatie objecten. Deze vormen
 * de set van gegevens die gebruikt kan worden om de autorisatie voor leveringen te verififeren.
 */
public interface LeveringinformatieService {

    /**
     * @param leveringsautorisatieID het leveringsautorisatieID
     * @param partijCode             de partijCode
     * @param soortDienst            de soortDienst
     * @return de leveringinformatie
     */
    Leveringinformatie geefLeveringinformatie(int leveringsautorisatieID, int partijCode, SoortDienst soortDienst);

    /**
     * Geeft leveringautorisatie obv leveringsautorisatieID en partijCode
     * TODO verplaatsen
     * @param leveringsautorisatieID
     * @param partijCode
     * @return
     */
    ToegangLeveringsautorisatie geefToegangLeveringautorisatie(int leveringsautorisatieID, Integer partijCode);

    /**
     * Geeft alle levering autorisaties die geldig zijn gefilterd met een lijst van categorie diensten.
     *
     * @param dienstSoorten lijst met categorie diensten
     * @return lijst met levering autorisaties.
     * @brp.bedrijfsregel R2052
     */
    List<Leveringinformatie> geefGeldigeLeveringinformaties(SoortDienst... dienstSoorten);

}
