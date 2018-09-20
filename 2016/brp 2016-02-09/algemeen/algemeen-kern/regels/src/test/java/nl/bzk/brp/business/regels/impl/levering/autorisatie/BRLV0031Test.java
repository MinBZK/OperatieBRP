/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

import nl.bzk.brp.business.regels.context.VerstrekkingsbeperkingRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BRLV0031Test {

    @InjectMocks
    private final BRLV0031 brlv0031 = new BRLV0031();
    private final Partij   partij   = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0031, brlv0031.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(VerstrekkingsbeperkingRegelContext.class, brlv0031.getContextType());
    }

    @Test
    public void testVoerRegelUitGeeftGeenMelding() {
        final PersoonView persoonView = new PersoonView(TestPersoonJohnnyJordaan.maak());
        final VerstrekkingsbeperkingRegelContext regelContext =
            new VerstrekkingsbeperkingRegelContext(persoonView, partij);

        final boolean resultaat = brlv0031.valideer(regelContext);

        Assert.assertTrue(resultaat);
    }

    @Test
    public void testVoerRegelUitGeeftWelMelding() {
        ReflectionTestUtils.setField(partij, "indicatieVerstrekkingsbeperkingMogelijk", JaNeeAttribuut.JA);

        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder verstrekkingsbeperkingBuilder =
            new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder();
        verstrekkingsbeperkingBuilder
            .nieuwStandaardRecord(20010101)
            .waarde(Ja.J)
            .eindeRecord();
        final PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        persoonHisVolledigImplBuilder.voegPersoonIndicatieVolledigeVerstrekkingsbeperkingToe(verstrekkingsbeperkingBuilder.build());

        final PersoonView persoonView = new PersoonView(persoonHisVolledigImplBuilder.build());

        final VerstrekkingsbeperkingRegelContext regelContext =
            new VerstrekkingsbeperkingRegelContext(persoonView, partij);

        final boolean resultaat = brlv0031.valideer(regelContext);

        Assert.assertFalse(resultaat);
    }

}
