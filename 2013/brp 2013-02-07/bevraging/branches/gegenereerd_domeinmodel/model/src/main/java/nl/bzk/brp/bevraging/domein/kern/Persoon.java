/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.kern;

import java.util.Set;

import nl.bzk.brp.bevraging.domein.GeslachtsAanduiding;
import nl.bzk.brp.bevraging.domein.Land;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Plaats;
import nl.bzk.brp.bevraging.domein.RedenOpschorting;
import nl.bzk.brp.bevraging.domein.SoortPersoon;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * De populatie personen bestaat uit ingeschrevenen. Het betreft zowel ingezetenen
 * als niet-ingezetenen. Zie ook groep "Bijhoudingsverantwoordelijkheid" en objecttype
 * "Betrokkenheid".
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor
 * dit objecttype de naam "Natuurlijk persoon" te gebruiken. Binnen de context van
 * BRP hebben we het bij het hanteren van de term Persoon echter nooit over
 * niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon
 * is verder dermate gebruikelijk binnen de context van BRP, dat ervoor gekozen is
 * deze naam te blijven hanteren. We spreken dus over Persoon en niet over "Natuurlijk persoon".
 *
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken
 * we in het logisch & operationeel model (maar dus NIET in de gegevensset) het
 * construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die wellicht
 * wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 */
public interface Persoon {

    /**
     * Retourneert de id van de persoon.
     * @return de id van de persoon.
     */
    Long getId();

    /**
     * @return soort persoon
     */
    SoortPersoon getSoort();

    /**
     * @return Het burgerservicenummer, bedoeld in artikel 1.1 van de Wet algemene bepalingen burgerservicenummer.
     */
    String getBurgerservicenummer();

    /**
     * @return Het administratienummer, bedoeld in artikel 50 van de Wet GBA.
     */
    String getAdministratienummer();

    /**
     * @return De stam van de volledige geslachtsnaam.
     */
    String getGeslachtsnaam();

    /**
     * @return De samenvoeging van alle exemplaren van Voornaam van een Persoon.
     */
    String getVoornamen();

    /**
     * @return Voorvoegsel behorende bij het naamdeel van de Geslachtsnaam.
     */
    String getVoorvoegsel();

    /**
     * @return Scheidingsteken dat een eventueel voorvoegsel scheidt van het naamdeel van de Geslachtsnaam.
     */
    String getScheidingsTeken();

    /**
     * @return Aanduiding van het geslacht van een Persoon.
     */
    GeslachtsAanduiding getGeslachtsAanduiding();

    /**
     * @return Datum waarop de Persoon geboren is.
     */
    Integer getDatumGeboorte();

    /**
     * @return Gemeente die verantwoordelijk is voor de gegevens.
     */
    Partij getBijhoudingsGemeente();

    /**
     * @return Buitenlandse plaats waar de Geboorte heeft plaatsgevonden.
     */
    String getBuitenlandseGeboorteplaats();

    /**
     * @return Buitenlandse plaats waar het overlijden heeft plaatsgevonden.
     */
    String getBuitenlandsePlaatsOverlijden();

    /**
     * @return Buitenlandse regio waar de Geboorte heeft plaatsgevonden.
     */
    String getBuitenlandseRegioGeboorte();

    /**
     * @return Nederlandse gemeente waar de Geboorte heeft plaatsgevonden.
     */
    Partij getGemeenteGeboorte();

    /**
     * @return Land waar de Geboorte heeft plaatsgevonden.
     */
    Land getLandGeboorte();

    /**
     * Geeft de verzameling adressen van deze {@link Persoon} als een unmodifiable {@link Set}.
     *
     * @return de verzameling adressen van deze {@link Persoon} als een unmodifiable {@link Set}.
     */
    Set<? extends PersoonAdres> getAdressen();

    /**
     * @return Omschrijving van de locatie waar de Geboorte heeft plaatsgevonden, niet zijnde een Gemeente, een Woonplaats of een Buitenlandse plaats of regio.
     */
    String getOmschrijvingGeboorteLocatie();

    /**
     * @return De reden voor de eventuele opschorting van de bijhouding van de persoonsgegevens.
     */
    RedenOpschorting getRedenOpschortingBijhouding();

    /**
     * @return Nederlandse woonplaats waar de Geboorte heeft plaatsgevonden.
     */
    Plaats getWoonplaatsGeboorte();

    /**
     * Indicator die aangeeft dat de persoon wordt behandeld als Nederlander.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean behandeldAlsNederlander();

    /**
     * Indicator die aangeeft dat aan de persoon geen reisdocument verstrekt mag worden.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean belemmeringVerstrekkingReisdocument();

    /**
     * Indicator die aangeeft dat de ingeschrevene beschikt over één of meer buitenlandse reisdocumenten of is
     * bijgeschreven in een buitenlands reisdocument.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean bezitBuitenlandsReisdocument();

    /**
     * Indicator die aangeeft of de persoon deel kan nemen aan de verkiezing van de leden van het Europees parlement,
     * zoals beschreven in afdeling V van de Kieswet.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean deelnameEUVerkiezingen();

    /**
     * Indicator die aangeeft dat een derde het gezag over de minderjarige persoon heeft.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean derdeHeeftGezag();

    /**
     * Indicator die aangeeft dat de betrokken persoon een geprivilegieerde status heeft.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean gepriviligeerde();

    /**
     * Indicator die aangeeft dat de persoon onder curatele staat.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean onderCuratele();

    /**
     * Indicatie dat de betrokkene uitgesloten is van het actieve kiesrecht voor de verkiezingen zoals bedoeld in de
     * afdelingen II, III en IV van de Kieswet.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean uitsluitingNLKiesrecht();

    /**
     * Indicator die aangeeft dat vastgesteld is dat de persoon geen Nederlander is.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean vastgesteldNietNederlander();

    /**
     * Indicator die aangeeft dat de persoon heeft gekozen voor beperkte verstrekking van zijn/haar gegevens aan derden.
     *
     * @return {@link Boolean#TRUE} als dit waar is, {@link Boolean#FALSE} als dit niet waar is, en <code>null</code> als
     *         dit niet van toepassing is.
     */
    Boolean verstrekkingsBeperking();

}
