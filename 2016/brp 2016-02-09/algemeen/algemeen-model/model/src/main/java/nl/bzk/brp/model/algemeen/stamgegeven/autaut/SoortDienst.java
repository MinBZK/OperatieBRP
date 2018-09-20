/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * De door BRP aangeboden soorten van diensten.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortDienst implements SynchroniseerbaarStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Mutatielevering op basis van doelbinding.
     */
    MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING("Mutatielevering op basis van doelbinding"),
    /**
     * Mutatielevering op basis van afnemerindicatie.
     */
    MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE("Mutatielevering op basis van afnemerindicatie"),
    /**
     * Plaatsen afnemerindicatie.
     */
    PLAATSEN_AFNEMERINDICATIE("Plaatsen afnemerindicatie"),
    /**
     * Attendering.
     */
    ATTENDERING("Attendering"),
    /**
     * Zoek persoon.
     */
    ZOEK_PERSOON("Zoek persoon"),
    /**
     * Zoek persoon op adresgegevens.
     */
    ZOEK_PERSOON_OP_ADRESGEGEVENS("Zoek persoon op adresgegevens"),
    /**
     * Geef medebewoners van persoon.
     */
    GEEF_MEDEBEWONERS_VAN_PERSOON("Geef medebewoners van persoon"),
    /**
     * Geef details persoon.
     */
    GEEF_DETAILS_PERSOON("Geef details persoon"),
    /**
     * Synchronisatie persoon.
     */
    SYNCHRONISATIE_PERSOON("Synchronisatie persoon"),
    /**
     * Synchronisatie stamgegeven.
     */
    SYNCHRONISATIE_STAMGEGEVEN("Synchronisatie stamgegeven"),
    /**
     * Mutatielevering stamgegeven.
     */
    MUTATIELEVERING_STAMGEGEVEN("Mutatielevering stamgegeven"),
    /**
     * Selectie.
     */
    SELECTIE("Selectie"),
    /**
     * Geef details persoon bulk.
     */
    GEEF_DETAILS_PERSOON_BULK("Geef details persoon bulk"),
    /**
     * Geef synchroniciteitsgegevens persoon.
     */
    GEEF_SYNCHRONICITEITSGEGEVENS_PERSOON("Geef synchroniciteitsgegevens persoon"),
    /**
     * Geef identificerende gegevens persoon bulk.
     */
    GEEF_IDENTIFICERENDE_GEGEVENS_PERSOON_BULK("Geef identificerende gegevens persoon bulk"),
    /**
     * Geef details terugmelding.
     */
    GEEF_DETAILS_TERUGMELDING("Geef details terugmelding"),
    /**
     * Opvragen aantal personen op adres.
     */
    OPVRAGEN_AANTAL_PERSONEN_OP_ADRES("Opvragen aantal personen op adres"),
    /**
     * Aanmelding gerede twijfel.
     */
    AANMELDING_GEREDE_TWIJFEL("Aanmelding gerede twijfel"),
    /**
     * Intrekking terugmelding.
     */
    INTREKKING_TERUGMELDING("Intrekking terugmelding"),
    /**
     * Verwijderen afnemerindicatie.
     */
    VERWIJDEREN_AFNEMERINDICATIE("Verwijderen afnemerindicatie");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortDienst
     */
    private SoortDienst(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort dienst.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
