/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser;


/**
 * Enumeratie voor de verschillende ondersteunde operator types.
 */
public enum OperatorType {

    /** Logische niet operator. */
    NIET("niet", 1),
    /** Logische en operator. */
    EN("en", 2),
    /** Logische of operator. */
    OF("of", 2),
    /** Gelijk aan operator. */
    GELIJK("gelijk", 2),
    /** Gelijk (case sensitive) operator. */
    GELIJK_CS("gelijk_cs", 2),
    /** Kleinder dan operator. */
    KLEINER("kleiner", 2),
    /** Groter dan operator. */
    GROTER("groter", 2),
    /** Kleinder dan of gelijk aan operator. */
    KLEINER_GELIJK("kleiner_gelijk", 2),
    /** Groter dan of gelijk aan operator. */
    GROTER_GELIJK("groter_gelijk", 2),
    /** Bevat operator. */
    BEVAT("bevat", 2),
    /** Start operator. */
    START("start", 0),
    /** Einde operator. */
    EINDE("einde", 0),
    /** Leeg operator. */
    LEEG("leeg", 1),
    /** Functie operator. */
    FUNCTIE("functie", -1);

    private final String name;
    private final int aantalOperands;


    /**
     * Standaard constructor die de naam van het operator type zet.
     * @param naam naam het operator type.
     * @param aantalOperands aantal operands.
     */
    private OperatorType(final String naam, final int aantalOperands) {
        this.name = naam;
        this.aantalOperands = aantalOperands;
    }

    /**
     * Retourneert de naam van de operator.
     * @return de naam van de operator.
     */
    public String getNaam() {
        return name;
    }

    /**
     * Indicatie of operator type een binaire operator is (dus twee operands heeft).
     * @return of operator type een binaire operator is.
     */
    public boolean isBinaireOperator() {
        return aantalOperands == 2;
    }

    /**
     * Indicatie of operator type een unaire operator is (dus een enkele operand heeft).
     * @return of operator type een unaire operator is.
     */
    public boolean isUnaireOperator() {
        return aantalOperands == 1;
    }

}
