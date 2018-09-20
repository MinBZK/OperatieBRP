/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils.maven.huisregels;

import java.io.IOException;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

/**
 * Een wrapper om een JDepend object, om specifiekere dependency informatie te verkrijgen.
 */
public class DependencyAnalyzer {
    private final JDepend jDepend;

    public DependencyAnalyzer() {
        jDepend = new JDepend();
    }

    public void addDirectory(String dir) throws IOException {
        jDepend.addDirectory(dir);
    }

    public void analyze() {
        jDepend.analyze();
    }

    /**
     * Geeft het aantal packages dat deel uitmaakt van een cycle.
     *
     * Let op: als package B en C een dependency op elkaar hebben, dus een cycle vormen,
     * en package A verwijst naar B, dan wordt A ook gezien als een package met een cycle.
     * Dat is een feature van JDepend.
     * @return
     */
    public int packagesWithCyclesCount() {
        int result = 0;
        for (Object jDependPackage : jDepend.getPackages()) {
            JavaPackage javaPackage = (JavaPackage) jDependPackage;
            if (javaPackage.containsCycle()) {
                result++;
            }
        }
        return result;
    }

    /**
     * Geeft aan of er een of meer cycles zijn gevonden.
     * @return
     */
    public boolean containsCycles() {
        return jDepend.containsCycles();
    }
}
