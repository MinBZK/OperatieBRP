/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.RelatieMdlRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.objecttype.impl.usr.BetrokkenheidMdl;
import nl.bzk.brp.model.objecttype.impl.usr.RelatieMdl;
import nl.bzk.brp.model.objecttype.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;
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

    private static final String SELECT_SQL = "SELECT relatie FROM RelatieMdl relatie, "
        + " BetrokkenheidMdl betrokkenheid, PersoonMdl persoon "
        + " WHERE relatie.id = betrokkenheid.relatie AND betrokkenheid.betrokkene = persoon.id ";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Long> haalopRelatiesVanPersoon(final Long persoonId, final RelatieSelectieFilter filter) {
        List<Long> personen = new ArrayList<Long>();
        List<NameValue> parameters = new ArrayList<NameValue>();
        String sqlString = bouwSqlFilter(parameters, filter, persoonId);
        List<RelatieMdl> relaties = haalopRelatiesGekoppeldAanPersoon(sqlString, parameters);
        List<GeslachtsAanduiding> geslachten = filter.getUitGeslachtsAanduidingen();
        List<SoortBetrokkenheid> soortBetrokkenheden = filter.getSoortRollen();
        List<SoortPersoon> persoonTypen = filter.getUitPersoonTypen();
        for (RelatieMdl relatie : relaties) {
            for (BetrokkenheidMdl betrokkenheid : relatie.getBetrokkenheden()) {
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
                if (!personen.contains(betrokkenheid.getBetrokkene().getId())) {
                    personen.add(betrokkenheid.getBetrokkene().getId());
                }
            }
        }

        return personen;
    }

    /**
     * Voer de sql statement uit met de bijbehorende parameters en geeft een lijst van persistent objecten terug.
     *
     * @param sqlString de sql statement
     * @param parameters de parameters
     * @return lijst van {@RelatieMdl}
     */
    private List<RelatieMdl> haalopRelatiesGekoppeldAanPersoon(final String sqlString,
        final List<NameValue> parameters)
    {
        TypedQuery<RelatieMdl> tQuery = em.createQuery(sqlString, RelatieMdl.class);
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
                sb.append(" AND (relatie.gegevens.datumAanvang is null or relatie.gegevens.datumAanvang <= :peilDatum)");
                sb.append(" AND (relatie.gegevens.datumEinde is null or relatie.gegevens.datumEinde > :peilDatum)");
                parameters.add(new NameValue("peilDatum", filter.getPeilDatum()));
            }
            if (null != filter.getSoortRelaties() && !filter.getSoortRelaties().isEmpty()) {
                sb.append(buildInListPart(parameters, filter.getSoortRelaties(),
                        "relatie.soort", "soortRelatie"));
            }
            if (null != filter.getSoortBetrokkenheden() && !filter.getSoortBetrokkenheden().isEmpty()) {
                sb.append(buildInListPart(parameters, filter.getSoortBetrokkenheden(),
                        "betrokkenheid.rol", "soortBetrokkenheid"));
            }
        }
        return sb.toString();
    }

    /**
     * Bouwt een deel van de sql stement in geval van een list. Bevat de list EEN object, wordt de statement
     * een '=', als er meerdere objecten wordt een 'in (?,?,?)'
     * @param parameters de parameters waarin de waarden worden toegevoegd
     * @param list de lijst van objecten
     * @param sqlAttribuutNaam het entiteit attribuut naam
     * @param paramNaam de parameter naam
     * @return de opgebouwde string
     */
    private String buildInListPart(final List<NameValue> parameters, final List<? extends Object> list,
            final String sqlAttribuutNaam, final String paramNaam)
    {
        StringBuilder sb = new StringBuilder();
        if (list.size() == 1) {
            sb.append(" AND ").append(sqlAttribuutNaam).append(" = :").append(paramNaam).append(" ");
            parameters.add(new NameValue(paramNaam, list.get(0)));
        } else {
            sb.append(" AND ").append(sqlAttribuutNaam).append(" in (");
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String selVolgNr = paramNaam + i;
                if (i > 0) {
                    sb.append(",").append(" ");
                }
                sb.append(" :").append(selVolgNr);
                parameters.add(new NameValue(selVolgNr, list.get(i)));
            }
            sb.append(") ");
        }
        return sb.toString();
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
