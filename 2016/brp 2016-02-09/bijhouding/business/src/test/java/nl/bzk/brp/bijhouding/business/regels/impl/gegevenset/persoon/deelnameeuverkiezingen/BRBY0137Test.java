/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonDeelnameEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0137Test {

    private final BRBY0137 brby0137 = new BRBY0137();

    @Before
    public void init() {
        ReflectionTestUtils.setField(brby0137, "brby0003", new BRBY0003());
    }

    @Test
    public void testVoerRegelUitMeerderjarigPersoon() {
        final List<BerichtEntiteit> berichtEntiteiten =
                brby0137.voerRegelUit(maakHuidigeSituatie(19830404), maakNieuweSituatie(true, true), null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testVoerRegelUitMinderjarigPersoonEuverkiezingenNull() {
        final List<BerichtEntiteit> berichtEntiteiten =
                brby0137.voerRegelUit(maakHuidigeSituatie(19830404), maakNieuweSituatie(false, false), null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testVoerRegelUitMinderjarigPersoonDatumNull() {
        final List<BerichtEntiteit> berichtEntiteiten =
                brby0137.voerRegelUit(maakHuidigeSituatie(19830404), maakNieuweSituatie(true, false), null, null);
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testVoerRegelUitMinderjarigPersoon() {
        PersoonBericht persoonBericht = maakNieuweSituatie(true, true);
        final List<BerichtEntiteit> berichtEntiteiten =
                brby0137.voerRegelUit(maakHuidigeSituatie(DatumAttribuut.vandaag().getWaarde()), persoonBericht, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0137, brby0137.getRegel());
    }

    private PersoonBericht maakNieuweSituatie(final boolean euverkiezingen, final boolean datumAanleidingAanpassing) {
        PersoonBericht persoon = new PersoonBericht();

        if (euverkiezingen) {
            PersoonDeelnameEUVerkiezingenGroepBericht euVerkiezing = new PersoonDeelnameEUVerkiezingenGroepBericht();
            if (datumAanleidingAanpassing) {
                euVerkiezing.setDatumAanleidingAanpassingDeelnameEUVerkiezingen(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
            }
            persoon.setDeelnameEUVerkiezingen(euVerkiezing);
        }

        return persoon;
    }

    private PersoonView maakHuidigeSituatie(final int datumGeboorte) {
        return new PersoonView(
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(
                        new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()),
                                       null, DatumTijdAttribuut.nu(), null))
                        .datumGeboorte(datumGeboorte).eindeRecord().build()
        );
    }
}
