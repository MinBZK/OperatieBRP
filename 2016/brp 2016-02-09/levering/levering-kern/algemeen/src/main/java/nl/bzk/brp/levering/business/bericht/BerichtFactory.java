/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Service voor het maken van berichten.
 */
public interface BerichtFactory {

    /**
     * Stelt een lijst met synchronisatieberichten samen, afhankelijk van de administratieve handeling en het leveringsautorisatie/dienst dat verwerkt
     * wordt komt er een mutatieberict en/of een volledigbericht in.
     *
     * @param personen                 De persoon his volledigs
     * @param leveringAutorisatie      De leveringAutorisatie
     * @param populatieMap             de persoonPopulatie correlaties, die aangeven hoe een persoon in de populatie staat
     * @param administratieveHandeling De adminstratieve handeling
     * @return De lijst met synchronisatieberichten.
     */
    List<SynchronisatieBericht> maakBerichten(List<PersoonHisVolledigView> personen, Leveringinformatie leveringAutorisatie,
        Map<Integer, Populatie> populatieMap, AdministratieveHandelingModel administratieveHandeling);

    /**
     * Stelt een VolledigBericht samen.
     *
     * @param persoon                  De persoon his volledig
     * @param leveringAutorisatie      De leveringAutorisatie
     * @param administratieveHandeling De adminstratieve handeling
     * @param materieelVanaf           materieel vanaf moment, om vulbericht te beperken
     * @return een VolledigBericht.
     */
    VolledigBericht maakVolledigBericht(PersoonHisVolledig persoon, Leveringinformatie leveringAutorisatie,
        AdministratieveHandelingModel administratieveHandeling, DatumAttribuut materieelVanaf);

    /**
     * Maakt de stuurgegevens voor een leveringbericht.
     *
     * @param partij de ontvangende partij
     * @return de gemaakte stuurgegevens
     */
    BerichtStuurgegevensGroepBericht maakStuurgegevens(Partij partij);

    /**
     * Maakt de stuurgegevens voor een leveringbericht.
     *
     * @param partij          de ontvangende partij
     * @param crossReferentie de crossReferentie naar het originele bericht
     * @return de gemaakte stuurgegevens
     */
    BerichtStuurgegevensGroepBericht maakStuurgegevens(Partij partij, String crossReferentie);

    /**
     * Maakt de parameters voor een leveringbericht.
     *
     * @param leveringAutorisatie de leveringAutorisatie
     * @param soortSynchronisatie het soort synchronisatie
     * @return de gemaakte parameters
     */
    BerichtParametersGroepBericht maakParameters(Leveringinformatie leveringAutorisatie, SoortSynchronisatie soortSynchronisatie);
}
