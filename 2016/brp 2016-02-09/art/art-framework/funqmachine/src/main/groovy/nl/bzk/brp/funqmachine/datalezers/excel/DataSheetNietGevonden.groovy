package nl.bzk.brp.funqmachine.datalezers.excel

import nl.bzk.brp.funqmachine.datalezers.DataNietValideException

/**
 * Exceptie die aangeeft dat een Sheet in excel niet is gevonden.
 */
class DataSheetNietGevonden extends DataNietValideException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    DataSheetNietGevonden(String msg) { super(msg) }
}
