/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.verzendingmodel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.common.collect.Lists;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import org.junit.Test;

public class SynchronisatieBerichtGegevensTest {

    private static final Long ADM_HND_ID = 1L;
    private static final Integer TOEGANG_LEVAUT_ID = 1;
    private static final List<Long> GELEVERDE_PERS_ID_LIJST = Lists.newArrayList(1L, 2L, 3L);
    private static final ZonedDateTime TS_REG = ZonedDateTime.of(2017, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC"));

    @Test
    public void test() {

        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(ADM_HND_ID);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(TOEGANG_LEVAUT_ID);

        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, TS_REG);
        GELEVERDE_PERS_ID_LIJST.forEach(archiveringOpdracht::addTeArchiverenPersoon);
        archiveringOpdracht.setData("<brp>mooie xml</brp>");

        final SynchronisatieBerichtGegevens berichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metStelsel(Stelsel.BRP)
                .metSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON)
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metArchiveringOpdracht(archiveringOpdracht)
                .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
                .metBrpEndpointURI("http://endpoint")
                .build();

        JsonStringSerializer serializer = new JsonStringSerializer();

        final String jsonString = serializer.serialiseerNaarString(berichtGegevens);
        assertThat(jsonString,
                is("{\"archiveringOpdracht\":{\"richting\":\"UITGAAND\",\"tijdstipRegistratie\":\"2017-01-01T12:00:00Z\",\"data\":\"<brp>mooie xml</brp>\","
                        + "\"teArchiverenPersonen\":[1,2,3]},\"brpEndpointURI\":\"http://endpoint\","
                        + "\"protocolleringOpdracht\":{\"administratieveHandelingId\":1,\"toegangLeveringsautorisatieId\":1},"
                        + "\"protocolleringsniveau\":\"GEEN_BEPERKINGEN\",\"soortDienst\":\"GEEF_DETAILS_PERSOON\",\"stelsel\":\"BRP\"}"));
    }
}
