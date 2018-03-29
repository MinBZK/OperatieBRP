/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common.operatie;

/**
 * Wrapper voor een checked exception om makkelijker gebruikt te kunnen worden binnen herhalingen.
 */
public class ExceptionWrapper extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final Exception wrapped;

    /**
     * Constructor.
     * @param wrapped the checked exception
     */
    public ExceptionWrapper(final Exception wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }

    /**
     * Geef de waarde van wrapped.
     * @return wrapped
     */
    public final Exception getWrapped() {
        return wrapped;
    }
}
