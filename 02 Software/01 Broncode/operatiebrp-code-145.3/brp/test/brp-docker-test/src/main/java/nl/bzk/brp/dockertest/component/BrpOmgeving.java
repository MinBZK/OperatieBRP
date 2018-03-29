/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import nl.bzk.brp.dockertest.service.AfnemerindicatieAsserts;
import nl.bzk.brp.dockertest.service.AlgemeenService;
import nl.bzk.brp.dockertest.service.ArchiveringAsserts;
import nl.bzk.brp.dockertest.service.AsynchroonberichtHelper;
import nl.bzk.brp.dockertest.service.AutorisatieHelper;
import nl.bzk.brp.dockertest.service.BeheerSelectieService;
import nl.bzk.brp.dockertest.service.CacheHelper;
import nl.bzk.brp.dockertest.service.ProtocolleringHelper;
import nl.bzk.brp.dockertest.service.SelectieService;
import nl.bzk.brp.dockertest.service.TestdataHelper;
import nl.bzk.brp.dockertest.service.VerzoekService;
import nl.bzk.brp.dockertest.service.gbasync.PersoonUitExcelHelper;
import nl.bzk.brp.test.common.xml.XPathHelper;

/**
 * Interface om specifieke BRP componenten te benaderen.
 */
public interface BrpOmgeving extends Omgeving {

    /**
     * Geeft het {@link DockerNaam#BRPDB} Component.
     * @return een {@link DatabaseDocker}
     */
    DatabaseDocker brpDatabase();

    /**
     * Geeft het {@link DockerNaam#SELECTIEBLOB_DATABASE} Component.
     * @return een {@link DatabaseDocker}
     */
    DatabaseDocker selectieDatabase();

    /**
     * Geeft het {@link DockerNaam#ARCHIVERINGDB} Component.
     * @return een {@link DatabaseDocker}
     */
    DatabaseDocker archiveringDatabase();

    /**
     * Geeft het {@link DockerNaam#PROTOCOLLERINGDB} Component.
     * @return een {@link DatabaseDocker}
     */
    DatabaseDocker protocolleringDatabase();

    /**
     * Geeft het {@link DockerNaam#ROUTERINGCENTRALE} Component.
     * @return een {@link JmsDocker}
     */
    JmsDocker routering();

    /**
     * Geeft de helper voor het verversen van de caches.
     * @return een {@link CacheHelper}
     */
    CacheHelper cache();

    /**
     * Geeft de helper voor opvoeren van autorisaties.
     * @return een {@link AutorisatieHelper}
     */
    AutorisatieHelper autorisaties();

    /**
     * Geeft de helper voor opvoeren van testdata.
     * @return een {@link PersoonUitExcelHelper}
     */
    TestdataHelper testdata();

    /**
     * Geeft de helper voor uitvoeren van verzoeken.
     * @return een {@link VerzoekService}
     */
    VerzoekService verzoekService();

    /**
     * Geeft de helper voor uitvoeren van selectie operaties.
     * @return een {@link SelectieService}
     */
    SelectieService selectieService();

    /**
     * Geeft de helper voor uitvoeren van selectie operaties in de beheerapplicatie.
     * @return een {@link BeheerSelectieService}
     */
    BeheerSelectieService beheerSelectieService();

    /**
     * Geeft de helper voor het doen van algemene zaken.
     * @return de {@link AlgemeenService}
     */
    AlgemeenService algemeenService();

    /**
     * Geeft de helper voor doen van asserts mbt archivering.
     * @return een {@link ArchiveringAsserts}
     */
    ArchiveringAsserts archivering();

    /**
     * Geeft de helper voor doen van asserts mbt afnemerindicaties.
     * @return een {@link AfnemerindicatieAsserts}
     */
    AfnemerindicatieAsserts afnemerindicatie();

    /**
     * Geeft de helper voor asynchrone berichten.
     * @return een {@link AsynchroonberichtHelper}
     */
    AsynchroonberichtHelper asynchroonBericht();

    /**
     * Geeft de helper voor doen van asserts mbt protocollering.
     * @return een {@link ProtocolleringHelper}
     *
     * @return een {@link ProtocolleringHelper}
     */
    ProtocolleringHelper protocollering();

    XPathHelper getxPathHelper();
}
