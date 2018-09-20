/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.business.service.AutenticatieService;
import nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService;
import nl.bzk.brp.bevraging.business.service.SvnVersionService;
import nl.bzk.brp.bevraging.ws.basis.Persoon;
import nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType;
import nl.bzk.brp.bevraging.ws.service.interceptor.helper.BerichtIDsThreadLocal;
import nl.bzk.brp.bevraging.ws.service.mapper.OpvragenPersoonAntwoordFoutMapper;
import nl.bzk.brp.bevraging.ws.service.mapper.PersoonMapper;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.x509.X509CertImpl;


/**
 * Web service implementatie voor het opvragen van personen op basis van zoek criteria.
 */
@WebService(targetNamespace = "http://www.brp.bzk.nl/bevraging/ws/service", portName = "OpvragenPersoonServicePort",
            serviceName = "OpvragenPersoonService", wsdlLocation = "bevraging.wsdl",
            endpointInterface = "nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType")
public class OpvragenPersoonService implements OpvragenPersoonPortType {

    private static final Logger      LOGGER = LoggerFactory.getLogger(OpvragenPersoonService.class);

    @Inject
    private BerichtUitvoerderService berichtUitvoerderService;

    @Resource
    private WebServiceContext        webServiceContext;

    @Inject
    private AutenticatieService      autenticatieService;

    @Inject
    private SvnVersionService        svnVersionService;

    /**
     * Haalt een persoon op op basis van een of meerdere zoek criteria.
     *
     * @param vraag vraag bericht met daarin de zoek criteria.
     * @return antwoord bericht met daarin (mogelijk) de gevonden persoon.
     */
    @Override
    public final OpvragenPersoonAntwoord opvragenPersoon(final OpvragenPersoonVraag vraag) {

        try {
            MessageContext messageContext = webServiceContext.getMessageContext();

            voegBRPVersionHeaderToe(messageContext);

            PersoonZoekCriteria criteria = bouwZoekCriteriaVoorVraag(vraag);
            BerichtContext context = getContext(vraag, messageContext);

            PersoonZoekCriteriaAntwoord antwoord = berichtUitvoerderService.voerBerichtUit(criteria, context);

            Collection<Persoon> result = new HashSet<Persoon>();

            for (nl.bzk.brp.bevraging.domein.kern.Persoon persoon : antwoord.getPersonen()) {
                result.add(PersoonMapper.mapDomeinObjectNaarDTO(persoon));
            }

            return bouwAntwoord(result, context, antwoord.getFouten());
        } catch (BerichtException b) {
            String errorMsg = "Fout in berichtId " + b.getBerichtId() + ":" + b.getClass().getSimpleName();
            LOGGER.warn(errorMsg, b);
            throw new SoapFault(errorMsg, b, nl.bzk.brp.bevraging.ws.service.OpvragenPersoonService.SERVICE);
        } catch (Throwable b) {
            String errorMsg = "Er is een onbekende fout opgetreden" + ":" + getClass().getSimpleName();
            LOGGER.warn(errorMsg, b);
            throw new SoapFault(errorMsg, b, nl.bzk.brp.bevraging.ws.service.OpvragenPersoonService.SERVICE);
        }
    }

    /**
     * Voegt de BRP versie toe aan de HTTP headers van het antwoord bericht.
     *
     * @param messageContext de context van het bericht.
     */
    private void voegBRPVersionHeaderToe(final MessageContext messageContext) {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("X-BRP-VERSION", Arrays.asList(svnVersionService.getAppString()));
        messageContext.put(MessageContext.HTTP_RESPONSE_HEADERS, headers);
    }

    /**
     * Bouwt een nieuwe instantie van {@link PersoonZoekCriteria} op basis van de {@link OpvragenPersoonVraag vraag}.
     *
     * @param vraag de vraag in het bericht.
     * @return een nieuwe instantie die de persoon zoek criteria bevat.
     */
    private PersoonZoekCriteria bouwZoekCriteriaVoorVraag(final OpvragenPersoonVraag vraag) {
        PersoonZoekCriteria criteria = new PersoonZoekCriteria();
        if (vraag.getBsn() != null) {
            criteria.setBsn(Long.parseLong(vraag.getBsn()));
        } else {
            criteria.setBsn(null);
        }
        criteria.setBeschouwing(Calendar.getInstance());
        return criteria;
    }

