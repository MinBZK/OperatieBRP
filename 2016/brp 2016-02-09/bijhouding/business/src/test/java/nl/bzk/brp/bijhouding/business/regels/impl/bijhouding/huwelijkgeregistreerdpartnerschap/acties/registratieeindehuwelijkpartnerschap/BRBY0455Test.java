/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BRBY0455Test {

    @Test
    public void testGetRegel() throws Exception {
        Assert.assertEquals(Regel.BRBY0455, new BRBY0455().getRegel());
    }

    @Test
    public void testDatumEindeHuwelijkIsGelijkAanDatumAanvangActie() {
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0455().voerRegelUit(null, maakHuwelijkBericht(20130101), maakActie(20130101), null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testDatumEindeHuwelijkIsNietGelijkAanDatumAanvangActie() {
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0455().voerRegelUit(null, maakHuwelijkBericht(20130201), maakActie(20130101), null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
    }

    @Test
    public void testNieuweSituatieZonderStandaardGroep() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = new HuwelijkBericht();

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0455().voerRegelUit(null, huwelijk, maakActie(20130101), null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testNieuweSituatieZonderDatumEindeStandaardGroep() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = new HuwelijkBericht();
        huwelijk.setStandaard(new RelatieStandaardGroepBericht());

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0455().voerRegelUit(null, huwelijk, maakActie(20130101), null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testNieuweSituatieLegeDatumEindeStandaardGroep() {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = new HuwelijkBericht();
        huwelijk.setStandaard(new RelatieStandaardGroepBericht());
        huwelijk.getStandaard().setDatumEinde(new DatumEvtDeelsOnbekendAttribuut((Integer) null));

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0455().voerRegelUit(null, huwelijk, maakActie(20130101), null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    /**
     * Maakt een huwelijk bericht.
     *
     * @param datumEinde datum einde
     * @return huwelijk geregistreerd partnerschap bericht
     */
    private HuwelijkGeregistreerdPartnerschapBericht maakHuwelijkBericht(final Integer datumEinde) {
        final HuwelijkGeregistreerdPartnerschapBericht huwelijk = new HuwelijkBericht();
        huwelijk.setStandaard(new RelatieStandaardGroepBericht());
        huwelijk.getStandaard().setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(datumEinde));
        return huwelijk;
    }

    /**
     * Maakt een actie.
     *
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @return actie bericht
     */
    private ActieBericht maakActie(final Integer datumAanvangGeldigheid) {
        final ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht actie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        return actie;
    }
}
