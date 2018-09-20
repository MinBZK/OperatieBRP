/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.groep.bericht.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;


public class GeboorteActieValidatorTest {

    private RelatieBericht standaardRelatie = null;

    @Test
    public void testValideRelatie() {
        ActieBericht actie = new ActieBericht();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testMetLegeRelatie() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        RelatieBericht relatie = new RelatieBericht();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testRelatieZonderKind() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        standaardRelatie.getBetrokkenheden().remove(kindBetrokkenheid);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testRelatieAlleVeldenLeegVoorKind() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        kindBetrokkenheid.setBetrokkene(new PersoonBericht());

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(3, meldingen.size());
    }

    @Test
    public void testRelatieAlleenIdentificatienummersVoorKind() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        PersoonBericht nieuwKind = new PersoonBericht();
        nieuwKind.setIdentificatienummers(kindBetrokkenheid.getBetrokkene().getIdentificatienummers());
        kindBetrokkenheid.setBetrokkene(nieuwKind);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }

    @Test
    public void testRelatieAlleVeldenLeegVoorVoornaamKind() throws IllegalAccessException {
        ActieBericht actie = new ActieBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        PersoonBericht kind = new PersoonBericht();
        kind.setPersoonVoornaam(new ArrayList<PersoonVoornaamBericht>());
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setGegevens(new PersoonVoornaamStandaardGroepBericht());
        voornaam.getGegevens().setVoornaam(new Voornaam(""));
        kind.getPersoonVoornaam().add(voornaam);
        kindBetrokkenheid.setBetrokkene(kind);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(5, meldingen.size());
    }

    @Test
    public void testRelatieZonderGeslachtsaanduidingVoorKind() {
        ActieBericht actie = new ActieBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        kindBetrokkenheid.getBetrokkene().setGeslachtsaanduiding(null);

        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
    }

    @Before
    public void bouwVolledigeCorrecteRelatie() {
        RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();
        gegevens.setDatumAanvang(new Datum(20060325));

        standaardRelatie = new RelatieBericht();
        standaardRelatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        standaardRelatie.setGegevens(gegevens);

        List<BetrokkenheidBericht> betrokkenheden = new ArrayList<BetrokkenheidBericht>();
        betrokkenheden.add(bouwBetrokkenheid(SoortBetrokkenheid.KIND, standaardRelatie,
                bouwKind("123456789", Integer.valueOf(20060325))));
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
    private PersoonBericht bouwKind(final String bsn, final Integer geboorteDatum) {
        PersoonBericht kind = new PersoonBericht();

        kind.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        kind.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        kind.getIdentificatienummers().setAdministratienummer(new Administratienummer(Long.valueOf(1234567890L)));

        kind.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        kind.getGeslachtsaanduiding().setGeslachtsaanduiding(Geslachtsaanduiding.MAN);

        kind.setGeboorte(new PersoonGeboorteGroepBericht());
        kind.getGeboorte().setDatumGeboorte(new Datum(geboorteDatum));
        kind.getGeboorte().setGemeenteGeboorte(new Partij());
        kind.getGeboorte().setLandGeboorte(new Land());

        List<PersoonVoornaamBericht> voornamen = new ArrayList<PersoonVoornaamBericht>();
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        voornaam.setGegevens(new PersoonVoornaamStandaardGroepBericht());
        voornaam.getGegevens().setVoornaam(new Voornaam("piets"));
        voornamen.add(voornaam);
        kind.setPersoonVoornaam(voornamen);

        List<PersoonGeslachtsnaamcomponentBericht> geslComponenten = new ArrayList<PersoonGeslachtsnaamcomponentBericht>();
        PersoonGeslachtsnaamcomponentBericht geslComp = new PersoonGeslachtsnaamcomponentBericht();
        geslComp.setVolgnummer(new Volgnummer(1));
        geslComp.setGegevens(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        geslComp.getGegevens().setGeslachtsnaamcomponent(new Geslachtsnaamcomponent("piets"));
        geslComponenten.add(geslComp);

        kind.setGeslachtsnaamcomponenten(geslComponenten);

        return kind;
    }

    /**
     * Bouwt en retourneert een voor het bericht vereiste persoon instantie, welke als ouder kan fungeren.
     *
     * @param bsn het BSN van de ouder.
     * @return een persoon instantie.
     */
    private PersoonBericht bouwOuder(final String bsn) {
        PersoonBericht persoon = new PersoonBericht();

        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));

        persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
        persoon.getAfgeleidAdministratief().setTijdstipLaatsteWijziging(
                new DatumTijd(new Timestamp(Calendar.getInstance().getTimeInMillis())));
        return persoon;
    }

    /**
     * Bouwt en retourneert een {@link BetrokkenheidBericht} met opgegeven soort, relatie en de betrokkene.
     *
     * @param soort de soort van de betrokkenheid.
     * @param relatie de relatie waartoe de betrokkenheid behoort.
     * @param betrokkene de betrokkene.
     * @return de betrokkenheid.
     */
    private BetrokkenheidBericht bouwBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
            final PersoonBericht betrokkene)
    {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(soort);
        betrokkenheid.setRelatie(relatie);
        betrokkenheid.setBetrokkene(betrokkene);
        return betrokkenheid;
    }
}
