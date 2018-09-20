/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Permission;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractVoaMainTest {

    protected void setupSecurityManager(final int exitCode) {
        final SecurityManager securityManager = new SecurityManager() {
            @Override
            public void checkPermission(final Permission permission) {
                if (("exitVM." + exitCode).equals(permission.getName())) {
                    throw new SecurityException("System.exit attempted and blocked.");
                } else if (("exitVM." + 1).equals(permission.getName())
                        || ("exitVM." + 2).equals(permission.getName())
                        || ("exitVM." + 3).equals(permission.getName())
                        || ("exitVM." + 4).equals(permission.getName())
                        || ("exitVM." + 5).equals(permission.getName())
                        || ("exitVM." + 6).equals(permission.getName())) {
                    throw new RuntimeException("Onverachte system.exit");
                }

            }
        };
        System.setSecurityManager(securityManager);
    }

    protected void createPassw() throws IOException {
        final File file = new File(".voiscPwd");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        final FileWriter writer = new FileWriter(file);
        writer.write("changeit");
        writer.flush();
        writer.close();
    }

    @After
    public void teardown() {
        final File file = new File(".voiscPwd");
        if (file.exists()) {
            file.delete();
        }
    }
}
