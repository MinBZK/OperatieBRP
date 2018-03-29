/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdresFunctieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import org.junit.Assert;
import org.junit.Test;

public class AdHocZoekPersonenOpAdresVerzoekBerichtTest extends AbstractSyncBerichtTestBasis {

    @Test
    public void test() throws Exception {
        final AdHocZoekPersonenOpAdresVerzoekBericht subject = new AdHocZoekPersonenOpAdresVerzoekBericht();
        subject.setPartijCode("123123");
        subject.setIdentificerendeGegevens("010110010120");
        subject.getGevraagdeRubrieken().add("076710");
        subject.setAdresfunctie(AdresFunctieType.A);
        subject.setIdentificatie(IdentificatieType.P);

        Assert.assertEquals("123123", subject.getPartijCode());
        Assert.assertEquals("010110010120", subject.getIdentificerendeGegevens());
        assertThat(subject.getGevraagdeRubrieken(), contains("076710"));
        Assert.assertEquals(AdresFunctieType.A, subject.getAdresFunctie());
        Assert.assertEquals(IdentificatieType.P, subject.getIdentificatie());
        Assert.assertEquals(SoortDienstType.ZOEK_PERSOON_OP_ADRESGEGEVENS, subject.getSoortDienst());

        controleerFormatParse(subject);
        controleerSerialization(subject);
    }

    @Test
    public void testVerzoekType() throws Exception {
        final AdHocZoekPersonenOpAdresVerzoekType type = new AdHocZoekPersonenOpAdresVerzoekType();
        type.setPartijCode("123123");
        type.setIdentificerendeGegevens("010110010120");
        type.getGevraagdeRubrieken().add("076710");
        type.setAdresfunctie(AdresFunctieType.A);
        type.setIdentificatie(IdentificatieType.P);
        final AdHocZoekPersonenOpAdresVerzoekBericht subject = new AdHocZoekPersonenOpAdresVerzoekBericht(type);

        Assert.assertEquals("123123", subject.getPartijCode());
        Assert.assertEquals("010110010120", subject.getIdentificerendeGegevens());
        assertThat(subject.getGevraagdeRubrieken(), contains("076710"));
        Assert.assertEquals(AdresFunctieType.A, subject.getAdresFunctie());
        Assert.assertEquals(IdentificatieType.P, subject.getIdentificatie());
        Assert.assertEquals(SoortDienstType.ZOEK_PERSOON_OP_ADRESGEGEVENS, subject.getSoortDienst());

        controleerFormatParse(subject);
        controleerSerialization(subject);
    }
}
