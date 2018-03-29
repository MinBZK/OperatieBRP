/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.DockerInfo;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.component.ProcessHelper;

/**
 */
public class ZetOmgevingLokaalNeerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    public static void main(String[] args) {
        Environment.loadUserSettings();

        if (System.getProperty("DOCKER_REGISTRY") == null) {
            return;
        }
        Arrays.stream(DockerNaam.values()).parallel().forEach(ZetOmgevingLokaalNeerUtil::pullDocker);
    }

    private static void pullDocker(final DockerNaam dockerNaam) {

        LOGGER.info("Pulling: " + dockerNaam);
        final String dockerRegistry = System.getProperty("DOCKER_REGISTRY");
        final String version = System.getProperty("APP_VERSION", "latest");
        final String docker =  String.format("%s/%s:%s", dockerRegistry, dockerNaam.getDockerImpl().getAnnotation(DockerInfo.class).image(), version);
        final ArrayList<String> cmdList = Lists.newArrayList("docker", "pull", docker);
        LOGGER.info("Einde pulling: " + dockerNaam);
        try {
            new ProcessHelper(1, TimeUnit.HOURS).startProces(cmdList);
        } catch (Exception e) {
            LOGGER.error("Kan docker niet uit registry halen: " + dockerNaam, e);
        }
    }
}
