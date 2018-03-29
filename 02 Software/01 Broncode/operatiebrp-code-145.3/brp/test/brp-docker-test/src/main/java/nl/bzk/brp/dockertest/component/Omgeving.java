/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Interface om een omgeving te starten en te stoppen.
 */
public interface Omgeving {

    String getVolumeId(String volumePath);

    /**
     * Start de omgeving
     */
    void start() throws InterruptedException;

    /**
     * Stop de omgeving
     */
    void stop();

    /**
     *
     *
     * @return
     */
    String getDockerHostname();

    /**
     * @param logischeNaam logische naam van het component
     * @return indicatie of de omgeving een component met de gegeven naam bevat
     */
    boolean bevat(DockerNaam logischeNaam);

    /**
     * Geeft de docker met de gegeven logische naam.
     *
     * @param logischeNaam naam van de docker
     * @return de docker indien gevonden
     * @throws OmgevingException indien de docker niet bestaat
     */
    <T extends Docker> T geefDocker(DockerNaam logischeNaam);

    /**
     * Geeft dockers welke voldoen aan het opgegeven type
     * @return alle componenten in deze omgeving
     */
    Collection<Docker> geefDockers();

    /**
     * @return indicatie of de omgeving is gestart
     */
    boolean isGestart();

    /**
     * @return indicatie of de omgeving is gestopt
     */
    boolean isGestopt();

    /**
     * @return de status van de omgeving
     */
    Status getStatus();

    /**
     * @return de logische naam van de omgeving
     */
    String getNaam();


    default List<String> getDockerCommandList(String ... commands) {
        final List<String> list = Environment.isJenkinsRun()
                ? Lists.newArrayList("docker", "-H", getDockerHostname())
                : Lists.newArrayList("docker");
        list.addAll(Arrays.asList(commands));
        return list;
    }

    /**
     * Mogelijke status van de omgeving.
     */
    enum Status {

        /**
         * De omgeving wordt nog bepaald.
         */
        INITIEEL,
        /**
         * De omgeving is definitief bepaald.
         */
        DEFINITIEF,
        /**
         * De start sequentie is begonnen, de componenten worden gestart.
         */
        START_SEQUENTIE,
        /**
         * Alle componenten zijn gestart en functioneel beschikbaar.
         */
        FUNCTIONEEL,
        /**
         * De stopsequentie is ingezet, de componenten worden gestopt.
         */
        STOP_SEQUENTIE,
        /**
         * Alle componenten zijn gestopt.
         */
        GESTOPT;
    }
}
