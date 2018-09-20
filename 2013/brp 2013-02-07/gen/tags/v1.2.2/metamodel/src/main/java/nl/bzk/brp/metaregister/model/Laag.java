/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

/** Enumeratie voor de verschillende lagen in het BMR. */
public enum Laag {

    /** De logische laag. */
    LOGISCH(1749),
    /** De operationele laag. */
    OPERATIONEEL(1751);

    private static ThreadLocal<Laag> laagThreadLocal = new ThreadLocal<Laag>();

    static {
        laagThreadLocal.set(Laag.LOGISCH);
    }

    private int id;

    /**
     * Standaard constructor die direct het id van de laag initialiseert.
     *
     * @param id het id van de laag.
     */
    private Laag(final int id) {
        this.id = id;
    }

    /**
     * Retourneert het id van de laag.
     *
     * @return het id van de laag.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourneert de laag waarmee de huidige thread werkt.
     *
     * @return de laag waarmee de huidige thread werkt.
     */
    public static Laag getLaag() {
        return laagThreadLocal.get();
    }

    /** Zet deze laag tot de laag waarmee de huidige thread werkt. */
    public void set() {
        laagThreadLocal.set(this);
    }

}
