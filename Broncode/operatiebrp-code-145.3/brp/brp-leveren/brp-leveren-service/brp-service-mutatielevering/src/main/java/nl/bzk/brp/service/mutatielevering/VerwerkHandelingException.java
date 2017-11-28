/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

/**
 * VerwerkHandelingException.
 */
public final class VerwerkHandelingException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor voor de fout.
     * @param e de root cause
     */
    public VerwerkHandelingException(final Exception e) {
        super(e);
    }
}
