/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;

/**
 * Autorisatie opschoner.
 */
public final class Lo3AutorisatieOpschoner {

    /**
     * Opschonen autorisatie. Alle regels waarvoor een error is geregistreerd worden verwijderd.
     *
     * Nota: LET OP! Gebruikt de Logging.context
     * @param autorisatie autorisatie
     * @return opgeschoonde autorisatie
     */
    public Lo3Autorisatie opschonen(final Lo3Autorisatie autorisatie) {
        final Lo3Stapel<Lo3AutorisatieInhoud> autorisatieStapel = opschonen(autorisatie.getAutorisatieStapel());

        return autorisatieStapel == null ? null : new Lo3Autorisatie(autorisatieStapel);
    }

    private Lo3Stapel<Lo3AutorisatieInhoud> opschonen(final Lo3Stapel<Lo3AutorisatieInhoud> autorisatieStapel) {
        final List<Lo3Categorie<Lo3AutorisatieInhoud>> result = new ArrayList<>();

        for (final Lo3Categorie<Lo3AutorisatieInhoud> autorisatieRegel : autorisatieStapel) {
            if (!regelOpschonen(autorisatieRegel)) {
                result.add(autorisatieRegel);
            }
        }

        return result.isEmpty() ? null : new Lo3Stapel<>(result);
    }

    private boolean regelOpschonen(final Lo3Categorie<Lo3AutorisatieInhoud> autorisatieRegel) {
        final int volgnr = autorisatieRegel.getLo3Herkomst().getVoorkomen();

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (LogSeverity.ERROR.compareTo(regel.getSeverity()) >= 0 && regel.getLo3Herkomst().getVoorkomen() == volgnr) {
                return true;
            }
        }

        return false;
    }
}
