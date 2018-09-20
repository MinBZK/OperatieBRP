/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Bepaler die kan bepalen hoe de positie van een een persoon is ten opzichte van een populatie beschreven door een {@link Expressie}.
 */
public interface PopulatieBepaler {

    /**
     * Bepaal de positie van een persoon ten opzichte van een populatie.
     *
     * @param persoon de persoon waarvan de waarde wordt bepaald
     * @param administratieveHandeling de administratieve handeling ten opzichte waarvan de bepaling wordt gedaan
     * @param populatiebeperking de populatiebeperking als expressie
     * @param leveringsautorisatie de leveringsautorisatie waarvoor de populatie wordt bepaald
     * @return waar de persoon valt ten opzichte van de populatie
     */
    Populatie bepaalInUitPopulatie(PersoonHisVolledig persoon, AdministratieveHandelingModel administratieveHandeling,
        Expressie populatiebeperking, Leveringsautorisatie leveringsautorisatie);
}
