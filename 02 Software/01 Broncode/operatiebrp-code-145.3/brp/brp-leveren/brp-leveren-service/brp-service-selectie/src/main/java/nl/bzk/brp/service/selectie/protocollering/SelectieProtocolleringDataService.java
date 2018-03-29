/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.protocollering;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;

/**
 * Transactionele service tbv het protocolleren van de selectie gegevens
 */
public interface SelectieProtocolleringDataService {

    /**
     * @return een lijst met te protocolleren {@link Selectietaak}en.
     */
    List<Selectietaak> selecteerTeProtocollerenSelectietaken();

    /**
     * @param selectietaak selectietaak
     * @param status status
     * @return selectietaak na update
     */
    Selectietaak updateSelectietaakStatus(Selectietaak selectietaak, SelectietaakStatus status);

    /**
     * Set de voortgang van een gegeven {@link Selectietaak}
     * @param selectietaak de selectietaak
     * @param regelsVerwerkt het aantal regels dat verwerkt is
     * @param totaalAantalRegels het aantal regels dat totaal verwerkt moet worden.
     */
    void setVoortgang(Selectietaak selectietaak, int regelsVerwerkt, int totaalAantalRegels);

}
