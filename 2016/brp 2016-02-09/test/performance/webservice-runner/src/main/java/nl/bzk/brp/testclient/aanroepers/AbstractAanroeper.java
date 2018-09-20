/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.xml.bind.JAXBElement;

import nl.bzk.brp.brp0200.Aktenummer;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingBronnen;
import nl.bzk.brp.brp0200.DatumTijd;
import nl.bzk.brp.brp0200.DocumentIdentificatie;
import nl.bzk.brp.brp0200.GroepBerichtStuurgegevens;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandelingBron;
import nl.bzk.brp.brp0200.ObjecttypeBericht;
import nl.bzk.brp.brp0200.ObjecttypeDocument;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.Referentienummer;
import nl.bzk.brp.brp0200.SysteemNaam;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.DuurDto;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.misc.StatistiekenImpl;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;


/**
 * De Class AbstractAanroeper.
 *
 * @param <P> het generic type
 */
public abstract class AbstractAanroeper<P> implements Callable<System> {

    /** tijd in het formaat yyyy-MM-ddTHH:mm:ss.[honderdsteseconden]+Tijdzone. */
    private static final String DATUMTIJDSTIP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /** Standaard parameter naam voor prevalidatie. */
    public static final String PARAMETER_PREVALIDATIE                      = "PREVALIDATIE";

    /** Standaard parameter naam voor entiteit type administratieve handeling. */
    public static final String OBJECTTYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";
    /** Standaard parameter naam voor entiteit type gedeblokkeerde melding. */
    public static final String OBJECTTYPE_GEDEBLOKKEERDE_MELDING    = "GedeblokkeerdeMelding";
    /** Standaard parameter naam voor entiteit type actie. */
    public static final String OBJECTTYPE_ACTIE                     = "Actie";
    /** Standaard parameter naam voor entiteit type document. */
    public static final String OBJECTTYPE_DOCUMENT                  = "Document";
    /** Standaard parameter naam voor entiteit type persoon. */
    public static final String OBJECTTYPE_PERSOON                   = "Persoon";
    /** Standaard parameter naam voor entiteit type adres. */
    public static final String OBJECTTYPE_PERSOONADRES             = "PersoonAdres";
    /** Standaard parameter naam voor entiteit type relatie. */
    public static final String OBJECTTYPE_RELATIE                   = "Relatie";
    /** Standaard parameter naam voor entiteit type betrokkenheid. */
    public static final String OBJECTTYPE_BETROKKENHEID             = "Betrokkenheid";
    /** Standaard parameter naam voor entiteit type voornaam. */
    public static final String OBJECTTYPE_PERSOONVOORNAAM           = "PersoonVoornaam";
    /** Standaard parameter naam voor entiteit type geslachtsnaamcomponent. */
    public static final String OBJECTTYPE_PERSOONGESLNAAMCOMP       = "PersoonGeslachtsnaamcomponent";
    /** Standaard parameter naam voor entiteit type nationaliteit. */
    public static final String OBJECTTYPE_PERSOONNATIONALITEIT      = "PersoonNationaliteit";
    /** Standaard parameter naam voor entiteit type persoon indicatie. */
    public static final String OBJECTTYPE_PERSOONINDICATIE          = "PersoonIndicatie";

    /** De Constante TRUSTSTORE_FILENAME. */
    private static final String TRUSTSTORE_FILENAME = "/certificaten/ca.jks";
    /** De Constante TRUSTSTORE_PASSWORD. */
    private static final String TRUSTSTORE_PASSWORD = "hallo123";
    /** De Constante KEYSTORE_FILENAME. */
    private static final String KEYSTORE_FILENAME = "/certificaten/kern_soap_client.jks";
    /** De Constante KEYSTORE_PASSWORD. */
    private static final String KEYSTORE_PASSWORD = "hallo123";

    /** Lengte van een BSN. */
    protected static final Integer BSN_LENGTE = 9;
    /** Lengte van een Administratienummer. */
    protected static final Integer ANR_LENGTE = 10;
    /** De partijcode voor het inschieten van berichten. */
    protected static final String PARTIJ_CODE = "000101";
    /**Organisatie voor stuurgegevens. */
    protected static final String ORGANISATIE = "000101";
    /**Applicatie voor de stuurgegevens. */
    protected static final String APPLICATIE = "Testclient";

