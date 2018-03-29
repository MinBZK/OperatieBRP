/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import javax.inject.Inject;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.delivery.bevraging.BevragingWebService;
import nl.bzk.brp.delivery.synchronisatie.SynchronisatieWebServiceImpl;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.test.common.xml.XmlUtils;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;
import nl.bzk.brp.tooling.apitest.dto.OntvangenBericht;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

/**
 * Stub klasse voor JmsService.
 */
final class LeverberichtStubServiceImpl implements PlaatsAfnemerBerichtService, LeverberichtStubService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final boolean SCHRIJF_ASYNC_BERICHT_NAAR_FILE = Boolean
            .parseBoolean(System.getProperty("schrijf_async_bericht_naar_file", "true"));
    private static final String STER = "*";
    private final List<OntvangenBericht> ontvangenBerichten = Lists.newLinkedList();
    private OntvangenBericht laatstBekekenBericht;

    @Inject
    private StoryService storyService;
    @Inject
    private SchemaValidatorService schemaValidatorService;


    @Override
    public void plaatsAfnemerberichten(final List<AfnemerBericht> afnemerBerichten) {
        if (!SCHRIJF_ASYNC_BERICHT_NAAR_FILE) {
            return;
        }

        for (AfnemerBericht afnemerBericht : afnemerBerichten) {
            final String uitgaandeXmlBericht = afnemerBericht.getSynchronisatieBerichtGegevens().getArchiveringOpdracht().getData();
            final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = afnemerBericht.getToegangLeveringsAutorisatie();
            final OntvangenBericht ontvangenBericht = new OntvangenBericht(uitgaandeXmlBericht, afnemerBericht.getSynchronisatieBerichtGegevens(),
                    toegangLeveringsautorisatie);
            try {
                assertBerichtXsdValide(ontvangenBericht);
            } catch (SchemaValidatorService.SchemaValidatieException se) {
                LOGGER.error("Bericht niet XSD valide:\n{}", uitgaandeXmlBericht);
                throw new RuntimeException(se);
            }
            ontvangenBerichten.add(ontvangenBericht);

            final File asynDir = new File(storyService.getOutputDir(), "leveringen");
            Assert.isTrue(asynDir.exists() || asynDir.mkdirs());

            final String fileNaam = String.format("%d-%s-voor-autorisatie-%s-en-%s.xml", ontvangenBerichten.size(),
                    afnemerBericht.getSynchronisatieBerichtGegevens().getProtocolleringOpdracht().getSoortSynchronisatie().getNaam(),
                    toegangLeveringsautorisatie.getLeveringsautorisatie().getNaam(),
                    toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getNaam().replaceAll("/", "_"));
            try {
                try (final Writer writer = new PrintWriter(new File(asynDir, fileNaam))) {
                    IOUtils.write(XmlUtils.format(uitgaandeXmlBericht), writer);
                }
                if (System.getProperty("ster") != null) {
                    try (final Writer writer = new PrintWriter(new File(asynDir, fileNaam + ".ster.xml"))) {
                        IOUtils.write(XmlUtils.format(XmlUtils.ster2(uitgaandeXmlBericht)), writer);
                    }
                }
            } catch (IOException e) {
                throw new BrpServiceRuntimeException("Wegschrijven leverbericht mislukt", e);
            }
        }
    }

    @Override
    public void assertBerichtIsOntvangen(final String leveringsautorisatieNaam,
                                         final SoortSynchronisatie soortSynchronisatie) {

        assertBerichtIsOntvangen(leveringsautorisatieNaam, soortSynchronisatie, STER);
    }

    @Override
    public void assertBerichtIsOntvangen(final String leveringsautorisatieNaam, final SoortSynchronisatie soortSynchronisatie,
                                         final String afnemerPartijNaam) {
        Assert.notNull(leveringsautorisatieNaam, "leveringsautorisatieNaam is leeg");
        Assert.notNull(afnemerPartijNaam, "afnemerPartijNaam leeg");

        if (ontvangenBerichten.isEmpty()) {
            throw new AssertionError("Geen berichten ontvangen");
        }

        Partij afnemerPartij = null;
        if (!STER.equals(afnemerPartijNaam)) {
            afnemerPartij = Iterables.find(Partijen.getPartijen(), partij -> partij.getNaam().equals(afnemerPartijNaam));
        }

        laatstBekekenBericht = null;
        for (final OntvangenBericht ontvangenBericht : ontvangenBerichten) {

            boolean gevonden = leveringsautorisatieNaam.equals(ontvangenBericht.getToegangLeveringsautorisatie().getLeveringsautorisatie().getNaam());
            gevonden &= soortSynchronisatie == ontvangenBericht.getMetaGegevens().getProtocolleringOpdracht().getSoortSynchronisatie();
            gevonden &= afnemerPartij == null || afnemerPartij.getId().equals(ontvangenBericht.getMetaGegevens().getArchiveringOpdracht()
                    .getOntvangendePartijId());

            if (gevonden) {
                laatstBekekenBericht = ontvangenBericht;
                break;
            }
        }
        if (laatstBekekenBericht == null) {
            throw new AssertionError("Bericht niet gevonden");
        }


    }

    @Override
    public String getLaatstBekekenBericht() {
        assertErIsEenBerichtBekeken();
        return laatstBekekenBericht.getUitgaandeXmlBericht();
    }

    @Override
    public List<OntvangenBericht> getOntvangenBerichten() {
        return Lists.newArrayList(ontvangenBerichten);
    }


    @Override
    public void assertGeenLevering(final String leveringsautorisatieNaam) {
        Assert.notNull(leveringsautorisatieNaam, "leveringsautorisatieNaam leeg");
        for (final OntvangenBericht verstuurdBericht : ontvangenBerichten) {
            Assert.isTrue(!leveringsautorisatieNaam.equals(verstuurdBericht.getToegangLeveringsautorisatie().getLeveringsautorisatie().getNaam()),
                    String.format("Onverwacht bericht ontvangen voor leveringautorisatie: %s", leveringsautorisatieNaam));
        }
    }

    @Override
    public void assertAantalOntvangenBerichten(final int count) {
        Assert
                .isTrue(count == ontvangenBerichten.size(),
                        String.format("Afwijkend aantal berichten ontvangen: verwacht [%d] ontvangen [%d]", count, ontvangenBerichten.size()));
    }

    @Override
    public void reset() {
        ontvangenBerichten.clear();
        laatstBekekenBericht = null;
    }

    private void assertBerichtXsdValide(final OntvangenBericht bericht) {
        final Source xmlSource = new StreamSource(new StringReader(bericht.getUitgaandeXmlBericht()));
        switch (bericht.getMetaGegevens().getSoortDienst()) {
            case ATTENDERING:
            case SYNCHRONISATIE_PERSOON:
            case MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE:
            case MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING:
            case PLAATSING_AFNEMERINDICATIE:
            case SELECTIE:
                schemaValidatorService.valideer(xmlSource, SynchronisatieWebServiceImpl.SCHEMA);
                break;
            case GEEF_DETAILS_PERSOON:
                schemaValidatorService.valideer(xmlSource, BevragingWebService.SCHEMA);
                break;
            default:
                throw new BrpServiceRuntimeException("Kan XSD niet valideren voor dienst: " + bericht.getMetaGegevens().getSoortDienst());
        }
    }

    private void assertErIsEenBerichtBekeken() {
        Assert.notNull(laatstBekekenBericht, "Er is geen bericht klaargezet voor de vergelijking");
    }

}
