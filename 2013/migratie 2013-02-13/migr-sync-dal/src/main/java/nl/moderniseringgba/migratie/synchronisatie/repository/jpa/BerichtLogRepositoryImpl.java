/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.repository.BerichtLogRepository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de BerichtLogRepository.
 */
@Repository
public final class BerichtLogRepositoryImpl implements BerichtLogRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Inject
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtLog findLaatsteBerichtLogVoorANummer(final BigDecimal administratienummer) {
        final String query =
                "FROM BerichtLog WHERE administratienummer = :administratienummer ORDER BY tijdstipVerwerking DESC";
        final List<BerichtLog> resultList =
                em.createQuery(query, BerichtLog.class).setMaxResults(1)
                        .setParameter("administratienummer", administratienummer).getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findLaatsteBerichtLogAnrs(final Date vanaf, final Date tot, final String gemeentecode) {
        final String query =
                "select distinct a_nummer, tijdstip_verwerking, gemeente_van_inschrijving FROM logging.berichtlog WHERE tijdstip_verwerking >= :vanaf and tijdstip_verwerking < :tot "
                        + " and (:gemeentecode = -1 or gemeente_van_inschrijving = :gemeentecode) "
                        + " ORDER BY tijdstip_verwerking  DESC";

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("vanaf", vanaf);
        parameters.put("tot", tot);
        parameters.put("gemeentecode", gemeentecode != null ? Integer.parseInt(gemeentecode) : -1);

        final SqlParameterSource namedParameters = new MapSqlParameterSource(parameters);
        final List<Long> anummers = jdbcTemplate.query(query, namedParameters, new BerichtLogAnrMapper());

        return anummers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtLog save(final BerichtLog berichtLog) {
        if (berichtLog == null) {
            throw new NullPointerException("berichtLog mag niet null zijn");
        }
        if (berichtLog.getId() == null) {
            em.persist(berichtLog);
            return berichtLog;
        } else {
            return em.merge(berichtLog);
        }
    }

    /**
     * Bericht mapper.
     */
    private static final class BerichtLogAnrMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Long(rs.getBigDecimal("a_nummer").longValue());
        }
    }

}
