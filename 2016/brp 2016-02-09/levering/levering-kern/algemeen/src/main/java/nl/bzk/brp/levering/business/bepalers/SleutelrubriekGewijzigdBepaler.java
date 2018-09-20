/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Bepaler die valideert of "sleutelrubrieken" zijn gewijzigd bij een persoon.
 */
public interface SleutelrubriekGewijzigdBepaler {

    /**
     * Bepaalt of het attenderingsCriterium aangeeft dat attributen zijn gewijzigd.
     *
     * @param persoon                  De te valideren persoon
     * @param administratieveHandeling de administratieve handeling waardoor de persoon geraakt is
     * @param attenderingsCriterium    de expressie van het attenderingsCriterium
     * @param leveringsautorisatie     de leveringsautorisatie waarbinnen de attendering valt
     * @return {@code true} als het attenderingsCriterium aangeeft dat attributen zijn gewijzigd
     */
    boolean bepaalAttributenGewijzigd(PersoonHisVolledig persoon, AdministratieveHandelingModel administratieveHandeling,
        Expressie attenderingsCriterium, Leveringsautorisatie leveringsautorisatie);

}
