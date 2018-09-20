/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service.bericht;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * AfleverService is verantwoordelijk voor het het afleveren van berichten n.a.v. een administratieve
 * handeling. Hiertoe krijgt de service de handeling, de geraakte bijgehoudenPersonen en de lijst van
 * leveringAutorisatie combinaties die een levering moeten krijgen.
 */
public interface AfleverService {

    /**
     * Levert berichten.
     *
     * @param administratieveHandeling De administratieve handeling die de oorsprong is van de leveringen
     * @param bijgehoudenPersonen de bijgehoudenPersonen geraakt door de administratieve handeling
     * @param leveringinformatiePopulatieMap map met als key een Leveringinformatie, en als value de positie van een
     *                                 persoon in de populatie van de leveringsautorisatie
     * @param persoonAttributenMap de persoon attributen mapping. key is de persoon Id. Geneste map is expressie string -> attribuut. Alleen relevant voor brp
     * @param persoonOnderzoekenMap de persoon onderzoeken map
     * @return resultaat van de verwerking
     */
    LeveringautorisatieVerwerkingResultaat leverBerichten(
            AdministratieveHandelingModel administratieveHandeling,
            List<PersoonHisVolledig> bijgehoudenPersonen,
            Map<Leveringinformatie, Map<Integer, Populatie>> leveringinformatiePopulatieMap, Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap,
        Map<Integer, Map<Integer, List<Attribuut>>>  persoonOnderzoekenMap);
}
