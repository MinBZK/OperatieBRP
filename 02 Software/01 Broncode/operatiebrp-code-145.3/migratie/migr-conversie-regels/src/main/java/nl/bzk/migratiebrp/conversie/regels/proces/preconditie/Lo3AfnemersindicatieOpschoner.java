/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;

/**
 * Afnemersindicatie opschoner.
 */
public final class Lo3AfnemersindicatieOpschoner {

    /**
     * Opschonen afnemersindicaties. Alle stapels waarvoor een error is worden verwijderd.
     *
     * Nota: LET OP! Gebruikt de Logging.context
     * @param afnemersindicatie afnemersindicaties
     * @return opgeschoonde afnemersindicaties
     */
    public Lo3Afnemersindicatie opschonen(final Lo3Afnemersindicatie afnemersindicatie) {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels = opschonen(afnemersindicatie.getAfnemersindicatieStapels());

        return new Lo3Afnemersindicatie(afnemersindicatie.getANummer(), afnemersindicatieStapels);
    }

    private List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> opschonen(final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels) {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> result = new ArrayList<>();

        for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel : afnemersindicatieStapels) {
            if (!stapelBevatError(afnemersindicatieStapel)) {
                result.add(afnemersindicatieStapel);
            }
        }
        return result;
    }

    private boolean stapelBevatError(final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel) {
        final int stapel = afnemersindicatieStapel.get(0).getLo3Herkomst().getStapel();

        for (final LogRegel regel : Logging.getLogging().getRegels()) {
            if (regel.hasSeverityLevelError() && regel.getLo3Herkomst().getStapel() == stapel) {
                return true;
            }
        }

        return false;
    }
}
