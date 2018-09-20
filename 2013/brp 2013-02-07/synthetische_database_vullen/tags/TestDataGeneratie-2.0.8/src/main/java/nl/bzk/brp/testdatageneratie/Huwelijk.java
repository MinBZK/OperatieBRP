/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

public class Huwelijk {

    private final int persIdMan, persIdVrouw;

    public Huwelijk(final int persIdMan, final int persIdVrouw) {
        this.persIdMan = persIdMan;
        this.persIdVrouw = persIdVrouw;
    }

    public int getPersIdMan() {
        return persIdMan;
    }

    public int getPersIdVrouw() {
        return persIdVrouw;
    }

}
