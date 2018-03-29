/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.docker.dockerservice.mojo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Action;
import nl.bzk.brp.docker.dockerservice.mojo.parameters.Mode;
import nl.bzk.brp.docker.dockerservice.mojo.service.ServiceUtil.ServiceConfigurationException;
import org.junit.Assert;
import org.junit.Test;

public class ServiceUtilTest {

    @Test
    public void bepaalServiceNaam() throws ServiceConfigurationException {
        // Default
        Assert.assertEquals("test-config", ServiceUtil.bepaalContainerNaam("test-config"));

        // Gezette naam
        Assert.assertEquals("test-anderenaam", ServiceUtil.bepaalContainerNaam("test-naam"));
    }

    @Test(expected = ServiceConfigurationException.class)
    public void bepaalActieArgumentenActieNietToegestaan() throws ServiceConfigurationException {
        ServiceUtil.bepaalActieArgumenten("test-config", Action.EXECUTE, Mode.RUN, null, null, null, false, null);
    }

    @Test
    public void bepaalActieArgumentenActieWeinigConfig() throws ServiceConfigurationException {
        final String image = "test/empty:latest";
        assertEqual(combine(image), ServiceUtil.bepaalActieArgumenten("test-empty", Action.CREATE, Mode.RUN, null, null, null, false, null));

        // Check service argumenten
        assertEqual(combine("-e", "AANTAL=2", image),
                ServiceUtil.bepaalActieArgumenten("test-empty", Action.CREATE, Mode.RUN, null, null, "-e AANTAL=2", false, null));

        // Check commando argumenten (zonder commando, dus argumenten worden niet meegenomen)
        assertEqual(combine(image),
                ServiceUtil.bepaalActieArgumenten("test-empty", Action.CREATE, Mode.RUN, null, null, null, false, "-Dproperty=value -DandereProperty=value"));

    }

    @Test
    public void bepaalActieArgumentenCreate() throws ServiceConfigurationException {
        final List<String> configuredServiceArguments = Arrays.asList("--network", "brp", "-p", "1212:1212", "-p", "3481:3481", "--log-driver", "json-file",
                "--log-opt", "max-size=1m", "--log-opt", "max-file=5", "-e", "MAILBOX_DB_ENV_HOSTNAME=migr-mailboxdatabase", "-e", "MAILBOX_DB_ENV_PORT=5432",
                "-e", "MAILBOX_DB_ENV_NAME=mailbox", "-e", "MAILBOX_DB_ENV_USERNAME=migratie", "-e", "MAILBOX_DB_ENV_PASSWORD=M1gratie");
        final String image = "test/service:latest";
        final List<String> configuredCommand =
                Arrays.asList("/dockerize", "--wait", "tcp://migr-mailboxdatabase:5432", "-timeout", "300s", "java", "nl.bzk.migratiebrp.tools.test.TestMain");

        assertEqual(combine(configuredServiceArguments, image, configuredCommand),
                ServiceUtil.bepaalActieArgumenten("test-config", Action.CREATE, Mode.RUN, null, null, null, false, null));

        // Check registry
        assertEqual(combine(configuredServiceArguments, "fac-reg.modernodam.nl:5000/test/service:latest", configuredCommand),
                ServiceUtil.bepaalActieArgumenten("test-config", Action.CREATE, Mode.RUN, "fac-reg.modernodam.nl:5000/", null, null, false, null));

        // Check version
        assertEqual(combine(configuredServiceArguments, "test/service:130", configuredCommand),
                ServiceUtil.bepaalActieArgumenten("test-config", Action.CREATE, Mode.RUN, null, "130", null, false, null));

        // Check service argumenten
        assertEqual(combine(configuredServiceArguments, "-e", "AANTAL=2", image, configuredCommand),
                ServiceUtil.bepaalActieArgumenten("test-config", Action.CREATE, Mode.RUN, null, null, "-e AANTAL=2", false, null));

        // Check commando argumenten
        assertEqual(combine(configuredServiceArguments, image, configuredCommand, "-Dproperty=value", "-DandereProperty=value"),
                ServiceUtil.bepaalActieArgumenten("test-config", Action.CREATE, Mode.RUN, null, null, null, false, "-Dproperty=value -DandereProperty=value"));
    }

    @SuppressWarnings("unchecked")
    private List<String> combine(final Object... objects) {
        final List<String> result = new ArrayList<>();
        for (final Object object : objects) {
            if (object instanceof Collection) {
                result.addAll((Collection<String>) object);
            } else {
                result.add((String) object);
            }
        }
        return result;
    }

    private void assertEqual(final List<String> expected, final List<String> actual) {
        System.out.println("Expected:" + expected);
        System.out.println("  Actual:" + actual);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void splitTest() {
        Assert.assertArrayEquals(new String[]{"sdf", "asdass"}, "   sdf     asdass   ".trim().split("\\s+"));
    }


}
