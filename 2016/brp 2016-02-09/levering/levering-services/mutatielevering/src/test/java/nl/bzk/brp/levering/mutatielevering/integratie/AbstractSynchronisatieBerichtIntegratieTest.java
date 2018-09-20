/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.integratie;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.bericht.MarshallService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.expressietaal.ExpressiesTestDataOphaler;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.KindHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.custommonkey.xmlunit.NamespaceContext;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import support.LoggingErrorHandler;
import support.ResourceResolver;

public abstract class AbstractSynchronisatieBerichtIntegratieTest extends AbstractIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Inject
    private BerichtFactory berichtFactory;

    @Inject
    private MarshallService marshallService;

    @Inject
    private BlobifierService blobifierService;

    @Inject
    private ExpressieService expressieService;

    @Inject
    private AttributenFilterService attributenFilterService;

    @Inject
    private ExpressiesTestDataOphaler expressiesTestDataOphaler;

    @Before
    public final void zorgVoorBlobsInDatabase() throws SQLException {
        for (final Integer persoonId : Arrays.asList(1, 46, 47, 48)) {
            blobifierService.blobify(persoonId, false);
        }

        final Connection connectie = DataSourceUtils.getConnection(getDataSource());
        connectie.setAutoCommit(false);
        connectie.commit();
    }

//    @After
//    public final void tearDown() throws SQLException {
//        DataSourceUtils.getConnection(getDataSource()).close();
//    }

    protected List<PersoonHisVolledigView> getTestPersonen() {
        return getTestPersonen(1, 46);
    }

    protected List<PersoonHisVolledigView> getTestPersonen(final int... persoonIds) {
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();

        for (final int persoonId : persoonIds) {
            final PersoonHisVolledig persoonHisVolledig = blobifierService.leesBlob(persoonId);
            bijgehoudenPersonen.add(new PersoonHisVolledigView(persoonHisVolledig, null));
        }

        return bijgehoudenPersonen;
    }

    protected void zetAlleAttributenOpMagGeleverdWorden(final SynchronisatieBericht leveringBericht) throws ExpressieExceptie {
        final Dienst dienst = em.find(Dienst.class, 1);
        attributenFilterService.zetMagGeleverdWordenVlaggen(leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen(), dienst,
            Rol.AFNEMER);
    }

    protected void zetBetrokkenOudersEnRelatieOpMagGeleverdWorden(final SynchronisatieBericht leveringBericht) {
        for (final PersoonHisVolledigView persoonHisVolledigView : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
            if (persoonHisVolledigView.getKindBetrokkenheid() != null) {
                final KindHisVolledigView kindBetrokkenheid = persoonHisVolledigView.getKindBetrokkenheid();
                kindBetrokkenheid.setMagLeveren(true);
                final RelatieHisVolledigView relatie = (RelatieHisVolledigView) persoonHisVolledigView.getKindBetrokkenheid().getRelatie();
                relatie.setMagLeveren(true);
                for (final OuderHisVolledig ouderHisVolledig : relatie.getOuderBetrokkenheden()) {
                    final OuderHisVolledigView ouderHisVolledigView = (OuderHisVolledigView) ouderHisVolledig;
                    ouderHisVolledigView.setMagLeveren(true);
                    if (ouderHisVolledig.getPersoon() != null) {
                        final PersoonHisVolledigView persoon = (PersoonHisVolledigView) ouderHisVolledig.getPersoon();
                        persoon.setMagLeveren(true);
                    }
                }
            }
        }
    }

    protected void verwijderBlobsUitDatabase() throws SQLException {
        em.createQuery("delete from PersoonCacheModel").executeUpdate();

        DataSourceUtils.getConnection(getDataSource()).commit();
    }

    protected SynchronisatieBericht maakLeveringBericht(final List<PersoonHisVolledigView> bijgehoudenPersonen, final List<Populatie> populatieList) {
        final AdministratieveHandelingModel administratieveHandelingModel = administratieveHandelingRepository.haalAdministratieveHandeling(1001L);
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, dienst);

        final Map<Integer, Populatie> populatieMap = new HashMap<>();

        for (int i = 0; i < bijgehoudenPersonen.size(); i++) {
            populatieMap.put(bijgehoudenPersonen.get(i).getID(), populatieList.get(i));
        }

        final List<SynchronisatieBericht> leveringBerichten =
            berichtFactory.maakBerichten(bijgehoudenPersonen, leveringAutorisatie, populatieMap,
                administratieveHandelingModel);

        final SynchronisatieBericht leveringBericht = leveringBerichten.get(0);
        final BerichtStuurgegevensGroepBericht testBerichtStuurgegevensGroepBericht = maakStuurgegevens();
        leveringBericht.setStuurgegevens(testBerichtStuurgegevensGroepBericht);

        final BerichtParametersGroepBericht parametersGroepBericht = new BerichtParametersGroepBericht();
        parametersGroepBericht.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(SoortSynchronisatie.MUTATIEBERICHT));
        parametersGroepBericht.setDienst(new DienstAttribuut(dienst));

        leveringBericht.setBerichtParameters(parametersGroepBericht);

        //Verplicht in xsd:
        leveringBericht.getAdministratieveHandeling().setVerwerkingssoort(Verwerkingssoort.TOEVOEGING);

        return leveringBericht;
    }

    private BerichtStuurgegevensGroepBericht maakStuurgegevens() {
        return berichtFactory.maakStuurgegevens(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde());
    }

    protected String bouwXmlString(final SynchronisatieBericht leveringBericht) {
        try {
            return marshallService.maakBericht(leveringBericht);
        } catch (final JiBXException e) {
            LOGGER.error("Fout bij marshallen. ", e);
        }
        return null;
    }

    protected boolean isValid(final String xml) {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        schemaFactory.setResourceResolver(new ResourceResolver());
        final LoggingErrorHandler errorHandler = new LoggingErrorHandler();

        try {
            final StringReader stringreader = new StringReader(xml);
            factory.setSchema(schemaFactory.newSchema(
                getClass().getClassLoader().getResource("xsd/BRP0200/brp0200_lvgSynchronisatie_Berichten.xsd")));

            final SAXParser parser = factory.newSAXParser();
            final XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(errorHandler);
            reader.parse(new InputSource(stringreader));
        } catch (final IOException | SAXException | ParserConfigurationException e) {
            LOGGER.error("{}", e);
            return false;
        }

        return errorHandler.isValid();
    }

    protected XpathEngine geefBrpXpathEngine() {
        final Map<String, String> namespaces = new HashMap<>();
        namespaces.put("brp", "http://www.bzk.nl/brp/brp0200");
        namespaces.put("stuf", "http://www.kinggemeenten.nl/StUF/StUF0302");

        final NamespaceContext ctx = new SimpleNamespaceContext(namespaces);
        final XpathEngine engine = XMLUnit.newXpathEngine();
        engine.setNamespaceContext(ctx);

        return engine;
    }
}
