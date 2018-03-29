/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.deelnameEuVerkiezingen;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.doc;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.uitsluiting;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Doc;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Kiesrecht;
import static org.mockito.Mockito.when;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpKiesrechtConverteerderTest {

    private static final String DOC = "Euro-Doc";
    private static final int DATUM_TIJD_REGISTRATIE = 2004_01_02;
    private static final int DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT = 2009_01_01;
    private static final int DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN = 2004_01_01;
    private static final int DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN = 2010_01_01;
    private static final String BRP_PARTIJ_CODE = "059901";
    private static final String LO3_GEMEENTE_CODE = "0599";
    @Mock
    private BrpAttribuutConverteerder attribuutConverteerder;
    private BrpKiesrechtConverteerder subject;


    @Before
    public void setUp() {
        subject = new BrpKiesrechtConverteerder(attribuutConverteerder);
        // Uitsluiting Kiesrecht
        when(attribuutConverteerder.converteerAanduidingUitgeslotenKiesrecht(new BrpBoolean(true)))
                .thenReturn(Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.asElement());
        when(attribuutConverteerder.converteerDatum(new BrpDatum(DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT, null)))
                .thenReturn(new Lo3Datum(DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT));

        // Deelname EU Verkiezing
        when(attribuutConverteerder.converteerAanduidingEuropeesKiesrecht(new BrpBoolean(true)))
                .thenReturn(Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.asElement());
        when(attribuutConverteerder.converteerDatum(new BrpDatum(DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN, null)))
                .thenReturn(new Lo3Datum(DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN));
        when(attribuutConverteerder.converteerDatum(new BrpDatum(DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN, null)))
                .thenReturn(new Lo3Datum(DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN));

        // Documentatie
        final BrpPartijCode brpPartijCode = new BrpPartijCode(BRP_PARTIJ_CODE);
        when(attribuutConverteerder.valideerRNIDeelnemerTegenBrp(brpPartijCode)).thenReturn(false);
        when(attribuutConverteerder.converteerGemeenteCode(brpPartijCode)).thenReturn(new Lo3GemeenteCode(LO3_GEMEENTE_CODE));
        when(attribuutConverteerder.converteerDatum(new BrpDatum(DATUM_TIJD_REGISTRATIE, null))).thenReturn(new Lo3Datum(DATUM_TIJD_REGISTRATIE));
        when(attribuutConverteerder.converteerString(new BrpString(DOC))).thenReturn(new Lo3String(DOC));

    }


    @Test
    public void test() {
        final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingen =
                stapel(groep(
                        deelnameEuVerkiezingen(true, DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN, DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN),
                        his(DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN),
                        act(7, DATUM_TIJD_REGISTRATIE, doc(DOC, BRP_PARTIJ_CODE))));

        final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel =
                stapel(groep(uitsluiting(true, DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT), his(DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT),
                        act(3, 2009_01_02)));

        // Execute

        final Lo3Stapel<Lo3KiesrechtInhoud> result = subject.converteer(deelnameEuVerkiezingen, uitsluitingKiesrechtStapel);

        final Lo3KiesrechtInhoud expected = lo3Kiesrecht(true, DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN,
                DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN, true, DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, LO3_GEMEENTE_CODE, DATUM_TIJD_REGISTRATIE, DOC);

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }

    @Test

    public void testUitsluitingOnly() {
        final int datumTijdRegistratie = 2004_01_02;
        final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel =
                stapel(groep(uitsluiting(true, DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT), his(DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT),
                        act(3, datumTijdRegistratie, doc(DOC, BRP_PARTIJ_CODE))));

        // Execute

        final Lo3Stapel<Lo3KiesrechtInhoud> result = subject.converteer(uitsluitingKiesrechtStapel);

        final Lo3KiesrechtInhoud expected = lo3Kiesrecht(null, null, null, true, DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", datumTijdRegistratie, DOC);

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }
}
