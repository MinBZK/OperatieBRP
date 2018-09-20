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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
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

public final class IstRelatieMapperTest extends AbstractIstMapperTest<BrpIstRelatieGroepInhoud> {
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
        new Partij("Bierum", 8),
        SoortAdministratieveHandeling.GBA_INITIELE_VULLING);

    private IstRelatieMapper mapper;
    private boolean heeftAkte;
    private boolean heeftPredicaat;
    private boolean heeftAdelijkeTitel;
    private boolean heeftOnderzoek;
    private boolean heeftOnjuist;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        mapper = new IstRelatieMapper(getDynamischeStamtabelRepository(), ADMINISTRATIEVE_HANDELING);

        heeftAdelijkeTitel = false;
        heeftPredicaat = false;
        heeftAkte = true;
        heeftOnjuist = false;
        heeftOnderzoek = false;
    }

    /**
     * Geef de waarde van relatie builder.
     *
     * @return relatie builder
     */
    private BrpIstRelatieGroepInhoud.Builder getRelatieBuilder() {
        return getRelatieBuilder(getStandaardBuilder());
    }

    private BrpIstRelatieGroepInhoud.Builder getRelatieBuilder(final BrpIstStandaardGroepInhoud.Builder standaardBuilder) {
        final BrpIstRelatieGroepInhoud.Builder inhoud = new BrpIstRelatieGroepInhoud.Builder(standaardBuilder.build());

        inhoud.anummer(new BrpLong(ANUMMER, null));
        inhoud.bsn(new BrpInteger(BSN, null));
        inhoud.rubriek6210DatumIngangFamilierechtelijkeBetrekking(new BrpInteger(DATUM_GEBOORTE, null));
        inhoud.datumGeboorte(new BrpInteger(DATUM_GEBOORTE, null));
        inhoud.gemeenteCodeGeboorte(BRP_GEMEENTE);
        inhoud.buitenlandsePlaatsGeboorte(new BrpString(BUITENLANDSE_PLAATS, null));
        inhoud.omschrijvingLocatieGeboorte(new BrpString(OMSCHRIJVING_LOC, null));
        inhoud.geslachtsaanduidingCode(GESLACHT_MAN);
        inhoud.geslachtsnaamstam(new BrpString(GESLACHTSNAAMSTAM, null));
        inhoud.landOfGebiedGeboorte(BRP_LAND_OF_GEBIED_CODE);
        inhoud.scheidingsteken(new BrpCharacter(SCHEIDINGSTEKEN, null));
        inhoud.voornamen(new BrpString(VOORNAMEN, null));
        inhoud.voorvoegsel(new BrpString(VOORVOEGSEL, null));

        return inhoud;
    }

    @Test
    public void testMapIstOpPersoon() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstRelatieGroepInhoud> groep = maakGroep(getRelatieBuilder().build());
        groepen.add(groep);

        final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = new BrpStapel<>(groepen);
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
        assertHuwelijkOfGp(voorkomen, false, false);
        assertGezagsverhouding(voorkomen, false);
    }

    @Override
    Map<Lo3CategorieEnum, Map<Integer, Stapel>> runTest(final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel, final Persoon persoon) {
        setupMockStandaard();
        setupMockRelaties();
        return mapper.mapIstStapelOpPersoon(brpStapel, persoon);

    }

    @Test
    public void testAdellijkTitel() {
        heeftAdelijkeTitel = true;
        final BrpIstRelatieGroepInhoud.Builder inhoud = getRelatieBuilder();
        inhoud.adellijkeTitel(getAdellijkeTitel());
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstRelatieGroepInhoud> groep = maakGroep(inhoud.build());
        groepen.add(groep);

        final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = new BrpStapel<>(groepen);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        runTest(brpStapel, persoon);

        assertPersoon(persoon);
    }

    @Test
    public void testPredicaat() {
        heeftPredicaat = true;
        final BrpIstRelatieGroepInhoud.Builder inhoud = getRelatieBuilder();
        inhoud.predicaat(getPredicaat());
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstRelatieGroepInhoud> groep = maakGroep(inhoud.build());
        groepen.add(groep);

        final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = new BrpStapel<>(groepen);
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

        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstRelatieGroepInhoud> groep = maakGroep(getRelatieBuilder(standaardBuilder).build());
        groepen.add(groep);

        final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel = new BrpStapel<>(groepen);
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
        return Lo3CategorieEnum.CATEGORIE_02;
    }

    @Override
    boolean heeftAkte() {
        return heeftAkte;
    }

    @Override
    boolean heeftAdellijkeTitel() {
        return heeftAdelijkeTitel;
    }

    @Override
    boolean heeftPredikaat() {
        return heeftPredicaat;
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
