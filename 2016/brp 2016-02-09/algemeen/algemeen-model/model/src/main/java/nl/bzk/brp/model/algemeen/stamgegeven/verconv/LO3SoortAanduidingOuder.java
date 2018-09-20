/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.verconv;

import javax.annotation.Generated;

/**
 * De mogelijke soorten van aanduidingen van Ouders.
 *
 * Initieel bestond deze stamtabel niet en was de Aanduiding gemodelleerd als waarde beperking (1 of 2) op het
 * "LO3 aanduiding Ouder.Soort". Dit gaf problemen bij de Java generatoren omdat dit een ENUM '1' en '2' genereerde (en
 * dit is geen valide ENUM). Vandaar dat gekozen is voor een verwijzing naar een Release stamgegeven.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum LO3SoortAanduidingOuder {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Ouder 1.
     */
    OUDER1("Ouder 1"),
    /**
     * Ouder 2.
     */
    OUDER2("Ouder 2");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor LO3SoortAanduidingOuder
     */
    private LO3SoortAanduidingOuder(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van LO3 Soort aanduiding Ouder.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
