/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.bezemwagen.service.impl;

import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.service.AdministratieveHandelingenOverslaanService;
import nl.bzk.brp.levering.bezemwagen.jms.AdministratieveHandelingVerwerker;
import nl.bzk.brp.levering.bezemwagen.service.AdministratieveHandelingPublicatieService;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.OngeleverdeAdministratieveHandelingRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

/**
 * De Class AdministratieveHandelingPublicatieServiceImpl vormt de implementatie van de interface
 * AdministratieveHandelingPublicatieService.
 */
@Service
@ManagedResource(objectName = "nl.bzk.brp.levering.bezemwagen:name=Bezemwagen",
        description = "De bezemwagen plaats opgeleverde administratieve handelingen op de queue om (opnieuw) te verwerken.")

public class AdministratieveHandelingPublicatieServiceImpl implements AdministratieveHandelingPublicatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private OngeleverdeAdministratieveHandelingRepository ongeleverdeAdministratieveHandelingRepository;

    @Inject
    private AdministratieveHandelingVerwerker administratieveHandelingVerwerker;

    @Inject
    private AdministratieveHandelingenOverslaanService administratieveHandelingenOverslaanService;

    @Value("${aantal.administratieve.handelingen.per.keer:#{null}}")
    private Integer aantalAdministratieveHandelingenPerKeer;

    @Override
    @ManagedOperation(description = "Plaats onverwerkte administratieve handelingen op queue.")
    public final void plaatsOnverwerkteAdministratieveHandelingenOpQueue() {

        MDC.put(MDCVeld.MDC_APPLICATIE_NAAM, "bezemwagen");

        try {
            final List<BigInteger> onverwerkteAdministratieveHandelingen = haalOnverwerkteAdministratieveHandelingenOp();
            plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);

            if (onverwerkteAdministratieveHandelingen.size() > 0) {
                LOGGER.info(
                    onverwerkteAdministratieveHandelingen.size()
                            + " onverwerkte administratieve handelingen gevonden "
                            + "en op de JMS queue gezet tbv mutaties zodat deze verwerkt kunnen worden.");

            }
        } finally {
            MDC.remove(MDCVeld.MDC_APPLICATIE_NAAM);
        }

    }

    /**
     * Plaats de identifiers van de Administratieve Handelingen op de JMS queue.
     *
     * @param onverwerkteAdministratieveHandelingen
     *            de onverwerkte administratieve handelingen
     */
    private void plaatsAdministratieveHandelingenOpQueue(final List<BigInteger> onverwerkteAdministratieveHandelingen) {
        administratieveHandelingVerwerker.plaatsAdministratieveHandelingenOpQueue(onverwerkteAdministratieveHandelingen);
    }

    /**
     * Haal onverwerkte administratieve handelingen op uit de Administratieve Handeling Repository.
     *
     * @return lijst met Big Integers die de identifiers voorstellen van de Administratieve Handelingen
     */
    private List<BigInteger> haalOnverwerkteAdministratieveHandelingenOp() {
        final List<SoortAdministratieveHandeling> soortAdministratieveHandelingenDieOvergeslagenMoetenWorden =
                administratieveHandelingenOverslaanService.geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

        return ongeleverdeAdministratieveHandelingRepository.haalOnverwerkteAdministratieveHandelingenOp(
            soortAdministratieveHandelingenDieOvergeslagenMoetenWorden,
            aantalAdministratieveHandelingenPerKeer);
    }

}
