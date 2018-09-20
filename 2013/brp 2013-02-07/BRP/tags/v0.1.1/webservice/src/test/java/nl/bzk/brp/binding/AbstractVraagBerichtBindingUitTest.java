/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.gedeeld.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.gedeeld.AdellijkeTitel;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.Predikaat;
import nl.bzk.brp.model.gedeeld.RedenOpschorting;
import nl.bzk.brp.model.gedeeld.RedenWijzigingAdres;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.gedeeld.Verantwoordelijke;
import nl.bzk.brp.model.gedeeld.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonAfgeleidAdministratief;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsVerantwoordelijke;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;
import nl.bzk.brp.model.logisch.groep.PersoonInschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.groep.PersoonOverlijden;
import nl.bzk.brp.model.logisch.groep.PersoonRedenOpschorting;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeAanschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;

/**
 * Abstracte class bedoeld voor het testen van de binding van antwoordberichten op vraag/informatie berichten. Deze
 * class biedt helper methodes om testdata aan te maken welke veelal gebruikt kan worden in de tests en helper methodes
 * voor het opbouwen van het verwachte antwoord.
 */
public abstract class AbstractVraagBerichtBindingUitTest<T> extends AbstractBerichtBindingUitTest<T> {

    /**
     * Retourneert de naam van het bericht element van het bericht dat in de test wordt getest.
     *
     * @return de naam van het bericht element van het bericht dat in de test wordt getest.
     */
    public abstract String getBerichtElementNaam();

