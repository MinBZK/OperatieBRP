/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.exceptie.VerplichteDataNietAanwezigExceptie;
import nl.bzk.brp.dataaccess.repository.RelatieMdlRepository;
import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * JPA specifieke implementatie van de {@link nl.bzk.brp.dataaccess.repository.RelatieMdlRepository} repository.
 * Deze repository biedt methodes voor het ophalen van relaties, personen in relaties en het opslaan van relaties.
 */
@Repository
public class RelatieMdlJpaRepository implements RelatieMdlRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieMdlJpaRepository.class);

    private static final String SELECT_RELATIES_QUERY = "SELECT relatie FROM RelatieModel relatie, "
        + "BetrokkenheidModel betrokkenheid, PersoonModel persoon "
        + "WHERE relatie.id = betrokkenheid.relatie AND betrokkenheid.betrokkene = persoon.id ";

    private static final String SELECT_RELATIES_VOOR_PERSONEN_QUERY = "SELECT relatie FROM RelatieModel relatie "
        + "WHERE ((SELECT be FROM BetrokkenheidModel be WHERE be.betrokkene = :persoon1 "
        + "        AND be.relatie = relatie AND be.rol = :srtBetr) MEMBER OF relatie.betrokkenheden) "
        + "  AND ((SELECT be FROM BetrokkenheidModel be WHERE be.betrokkene = :persoon2 AND be.relatie = relatie "
        + "        AND be.rol = :srtBetr) MEMBER OF relatie.betrokkenheden) "
        + "  AND (relatie.gegevens.datumAanvang IS NULL OR relatie.gegevens.datumAanvang <= :peildatum) "
        + "  AND (relatie.gegevens.datumEinde IS NULL OR relatie.gegevens.datumEinde > :peildatum) ";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private GroepHistorieRepository<BetrokkenheidModel> betrokkenheidOuderschapHistorieRepository;
    @Inject
    private GroepHistorieRepository<BetrokkenheidModel> betrokkenheidOuderlijkgezagHistorieRepository;

    @Override
    public RelatieModel opslaanNieuweRelatie(final RelatieModel relatie, final Datum datumAanvangGeldigheid,
        final Date tijdstipRegistratie, final ActieModel actie)
    {
        if (relatie.getBetrokkenheden() == null || relatie.getBetrokkenheden().isEmpty()) {
            LOGGER.error("Kan relatie niet opslaan vanwege missende betrokkenheden.");
            throw new VerplichteDataNietAanwezigExceptie("Geen betrokkenheden opgegeven voor relatie");
        }

        for (BetrokkenheidModel betrokkenheid : relatie.getBetrokkenheden()) {
            if (betrokkenheid.getBetrokkene() == null || betrokkenheid.getBetrokkene().getId() == null) {
                String bericht = "Betrokkene in relatie beschikt niet over primaire sleutel, waardoor deze niet "
                    + "gerefereerd kan worden bij opslag. Gebruik alleen opgeslagen objecten voor betrokkenen in "
                    + "relatie opslag.";
                LOGGER.error(bericht);
                throw new VerplichteDataNietAanwezigExceptie(bericht);
            }
        }

        // Opslaan in A-laag
        em.persist(relatie);

        opslaanRelatieHistorie(relatie, datumAanvangGeldigheid, tijdstipRegistratie, actie);

        return relatie;
    }

    /**
     * Slaat de actie op die heeft geleid tot een wijziging/toevoeging van een relatie.
     *
     * @param actie de actie die dient te worden opgeslagen.
     */
    private void opslaanActie(final ActieModel actie) {
        em.persist(actie);
    }

    /**
     * Slaat de historie op van een gewijzigde/toegevoegde relatie.
     *
     * @param relatie de gewijzigde/toegevoegde relatie
     * @param datumAanvangGeldigheid de datum van aanvang van de wijziging/toevoeging
     * @param tijdstipRegistratie het tijdstip van de betreffende registratie
     * @param actie de actie die heeft geleid tot de wijziging/toevoeging
     */
    private void opslaanRelatieHistorie(final RelatieModel relatie, final Datum datumAanvangGeldigheid,
        final Date tijdstipRegistratie, final ActieModel actie)
    {
        opslaanActie(actie);
        if (SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort()) {
            // Bij familie rechtelijke betrekking geen historie op relatie; alleen op betrokkenheden ouders
            for (BetrokkenheidModel betrokkenheid : relatie.getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
                    if (betrokkenheid.getBetrokkenheidOuderschap() != null) {
                        betrokkenheidOuderschapHistorieRepository.persisteerHistorie(betrokkenheid, actie,
                            datumAanvangGeldigheid, null);
                    }
                    if (betrokkenheid.getBetrokkenheidOuderlijkGezag() != null) {
                        betrokkenheidOuderlijkgezagHistorieRepository.persisteerHistorie(betrokkenheid, actie,
                            datumAanvangGeldigheid, null);
                    }
                }
            }
        }
    }

    @Override
    public List<Long> haalopPartners(final Long persoonId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        return haalopPersoonIdsVanRelatiesVanPersoon(persoonId, filter);
    }

    @Override
    public List<Long> haalopEchtgenoten(final Long persoonId) {
        return haalopEchtgenoten(persoonId, null);
    }

    @Override
    public List<Long> haalopEchtgenoten(final Long persoonId, final Datum peilDatum) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setPeilDatum(peilDatum);
        return haalopPersoonIdsVanRelatiesVanPersoon(persoonId, filter);
    }

    @Override
    public List<Long> haalopKinderen(final Long persoonId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        return haalopPersoonIdsVanRelatiesVanPersoon(persoonId, filter);
    }

    @Override
    public List<Long> haalopOuders(final Long persoonId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        return haalopPersoonIdsVanRelatiesVanPersoon(persoonId, filter);
    }

    @Override
    public List<Long> haalopFamilie(final Long persoonId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return haalopPersoonIdsVanRelatiesVanPersoon(persoonId, filter);
    }

    @Override
    public List<Long> haalopPersoonIdsVanRelatiesVanPersoon(final Long persoonId, final RelatieSelectieFilter filter) {
        List<NameValue> parameters = new ArrayList<NameValue>();
        String query = bouwRelatieQuery(parameters, filter, persoonId);
        List<RelatieModel> relaties = voerRelatieSelectieQueryUit(query, parameters);

        return filterRelatiesEnExtraheerGerelateerdePersoonIds(filter, relaties);
    }

    @Override
    public List<RelatieModel> vindSoortRelatiesMetPersonenInRol(final PersoonModel persoon1,
        final PersoonModel persoon2, final SoortBetrokkenheid soortBetrokkenheid, final Datum peilDatum,
        final SoortRelatie... soorten)
    {
        final StringBuilder sb = new StringBuilder(SELECT_RELATIES_VOOR_PERSONEN_QUERY);

        sb.append("  AND relatie.soort in (");
        for (SoortRelatie soortRelatie : soorten) {
            sb.append(":").append(soortRelatie.getCode()).append(",");
        }
        //Verwijder laatste comma
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        TypedQuery<RelatieModel> query = em.createQuery(sb.toString(), RelatieModel.class);
        query.setParameter("persoon1", persoon1);
        query.setParameter("persoon2", persoon2);
        query.setParameter("srtBetr", soortBetrokkenheid);
        query.setParameter("peildatum", peilDatum);

        for (SoortRelatie soortRelatie : soorten) {
            query.setParameter(soortRelatie.getCode(), soortRelatie);
        }
        return query.getResultList();
    }

    /**
     * Filtert de opgegeven relaties en retourneert een lijst van de persoons ids van die personen die in de relaties
     * zitten die voldoen aan de fitler.
     *
     * @param filter de filter waaraan de relaties en betrokkenen in de relaties moeten voldoen.
     * @param relaties de relaties die gefilterd moeten worden en waarvan de persoonids moeten worden geretourneerd.
     * @return een lijst van persoonids die in de relaties voorkomen die voldoen aan de opgegeven filter.
     */
    private List<Long> filterRelatiesEnExtraheerGerelateerdePersoonIds(final RelatieSelectieFilter filter,
        final List<RelatieModel> relaties)
    {
        List<Long> idsGerelateerdePersonen = new ArrayList<Long>();

        List<GeslachtsAanduiding> geslachten = filter.getUitGeslachtsAanduidingen();
        List<SoortBetrokkenheid> soortBetrokkenheden = filter.getSoortRollen();
        List<SoortPersoon> persoonTypen = filter.getUitPersoonTypen();

        for (RelatieModel relatie : relaties) {
            for (BetrokkenheidModel betrokkenheid : relatie.getBetrokkenheden()) {
                if ((null != soortBetrokkenheden)
                    && (!soortBetrokkenheden.contains(betrokkenheid.getRol())))
                {
                    continue;
                }
                if ((null != geslachten)
                    && (!geslachten.contains(betrokkenheid.getBetrokkene().getGeslachtsAanduiding()
                                                          .getGeslachtsAanduiding())))
                {
                    continue;
                }
                if ((null != persoonTypen)
                    && (!persoonTypen.contains(betrokkenheid.getBetrokkene().getSoort())))
                {
                    continue;
                }
                if (!idsGerelateerdePersonen.contains(betrokkenheid.getBetrokkene().getId())) {
                    idsGerelateerdePersonen.add(betrokkenheid.getBetrokkene().getId());
                }
            }
        }

        return idsGerelateerdePersonen;
    }

    /**
     * Voert de query uit met de bijbehorende parameters en geeft een lijst van persistent relatie objecten terug.
     *
     * @param query de (JPQL) query
     * @param parameters de parameters
     * @return lijst van {@RelatieModel}
     */
    private List<RelatieModel> voerRelatieSelectieQueryUit(final String query,
        final List<NameValue> parameters)
    {
        TypedQuery<RelatieModel> tQuery = em.createQuery(query, RelatieModel.class);
        for (NameValue parameter : parameters) {
            tQuery.setParameter(parameter.naam, parameter.waarde);
        }
        return tQuery.getResultList();
    }

    /**
     * Bouwt een (JPQL) selectie query voor het ophalen van relaties van een persoon, waarbij de relaties worden
     * gefilterd conform de opgegeven filter. De voor de uitvoer te gebruiken parameters worden bijgehouden in de
     * in/uit parameter <code>parameters</code>.
     *
     * @param parameters in-/uitvoer van lijst met parameters. Moet een lege lijst meegeven, die gevuld wordt
     * door deze methode met de correcte waarden. Merk op dat deze parameter dus een in/uit parameters is.
     * @param filter de selectie filter
     * @param persoonId de hoofd persoon id
     * @return het te bouwen selectie SQL string
     */
    private String bouwRelatieQuery(final List<NameValue> parameters, final RelatieSelectieFilter filter,
        final Long persoonId)
    {
        StringBuilder sb = new StringBuilder(SELECT_RELATIES_QUERY);
        sb.append(" AND persoon.id = :persoonId ");
        parameters.add(new NameValue("persoonId", persoonId));

        if (null != (filter)) {
            if (null != filter.getPeilDatum()) {
                sb.append(
                    " AND (relatie.gegevens.datumAanvang is null or relatie.gegevens.datumAanvang <= :peilDatum)");
                sb.append(" AND (relatie.gegevens.datumEinde is null or relatie.gegevens.datumEinde > :peilDatum)");
                parameters.add(new NameValue("peilDatum", filter.getPeilDatum()));
            }
            if (null != filter.getSoortRelaties() && !filter.getSoortRelaties().isEmpty()) {
                sb.append(bouwWhereClausuleVoorLijst(parameters, filter.getSoortRelaties(),
                    "relatie.soort", "soortRelatie"));
            }
            if (null != filter.getSoortBetrokkenheden() && !filter.getSoortBetrokkenheden().isEmpty()) {
                sb.append(bouwWhereClausuleVoorLijst(parameters, filter.getSoortBetrokkenheden(),
                    "betrokkenheid.rol", "soortBetrokkenheid"));
            }
        }
        return sb.toString();
    }

    /**
     * Bouwt een deel van de sql statement in geval van een lijst. Bevat de lijst slechts een enkel object, dan wordt
     * het statement een '=', als er meerdere objecten wordt een 'in (?,?,?)'.
     *
     * @param parameters de parameters waarin de waarden worden toegevoegd
     * @param lijst de lijst van objecten
     * @param attribuutNaam het entiteit attribuut naam
     * @param parameterNaam de parameter naam
     * @return de opgebouwde string
     */
    private String bouwWhereClausuleVoorLijst(final List<NameValue> parameters, final List<? extends Object> lijst,
        final String attribuutNaam, final String parameterNaam)
    {
        StringBuilder sb = new StringBuilder();
        if (lijst.size() == 1) {
            sb.append(" AND ").append(attribuutNaam).append(" = :").append(parameterNaam).append(" ");
            parameters.add(new NameValue(parameterNaam, lijst.get(0)));
        } else {
            sb.append(" AND ").append(attribuutNaam).append(" in (");
            int size = lijst.size();
            for (int i = 0; i < size; i++) {
                final String genummerdeParameterNaam = parameterNaam + i;
                if (i > 0) {
                    sb.append(",").append(" ");
                }
                sb.append(" :").append(genummerdeParameterNaam);
                parameters.add(new NameValue(genummerdeParameterNaam, lijst.get(i)));
            }
            sb.append(") ");
        }
        return sb.toString();
    }

    /** Een pure shortcut om 2 objecten bij elkaar te houden als name-value' paar . */
    private static final class NameValue {

        private final String naam;
        private final Object waarde;

        /**
         * Een pure shortcut om 2 objecten bij elkaar te houden.
         *
         * @param naam de naam
         * @param waarde de waarde
         */
        private NameValue(final String naam, final Object waarde) {
            this.naam = naam;
            this.waarde = waarde;
        }
    }
}
