/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.sql.SQLException;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.postgres.Plan;
import nl.bzk.algemeenbrp.util.common.postgres.QueryPlan;
import nl.bzk.brp.service.dalapi.QueryCancelledException;
import nl.bzk.brp.service.dalapi.QueryNietUitgevoerdException;
import nl.bzk.brp.service.dalapi.QueryTeDuurException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * ZoekPersoonDataOphalerServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonDataOphalerServiceImplTest {


    @InjectMocks
    private ZoekPersoonDataOphalerServiceImpl zoekPersoonDataOphalerService;

    @Mock
    private ZoekPersoonRepository zoekPersoonRepository;

    @Before
    public void setUp() throws SQLException, JsonProcessingException {
        Mockito.when(zoekPersoonRepository.isPostgres()).thenReturn(true);

        ReflectionTestUtils.setField(zoekPersoonDataOphalerService, "maxCostsQueryPlan", 100);
        final QueryPlan queryPlan = new QueryPlan();
        final Plan plan = new Plan();
        plan.setTotalCost(1);
        queryPlan.setPlan(plan);
        final ObjectMapper mapper = new ObjectMapper();
        final String queryPlanStr = mapper.writeValueAsString(new QueryPlan[]{queryPlan});
        Mockito.when(zoekPersoonRepository.bepaalQueryPlan(Mockito.any(SqlStamementZoekPersoon.class))).thenReturn(queryPlanStr);

    }

    @Test(expected = QueryTeDuurException.class)
    public void geenQueryPlan() throws SQLException, JsonProcessingException, QueryNietUitgevoerdException {
        final QueryPlan queryPlan = new QueryPlan();
        final Plan plan = new Plan();
        plan.setTotalCost(1000);
        queryPlan.setPlan(plan);
        final ObjectMapper mapper = new ObjectMapper();
        final String queryPlanStr = mapper.writeValueAsString(new QueryPlan[]{queryPlan});
        Mockito.when(zoekPersoonRepository.bepaalQueryPlan(Mockito.any(SqlStamementZoekPersoon.class))).thenReturn(queryPlanStr);

        zoekPersoonDataOphalerService.zoekPersonenActueel(Sets.newHashSet(), 10);
    }

    @Test(expected = QueryCancelledException.class)
    public void testSqlException() throws SQLException, JsonProcessingException, QueryNietUitgevoerdException {

        Mockito.when(zoekPersoonRepository.zoekPersonen(Mockito.any(SqlStamementZoekPersoon.class), Mockito.anyBoolean()))
                .thenThrow(new QueryCancelledException
                        (null));
        zoekPersoonDataOphalerService.zoekPersonenActueel(Sets.newHashSet(), 10);
    }

    @Test
    public void testGoedPad() throws SQLException, JsonProcessingException, QueryNietUitgevoerdException {
        Mockito.when(zoekPersoonRepository.zoekPersonen(Mockito.any(SqlStamementZoekPersoon.class), Mockito.anyBoolean()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L));
        final List<Long> ids = zoekPersoonDataOphalerService.zoekPersonenActueel(Sets.newHashSet(), 10);
        Assert.assertEquals(3, ids.size());
    }

    @Test
    public void testGoedPadGeenPostgres() throws SQLException, JsonProcessingException, QueryNietUitgevoerdException {
        Mockito.when(zoekPersoonRepository.isPostgres()).thenReturn(false);
        Mockito.when(zoekPersoonRepository.zoekPersonen(Mockito.any(SqlStamementZoekPersoon.class), Mockito.anyBoolean()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L));
        final List<Long> ids = zoekPersoonDataOphalerService.zoekPersonenActueel(Sets.newHashSet(), 10);
        Assert.assertEquals(3, ids.size());
    }

    @Test
    public void testGoedPadHistorischzoeken() throws SQLException, JsonProcessingException, QueryNietUitgevoerdException {
        Mockito.when(zoekPersoonRepository.zoekPersonen(Mockito.any(SqlStamementZoekPersoon.class), Mockito.anyBoolean()))
                .thenReturn(Lists.newArrayList(1L, 2L, 3L));
        final List<Long> ids = zoekPersoonDataOphalerService.zoekPersonenHistorisch(Sets.newHashSet(), 20140101, false, 10);
        Assert.assertEquals(3, ids.size());
    }
}
