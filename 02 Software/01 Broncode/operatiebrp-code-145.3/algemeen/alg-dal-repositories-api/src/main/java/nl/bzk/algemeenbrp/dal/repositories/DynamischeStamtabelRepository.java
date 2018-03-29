/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;

/**
 * Levert alle methoden op gegevens uit dynamische stamtabellen op te zoeken a.d.h.v. hun unieke code.
 */
public interface DynamischeStamtabelRepository {

    /**
     * @param code de unieke code
     * @return de hoedanigheid waarmee een persoon die aangifte doet van verblijf en adres of van adreswijziging kan staan ten opzichte van de Persoon wiens
     * adres is aangegeven. Null als er geen resultaat gevonden kan worden voor de code.
     */
    Aangever getAangeverByCode(char code);

    /**
     * @param landcode de unieke code als string
     * @return de onafhankelijke staat, zoals door Nederland erkend. Null als er geen resultaat gevonden kan worden voor de landcode.
     */
    LandOfGebied getLandOfGebiedByCode(String landcode);

    /**
     * @param nationaliteitcode de unieke code
     * @return de aanduiding van de staat waarmee een persoon een juridische band kan hebben zoals bedoeld in het Europees verdrag inzake nationaliteit,
     * Straatsburg 06-11-1997. Null als er geen resultaat gevonden kan worden voor de nationaliteitcode.
     */
    Nationaliteit getNationaliteitByNationaliteitcode(String nationaliteitcode);

    /**
     * @param gemeentecode de unieke code. Het type van de gemeentecode wordt gewijzigd.
     * @return een Nederlandse gemeente. Null als er geen resultaat gevonden kan worden voor de gemeentecode.
     */
    Gemeente getGemeenteByGemeentecode(String gemeentecode);

    /**
     * @param partijcode de unieke code
     * @return een Nederlandse gemeente. Null als er geen resultaat gevonden kan worden voor de partijcode.
     */
    Gemeente getGemeenteByPartijcode(String partijcode);

    /**
     * @param partij de unieke code
     * @return een Nederlandse gemeente.
     * @throws IllegalArgumentException als er niet exact 1 resultaat gevonden kan worden
     */
    Gemeente getGemeenteByPartij(Partij partij);

    /**
     * @param code de partij code
     * @return de partij horende bij de gegeven code. Null als er geen resultaat gevonden kan worden voor de code.
     */
    Partij getPartijByCode(String code);

    /**
     * @param naam de partij naam
     * @return de partij horende bij de gegeven naam of null als de partij niet gevonden kan worden
     */
    Partij findPartijByNaam(String naam);

    /**
     * @param code de unieke code
     * @return de reden om een Relatie te beëindigen. Null als er geen resultaat gevonden kan worden voor de code.
     */
    RedenBeeindigingRelatie getRedenBeeindigingRelatieByCode(char code);

    /**
     * @param code de unieke code
     * @return de reden voor het verkrijgen van de Nederlandse nationaliteit. Null als er geen resultaat gevonden kan worden voor de code.
     */
    RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteitByCode(String code);

    /**
     * @param code de unieke code
     * @return de reden voor het verliezen van de Nederlandse nationaliteit. Null als er geen resultaat gevonden kan worden voor de code.
     */
    RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteitByCode(String code);

    /**
     * @param code de unieke code
     * @return de reden voor het vervallen van een reisdocument. Null als er geen resultaat gevonden kan worden voor de code.
     */
    AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocumentByCode(char code);

    /**
     * @param code de unieke code
     * @return de reden voor het wijzigen van een verblijf. Null als er geen resultaat gevonden kan worden voor de code.
     */

    RedenWijzigingVerblijf getRedenWijzigingVerblijf(char code);

    /**
     * @param naam de unieke naam
     * @return het soort document. Null als er geen resultaat gevonden kan worden voor de naam.
     */
    SoortDocument getSoortDocumentByNaam(String naam);

    /**
     * @param code de unieke code
     * @return het soort Nederlandse reisdocument. Null als er geen resultaat gevonden kan worden voor de code.
     */
    SoortNederlandsReisdocument getSoortNederlandsReisdocumentByCode(String code);

    /**
     * Geeft de {@link Verblijfsrecht} voor de opgegeven code terug. Null als er resultaat gevonden kan worden.
     * @param code de code van het verblijfsrecht
     * @return de {@link Verblijfsrecht} horende bij de code, Null als er geen resultaat gevonden kan worden.
     */
    Verblijfsrecht getVerblijfsrechtByCode(String code);

    /**
     * @param lo3Rubriek lo3 rubriek naam
     * @return de lo3 rubriek
     * @throws IllegalArgumentException als er niet exact 1 resultaat gevonden kan worden
     */
    Lo3Rubriek getLo3RubriekByNaam(String lo3Rubriek);

    /**
     * Geeft de soort partij code terug met de opgegeven naam.
     * @param naam De soortPartij naam.
     * @return De soort partij entiteit. Null als er geen resultaat gevonden kan worden voor de naam.
     */
    SoortPartij getSoortPartijByNaam(String naam);

    /**
     * Geeft de autorisatie van afgifte buitenlands persoonsnummer terug voor opgegeven nationaliteit.
     * @param code de nationaliteit code
     * @return de autorisatie van afgifte
     */
    AutoriteitAfgifteBuitenlandsPersoonsnummer getAutorisatieVanAfgifteBuitenlandsPersoonsnummer(String code);

    /**
     * @param naam waarop gefilterd word.
     * @return Plaats, null als geen gevonden wordt
     */
    Plaats getPlaatsByPlaatsNaam(String naam);

    /**
     * Geeft het {@link Voorvoegsel} terug obv de opgegeven sleutel.
     * @param voorvoegselSleutel een voorvoegsel sleutel
     * @return een {@link Voorvoegsel}. Null als er geen resultaat gevonden wordt.
     */
    Voorvoegsel getVoorvoegselByVoorvoegselSleutel(VoorvoegselSleutel voorvoegselSleutel);

    /**
     * Geeft het {@link SoortActieBrongebruik} terug obv de opgegeven sleutel.
     * @param soortActieBrongebruikSleutel een soortActieBrongebruik sleutel
     * @return een {@link SoortActieBrongebruik}. Null als er geen resultaat gevonden wordt.
     */
    SoortActieBrongebruik getSoortActieBrongebruikBySoortActieBrongebruikSleutel(SoortActieBrongebruikSleutel soortActieBrongebruikSleutel);

    /**
     * Geef de {@link Rechtsgrond} terug voor de gegeven code of null als deze niet bestaat.
     * @param code de code die de rechtsgrond identificeert
     * @return de rechtsgrond horende bij de gegeven code of null als deze niet bestaat
     */
    Rechtsgrond getRechtsgrondByCode(String code);

    /**
     * @param partij De partij die opgeslagen moet worden.
     * @return De opgeslagen partij.
     */
    Partij savePartij(Partij partij);
}
