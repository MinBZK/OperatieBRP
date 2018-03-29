/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service;

import com.google.common.collect.Iterables;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.Docker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.OmgevingException;
import nl.bzk.brp.dockertest.component.Poorten;
import nl.bzk.brp.dockertest.jbehave.JBehaveState;
import nl.bzk.brp.dockertest.util.ResourceUtils;
import nl.bzk.brp.dockertest.util.SchemaValidator;
import nl.bzk.brp.service.algemeen.Mappings;
import nl.bzk.brp.service.algemeen.request.OIN;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.test.common.xml.XmlUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Service voor het doen van SOAP requests
 */
public final class VerzoekService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String MORGEN_PLACEHOLDER = "${morgen}";
    private static final String GISTER_PLACEHOLDER = "${gister}";
    private static final String GISTEREN_PLACEHOLDER = "${gisteren}";
    private static final String VANDAAG_PLACEHOLDER = "${vandaag}";

    private final BrpOmgeving brpOmgeving;
    private final ObjectSleutelService objectSleutelService;

    private Dienst dienst;
    private String request;
    private String response;
    private boolean soapFoutOpgetreden;
    private boolean vrijberichtVerzoek;
    private boolean bijhoudingverzoek;
    private ToegangLeveringsAutorisatie toegangLeveringautorisatie;

    /**
     *
     * @param brpOmgeving
     */
    public VerzoekService(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
        objectSleutelService = new ObjectSleutelServiceImpl();
    }

    /**
     *
     * @param requestBestand
     * @param toegangLeveringsAutorisatie
     */
    public void maakVerzoek(final Resource requestBestand,
                            final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {

        LOGGER.info("maakVerzoek {}", requestBestand);
        reset();

        final SoortDienst soortDienst = bepaalSoortDienst(requestBestand);
        bepaalDienst(toegangLeveringsAutorisatie, soortDienst);

        this.request = maakRequest(requestBestand, toegangLeveringsAutorisatie);
        this.toegangLeveringautorisatie = toegangLeveringsAutorisatie;
        final URL url;
        try {
            url = bepaalURL(soortDienst);
        } catch (MalformedURLException e) {
            throw new TestclientExceptie(e);
        }

        try {
            final String oinOndertekenaar =
                    toegangLeveringsAutorisatie.getOndertekenaar() == null ? null : toegangLeveringsAutorisatie.getOndertekenaar().getOin();
            final String oinTransporteur =
                    toegangLeveringsAutorisatie.getTransporteur() == null ? null : toegangLeveringsAutorisatie.getTransporteur().getOin();
            verzendBerichtNaarService(request, url, oinOndertekenaar, oinTransporteur);
            ResourceUtils.schrijfBerichtNaarBestand(response, JBehaveState.getScenarioState().getCurrentStory().getPath(),
                    JBehaveState.getScenarioState().getCurrectScenario(), "response.xml");
        } catch (Exception e) {
            throw new TestclientExceptie(e);
        }
    }

    /**
     *
     * @param requestBestand
     * @param partijNaam
     * @param gbaVerzoek
     */
    public void maakBijhoudingVerzoek(final Resource requestBestand, String partijNaam, boolean gbaVerzoek) {
        LOGGER.info("maakBijhoudingVerzoek {}", requestBestand);
        reset();
        this.bijhoudingverzoek = true;

        URL url = null;
        this.request = ResourceUtils.resourceToString(requestBestand);
        try {
            final Document requestXmlDocument = XmlUtils.inputStreamToDocument(requestBestand.getInputStream());
            final XPath xpath = XPathFactory.newInstance().newXPath();

            final String zendendePartijWaarde =
                    (String) xpath.evaluate("//*[local-name()='zendendePartij']/text()", requestXmlDocument, XPathConstants.STRING);

            vervangBsnDoorObjectSleutel(requestXmlDocument, xpath);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, MORGEN_PLACEHOLDER);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, GISTER_PLACEHOLDER);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, GISTEREN_PLACEHOLDER);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, VANDAAG_PLACEHOLDER);

            if (!gbaVerzoek) {
                final String verzoek = XmlUtils.documentToString(requestXmlDocument);
                final SoortBericht soortBericht =
                        SoortBericht.parseIdentifier(XmlUtils.resourceToDomSource(requestBestand).getNode().getFirstChild().getLocalName());
                url = bepaalURL(soortBericht);
                final MutableObject<String> oinZendendePartij = new MutableObject<>();
                brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
                    final List<String> list = jdbcTemplate.queryForList(String.format("select oin from kern.partij where code = '%s'",
                            zendendePartijWaarde), String.class);
                    if (!list.isEmpty()) {
                        final String oin = list.iterator().next();
                        LOGGER.debug("OIN voor Partij {} is {}", zendendePartijWaarde, oin);
                        oinZendendePartij.setValue(oin);
                    }
                });

                verzendBerichtNaarService(verzoek, url, oinZendendePartij.getValue(), oinZendendePartij.getValue());
                ResourceUtils.schrijfBerichtNaarBestand(response, JBehaveState.getScenarioState().getCurrentStory().getPath(),
                        JBehaveState.getScenarioState().getCurrectScenario(), "response.xml");
            }
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            LOGGER.error("Fout tijdens verwerkenBericht van bijhoudingsbericht", e);
            throw new TestclientExceptie(e);
        } catch (SOAPException e) {
            LOGGER.error(String.format("Fout tijdens versturen bijhoudingsverzoek, url %s", url), e);
            throw new TestclientExceptie(e);
        }

    }

    /**
     *
     * @param requestBestand
     */
    public void maakVrijberichtVerzoek(final ClassPathResource requestBestand) {
        LOGGER.info("maakVrijberichtVerzoek {}", requestBestand);
        reset();
        this.vrijberichtVerzoek = true;
        this.request = ResourceUtils.resourceToString(requestBestand);
        final Document document = XmlUtils.stringToDocument(request);

        final String zendendePartijCode = XmlUtils.getNodeWaarde("//brp:zenderVrijBericht", document);

        final MutableObject<String> oinZendendePartij = new MutableObject<>();
        brpOmgeving.brpDatabase().template().readonly(jdbcTemplate -> {
            final List<String> list = jdbcTemplate.queryForList(String.format("select oin from kern.partij where code = '%s'",
                    zendendePartijCode), String.class);
            if (!list.isEmpty()) {
                final String oin = list.iterator().next();
                LOGGER.debug("OIN voor Partij {} is {}", zendendePartijCode, oin);
                oinZendendePartij.setValue(oin);
            }
        });

        try {
            verzendBerichtNaarService(request, getUrl("vrijbericht/VrijBerichtService/vrbStuurVrijBericht", DockerNaam.VRIJBERICHT),
                    oinZendendePartij.getValue(), oinZendendePartij.getValue());
        } catch (IOException | SOAPException | SAXException | ParserConfigurationException e) {
            LOGGER.error("Fout bij verzenden vrij bericht verzoek: " + e);
            throw new TestclientExceptie(e);
        }
        ResourceUtils.schrijfBerichtNaarBestand(response, JBehaveState.getScenarioState().getCurrentStory().getPath(),
                JBehaveState.getScenarioState().getCurrectScenario(), "response.xml");
    }


    /**
     * @return indicatie of er een SOAP fout is opgetreden
     */
    public boolean isSoapFoutOpgetreden() {
        return soapFoutOpgetreden;
    }

    /**
     * @return het requestbericht
     */
    public String getRequest() {
        Assert.notNull(request, "Geen request bericht gevonden!");
        return request;
    }

    /**
     * @return het responsebericht
     */
    public String getResponse() {
        Assert.notNull(response, "Geen response bericht gevonden!");
        return response;
    }

    /**
     * Assert dat het responsebericht XSD valide is.
     */
    public void assertResponseXsdValide() {
        final Source xmlSource = new StreamSource(new StringReader(getResponse()));
        if (dienst == null) {
            SchemaValidator.valideerTegenVrijBerichtSchema(xmlSource);
        } else {
            switch (dienst.getSoortDienst()) {
                case VERWIJDERING_AFNEMERINDICATIE:
                case PLAATSING_AFNEMERINDICATIE:
                    SchemaValidator.valideerTegenAfnemerindicatieSchema(xmlSource);
                    break;
                case GEEF_DETAILS_PERSOON:
                case ZOEK_PERSOON_OP_ADRESGEGEVENS:
                case ZOEK_PERSOON:
                case GEEF_MEDEBEWONERS_VAN_PERSOON:
                    SchemaValidator.valideerTegenBevragingSchema(xmlSource);
                    break;
                case SYNCHRONISATIE_PERSOON:
                case SYNCHRONISATIE_STAMGEGEVEN:
                    SchemaValidator.valideerTegenSynchronisatieSchema(xmlSource);
                    break;
                default:
                    throw new TestclientExceptie("Kan antwoordbericht niet valideren voor dienst: " + dienst.getSoortDienst());

            }
        }
    }

    /**
     * @return de Toegangleveringsautorisatie waar het verzoek voor uitgevoerd is
     */
    public ToegangLeveringsAutorisatie getToegangLeveringautorisatie() {
        return toegangLeveringautorisatie;
    }

    /**
     * @return de dienst waar het verzoek voor uitgevoerd is
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * @return indicatie of het een vrijbericht verzoek betreft.
     */
    boolean isVrijberichtVerzoek() {
        return vrijberichtVerzoek;
    }

    /**
     * @return indicatie of het een vrijbericht verzoek betreft.
     */
    boolean isBijhoudingVerzoek() {
        return bijhoudingverzoek;
    }


    /**
     * Standaard methode voor het verzenden van een bericht naar de WebService in een integratietest. De opgegeven naam van het bericht bestand zal worden
     * ingelezen en als de body van het verzoek naar de webservice worden verstuurd, waarbij deze methode tevens voor het toevoegen van de benodigde SOAP
     * headers etc. zorgt. Uit het antwoord dat terugkomt van de nl.sandersmee.testtool.ontvanger wordt de body geextraheerd en als {@link Node} geretourneerd
     * door deze methode.
     * @param berichtBestand de bestandsnaam die verwijst naar een bestand met daarin de content die moet worden verstuurd.
     */
    private void verzendBerichtNaarService(final String berichtBestand, final URL wsdlURL, final String oinOndertekenaar, final String oinTransporteur)
            throws IOException, SOAPException, SAXException, ParserConfigurationException {
        // Bouw het initiele request bericht
        final SOAPMessage soapMessage = bouwInitieleRequestBericht(getInputStream(berichtBestand));
        // Haal het antwoord op
        //AutAutContext.VerzoekAutorisatie verzoek = AutAutContext.get().getAutorisatieVoorVerzoek();
        SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
        final SOAPConnection connection = factory.createConnection();
        if (oinOndertekenaar != null) {
            soapMessage.getMimeHeaders().addHeader(OIN.OIN_ONDERTEKENAAR, oinOndertekenaar);
        }
        if (oinTransporteur != null) {
            soapMessage.getMimeHeaders().addHeader(OIN.OIN_TRANSPORTEUR, oinTransporteur);
        }

        final SOAPMessage soapResponse = connection.call(soapMessage, wsdlURL);
        this.soapFoutOpgetreden = soapResponse.getSOAPBody().getFault() != null;
        this.response = getResponse(soapResponse);

        LOGGER.debug("\nResponse:\n{}", this.response);
    }

    private InputStream getInputStream(final String bericht) throws IOException {
        return new ByteArrayInputStream(bericht.getBytes(StandardCharsets.UTF_8));
    }

    private SOAPMessage bouwInitieleRequestBericht(final InputStream inputStream)
            throws ParserConfigurationException, IOException, SAXException, SOAPException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        final Document document = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
        final SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPBody().addDocument(document);
        return soapMessage;
    }

    private String getResponse(final SOAPMessage msg) {
        try {
            final Node node = extraheerAntwoordUitSoapBericht(msg);
            return XmlUtils.format(XmlUtils.nodeToString(node));
        } catch (Exception e) {
            throw new TestclientExceptie(e);
        }
    }

    private Node extraheerAntwoordUitSoapBericht(final SOAPMessage soapMessage) throws TransformerConfigurationException, SOAPException {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        final DOMSource source = new DOMSource(soapMessage.getSOAPBody().getFirstChild());
        return source.getNode();
    }

    private String maakRequest(final Resource requestBestand,
                               final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        String tempRequest;
        try (final InputStream inputStream = requestBestand.getInputStream()) {
            tempRequest = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new OmgevingException(e);
        }
        tempRequest = StringUtils.replace(tempRequest, "${levsautorisatieId}",
                toegangLeveringsAutorisatie.getLeveringsautorisatie().getId().toString());
        tempRequest = StringUtils.replace(tempRequest, "${partijCode}",
                StringUtils.leftPad(String.valueOf(toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().getCode()), 6, "0"));
        tempRequest = StringUtils.replace(tempRequest, "${dienstId}", dienst.getId().toString());

        LOGGER.debug("Request:\n{}", tempRequest);
        return tempRequest;
    }

    private void bepaalDienst(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final SoortDienst soortDienst) {
        final Set<Dienstbundel> dienstbundelSet = toegangLeveringsAutorisatie.getLeveringsautorisatie().getDienstbundelSet();
        for (Dienstbundel dienstbundel : dienstbundelSet) {
            for (Dienst mogelijkeDienst : dienstbundel.getDienstSet()) {
                if (mogelijkeDienst.getSoortDienst() == soortDienst) {
                    this.dienst = mogelijkeDienst;
                    break;
                }
            }
        }
        Assert.notNull(dienst, "Dienst niet gevonden van type: " + soortDienst);
        LOGGER.debug("Toegangleveringsautorisatie = {}, Dienst = {}", toegangLeveringsAutorisatie.getId(), this.dienst.getId());
    }

    private SoortDienst bepaalSoortDienst(final Resource requestBestand) {
        final SoortDienst soortDienst;
        try {
            final DOMSource requestSource = XmlUtils.resourceToDomSource(requestBestand);
            final Set<SoortDienst> soortDienstSet = Mappings.soortDienst(requestSource.getNode().getFirstChild().getLocalName());
            Assert.isTrue(!soortDienstSet.isEmpty(),
                    "SoortDienst niet gevonden voor requestelement: " + requestSource.getNode().getFirstChild().getLocalName());
            if (soortDienstSet.size() > 1) {
                //registreer afnemerindicatie kan plaatsen of verwijderen afn.ind. zijn
                final Document requestXmlDocument = XmlUtils.inputStreamToDocument(requestBestand.getInputStream());
                if (brpOmgeving.getxPathHelper()
                        .isNodeAanwezig(XmlUtils.documentToString(requestXmlDocument), "brp:lvg_synRegistreerAfnemerindicatie/brp:plaatsingAfnemerindicatie")) {
                    soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
                } else {
                    soortDienst = SoortDienst.VERWIJDERING_AFNEMERINDICATIE;
                }
            } else {
                soortDienst = Iterables.getOnlyElement(soortDienstSet);
            }
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
        return soortDienst;
    }

    private URL bepaalURL(final SoortDienst soortDienst) throws MalformedURLException {
        String servicePostfix = null;
        DockerNaam dockerNaam = null;
        switch (soortDienst) {
            case PLAATSING_AFNEMERINDICATIE:
            case VERWIJDERING_AFNEMERINDICATIE:
                servicePostfix = "afnemerindicaties/AfnemerindicatiesService/lvgAfnemerindicaties";
                dockerNaam = DockerNaam.ONDERHOUDAFNEMERINDICATIES;
                break;
            case GEEF_STUF_BG_BERICHT:
                servicePostfix = "stuf/StufbgVertalingService/stvStufbgVertaling";
                dockerNaam = DockerNaam.STUF;
                break;
            case GEEF_DETAILS_PERSOON:
            case GEEF_MEDEBEWONERS_VAN_PERSOON:
            case ZOEK_PERSOON:
            case ZOEK_PERSOON_OP_ADRESGEGEVENS:
                servicePostfix = "bevraging/LeveringBevragingService/lvgBevraging";
                dockerNaam = DockerNaam.BEVRAGING;
                break;
            case SYNCHRONISATIE_STAMGEGEVEN:
            case SYNCHRONISATIE_PERSOON:
                servicePostfix = "synchronisatie/SynchronisatieService/lvgSynchronisatie";
                dockerNaam = DockerNaam.SYNCHRONISATIE;
                break;
            default:
                Assert.isTrue(false, "Dienst wordt niet ondersteund: " + soortDienst);
        }
        return getUrl(servicePostfix, dockerNaam);
    }

    private URL bepaalURL(final SoortBericht soortBericht) throws MalformedURLException {
        String servicePostfix = null;
        DockerNaam dockerNaam = null;
        switch (soortBericht) {
            case BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP:
                servicePostfix = "bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap";
                dockerNaam = DockerNaam.BIJHOUDING;
                break;
            case BHG_AFS_REGISTREER_GEBOORTE:
                servicePostfix = "bijhouding/BijhoudingService/bhgAfstamming";
                dockerNaam = DockerNaam.BIJHOUDING;
                break;
            default:
                Assert.isTrue(false, "Verzoek wordt niet ondersteund: " + soortBericht);
        }
        return getUrl(servicePostfix, dockerNaam);
    }

    private URL getUrl(final String servicePostfix, final DockerNaam dockerNaam) throws MalformedURLException {
        final Docker component = brpOmgeving.geefDocker(dockerNaam);
        return new URL(String.format("http://%s:%s/%s",
                brpOmgeving.getDockerHostname(), component.getPoortMap().get(Poorten.APPSERVER_PORT), servicePostfix));
    }

    private void vervangBsnDoorObjectSleutel(final Document doc, final XPath xpath) throws XPathExpressionException {
        final NodeList persoonNodes = (NodeList) xpath.evaluate("//*[local-name()='persoon']", doc, XPathConstants.NODESET);
        for (int index = 0; index < persoonNodes.getLength(); index++) {
            final Node node = persoonNodes.item(index);
            final Node objectSleutelNode = node.getAttributes().getNamedItem("brp:objectSleutel");
            if (objectSleutelNode != null) {
                final PersIdVerzoek persIdVerzoek = new PersIdVerzoek(objectSleutelNode.getNodeValue());
                brpOmgeving.brpDatabase().template().readonly(persIdVerzoek);
                final String objectSleutel = objectSleutelService.maakPersoonObjectSleutel(persIdVerzoek.getPersId(), persIdVerzoek.getLockVersie()).maskeren();
                objectSleutelNode.setNodeValue(objectSleutel);
            }
        }
    }

    private void vervangDatumPlaceholdersMetEchteDatum(final Document doc, final XPath xpath, final String datumPlaceholder)
            throws XPathExpressionException {
        final NodeList datumNodes = (NodeList) xpath.evaluate("//*[text()[contains(., '" + datumPlaceholder + "')]]", doc, XPathConstants.NODESET);
        final int nieuweDatum;

        switch (datumPlaceholder) {
            case MORGEN_PLACEHOLDER:
                nieuweDatum = DatumUtil.morgen();
                break;
            case GISTEREN_PLACEHOLDER:
            case GISTER_PLACEHOLDER:
                nieuweDatum = DatumUtil.gisteren();
                break;
            case VANDAAG_PLACEHOLDER:
                nieuweDatum = DatumUtil.vandaag();
                break;
            default:
                throw new TestclientExceptie("Onbekende placeholder voor een datum gevonden");
        }

        for (int index = 0; index < datumNodes.getLength(); index++) {
            final Node datumNode = datumNodes.item(index);
            String nd = Integer.toString(nieuweDatum);
            datumNode.setTextContent(nd.substring(0, 4) + "-" + nd.substring(4, 6) + "-" + nd.substring(6));
        }
    }

    private void updateCacheSelectief(final SoortDienst soortDienst) {
        DockerNaam[] teVerversenComponenten;
        switch (soortDienst) {
            case VERWIJDERING_AFNEMERINDICATIE:
            case PLAATSING_AFNEMERINDICATIE:
                teVerversenComponenten = new DockerNaam[]{DockerNaam.ONDERHOUDAFNEMERINDICATIES, DockerNaam.VERZENDING};
                break;
            case GEEF_DETAILS_PERSOON:
            case GEEF_MEDEBEWONERS_VAN_PERSOON:
            case ZOEK_PERSOON:
            case ZOEK_PERSOON_OP_ADRESGEGEVENS:
                teVerversenComponenten = new DockerNaam[]{DockerNaam.BEVRAGING};
                break;
            case SYNCHRONISATIE_PERSOON:
                teVerversenComponenten = new DockerNaam[]{DockerNaam.SYNCHRONISATIE, DockerNaam.VERZENDING};
                break;
            case SYNCHRONISATIE_STAMGEGEVEN:
                teVerversenComponenten = new DockerNaam[]{DockerNaam.SYNCHRONISATIE};
                break;
            default:
                teVerversenComponenten = null;
        }

        if (teVerversenComponenten != null) {
            brpOmgeving.cache().refresh(teVerversenComponenten);
        }
    }

    private void reset() {
        dienst = null;
        request = null;
        response = null;
        soapFoutOpgetreden = false;
        toegangLeveringautorisatie = null;

        if (brpOmgeving.bevat(DockerNaam.ROUTERINGCENTRALE)) {
            brpOmgeving.asynchroonBericht().purge();
        }
        vrijberichtVerzoek = false;
    }
}
