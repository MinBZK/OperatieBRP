/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Utility class voor het parsen en evalueren van BRP-expressies.
 */
public final class BRPExpressies {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Private constructor voor utility class.
     */
    private BRPExpressies() {
    }

    /**
     * Evalueert de expressie voor de gegeven persoonsgegevens.
     * @param expressie De te evalueren expressie.
     * @param persoonslijst De persoon (zonder historie).
     * @return Resultaat van de evaluatie.
     * @throws ExpressieException als expressie evaluatie faalt
     */
    public static Expressie evalueer(final Expressie expressie, final Persoonslijst persoonslijst) throws ExpressieException {
        return evalueer(expressie, maakPersoonContext(persoonslijst));
    }

    /**
     * Evalueert de expressie voor de gegeven persoonsgegevens.
     * @param expressie De te evalueren expressie.
     * @param persoonslijst De persoon (zonder historie).
     * @param datumStartSelectie De datum waarop de selectierun gestart is
     * @param selectieLijst selectieLijst
     * @return Resultaat van de evaluatie.
     * @throws ExpressieException als expressie evaluatie faalt
     */
    public static Expressie evalueerMetSelectieDatumEnSelectielijst(final Expressie expressie, final Persoonslijst persoonslijst,
                                                                    final int datumStartSelectie, final SelectieLijst selectieLijst)
            throws ExpressieException {
        final Context context = maakPersoonContext(persoonslijst);
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, datumStartSelectie);
        context.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_SELECTIE_LIJST, selectieLijst);
        return evalueer(expressie, context);
    }


    /**
     * Evalueert de expressie voor de gegeven persoonsgegevens.
     * @param expressie De te evalueren expressie.
     * @param persoonslijst De persoon (zonder historie).
     * @param datumStartSelectie De datum waarop de selectierun gestart is
     * @return Resultaat van de evaluatie.
     * @throws ExpressieException als expressie evaluatie faalt
     */
    public static Expressie evalueerMetSelectieDatum(final Expressie expressie, final Persoonslijst persoonslijst,
                                                     final int datumStartSelectie)
            throws ExpressieException {
        final Context context = maakPersoonContext(persoonslijst);
        context.addProperty(ExpressieTaalConstanten.PROPERTY_DATUM_START_SELECTIE, datumStartSelectie);
        return evalueer(expressie, context);
    }

    /**
     * Evalueer het attenderingsCriterium.
     * @param expressie de attenderingsCriterium expressie
     * @param oud de oude situatie van de persoon
     * @param nieuw de nieuwe situatie van de persoon
     * @return de resultaat expressie
     */
    public static Expressie evalueerAttenderingsCriterium(final Expressie expressie, final Persoonslijst oud, final Persoonslijst nieuw) {
        final Context context = new Context();

        if (oud != null) {
            //indien er geen vorige handeling bestaat dan is oud NULL
            context.addProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD, oud);
        }
        context.addProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW, nieuw);
        return expressie.evalueer(context);
    }


    /**
     * Maakt een context voor standaard persoon evaluaties.
     * @return de context.
     */
    private static Context maakPersoonContext(final Persoonslijst persoonslijst) {
        final Context context = new Context();
        context.definieer(ExpressieTaalConstanten.CONTEXT_PERSOON,
                new MetaObjectLiteral(persoonslijst.getMetaObject(), ExpressieType.BRP_METAOBJECT));
        context.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, persoonslijst);
        return context;
    }


    /**
     * Evalueert de expressie voor de gegeven persoon.
     * @param expressie De te evalueren expressie.
     * @param context Gedefinieerde symbolische namen met hun waarden.
     * @return Resultaat van de evaluatie.
     */
    private static Expressie evalueer(final Expressie expressie, final Context context) throws ExpressieException {
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("Evalueer {}", expressie);
        }
        try {
            final Expressie result = expressie.evalueer(context);
            if (ExpressieLogger.IS_DEBUG_ENABLED) {
                LOGGER.debug("Resultaat: {}", result);
            }
            return result;
        } catch (final ExpressieRuntimeException e) {
            LOGGER.info("Expressie evaluatie foutmelding = {}", e.getMessage());
            throw new ExpressieException("Fout bij evaluatie expressie: " + expressie.toString(), e);
        }
    }
}
