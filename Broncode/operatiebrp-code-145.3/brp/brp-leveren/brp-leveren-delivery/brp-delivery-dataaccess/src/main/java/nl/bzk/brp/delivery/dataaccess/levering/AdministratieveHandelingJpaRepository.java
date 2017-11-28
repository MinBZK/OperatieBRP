/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.dalapi.TeLeverenHandelingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de AdministratieveHandelingEntity class.
 */
@Repository
public final class AdministratieveHandelingJpaRepository implements AdministratieveHandelingRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Inject
    private DataSource masterDataSource;

    @Value("${brp.levering.admhndpublicatie.limit:1000}")
    private int maxHandelingenVoorPublicatie;

    private AdministratieveHandelingJpaRepository() {
    }

    @Override
    public List<BRPActie> haalAdministratieveHandelingOp(final Long administratieveHandelingId) {
        final TypedQuery<BRPActie> typedQuery = em
                .createQuery("SELECT a FROM BRPActie a "
                                + "JOIN FETCH a.administratieveHandeling admhnd WHERE admhnd.id = a.administratieveHandeling.id AND a"
                                + ".administratieveHandeling.id = "
                                + ":administratieveHandelingId",
                        BRPActie.class);
        typedQuery.setParameter("administratieveHandelingId", administratieveHandelingId);
        return typedQuery.getResultList();
    }

    @Override
    @Bedrijfsregel(Regel.R1988)
    public int markeerAdministratieveHandelingAlsVerwerkt(final Long administratieveHandelingId) {
        //update alle handelingen die nog niet geleverd zijn, en ook niet al op klaar. Status mag null zijn of in levering
        final String sql
                = "UPDATE kern.admhnd SET statuslev = :statuslevVerwerkt, tslev = :tslev WHERE id = :admhndIdVerwerkt "
                + "AND (tslev IS NULL OR statuslev = :statuslevInLevering)";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("statuslevVerwerkt", StatusLeveringAdministratieveHandeling.GELEVERD.getId());
        parameters.put("tslev", new Date());
        parameters.put("admhndIdVerwerkt", administratieveHandelingId);
        parameters.put("statuslevInLevering", StatusLeveringAdministratieveHandeling.IN_LEVERING.getId());
        return jdbcTemplate.update(sql, parameters);
    }

    @Override
    public int markeerAdministratieveHandelingAlsFout(final Long administratieveHandelingId) {
        final String sql = "UPDATE kern.admhnd SET statuslev = :statuslevFout WHERE id = :admhndIdFout "
                + "AND (tslev IS NULL OR statuslev = :statuslevInLeveringFoutPad)";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("admhndIdFout", administratieveHandelingId);
        parameters.put("statuslevFout", StatusLeveringAdministratieveHandeling.FOUT.getId());
        parameters.put("statuslevInLeveringFoutPad", StatusLeveringAdministratieveHandeling.IN_LEVERING.getId());
        return jdbcTemplate.update(sql, parameters);
    }

    @Override
    public List<TeLeverenHandelingDTO> geefHandelingenVoorAdmhndPublicatie() {
        //selecteer alle handelingen met oudste tsreg en nog niet geleverd en die handelingen die al in levering staan
        final String basicSelect =
                "SELECT admhnd.id as id, admhnd.tsreg as tsreg, admhnd.statuslev as status, afad.pers pers "
                        + "FROM kern.admhnd admhnd INNER JOIN kern.his_persafgeleidadministrati afad "
                        + "ON afad.admhnd = admhnd.id WHERE ";
        final String sql = String.format("( %s admhnd.statuslev = :televerenstatus ORDER BY admhnd.tsreg ASC LIMIT :maxAantal ) "
                + "UNION ALL "
                + "(%s admhnd.statuslev = :statuslev )", basicSelect, basicSelect);

        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("maxAantal", maxHandelingenVoorPublicatie);
        parameters.put("televerenstatus", StatusLeveringAdministratieveHandeling.TE_LEVEREN.getId());
        parameters.put("statuslev", StatusLeveringAdministratieveHandeling.IN_LEVERING.getId());
        return jdbcTemplate.query(sql, parameters, (resultSet, i) -> {
            final TeLeverenHandelingDTO teLeverenHandeling = new TeLeverenHandelingDTO();
            teLeverenHandeling.setAdmhndId(resultSet.getLong("id"));
            final java.sql.Date tsreg = resultSet.getDate("tsreg");
            teLeverenHandeling
                    .setTijdstipRegistratie(DatumUtil.vanLongNaarZonedDateTime(tsreg.getTime()));
            final int status = resultSet.getInt("status");
            if (status != 0) {
                teLeverenHandeling.setStatus(StatusLeveringAdministratieveHandeling.parseId(status));
            }
            final long pers = resultSet.getLong("pers");
            if (pers != 0) {
                teLeverenHandeling.setBijgehoudenPersoon(pers);
            }
            return teLeverenHandeling;
        });
    }

    @Override
    public int zetHandelingenStatusInLevering(final Set<Long> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        final String sql = "UPDATE kern.admhnd SET statuslev = 3 WHERE id IN (:ids) AND statuslev = 1";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("ids", ids);
        return jdbcTemplate.update(sql, parameters);
    }
}
