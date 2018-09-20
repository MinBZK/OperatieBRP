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

import nl.bzk.brp.bevraging.business.berichtcmds.OpvragenPersoonBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.business.service.AutenticatieService;
import nl.bzk.brp.bevraging.business.service.AutenticatieService.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService;
import nl.bzk.brp.bevraging.business.service.SvnVersionService;
import nl.bzk.brp.bevraging.ws.basis.Persoon;
import nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType;
import nl.bzk.brp.bevraging.ws.service.interceptor.BerichtIdGeneratorInterceptor;
import nl.bzk.brp.bevraging.ws.service.mapper.OpvragenPersoonAntwoordFoutMapper;
import nl.bzk.brp.bevraging.ws.service.mapper.PersoonMapper;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSSecurityEngineResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
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

    @Inject
    private ApplicationContext       applicationContext;

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
            BrpBerichtContext context = getContext(vraag, messageContext);

            OpvragenPersoonBerichtCommand bericht = bouwBerichtCommand(criteria, context);

            berichtUitvoerderService.voerBerichtUit(bericht);

            Collection<Persoon> result = new HashSet<Persoon>();

            if (bericht.getAntwoord() != null && bericht.getAntwoord().getPersonen() != null) {
                for (nl.bzk.brp.bevraging.domein.Persoon persoon : bericht.getAntwoord().getPersonen()) {
                    result.add(PersoonMapper.mapDomeinObjectNaarDTO(persoon));
                }
            }

            return bouwAntwoord(result, context, bericht.getFouten());
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
     * Bouwt het initiele bericht command op, op basis van de zoek criteria en de bericht context.
     *
     * @param criteria de zoek criteria waarop gezocht dient te worden.
     * @param context de bericht context.
     * @return het bericht command.
     */
    private OpvragenPersoonBerichtCommand bouwBerichtCommand(final PersoonZoekCriteria criteria,
            final BrpBerichtContext context)
    {
        // TODO: bericht command dient via een factory gebouwd te worden.
        // TODO: uit architectonisch oogpunt dient de web service laag niet zelf command objecten te instantieren
        // (risico), maar dient dat in de business laag te gebeuren
        OpvragenPersoonBerichtCommand bericht =
            (OpvragenPersoonBerichtCommand) applicationContext.getBean(
                    OpvragenPersoonBerichtCommand.class.getSimpleName(), criteria, context);
        return bericht;
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
            criteria.setBsn(vraag.getBsn().longValue());
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
    private BrpBerichtContext getContext(final OpvragenPersoonVraag vraag, final MessageContext messageContext)
        throws OnbekendePartijException
    {
        BrpBerichtContext context = new BrpBerichtContext();

        Long berichtId = (Long) messageContext.get(BerichtIdGeneratorInterceptor.BRP_BERICHT_ID);
        context.setBerichtId(berichtId);

        WSSecurityEngineResult wsSecurityEngineResult =
            (WSSecurityEngineResult) messageContext.get(WSS4JInInterceptor.SIGNATURE_RESULT);
        // MapUtils.debugPrint(System.out, "wsSecurityEngineResult", wsSecurityEngineResult);

        // byte[] signatureValue = (byte[]) wsSecurityEngineResult.get(WSSecurityEngineResult.TAG_SIGNATURE_VALUE);
        // X500Principal x500Principal = (X500Principal)
        // wsSecurityEngineResult.get(WSSecurityEngineResult.TAG_PRINCIPAL);
        X509CertImpl x509CertImpl =
            (X509CertImpl) wsSecurityEngineResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE);
        AuthenticatieMiddelDTO authenticatieMiddelDTO =
            autenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(
                    x509CertImpl.getSerialNumber(), x509CertImpl.getSignature(), x509CertImpl.getSubjectDN().getName());

        if (authenticatieMiddelDTO == null) {
            throw new OnbekendePartijException(berichtId);
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
    private OpvragenPersoonAntwoord bouwAntwoord(final Collection<Persoon> personen, final BrpBerichtContext context,
            final List<BerichtVerwerkingsFout> fouten)
    {
        OpvragenPersoonAntwoord antwoord = new OpvragenPersoonAntwoord();

        antwoord.setId(context.getBerichtId());
        if (personen != null) {
            antwoord.setAantalPersonen(personen.size());
            antwoord.getPersoon().addAll(personen);
        }
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
