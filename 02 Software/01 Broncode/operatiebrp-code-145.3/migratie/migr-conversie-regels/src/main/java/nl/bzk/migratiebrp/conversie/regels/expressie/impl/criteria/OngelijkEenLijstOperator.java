/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

/**
 * Ongelijk een lijst operator. (NIET (%1$s IN {%2$s}) OF IS_NULL(%1$s))
 */
public class OngelijkEenLijstOperator extends StandaardOperator {

    /**
     * Constructor.
     */
    public OngelijkEenLijstOperator() {
        super("NIET(%1$s AIN {%2$s})");
    }
}
