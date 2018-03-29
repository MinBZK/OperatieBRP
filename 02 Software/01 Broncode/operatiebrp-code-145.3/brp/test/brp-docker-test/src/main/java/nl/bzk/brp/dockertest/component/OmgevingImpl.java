/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.springframework.util.Assert;

/**
 * Implementatie van {@link Omgeving}
 */
final class OmgevingImpl implements Omgeving {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int OMGEVING_START_TIMEOUT_MSEC = 10 * 60 * 1000;

    private final Map<DockerNaam, Docker> dockerMap = Maps.newLinkedHashMap();
    private Map<String, String > volumeMap = Maps.newHashMap();
    private Status status = Status.INITIEEL;
    private long startTime;
    private String naam;
    private Dockerhost dockerhost;

    OmgevingImpl(final String naam, final Dockerhost dockerhost, final Iterable<Docker> dockers) {
        this.naam = naam;
        this.dockerhost = dockerhost;
        for (Docker docker : dockers) {
            dockerMap.put(docker.getLogischeNaam(), docker);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (status != Status.GESTOPT) {
                    Thread.currentThread().setName(naam);
                    LOGGER.info("JVM gestopt, probeer omgeving alsnog te stoppen");
                    OmgevingImpl.this.stop();
                }
            }
        });
    }

    @Override
    public String getDockerHostname() {
        return dockerhost.getHostname();
    }

    @Override
    public boolean bevat(final DockerNaam logischeNaam) {
        return dockerMap.containsKey(logischeNaam);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public String getVolumeId(String volumePath) {
        if (!volumeMap.containsKey(volumePath)) {
            throw new TestclientExceptie("Volume niet gevonden: " + volumePath);
        }
        return volumeMap.get(volumePath);
    }

    @Override
    public void start() throws InterruptedException {
        Thread.currentThread().setName(naam);
        if (status != Status.DEFINITIEF) {
            throw new TestclientExceptie("Alleen omgeving met status definitief kan gestart worden!");
        }

        startTime = System.currentTimeMillis();
        //clear de eventuele interrupted status
        Thread.interrupted();

        //start omgeving
        final CompletableFuture<Void> startOmgevingFuture = CompletableFuture.runAsync(this::startOmgeving);
        //start een thread om te controleren dat de omgeving binnen redelijke tijd gestart is.
        CompletableFuture.runAsync(() -> startBootMonitor(startOmgevingFuture));
        //wacht tot de omgeving gestart of gefaald is
        try {
            startOmgevingFuture.get();
        } catch (ExecutionException e) {
            stop();
            throw new TestclientExceptie(String.format("Het is niet gelukt de omgeving '%s' te starten", naam), e);
        }
    }

    private void startOmgeving() {
        Thread.currentThread().setName(naam);
        LOGGER.info("Omgeving wordt gestart");
        status = Status.START_SEQUENTIE;
        //check dockers en pre-start
        assertOmgevingCorrect();
        dockerMap.values().forEach(docker -> ((AbstractDocker)docker).setOmgeving(this));
        dockerMap.values().forEach(Docker::preStart);

        //bepaal aan te maken volumes
        dockerMap.values().stream()
                .map(docker -> Arrays.asList(docker.getDockerInfo().dynamicVolumes()))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .forEach(volumePath -> volumeMap.put(volumePath, ProcessHelper.DEFAULT.
                        startProces(getDockerCommandList("volume", "create")).geefOutput()));

        final Multimap<Integer, Docker> bootMap = HashMultimap.create();
        dockerMap.values().forEach(docker -> bootMap.put(docker.getDockerInfo().bootLevel(), docker));

        bootMap.keySet().stream().sorted().forEachOrdered(bootLevel -> {
            final Collection<Docker> dockers = bootMap.get(bootLevel);
            LOGGER.info("Start bootlevel {} met docker(s) {}", bootLevel, dockers);

            if (Environment.PARALLEL_START) {
                dockers.forEach(Docker::start);
                wachtTotFunctioneelBeschikbaar(dockers);
            } else {
                dockers.forEach(docker -> {
                    docker.start();
                    wachtTotFunctioneelBeschikbaar(Lists.newArrayList(docker));
                });
            }

            LOGGER.info("Einde bootlevel {}", bootLevel);
        });
        status = Status.FUNCTIONEEL;
        LOGGER.info("Omgeving functioneel in {} sec", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Override
    public void stop() {
        if (status.ordinal() < Status.START_SEQUENTIE.ordinal() ||
                status.ordinal() >= Status.STOP_SEQUENTIE.ordinal()) {
            return;
        }
        LOGGER.info("Omgeving wordt gestopt");
        status = Status.STOP_SEQUENTIE;

        dockerMap.values().stream().parallel().forEach(component -> {
            Thread.currentThread().setName(getNaam());
            try {
                LOGGER.info("Init stop Docker {}", component.getLogischeNaam());
                component.stop();
                LOGGER.info("Docker {} gestopt", component.getLogischeNaam());
            } catch (Exception e) {
                LOGGER.warn("Het is niet gelukt om Docker [{}] te stoppen", component.getLogischeNaam(), e);
            }
        });
        status = Status.GESTOPT;
        dockerhost.release();
        LOGGER.info("Omgeving gestopt");
    }

    @Override
    public <T extends Docker> T geefDocker(final DockerNaam logischeNaam) {
        for (final Docker docker : dockerMap.values()) {
            if (logischeNaam == docker.getLogischeNaam()) {
                return (T) docker;
            }
        }
        throw new OmgevingException(String.format("Docker met naam %s niet gevonden", logischeNaam));
    }

    @Override
    public Collection<Docker> geefDockers() {
        return dockerMap.values();
    }

    @Override
    public boolean isGestart() {
        return status == Status.FUNCTIONEEL;
    }

    @Override
    public boolean isGestopt() {
        return status == Status.GESTOPT;
    }

    Omgeving build() {
        if (status != Status.INITIEEL) {
            throw new IllegalStateException();
        }
        status = Status.DEFINITIEF;
        LOGGER.info("Omgeving definief gemaakt " + dockerMap.keySet());
        return this;
    }

    /**
     * Wacht tot alle Dockers functioneel beschikbaar zijn.
     */
    private void wachtTotFunctioneelBeschikbaar(Iterable<Docker> dockerList) {
        LOGGER.info("Start wacht tot omgeving functioneel beschikbaar is.");
        final List<Docker> dockers = Lists.newArrayList(dockerList);
        while (!dockers.isEmpty() && status == Status.START_SEQUENTIE) {
            final Iterator<Docker> iterator = dockers.iterator();
            while (iterator.hasNext()) {
                final Docker next = iterator.next();
                if (next.isFunctioneelBeschikbaar()) {
                    LOGGER.debug("Docker {} is beschikbaar...", next.getLogischeNaam());
                    iterator.remove();
                } else {
                    LOGGER.debug("Docker {} nog niet beschikbaar...", next.getLogischeNaam());
                }
            }
            if (!dockers.isEmpty()) {
                LOGGER.info("Docker(s) nog niet beschikbaar, even wachten...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new TestclientExceptie("Interrupt tijdens wachten functioneel beschikbaar");
                }
            }
        }
    }


    private void assertOmgevingCorrect() {
        LOGGER.info("Omgeving wordt gecontroleerd");
        //bepaal of omgeving compleet is
        for (Docker docker : dockerMap.values()) {
            for (DockerNaam afhankelijkeDocker : docker.getDockerInfo().afhankelijkheden()) {
                Assert.isTrue(dockerMap.containsKey(afhankelijkeDocker),
                        String.format("Omgeving is incompleet %s is afhankelijk van %s", docker.getLogischeNaam(), afhankelijkeDocker));
            }
        }
    }

    private void startBootMonitor(CompletableFuture<Void> completableFuture) {
        Thread.currentThread().setName(naam);
        LOGGER.info("Start Bootmonitor");
        while (!completableFuture.isDone()) {
            if (System.currentTimeMillis() - startTime > OMGEVING_START_TIMEOUT_MSEC) {
                completableFuture.completeExceptionally(new IllegalStateException("Starten van de omgeving duurt te lang"));
            } else {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            LOGGER.info("Booting...");
        }
        LOGGER.info("Stop Bootmonitor");
    }
}
