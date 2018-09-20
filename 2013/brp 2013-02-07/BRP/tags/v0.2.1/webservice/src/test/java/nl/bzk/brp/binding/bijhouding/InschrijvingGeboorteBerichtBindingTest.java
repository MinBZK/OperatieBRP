/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;
import java.util.List;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.OverruleMelding;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Binding test voor een {@link nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht} met daarin een
 * inschrijving geboorte bericht.
 */
public class InschrijvingGeboorteBerichtBindingTest
    extends AbstractBindingInIntegratieTest<InschrijvingGeboorteBericht>
{

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MAX.xml");
        valideerTegenSchema(xml);

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "organisatie", "applicatie", "referentienummer");
        checkActies(bericht, 20120101, SoortActie.AANGIFTE_GEBOORTE, SoortActie.REGISTRATIE_NATIONALITEIT);
        // Assert.assertEquals("id.inschrijvingGeboorte", bericht.getBrpActies().get(0).getVerzendendId());
        // Assert.assertEquals("id.regNat", bericht.getBrpActies().get(1).getVerzendendId());

        RelatieBericht relatie = getRelatieUitGeboorteActie(bericht);
        checkRelatieEnBetrokkenheden(relatie, 3);

        PersoonBericht kind = getKindUitGeboorteActie(bericht);
        checkIdentificatienummersEnGeslacht(kind, "123456789", 123456789L, Geslachtsaanduiding.ONBEKEND);
        checkAanschrijving(kind, JaNee.Ja, JaNee.Nee, "Test");
        checkGeboorte(kind, 20120101);
        checkVoornamen(kind, "Piet", "Veerman");
        checkGeslachtsnaamcomponenten(kind, new String[] { "Bokkel", "van", "/" });

        PersoonBericht ouder1 = getOuderUitGeboorteActie(bericht, "111222333");
        if (null != ouder1) {
            // .GRAAF
            checkSamengesteldeNaam(ouder1, "G", "Jan-Pieter", "Meern");
        }

        // actie[1].bron[x] zijn nog niet geimplementeerd.
        PersoonBericht persoon = getKindUitRegistratieNationaliteitActie(bericht);
        // groep AfgeleidAdministratief, Indicaties
        // nog niet gebind in Jibx.
        // pas dit aan als het WEL geimplementeerd is.
        Assert.assertNull(persoon.getAfgeleidAdministratief());

        // Nog niet supported
        // Assert.assertTrue(persoon.getIndicaties().isEmpty());

        checkNationaliteit(persoon, (short) 1, (short) 2, "reden verlies");

        checkAlleVerzendedIds(bericht);
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN.xml");
        valideerTegenSchema(xml);

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "16", "bzm", "0123456789");
        checkActies(bericht, 1012012, SoortActie.AANGIFTE_GEBOORTE, SoortActie.REGISTRATIE_NATIONALITEIT);

        PersoonBericht kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        Assert.assertNotNull(kind.getGeslachtsaanduiding());
        Assert.assertEquals(Geslachtsaanduiding.VROUW, kind.getGeslachtsaanduiding().getGeslachtsaanduiding());
        Assert.assertNotNull(kind.getGeboorte());
        Assert.assertEquals(20120101, kind.getGeboorte().getDatumGeboorte().getWaarde().longValue());
        Assert.assertEquals("6039", kind.getGeboorte().getLandGeboorteCode().toString());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten());
        Assert.assertNull(kind.getPersoonVoornaam());
        Assert.assertNull(kind.getBetrokkenheden());
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
        valideerTegenSchema(xml);

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        PersoonBericht kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        Assert.assertNull(kind.getGeslachtsaanduiding());
        Assert.assertNull(kind.getGeboorte());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten());
        Assert.assertNull(kind.getPersoonVoornaam());
        Assert.assertNull(kind.getBetrokkenheden());
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
        valideerTegenSchema(xml);

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        PersoonBericht kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers().getAdministratienummer());
        Assert.assertNull(kind.getIdentificatienummers().getBurgerservicenummer());

        Assert.assertNull(kind.getGeboorte().getGemeenteGeboorte());
        Assert.assertNull(kind.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertNull(kind.getGeboorte().getWoonplaatsGeboorte());
        Assert.assertNull(kind.getGeboorte().getWoonplaatsGeboorteCode());
        Assert.assertNull(kind.getGeboorte().getBuitenlandseGeboortePlaats());
        Assert.assertNull(kind.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertNull(kind.getGeboorte().getOmschrijvingGeboorteLocatie());

        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getGegevens().getVoorvoegsel());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getGegevens().getScheidingsteken());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getGegevens().getPredikaat());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getGegevens().getAdellijkeTitel());

        PersoonBericht ouder = getOuderUitGeboorteActie(bericht);

        Assert.assertNull(ouder.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertNull(ouder.getGeboorte().getWoonplaatsGeboorteCode());
        Assert.assertNull(ouder.getGeboorte().getBuitenlandseGeboortePlaats());
        Assert.assertNull(ouder.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertNull(ouder.getGeboorte().getOmschrijvingGeboorteLocatie());

        Assert.assertNull(ouder.getSamengesteldeNaam().getPredikaat());
        Assert.assertNull(ouder.getSamengesteldeNaam().getAdellijkeTitel());
        Assert.assertNull(ouder.getSamengesteldeNaam().getVoornamen());
        Assert.assertNull(ouder.getSamengesteldeNaam().getVoorvoegsel());
        Assert.assertNull(ouder.getSamengesteldeNaam().getScheidingsteken());

        // ouderschap
        BetrokkenheidBericht betrokkenheid =
            ((RelatieBericht) bericht.getBrpActies().get(0).getRootObjecten().get(0)).getBetrokkenheden().iterator()
                    .next();
        Assert.assertNull(betrokkenheid.getBetrokkenheidOuderschap());
        Assert.assertNull(betrokkenheid.getBetrokkenheidOuderlijkGezag());
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
        valideerTegenSchema(xml);

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
    }

    /** Controle van de stuurgegevens; controle of opgegeven stuurgegevens zijn gevonden. */
    private void checkStuurgegevens(final BijhoudingsBericht bericht, final String organisatie,
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
     * rootobjecten er zijn en van het type {@link PersoonBericht} zijn.
     */
    private void checkActies(final BijhoudingsBericht bericht, final int datumAanvangGeldigheid,
            final SoortActie... actieSoorten)
    {
        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertEquals(actieSoorten.length, bericht.getBrpActies().size());

        for (int i = 0; i < actieSoorten.length; i++) {
            Actie actie = bericht.getBrpActies().get(i);
            Assert.assertEquals(datumAanvangGeldigheid, actie.getDatumAanvangGeldigheid().getWaarde().intValue());
            Assert.assertEquals(actieSoorten[i], actie.getSoort());
            Assert.assertNotNull(actie.getRootObjecten());
            Assert.assertEquals(1, actie.getRootObjecten().size());
            RootObject rootObject = actie.getRootObjecten().get(0);
            Assert.assertNotNull(rootObject);
            if (SoortActie.AANGIFTE_GEBOORTE == actieSoorten[i]) {
                Assert.assertTrue(rootObject instanceof RelatieBericht);
            } else if (SoortActie.REGISTRATIE_NATIONALITEIT == actieSoorten[i]) {
                Assert.assertTrue(rootObject instanceof PersoonBericht);
            }
        }
    }

    /** Retourneert de relatie uit de geboorte actie. */
    private RelatieBericht getRelatieUitGeboorteActie(final BijhoudingsBericht bericht) {
        Actie geboortActie = bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, geboortActie.getSoort());
        RootObject relatie = geboortActie.getRootObjecten().get(0);
        Assert.assertTrue(relatie instanceof RelatieBericht);
        return (RelatieBericht) relatie;
    }

    /**
     * Retourneert het kind dat in het bericht wordt ingeschreven; uit de eerst actie (moet een aangifte geboorte
     * actie zijn) wordt het eerste root object gehaald, wat een persoon (het kind) zou moeten zijn.
     */
    private PersoonBericht getKindUitGeboorteActie(final BijhoudingsBericht bericht) {
        Assert.assertFalse(bericht.getBrpActies().isEmpty());
        Actie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getSoort());
        Assert.assertFalse(actie.getRootObjecten().isEmpty());

        PersoonBericht kind = null;
        for (BetrokkenheidBericht betrokkenheid : ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden())
        {
            if (betrokkenheid.getRol() == SoortBetrokkenheid.KIND) {
                kind = betrokkenheid.getBetrokkene();
                break;
            }
        }

        Assert.assertNotNull(kind);
        return kind;
    }

    /** Retourneert de ouder. */
    private PersoonBericht getOuderUitGeboorteActie(final BijhoudingsBericht bericht, final String bsn) {
        Assert.assertFalse(bericht.getBrpActies().isEmpty());
        Actie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getSoort());
        Assert.assertFalse(actie.getRootObjecten().isEmpty());

        PersoonBericht ouder = null;
        for (BetrokkenheidBericht betrokkenheid : ((RelatieBericht) actie.getRootObjecten().get(0)).getBetrokkenheden())
        {
            if (betrokkenheid.getRol() == SoortBetrokkenheid.OUDER) {
                if (null == bsn) {
                    ouder = betrokkenheid.getBetrokkene();
                    break;
                } else if (bsn.equals(betrokkenheid.getBetrokkene().getIdentificatienummers().getBurgerservicenummer()
                        .getWaarde()))
                {
                    ouder = betrokkenheid.getBetrokkene();
                    break;
                }
            }
        }

        Assert.assertNotNull(ouder);
        return ouder;
    }

    private PersoonBericht getOuderUitGeboorteActie(final BijhoudingsBericht bericht) {
        // willekeurige ouder uit de lijst
        return getOuderUitGeboorteActie(bericht, null);
    }

    /**
     * Retourneert het kind waarvan in het bericht de nationaliteit wordt geregistreerd; uit de tweede actie (moet een
     * registratie nationaliteit zijn) wordt het eerste root object gehaald, wat een persoon (het kind) zou moeten
     * zijn.
     */
    private PersoonBericht getKindUitRegistratieNationaliteitActie(final BijhoudingsBericht bericht) {
        Assert.assertTrue(bericht.getBrpActies().size() >= 2);
        Actie actieRegistratieNationaliteit = bericht.getBrpActies().get(1);
        Assert.assertEquals(SoortActie.REGISTRATIE_NATIONALITEIT, actieRegistratieNationaliteit.getSoort());
        Assert.assertFalse(actieRegistratieNationaliteit.getRootObjecten().isEmpty());
        return (PersoonBericht) actieRegistratieNationaliteit.getRootObjecten().get(0);
    }

    /** Controle op identificatienummers en geslachtsaanduiding. */
    private void checkIdentificatienummersEnGeslacht(final PersoonBericht kind, final String bsn, final Long aNummer,
        final Geslachtsaanduiding geslachtsaanduiding)
    {
        Assert.assertEquals(bsn, kind.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(aNummer, kind.getIdentificatienummers().getAdministratienummer().getWaarde());
        Assert.assertEquals(geslachtsaanduiding, kind.getGeslachtsaanduiding().getGeslachtsaanduiding());
    }

    /** Controle op de aanschrijving. */
    private void checkAanschrijving(final PersoonBericht kind, final JaNee indAdellijkeTitels, final JaNee indAfgeleid,
            final String naam)
    {
        PersoonAanschrijvingGroepBericht aanschrijving = kind.getAanschrijving();
        Assert.assertEquals(indAdellijkeTitels, aanschrijving.getIndAanschrijvenMetAdellijkeTitel());
        Assert.assertEquals(indAfgeleid, aanschrijving.getIndAanschrijvingAlgorthmischAfgeleid());
        Assert.assertEquals(naam, aanschrijving.getGeslachtsnaam().getWaarde());
    }

    private void checkSamengesteldeNaam(final PersoonBericht persoon, final String adellijkeTitel,
            final String voornamen, final String naam)
    {
        PersoonSamengesteldeNaamGroepBericht samengesteldenaam = persoon.getSamengesteldeNaam();
        Assert.assertEquals(adellijkeTitel, samengesteldenaam.getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals(voornamen, samengesteldenaam.getVoornamen().getWaarde());
        // Assert.assertEquals(indnamenReeks, samengesteldenaam.getIndNamenreeksAlsGeslachtsnaam());
        Assert.assertEquals(naam, samengesteldenaam.getGeslachtsnaam().getWaarde());
    }

    /** Controle op geboorte; controleert of geboorte standaard adres info heeft en opgegeven datum. */
    private void checkGeboorte(final PersoonBericht kind, final int datumGeboorte) {
        PersoonGeboorteGroepBericht geboorte = kind.getGeboorte();
        Assert.assertEquals(datumGeboorte, geboorte.getDatumGeboorte().getWaarde().intValue());
        Assert.assertNull(geboorte.getGemeenteGeboorte());
        Assert.assertEquals("1111", geboorte.getGemeenteGeboorteCode().toString());
        Assert.assertNull(geboorte.getWoonplaatsGeboorte());
        Assert.assertEquals("1111", geboorte.getWoonplaatsGeboorteCode().toString());
        Assert.assertEquals("verweggiestan", geboorte.getBuitenlandseGeboortePlaats().getWaarde());
        Assert.assertEquals("derde berg links", geboorte.getBuitenlandseRegioGeboorte().getWaarde());
        Assert.assertNull(geboorte.getLandGeboorte());
        Assert.assertEquals("1111", geboorte.getLandGeboorteCode().toString());
        Assert.assertEquals("hoop zand", geboorte.getOmschrijvingGeboorteLocatie().getWaarde());
    }

    /** Controle van de voornamen; de opgegeven voornamen moeten opvolgend aanwezig zijn. */
    private void checkVoornamen(final PersoonBericht kind, final String... voornamen) {
        List<PersoonVoornaamBericht> persoonVoornamen = kind.getPersoonVoornaam();
        Assert.assertEquals(voornamen.length, persoonVoornamen.size());

        for (int i = 0; i < persoonVoornamen.size(); i++) {
            PersoonVoornaamBericht persoonVoornaam = persoonVoornamen.get(i);
            Assert.assertEquals(i + 1, persoonVoornaam.getVolgnummer().getWaarde().intValue());
            Assert.assertEquals(voornamen[i], persoonVoornaam.getGegevens().getVoornaam().getWaarde());
        }
    }

    /** Controle van de geslachtsnaamcomponenten. */
    private void checkGeslachtsnaamcomponenten(final PersoonBericht kind, final String[]... componenten) {
        List<PersoonGeslachtsnaamcomponentBericht> persoonGeslachtsnaamcomponenten = kind.getGeslachtsnaamcomponenten();
        Assert.assertEquals(componenten.length, persoonGeslachtsnaamcomponenten.size());
        for (int i = 0; i < componenten.length; i++) {
            PersoonGeslachtsnaamcomponentBericht component = persoonGeslachtsnaamcomponenten.get(i);
            Assert.assertEquals(i + 1, component.getVolgnummer().getWaarde().intValue());
            Assert.assertEquals(componenten[i][0], component.getGegevens().getNaam().getWaarde());
            Assert.assertEquals(componenten[i][1], component.getGegevens().getVoorvoegsel().getWaarde());
            Assert.assertEquals(componenten[i][2], component.getGegevens().getScheidingsteken().getWaarde());
        }
    }

    /** Controle op de relatie en de betrokkenheden. */
    private void checkRelatieEnBetrokkenheden(final RelatieBericht relatie, final int aantalBetrokkenheden) {
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoort());
        Assert.assertEquals(aantalBetrokkenheden, relatie.getBetrokkenheden().size());
        for (BetrokkenheidBericht betrokkenheid : relatie.getBetrokkenheden()) {
            Assert.assertTrue(betrokkenheid.getRol() == SoortBetrokkenheid.KIND
                || betrokkenheid.getRol() == SoortBetrokkenheid.OUDER);
        }
    }

    /** Controle van nationaliteit van een persoon; Controleert of nationaliteit beschikt over opgegeven waardes. */
    private void checkNationaliteit(final PersoonBericht persoon, final Short nationaliteitcode,
            final Short rdnVerkrijging, final String rdnVerlies)
    {
        PersoonNationaliteitBericht persoonNationaliteit = persoon.getNationaliteiten().get(0);
        Assert.assertEquals(nationaliteitcode, persoonNationaliteit.getNationaliteitcode().getWaarde());
        Assert.assertEquals(rdnVerkrijging, persoonNationaliteit.getGegevens().getRedenVerkregenNlNationaliteitCode().getWaarde());

        // Reden verlies komt niet meer voor in de xsd
        // Assert.assertEquals(rdnVerlies, persoonNationaliteit.getRedenVerliesNaam());
    }

    private void checkAlleVerzendedIds(final PersoonBericht persoon) {
        Assert.assertTrue(StringUtils.isNotBlank(persoon.getVerzendendId()));
        if (persoon.getIdentificatienummers() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getIdentificatienummers().getVerzendendId()));
        }
        if (persoon.getAdressen() != null && !persoon.getAdressen().isEmpty()) {
            for (PersoonAdresBericht adres : persoon.getAdressen()) {
                Assert.assertTrue(StringUtils.isNotBlank(adres.getVerzendendId()));
            }
        }
        if (persoon.getAfgeleidAdministratief() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getAfgeleidAdministratief().getVerzendendId()));
        }
        if (persoon.getBijhoudingsgemeente() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getBijhoudingsgemeente().getVerzendendId()));
        }
        if (persoon.getBijhoudingsverantwoordelijkheid() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getBijhoudingsverantwoordelijkheid().getVerzendendId()));
        }
        if (persoon.getGeboorte() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getGeboorte().getVerzendendId()));
        }
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            for (PersoonGeslachtsnaamcomponentBericht geslnaamcomp : persoon.getGeslachtsnaamcomponenten()) {
                Assert.assertTrue(StringUtils.isNotBlank(geslnaamcomp.getVerzendendId()));
            }
        }
        // TODO indicaties ...
        if (persoon.getNationaliteiten() != null && !persoon.getNationaliteiten().isEmpty()) {
            for (PersoonNationaliteitBericht nationaliteit : persoon.getNationaliteiten()) {
                Assert.assertTrue(StringUtils.isNotBlank(nationaliteit.getVerzendendId()));
            }
        }
        if (persoon.getOverlijden() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getOverlijden().getVerzendendId()));
        }
        if (persoon.getGeslachtsaanduiding() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getGeslachtsaanduiding().getVerzendendId()));
        }
        if (persoon.getPersoonVoornaam() != null && !persoon.getPersoonVoornaam().isEmpty()) {
            for (PersoonVoornaamBericht voornaam : persoon.getPersoonVoornaam()) {
                Assert.assertTrue(StringUtils.isNotBlank(voornaam.getVerzendendId()));
            }
        }
        if (persoon.getOpschorting() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getOpschorting().getVerzendendId()));
        }
        if (persoon.getAanschrijving() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getAanschrijving().getVerzendendId()));
        }
        if (persoon.getSamengesteldeNaam() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getSamengesteldeNaam().getVerzendendId()));
        }
    }

    private void checkAlleVerzendedIds(final BijhoudingsBericht bericht) {

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
        // (Betrokkenheid en Ouderschap.
        for (Actie actie : bericht.getBrpActies()) {
            // TODO nog niet geimplementeerd.
            // Assert.assertTrue(StringUtils.isNotBlank(actie.getVerzendendId()));
            if (actie.getSoort() == SoortActie.AANGIFTE_GEBOORTE) {
                RelatieBericht relatie = (RelatieBericht) actie.getRootObjecten().get(0);
                Assert.assertTrue(StringUtils.isNotBlank(relatie.getVerzendendId()));
                for (BetrokkenheidBericht betr : relatie.getBetrokkenheden()) {
                    Assert.assertTrue(StringUtils.isNotBlank(betr.getVerzendendId()));
                    PersoonBericht persoon = betr.getBetrokkene();
                    Assert.assertTrue(StringUtils.isNotBlank(persoon.getVerzendendId()));
                    // check nu alle id's van een persoon.
                    checkAlleVerzendedIds(persoon);
                }
            } else if (actie.getSoort() == nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie.VERHUIZING) {
                PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);
                checkAlleVerzendedIds(persoon);
            } else if (actie.getSoort() == nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie.REGISTRATIE_NATIONALITEIT)
            {
                PersoonBericht persoon = (PersoonBericht) actie.getRootObjecten().get(0);
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
