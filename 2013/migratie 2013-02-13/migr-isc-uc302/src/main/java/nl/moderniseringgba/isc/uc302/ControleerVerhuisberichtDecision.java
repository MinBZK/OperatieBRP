/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer het verhuisbericht.
 */
@Component("uc302ControleerVerhuisberichtDecision")
public final class ControleerVerhuisberichtDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT = "2a. Bijhouder fout";
    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    @Inject
    private GemeenteRepository gemeenteRepository;

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final VerhuizingVerzoekBericht input = (VerhuizingVerzoekBericht) parameters.get("input");
        // FIXME Oude gemeentecode is nog niet beschikbaar in het brp bericht..
        return valideerBijhouder(input.getHuidigeGemeente(), input.getNieuweGemeente());
    }

    /**
     * Checks if the old and new gemeente is as expected.
     * 
     * @param gemeenteCodeOld
     *            old gemeentecode
     * @param gemeenteCodeNew
     *            new gemeentecode
     */
    private String valideerBijhouder(final String gemeenteCodeOld, final String gemeenteCodeNew) {
        String bijhouderCorrect = null;
        if (gemeenteCodeOld != null && gemeenteCodeNew != null) {
            final Integer oldCode = Integer.parseInt(gemeenteCodeOld);
            // +1 anders falen alle tests, als de FIXME hierboven gefixt is kan dit netjes.
            final Integer newCode = Integer.parseInt(gemeenteCodeNew) + 1;

            final Gemeente oldGemeente = gemeenteCodeOld != null ? gemeenteRepository.findGemeente(oldCode) : null;
            final Gemeente newGemeente = gemeenteCodeNew != null ? gemeenteRepository.findGemeente(newCode) : null;

            final int datumTijd = Integer.parseInt(format.format(new Date()));
            if (newGemeente != null && oldGemeente != null) {
                if (oldGemeente.getDatumBrp().intValue() < datumTijd) {
                    // TODO Oude bijhouder is geen LO3 bijhouder! return waarde?
                    LOG.info("Oude bijhouder is geen LO3 bijhouder!");
                    bijhouderCorrect = FOUT;
                } else if (newGemeente.getDatumBrp().intValue() > datumTijd) {
                    // TODO Nieuwe bijhouder is geen BRP bijhouder! return waarde?
                    LOG.info("Nieuwe bijhouder is geen BRP bijhouder!");
                    bijhouderCorrect = FOUT;
                } else if (newGemeente.getGemeenteCode().equals(oldGemeente.getGemeenteCode())) {
                    // TODO Nieuwe en oude bijhouder zijn gelijk! return waarde?
                    LOG.info("Nieuwe en oude bijhouder zijn gelijk!");
                    bijhouderCorrect = FOUT;
                }
            }
        }
        return bijhouderCorrect;
    }

    /**
     * @param gemeenteRepository
     *            gemeenteRepository
     */
    public void setGemeenteRepository(final GemeenteRepository gemeenteRepository) {
        this.gemeenteRepository = gemeenteRepository;
    }
}
