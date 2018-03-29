/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Lo3StamtabelServiceTest {
    private static final String MELDING_STRING = "whatever";

    private static final String STRING_FORMAT = "%s (%s)"; // code (omschrijving)
    // private static final String STRING_FORMAT_GESL = "%s (%s / %s)"; // code (omschrijving mannelijk / omschrijving

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    private ConversietabelFactory conversietabellen;

    @InjectMocks
    private Lo3StamtabelServiceImpl lo3StamtabelService;

    @Test
    public void getAanduidingInhoudingVermissingNlReisdocumentOkTest() {
        final String loAanduidingInhVermNlReisdoc = "I";
        final String expedtedLabel = "Ingehouden";
        final String expected = String.format(STRING_FORMAT, loAanduidingInhVermNlReisdoc, expedtedLabel);

        final String resultNat = lo3StamtabelService.getAanduidingInhoudingVermissingNlReisdocument(loAanduidingInhVermNlReisdoc);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAanduidingInhoudingVermissingNlReisdocumentNOkTest() {
        final String loAanduidingInhVermNlReisdoc = "X";

        final String resultNat = lo3StamtabelService.getAanduidingInhoudingVermissingNlReisdocument(loAanduidingInhVermNlReisdoc);
        Assert.assertEquals(loAanduidingInhVermNlReisdoc, resultNat);
    }

    @Test
    public void getAangifteAdreshoudingOkTest() {
        final String code = "A";
        final String expedtedLabel = "Ambtshalve";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getAangifteAdreshouding(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAangifteAdreshoudingNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getAangifteAdreshouding(code);
        Assert.assertEquals(code, resultNat);
    }

    @Test
    public void getAdellijkeTitelPredikaatOkTest() {
        final String code = "JH";
        final String expectedLabel = "jonkheer / jonkvrouw";
        final String expected = code + " (" + expectedLabel + ")";

        final String resultNat = lo3StamtabelService.getAdellijkeTitelPredikaat(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAdellijkeTitelPredikaatNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getAdellijkeTitelPredikaat(code);
        Assert.assertEquals(code, resultNat);
    }

    // getAanduidingBijzonderNederlandschap
    @Test
    public void getAanduidingBijzonderNederlandschapOkTest() {
        final String code = "B";
        final String expedtedLabel = "Behandeld als nederlander";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getAanduidingBijzonderNederlandschap(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAanduidingBijzonderNederlandschapNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getAanduidingBijzonderNederlandschap(code);
        Assert.assertEquals(code, resultNat);
    }

    // getAanduidingEuropeesKiesrecht
    @Test
    public void getAanduidingEuropeesKiesrechtOkTest() {
        final String code = "1";
        final String expedtedLabel = "Uitgesloten";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getAanduidingEuropeesKiesrecht(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAanduidingEuropeesKiesrechtNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getAanduidingEuropeesKiesrecht(code);
        Assert.assertEquals(code, resultNat);
    }

    // getAanduidingHuisnummer
    @Test
    public void getAanduidingHuisnummerOkTest() {
        final String code = "by";
        final String expedtedLabel = "Bij";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getAanduidingHuisnummer(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAanduidingHuisnummerNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getAanduidingHuisnummer(code);
        Assert.assertEquals(code, resultNat);
    }

    // getAanduidingUitgeslotenKiesrecht
    @Test
    public void getAanduidingUitgeslotenKiesrechtOkTest() {
        final String code = "A";
        final String expedtedLabel = "Uitgesloten kiesrecht";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getAanduidingUitgeslotenKiesrecht(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getAanduidingUitgeslotenKiesrechtNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getAanduidingUitgeslotenKiesrecht(code);
        Assert.assertEquals(code, resultNat);
    }

    // getIndicatieGeheim
    @Test
    public void getIndicatieGeheimOkTest() {
        final String code = "0";
        final String expedtedLabel = "Geen beperking";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getIndicatieGeheim(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getIndicatieGeheimNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatieGeheim(code);
        Assert.assertEquals(code, resultNat);
    }

    // getIndicatiePKVolledigGeconverteerdCode
    @Test
    public void getIndicatiePKVolledigGeconverteerdCodeOkTest() {
        final String code = "P";
        final String expedtedLabel = "Volledig geconverteerd";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getIndicatiePKVolledigGeconverteerdCode(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getIndicatiePKVolledigGeconverteerdCodeNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatiePKVolledigGeconverteerdCode(code);
        Assert.assertEquals(code, resultNat);
    }

    // getSoortVerbintenis
    @Test
    public void getSoortVerbintenisOkTest() {
        final String code = "H";
        final String expedtedLabel = "Huwelijk";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getSoortVerbintenis(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getSoortVerbintenisNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getSoortVerbintenis(code);
        Assert.assertEquals(code, resultNat);
    }

    // getIndicatieCurateleRegister
    @Test
    public void getIndicatieCurateleRegisterOkTest() {
        final String code = "1";
        final String expedtedLabel = "Curator aangesteld";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getIndicatieCurateleRegister(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getIndicatieCurateleRegisterNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatieCurateleRegister(code);
        Assert.assertEquals(code, resultNat);
    }

    // getIndicatieGezagMinderjarige
    @Test
    public void getIndicatieGezagMinderjarigeOkTest() {
        final String code = "1";
        final String expedtedLabel = "Ouder1";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getIndicatieGezagMinderjarige(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getIndicatieGezagMinderjarigeNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatieGezagMinderjarige(code);
        Assert.assertEquals(code, resultNat);
    }

    // getIndicatieOnjuist
    @Test
    public void getIndicatieOnjuistOkTest() {
        final String code = "O";
        final String expedtedLabel = "Onjuist";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getIndicatieOnjuist(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getIndicatieOnjuistNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatieOnjuist(code);
        Assert.assertEquals(code, resultNat);
    }

    // getIndicatieDocument
    @Test
    public void getIndicatieDocumentOkTest() {
        final String code = "1";
        final String expedtedLabel = "Ja";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getIndicatieDocument(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getIndicatieDocumentNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatieDocument(code);
        Assert.assertEquals(code, resultNat);
    }

    @Test
    public void getIndicatiePkVolledigGeconverteerdCodeNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getIndicatiePKVolledigGeconverteerdCode(code);
        Assert.assertEquals(code, resultNat);
    }

    // getSignalering
    @Test
    public void getSignaleringOkTest() {
        final String code = "1";
        final String expedtedLabel = "Ja";
        final String expected = String.format(STRING_FORMAT, code, expedtedLabel);

        final String resultNat = lo3StamtabelService.getSignalering(code);
        Assert.assertEquals(expected, resultNat);
    }

    @Test
    public void getSignaleringNOkTest() {
        final String code = "X";

        final String resultNat = lo3StamtabelService.getSignalering(code);
        Assert.assertEquals(code, resultNat);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getNationaliteitTestNotExist() {
        final String natCode = "0999";
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getNationaliteitByNationaliteitcode(natCode);

        final String resultNat = lo3StamtabelService.getNationaliteit(natCode);
        Assert.assertEquals("0999", resultNat);
    }

    @Test
    public void getNaamgebruikTestOK() {
        final BrpNaamgebruikCode wggCode = BrpNaamgebruikCode.E;
        final Naamgebruik expectedWgg = Naamgebruik.EIGEN;
        final String expected = String.format(STRING_FORMAT, expectedWgg.getCode(), expectedWgg.getNaam());
        final String resultWgg = lo3StamtabelService.getNaamgebruik(wggCode.getWaarde());
        Assert.assertEquals(expected, resultWgg);
    }

    @Test
    public void getFunctieAdresTestOK() {
        final BrpSoortAdresCode faCode = BrpSoortAdresCode.B;
        final SoortAdres expectedFa = SoortAdres.BRIEFADRES;
        final String expected = String.format(STRING_FORMAT, expectedFa.getCode(), expectedFa.getNaam());
        final String resultFa = lo3StamtabelService.getFunctieAdres(faCode.getWaarde());
        Assert.assertEquals(expected, resultFa);
    }

    @Test
    public void getGemeenteTestOK() {
        final String gemCode = "0008";
        final Partij expectedPartij = new Partij("Bierum", gemCode+"01");
        final Gemeente expectedGem = new Gemeente((short) 1, "Bierum", gemCode, expectedPartij);
        final String expected = String.format(STRING_FORMAT, expectedGem.getCode(), expectedGem.getNaam());
        Mockito.doReturn(expectedGem).when(dynamischeStamtabelRepository).getGemeenteByGemeentecode(gemCode);

        final String resultGem = lo3StamtabelService.getGemeente(gemCode);
        Assert.assertEquals(expected, resultGem);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getGemeenteTestNotExist() {
        final String gemCode = "5000";
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getGemeenteByGemeentecode(gemCode);

        final String resultGem = lo3StamtabelService.getGemeente(gemCode);
        Assert.assertEquals("5000", resultGem);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven omdat dit geen gemeenteCode is. GemeenteCode moet 4 cijfers lang
     * zijn.
     */
    @Test
    public void getGemeenteTestNotNumeric() {
        final String gemCode = "Plaats";
        final String resultGem = lo3StamtabelService.getGemeente(gemCode);
        Assert.assertEquals(gemCode, resultGem);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat dit een plaats kan zijn en geen gemeenteCode is.
     * GemeenteCode moet 4 cijfers lang zijn.
     */
    @Test
    public void getGemeenteTestNotValid() {
        final String gemCode = "12345";
        final String resultGem = lo3StamtabelService.getGemeente(gemCode);
        Assert.assertEquals(gemCode, resultGem);
    }

    @Test
    public void getLandTestOK() {
        final String landCode = "6037";
        final LandOfGebied expectedLandOfGebied = new LandOfGebied(landCode, "Spanje");
        final String landCodePadded = expectedLandOfGebied.getCode();
        final String expected = String.format(STRING_FORMAT, landCodePadded, expectedLandOfGebied.getNaam());
        Mockito.doReturn(expectedLandOfGebied).when(dynamischeStamtabelRepository).getLandOfGebiedByCode(landCode);

        final String resultLand = lo3StamtabelService.getLandOfGebied(landCode);
        Assert.assertEquals(expected, resultLand);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getLandTestNotExist() {
        final String landCode = "0050";
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getLandOfGebiedByCode(landCode);

        final String resultLand = lo3StamtabelService.getLandOfGebied(landCode);
        Assert.assertEquals("0050", resultLand);
    }

    @Test
    public void getGeslachtsaanduidingTestOK() {
        final BrpGeslachtsaanduidingCode gaCode = BrpGeslachtsaanduidingCode.VROUW;
        final Geslachtsaanduiding expectedGa = Geslachtsaanduiding.VROUW;
        final String expected = String.format(STRING_FORMAT, expectedGa.getCode(), expectedGa.getNaam());
        final String resultGa = lo3StamtabelService.getGeslachtsaanduiding(gaCode.getWaarde());
        Assert.assertEquals(expected, resultGa);
    }

    @Test
    public void getRedenVerkrijgingNlTestOK() {
        final String rvnCode = "000";
        final RedenVerkrijgingNLNationaliteit expectedRvn = new RedenVerkrijgingNLNationaliteit(rvnCode, "000");
        final String rvnCodePadded = expectedRvn.getCode();
        final String expected = String.format(STRING_FORMAT, rvnCodePadded, expectedRvn.getOmschrijving());
        Mockito.doReturn(expectedRvn).when(dynamischeStamtabelRepository).getRedenVerkrijgingNLNationaliteitByCode(rvnCode);

        final String resultRvn = lo3StamtabelService.getRedenVerkrijgingNederlandschap(rvnCode);
        Assert.assertEquals(expected, resultRvn);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenVerkrijgingNlTestNotExist() {
        final String rvnCode = "300";
        Mockito.when(dynamischeStamtabelRepository.getRedenVerkrijgingNLNationaliteitByCode(rvnCode)).thenReturn(null);
        final String resultRvn = lo3StamtabelService.getRedenVerkrijgingNederlandschap(rvnCode);
        Assert.assertEquals("300", resultRvn);
    }

    @Test
    public void getRedenVerliesNlTestOK() {
        final String rvnCode = "034";
        final RedenVerliesNLNationaliteit expectedRvn = new RedenVerliesNLNationaliteit(rvnCode, "034");
        final String rvnCodePadded = expectedRvn.getCode();
        final String expected = String.format(STRING_FORMAT, rvnCodePadded, expectedRvn.getOmschrijving());
        Mockito.doReturn(expectedRvn).when(dynamischeStamtabelRepository).getRedenVerliesNLNationaliteitByCode(rvnCode);

        final String resultRvn = lo3StamtabelService.getRedenVerliesNederlandschap(rvnCode);
        Assert.assertEquals(expected, resultRvn);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenVerliesNlTestNotExist() {
        final String rvnCode = "002";
        Mockito.when(dynamischeStamtabelRepository.getRedenVerliesNLNationaliteitByCode(rvnCode)).thenReturn(null);

        final String resultRvn = lo3StamtabelService.getRedenVerliesNederlandschap(rvnCode);
        Assert.assertEquals("002", resultRvn);
    }

    @Test
    public void getSoortNlReisdocumentTestOK() {
        final String snrCode = "NI";
        final SoortNederlandsReisdocument expectedSnr = new SoortNederlandsReisdocument(snrCode, "Nederlandse identiteitskaart");
        final String expected = String.format(STRING_FORMAT, expectedSnr.getCode(), expectedSnr.getOmschrijving());
        Mockito.doReturn(expectedSnr).when(dynamischeStamtabelRepository).getSoortNederlandsReisdocumentByCode(snrCode);

        final String resultSnr = lo3StamtabelService.getSoortNlReisdocument(snrCode);
        Assert.assertEquals(expected, resultSnr);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getSoortNlReisdocumentTestNotExist() {
        final String snrCode = "XY";
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getSoortNederlandsReisdocumentByCode(snrCode);

        final String resultSnr = lo3StamtabelService.getSoortNlReisdocument(snrCode);
        Assert.assertEquals("XY", resultSnr);
    }

    @Test
    public void getAutoriteitAfgifteReisdocTestOK() {
        final String aarCode = "PK";
        final String resultAar = lo3StamtabelService.getAutoriteitVanAfgifteReisdocument(aarCode);
        Assert.assertEquals(aarCode, resultAar);
    }

    @Test
    public void getRedenOpschortingTestOK() {
        final BrpNadereBijhoudingsaardCode robCode = BrpNadereBijhoudingsaardCode.BIJZONDERE_STATUS;
        final NadereBijhoudingsaard expectedRob = NadereBijhoudingsaard.BIJZONDERE_STATUS;
        final String expected = String.format(STRING_FORMAT, expectedRob.getCode(), expectedRob.getNaam());
        final String resultRob = lo3StamtabelService.getRedenOpschorting(robCode.getWaarde());
        Assert.assertEquals(expected, resultRob);
    }

    @Test
    public void getRedenEindeRelatieTestOK() {
        final char rerCode = 'N';
        final RedenBeeindigingRelatie expectedRer = new RedenBeeindigingRelatie(rerCode, "Nietigverklaring");
        final String expected = String.format(STRING_FORMAT, expectedRer.getCode(), expectedRer.getOmschrijving());
        Mockito.doReturn(expectedRer).when(dynamischeStamtabelRepository).getRedenBeeindigingRelatieByCode(rerCode);

        final String resultRer = lo3StamtabelService.getRedenEindeRelatie(String.valueOf(rerCode));
        Assert.assertEquals(expected, resultRer);
    }

    /**
     * Verwacht dat dezelfde waarde wordt teruggegeven. Omdat deze niet bestaat kan er dus ook geen omschrijving worden
     * teruggegeven.
     */
    @Test
    public void getRedenEindeRelatieNotExist() {
        final char rerCode = '-';
        Mockito.doThrow(new IllegalArgumentException(MELDING_STRING)).when(dynamischeStamtabelRepository).getRedenBeeindigingRelatieByCode(rerCode);

        final String resultRer = lo3StamtabelService.getRedenEindeRelatie(String.valueOf(rerCode));
        Assert.assertEquals("-", resultRer);
    }

    @Test
    public void getRNIDeelnemerTestOK() {
        final Lo3RNIDeelnemerCode lo3RniDeelnemerCode = new Lo3RNIDeelnemerCode("0101");
        final BrpPartijCode brpPartijCode = new BrpPartijCode("250001");
        final Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> convTabel = Mockito.mock(Conversietabel.class);
        Mockito.when(conversietabellen.createRNIDeelnemerConversietabel()).thenReturn(convTabel);
        Mockito.when(convTabel.converteerNaarBrp(lo3RniDeelnemerCode)).thenReturn(brpPartijCode);

        final Partij partij = new Partij("Belastingdienst - Centrum voor ICT", "250001");
        final String expected = String.format(STRING_FORMAT, lo3RniDeelnemerCode.getWaarde(), partij.getNaam());
        Mockito.doReturn(partij).when(dynamischeStamtabelRepository).getPartijByCode(brpPartijCode.getWaarde());

        final String result = lo3StamtabelService.getRNIDeelnemer(lo3RniDeelnemerCode);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getRNIDeelnemerTestNotExist() {
        final Lo3RNIDeelnemerCode lo3RniDeelnemerCode = new Lo3RNIDeelnemerCode("1234");

        final Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> convTabel = Mockito.mock(Conversietabel.class);
        Mockito.when(conversietabellen.createRNIDeelnemerConversietabel()).thenReturn(convTabel);
        Mockito.when(convTabel.converteerNaarBrp(lo3RniDeelnemerCode)).thenReturn(null);

        final String result = lo3StamtabelService.getRNIDeelnemer(lo3RniDeelnemerCode);
        Assert.assertEquals(lo3RniDeelnemerCode.getWaarde(), result);
    }
}
