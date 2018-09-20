/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonModelTest {

    @Test
    public void testGetBetrokkenPersonenInRelaties() {
        PersoonModel ouder1 = this.maakPersoon(1);
        PersoonModel ouder2 = this.maakPersoon(2);
        PersoonModel kind = this.maakPersoon(3);

        // Zonder betrokkenheden geen indirect betrokken personen.
        Assert.assertTrue(ouder1.getBetrokkenPersonenInRelaties().isEmpty());

        maakFamilierechtelijkeBetrekking(ouder1, ouder2, kind);

        // Twee indirect betrokken personen in de familierechtelijke betrekking.
        Assert.assertEquals(2, ouder1.getBetrokkenPersonenInRelaties().size());
        Assert.assertEquals(2, ouder2.getBetrokkenPersonenInRelaties().size());
        Assert.assertEquals(2, kind.getBetrokkenPersonenInRelaties().size());

        // Niet de persoon zelf in de indirect betrokken personen.
        assertFalse(ouder1.getBetrokkenPersonenInRelaties().contains(ouder1));
        assertFalse(ouder2.getBetrokkenPersonenInRelaties().contains(ouder2));
        assertFalse(kind.getBetrokkenPersonenInRelaties().contains(kind));
    }

    @Test
    public void testHeeftBetrokkenheden() {
        PersoonModel ouder1 = this.maakPersoon(1);
        PersoonModel ouder2 = this.maakPersoon(2);
        PersoonModel kind = this.maakPersoon(3);
        assertFalse(ouder1.heeftBetrokkenheden());
        assertFalse(ouder2.heeftBetrokkenheden());
        assertFalse(kind.heeftBetrokkenheden());

        maakFamilierechtelijkeBetrekking(ouder1, ouder2, kind);
        Assert.assertTrue(ouder1.heeftBetrokkenheden());
        Assert.assertTrue(ouder2.heeftBetrokkenheden());
        Assert.assertTrue(kind.heeftBetrokkenheden());
    }

    @Test
    public void testHeeftVoornamen() {
        PersoonModel persoonModel = this.maakPersoon(1);
        assertFalse(persoonModel.heeftVoornamen());

        Set<PersoonVoornaamModel> voornamen = new TreeSet<>(new Comparator<PersoonVoornaamModel>() {
            @Override
            public int compare(final PersoonVoornaamModel persoonVoornaamModel, final PersoonVoornaamModel t1) {
                return 0;
            }
        });
        ReflectionTestUtils.setField(persoonModel, "voornamen", voornamen);
        assertFalse(persoonModel.heeftVoornamen());

        voornamen.add(new PersoonVoornaamModel());
        ReflectionTestUtils.setField(persoonModel, "voornamen", voornamen);
        assertTrue(persoonModel.heeftVoornamen());
    }

    @Test
    public void testHeeftGeslachtsnaamComponenten() {
        PersoonModel persoonModel = this.maakPersoon(1);
        assertFalse(persoonModel.heeftGeslachtsnaamcomponenten());

        Set<PersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponenten = new TreeSet<>(new Comparator<PersoonGeslachtsnaamcomponentModel>() {
            @Override
            public int compare(final PersoonGeslachtsnaamcomponentModel t1, final PersoonGeslachtsnaamcomponentModel t2) {
                return 0;
            }
        });
        ReflectionTestUtils.setField(persoonModel, "geslachtsnaamcomponenten", geslachtsnaamcomponenten);
        assertFalse(persoonModel.heeftGeslachtsnaamcomponenten());

        geslachtsnaamcomponenten.add(new PersoonGeslachtsnaamcomponentModel());
        ReflectionTestUtils.setField(persoonModel, "geslachtsnaamcomponenten", geslachtsnaamcomponenten);
        assertTrue(persoonModel.heeftGeslachtsnaamcomponenten());
    }

    @Test
    public void testHeeftNationaliteiten() {
        PersoonModel persoonModel = this.maakPersoon(1);
        assertFalse(persoonModel.heeftNationaliteiten());

        Set<PersoonNationaliteitModel> nationaliteiten = new TreeSet<>(new Comparator<PersoonNationaliteitModel>() {
            @Override
            public int compare(final PersoonNationaliteitModel t1, final PersoonNationaliteitModel t2) {
                return 0;
            }
        });
        ReflectionTestUtils.setField(persoonModel, "nationaliteiten", nationaliteiten);
        assertFalse(persoonModel.heeftNationaliteiten());

        nationaliteiten.add(new PersoonNationaliteitModel());
        ReflectionTestUtils.setField(persoonModel, "nationaliteiten", nationaliteiten);
        assertTrue(persoonModel.heeftNationaliteiten());
    }

    @Test
    public void testHeeftActueleNederlandscheNationaliteit() {
        PersoonModel persoonModel = this.maakPersoon(1);
        assertFalse(persoonModel.heeftNederlandseNationaliteit());
        assertFalse(persoonModel.heeftActueleNederlandseNationaliteit());

        Set<PersoonNationaliteitModel> nationaliteiten = new TreeSet<>(new Comparator<PersoonNationaliteitModel>() {
            @Override
            public int compare(final PersoonNationaliteitModel t1, final PersoonNationaliteitModel t2) {
                return 0;
            }
        });
        ReflectionTestUtils.setField(persoonModel, "nationaliteiten", nationaliteiten);
        assertFalse(persoonModel.heeftNederlandseNationaliteit());
        assertFalse(persoonModel.heeftActueleNederlandseNationaliteit());

        PersoonNationaliteitModel persoonNationaliteitModel = new PersoonNationaliteitModel();
        NationaliteitAttribuut nationaliteitcodeAttribuut = new NationaliteitAttribuut(new Nationaliteit(new NationaliteitcodeAttribuut("0042"), null, null, null));
        ReflectionTestUtils.setField(persoonNationaliteitModel, "nationaliteit", nationaliteitcodeAttribuut);

        nationaliteiten.add(persoonNationaliteitModel);
        ReflectionTestUtils.setField(persoonModel, "nationaliteiten", nationaliteiten);

        assertFalse(persoonModel.heeftNederlandseNationaliteit());
        assertFalse(persoonModel.heeftActueleNederlandseNationaliteit());

        persoonNationaliteitModel = new PersoonNationaliteitModel();
        nationaliteitcodeAttribuut = new NationaliteitAttribuut(new Nationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, null, null, null));
        ReflectionTestUtils.setField(persoonNationaliteitModel, "nationaliteit", nationaliteitcodeAttribuut);

        nationaliteiten.clear();
        nationaliteiten.add(persoonNationaliteitModel);
        ReflectionTestUtils.setField(persoonModel, "nationaliteiten", nationaliteiten);
        assertTrue(persoonModel.heeftNederlandseNationaliteit());
        assertTrue(persoonModel.heeftActueleNederlandseNationaliteit());
    }

    private FamilierechtelijkeBetrekkingModel maakFamilierechtelijkeBetrekking(final PersoonModel ouder1,
            final PersoonModel ouder2, final PersoonModel kind)
    {
        FamilierechtelijkeBetrekkingModel relatie = new FamilierechtelijkeBetrekkingModel();
        BetrokkenheidModel betrokkenheid1 = new OuderModel(relatie, ouder1);
        ReflectionTestUtils.setField(betrokkenheid1, "iD", 100);
        ouder1.getBetrokkenheden().add(betrokkenheid1);
        BetrokkenheidModel betrokkenheid2 = new OuderModel(relatie, ouder2);
        ReflectionTestUtils.setField(betrokkenheid2, "iD", 101);
        ouder2.getBetrokkenheden().add(betrokkenheid2);
        BetrokkenheidModel betrokkenheid3 = new KindModel(relatie, kind);
        ReflectionTestUtils.setField(betrokkenheid3, "iD", 102);
        kind.getBetrokkenheden().add(betrokkenheid3);
        relatie.getBetrokkenheden().addAll(Arrays.asList(betrokkenheid1, betrokkenheid2, betrokkenheid3));
        return relatie;
    }

    public PersoonModel maakPersoon(final int id) {
        PersoonModel persoon = new PersoonModel();
        ReflectionTestUtils.setField(persoon, "iD", id);
        return persoon;
    }

}
