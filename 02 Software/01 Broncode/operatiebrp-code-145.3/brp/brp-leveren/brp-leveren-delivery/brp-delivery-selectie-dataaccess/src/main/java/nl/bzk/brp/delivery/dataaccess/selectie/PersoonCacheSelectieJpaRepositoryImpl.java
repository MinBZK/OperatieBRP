/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.selectie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import nl.bzk.brp.service.selectie.lezer.MinMaxPersoonCacheDTO;
import nl.bzk.brp.service.selectie.lezer.PersoonCacheSelectieRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache} class en standaard implementatie van de {@link PersoonCacheRepository}
 * class voor selectie.
 */
@Bedrijfsregel(Regel.R1539)
@Repository
public final class PersoonCacheSelectieJpaRepositoryImpl implements PersoonCacheSelectieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.selectie")
    private EntityManager entityManager;

    @Inject
    private DataSource selectieDataSource;


    private PersoonCacheSelectieJpaRepositoryImpl() {
    }

    @Override
    public List<PersoonCache> haalPersoonCachesOp(final long minId, final long maxId) {
        final String query = "select persoonCache from PersoonCache persoonCache left join persoonCache.persoon persoon "
                + "where persoonCache.id >= :min and persoonCache.id < :max "
                + "and persoon.soortPersoonId = :soortPersoon "
                + "and (persoon.nadereBijhoudingsaardId is null "
                + "or persoon.nadereBijhoudingsaardId not in (:nadereBijhoudingsaardId1, :nadereBijhoudingsaardId2, "
                + ":nadereBijhoudingsaardId3))";
        return entityManager.createQuery(query, PersoonCache.class)
                .setParameter("min", minId)
                .setParameter("max", maxId)
                .setParameter("soortPersoon", SoortPersoon.INGESCHREVENE.getId())
                .setParameter("nadereBijhoudingsaardId1", NadereBijhoudingsaard.FOUT.getId())
                .setParameter("nadereBijhoudingsaardId2", NadereBijhoudingsaard.ONBEKEND.getId())
                .setParameter("nadereBijhoudingsaardId3", NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();

    }

    @Override
    public MinMaxPersoonCacheDTO selecteerMinMaxIdVoorSelectie() {
        final String sql
                = "select min(id) as laag, max(id) as hoog from kern.perscache";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(selectieDataSource);
        final Map<String, Object> parameters = new HashMap<>();
        return jdbcTemplate.queryForObject(sql, parameters, (resultSet, i) -> {
            final MinMaxPersoonCacheDTO minMaxPersoonCacheDTO = new MinMaxPersoonCacheDTO();
            minMaxPersoonCacheDTO.setMinId(resultSet.getInt("laag"));
            minMaxPersoonCacheDTO.setMaxId(resultSet.getInt("hoog"));
            return minMaxPersoonCacheDTO;
        });
    }

}
