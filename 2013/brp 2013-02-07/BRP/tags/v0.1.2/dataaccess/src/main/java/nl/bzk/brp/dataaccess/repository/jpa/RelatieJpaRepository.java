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

import nl.bzk.brp.dataaccess.converter.RelatieConverter;
import nl.bzk.brp.dataaccess.exceptie.VerplichteDataNietAanwezigExceptie;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.dataaccess.repository.historie.BetrokkenheidOuderHistorieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * JPA specifieke implementatie van de {@link RelatieRepository} repository. Deze repository biedt methodes voor het
 * ophalen van relaties, personen in relaties en het opslaan van relaties.
 */
@Repository
public class RelatieJpaRepository implements RelatieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelatieJpaRepository.class);

    private static final String SELECT_SQL = "SELECT relatie FROM PersistentRelatie relatie, "
        + " PersistentBetrokkenheid betrokkenheid, PersistentPersoon persoon "
        + " WHERE relatie.id = betrokkenheid.relatie AND betrokkenheid.betrokkene = persoon.id ";


    @PersistenceContext
    private EntityManager em;

    @Inject
    private BetrokkenheidOuderHistorieRepository betrokkenheidOuderHistorieRepository;

    /**
     * Voer de sql statement uit met de bijbehorende parameters en geeft een lijst van persistent objecten terug.
     *
     * @param sqlString de sql statement
     * @param parameters de parameters
     * @return lijst van {@PersistentRelatie}
     */
    private List<PersistentRelatie> haalopRelatiesGekoppeldAanPersoon(final String sqlString,
        final List<NameValue> parameters)
    {
        TypedQuery<PersistentRelatie> tQuery = em.createQuery(sqlString, PersistentRelatie.class);
        for (NameValue param : parameters) {
            tQuery.setParameter(param.naam, param.waarde);
        }
        return tQuery.getResultList();
    }


    /**
     * bouwt de selectie deel van de SQL query, afhankelijk van de waarden in de filter.
     *
     * @param parameters in/uit voer van lijst met parameters. Moet een lege lijst meegeven, die gevuld wordt
     * door dee methode met de correcte waarden.
     * @param filter de selectie filter
     * @param persoonId de hoofd persoon id
     * @return het te bouwen selectie SQL string
     */
    private String bouwSqlFilter(final List<NameValue> parameters, final RelatieSelectieFilter filter,
        final Long persoonId)
    {
        StringBuilder sb = new StringBuilder(SELECT_SQL);

        sb.append(" AND persoon.id = :persoonId ");
        parameters.add(new NameValue("persoonId", persoonId));
        if (null != (filter)) {
            if (null != filter.getPeilDatum()) {
                sb.append(" AND (relatie.datumAanvang is null or relatie.datumAanvang <= :peilDatum)");
                sb.append(" AND (relatie.datumEinde is null or relatie.datumEinde > :peilDatum)");
                parameters.add(new NameValue("peilDatum", filter.getPeilDatum()));
            }
            if (null != filter.getSoortRelaties() && !filter.getSoortRelaties().isEmpty()) {
                if (filter.getSoortRelaties().size() == 1) {
                    sb.append(" AND relatie.soortRelatie = :soortRelatie");
                    parameters.add(new NameValue("soortRelatie", filter.getSoortRelaties().get(0)));
                } else {
                    StringBuilder inSel = new StringBuilder(" AND relatie.soortRelatie in (");
                    int size = filter.getSoortRelaties().size();
                    for (int i = 0; i < size; i++) {
                        String selVolgNr = "soortRelatie" + i;
                        if (i > 0) {
                            inSel.append(",");
                        }
                        inSel.append(" :").append(selVolgNr);
                        parameters.add(new NameValue(selVolgNr, filter.getSoortRelaties().get(i)));
                    }
                    inSel.append(")");
                    sb.append(inSel);
                }
            }
            if (null != filter.getSoortBetrokkenheden() && !filter.getSoortBetrokkenheden().isEmpty()) {
                if (filter.getSoortBetrokkenheden().size() == 1) {
                    sb.append(" AND betrokkenheid.soortBetrokkenheid = :soortBetrokkenheid");
                    parameters.add(new NameValue("soortBetrokkenheid", filter.getSoortBetrokkenheden().get(0)));
                } else {
                    StringBuilder inSel = new StringBuilder(" AND betrokkenheid.soortBetrokkenheid in (");
                    int size = filter.getSoortRelaties().size();
                    for (int i = 0; i < size; i++) {
                        String selVolgNr = "soortBetrokkenheid" + i;
                        if (i > 0) {
                            inSel.append(",");
                        }
                        inSel.append(" :").append(selVolgNr);
                        parameters.add(new NameValue(selVolgNr, filter.getSoortBetrokkenheden().get(i)));
                    }
                    inSel.append(")");
                    sb.append(inSel);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public List<Long> haalopRelatiesVanPersoon(final Long primaryId, final RelatieSelectieFilter filter) {
        List<Long> personen = new ArrayList<Long>();
        List<NameValue> parameters = new ArrayList<NameValue>();
        String sqlString = bouwSqlFilter(parameters, filter, primaryId);
        List<PersistentRelatie> relaties = haalopRelatiesGekoppeldAanPersoon(sqlString, parameters);
        List<GeslachtsAanduiding> geslachten = filter.getUitGeslachtsAanduidingen();
        List<SoortBetrokkenheid> soortBetrokkenheden = filter.getSoortRollen();
        List<SoortPersoon> persoonTypen = filter.getUitPersoonTypen();
        for (PersistentRelatie relatie : relaties) {
            for (PersistentBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                if ((null != soortBetrokkenheden)
                    && (!soortBetrokkenheden.contains(betrokkenheid.getSoortBetrokkenheid())))
                {
                    continue;
                }
                if ((null != geslachten)
                    && (!geslachten.contains(betrokkenheid.getBetrokkene().getGeslachtsAanduiding())))
                {
                    continue;
                }
                if ((null != persoonTypen)
                    && (!persoonTypen.contains(betrokkenheid.getBetrokkene().getSoortPersoon())))
                {
                    continue;
                }
                if (!personen.contains(betrokkenheid.getBetrokkene().getId())) {
                    personen.add(betrokkenheid.getBetrokkene().getId());
                }
            }
        }

        return personen;
    }

    @Override
    public List<Long> haalopPartners(final Long primaryId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        return haalopRelatiesVanPersoon(primaryId, filter);
    }

    @Override
    public List<Long> haalopEchtgenoten(final Long primaryId) {
        return haalopEchtgenoten(primaryId, null);
    }

    @Override
    public List<Long> haalopEchtgenoten(final Long primaryId, final Integer peilDatum) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.HUWELIJK);
        filter.setPeilDatum(peilDatum);
        return haalopRelatiesVanPersoon(primaryId, filter);
    }

    @Override
    public List<Long> haalopKinderen(final Long primaryId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.KIND);
        return haalopRelatiesVanPersoon(primaryId, filter);
    }

    @Override
    public List<Long> haalopOuders(final Long primaryId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        filter.setSoortRollen(SoortBetrokkenheid.OUDER);
        return haalopRelatiesVanPersoon(primaryId, filter);
    }

    @Override
    public List<Long> haalopFamilie(final Long primaryId) {
        RelatieSelectieFilter filter = new RelatieSelectieFilter();
        filter.setSoortRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return haalopRelatiesVanPersoon(primaryId, filter);
    }

    @Override
    public Long opslaanNieuweRelatie(final Relatie relatie, final Integer datumAanvang) {
        if (relatie.getBetrokkenheden() == null || relatie.getBetrokkenheden().isEmpty()) {
            LOGGER.error("Kan relatie niet opslaan vanwege missende betrokkenheden.");
            throw new VerplichteDataNietAanwezigExceptie("Geen betrokkenheden opgegeven voor relatie");
        }

        for (Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
            if (betrokkenheid.getBetrokkene() == null || betrokkenheid.getBetrokkene().getId() == null) {
                String bericht = "Betrokkene in relatie beschikt niet over primaire sleutel, waardoor deze niet "
                    + "gerefereerd kan worden bij opslag. Gebruik alleen opgeslagen objecten voor betrokkenen in "
                    + "relatie opslag.";
                LOGGER.error(bericht);
                throw new VerplichteDataNietAanwezigExceptie(bericht);
            }
        }

        final Date registratieTijd = new Date();

        // Opslaan in A-laag
        PersistentRelatie nieuweRelatie = RelatieConverter.bouwPersistentRelatie(relatie, datumAanvang);
        em.persist(nieuweRelatie);

        // Opslaan in C-laag
        werkHistorieBij(nieuweRelatie, registratieTijd, datumAanvang);

        return nieuweRelatie.getId();
    }

    /**
     * Werkt de historie van een relatie bij op basis van de opgegeven waarden en de soort van de relatie. Voor een
     * familierechtelijke betrekking dient er op de relatie geen historie bijgehouden te worden, maar op de
     * betrokkenheden van de ouders wel. Voor huwelijk en geregistreerd partnerschap dient er wel ook op de relatie
     * historie bijgehouden te worden.
     *
     * @param nieuweRelatie de nieuwe (operationeel model) relatie.
     * @param registratieTijd het tijdstip van registratie.
     * @param datumAanvang de datum van aanvang.
     */
    private void werkHistorieBij(final PersistentRelatie nieuweRelatie, final Date registratieTijd,
        final Integer datumAanvang)
    {
        if (SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == nieuweRelatie.getSoortRelatie()) {
            // Bij familie rechtelijke betrekking geen historie op relatie; alleen op betrokkenheden ouders
            for (PersistentBetrokkenheid betrokkenheid : nieuweRelatie.getBetrokkenheden()) {
                if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()) {
                    betrokkenheidOuderHistorieRepository
                        .opslaanHistorie(betrokkenheid, datumAanvang, null, registratieTijd);
                }
            }
        } else {
            throw new UnsupportedOperationException(
                "Deze methode ondersteund nog geen historie voor huwelijk en/of geregistreerd partnerschap");
        }
    }


    /** Een pure shortcut om 2 objecten bij elkaar te houden. */
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
