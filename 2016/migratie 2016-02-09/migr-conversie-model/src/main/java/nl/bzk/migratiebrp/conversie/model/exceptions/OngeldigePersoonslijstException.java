/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.exceptions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Exception class voor preconditie fouten in niet onjuiste voorkomens tijdens conversie LO3 -> BRP.
 */
@Root
public final class OngeldigePersoonslijstException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param message
     *            melding
     */
    public OngeldigePersoonslijstException(@Element(name = "message") final String message) {
        super(message);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof OngeldigePersoonslijstException)) {
            return false;
        }
        return new EqualsBuilder().isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    @Element(name = "message")
    public String getMessage() {
        return super.getMessage();
    }
}
