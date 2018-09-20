/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.serialisatie.JsonStringSerializer;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import uk.co.datumedge.hamcrest.json.SameJSONAs;


public class AdministratieveHandelingVerwerktOpdrachtTest {

    private static final String JSON_TEST_STRING =
        "{\"administratieveHandelingId\":123,"
            + "\"bijgehoudenPersoonIds\":[12,23,34],\"partijCode\":36101}";

    private static final Long                ADMHND_ID   = 123L;
    private static final PartijCodeAttribuut PARTIJ_CODE = new PartijCodeAttribuut(36101);

    private static final List<Integer> PERSOON_IDS = Arrays.asList(12, 23, 34);

    private JsonStringSerializer<AdministratieveHandelingVerwerktOpdracht> serialiseerder =
        new JsonStringSerializer<>(
            AdministratieveHandelingVerwerktOpdracht.class);

    @Test
    public void testJavaBean() {
        final AdministratieveHandelingVerwerktOpdracht opdracht =
            new AdministratieveHandelingVerwerktOpdracht(ADMHND_ID, PARTIJ_CODE, PERSOON_IDS);

        Assert.assertThat(opdracht.getAdministratieveHandelingId(), Matchers.equalTo(ADMHND_ID));
        Assert.assertThat(opdracht.getPartijCode().getWaarde(), Matchers.equalTo(PARTIJ_CODE.getWaarde()));
        Assert.assertThat(opdracht.getBijgehoudenPersoonIds(), Matchers.containsInAnyOrder(23, 34, 12));
    }

    @Test
    public void testSerialiseren() {
        final AdministratieveHandelingVerwerktOpdracht opdracht =
                new AdministratieveHandelingVerwerktOpdracht(ADMHND_ID, PARTIJ_CODE, PERSOON_IDS);

        MatcherAssert.assertThat(serialiseerder.serialiseerNaarString(opdracht), SameJSONAs.sameJSONAs(JSON_TEST_STRING)
                .allowingAnyArrayOrdering());
    }

    @Test
    public void testHeenEnWeer() {
        final AdministratieveHandelingVerwerktOpdracht opdracht =
                serialiseerder.deserialiseerVanuitString(JSON_TEST_STRING);

        MatcherAssert.assertThat(serialiseerder.serialiseerNaarString(opdracht), SameJSONAs.sameJSONAs(JSON_TEST_STRING)
                .allowingAnyArrayOrdering());
    }

}
