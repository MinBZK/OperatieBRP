/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.Predikaat;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import org.junit.Before;
import org.junit.Test;

public class SamengesteldeNaamAfleidingTest {

    private SamengesteldeNaamAfleiding samengesteldeNaamAfleiding;

    @Before
    public void init() {
        samengesteldeNaamAfleiding = new SamengesteldeNaamAfleiding();
    }

    @Test
    public void testSamengesteldeNaamAfleidingMet1VoornaamEn1GeslachtsnaamComp() {
        final Persoon persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
                maakPersoonGeslachtsnaamcomponent("Chougna", "del", AdellijkeTitel.BARON, Predikaat.HOOGHEID, "-")
        );
        persoon.getPersoonVoornamen().add(
                maakPersoonVoorNaam("Oussama", 1)
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        checkResultaat(
                persoon.getSamengesteldenaam(), AdellijkeTitel.BARON, Predikaat.HOOGHEID, "Chougna", "del", "-", "Oussama");
    }

    @Test
    public void testSamengesteldeNaamAfleidingMet2VoornamenEn1GeslachtsnaamComp() {
        final Persoon persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
                maakPersoonGeslachtsnaamcomponent("Chougna", "del", AdellijkeTitel.BARON, Predikaat.HOOGHEID, "-")
        );
        persoon.getPersoonVoornamen().add(
                maakPersoonVoorNaam("Jansen", 2)
        );
        persoon.getPersoonVoornamen().add(
                maakPersoonVoorNaam("Oussama", 1)
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        checkResultaat(
                persoon.getSamengesteldenaam(), AdellijkeTitel.BARON, Predikaat.HOOGHEID, "Chougna", "del", "-", "Oussama Jansen");
    }

    @Test
    public void testSamengesteldeNaamAfleidingZonderVoornamenEn1GeslachtsnaamComp() {
        final Persoon persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
                maakPersoonGeslachtsnaamcomponent("Chougna", "del", AdellijkeTitel.BARON, Predikaat.HOOGHEID, "-")
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        checkResultaat(
                persoon.getSamengesteldenaam(), AdellijkeTitel.BARON, Predikaat.HOOGHEID, "Chougna", "del", "-", null);
    }

    @Test
    public void testSamengesteldeNaamAfleidingZonderVoornamenEnZonderGeslachtsnaamComp() {
        Persoon persoon = maakStandaardPersoon();
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        Assert.assertNull(persoon.getSamengesteldenaam());
    }

    private void checkResultaat(final PersoonSamengesteldeNaam resultaat,
                                final AdellijkeTitel titel, final Predikaat predikaat, final String geslNaam,
                                final String voorvoegsel, final String scheidingsteken, final String voornamen)
    {
        Assert.assertEquals(titel, resultaat.getAdellijkeTitel());
        Assert.assertEquals(predikaat, resultaat.getPredikaat());
        Assert.assertEquals(geslNaam, resultaat.getGeslachtsnaam());
        Assert.assertEquals(voorvoegsel, resultaat.getVoorvoegsel());
        Assert.assertEquals(scheidingsteken, resultaat.getScheidingsTeken());
        Assert.assertEquals(voornamen, resultaat.getVoornamen());
        Assert.assertTrue(resultaat.getIndAlgoritmischAfgeleid());
        Assert.assertFalse(resultaat.getIndNamenreeksAlsGeslachtsnaam());
    }

    private Persoon maakStandaardPersoon() {
        return new Persoon();
    }

    private PersoonGeslachtsnaamcomponent maakPersoonGeslachtsnaamcomponent(final String naam,
                                                                            final String voorvoegsel,
                                                                            final AdellijkeTitel adellijkeTitel,
                                                                            final Predikaat predikaat,
                                                                            final String scheidingsteken)
    {
        final PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent();
        comp.setNaam(naam);
        comp.setVoorvoegsel(voorvoegsel);
        comp.setAdellijkeTitel(adellijkeTitel);
        comp.setPredikaat(predikaat);
        comp.setScheidingsTeken(scheidingsteken);
        return comp;
    }

    private PersoonVoornaam maakPersoonVoorNaam(final String naam, final Integer volgnummer) {
        final PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setNaam(naam);
        voornaam.setVolgnummer(volgnummer);
        return voornaam;
    }
}