    /** De eigenschappen. */
    private final Eigenschappen eigenschappen;
    /** De port type. */
    private final P portType;
    /** De cxf client. */
    private final Client cxfClient;
    /** De http conduit. */
    private final HTTPConduit httpConduit;
    /** De Constante valueMap. */
    private final Map<String, String> valueMap = new HashMap<>();

    /** De JAXB objectfacory. */
    private final ObjectFactory objectFactory;

    private Integer communicatieIdTeller;

    /**
     * Verkrijgt bericht.
     *
     * @return bericht
     */
    public abstract BERICHT getBericht();

    /**
     * Instantieert een nieuwe abstract aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param portType de port type
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    protected AbstractAanroeper(final Eigenschappen eigenschappen, final P portType,
        final Map<String, String> parameterMap) throws Exception
    {
        this.eigenschappen = eigenschappen;
        this.portType = portType;

        List<String> parameterList = new ArrayList<>();
        if (getParameterLijst() != null) {
            parameterList = getParameterLijst();
        }

        for (String parameter : parameterList) {
            if (parameterMap.containsKey(parameter)) {
                valueMap.put(parameter, parameterMap.get(parameter));
            }
        }

        objectFactory = new ObjectFactory();

        InputStream in = TestClient.class.getResourceAsStream(TRUSTSTORE_FILENAME);
        File tempTruststoreFile = new File(System.getProperty("java.io.tmpdir"), "test-client-truststore.jks");

        OutputStream out = new FileOutputStream(tempTruststoreFile);
        IOUtils.copy(in, out);

        System.setProperty("javax.net.ssl.trustStore", tempTruststoreFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword", TRUSTSTORE_PASSWORD);

        Map<String, Object> outProps = new HashMap<>();
        outProps.put(WSHandlerConstants.ACTION, "Timestamp Signature");
        outProps.put(WSHandlerConstants.USER, "1");
        outProps.put(WSHandlerConstants.SIG_PROP_FILE, "sigpropfile_client.properties");
        outProps.put(WSHandlerConstants.PW_CALLBACK_REF,
                     new ServerPasswordCallback(eigenschappen.getPrivatekeypassword()));
        WSS4JOutInterceptor wss4JOutInterceptor = new WSS4JOutInterceptor(outProps);

        Map<String, Object> inProps = new HashMap<>();
        inProps.put(WSHandlerConstants.ACTION, "Timestamp Signature");
        inProps.put(WSHandlerConstants.SIG_PROP_FILE, "sigpropfile_server.properties");
        WSS4JInInterceptor wss4JInInterceptor = new WSS4JInInterceptor(inProps);

        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(TestClient.class.getResourceAsStream(KEYSTORE_FILENAME), KEYSTORE_PASSWORD.toCharArray());
        KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyFactory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
        KeyManager[] km = keyFactory.getKeyManagers();
        tlsClientParameters.setKeyManagers(km);

        cxfClient = ClientProxy.getClient(portType);

        Endpoint cxfEndpoint = cxfClient.getEndpoint();
        cxfEndpoint.getOutInterceptors().add(wss4JOutInterceptor);

        /*cxfEndpoint.getInInterceptors().add(wss4JInInterceptor);*/

        // Aanzetten als je berichten wilt zien (vergeet niet log4j te configureren)
//        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
//        loggingOutInterceptor.setPrettyLogging(true);
//        cxfEndpoint.getOutInterceptors().add(loggingOutInterceptor);
//
//        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
//        loggingInInterceptor.setPrettyLogging(true);
//        cxfEndpoint.getInInterceptors().add(loggingInInterceptor);

        httpConduit = (HTTPConduit) cxfClient.getConduit();
//        httpConduit.setTlsClientParameters(tlsClientParameters);

