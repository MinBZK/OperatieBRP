/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;

/**
 * Typering van de (logische) Elementen.
 *
 * Element wordt gebruikt zowel in het Meta-model als in het (gegenereerde) model. Keuze is gemaakt om die soorten te
 * onderkennen die relevant zijn in het (gegeneerde) BRP systeem. Dit betekent een beperking ten opzichte van de soorten
 * element die voorkomen in het BRP MetaRegister zelf.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortElement implements ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Objecttype.
     */
    OBJECTTYPE("Objecttype"),
    /**
     * Groep.
     */
    GROEP("Groep"),
    /**
     * Attribuut.
     */
    ATTRIBUUT("Attribuut");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortElement
     */
    private SoortElement(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort element.
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
        return ElementEnum.SOORTELEMENT;
    }

}
