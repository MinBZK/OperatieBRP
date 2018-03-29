/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessageUtilTest {
    @Test
    public void composeEmptyCause() {
        assertEquals(
                "[MELDING-8000]:  Fout tijdens inloggen MailboxServer. (LogonResult= 1).",
                MessageUtil.composeMessage("8000", new Object[]{1}));
    }

    @Test
    public void composeEmptyParams() {
        assertEquals(
                "[MELDING-8001]:  Fout tijdens ontvangen van spd_LogonConfirmation.",
                MessageUtil.composeMessage("8001", null));
    }

    @Test
    public void composeTooManyParams() {
        assertEquals("[MELDING-8000]:  Fout tijdens inloggen MailboxServer. (LogonResult= 1).2, 3, ",
                MessageUtil.composeMessage("8000", new Object[]{1, 2, 3}));
    }

    @Test
    public void composeWithCause() {
        assertThat(
                MessageUtil.composeMessage("8000", new Object[]{1}, new IllegalArgumentException()),
                containsString("java.lang.IllegalArgumentException"));
    }

    @Test
    public void composeWithVoaRuntimeExceptionCause() {
        assertThat(
                MessageUtil.composeMessage("8000", new Object[]{1}, new VoaRuntimeException("1", null)),
                not(containsString("VoaRuntimeException")));
    }

    @Test
    public void composeWithNestedCause() {
        assertThat(
                MessageUtil.composeMessage("8000", new Object[]{1}, new IllegalArgumentException("error", new IllegalStateException("nested"))),
                containsString("nested"));
    }

    @Test
    public void composeWithNestedCauseNoMessage() {
        assertThat(
                MessageUtil.composeMessage("8000", new Object[]{1}, new IllegalArgumentException("error", new IllegalStateException())),
                containsString("java.lang.IllegalStateException"));
    }

    @Test
    public void composeWithNestedCauseEmptyMessage() {
        assertThat(
                MessageUtil.composeMessage("8000", new Object[]{1}, new IllegalArgumentException("", new IllegalStateException(""))),
                containsString("java.lang.IllegalStateException"));
    }

    @Test
    public void composeUnknownCode() {
        assertEquals(
                "[MELDING-1]:  ???1??? {0}; {1}; {2}; {3}; {4}; {5}; {6}; {7}.",
                MessageUtil.composeMessage("1", null));
    }
}
