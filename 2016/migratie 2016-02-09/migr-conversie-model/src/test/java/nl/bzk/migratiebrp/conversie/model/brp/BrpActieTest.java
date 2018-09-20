/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie.Builder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.junit.Test;

public class BrpActieTest {
    public static BrpActie actie = new BrpActie(
        1000l,
        BrpSoortActieCode.CONVERSIE_GBA,
        BrpPartijCode.MINISTER,
            null,
        null,
        Collections.<BrpActieBron>emptyList(),
        1,
        null);
    public static BrpActie actie2 = new BrpActie(
        1001l,
        BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE,
        BrpPartijCode.MINISTER,
            null,
        null,
        Collections.<BrpActieBron>emptyList(),
        1,
        null);

    @Test(expected = NullPointerException.class)
    public void testConstructorIdentificatieNull() {
        new BrpActie(
            null,
            BrpSoortActieCode.CONVERSIE_GBA,
            BrpPartijCode.MINISTER,
                null,
            null,
            Collections.<BrpActieBron>emptyList(),
            1,
            null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorPartijNull() {
        new BrpActie(1000l, null, null, null, null, Collections.<BrpActieBron>emptyList(), 1, null);
    }

    @Test
    public void testGetDocumentStapelsViaActieBron() throws Exception {
        assertTrue(actie.getDocumentStapelsViaActieBron().isEmpty());
        BrpActie actie2 = createBrpActieMet2Bronnen();
        assertEquals(2, actie2.getDocumentStapelsViaActieBron().size());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(actie.equals(getActie(false)));
        assertFalse(actie.equals(getActie(true)));
        assertFalse(actie.equals(actie2));
    }

    @Test
    public void testIsConversieActie() throws Exception {
        BrpActie ac =
                new BrpActie(
                    1000l,
                    BrpSoortActieCode.CORRECTIE_ADRES,
                    BrpPartijCode.MINISTER,
                        null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
        assertFalse(ac.isConversieActie());
        ac =
                new BrpActie(
                    1000l,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.MINISTER,
                        null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
        assertTrue(ac.isConversieActie());
        ac =
                new BrpActie(
                    1000l,
                    BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST,
                    BrpPartijCode.MINISTER,
                        null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
        assertTrue(ac.isConversieActie());
        ac =
                new BrpActie(
                    1000l,
                    BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE,
                    BrpPartijCode.MINISTER,
                        null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
        assertTrue(ac.isConversieActie());
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder() {
        final Builder builder = new Builder();
        builder.build();
    }

    @Test
    public void testBuilderOk() {
        final Builder builder = new Builder();
        builder.id(1002L);
        builder.soortActieCode(BrpSoortActieCode.CONVERSIE_GBA);
        BrpDatumTijd registratie = new BrpDatumTijd(Calendar.getInstance().getTime(), null);
        builder.datumTijdRegistratie(registratie);
        builder.actieBronnen(getBrpActieBronnen());
        builder.datumOntlening(new BrpDatum(20010101, null));
        builder.lo3Herkomst(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        builder.partijCode(BrpPartijCode.MINISTER);
        builder.sortering(3);
        BrpActie result = builder.build();
        assertEquals(2, result.getActieBronnen().size());
        assertEquals("Conversie GBA", result.getSoortActieCode().getWaarde());
        assertEquals("01", result.getLo3Herkomst().getCategorie().getCategorie());
        assertEquals(20010101, result.getDatumOntlening().getWaarde().intValue());
        assertEquals(registratie.getWaarde(), result.getDatumTijdRegistratie().getWaarde());
        assertEquals(1002, result.getId().intValue());
        assertEquals(3, result.getSortering());
        assertEquals(199901, result.getPartijCode().getWaarde().intValue());
    }

    private BrpActie getActie(boolean returnNull) {
        if (returnNull) {
            return null;
        }
        return actie;
    }

    private BrpActie createBrpActieMet2Bronnen() {
        List<BrpActieBron> bronnen = getBrpActieBronnen();
        return new BrpActie(1000l, null, BrpPartijCode.MINISTER, null, null, bronnen, 1, null);
    }

    private List<BrpActieBron> getBrpActieBronnen() {
        List<BrpActieBron> bronnen = new ArrayList<>();
        List<BrpGroep<BrpDocumentInhoud>> groepen = new ArrayList<>();

        BrpHistorie historie = new BrpHistorie(new BrpDatumTijd(Calendar.getInstance().getTime(), null), null, null);
        BrpDocumentInhoud docInhoud = BrpDocumentInhoudTest.createInhoud();
        groepen.add(new BrpGroep<>(docInhoud, historie, null, null, null));
        BrpStapel<BrpDocumentInhoud> docStapel = new BrpStapel<>(groepen);
        bronnen.add(new BrpActieBron(docStapel, new BrpString("een")));
        bronnen.add(new BrpActieBron(docStapel, new BrpString("twee")));
        return bronnen;
    }

}
