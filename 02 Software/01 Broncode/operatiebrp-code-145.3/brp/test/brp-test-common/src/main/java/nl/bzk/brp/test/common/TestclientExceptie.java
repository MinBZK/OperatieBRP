/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common;

import java.net.SocketException;
import java.util.concurrent.ExecutionException;

/**
 */
public class TestclientExceptie extends RuntimeException {
    public TestclientExceptie(final String s) {
        super(s);
    }

    public TestclientExceptie(final SocketException e) {
        super(e);
    }

    public TestclientExceptie(final String s, final Throwable t) {
        super(s, t);
    }

    public TestclientExceptie(final Exception e) {
        super(e);
    }
}
