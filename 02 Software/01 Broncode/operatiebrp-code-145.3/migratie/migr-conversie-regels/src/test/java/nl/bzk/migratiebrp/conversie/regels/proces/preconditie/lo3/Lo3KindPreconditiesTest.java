/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.conversie.regels.tabel.GemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.LandConversietabel;
import nl.bzk.migratiebrp.conversie.regels.tabel.VoorvoegselScheidingstekenConversietabel;
import org.junit.Test;

/**
 * Precondities tests voor categorie 09: kind.
 */
public class Lo3KindPreconditiesTest extends AbstractPreconditieTest {

    private static final String DOC = "Doc";
    private static final String GEM_CODE = "0514";
    private static final Lo3Herkomst LO3_HERKOMST_KIND = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 0, 0);
    private static final Lo3Herkomst LO3_HERKOMST_KIND_1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 0, 1);
    private static final Lo3Herkomst LO3_HERKOMST_KIND_2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 0, 2);
    private static final Lo3String PERSOON_ANUMMER = new Lo3String("1234567890");

    private Lo3KindPrecondities precondities = new Lo3KindPrecondities(new ConversietabelFactoryImpl());

    private Lo3KindInhoud.Builder builder() {
        final Lo3KindInhoud.Builder builder = new Lo3KindInhoud.Builder();
        builder.aNummer(Lo3String.wrap("1069532945"));
        builder.burgerservicenummer(Lo3String.wrap("179543489"));

        builder.voornamen(Lo3String.wrap("Jaap"));
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap("van"));
        builder.geslachtsnaam(Lo3String.wrap("Joppen"));

        builder.geboortedatum(new Lo3Datum(19940104));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode(GEM_CODE));
        builder.geboorteLandCode(new Lo3LandCode("6030"));

        return builder;
    }

    @Test
    public void testHappy() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testPreconditie073ZelfdeAnr() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        final List<Lo3Stapel<Lo3KindInhoud>> stapels = new ArrayList<>();
        stapels.add(stapel);
        stapels.add(stapel);
        precondities.controleerStapels(stapels, PERSOON_ANUMMER);

        assertSoortMeldingCode(SoortMeldingCode.PRE073, 1);
        assertAantalErrors(1);
    }

    @Test
    public void testPreconditie073VerschillendeAnrs() {
        final List<Lo3Stapel<Lo3KindInhoud>> stapels = new ArrayList<>();
        final Lo3Stapel<Lo3KindInhoud> stapel1 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));
        stapels.add(stapel1);
        final Lo3KindInhoud.Builder stapel2Builder = builder();
        stapel2Builder.aNummer(Lo3String.wrap("1868196961"));
        final Lo3Stapel<Lo3KindInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        stapel2Builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));
        stapels.add(stapel2);
        precondities.controleerStapels(stapels, PERSOON_ANUMMER);

        assertAantalErrors(0);
    }

    @Test
    public void testContr101() {
        final Lo3Stapel<Lo3KindInhoud> stapelBeeindigingOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND_1), Lo3StapelHelper.lo3Cat(
                        new Lo3KindInhoud.Builder().build(),
                        Lo3StapelHelper.lo3Doc(2L, GEM_CODE, 20010101, DOC),
                        Lo3StapelHelper.lo3His(20010101),
                        LO3_HERKOMST_KIND));
        precondities.controleerStapels(Collections.singletonList(stapelBeeindigingOk), PERSOON_ANUMMER);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3KindInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        new Lo3KindInhoud.Builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);

        assertAantalWarnings(0);
        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE050, 1);
    }

    @Test
    public void testContr104() {
        Lo3Categorie<Lo3KindInhoud> onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_KIND_2);
        final Lo3Categorie<Lo3KindInhoud> onjuist2 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3Akt(2), Lo3StapelHelper.lo3His("O", 19990101, 20000101), LO3_HERKOMST_KIND_1);
        final Lo3Categorie<Lo3KindInhoud> juist3 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3Akt(3), Lo3StapelHelper.lo3His(null, 19990101, 20000101), LO3_HERKOMST_KIND);

        final Lo3Stapel<Lo3KindInhoud> stapelOk = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2, juist3);

        precondities.controleerStapels(Collections.singletonList(stapelOk), PERSOON_ANUMMER);
        assertAantalErrors(0);

        onjuist1 =
                Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His("O", 19990101, 19990101), LO3_HERKOMST_KIND);

        final Lo3Stapel<Lo3KindInhoud> stapelNok = Lo3StapelHelper.lo3Stapel(onjuist1, onjuist2);

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    @Test
    public void testContr1091() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geboorteGemeenteCode(new Lo3GemeenteCode("Brussel"));
        builder.geboorteLandCode(new Lo3LandCode("5010"));

        final Lo3Stapel<Lo3KindInhoud> stapelBuitenlandsOk =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapelBuitenlandsOk), PERSOON_ANUMMER);

        assertAantalErrors(0);

        builder.geboorteLandCode(new Lo3LandCode("6030"));

        final Lo3Stapel<Lo3KindInhoud> stapelNok =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapelNok), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE025, 1);
    }

    @Test
    public void testContr110() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 20000101, 20000100),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr112() {
        final Lo3Stapel<Lo3KindInhoud> stapel2 =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 0, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel2), PERSOON_ANUMMER);

        assertAantalErrors(0);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000100, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE067, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr252() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), null, Lo3StapelHelper.lo3His(20000101), LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(0);
    }

    @Test
    public void testContr253() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, null, 20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE030, 1);
    }

    @Test
    public void testContr254() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 20000101, null),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE031, 1);
    }

    @Test
    public void testContr255() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.voornamen(null);
        builder.adellijkeTitelPredikaatCode(null);
        builder.voorvoegselGeslachtsnaam(null);
        builder.geslachtsnaam(null);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);
        assertSoortMeldingCode(SoortMeldingCode.PRE048, 1);
    }

    @Test
    public void testContr257() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His("O", 20010101, 20000101),
                        LO3_HERKOMST_KIND));
        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        // assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE055, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr301() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.aNummer(null);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalInfos(1);
    }

    @Test
    public void testContr303() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geslachtsnaam(null);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE064, 1);
    }

    @Test
    public void testContr304() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geboortedatum(null);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    @Test
    public void testContr306() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geboortedatum(null);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE007, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr4011() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geboortedatum(new Lo3Datum(20031424));

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40121() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20050155, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40124() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 20040141, 20010101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr40125() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(null, 20010101, 20040141),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
    }

    @Test
    public void testContr4021() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geboorteGemeenteCode(GemeenteConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE025, 1);
    }

    @Test
    public void testContr4031() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.geboorteLandCode(LandConversietabel.LO3_NIET_VALIDE_UITZONDERING);

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE008, 1);
    }

    @Test
    public void testContr4063() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), "1-x0001", null, null, null),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr4064() {
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, LandConversietabel.LO3_NIET_VALIDE_UITZONDERING.getWaarde(), 20010101, DOC),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    @Test
    public void testContr411() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.adellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("QQ"));

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testContr412() {
        final Lo3KindInhoud.Builder builder = builder();
        builder.voorvoegselGeslachtsnaam(Lo3String.wrap(VoorvoegselScheidingstekenConversietabel.LO3_NIET_VALIDE_UITZONDERING));

        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder.build(),
                        Lo3StapelHelper.lo3Akt(1),
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE054, 1);
    }

    @Test
    public void testControleerGroep83Procedure8310NietGevuld() {
        final Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(null, new Lo3Datum(20140101), null);
        final Lo3Stapel<Lo3KindInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                        builder().build(),
                        Lo3StapelHelper.lo3Doc(1L, GEM_CODE, 20000101, DOC),
                        lo3Onderzoek,
                        Lo3StapelHelper.lo3His(20000101),
                        LO3_HERKOMST_KIND));

        precondities.controleerStapels(Collections.singletonList(stapel), PERSOON_ANUMMER);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.PRE099, 1);
    }
}
