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
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.business.service.AuthenticatieService;
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
    private AuthenticatieService     authenticatieService;

    @Inject
    private SvnVersionService        svnVersionService;

    /**
     * Haalt een persoon op op basis van een of meerdere zoek criteria.
     *
     * @param vraag vraag bericht met daarin de zoek criteria.
     * @return antwoord bericht met daarin (mogelijk) de gevonden persoon.
     * @brp.bedrijfsregel BRLO0001, FTPE0001
     */
    @Override
    public final OpvragenPersoonAntwoord opvragenPersoon(final OpvragenPersoonVraag vraag) {
        OpvragenPersoonAntwoord antwoord;
        try {
            BerichtContext context = getContext(vraag);

            MessageContext messageContext = webServiceContext.getMessageContext();
            voegBRPVersionHeaderToe(messageContext);

            AuthenticatieMiddelDTO authenticatieMiddel = getAuthenticatieMiddelOpBasisVanCertificaat(messageContext);
            if (authenticatieMiddel == null) {
                LOGGER.info("Kan bericht {} niet verwerken vanwege onbekende/ongeldige handtekening.",
                        context.getIngaandBerichtId());
                antwoord =
                    bouwAntwoord(context, Arrays.asList(new BerichtVerwerkingsFout(
                            BerichtVerwerkingsFoutCode.ONGELDIGE_AUTHENTICATIE,
                            BerichtVerwerkingsFoutZwaarte.WAARSCHUWING)));
            } else {
                voegAuthenticatieInfoToeAanContext(context, authenticatieMiddel);
                antwoord = verwerkVraag(vraag, context);
            }
        } catch (Throwable t) {
            String berichtId = "<<onbekend>>";
            if (BerichtIDsThreadLocal.getBerichtIDs() != null
                && BerichtIDsThreadLocal.getBerichtIDs().getIngaandBerichtId() != null)
            {
                berichtId = BerichtIDsThreadLocal.getBerichtIDs().getIngaandBerichtId().toString();
            }
            LOGGER.error(String.format("Onbekende fout opgetreden in bericht %s", berichtId), t);
            throw new SoapFault("Er is een onbekende fout opgetreden in de verwerking. Probeer later nogmaals", t,
                    nl.bzk.brp.bevraging.ws.service.OpvragenPersoonService.SERVICE);
        }

        return antwoord;
    }

    /**
     * Verwerkt de vraag door de vraag (het verzoek) om te zetten naar een verzoek DTO en deze uit te laten
     * voeren door de {@link BerichtUitvoerderService} in de business laag. Het uit de business laag verkregen
     * antwoord wordt dan weer omgezet naar DTOs en zo wordt het uiteindelijke antwoord gevuld en geretourneerd.
     *
     * @param vraag het vraag bericht.
     * @param context de context waarbinnen het bericht moet worden uitgevoerd.
     * @return het antwoord op de vraag.
     */
    private OpvragenPersoonAntwoord verwerkVraag(final OpvragenPersoonVraag vraag, final BerichtContext context) {
        PersoonZoekCriteria criteria = bouwZoekCriteriaVoorVraag(vraag);
        PersoonZoekCriteriaAntwoord personenAntwoord = berichtUitvoerderService.voerBerichtUit(criteria, context);

        Collection<Persoon> personen = mapGevondenPersonenNaarPersoonDTOs(personenAntwoord);

        return bouwAntwoord(personen, context, personenAntwoord.getFouten());
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
     * Retourneert de context van het bericht; haalt de bericht ids uit de ThreadLocal en zet het abonnement op
     * basis van de content van de vraag.
     *
     * @param vraag de vraag in het bericht.
     * @return de context van het bericht.
     */
    private BerichtContext getContext(final OpvragenPersoonVraag vraag) {
        BerichtContext context = new BerichtContext();

        context.setIngaandBerichtId(BerichtIDsThreadLocal.getBerichtIDs().getIngaandBerichtId());
        context.setUitgaandBerichtId(BerichtIDsThreadLocal.getBerichtIDs().getUitgaandBerichtId());
        context.setAbonnementId(vraag.getAbonnementId());

        return context;
    }

    /**
     * Voegt de authenticatie informatie toe aan de bericht context op basis van het opgegeven authenticatiemiddel.
     *
     * @param authenticatieMiddel het authenticatieMiddel waarmee het bericht is getekend.
     * @param berichtContext de context waarbinnen het bericht wordt uitgevoerd.
     */
    private void voegAuthenticatieInfoToeAanContext(final BerichtContext berichtContext,
            final AuthenticatieMiddelDTO authenticatieMiddel)
    {
        berichtContext.setAuthenticatieMiddelId(authenticatieMiddel.getAuthenticatieMiddelId());
        berichtContext.setPartijId(authenticatieMiddel.getAuthenticatieMiddelPartijId());
    }

    /**
     * Haalt het gebruikte authenticatiemiddel op, en daarmee de partij, op basis van het certificaat waarmee het
     * bericht was getekend. Dit certificaat zit in de <code>messageContext</code>.
     *
     * @param messageContext de context van het bericht met daarin het gebruikte certificaat.
     * @return het authenticatiemiddel dat gebruikt is op basis van het certificaat of <code>null</code> als
     *         het certificaat/partij niet gevonden kon worden.
     */
    @SuppressWarnings("restriction")
    private AuthenticatieMiddelDTO getAuthenticatieMiddelOpBasisVanCertificaat(final MessageContext messageContext) {
        WSSecurityEngineResult wsSecurityEngineResult =
            (WSSecurityEngineResult) messageContext.get(WSS4JInInterceptor.SIGNATURE_RESULT);
        X509CertImpl x509CertImpl =
            (X509CertImpl) wsSecurityEngineResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE);

        return authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(
                x509CertImpl.getSerialNumber(), x509CertImpl.getSignature(), x509CertImpl.getSubjectDN().getName());
    }

    /**
     * Bouwt een collectie van {@link Persoon} DTOs op, op basis van de in het <code>personenAntwoord</code> aanwezige
     * (en dus gevonden) personen.
     *
     * @param personenAntwoord het antwoord op het verzoek met daarin de personen die voldoen aan het verzoek.
     * @return de in het antwoord opgenomen personen gemapt naar DTOs.
     */
    private Collection<Persoon> mapGevondenPersonenNaarPersoonDTOs(final PersoonZoekCriteriaAntwoord personenAntwoord) {
        Collection<Persoon> personen = new HashSet<Persoon>();

        for (nl.bzk.brp.bevraging.domein.kern.Persoon persoon : personenAntwoord.getPersonen()) {
            personen.add(PersoonMapper.mapDomeinObjectNaarDTO(persoon));
        }
        return personen;
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
        OpvragenPersoonAntwoord antwoord = bouwAntwoord(context, fouten);
        antwoord.setAantalPersonen(personen.size());
        antwoord.getPersoon().addAll(personen);

        return antwoord;
    }

    /**
     * Bouwt een DTO voor het antwoord bericht op basis van de context en de opgetreden fouten.
     *
     * @param context de context van het bericht.
     * @param fouten de lijst van opgetreden fouten.
     * @return het opgebouwde antwoord bericht.
     */
    private OpvragenPersoonAntwoord bouwAntwoord(final BerichtContext context,
            final List<BerichtVerwerkingsFout> fouten)
    {
        OpvragenPersoonAntwoord antwoord = new OpvragenPersoonAntwoord();
        antwoord.setId(context.getIngaandBerichtId());
        antwoord.setAantalFouten(fouten.size());
        antwoord.setTijdstipVerzonden(huidigTijdstipAlsXMLObject());

        for (BerichtVerwerkingsFout fout : fouten) {
            antwoord.getOpvragenPersoonAntwoordFout().add(
                    OpvragenPersoonAntwoordFoutMapper.mapDomeinObjectNaarDTO(fout));
        }

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
