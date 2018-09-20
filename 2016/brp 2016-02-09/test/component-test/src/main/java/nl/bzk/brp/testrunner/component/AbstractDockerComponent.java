/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.omgeving.ComponentException;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.omgeving.OmgevingException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import sun.java2d.xr.MutableInteger;

/**
 */
public abstract class AbstractDockerComponent extends AbstractComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String dockerContainerId;

    protected AbstractDockerComponent(final Omgeving omgeving) {
        super(omgeving);
    }

    public final String getDockerContainerId() {
        return dockerContainerId;
    }

    @Override
    public final void herstart() {
        LOGGER.info("begin herstart");
        final List<String> commands = new LinkedList<>();
        commands.addAll(Arrays.asList("docker", "restart", dockerContainerId));
        wacht(voerCommandoUit(commands));
        LOGGER.info("einde herstart");
    }

    @Override
    protected void doPrestart() {

        final List<String> commands = new LinkedList<>();
        commands.addAll(Arrays.asList("docker", "inspect", geefDockerImage().geefCompleteNaam()));
        final String output = StringUtils.join(geefOutput(wacht(voerCommandoUit(commands))), "");
        if (output.toLowerCase().contains("error")) {
            throw new OmgevingException("Component kan niet gevonden worden: " + geefDockerImage().geefCompleteNaam());
        }
    }

    protected final void doStart() {

        final List<String> commands = new LinkedList<>();
        commands.addAll(Arrays.asList("docker", "run", "-d"));

        //poorten
        final Map<Integer, Integer> poortMap = getPoortMap();
        if (!poortMap.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : poortMap.entrySet()) {
                commands.add("-p");
                commands.add(String.format("%d:%d", entry.getValue(), entry.getKey()));
            }
        }

        final Map<String, String> omgevingsVariabelen = new HashMap<>(geefOmgevingsVariabelen());
        final Map<String, String> addHostMap = new HashMap<>();

        //links
        final Map<String, String> linkMap = geefInterneLinkOpLogischeLinkMap();

        if (!linkMap.isEmpty()) {
            for (Map.Entry<String, String> entry : linkMap.entrySet()) {
                String linkId = null;
                final AbstractComponent component = (AbstractComponent) getOmgeving().geefComponent(entry.getValue());
                if (component instanceof AbstractDockerComponent) {
                    linkId = ((AbstractDockerComponent) component).getDockerContainerId();
                }

                if (linkId == null) {
                    omgevingsVariabelen.putAll(component.geefInjectOmgevingsVariabelen());
                    addHostMap.putAll(component.geefAddHostParameters());
                    continue;
                }
                commands.add("--link");
                commands.add(String.format("%s:%s", linkId, entry.getKey()));
            }
        }

        //omgeving
        if (!omgevingsVariabelen.isEmpty()) {
            for (Map.Entry<String, String> entry : omgevingsVariabelen.entrySet()) {
                commands.add("-e");
                commands.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
        }

        //extra hosts
        if (!addHostMap.isEmpty()) {
            for (Map.Entry<String, String> entry : addHostMap.entrySet()) {
                commands.add("--add-host");
                commands.add(String.format("%s:%s", entry.getKey(), entry.getValue()));
            }
        }


        //volumes
        if (!volumesFrom().isEmpty()) {
            for (String volume : volumesFrom()) {
                commands.add("--volumes-from");
                commands.add(((AbstractDockerComponent) getOmgeving().geefComponent(volume)).getDockerContainerId());
            }
        }

        commands.addAll(geefOverigeRunCommandos());

        commands.add(geefDockerImage().geefCompleteNaam());

        LOGGER.info(String.format("Start docker [%s] met commando [%s]", getLogischeNaam(), commands));
        final Process process = voerCommandoUit(commands);

        this.dockerContainerId = geefEnkeleOutput(wacht(process));
        if (StringUtils.isEmpty(this.dockerContainerId)) {
            throw new ComponentException("Fout bij starten Docker container, containerId is leeg");
        }
        LOGGER.info(String.format("Container gestart [%s] met id [%s]", getLogischeNaam(), dockerContainerId));

    }


    public List<String> volumesFrom() {
        return new LinkedList<>();
    }

    protected void doStop() {

        wacht(voerCommandoUit(Arrays.asList("docker", "stop", this.dockerContainerId)));

        LOGGER.info(String.format("%s met id %s gestopt", getLogischeNaam(), dockerContainerId));
    }


    /**
     * Definieert de interne namen voor links naar andere componenten.
     */
    protected Map<String, String> geefInterneLinkOpLogischeLinkMap() {
        return new HashMap<>();
    }

    protected abstract DockerImage geefDockerImage();

    protected Map<String, String> geefOmgevingsVariabelen() {
        final HashMap<String, String> map = new HashMap<>();
        //compose / swarm hack
        map.put("ROUTERINGCENTRALE_ENV_HOSTNAME", ComponentNamen.ROUTERINGCENTRALE);
        return map;
    }

    protected List<String> geefOverigeRunCommandos() {
        return Collections.emptyList();
    }

    protected final Process voerCommandoUit(final List<String> commands) {
        LOGGER.debug(String.format("Docker commando: %s", commands));
        final ProcessBuilder pb = new ProcessBuilder(commands);
        try {
            return pb.start();
        } catch (IOException e) {
            LOGGER.error("Fout bij uitvoeren commando", e.getMessage());
            throw new ComponentException(e);
        }
    }

    protected final Process wacht(final Process process) {
        final MutableInteger returnValue = new MutableInteger(Integer.MAX_VALUE);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    returnValue.setValue(process.waitFor());
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        });
        thread.start();
        try {
            thread.join(30 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        if (returnValue.getValue() == Integer.MAX_VALUE) {
            throw new RuntimeException("Gestopt met wachten, duurt te lang");
        } else if (returnValue.getValue() != 0) {
            throw new RuntimeException("Fout bij uitvoeren commando" + geefOutput(process));
        }

        return process;
    }

    protected final String geefEnkeleOutput(final Process p) {
        return geefOutput(p).iterator().next();
    }

    protected final List<String> geefOutput(final Process p) {

        try (final InputStream is = p.getErrorStream();) {
            final List<String> errorRegels = IOUtils.readLines(is);
            if (!errorRegels.isEmpty()) {
                throw new ComponentException(errorRegels.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Kan errorstream niet lezen", e);
        }
        try (final InputStream is = p.getInputStream();) {
            final List<String> lines = IOUtils.readLines(is);
            LOGGER.debug("Output: " + lines);
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Kan inputstream niet lezen", e);
        }
    }

    protected void printSysteemLog() {
        final List<String> strings = geefOutput(
            wacht(voerCommandoUit(Arrays.asList("docker", "exec", getDockerContainerId(), "less", "logs/brp-systeem.log"))));
        for (String logRegel : strings) {
            LOGGER.info(logRegel);
        }
    }

    protected final class DockerImage {

//        private String repositoryHost = "fac-reg.modernodam.nl";
//        private int repositoryPort = 5000;
        private String image;
//        private String versie = "89-SNAPSHOT";

        public DockerImage(final String image) {
            this.image = image;
        }

        private String geefCompleteNaam() {
            return image;
        }
    }


}
