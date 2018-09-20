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
 * De mogelijke functie van een adres.
 *
 *
 * Het Adres van een Persoon fungeert hetzij als woonadres, hetzij als briefadres.In geval van een niet-ingezetene
 * betreft het altijd een woonadres. Deze twee begrippen zijn gedefinieerd in de Wet BRP (artikel 1.1.)
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum FunctieAdres implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy"),
    /**
     * Woonadres.
     */
    WOONADRES("W", "Woonadres"),
    /**
     * Briefadres.
     */
    BRIEFADRES("B", "Briefadres");

    private final String code;
    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor FunctieAdres
     * @param naam Naam voor FunctieAdres
     */
    private FunctieAdres(final String code, final String naam) {
        this.code = code;
        this.naam = naam;
    }

    /**
     * Retourneert Code van Functie adres.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Functie adres.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.FUNCTIEADRES;
    }

}
