/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0032Test {

    private BRBY0032 brby0032;

    @Before
    public void init() {
        brby0032 = new BRBY0032();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0032, brby0032.getRegel());
    }

    @Test
    public void testDatumEindeVoorAanvang() {
        final ActieBericht actie = maakActie(new DatumEvtDeelsOnbekendAttribuut(20120325), new DatumEvtDeelsOnbekendAttribuut(20120324));

        final List<BerichtEntiteit> resultaat = brby0032.voerRegelUit(null, null, actie, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof ActieBericht);
    }

    @Test
    public void testDatumEindeGelijkAanAanvang() {
        final ActieBericht actie = maakActie(new DatumEvtDeelsOnbekendAttribuut(20120324), new DatumEvtDeelsOnbekendAttribuut(20120324));

        final List<BerichtEntiteit> resultaat = brby0032.voerRegelUit(null, null, actie, null);

        Assert.assertEquals(1, resultaat.size());
        Assert.assertTrue(resultaat.get(0) instanceof ActieBericht);
    }

    @Test
    public void testDatumEindeNaAanvang() {
        final ActieBericht actie = maakActie(new DatumEvtDeelsOnbekendAttribuut(20120324), new DatumEvtDeelsOnbekendAttribuut(20120325));

        final List<BerichtEntiteit> resultaat = brby0032.voerRegelUit(null, null, actie, null);

        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testDatumEindeLeeg() {
        final ActieBericht actie = maakActie(new DatumEvtDeelsOnbekendAttribuut(20120324), null);

        final List<BerichtEntiteit> resultaat = brby0032.voerRegelUit(null, null, actie, null);

        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Maakt een actie met datumAanvang en datumEinde.
     *
     * @param datumAanvang datum aanvang
     * @param datumEinde datum einde
     * @return actie bericht
     */
    private ActieBericht maakActie(final DatumEvtDeelsOnbekendAttribuut datumAanvang,
                                   final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        final ActieBericht actie = new ActieCorrectieAdresBericht();

        actie.setDatumAanvangGeldigheid(datumAanvang);
        actie.setDatumEindeGeldigheid(datumEinde);

        return actie;
    }
}
