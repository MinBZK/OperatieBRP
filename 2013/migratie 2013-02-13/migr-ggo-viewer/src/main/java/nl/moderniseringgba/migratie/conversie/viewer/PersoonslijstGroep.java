/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;
import nl.moderniseringgba.migratie.conversie.viewer.vergelijk.StapelVergelijking;

/**
 * Ons Model, bevattende alle data rondom een persoonslijst die we op het scherm weer willen geven, zoals de lo3 en BRP
 * persoonslijst, de bijbehorende getriggerde precondities en BCM regels, etc.
 */
public class PersoonslijstGroep {
    private final Lo3Persoonslijst lo3Persoonslijst;
    private final BrpPersoonslijst brpPersoonslijst;
    private final Lo3Persoonslijst teruggeconverteerd;
    private final List<StapelVergelijking> vergelijking;
    private final List<LogRegel> precondities;
    private final List<FoutRegel> bcmSignaleringen;

    /**
     * @param lo3Persoonslijst
     *            Persoonslijst in lo3 formaat.
     * @param precondities
     *            De precondities die gefaalt zijn.
     * @param bcmSignaleringen
     *            De BCM Signaleringen die getriggered zijn.
     * @param brpPersoonslijst
     *            De persoonslijst in brp formaat.
     * @param teruggeconverteerd
     *            De persoonslijst in lo3 formaat terugconverteerd van een brp pl.
     * @param vergelijking
     *            De verschillen tussen de lo3Persoonslijst en teruggeconverteerd.
     */
    public PersoonslijstGroep(
            final Lo3Persoonslijst lo3Persoonslijst,
            final BrpPersoonslijst brpPersoonslijst,
            final Lo3Persoonslijst teruggeconverteerd,
            final List<StapelVergelijking> vergelijking,
            final List<LogRegel> precondities,
            final List<FoutRegel> bcmSignaleringen) {

        this.lo3Persoonslijst = lo3Persoonslijst;
        this.brpPersoonslijst = brpPersoonslijst;
        this.teruggeconverteerd = teruggeconverteerd;
        this.vergelijking = vergelijking;
        this.precondities = precondities;
        this.bcmSignaleringen = bcmSignaleringen;
    }

    /**
     * @return the lo3Persoonslijst
     */
    public final Lo3Persoonslijst getLo3Persoonslijst() {
        return lo3Persoonslijst;
    }

    /**
     * @return the brpPersoonslijst
     */
    public final BrpPersoonslijst getBrpPersoonslijst() {
        return brpPersoonslijst;
    }

    /**
     * @return the teruggeconverteerd
     */
    public final Lo3Persoonslijst getTeruggeconverteerd() {
        return teruggeconverteerd;
    }

    /**
     * @return the vergelijking
     */
    public final List<StapelVergelijking> getVergelijking() {
        return vergelijking;
    }

    /**
     * @return the precondities
     */
    public final List<LogRegel> getPrecondities() {
        return precondities;
    }

    /**
     * @return the bcmSignaleringen
     */
    public final List<FoutRegel> getBcmSignaleringen() {
        return bcmSignaleringen;
    }

}
