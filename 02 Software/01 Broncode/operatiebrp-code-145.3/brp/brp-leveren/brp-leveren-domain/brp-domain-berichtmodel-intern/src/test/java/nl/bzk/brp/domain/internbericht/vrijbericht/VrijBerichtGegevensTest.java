/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.vrijbericht;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import org.junit.Test;

public class VrijBerichtGegevensTest {

    private static final ZonedDateTime TS_REG = ZonedDateTime.of(2017,1,1, 12, 0, 0, 0, ZoneId.of("UTC"));

    @Test
    public void test() {
        final ArchiveringOpdracht
                archiveringOpdracht =
                new ArchiveringOpdracht(Richting.UITGAAND, TS_REG);
        archiveringOpdracht.setData("<brp>mooie xml</brp>");

        final VrijBerichtGegevens vrijBerichtGegevens = VrijBerichtGegevens.builder()
                .metArchiveringOpdracht(archiveringOpdracht)
                .metBrpEndpointUrl("http://endpoint")
                .metPartij(TestPartijBuilder.maakBuilder().metId(1).metCode("000000").build())
                .metStelsel(Stelsel.BRP)
                .build();

        JsonStringSerializer serializer = new JsonStringSerializer();
        final String jsonString = serializer.serialiseerNaarString(vrijBerichtGegevens);

        assertThat(jsonString,
                is("{\"archiveringOpdracht\":{\"richting\":\"UITGAAND\",\"tijdstipRegistratie\":\"2017-01-01T12:00:00Z\","
                        + "\"data\":\"<brp>mooie xml</brp>\"},\"brpEndpointURI\":\"http://endpoint\",\"partij\":{},\"stelsel\":\"BRP\"}"));
    }
}
