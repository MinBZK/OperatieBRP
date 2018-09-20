/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator;

import org.junit.Assert;
import org.junit.Test;


public class GeneratorPackageNamesTest {

    @Test
    public final void shouldReturnFullPackageName() {
        for (GenerationPackageNames packageName : GenerationPackageNames.values()) {
            if (packageName.getBasePackage() != null) {
                Assert.assertEquals("packagenaam komt niet overeen met base + sub", packageName.getBasePackage().getPackage()
                    + "." + packageName.getSubPackage(), packageName.getPackage());
            } else
                Assert.assertEquals("packagenaam komt niet overeen met sub", packageName.getSubPackage(),
                        packageName.getPackage());
        }
    }

    @Test
    public final void shouldReturnFullPackagePath() {
        for (GenerationPackageNames packageName : GenerationPackageNames.values()) {
            if (packageName.getBasePackage() != null) {
                Assert.assertEquals("packagepath komt niet overeen met base + sub", (packageName.getBasePackage().getPackage()
                    + "." + packageName.getSubPackage()).replace(".", "/"), packageName.getPackageAsPath());
            } else
                Assert.assertEquals("packagepath komt niet overeen met sub", (packageName.getSubPackage()).replace(".", "/"), packageName.getPackageAsPath());
        }
    }

}
