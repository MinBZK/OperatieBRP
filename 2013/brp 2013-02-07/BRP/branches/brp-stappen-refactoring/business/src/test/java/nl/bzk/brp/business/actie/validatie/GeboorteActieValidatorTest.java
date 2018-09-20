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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;


public class GeboorteActieValidatorTest {

    private FamilierechtelijkeBetrekkingBericht standaardRelatie = null;

    @Test
    public void testValideRelatie() {
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(standaardRelatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testMetLegeRelatie() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        List<RootObject> rootObjecten = new ArrayList<RootObject>();
        rootObjecten.add(relatie);

        actie.setRootObjecten(rootObjecten);

        final List<Melding> meldingen = new GeboorteActieValidator().valideerActie(actie);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testRelatieZonderKind() throws IllegalAccessException {
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
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
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        kindBetrokkenheid.setPersoon(new PersoonBericht());

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
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        PersoonBericht nieuwKind = new PersoonBericht();
        nieuwKind.setIdentificatienummers(kindBetrokkenheid.getPersoon().getIdentificatienummers());
        kindBetrokkenheid.setPersoon(nieuwKind);

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
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        PersoonBericht kind = new PersoonBericht();
        kind.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam.getStandaard().setNaam(new Voornaam(""));
        kind.getVoornamen().add(voornaam);
        kindBetrokkenheid.setPersoon(kind);

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
        ActieBericht actie = new ActieRegistratieGeboorteBericht();
        BetrokkenheidBericht kindBetrokkenheid = standaardRelatie.getKindBetrokkenheid();
        kindBetrokkenheid.getPersoon().setGeslachtsaanduiding(null);

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
        // RelatieStandaardGroepBericht gegevens = new RelatieStandaardGroepBericht();
        // gegevens.setDatumAanvang(new Datum(20060325));

        standaardRelatie = new FamilierechtelijkeBetrekkingBericht();
        // standaardRelatie.setStandaard(gegevens);

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
        kind.getIdentificatienummers().setAdministratienummer(new ANummer("" + 1234567890L));

        kind.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());
        kind.getGeslachtsaanduiding().setGeslachtsaanduiding(Geslachtsaanduiding.MAN);

        kind.setGeboorte(new PersoonGeboorteGroepBericht());
        kind.getGeboorte().setDatumGeboorte(new Datum(geboorteDatum));
        kind.getGeboorte().setGemeenteGeboorte(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        kind.getGeboorte().setLandGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND);

        List<PersoonVoornaamBericht> voornamen = new ArrayList<PersoonVoornaamBericht>();
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        voornaam.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam.getStandaard().setNaam(new Voornaam("piets"));
        voornamen.add(voornaam);
        kind.setVoornamen(voornamen);

        List<PersoonGeslachtsnaamcomponentBericht> geslComponenten =
            new ArrayList<PersoonGeslachtsnaamcomponentBericht>();
        PersoonGeslachtsnaamcomponentBericht geslComp = new PersoonGeslachtsnaamcomponentBericht();
        geslComp.setVolgnummer(new Volgnummer(1));
        geslComp.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        geslComp.getStandaard().setNaam(new Geslachtsnaamcomponent("piets"));
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
        BetrokkenheidBericht betrokkenheid;

        switch (soort) {
            case KIND:
                betrokkenheid = new KindBericht();
                break;
            case OUDER:
                betrokkenheid = new OuderBericht();
                break;
            default:
                betrokkenheid = null;
                break;
        }

        if (betrokkenheid != null) {
            betrokkenheid.setRelatie(relatie);
            betrokkenheid.setPersoon(betrokkene);
        }

        return betrokkenheid;
    }
}