    /**
     * Instantieert een {@link Persoon} en vult deze geheel met test data, welke deels kan worden meegegeven, maar
     * grotendeels in deze methode wordt bepaald.
     *
     * @param id de id van de persoon.
     * @param gemeente de gemeente waartoe de persoon behoord
     * @param plaats de plaats waarin deze persoon woont
     * @param land het land waarin deze persoon woont
     * @param bsn het bsn van de persoon
     * @return de aangemaakte persoon
     */
    protected Persoon maakPersoon(final Long id, final Partij gemeente, final Plaats plaats, final Land land,
        final String bsn)
    {
        Persoon pers = new Persoon();
        //Identiteit
        pers.setIdentiteit(new PersoonIdentiteit());
        pers.getIdentiteit().setId(id);
        // Identificatienummers
        pers.setIdentificatienummers(new PersoonIdentificatienummers());
        pers.getIdentificatienummers().setBurgerservicenummer(bsn);
        pers.getIdentificatienummers().setAdministratienummer("987654321");
        // Geslachtsaanduiding
        pers.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        pers.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.MAN);
        // Samengesteldnaam
        pers.setSamengesteldenaam(new PersoonSamengesteldeNaam());
        pers.getSamengesteldenaam().setAdellijkeTitel(AdellijkeTitel.BARON);
        pers.getSamengesteldenaam().setGeslachtsnaam("geslachtsnaam");
        pers.getSamengesteldenaam().setIndAlgoritmischAfgeleid(false);
        pers.getSamengesteldenaam().setIndNamenreeksAlsGeslachtsnaam(false);
        pers.getSamengesteldenaam().setPredikaat(Predikaat.HOOGHEID);
        pers.getSamengesteldenaam().setScheidingsTeken(",");
        pers.getSamengesteldenaam().setVoornamen("Voornaam");
        pers.getSamengesteldenaam().setVoorvoegsel("voor");
        // Aanschrijving
        pers.setSamengesteldeAanschrijving(new PersoonSamengesteldeAanschrijving());
        pers.getSamengesteldeAanschrijving().setGeslachtsnaam("geslachtsnaam");
        pers.getSamengesteldeAanschrijving().setIndAanschrijvingMetAdellijkeTitels(false);
        pers.getSamengesteldeAanschrijving().setIndAlgoritmischAfgeleid(false);
        pers.getSamengesteldeAanschrijving().setPredikaat(Predikaat.HOOGHEID);
        pers.getSamengesteldeAanschrijving().setScheidingsTeken(",");
        pers.getSamengesteldeAanschrijving().setVoornamen("voornaam");
        pers.getSamengesteldeAanschrijving().setVoorvoegsel("voor");
        pers.getSamengesteldeAanschrijving().setWijzeGebruikGeslachtsnaam(WijzeGebruikGeslachtsnaam.EIGEN);
        // Geboorte
        pers.setGeboorte(new PersoonGeboorte());
        pers.getGeboorte().setBuitenlandsePlaats("buitenland");
        pers.getGeboorte().setBuitenlandseRegio("bregio");
        pers.getGeboorte().setDatumGeboorte(20120101);
        pers.getGeboorte().setGemeenteGeboorte(gemeente);
        pers.getGeboorte().setLandGeboorte(land);
        pers.getGeboorte().setOmschrijvingLocatie("omschrijving");
        pers.getGeboorte().setWoonplaatsGeboorte(plaats);
        // Overlijden
        pers.setOverlijden(new PersoonOverlijden());
        pers.getOverlijden().setBuitenlandsePlaats("buitenland");
        pers.getOverlijden().setBuitenlandseRegio("bregio");
        pers.getOverlijden().setDatumOverlijden(21000101);
        pers.getOverlijden().setGemeenteOverlijden(gemeente);
        pers.getOverlijden().setLandOverlijden(land);
        pers.getOverlijden().setOmschrijvingLocatie("omschrijving");
        pers.getOverlijden().setWoonplaatsOverlijden(plaats);
        // Bijhoudingsverwantwoordelijke
        pers.setBijhoudingVerantwoordelijke(new PersoonBijhoudingsVerantwoordelijke());
        pers.getBijhoudingVerantwoordelijke().setVerantwoordelijke(Verantwoordelijke.MINISTER);
        // Opschorting
        pers.setRedenOpschorting(new PersoonRedenOpschorting());
        pers.getRedenOpschorting().setRedenOpschortingBijhouding(RedenOpschorting.MINISTER);
        // Bijhoudingsgemeente
        pers.setBijhoudingGemeente(new PersoonBijhoudingsGemeente());
        pers.getBijhoudingGemeente().setDatumInschrijving(20120101);
        pers.getBijhoudingGemeente().setGemeente(gemeente);
        pers.getBijhoudingGemeente().setIndOnverwerktDocumentAanwezig(false);
        // Inschrijving
        pers.setInschrijving(new PersoonInschrijving());
        pers.getInschrijving().setDatumInschrijving(20101122);
        pers.getInschrijving().setVersienummer(1L);
        // Afgeleid amdministratief
        pers.setAfgeleidAdministratief(new PersoonAfgeleidAdministratief());
        pers.getAfgeleidAdministratief().setIndGegevensInOnderzoek(false);
        // TODO controlleer xsd validatie, deze klopt nog niet, xsd zegt dat het van de type positive integer moet zijn
        // Calendar datum = Calendar.getInstance();
        // datum.setTimeInMillis(1000);
        // pers.getAfgeleidAdministratief().setLaatstGewijzigd(datum.getTime());
        // Voornamen
        PersoonVoornaam persoonVoornaam1 = new PersoonVoornaam();
        persoonVoornaam1.setNaam("voornaam1");
        persoonVoornaam1.setVolgnummer(1);
        PersoonVoornaam persoonVoornaam2 = new PersoonVoornaam();
        persoonVoornaam2.setNaam("voornaam2");
        persoonVoornaam2.setVolgnummer(2);
        pers.voegPersoonVoornaamToe(persoonVoornaam1);
        pers.voegPersoonVoornaamToe(persoonVoornaam2);
        // Geslachtsnaamcomponenten
        PersoonGeslachtsnaamcomponent component1 = new PersoonGeslachtsnaamcomponent();
        component1.setAdellijkeTitel(AdellijkeTitel.HERTOG);
        component1.setNaam("comp");
        component1.setPredikaat(Predikaat.HOOGHEID);
        component1.setScheidingsTeken(";");
        component1.setVolgnummer(1);
        component1.setVoorvoegsel("voorv");
        PersoonGeslachtsnaamcomponent component2 = new PersoonGeslachtsnaamcomponent();
        component2.setAdellijkeTitel(AdellijkeTitel.HERTOG);
        component2.setNaam("comp");
        component2.setPredikaat(Predikaat.HOOGHEID);
        component2.setScheidingsTeken(";");
        component2.setVolgnummer(1);
        component2.setVoorvoegsel("voorv");
        pers.voegGeslachtsnaamcomponentToe(component2);
        // Adressen
        pers.setAdressen(new HashSet<PersoonAdres>());
        PersoonAdres adr = new PersoonAdres();
        adr.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
        adr.setAdresseerbaarObject("acd");
        adr.setAfgekorteNaamOpenbareRuimte("afg");
        adr.setBuitenlandsAdresRegel1("buitregel1");
        adr.setBuitenlandsAdresRegel2("buitregel2");
        adr.setBuitenlandsAdresRegel3("buitregel3");
        adr.setBuitenlandsAdresRegel4("buitregel4");
        adr.setBuitenlandsAdresRegel5("buitregel5");
        adr.setBuitenlandsAdresRegel6("buitregel6");
        adr.setDatumAanvangAdreshouding(20120101);
        adr.setDatumVertrekUitNederland(20500101);
        adr.setGemeente(gemeente);
        adr.setGemeentedeel("gemdeel");
        adr.setHuisletter("A");
        adr.setHuisnummer("12");
        adr.setHuisnummertoevoeging("b");
        adr.setIdentificatiecodeNummeraanduiding("idcode");
        adr.setLand(land);
        adr.setLocatieOmschrijving("omschr");
        adr.setLocatieTovAdres("TO");
        adr.setNaamOpenbareRuimte("opNaam");
        adr.setPersoonAdresStatusHis("A");
        adr.setPostcode("1234AB");
        adr.setRedenWijziging(RedenWijzigingAdres.AMBTSHALVE);
        adr.setSoort(FunctieAdres.BRIEFADRES);
        adr.setWoonplaats(plaats);
        pers.getAdressen().add(adr);
        // Nationaliteiten
        PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit();
        persoonNationaliteit.setNationaliteit(new Nationaliteit());
        persoonNationaliteit.getNationaliteit().setCode("0031");
        persoonNationaliteit.getNationaliteit().setNaam("abcd");
        pers.getNationaliteiten().add(persoonNationaliteit);
        return pers;
    }

    /**
     * Instantieert een {@link Betrokkenheid} en voegt deze toe aan de opgegeven relatie. De velden van de
     * betrokkenheid
     * worden gezet op basis van de opgegeven waardes. De geinstantieerde betrokkenheid wordt geretourneerd.
     *
     * @param indOuderHeeftGezag boolean die aangeeft of de opgegeven persoon als ouder gezag heeft.
     * @param betrokkene de persoon die als betrokkene fungeert binnen de aan te maken betrokkenheid.
     * @param soort de soort van de betrokkenheid.
     * @param relatie de relatie waar aan de betrokkenheid moet worden toegevoegd.
     * @param datumAanvangOuderschap zet een eventuele datumAanvangOuderschap op de betrokkenheid.
     * @return de aangemaakte betrokkenheid.
     */
    protected Betrokkenheid maakBetrokkenheidEnVoegtoeAanRelatie(final boolean indOuderHeeftGezag,
        final Persoon betrokkene, final SoortBetrokkenheid soort, final Relatie relatie,
        final Integer datumAanvangOuderschap)
    {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setIndOuderHeeftGezag(indOuderHeeftGezag);
        betrokkenheid.setBetrokkene(betrokkene);
        betrokkenheid.setSoortBetrokkenheid(soort);
        betrokkenheid.setRelatie(relatie);
        betrokkenheid.setDatumAanvangOuderschap(datumAanvangOuderschap);
        relatie.getBetrokkenheden().add(betrokkenheid);
        return betrokkenheid;
    }

    /**
     * Bouwt een {@link OpvragenPersoonResultaat} instantie met daarin een compleet persoon, inclusief relaties naar
     * een kind, ouders en een partner. De opgegeven gemeente, land en plaats worden gebruikt voor alle personen, dus
     * zowel voor de op te vragen persoon in het resultaat als voor de betrokkene in zijn/haar relaties.
     *
     * @param gemeente de gemeente die in het antwoordbericht wordt gebruikt voor de personen
     * @param land het land die in het antwoordbericht wordt gebruikt voor de personen
     * @param plaats de woonplaats die in het antwoordbericht wordt gebruikt voor de personen
     * @return het verwachte bericht resultaat
     */
    protected OpvragenPersoonResultaat bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(final Partij gemeente,
        final Land land, final Plaats plaats, final SoortRelatie relatieSoort)
    {
        Set<Persoon> gevondenPersonen = new HashSet<Persoon>();
        Persoon opTeVragenPersoon = maakPersoon(1L, gemeente, plaats, land, "persoon");
        Persoon kindVanOpTeVragenPersoon = maakPersoon(2L, gemeente, plaats, land, "kind");
        Persoon vader = maakPersoon(3L, gemeente, plaats, land, "vader");
        Persoon moeder = maakPersoon(4L, gemeente, plaats, land, "moeder");
        Persoon partnerVanOpTeVragenPersoon = maakPersoon(5L, gemeente, plaats, land, "partner");

        // Betrokkenheid familie rechtelijk opTeVragenPersoon van vader en moeder
        Relatie relatie = new Relatie();
        relatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
//        relatie.setDatumAanvang(Integer.valueOf(20110305));
        relatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        maakBetrokkenheidEnVoegtoeAanRelatie(false, vader, SoortBetrokkenheid.OUDER, relatie, 20110305);
        maakBetrokkenheidEnVoegtoeAanRelatie(false, moeder, SoortBetrokkenheid.OUDER, relatie, 20110305);
        Betrokkenheid kindBetr = maakBetrokkenheidEnVoegtoeAanRelatie(false, opTeVragenPersoon, SoortBetrokkenheid.KIND,
            relatie, null);
        opTeVragenPersoon.getBetrokkenheden().add(kindBetr);

        // Huwelijk
        Relatie huwelijk = new Relatie();
        huwelijk.setBetrokkenheden(new HashSet<Betrokkenheid>());
        huwelijk.setSoortRelatie(relatieSoort);
        huwelijk.setOmschrijvingLocatieAanvang("Timboektoe");
        huwelijk.setOmschrijvingLocatieEinde("blakalafm");
        huwelijk.setDatumAanvang(19830404);
        huwelijk.setDatumEinde(19830404);
        huwelijk.setLandAanvang(land);
        huwelijk.setLandEinde(land);
        huwelijk.setBuitenlandsePlaatsAanvang("Verweggiestan");
        huwelijk.setBuitenlandsePlaatsEinde("Tajikistan");
        huwelijk.setBuitenlandseRegioAanvang("In het bos");
        huwelijk.setBuitenlandseRegioEinde("In de woestijn");
        huwelijk.setGemeenteAanvang(gemeente);
        huwelijk.setGemeenteEinde(gemeente);
        huwelijk.setPlaatsAanvang(plaats);
        huwelijk.setPlaatsEinde(plaats);

        Betrokkenheid partnerVanOpTeVragenPersoonBetr = maakBetrokkenheidEnVoegtoeAanRelatie(false,
            partnerVanOpTeVragenPersoon, SoortBetrokkenheid.PARTNER, huwelijk, null);
        Betrokkenheid partnerBetr = maakBetrokkenheidEnVoegtoeAanRelatie(false, opTeVragenPersoon,
            SoortBetrokkenheid.PARTNER, huwelijk, null);
        opTeVragenPersoon.getBetrokkenheden().add(partnerBetr);

        // Betrokkenheid familie rechtelijk kind van Op te vragen persoon en partner
        Relatie kindVanOpTeVragenPersRelatie = new Relatie();
//        kindVanOpTeVragenPersRelatie.setDatumAanvang(Integer.valueOf(20110305));
        kindVanOpTeVragenPersRelatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
        kindVanOpTeVragenPersRelatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        maakBetrokkenheidEnVoegtoeAanRelatie(false, kindVanOpTeVragenPersoon, SoortBetrokkenheid.KIND,
            kindVanOpTeVragenPersRelatie, null);
        maakBetrokkenheidEnVoegtoeAanRelatie(false, partnerVanOpTeVragenPersoon, SoortBetrokkenheid.OUDER,
            kindVanOpTeVragenPersRelatie, 20110305);
        Betrokkenheid optevragenPersoonVaderBetr = maakBetrokkenheidEnVoegtoeAanRelatie(false, opTeVragenPersoon,
            SoortBetrokkenheid.OUDER, kindVanOpTeVragenPersRelatie, 20110305);
        opTeVragenPersoon.getBetrokkenheden().add(optevragenPersoonVaderBetr);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);
        return resultaat;
    }

    /**
     * Bouwt en retourneert een verwacht antwoord bericht met opgeven tijdstip van registratie, maar zonder meldingen
     * en zonder werkelijke data in het antwoordbericht.
     *
     * @param tijdstipRegistratie het tijdstip van registratie dat gebruikt dient te worden in het antwoordbericht.
     * @return het verwachte antwoordbericht.
     */
    protected String bouwVerwachteAntwoordBericht(final String tijdstipRegistratie) {
        return bouwVerwachteAntwoordBericht(tijdstipRegistratie, null, null, null, null);
    }

    /**
     * Bouwt en retourneert een verwacht antwoord bericht met opgeven tijdstip van registratie en een melding, waarbij
     * de melding de opgegeven melding code, regelcode en melding omschrijving heeft. Het antwoordbericht bevat verder
     * de content zoals opgenomen in het bestand zoals opgegeven door de 'verwachtAntwoordBerichtBestandsNaam'.
     *
     * @param tijdstipRegistratie het tijdstip van registratie dat gebruikt dient te worden in het antwoordbericht.
     * @param meldingCode de code van de melding soort.
     * @param regelCode de regel code van de melding.
     * @param verwachtAntwoordBerichtBestandsNaam de bestandsnaam van het bestand met daarin de verwachte content van
     * het antwoord.
     * @return het verwachte antwoordbericht.
     */
    protected String bouwVerwachteAntwoordBericht(final String tijdstipRegistratie, final String meldingCode,
        final String regelCode, final String meldingOmschrijving, final String verwachtAntwoordBerichtBestandsNaam)
    {
        StringBuilder verwachteWaarde = new StringBuilder()
            .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
            .append(String.format(
                "<%s xmlns=\"http://www.bprbzk.nl/BRP/0001\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">",
                getBerichtElementNaam()));

        bouwStuurgegevensElement(verwachteWaarde);
        bouwResultaatElement(verwachteWaarde, tijdstipRegistratie, meldingCode);
        if (meldingCode != null && regelCode != null) {
            bouwMeldingenElement(verwachteWaarde, meldingCode, regelCode, meldingOmschrijving);
        }
        if (verwachtAntwoordBerichtBestandsNaam != null) {
            bouwAntwoordElement(verwachteWaarde, verwachtAntwoordBerichtBestandsNaam);
        }
        verwachteWaarde.append(String.format("</%s>", getBerichtElementNaam()));
        return verwachteWaarde.toString();
    }

    /**
     * Voegt een standaard stuurgegevens element toe aan de opgegeven {@link StringBuilder} instantie.
     *
     * @param stringBuilder de stringbuilder waar het element aan toegevoegd dient te worden.
     */
    private void bouwStuurgegevensElement(final StringBuilder stringBuilder) {
        stringBuilder.append("<stuurgegevens>").append(bouwElement("organisatie", "Ministerie BZK"))
                     .append(bouwElement("applicatie", "BRP")).append(bouwElement("referentienummer", "OnbekendeID"))
                     .append(bouwElement("crossReferentienummer", "nog niet geimplementeerd"))
                     .append("</stuurgegevens>");
    }

    /**
     * Voegt een standaard resultaat element toe aan de opgegeven {@link StringBuilder} instantie, waarbij de opgegeven
     * waardes voor tijdstip registratie en hoogste melding code in het resultaat worden verwerkt.
     *
     * @param stringBuilder de stringbuilder waar het element aan toegevoegd dient te worden.
     * @param tijdstipRegistratie het tijdstip van registratie wat in het resultaat opgenomen dient te worden.
     * @param hoogsteMeldingCode de hoogste melding code die in het resultaat opgenomen dient te worden.
     */
    private void bouwResultaatElement(final StringBuilder stringBuilder, final String tijdstipRegistratie,
        final String hoogsteMeldingCode)
    {
        stringBuilder.append("<resultaat>").append(bouwElement("verwerkingCode", "G"));
        if (hoogsteMeldingCode == null) {
            stringBuilder.append("<hoogsteMeldingsniveauCode xsi:nil=\"true\"/>");
        } else {
            stringBuilder.append(bouwElement("hoogsteMeldingsniveauCode", hoogsteMeldingCode));
        }
//        stringBuilder.append(bouwElement("peilmomentMaterieel", tijdstipRegistratie));
        stringBuilder.append("</resultaat>");
    }

    /**
     * Voegt een standaard meldingen element toe aan de opgegeven {@link StringBuilder} instantie, met daarin een
     * enkele melding met de opgegeven regelcode en melding.
     *
     * @param stringBuilder de stringbuilder waar het element aan toegevoegd dient te worden.
     * @param regelCode de regelcode van de melding die in het meldingen element dient te zitten.
     * @param melding de melding omschrijving van de melding die in het meldingen elment dient te zitten.
     */
    private void bouwMeldingenElement(final StringBuilder stringBuilder, final String meldingCode,
        final String regelCode, final String melding)
    {
        stringBuilder.append("<meldingen><melding>").append(bouwElement("regelCode", regelCode))
            .append(bouwElement("soortCode", meldingCode)).append(bouwElement("melding", melding))
            .append("</melding></meldingen>");
    }

    private String bouwElement(final String naam, final String waarde) {
        return String.format("<%1$s>%2$s</%1$s>", naam, waarde);
    }
}
