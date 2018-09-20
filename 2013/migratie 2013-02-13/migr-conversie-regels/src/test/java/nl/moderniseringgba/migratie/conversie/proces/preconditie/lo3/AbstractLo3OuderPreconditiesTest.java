/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Test;

/**
 * Preconditie tests voor algemene code ouder1/ouder2.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLo3OuderPreconditiesTest extends AbstractPreconditieTest {

    abstract Lo3Herkomst getHerkomst();

    abstract Lo3Herkomst getHerkomst(int voorkomen);

    abstract Lo3OuderPrecondities getPrecondities();

    Lo3OuderInhoud.Builder builder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.setaNummer(1069532945L);
        builder.setBurgerservicenummer(179543489L);

        builder.setVoornamen("Jaap");
        builder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("P"));
        builder.setVoorvoegselGeslachtsnaam("van");
        builder.setGeslachtsnaam("Joppen");

        builder.setGeboortedatum(new Lo3Datum(19940104));
        builder.setGeboorteGemeenteCode(new Lo3GemeenteCode("0514"));
        builder.setGeboorteLandCode(new Lo3LandCode("6030"));

        builder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("M"));

        builder.setFamilierechtelijkeBetrekking(new Lo3Datum(20010101));

        return builder;
    }

    @Test
    public void testBijzondereSituatieOnbekendeOuder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.setGeslachtsnaam(".");
        builder.setFamilierechtelijkeBetrekking(new Lo3Datum(20010101));
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), getHerkomst()));

        getPrecondities().controleerStapel(stapel);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB013);
    }

    @Test
    public void testBijzondereSituatieOnbekendeOuderGeen8510() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.setGeslachtsnaam(".");
        builder.setFamilierechtelijkeBetrekking(new Lo3Datum(20010101));
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                        Lo3StapelHelper.lo3His(null, null, 20120101), Lo3StapelHelper.lo3Akt(1), getHerkomst()));

        getPrecondities().controleerStapel(stapel);

        assertAantalErrors(1);
    }

    @Test
    public void testBijzondereSituatieOnbekendeOuderGeen8610GeenAkteWelDocument() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        builder.setGeslachtsnaam(".");
        builder.setFamilierechtelijkeBetrekking(new Lo3Datum(20010101));
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                        Lo3StapelHelper.lo3His(null, null, 20120101),
                        Lo3StapelHelper.lo3Akt(1l, "0518", 20120101, "inhoud"), getHerkomst()));

        getPrecondities().controleerStapel(stapel);
        assertAantalErrors(1);
    }

    @Test
    public void testBijzondereSituatieJuridischGeenOuder() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(), Lo3StapelHelper.lo3His(20000101),
                        Lo3StapelHelper.lo3Akt(1), getHerkomst()));

        getPrecondities().controleerStapel(stapel);

        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB014);
    }

    @Test
    public void testBijzondereSituatieJuridischGeenOuderGeen8510() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                        Lo3StapelHelper.lo3His(null, null, 20120101), Lo3StapelHelper.lo3Akt(1), getHerkomst()));

        getPrecondities().controleerStapel(stapel);
        assertAantalErrors(1);
    }

    @Test
    public void testBijzondereSituatieJuridischGeenOuderGeen8610GeenAkteWelDocument() {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder.build(),
                        Lo3StapelHelper.lo3His(null, null, 20120101),
                        Lo3StapelHelper.lo3Akt(1l, "0518", 20120101, "inhoud"), getHerkomst()));

        getPrecondities().controleerStapel(stapel);
        assertAantalErrors(1);
    }

    @Test
    public void testBijzondereSituatieZwakkeAdoptie() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, "1904", 20120101,
                                "akte (zwak) Haags adoptieverdrag"), getHerkomst()));
        getPrecondities().controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB021);
    }

    @Test
    public void testBijzondereSituatieZwakkeAdoptie2() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, "1904", 20120101,
                                "akte (zwak) Wet conflictenrecht adoptie"), getHerkomst()));
        getPrecondities().controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB021);
    }

    @Test
    public void testBijzondereSituatieGeenZwakkeAdoptie() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper
                                .lo3Documentatie(1L, null, null, "1904", 20120101, "Omschrijving van document"),
                        getHerkomst()));
        getPrecondities().controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    public void testBijzondereSituatieGeenZwakkeAdoptieAkte() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Akt(1), getHerkomst()));
        getPrecondities().controleerStapel(stapel);
        assertAantalInfos(0);
    }

    @Test
    public void testBijzondereSituatieZwakkeAdoptieHoofdLetters() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, "1904", 20120101,
                                "AKTE (ZWAK) WET CONFLICTENRECHT ADOPTIE"), getHerkomst()));
        getPrecondities().controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB021);
    }

    @Test
    public void testBijzondereSituatieZwakkeAdoptieKleineLetters() {
        final Lo3Stapel<Lo3OuderInhoud> stapel =
                Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(builder().build(), Lo3StapelHelper.lo3His(20120101),
                        Lo3StapelHelper.lo3Documentatie(1L, null, null, "1904", 20120101,
                                "akte (zwak) wet conflictenrecht adoptie"), getHerkomst()));
        getPrecondities().controleerStapel(stapel);
        assertAantalInfos(1);
        assertBijzondereSituatie(BijzondereSituaties.BIJZ_CONV_LB021);
    }
}
