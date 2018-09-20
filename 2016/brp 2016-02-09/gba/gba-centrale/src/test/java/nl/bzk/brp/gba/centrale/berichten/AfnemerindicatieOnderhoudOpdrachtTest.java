/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import org.junit.Assert;
import org.junit.Test;

public class AfnemerindicatieOnderhoudOpdrachtTest {
    @Test
    public void test() {
        final AfnemerindicatieOnderhoudOpdracht subject = new AfnemerindicatieOnderhoudOpdracht();
        Assert.assertEquals(null, subject.getEffectAfnemerindicatie());
        Assert.assertEquals(null, subject.getPersoonId());
        Assert.assertEquals(null, subject.getToegangLeveringsautorisatieId());
        Assert.assertEquals(null, subject.getReferentienummer());
        Assert.assertEquals(null, subject.getDienstId());

        subject.setEffectAfnemerindicatie(EffectAfnemerindicaties.PLAATSING);
        subject.setPersoonId(1);
        subject.setToegangLeveringsautorisatieId(2);
        subject.setReferentienummer("3");
        subject.setDienstId(4);

        Assert.assertEquals(EffectAfnemerindicaties.PLAATSING, subject.getEffectAfnemerindicatie());
        Assert.assertEquals(Integer.valueOf(1), subject.getPersoonId());
        Assert.assertEquals(Integer.valueOf(2), subject.getToegangLeveringsautorisatieId());
        Assert.assertEquals("3", subject.getReferentienummer());
        Assert.assertEquals(Integer.valueOf(4), subject.getDienstId());
    }
}
