/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser.exception;

import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;

/**
 * Exceptie voor het geval dat de expressie niet geparsed kan worden.
 */
public final class ExpressieParseException extends ExpressieRuntimeException {

    private static final long serialVersionUID = 4921218674040009727L;

    /**
     * Constructor met String.
     *
     * @param message de fout
     */
    public ExpressieParseException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param parserFout de parserfout.
     */
    public ExpressieParseException(final ParserFout parserFout) {
        super(parserFout.getFoutCode().getFoutmelding());
    }
}
