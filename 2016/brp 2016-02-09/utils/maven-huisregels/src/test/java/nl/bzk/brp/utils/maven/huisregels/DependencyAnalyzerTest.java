/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils.maven.huisregels;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class DependencyAnalyzerTest {
    @Test
    public void testPackagesWithCyclesCount() throws IOException {
        JDepend jDepend = new JDepend();
        JavaPackage a = new JavaPackage("a");
        JavaPackage b = new JavaPackage("b");
        JavaPackage c = new JavaPackage("c");
        jDepend.addPackage(a);
        jDepend.addPackage(b);
        jDepend.addPackage(c);

        DependencyAnalyzer analyzer = new DependencyAnalyzer();
        ReflectionTestUtils.setField(analyzer, "jDepend", jDepend);

        assertEquals(0, analyzer.packagesWithCyclesCount());

        b.dependsUpon(c);
        assertEquals(0, analyzer.packagesWithCyclesCount());

        c.dependsUpon(b);
        assertEquals(2, analyzer.packagesWithCyclesCount());

        a.dependsUpon(b);
        // a wordt nu ook gezien als package met een cyclecount
        assertEquals(3, analyzer.packagesWithCyclesCount());
    }
}
