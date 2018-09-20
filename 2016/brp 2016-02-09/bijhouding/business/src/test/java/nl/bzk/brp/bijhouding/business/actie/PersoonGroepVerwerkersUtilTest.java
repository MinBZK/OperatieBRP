/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.GeboorteGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsaanduiding.GeslachtsaanduidingGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.GeslachtsnaamcomponentVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers.IdentificatienummersGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.SamengesteldeNaamGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.voornaam.VoornamenVerwerker;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test klasse voor het testen van de functionaliteit in de {@link PersoonGroepVerwerkersUtil} klasse.
 */
public class PersoonGroepVerwerkersUtilTest {

    private final ActieModel             actie  =
        new ActieModel(ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_GEBOORTE).getActie(), null);
    private final PersoonHisVolledigImpl ouwkiv = null;

    private PersoonBericht         persoonBericht;
    private List<Verwerkingsregel> regels;
    private PersoonHisVolledigImpl persoonHisVolledig;

    @Before
    public void init() {
        persoonBericht = new PersoonBericht();
        persoonBericht.setReferentieID("123");

        persoonHisVolledig = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    }

    @Test
    public void testGeenAanwezigeGroepen() {
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertTrue(regels.isEmpty());
    }

    @Test
    public void testGeenAanwezigeGroepenZonderOuwkiv() {
        bepaalRegelsZonderOuwkiv();

        Assert.assertNotNull(regels);
        Assert.assertTrue(regels.isEmpty());
    }

    @Test
    public void testIdentificatienummersGroep() {
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof IdentificatienummersGroepVerwerker);
    }

    @Test
    public void testSamengesteldenaamGroep() {
        persoonBericht.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof SamengesteldeNaamGroepVerwerker);
    }

    @Test
    public void testGeboorteGroep() {
        persoonBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof GeboorteGroepVerwerker);
    }

    @Test
    public void testGeslachtsaanduidingGroep() {
        persoonBericht.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof GeslachtsaanduidingGroepVerwerker);
    }

    @Test
    public void testLegeGeslachtsnaamcomponenten() {
        persoonBericht.setGeslachtsnaamcomponenten(bouwLijstGeslachtsnaamcomponenten(0));
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertTrue(regels.isEmpty());

        // Test of juiste aantal geslachtsnaamcomponenten is toegevoegd.
        Assert.assertEquals(0, persoonHisVolledig.getGeslachtsnaamcomponenten().size());
    }

    @Test
    public void testEnkeleGeslachtsnaamcomponent() {
        persoonBericht.setGeslachtsnaamcomponenten(bouwLijstGeslachtsnaamcomponenten(1));
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof GeslachtsnaamcomponentVerwerker);

        // Test of juiste aantal geslachtsnaamcomponenten is toegevoegd.
        Assert.assertEquals(1, persoonHisVolledig.getGeslachtsnaamcomponenten().size());
    }

    @Test
    public void testMeerdereGeslachtsnaamcomponenten() {
        persoonBericht.setGeslachtsnaamcomponenten(bouwLijstGeslachtsnaamcomponenten(3));
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(3, regels.size());
        Assert.assertTrue(regels.get(0) instanceof GeslachtsnaamcomponentVerwerker);

        // Test of juiste aantal geslachtsnaamcomponenten is toegevoegd.
        Assert.assertEquals(3, persoonHisVolledig.getGeslachtsnaamcomponenten().size());
    }

    @Test
    public void testMeerdereGeslachtsnaamcomponentenMetReedsBestaande() {
        persoonBericht.setGeslachtsnaamcomponenten(bouwLijstGeslachtsnaamcomponenten(3));
        persoonHisVolledig.getGeslachtsnaamcomponenten()
                       .add(new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoonHisVolledig, new VolgnummerAttribuut(1)));
        bepaalRegels();

        Assert.assertNotNull(regels);
        Assert.assertEquals(3, regels.size());
        Assert.assertTrue(regels.get(0) instanceof GeslachtsnaamcomponentVerwerker);

        // Test of juiste aantal geslachtsnaamcomponenten is toegevoegd.
        Assert.assertEquals(3, persoonHisVolledig.getGeslachtsnaamcomponenten().size());
    }


    @Test
    public void testLegeVoornamen() {
        persoonBericht.setVoornamen(bouwLijstVoornamen(0));
        bepaalRegels();

        // Voornamen worden altijd 'met zijn allen' verwerkt, dus altijd 1 voornamen verwerker.
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof VoornamenVerwerker);
    }

    @Test
    public void testEnkeleVoornaam() {
        persoonBericht.setVoornamen(bouwLijstVoornamen(1));
        bepaalRegels();

        // Voornamen worden altijd 'met zijn allen' verwerkt, dus altijd 1 voornamen verwerker.
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof VoornamenVerwerker);
    }

    @Test
    public void testMeerdereVoornamen() {
        persoonBericht.setVoornamen(bouwLijstVoornamen(3));
        bepaalRegels();

        // Voornamen worden altijd 'met zijn allen' verwerkt, dus altijd 1 voornamen verwerker.
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof VoornamenVerwerker);
    }

    @Test
    public void testMeerdereVoornamenMetReedsBestaande() {
        persoonBericht.setVoornamen(bouwLijstVoornamen(3));
        persoonHisVolledig.getVoornamen().add(new PersoonVoornaamHisVolledigImpl(persoonHisVolledig, new VolgnummerAttribuut(1)));
        bepaalRegels();

        // Voornamen worden altijd 'met zijn allen' verwerkt, dus altijd 1 voornamen verwerker.
        Assert.assertEquals(1, regels.size());
        Assert.assertTrue(regels.get(0) instanceof VoornamenVerwerker);
    }

    /**
     * Bepaalt de uit te voeren verwerkingsregels op basis van het bericht en de opgegegeven persoon.
     */
    private void bepaalRegels() {
        regels = PersoonGroepVerwerkersUtil.bepaalAlleVerwerkingsregels(persoonBericht, persoonHisVolledig, actie, ouwkiv);
    }

    /**
     * Bepaalt de uit te voeren verwerkingsregels op basis van het bericht en de opgegegeven persoon zonder ouwkiv.
     */
    private void bepaalRegelsZonderOuwkiv() {
        regels = PersoonGroepVerwerkersUtil.bepaalAlleVerwerkingsregels(persoonBericht, persoonHisVolledig, actie);
    }

    /**
     * Bouwt en retourneert een lijst van {@link PersoonGeslachtsnaamcomponentBericht} instanties waarbij het aantal
     * wordt bepaald door het opgegeven aantal.
     *
     * @param aantal het aantal geslachtsnaamcomponenten dat in de lijst moet zitten.
     * @return een lijst met geslachtsnaamcomponenten.
     */
    private List<PersoonGeslachtsnaamcomponentBericht> bouwLijstGeslachtsnaamcomponenten(final int aantal) {
        final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamcomponenten = new ArrayList
            <PersoonGeslachtsnaamcomponentBericht>();
        for (int i = 1; i <= aantal; i++) {
            PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentBericht();
            geslachtsnaamcomponent.setVolgnummer(new VolgnummerAttribuut(i));
            geslachtsnaamcomponenten.add(geslachtsnaamcomponent);
        }
        return geslachtsnaamcomponenten;
    }

    /**
     * Bouwt en retourneert een lijst van {@link PersoonVoornaamBericht} instanties waarbij het aantal
     * wordt bepaald door het opgegeven aantal.
     *
     * @param aantal het aantal voornamen dat in de lijst moet zitten.
     * @return een lijst met voornamen.
     */
    private List<PersoonVoornaamBericht> bouwLijstVoornamen(final int aantal) {
        final List<PersoonVoornaamBericht> voornamen = new ArrayList<>();
        for (int i = 1; i <= aantal; i++) {
            PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
            voornaam.setVolgnummer(new VolgnummerAttribuut(i));
            voornamen.add(voornaam);
        }
        return voornamen;
    }
}
