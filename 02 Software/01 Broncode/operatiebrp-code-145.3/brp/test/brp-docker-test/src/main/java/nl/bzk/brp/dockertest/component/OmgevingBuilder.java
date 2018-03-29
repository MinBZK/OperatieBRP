/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Supplier;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.junit.Assert;

/**
 * Helper klasse om een omgeving op te bouwen.
 */
public final class OmgevingBuilder {

    private static final Map<DockerNaam, Supplier<Docker>> FACTORYMAP = Maps.newHashMap();

    static {
        for (DockerNaam dockerNaam : DockerNaam.values()) {
            final Supplier<Docker> supplier = () -> {
                try {
                    return dockerNaam.getDockerImpl().newInstance();
                } catch (Exception e) {
                    throw new TestclientExceptie("Kan Docker niet instantieren: " + dockerNaam.getDockerImpl().getName());
                }
            };
            FACTORYMAP.put(dockerNaam.getDockerImpl().getAnnotation(DockerInfo.class).logischeNaam(), supplier);
        }
    }

    private Map<DockerNaam, Docker> buildMap = Maps.newHashMap();
    private String naam;

    public OmgevingBuilder() {
    }

    /**
     * Bouw de {@link BrpOmgeving}.
     * @return de {@link BrpOmgeving}
     */
    public BrpOmgeving build() {
        //maak de naam uniek; je kunt geen container starten als er reeds een beeindigde container met dezelfde naam bestaat
        final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uMdHms"));
        final String uniekeNaam = time + "-" + naam;
        final Dockerhost dockerhost = Environment.bepaalDockerhost();
        final OmgevingImpl omgeving = new OmgevingImpl(uniekeNaam, dockerhost, buildMap.values());
        return new BrpOmgevingImpl(omgeving.build());
    }

    /**
     * Zet de logische naam van de omgeving.
     * @param naamOmgeving een naam.
     * @return deze {@link OmgevingBuilder}
     */
    public OmgevingBuilder metNaam(String naamOmgeving) {
        this.naam = naamOmgeving;
        return this;
    }

    /**
     * Maakt een {@link BrpOmgeving} met de gegeven top-level dockers.
     * Afhankelijkheden worden automatisch geresolved.
     * @param dockers lijst met dockers
     * @return een {@link BrpOmgeving}
     */
    public OmgevingBuilder metTopLevelDockers(DockerNaam... dockers) {
        for (DockerNaam docker : dockers) {
            addDocker(docker);
        }
        return this;
    }

    /**
     * Voegt de gegeven docker toe aan de omgeving.
     *
     * @param dockerNaam naam van de docker
     * @return deze omgeving builder
     */
    public OmgevingBuilder addDocker(DockerNaam dockerNaam) {
        Assert.assertTrue("Docker bestaat niet: " + dockerNaam, FACTORYMAP.containsKey(dockerNaam));
        final Docker computedDocker = buildMap.computeIfAbsent(dockerNaam, dockerNaam2 -> FACTORYMAP.get(dockerNaam2).get());
        for (DockerNaam naam : computedDocker.getDockerInfo().afhankelijkheden()) {
            addDocker(naam);
        }
        return this;
    }
}
