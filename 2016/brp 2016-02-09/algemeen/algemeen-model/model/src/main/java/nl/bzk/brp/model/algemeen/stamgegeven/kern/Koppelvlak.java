/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;

/**
 * De typering van koppelvlakken rondom de BRP.
 *
 * Het Koppelvlak geeft een nadere definitie ten behoeve van welk doel een gegeven wordt uitgewisseld.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Koppelvlak implements ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null),
    /**
     * Bijhouding.
     */
    BIJHOUDING("Bijhouding", Stelsel.BRP),
    /**
     * Levering.
     */
    LEVERING("Levering", Stelsel.BRP),
    /**
     * ISC.
     */
    I_S_C("ISC", Stelsel.BRP),
    /**
     * GBA.
     */
    GBA("GBA", Stelsel.GBA);

    private final String naam;
    private final Stelsel stelsel;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Koppelvlak
     * @param stelsel Stelsel voor Koppelvlak
     */
    private Koppelvlak(final String naam, final Stelsel stelsel) {
        this.naam = naam;
        this.stelsel = stelsel;
    }

    /**
     * Retourneert Naam van Koppelvlak.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Stelsel van Koppelvlak.
     *
     * @return Stelsel.
     */
    public Stelsel getStelsel() {
        return stelsel;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.KOPPELVLAK;
    }

}
