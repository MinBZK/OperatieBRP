/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

@RunWith(MockitoJUnitRunner.class)
public class LeveringinformatieServiceImplTest {

    private static final String LEVERINGSAUTORISATIE_NAAM = "leveringsautorisatie";
    private static final String FOUT                      = "Fout!";

    @Mock
    private PartijService partijService;

    @Mock
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @InjectMocks
    private final LeveringinformatieService leveringinformatieService = new LeveringinformatieServiceImpl();


    @Test
    public final void testGeefLeveringinformatie() {
        // arrange
        when(toegangLeveringsautorisatieService.bestaatLeveringautorisatie(anyInt())).thenReturn(true);
        when(partijService.bestaatPartij(anyInt())).thenReturn(true);

        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(TestDienstBuilder.maker().
                metSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON).maak()).maak();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metDienstbundels(dienstbundel).maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();

        when(toegangLeveringsautorisatieService.geefToegangLeveringsautorisatieOpZonderControle(anyInt(), anyInt()))
            .thenReturn(tla);

        // act
        final int partijCode = 758;
        final Leveringinformatie li =
            leveringinformatieService.geefLeveringinformatie(0, partijCode, SoortDienst.SYNCHRONISATIE_PERSOON);

        // assert
        Assert.assertNotNull(li);
        Assert.assertEquals(tla, li.getToegangLeveringsautorisatie());
        Assert.assertEquals(dienstbundel.getDiensten().iterator().next(), li.getDienst());
    }

    @Test
    public final void geefLeveringinformatieMetDienstId() {
        // arrange
        when(toegangLeveringsautorisatieService.bestaatLeveringautorisatie(anyInt())).thenReturn(true);
        when(partijService.bestaatPartij(anyInt())).thenReturn(true);

        final Dienstbundel dienstbundel1 = TestDienstbundelBuilder.maker().
                metDiensten(
                        TestDienstBuilder.maker().
                                metId(1).
                                metSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON).
                                maak()).
                maak();
        final Dienstbundel dienstbundel2 = TestDienstbundelBuilder.maker().
                metDiensten(TestDienstBuilder.maker().
                        metId(2).metSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON).
                        maak()).
                maak();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().
                metDienstbundels(dienstbundel1, dienstbundel2).
                maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().
                metLeveringsautorisatie(la).
                maak();

        when(toegangLeveringsautorisatieService.geefToegangLeveringsautorisatieOpZonderControle(anyInt(), anyInt()))
                .thenReturn(tla);

        // act
        final int partijCode = 758;
        final ToegangLeveringsautorisatie li =
                leveringinformatieService.geefToegangLeveringautorisatie(0, partijCode);

        // assert
        Assert.assertNotNull(li);
        Assert.assertEquals(tla, li);
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testGeefLeveringinformatieMetFoutievePartijCode() {
        // arrange
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(TestDienstBuilder.maker().maak()).maak();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metDienstbundels(dienstbundel).maak();
        when(partijService.vindPartijOpCode(anyInt())).thenThrow(new EmptyResultDataAccessException(FOUT, 1));
        when(toegangLeveringsautorisatieService.geefLeveringautorisatie(anyInt())).thenReturn(la);

        // act
        leveringinformatieService.geefLeveringinformatie(0, 757,  SoortDienst.SYNCHRONISATIE_PERSOON);
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testGeefLeveringinformatieMetFoutieveLeveringsautorisatieNaam() {
        // arrange
        when(toegangLeveringsautorisatieService.geefLeveringautorisatie(anyInt())).thenThrow(new EmptyResultDataAccessException(FOUT, 1));

        // act
        leveringinformatieService.geefLeveringinformatie(10, 758, SoortDienst.SYNCHRONISATIE_PERSOON);
    }

    @Test
    public final void testGeefLeveringautorisatie() {
        maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true);
        final List<Leveringinformatie> leveringAutorisaties = leveringinformatieService.
            geefGeldigeLeveringinformaties(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);

        Assert.assertNotNull(leveringAutorisaties);
        Assert.assertEquals(1, leveringAutorisaties.size());
    }

    @Test
    public final void testGeenLeveringautorisatieIndienSoortDienstMismatch() {
        maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true);
        final List<Leveringinformatie> leveringAutorisaties = leveringinformatieService.
            geefGeldigeLeveringinformaties(SoortDienst.ATTENDERING);

        Assert.assertNotNull(leveringAutorisaties);
        Assert.assertEquals(0, leveringAutorisaties.size());
    }

    @Test
    public final void testGeefGeldigeLeveringinformatiesMetLegeArrayCategorieDiensten() {
        maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true);
        final SoortDienst[] categorieDiensten = new SoortDienst[]{};
        final List<Leveringinformatie> leveringAutorisaties = leveringinformatieService.geefGeldigeLeveringinformaties(categorieDiensten);

        Assert.assertNotNull(leveringAutorisaties);
        Assert.assertEquals(1, leveringAutorisaties.size());
    }

    @Test
    public final void testGeefGeldigeLeveringinformatiesMetNullArrayCategorieDiensten() {
        maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true);
        final List<Leveringinformatie> leveringAutorisaties = leveringinformatieService.geefGeldigeLeveringinformaties((SoortDienst[]) null);

        Assert.assertNotNull(leveringAutorisaties);
        Assert.assertEquals(0, leveringAutorisaties.size());
    }

    @Test
    public final void testGeefGeldigeLeveringinformatiesZonderCategorieDiensten() {
        maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true);
        final List<Leveringinformatie> leveringAutorisaties = leveringinformatieService.geefGeldigeLeveringinformaties();

        Assert.assertNotNull(leveringAutorisaties);
        Assert.assertEquals(1, leveringAutorisaties.size());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void gooitExceptieAlsPartijNietAanwezig() {
        when(toegangLeveringsautorisatieService.bestaatLeveringautorisatie(anyInt())).thenReturn(true);
        leveringinformatieService.geefLeveringinformatie(0, 758, SoortDienst.SYNCHRONISATIE_PERSOON);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void gooitExceptieAlsGeenToegangleveringsautorisatieGevonden() {
        when(toegangLeveringsautorisatieService.bestaatLeveringautorisatie(anyInt())).thenReturn(true);
        when(partijService.bestaatPartij(anyInt())).thenReturn(true);
        when(toegangLeveringsautorisatieService.geefToegangLeveringsautorisatieOpZonderControle(anyInt(), anyInt()))
            .thenThrow(new EmptyResultDataAccessException(FOUT, 1));

        leveringinformatieService.geefLeveringinformatie(0, 758, SoortDienst.SYNCHRONISATIE_PERSOON);
    }

    private void maakToegangLeveringautorisatie(final SoortDienst soortDienst, boolean leveringautorisatieGeldig, boolean dienstGeldig) {

        final DatumAttribuut datumIngangToegangLeveringautorisatie = DatumAttribuut.gisteren();
        DatumAttribuut datumEindeToegangLeveringautorisatie = DatumAttribuut.morgen();
        final DatumAttribuut datumIngangDienst = DatumAttribuut.gisteren();
        DatumAttribuut datumEindeDienst = DatumAttribuut.morgen();

        if (!leveringautorisatieGeldig) {
            datumEindeToegangLeveringautorisatie = DatumAttribuut.gisteren();
        }
        if (!dienstGeldig) {
            datumEindeDienst = DatumAttribuut.gisteren();
        }

        final List<Dienst> diensten = new LinkedList<>();
        diensten.add(TestDienstBuilder.maker().metSoortDienst(soortDienst).metDatumIngang(datumIngangDienst).metDatumEinde(datumEindeDienst).maak());

        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(diensten.toArray(new Dienst[diensten.size()])).maak();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metDienstbundels(dienstbundel).metNaam(LEVERINGSAUTORISATIE_NAAM).maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).
            metDatumIngang(datumIngangToegangLeveringautorisatie).metDatumEinde(datumEindeToegangLeveringautorisatie).maak();

        Mockito.when(toegangLeveringsautorisatieService.geefGeldigeLeveringsautorisaties()).thenReturn(Collections.singletonList(tla));
    }

}
