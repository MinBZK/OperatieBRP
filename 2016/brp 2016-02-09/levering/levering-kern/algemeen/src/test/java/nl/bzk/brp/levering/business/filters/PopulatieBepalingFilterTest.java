/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class PopulatieBepalingFilterTest {

    private static final int PERSOON_ID = 123;

    private final PopulatieBepalingFilter populatieBepalingFilter = new PopulatieBepalingFilter();
    private final AdministratieveHandelingModel administratieveHandelingModel = mock(AdministratieveHandelingModel.class);
    private final PersoonHisVolledig testPersoon = mock(PersoonHisVolledig.class);

    @Before
    public final void setup() {
        when(testPersoon.getID()).thenReturn(PERSOON_ID);
        when(administratieveHandelingModel.getSoort())
            .thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.ERKENNING_NA_GEBOORTE));
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingBetreedPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.BETREEDT;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(null, populatie, leveringAutorisatie,
                administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingBinnenPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.BINNEN;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie,
                administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingVerlaatPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.VERLAAT;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie,
                administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingBuitenPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.BUITEN;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie,
                administratieveHandelingModel);

        Assert.assertFalse(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieBetreedPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.BETREEDT;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieBinnenPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.BINNEN;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieVerlaatPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.VERLAAT;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieBuitenPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.BUITEN;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilterNietTeLeverenPersonenVoorAttenderingBetreedPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.BETREEDT;
        populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAttenderingBinnenPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.BINNEN;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);

        Assert.assertTrue(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilterNietTeLeverenPersonenVoorAttenderingVerlaatPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.VERLAAT;
        populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAttenderingBuitenPopulatie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.BUITEN;
        final boolean resultaat = populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);

        Assert.assertFalse(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilterNietTeLeverenPersonenVoorOnbekendeCatalogusOptie() throws Exception {
        final Leveringinformatie leveringAutorisatie = maakLeveringinformatie(SoortDienst.SYNCHRONISATIE_PERSOON);

        final Populatie populatie = Populatie.BINNEN;
        populatieBepalingFilter.magLeverenDoorgaan(testPersoon, populatie, leveringAutorisatie, administratieveHandelingModel);
    }

    private Leveringinformatie maakLeveringinformatie(final SoortDienst soortDienst) {
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().maak();
        final Dienst diest = TestDienstBuilder.maker().metSoortDienst(soortDienst).maak();
        return new Leveringinformatie(tla, diest);
    }
}
