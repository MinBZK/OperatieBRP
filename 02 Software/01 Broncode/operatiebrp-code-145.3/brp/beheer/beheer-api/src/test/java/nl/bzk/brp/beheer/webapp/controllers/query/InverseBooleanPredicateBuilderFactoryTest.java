/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link InverseBooleanPredicateBuilderFactory}.
 */
public class InverseBooleanPredicateBuilderFactoryTest {

    private final InverseBooleanPredicateBuilderFactory factory = new InverseBooleanPredicateBuilderFactory("test");

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testNull() throws Exception {
        final PredicateBuilder predicateBuilder = factory.getPredicateBuilder(null);

        assertThat(ReflectionTestUtils.getField(predicateBuilder, "naam"), is("test"));
        assertThat(ReflectionTestUtils.getField(predicateBuilder, "value"), is(true));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTrue() throws Exception {
        final OrPredicateBuilder predicateBuilder = (OrPredicateBuilder) factory.getPredicateBuilder(true);

        assertThat(predicateBuilder, is(instanceOf(OrPredicateBuilder.class)));
        final List<PredicateBuilder> predicateBuilders = (List<PredicateBuilder>) ReflectionTestUtils.getField(predicateBuilder, "predicateBuilders");
        assertThat(predicateBuilders.get(0), is(instanceOf(IsNullPredicateBuilder.class)));
        assertThat(ReflectionTestUtils.getField(predicateBuilders.get(1), "naam"), is("test"));
        assertThat(ReflectionTestUtils.getField(predicateBuilders.get(1), "value"), is(false));
    }

    @Test
    public void testFalse() throws Exception {
        final PredicateBuilder predicateBuilder = factory.getPredicateBuilder(false);

        assertThat(ReflectionTestUtils.getField(predicateBuilder, "naam"), is("test"));
        assertThat(ReflectionTestUtils.getField(predicateBuilder, "value"), is(true));
    }

}