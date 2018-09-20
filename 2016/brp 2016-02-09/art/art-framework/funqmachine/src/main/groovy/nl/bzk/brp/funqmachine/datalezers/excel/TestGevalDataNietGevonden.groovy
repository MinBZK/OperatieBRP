package nl.bzk.brp.funqmachine.datalezers.excel

import nl.bzk.brp.funqmachine.datalezers.DataNietValideException

/**
 * Exceptie die aangeeft dat de gevraagde kolom (testgeval) in een
 * excelbestand niet bestaat.
 */
class TestGevalDataNietGevonden extends DataNietValideException {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    TestGevalDataNietGevonden(String msg) { super(msg) }
}
