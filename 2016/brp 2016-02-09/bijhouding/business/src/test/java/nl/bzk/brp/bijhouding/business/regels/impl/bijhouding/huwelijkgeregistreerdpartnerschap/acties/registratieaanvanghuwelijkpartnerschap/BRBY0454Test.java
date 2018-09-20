/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratieaanvanghuwelijkpartnerschap;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0454Test {

    private final BRBY0454 brby0454 = new BRBY0454();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0454, brby0454.getRegel());
    }

    @Test
    public void testAanvangGelijkAanRelatieAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = new HuwelijkBericht();
        huwelijkGeregistreerdPartnerschapBericht.setStandaard(new RelatieStandaardGroepBericht());
        huwelijkGeregistreerdPartnerschapBericht.getStandaard().setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20110101));

        final ActieBericht actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));

        final List<BerichtEntiteit> berichtEntiteits = brby0454.voerRegelUit(null, huwelijkGeregistreerdPartnerschapBericht, actie, null);

        Assert.assertEquals(0, berichtEntiteits.size());
    }

    @Test
    public void testAanvangNietGelijkAanRelatieAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijkGeregistreerdPartnerschapBericht = new HuwelijkBericht();
        huwelijkGeregistreerdPartnerschapBericht.setStandaard(new RelatieStandaardGroepBericht());
        huwelijkGeregistreerdPartnerschapBericht.getStandaard().setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20110101));

        final ActieBericht actie = new ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschapBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20020202));

        final List<BerichtEntiteit> berichtEntiteits = brby0454.voerRegelUit(null, huwelijkGeregistreerdPartnerschapBericht, actie, null);

        Assert.assertEquals(1, berichtEntiteits.size());
        Assert.assertEquals(huwelijkGeregistreerdPartnerschapBericht, berichtEntiteits.get(0));
    }

}
