/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Een Regel is een door de BRP te bewaken wetmatigheid.
 * @version 1.0.18.
 */
public enum Regel {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", "", "");

    /** Classificatie van de regel. */
    private final String soort;
    /** Funtionele code die aan de regel is toegekend. */
    private final String code;
    /** De omschrijving van de regel. Dit is een relatief korte(!) aanduiding van wat de regel poogt te bewerkstelligen. */
    private final String omschrijving;
    /** De functionele specificatie van de regel. */
    private final String specificatie;

    /**
     * Constructor.
     *
     * @param soort Classificatie van de regel.
     * @param code Funtionele code die aan de regel is toegekend.
     * @param omschrijving De omschrijving van de regel. Dit is een relatief korte(!) aanduiding van wat de regel poogt te bewerkstelligen.
     * @param specificatie De functionele specificatie van de regel.
     *
     */
    private Regel(final String soort, final String code, final String omschrijving, final String specificatie) {
        this.soort = soort;
        this.code = code;
        this.omschrijving = omschrijving;
        this.specificatie = specificatie;
    }

    /**
     * @return Classificatie van de regel.
     */
    public String getSoort() {
        return soort;
    }

    /**
     * @return Funtionele code die aan de regel is toegekend.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return De omschrijving van de regel. Dit is een relatief korte(!) aanduiding van wat de regel poogt te bewerkstelligen.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * @return De functionele specificatie van de regel.
     */
    public String getSpecificatie() {
        return specificatie;
    }

}
