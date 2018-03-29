/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;


import java.util.Collections;
import java.util.HashSet;
import org.junit.Assert;
import org.junit.Test;

public class InitVullingLogTest {

    @Test
    public void fingerPrints() {
        FingerPrint finger = new FingerPrint();
        Assert.assertNull(finger.getLog());

        InitVullingLog subject = new InitVullingLog();
        Assert.assertTrue(subject.getFingerPrints().isEmpty());

        subject.setFingerPrints(Collections.singleton(finger));
        Assert.assertEquals(1, subject.getFingerPrints().size());
        Assert.assertSame(subject, finger.getLog());

        finger.setLog(null);
        Assert.assertNull(finger.getLog());

        subject.setFingerPrints(new HashSet<>());
        Assert.assertTrue(subject.getFingerPrints().isEmpty());

        subject.addFingerPrint(finger);
        Assert.assertEquals(1, subject.getFingerPrints().size());
        Assert.assertSame(subject, finger.getLog());
    }

    @Test
    public void analyseRegels() {
        VerschilAnalyseRegel finger = new VerschilAnalyseRegel();
        Assert.assertNull(finger.getLog());

        InitVullingLog subject = new InitVullingLog();
        Assert.assertTrue(subject.getVerschilAnalyseRegels().isEmpty());

        subject.setdiffAnalyseRegels(Collections.singleton(finger));
        Assert.assertEquals(1, subject.getVerschilAnalyseRegels().size());
        Assert.assertSame(subject, finger.getLog());

        finger.setLog(null);
        Assert.assertNull(finger.getLog());

        subject.setdiffAnalyseRegels(new HashSet<>());
        Assert.assertTrue(subject.getVerschilAnalyseRegels().isEmpty());

        subject.addVerschilAnalyseRegels(Collections.singleton(finger));
        Assert.assertEquals(1, subject.getVerschilAnalyseRegels().size());
        Assert.assertSame(subject, finger.getLog());
    }

    @Test
    public void lo3Bericht() {
        InitVullingLog subject = new InitVullingLog();
        Assert.assertNull(subject.getLo3Bericht());
        subject.setLo3Bericht("lo3bericht");
        Assert.assertEquals("lo3bericht", subject.getLo3Bericht());
    }

    @Test
    public void berichtNaTerugConv() {
        InitVullingLog subject = new InitVullingLog();
        Assert.assertNull(subject.getBerichtNaTerugConversie());
        subject.setBerichtNaTerugConversie("lo3bericht");
        Assert.assertEquals("lo3bericht", subject.getBerichtNaTerugConversie());
    }
}
