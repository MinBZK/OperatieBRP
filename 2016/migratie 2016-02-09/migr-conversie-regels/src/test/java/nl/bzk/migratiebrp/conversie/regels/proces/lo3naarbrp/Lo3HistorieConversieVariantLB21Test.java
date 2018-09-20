/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Test;

public class Lo3HistorieConversieVariantLB21Test extends AbstractLo3HistorieConversieVariantTest {

    private static final String OMSCHRIJVING_VERDRAG = "Omschrijving verdrag";
    @Inject
    @Named("lo3HistorieConversieVariantLB21")
    private Lo3HistorieConversieVariantLB21 conversie;

    @Test
    public void testCasus_LB21_1() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 0, 20000202);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 0, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB21_2() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000202);
        maakLo3Groep(1, null, 0, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 0, 20000101, 20000102010000L, null);
    }

    @Test
    public void testCasus_LB21_3() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 0, 20000202);
        maakLo3Groep(1, "O", 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 0, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB21_4() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, null, 0, 20000202);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(2, 2, 20000101, 20010101, 20000102010000L, null);
        valideerBrpGroep(1, 1, 0, 20000101, 20000202010000L, null);
    }

    @Test
    public void testCasus_LB21_5() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000102010100L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB21_6() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(1, 1, 20000101, 20010101, 20000102010100L, null);
        valideerBrpGroep(2, 2, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB21_7() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, null, 20000101, 20000103);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(1, 2, 20000101, 20010101, 20000103010000L, null);
        valideerBrpGroep(2, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB21_8() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000202);
        maakLo3Groep(1, null, 20010101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 20010101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB21_9() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, "O", 20000101, 20000103);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000103010000L, 20000103010000L);
        valideerBrpGroep(2, 2, 20000101, 20010101, 20000102010000L, null);
    }

    @Test
    public void testLegeGroepVooraan() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000102, 20000102);
        legeLo3Groep(1, null, 20000101, 20000101);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000102, null, 20000102010000L, null);
    }

    /* Test eigenlijk van de abstract superclass: Lo3HistorieConversieVariant.maakActie */
    @Test
    public void testRniDeelnemer() {

        final Lo3Documentatie doc =
                new Lo3Documentatie(1L, null, null, null, null, null, Lo3RNIDeelnemerCode.STANDAARD, Lo3String.wrap(OMSCHRIJVING_VERDRAG));
        final Lo3Historie his = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19980202));
        final Lo3Herkomst her = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 2, 3);

        final Map<Long, BrpActie> actieCache = new HashMap<>();

        conversie.maakActie(doc, his, her, actieCache, BrpSoortActieCode.CONVERSIE_GBA);

        assertEquals(1, actieCache.size());
        final BrpActie actie = actieCache.get(1L); // op key

        assertEquals(new BrpPartijCode(0), actie.getPartijCode());

        assertEquals(1, actie.getActieBronnen().size());
        final BrpActieBron actieBron1 = actie.getActieBronnen().get(0);

        assertEquals(new BrpString(OMSCHRIJVING_VERDRAG, null), actieBron1.getRechtsgrondOmschrijving());
    }

    /* Test eigenlijk van de abstract superclass: Lo3HistorieConversieVariant.maakActie */
    @Test
    public void testRniDeelnemerPlusDocument() {

        final Lo3Documentatie doc =
                new Lo3Documentatie(
                    1L,
                    new Lo3GemeenteCode("1234"),
                    Lo3String.wrap("AB1234"),
                    new Lo3GemeenteCode("1234"),
                    new Lo3Datum(19970202),
                    Lo3String.wrap("Omschrijving document"),
                    Lo3RNIDeelnemerCode.STANDAARD,
                    Lo3String.wrap(OMSCHRIJVING_VERDRAG));
        final Lo3Historie his = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19980202));
        final Lo3Herkomst her = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 2, 3);

        final Map<Long, BrpActie> actieCache = new HashMap<>();

        conversie.maakActie(doc, his, her, actieCache, BrpSoortActieCode.CONVERSIE_GBA);

        assertEquals(1, actieCache.size());
        final BrpActie actie = actieCache.get(1L); // op key

        assertEquals(new BrpPartijCode(0), actie.getPartijCode());

        assertEquals(2, actie.getActieBronnen().size());
        final BrpActieBron actieBron1 = actie.getActieBronnen().get(0);

        assertNull(actieBron1.getDocumentStapel());
        assertEquals(new BrpString(OMSCHRIJVING_VERDRAG, null), actieBron1.getRechtsgrondOmschrijving());

        final BrpActieBron actieBron2 = actie.getActieBronnen().get(1);

        assertNotNull(actieBron2.getDocumentStapel());
        assertEquals(1, actieBron2.getDocumentStapel().size());
        assertEquals(new BrpString("AB1234", null), actieBron2.getDocumentStapel().get(0).getInhoud().getAktenummer());
        assertEquals(BrpDatumTijd.fromDatumTijdMillis(19980202010000000L, null), actieBron2.getDocumentStapel()
                                                                                           .get(0)
                                                                                           .getHistorie()
                                                                                           .getDatumTijdRegistratie());
    }

    private void converteer() {
        setResultaat(conversie.converteer(getInvoer(), new HashMap<Long, BrpActie>()));
        Collections.reverse(getResultaat());
    }
}
