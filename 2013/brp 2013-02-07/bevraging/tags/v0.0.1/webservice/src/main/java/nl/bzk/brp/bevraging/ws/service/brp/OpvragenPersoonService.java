/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.brp;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.bzk.brp.bevraging.business.OpvragenPersoonBusinessService;
import nl.bzk.brp.bevraging.business.antwoord.Antwoord;
import nl.bzk.brp.bevraging.business.vraag.Vraag;
import nl.bzk.brp.bevraging.ws.basis.Persoon;
import nl.bzk.brp.bevraging.ws.service.BerichtIdGenerator;
import nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import org.apache.commons.logging.Log;
import org.springframework.transaction.annotation.Transactional;


/**
 * Web service implementatie voor het opvragen van personen op basis van zoek criteria.
 */
@WebService(targetNamespace = "http://brp.service.ws.bevraging.brp.bzk.nl/", portName = "OpvragenPersoonServicePort",
            serviceName = "OpvragenPersoonServiceService",
            endpointInterface = "nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType")
public class OpvragenPersoonService implements OpvragenPersoonPortType {

    @Inject
    private OpvragenPersoonBusinessService opvragenPersoonService;
    @Inject
    private BerichtIdGenerator             berichtIdGenerator;
    @Inject
    private Log                            log;

    // @Resource
    // private WebServiceContext wsContext;


    /**
     * Haalt een persoon op op basis van een of meerdere zoek criteria.
     *
     * @param vraag vraag bericht met daarin de zoek criteria.
     * @return antwoord bericht met daarin (mogelijk) de gevonden persoon.
     */
    @Override
    @Transactional
    public final OpvragenPersoonAntwoord opvragenPersoon(final OpvragenPersoonVraag vraag) {
        log.info("opvragenPersoon()");

        Vraag<BigDecimal> vraagDTO = new Vraag<BigDecimal>();
        vraagDTO.setVraag(vraag.getBsn());

        Antwoord<nl.bzk.brp.bevraging.domein.Persoon> antwoord =
                opvragenPersoonService.opvragenPersoonOpBasisVanBsn(vraagDTO);
        Persoon persoonDTO = zetPersoonDomeinObjectOmNaarDTO(antwoord.getResultaat());

        return bouwAntwoord(persoonDTO);
    }

    /**
     * Zet een instantie van het {@link nl.bzk.brp.bevraging.domein.Persoon} domein object om in een {@link Persoon} DTO
     * object. Hiervoor worden de waardes van het domein object, waar nodig, overgezet naar de DTO.
     *
     * @param persoonDO de domein object instantie.
     * @return de 'gevulde' persoon DTO.
     */
    private Persoon zetPersoonDomeinObjectOmNaarDTO(final nl.bzk.brp.bevraging.domein.Persoon persoonDO) {
        if (persoonDO == null) {
            return null;
        }

        Persoon persoon = new Persoon();
        persoon.setBsn(new BigDecimal(persoonDO.getBsn()));
        if (persoonDO.getGeslachtsAanduiding() != null) {
            persoon.setGeslachtsAanduiding(persoonDO.getGeslachtsAanduiding().getOmschrijving());
            persoon.setGeslachtsAanduidingCode(persoonDO.getGeslachtsAanduiding().getCode());
        }
        persoon.setGeslachtsNaam(persoonDO.getGeslachtsnaam());
        persoon.setVoornamen(persoonDO.getVoornamen());
        persoon.setVoorvoegsel(persoonDO.getVoorvoegsel());
        if (persoonDO.getGeboorteDatum() != null) {
            persoon.setDatumGeboorte(new BigDecimal(persoonDO.getGeboorteDatum()));
        }

        return persoon;
    }

    /**
     * Bouwt een DTO voor het antwoord bericht op basis van een {@link Persoon} instantie DTO welke geretourneerd
     * dient te worden.
     *
     * @param persoon de persoon die gevonden is en geretourneerd dient te worden.
     * @return het opgebouwde antwoord bericht.
     */
    private OpvragenPersoonAntwoord bouwAntwoord(final Persoon persoon) {
        OpvragenPersoonAntwoord antwoord = new OpvragenPersoonAntwoord();

        antwoord.setId(berichtIdGenerator.volgendeId());
        if (persoon != null) {
            antwoord.setAantalPersonen(1);
            antwoord.getPersoon().add(persoon);
        }
        antwoord.setAantalFouten(0);
        antwoord.setTijdstipVerzonden(huidigTijdstipAlsXMLObject());

        return antwoord;
    }

    /**
     * Retourneert het huidige tijdstip als XML Object; als {@link XMLGregorianCalendar} instance.
     * @return het huidige tijdstip als XML Object.
     */
    private XMLGregorianCalendar huidigTijdstipAlsXMLObject() {
        XMLGregorianCalendar datum = null;
        try {
            datum =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            (GregorianCalendar) GregorianCalendar.getInstance());
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return datum;
    }

}
