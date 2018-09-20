/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamCompStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamComponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class SamengesteldeNaamAfleidingTest {

    private SamengesteldeNaamAfleiding samengesteldeNaamAfleiding;

    private AdellijkeTitel baron;
    private Predikaat hoogheid;

    @Before
    public void init() {
        samengesteldeNaamAfleiding = new SamengesteldeNaamAfleiding();
        baron = new AdellijkeTitel();
        ReflectionTestUtils.setField(baron, "adellijkeTitelCode", new AdellijkeTitelCode("B"));
        hoogheid = new Predikaat();
        ReflectionTestUtils.setField(hoogheid, "code", new PredikaatCode("H"));
    }

    @Test
    public void testSamengesteldeNaamAfleidingMet1VoornaamEn1GeslachtsnaamComp() {
        final PersoonBericht persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
                maakPersoonGeslachtsnaamcomponent("Chougna", "del", baron, hoogheid, "-")
        );

        persoon.getPersoonVoornaam().add(
                maakPersoonVoorNaam("Oussama", 1)
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        checkResultaat(
                persoon.getSamengesteldeNaam(),
                baron,
                hoogheid,
                new GeslachtsnaamComponent("Chougna"),
                new Voorvoegsel("del"),
                new ScheidingsTeken("-"),
                new Voornaam("Oussama"));
    }

    @Test
    public void testSamengesteldeNaamAfleidingMet2VoornamenEn1GeslachtsnaamComp() {
        final PersoonBericht persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
                maakPersoonGeslachtsnaamcomponent("Chougna", "del", baron, hoogheid, "-")
        );
        persoon.getPersoonVoornaam().add(
                maakPersoonVoorNaam("Jansen", 2)
        );
        persoon.getPersoonVoornaam().add(
                maakPersoonVoorNaam("Oussama", 1)
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);

        checkResultaat(
                persoon.getSamengesteldeNaam(),
                baron,
                hoogheid,
                new GeslachtsnaamComponent("Chougna"),
                new Voorvoegsel("del"),
                new ScheidingsTeken("-"),
                new Voornaam("Oussama Jansen"));
    }

    @Test
    public void testSamengesteldeNaamAfleidingZonderVoornamenEn1GeslachtsnaamComp() {
        final PersoonBericht persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
                maakPersoonGeslachtsnaamcomponent("Chougna", "del", baron, hoogheid, "-")
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);

        checkResultaat(
                persoon.getSamengesteldeNaam(),
                baron,
                hoogheid,
                new GeslachtsnaamComponent("Chougna"),
                new Voorvoegsel("del"),
                new ScheidingsTeken("-"),
                null);
    }

    @Test
    public void testSamengesteldeNaamAfleidingZonderVoornamenEnZonderGeslachtsnaamComp() {
        Persoon persoon = maakStandaardPersoon();
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        Assert.assertNull(persoon.getSamengesteldeNaam());
    }

    private void checkResultaat(final PersoonSamengesteldeNaamGroep resultaat,
                                final AdellijkeTitel titel, final Predikaat predikaat, final GeslachtsnaamComponent geslNaam,
                                final Voorvoegsel voorvoegsel, final ScheidingsTeken scheidingsteken, final Voornaam voornamen)
    {
        Assert.assertEquals(titel, resultaat.getAdellijkeTitel());
        Assert.assertEquals(predikaat, resultaat.getPredikaat());
        Assert.assertEquals(geslNaam, resultaat.getGeslachtsnaam());
        Assert.assertEquals(voorvoegsel, resultaat.getVoorvoegsel());
        Assert.assertEquals(scheidingsteken, resultaat.getScheidingsteken());
        Assert.assertEquals(voornamen, resultaat.getVoornamen());
        Assert.assertEquals(JaNee.Ja, resultaat.getIndAlgorithmischAfgeleid());
        Assert.assertEquals(JaNee.Nee, resultaat.getIndNamenreeksAlsGeslachtsNaam());
    }

    private PersoonBericht maakStandaardPersoon() {
        PersoonBericht persoonBericht = new PersoonBericht();
        ReflectionTestUtils.setField(persoonBericht, "geslachtsnaamcomponenten", new ArrayList<PersoonGeslachtsnaamComponentBericht>());
        ReflectionTestUtils.setField(persoonBericht, "persoonVoornaam", new ArrayList<PersoonVoornaamBericht>());
        return persoonBericht;
    }

    private PersoonGeslachtsnaamComponentBericht maakPersoonGeslachtsnaamcomponent(final String naam,
                                                                            final String voorvoegsel,
                                                                            final AdellijkeTitel adellijkeTitel,
                                                                            final Predikaat predikaat,
                                                                            final String scheidingsteken)
    {
        final PersoonGeslachtsnaamComponentBericht comp = new PersoonGeslachtsnaamComponentBericht();
        PersoonGeslachtsnaamCompStandaardGroepBericht standaardGroepBericht = new PersoonGeslachtsnaamCompStandaardGroepBericht();
        standaardGroepBericht.setGeslachtsnaamComponent(new GeslachtsnaamComponent(naam));
        standaardGroepBericht.setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        standaardGroepBericht.setAdellijkeTitel(adellijkeTitel);
        standaardGroepBericht.setPredikaat(predikaat);
        standaardGroepBericht.setScheidingsteken(new ScheidingsTeken(scheidingsteken));
        comp.setPersoonGeslachtsnaamCompStandaardGroep(standaardGroepBericht);
        return comp;
    }

    private PersoonVoornaamBericht maakPersoonVoorNaam(final String naam, final Integer volgnummer) {
        final PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        PersoonVoornaamStandaardGroepBericht standaardGroepBericht = new PersoonVoornaamStandaardGroepBericht();
        standaardGroepBericht.setVoornaam(new Voornaam(naam));
        voornaam.setGegevens(standaardGroepBericht);
        voornaam.setVolgnummer(new Volgnummer(volgnummer));
        return voornaam;
    }
}
