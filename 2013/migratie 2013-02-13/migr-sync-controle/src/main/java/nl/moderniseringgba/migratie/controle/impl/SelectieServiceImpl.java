/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.controle.SelectieService;
import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.Opties;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.logging.service.LoggingService;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.springframework.stereotype.Component;

/**
 * Service voor het selecteren van geschikte PL-en voor de controle.
 */
@Component
public class SelectieServiceImpl implements SelectieService {

    private static final String RNI_GEMEENTE = "1999";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private LoggingService loggingService;

    @Override
    public final Set<Long> selecteerPLen(final Opties opties, final ControleRapport controleRapport) {
        final Set<Long> anummers = new HashSet<Long>();
        final Set<Long> brpAnummers = new HashSet<Long>();
        final Set<Long> gbavAnummers = new HashSet<Long>();

        switch (opties.getControleType()) {
            case ALLE_PERSONEN:
                // Criteria: datumtijd vanaf' ≤ datumtijdstempel < 'datumtijd tot'
                toevoegenAnrs(loggingService.findLogs(opties.getVanaf(), opties.getTot(), null), gbavAnummers,
                        brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), null), brpAnummers);
                break;
            case EEN_PERSOON:
                // Wordt (nog) niet ondersteund.
                LOGGER.warn("Controle met 1 persoon wordt niet ondersteund!");
                break;
            case GEMEENTE:
                // Criteria: datumtijd vanaf' ≤ datumtijdstempel < 'datumtijd tot' EN
                // Selecteer de PL-en waarvan de gemeente van inschrijving (rubriek 08.09.10) overeenkomt met de
                // ingevoerde Gemeentecode.
                toevoegenAnrs(loggingService.findLogs(opties.getVanaf(), opties.getTot(), opties.getGemeenteCode()),
                        gbavAnummers, brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(),
                                opties.getGemeenteCode()), brpAnummers);
                break;
            case RNI:
                // Criteria: datumtijd vanaf' ≤ datumtijdstempel < 'datumtijd tot' EN
                // Selecteer de PL-en waarvan de gemeente van inschrijving (rubriek 08.09.10) gelijk is aan de
                // gemeentecode van "RNI"; 1999.
                toevoegenAnrs(loggingService.findLogs(opties.getVanaf(), opties.getTot(), RNI_GEMEENTE),
                        gbavAnummers,
                        brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), RNI_GEMEENTE),
                        brpAnummers);
                break;
            default:
                break;
        }

        controleRapport.setAantalBrpPl(brpAnummers.size());
        controleRapport.setAantalGbavPl(gbavAnummers.size());
        anummers.addAll(brpAnummers);
        anummers.addAll(gbavAnummers);

        return anummers;
    }

    private void toevoegenAnrs(
            final List<Long> gbaAnrs,
            final Set<Long> gbavAnummers,
            final List<Long> brpAnrs,
            final Set<Long> brpAnummers) {
        if (gbaAnrs != null) {
            gbavAnummers.addAll(gbaAnrs);
        }

        if (brpAnrs != null) {
            brpAnummers.addAll(brpAnrs);
        }

    }
}
