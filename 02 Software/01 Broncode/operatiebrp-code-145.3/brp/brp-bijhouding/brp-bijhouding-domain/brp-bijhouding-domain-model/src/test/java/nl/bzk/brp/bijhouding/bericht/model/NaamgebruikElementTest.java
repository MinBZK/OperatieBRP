/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 * Unittest voor {@link NaamgebruikElementTest}.
 */
public class NaamgebruikElementTest extends AbstractNaamTest {

    private static final StringElement NAAMGEBRUIK_CODE_EIGEN = new StringElement("E");

    @Test
    public void testOngeldigVoorvoegselScheidingstekenPaar() {
        test(
            BooleanElement.NEE,
            NAAMGEBRUIK_CODE_EIGEN,
            VOORNAMEN_ELEMENT,
            GESLACHTSNAAMSTAM_ELEMENT,
            null,
            null,
            SCHEIDINGSTEKEN_ELEMENT,
            new StringElement("bla"),
            Regel.R2160);
    }

    @Test
    public void testGeldigVoorvoegselScheidingstekenPaar() {
        test(
            BooleanElement.NEE,
            NAAMGEBRUIK_CODE_EIGEN,
            VOORNAMEN_ELEMENT,
            GESLACHTSNAAMSTAM_ELEMENT,
            null,
            null,
            SCHEIDINGSTEKEN_ELEMENT,
            VOORVOEGSEL_ELEMENT);
    }

    @Test
    public void valideerAdelijkeTitelGeldig() {
        test(BooleanElement.NEE, NAAMGEBRUIK_CODE_EIGEN, VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, ADELIJKE_TITEL_ELEMENT, null, null, null);
    }

    @Test
    public void valideerPredicaatGeldig() {
        test(BooleanElement.NEE, NAAMGEBRUIK_CODE_EIGEN, VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, null, PREDICAAT_ELEMENT, null, null);
    }

    @Test
    public void valideerPredicaatEnAdelijkeTitelBeideGevuldOngeldig() {
        test(
            BooleanElement.NEE,
            NAAMGEBRUIK_CODE_EIGEN,
            VOORNAMEN_ELEMENT,
            GESLACHTSNAAMSTAM_ELEMENT,
            ADELIJKE_TITEL_ELEMENT,
            PREDICAAT_ELEMENT,
            null,
            null,
            Regel.R2162);
    }

    @Test
    public void testNaamgebruikCode() {
        test(BooleanElement.NEE, NAAMGEBRUIK_CODE_EIGEN, VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, null);
        test(BooleanElement.NEE, new StringElement("P"), VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, null);
        test(BooleanElement.NEE, new StringElement("N"), VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, null);
        test(BooleanElement.NEE, new StringElement("V"), VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, null);
        test(BooleanElement.NEE, new StringElement("G"), VOORNAMEN_ELEMENT, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, null, Regel.R1681);
    }

