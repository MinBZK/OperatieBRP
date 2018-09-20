/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.levering.business.filters.LeverenPersoonFilter;
import nl.bzk.brp.levering.business.filters.PersoonHeeftAfnemerIndicatieFilter;
import nl.bzk.brp.levering.business.filters.PopulatieBepalingFilter;
import nl.bzk.brp.levering.business.filters.SleutelrubriekGewijzigdFilter;
import nl.bzk.brp.levering.business.filters.VerstrekkingsbeperkingFilter;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class FilterNietTeLeverenPersonenServiceIntegratieTest extends AbstractIntegratieTest {

    @Inject
    private FilterNietTeLeverenPersonenService nietTeLeverenPersonenService;

    @Test
    public final void filtersHebbenCorrecteVolgorde() {
        final List<LeverenPersoonFilter> filters =
                (List) ReflectionTestUtils.getField(nietTeLeverenPersonenService, "filters");

        // assert
        assertThat(filters.size(), is(4));
        assertThat(filters.get(0), instanceOf(PopulatieBepalingFilter.class));
        assertThat(filters.get(1), instanceOf(PersoonHeeftAfnemerIndicatieFilter.class));
        assertThat(filters.get(2), instanceOf(SleutelrubriekGewijzigdFilter.class));
        assertThat(filters.get(3), instanceOf(VerstrekkingsbeperkingFilter.class));
    }
}
