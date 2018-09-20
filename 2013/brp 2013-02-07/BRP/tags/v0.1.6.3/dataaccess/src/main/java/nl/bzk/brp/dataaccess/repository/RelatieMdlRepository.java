/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Date;
import java.util.List;

import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.springframework.stereotype.Repository;

/** Repository voor de {@link RelatieModel} class. */
@Repository
public interface RelatieMdlRepository {

    /**
     * Opslaan nieuwe relatie (bijv. ten behoeve van eerste inschrijving). Deze methode slaat de relatie op, inclusief
     * de betrokkenheden, maar cascadeert niet door naar de betrokkene. Deze methode gaat er dan ook van uit dat alle
     * betrokkene reeds zijn gepersisteerd en dus inclusief primaire sleutel (ID) in de
     * {@link nl.bzk.brp.model.logisch.Relatie} instantie aanwezig zijn.
     *
     * @param relatie de relatie die opgeslagen dient te worden.
     * @param datumAanvangGeldigheid Datum waarop de relatie ingaat.
     * @param tijdstipRegistratie tijdstip waarop de registratie plaatsvindt.
     * @param actie de actie die heeft geleid tot deze nieuwe relatie.
     * @return de id van de opgeslagen relatie.
     */
    RelatieModel opslaanNieuweRelatie(final RelatieModel relatie, final Datum datumAanvangGeldigheid,
        final Date tijdstipRegistratie, final ActieModel actie);

    /**
     * Haalt een lijst op van persoonIds die een huwelijk of registeerd partnerschap hebben met de persoon met de
     * opgegeven (technische) persoon id.
     * <p/>
     * Let op; de lijst bevat ook de persoon zelf.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de partners moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopPartners(Long persoonId);

    /**
     * Haalt een lijst op van persoonIds die een huwelijk relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     * <p/>
     * Let op; de lijst bevat ook de persoon zelf.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de echtgeno(o)t(e) moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopEchtgenoten(Long persoonId);

    /**
     * Haalt een lijst op van persoonIds die een huwelijk relatie hebben met de persoon met de opgegeven (technische)
     * persoon id op de opgegeven peildatum.
     * <p/>
     * Let op; de lijst bevat ook de persoon zelf.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de echtgeno(o)t(e) moeten worden opgehaald.
     * @param peilDatum de datum waarop voor de persoon de echtgeno(o)t(e) gezocht worden.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopEchtgenoten(Long persoonId, Datum peilDatum);

    /**
     * Haalt een lijst op van persoonIds die een kind relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de kind relaties moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopKinderen(Long persoonId);

    /**
     * Haalt een lijst op van persoonIds die een ouder relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de ouder relaties moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopOuders(Long persoonId);

    /**
     * Haalt een lijst op van persoonIds die een familie relatie hebben met de persoon met de opgegeven (technische)
     * persoon id.
     * <p/>
     * Let op; de lijst bevat ook de persoon zelf.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de familie relaties moeten worden opgehaald.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopFamilie(Long persoonId);

    /**
     * Een generieke methode om de technische ids van de personen op te halen die een specifieke relatie hebben met de
     * opgegeven persoon. Aan de hand van de waarden in de filter kan men bepaalde relatie type / betrokkenheid type
     * etc. selecteren.
     *
     * @param persoonId de technische id van de hoofdpersoon waarvoor de gerelateerde personen opgehaald dienen te
     * worden.
     * @param filter de filter die alleen specifieke relaties (van een bepaald type of met een specifieke rol) filtert.
     * @return de (technische) persoon ids van de relaties.
     */
    List<Long> haalopPersoonIdsVanRelatiesVanPersoon(Long persoonId, RelatieSelectieFilter filter);

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

}
