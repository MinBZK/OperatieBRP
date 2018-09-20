/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieCurateleBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY2014Test {

    private final BRBY2014 brby2014 = new BRBY2014();
    private ActieBericht actie = new ActieRegistratieCurateleBericht();

    @Before
    public void init() {
        ReflectionTestUtils.setField(brby2014, "brby0003", new BRBY0003());
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
    }

    @Test
    public void testVoerRegelUitMeerderjarigPersoon() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final List<BerichtEntiteit> berichtEntiteits =
                brby2014.voerRegelUit(maakPersoon(19830404), persoonBericht, actie, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitMinderjarigPersoon() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final List<BerichtEntiteit> berichtEntiteits =
                brby2014.voerRegelUit(maakPersoon(DatumAttribuut.vandaag().getWaarde()), persoonBericht, actie, null);
        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2014, brby2014.getRegel());
    }

    private PersoonView maakPersoon(final int datumGeboorte) {
        return new PersoonView(
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(
                        new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()),
                                       null, DatumTijdAttribuut.nu(), null))
                        .datumGeboorte(datumGeboorte).eindeRecord().build()
        );
    }
}
