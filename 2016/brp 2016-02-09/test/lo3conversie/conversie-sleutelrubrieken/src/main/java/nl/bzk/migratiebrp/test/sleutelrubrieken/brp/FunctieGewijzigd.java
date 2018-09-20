/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken.brp;

import java.util.List;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.functies.FunctieGEWIJZIGD;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Wrapper om {@link FunctieGEWIJZIGD} tbv logging.
 */
public final class FunctieGewijzigd extends FunctieDelegate {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static StringBuilder logger;

    /**
     * Constructor.
     */
    public FunctieGewijzigd() {
        super(new FunctieGEWIJZIGD(), LOG);
    }

    /**
     * Zet de logger.
     *
     * @param logger
     *            De te zetten logger.
     */
    public static void setLogger(final StringBuilder logger) {
        FunctieGewijzigd.logger = logger;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final Expressie resultaat = super.pasToe(argumenten, context);

        if (logger != null) {
            if (argumenten.size() > 2) {
                final Expressie attribuutCode = argumenten.get(2);
                logger.append(attribuutCode.alsString()).append(": ");
            }

            final Expressie oudeWaarde = argumenten.get(0).evalueer(context);
            final Expressie nieuweWaarde = argumenten.get(1).evalueer(context);

            logger.append(oudeWaarde.evalueer(context).alsString())
                  .append(" -> ")
                  .append(nieuweWaarde.evalueer(context).alsString())
                  .append(" ==> ")
                  .append(resultaat.alsString())
                  .append("\n");
        }

        return resultaat;
    }
}
