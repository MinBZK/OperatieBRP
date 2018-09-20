/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0040Test {

    @Test
    public void testIndicatieWaardeNull() {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final PersoonView huidigeSituatie = new PersoonView(
                maakPersoon(SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT));
        ReflectionTestUtils.setField(huidigeSituatie.getIndicaties().iterator().next().getStandaard().getWaarde(), "waarde", null);
        Assert.assertEquals(0, new BRBY0040().voerRegelUit(huidigeSituatie, nieuweSituatie, null, null).size());
    }

    @Test
    public void testVoerRegelUitRegelGaatAf() {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final PersoonHisVolledigImpl huidigeSituatie =
                maakPersoon(SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT);
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0040().voerRegelUit(new PersoonView(huidigeSituatie), nieuweSituatie, null, null);
        Assert.assertEquals(nieuweSituatie, berichtEntiteits.get(0));
    }

    @Test
    public void testVoerRegelUitRegelGaatNietAf() {
        final PersoonHisVolledigImpl huidigeSituatie =
                maakPersoon(SoortIndicatie.INDICATIE_ONDER_CURATELE);
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0040().voerRegelUit(new PersoonView(huidigeSituatie), null, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }


    @Test
    public void testVoerRegelUitPersoonZonderIndicaties() {
        final PersoonHisVolledigImpl huidigeSituatie =
               new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0040().voerRegelUit(new PersoonView(huidigeSituatie), null, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    private PersoonHisVolledigImpl maakPersoon(final SoortIndicatie srtIndicatie) {
        if (srtIndicatie == SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT) {
            final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl indicatie =
                    new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImplBuilder()
                            .nieuwStandaardRecord(20110101).waarde(Ja.J).eindeRecord().build();
            return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .voegPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentToe(indicatie).build();
        } else {
            final PersoonIndicatieOnderCurateleHisVolledigImpl indicatie =
                    new PersoonIndicatieOnderCurateleHisVolledigImplBuilder()
                            .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
            return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .voegPersoonIndicatieOnderCurateleToe(indicatie).build();
        }
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0040, new BRBY0040().getRegel());
    }
}
