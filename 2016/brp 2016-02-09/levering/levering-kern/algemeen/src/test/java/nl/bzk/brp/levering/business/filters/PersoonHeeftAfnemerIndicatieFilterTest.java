/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PersoonHeeftAfnemerIndicatieFilterTest extends AbstractFilterTest {

    private final PersoonHeeftAfnemerIndicatieFilter afnemerIndicatieFilter = new PersoonHeeftAfnemerIndicatieFilter();

    @Test
    public final void dienstAndersDanLeverenAfnemerIndicatie() {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.SYNCHRONISATIE_PERSOON, 1);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(null, Populatie.BINNEN, leveringAutorisatie, administratieveHandeling);

        assertThat(resultaat, is(true));
    }

    @Test
    public final void geenAfnemerIndicatieGevonden() {
        // arrange
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 123);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(persoon, Populatie.BINNEN, leveringAutorisatie, administratieveHandeling);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void welAfnemerIndicatieGevonden() {
        // arrange
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatieImpl(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
                TestPersoonJohnnyJordaan.AFNEMERINDICATIE_PARTIJ);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(persoon, Populatie.BINNEN, leveringAutorisatie, administratieveHandeling);

        // assert
        assertThat(resultaat, is(true));
    }

    @Test
    public final void afnemerIndicatieNietGevondenPartijMismatch() {
        // arrange
        final Partij partijZonderAfnemerindicatie = TestPartijBuilder.maker().metCode(Integer.MAX_VALUE).maak();
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatieImpl(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
                partijZonderAfnemerindicatie);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(persoon, Populatie.BINNEN, leveringAutorisatie, administratieveHandeling);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void verlopenAfnemerIndicatieGevonden() {
        // arrange
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 2);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(persoon, Populatie.BINNEN, leveringAutorisatie, administratieveHandeling);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void verlopenNuAfnemerIndicatieGevonden() {
        // arrange
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 4);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(persoon, Populatie.BINNEN, leveringAutorisatie, administratieveHandeling);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void welAfnemerIndicatieGevondenPersoonBuitenPopulatie() {
        // arrange
        final PersoonHisVolledig persoon = TestPersoonJohnnyJordaan.maak();
        final Populatie populatieBepaling = Populatie.BUITEN;
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatieImpl(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
                3, TestPersoonJohnnyJordaan.AFNEMERINDICATIE_PARTIJ);
        final AdministratieveHandelingModel administratieveHandeling = maakTestAdministratieveHandeling();

        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeverenDoorgaan(persoon, populatieBepaling, leveringAutorisatie, administratieveHandeling);

        // assert
        assertThat(resultaat, is(true));
    }

    /**
     * Maakt een test administratieve handeling.
     *
     * @return administratieve handeling model
     */
    private AdministratieveHandelingModel maakTestAdministratieveHandeling() {
        return new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND),
                new PartijAttribuut(TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(2).maak()),
                new OntleningstoelichtingAttribuut(""),
                DatumTijdAttribuut.nu());
    }

    private Leveringinformatie maakLeveringinformatieImpl(final SoortDienst soortDienst,
                                                          final int leveringautorisatieId,
                                                          final Partij partij) {
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst).maak();
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().metId(leveringautorisatieId).metDienstbundels(
                TestDienstbundelBuilder.maker().metDiensten(dienst).maak()).maak();
        final PartijRol partijRol = new PartijRol(partij, null, null, null);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().
                metLeveringsautorisatie(la).metGeautoriseerde(partijRol).maak();
        return new Leveringinformatie(tla, dienst);
    }
}
