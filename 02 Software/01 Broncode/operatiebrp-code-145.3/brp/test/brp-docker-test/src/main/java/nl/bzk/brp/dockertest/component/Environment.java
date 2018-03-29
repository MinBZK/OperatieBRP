/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;

/**
 */
public class Environment {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static DockerNaam teDebuggenComponent;

    private static final Dockerhost[] JENKINS_AVAIL_HOSTS = new Dockerhost[]{
            new Dockerhost("oap-dok01.modernodam.nl", 2),
            new Dockerhost("oap-dok02.modernodam.nl", 2),
            new Dockerhost("oap-dok03.modernodam.nl", 1),
            new Dockerhost("oap-dok04.modernodam.nl",  1),
            new Dockerhost("oap-dok05.modernodam.nl", 1)
    };

    private static final Iterator<Dockerhost> JENKINS_HOST_ITERATOR = Iterators.cycle(Lists.newArrayList(JENKINS_AVAIL_HOSTS));

    static {
        loadUserSettings();
    }

    /**
     * Boolean property om componenten parallel te starten.
     */
    static final boolean PARALLEL_START = Boolean.parseBoolean(System.getProperty("PARALLEL_START", "false"));

    private Environment() {
    }

    static boolean isDebuggerEnabled() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
    }


    public static void setTeDebuggenComponent(DockerNaam dockerNaam) {
        teDebuggenComponent = dockerNaam;
    }
    static DockerNaam geefDebugComponent() {
        return teDebuggenComponent;
    }

    static boolean gebruikPoortVoorkeur() {
        return System.getenv().containsKey("VASTE_POORT");
    }

    public static boolean isJenkinsRun() {
        return System.getenv("JENKINS_HOME") != null;
    }

    public static void loadUserSettings() {
        File f = new File(new File(System.getProperty("user.home")), "brpe2e.properties");
        if (f.exists()) {
            LOGGER.info("Gebruik user properties uit: " + f);
            try (FileInputStream fis = new FileInputStream(f)){
                System.getProperties().load(fis);
            } catch (IOException e) {
                throw new TestclientExceptie(e);
            }
        }
    }

    /**
     * Deze methode geeft het host IP-adres van de omgeving.
     */
    public static Dockerhost bepaalDockerhost() {
        final Dockerhost host;
        if (isJenkinsRun()) {
            host = bepaalDockerHostVoorJenkins();
        } else {
            host = new Dockerhost(bepaalDockerHostVoorLokaal());
        }
        LOGGER.info("Dockerhost IP = {}", host);
        return host;
    }

    private static synchronized Dockerhost bepaalDockerHostVoorJenkins() {
        for (int i = 0 ; i < JENKINS_AVAIL_HOSTS.length ; i++) {
            final Dockerhost dockerhost = JENKINS_HOST_ITERATOR.next();
            if (dockerhost.getCurrentParallel() < dockerhost.getMaxParallel()) {
                dockerhost.claim();
                return dockerhost;
            }
        }
        LOGGER.info("Geen vrije omgeving beschikbaar...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TestclientExceptie(e);
        }
        return bepaalDockerHostVoorJenkins();
    }

    private static String bepaalDockerHostVoorLokaal() {
        final String envDockerHost = System.getenv("DOCKER_IP");
        if (envDockerHost != null) {
            if (envDockerHost.contains(":")) {
                return envDockerHost.substring(0, envDockerHost.indexOf(':'));
            }
            return envDockerHost;
        }
        String dockerIp;
        if ((dockerIp = System.getProperty("DOCKER_IP")) != null) {
            return dockerIp;
        }
        throw new TestclientExceptie("DOCKER_IP niet gezet");
    }

    public static boolean moetStoppen(final AbstractDocker abstractDocker) {
        return isJenkinsRun() || Boolean.parseBoolean(System.getProperty("brp.e2e.shutdown." + abstractDocker.getDockerInfo().logischeNaam(), "true"));
    }
}
