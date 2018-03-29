/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

import java.util.Collections;
import java.util.List;

/**
 * Een opgedeelde expressie. Alleen indien de expressie is op te delen zal deze worden opgedeeld.
 */
public class ElementWaarde implements Expressie {

    private final Criterium criterium;

    /**
     * Initialiseert een element waarde met een brpExpressie.
     * @param criterium het criterium
     */
    public ElementWaarde(final Criterium criterium) {
        this.criterium = criterium;
    }

    @Override
    public final String getBrpExpressie() {
        return criterium.getExpressieString();
    }

    @Override
    public final List<Criterium> getCriteria() {
        return Collections.singletonList(criterium);
    }

}
