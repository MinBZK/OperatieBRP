/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import javax.inject.Inject;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.afnemerindicatie.OnderhoudAfnemerindicatiesWebServiceImpl;
import nl.bzk.brp.delivery.bevraging.BevragingWebService;
import nl.bzk.brp.delivery.stuf.StufWebService;
import nl.bzk.brp.delivery.synchronisatie.SynchronisatieWebServiceImpl;
import nl.bzk.brp.delivery.vrijbericht.VrijBerichtWebService;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.test.common.xml.XmlUtils;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.OinStubService;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;
import nl.bzk.brp.tooling.apitest.service.leveringalgemeen.LeverberichtStubService;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

/**
 * Service voor het uitvoeren van verzoeken op de API-services.
 */
final class VerzoekServiceImpl implements VerzoekService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final boolean VERZOEK_RESP_TO_FILE = Boolean.parseBoolean(System.getProperty("verzoek_resp_to_file", "true"));

    @Inject
    private StoryService storyService;
    @Inject
    private LeverberichtStubService leverberichtStubService;

    @Inject
    private SchemaValidatorService schemaValidatorService;
    @Inject
    private OinStubService oinStubService;

    private Antwoordbericht laatsteAntwoordbericht;
    private Verzoek laatsteVerzoek;
    private int verzoekIndex;

    @Override
    public String getLaatsteAntwoordbericht() {
        assertErIsEenAntwoordbericht();
        return laatsteAntwoordbericht.getXml();
    }

    @Override
    public Verzoek getLaatsteVerzoek() {
        Assert.notNull(laatsteVerzoek, "Er is geen laatste verzoek");
        return laatsteVerzoek;
    }

    @Override
    public void xmlVerzoek(final XmlVerzoek xmlVerzoek, final Provider<DOMSource> webserviceProvider, final SoortDienst soortDienst) {
        LOGGER.info(String.format("XMLVerzoek voor dienst: %s: ", soortDienst == null ? "Vrij bericht" : soortDienst.getNaam()));
        oinStubService.setOINs(new OIN(xmlVerzoek.getOndertekenaar(), xmlVerzoek.getTransporteur()));
        final DOMSource domSource = XmlUtils.resourceToDomSource(storyService.resolvePath(xmlVerzoek.getXmlPath()));
        final String antwoord = XmlUtils.domSourceToString(webserviceProvider.invoke(domSource));
        doLogVerzoek(soortDienst, antwoord);
    }

    @Override
    public void bewaarVerzoekAntwoord(final Verzoek verzoek, final String antwoord) {
        doLogVerzoek(verzoek.getSoortDienst(), antwoord);
        laatsteVerzoek = verzoek;

    }

    @Override
    public void resetStubState() {
        leverberichtStubService.reset();
        verzoekIndex = 0;
    }

    @Override
    public void reset() {
        laatsteVerzoek = null;
        laatsteAntwoordbericht = null;
    }

    private void doLogVerzoek(final SoortDienst soortDienst, final String antwoord) {
        if (!VERZOEK_RESP_TO_FILE) {
            return;
        }
        verzoekIndex++;

        final File verzoekDir = new File(storyService.getOutputDir(), "verzoeken");
        Assert.isTrue(verzoekDir.mkdirs() || verzoekDir.exists());
        final String fileNaam = String.format("%d-%s.xml", verzoekIndex, soortDienst == null ? "Vrij bericht" : soortDienst);
        final File verzoekAntwoordFile = new File(verzoekDir, fileNaam);
        try {
            try (final Writer writer = new PrintWriter(verzoekAntwoordFile)) {
                IOUtils.write(XmlUtils.format(antwoord), writer);
            }

            if (System.getProperty("ster") != null) {
                try (final Writer writer = new PrintWriter(new File(verzoekDir, fileNaam + ".ster.xml"))) {
                    IOUtils.write(XmlUtils.format(XmlUtils.ster2(antwoord)), writer);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Wegschrijven verzoek antwoord mislukt", e);
        }

        final Antwoordbericht antwoordbericht = new Antwoordbericht(antwoord, soortDienst);
        assertAntwoordberichtXsdValide(antwoordbericht);
        laatsteAntwoordbericht = antwoordbericht;
    }

    private void assertAntwoordberichtXsdValide(final Antwoordbericht antwoordbericht) {
        final Source xmlSource = new StreamSource(new StringReader(antwoordbericht.getXml()));
        if (antwoordbericht.getDienst() == null) {
            schemaValidatorService.valideer(xmlSource, VrijBerichtWebService.SCHEMA);
        } else {
            switch (antwoordbericht.getDienst()) {
                case GEEF_STUF_BG_BERICHT:
                    schemaValidatorService.valideer(xmlSource, StufWebService.SCHEMA);
                    break;
                case VERWIJDERING_AFNEMERINDICATIE:
                case PLAATSING_AFNEMERINDICATIE:
                    schemaValidatorService.valideer(xmlSource, OnderhoudAfnemerindicatiesWebServiceImpl.SCHEMA);
                    break;
                case GEEF_DETAILS_PERSOON:
                case ZOEK_PERSOON_OP_ADRESGEGEVENS:
                case ZOEK_PERSOON:
                case GEEF_MEDEBEWONERS_VAN_PERSOON:
                    schemaValidatorService.valideer(xmlSource, BevragingWebService.SCHEMA);
                    break;
                case SYNCHRONISATIE_PERSOON:
                case SYNCHRONISATIE_STAMGEGEVEN:
                    schemaValidatorService.valideer(xmlSource, SynchronisatieWebServiceImpl.SCHEMA);
                    break;

                default:
                    throw new IllegalStateException("Kan antwoordbericht niet valideren voor dienst: " + antwoordbericht.getDienst());

            }
        }
    }

    private void assertErIsEenAntwoordbericht() {
        Assert.notNull(laatsteAntwoordbericht, "Er is geen antwoordbericht");
    }

    /**
     * Dit object bevat het antwoordbericht.
     */
    private static final class Antwoordbericht {

        private final String xml;
        private final SoortDienst dienst;

        private Antwoordbericht(final String xml, final SoortDienst dienst) {
            this.xml = xml;
            this.dienst = dienst;
        }

        public String getXml() {
            return xml;
        }

        public SoortDienst getDienst() {
            return dienst;
        }
    }
}
