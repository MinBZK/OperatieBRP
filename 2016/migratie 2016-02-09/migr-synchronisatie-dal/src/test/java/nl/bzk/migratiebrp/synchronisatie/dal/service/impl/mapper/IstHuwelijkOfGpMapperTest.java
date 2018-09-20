/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import org.junit.Before;
import org.junit.Test;

public final class IstHuwelijkOfGpMapperTest extends AbstractIstMapperTest<BrpIstHuwelijkOfGpGroepInhoud> {
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
        new Partij("Bierum", 8),
        SoortAdministratieveHandeling.GBA_INITIELE_VULLING);

    private IstHuwelijkOfGpMapper mapper;
    private boolean heeftAkte;
    private boolean heeftOnderzoek;
    private boolean heeftOnjuist;
    private boolean isSluiting;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        mapper = new IstHuwelijkOfGpMapper(getDynamischeStamtabelRepository(), ADMINISTRATIEVE_HANDELING);
        heeftAkte = true;
        heeftOnjuist = false;
        heeftOnderzoek = false;
        isSluiting = true;
    }

    /**
     * Geef de waarde van huwelijk builder.
     *
     * @return huwelijk builder
     */
    private BrpIstHuwelijkOfGpGroepInhoud.Builder getHuwelijkBuilder() {
        return getHuwelijkBuilder(getStandaardBuilder());
    }

    private BrpIstHuwelijkOfGpGroepInhoud.Builder getHuwelijkBuilder(final BrpIstStandaardGroepInhoud.Builder standaardBuilder) {
        final BrpIstStandaardGroepInhoud standaardGegevens = standaardBuilder.build();
        final BrpIstRelatieGroepInhoud.Builder relatieBuilder = new BrpIstRelatieGroepInhoud.Builder(standaardGegevens);
        relatieBuilder.anummer(new BrpLong(ANUMMER, null));
        relatieBuilder.bsn(new BrpInteger(BSN, null));
        relatieBuilder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(new BrpInteger(DATUM_GEBOORTE, null));
        relatieBuilder.datumGeboorte(new BrpInteger(DATUM_GEBOORTE, null));
        relatieBuilder.gemeenteCodeGeboorte(BRP_GEMEENTE);
        relatieBuilder.buitenlandsePlaatsGeboorte(new BrpString(BUITENLANDSE_PLAATS, null));
        relatieBuilder.omschrijvingLocatieGeboorte(new BrpString(OMSCHRIJVING_LOC, null));
        relatieBuilder.geslachtsaanduidingCode(GESLACHT_MAN);
        relatieBuilder.geslachtsnaamstam(new BrpString(GESLACHTSNAAMSTAM, null));
        relatieBuilder.landOfGebiedGeboorte(BRP_LAND_OF_GEBIED_CODE);
        relatieBuilder.scheidingsteken(new BrpCharacter(SCHEIDINGSTEKEN, null));
        relatieBuilder.voornamen(new BrpString(VOORNAMEN, null));
        relatieBuilder.voorvoegsel(new BrpString(VOORVOEGSEL, null));

        final BrpIstHuwelijkOfGpGroepInhoud.Builder inhoud = new BrpIstHuwelijkOfGpGroepInhoud.Builder(standaardGegevens, relatieBuilder.build());
        // Huwelijk gegevens
        inhoud.datumAanvang(new BrpInteger(DATUM_SLUITING, null));
        inhoud.gemeenteCodeAanvang(BRP_GEMEENTE);
        inhoud.buitenlandsePlaatsAanvang(new BrpString(BUITENLANDSE_PLAATS, null));
        inhoud.omschrijvingLocatieAanvang(new BrpString(OMSCHRIJVING_LOC, null));
        inhoud.landOfGebiedAanvang(BRP_LAND_OF_GEBIED_CODE);
        inhoud.soortRelatieCode(BrpSoortRelatieCode.HUWELIJK);

        return inhoud;
    }

    @Test
    public void testMapIstOpPersoon() {
        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> groep = maakGroep(getHuwelijkBuilder().build());
        groepen.add(groep);

        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel = new BrpStapel<>(groepen);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelsPerCategorie = runTest(brpStapel, persoon);

        assertPersoon(persoon);
        assertStapelsPerCategorie(stapelsPerCategorie);
    }

    @Override
    void assertPersoon(final Persoon persoon) {
        final StapelVoorkomen voorkomen = assertStapelEnVoorkomen(persoon, getCategorie());
        assertStandaard(voorkomen);
        assertRelatie(voorkomen, true);
        assertHuwelijkOfGp(voorkomen, true, isSluiting);
        assertGezagsverhouding(voorkomen, false);
    }

    @Override
    Map<Lo3CategorieEnum, Map<Integer, Stapel>> runTest(final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel, final Persoon persoon) {
        setupMockStandaard();
        setupMockRelaties();
        setupMockHuwelijk();
        return mapper.mapIstStapelOpPersoon(brpStapel, persoon);
    }

    @Test
    public void testOntbinding() {
        isSluiting = false;
        final BrpIstHuwelijkOfGpGroepInhoud.Builder inhoud = getHuwelijkBuilder();
        inhoud.datumAanvang(null);
        inhoud.gemeenteCodeAanvang(null);
        inhoud.buitenlandsePlaatsAanvang(null);
        inhoud.omschrijvingLocatieAanvang(null);
        inhoud.landOfGebiedAanvang(null);
        inhoud.datumEinde(new BrpInteger(DATUM_ONTBINDING, null));
        inhoud.gemeenteCodeEinde(BRP_GEMEENTE);
        inhoud.buitenlandsePlaatsEinde(new BrpString(BUITENLANDSE_PLAATS, null));
        inhoud.omschrijvingLocatieEinde(new BrpString(OMSCHRIJVING_LOC, null));
        inhoud.landOfGebiedEinde(BRP_LAND_OF_GEBIED_CODE);
        inhoud.redenBeeindigingRelatieCode(new BrpRedenEindeRelatieCode(REDEN_EINDE_CODE));

        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> groep = maakGroep(inhoud.build());
        groepen.add(groep);

        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel = new BrpStapel<>(groepen);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        runTest(brpStapel, persoon);

        assertPersoon(persoon);
    }

    @Test
    public void testDocumentOnjuistOnderzoek() {
        heeftAkte = false;
        heeftOnderzoek = true;
        heeftOnjuist = true;
        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = getStandaardBuilder();

        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> groep = maakGroep(getHuwelijkBuilder(standaardBuilder).build());
        groepen.add(groep);

        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel = new BrpStapel<>(groepen);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        runTest(brpStapel, persoon);

        assertPersoon(persoon);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractIstMapperTest#getCategorie()
     */
    @Override
    Lo3CategorieEnum getCategorie() {
        return Lo3CategorieEnum.CATEGORIE_05;
    }

    @Override
    boolean heeftAkte() {
        return heeftAkte;
    }

    @Override
    boolean heeftAdellijkeTitel() {
        return false;
    }

    @Override
    boolean heeftPredikaat() {
        return false;
    }

    @Override
    boolean heeftOnjuist() {
        return heeftOnjuist;
    }

    @Override
    boolean heeftOnderzoek() {
        return heeftOnderzoek;
    }
}
