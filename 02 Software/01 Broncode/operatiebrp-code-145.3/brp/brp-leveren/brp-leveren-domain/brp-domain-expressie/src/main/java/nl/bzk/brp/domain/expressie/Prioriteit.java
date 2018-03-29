/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Enumeratie van prioriteiten van expressie operatoren.
 */
public enum Prioriteit {
    /**
     * Standaard prioriteit (precedence/volgorde) van literals in een expressie.
     */
    PRIORITEIT_LITERAL(120),
    /**
     * Standaard prioriteit (precedence/volgorde) van gelijkheidsoperator in een expressie.
     */
    PRIORITEIT_GELIJKHEIDSOPERATOR(60),
    /**
     * Standaard prioriteit (precedence/volgorde) van vergelijkingsoperator in een expressie.
     */
    PRIORITEIT_VERGELIJKINGSOPERATOR(70),
    /**
     * Standaard prioriteit (precedence/volgorde) van logische 'EN' operator in een expressie.
     */
    PRIORITEIT_LOGISCHE_EN(30),
    /**
     * Standaard prioriteit (precedence/volgorde) van logische 'OF' operator in een expressie.
     */
    PRIORITEIT_LOGISCHE_OF(20),
    /**
     * Standaard prioriteit (precedence/volgorde) van logische 'NIET' operator in een expressie.
     */
    PRIORITEIT_LOGISCHE_NIET(80),
    /**
     * Standaard prioriteit (precedence/volgorde) van '+' operator in een expressie.
     */
    PRIORITEIT_PLUS(90),
    /**
     * Standaard prioriteit (precedence/volgorde) van '-' operator  in een expressie.
     */
    PRIORITEIT_MINUS(90),
    /**
     * Standaard prioriteit (precedence/volgorde) van 'inverse' operator in een expressie.
     */
    PRIORITEIT_INVERSE(100),
    /**
     * Standaard prioriteit (precedence/volgorde) van 'bevat' operator in een expressie.
     */
    PRIORITEIT_BEVAT(40),
    /**
     * Standaard prioriteit (precedence/volgorde) van 'waarbij' operator in een expressie.
     */
    PRIORITEIT_WAARBIJ(0);

    private int waarde;

    /**
     * Constructor.
     *
     * @param waarde de prioriteitwaarde
     */
    Prioriteit(final int waarde) {
        this.waarde = waarde;
    }

    /**
     * @return de prioriteitwaarde, groter betekent hogere prioriteit
     */
    public int getWaarde() {
        return waarde;
    }
}
