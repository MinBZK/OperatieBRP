/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import java.util.UUID;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtAntwoord;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtQueue;
import nl.bzk.migratiebrp.synchronisatie.runtime.AbstractIT;
import nl.bzk.migratiebrp.synchronisatie.runtime.Main;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor vrijbericht.
 */
public class VrijBerichtServiceIT extends AbstractIT {

    public VrijBerichtServiceIT() {
        super(Main.Modus.SYNCHRONISATIE);
    }

    @Test
    public void testNaarBrp() throws Exception {
        String iscVrijBericht = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<VrijBerichtVerzoek xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\">"
                + "<verzendendePartij>062601</verzendendePartij>"
                + "<ontvangendePartij>059901</ontvangendePartij>"
                + "<bericht>BERICHT</bericht>"
                + "<referentienummer>REF-123</referentienummer>"
                + "</VrijBerichtVerzoek>";
        putMessage(SYNC_VERZOEK_QUEUE, iscVrijBericht, "12345");
        final String brpVrijBericht = getContent(expectBrpMessage(VrijBerichtQueue.VERZOEK.getQueueNaam()));
        Assert.assertEquals("{\"bericht\":\"BERICHT\",\"ontvangendePartijCode\":\"059901\",\"referentienummer\":\"REF-123\","
                + "\"verzendendePartijCode\":\"062601\"}", brpVrijBericht);
    }

    @Test
    public void testVanBrp() throws Exception {
        final VrijBerichtAntwoord vrijBerichtAntwoord = new VrijBerichtAntwoord();
        vrijBerichtAntwoord.setGeslaagd(true);
        putBrpMessage(VrijBerichtQueue.ANTWOORD.getQueueNaam(), JsonMapper.writer().writeValueAsString(vrijBerichtAntwoord), UUID.randomUUID().toString());

        final String antwoord = getContent(expectMessage(SYNC_ANTWOORD_QUEUE));
        System.out.println(antwoord);
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><VrijBerichtAntwoord xmlns=\"http://www.bzk"
                + ".nl/migratiebrp/SYNC/0001\"><status>Ok</status></VrijBerichtAntwoord>", antwoord);
    }


}
