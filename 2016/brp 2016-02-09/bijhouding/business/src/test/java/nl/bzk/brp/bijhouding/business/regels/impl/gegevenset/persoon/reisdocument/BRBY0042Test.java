/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0042Test {

    @Test
    public void testPersoonHeeftAlReisdocumentVanSoort() {
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonHuidigeSituatie(StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART.getWaarde(), false);
        final PersoonBericht persoonBericht =
                maakPersoonNieuweSituatie(StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART.getWaarde());

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0042().voerRegelUit(new PersoonView(persoonHisVolledig), persoonBericht, null, null);

        Assert.assertEquals(persoonBericht.getReisdocumenten().get(0), berichtEntiteits.get(0));
    }

    @Test
    public void testPersoonHeeftAlReisdocumentVanSoortMaarIsVervallen() {
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonHuidigeSituatie(StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART.getWaarde(), true);
        final PersoonBericht persoonBericht =
                maakPersoonNieuweSituatie(StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART.getWaarde());

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0042().voerRegelUit(new PersoonView(persoonHisVolledig), persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testPersoonHeeftReisdocumentVanAnderSoort() {
        final PersoonHisVolledigImpl persoonHisVolledig =
                maakPersoonHuidigeSituatie(StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART.getWaarde(), false);
        final PersoonBericht persoonBericht =
                maakPersoonNieuweSituatie(StatischeObjecttypeBuilder.EUROPEES_ID_KAART.getWaarde());

        final List<BerichtEntiteit> berichtEntiteits =
                new BRBY0042().voerRegelUit(new PersoonView(persoonHisVolledig), persoonBericht, null, null);

        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0042, new BRBY0042().getRegel());
    }

    private PersoonHisVolledigImpl maakPersoonHuidigeSituatie(final SoortNederlandsReisdocument srtDocument,
                                                              final boolean vervallenReisDocument)
    {
        final PersoonReisdocumentHisVolledigImpl reisdocumentBuilder =
                new PersoonReisdocumentHisVolledigImplBuilder(null, srtDocument)
                        .nieuwStandaardRecord(20110101).eindeRecord().build();

        if (vervallenReisDocument) {
            ReflectionTestUtils.setField(reisdocumentBuilder.getPersoonReisdocumentHistorie().getActueleRecord(),
                                         "aanduidingInhoudingVermissing",
              new AanduidingInhoudingVermissingReisdocumentAttribuut(StatischeObjecttypeBuilder.INGEHOUDEN_INGELEVERD));
        }

        return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .voegPersoonReisdocumentToe(reisdocumentBuilder)
                .build();
    }

    private PersoonBericht maakPersoonNieuweSituatie(final SoortNederlandsReisdocument srtDocument) {
        PersoonBericht persoonBericht = new PersoonBericht();
        final PersoonReisdocumentBericht reisdocumentBericht = new PersoonReisdocumentBericht();
        reisdocumentBericht.setSoort(new SoortNederlandsReisdocumentAttribuut(srtDocument));
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(reisdocumentBericht);
        return persoonBericht;
    }
}
