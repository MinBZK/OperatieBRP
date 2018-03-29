/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Abstracte implementatie voor Docker componenten.
 */
public abstract class AbstractDocker implements Docker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String dockerContainerId;
    private Status status = Status.INIT;
    private Map<Integer, Integer> poortMap = new HashMap<>();
    private Omgeving omgeving;

    @Override
    public boolean isGestart() {
        return status == Status.GESTART;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return isGestart();
    }

    @Override
    public boolean isGestopt() {
        return status == Status.GESTOPT;
    }

    void setOmgeving(final Omgeving omgeving) {
        this.omgeving = omgeving;
    }

    public Omgeving getOmgeving() {
        return omgeving;
    }

    @Override
    public final void preStart() {
        for (int internePoort : getDockerInfo().internePoorten()) {
            poortMap.put(internePoort, maakExternePoort(internePoort));
        }
        if (Environment.isDebuggerEnabled() && getDockerInfo().logischeNaam() == Environment.geefDebugComponent()) {
            poortMap.put(Poorten.DEBUG_POORT, Poorten.DEBUG_POORT + 1);
        }
    }

    @Override
    public final void start() {
        if (omgeving.getStatus() != Omgeving.Status.START_SEQUENTIE) {
            throw new IllegalStateException("Kan component niet starten, de omgeving is niet (meer) in de start fase.");
        }
        if (status == Status.INIT) {
            try {
                final List<String> commands = maakDockerStartCommando();
                LOGGER.info(String.format("Start docker [%s]", getLogischeNaam()));
                this.dockerContainerId = ProcessHelper.DEFAULT.startProces(commands).geefOutput();
                if (StringUtils.isEmpty(this.dockerContainerId)) {
                    throw new ComponentException("Fout bij starten Docker container, containerId is leeg");
                }
                final List<String> extraStartCommando = maakDockerExtraStartCommando();
                if (!extraStartCommando.isEmpty()) {
                    ProcessHelper.DEFAULT.startProces(maakDockerExtraStartCommando()).geefOutput();
                }
                LOGGER.info(String.format("Container gestart [%s] met id [%s]", getLogischeNaam(), dockerContainerId));
            } finally {
                this.status = Status.GESTART;
            }
        }
    }

    @Override
    public final void stop() {
        if (status == Docker.Status.GESTART) {
            try {
                beforeStop();
                if (this.dockerContainerId != null && Environment.moetStoppen(this)) {
                    ProcessHelper.DEFAULT.startProces(omgeving.getDockerCommandList("stop", this.dockerContainerId));
                    LOGGER.info(String.format("Container [%s] met id [%s] gestopt", getLogischeNaam(), dockerContainerId));
                }
                afterStop();
            } finally {
                this.status = Docker.Status.GESTOPT;
            }
        }
    }

    @Override
    public Map<Integer, Integer> getPoortMap() {
        return Collections.unmodifiableMap(poortMap);
    }


    @Override
    public String toString() {
        return getDockerInfo().logischeNaam().name();
    }

    @Override
    public String getDockerContainerId() {
        return dockerContainerId;
    }

    protected Integer geefPoortVoorkeur(int internepoort) {
        //geen voorkeur
        return null;
    }

    /**
     * Set met environmentvariabelen welke specifiek voor dit component nodig zijn.
     * @return map met environmentwaardenn
     */
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = Maps.newHashMap();
        map.put("DOCKER_IP", getOmgeving().getDockerHostname());
        if (Environment.isDebuggerEnabled()) {
            map.put("JAVA_OPTS", String.format("\"-agentlib:jdwp=transport=dt_socket,address=%d,server=y,suspend=n\"", Poorten.DEBUG_POORT));
        } else {

            final String javaOptsValue = org.apache.commons.lang3.StringUtils.join(new String[]{
                    //docker exec <docker> jcmd <pid> VM.native_memory summary
                    //"-XX:NativeMemoryTracking=summary",
                    "-Xmx256m",
                    //Log4j async loggers hebben hele grote initiele ringbuffer size (256*1024), dit reserveert veel geheugen.
                    //Voor de e2e testen is niet veel nodig daarom gebruiken we hier de minimale setting.
                    "-DAsyncLogger.RingBufferSize=128",
                    "-DAsyncLoggerConfig.RingBufferSize=128"
                    //"\"-Djava.security.egd=file:/dev/./urandom\""
            }, " ");
            map.put("CATALINA_OPTS", javaOptsValue);
        }
        return map;
    }

    /**
     * Set met environmentvariabelen nodig zijn voor afhankelijke
     * componenten.
     * @return map met environment waarden.
     */
    protected Map<String, String> getEnvironmentVoorDependency() {
        return new HashMap<>();
    }

    /**
     * Hook voor voor het stoppen.
     */
    protected void beforeStop() {
    }

    /**
     * Hook voor na het stoppen.
     */
    protected void afterStop() {
    }

    protected List<String> geefOverigeRunCommandos() {
        return Lists.newLinkedList();
    }

    protected List<String> maakDockerExtraStartCommando() {
        return Lists.newArrayList();
    }

    protected List<String> maakDockerStartCommando() {
        final List<String> commands = omgeving.getDockerCommandList();
        commands.add("run");
        commands.add("-d");
        commands.add("--name");
        commands.add(getDockerInfo().logischeNaam() + "-" + omgeving.getNaam());

        //dummy logstash om error in log te voorkomen
        commands.add("--add-host");
        commands.add("logstash:127.0.0.1");

        //poorten
        if (!getPoortMap().isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : getPoortMap().entrySet()) {
                commands.add("-p");
                commands.add(String.format("%d:%d", entry.getValue(), entry.getKey()));
            }
        }

        //ENV
        final Map<String, String> omgevingsVariabelen = new HashMap<>(getEnvironment());
        for (DockerNaam dep : getDockerInfo().afhankelijkheden()) {
            final AbstractDocker component = omgeving.geefDocker(dep);
            omgevingsVariabelen.putAll(component.getEnvironmentVoorDependency());
        }

        if (!omgevingsVariabelen.isEmpty()) {
            for (Map.Entry<String, String> entry : omgevingsVariabelen.entrySet()) {
                commands.add("-e");
                commands.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
        }
        //volumes-from voor pure volumes
        for (String volumePath : geefDockerInfo().dynamicVolumes()) {
            commands.add("-v");
            commands.add(omgeving.getVolumeId(volumePath) + ":" + volumePath);
        }
        //volumes-from voor containers
        for (DockerNaam d : getDockerInfo().afhankelijkheden()) {
            final Docker docker = omgeving.geefDocker(d);
            if (docker.getDockerInfo().isVolume()) {
                commands.add("--volumes-from");
                commands.add(docker.getDockerContainerId());
            }
        }
        //mounts
        for (DockerMountingInfo mountingInfo : Arrays.asList(geefDockerInfo().mount())) {
            commands.add("-v");
            commands.add(Paths.get(mountingInfo.hostDir()).toAbsolutePath().toString() + ":" + mountingInfo.containerDir());
        }

        commands.addAll(geefOverigeRunCommandos());
        commands.add(geefVolledigeImageNaam());
        return commands;
    }

    protected String geefVolledigeImageNaam() {
        if (System.getProperty("DOCKER_REGISTRY") != null) {
            final String dockerRegistry = System.getProperty("DOCKER_REGISTRY");
            final String version = System.getProperty("APP_VERSION", "latest");
            return String.format("%s/%s:%s", dockerRegistry, getDockerInfo().image(), version);
        } else {
            return getDockerInfo().image();
        }
    }

    private DockerInfo geefDockerInfo() {
        final DockerInfo annotation = getClass().getAnnotation(DockerInfo.class);
        Assert.notNull(annotation, "Geen DockerImage annotatie gevonden");
        return annotation;
    }

    private int maakExternePoort(int internepoort) {
        return PoortManager.geefVrijePoort(Environment.gebruikPoortVoorkeur() ? geefPoortVoorkeur(internepoort) : null, this);
    }
}
