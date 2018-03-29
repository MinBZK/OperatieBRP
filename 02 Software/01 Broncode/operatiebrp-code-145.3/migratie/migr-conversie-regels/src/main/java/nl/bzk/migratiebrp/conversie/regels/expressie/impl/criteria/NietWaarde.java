/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

import java.util.List;

/**
 * Geeft inverse van expressie.
 */
public class NietWaarde implements Expressie {

    private Expressie expressie;

    /**
     * Constructor.
     * @param expressie De expressie die inverse moet zijn
     */
    public NietWaarde(Expressie expressie) {
        this.expressie = expressie;
    }

    @Override
    public String getBrpExpressie() {
        return String.format("NIET(%s)", expressie.getBrpExpressie());
    }

    @Override
    public List<Criterium> getCriteria() {
        return expressie.getCriteria();
    }
}
