/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractConversieTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Super class voor de CasusTesten. Door in deze class de maak*() methoden te overriden kan voor de test de gewenste
 * stapel worden gemaakt, terwijl niet telkens alle verplichte stapels hoeven worden gedefinieerd.
 * 
 */

public abstract class AbstractCasusTest extends AbstractConversieTest {

    protected static final String RDAM = "1234";
    protected static final String ADAM = "4321";
    protected static final Lo3SoortVerbintenis H = Lo3SoortVerbintenisEnum.HUWELIJK.asElement();
    protected static final Lo3SoortVerbintenis P = Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.asElement();
    protected static final Lo3IndicatieOnjuist ONJUIST = Lo3IndicatieOnjuistEnum.ONJUIST.asElement();
    private static final String LANDCODE = "6030";
    private static final Comparator<BrpGroep<?>> BRP_GROEP_COMPARATOR = new BrpGroepComparator();
    private static long brpActieIdTeller;
    private Lo3Persoonslijst lo3Persoonslijst;

    protected static BrpRelatieInhoud buildBrpRelatie(
        final Integer datumSluiting,
        final String sluitingGemeenteCode,
        final Integer datumOntbinding,
        final String ontbindingGemeenteCode,
        final Character redenOntbindingCode)
    {
        final boolean isOntbonden = datumOntbinding != null;
        final boolean isGesloten = datumSluiting != null;
        final BrpRelatieInhoud inhoud;
        if (!isGesloten && !isOntbonden) {
            // sluiting en ontbinding kunnen allebei leeg zijn
            inhoud = new BrpRelatieInhoud(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        } else {

            final BrpDatum datumaanvang = isGesloten ? new BrpDatum(datumSluiting, null) : null;
            final BrpDatum datumEinde = isOntbonden ? new BrpDatum(datumOntbinding, null) : null;
            final BrpRedenEindeRelatieCode redenEinde = isOntbonden ? new BrpRedenEindeRelatieCode(redenOntbindingCode) : null;
            final BrpGemeenteCode gemeenteCodeAanvang = isGesloten ? new BrpGemeenteCode(Short.parseShort(sluitingGemeenteCode)) : null;
            final BrpGemeenteCode gemeenteCodeEinde = isOntbonden ? new BrpGemeenteCode(Short.parseShort(ontbindingGemeenteCode)) : null;
            final BrpLandOfGebiedCode landCodeAanvang = isGesloten ? new BrpLandOfGebiedCode(Short.parseShort(LANDCODE)) : null;
            final BrpLandOfGebiedCode landCodeEinde = isOntbonden ? new BrpLandOfGebiedCode(Short.parseShort(LANDCODE)) : null;

            inhoud =
                    new BrpRelatieInhoud(
                        datumaanvang,
                        gemeenteCodeAanvang,
                        null,
                        null,
                        null,
                        landCodeAanvang,
                        null,
                        redenEinde,
                        datumEinde,
                        gemeenteCodeEinde,
                        null,
                        null,
                        null,
                        landCodeEinde,
                        null);
        }
        return inhoud;
    }

    protected static BrpGeboorteInhoud buildBrpGeboorteInhoud(final Integer datum, final String gemeenteCode) {
        return new BrpGeboorteInhoud(
            new BrpDatum(datum, null),
            new BrpGemeenteCode(Short.parseShort(gemeenteCode)),
            null,
            null,
            null,
            new BrpLandOfGebiedCode(Short.parseShort(LANDCODE)),
            null);
    }

    protected static Lo3Documentatie buildLo3Akte(final long id) {
        return new Lo3Documentatie(id, new Lo3GemeenteCode("0518"), Lo3String.wrap(id + "A"), null, null, null, null, null);
    }

    protected static Lo3Documentatie buildLo3Document(final long id, final Integer datum, final String beschrijving) {
        return new Lo3Documentatie(id, null, null, new Lo3GemeenteCode("0518"), new Lo3Datum(datum), Lo3String.wrap(beschrijving), null, null);
    }

    @Before
    @Override
    public void setUp() {
        lo3Persoonslijst = maakPersoonslijst();
        Logging.initContext();
    }

    @Override
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
    public abstract void testLo3NaarBrp();

    /**
     * Converteer de LO3 persoonslijst eerst naar een Brp persoonslijst en converteer deze terug naar LO3
     *
     * @throws Exception
     */
    public abstract void testRondverteer();

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
        return new Lo3OuderInhoud(
            Lo3Long.wrap(aNummer),
            Lo3Integer.wrap((int) (aNummer / 10)),
            Lo3String.wrap("Ouder"),
            null,
            null,
            Lo3String.wrap("Ouderson"),
            new Lo3Datum(19030101),
            new Lo3GemeenteCode("0518"),
            new Lo3LandCode(LANDCODE),
            Lo3GeslachtsaanduidingEnum.ONBEKEND.asElement(),
            new Lo3Datum(datumFamilie));
    }

