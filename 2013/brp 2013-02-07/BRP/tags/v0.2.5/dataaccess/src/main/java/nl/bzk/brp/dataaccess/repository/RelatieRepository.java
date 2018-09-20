/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.springframework.stereotype.Repository;


/** Repository voor de {@link RelatieModel} class. */
@Repository
public interface RelatieRepository {

    /**
     * Opslaan nieuwe relatie (bijv. ten behoeve van eerste inschrijving). Deze methode slaat de relatie op, inclusief
     * de betrokkenheden, maar cascadeert niet door naar de betrokkene. Deze methode gaat er dan ook van uit dat alle
     * betrokkene reeds zijn gepersisteerd en dus inclusief primaire sleutel (ID) in de {@link RelatieModel} instantie
     * aanwezig zijn.
     *
     * @param relatie de relatie die opgeslagen dient te worden.
     * @param actie de actie die heeft geleid tot deze nieuwe relatie.
     * @param datumAanvangGeldigheid Datum waarop de relatie ingaat.
     * @return de id van de opgeslagen relatie.
     */
    RelatieModel opslaanNieuweRelatie(final RelatieModel relatie, final ActieModel actie,
            final Datum datumAanvangGeldigheid);

    /**
     * @brp.bedrijfsregel BRBY0001
     *
     *                    Controlleert of persoonId1 en persoonId2 verwant aan elkaar zijn.
     * @param persoonId1 technisch id van persoon1
     * @param persoonId2 technisch id van persoon2
     * @return true als persoonId1 verwant is aan persoonId2
     */
    boolean isVerwant(Integer persoonId1, Integer persoonId2);

    /**
     * Controlleert of persoon partners heeft.
     *
     * @param persoonId id van persoon waarnaar gekeken moet worden.
     * @param peilDatum datum aanvang van huwelijk, als het in de toekomst ligt wordt automatisch gekeken naar vandaag
     *            (systeem datum)
     * @return true wanneer persoon partners heeft.
     */
    boolean heeftPartners(Integer persoonId, Datum peilDatum);

    /**
     * Haalt een lijst op van persoonIds die een huwelijk of registeerd partnerschap hebben met de persoon met de
     * opgegeven (technische) persoon id.
     * <p/>
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de partners moeten worden opgehaald.
     * @param peilDatum de datum waarop gekeken moet worden.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopPartners(Integer persoonId, Datum peilDatum);

    /**
     * Haalt een lijst op van persoonIds die een huwelijk relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     * <p/>
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de echtgeno(o)t(e) moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopEchtgenoten(Integer persoonId);

    /**
     * Haalt een lijst op van persoonIds die een huwelijk relatie hebben met de persoon met de opgegeven (technische)
     * persoon id op de opgegeven peildatum.
     * <p/>
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de echtgeno(o)t(e) moeten worden opgehaald.
     * @param peilDatum de datum waarop voor de persoon de echtgeno(o)t(e) gezocht worden.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopEchtgenoten(Integer persoonId, Datum peilDatum);

    /**
     * Haalt een lijst op van persoonIds die een kind relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de kind relaties moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopKinderen(Integer persoonId);

    /**
     * Haalt een lijst op van persoonIds die een ouder relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de ouder relaties moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopOuders(Integer persoonId);

    /**
     * Haalt een lijst op van persoonIds die een familie relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     * <p/>
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de familie relaties moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopFamilie(Integer persoonId);

    /**
     * Een generieke methode om de technische ids van de personen op te halen die een specifieke relatie hebben met de
     * opgegeven persoon. Aan de hand van de waarden in de filter kan men bepaalde relatie type / betrokkenheid type
     * etc. selecteren.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de gerelateerde personen opgehaald dienen te
     *            worden.
     * @param filter de filter die alleen specifieke relaties (van een bepaald type of met een specifieke rol) filtert.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Integer> haalopPersoonIdsVanRelatiesVanPersoon(Integer persoonId, RelatieSelectieFilter filter);

    /**
     * Haal alle betrokkenheden op van de opgegeven persoon.
     *
     * @param persoon de persoonModel
     * @param filter de filter die alleen specifieke relaties (van een bepaald type of met een specifieke rol) filtert.
     * @return betrokkenheden
     */
    List<BetrokkenheidModel> haalOpBetrokkenhedenVanPersoon(final PersoonModel persoon, RelatieSelectieFilter filter);

    /**
     * Vind relaties van de opgegeven soorten waarin zowel persoon1 als persoon2 betrokkenen zijn.
     *
     * @param persoon1 Persoon 1.
     * @param persoon2 Persoon 2.
     * @param soortBetrokkenheid De rol die persoon 1 en 2 hebben.
     * @param peilDatum De datum waarop de relaties en betrokkenheden actief zijn, dus niet beeindigd.
     * @param soorten Soorten relaties die gezocht worden.
     * @return Lijst van relaties.
     */
    List<RelatieModel> vindSoortRelatiesMetPersonenInRol(final PersoonModel persoon1, final PersoonModel persoon2,
            final SoortBetrokkenheid soortBetrokkenheid, final Datum peilDatum, final SoortRelatie... soorten);

    /**
     *
     * @param id Database id
     * @return relatiemodel
     */
    RelatieModel vindBijId(Integer id);

    /**
     *
     * @param relatieModel relatie die aangepast moet worden
     * @param relatieStandaardGroepModel nieuwe relatie gegevens
     */
    void opslaanVeranderdeRelatieStandaardGegevens(RelatieModel relatieModel,
            RelatieStandaardGroepModel relatieStandaardGroepModel, ActieModel actieModel);
}
