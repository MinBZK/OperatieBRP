/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonDeelnameEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0132Test {

    @Test
    public void testNederlander() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(true));
        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0132().voerRegelUit(maakPersoon(true), persoonBericht, null, null);
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testGeenNederlander() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setDeelnameEUVerkiezingen(new PersoonDeelnameEUVerkiezingenGroepBericht());
        persoonBericht.getDeelnameEUVerkiezingen().setIndicatieDeelnameEUVerkiezingen(new JaNeeAttribuut(true));
        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0132().voerRegelUit(maakPersoon(false), persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    private PersoonView maakPersoon(final boolean metNlNationaliteit) {
        final PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigImpl persoon = persoonHisVolledigImplBuilder.build();

        if (metNlNationaliteit) {
            final PersoonNationaliteitHisVolledigImpl persNation = new PersoonNationaliteitHisVolledigImplBuilder(
                    persoon, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde())
                    .nieuwStandaardRecord(20100101, null, 20100101).eindeRecord().build();
            persoon.getNationaliteiten().add(persNation);
        }

        return new PersoonView(persoon);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0132, new BRBY0132().getRegel());
    }
}
