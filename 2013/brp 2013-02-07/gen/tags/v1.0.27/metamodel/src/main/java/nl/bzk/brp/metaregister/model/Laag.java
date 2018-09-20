/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

public enum Laag {

    LOGISCH(1749), OPERATIONEEL(1751);

    private static ThreadLocal<Laag> laagThreadLocal = new ThreadLocal<Laag>();

    static {
        laagThreadLocal.set(Laag.LOGISCH);
    }

    private int id;

    private Laag(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Laag getLaag() {
        return laagThreadLocal.get();
    }

    public void set() {
        laagThreadLocal.set(this);
    }

}
