/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.selectie;

import java.util.Arrays;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import org.junit.Test;

/** Unit test class voor de {@link RelatieSelectieFilter} class. */
public class RelatieSelectieFilterTest {

    @Test
    public void testGettersEnSetters() {
        RelatieSelectieFilter relatieSelectieFilter = new RelatieSelectieFilter();

        // Test of alles null is voor het zetten
        Assert.assertNull(relatieSelectieFilter.getPeilDatum());
        Assert.assertNull(relatieSelectieFilter.getSoortBetrokkenheden());
        Assert.assertNull(relatieSelectieFilter.getSoortRelaties());
        Assert.assertNull(relatieSelectieFilter.getSoortRollen());
        Assert.assertNull(relatieSelectieFilter.getUitGeslachtsaanduidingen());
        Assert.assertNull(relatieSelectieFilter.getUitPersoonTypen());

        // Voer het zetten van de de velden uit
        relatieSelectieFilter.setPeilDatum(new Datum(20120312));
        relatieSelectieFilter.setSoortBetrokkenheden(Arrays.asList(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.KIND));
        relatieSelectieFilter.setSoortRelaties(Arrays.asList(SoortRelatie.HUWELIJK,
            SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
        relatieSelectieFilter.setSoortRollen(Arrays.asList(SoortBetrokkenheid.OUDER, SoortBetrokkenheid.PARTNER));
        relatieSelectieFilter.setUitGeslachtsaanduidingen(Arrays.asList(Geslachtsaanduiding.MAN,
            Geslachtsaanduiding.VROUW));
        relatieSelectieFilter.setUitPersoonTypen(Arrays.asList(SoortPersoon.INGESCHREVENE,
            SoortPersoon.NIET_INGESCHREVENE));

        // Test of alles goed gezet is
        Assert.assertEquals(20120312, relatieSelectieFilter.getPeilDatum().getWaarde().intValue());
        Assert.assertEquals(2, relatieSelectieFilter.getSoortBetrokkenheden().size());
        Assert.assertEquals(SoortBetrokkenheid.KIND, relatieSelectieFilter.getSoortBetrokkenheden().get(1));
        Assert.assertEquals(2, relatieSelectieFilter.getSoortRelaties().size());
        Assert.assertEquals(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, relatieSelectieFilter.getSoortRelaties().get(1));
        Assert.assertEquals(2, relatieSelectieFilter.getSoortRollen().size());
        Assert.assertEquals(SoortBetrokkenheid.PARTNER, relatieSelectieFilter.getSoortRollen().get(1));
        Assert.assertEquals(2, relatieSelectieFilter.getUitGeslachtsaanduidingen().size());
        Assert.assertEquals(Geslachtsaanduiding.VROUW, relatieSelectieFilter.getUitGeslachtsaanduidingen().get(1));
        Assert.assertEquals(2, relatieSelectieFilter.getUitPersoonTypen().size());
        Assert.assertEquals(SoortPersoon.NIET_INGESCHREVENE, relatieSelectieFilter.getUitPersoonTypen().get(1));
    }
}
