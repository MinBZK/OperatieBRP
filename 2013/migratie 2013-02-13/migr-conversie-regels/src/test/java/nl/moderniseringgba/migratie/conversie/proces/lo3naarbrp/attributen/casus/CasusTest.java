/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.proces.AbstractConversieServiceTest;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Super class voor de CasusTesten. Door in deze class de maak*() methoden te overriden kan voor de test de gewenste
 * stapel worden gemaakt, terwijl niet telkens alle verplichte stapels hoeven worden gedefinieerd.
 * 
 */

public abstract class CasusTest extends AbstractConversieServiceTest {

    public enum Document {
        AKTE, DOCUMENT;
    }

    protected static final String RDAM = "1234";
    protected static final String ADAM = "4321";

    protected static final Lo3SoortVerbintenis H = Lo3SoortVerbintenisEnum.HUWELIJK.asElement();
    protected static final Lo3SoortVerbintenis P = Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.asElement();

    protected static final Lo3IndicatieOnjuist ONJUIST = Lo3IndicatieOnjuistEnum.ONJUIST.asElement();

    private static final Comparator<BrpGroep<?>> BRP_GROEP_COMPARATOR = new BrpGroepComparator();

    private static long brpActieIdTeller;

    private Lo3Persoonslijst lo3Persoonslijst;

    @Before
    public void setup() {
        lo3Persoonslijst = maakPersoonslijst();
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    @Test
    public void testMaakPersoonslijst() {
        assertNotNull(lo3Persoonslijst.getPersoonStapel());
        assertNotNull(lo3Persoonslijst.getInschrijvingStapel());
        assertNotNull(lo3Persoonslijst.getVerblijfplaatsStapel());
    }

    /**
     * Test de conversie van LO3 naar BRP
     * 
     * @throws Exception
     */
    public abstract void testLo3NaarBrp() throws Exception;

    /**
     * Converteer de LO3 persoonslijst eerst naar een Brp persoonslijst en converteer deze terug naar LO3
     * 
     * @throws Exception
     */
    public abstract void testRondverteer() throws Exception;

    protected Lo3Persoonslijst maakPersoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.inschrijvingStapel(maakInschrijvingStapel());
        builder.verblijfplaatsStapel(maakVerblijfplaatsStapel());
        builder.persoonStapel(maakPersoonStapel());
        builder.nationaliteitStapel(maakNationaliteitStapel());
        builder.huwelijkOfGpStapel(maakHuwelijkStapel());
        builder.verblijfstitelStapel(maakVerblijfstitelStapel());
        builder.ouder1Stapel(maakOuder1Stapel());
        builder.ouder2Stapel(maakOuder2Stapel());
        return builder.build();
    }

    protected Lo3OuderInhoud buildOuder(final long aNummer, final int datumFamilie) {
        return new Lo3OuderInhoud(aNummer, aNummer / 10, "Ouder", null, null, "Ouderson", new Lo3Datum(19030101),
                new Lo3GemeenteCode("0518"), new Lo3LandCode("6030"),
                Lo3GeslachtsaanduidingEnum.ONBEKEND.asElement(), new Lo3Datum(datumFamilie));
    }

    protected Lo3Stapel<Lo3OuderInhoud> maakOuder1Stapel() {
        return VerplichteStapel.createOuderStapel(1111111111L);
    }

    protected Lo3Stapel<Lo3OuderInhoud> maakOuder2Stapel() {
        return VerplichteStapel.createOuderStapel(2222222222L);
    }

    protected Lo3Stapel<Lo3InschrijvingInhoud> maakInschrijvingStapel() {
        return VerplichteStapel.createInschrijvingStapel();
    }

