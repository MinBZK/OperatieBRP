/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.mutatielevering;

import nl.bzk.brp.delivery.mutatielevering.boot.Booter;
import nl.bzk.brp.delivery.mutatielevering.boot.BooterImpl;

/**
 * Mutatielevering Main.
 */
public final class Main {

    /**
     * Booter implementatie van mutatielevering.
     */
    public static final String MUTATIELEVERING_BOOTER = "mutatielevering.booter";

    private Main() {
    }

    static {
        System.getProperties().putIfAbsent(MUTATIELEVERING_BOOTER, new BooterImpl());
    }

    /**
     * Main voor booter.
     * @param args args
     */
    public static void main(String[] args) {
        final Booter booter = (Booter) System.getProperties().get(MUTATIELEVERING_BOOTER);
        booter.springboot("classpath:mutatielevering.xml");
    }

}
