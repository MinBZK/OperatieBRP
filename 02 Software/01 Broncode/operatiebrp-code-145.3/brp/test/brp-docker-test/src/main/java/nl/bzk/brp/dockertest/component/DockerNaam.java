/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

/**
 * De naam die gebruikt wordt in de Docker properties te
 * verwijzen naar andere componenten.
 */
public enum DockerNaam {

    /**
     * Naam voor beheer.
     */
    BEHEER(BeheerDocker.class),
    /**
     * Naam voor beheer selectie.
     */
    BEHEER_SELECTIE(BeheerSelectieDocker.class),
    /**
     * Naam voor beheer selectie frontend.
     */
    BEHEER_SELECTIE_FRONTEND(BeheerSelectieFrontendDocker.class),
    /**
     * Naam voor bevraging.
     */
    BEVRAGING(BevragingDocker.class),

    /**
     * Naam voor stuf.
     */
    STUF(StufDocker.class),

    /**
     * Naam voor de routeringcentrale.
     */
    ROUTERINGCENTRALE(RouteringcentraleDocker.class),
    /**
     * Naam voor de selectie routeringcentrale.
     */
    SELECTIE_ROUTERINGCENTRALE(SelectieRouteringCentraleDocker.class),
    /**
     * Naam voor selectie.
     */
    SELECTIE(SelectieDocker.class),
    /**
     * Naam voor de selectie volume
     */
    SELECTIE_VOLUME(SelectieVolumeDocker.class),
    /**
     * Naam voor selectie schrijver.
     */
    SELECTIE_SCHRIJVER(SelectieSchrijverDocker.class),
    /**
     * Naam voor selectie afnemerindicatie.
     */
    SELECTIE_AFNEMERINDICATIE(SelectieAfnemerindicatieDocker.class),
    /**
     * Naam voor selectie verwerker.
     */
    SELECTIE_VERWERKER(SelectieVerwerkerDocker.class),
    /**
     * Naam voor selectie protocollering.
     */
    SELECTIE_PROTOCOLLERING(SelectieProtocolleringDocker.class),
    /**
     * Naam voor de database.
     */
    BRPDB(BrpDatabaseDocker.class),
    /**
     * Naam voor de selectie database.
     */
    SELECTIEBLOB_DATABASE(SelectieBlobDatabaseDocker.class),
    /**
     * Naam voor de afnemervoorbeeld database.
     */
    AFNEMERVOORBEELDDB(AfnemervoorbeeldDatabaseDocker.class),
    /**
     * Naam voor de protocollering database.
     */
    PROTOCOLLERINGDB(ProtocolleringDatabaseDocker.class),
    /**
     * Naam voor de archivering database.
     */
    ARCHIVERINGDB(ArchiveringDatabaseDocker.class),
    /**
     * Naam voor de routeringcentrale database.
     */
    ACTIVEMQDB(RouteringcentraleDatabaseDocker.class),
    /**
     * Naam voor mutatielevering.
     */
    MUTATIELEVERING(MutatieleveringDocker.class),
    /**
     * Naam voor verzending.
     */
    VERZENDING(VerzendingDocker.class),
    /**
     * Naam voor het afnemervoorbeeld.
     */
    AFNEMERVOORBEELD(AfnemerVoorbeeldDocker.class),
    /**
     * Naam voor syncronisatie.
     */
    SYNCHRONISATIE(SynchronisatieDocker.class),
    /**
     * Naam voor onderhoudafnemerindicaties.
     */
    ONDERHOUDAFNEMERINDICATIES(AfnemerindicatieDocker.class),
    /**
     * Naam voor sleutelbos.
     */
    SLEUTELBOS(SleutelbosDocker.class),
    /**
     * Naam voor vrijbericht.
     */
    VRIJBERICHT(VrijberichtDocker.class),
    /**
     * Naam voor bijhouding.
     */
    BIJHOUDING(BijhoudingDocker.class),
    /**
     * Naam voor Administratievehandelingpublicatie
     */
    AH_PUBLICATIE(AdministratievehandelingPublicatieDocker.class);

    private final Class<? extends AbstractDocker> dockerImpl;

    DockerNaam(final Class<? extends AbstractDocker> dockerImpl) {
        this.dockerImpl = dockerImpl;
    }

    public Class<? extends AbstractDocker> getDockerImpl() {
        return dockerImpl;
    }
}
