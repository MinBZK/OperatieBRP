/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.signatuur;

import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;

/**
 * Exceptie die gegooid wordt als de signatuur niet correct is.
 */
final class SignatuurIncorrectException extends ExpressieRuntimeException {

    private static final long serialVersionUID = 3046457467849984920L;

    /**
     * Constructor.
     *
     * @param message de foutmelding
     */
    SignatuurIncorrectException(final String message) {
        super(message);
    }
}

