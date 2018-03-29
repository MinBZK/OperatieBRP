/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

/**
 * Boolean value adapter.
 */
public final class BooleanValueAdapter implements ValueAdapter<Boolean> {

    @Override
    public Boolean adaptValue(final String value) {
        Boolean result = null;
        if (value != null) {
            if ("Ja".equals(value)) {
                result = Boolean.TRUE;
            } else {
                result = Boolean.parseBoolean(value);
            }
        }
        return result;
    }
}
