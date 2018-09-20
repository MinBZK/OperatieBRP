/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkView;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BRBY0443Test {

    private final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie = new HuwelijkBericht();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0443, new BRBY0443().getRegel());
    }

    @Test
    public void testDatumEindeIsAlGevuld() {
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0443().voerRegelUit(maakHuidigeSituatie(20130101), nieuweSituatie, null, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
        Assert.assertEquals(nieuweSituatie, berichtEntiteits.get(0));
    }

    @Test
    public void testDatumEindeIsNietGevuld() {
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0443().voerRegelUit(maakHuidigeSituatie(null), nieuweSituatie, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    private HuwelijkGeregistreerdPartnerschapView maakHuidigeSituatie(final Integer datumEinde)
    {
        final HuwelijkHisVolledigImpl huwelijk = new HuwelijkHisVolledigImplBuilder()
                .nieuwStandaardRecord(20120101)
                .datumEinde(datumEinde).eindeRecord().build();
        return new HuwelijkView(huwelijk, DatumTijdAttribuut.nu());
    }

}
