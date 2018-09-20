/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AangeverAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVervallenReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenWijzigingAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortDocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortVerificatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verblijfsrecht;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verdrag;

/**
 * Levert alle methoden op gegevens uit dynamische stamtabellen op te zoeken a.d.h.v. hun unieke code.
 * 
 */
public interface DynamischeStamtabelRepository {

    /**
     * @param code
     *            de unieke code
     * @return de hoedanigheid waarmee een persoon die aangifte doet van verblijf en adres of van adreswijziging kan
     *         staan ten opzichte van de Persoon wiens adres is aangegeven.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    AangeverAdreshouding findAangeverAdreshoudingByCode(final String code);

    /**
     * @param code
     *            de unieke code
     * @return de autoriteit van afgifte van een reisdocument.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    AutoriteitVanAfgifteReisdocument findAutoriteitVanAfgifteReisdocumentByCode(final String code);

    /**
     * @param landcode
     *            de unieke code
     * @return de onafhankelijke staat, zoals door Nederland erkend
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Land findLandByLandcode(final BigDecimal landcode);

    /**
     * @param nationaliteitcode
     *            de unieke code
     * @return de aanduiding van de staat waarmee een persoon een juridische band kan hebben zoals bedoeld in het
     *         Europees verdrag inzake nationaliteit, Straatsburg 06-11-1997.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Nationaliteit findNationaliteitByNationaliteitcode(final BigDecimal nationaliteitcode);

    /**
     * @param gemeentecode
     *            de unieke code
     * @return een Nederlandse gemeente.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Partij findPartijByGemeentecode(final BigDecimal gemeentecode);

    /**
     * @param naam
     *            de partij naam
     * @return de partij horende bij de gegeven naam.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Partij findPartijByNaam(final String naam);

    /**
     * @param woonplaatscode
     *            de unieke code
     * @return een woonplaats, zoals onderhouden vanuit de BAG.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Plaats findPlaatsByWoonplaatscode(final String woonplaatscode);

    /**
     * @param naam
     *            de unieke plaatsnaam
     * @return een woonplaats, zoals onderhouden vanuit de BAG.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Plaats findPlaatsByNaam(final String naam);

    /**
     * @param code
     *            de unieke code
     * @return de reden om een Relatie te beëindigen.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(final String code);

    /**
     * @param naam
     *            de unieke naam
     * @return de reden voor het verkrijgen van de Nederlandse nationaliteit.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByNaam(final BigDecimal naam);

    /**
     * @param naam
     *            de unieke naam
     * @return de reden voor het verliezen van de Nederlandse nationaliteit.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByNaam(final BigDecimal naam);

    /**
     * @param code
     *            de unieke code
     * @return de reden voor het vervallen van een reisdocument.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenVervallenReisdocument findRedenVervallenReisdocumentByCode(final String code);

    /**
     * @param code
     *            de unieke code
     * @return de reden voor het wijzigen van een adres.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenWijzigingAdres findRedenWijzigingAdres(final String code);

    /**
     * @param omschrijving
     *            de unieke omschrijving
     * @return het soort document.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    SoortDocument findSoortDocumentByOmschrijving(final String omschrijving);

    /**
     * @param code
     *            de unieke code
     * @return het soort Nederlandse reisdocument.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    SoortNederlandsReisdocument findSoortNederlandsReisdocumentByCode(final String code);

    /**
     * 
     * @param naam
     *            de unieke naam
     * @return de verificatie van gegevens in de BRP.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    SoortVerificatie findSoortVerificatieByNaam(final String naam);

    /**
     * @param omschrijving
     *            de unieke omschrijving
     * @return de Verblijfsrechtelijke status van Persoon.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Verblijfsrecht findVerblijfsrechtByOmschrijving(final String omschrijving);

    /**
     * @param omschrijving
     *            de unieke omschrijving
     * @return het verdrag.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Verdrag findVerdragByOmschrijving(final String omschrijving);
}
