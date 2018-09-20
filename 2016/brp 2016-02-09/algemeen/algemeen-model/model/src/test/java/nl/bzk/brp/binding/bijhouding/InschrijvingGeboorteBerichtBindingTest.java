/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;
import java.util.List;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


/**
 * Binding test voor een {@link nl.bzk.brp.model.bijhouding.BijhoudingsBericht} met daarin een
 * inschrijving geboorte bericht.
 */
public class InschrijvingGeboorteBerichtBindingTest extends AbstractBindingInIntegratieTest<RegistreerGeboorteBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        final String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MAX.xml");
        valideerTegenSchema(xml);

        final BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "123456", "UNITTEST", "12345678-1234-1234-1234-123456789123");
        checkActies(bericht, 20120101, SoortActie.REGISTRATIE_GEBOORTE, SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS,
                SoortActie.REGISTRATIE_NAAMGEBRUIK, SoortActie.REGISTRATIE_NATIONALITEIT,
                SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER, SoortActie.REGISTRATIE_STAATLOOS,
                SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING);

        final RelatieBericht relatie = getRelatieUitGeboorteActie(bericht);
        checkRelatieEnBetrokkenheden(relatie, 3);

        final PersoonBericht kind = getKindUitGeboorteActie(bericht);
        checkGeslacht(kind, Geslachtsaanduiding.ONBEKEND);
        // Aanschrijving zit niet meer in het inschrijving geboorte bericht
        // checkAanschrijving(kind, JaNee.JA, JaNee.NEE, "Test");
        checkGeboorte(kind, 20120101);
        checkVoornamen(kind, "Piet", "Veerman");
        checkGeslachtsnaamcomponenten(kind, new String[] { "Bokkel", "van", "/" });

        final PersoonBericht ouder1 = getOuderUitGeboorteActie(bericht, 111222333L);
        if (null != ouder1) {
            // .GRAAF
            checkSamengesteldeNaam(ouder1, "G", "Jan-Pieter", "Meern");
        }

        // actie[1].bron[x] zijn nog niet geimplementeerd.
        final PersoonBericht persoon = getKindUitRegistratieNationaliteitActie(bericht);
        // groep AfgeleidAdministratief, Indicaties
        // nog niet gebind in Jibx.
        // pas dit aan als het WEL geimplementeerd is.
        Assert.assertNull(persoon.getAfgeleidAdministratief());

        // Nog niet supported
        // Assert.assertTrue(persoon.getIndicaties().isEmpty());

        checkNationaliteit(persoon, "0001", "002", "reden verlies");

        for (final Actie actie : bericht.getAdministratieveHandeling().getActies()) {
            if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER) {
                checkIndicatie((PersoonBericht) actie.getRootObject(),
                        SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER);
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_STAATLOOS) {
                checkIndicatie((PersoonBericht) actie.getRootObject(), SoortIndicatie.INDICATIE_STAATLOOS);
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING) {
                checkIndicatie((PersoonBericht) actie.getRootObject(),
                        SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING);
            }

        }
        checkAlleVerzendedIds(bericht);
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN.xml");
        valideerTegenSchema(xml);

        final BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "123456", "UNITTEST", "12345678-1234-1234-1234-123456789123");
        checkActies(bericht, 10120102, SoortActie.REGISTRATIE_GEBOORTE, SoortActie.REGISTRATIE_NAAMGEBRUIK,
                SoortActie.REGISTRATIE_NATIONALITEIT);

        final PersoonBericht kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        Assert.assertNotNull(kind.getGeslachtsaanduiding());
        Assert.assertEquals(Geslachtsaanduiding.VROUW, kind.getGeslachtsaanduiding().getGeslachtsaanduiding()
                .getWaarde());
        Assert.assertNotNull(kind.getGeboorte());
        Assert.assertEquals(20120101, kind.getGeboorte().getDatumGeboorte().getWaarde().longValue());
        Assert.assertEquals("6039", kind.getGeboorte().getGemeenteGeboorteCode());
        Assert.assertNotNull(kind.getGeslachtsnaamcomponenten());
        // Assert.assertEquals(Integer.valueOf(1),
        // kind.getGeslachtsnaamcomponenten().get(0).getVolgnummer().getWaarde());
        Assert.assertEquals("V", kind.getGeslachtsnaamcomponenten().get(0).getStandaard().getStam().getWaarde());

        Assert.assertNull(kind.getVoornamen());
        Assert.assertNull(kind.getBetrokkenheden());
    }

    /**
     * Test minimaal bericht velden op de laagste niveau op nil staan waar het toegestaan is volgens de xsd.
     *
     * @throws java.io.IOException
     * @throws org.jibx.runtime.JiBXException
     */
    @Test
    public void testBindingBerichtMinNilInWaarde() throws IOException, JiBXException {
        final String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN-waardeNiveau.xml");
        valideerTegenSchema(xml);

        final BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        final PersoonBericht kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        // Assert.assertNull(kind.getIdentificatienummers().getAdministratienummer());
        // Assert.assertNull(kind.getIdentificatienummers().getBurgerservicenummer());

        Assert.assertNull(kind.getGeboorte().getGemeenteGeboorte());
        Assert.assertNull(kind.getGeboorte().getWoonplaatsnaamGeboorte());
        Assert.assertNull(kind.getGeboorte().getBuitenlandsePlaatsGeboorte());
        Assert.assertNull(kind.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertNull(kind.getGeboorte().getOmschrijvingLocatieGeboorte());

        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getStandaard().getVoorvoegsel());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getStandaard().getScheidingsteken());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getStandaard().getPredicaat());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten().get(0).getStandaard().getAdellijkeTitel());

        final PersoonBericht ouder = getOuderUitGeboorteActie(bericht);

        Assert.assertNull(ouder.getGeboorte().getWoonplaatsnaamGeboorte());
        Assert.assertNull(ouder.getGeboorte().getBuitenlandsePlaatsGeboorte());
        Assert.assertNull(ouder.getGeboorte().getBuitenlandseRegioGeboorte());
        Assert.assertNull(ouder.getGeboorte().getOmschrijvingLocatieGeboorte());

        Assert.assertNull(ouder.getSamengesteldeNaam().getPredicaat());
        Assert.assertNull(ouder.getSamengesteldeNaam().getAdellijkeTitel());
        Assert.assertNull(ouder.getSamengesteldeNaam().getVoornamen());
        Assert.assertNull(ouder.getSamengesteldeNaam().getVoorvoegsel());
        Assert.assertNull(ouder.getSamengesteldeNaam().getScheidingsteken());

        // ouderschap
        final OuderBericht betrokkenheid =
                (OuderBericht) ((RelatieBericht) bericht.getAdministratieveHandeling().getActies().get(0)
                        .getRootObject())
                        .getBetrokkenheden().get(1);
        Assert.assertNotNull(betrokkenheid.getOuderschap());
        Assert.assertNull(betrokkenheid.getOuderschap().getIndicatieOuder());
        Assert.assertNull(betrokkenheid.getOuderlijkGezag());
    }

    /**
     * Test sub groepen nil.
     *
     * @throws java.io.IOException
     * @throws org.jibx.runtime.JiBXException
     */
    @Test
    public void testBindingBerichtMinInGroep() throws IOException, JiBXException {
        final String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN-groepNiveau.xml");
        valideerTegenSchema(xml);

        final BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
    }

    /**
     * Controle van de stuurgegevens; controle of opgegeven stuurgegevens zijn gevonden.
     */
    private void checkStuurgegevens(final BijhoudingsBericht bericht, final String zendendePartij,
            final String zendendeSysteem, final String referentieNummer)
    {
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = bericht.getStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals(zendendeSysteem, berichtStuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals(zendendePartij, berichtStuurgegevens.getZendendePartijCode());
        Assert.assertEquals(referentieNummer, berichtStuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());
    }

    /**
     * Controle van de acties; controle of de verwachte acties er zijn, de datum aanvanggeldigheid ook klopt en of de
     * rootobjecten er zijn en van het type {@link nl.bzk.brp.model.bericht.kern.PersoonBericht} zijn.
     */
    private void checkActies(final BijhoudingsBericht bericht, final int datumAanvangGeldigheid,
            final SoortActie... actieSoorten)
    {
        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertEquals(actieSoorten.length, bericht.getAdministratieveHandeling().getActies().size());

        for (int i = 0; i < actieSoorten.length; i++) {
            final Actie actie = bericht.getAdministratieveHandeling().getActies().get(i);
            Assert.assertEquals(datumAanvangGeldigheid, actie.getDatumAanvangGeldigheid().getWaarde().intValue());
            Assert.assertEquals(actieSoorten[i], actie.getSoort().getWaarde());
            Assert.assertNotNull(actie.getRootObject());
            final RootObject rootObject = actie.getRootObject();
            Assert.assertNotNull(rootObject);
            if (SoortActie.REGISTRATIE_GEBOORTE == actieSoorten[i]) {
                Assert.assertTrue(rootObject instanceof RelatieBericht);
            } else if (SoortActie.REGISTRATIE_NATIONALITEIT == actieSoorten[i]) {
                Assert.assertTrue(rootObject instanceof PersoonBericht);
            }
        }
    }

    /**
     * Retourneert de relatie uit de geboorte actie.
     */
    private RelatieBericht getRelatieUitGeboorteActie(final BijhoudingsBericht bericht) {
        final Actie geboortActie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertEquals(SoortActie.REGISTRATIE_GEBOORTE, geboortActie.getSoort().getWaarde());
        final RootObject relatie = geboortActie.getRootObject();
        Assert.assertTrue(relatie instanceof RelatieBericht);
        return (RelatieBericht) relatie;
    }

    /**
     * Retourneert het kind dat in het bericht wordt ingeschreven; uit de eerst actie (moet een aangifte geboorte
     * actie zijn) wordt het eerste root object gehaald, wat een persoon (het kind) zou moeten zijn.
     */
    private PersoonBericht getKindUitGeboorteActie(final BijhoudingsBericht bericht) {
        Assert.assertFalse(bericht.getAdministratieveHandeling().getActies().isEmpty());
        final Actie actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertEquals(SoortActie.REGISTRATIE_GEBOORTE, actie.getSoort().getWaarde());
        Assert.assertNotNull(actie.getRootObject());

        PersoonBericht kind = null;
        for (final BetrokkenheidBericht betrokkenheid : ((RelatieBericht) actie.getRootObject()).getBetrokkenheden()) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.KIND) {
                kind = betrokkenheid.getPersoon();
                break;
            }
        }

        Assert.assertNotNull(kind);
        return kind;
    }

    /**
     * Retourneert de ouder.
     */
    private PersoonBericht getOuderUitGeboorteActie(final BijhoudingsBericht bericht,
            final Long technischeSleutel)
    {
        Assert.assertFalse(bericht.getAdministratieveHandeling().getActies().isEmpty());
        final Actie actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertEquals(SoortActie.REGISTRATIE_GEBOORTE, actie.getSoort().getWaarde());
        Assert.assertNotNull(actie.getRootObject());

        PersoonBericht ouder = null;
        for (final BetrokkenheidBericht betrokkenheid : ((RelatieBericht) actie.getRootObject()).getBetrokkenheden()) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.OUDER) {
                if (null == technischeSleutel) {
                    ouder = betrokkenheid.getPersoon();
                    break;
                } else if (technischeSleutel.toString().equals(betrokkenheid.getPersoon().getObjectSleutel())) {
                    ouder = betrokkenheid.getPersoon();
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
        Assert.assertTrue(bericht.getAdministratieveHandeling().getActies().size() >= 2);
        final Actie actieRegistratieNationaliteit = bericht.getAdministratieveHandeling().getActies().get(3);
        Assert.assertEquals(SoortActie.REGISTRATIE_NATIONALITEIT, actieRegistratieNationaliteit.getSoort().getWaarde());
        Assert.assertNotNull(actieRegistratieNationaliteit.getRootObject());
        return (PersoonBericht) actieRegistratieNationaliteit.getRootObject();
    }

    /**
     * Controle op identificatienummers en geslachtsaanduiding.
     */
    private void checkGeslacht(final PersoonBericht kind, final Geslachtsaanduiding geslachtsaanduiding) {
        Assert.assertEquals(geslachtsaanduiding, kind.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde());
    }

    /**
     * Controle op de aanschrijving.
     */
    private void checkAanschrijving(final PersoonBericht kind, final JaNeeAttribuut indAdellijkeTitels,
            final JaNeeAttribuut indAfgeleid, final String naam)
    {
        final PersoonNaamgebruikGroepBericht naamgebruik = kind.getNaamgebruik();
        Assert.assertEquals(indAdellijkeTitels, naamgebruik.getIndicatieNaamgebruikAfgeleid());
        Assert.assertEquals(indAfgeleid, naamgebruik.getIndicatieNaamgebruikAfgeleid());
        Assert.assertEquals(naam, naamgebruik.getGeslachtsnaamstamNaamgebruik().getWaarde());
    }

    private void checkSamengesteldeNaam(final PersoonBericht persoon, final String adellijkeTitel,
            final String voornamen, final String naam)
    {
        final PersoonSamengesteldeNaamGroepBericht samengesteldenaam = persoon.getSamengesteldeNaam();
        Assert.assertEquals(adellijkeTitel, samengesteldenaam.getAdellijkeTitelCode());
        Assert.assertEquals(voornamen, samengesteldenaam.getVoornamen().getWaarde());
        // Assert.assertEquals(indnamenReeks, samengesteldenaam.getIndNamenreeksAlsGeslachtsnaam());
        Assert.assertEquals(naam, samengesteldenaam.getGeslachtsnaamstam().getWaarde());
    }

    /**
     * Controle op geboorte; controleert of geboorte standaard adres info heeft en opgegeven datum.
     */
    private void checkGeboorte(final PersoonBericht kind, final int datumGeboorte) {
        final PersoonGeboorteGroepBericht geboorte = kind.getGeboorte();
        Assert.assertEquals(datumGeboorte, geboorte.getDatumGeboorte().getWaarde().intValue());
        Assert.assertNull(geboorte.getGemeenteGeboorte());
        Assert.assertEquals("1111", geboorte.getGemeenteGeboorteCode());
        Assert.assertNull(geboorte.getWoonplaatsnaamGeboorte());
    }

    /**
     * Controle van de voornamen; de opgegeven voornamen moeten opvolgend aanwezig zijn.
     */
    private void checkVoornamen(final PersoonBericht kind, final String... voornamen) {
        final List<PersoonVoornaamBericht> persoonVoornamen = kind.getVoornamen();
        Assert.assertEquals(voornamen.length, persoonVoornamen.size());

        for (int i = 0; i < persoonVoornamen.size(); i++) {
            final PersoonVoornaamBericht persoonVoornaam = persoonVoornamen.get(i);
            Assert.assertEquals(i + 1, persoonVoornaam.getVolgnummer().getWaarde().intValue());
            Assert.assertEquals(voornamen[i], persoonVoornaam.getStandaard().getNaam().getWaarde());
        }
    }

    /**
     * Controle van de geslachtsnaamcomponenten.
     */
    private void checkGeslachtsnaamcomponenten(final PersoonBericht kind, final String[]... componenten) {
        final List<PersoonGeslachtsnaamcomponentBericht> persoonGeslachtsnaamcomponenten = kind.getGeslachtsnaamcomponenten();
        Assert.assertEquals(componenten.length, persoonGeslachtsnaamcomponenten.size());
        for (int i = 0; i < componenten.length; i++) {
            final PersoonGeslachtsnaamcomponentBericht component = persoonGeslachtsnaamcomponenten.get(i);
            // Assert.assertEquals(i + 1, component.getVolgnummer().getWaarde().intValue());
            Assert.assertEquals(componenten[i][0], component.getStandaard().getStam().getWaarde());
            Assert.assertEquals(componenten[i][1], component.getStandaard().getVoorvoegsel().getWaarde());
            Assert.assertEquals(componenten[i][2], component.getStandaard().getScheidingsteken().getWaarde());
        }
    }

    /**
     * Controle op de relatie en de betrokkenheden.
     */
    private void checkRelatieEnBetrokkenheden(final RelatieBericht relatie, final int aantalBetrokkenheden) {
        Assert.assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoort().getWaarde());
        Assert.assertEquals(aantalBetrokkenheden, relatie.getBetrokkenheden().size());
        for (final BetrokkenheidBericht betrokkenheid : relatie.getBetrokkenheden()) {
            Assert.assertTrue(betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.KIND
                    || betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.OUDER);
        }
    }

    /**
     * Controle van nationaliteit van een persoon; Controleert of nationaliteit beschikt over opgegeven waardes.
     */
    private void checkNationaliteit(final PersoonBericht persoon, final String nationaliteitcode,
            final String rdnVerkrijging, final String rdnVerlies)
    {
        final PersoonNationaliteitBericht persoonNationaliteit = persoon.getNationaliteiten().get(0);
        Assert.assertEquals(nationaliteitcode, persoonNationaliteit.getNationaliteitCode());
        Assert.assertEquals(rdnVerkrijging, persoonNationaliteit.getStandaard().getRedenVerkrijgingCode());

        // Reden verlies komt niet meer voor in de xsd
        // Assert.assertEquals(rdnVerlies, persoonNationaliteit.getRedenVerliesNaam());
    }

    private void checkIndicatie(final PersoonBericht persoon, final SoortIndicatie soortIndicatie) {
        // datum check ??
        Assert.assertNotNull(persoon.getIndicaties());
        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertEquals(soortIndicatie, persoon.getIndicaties().get(0).getSoort().getWaarde());
        Assert.assertEquals(Ja.J, persoon.getIndicaties().get(0).getStandaard().getWaarde().getWaarde());
    }

    private void checkAlleVerzendedIds(final PersoonBericht persoon) {
        Assert.assertTrue(StringUtils.isNotBlank(persoon.getCommunicatieID()));
        if (persoon.getIdentificatienummers() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getIdentificatienummers().getCommunicatieID()));
        }
        if (persoon.getAdressen() != null && !persoon.getAdressen().isEmpty()) {
            for (final PersoonAdresBericht adres : persoon.getAdressen()) {
                Assert.assertTrue(StringUtils.isNotBlank(adres.getCommunicatieID()));
            }
        }
        if (persoon.getAfgeleidAdministratief() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getAfgeleidAdministratief().getCommunicatieID()));
        }
        if (persoon.getBijhouding() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getBijhouding().getCommunicatieID()));
        }
        if (persoon.getGeboorte() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getGeboorte().getCommunicatieID()));
        }
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            for (final PersoonGeslachtsnaamcomponentBericht geslnaamcomp : persoon.getGeslachtsnaamcomponenten()) {
                Assert.assertTrue(StringUtils.isNotBlank(geslnaamcomp.getCommunicatieID()));
            }
        }
        // TODO indicaties ...
        if (persoon.getNationaliteiten() != null && !persoon.getNationaliteiten().isEmpty()) {
            for (final PersoonNationaliteitBericht nationaliteit : persoon.getNationaliteiten()) {
                Assert.assertTrue(StringUtils.isNotBlank(nationaliteit.getCommunicatieID()));
            }
        }
        if (persoon.getOverlijden() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getOverlijden().getCommunicatieID()));
        }
        if (persoon.getGeslachtsaanduiding() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getGeslachtsaanduiding().getCommunicatieID()));
        }
        if (persoon.getVoornamen() != null && !persoon.getVoornamen().isEmpty()) {
            for (final PersoonVoornaamBericht voornaam : persoon.getVoornamen()) {
                Assert.assertTrue(StringUtils.isNotBlank(voornaam.getCommunicatieID()));
            }
        }
        if (persoon.getBijhouding() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getBijhouding().getCommunicatieID()));
        }
        if (persoon.getNaamgebruik() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getNaamgebruik().getCommunicatieID()));
        }
        if (persoon.getSamengesteldeNaam() != null) {
            Assert.assertTrue(StringUtils.isNotBlank(persoon.getSamengesteldeNaam().getCommunicatieID()));
        }
    }

    private void checkAlleVerzendedIds(final BijhoudingsBericht bericht) {
        if (bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen() != null
                && !bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen().isEmpty())
        {
            for (final AdministratieveHandelingGedeblokkeerdeMeldingBericht melding : bericht.getAdministratieveHandeling()
                    .getGedeblokkeerdeMeldingen())
            {
                Assert.assertTrue(StringUtils.isNotBlank(melding.getGedeblokkeerdeMelding().getCommunicatieID()));
            }
        }

        // we zijn hier niet geinteresseerd in de echte waarde va de id's zolang het maar niet null/empty is.
        // we weten dat in sommige gevallen 2 groepen op een groep is gemapped.
        // (Betrokkenheid en Ouderschap.
        for (final Actie actie : bericht.getAdministratieveHandeling().getActies()) {
            // TODO nog niet geimplementeerd.
            // Assert.assertTrue(StringUtils.isNotBlank(actie.getCommunicatieID()));
            if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_GEBOORTE) {
                final RelatieBericht relatie = (RelatieBericht) actie.getRootObject();
                Assert.assertTrue(StringUtils.isNotBlank(relatie.getCommunicatieID()));
                for (final BetrokkenheidBericht betr : relatie.getBetrokkenheden()) {
                    Assert.assertTrue(StringUtils.isNotBlank(betr.getCommunicatieID()));
                    final PersoonBericht persoon = betr.getPersoon();
                    Assert.assertTrue(StringUtils.isNotBlank(persoon.getCommunicatieID()));
                    // check nu alle id's van een persoon.
                    checkAlleVerzendedIds(persoon);
                }
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_ADRES) {
                final PersoonBericht persoon = (PersoonBericht) actie.getRootObject();
                checkAlleVerzendedIds(persoon);
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_NATIONALITEIT) {
                final PersoonBericht persoon = (PersoonBericht) actie.getRootObject();
                checkAlleVerzendedIds(persoon);
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER) {
                final PersoonBericht persoon = (PersoonBericht) actie.getRootObject();
                checkAlleVerzendedIds(persoon);
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_STAATLOOS) {
                final PersoonBericht persoon = (PersoonBericht) actie.getRootObject();
                checkAlleVerzendedIds(persoon);
            } else if (actie.getSoort().getWaarde() == SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING) {
                final PersoonBericht persoon = (PersoonBericht) actie.getRootObject();
                checkAlleVerzendedIds(persoon);
            }
            // hmm, wat is hier de toegevogde waarde voor alle 'indicaties' ?
        }
    }

    @Override
    public Class<RegistreerGeboorteBericht> getBindingClass() {
        return RegistreerGeboorteBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdAfstammingBerichten();
    }

}
