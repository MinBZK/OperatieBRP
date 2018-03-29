/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.vrijbericht;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.test.common.xml.XmlUtils;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.BeheerRepositoryStub;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 */
@Service
class VrijBerichtControleServiceImpl implements VrijBerichtControleService {

    @Inject
    private VrijBerichtStubService vrijBerichtStubService;
    @Inject
    private BeheerRepositoryStub beheerRepositoryStub;
    @Inject
    private StoryService storyService;

    @Override
    public void assertErIsEenVrijBerichtVoorPartijVerstuurdNaarAfleverpunt(final String partij, final String afleverpunt) {
        Assert.isTrue(vrijBerichtStubService.heeftVrijBerichtOntvangen(), "Geen vrij bericht ontvangen.");
        final VrijBerichtGegevens vrijBerichtGegevens = vrijBerichtStubService.geefVrijBericht();
        Assert.isTrue(partij.equals(String.valueOf(vrijBerichtGegevens.getPartij().getCode())),
                String.format("Partij klopt niet, was %s.", vrijBerichtGegevens.getPartij().getCode()));
        Assert.isTrue(afleverpunt.equals(vrijBerichtGegevens.getBrpEndpointURI()),
                String.format("Afleverpunt klopt niet, was %s.", vrijBerichtGegevens.getBrpEndpointURI()));
    }


    @Override
    public void assertErIsGeenVrijBerichtVerzonden() {
        Assert.isTrue(!vrijBerichtStubService.heeftVrijBerichtOntvangen(), "Er is een vrij bericht ontvangen, terwijl dit niet werd verwacht.");
    }

    @Override
    public void assertIsVerstuurdVrijBerichtGelijkAan(final String bestand) throws Exception {
        final String expectedXml = geefExpectedXml(bestand);
        final File outputDir = storyService.getOutputDir();
        outputDir.mkdirs();
        final String partijCode = vrijBerichtStubService.geefVrijBericht().getPartij().getCode();
        final String xml = vrijBerichtStubService.geefVrijBericht().getArchiveringOpdracht().getData();
        try (final OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(new File(outputDir, String.format("VB-%s-EXPECTED.xml", partijCode))), StandardCharsets.UTF_8)) {
            IOUtils.write(expectedXml, osw);
        }
        try (final FileOutputStream fos = new FileOutputStream(new File(outputDir, String.format("VB-%s-ACTUAL.xml", partijCode)))) {
            IOUtils.write(XmlUtils.format(xml), fos, StandardCharsets.UTF_8);
        }
        XmlUtils.assertGelijk(null, expectedXml, XmlUtils.ster(xml));
    }

    @Override
    public void assertVrijBerichtCorrectOpgeslagen(final String zendendePartijCode, final String soortNaam, final String inhoud) throws Exception {
        Assert.isTrue(beheerRepositoryStub.isVrijBerichtGepersisteerd(), "Geen vrij bericht opgeslagen.");

        boolean berichtGevonden = false;
        for (final VrijBericht vrijBericht : beheerRepositoryStub.geefVrijeBerichten()) {
            Assert.isTrue(!vrijBericht.getVrijBerichtPartijen().isEmpty());
            final String zendendePartijCodeDb =  String.valueOf(vrijBericht.getVrijBerichtPartijen().get(0).getPartij().getCode());
            if (soortNaam.equals(vrijBericht.getSoortVrijBericht().getNaam())
                    && inhoud.equals(vrijBericht.getData()) && zendendePartijCode.equals(zendendePartijCodeDb)) {
                berichtGevonden = true;
                break;
            }
        }
        Assert.isTrue(berichtGevonden, "Geen opgeslagen vrij bericht gevonden met de opgegeven soortnaam en inhoud.");
    }

    private String geefExpectedXml(final String bestand) throws IOException {
        final Resource resource = storyService.resolvePath(bestand);
        final String expectedXml;
        try (final InputStream inputStream = resource.getInputStream()) {
            expectedXml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        return expectedXml;
    }

    /**
     * reset de state
     */
    @Override
    public void reset() {
        beheerRepositoryStub.reset();
        vrijBerichtStubService.reset();
    }
}