    protected Lo3Stapel<Lo3OuderInhoud> maakOuder1Stapel() {
        return VerplichteStapel.createOuderStapel(1111111111L, Lo3CategorieEnum.CATEGORIE_02);
    }

    protected Lo3Stapel<Lo3OuderInhoud> maakOuder2Stapel() {
        return VerplichteStapel.createOuderStapel(2222222222L, Lo3CategorieEnum.CATEGORIE_03);
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
        final String geboorteGemeente)
    {
        return new Lo3PersoonInhoud(
            Lo3Long.wrap(1234567890L),
            Lo3Integer.wrap(123456789),
            Lo3String.wrap(voornaam),
            null,
            null,
            Lo3String.wrap(geslachtsnaam),
            new Lo3Datum(geboorteDatum),
            new Lo3GemeenteCode(geboorteGemeente),
            Lo3LandCode.NEDERLAND,
            Lo3GeslachtsaanduidingEnum.MAN.asElement(),
            null,
            null,
            Lo3AanduidingNaamgebruikCodeEnum.EIGEN_GESLACHTSNAAM.asElement());
    }

    protected Lo3PersoonInhoud buildPersoonMetNamen(final String voornaam, final String geslachtsnaam) {
        return buildPersoonMetNaamEnGeboorte(voornaam, geslachtsnaam, 19800101, RDAM);
    }

    protected Lo3PersoonInhoud buildPersoonMetVoornaam(final String voornaam) {
        return buildPersoonMetNamen(voornaam, "Jansen");
    }

    protected Lo3HuwelijkOfGpInhoud buildHuwelijk(final Integer datumSluiting, final Integer datumOntbinding) {
        // aanroep met default gemeente codes
        return buildHuwelijk(datumSluiting, RDAM, datumOntbinding, RDAM);
    }

    protected Lo3HuwelijkOfGpInhoud buildHuwelijk(
        final Integer datumSluiting,
        final String gemeenteSluitingCode,
        final Integer datumOntbinding,
        final String gemeenteOntbindingCode)
    {
        return buildHuwelijk(datumSluiting, gemeenteSluitingCode, datumOntbinding, gemeenteOntbindingCode, Lo3SoortVerbintenisEnum.HUWELIJK.asElement());
    }

