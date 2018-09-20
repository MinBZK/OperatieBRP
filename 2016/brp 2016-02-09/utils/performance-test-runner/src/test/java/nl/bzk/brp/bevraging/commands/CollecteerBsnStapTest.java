/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Tests for {@link CollecteerBsnStap}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CollecteerBsnStapTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
    @InjectMocks
    private CollecteerBsnStap collecteerBsnStap = new CollecteerBsnStap();

    @Test
    public void executeReturnsTheRequiredResults() throws Exception {
        // given
        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(200L);
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher())))
                .thenReturn(Arrays.asList(map(12, 10001), map(34, 10002), map(54, 10003), map(98, 10004), map(234, 10006)))
                .thenReturn(Arrays.asList(map(807, 10007), map(789, 10008), map(675, 10009), map(234, 10010), map(543, 10011)));

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(greaterThanOrEqualTo(10)));

        verify(jdbcTemplate, times(2)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()));
    }

    @Test
    public void executeReturnsMoreThanTheRequiredResults() throws Exception {
        // given
        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(200L);
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher())))
                .thenReturn(Arrays.asList(map(12, 10001), map(34, 10002), map(54, 10003), map(98, 10004), map(7987, 10005), map(234, 10006)))
                .thenReturn(Arrays.asList(map(807, 10007), map(789, 10008), map(675, 10009), map(234, 10010), map(543, 10011)));

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(10));

        verify(jdbcTemplate, times(2)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()));
    }

    @Test
    public void executeReturnsLessThanTheRequiredResults() throws Exception {
        // given
        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(200L);
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher())))
                .thenReturn(Arrays.asList(map(12, 10000001), map(34, 10000002), map(54, 10000003), map(98, 10000004)))
                .thenReturn(Collections.<Map<String, Object>>emptyList());

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(4));

        verify(jdbcTemplate, times(3)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()));
    }

    @Test
    public void executeAgainstAnEmptyDatabase() throws Exception {
        // given
        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(0L);

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        boolean result = collecteerBsnStap.execute(context);

        assertTrue(result);
    }

    @Test
    public void useBlockSize() throws Exception {
        // given
        collecteerBsnStap.setBlockSize(3);

        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(200L);
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher())))
                .thenReturn(Arrays.asList(map(12, 10001), map(34, 10002), map(54, 10003)))
                .thenReturn(Arrays.asList(map(98, 10004), map(234, 10006), map(807, 10007)))
                .thenReturn(Arrays.asList(map(789, 10008), map(675, 10009), map(234, 10010)))
                .thenReturn(Arrays.asList(map(543, 10011), map(321, 10012), map(678, 10013)));

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(10));

        verify(jdbcTemplate, times(4)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()));
    }

    private Map<String, Object> map(Integer id, Integer bsn) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        result.put("bsn", bsn);

        return result;
    }


    class MapSqlParamterSourceMatcher extends ArgumentMatcher<MapSqlParameterSource> {
        @Override
        public boolean matches(final Object argument) {
            return argument instanceof MapSqlParameterSource;
        }
    }
}
