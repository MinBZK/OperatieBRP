/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class VerstrekkingsbeperkingFilterTest extends AbstractFilterTest {

    @InjectMocks
    private final VerstrekkingsbeperkingFilter verstrekkingsbeperkingFilter = new VerstrekkingsbeperkingFilter();

    private final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();

    @Test
    public final void testLeveringMagDoorgaan() {
        final Leveringinformatie leveringAutorisatie =
                maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 123);

        final boolean magDoorgaan = verstrekkingsbeperkingFilter
                .magLeverenDoorgaan(persoon, null, leveringAutorisatie, null);

        Assert.assertTrue(magDoorgaan);
    }

    @Test
    public final void testLeveringMagNietDoorgaan() {
        final ActieModel actie20130101 = new ActieModel(new SoortActieAttribuut(SoortActie.CONVERSIE_G_B_A), null, null,
                                                  new DatumEvtDeelsOnbekendAttribuut(20130101), null,
                                                  DatumTijdAttribuut.datumTijd(2013, 1, 1, 0, 0, 0), null);

        final Leveringinformatie leveringAutorisatie =
                maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 123);

        final Partij partij = leveringAutorisatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
        ReflectionTestUtils.setField(partij, "indicatieVerstrekkingsbeperkingMogelijk", new JaNeeAttribuut(true));
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder vollVerstrBepBuilder
            = new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(persoon);
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl vollVerstrekkingsbeperking =
                vollVerstrBepBuilder.nieuwStandaardRecord(20130101).eindeRecord().build();

        ReflectionTestUtils.setField(persoon, "indicatieVolledigeVerstrekkingsbeperking",
                                     vollVerstrekkingsbeperking);
        persoon.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie()
                .voegToe(new HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel(vollVerstrekkingsbeperking,
                                                                                     new JaAttribuut(Ja.J),
                                                                                     actie20130101,
                                                                                     null));

        final boolean magDoorgaan = verstrekkingsbeperkingFilter
                .magLeverenDoorgaan(persoon, null, leveringAutorisatie, null);

        Assert.assertFalse(magDoorgaan);
    }

}
