/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import org.junit.Assert;
import org.junit.Test;

public class ZoekPersoonServiceUtilTest {

    @Test
    public void toLong() {
        Assert.assertNull(ZoekPersoonServiceUtil.toLong(null));
        Assert.assertNull(ZoekPersoonServiceUtil.toLong(""));
        Assert.assertEquals(Long.valueOf(2), ZoekPersoonServiceUtil.toLong("2"));
    }

    @Test
    public void toInteger() {
        Assert.assertNull(ZoekPersoonServiceUtil.toInteger(null));
        Assert.assertNull(ZoekPersoonServiceUtil.toInteger(""));
        Assert.assertEquals(Integer.valueOf(2), ZoekPersoonServiceUtil.toInteger("2"));
    }

    @Test
    public void maakZoekPersoonAntwoordGeen() {
        final SyncBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        verzoek.setMessageId("MSG-1");

        final List<GevondenPersoon> gevondenPersonen = new ArrayList<>();

        final ZoekPersoonAntwoordBericht antwoord = ZoekPersoonServiceUtil.maakZoekPersoonAntwoord(verzoek, gevondenPersonen);
        Assert.assertNotNull(antwoord);
        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals("MSG-1", antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.GEEN, antwoord.getResultaat());
        Assert.assertNull(antwoord.getPersoonId());
        Assert.assertNull(antwoord.getAnummer());
        Assert.assertNull(antwoord.getGemeente());
    }

    @Test
    public void maakZoekPersoonAntwoordGevonden() {
        final SyncBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        verzoek.setMessageId("MSG-1");

        final List<GevondenPersoon> gevondenPersonen = new ArrayList<>();
        gevondenPersonen.add(new GevondenPersoon(1, 1234567890L, "0003"));

        final ZoekPersoonAntwoordBericht antwoord = ZoekPersoonServiceUtil.maakZoekPersoonAntwoord(verzoek, gevondenPersonen);
        Assert.assertNotNull(antwoord);
        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals("MSG-1", antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.GEVONDEN, antwoord.getResultaat());
        Assert.assertEquals(Integer.valueOf(1), antwoord.getPersoonId());
        Assert.assertEquals("1234567890", antwoord.getAnummer());
        Assert.assertEquals("0003", antwoord.getGemeente());
    }

    @Test
    public void maakZoekPersoonAntwoordMeerdere() {
        final SyncBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
        verzoek.setMessageId("MSG-1");

        final List<GevondenPersoon> gevondenPersonen = new ArrayList<>();
        gevondenPersonen.add(new GevondenPersoon(1, 2L, "3"));
        gevondenPersonen.add(new GevondenPersoon(4, 5L, "6"));

        final ZoekPersoonAntwoordBericht antwoord = ZoekPersoonServiceUtil.maakZoekPersoonAntwoord(verzoek, gevondenPersonen);
        Assert.assertNotNull(antwoord);
        Assert.assertNotNull(antwoord.getMessageId());
        Assert.assertEquals("MSG-1", antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.OK, antwoord.getStatus());
        Assert.assertEquals(ZoekPersoonResultaatType.MEERDERE, antwoord.getResultaat());
        Assert.assertNull(antwoord.getPersoonId());
        Assert.assertNull(antwoord.getAnummer());
        Assert.assertNull(antwoord.getGemeente());
    }
}
