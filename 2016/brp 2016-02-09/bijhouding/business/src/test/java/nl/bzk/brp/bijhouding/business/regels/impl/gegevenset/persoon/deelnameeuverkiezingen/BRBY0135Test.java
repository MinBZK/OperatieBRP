/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonDeelnameEUVerkiezingenGroepBericht;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BRBY0135Test {

    @Test
    public void testVoerRegelUitDeelnameEUVerkiezingenNull() {
        PersoonBericht persoonBericht = new PersoonBericht();

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0135().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitDatumEindeNull() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumAanleidingAanpassingDeelnameEUVerkiezingen(
                new DatumEvtDeelsOnbekendAttribuut(20100101));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0135().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitDatumAanleidingNull() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(20100505));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0135().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitDatumEindeNaDatumAanleiding() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumAanleidingAanpassingDeelnameEUVerkiezingen(
                new DatumEvtDeelsOnbekendAttribuut(20100101));
        persoonBericht.getDeelnameEUVerkiezingen().setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(20100505));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0135().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitDatumEindeVoorDatumAanleiding() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumAanleidingAanpassingDeelnameEUVerkiezingen(
                new DatumEvtDeelsOnbekendAttribuut(20100101));
        persoonBericht.getDeelnameEUVerkiezingen().setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(20090505));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0135().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitDatumEindeOpDatumAanleiding() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumAanleidingAanpassingDeelnameEUVerkiezingen(
                new DatumEvtDeelsOnbekendAttribuut(20100101));
        persoonBericht.getDeelnameEUVerkiezingen().setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(20100101));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0135().voerRegelUit(null, persoonBericht, null, null);

        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0135, new BRBY0135().getRegel());
    }
}
