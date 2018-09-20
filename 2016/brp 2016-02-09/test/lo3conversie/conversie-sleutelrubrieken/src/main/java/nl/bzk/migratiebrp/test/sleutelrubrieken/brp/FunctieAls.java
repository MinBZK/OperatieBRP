/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken.brp;

import nl.bzk.brp.expressietaal.expressies.functies.FunctieALS;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Wrapper om {@link FunctieALS} tbv logging.
 */
public class FunctieAls extends FunctieDelegate {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public FunctieAls() {
        super(new FunctieALS(), LOG);
    }

}
