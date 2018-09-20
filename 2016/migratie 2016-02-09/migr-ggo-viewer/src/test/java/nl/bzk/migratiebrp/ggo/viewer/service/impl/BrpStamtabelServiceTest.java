/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FunctieAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpStamtabelServiceTest {
    private static final String MELDING_STRING = "whatever";
    private static final String STRING_FORMAT = "%s (%s)"; // code (omschrijving)
    private static final String STRING_FORMAT_GESL = "%s (%s / %s)"; // code (omschrijving mannelijk / omschrijving

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @InjectMocks
    private BrpStamtabelServiceImpl brpStamtabelService;

    @Test
    public void getNationaliteitTestOK() {
        final short natCode = (short) 1;
        final Nationaliteit expectedNat = new Nationaliteit("Nederlandse", natCode);
        final String natCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedNat.getCode()), 4);
        final String expected = String.format(STRING_FORMAT, natCodePadded, expectedNat.getNaam());
        Mockito.doReturn(expectedNat).when(dynamischeStamtabelRepository).getNationaliteitByNationaliteitcode(natCode);

        final String resultNat = brpStamtabelService.getNationaliteit(String.valueOf(natCode));
        assertEquals(expected, resultNat);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getNationaliteitTestNotExist() {
        final short natCode = (short) 999;
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING))
               .when(dynamischeStamtabelRepository)
               .getNationaliteitByNationaliteitcode(natCode);

        final String resultNat = brpStamtabelService.getNationaliteit(String.valueOf(natCode));
        assertEquals("0999", resultNat);
    }

    @Test
    public void getNaamgebruikTestOK() {
        final BrpNaamgebruikCode wggCode = BrpNaamgebruikCode.E;
        final Naamgebruik expectedWgg = Naamgebruik.EIGEN;
        final String expected = String.format(STRING_FORMAT, expectedWgg.getCode(), expectedWgg.getNaam());
        final String resultWgg = brpStamtabelService.getNaamgebruik(wggCode.getWaarde());
        assertEquals(expected, resultWgg);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getNaamgebruikTestNotExist() {
        final String naamgebruikCode = "Blaat";
        final String resultWgg = brpStamtabelService.getNaamgebruik(naamgebruikCode);
        assertEquals(resultWgg, naamgebruikCode);
    }

    @Test
    public void getAdellijkeTitelTestOK() {
        final BrpAdellijkeTitelCode atCode = new BrpAdellijkeTitelCode("B");
        final AdellijkeTitel expectedAt = AdellijkeTitel.B;
        final String expected = String.format(STRING_FORMAT_GESL, expectedAt.getCode(), expectedAt.getNaamMannelijk(), expectedAt.getNaamVrouwelijk());
        final String resultAt = brpStamtabelService.getAdellijkeTitel(atCode.getWaarde());
        assertEquals(expected, resultAt);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getAdellijkeTitelTestNotExist() {
        final BrpAdellijkeTitelCode atCode = new BrpAdellijkeTitelCode("X");
        final String resultAt = brpStamtabelService.getAdellijkeTitel(atCode.getWaarde());
        assertEquals(atCode.getWaarde(), resultAt);
    }

    @Test
    public void getBijhoudingsaardTestOK() {
        final BrpBijhoudingsaardCode baCode = BrpBijhoudingsaardCode.INGEZETENE;
        final Bijhoudingsaard expectedBa = Bijhoudingsaard.INGEZETENE;
        final String expected = String.format(STRING_FORMAT, expectedBa.getCode(), expectedBa.getNaam());
        final String resultAt = brpStamtabelService.getBijhoudingsaard(baCode.getWaarde());
        assertEquals(expected, resultAt);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getBijhoudingsaardTestNotExist() {
        final String baCode = "X";
        final String resultBa = brpStamtabelService.getBijhoudingsaard(baCode);
        assertEquals(resultBa, baCode);
    }

    @Test
    public void getPredikaatTestOK() {
        final BrpPredicaatCode predCode = new BrpPredicaatCode("J");
        final Predicaat expectedPred = Predicaat.J;
        final String expected =
                String.format(STRING_FORMAT_GESL, expectedPred.getCode(), expectedPred.getNaamMannelijk(), expectedPred.getNaamVrouwelijk());
        final String resultPred = brpStamtabelService.getPredicaat(predCode.getWaarde());
        assertEquals(expected, resultPred);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getPredikaatTestNotExist() {
        final BrpPredicaatCode predCode = new BrpPredicaatCode("X");
        final String resultPred = brpStamtabelService.getPredicaat(predCode.getWaarde());
        assertEquals(predCode.getWaarde(), resultPred);
    }

    @Test
    public void getFunctieAdresTestOK() {
        final BrpSoortAdresCode faCode = BrpSoortAdresCode.B;
        final FunctieAdres expectedFa = FunctieAdres.BRIEFADRES;
        final String expected = String.format(STRING_FORMAT, expectedFa.getCode(), expectedFa.getNaam());
        final String resultFa = brpStamtabelService.getFunctieAdres(faCode.getWaarde());
        assertEquals(expected, resultFa);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getFunctieAdresTestNotExist() {
        final String faCode = "Q";
        final String resultFa = brpStamtabelService.getFunctieAdres(faCode);
        assertEquals(resultFa, faCode);
    }

    @Test
    public void getRedenWijzigingAdresTestOK() {
        final char rwaCode = 'A';
        final RedenWijzigingVerblijf expectedRwv = new RedenWijzigingVerblijf(rwaCode, "Ambtshalve");
        final String expected = String.format(STRING_FORMAT, expectedRwv.getCode(), expectedRwv.getNaam());
        Mockito.doReturn(expectedRwv).when(dynamischeStamtabelRepository).getRedenWijzigingVerblijf(rwaCode);

        final String resultRwa = brpStamtabelService.getRedenWijzigingVerblijf(rwaCode);
        assertEquals(expected, resultRwa);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenWijzigingAdresTestNotExist() {
        final char rwaCode = 'X';
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getRedenWijzigingVerblijf(rwaCode);

        final String resultRwa = brpStamtabelService.getRedenWijzigingVerblijf(rwaCode);
        assertEquals(Character.toString(rwaCode), resultRwa);
    }

    @Test
    public void getAangeverAdreshoudingTestOK() {
        final char aaCode = 'I';
        final Aangever expectedA = new Aangever(aaCode, "Ingeschrevene", "ingeschrevene");
        final String expected = String.format(STRING_FORMAT, expectedA.getCode(), expectedA.getNaam());
        Mockito.doReturn(expectedA).when(dynamischeStamtabelRepository).getAangeverByCode(aaCode);

        final String resultAa = brpStamtabelService.getAangever(aaCode);
        assertEquals(expected, resultAa);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getAangeverAdreshoudingTestNotExist() {
        final char aaCode = 'X';
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getAangeverByCode(aaCode);

        final String resultAa = brpStamtabelService.getAangever(aaCode);
        assertEquals(Character.toString(aaCode), resultAa);
    }

    @Test
    public void getGemeenteByPartijTestOK() {
        final Short gemCode = 8;
        final Partij expectedPartij = new Partij("Bierum", gemCode);
        final Gemeente expectedGem = new Gemeente((short) 1, "Bierum", gemCode, expectedPartij);
        final String gemCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedGem.getCode()), 4);
        final String expected = String.format(STRING_FORMAT, gemCodePadded, expectedGem.getNaam());
        Mockito.doReturn(expectedPartij).when(dynamischeStamtabelRepository).getPartijByCode(gemCode);
        Mockito.doReturn(expectedGem).when(dynamischeStamtabelRepository).getGemeenteByPartij(expectedPartij);

        final String resultGem = brpStamtabelService.getGemeenteByPartij(gemCode);
        assertEquals(expected, resultGem);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getGemeenteByPartijTestNotExist() {
        final Short gemCode = 5000;
        final Partij expectedPartij = new Partij("Onbekend", gemCode);
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getPartijByCode(gemCode);
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getGemeenteByPartij(expectedPartij);

        final String resultGem = brpStamtabelService.getGemeenteByPartij(gemCode);
        assertEquals("5000", resultGem);
    }

    @Test
    public void getGemeenteCodeByPartijTestOK() {
        final Short gemCode = 8;
        final Partij expectedPartij = new Partij("Bierum", gemCode);
        final Gemeente expectedGem = new Gemeente((short) 1, "Bierum", gemCode, expectedPartij);
        final String gemCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedGem.getCode()), 4);
        final String expected = gemCodePadded;
        Mockito.doReturn(expectedPartij).when(dynamischeStamtabelRepository).getPartijByCode(gemCode);
        Mockito.doReturn(expectedGem).when(dynamischeStamtabelRepository).getGemeenteByPartij(expectedPartij);

        final String resultGem = brpStamtabelService.getGemeenteCodeByPartij(gemCode);
        assertEquals(expected, resultGem);
    }

    @Test
    public void getGemeenteCodeByPartijTestNotExist() {
        final Short gemCode = 5000;
        final Partij expectedPartij = new Partij("Onbekend", gemCode);
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getPartijByCode(gemCode);
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getGemeenteByPartij(expectedPartij);

        final String resultGem = brpStamtabelService.getGemeenteCodeByPartij(gemCode);
        assertNull(resultGem);
    }

    @Test
    public void getPartijTestOK() {
        final int partijCode = 1401;
        final Partij expectedPartij = new Partij("Groningen", partijCode);
        final String gemCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedPartij.getCode()), 4);
        final String expected = String.format(STRING_FORMAT, gemCodePadded, expectedPartij.getNaam());
        Mockito.doReturn(expectedPartij).when(dynamischeStamtabelRepository).getPartijByCode(partijCode);

        final String resultPartij = brpStamtabelService.getPartij(partijCode);
        assertEquals(expected, resultPartij);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getPartijTestNotExist() {
        final int partijCode = 500000;
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getPartijByCode(partijCode);

        final String resultGem = brpStamtabelService.getPartij(partijCode);
        assertEquals("500000", resultGem);
    }

    @Test
    public void getLandTestOK() {
        final short landCode = (short) 6037;
        final LandOfGebied expectedLandOfGebied = new LandOfGebied(landCode, "Spanje");
        final String landCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedLandOfGebied.getCode()), 4);
        final String expected = String.format(STRING_FORMAT, landCodePadded, expectedLandOfGebied.getNaam());
        Mockito.doReturn(expectedLandOfGebied).when(dynamischeStamtabelRepository).getLandOfGebiedByCode(landCode);

        final String resultLand = brpStamtabelService.getLandOfGebied(String.valueOf(landCode));
        assertEquals(expected, resultLand);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getLandTestNotExist() {
        final short landCode = (short) 50;
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getLandOfGebiedByCode(landCode);

        final String resultLand = brpStamtabelService.getLandOfGebied(String.valueOf(landCode));
        assertEquals("0050", resultLand);
    }

    @Test
    public void getGeslachtsaanduidingTestOK() {
        final BrpGeslachtsaanduidingCode gaCode = BrpGeslachtsaanduidingCode.VROUW;
        final Geslachtsaanduiding expectedGa = Geslachtsaanduiding.VROUW;
        final String expected = String.format(STRING_FORMAT, expectedGa.getCode(), expectedGa.getNaam());
        final String resultGa = brpStamtabelService.getGeslachtsaanduiding(gaCode.getWaarde());
        assertEquals(expected, resultGa);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getGeslachtsaanduidingTestNotExist() {
        final String gaCode = "S";
        final String resultGa = brpStamtabelService.getGeslachtsaanduiding(gaCode);
        assertEquals(resultGa, gaCode);
    }

    @Test
    public void getRedenVerkrijgingNlTestOK() {
        final short rvnCode = (short) 0;
        final RedenVerkrijgingNLNationaliteit expectedRvn = new RedenVerkrijgingNLNationaliteit(rvnCode, "000");
        final String rvnCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedRvn.getCode()), 3);
        final String expected = String.format(STRING_FORMAT, rvnCodePadded, expectedRvn.getOmschrijving());
        Mockito.doReturn(expectedRvn).when(dynamischeStamtabelRepository).getRedenVerkrijgingNLNationaliteitByCode(rvnCode);

        final String resultRvn = brpStamtabelService.getRedenVerkrijgingNederlandschap(String.valueOf(rvnCode));
        assertEquals(expected, resultRvn);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenVerkrijgingNlTestNotExist() {
        final short rvnCode = (short) 300;
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING))
               .when(dynamischeStamtabelRepository)
               .getRedenVerkrijgingNLNationaliteitByCode(rvnCode);

        final String resultRvn = brpStamtabelService.getRedenVerkrijgingNederlandschap(String.valueOf(rvnCode));
        assertEquals("300", resultRvn);
    }

    @Test
    public void getRedenVerliesNlTestOK() {
        final short rvnCode = (short) 34;
        final RedenVerliesNLNationaliteit expectedRvn = new RedenVerliesNLNationaliteit(rvnCode, "034");
        final String rvnCodePadded = BrpStamtabelServiceTest.zeroPad(String.valueOf(expectedRvn.getCode()), 3);
        final String expected = String.format(STRING_FORMAT, rvnCodePadded, expectedRvn.getOmschrijving());
        Mockito.doReturn(expectedRvn).when(dynamischeStamtabelRepository).getRedenVerliesNLNationaliteitByCode(rvnCode);

        final String resultRvn = brpStamtabelService.getRedenVerliesNederlandschap(String.valueOf(rvnCode));
        assertEquals(expected, resultRvn);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenVerliesNlTestNotExist() {
        final short rvnCode = (short) 2;
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING))
               .when(dynamischeStamtabelRepository)
               .getRedenVerliesNLNationaliteitByCode(rvnCode);

        final String resultRvn = brpStamtabelService.getRedenVerliesNederlandschap(String.valueOf(rvnCode));
        assertEquals("002", resultRvn);
    }

    @Test
    public void getSoortNlReisdocumentTestOK() {
        final String snrCode = "NI";
        final SoortNederlandsReisdocument expectedSnr = new SoortNederlandsReisdocument(snrCode, "Nederlandse identiteitskaart");
        final String expected = String.format(STRING_FORMAT, expectedSnr.getCode(), expectedSnr.getOmschrijving());
        Mockito.doReturn(expectedSnr).when(dynamischeStamtabelRepository).getSoortNederlandsReisdocumentByCode(snrCode);

        final String resultSnr = brpStamtabelService.getSoortNlReisdocument(snrCode);
        assertEquals(expected, resultSnr);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getSoortNlReisdocumentTestNotExist() {
        final String snrCode = "XY";
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING))
               .when(dynamischeStamtabelRepository)
               .getSoortNederlandsReisdocumentByCode(snrCode);

        final String resultSnr = brpStamtabelService.getSoortNlReisdocument(snrCode);
        assertEquals("XY", resultSnr);
    }

    @Test
    public void getAutoriteitAfgifteReisdocTestOK() {
        final String aarCode = "PK";

        final String resultAar = brpStamtabelService.getAutoriteitVanAfgifteReisdocument(aarCode);
        assertEquals(aarCode, resultAar);
    }

    @Test
    public void getRedenVervallenReisdocTestOK() {
        final char rvrCode = '?';
        final AanduidingInhoudingOfVermissingReisdocument expectedRvr = new AanduidingInhoudingOfVermissingReisdocument(rvrCode, "Onbekend");
        final String expected = String.format(STRING_FORMAT, expectedRvr.getCode(), expectedRvr.getNaam());
        Mockito.doReturn(expectedRvr).when(dynamischeStamtabelRepository).getAanduidingInhoudingOfVermissingReisdocumentByCode(rvrCode);

        final String resultRvr = brpStamtabelService.getRedenOntbrekenReisdocument(String.valueOf(rvrCode));
        assertEquals(expected, resultRvr);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenVervallenReisdocTestNotExist() {
        final char rvrCode = '#';
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING))
               .when(dynamischeStamtabelRepository)
               .getAanduidingInhoudingOfVermissingReisdocumentByCode(rvrCode);

        final String resultRvr = brpStamtabelService.getRedenOntbrekenReisdocument(String.valueOf(rvrCode));
        assertEquals("#", resultRvr);
    }

    @Test
    public void getVerblijfstitelTestOK() {
        final Verblijfsrecht expectedVb =
                new Verblijfsrecht((short) 11, "Vergunning art. 9 Vw, zonder beperking, dan wel gerechtigd krachtens art. 10 Vw");
        // TODO: verblijfstitel opzoeken bij code en dan code + omschrijving terugkrijgen.
        // final String expected = String.format(STRING_FORMAT, "", expectedVb.getOmschrijving());
        final String expected = expectedVb.getOmschrijving();
        Mockito.doReturn(expectedVb).when(dynamischeStamtabelRepository).getVerblijfsrechtByCode(expectedVb.getCode());

        final String resultVb = brpStamtabelService.getVerblijfstitel("11");
        assertEquals("11 (Vergunning art. 9 Vw, zonder beperking, dan wel gerechtigd krachtens art. 10 Vw)", resultVb);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getVerblijfsrechtTestNotExist() {
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getVerblijfsrechtByCode((short) 99);

        final String resultVb = brpStamtabelService.getVerblijfstitel("99");
        assertEquals("99", resultVb);
    }

    @Test
    public void getRedenOpschortingTestOK() {
        final BrpNadereBijhoudingsaardCode robCode = BrpNadereBijhoudingsaardCode.MINISTERIEEL_BESLUIT;
        final NadereBijhoudingsaard expectedRob = NadereBijhoudingsaard.MINISTERIEEL_BESLUIT;
        final String expected = String.format(STRING_FORMAT, expectedRob.getCode(), expectedRob.getNaam());
        final String resultRob = brpStamtabelService.getRedenOpschorting(robCode.getWaarde());
        assertEquals(expected, resultRob);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenOpschortingTestNotExist() {
        final String onbekendeRedenOpschorting = "Z";
        final String resultRob = brpStamtabelService.getRedenOpschorting(onbekendeRedenOpschorting);
        assertEquals(onbekendeRedenOpschorting, resultRob);
    }

    @Test
    public void getRedenEindeRelatieTestOK() {
        final char rerCode = 'N';
        final RedenBeeindigingRelatie expectedRer = new RedenBeeindigingRelatie(rerCode, "Nietigverklaring");
        expectedRer.setCode(rerCode);
        expectedRer.setOmschrijving("Nietigverklaring");
        final String expected = String.format(STRING_FORMAT, expectedRer.getCode(), expectedRer.getOmschrijving());
        Mockito.doReturn(expectedRer).when(dynamischeStamtabelRepository).getRedenBeeindigingRelatieByCode(rerCode);

        final String resultRer = brpStamtabelService.getRedenEindeRelatie(String.valueOf(rerCode));
        assertEquals(expected, resultRer);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenEindeRelatieNotExist() {
        final char rerCode = '-';
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING))
               .when(dynamischeStamtabelRepository)
               .getRedenBeeindigingRelatieByCode(rerCode);

        final String resultRer = brpStamtabelService.getRedenEindeRelatie(String.valueOf(rerCode));
        assertEquals("-", resultRer);
    }

    private static String zeroPad(final String string, final int size) {
        return StringUtils.leftPad(string, size, "0");
    }
}