    protected Lo3HuwelijkOfGpInhoud buildHuwelijk(
        final Integer datumSluiting,
        final String gemeenteSluitingCode,
        final Integer datumOntbinding,
        final String gemeenteOntbindingCode,
        final Lo3SoortVerbintenis soort)
    {
        final boolean isOntbonden = datumOntbinding != null;
        final boolean isGesloten = datumSluiting != null;
        final Lo3HuwelijkOfGpInhoud inhoud;
        if (!isGesloten && !isOntbonden) {
            // sluiting en ontbinding kunnen allebei leeg zijn
            inhoud = new Lo3HuwelijkOfGpInhoud(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        } else {

            final Lo3Datum lo3DatumSluiting = isGesloten ? new Lo3Datum(datumSluiting) : null;
            final Lo3Datum lo3DatumOntbinding = isOntbonden ? new Lo3Datum(datumOntbinding) : null;
            final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode = isOntbonden ? new Lo3RedenOntbindingHuwelijkOfGpCode("S") : null;
            final Lo3GemeenteCode gemeenteSluiting = isGesloten ? new Lo3GemeenteCode(gemeenteSluitingCode) : null;
            final Lo3GemeenteCode gemeenteOntbinding = isOntbonden ? new Lo3GemeenteCode(gemeenteOntbindingCode) : null;
            final Lo3LandCode landSluiting = isGesloten ? new Lo3LandCode(LANDCODE) : null;
            final Lo3LandCode landOntbinding = isOntbonden ? new Lo3LandCode(LANDCODE) : null;
            inhoud =
                    new Lo3HuwelijkOfGpInhoud(
                        Lo3Long.wrap(1234567890L),
                        Lo3Integer.wrap(123456789),
                        Lo3String.wrap("Ben"),
                        null,
                        null,
                        Lo3String.wrap("Getrouwd"),
                        new Lo3Datum(19800101),
                        new Lo3GemeenteCode(RDAM),
                        new Lo3LandCode(LANDCODE),
                        Lo3GeslachtsaanduidingEnum.MAN.asElement(),
                        lo3DatumSluiting,
                        gemeenteSluiting,
                        landSluiting,
                        lo3DatumOntbinding,
                        gemeenteOntbinding,
                        landOntbinding,
                        redenOntbindingHuwelijkOfGpCode,
                        soort);
        }
        return inhoud;
    }

    private BrpStapel<BrpDocumentInhoud> buildBrpAkteStapel(final String akteNummer, final BrpDatumTijd registratie) {
        return buildBrpAkteOfDocument(new BrpSoortDocumentCode(akteNummer.substring(0, 1)), akteNummer, null, registratie);
    }

    private BrpStapel<BrpDocumentInhoud> buildBrpDocumentStapel(final String docNummer, final BrpDatumTijd registratie) {
        return buildBrpAkteOfDocument(BrpSoortDocumentCode.HISTORIE_CONVERSIE, null, docNummer, registratie);
    }

    private BrpStapel<BrpDocumentInhoud> buildBrpAkteOfDocument(
        final BrpSoortDocumentCode soortDocument,
        final String aktenummer,
        final String omschrijving,
        final BrpDatumTijd registratie)
    {
        return new BrpStapel<>(
            Collections.singletonList(
                new BrpGroep<>(
                    new BrpDocumentInhoud(
                        soortDocument,
                        null,
                        BrpString.wrap(aktenummer, null),
                        BrpString.wrap(omschrijving, null),
                        new BrpPartijCode(Integer.valueOf("051801"))),
                    new BrpHistorie(null, null, registratie, null, null),
                    null,
                    null,
                    null)));
    }

    protected BrpActie buildBrpActie(final BrpDatumTijd dtRegistratie, final String documentNr) {
        return buildBrpActie(dtRegistratie, documentNr, BrpSoortActieCode.CONVERSIE_GBA);
    }

    protected BrpActie buildBrpActie(final BrpDatumTijd dtRegistratie, final String documentNr, final BrpSoortActieCode soortActieCode) {
        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        actieBronnen.add(new BrpActieBron(buildBrpAkteStapel(documentNr, dtRegistratie), null));

        return new BrpActie(brpActieIdTeller++, soortActieCode, BrpPartijCode.MIGRATIEVOORZIENING, dtRegistratie, null, actieBronnen, 0, null);
    }

    protected BrpActie buildBrpActie(final Integer registratieDatumTijd, final String documentNr, final Document documentSoort) {
        final BrpActie actie;
        final BrpDatumTijd dtRegistratie = BrpDatumTijd.fromDatum(registratieDatumTijd, null);
        if (documentSoort.equals(Document.AKTE)) {
            actie = buildBrpActie(dtRegistratie, documentNr);
        } else {
            actie =
                    new BrpActie(
                        brpActieIdTeller++,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        BrpPartijCode.MIGRATIEVOORZIENING,
                        dtRegistratie,
                        new BrpDatum(registratieDatumTijd, null),
                        Collections.singletonList(new BrpActieBron(buildBrpDocumentStapel(documentNr, dtRegistratie), null)),
                        0,
                        null);
        }
        return actie;
    }

    /**
     * Geef de waarde van lo3 persoonslijst.
     *
     * @return lo3 persoonslijst
     */
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
        final BrpActie expectedActieVerval)
    {
        assertGroepExclusiefInhoud(groep, expectedHistorie, expectedActie, expectedActieGeldigheid, expectedActieVerval, false);
    }

