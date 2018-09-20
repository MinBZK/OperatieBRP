/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Categorisatie van bijhoudingsaard.
 *
 * Duiding van de afdeling van de Wet BRP waaronder de bijhouding (normaliter) plaatsvindt.
 *
 * De waarde '?' wordt bij conversie gebruikt. In GBA is er over de adressen historie bekend, maar niet over de
 * bijhoudingsdaard. Om de historie volledig dekkend te maken, wordt er een bijhoudingsaard aangemaakt die geen
 * uitspraak doet over de waarde (die weten we immers niet). Er wordt dus een bijhoudingsaard ? aangemaakt. Deze waarde
 * zal dan ook niet voor komen in actuele gegevens.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Bijhoudingsaard implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", "Dummy"),
    /**
     * De bijhouding van de persoonsgegevens is normaliter geregeld conform afdeling I van de Wet BRP..
     */
    INGEZETENE("I", "Ingezetene", "De bijhouding van de persoonsgegevens is normaliter geregeld conform afdeling I van de Wet BRP."),
    /**
     * De bijhouding van de persoonsgegevens is normaliter geregeld conform afdeling II van de Wet BRP..
     */
    NIET_INGEZETENE("N", "Niet-ingezetene", "De bijhouding van de persoonsgegevens is normaliter geregeld conform afdeling II van de Wet BRP."),
    /**
     * Het is onbekend conform welke afdeling van de Wet BRP de bijhouding normaliter geregeld is. Alleen te gebruiken
     * voor historische waarden..
     */
    ONBEKEND("?", "Onbekend",
            "Het is onbekend conform welke afdeling van de Wet BRP de bijhouding normaliter geregeld is. Alleen te gebruiken voor historische waarden.");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor Bijhoudingsaard
     * @param naam Naam voor Bijhoudingsaard
     * @param omschrijving Omschrijving voor Bijhoudingsaard
     */
    private Bijhoudingsaard(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Bijhoudingsaard.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Bijhoudingsaard.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Bijhoudingsaard.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.BIJHOUDINGSAARD;
    }

}
