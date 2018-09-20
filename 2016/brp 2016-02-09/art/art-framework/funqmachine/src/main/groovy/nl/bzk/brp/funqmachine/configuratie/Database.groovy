package nl.bzk.brp.funqmachine.configuratie

/**
 * De mogelijke waardes voor een BRP database.
 * Deze waardes komen terug in {@link Environment} en de bijbehorende
 * {@code landscape.groovy}.
 *
 * @see DatabaseConfig
 * @see Environment
 */
public enum Database {

    /** De BRP Kern database. */
    KERN,

    /** De BRP Prot database. */
    PROT,

    /** De BRP Ber database. */
    BER,

    /**
     * De activeMQ database.
     * @deprecated Er zouden geen queries in de ActiveMQ database gedaan moeten worden.
     */
    @Deprecated
    ActiveMQ
}
