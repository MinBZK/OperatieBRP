package nl.bzk.brp.funqmachine.verstuurder.soap

/**
 * Specifieke exceptie voor het aanduiden dat de service(s) niet
 * bereikbaar zijn.
 */
class ServicesNietBereikbaarException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    ServicesNietBereikbaarException(String s, Throwable t) {
        super(s, t)
    }
}
