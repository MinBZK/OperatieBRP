/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;

import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0438Test {

    private BRBY0438 brby0438;

    @Before
    public void init() {
        brby0438 = new BRBY0438();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0438, brby0438.getRegel());
    }

    @Test
    public void testDatumHuwelijkLigtInVerleden() {
        final HuwelijkGeregistreerdPartnerschapBericht
                huwelijk = new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().bouwHuwelijkRelatie().setDatumAanvang(20080404).getRelatie();
        final List<BerichtEntiteit> resultaat = brby0438.voerRegelUit(null, huwelijk, null, null);
        Assert.assertTrue(resultaat.isEmpty());
    }

    @Test
    public void testDatumHuwelijkIsVandaag() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().
                bouwHuwelijkRelatie()
                .setDatumAanvang(DatumAttribuut.vandaag().getWaarde()).getRelatie();
        final List<BerichtEntiteit> resultaat = brby0438.voerRegelUit(null, huwelijk, null, null);
        Assert.assertTrue(resultaat.isEmpty());
    }

    @Test
    public void testDatumHuwelijkLigtInToekomst() {
        final DatumAttribuut vandaag = DatumAttribuut.vandaag();
        int vandaagInt = vandaag.getWaarde();
        vandaagInt++;

        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = new RelatieBuilder<HuwelijkGeregistreerdPartnerschapBericht>().
                bouwHuwelijkRelatie().setDatumAanvang(vandaagInt).getRelatie();

        final List<BerichtEntiteit> resultaat = brby0438.voerRegelUit(null, huwelijk, null, null);
        Assert.assertEquals(resultaat.size(), 1);
        Assert.assertTrue(resultaat.get(0) instanceof HuwelijkGeregistreerdPartnerschapBericht);
    }
}
