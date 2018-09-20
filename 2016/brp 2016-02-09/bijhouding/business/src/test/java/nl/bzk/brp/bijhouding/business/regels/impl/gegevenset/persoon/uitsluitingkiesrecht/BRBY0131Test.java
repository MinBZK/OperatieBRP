/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.uitsluitingkiesrecht;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0003;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0131Test {

    private final BRBY0131 brby0131 = new BRBY0131();

    @Before
    public void init() {
        ReflectionTestUtils.setField(brby0131, "brby0003", new BRBY0003());
    }

    @Test
    public void testVoerRegelUitMeerderjarigPersoon() {
        PersoonBericht persoonBericht = new PersoonBericht();
        final List<BerichtEntiteit> berichtEntiteits =
            brby0131.voerRegelUit(maakPersoon(19830404), persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testVoerRegelUitMinderjarigPersoon() {
        PersoonBericht persoonBericht = new PersoonBericht();
        final List<BerichtEntiteit> berichtEntiteits =
            brby0131.voerRegelUit(maakPersoon(DatumAttribuut.vandaag().getWaarde()), persoonBericht, null, null);
        Assert.assertEquals(persoonBericht, berichtEntiteits.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0131, new BRBY0131().getRegel());
    }

    private PersoonView maakPersoon(final int datumGeboorte) {
        return new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(
                        new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()),
                                       null, DatumTijdAttribuut.nu(), null))
                .datumGeboorte(datumGeboorte).eindeRecord().build());
    }
}