    /**
     * Retourneert de context van het bericht.
     *
     * @param messageContext SOAP message context van het bericht.
     * @param vraag de vraag in het bericht.
     * @return de context van het bericht.
     * @throws OnbekendePartijException indien een bericht is gesigned met een onbekend certificaat.
     */
    @SuppressWarnings("restriction")
    private BerichtContext getContext(final OpvragenPersoonVraag vraag, final MessageContext messageContext)
        throws OnbekendePartijException
    {
        BerichtContext context = new BerichtContext();

        final long ingaandBerichtId = BerichtIDsThreadLocal.getBerichtIDs().getIngaandBerichtId();
        final long uitgaandBerichtId = BerichtIDsThreadLocal.getBerichtIDs().getUitgaandBerichtId();
        context.setIngaandBerichtId(ingaandBerichtId);
        context.setUitgaandBerichtId(uitgaandBerichtId);

        WSSecurityEngineResult wsSecurityEngineResult =
            (WSSecurityEngineResult) messageContext.get(WSS4JInInterceptor.SIGNATURE_RESULT);

        X509CertImpl x509CertImpl =
            (X509CertImpl) wsSecurityEngineResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE);
        AuthenticatieMiddelDTO authenticatieMiddelDTO =
            autenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(
                    x509CertImpl.getSerialNumber(), x509CertImpl.getSignature(), x509CertImpl.getSubjectDN().getName());

        if (authenticatieMiddelDTO == null) {
            throw new OnbekendePartijException(ingaandBerichtId);
        }

        context.setAuthenticatieMiddelId(authenticatieMiddelDTO.getAuthenticatieMiddelId());
        context.setPartijId(authenticatieMiddelDTO.getAuthenticatieMiddelPartijId());
        context.setAbonnementId(vraag.getAbonnementId());

        return context;
    }

    /**
     * Bouwt een DTO voor het antwoord bericht op basis van een lijst van {@link Persoon} instantie DTOs welke
     * geretourneerd dient te worden, de context en de opgetreden fouten.
     *
     * @param personen de personen die gevonden zijn en geretourneerd dienen te worden.
     * @param context de context van het bericht.
     * @param fouten de lijst van opgetreden fouten.
     * @return het opgebouwde antwoord bericht.
     */
    private OpvragenPersoonAntwoord bouwAntwoord(final Collection<Persoon> personen, final BerichtContext context,
            final List<BerichtVerwerkingsFout> fouten)
    {
        OpvragenPersoonAntwoord antwoord = new OpvragenPersoonAntwoord();

        antwoord.setId(context.getIngaandBerichtId());
        antwoord.setAantalPersonen(personen.size());
        antwoord.getPersoon().addAll(personen);
        antwoord.setAantalFouten(fouten.size());
        for (BerichtVerwerkingsFout fout : fouten) {
            antwoord.getOpvragenPersoonAntwoordFout().add(
                    OpvragenPersoonAntwoordFoutMapper.mapDomeinObjectNaarDTO(fout));
        }
        antwoord.setTijdstipVerzonden(huidigTijdstipAlsXMLObject());

        return antwoord;
    }

    /**
     * Retourneert het huidige tijdstip als XML Object; als {@link XMLGregorianCalendar} instance.
     *
     * @return het huidige tijdstip als XML Object.
     */
    private XMLGregorianCalendar huidigTijdstipAlsXMLObject() {
        XMLGregorianCalendar datum = null;
        try {
            datum = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) Calendar.getInstance());
        } catch (DatatypeConfigurationException e) {
            LOGGER.warn("Problemen bij het omzetten van de huidige datum/tijd naar XML Object.");
        }
        return datum;
    }

}
