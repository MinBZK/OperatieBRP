/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.Collection;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.Lo3Rubriek;

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
    Aangever getAangeverByCode(final char code);

    /**
     * @param landcode
     *            de unieke code
     * @return de onafhankelijke staat, zoals door Nederland erkend
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    LandOfGebied getLandOfGebiedByCode(final Short landcode);

    /**
     * @param nationaliteitcode
     *            de unieke code
     * @return de aanduiding van de staat waarmee een persoon een juridische band kan hebben zoals bedoeld in het
     *         Europees verdrag inzake nationaliteit, Straatsburg 06-11-1997.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Nationaliteit getNationaliteitByNationaliteitcode(final Short nationaliteitcode);

    /**
     * @param gemeentecode
     *            de unieke code
     * @return een Nederlandse gemeente.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Gemeente getGemeenteByGemeentecode(final short gemeentecode);

    /**
     * @param partij
     *            de unieke code
     * @return een Nederlandse gemeente.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Gemeente getGemeenteByPartij(final Partij partij);

    /**
     * @param code
     *            de partij code
     * @return de partij horende bij de gegeven code.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Partij getPartijByCode(final int code);

    /**
     * @param code
     *            de partij code
     * @return de partij horende bij de gegeven code of null als de partij niet gevonden kan worden
     */
    Partij findPartijByCode(final int code);

    /**
     * @param naam
     *            de partij naam
     * @return de partij horende bij de gegeven naam of null als de partij niet gevonden kan worden
     */
    Partij findPartijByNaam(final String naam);

    /**
     * @param code
     *            de unieke code
     * @return de reden om een Relatie te beëindigen.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenBeeindigingRelatie getRedenBeeindigingRelatieByCode(final char code);

    /**
     * @param code
     *            de unieke code
     * @return de reden voor het verkrijgen van de Nederlandse nationaliteit.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteitByCode(final Short code);

    /**
     * @param code
     *            de unieke code
     * @return de reden voor het verliezen van de Nederlandse nationaliteit.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteitByCode(final Short code);

    /**
     * @param code
     *            de unieke code
     * @return de reden voor het vervallen van een reisdocument.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocumentByCode(final char code);

    /**
     * @param code
     *            de unieke code
     * @return de reden voor het wijzigen van een verblijf.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    RedenWijzigingVerblijf getRedenWijzigingVerblijf(final char code);

    /**
     * @param naam
     *            de unieke naam
     * @return het soort document.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    SoortDocument getSoortDocumentByNaam(final String naam);

    /**
     * @param code
     *            de unieke code
     * @return het soort Nederlandse reisdocument.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    SoortNederlandsReisdocument getSoortNederlandsReisdocumentByCode(final String code);

    /**
     * @param waarde
     *            de unieke code
     * @return de Verblijfsrechtelijke status van Persoon.
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Verblijfsrecht getVerblijfsrechtByCode(Short waarde);

    /**
     * @param partij
     *            De partij die opgeslagen moet worden.
     * @return De opgeslagen partij.
     */
    Partij savePartij(final Partij partij);

    /**
     * Haalt de partij rol van een partij op.
     *
     * @param partij
     *            De partij waarvoor de partijrol wordt opgehaald.
     * @param rol
     *            de rol
     * @return De gevonden partijrol indien gevonden, anders null.
     */
    PartijRol getPartijRolByPartij(Partij partij, Rol rol);

    /**
     * @param lo3Rubriek
     *            lo3 rubriek naam
     * @return de lo3 rubriek
     * @throws IllegalArgumentException
     *             als er niet exact 1 resultaat gevonden kan worden
     */
    Lo3Rubriek getLo3RubriekByNaam(final String lo3Rubriek);

    /**
     * @param partijRol
     *            De partijRol die opgeslagen moet worden.
     * @return De opgeslagen partijRol.
     */
    PartijRol savePartijRol(PartijRol partijRol);

    /**
     * @param toegangLeveringsAutorisatie
     *            De toegangLeveringsAutorisatie die opgeslagen moet worden.
     * @return De opgeslagen toegangLeveringsAutorisatie.
     */
    ToegangLeveringsAutorisatie saveToegangLeveringsAutorisatie(ToegangLeveringsAutorisatie toegangLeveringsAutorisatie);

    /**
     * Geeft alle gba toegang leveringsautorisaties.
     *
     * @return Alle gba toegang leveringsautorisaties
     */
    Collection<Leveringsautorisatie> geefAlleGbaLeveringsautorisaties();

    /**
     * Geeft de lijst van toegang leveringsautorisaties voor de betreffende partij op de gegeven ingangsdatum.
     *
     * @param partijRol
     *            De partijRol waarvoor de toegang leveringsautorisatie wordt opgehaald.
     * @param datumIngang
     *            De ingangsdatum van de toegang leveringsautorisatie
     * @return De opgehaalde lijst van toegang leveringsautorisaties
     */
    List<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieByPartijEnDatumIngang(PartijRol partijRol, Integer datumIngang);

    /**
     * Geeft de soort partij code terug met de opgegeven naam.
     *
     * @param soortPartij
     *            De soortPartij.
     * @return De soort partij entiteit.
     */
    SoortPartij getSoortPartijByNaam(String soortPartij);

}
