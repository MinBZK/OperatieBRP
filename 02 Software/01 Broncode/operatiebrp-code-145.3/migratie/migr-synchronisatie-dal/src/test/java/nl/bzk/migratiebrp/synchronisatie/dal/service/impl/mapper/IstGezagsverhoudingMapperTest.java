/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;

import org.junit.Before;
import org.junit.Test;

public final class IstGezagsverhoudingMapperTest extends AbstractIstMapperTest<BrpIstGezagsVerhoudingGroepInhoud> {
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
            new Partij("Bierum", "000008"),
            SoortAdministratieveHandeling.GBA_INITIELE_VULLING,
            new Timestamp(System.currentTimeMillis()));

    private IstGezagsverhoudingMapper mapper;
    private boolean heeftOnderzoek;
    private boolean heeftOnjuist;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        mapper = new IstGezagsverhoudingMapper(getDynamischeStamtabelRepository(), ADMINISTRATIEVE_HANDELING);
        heeftOnjuist = false;
        heeftOnderzoek = false;
    }

    /**
     * Geef de waarde van inhoud.
     * @return inhoud
     */
    private BrpIstGezagsVerhoudingGroepInhoud getInhoud() {
        return getInhoud(getStandaardBuilder());
    }

    private BrpIstGezagsVerhoudingGroepInhoud getInhoud(final BrpIstStandaardGroepInhoud.Builder standaardBuilder) {
        final BrpIstGezagsVerhoudingGroepInhoud.Builder inhoud = new BrpIstGezagsVerhoudingGroepInhoud.Builder(standaardBuilder.build());
        inhoud.indicatieDerdeHeeftGezag(new BrpBoolean(true, null));
        inhoud.indicatieOnderCuratele(new BrpBoolean(true, null));
        inhoud.indicatieOuder1HeeftGezag(new BrpBoolean(true, null));
        inhoud.indicatieOuder2HeeftGezag(new BrpBoolean(true, null));

        return inhoud.build();
    }

    @Test
    public void testMapIstOpPersoon() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstGezagsVerhoudingGroepInhoud> groep = maakGroep(getInhoud());
        groepen.add(groep);

        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpStapel = new BrpStapel<>(groepen);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Map<Lo3CategorieEnum, Map<Integer, Stapel>> stapelPerCategorie = runTest(brpStapel, persoon);

        assertPersoon(persoon);
        assertStapelsPerCategorie(stapelPerCategorie);
    }

    @Override
    void assertPersoon(final Persoon persoon) {
        final StapelVoorkomen voorkomen = assertStapelEnVoorkomen(persoon, getCategorie());
        assertStandaard(voorkomen);
        assertRelatie(voorkomen, false);
        assertHuwelijkOfGp(voorkomen, false, false);
        assertGezagsverhouding(voorkomen, true);
    }

    @Override
    Map<Lo3CategorieEnum, Map<Integer, Stapel>> runTest(final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpStapel, final Persoon persoon) {
        setupMockStandaard();
        return mapper.mapIstStapelOpPersoon(brpStapel, persoon);
    }

    @Test
    public void testOnjuistOnderzoek() {
        heeftOnderzoek = true;
        heeftOnjuist = true;

        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = getStandaardBuilder();

        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpIstGezagsVerhoudingGroepInhoud> groep = maakGroep(getInhoud(standaardBuilder));
        groepen.add(groep);

        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpStapel = new BrpStapel<>(groepen);
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
        return Lo3CategorieEnum.CATEGORIE_11;
    }

    @Override
    boolean heeftAkte() {
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

    @Override
    boolean heeftPredikaat() {
        return false;
    }

    @Override
    boolean heeftAdellijkeTitel() {
        return false;
    }
}
