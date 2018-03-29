/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie;

import java.util.Date;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

public class BijhoudingsplanNotificatieBerichtTest {

    private static final Long ADM_HND_ID = 1L;
    private static final String REF_NR = "referentieNummer";
    private static final String CROSS_REF_NR = "crossReferentieNummer";
    private static final Date VANDAAG = DatumUtil.nu();
    private static final Short ONTV_PARTIJ_ID = (short) 1;
    private static final String ONTV_PARTIJ_CODE = "000001";
    private static final String ZENDENDE_PARTIJ_CODE = "000002";
    private static final String ONTV_SYSTEEM = "ontvSysteem";
    private static final String ZENDENDE_SYSTEEM = "zendendeSysteem";
    private static final String VERWERK_BIJH_PLAN_BERICHT = "bericht";

    @Test
    public void test() {
        final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht = maakBijhoudingsplanNotificatieBericht();
        Assert.assertEquals(ADM_HND_ID, bijhoudingsplanNotificatieBericht.getAdministratieveHandelingId());
        Assert.assertEquals(REF_NR, bijhoudingsplanNotificatieBericht.getReferentieNummer());
        Assert.assertEquals(CROSS_REF_NR, bijhoudingsplanNotificatieBericht.getCrossReferentieNummer());
        Assert.assertEquals(ONTV_PARTIJ_ID, bijhoudingsplanNotificatieBericht.getOntvangendePartijId());
        Assert.assertEquals(ONTV_PARTIJ_CODE, bijhoudingsplanNotificatieBericht.getOntvangendePartijCode());
        Assert.assertEquals(ZENDENDE_PARTIJ_CODE, bijhoudingsplanNotificatieBericht.getZendendePartijCode());
        Assert.assertEquals(ONTV_SYSTEEM, bijhoudingsplanNotificatieBericht.getOntvangendeSysteem());
        Assert.assertEquals(ZENDENDE_SYSTEEM, bijhoudingsplanNotificatieBericht.getZendendeSysteem());
        Assert.assertEquals(VANDAAG.getTime(), bijhoudingsplanNotificatieBericht.getTijdstipVerzending().longValue());
        Assert.assertEquals(VERWERK_BIJH_PLAN_BERICHT, bijhoudingsplanNotificatieBericht.getVerwerkBijhoudingsplanBericht());
    }

    private BijhoudingsplanNotificatieBericht maakBijhoudingsplanNotificatieBericht() {
        final BijhoudingsplanNotificatieBericht bijhoudingsplanNotificatieBericht =
                new BijhoudingsplanNotificatieBericht()
                        .setAdministratieveHandelingId(ADM_HND_ID)
                        .setReferentieNummer(REF_NR)
                        .setCrossReferentieNummer(CROSS_REF_NR)
                        .setOntvangendePartijId(ONTV_PARTIJ_ID)
                        .setOntvangendePartijCode(ONTV_PARTIJ_CODE)
                        .setZendendePartijCode(ZENDENDE_PARTIJ_CODE)
                        .setOntvangendeSysteem(ONTV_SYSTEEM)
                        .setZendendeSysteem(ZENDENDE_SYSTEEM)
                        .setTijdstipVerzending(VANDAAG)
                        .setVerwerkBijhoudingsplanBericht(VERWERK_BIJH_PLAN_BERICHT);
        return bijhoudingsplanNotificatieBericht;
    }
}
