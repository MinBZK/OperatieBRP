/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import org.junit.Before;
import org.junit.Test;

public class SamengesteldeNaamAfleidingTest {

    private SamengesteldeNaamAfleiding samengesteldeNaamAfleiding;

    private AdellijkeTitel baron;
    private Predikaat      hoogheid;

    @Before
    public void init() {
        samengesteldeNaamAfleiding = new SamengesteldeNaamAfleiding();
        baron = new AdellijkeTitel(new AdellijkeTitelCode("B"), null, null);
        hoogheid = new Predikaat(new PredikaatCode("H"), null, null);
    }

    @Test
    public void testSamengesteldeNaamAfleidingMet1VoornaamEn1Geslachtsnaamcomponent() {
        final PersoonBericht persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
            maakPersoonGeslachtsnaamcomponent("Chougna", "del", baron, hoogheid, "-")
        );

        persoon.getVoornamen().add(
            maakPersoonVoorNaam("Oussama", 1)
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        checkResultaat(
            persoon.getSamengesteldeNaam(),
            baron,
            hoogheid,
            new Geslachtsnaam("Chougna"),
            new Voorvoegsel("del"),
            new Scheidingsteken("-"),
            new Voornamen("Oussama"));
    }

    @Test
    public void testSamengesteldeNaamAfleidingMet2VoornamenEn1Geslachtsnaamcomponent() {
        final PersoonBericht persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
            maakPersoonGeslachtsnaamcomponent("Chougna", "del", baron, hoogheid, "-")
        );
        persoon.getVoornamen().add(
            maakPersoonVoorNaam("Jansen", 2)
        );
        persoon.getVoornamen().add(
            maakPersoonVoorNaam("Oussama", 1)
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);

        checkResultaat(
            persoon.getSamengesteldeNaam(),
            baron,
            hoogheid,
            new Geslachtsnaam("Chougna"),
            new Voorvoegsel("del"),
            new Scheidingsteken("-"),
            new Voornamen("Oussama Jansen"));
    }

    @Test
    public void testSamengesteldeNaamAfleidingZonderVoornamenEn1Geslachtsnaamcomponent() {
        final PersoonBericht persoon = maakStandaardPersoon();
        persoon.getGeslachtsnaamcomponenten().add(
            maakPersoonGeslachtsnaamcomponent("Chougna", "del", baron, hoogheid, "-")
        );
        samengesteldeNaamAfleiding.executeer(null, persoon, null);

        checkResultaat(
            persoon.getSamengesteldeNaam(),
            baron,
            hoogheid,
            new Geslachtsnaam("Chougna"),
            new Voorvoegsel("del"),
            new Scheidingsteken("-"),
            null);
    }

    @Test
    public void testSamengesteldeNaamAfleidingZonderVoornamenEnZonderGeslachtsnaamcomponent() {
        Persoon persoon = maakStandaardPersoon();
        samengesteldeNaamAfleiding.executeer(null, persoon, null);
        Assert.assertNull(persoon.getSamengesteldeNaam());
    }

    @Test
    public void testSamenGesteldeNaamRelatie() {
        PersoonBericht persoon = maakStandaardPersoon();
        RelatieBericht relatie = new HuwelijkBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.getBetrokkenheden().add(new PartnerBericht());
        relatie.getBetrokkenheden().get(0).setPersoon(persoon);
        samengesteldeNaamAfleiding.executeer(null, relatie, null);
        Assert.assertNull(persoon.getSamengesteldeNaam());
    }

    private void checkResultaat(final PersoonSamengesteldeNaamGroep resultaat,
        final AdellijkeTitel titel, final Predikaat predikaat, final Geslachtsnaam geslNaam,
        final Voorvoegsel voorvoegsel, final Scheidingsteken scheidingsteken, final Voornamen voornamen)
    {
        Assert.assertEquals(titel, resultaat.getAdellijkeTitel());
        Assert.assertEquals(predikaat, resultaat.getPredikaat());
        Assert.assertEquals(geslNaam, resultaat.getGeslachtsnaam());
        Assert.assertEquals(voorvoegsel, resultaat.getVoorvoegsel());
        Assert.assertEquals(scheidingsteken, resultaat.getScheidingsteken());
        Assert.assertEquals(voornamen, resultaat.getVoornamen());
        Assert.assertEquals(JaNee.JA, resultaat.getIndicatieAlgoritmischAfgeleid());
        Assert.assertEquals(JaNee.NEE, resultaat.getIndicatieNamenreeks());
    }

    private PersoonBericht maakStandaardPersoon() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());
        persoonBericht.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        return persoonBericht;
    }

    private PersoonGeslachtsnaamcomponentBericht maakPersoonGeslachtsnaamcomponent(final String naam,
        final String voorvoegsel,
        final AdellijkeTitel adellijkeTitel,
        final Predikaat predikaat,
        final String scheidingsteken)
    {
        final PersoonGeslachtsnaamcomponentBericht comp = new PersoonGeslachtsnaamcomponentBericht();
        PersoonGeslachtsnaamcomponentStandaardGroepBericht
            standaardGroepBericht = new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        standaardGroepBericht.setNaam(new Geslachtsnaamcomponent(naam));
        standaardGroepBericht.setVoorvoegsel(new Voorvoegsel(voorvoegsel));
        standaardGroepBericht.setAdellijkeTitel(adellijkeTitel);
        standaardGroepBericht.setPredikaat(predikaat);
        standaardGroepBericht.setScheidingsteken(new Scheidingsteken(scheidingsteken));
        comp.setStandaard(standaardGroepBericht);
        return comp;
    }

    private PersoonVoornaamBericht maakPersoonVoorNaam(final String naam, final Integer volgnummer) {
        final PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        PersoonVoornaamStandaardGroepBericht standaardGroepBericht = new PersoonVoornaamStandaardGroepBericht();
        standaardGroepBericht.setNaam(new Voornaam(naam));
        voornaam.setStandaard(standaardGroepBericht);
        voornaam.setVolgnummer(new Volgnummer(volgnummer));
        return voornaam;
    }
}
