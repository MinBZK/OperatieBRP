/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotatie voor een Docker component.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DockerInfo {

    int DEFAULT_BOOTLEVEL = 100;

    /**
     * @return imagenaam van de Docker
     */
    String image();

    /**
     * @return de versie van de docker images.
     */
    String version() default "latest";

    /**
     * @return de logische naam van de Docker
     */
    DockerNaam logischeNaam();

    /**
     * @return afhandekelijkheden van de Docker op ander Dockers
     */
    DockerNaam[] afhankelijkheden() default {};

    /**
     * Deze waarde bepaalt de volgorde van opstarten binnen de omgeving.
     * Componenten met gelijk bootLevel worden gelijk gestart. Componenten
     * met lager bootlevel worden eerder gestart.
     * @return indicatie of het component parallel gestart kan worden
     */
    int bootLevel() default DEFAULT_BOOTLEVEL;

    /**
     * @return array van poorten welke intern geopend zijn
     */
    int[] internePoorten() default {};

    /**
     * @return indicatie of de container als volume gezien dient te worden.
     */
    boolean isVolume() default false;

    /**
     * @return array van volume paths die in de docker beschikbaar moeten komen.
     */
    String[] dynamicVolumes() default {};

    /**
     * Informatie mbt mounting (hostdir & containerdir)
     * @return {@link DockerMountingInfo}
     */
    DockerMountingInfo[] mount() default {};
}
