/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import javax.inject.Inject;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBElement;
import nl.bzk.brp.brp0200.FiatteringNotificeerBijhoudingsplan;
import nl.bzk.brp.brp0200.GroepBerichtStuurgegevens;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.SynchronisatieVerwerkPersoon;
import nl.bzk.brp.levering.berichtverwerking.service.LvgSynchronisatieVerwerking;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;


/**
 * De implementatie van de webservice die kennisgevingen ontvangt van de BRP.
 */
@WebService(wsdlLocation = "wsdl/brp-berichtverwerking.wsdl", serviceName = "BrpBerichtVerwerkingService", portName = "lvgSynchronisatieVerwerking")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class BrpVoorbeeldKennisgevingOntvanger implements LvgSynchronisatieVerwerking {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    public static final String GEEN_ZENDER = "Geen zender";
    public static final String GEEN_ONTVANGER = "Geen ontvanger";
    public static final String FOUT_BIJ_HET_VERWERKEN_VAN_DE_ONTVANGEN_VAN_EEN_MET_REFERENTIENUMMER
        = "Fout bij het verwerken van de ontvangen van een {} met referentienummer '{}' ";

    @Inject
    private KennisgevingVerwerker kennisgevingVerwerker;

    @Override
    @Oneway
    @WebMethod(action = "verwerkSynchronisatiePersoon")
    public final void verwerkPersoon(final SynchronisatieVerwerkPersoon synVerwerkMutatiePersoon) {
        LOGGER.debug("Start ontvangen bericht");
        if (synVerwerkMutatiePersoon == null) {
            LOGGER.info("Geen valide BRP0100 kennisgevings bericht gestuurd, dit is vast een foutje...");
            return;
        }

        final JAXBElement<GroepBerichtStuurgegevens> stuurgegevensElement = synVerwerkMutatiePersoon.getStuurgegevens();
        if (stuurgegevensElement == null) {
            return;
        }
        final GroepBerichtStuurgegevens stuurgegevens = stuurgegevensElement.getValue();

        try {
            updateMDC(stuurgegevens.getOntvangendePartij().getValue());

            final String zendendePartij;
            if (stuurgegevens.getZendendePartij().getValue() != null) {
                zendendePartij = stuurgegevens.getZendendePartij().getValue().getValue();
            } else {
                zendendePartij = GEEN_ZENDER;
            }

            final String ontvangendePartij;
            if (stuurgegevens.getOntvangendePartij().getValue() != null) {
                ontvangendePartij = stuurgegevens.getOntvangendePartij().getValue().getValue();
            } else {
                ontvangendePartij = GEEN_ONTVANGER;
            }

            LOGGER.info("Kennisgeving stuurgegevens: zendendeSysteem={},zendendePartij={},ontvangendePartij={},referentienummer={}",
                stuurgegevens.getZendendeSysteem().getValue().getValue(), zendendePartij, ontvangendePartij,
                stuurgegevens.getReferentienummer().getValue().getValue());

            kennisgevingVerwerker.verwerkKennisgeving(synVerwerkMutatiePersoon);

            LOGGER.debug("Einde ontvangen bericht");

        } catch (final Exception ex) {
            LOGGER.error(FOUT_BIJ_HET_VERWERKEN_VAN_DE_ONTVANGEN_VAN_EEN_MET_REFERENTIENUMMER,
                stuurgegevens.getObjecttype(),
                stuurgegevens.getReferentienummer(), ex);
        } finally {
            clearMDC();
        }
    }

    @Override
    @Oneway
    @WebMethod(action = "verwerkNotificatieBijhoudingsplan")
    public void verwerkBijhoudingsplan(final FiatteringNotificeerBijhoudingsplan plan) {
        if (plan == null) {
            LOGGER.info("Geen valide BRP0100 notificatie bericht gestuurd, dit is vast een foutje...");
            return;
        }
        final GroepBerichtStuurgegevens stuurgegevens = plan.getStuurgegevens().getValue();
        try {

            updateMDC(stuurgegevens.getOntvangendePartij().getValue());

            final String zendendePartij;
            if (stuurgegevens.getZendendePartij().getValue() != null) {
                zendendePartij = stuurgegevens.getZendendePartij().getValue().getValue();
            } else {
                zendendePartij = GEEN_ZENDER;
            }

            final String ontvangendePartij;
            if (stuurgegevens.getOntvangendePartij().getValue() != null) {
                ontvangendePartij = stuurgegevens.getOntvangendePartij().getValue().getValue();
            } else {
                ontvangendePartij = GEEN_ONTVANGER;
            }

            LOGGER.info("Notificatie stuurgegevens: zendendeSysteem={},zendendePartij={},ontvangendePartij={}"
                    + ",referentienummer={}",
                stuurgegevens.getZendendeSysteem().getValue().getValue(),
                zendendePartij,
                ontvangendePartij,
                stuurgegevens.getReferentienummer().getValue().getValue());

            // TODO POC Bijhouding weer aanzetten.
            //            kennisgevingVerwerker.verwerkKennisgeving(plan);

        } catch (final Exception ex) {
            LOGGER.error(FOUT_BIJ_HET_VERWERKEN_VAN_DE_ONTVANGEN_VAN_EEN_MET_REFERENTIENUMMER,
                stuurgegevens.getObjecttype(),
                stuurgegevens.getReferentienummer(), ex);
        } finally {
            clearMDC();
        }
    }


    /**
     * Voegt waardes toe aan de {@link nl.bzk.brp.logging.MDC LoggingContext}.
     *
     * @param ontvanger ontvanger om toe te voegen
     */
    private void updateMDC(final PartijCode ontvanger) {
        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "kennisgeving-ontvanger");
        if (ontvanger != null) {
            MDC.put(MDCVeld.MDC_PARTIJ_CODE, ontvanger.getValue());
        }
    }

    /**
     * Ruimt waardes op uit de {@link nl.bzk.brp.logging.MDC LoggingContext}.
     */
    private void clearMDC() {
        MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
        MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
    }
}
