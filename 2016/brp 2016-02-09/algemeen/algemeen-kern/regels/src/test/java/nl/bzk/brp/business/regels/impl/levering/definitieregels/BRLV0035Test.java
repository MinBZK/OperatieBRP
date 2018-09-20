/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.definitieregels;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.HashSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledigImplBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BRLV0035Test {

    private static final PartijCodeAttribuut PARTIJ_CODE = new PartijCodeAttribuut(136);

    private static final Partij VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ = TestPartijBuilder.maker()
        .metCode(PARTIJ_CODE)
        .metIndicatieVerstrekkingsbeperkingMogelijk(JaNeeAttribuut.JA)
        .maak();

    private static final Partij VERSTREKKINGSBEPERKING_MOGELIJK_NEE_PARTIJ = TestPartijBuilder.maker()
        .metCode(PARTIJ_CODE)
        .metIndicatieVerstrekkingsbeperkingMogelijk(JaNeeAttribuut.NEE)
        .maak();

    private final BRLV0035 brlv0035 = new BRLV0035();

    private final PersoonHisVolledigImpl persoon = TestPersoonJohnnyJordaan.maak();

    @Test
    public void testGeenVerstrekkingsbeperking() {
        final Partij partij = TestPartijBuilder.maker().metCode(PARTIJ_CODE).maak();
        final boolean verstrekkingsbeperkingAanwezig = brlv0035
            .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVolledigeVerstrekkingsbeperking() {
        voegVolledigeVerstrekkingsbeperkingToeAanPersoon(Ja.J);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
            .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ);

        assertTrue(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpPartij() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ;
        voegVerstrekkingsbeperkingToeAanPersoonVoorPartij(partij);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
            .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertTrue(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpGemeenteVerordening() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ;
        voegVerstrekkingsbeperkingGemeenteVerordeningToeAanPersoonVoorPartij(partij);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
            .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testWelVolledigeVerstrekkingsBeperkingMaarNietMogelijkOpPartij() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_NEE_PARTIJ;
        voegVolledigeVerstrekkingsbeperkingToeAanPersoon(Ja.J);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
                .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpPartijMaarNietMogelijk() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_NEE_PARTIJ;
        voegVerstrekkingsbeperkingToeAanPersoonVoorPartij(partij);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
                .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testDefaultVolledigeVerstrekkingsbeperking() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ;

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
                .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVerstrekkingsbeperkingOpAnderePartij() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ;
        final Partij anderePartij = TestPartijBuilder.maker()
            .metCode(new PartijCodeAttribuut(15643))
            .maak();

        voegVerstrekkingsbeperkingToeAanPersoonVoorPartij(anderePartij);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
                .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    @Test
    public void testVolledigeVerstrekkingsbeperkingNull() {
        final Partij partij = VERSTREKKINGSBEPERKING_MOGELIJK_JA_PARTIJ;

        voegVolledigeVerstrekkingsbeperkingToeAanPersoon(null);

        final boolean verstrekkingsbeperkingAanwezig = brlv0035
                .isErEenGeldigeVerstrekkingsBeperking(new PersoonView(persoon), partij);

        assertFalse(verstrekkingsbeperkingAanwezig);
    }

    private void voegVerstrekkingsbeperkingToeAanPersoonVoorPartij(final Partij verstrekkingsbeperkingPartij) {
        final PersoonVerstrekkingsbeperkingHisVolledigImplBuilder verstrekkingsbeperkingHisVolledigImplBuilder =
                new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(persoon,
                        verstrekkingsbeperkingPartij, null, null);
        final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrekkingsbeperkingHisVolledig =
                verstrekkingsbeperkingHisVolledigImplBuilder.build();

        final ActieModel actieInhoud =
                new ActieModel(null, null,
                        null, null, null,
                        DatumTijdAttribuut.nu(), null);

        final HisPersoonVerstrekkingsbeperkingModel hisPersoonVerstrekkingsbeperkingModel =
                new HisPersoonVerstrekkingsbeperkingModel(verstrekkingsbeperkingHisVolledig, actieInhoud);
        verstrekkingsbeperkingHisVolledig.getPersoonVerstrekkingsbeperkingHistorie()
                .voegToe(hisPersoonVerstrekkingsbeperkingModel);

        persoon.setVerstrekkingsbeperkingen(new HashSet<>(
                Arrays.asList(
                        new PersoonVerstrekkingsbeperkingHisVolledigImpl[] { verstrekkingsbeperkingHisVolledig })));
    }

    private void voegVerstrekkingsbeperkingGemeenteVerordeningToeAanPersoonVoorPartij(
            final Partij verstrekkingsbeperkingPartij)
    {
        final PersoonVerstrekkingsbeperkingHisVolledigImplBuilder verstrekkingsbeperkingHisVolledigImplBuilder =
                new PersoonVerstrekkingsbeperkingHisVolledigImplBuilder(persoon,
                        null,
                        new OmschrijvingEnumeratiewaardeAttribuut(
                                "Sinterklaas"),
                        verstrekkingsbeperkingPartij);
        final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrekkingsbeperkingHisVolledig =
                verstrekkingsbeperkingHisVolledigImplBuilder.build();

        persoon.setVerstrekkingsbeperkingen(new HashSet<>(
                Arrays.asList(
                        new PersoonVerstrekkingsbeperkingHisVolledigImpl[] { verstrekkingsbeperkingHisVolledig })));
    }

    private void voegVolledigeVerstrekkingsbeperkingToeAanPersoon(final Ja ja) {
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder
                volledigeVerstrekkingsbeperkingBuilder =
                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImplBuilder(persoon);
        volledigeVerstrekkingsbeperkingBuilder.nieuwStandaardRecord(20000101).waarde(ja).eindeRecord();
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl volledigeVerstrekkingsbeperkingVolledig =
                volledigeVerstrekkingsbeperkingBuilder.build();

        persoon.setIndicatieVolledigeVerstrekkingsbeperking(volledigeVerstrekkingsbeperkingVolledig);
    }

}
