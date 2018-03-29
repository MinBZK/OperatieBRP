/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractLoggingTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Test;

/**
 * Test voor het splitsen van verbintenissen.
 */
public class Lo3VerbintenisUtilTest extends AbstractLoggingTest {

    private static final Lo3SoortVerbintenis HUWELIJK = new Lo3SoortVerbintenis("H");
    private static final Lo3SoortVerbintenis PARTNERSCHAP = new Lo3SoortVerbintenis("P");
    private static final Lo3RedenOntbindingHuwelijkOfGpCode REDEN_OMZETTING = new Lo3RedenOntbindingHuwelijkOfGpCode("Z");
    private static final Lo3RedenOntbindingHuwelijkOfGpCode SCHEIDING = new Lo3RedenOntbindingHuwelijkOfGpCode("S");

    @Test
    public void happyFlow() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);

        final Lo3Historie historie1 = maaKHistorie(false, 20160101);
        final Lo3Historie historie2 = maaKHistorie(false, 20150101);

        final Lo3HuwelijkOfGpInhoud geslotenHuwelijk = maakHuwelijkOfGpSluiting(20160101, HUWELIJK);
        final Lo3HuwelijkOfGpInhoud geslotenPartnerschap = maakHuwelijkOfGpSluiting(20150101, PARTNERSCHAP);

        categorieen.add(new Lo3Categorie<>(geslotenPartnerschap, doc2, historie2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1)));
        categorieen.add(new Lo3Categorie<>(geslotenHuwelijk, doc1, historie1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0)));

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> geplitsteStapels = Lo3VerbintenisUtil.splitsEnZetVerbintenissenOm(new Lo3Stapel<>(categorieen));
        assertEquals(2, geplitsteStapels.size());
        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel : geplitsteStapels) {
            if (!stapel.bevatLo3ActueelVoorkomen()) {
                // Dit is de stapel van het partnerschap, controleren of deze nu een ontbindings rij heeft met reden Z
                assertEquals(2, stapel.getCategorieen().size());
                final Lo3Categorie<Lo3HuwelijkOfGpInhoud> ontbindingsRij = stapel.getCategorieen().get(0);
                final Lo3HuwelijkOfGpInhoud inhoud = ontbindingsRij.getInhoud();
                assertEquals(PARTNERSCHAP, inhoud.getSoortVerbintenis());
                assertEquals(REDEN_OMZETTING, inhoud.getRedenOntbindingHuwelijkOfGpCode());
                assertEquals(geslotenHuwelijk.getDatumSluitingHuwelijkOfAangaanGp(), inhoud.getDatumOntbindingHuwelijkOfGp());
                assertEquals(geslotenHuwelijk.getGemeenteCodeSluitingHuwelijkOfAangaanGp(), inhoud.getGemeenteCodeOntbindingHuwelijkOfGp());
                assertEquals(geslotenHuwelijk.getLandCodeSluitingHuwelijkOfAangaanGp(), inhoud.getLandCodeOntbindingHuwelijkOfGp());
            }
        }
    }

    @Test
    public void testBijzondereSituatieLb041() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);
        final Lo3Documentatie doc3 = maakDocumentatie(2);

        final Lo3Historie historie1 = maaKHistorie(false, 20160101);
        final Lo3Historie historie2 = maaKHistorie(false, 20150101);
        final Lo3Historie historie3 = maaKHistorie(false, 20150101);

        final Lo3Herkomst herkomstOntbinding = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1);

        final Lo3HuwelijkOfGpInhoud geslotenHuwelijk = maakHuwelijkOfGpSluiting(20160101, HUWELIJK);
        final Lo3HuwelijkOfGpInhoud ontbondenPartnerschap = maakHuwelijkOfGpOntbinding(20150101, SCHEIDING, PARTNERSCHAP);
        final Lo3HuwelijkOfGpInhoud geslotenPartnerschap = maakHuwelijkOfGpSluiting(20140101, PARTNERSCHAP);

        categorieen.add(new Lo3Categorie<>(geslotenPartnerschap, doc3, historie3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 2)));
        categorieen.add(new Lo3Categorie<>(ontbondenPartnerschap, doc2, historie2, herkomstOntbinding));
        categorieen.add(new Lo3Categorie<>(geslotenHuwelijk, doc1, historie1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0)));

        Lo3VerbintenisUtil.splitsEnControleerVerbintenissen(new Lo3Stapel<>(categorieen));

        final Set<LogRegel> regels = Logging.getLogging().getRegels();
        assertEquals(2, regels.size());
        boolean bevatLb041 = false;
        boolean bevatLb015 = false;
        for (final LogRegel regel : regels) {
            if (SoortMeldingCode.BIJZ_CONV_LB041.equals(regel.getSoortMeldingCode())) {
                bevatLb041 = true;
            }
            if (SoortMeldingCode.BIJZ_CONV_LB015.equals(regel.getSoortMeldingCode())) {
                bevatLb015 = true;
            }
        }
        assertTrue("Geen Lb015 gevonden", bevatLb015);
        assertTrue("Geen Lb041 gevonden", bevatLb041);
    }

    @Test
    public void testHuwelijkMetLegeRijInHistorie() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);

        final Lo3Historie historie1 = maaKHistorie(false, 20160101);
        final Lo3Historie historie2 = maaKHistorie(false, 20150101);

        final Lo3Herkomst herkomstOntbinding = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1);

        final Lo3HuwelijkOfGpInhoud geslotenHuwelijk = maakHuwelijkOfGpSluiting(20160101, HUWELIJK);
        // Technisch gezien geen lege rij, maar dit voldoet
        final Lo3HuwelijkOfGpInhoud leegHuwelijk = maakHuwelijkOfGpSluiting(20150101, null);

        categorieen.add(new Lo3Categorie<>(leegHuwelijk, doc2, historie2, herkomstOntbinding));
        categorieen.add(new Lo3Categorie<>(geslotenHuwelijk, doc1, historie1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0)));

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> geplitsteStapels = Lo3VerbintenisUtil.splitsEnZetVerbintenissenOm(new Lo3Stapel<>(categorieen));
        assertEquals(1, geplitsteStapels.size());
        assertEquals(2, geplitsteStapels.get(0).getCategorieen().size());
    }

    @Test
    public void testHuwelijkMetLegeRijAlsActueel() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);

        final Lo3Historie historie1 = maaKHistorie(false, 20160101);
        final Lo3Historie historie2 = maaKHistorie(false, 20150101);

        final Lo3Herkomst herkomstOntbinding = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1);

        // Technisch gezien geen lege rij, maar dit voldoet
        final Lo3HuwelijkOfGpInhoud leegHuwelijk = maakHuwelijkOfGpSluiting(20160101, null);
        final Lo3HuwelijkOfGpInhoud geslotenHuwelijk = maakHuwelijkOfGpSluiting(20150101, HUWELIJK);

        categorieen.add(new Lo3Categorie<>(geslotenHuwelijk, doc2, historie2, herkomstOntbinding));
        categorieen.add(new Lo3Categorie<>(leegHuwelijk, doc1, historie1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0)));

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> geplitsteStapels = Lo3VerbintenisUtil.splitsEnZetVerbintenissenOm(new Lo3Stapel<>(categorieen));
        assertEquals(1, geplitsteStapels.size());
        assertEquals(2, geplitsteStapels.get(0).getCategorieen().size());
    }

    @Test
    public void testPre074() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);

        final Lo3Historie historie1 = maaKHistorie(false, 20160101);
        final Lo3Historie historie2 = maaKHistorie(false, 20150101);

        final Lo3Herkomst herkomstOntbinding = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1);
        final Lo3Herkomst herkomstActueel = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0);

        // Technisch gezien geen lege rij, maar dit voldoet
        final Lo3HuwelijkOfGpInhoud anderHuwelijk = maakHuwelijkOfGpSluiting(20160101, HUWELIJK);
        final Lo3HuwelijkOfGpInhoud geslotenHuwelijk = maakHuwelijkOfGpSluiting(20150101, HUWELIJK);

        categorieen.add(new Lo3Categorie<>(geslotenHuwelijk, doc2, historie2, herkomstOntbinding));
        categorieen.add(new Lo3Categorie<>(anderHuwelijk, doc1, historie1, herkomstActueel));

        Lo3VerbintenisUtil.splitsEnControleerVerbintenissen(new Lo3Stapel<>(categorieen));
        final Set<LogRegel> regels = Logging.getLogging().getRegels();
        assertEquals(1, regels.size());
        final LogRegel regel = regels.iterator().next();
        assertEquals(SoortMeldingCode.PRE074, regel.getSoortMeldingCode());
        assertEquals(LogSeverity.INFO, regel.getSeverity());
        assertEquals(herkomstActueel, regel.getLo3Herkomst());
    }

    @Test
    public void testPre075() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);
        final Lo3Documentatie doc3 = maakDocumentatie(3);

        final Lo3Historie historie1 = maaKHistorie(false, 20160201);
        final Lo3Historie historie2 = maaKHistorie(false, 20160101);
        final Lo3Historie historie3 = maaKHistorie(false, 20150101);

        final Lo3Herkomst herkomstOntbinding = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1);
        final Lo3Herkomst herkomstActueel = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0);

        // Technisch gezien geen lege rij, maar dit voldoet
        final Lo3HuwelijkOfGpInhoud anderOntbondenHuwelijk = maakHuwelijkOfGpOntbinding(20160201, SCHEIDING, HUWELIJK);
        final Lo3HuwelijkOfGpInhoud ontbondenHuwelijk = maakHuwelijkOfGpOntbinding(20160101, SCHEIDING, HUWELIJK);
        final Lo3HuwelijkOfGpInhoud geslotenHuwelijk = maakHuwelijkOfGpSluiting(20150101, HUWELIJK);

        categorieen.add(new Lo3Categorie<>(geslotenHuwelijk, doc3, historie3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 2)));
        categorieen.add(new Lo3Categorie<>(ontbondenHuwelijk, doc2, historie2, herkomstOntbinding));
        categorieen.add(new Lo3Categorie<>(anderOntbondenHuwelijk, doc1, historie1, herkomstActueel));

        Lo3VerbintenisUtil.splitsEnControleerVerbintenissen(new Lo3Stapel<>(categorieen));
        final Set<LogRegel> regels = Logging.getLogging().getRegels();
        assertEquals(1, regels.size());
        final LogRegel regel = regels.iterator().next();
        assertEquals(SoortMeldingCode.PRE075, regel.getSoortMeldingCode());
        assertEquals(LogSeverity.INFO, regel.getSeverity());
        assertEquals(herkomstActueel, regel.getLo3Herkomst());
    }

    @Test
    public void testPre114() {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = new ArrayList<>();
        final Lo3Documentatie doc1 = maakDocumentatie(1);
        final Lo3Documentatie doc2 = maakDocumentatie(2);

        final Lo3Historie historie1 = maaKHistorie(false, 20160101);
        final Lo3Historie historie2 = maaKHistorie(false, 20150101);

        final Lo3Herkomst herkomstOntbinding = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, 0, 1);
        final Lo3Herkomst herkomstActueel = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0);

        // Technisch gezien geen lege rij, maar dit voldoet
        final Lo3HuwelijkOfGpInhoud ontbondenHuwelijk = maakHuwelijkOfGpOntbinding(20160101, SCHEIDING, HUWELIJK);
        final Lo3HuwelijkOfGpInhoud geslotenPartnerschap = maakHuwelijkOfGpSluiting(20150101, PARTNERSCHAP);

        categorieen.add(new Lo3Categorie<>(geslotenPartnerschap, doc2, historie2, herkomstOntbinding));
        categorieen.add(new Lo3Categorie<>(ontbondenHuwelijk, doc1, historie1, herkomstActueel));

        Lo3VerbintenisUtil.splitsEnControleerVerbintenissen(new Lo3Stapel<>(categorieen));
        final Set<LogRegel> regels = Logging.getLogging().getRegels();
        assertEquals(2, regels.size());
        boolean bevatPre114 = false;
        boolean bevatLb015 = false;
        for (final LogRegel regel : regels) {
            if (SoortMeldingCode.PRE114.equals(regel.getSoortMeldingCode())) {
                bevatPre114 = true;
            }
            if (SoortMeldingCode.BIJZ_CONV_LB015.equals(regel.getSoortMeldingCode())) {
                bevatLb015 = true;
            }
        }
        assertTrue("Geen Lb015 gevonden", bevatLb015);
        assertTrue("Geen PRE114 gevonden", bevatPre114);
    }

    private Lo3HuwelijkOfGpInhoud maakHuwelijkOfGpSluiting(final int datumSluiting, final Lo3SoortVerbintenis soortVerbintenis) {
        final Lo3HuwelijkOfGpInhoud.Builder builder = maakBasisHuwelijkOfGpInhoud();
        builder.datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(datumSluiting));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(new Lo3GemeenteCode("0518"));
        builder.landCodeSluitingHuwelijkOfAangaanGp(Lo3LandCode.NEDERLAND);
        builder.soortVerbintenis(soortVerbintenis);
        return builder.build();
    }

    private Lo3HuwelijkOfGpInhoud maakHuwelijkOfGpOntbinding(
            final int datumOntbinding,
            final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbinding,
            final Lo3SoortVerbintenis soortVerbintenis) {
        final Lo3HuwelijkOfGpInhoud.Builder builder = maakBasisHuwelijkOfGpInhoud();
        builder.datumOntbindingHuwelijkOfGp(new Lo3Datum(datumOntbinding));
        builder.gemeenteCodeOntbindingHuwelijkOfGp(new Lo3GemeenteCode("0518"));
        builder.landCodeOntbindingHuwelijkOfGp(Lo3LandCode.NEDERLAND);
        builder.redenOntbindingHuwelijkOfGpCode(redenOntbinding);
        builder.soortVerbintenis(soortVerbintenis);
        return builder.build();
    }

    private Lo3HuwelijkOfGpInhoud.Builder maakBasisHuwelijkOfGpInhoud() {
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();
        builder.aNummer(Lo3String.wrap("1234567890"));
        builder.burgerservicenummer(Lo3String.wrap("123456789"));
        builder.voornamen(Lo3String.wrap("Ben"));
        builder.geslachtsnaam(Lo3String.wrap("Getrouwd"));
        builder.geboortedatum(new Lo3Datum(19800101));
        builder.geboorteGemeenteCode(new Lo3GemeenteCode("1234"));
        builder.geboorteLandCode(Lo3LandCode.NEDERLAND);
        builder.geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.MAN.asElement());
        return builder;
    }

    private Lo3Documentatie maakDocumentatie(final long id) {
        return new Lo3Documentatie(id, new Lo3GemeenteCode("0518"), Lo3String.wrap("A" + id), null, null, null, null, null);
    }

    private Lo3Historie maaKHistorie(final boolean onjuist, final Integer ingangsdatum) {
        final Lo3IndicatieOnjuist indicatieOnjuist;
        if (onjuist) {
            indicatieOnjuist = Lo3IndicatieOnjuist.O;
        } else {
            indicatieOnjuist = null;
        }

        return new Lo3Historie(indicatieOnjuist, new Lo3Datum(ingangsdatum), new Lo3Datum(ingangsdatum + 1));
    }
}
