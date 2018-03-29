/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

/**
 * Ongelijk alle operator. ALLE(%1$s, v, NIET(v = %2$s))
 */
public class OngelijkAlleOperator extends StandaardOperator {

    /**
     * Constructor.
     */
    public OngelijkAlleOperator() {
        super("NIET(%1$s E= %2$s)");
    }
}