        communicatieIdTeller = 0;
    }

    /**
     * Instantieert een nieuwe Abstract aanroeper.
     *
     * @param eigenschappen eigenschappen
     */
    protected AbstractAanroeper(final Eigenschappen eigenschappen) {
        this.eigenschappen = eigenschappen;
        portType = null;
        cxfClient = null;
        httpConduit = null;
        objectFactory = new ObjectFactory();
    }

    /**
     * Roep aan.
     *
     * @param command de command
     * @return de long
     */
    public DuurDto roepAan(final AbstractSoapCommand<P, ?, ?> command) {
        TestClient.RATELIMITER.acquire();
        DuurDto  duurDto = command.voerUit(portType);
        duurDto.setBericht(getBericht());
        duurDto.setType(getBericht().getType());
        StatistiekenImpl.INSTANCE.bijwerkenNaBericht(duurDto);
        return duurDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public System call() {
        fire();
        return null;
    }

    /** Fire. */
    public abstract void fire();

    /** Close. */
    public void close() {
        httpConduit.close();
        cxfClient.destroy();
    }

    /**
     * Haalt de eigenschappen op.
     *
     * @return eigenschappen
     */
    protected final Eigenschappen getEigenschappen() {
        return eigenschappen;
    }

    /**
     * Haalt een value from value map op.
     *
     * @param parameterNaam de parameter naam
     * @param standaardWaarde de standaard value
     * @return value from value map
     */
    protected String getValueFromValueMap(final String parameterNaam, final String standaardWaarde) {
        final String value;
        if (valueMap.containsKey(parameterNaam) && valueMap.get(parameterNaam) != null) {
            value = valueMap.get(parameterNaam);
        } else {
            value = standaardWaarde;
        }
        return value;
    }

    /**
     * Haalt een parameter lijst op.
     *
     * @return parameter lijst
     */
    protected abstract List<String> getParameterLijst();


    /**
     * Voegt stuurgegevens toe aan het opgegeven bericht. Hiervoor worden de standaard organisatie, applicatie naam
     * en referentienummer gebruikt.
     *
     * @param bericht het bericht waaraan de stuurgegevens dienen te worden toegevoegd.
     */
    protected void zetStuurgegevens(final ObjecttypeBericht bericht) {
        bericht.setStuurgegevens(objectFactory.createObjecttypeBerichtStuurgegevens(
        bouwStuurgegevensBRP(ORGANISATIE, APPLICATIE, "" + UUID.randomUUID())));
    }

    /**
     * Instantieert en zet de waardes van een nieuwe {@link GroepBerichtStuurgegevens} instantie naar de opgegeven waardes.
     *
     * @param organisatie de organisatie die het bericht verstuurt.
     * @param applicatie de applicatie van waaruit het bericht wordt verstuurt.
     * @param referentieNummer het referentie nummer dat vanuit de organisatie wordt meegegeven aan het bericht.
     * @return een nieuwe instantie van de stuurgegevens.
     */
    private GroepBerichtStuurgegevens bouwStuurgegevensBRP(final String organisatie, final String applicatie,
        final String referentieNummer)
    {
        final GroepBerichtStuurgegevens stuurgegevens = new GroepBerichtStuurgegevens();

        final PartijCode zendendePartijCode = new PartijCode();
        zendendePartijCode.setValue(organisatie);
        final JAXBElement<PartijCode> zendendePartij =
                objectFactory.createGroepBerichtStuurgegevensZendendePartij(zendendePartijCode);
        stuurgegevens.setZendendePartij(zendendePartij);

        final SysteemNaam zendendSysteem = new SysteemNaam();
        zendendSysteem.setValue("Webservice Runner");
        final JAXBElement<SysteemNaam> zendendSysteemJaxb =
                objectFactory.createGroepBerichtStuurgegevensZendendeSysteem(zendendSysteem);
        stuurgegevens.setZendendeSysteem(zendendSysteemJaxb);

        final Referentienummer referentienummer = new Referentienummer();
        referentienummer.setValue(referentieNummer);
        final JAXBElement<Referentienummer> referentienummerJaxb =
                objectFactory.createGroepBerichtStuurgegevensReferentienummer(referentienummer);
        stuurgegevens.setReferentienummer(referentienummerJaxb);
        stuurgegevens.setCommunicatieID("stuurgegevensComId");

        final DatumTijd tijdstipVerzending = new DatumTijd();
        tijdstipVerzending.setValue(javaDateNaarW3cDatumString(new Date()));
        final JAXBElement<DatumTijd> tijdstipVerzendingJaxb =
                objectFactory.createGroepBerichtStuurgegevensTijdstipVerzending(tijdstipVerzending);
        stuurgegevens.setTijdstipVerzending(tijdstipVerzendingJaxb);

        return stuurgegevens;
    }

    /**
     * Verkrijgt object factory.
     *
     * @return object factory
     */
    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    /**
     * Retourneert de volgende communicatie id die in een bericht gebruikt kan worden.
     *
     * @return een (voor deze instantie) unieke communicatie id.
     */
    protected String nextCommunicatieId() {
        communicatieIdTeller++;
        return String.valueOf(communicatieIdTeller);
    }

    /**
     * Zet een {@link java.util.Date} instantie om naar een tekstuele representatie, waarbij het formaat dat gebruikt
     * wordt voldoet aan de W3C en de koppelvlak standaard.
     *
     * @param datumTijd het tijdstip dat omgezet dient te worden.
     * @return een W3C compliant datum formaat string.
     */
    public static String javaDateNaarW3cDatumString(final Date datumTijd) {
        final String resultaat;
        if (datumTijd != null) {
            String javaDateString = new SimpleDateFormat(DATUMTIJDSTIP_FORMAT).format(datumTijd);
            // De Simple Date Format timezone (Z) bevat geen ':', dus die moeten we er nog even bij zetten,
            // en wel op de 2-na-laatste positie van de string. Niet fraai, maar werkt wel altijd.
            resultaat = javaDateString.substring(0, javaDateString.length() - 2)
                    + ":"
                    + javaDateString.substring(javaDateString.length() - 2);
        } else {
            resultaat = "";
        }
        return resultaat;
    }

    /**
     * Plaatst de bijgehouden documenten.
     *
     * @param handeling handeling
     * @param partijCode partij code
     * @param soortAkteNaam soort akte naam
     * @return bijgehouden documenten
     */
    protected String setBijgehoudenDocumenten(final ObjecttypeAdministratieveHandeling handeling,
                                              final PartijCode partijCode, final String soortAkteNaam)
    {
        final String communicatieId = nextCommunicatieId();
        final ObjecttypeDocument document = objectFactory.createObjecttypeDocument();
        document.setObjecttype("Document");
        document.setPartijCode(objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));
        document.setSoortNaam(creeerJaxbSoortDocumentNaam(soortAkteNaam));
        document.setCommunicatieID(communicatieId);
        document.setAktenummer(creeerJaxbAktenummer("3AA0001"));
        document.setIdentificatie(creeerJaxbIdentificatie("HA0530-3AA0001"));

        final ContainerAdministratieveHandelingBronnen bronnen = new ContainerAdministratieveHandelingBronnen();
        ObjecttypeAdministratieveHandelingBron bron = new ObjecttypeAdministratieveHandelingBron();
        bron.setObjecttype("ActieBron");
        bron.setCommunicatieID("comid.bron1");
        bron.setDocument(objectFactory.createObjecttypeActieBronDocument(document));
        bronnen.getBron().add(bron);

        final JAXBElement<ContainerAdministratieveHandelingBronnen> bronnenJaxb =
                objectFactory.createObjecttypeAdministratieveHandelingBronnen(bronnen);
        handeling.setBronnen(bronnenJaxb);
//        handeling.setBijgehoudenDocumenten(objectFactory.createObjecttypeAdministratieveHandelingBijgehoudenDocumenten(
//                objectFactory.createContainerAdministratieveHandelingBijgehoudenDocumenten()));
//        handeling.getBijgehoudenDocumenten().getValue().getDocument().add(document);

        return communicatieId;
    }

    /**
     * Creeert jaxb soort document naam.
     *
     * @param docSoortNaam doc soort naam
     * @return JAXB element
     */
    private JAXBElement<NaamEnumeratiewaarde> creeerJaxbSoortDocumentNaam(final String docSoortNaam) {
        NaamEnumeratiewaarde  attribuut = objectFactory.createNaamEnumeratiewaarde();
        attribuut.setValue(docSoortNaam);
        return objectFactory.createObjecttypeDocumentSoortNaam(attribuut);
    }

    /**
     * Creeert jaxb aktenummer.
     *
     * @param aktenummer aktenummer
     * @return jAXB element
     */
    private JAXBElement<Aktenummer> creeerJaxbAktenummer(final String aktenummer) {
        Aktenummer attribuut = objectFactory.createAktenummer();
        attribuut.setValue(aktenummer);
        return objectFactory.createObjecttypeDocumentAktenummer(attribuut);
    }

    /**
     * Creeert jaxb identificatie.
     *
     * @param identificatienummer identificatienummer
     * @return jAXB element
     */
    private JAXBElement<DocumentIdentificatie> creeerJaxbIdentificatie(final String identificatienummer) {
        DocumentIdentificatie attribuut = objectFactory.createDocumentIdentificatie();
        attribuut.setValue(identificatienummer);
        return objectFactory.createObjecttypeDocumentIdentificatie(attribuut);
    }
}
