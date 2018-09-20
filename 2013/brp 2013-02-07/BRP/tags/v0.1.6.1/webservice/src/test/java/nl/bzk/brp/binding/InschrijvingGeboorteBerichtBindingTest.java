/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeAanschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.validatie.OverruleMelding;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Binding test voor een {@link nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht} met daarin een
 * inschrijving geboorte bericht.
 */
public class InschrijvingGeboorteBerichtBindingTest extends AbstractBindingInTest<InschrijvingGeboorteBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MAX.xml");
        valideerOutputTegenSchema(xml);

        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "organisatie", "applicatie", "referentienummer");
        checkActies(bericht, 20120101, SoortActie.AANGIFTE_GEBOORTE, SoortActie.REGISTRATIE_NATIONALITEIT);
//        Assert.assertEquals("id.inschrijvingGeboorte", bericht.getBrpActies().get(0).getVerzendendId());
//      Assert.assertEquals("id.regNat", bericht.getBrpActies().get(1).getVerzendendId());

        Relatie relatie = getRelatieUitGeboorteActie(bericht);
        checkRelatieEnBetrokkenheden(relatie, 3);

        Persoon kind = getKindUitGeboorteActie(bericht);
        checkIdentificatieNummersEnGeslacht(kind, "123456789", "123456789", GeslachtsAanduiding.ONBEKEND);
        checkAanschrijving(kind, true, false, "Test");
        checkGeboorte(kind, 20120101);
        checkVoornamen(kind, "Piet", "Veerman");
        checkGeslachtsnaamcomponenten(kind, new String[] { "Bokkel", "van", "/" });

        Persoon ouder1 = getOuderUitGeboorteActie(bericht, "111222333");
        if (null != ouder1) {
            checkSamengesteldeNaam(ouder1, AdellijkeTitel.GRAAF, "Jan-Pieter", "Meern");
        }

        // actie[1].bron[x] zijn nog niet geimplementeerd.
        Persoon persoon = getKindUitRegistratieNationaliteitActie(bericht);
        // groep AfgeleidAdministratief, Indicaties
        // nog niet gebind in Jibx.
        // pas dit aan als het WEL geimplementeerd is.
        Assert.assertNull(persoon.getAfgeleidAdministratief());
        Assert.assertTrue(persoon.getIndicaties().isEmpty());
        checkNationaliteit(persoon, "1", "reden verkrijging", "reden verlies");

        checkAlleVerzendedIds(bericht);
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN.xml");
        valideerOutputTegenSchema(xml);

        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "16", "bzm", "0123456789");
        checkActies(bericht, 1012012, SoortActie.AANGIFTE_GEBOORTE, SoortActie.REGISTRATIE_NATIONALITEIT);

        Persoon kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        Assert.assertNotNull(kind.getPersoonGeslachtsAanduiding());
        Assert.assertEquals(GeslachtsAanduiding.VROUW, kind.getPersoonGeslachtsAanduiding().getGeslachtsAanduiding());
        Assert.assertNotNull(kind.getGeboorte());
        Assert.assertEquals(20120101, kind.getGeboorte().getDatumGeboorte().longValue());
        Assert.assertEquals("6039", kind.getGeboorte().getLandGeboorte().getLandcode());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten());
        Assert.assertNull(kind.getPersoonVoornamen());
        Assert.assertTrue(kind.getBetrokkenheden().isEmpty());
    }

    /**
     * Test minimaal bericht waar de hoofd groep nil is.
     *
     * @throws IOException
     * @throws JiBXException
     */
    @Test
    public void testBindingBerichtMinNil() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN-nil1.xml");
        valideerOutputTegenSchema(xml);

        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        Persoon kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        Assert.assertNull(kind.getPersoonGeslachtsAanduiding());
        Assert.assertNull(kind.getGeboorte());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten());
        Assert.assertNull(kind.getPersoonVoornamen());
        Assert.assertTrue(kind.getBetrokkenheden().isEmpty());
    }

    /**
     * Test minimaal bericht velden op de laagste niveau op nil staan waar het toegestaan is volgens de xsd.
     *
     * @throws IOException
     * @throws JiBXException
     */
    @Test
    public void testBindingBerichtMinNilInWaarde() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN-waardeNiveau.xml");
        valideerOutputTegenSchema(xml);

        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        Persoon kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers().getAdministratienummer());
        Assert.assertNull(kind.getIdentificatienummers().getBurgerservicenummer());

        Assert.assertNull(kind.getGeboorte().getGemeenteGeboorte().getGemeentecode());
        Assert.assertNull(kind.getGeboorte().getWoonplaatsGeboorte().getWoonplaatscode());
        Assert.assertNull(kind.getGeboorte().getBuitenlandsePlaats());
        Assert.assertNull(kind.getGeboorte().getBuitenlandseRegio());
        Assert.assertNull(kind.getGeboorte().getOmschrijvingLocatie());

        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getVoorvoegsel());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getScheidingsTeken());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getPredikaat());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getAdellijkeTitel());

        Persoon ouder = getOuderUitGeboorteActie(bericht);

        Assert.assertNull(ouder.getGeboorte().getGemeenteGeboorte().getGemeentecode());
        Assert.assertNull(ouder.getGeboorte().getWoonplaatsGeboorte().getWoonplaatscode());
        Assert.assertNull(ouder.getGeboorte().getBuitenlandsePlaats());
        Assert.assertNull(ouder.getGeboorte().getBuitenlandseRegio());
        Assert.assertNull(ouder.getGeboorte().getOmschrijvingLocatie());

        Assert.assertNull(ouder.getSamengesteldenaam().getPredikaat());
        Assert.assertNull(ouder.getSamengesteldenaam().getAdellijkeTitel());
        Assert.assertNull(ouder.getSamengesteldenaam().getVoornamen());
        Assert.assertNull(ouder.getSamengesteldenaam().getVoorvoegsel());
        Assert.assertNull(ouder.getSamengesteldenaam().getScheidingsTeken());

        // ouderschap
        Betrokkenheid betrokkenheid  = ((Relatie) bericht.getBrpActies().get(0).getRootObjecten().get(0))
                    .getBetrokkenheden().iterator().next();
        Assert.assertNull(betrokkenheid.getDatumAanvangOuderschap());
        Assert.assertNull(betrokkenheid.isIndAdresGevend());
    }

    /**
     * Test sub groepen nil.
     *
     * @throws IOException
     * @throws JiBXException
     */
    @Test
    public void testBindingBerichtMinInGroep() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN-groepNiveau.xml");
        valideerOutputTegenSchema(xml);

        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
    }

    /** Controle van de stuurgegevens; controle of opgegeven stuurgegevens zijn gevonden. */
    private void checkStuurgegevens(final AbstractBijhoudingsBericht bericht, final String organisatie,
            final String applicatie, final String referentieNummer)
    {
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals(organisatie, berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals(applicatie, berichtStuurgegevens.getApplicatie());
        Assert.assertEquals(referentieNummer, berichtStuurgegevens.getReferentienummer());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());
    }

    /**
     * Controle van de acties; controle of de verwachte acties er zijn, de datum aanvanggeldigheid ook klopt en of de
     * rootobjecten er zijn en van het type {@link Persoon} zijn.
     */
    private void checkActies(final AbstractBijhoudingsBericht bericht, final int datumAanvangGeldigheid,
            final SoortActie... actieSoorten)
    {
        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertEquals(actieSoorten.length, bericht.getBrpActies().size());

        for (int i = 0; i < actieSoorten.length; i++) {
            BRPActie actie = bericht.getBrpActies().get(i);
            Assert.assertEquals(datumAanvangGeldigheid, actie.getDatumAanvangGeldigheid().intValue());
            Assert.assertEquals(actieSoorten[i], actie.getSoort());
            Assert.assertNotNull(actie.getRootObjecten());
            Assert.assertEquals(1, actie.getRootObjecten().size());
            RootObject rootObject = actie.getRootObjecten().get(0);
            Assert.assertNotNull(rootObject);
            if (SoortActie.AANGIFTE_GEBOORTE == actieSoorten[i]) {
                Assert.assertTrue(rootObject instanceof Relatie);
            } else if (SoortActie.REGISTRATIE_NATIONALITEIT == actieSoorten[i]) {
                Assert.assertTrue(rootObject instanceof Persoon);
            }
        }
    }

    /** Retourneert de relatie uit de geboorte actie. */
    private Relatie getRelatieUitGeboorteActie(final AbstractBijhoudingsBericht bericht) {
        BRPActie geboortActie = bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, geboortActie.getSoort());
        RootObject relatie = geboortActie.getRootObjecten().get(0);
        Assert.assertTrue(relatie instanceof Relatie);
        return (Relatie) relatie;
    }

    /**
     * Retourneert het kind dat in het bericht wordt ingeschreven; uit de eerst actie (moet een aangifte geboorte
     * actie zijn) wordt het eerste root object gehaald, wat een persoon (het kind) zou moeten zijn.
     */
    private Persoon getKindUitGeboorteActie(final AbstractBijhoudingsBericht bericht) {
        Assert.assertFalse(bericht.getBrpActies().isEmpty());
        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getSoort());
        Assert.assertFalse(actie.getRootObjecten().isEmpty());

        Persoon kind = null;
        for (Betrokkenheid betrokkenheid : ((Relatie) actie.getRootObjecten().get(0)).getBetrokkenheden()) {
            if (betrokkenheid.isKind()) {
                kind = betrokkenheid.getBetrokkene();
                break;
            }
        }

        Assert.assertNotNull(kind);
        return kind;
    }

    /**
     * Retourneert de ouder.
     */
    private Persoon getOuderUitGeboorteActie(final AbstractBijhoudingsBericht bericht, final String bsn) {
        Assert.assertFalse(bericht.getBrpActies().isEmpty());
        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getSoort());
        Assert.assertFalse(actie.getRootObjecten().isEmpty());

        Persoon ouder = null;
        for (Betrokkenheid betrokkenheid : ((Relatie) actie.getRootObjecten().get(0)).getBetrokkenheden()) {
            if (betrokkenheid.isOuder()) {
                if (null == bsn) {
                    ouder = betrokkenheid.getBetrokkene();
                    break;
                } else if (bsn.equals(betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer()))
                {
                    ouder = betrokkenheid.getBetrokkene();
                    break;
                }
            }
        }

        Assert.assertNotNull(ouder);
        return ouder;
    }

    private Persoon getOuderUitGeboorteActie(final AbstractBijhoudingsBericht bericht) {
        // willekeurige ouder uit de lijst
        return getOuderUitGeboorteActie(bericht, null);
    }

    /**
     * Retourneert het kind waarvan in het bericht de nationaliteit wordt geregistreerd; uit de tweede actie (moet een
     * registratie nationaliteit zijn) wordt het eerste root object gehaald, wat een persoon (het kind) zou moeten
     * zijn.
     */
    private Persoon getKindUitRegistratieNationaliteitActie(final AbstractBijhoudingsBericht bericht) {
        Assert.assertTrue(bericht.getBrpActies().size() >= 2);
        BRPActie actieRegistratieNationaliteit = bericht.getBrpActies().get(1);
        Assert.assertEquals(SoortActie.REGISTRATIE_NATIONALITEIT, actieRegistratieNationaliteit.getSoort());
        Assert.assertFalse(actieRegistratieNationaliteit.getRootObjecten().isEmpty());
        return (Persoon) actieRegistratieNationaliteit.getRootObjecten().get(0);
    }

    /** Controle op identificatienummers en geslachtsaanduiding. */
    private void checkIdentificatieNummersEnGeslacht(final Persoon kind, final String bsn, final String aNummer,
            final GeslachtsAanduiding geslachtsAanduiding)
    {
        Assert.assertEquals(bsn, kind.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals(aNummer, kind.getIdentificatienummers().getAdministratienummer());
        Assert.assertEquals(geslachtsAanduiding, kind.getPersoonGeslachtsAanduiding().getGeslachtsAanduiding());
    }

    /** Controle op de aanschrijving. */
    private void checkAanschrijving(final Persoon kind, final boolean indAdellijkeTitels, final boolean indAfgeleid,
            final String naam)
    {
        PersoonSamengesteldeAanschrijving aanschrijving = kind.getSamengesteldeAanschrijving();
        Assert.assertEquals(indAdellijkeTitels, aanschrijving.getIndAanschrijvingMetAdellijkeTitels());
        Assert.assertEquals(indAfgeleid, aanschrijving.getIndAlgoritmischAfgeleid());
        Assert.assertEquals(naam, aanschrijving.getGeslachtsnaam());
    }

    private void checkSamengesteldeNaam(final Persoon persoon, final AdellijkeTitel adellijkeTitel,
            final String voornamen, final String naam)
    {
        PersoonSamengesteldeNaam samengesteldenaam = persoon.getSamengesteldenaam();
        Assert.assertEquals(adellijkeTitel, samengesteldenaam.getAdellijkeTitel());
        Assert.assertEquals(voornamen, samengesteldenaam.getVoornamen());
//        Assert.assertEquals(indnamenReeks, samengesteldenaam.getIndNamenreeksAlsGeslachtsnaam());
        Assert.assertEquals(naam, samengesteldenaam.getGeslachtsnaam());
    }

    /** Controle op geboorte; controleert of geboorte standaard adres info heeft en opgegeven datum. */
    private void checkGeboorte(final Persoon kind, final int datumGeboorte) {
        PersoonGeboorte geboorte = kind.getGeboorte();
        Assert.assertEquals(datumGeboorte, geboorte.getDatumGeboorte().intValue());
        Assert.assertEquals("1111", geboorte.getGemeenteGeboorte().getGemeentecode());
        Assert.assertEquals("1111", geboorte.getWoonplaatsGeboorte().getWoonplaatscode());
        Assert.assertEquals("verweggiestan", geboorte.getBuitenlandsePlaats());
        Assert.assertEquals("derde berg links", geboorte.getBuitenlandseRegio());
        Assert.assertEquals("1111", geboorte.getLandGeboorte().getLandcode());
        Assert.assertEquals("hoop zand", geboorte.getOmschrijvingLocatie());
    }

    /** Controle van de voornamen; de opgegeven voornamen moeten opvolgend aanwezig zijn. */
    private void checkVoornamen(final Persoon kind, final String... voornamen) {
        List<PersoonVoornaam> persoonVoornamen = kind.getPersoonVoornamen();
        Assert.assertEquals(voornamen.length, persoonVoornamen.size());

        for (int i = 0; i < persoonVoornamen.size(); i++) {
            PersoonVoornaam persoonVoornaam = persoonVoornamen.get(i);
            Assert.assertEquals(i + 1, persoonVoornaam.getVolgnummer().intValue());
            Assert.assertEquals(voornamen[i], persoonVoornaam.getNaam());
        }
    }

    /** Controle van de geslachtsnaamcomponenten. */
    private void checkGeslachtsnaamcomponenten(final Persoon kind, final String[]... componenten) {
        List<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponenten = kind.getGeslachtsnaamcomponenten();
        Assert.assertEquals(componenten.length, persoonGeslachtsnaamcomponenten.size());
        for (int i = 0; i < componenten.length; i++) {
            PersoonGeslachtsnaamcomponent component = persoonGeslachtsnaamcomponenten.get(i);
            Assert.assertEquals(i + 1, component.getVolgnummer().intValue());
            Assert.assertEquals(componenten[i][0], component.getNaam());
            Assert.assertEquals(componenten[i][1], component.getVoorvoegsel());
            Assert.assertEquals(componenten[i][2], component.getScheidingsTeken());
        }
    }

    /** Controle op de relatie en de betrokkenheden. */
    private void checkRelatieEnBetrokkenheden(final Relatie relatie, final int aantalBetrokkenheden) {
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatie());
        Assert.assertEquals(aantalBetrokkenheden, relatie.getBetrokkenheden().size());
        for (Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
            Assert.assertTrue(betrokkenheid.isKind() || betrokkenheid.isOuder());
        }
    }

    /** Controle van nationaliteit van een persoon; Controleert of nationaliteit beschikt over opgegeven waardes. */
    private void checkNationaliteit(final Persoon persoon, final String nationaliteitCode, final String rdnVerkrijging,
            final String rdnVerlies)
    {
        PersoonNationaliteit persoonNationaliteit = persoon.getNationaliteiten().get(0);
        Assert.assertEquals(nationaliteitCode, persoonNationaliteit.getNationaliteit().getCode());
        Assert.assertEquals(rdnVerkrijging, persoonNationaliteit.getRedenVerkrijgingNaam());

        //Reden verlies komt niet meer voor in de xsd
        //Assert.assertEquals(rdnVerlies, persoonNationaliteit.getRedenVerliesNaam());
    }

    private void checkAlleVerzendedIds(final Persoon persoon) {
        Assert.assertTrue(StringUtils.isNotBlank(persoon.getVerzendendId()));
        if (persoon.getIdentificatienummers() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getIdentificatienummers().getVerzendendId()));
        }
        if (persoon.getAdressen() != null && !persoon.getAdressen().isEmpty()) {
            for (PersoonAdres adres : persoon.getAdressen()) {
                Assert.assertTrue(StringUtils.isNotBlank(adres.getVerzendendId()));
            }
        }
        if (persoon.getAfgeleidAdministratief() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getAfgeleidAdministratief().getVerzendendId()));
        }
        if (persoon.getBijhoudingGemeente() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getBijhoudingGemeente().getVerzendendId()));
        }
        if (persoon.getBijhoudingVerantwoordelijke() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getBijhoudingVerantwoordelijke().getVerzendendId()));
        }
        if (persoon.getGeboorte() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getGeboorte().getVerzendendId()));
        }
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            for (PersoonGeslachtsnaamcomponent geslnaamcomp : persoon.getGeslachtsnaamcomponenten()) {
                Assert.assertTrue(StringUtils.isNotBlank(geslnaamcomp.getVerzendendId()));
            }
        }
        // TODO indicaties ...
        if (persoon.getNationaliteiten() != null && !persoon.getNationaliteiten().isEmpty()) {
            for (PersoonNationaliteit nationaliteit : persoon.getNationaliteiten()) {
                Assert.assertTrue(StringUtils.isNotBlank(nationaliteit.getVerzendendId()));
            }
        }
        if (persoon.getOverlijden() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getOverlijden().getVerzendendId()));
        }
        if (persoon.getPersoonGeslachtsAanduiding() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getPersoonGeslachtsAanduiding().getVerzendendId()));
        }
        if (persoon.getPersoonVoornamen() != null && !persoon.getPersoonVoornamen().isEmpty()) {
            for (PersoonVoornaam voornaam : persoon.getPersoonVoornamen()) {
                Assert.assertTrue(StringUtils.isNotBlank(voornaam.getVerzendendId()));
            }
        }
        if (persoon.getRedenOpschorting() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getRedenOpschorting().getVerzendendId()));
        }
        if (persoon.getSamengesteldeAanschrijving() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getSamengesteldeAanschrijving().getVerzendendId()));
        }
        if (persoon.getSamengesteldenaam() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getSamengesteldenaam().getVerzendendId()));
        }
    }

    private void checkAlleVerzendedIds(final AbstractBijhoudingsBericht bericht) {

        if (bericht.getBerichtStuurgegevens() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(bericht.getBerichtStuurgegevens().getVerzendendId()));
        }
        if (bericht.getOverruledMeldingen() != null && !bericht.getOverruledMeldingen().isEmpty()) {
            for (OverruleMelding melding : bericht.getOverruledMeldingen()) {
                Assert.assertTrue(StringUtils.isNotBlank(melding.getVerzendendId()));
            }
        }

        // we zijn hier niet geinteresseerd in de echte waarde va de id's zolang het maar niet null/empty is.
        // we weten dat in sommige gevallen 2 groepen op een groep is gemapped.
        //      (Betrokkenheid en Ouderschap.
        for (BRPActie actie : bericht.getBrpActies()) {
             // TODO nog niet geimplementeerd.
//            Assert.assertTrue(StringUtils.isNotBlank(actie.getVerzendendId()));
            if (actie.isInschrijvingGeboorte()) {
                Relatie relatie = (Relatie) actie.getRootObjecten().get(0);
                Assert.assertTrue(StringUtils.isNotBlank(relatie.getVerzendendId()));
                for (Betrokkenheid betr : relatie.getBetrokkenheden()) {
                    Assert.assertTrue(StringUtils.isNotBlank(betr.getVerzendendId()));
                    Persoon persoon = betr.getBetrokkene();
                    Assert.assertTrue(StringUtils.isNotBlank(persoon.getVerzendendId()));
                    // check nu alle id's van een persoon.
                    checkAlleVerzendedIds(persoon);
                }
            } else if (actie.isVerhuizing()) {
                Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
                checkAlleVerzendedIds(persoon);
            } else if (actie.isRegistratieNationaliteit()) {
                Persoon persoon = (Persoon) actie.getRootObjecten().get(0);
                checkAlleVerzendedIds(persoon);
            }
        }
    }

    @Override
    public Class<InschrijvingGeboorteBericht> getBindingClass() {
        return InschrijvingGeboorteBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Afstamming_Berichten.xsd";
    }

}