    protected Lo3Stapel<Lo3VerblijfplaatsInhoud> maakVerblijfplaatsStapel() {
        return VerplichteStapel.createVerblijfplaatsStapel();
    }

    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return VerplichteStapel.createPersoonStapel();
    }

    protected Lo3PersoonInhoud buildPersoonMetNaamEnGeboorte(
            final String voornaam,
            final String geslachtsnaam,
            final Integer geboorteDatum,
            final String geboorteGemeente) {
        return new Lo3PersoonInhoud(1234567890L, 123456789L, voornaam, null, null, geslachtsnaam, new Lo3Datum(
                geboorteDatum), new Lo3GemeenteCode(geboorteGemeente), Lo3LandCode.NEDERLAND,
                Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement(), null, null);
    }

    protected Lo3PersoonInhoud buildPersoonMetNamen(final String voornaam, final String geslachtsnaam) {
        return buildPersoonMetNaamEnGeboorte(voornaam, geslachtsnaam, 19800101, "1234");
    }

    protected Lo3PersoonInhoud buildPersoonMetVoornaam(final String voornaam) {
        return buildPersoonMetNamen(voornaam, "Jansen");
    }

    protected Lo3HuwelijkOfGpInhoud buildHuwelijk(final Integer datumSluiting, final Integer datumOntbinding) {
        // aanroep met default gemeente codes
        return buildHuwelijk(datumSluiting, "1234", datumOntbinding, "1234");
    }

    protected Lo3HuwelijkOfGpInhoud buildHuwelijk(
            final Integer datumSluiting,
            final String gemeenteSluitingCode,
            final Integer datumOntbinding,
            final String gemeenteOntbindingCode) {
        return buildHuwelijk(datumSluiting, gemeenteSluitingCode, datumOntbinding, gemeenteOntbindingCode,
                Lo3SoortVerbintenisEnum.HUWELIJK.asElement());
    }

    protected Lo3HuwelijkOfGpInhoud buildHuwelijk(
            final Integer datumSluiting,
            final String gemeenteSluitingCode,
            final Integer datumOntbinding,
            final String gemeenteOntbindingCode,
            final Lo3SoortVerbintenis soort) {
        final boolean isOntbonden = datumOntbinding != null;
        final boolean isGesloten = datumSluiting != null;
        final Lo3HuwelijkOfGpInhoud inhoud;
        if (!isGesloten && !isOntbonden) {
            // sluiting en ontbinding kunnen allebei leeg zijn
            inhoud =
                    new Lo3HuwelijkOfGpInhoud(null, null, null, null, null, null, null, null, null, null, null, null,
                            null, null, null, null, null, null);
        } else {

            final Lo3Datum lo3DatumSluiting = isGesloten ? new Lo3Datum(datumSluiting) : null;
            final Lo3Datum lo3DatumOntbinding = isOntbonden ? new Lo3Datum(datumOntbinding) : null;
            final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode =
                    isOntbonden ? new Lo3RedenOntbindingHuwelijkOfGpCode("S") : null;
            final Lo3GemeenteCode gemeenteSluiting = isGesloten ? new Lo3GemeenteCode(gemeenteSluitingCode) : null;
            final Lo3GemeenteCode gemeenteOntbinding =
                    isOntbonden ? new Lo3GemeenteCode(gemeenteOntbindingCode) : null;
            final Lo3LandCode landSluiting = isGesloten ? new Lo3LandCode("6030") : null;
            final Lo3LandCode landOntbinding = isOntbonden ? new Lo3LandCode("6030") : null;
            inhoud =
                    new Lo3HuwelijkOfGpInhoud(1234567890L, 123456789L, "Ben", null, null, "Getrouwd", new Lo3Datum(
                            19800101), new Lo3GemeenteCode("1234"), new Lo3LandCode("6030"),
                            Lo3GeslachtsaanduidingEnum.MAN.asElement(), lo3DatumSluiting, gemeenteSluiting,
                            landSluiting, lo3DatumOntbinding, gemeenteOntbinding, landOntbinding,
                            redenOntbindingHuwelijkOfGpCode, soort);
        }
        return inhoud;
    }

    protected static BrpRelatieInhoud buildBrpRelatie(
            final Integer datumSluiting,
            final String sluitingGemeenteCode,
            final Integer datumOntbinding,
            final String ontbindingGemeenteCode,
            final String redenOntbindingCode) {
        final boolean isOntbonden = datumOntbinding != null;
        final boolean isGesloten = datumSluiting != null;
        final BrpRelatieInhoud inhoud;
        if (!isGesloten && !isOntbonden) {
            // sluiting en ontbinding kunnen allebei leeg zijn
            inhoud =
                    new BrpRelatieInhoud(null, null, null, null, null, null, null, null, null, null, null, null,
                            null, null, null);
        } else {

            final BrpDatum datumaanvang = isGesloten ? new BrpDatum(datumSluiting) : null;
            final BrpDatum datumEinde = isOntbonden ? new BrpDatum(datumOntbinding) : null;
            final BrpRedenEindeRelatieCode redenEinde =
                    isOntbonden ? new BrpRedenEindeRelatieCode(redenOntbindingCode) : null;
            final BrpGemeenteCode gemeenteCodeAanvang =
                    isGesloten ? new BrpGemeenteCode(new BigDecimal(sluitingGemeenteCode)) : null;
            final BrpGemeenteCode gemeenteCodeEinde =
                    isOntbonden ? new BrpGemeenteCode(new BigDecimal(ontbindingGemeenteCode)) : null;
            final BrpLandCode landCodeAanvang = isGesloten ? new BrpLandCode(Integer.valueOf("6030")) : null;
            final BrpLandCode landCodeEinde = isOntbonden ? new BrpLandCode(Integer.valueOf("6030")) : null;

            inhoud =
                    new BrpRelatieInhoud(datumaanvang, gemeenteCodeAanvang, null, null, null, landCodeAanvang, null,
                            redenEinde, datumEinde, gemeenteCodeEinde, null, null, null, landCodeEinde, null);
        }
        return inhoud;
    }

    protected static BrpGeboorteInhoud buildBrpGeboorteInhoud(final Integer datum, final String gemeenteCode) {
        return new BrpGeboorteInhoud(new BrpDatum(datum), new BrpGemeenteCode(new BigDecimal(gemeenteCode)), null,
                null, null, new BrpLandCode(Integer.valueOf("6030")), null);
    }

    protected static Lo3Documentatie buildLo3Akte(final long id) {
        return new Lo3Documentatie(id, new Lo3GemeenteCode("0518"), "A" + id, null, null, null, null, null);
    }

    protected static Lo3Documentatie buildLo3Document(final long id, final Integer datum, final String beschrijving) {
        return new Lo3Documentatie(id, null, null, new Lo3GemeenteCode("0518"), new Lo3Datum(datum), beschrijving,
                null, null);
    }

    private BrpStapel<BrpDocumentInhoud> buildBrpAkteStapel(final String akteNummer, final BrpDatumTijd registratie) {
        return buildBrpAkteOfDocument(BrpSoortDocumentCode.AKTE, akteNummer, null, registratie);
    }

    private BrpStapel<BrpDocumentInhoud> buildBrpExtraDocument(
            final String omschrijving,
            final BrpDatumTijd registratie) {
        final BrpDocumentInhoud inhoud =
                new BrpDocumentInhoud(BrpSoortDocumentCode.MIGRATIEVOORZIENING, null, null, omschrijving,
                        BrpPartijCode.MIGRATIEVOORZIENING);
        final BrpHistorie historie = new BrpHistorie(null, null, registratie, null);
        final BrpGroep<BrpDocumentInhoud> groep = new BrpGroep<BrpDocumentInhoud>(inhoud, historie, null, null, null);

        return new BrpStapel<BrpDocumentInhoud>(Collections.singletonList(groep));
    }

    private BrpStapel<BrpDocumentInhoud>
            buildBrpDocumentStapel(final String docNummer, final BrpDatumTijd registratie) {
        return buildBrpAkteOfDocument(BrpSoortDocumentCode.DOCUMENT, null, docNummer, registratie);
    }

    private BrpStapel<BrpDocumentInhoud> buildBrpAkteOfDocument(
            final BrpSoortDocumentCode soortDocument,
            final String aktenummer,
            final String omschrijving,
            final BrpDatumTijd registratie) {
        return new BrpStapel<BrpDocumentInhoud>(Collections.singletonList(new BrpGroep<BrpDocumentInhoud>(
                new BrpDocumentInhoud(soortDocument, null, aktenummer, omschrijving, new BrpPartijCode(null, Integer
                        .valueOf("0518"))), new BrpHistorie(null, null, registratie, null), null, null, null)));
    }

    protected BrpActie buildBrpActie(final BrpDatumTijd dtRegistratie, final String documentNr) {
        return new BrpActie(brpActieIdTeller++, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MIGRATIEVOORZIENING,
                null, null, dtRegistratie, Collections.singletonList(buildBrpAkteStapel(documentNr, dtRegistratie)),
                0, null);
    }

    protected BrpActie buildBrpActie(
            final BrpDatumTijd dtRegistratie,
            final String documentNr,
            final String extraDocumentOmschrijving) {
        final List<BrpStapel<BrpDocumentInhoud>> documenten = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
        documenten.add(buildBrpAkteStapel(documentNr, dtRegistratie));
        documenten.add(buildBrpExtraDocument(extraDocumentOmschrijving, dtRegistratie));

        return new BrpActie(brpActieIdTeller++, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MIGRATIEVOORZIENING,
                null, null, dtRegistratie, documenten, 0, null);
    }

    protected BrpActie buildBrpActie(
            final BrpDatumTijd dtRegistratie,
            final String documentNr,
            final Document documentSoort) {
        BrpActie actie;
        if (documentSoort.equals(Document.AKTE)) {
            actie = buildBrpActie(dtRegistratie, documentNr);
        } else {
            actie =
                    new BrpActie(brpActieIdTeller++, BrpSoortActieCode.CONVERSIE_GBA,
                            BrpPartijCode.MIGRATIEVOORZIENING, null, dtRegistratie, dtRegistratie,
                            Collections.singletonList(buildBrpDocumentStapel(documentNr, dtRegistratie)), 0, null);
        }
        return actie;
    }

    public Lo3Persoonslijst getLo3Persoonslijst() {
        return lo3Persoonslijst;
    }

    protected Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel() {
        return null;
    }

    protected Lo3Stapel<Lo3HuwelijkOfGpInhoud> maakHuwelijkStapel() {
        return null;
    }

    protected Lo3Stapel<Lo3VerblijfstitelInhoud> maakVerblijfstitelStapel() {
        return null;
    }

    protected void assertGroepExclusiefInhoud(
            final BrpGroep<? extends BrpGroepInhoud> groep,
            final BrpHistorie expectedHistorie,
            final BrpActie expectedActie,
            final BrpActie expectedActieGeldigheid,
            final BrpActie expectedActieVerval) {
        assertGroepExclusiefInhoud(groep, expectedHistorie, expectedActie, expectedActieGeldigheid,
                expectedActieVerval, false);
    }

    protected void assertGroepExclusiefInhoud(
            final BrpGroep<? extends BrpGroepInhoud> groep,
            final BrpHistorie expectedHistorie,
            final BrpActie expectedActie,
            final BrpActie expectedActieGeldigheid,
            final BrpActie expectedActieVerval,
            final boolean negeerMaterieleHistorie) {
        if (negeerMaterieleHistorie) {
            assertEquals("Datumtijd registratie niet gelijk", expectedHistorie.getDatumTijdRegistratie(), groep
                    .getHistorie().getDatumTijdRegistratie());
            assertEquals("Datumtijd verval niet gelijk", expectedHistorie.getDatumTijdVerval(), groep.getHistorie()
                    .getDatumTijdVerval());
        } else {
            assertEquals("Historie niet gelijk", expectedHistorie, groep.getHistorie());
        }
        assertActie(expectedActieGeldigheid, groep.getActieGeldigheid());
        assertActie(expectedActieVerval, groep.getActieVerval());
        assertActie(expectedActie, groep.getActieInhoud());

    }

    protected void assertActie(final BrpActie expectedActie, final BrpActie actie) {
        if (expectedActie == null) {
            assertNull(actie);
            return;
        }
        assertNotNull(actie);
        // inhoud
        assertEquals(expectedActie.getDatumTijdOntlening(), actie.getDatumTijdOntlening());
        assertEquals(expectedActie.getDatumTijdRegistratie(), actie.getDatumTijdRegistratie());
        // assertEquals(expectedActie.getId(), actie.getId());
        assertEquals(expectedActie.getPartijCode(), actie.getPartijCode());
        assertEquals(expectedActie.getSoortActieCode(), actie.getSoortActieCode());
        assertEquals(expectedActie.getVerdragCode(), actie.getVerdragCode());

        // documenten
        final List<BrpStapel<BrpDocumentInhoud>> documenten = actie.getDocumentStapels();
        final List<BrpStapel<BrpDocumentInhoud>> expectedDocumenten = expectedActie.getDocumentStapels();
        assertEquals(expectedDocumenten.size(), documenten.size());

        System.out.println("Actual: " + documenten);
        System.out.println("Expect: " + expectedDocumenten);

        assertTrue(zijnDocumentStapelsGelijk(documenten, expectedDocumenten));
    }

    private boolean zijnDocumentStapelsGelijk(
            final List<BrpStapel<BrpDocumentInhoud>> documentStapels,
            final List<BrpStapel<BrpDocumentInhoud>> expectedDocumentStapels) {
        for (int index = 0; index < documentStapels.size(); index++) {
            final BrpStapel<BrpDocumentInhoud> documentStapel = documentStapels.get(index);
            final BrpStapel<BrpDocumentInhoud> expectedDocumentStapel = expectedDocumentStapels.get(index);

            assertEquals(expectedDocumentStapel.size(), documentStapel.size());
            assertEquals(1, documentStapel.size());
            final BrpGroep<BrpDocumentInhoud> documentGroep = documentStapel.getMeestRecenteElement();
            final BrpGroep<BrpDocumentInhoud> expectedDocumentGroep = expectedDocumentStapel.getMeestRecenteElement();
            assertEquals(expectedDocumentGroep.getActieGeldigheid(), documentGroep.getActieGeldigheid());
            assertEquals(expectedDocumentGroep.getActieInhoud(), documentGroep.getActieInhoud());
            assertEquals(expectedDocumentGroep.getActieVerval(), documentGroep.getActieVerval());
            assertEquals(expectedDocumentGroep.getHistorie(), documentGroep.getHistorie());
            assertEquals(expectedDocumentGroep.getInhoud(), documentGroep.getInhoud());

        }
        return documentStapels.containsAll(expectedDocumentStapels);
    }

    protected void assertNationaliteit(
            final BrpGroep<BrpNationaliteitInhoud> nationaliteitGroep,
            final BrpTestObject<BrpNationaliteitInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(nationaliteitGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval());
        assertEquals(expected.getInhoud().getNationaliteitCode(), nationaliteitGroep.getInhoud()
                .getNationaliteitCode());
        assertEquals(expected.getInhoud().getRedenVerkrijgingNederlandschapCode(), nationaliteitGroep.getInhoud()
                .getRedenVerkrijgingNederlandschapCode());
        assertEquals(expected.getInhoud().getRedenVerliesNederlandschapCode(), nationaliteitGroep.getInhoud()
                .getRedenVerliesNederlandschapCode());

    }

    protected void assertVerblijfsrecht(
            final BrpGroep<BrpVerblijfsrechtInhoud> verblijfsrechtGroep,
            final BrpTestObject<BrpVerblijfsrechtInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(verblijfsrechtGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval());
        assertEquals(expected.getInhoud().getVerblijfsrechtCode(), verblijfsrechtGroep.getInhoud()
                .getVerblijfsrechtCode());
        assertEquals(expected.getInhoud().getAanvangVerblijfsrecht(), verblijfsrechtGroep.getInhoud()
                .getAanvangVerblijfsrecht());
        assertEquals(expected.getInhoud().getAanvangAaneensluitendVerblijfsrecht(), verblijfsrechtGroep.getInhoud()
                .getAanvangAaneensluitendVerblijfsrecht());
        assertEquals(expected.getInhoud().getVoorzienEindeVerblijfsrecht(), verblijfsrechtGroep.getInhoud()
                .getVoorzienEindeVerblijfsrecht());
    }

    protected void assertBehandeldAlsNederlander(
            final BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderGroep,
            final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(behandeldAlsNederlanderGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval());
        assertEquals(Boolean.TRUE, behandeldAlsNederlanderGroep.getInhoud().getHeeftIndicatie());
    }

    protected void assertVastgesteldNietNederlander(
            final BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderGroep,
            final BrpTestObject<BrpVastgesteldNietNederlanderIndicatieInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(vastgesteldNietNederlanderGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval());
        assertEquals(Boolean.TRUE, vastgesteldNietNederlanderGroep.getInhoud().getHeeftIndicatie());
    }

    protected void assertVoornaam(
            final BrpGroep<BrpVoornaamInhoud> voornaamGroep,
            final BrpTestObject<BrpVoornaamInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(voornaamGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval());
        assertEquals(expected.getInhoud().getVoornaam(), voornaamGroep.getInhoud().getVoornaam());
        assertEquals(expected.getInhoud().getVolgnummer(), voornaamGroep.getInhoud().getVolgnummer());

    }

    protected void assertSamengesteldeNaam(
            final BrpGroep<BrpSamengesteldeNaamInhoud> naamGroep,
            final BrpTestObject<BrpSamengesteldeNaamInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(naamGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval());
        assertEquals(expected.getInhoud().getAdellijkeTitelCode(), naamGroep.getInhoud().getAdellijkeTitelCode());
        assertEquals(expected.getInhoud().getGeslachtsnaam(), naamGroep.getInhoud().getGeslachtsnaam());
        assertEquals(expected.getInhoud().getIndicatieAfgeleid(), naamGroep.getInhoud().getIndicatieAfgeleid());
        assertEquals(expected.getInhoud().getIndicatieNamenreeks(), naamGroep.getInhoud().getIndicatieNamenreeks());
        assertEquals(expected.getInhoud().getPredikaatCode(), naamGroep.getInhoud().getPredikaatCode());
        assertEquals(expected.getInhoud().getScheidingsteken(), naamGroep.getInhoud().getScheidingsteken());
        assertEquals(expected.getInhoud().getVoornamen(), naamGroep.getInhoud().getVoornamen());
        assertEquals(expected.getInhoud().getVoorvoegsel(), naamGroep.getInhoud().getVoorvoegsel());

    }

    protected void assertGeboorte(
            final BrpGroep<BrpGeboorteInhoud> geboorteGroep,
            final BrpTestObject<BrpGeboorteInhoud> expected) {
        final boolean negeerMaterieleHistorie = true;
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(geboorteGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval(), negeerMaterieleHistorie);
        assertEquals(expected.getInhoud().getBuitenlandseGeboorteplaats(), geboorteGroep.getInhoud()
                .getBuitenlandseGeboorteplaats());
        assertEquals(expected.getInhoud().getBuitenlandseRegioGeboorte(), geboorteGroep.getInhoud()
                .getBuitenlandseRegioGeboorte());
        assertEquals(expected.getInhoud().getGeboortedatum(), geboorteGroep.getInhoud().getGeboortedatum());
        assertEquals(expected.getInhoud().getGemeenteCode(), geboorteGroep.getInhoud().getGemeenteCode());
        assertEquals(expected.getInhoud().getLandCode(), geboorteGroep.getInhoud().getLandCode());
        assertEquals(expected.getInhoud().getOmschrijvingGeboortelocatie(), geboorteGroep.getInhoud()
                .getOmschrijvingGeboortelocatie());
        assertEquals(expected.getInhoud().getPlaatsCode(), geboorteGroep.getInhoud().getPlaatsCode());

    }

    protected void assertHuwelijk(
            final BrpGroep<BrpRelatieInhoud> relatieGroep,
            final BrpTestObject<BrpRelatieInhoud> expected) {
        final boolean negeerMaterieleHistorie = true;
        final BrpHistorie expectedHistorie =
                new BrpHistorie(expected.getAanvangGeldigheid(), expected.getEindeGeldigheid(),
                        expected.getRegistratie(), expected.getVerval());
        assertGroepExclusiefInhoud(relatieGroep, expectedHistorie, expected.getActieInhoud(),
                expected.getActieGeldigheid(), expected.getActieVerval(), negeerMaterieleHistorie);
        assertEquals(expected.getInhoud().getBuitenlandsePlaatsAanvang(), relatieGroep.getInhoud()
                .getBuitenlandsePlaatsAanvang());
        assertEquals(expected.getInhoud().getBuitenlandsePlaatsEinde(), relatieGroep.getInhoud()
                .getBuitenlandsePlaatsEinde());
        assertEquals(expected.getInhoud().getBuitenlandseRegioAanvang(), relatieGroep.getInhoud()
                .getBuitenlandseRegioAanvang());
        assertEquals(expected.getInhoud().getBuitenlandseRegioEinde(), relatieGroep.getInhoud()
                .getBuitenlandseRegioEinde());
        assertEquals(expected.getInhoud().getDatumAanvang(), relatieGroep.getInhoud().getDatumAanvang());
        assertEquals(expected.getInhoud().getDatumEinde(), relatieGroep.getInhoud().getDatumEinde());
        assertEquals(expected.getInhoud().getGemeenteCodeAanvang(), relatieGroep.getInhoud().getGemeenteCodeAanvang());
        assertEquals(expected.getInhoud().getGemeenteCodeEinde(), relatieGroep.getInhoud().getGemeenteCodeEinde());
        assertEquals(expected.getInhoud().getLandCodeAanvang(), relatieGroep.getInhoud().getLandCodeAanvang());
        assertEquals(expected.getInhoud().getLandCodeEinde(), relatieGroep.getInhoud().getLandCodeEinde());
        assertEquals(expected.getInhoud().getOmschrijvingLocatieAanvang(), relatieGroep.getInhoud()
                .getOmschrijvingLocatieAanvang());
        assertEquals(expected.getInhoud().getOmschrijvingLocatieEinde(), relatieGroep.getInhoud()
                .getOmschrijvingLocatieEinde());
        assertEquals(expected.getInhoud().getPlaatsCodeAanvang(), relatieGroep.getInhoud().getPlaatsCodeAanvang());
        assertEquals(expected.getInhoud().getPlaatsCodeEinde(), relatieGroep.getInhoud().getPlaatsCodeEinde());
        assertEquals(expected.getInhoud().getRedenEinde(), relatieGroep.getInhoud().getRedenEinde());

    }

    protected void sorteerBrpStapel(final BrpStapel<? extends BrpGroepInhoud> brpStapel) {
        // TODO sorteren werkt niet (waarschijnlijk unmodifiable list)
        Collections.sort(brpStapel.getGroepen(), BRP_GROEP_COMPARATOR);
    }

    private static class BrpGroepComparator implements Comparator<BrpGroep<?>> {

        @Override
        public int compare(final BrpGroep<?> groep1, final BrpGroep<?> groep2) {
            final BrpDatumTijd tijd1 = groep1.getHistorie().getDatumTijdRegistratie();
            final BrpDatumTijd tijd2 = groep2.getHistorie().getDatumTijdRegistratie();

            return tijd1.compareTo(tijd2);
        }
    }

}
