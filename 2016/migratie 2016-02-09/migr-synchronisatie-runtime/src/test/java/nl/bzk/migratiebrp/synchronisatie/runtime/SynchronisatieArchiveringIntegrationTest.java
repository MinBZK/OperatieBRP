/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.util.UUID;
import javax.jms.JMSException;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class SynchronisatieArchiveringIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    public SynchronisatieArchiveringIntegrationTest() {
        super("synchronisatie");
    }

    @Test
    public void test() throws InterruptedException, JMSException {
        final String content = maakVerzoek();
        LOG.info("Versturen: {}", content);

        putMessage(ARCHIVERING_QUEUE, content, UUID.randomUUID().toString());

        LOG.info("Wachten op bericht op BRP queue ...");
        final String notificatie = getContent(expectBrpMessage("GbaArchief"));
        LOG.info("BRP Verzoek ontvangen: {}", notificatie);
        Assert.assertEquals("{\"soortBericht\":\"Lg01\",\"richting\":\"INGAAND\","
                            + "\"zendendePartijCode\":189501,\"zendendeSysteem\":\"GBANetwerk\","
                            + "\"ontvangendePartijCode\":null,\"ontvangendeSysteem\":\"Migratievoorzieningen\","
                            + "\"referentienummer\":\"REF-000002\",\"crossReferentienummer\":\"REF-000001\","
                            + "\"tijdstipVerzending\":null,\"tijdstipOntvangst\":\"20010101121400000\","
                            + "\"data\":\"BerichtInhoud\"}", notificatie);
    }

    private String maakVerzoek() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>").append(System.lineSeparator());
        builder.append("<ns1:ArchiveerInBrpVerzoek xmlns:ns1=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">").append(System.lineSeparator());
        builder.append("<ns1:soortBericht>Lg01</ns1:soortBericht>").append(System.lineSeparator());
        builder.append("<ns1:richting>INGAAND</ns1:richting>").append(System.lineSeparator());
        builder.append("<ns1:zender><ns1:gemeente>1895</ns1:gemeente>").append(System.lineSeparator());
        builder.append("<ns1:systeem>GBANetwerk</ns1:systeem></ns1:zender>").append(System.lineSeparator());
        builder.append("<ns1:ontvanger><ns1:systeem>Migratievoorzieningen</ns1:systeem></ns1:ontvanger>").append(System.lineSeparator());
        builder.append("<ns1:referentienummer>REF-000002</ns1:referentienummer>").append(System.lineSeparator());
        builder.append("<ns1:crossReferentienummer>REF-000001</ns1:crossReferentienummer>").append(System.lineSeparator());
        // builder.append("<ns1:tijdstipVerzending></ns1:tijdstipVerzending>").append(System.lineSeparator());
        builder.append("<ns1:tijdstipOntvangst>2001-01-01T12:14:00+00:00</ns1:tijdstipOntvangst>").append(System.lineSeparator());
        builder.append("<ns1:data>BerichtInhoud</ns1:data>").append(System.lineSeparator());
        builder.append("</ns1:ArchiveerInBrpVerzoek>");

        return builder.toString();
    }

}
