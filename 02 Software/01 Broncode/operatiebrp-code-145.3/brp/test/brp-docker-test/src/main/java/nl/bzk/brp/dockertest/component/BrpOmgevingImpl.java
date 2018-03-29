/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.Collection;
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
import nl.bzk.brp.test.common.xml.XPathHelper;

/**
 * Implementatie van {@link BrpOmgeving}
 */
class BrpOmgevingImpl implements BrpOmgeving {

    private final Omgeving omgeving;
    private final VerzoekService verzoekService = new VerzoekService(this);
    private final SelectieService selectieService = new SelectieService(this);
    private final BeheerSelectieService beheerSelectieService = new BeheerSelectieService(this);
    private final AlgemeenService algemeenService = new AlgemeenService(this);
    private final AutorisatieHelper autorisatieHelper = new AutorisatieHelper(this);
    private final AsynchroonberichtHelper asynchroonberichtHelper = new AsynchroonberichtHelper(this);
    private final TestdataHelper testdataHelper = new TestdataHelper(this);
    private ArchiveringAsserts archiveringAsserts;
    private final XPathHelper xPathHelper = new XPathHelper();

    BrpOmgevingImpl(final Omgeving omgeving) {
        this.omgeving = omgeving;
    }

    @Override
    public String getVolumeId(final String volumePath) {
        return omgeving.getVolumeId(volumePath);
    }

    @Override
    public void start() throws InterruptedException {
        omgeving.start();
    }

    @Override
    public void stop() {
        omgeving.stop();
    }

    @Override
    public String getDockerHostname() {
        return omgeving.getDockerHostname();
    }

    @Override
    public boolean bevat(final DockerNaam logischeNaam) {
        return omgeving.bevat(logischeNaam);
    }

    @Override
    public <T extends Docker> T geefDocker(final DockerNaam logischeNaam) {
        return omgeving.geefDocker(logischeNaam);
    }

    @Override
    public Collection<Docker> geefDockers() {
        return omgeving.geefDockers();
    }

    @Override
    public boolean isGestart() {
        return omgeving.isGestart();
    }

    @Override
    public boolean isGestopt() {
        return omgeving.isGestopt();
    }

    @Override
    public Status getStatus() {
        return omgeving.getStatus();
    }

    @Override
    public String getNaam() {
        return omgeving.getNaam();
    }

    @Override
    public DatabaseDocker brpDatabase() {
        return geefDocker(DockerNaam.BRPDB);
    }

    @Override
    public DatabaseDocker selectieDatabase() {
        return geefDocker(DockerNaam.SELECTIEBLOB_DATABASE);
    }

    @Override
    public DatabaseDocker archiveringDatabase() {
        return geefDocker(DockerNaam.ARCHIVERINGDB);
    }

    @Override
    public DatabaseDocker protocolleringDatabase() {
        return geefDocker(DockerNaam.PROTOCOLLERINGDB);
    }

    @Override
    public CacheHelper cache() {
        return new CacheHelper(omgeving);
    }

    @Override
    public AutorisatieHelper autorisaties() {
        return autorisatieHelper;
    }

    @Override
    public VerzoekService verzoekService() {
        return verzoekService;
    }

    @Override
    public SelectieService selectieService() {
        return selectieService;
    }

    @Override
    public BeheerSelectieService beheerSelectieService() {
        return beheerSelectieService;
    }

    @Override
    public AlgemeenService algemeenService() {
        return algemeenService;
    }

    @Override
    public ArchiveringAsserts archivering() {
        if (archiveringAsserts == null) {
            archiveringAsserts = new ArchiveringAsserts(this);
        }
        return archiveringAsserts;
    }

    @Override
    public AfnemerindicatieAsserts afnemerindicatie() {
        return new AfnemerindicatieAsserts(this);
    }

    @Override
    public TestdataHelper testdata() {
        return testdataHelper;
    }

    @Override
    public AsynchroonberichtHelper asynchroonBericht() {
        return asynchroonberichtHelper;
    }

    @Override
    public ProtocolleringHelper protocollering() {
        return new ProtocolleringHelper(this);
    }

    @Override
    public JmsDocker routering() {
        return (JmsDocker) geefDocker(DockerNaam.ROUTERINGCENTRALE);
    }

    @Override
    public XPathHelper getxPathHelper() {
        return xPathHelper;
    }
}
