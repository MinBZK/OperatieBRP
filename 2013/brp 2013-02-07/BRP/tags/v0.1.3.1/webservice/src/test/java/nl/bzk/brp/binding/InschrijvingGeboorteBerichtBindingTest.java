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
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeAanschrijving;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/** Binding test voor een {@link nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht} met daarin een inschrijving geboorte bericht. */
public class InschrijvingGeboorteBerichtBindingTest extends AbstractBindingInTest<InschrijvingGeboorteBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MAX.xml");
        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "organisatie", "applicatie", "referentienummer");
        checkActies(bericht, 20120101, SoortActie.AANGIFTE_GEBOORTE, SoortActie.REGISTRATIE_NATIONALITEIT);

        Relatie relatie = getRelatieUitGeboorteActie(bericht);
        checkRelatieEnBetrokkenheden(relatie, 3);

        Persoon kind = getKindUitGeboorteActie(bericht);
        checkIdentificatieNummersEnGeslacht(kind, "123456789", "123456789", GeslachtsAanduiding.ONBEKEND);
        checkAanschrijving(kind, true, false, "Test");
        checkGeboorte(kind, 20120101);
        checkVoornamen(kind, "Piet", "Veerman");
        checkGeslachtsnaamcomponenten(kind, new String[]{ "Bokkel", "van", "/" });

        Persoon persoon = getKindUitRegistratieNationaliteitActie(bericht);
        checkNationaliteit(persoon, "1", "reden verkrijging", "reden verlies");
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN.xml");

        AbstractBijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        checkStuurgegevens(bericht, "16", "bzm", "0123456789");
        checkActies(bericht, 1012012, SoortActie.AANGIFTE_GEBOORTE);

        Persoon kind = getKindUitGeboorteActie(bericht);
        Assert.assertNull(kind.getIdentificatienummers());
        Assert.assertNull(kind.getPersoonGeslachtsAanduiding());
        Assert.assertNull(kind.getGeboorte());
        Assert.assertNull(kind.getGeslachtsnaamcomponenten());
        Assert.assertNull(kind.getPersoonVoornamen());
        Assert.assertTrue(kind.getBetrokkenheden().isEmpty());
    }

    /** Controle van de stuurgegevens; controle of opgegeven stuurgegevens zijn gevonden. */
    private void checkStuurgegevens(final AbstractBijhoudingsBericht bericht, final String organisatie, final String applicatie,
        final String referentieNummer)
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
     * Retourneert het kind waarvan in het bericht de nationaliteit wordt geregistreerd; uit de tweede actie (moet een
     * registratie nationaliteit zijn) wordt het eerste root object gehaald, wat een persoon (het kind) zou moeten
     * zijn.
     */
    private Persoon getKindUitRegistratieNationaliteitActie(final AbstractBijhoudingsBericht bericht) {
        Assert.assertTrue(bericht.getBrpActies().size() >= 2);
        BRPActie actie = bericht.getBrpActies().get(1);
        Assert.assertEquals(SoortActie.REGISTRATIE_NATIONALITEIT, actie.getSoort());
        Assert.assertFalse(actie.getRootObjecten().isEmpty());
        return (Persoon) actie.getRootObjecten().get(0);
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
        Assert.assertEquals(rdnVerlies, persoonNationaliteit.getRedenVerliesNaam());
    }

    @Override
    public Class<InschrijvingGeboorteBericht> getBindingClass() {
        return InschrijvingGeboorteBericht.class;
    }

}
