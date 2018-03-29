/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Dockercomponent voor Mutatielevering.
 */
@DockerInfo(
        image = "brp/mutatielevering",
        logischeNaam = DockerNaam.MUTATIELEVERING,
        afhankelijkheden = {DockerNaam.BRPDB, DockerNaam.ROUTERINGCENTRALE},
        internePoorten = {Poorten.JMX_POORT}
)
final class MutatieleveringDocker extends AbstractDocker implements LogfileAware, CacheSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar()
                && isGestartVolgensLog();
    }

    /**
     * Set met environmentvariabelen welke specifiek voor dit component nodig zijn.
     * @return map met environmentwaardenn
     */
    protected Map<String, String> getEnvironment() {
        final Map<String, String> map = Maps.newHashMap();
        map.put("DOCKER_IP", getOmgeving().getDockerHostname());
        if (Environment.isDebuggerEnabled()) {
            map.put("JAVA_OPTS", String.format("-agentlib:jdwp=transport=dt_socket,address=%d,server=y,suspend=n", Poorten.DEBUG_POORT));
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
            map.put("JAVA_OPTS", javaOptsValue);
        }
        return map;
    }

    private boolean isGestartVolgensLog() {
        try {
            final List<String> commandList = getOmgeving().getDockerCommandList("exec",
                    getDockerContainerId(), "tail", "-n", "100", "logs/systeem.log");
            final String output = ProcessHelper.DEFAULT.startProces(commandList).geefOutput();
            return output.contains("Applicatie gestart");
        } catch (AbnormalProcessTerminationException e) {
            LOGGER.warn("Logfile bestaat nog niet? ", e);
            return false;
        }
    }

    @Override
    public String getJmxDomain() {
        return "levering";
    }
}
