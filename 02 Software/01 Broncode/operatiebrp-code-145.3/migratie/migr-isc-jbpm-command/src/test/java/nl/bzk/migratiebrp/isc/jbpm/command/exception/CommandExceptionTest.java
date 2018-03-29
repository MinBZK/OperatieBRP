/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.exception;

import org.junit.Assert;
import org.junit.Test;

public class CommandExceptionTest {

    @Test
    public void test() {
        final Exception cause = new Exception();
        final String message = "Bla";

        final CommandException subject = new CommandException(message, cause);
        Assert.assertEquals(cause, subject.getCause());
        Assert.assertEquals(message, subject.getMessage());
    }
}
