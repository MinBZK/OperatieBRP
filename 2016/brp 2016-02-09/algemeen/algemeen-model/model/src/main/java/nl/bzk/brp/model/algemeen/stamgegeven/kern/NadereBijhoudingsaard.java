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
 * Categorisatie van nadere bijhoudingsaard.
 *
 * De waarde ? is uitsluitend bedoeld voor conversie. Het is ooit voor gekomen dat er een 'onbekend' in GBA aanwezig
 * was, maar mogelijk dat deze momenteel zelfs weer verdwenen is.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum NadereBijhoudingsaard implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", "Dummy"),
    /**
     * Alle actuele gegevens worden bijgehouden.
     */
    ACTUEEL("A", "Actueel", "Alle actuele gegevens worden bijgehouden"),
    /**
     * De persoon is rechtstreeks een niet-ingezetene geworden.
     */
    RECHTSTREEKS_NIET_INGEZETENE("R", "Rechtstreeks niet-ingezetene", "De persoon is rechtstreeks een niet-ingezetene geworden"),
    /**
     * De persoon is geëmigreerd.
     */
    EMIGRATIE("E", "Emigratie", "De persoon is ge\u00EBmigreerd"),
    /**
     * De persoon is overleden.
     */
    OVERLEDEN("O", "Overleden", "De persoon is overleden"),
    /**
     * Verblijf van de persoon is onbekend.
     */
    VERTROKKEN_ONBEKEND_WAARHEEN("V", "Vertrokken onbekend waarheen", "Verblijf van de persoon is onbekend"),
    /**
     * De bijhouding is beperkt vanwege een Ministerieel besluit.
     */
    MINISTERIEEL_BESLUIT("M", "Ministerieel besluit", "De bijhouding is beperkt vanwege een Ministerieel besluit"),
    /**
     * Persoonslijst is ten onrechte opgenomen.
     */
    FOUT("F", "Fout", "Persoonslijst is ten onrechte opgenomen"),
    /**
     * De nadere bijhoudingsaard is niet bekend. Alleen te gebruiken voor historische waarden..
     */
    ONBEKEND("?", "Onbekend", "De nadere bijhoudingsaard is niet bekend. Alleen te gebruiken voor historische waarden.");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor NadereBijhoudingsaard
     * @param naam Naam voor NadereBijhoudingsaard
     * @param omschrijving Omschrijving voor NadereBijhoudingsaard
     */
    private NadereBijhoudingsaard(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Nadere bijhoudingsaard.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Nadere bijhoudingsaard.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Nadere bijhoudingsaard.
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
        return ElementEnum.NADEREBIJHOUDINGSAARD;
    }

}
