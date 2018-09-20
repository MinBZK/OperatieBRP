/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonDeelnameEUVerkiezingenGroepBericht;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0133Test {

    @Test
    public void testVoerRegelUitDatumEindeGevuldEnGeenDeelname() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(
            DatumAttribuut.vandaag()));
        persoonBericht.getDeelnameEUVerkiezingen().setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(false));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0133().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitDatumEindeGevuldEnWelDeelname() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setDatumVoorzienEindeUitsluitingEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        persoonBericht.getDeelnameEUVerkiezingen().setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(true));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0133().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitDatumEindeNietGevuldEnWelDeelname() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(true));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0133().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitDatumEindeNietGevuldEnGeenDeelname() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(false));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY0133().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0133, new BRBY0133().getRegel());
    }
}
