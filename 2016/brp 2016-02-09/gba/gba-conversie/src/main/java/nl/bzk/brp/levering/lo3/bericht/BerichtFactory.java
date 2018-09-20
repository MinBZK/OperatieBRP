/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * Service voor het maken van lo3 berichten.
 */
public interface BerichtFactory {

    /**
     * Stelt een lijst met synchronisatieberichten samen, afhankelijk van de administratieve handeling en het
     * abonnement/dienst dat verwerkt wordt komt er een kennisgeving en/of een Volledigbericht in.
     *
     * @param personen De persoon his volledigs
     * @param leveringAutorisatie De leveringAutorisatie
     * @param populatieMap de persoonPopulatie correlaties, die aangeven hoe een persoon in de populatie staat
     * @param administratieveHandeling De adminstratieve handeling
     * @return De lijst met leveringsberichten.
     */
    List<SynchronisatieBericht> maakBerichten(
        List<PersoonHisVolledig> personen,
        Leveringinformatie leveringAutorisatie,
        Map<Integer, Populatie> populatieMap,
        AdministratieveHandelingModel administratieveHandeling);

    /**
     * Maak een bericht tbv plaatsen afnemersindicatie.
     *
     * @param persoon persoon
     * @return bericht
     */
    Bericht maakAg01Bericht(final PersoonHisVolledig persoon);
}
