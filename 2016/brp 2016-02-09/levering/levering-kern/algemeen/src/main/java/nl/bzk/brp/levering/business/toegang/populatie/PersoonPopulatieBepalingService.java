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
 * De Interface PersoonPopulatieBepalingService waarin alle service-methoden bepalen of voor een leveringAutorisatie een persoon geleverd moet worden.
 */
public interface PersoonPopulatieBepalingService {

    /**
     * Bepaalt hoe de personen vallen ten opzichte van de populatie beschrijving van een leveringAutorisatie.
     *
     * @param administratieveHandeling de administratieve handeling
     * @param personen de personen
     * @param leveringAutorisatie de leveringAutorisatie
     * @return een mapping van persoonID - populatie positie
     * @throws ExpressieExceptie de expressie exceptie
     */
    Map<Integer, Populatie> geefPersoonPopulatieCorrelatie(final AdministratieveHandelingModel administratieveHandeling,
        final List<PersoonHisVolledig> personen, final Leveringinformatie leveringAutorisatie) throws ExpressieExceptie;

}
