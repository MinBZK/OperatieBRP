/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

/**
 * Categorisatie van Indicaties.
 *
 * <p>
 * Toelichting: Voor een Persoon kunnen één of meer Indicaties van toepassing zijn, bijvoorbeeld de indicatie
 * 'Statenloos', of 'Behandeld als Nederlander'.
 * </p>
 * <p>
 * De Soort indicatie is de typering van deze Indicaties voor een Persoon.
 * </p>
 */
public enum SoortIndicatie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null),
    /**
     * Derde heeft gezag?.
     */
    DERDE_HEEFT_GEZAG("Derde heeft gezag?"),
    /**
     * Onder curatele?.
     */
    ONDER_CURATELE("Onder curatele?"),
    /**
     * Verstrekkingsbeperking?.
     */
    VERSTREKKINGSBEPERKING("Verstrekkingsbeperking?"),
    /**
     * Geprivilegieerde?.
     */
    GEPRIVILEGIEERDE("Geprivilegieerde?"),
    /**
     * Vastgesteld niet Nederlander?.
     */
    VASTGESTELD_NIET_NEDERLANDER("Vastgesteld niet Nederlander?"),
    /**
     * Behandeld als Nederlander?.
     */
    BEHANDELD_ALS_NEDERLANDER("Behandeld als Nederlander?"),
    /**
     * Belemmering verstrekking reisdocument?.
     */
    BELEMMERING_VERSTREKKING_REISDOCUMENT("Belemmering verstrekking reisdocument?"),
    /**
     * Bezit buitenlands reisdocument?.
     */
    BEZIT_BUITENLANDS_REISDOCUMENT("Bezit buitenlands reisdocument?"),
    /**
     * Statenloos?.
     */
    STATENLOOS("Statenloos?");

    private String naam;

    /**
     * Private constructor voor eenmalige instantiatie in deze file.
     *
     * @param naam De naam van de waarde.
     */
    private SoortIndicatie(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return this.naam;
    }

}
