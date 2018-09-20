/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import org.junit.Test;


public class ReferentieDataJpaRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Test
    public void testVindWoonplaatsOpCode() {
        Plaats woonplaats = referentieDataRepository.vindWoonplaatsOpCode(new PlaatsCode((short) 34));
        Assert.assertNotNull(woonplaats);
        Assert.assertEquals("Almeres", woonplaats.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeWoonplaatsOpCode() {
        referentieDataRepository.vindWoonplaatsOpCode(new PlaatsCode((short) 0));
    }

    @Test
    public void testVindGemeenteOpCode() {
        Partij gemeente = referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 34));
        Assert.assertNotNull(gemeente);
        Assert.assertEquals("Almere", gemeente.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeGemeenteOpCode() {
        referentieDataRepository.vindGemeenteOpCode(new Gemeentecode((short) 0));
    }

    @Test
    public void testVindLandOpCode() {
        Land land = referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE);
        Assert.assertNotNull(land);
        Assert.assertEquals("Nederland", land.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandLandOpCode() {
        referentieDataRepository.vindLandOpCode(new Landcode((short) 0));
    }

    @Test
    public void testVindNationaliteitOpCode() {
        Nationaliteit nationaliteit =
            referentieDataRepository.vindNationaliteitOpCode(new Nationaliteitcode((short) 1));
        Assert.assertNotNull(nationaliteit);
        Assert.assertEquals("Nederlandse", nationaliteit.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeNationaliteitOpCode() {
        referentieDataRepository.vindNationaliteitOpCode(new Nationaliteitcode((short) 9999));
    }

    @Test
    public void testVindRedenWijzingAdresOpCode() {
        RedenWijzigingAdres redenwijziging =
            referentieDataRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("P"));
        Assert.assertNotNull(redenwijziging);
        Assert.assertEquals("Aangifte door persoon", redenwijziging.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenWijzigingAdresOpCode() {
        referentieDataRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("0000"));
    }

    @Test
    public void testVindAangeverAdreshoudingOpCode() {
        AangeverAdreshouding aangever =
            referentieDataRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("G"));
        Assert.assertNotNull(aangever);
        Assert.assertEquals("Gezaghouder", aangever.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAangeverAdreshoudingOpCode() {
        referentieDataRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("0000"));
    }

    @Test
    public void testVindAdellijkeTitelOpCode() {
        AdellijkeTitel adellijkeTitel =
            referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("G"));
        Assert.assertNotNull(adellijkeTitel);
        Assert.assertEquals("graaf", adellijkeTitel.getNaamMannelijk().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAdellijkeTitelOpCode() {
        referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("X"));
    }

    @Test
    public void testVindPredikaatOpCode() {
        Predikaat predikaat =
            referentieDataRepository.vindPredikaatOpCode(new PredikaatCode("H"));
        Assert.assertNotNull(predikaat);
        Assert.assertEquals("Zijne Hoogheid", predikaat.getNaamMannelijk().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandPredikaatOpCode() {
        referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("X"));
    }

    @Test
    public void testVindRedenVerkregenNlNationaliteitOpCode() {
        RedenVerkrijgingNLNationaliteit redenVerkrijging =
            referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(new RedenVerkrijgingCode((short) 0));
        Assert.assertNotNull(redenVerkrijging);
        Assert.assertEquals("Onbekend", redenVerkrijging.getOmschrijving().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenVerkregenNationaliteitOpCode() {
        referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(new RedenVerkrijgingCode((short) 10));
    }

//    @Test
//    public void testVindRedenVerliesNlNationaliteitOpNaam() {
//        RedenVerliesNLNationaliteit redenVerlies =
//            referentieDataRepository.vindRedenVerliesNLNationaliteitOpNaam(new RedenVerliesNaam("H"));
//        Assert.assertNotNull(redenVerlies);
//        Assert.assertEquals("Gezaghouder", redenVerlies.getNaam().getWaarde());
//    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenVerliesNlNationaliteitOpNaam() {
        referentieDataRepository.vindRedenVerliesNLNationaliteitOpNaam(new RedenVerliesNaam("X"));
    }

}
