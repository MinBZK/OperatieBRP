/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()),
                                       eq(Integer.class))).thenReturn(
                Arrays.asList(12, 34, 54, 98, 909));

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(greaterThanOrEqualTo(10)));

        verify(jdbcTemplate, times(2)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()), eq(
                Integer.class));
    }

    @Test
    public void executeReturnsMoreThanTheRequiredResults() throws Exception {
        // given
        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(200L);
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()),
                                       eq(Integer.class))).thenReturn(
                Arrays.asList(12, 34, 54, 98, 7987, 234));

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(10));

        verify(jdbcTemplate, times(2)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()), eq(
                Integer.class));
    }

    @Test
    public void executeReturnsLessThanTheRequiredResults() throws Exception {
        // given
        when(jdbcTemplate.queryForInt(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(1);
        when(jdbcTemplate.queryForLong(anyString(), argThat(new MapSqlParamterSourceMatcher()))).thenReturn(200L);
        when(jdbcTemplate.queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()),
                                       eq(Integer.class))).thenReturn(Arrays.asList(12, 34, 54, 98))
                .thenReturn(Collections.<Integer>emptyList());

        // when
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        collecteerBsnStap.execute(context);

        // then
        List<Integer> result = (List) context.get(ContextParameterNames.BSNLIJST);

        assertThat(result, notNullValue());
        assertThat(result.size(), is(4));

        verify(jdbcTemplate, times(3)).queryForList(anyString(), argThat(new MapSqlParamterSourceMatcher()), eq(
                Integer.class));
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


    class MapSqlParamterSourceMatcher extends ArgumentMatcher<MapSqlParameterSource> {
        @Override
        public boolean matches(final Object argument) {
            return argument instanceof MapSqlParameterSource;
        }
    }
}
