/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRAL0208Test extends AbstractLocatieRegelTest {

    private BRAL0208 bral0208;

    @Before
    public void init() {
        bral0208 = new BRAL0208();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL0208, bral0208.getRegel());
    }

    @Test
    public void testRegel0208() {
        PersoonBericht persoon = getPersoonGeboorte(getNederland(), null, null, getBuitenlandsePlaats(), null, null);
        List<BerichtEntiteit> berichtEntiteiten = bral0208.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());

        persoon = getPersoonGeboorte(getBuitenland(), null, null, getBuitenlandsePlaats(), null, null);
        berichtEntiteiten = bral0208.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testRegel0208OverlijdenGroep() {
        final PersoonBericht persoon = getPersoonGeboorte(getNederland(), null, null, null, null, null);
        persoon.setOverlijden(new PersoonOverlijdenGroepBericht());
        persoon.getOverlijden().setBuitenlandsePlaatsOverlijden(getBuitenlandsePlaats());
        persoon.getOverlijden().setLandGebiedOverlijden(new LandGebiedAttribuut(getNederland()));

        final List<BerichtEntiteit> berichtEntiteiten = bral0208.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    @Test
    public void testRegel0208MetHuwelijkAanvang() {
        final PersoonBericht persoon = getPersoonGeboorte(getNederland(), null, null, null, null, null);
        final GeregistreerdPartnerschapBericht bericht =
                getGeregistreerdPartnerschap(new DatumEvtDeelsOnbekendAttribuut(20001010), getNederland(), getBuitenlandsePlaats(), null, null, null, persoon);

        final List<BerichtEntiteit> berichtEntiteiten = bral0208.voerRegelUit(null, bericht, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    @Test
    public void testRegel0208MetHuwelijkEinde() {
        final PersoonBericht persoon = getPersoonGeboorte(getNederland(), null, null, null, null, null);
        final GeregistreerdPartnerschapBericht bericht =
                getGeregistreerdPartnerschap(null, null, null, getNederland(), getBuitenlandsePlaats(), new DatumEvtDeelsOnbekendAttribuut(20101010),
                        persoon);


        final List<BerichtEntiteit> berichtEntiteiten = bral0208.voerRegelUit(null, bericht, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

}