    protected void assertGroepExclusiefInhoud(
        final BrpGroep<? extends BrpGroepInhoud> groep,
        final BrpHistorie expectedHistorie,
        final BrpActie expectedActie,
        final BrpActie expectedActieGeldigheid,
        final BrpActie expectedActieVerval,
        final boolean negeerMaterieleHistorie)
    {
        if (negeerMaterieleHistorie) {
            Assert.assertEquals(
                "Datumtijd registratie niet gelijk",
                expectedHistorie.getDatumTijdRegistratie(),
                groep.getHistorie().getDatumTijdRegistratie());
            Assert.assertEquals("Datumtijd verval niet gelijk", expectedHistorie.getDatumTijdVerval(), groep.getHistorie().getDatumTijdVerval());
            // Assert.assertEquals("Nadere aanduiding verval niet gelijk", expectedHistorie.getNadereAanduidingVerval(),
            // groep.getHistorie().getNadereAanduidingVerval());
        } else {
            Assert.assertEquals("Historie niet gelijk", expectedHistorie, groep.getHistorie());
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
        Assert.assertEquals(expectedActie.getDatumTijdRegistratie(), actie.getDatumTijdRegistratie());
        // assertEquals(expectedActie.getId(), actie.getId());
        Assert.assertEquals(expectedActie.getPartijCode(), actie.getPartijCode());
        Assert.assertEquals(expectedActie.getSoortActieCode(), actie.getSoortActieCode());

        // documenten
        final List<BrpActieBron> actieBronnen = actie.getActieBronnen();
        final List<BrpActieBron> expectedActieBronnen = expectedActie.getActieBronnen();
        Assert.assertEquals(expectedActieBronnen.size(), actie.getActieBronnen().size());

        assertTrue(zijnDocumentStapelsGelijk(actieBronnen, expectedActieBronnen));
    }

    private boolean zijnDocumentStapelsGelijk(final List<BrpActieBron> documentStapels, final List<BrpActieBron> expectedDocumentStapels) {
        for (int index = 0; index < documentStapels.size(); index++) {
            final BrpActieBron documentStapel = documentStapels.get(index);
            final BrpActieBron expectedDocumentStapel = expectedDocumentStapels.get(index);

            Assert.assertEquals(expectedDocumentStapel.getDocumentStapel().size(), documentStapel.getDocumentStapel().size());
            Assert.assertEquals(1, documentStapel.getDocumentStapel().size());
            final BrpGroep<BrpDocumentInhoud> documentGroep = documentStapel.getDocumentStapel().getLaatsteElement();
            final BrpGroep<BrpDocumentInhoud> expectedDocumentGroep = expectedDocumentStapel.getDocumentStapel().getLaatsteElement();
            Assert.assertEquals(expectedDocumentGroep.getActieGeldigheid(), documentGroep.getActieGeldigheid());
            Assert.assertEquals(expectedDocumentGroep.getActieInhoud(), documentGroep.getActieInhoud());
            Assert.assertEquals(expectedDocumentGroep.getActieVerval(), documentGroep.getActieVerval());
            Assert.assertEquals(expectedDocumentGroep.getHistorie(), documentGroep.getHistorie());
            Assert.assertEquals(expectedDocumentGroep.getInhoud(), documentGroep.getInhoud());

        }
        return documentStapels.containsAll(expectedDocumentStapels);
    }

    protected void assertNationaliteit(final BrpGroep<BrpNationaliteitInhoud> nationaliteitGroep, final BrpTestObject<BrpNationaliteitInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            nationaliteitGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval());
        Assert.assertEquals(expected.getInhoud().getNationaliteitCode(), nationaliteitGroep.getInhoud().getNationaliteitCode());
        Assert.assertEquals(
            expected.getInhoud().getRedenVerkrijgingNederlandschapCode(),
            nationaliteitGroep.getInhoud().getRedenVerkrijgingNederlandschapCode());
        Assert.assertEquals(expected.getInhoud().getRedenVerliesNederlandschapCode(), nationaliteitGroep.getInhoud().getRedenVerliesNederlandschapCode());

    }

