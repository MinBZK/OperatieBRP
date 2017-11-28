/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Enumeratie van de vergelijkingsoperator welke ondersteund worden.
 */
public enum OperatorType {

    /**
     * Operator voor X gelijk aan Y.
     */
    GELIJK("=", Prioriteit.PRIORITEIT_GELIJKHEIDSOPERATOR),
    /**
     * Operator voor X ongelijk aan Y.
     */
    ONGELIJK("<>", Prioriteit.PRIORITEIT_GELIJKHEIDSOPERATOR),
    /**
     * Operator voor X groter dan Y.
     */
    GROTER(">", Prioriteit.PRIORITEIT_VERGELIJKINGSOPERATOR),
    /**
     * Operator voor X groter dan of gelijk aan Y.
     */
    GROTER_OF_GELIJK(">=", Prioriteit.PRIORITEIT_VERGELIJKINGSOPERATOR),
    /**
     * Operator voor X kleiner dan Y.
     */
    KLEINER("<", Prioriteit.PRIORITEIT_VERGELIJKINGSOPERATOR),
    /**
     * Operator voor X kleiner dan of gelijk Y.
     */
    KLEINER_OF_GELIJK("<=", Prioriteit.PRIORITEIT_VERGELIJKINGSOPERATOR),
    /**
     * Operator voor X wilcard vergelijk aan Y.
     */
    WILDCARD("=%", Prioriteit.PRIORITEIT_GELIJKHEIDSOPERATOR),
    /**
     * Operator plus.
     */
    PLUS("+", Prioriteit.PRIORITEIT_PLUS),
    /**
     * Operator min.
     */
    MIN("-", Prioriteit.PRIORITEIT_MINUS);

    private String operatorString;
    private Prioriteit prioriteit;

    /**
     * Constructor.
     *
     * @param operatorString operatorString
     * @param prioriteit     de prioriteit van de operator
     */
    OperatorType(final String operatorString, final Prioriteit prioriteit) {
        this.operatorString = operatorString;
        this.prioriteit = prioriteit;
    }

    /**
     * Parsed een operatorString naar een Operator instantie.
     *
     * @param operatorString een operator String
     * @return een Operator
     */
    public static OperatorType parseWaarde(final String operatorString) {
        for (final OperatorType operator : OperatorType.values()) {
            if (operator.operatorString.equals(operatorString)) {
                return operator;
            }
        }
        //parse-time zal dit niet snel gegooid worden, want dan zal ANTLR
        //dat als syntaxfout zien.
        throw new IllegalArgumentException(operatorString);
    }

    public Prioriteit getPrioriteit() {
        return prioriteit;
    }

    @Override
    public String toString() {
        return operatorString;
    }
}
