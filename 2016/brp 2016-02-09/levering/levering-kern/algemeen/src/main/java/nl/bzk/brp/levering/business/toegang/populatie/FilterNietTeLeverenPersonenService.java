/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Service die de personen die niet geleverd hoeven te worden filterd.
 */
public interface FilterNietTeLeverenPersonenService {

    /**
     * Filtert de personen die niet geleverd moeten worden uit de populatie-map.
     *
     * @param personen De personen.
     * @param mogelijkTeLeverenPersonen De populatie-map.
     * @param leveringAutorisatie De leveringAutorisatie.
     * @param administratieveHandeling De adminsitratieve handeling.
     * @throws ExpressieExceptie de expressie exceptie
     */
    void filterNietTeLeverenPersonen(List<PersoonHisVolledig> personen,
            Map<Integer, Populatie> mogelijkTeLeverenPersonen,
            Leveringinformatie leveringAutorisatie,
            AdministratieveHandelingModel administratieveHandeling) throws ExpressieExceptie;

}