    protected void assertVerblijfstitel(
        final BrpGroep<BrpVerblijfsrechtInhoud> verblijfstitelGroep,
        final BrpTestObject<BrpVerblijfsrechtInhoud> expected)
    {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            verblijfstitelGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval());
        Assert.assertEquals(expected.getInhoud().getAanduidingVerblijfsrechtCode(), verblijfstitelGroep.getInhoud().getAanduidingVerblijfsrechtCode());
        Assert.assertEquals(expected.getInhoud().getDatumMededelingVerblijfsrecht(), verblijfstitelGroep.getInhoud().getDatumMededelingVerblijfsrecht());
        Assert.assertEquals(
            expected.getInhoud().getDatumVoorzienEindeVerblijfsrecht(),
            verblijfstitelGroep.getInhoud().getDatumVoorzienEindeVerblijfsrecht());
    }

    protected void assertBehandeldAlsNederlander(
        final BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderGroep,
        final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> expected)
    {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            behandeldAlsNederlanderGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval());
        Assert.assertEquals(Boolean.TRUE, behandeldAlsNederlanderGroep.getInhoud().heeftIndicatie());
    }

    protected void assertVastgesteldNietNederlander(
        final BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderGroep,
        final BrpTestObject<BrpVastgesteldNietNederlanderIndicatieInhoud> expected)
    {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            vastgesteldNietNederlanderGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval());
        Assert.assertEquals(Boolean.TRUE, vastgesteldNietNederlanderGroep.getInhoud().heeftIndicatie());
    }

    protected void assertVoornaam(final BrpGroep<BrpVoornaamInhoud> voornaamGroep, final BrpTestObject<BrpVoornaamInhoud> expected) {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(voornaamGroep, expectedHistorie, expected.getActieInhoud(), expected.getActieGeldigheid(), expected.getActieVerval());
        Assert.assertEquals(expected.getInhoud().getVoornaam(), voornaamGroep.getInhoud().getVoornaam());
        Assert.assertEquals(expected.getInhoud().getVolgnummer(), voornaamGroep.getInhoud().getVolgnummer());

    }

    protected void assertSamengesteldeNaam(
        final BrpGroep<BrpSamengesteldeNaamInhoud> naamGroep,
        final BrpTestObject<BrpSamengesteldeNaamInhoud> expected)
    {
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(naamGroep, expectedHistorie, expected.getActieInhoud(), expected.getActieGeldigheid(), expected.getActieVerval());
        Assert.assertEquals(expected.getInhoud().getAdellijkeTitelCode(), naamGroep.getInhoud().getAdellijkeTitelCode());
        Assert.assertEquals(expected.getInhoud().getGeslachtsnaamstam(), naamGroep.getInhoud().getGeslachtsnaamstam());
        Assert.assertEquals(expected.getInhoud().getIndicatieAfgeleid(), naamGroep.getInhoud().getIndicatieAfgeleid());
        Assert.assertEquals(expected.getInhoud().getIndicatieNamenreeks(), naamGroep.getInhoud().getIndicatieNamenreeks());
        Assert.assertEquals(expected.getInhoud().getPredicaatCode(), naamGroep.getInhoud().getPredicaatCode());
        Assert.assertEquals(expected.getInhoud().getScheidingsteken(), naamGroep.getInhoud().getScheidingsteken());
        Assert.assertEquals(expected.getInhoud().getVoornamen(), naamGroep.getInhoud().getVoornamen());
        Assert.assertEquals(expected.getInhoud().getVoorvoegsel(), naamGroep.getInhoud().getVoorvoegsel());

    }

    protected void assertGeboorte(final BrpGroep<BrpGeboorteInhoud> geboorteGroep, final BrpTestObject<BrpGeboorteInhoud> expected) {
        final boolean negeerMaterieleHistorie = true;
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            geboorteGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval(),
            negeerMaterieleHistorie);
        Assert.assertEquals(expected.getInhoud().getBuitenlandsePlaatsGeboorte(), geboorteGroep.getInhoud().getBuitenlandsePlaatsGeboorte());
        Assert.assertEquals(expected.getInhoud().getBuitenlandseRegioGeboorte(), geboorteGroep.getInhoud().getBuitenlandseRegioGeboorte());
        Assert.assertEquals(expected.getInhoud().getGeboortedatum(), geboorteGroep.getInhoud().getGeboortedatum());
        Assert.assertEquals(expected.getInhoud().getGemeenteCode(), geboorteGroep.getInhoud().getGemeenteCode());
        Assert.assertEquals(expected.getInhoud().getLandOfGebiedCode(), geboorteGroep.getInhoud().getLandOfGebiedCode());
        Assert.assertEquals(expected.getInhoud().getOmschrijvingGeboortelocatie(), geboorteGroep.getInhoud().getOmschrijvingGeboortelocatie());
        Assert.assertEquals(expected.getInhoud().getWoonplaatsnaamGeboorte(), geboorteGroep.getInhoud().getWoonplaatsnaamGeboorte());

    }

    protected void assertOverlijden(final BrpGroep<BrpOverlijdenInhoud> geboorteGroep, final BrpTestObject<BrpOverlijdenInhoud> expected) {
        final boolean negeerMaterieleHistorie = true;
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            geboorteGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval(),
            negeerMaterieleHistorie);
        Assert.assertEquals(expected.getInhoud().getBuitenlandsePlaats(), geboorteGroep.getInhoud().getBuitenlandsePlaats());
        Assert.assertEquals(expected.getInhoud().getBuitenlandseRegio(), geboorteGroep.getInhoud().getBuitenlandseRegio());
        Assert.assertEquals(expected.getInhoud().getDatum(), geboorteGroep.getInhoud().getDatum());
        Assert.assertEquals(expected.getInhoud().getGemeenteCode(), geboorteGroep.getInhoud().getGemeenteCode());
        Assert.assertEquals(expected.getInhoud().getLandOfGebiedCode(), geboorteGroep.getInhoud().getLandOfGebiedCode());
        Assert.assertEquals(expected.getInhoud().getOmschrijvingLocatie(), geboorteGroep.getInhoud().getOmschrijvingLocatie());
        Assert.assertEquals(expected.getInhoud().getWoonplaatsnaamOverlijden(), geboorteGroep.getInhoud().getWoonplaatsnaamOverlijden());
    }

    protected void assertHuwelijk(final BrpGroep<BrpRelatieInhoud> relatieGroep, final BrpTestObject<BrpRelatieInhoud> expected) {
        final boolean negeerMaterieleHistorie = true;
        final BrpHistorie expectedHistorie =
                new BrpHistorie(
                    expected.getAanvangGeldigheid(),
                    expected.getEindeGeldigheid(),
                    expected.getRegistratie(),
                    expected.getVerval(),
                    expected.getNadereAanduidingVerval());
        assertGroepExclusiefInhoud(
            relatieGroep,
            expectedHistorie,
            expected.getActieInhoud(),
            expected.getActieGeldigheid(),
            expected.getActieVerval(),
            negeerMaterieleHistorie);
        Assert.assertEquals(expected.getInhoud().getBuitenlandsePlaatsAanvang(), relatieGroep.getInhoud().getBuitenlandsePlaatsAanvang());
        Assert.assertEquals(expected.getInhoud().getBuitenlandsePlaatsEinde(), relatieGroep.getInhoud().getBuitenlandsePlaatsEinde());
        Assert.assertEquals(expected.getInhoud().getBuitenlandseRegioAanvang(), relatieGroep.getInhoud().getBuitenlandseRegioAanvang());
        Assert.assertEquals(expected.getInhoud().getBuitenlandseRegioEinde(), relatieGroep.getInhoud().getBuitenlandseRegioEinde());
        Assert.assertEquals(expected.getInhoud().getDatumAanvang(), relatieGroep.getInhoud().getDatumAanvang());
        Assert.assertEquals(expected.getInhoud().getDatumEinde(), relatieGroep.getInhoud().getDatumEinde());
        Assert.assertEquals(expected.getInhoud().getGemeenteCodeAanvang(), relatieGroep.getInhoud().getGemeenteCodeAanvang());
        Assert.assertEquals(expected.getInhoud().getGemeenteCodeEinde(), relatieGroep.getInhoud().getGemeenteCodeEinde());
        Assert.assertEquals(expected.getInhoud().getLandOfGebiedCodeAanvang(), relatieGroep.getInhoud().getLandOfGebiedCodeAanvang());
        Assert.assertEquals(expected.getInhoud().getLandOfGebiedCodeEinde(), relatieGroep.getInhoud().getLandOfGebiedCodeEinde());
        Assert.assertEquals(expected.getInhoud().getOmschrijvingLocatieAanvang(), relatieGroep.getInhoud().getOmschrijvingLocatieAanvang());
        Assert.assertEquals(expected.getInhoud().getOmschrijvingLocatieEinde(), relatieGroep.getInhoud().getOmschrijvingLocatieEinde());
        Assert.assertEquals(expected.getInhoud().getWoonplaatsnaamAanvang(), relatieGroep.getInhoud().getWoonplaatsnaamAanvang());
        Assert.assertEquals(expected.getInhoud().getWoonplaatsnaamEinde(), relatieGroep.getInhoud().getWoonplaatsnaamEinde());
        Assert.assertEquals(expected.getInhoud().getRedenEindeRelatieCode(), relatieGroep.getInhoud().getRedenEindeRelatieCode());
    }

    protected void sorteerBrpStapel(final BrpStapel<? extends BrpGroepInhoud> brpStapel) {
        // TODO sorteren werkt niet (waarschijnlijk unmodifiable list)
        Collections.sort(brpStapel.getGroepen(), BRP_GROEP_COMPARATOR);
    }

    public enum Document {
        AKTE, DOCUMENT
    }

    private static class BrpGroepComparator implements Comparator<BrpGroep<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<?> groep1, final BrpGroep<?> groep2) {
            final BrpDatumTijd tijd1 = groep1.getHistorie().getDatumTijdRegistratie();
            final BrpDatumTijd tijd2 = groep2.getHistorie().getDatumTijdRegistratie();

            return tijd1.compareTo(tijd2);
        }
    }

}
