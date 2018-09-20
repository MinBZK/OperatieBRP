/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import org.junit.Assert;
import org.junit.Test;


public class BRBY0908Test {

    private final BRBY0908 brby0908 = new BRBY0908();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0908, brby0908.getRegel());
    }

    @Test
    public void testDatumGelijk() {
        final ActieBericht actie = new ActieRegistratieOverlijdenBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(20120101));
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setOverlijden(overlijden);

        final List<BerichtEntiteit> resultaat = brby0908.voerRegelUit(null, persoon, actie, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testOverlijdenNull() {
        final ActieBericht actie = new ActieRegistratieOverlijdenBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(null);
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setOverlijden(overlijden);

        List<BerichtEntiteit> resultaat = brby0908.voerRegelUit(null, persoon, actie, null);
        Assert.assertEquals(0, resultaat.size());

        persoon.setOverlijden(null);

        resultaat = brby0908.voerRegelUit(null, persoon, actie, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testDatumNietGelijk() {
        final ActieBericht actie = new ActieRegistratieOverlijdenBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(20120100));
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setOverlijden(overlijden);

        final List<BerichtEntiteit> resultaat = brby0908.voerRegelUit(null, persoon, actie, null);
        Assert.assertEquals(1, resultaat.size());
        Assert.assertEquals(persoon, resultaat.get(0));
    }

}
