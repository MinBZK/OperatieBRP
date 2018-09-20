/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractIstMapperTest<T extends AbstractBrpIstGroepInhoud> {

    static final int STAPEL_ID = 0;
    static final int VOORKOMEN_ID = 0;
    static final String ADELLIJKE_TITEL_CODE = "B";
    static final String PREDICAAT_CODE = "J";
    static final String VOORNAMEN = "Piet";
    static final String VOORVOEGSEL = "van";
    static final Character SCHEIDINGSTEKEN = ' ';
    static final String GESLACHTSNAAMSTAM = "Klaveren";
    static final String AKTENUMMER = "A350";
    static final short BRP_GEMEENTE_CODE = (short) 518;
    static final int BRP_PARTIJ_CODE = 51801;
    static final int MIGRATIEVOORZIENING_PARTIJ_CODE = 199902;
    static final short BRP_LAND_OF_GEBIED_CODE_NL = (short) 6030;
    static final Long ANUMMER = 1234597890L;
    static final Integer BSN = 123456789;
    static final Integer DATUM_OPNEMING = 20120102;
    static final Integer DATUM_GELDIGHEID = 20120101;
    static final Integer DATUM_GEBOORTE = 1980101;
    static final Integer DATUM_SLUITING = 20120101;
    static final Integer DATUM_ONTBINDING = 20120501;
    static final Character REDEN_EINDE_CODE = 'S';
    static final Integer DATUM_DOCUMENT = 20120103;
    static final String DOCUMENT_OMSCHRIJVING = "omschrijving";
    static final Integer GEGEVENS_IN_ONDERZOEK = 020110;
    static final Integer DATUM_INGANG_ONDERZOEK = 20120202;
    static final Integer DATUM_EINDE_ONDERZOEK = 20120203;
    static final Character ONJUIST = 'O';
    static final String BUITENLANDSE_PLAATS = "Buitenlandse plaats";
    static final String OMSCHRIJVING_LOC = "Omschrijving locatie";

    // BRP conversie model.
    static final BrpGeslachtsaanduidingCode GESLACHT_MAN = BrpGeslachtsaanduidingCode.MAN;
    static final BrpGemeenteCode BRP_GEMEENTE = new BrpGemeenteCode(BRP_GEMEENTE_CODE);
    static final BrpLandOfGebiedCode BRP_LAND_OF_GEBIED_CODE = new BrpLandOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL);
    static final BrpPartijCode BRP_PARTIJ = new BrpPartijCode(BRP_PARTIJ_CODE);

    // Mock entities
    private static final SoortDocument SOORT_DOCUMENT_AKTE =
            new SoortDocument(
                "Geboorteakte",
                "Geboortakte, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a, dan wel artikel 2.8 lid 2 sub a");
    private static final SoortDocument SOORT_DOCUMENT_DOCUMENT = new SoortDocument("Overig", "Overig");
    private static final Partij PARTIJ = new Partij("Gemeente \'s-Gravenhage", BRP_PARTIJ_CODE);
    private static final Partij PARTIJ_MIGRATIEVOORZIENING = new Partij("Migratievoorziening", MIGRATIEVOORZIENING_PARTIJ_CODE);
    private static final Gemeente GEMEENTE = new Gemeente((short) 0, "\'s-Gravenhage", BRP_GEMEENTE_CODE, PARTIJ);
    private static final LandOfGebied LAND_OF_GEBIED = new LandOfGebied(BRP_LAND_OF_GEBIED_CODE_NL, "Nederland");
    private static final Predicaat PREDIKAAT = Predicaat.J;
    private static final AdellijkeTitel ADELLIJKE_TITEL = AdellijkeTitel.B;
    private static final Geslachtsaanduiding GESLACHTS_AANDUIDING = Geslachtsaanduiding.MAN;
    private static final SoortRelatie SOORT_RELATIE = SoortRelatie.HUWELIJK;
    private static final RedenBeeindigingRelatie REDEN_EINDE_RELATIE = new RedenBeeindigingRelatie(REDEN_EINDE_CODE, "Scheiding");

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    private BrpAdellijkeTitelCode adellijkeTitel;
    private BrpPredicaatCode predikaat;

    @Before
    public void setUp() {
        adellijkeTitel = new BrpAdellijkeTitelCode(ADELLIJKE_TITEL_CODE);
        adellijkeTitel.setGeslachtsaanduiding(GESLACHT_MAN);

        predikaat = new BrpPredicaatCode(PREDICAAT_CODE);
        predikaat.setGeslachtsaanduiding(GESLACHT_MAN);
    }

    public void setupMockStandaard() {
        if (heeftAkte()) {
            when(dynamischeStamtabelRepository.getSoortDocumentByNaam(SOORT_DOCUMENT_AKTE.getNaam())).thenReturn(SOORT_DOCUMENT_AKTE);
        } else {
            when(dynamischeStamtabelRepository.getSoortDocumentByNaam(BrpSoortDocumentCode.HISTORIE_CONVERSIE.getWaarde())).thenReturn(SOORT_DOCUMENT_DOCUMENT);
        }
        when(dynamischeStamtabelRepository.getPartijByCode(BRP_PARTIJ.getWaarde())).thenReturn(PARTIJ);
        when(dynamischeStamtabelRepository.getPartijByCode(BRP_PARTIJ.getWaarde())).thenReturn(PARTIJ);
        when(dynamischeStamtabelRepository.getPartijByCode(MIGRATIEVOORZIENING_PARTIJ_CODE)).thenReturn(PARTIJ_MIGRATIEVOORZIENING);
    }

    public void setupMockRelaties() {
        when(dynamischeStamtabelRepository.getGemeenteByGemeentecode(BRP_GEMEENTE_CODE)).thenReturn(GEMEENTE);
        when(dynamischeStamtabelRepository.getLandOfGebiedByCode(BRP_LAND_OF_GEBIED_CODE_NL)).thenReturn(LAND_OF_GEBIED);

    }

    public void setupMockHuwelijk() {
        when(dynamischeStamtabelRepository.getGemeenteByGemeentecode(BRP_GEMEENTE_CODE)).thenReturn(GEMEENTE);
        when(dynamischeStamtabelRepository.getLandOfGebiedByCode(BRP_LAND_OF_GEBIED_CODE_NL)).thenReturn(LAND_OF_GEBIED);
        when(dynamischeStamtabelRepository.getRedenBeeindigingRelatieByCode(REDEN_EINDE_CODE)).thenReturn(REDEN_EINDE_RELATIE);
    }

    BrpGroep<T> maakGroep(final T inhoud) {
        final BrpHistorie historie = new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null);
        return new BrpGroep<>(inhoud, historie, null, null, null);
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    BrpAdellijkeTitelCode getAdellijkeTitel() {
        return adellijkeTitel;
    }

    abstract boolean heeftAkte();

    abstract boolean heeftPredikaat();

    abstract boolean heeftAdellijkeTitel();

    abstract boolean heeftOnderzoek();

    abstract boolean heeftOnjuist();

    abstract Map<Lo3CategorieEnum, Map<Integer, Stapel>> runTest(BrpStapel<T> brpStapel, Persoon persoon);

    abstract void assertPersoon(Persoon persoon);

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    abstract Lo3CategorieEnum getCategorie();

    /**
     * Geef de waarde van standaard builder.
     *
     * @return standaard builder
     */
    public BrpIstStandaardGroepInhoud.Builder getStandaardBuilder() {
        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = new BrpIstStandaardGroepInhoud.Builder(getCategorie(), 0, VOORKOMEN_ID);
        standaardBuilder.partij(BRP_PARTIJ);
        if (heeftAkte()) {
            // BrpSoortDocumentCode akte = new BrpSoortDocumentCode(naam);
            // akte.
            standaardBuilder.soortDocument(new BrpSoortDocumentCode("Geboorteakte"));
            standaardBuilder.aktenummer(new BrpString(AKTENUMMER, null));
        } else {
            standaardBuilder.soortDocument(BrpSoortDocumentCode.HISTORIE_CONVERSIE);
            standaardBuilder.rubriek8220DatumDocument(new BrpInteger(DATUM_DOCUMENT, null));
            standaardBuilder.documentOmschrijving(new BrpString(DOCUMENT_OMSCHRIJVING, null));
        }

        if (heeftOnderzoek()) {
            standaardBuilder.rubriek8310AanduidingGegevensInOnderzoek(new BrpInteger(GEGEVENS_IN_ONDERZOEK, null));
            standaardBuilder.rubriek8320DatumIngangOnderzoek(new BrpInteger(DATUM_INGANG_ONDERZOEK, null));
            standaardBuilder.rubriek8330DatumEindeOnderzoek(new BrpInteger(DATUM_EINDE_ONDERZOEK, null));
        }

        if (heeftOnjuist()) {
            standaardBuilder.rubriek8410OnjuistOfStrijdigOpenbareOrde(new BrpCharacter(ONJUIST, null));
        }

        standaardBuilder.rubriek8510IngangsdatumGeldigheid(new BrpInteger(DATUM_GELDIGHEID, null));
        standaardBuilder.rubriek8610DatumVanOpneming(new BrpInteger(DATUM_OPNEMING, null));
        return standaardBuilder;
    }

    StapelVoorkomen assertStapelEnVoorkomen(final Persoon persoon, final Lo3CategorieEnum categorie) {
        final Set<Stapel> stapels = persoon.getStapels();
        assertNotNull(stapels);
        assertFalse(stapels.isEmpty());
        assertTrue(stapels.size() == 1);

        final Stapel stapel = stapels.iterator().next();
        assertEquals(categorie.getCategorie(), stapel.getCategorie());
        assertTrue(stapel.getVolgnummer() == STAPEL_ID);
        final Set<StapelVoorkomen> voorkomens = stapel.getStapelvoorkomens();
        assertNotNull(voorkomens);
        assertFalse(voorkomens.isEmpty());
        assertTrue(stapel.getStapelvoorkomens().size() == 1);

        return stapel.getStapelvoorkomens().iterator().next();
    }

    void assertStandaard(final StapelVoorkomen voorkomen) {
        assertEquals(VOORKOMEN_ID, voorkomen.getVolgnummer());
        assertEquals(PARTIJ, voorkomen.getPartij());
        if (heeftAkte()) {
            assertEquals(SOORT_DOCUMENT_AKTE, voorkomen.getSoortDocument());
            assertEquals(AKTENUMMER, voorkomen.getAktenummer());
        } else {
            assertEquals(SOORT_DOCUMENT_DOCUMENT, voorkomen.getSoortDocument());
            assertEquals(DATUM_DOCUMENT, voorkomen.getRubriek8220DatumDocument());
            assertEquals(DOCUMENT_OMSCHRIJVING, voorkomen.getDocumentOmschrijving());
        }
        if (heeftOnderzoek()) {
            assertEquals(GEGEVENS_IN_ONDERZOEK, voorkomen.getRubriek8310AanduidingGegevensInOnderzoek());
            assertEquals(DATUM_INGANG_ONDERZOEK, voorkomen.getRubriek8320DatumIngangOnderzoek());
            assertEquals(DATUM_EINDE_ONDERZOEK, voorkomen.getRubriek8330DatumEindeOnderzoek());
        } else {
            assertNull(voorkomen.getRubriek8310AanduidingGegevensInOnderzoek());
            assertNull(voorkomen.getRubriek8320DatumIngangOnderzoek());
            assertNull(voorkomen.getRubriek8330DatumEindeOnderzoek());
        }
        if (heeftOnjuist()) {
            assertEquals(ONJUIST, voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
        } else {
            assertNull(voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
        }
        assertEquals(DATUM_GELDIGHEID, voorkomen.getRubriek8510IngangsdatumGeldigheid());
        assertEquals(DATUM_OPNEMING, voorkomen.getRubriek8610DatumVanOpneming());
    }

    void assertRelatie(final StapelVoorkomen voorkomen, final boolean relatie) {
        if (relatie) {
            assertEquals(DATUM_GEBOORTE, voorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking());
            assertEquals(ANUMMER, voorkomen.getAnummer());
            assertEquals(BSN, voorkomen.getBsn());
            assertEquals(VOORNAMEN, voorkomen.getVoornamen());
            if (heeftPredikaat() || heeftAdellijkeTitel()) {
                assertEquals(GESLACHTS_AANDUIDING, voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat());
                if (heeftPredikaat()) {
                    assertEquals(PREDIKAAT, voorkomen.getPredicaat());
                } else {
                    assertNull(voorkomen.getPredicaat());
                }
                if (heeftAdellijkeTitel()) {
                    assertEquals(ADELLIJKE_TITEL, voorkomen.getAdellijkeTitel());
                } else {
                    assertNull(voorkomen.getAdellijkeTitel());
                }
            } else {
                assertNull(voorkomen.getPredicaat());
                assertNull(voorkomen.getAdellijkeTitel());
                assertNull(voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat());
            }
            assertEquals(VOORVOEGSEL, voorkomen.getVoorvoegsel());
            assertEquals(SCHEIDINGSTEKEN, voorkomen.getScheidingsteken());
            assertEquals(GESLACHTSNAAMSTAM, voorkomen.getGeslachtsnaamstam());
            assertEquals(DATUM_GEBOORTE, voorkomen.getDatumGeboorte());
            assertEquals(GEMEENTE, voorkomen.getGemeenteGeboorte());
            assertEquals(BUITENLANDSE_PLAATS, voorkomen.getBuitenlandsePlaatsGeboorte());
            assertEquals(OMSCHRIJVING_LOC, voorkomen.getOmschrijvingLocatieGeboorte());
            assertEquals(LAND_OF_GEBIED, voorkomen.getLandOfGebiedGeboorte());
            assertEquals(GESLACHTS_AANDUIDING, voorkomen.getGeslachtsaanduiding());
        } else {
            assertNull(voorkomen.getRubriek6210DatumIngangFamilierechtelijkeBetrekking());
            assertNull(voorkomen.getAnummer());
            assertNull(voorkomen.getBsn());
            assertNull(voorkomen.getVoornamen());
            assertNull(voorkomen.getPredicaat());
            assertNull(voorkomen.getAdellijkeTitel());
            assertNull(voorkomen.getGeslachtsaanduidingBijAdellijkeTitelOfPredikaat());
            assertNull(voorkomen.getVoorvoegsel());
            assertNull(voorkomen.getScheidingsteken());
            assertNull(voorkomen.getGeslachtsnaamstam());
            assertNull(voorkomen.getDatumGeboorte());
            assertNull(voorkomen.getGemeenteGeboorte());
            assertNull(voorkomen.getBuitenlandsePlaatsGeboorte());
            assertNull(voorkomen.getOmschrijvingLocatieGeboorte());
            assertNull(voorkomen.getLandOfGebiedGeboorte());
            assertNull(voorkomen.getGeslachtsaanduiding());
        }
    }

    void assertHuwelijkOfGp(final StapelVoorkomen voorkomen, final boolean huwelijkofGp, final boolean sluiting) {
        if (huwelijkofGp) {
            if (sluiting) {
                assertEquals(DATUM_SLUITING, voorkomen.getDatumAanvang());
                assertEquals(GEMEENTE, voorkomen.getGemeenteAanvang());
                assertEquals(BUITENLANDSE_PLAATS, voorkomen.getBuitenlandsePlaatsAanvang());
                assertEquals(OMSCHRIJVING_LOC, voorkomen.getOmschrijvingLocatieAanvang());
                assertEquals(LAND_OF_GEBIED, voorkomen.getLandOfGebiedAanvang());
                assertNull(voorkomen.getRedenBeeindigingRelatie());
                assertNull(voorkomen.getDatumEinde());
                assertNull(voorkomen.getGemeenteEinde());
                assertNull(voorkomen.getBuitenlandsePlaatsEinde());
                assertNull(voorkomen.getOmschrijvingLocatieEinde());
                assertNull(voorkomen.getLandOfGebiedEinde());
            } else {
                assertNull(voorkomen.getDatumAanvang());
                assertNull(voorkomen.getGemeenteAanvang());
                assertNull(voorkomen.getBuitenlandsePlaatsAanvang());
                assertNull(voorkomen.getOmschrijvingLocatieAanvang());
                assertNull(voorkomen.getLandOfGebiedAanvang());
                assertEquals(REDEN_EINDE_RELATIE, voorkomen.getRedenBeeindigingRelatie());
                assertEquals(DATUM_ONTBINDING, voorkomen.getDatumEinde());
                assertEquals(GEMEENTE, voorkomen.getGemeenteEinde());
                assertEquals(BUITENLANDSE_PLAATS, voorkomen.getBuitenlandsePlaatsEinde());
                assertEquals(OMSCHRIJVING_LOC, voorkomen.getOmschrijvingLocatieEinde());
                assertEquals(LAND_OF_GEBIED, voorkomen.getLandOfGebiedEinde());
            }
            assertEquals(SOORT_RELATIE, voorkomen.getSoortRelatie());
        } else {
            assertNull(voorkomen.getDatumAanvang());
            assertNull(voorkomen.getGemeenteAanvang());
            assertNull(voorkomen.getBuitenlandsePlaatsAanvang());
            assertNull(voorkomen.getOmschrijvingLocatieAanvang());
            assertNull(voorkomen.getLandOfGebiedAanvang());
            assertNull(voorkomen.getRedenBeeindigingRelatie());
            assertNull(voorkomen.getDatumEinde());
            assertNull(voorkomen.getGemeenteEinde());
            assertNull(voorkomen.getBuitenlandsePlaatsEinde());
            assertNull(voorkomen.getOmschrijvingLocatieEinde());
            assertNull(voorkomen.getLandOfGebiedEinde());
            assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, voorkomen.getSoortRelatie());
        }
    }

    void assertGezagsverhouding(final StapelVoorkomen voorkomen, final boolean gezagsVerhouding) {
        if (gezagsVerhouding) {
            assertTrue(voorkomen.getIndicatieDerdeHeeftGezag());
            assertTrue(voorkomen.getIndicatieOnderCuratele());
            assertTrue(voorkomen.getIndicatieOuder1HeeftGezag());
            assertTrue(voorkomen.getIndicatieOuder2HeeftGezag());
        } else {
            assertNull(voorkomen.getIndicatieDerdeHeeftGezag());
            assertNull(voorkomen.getIndicatieOnderCuratele());
            assertNull(voorkomen.getIndicatieOuder1HeeftGezag());
            assertNull(voorkomen.getIndicatieOuder2HeeftGezag());
        }
    }

    void assertStapelsPerCategorie(final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie) {
        assertTrue(stapelsPerCategorie.containsKey(getCategorie()));
        final Map<Integer, Stapel> stapels = stapelsPerCategorie.get(getCategorie());
        assertTrue(stapels.containsKey(0));
        final Stapel stapel = stapels.get(0);

        assertEquals(getCategorie().getCategorie(), stapel.getCategorie());
        assertEquals(0, stapel.getVolgnummer());
    }

    /**
     * Geef de waarde van predicaat.
     *
     * @return predicaat
     */
    BrpPredicaatCode getPredicaat() {
        return predikaat;
    }

    /**
     * Geef de waarde van dynamische stamtabel repository.
     *
     * @return dynamische stamtabel repository
     */
    public DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return dynamischeStamtabelRepository;
    }
}
