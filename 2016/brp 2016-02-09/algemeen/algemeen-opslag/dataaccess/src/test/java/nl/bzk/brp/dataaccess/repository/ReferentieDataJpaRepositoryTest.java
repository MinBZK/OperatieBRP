/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;


public class ReferentieDataJpaRepositoryTest extends AbstractRepositoryTestCase {

    private static final String ALMERE = "Almere";
    private static final String ABC = "ABC";
    private static final String WAARDE_0000 = "0000";
    private static final String G = "G";
    private static final String X = "X";
    private static final String ONBEKEND = "Onbekend";
    private static final String XX = "XX";
    private static final String VAN_DE = "van de";
    private static final String SCHEIDINGSTEKEN = " ";
    private static final String DAL = "dal";

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Test
    public void testIsBestaandeWoonplaats() {
        Assert.assertTrue(referentieDataRepository.isBestaandeWoonplaats(new NaamEnumeratiewaardeAttribuut(ALMERE)));
    }

    @Test
    public void testIsNietBestaandeWoonplaats() {
        Assert.assertFalse(referentieDataRepository.isBestaandeWoonplaats(new NaamEnumeratiewaardeAttribuut(ABC)));
    }

    @Test
    public void testVindWoonplaatsOpNaam() {
        final Plaats woonplaats = referentieDataRepository.vindWoonplaatsOpNaam(new NaamEnumeratiewaardeAttribuut(ALMERE));
        Assert.assertNotNull(woonplaats);
        Assert.assertEquals(ALMERE, woonplaats.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeWoonplaatsOpNaam() {
        referentieDataRepository.vindWoonplaatsOpNaam(new NaamEnumeratiewaardeAttribuut(ABC));
    }

    @Test
    public void testVindGemeenteOpCode() {
        final Gemeente gemeente = referentieDataRepository.vindGemeenteOpCode(
                new GemeenteCodeAttribuut(Short.parseShort("0034")));
        Assert.assertNotNull(gemeente);
        Assert.assertEquals(ALMERE, gemeente.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeGemeenteOpCode() {
        Gemeente gemeente = referentieDataRepository.vindGemeenteOpCode(new GemeenteCodeAttribuut((short) -1));
        System.out.println(gemeente.getNaam());
    }

    @Test
    public void testFindHuidigeGemeenteByPersoonID() {
        final Gemeente huidigeGemeente = referentieDataRepository.findHuidigeGemeenteByPersoonID(1);
        Assert.assertEquals(ALMERE, huidigeGemeente.getNaam().getWaarde());
    }

    @Test
    public void testFindHuidigeGemeenteByPersoonIDGeenAdressen() {
        final Gemeente huidigeGemeente = referentieDataRepository.findHuidigeGemeenteByPersoonID(888888888);
        Assert.assertNull(huidigeGemeente);
    }

    @Test
    public void testVindPartijOpCode() {
        final Partij partij = referentieDataRepository.vindPartijOpCode(
                new PartijCodeAttribuut(3401));
        Assert.assertNotNull(partij);
        Assert.assertEquals("Gemeente Almere", partij.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandePartijOpCode() {
        referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt("99999")));
    }

    @Test
    public void testVindLandOpCode() {
        final LandGebied land = referentieDataRepository.vindLandOpCode(LandGebiedCodeAttribuut.NEDERLAND);
        Assert.assertNotNull(land);
        Assert.assertEquals("Nederland", land.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandLandOpCode() {
        referentieDataRepository.vindLandOpCode(new LandGebiedCodeAttribuut((short) 0));
    }

    @Test
    public void testVindNationaliteitOpCode() {
        final Nationaliteit nationaliteit =
                referentieDataRepository.vindNationaliteitOpCode(new NationaliteitcodeAttribuut("0001"));
        Assert.assertNotNull(nationaliteit);
        Assert.assertEquals("Nederlandse", nationaliteit.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeNationaliteitOpCode() {
        referentieDataRepository.vindNationaliteitOpCode(new NationaliteitcodeAttribuut("9999"));
    }

    @Test
    public void testVindRedenWijzingAdresOpCode() {
        final RedenWijzigingVerblijf redenwijziging =
                referentieDataRepository.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut("P"));
        Assert.assertNotNull(redenwijziging);
        Assert.assertEquals("Aangifte door persoon", redenwijziging.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenWijzigingAdresOpCode() {
        referentieDataRepository.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut(WAARDE_0000));
    }

    @Test
    public void testVindAangeverAdreshoudingOpCode() {
        final Aangever aangever =
                referentieDataRepository.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut(G));
        Assert.assertNotNull(aangever);
        Assert.assertEquals("Gezaghouder", aangever.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAangeverAdreshoudingOpCode() {
        referentieDataRepository.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut(WAARDE_0000));
    }

    @Test
    public void testVindAdellijkeTitelOpCode() {
        final AdellijkeTitel adellijkeTitel =
                referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(G));
        Assert.assertNotNull(adellijkeTitel);
        Assert.assertEquals("graaf", adellijkeTitel.getNaamMannelijk().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAdellijkeTitelOpCode() {
        referentieDataRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(X));
    }

    @Test
    public void testVindPredikaatOpCode() {
        final Predicaat predikaat =
                referentieDataRepository.vindPredicaatOpCode(new PredicaatCodeAttribuut("H"));
        Assert.assertNotNull(predikaat);
        Assert.assertEquals("Zijne Hoogheid", predikaat.getNaamMannelijk().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandPredicaatOpCode() {
        referentieDataRepository.vindPredicaatOpCode(new PredicaatCodeAttribuut(X));
    }

    @Test
    public void testVindRedenVerkregenNlNationaliteitOpCode() {
        final RedenVerkrijgingNLNationaliteit redenVerkrijging =
                referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(new RedenVerkrijgingCodeAttribuut(
                        Short.parseShort("999")));
        Assert.assertNotNull(redenVerkrijging);
        Assert.assertEquals(ONBEKEND, redenVerkrijging.getOmschrijving().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenVerkregenNationaliteitOpCode() {
        referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(new RedenVerkrijgingCodeAttribuut((short) 10));
    }

    @Test
    public void testVindRedenVerliesNlNationaliteitOpNaam() {
        final RedenVerliesNLNationaliteit redenVerlies =
                referentieDataRepository.vindRedenVerliesNLNationaliteitOpCode(new RedenVerliesCodeAttribuut((short) 4));
        Assert.assertNotNull(redenVerlies);
        Assert.assertEquals("Test", redenVerlies.getOmschrijving().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenVerliesNlNationaliteitOpNaam() {
        referentieDataRepository.vindRedenVerliesNLNationaliteitOpCode(new RedenVerliesCodeAttribuut((short) 7));
    }

    @Test
    public void testVindRedenEindeRelatieOpCode() {
        final RedenEindeRelatie redenEinde =
                referentieDataRepository
                        .vindRedenEindeRelatieOpCode(new RedenEindeRelatieCodeAttribuut("?"));
        Assert.assertNotNull(redenEinde);
        Assert.assertEquals(ONBEKEND, redenEinde.getOmschrijving().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeRedenEindeRelatieOpCode() {
        referentieDataRepository.vindRedenEindeRelatieOpCode(new RedenEindeRelatieCodeAttribuut(XX));
    }

    @Test
    public void testVindSoortDocumentOpNaam() {
        final SoortDocument soortDocument = referentieDataRepository.vindSoortDocumentOpNaam(new NaamEnumeratiewaardeAttribuut("Geboorteakte"));
        Assert.assertNotNull(soortDocument);
        Assert.assertEquals("notnull", soortDocument.getOmschrijving().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeSoortDocumentOpNaam() {
        referentieDataRepository.vindSoortDocumentOpNaam(new NaamEnumeratiewaardeAttribuut(XX));
    }

    @Test
    public void testVindSoortReisdocumentOpCode() {
        final SoortNederlandsReisdocument soortNederlandsReisdocument =
                referentieDataRepository.vindSoortReisdocumentOpCode(new SoortNederlandsReisdocumentCodeAttribuut("EK"));
        Assert.assertNotNull(soortNederlandsReisdocument);
        Assert.assertEquals("Europese identiteitskaart", soortNederlandsReisdocument.getOmschrijving().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandSoortReisdocumentOpCode() {
        referentieDataRepository.vindSoortReisdocumentOpCode(new SoortNederlandsReisdocumentCodeAttribuut(XX));
    }

    @Test
    public void testVindAanduidingInhoudingVermissingReisdocumentOpCode() {
        final AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument =
                referentieDataRepository.vindAanduidingInhoudingVermissingReisdocumentOpCode(
                        new AanduidingInhoudingVermissingReisdocumentCodeAttribuut("V"));
        Assert.assertNotNull(aanduidingInhoudingVermissingReisdocument);
        Assert.assertEquals("Vermist", aanduidingInhoudingVermissingReisdocument.getNaam().getWaarde());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testVindNietBestaandeAanduidingInhoudingVermissingReisdocumentOpCode() {
        referentieDataRepository.vindAanduidingInhoudingVermissingReisdocumentOpCode(
                new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(X));
    }

    @Test
    public void testBestaatVoorvoegselScheidingsteken() {
        Assert.assertTrue(referentieDataRepository.bestaatVoorvoegselScheidingsteken(VAN_DE, SCHEIDINGSTEKEN));
        Assert.assertTrue(referentieDataRepository.bestaatVoorvoegselScheidingsteken(DAL, "'"));
        Assert.assertFalse(referentieDataRepository.bestaatVoorvoegselScheidingsteken("van der", SCHEIDINGSTEKEN));
        Assert.assertFalse(referentieDataRepository.bestaatVoorvoegselScheidingsteken(DAL, SCHEIDINGSTEKEN));
        Assert.assertFalse(referentieDataRepository.bestaatVoorvoegselScheidingsteken(null, SCHEIDINGSTEKEN));
        Assert.assertFalse(referentieDataRepository.bestaatVoorvoegselScheidingsteken(VAN_DE, null));
        Assert.assertFalse(referentieDataRepository.bestaatVoorvoegselScheidingsteken("", ""));
        Assert.assertFalse(referentieDataRepository.bestaatVoorvoegselScheidingsteken(null, null));
    }

    @Test
    public void testGetNederland() {
        final LandGebied nederland = referentieDataRepository.getNederland();
        Assert.assertNotNull(nederland);
        Assert.assertEquals(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, nederland.getCode().getWaarde().toString());
    }
}
