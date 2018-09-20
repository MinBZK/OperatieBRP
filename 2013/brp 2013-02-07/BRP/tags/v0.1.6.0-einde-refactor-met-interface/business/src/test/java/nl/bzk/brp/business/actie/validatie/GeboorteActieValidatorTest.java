/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonAfgeleidAdministratief;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class GeboorteActieValidatorTest {

    private Relatie standaardRelatie = null;

    @Test
    public void testValideRelatie() {
        BRPActie actie = new BRPActie();
        actie.voegRelatieToe(standaardRelatie);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testMetLegeRelatie() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Relatie relatie = new Relatie();
        actie.voegRelatieToe(relatie);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testRelatieZonderKind() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Betrokkenheid kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        standaardRelatie.getBetrokkenheden().remove(kindBetrokkenheid);

        actie.voegRelatieToe(standaardRelatie);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testRelatieAlleVeldenLeegVoorKind() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Betrokkenheid kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        kindBetrokkenheid.setBetrokkene(new Persoon());

        actie.voegRelatieToe(standaardRelatie);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(3, meldingen.size());
    }

    @Test
    public void testRelatieAlleenIdentificatieNummersVoorKind() throws IllegalAccessException {
        BRPActie actie = new BRPActie();
        Betrokkenheid kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        Persoon nieuwKind = new Persoon();
        nieuwKind.setIdentificatienummers(kindBetrokkenheid.getBetrokkene().getIdentificatienummers());
        kindBetrokkenheid.setBetrokkene(nieuwKind);

        actie.voegRelatieToe(standaardRelatie);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testRelatieZonderGeslachtsAanduidingVoorKind() {
        BRPActie actie = new BRPActie();
        Betrokkenheid kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        kindBetrokkenheid.getBetrokkene().setPersoonGeslachtsAanduiding(null);

        actie.voegRelatieToe(standaardRelatie);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
    }

    @Before
    public void bouwVolledigeCorrecteRelatie() {
        standaardRelatie = new Relatie();
        standaardRelatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        standaardRelatie.setDatumAanvang(Integer.valueOf(20060325));

        Set<Betrokkenheid> betrokkenheden = new HashSet<Betrokkenheid>();
        betrokkenheden.add(bouwBetrokkenheid(SoortBetrokkenheid.KIND, standaardRelatie, bouwKind("123456789",
            Integer.valueOf(20060325))));
        betrokkenheden.add(bouwBetrokkenheid(SoortBetrokkenheid.OUDER, standaardRelatie, bouwOuder("234567891")));
        betrokkenheden.add(bouwBetrokkenheid(SoortBetrokkenheid.OUDER, standaardRelatie, bouwOuder("234567891")));

        standaardRelatie.setBetrokkenheden(betrokkenheden);
    }

    /**
     * Bouwt en retourneert een (voor 1e inschrijving bericht) volledig kind.
     *
     * @param bsn BSN van het kind.
     * @param geboorteDatum geboortedatum van het kind.
     * @return een volledig ingevuld kind.
     */
    private Persoon bouwKind(final String bsn, final Integer geboorteDatum) {
        Persoon kind = new Persoon();

        kind.setIdentificatienummers(new PersoonIdentificatienummers());
        kind.getIdentificatienummers().setBurgerservicenummer(bsn);
        kind.getIdentificatienummers().setAdministratienummer("1234567890");

        kind.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        kind.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.MAN);

        kind.setGeboorte(new PersoonGeboorte());
        kind.getGeboorte().setDatumGeboorte(geboorteDatum);
        kind.getGeboorte().setGemeenteGeboorte(new Partij());
        kind.getGeboorte().setLandGeboorte(new Land());

        List<PersoonVoornaam> voornamen = new ArrayList<PersoonVoornaam>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("piets");
        voornamen.add(voornaam);
        ReflectionTestUtils.setField(kind, "persoonVoornamen", voornamen);

        List<PersoonGeslachtsnaamcomponent> geslComponenten = new ArrayList<PersoonGeslachtsnaamcomponent>();
        PersoonGeslachtsnaamcomponent geslComp = new PersoonGeslachtsnaamcomponent();
        geslComp.setVolgnummer(1);
        geslComp.setNaam("piets");
        geslComponenten.add(geslComp);
        ReflectionTestUtils.setField(kind, "geslachtsnaamcomponenten", geslComponenten);

        return kind;
    }

    /**
     * Bouwt en retourneert een voor het bericht vereiste persoon instantie, welke als ouder kan fungeren.
     *
     * @param bsn het BSN van de ouder.
     * @return een persoon instantie.
     */
    private Persoon bouwOuder(final String bsn) {
        Persoon persoon = new Persoon();

        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer(bsn);

        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratief());
        persoon.getAfgeleidAdministratief().setLaatstGewijzigd(new Date());
        return persoon;
    }

    /**
     * Bouwt en retourneert een {@link Betrokkenheid} met opgegeven soort, relatie en de betrokkene.
     *
     * @param soort de soort van de betrokkenheid.
     * @param relatie de relatie waartoe de betrokkenheid behoort.
     * @param betrokkene de betrokkene.
     * @return de betrokkenheid.
     */
    private Betrokkenheid bouwBetrokkenheid(final SoortBetrokkenheid soort, final Relatie relatie,
        final Persoon betrokkene)
    {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(soort);
        betrokkenheid.setRelatie(relatie);
        betrokkenheid.setBetrokkene(betrokkene);
        return betrokkenheid;
    }
}
