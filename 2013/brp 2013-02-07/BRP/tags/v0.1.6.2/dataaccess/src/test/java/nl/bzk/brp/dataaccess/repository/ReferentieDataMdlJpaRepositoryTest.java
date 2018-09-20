/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingNaam;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import org.junit.Test;


public class ReferentieDataMdlJpaRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private ReferentieDataMdlRepository referentieDataMdlRepository;

    @Test
    public void testVindWoonplaatsOpCode() {
        Plaats woonplaats = referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0034"));
        Assert.assertNotNull(woonplaats);
        Assert.assertEquals("Almeres", woonplaats.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeWoonplaatsOpCode() {
        referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0000"));
    }

    @Test
    public void testVindGemeenteOpCode() {
        Partij gemeente = referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034"));
        Assert.assertNotNull(gemeente);
        Assert.assertEquals("Almere", gemeente.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeGemeenteOpCode() {
        referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0000"));
    }

    @Test
    public void testVindLandOpCode() {
        Land land = referentieDataMdlRepository.vindLandOpCode(new LandCode("6030"));
        Assert.assertNotNull(land);
        Assert.assertEquals("Nederland", land.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandLandOpCode() {
        referentieDataMdlRepository.vindLandOpCode(new LandCode("0000"));
    }

    @Test
    public void testVindNationaliteitOpCode() {
        Nationaliteit nationaliteit =
            referentieDataMdlRepository.vindNationaliteitOpCode(new NationaliteitCode("0001"));
        Assert.assertNotNull(nationaliteit);
        Assert.assertEquals("Nederlandse", nationaliteit.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeNationaliteitOpCode() {
        referentieDataMdlRepository.vindNationaliteitOpCode(new NationaliteitCode("9999"));
    }

    @Test
    public void testVindRedenWijzingAdresOpCode() {
        RedenWijzigingAdres redenwijziging =
            referentieDataMdlRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("P"));
        Assert.assertNotNull(redenwijziging);
        Assert.assertEquals("Aangifte door persoon", redenwijziging.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenWijzigingAdresOpCode() {
        referentieDataMdlRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("0000"));
    }

    @Test
    public void testVindAangeverAdreshoudingOpCode() {
        AangeverAdreshouding aangever =
            referentieDataMdlRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("G"));
        Assert.assertNotNull(aangever);
        Assert.assertEquals("Gezaghouder", aangever.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAangeverAdreshoudingOpCode() {
        referentieDataMdlRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("0000"));
    }

    @Test
    public void testVindAdellijkeTitelOpCode() {
        AdellijkeTitel adellijkeTitel =
            referentieDataMdlRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("G"));
        Assert.assertNotNull(adellijkeTitel);
        Assert.assertEquals("graaf", adellijkeTitel.getNaamMannelijk().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAdellijkeTitelOpCode() {
        referentieDataMdlRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("X"));
    }

    @Test
    public void testVindPredikaatOpCode() {
        Predikaat predikaat =
            referentieDataMdlRepository.vindPredikaatOpCode(new PredikaatCode("H"));
        Assert.assertNotNull(predikaat);
        Assert.assertEquals("Zijne Hoogheid", predikaat.getNaamMannelijk().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandPredikaatOpCode() {
        referentieDataMdlRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode("X"));
    }

//    @Test
//    public void testVindRedenVerkregenNlNationaliteitOpNaam() {
//        RedenVerkrijgingNLNationaliteit redenVerkrijging =
//            referentieDataMdlRepository.vindRedenVerkregenNlNationaliteitOpNaam(new RedenVerkrijgingNaam("H"));
//        Assert.assertNotNull(redenVerkrijging);
//        Assert.assertEquals("Gezaghouder", redenVerkrijging.getRedenVerkrijgingNaam().getWaarde());
//    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenVerkregenNationaliteitOpNaam() {
        referentieDataMdlRepository.vindRedenVerkregenNlNationaliteitOpNaam(new RedenVerkrijgingNaam("X"));
    }

//    @Test
//    public void testVindRedenVerliesNlNationaliteitOpNaam() {
//        RedenVerliesNLNationaliteit redenVerlies =
//            referentieDataMdlRepository.vindRedenVerliesNLNationaliteitOpNaam(new RedenVerliesNaam("H"));
//        Assert.assertNotNull(redenVerlies);
//        Assert.assertEquals("Gezaghouder", redenVerlies.getNaam().getWaarde());
//    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenVerliesNlNationaliteitOpNaam() {
        referentieDataMdlRepository.vindRedenVerliesNLNationaliteitOpNaam(new RedenVerliesNaam("X"));
    }

}
