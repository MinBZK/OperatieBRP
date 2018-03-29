/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import nl.bzk.migratiebrp.bericht.model.lo3.AbstractUnparsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;

/**
 * Delivery report.
 */
public final class DeliveryReport extends AbstractUnparsedLo3Bericht {

    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.NON_DELIVERY_REASON);

    /**
     * Constructor.
     */
    public DeliveryReport() {
        super(HEADER, "DelR", null);
    }

    /* ************************************************************************************************************* */

    /**
     * Zet de non delivery reason.
     * @param nonDeliveryReason non delivery reason
     */
    public void setNonDeliveryReason(final int nonDeliveryReason) {
        setHeader(Lo3HeaderVeld.NON_DELIVERY_REASON, Integer.toString(nonDeliveryReason));
    }

}
