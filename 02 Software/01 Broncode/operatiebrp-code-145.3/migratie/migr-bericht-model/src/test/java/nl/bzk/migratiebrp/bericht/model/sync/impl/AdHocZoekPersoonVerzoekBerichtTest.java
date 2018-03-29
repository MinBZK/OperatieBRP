/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersoonVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import org.junit.Assert;
import org.junit.Test;

public class AdHocZoekPersoonVerzoekBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void test() throws Exception {
        final AdHocZoekPersoonVerzoekBericht subject = new AdHocZoekPersoonVerzoekBericht();
        subject.setPartijCode("123123");
        subject.setPersoonIdentificerendeGegevens("010110010120");
        subject.getGevraagdeRubrieken().add("076710");
        subject.setSoortDienst(SoortDienstType.ZOEK_PERSOON);

        Assert.assertEquals("123123", subject.getPartijCode());
        Assert.assertEquals("010110010120", subject.getPersoonIdentificerendeGegevens());
        assertThat(subject.getGevraagdeRubrieken(), contains("076710"));
        Assert.assertEquals(SoortDienstType.ZOEK_PERSOON, subject.getSoortDienst());

        controleerFormatParse(subject);
        controleerSerialization(subject);
    }

    @Test
    public void testVerzoekType() throws Exception {
        final AdHocZoekPersoonVerzoekType type = new AdHocZoekPersoonVerzoekType();
        type.setPartijCode("123123");
        type.setPersoonIdentificerendeGegevens("010110010120");
        type.getGevraagdeRubrieken().add("076710");
        type.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        final AdHocZoekPersoonVerzoekBericht subject = new AdHocZoekPersoonVerzoekBericht(type);

        Assert.assertEquals("123123", subject.getPartijCode());
        Assert.assertEquals("010110010120", subject.getPersoonIdentificerendeGegevens());
        assertThat(subject.getGevraagdeRubrieken(), contains("076710"));
        Assert.assertEquals(SoortDienstType.ZOEK_PERSOON, subject.getSoortDienst());

        controleerFormatParse(subject);
        controleerSerialization(subject);
    }
}