    @Test
    public void testAfgeleidEnVeldenGevuld() {
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, null, null, null, null, null, null);
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, VOORNAMEN_ELEMENT, null, null, null, null, null, Regel.R1677);
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, null, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, null, Regel.R1677);
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, null, null, ADELIJKE_TITEL_ELEMENT, null, null, null, Regel.R1677);
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, null, null, null, PREDICAAT_ELEMENT, null, null, Regel.R1677);
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, null, null, null, null, SCHEIDINGSTEKEN_ELEMENT, VOORVOEGSEL_ELEMENT, Regel.R1677);
        test(BooleanElement.JA, NAAMGEBRUIK_CODE_EIGEN, null, null, null, null, SCHEIDINGSTEKEN_ELEMENT, VOORVOEGSEL_ELEMENT, Regel.R1677);
    }

    @Test
    public void testNietAfgeleidGeslachtsnaamNietIngevuld() {
        test(BooleanElement.NEE, NAAMGEBRUIK_CODE_EIGEN, null, null, null, null, null, null, Regel.R1680);
    }

    @Test
    public void testWijzigenNaamgebruikAfgeleid() {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);

        final BRPActie brpActie = mock(BRPActie.class);
        when(brpActie.getDatumTijdRegistratie()).thenReturn(Timestamp.from(Instant.now()));

        final NaamgebruikElement naamgebruikElement =
                new NaamgebruikElement(Collections.emptyMap(), new StringElement("E"), BooleanElement.JA, null, null, null, null, null, new StringElement(
                    "Stam"));
        persoon.wijzigPersoonNaamgebruikHistorie(brpActie, naamgebruikElement, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();
        assertEquals(samengesteldeNaamHistorie.getGeslachtsnaamstam(), naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
    }

    @Test
    public void testWijzigenNaamgebruikNietAfgeleid() {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));

        final BRPActie brpActie = mock(BRPActie.class);
        when(brpActie.getDatumTijdRegistratie()).thenReturn(Timestamp.from(Instant.now()));

        final String voorvoegsel = "van";
        final Character scheidingsteken = ' ';
        final String geslachtsnaam = "Stam";
        final String voornamen = "Jaap";
        final String predicaatCode = "H";
        final String adellijkeTitelCode = "B";
        final NaamgebruikElement naamgebruikElement =
                new NaamgebruikElement(
                    Collections.emptyMap(),
                    new StringElement("E"),
                    BooleanElement.NEE,
                    new StringElement(predicaatCode),
                    new StringElement(voornamen),
                    new StringElement(adellijkeTitelCode),
                    new StringElement(voorvoegsel),
                    new CharacterElement(scheidingsteken),
                    new StringElement(geslachtsnaam));

        persoon.wijzigPersoonNaamgebruikHistorie(brpActie, naamgebruikElement, false);
        assertEquals(1, persoon.getPersoonNaamgebruikHistorieSet().size());
        final PersoonNaamgebruikHistorie naamgebruikHistorie = persoon.getPersoonNaamgebruikHistorieSet().iterator().next();
        assertFalse(naamgebruikHistorie.getIndicatieNaamgebruikAfgeleid());
        assertEquals(voorvoegsel, naamgebruikHistorie.getVoorvoegselNaamgebruik());
        assertEquals(scheidingsteken, naamgebruikHistorie.getScheidingstekenNaamgebruik());
        assertEquals(geslachtsnaam, naamgebruikHistorie.getGeslachtsnaamstamNaamgebruik());
        assertEquals(voornamen, naamgebruikHistorie.getVoornamenNaamgebruik());
        assertEquals(predicaatCode, naamgebruikHistorie.getPredicaat().getCode());
        assertEquals(adellijkeTitelCode, naamgebruikHistorie.getAdellijkeTitel().getCode());
    }

    @Test
    public void valideerVoorvoegselOfScheidingstekenLeeg() throws Exception {
        test(BooleanElement.NEE, NAAMGEBRUIK_CODE_EIGEN, null, GESLACHTSNAAMSTAM_ELEMENT, null, null, SCHEIDINGSTEKEN_ELEMENT, null, Regel.R2229);
        test(BooleanElement.NEE, NAAMGEBRUIK_CODE_EIGEN, null, GESLACHTSNAAMSTAM_ELEMENT, null, null, null, VOORVOEGSEL_ELEMENT, Regel.R2229);
    }

    private void test(
        final BooleanElement indicatieAfgeleid,
        final StringElement naamgebruikCode,
        final StringElement voornamen,
        final StringElement geslachtsnaamstam,
        final StringElement adelijkeTitel,
        final StringElement predicaat,
        final CharacterElement scheidingsteken,
        final StringElement voorvoegsel,
        final Regel... regels)
    {
        final NaamgebruikElement element =
                new NaamgebruikElement(
                    new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").build(),
                    naamgebruikCode,
                    indicatieAfgeleid,
                    predicaat,
                    voornamen,
                    adelijkeTitel,
                    voorvoegsel,
                    scheidingsteken,
                    geslachtsnaamstam);
        controleerRegels(element.valideer(), regels);
    }
}
