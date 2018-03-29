/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken.brp;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.functie.FunctieFactory;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Wrapper om {@link nl.bzk.brp.domain.expressie.functie.FunctieGewijzigd} tbv logging.
 */
public final class FunctieGewijzigd extends FunctieDelegate {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static StringBuilder logger;

    /**
     * Constructor.
     */
    public FunctieGewijzigd() {
        super(FunctieFactory.geefFunctie("GEWIJZIGD"), LOG);
    }

    /**
     * Zet de logger.
     *
     * @param logger
     *            De te zetten logger.
     */
    public static void setLogger(final StringBuilder logger) {
        nl.bzk.migratiebrp.test.sleutelrubrieken.brp.FunctieGewijzigd.logger = logger;
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie resultaat = super.evalueer(argumenten, context);

        if (logger != null) {
            if (argumenten.size() > 2) {
                final Expressie attribuutCode = argumenten.get(2);
                logger.append(attribuutCode.alsString()).append(": ");
            }
            try {
                final Context newContext = new Context(context);
                newContext.definieer(
                    ExpressieTaalConstanten.CONTEXT_PERSOON_OUD,
                    new MetaObjectLiteral(context.<Persoonslijst>getProperty("oud").getMetaObject(), ExpressieType.BRP_METAOBJECT));
                newContext.definieer(
                    ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW,
                    new MetaObjectLiteral(context.<Persoonslijst>getProperty("nieuw").getMetaObject(), ExpressieType.BRP_METAOBJECT));

                final Expressie oudeWaarde = argumenten.get(0).evalueer(newContext);
                final Expressie nieuweWaarde = argumenten.get(1).evalueer(newContext);

                logger.append(oudeWaarde.evalueer(context).alsString())
                      .append(" -> ")
                      .append(nieuweWaarde.evalueer(context).alsString())
                      .append(" ==> ")
                      .append(resultaat.alsString())
                      .append("\n");
            } catch (final Exception e) {
                logger.append("Opgedeelde expressie kan niet worden geevalueert. Complete expressie geeft geen fouten");
            }
        }

        return resultaat;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }
}
