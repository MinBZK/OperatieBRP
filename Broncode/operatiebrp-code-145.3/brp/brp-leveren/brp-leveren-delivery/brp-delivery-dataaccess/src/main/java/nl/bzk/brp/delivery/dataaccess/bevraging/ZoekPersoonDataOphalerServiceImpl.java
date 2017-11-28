/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlan;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlanParser;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.service.dalapi.AlgemeneQueryFout;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import nl.bzk.brp.service.dalapi.QueryNietUitgevoerdException;
import nl.bzk.brp.service.dalapi.QueryTeDuurException;
import nl.bzk.brp.service.dalapi.ZoekPersoonDataOphalerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * ZoekPersoonDataOphalerServiceImpl.
 */
@Service
@Bedrijfsregel(Regel.R2284)
final class ZoekPersoonDataOphalerServiceImpl implements ZoekPersoonDataOphalerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ZoekPersoonRepository zoekPersoonRepository;

    @Value("${brp.bevraging.zoekpersoon.max.costs.queryplan:250}")
    private int maxCostsQueryPlan;

    private ZoekPersoonDataOphalerServiceImpl() {
    }


    @Override
    public List<Long> zoekPersonenActueel(final Set<ZoekCriterium> zoekCriteria, final int maxResults)
            throws QueryNietUitgevoerdException {
        //zoek criteria transformeren voor null vragen
        return zoekPersonen(zoekCriteria, maxResults, false, false, null);
    }

    @Override
    public List<Long> zoekPersonenHistorisch(final Set<ZoekCriterium> zoekCriteria, final Integer materieelPeilmoment, final boolean peilperiode,
                                             final int maxResults)
            throws QueryNietUitgevoerdException {
        return zoekPersonen(zoekCriteria, maxResults, true, peilperiode, materieelPeilmoment);
    }

    private List<Long> zoekPersonen(final Set<ZoekCriterium> zoekCriteria, final int maxResults, final boolean historisch, final boolean peilperiode,
                                    final Integer materieelPeilmoment) throws QueryNietUitgevoerdException {
        SqlStamementZoekPersoon sql = null;
        try {
            final boolean postgres = zoekPersoonRepository.isPostgres();
            sql = new SqlBepaler(zoekCriteria, maxResults, historisch, materieelPeilmoment, peilperiode).maakSql();
            LOGGER.info(String.format("Start zoek persoon met query met sql [%s]", sql));
            if (postgres) {
                final String queryPlanJson = zoekPersoonRepository.bepaalQueryPlan(sql);
                final QueryPlan queryPlan = new QueryPlanParser(queryPlanJson).parse();
                final double cost = queryPlan.getPlan().getTotalCost();
                if (cost > maxCostsQueryPlan) {
                    final String message = String.format("Query met sql %s afgewezen op basis van query plan", sql);
                    LOGGER.debug(message);
                    throw new QueryTeDuurException(message);
                }
            }

            final List<Long> gevondenPersonen = zoekPersoonRepository.zoekPersonen(sql, postgres);
            LOGGER.info(String.format("Query leverde %d personen op", gevondenPersonen.size()));
            return gevondenPersonen;
        } catch (QueryCancelledException e) {
            LOGGER.info(QueryCancelledException.STANDAARD_MELDING + ", met sql: " + sql, e);
            throw e;
        } catch (IOException | SQLException e) {
            throw new AlgemeneQueryFout(e);
        }
    }
}
