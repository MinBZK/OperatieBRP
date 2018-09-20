/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;

/**
 * Interne class voor het doorgeven van de historie-rij context waarin de vergelijker aan het werk is.
 */
public final class HistorieContext {

    private static final HistorieContext LEGE_HISTORIE_CONTEXT = new HistorieContext(null, null);

    private final FormeleHistorie bestaandeHistorieRij;
    private final FormeleHistorie nieuweHistorieRij;

    private HistorieContext(final FormeleHistorie bestaandeHistorieRij, final FormeleHistorie nieuweHistorieRij) {
        this.bestaandeHistorieRij = bestaandeHistorieRij;
        this.nieuweHistorieRij = nieuweHistorieRij;
    }

    /**
     * @return Geeft de bestaande historie rij terug
     */
    public FormeleHistorie getBestaandeHistorieRij() {
        return bestaandeHistorieRij;
    }

    /**
     * @return Geeft de nieuwe historie rij terug
     */
    public FormeleHistorie getNieuweHistorieRij() {
        return nieuweHistorieRij;
    }

    /**
     * @return true als de {@link HistorieContext} gevuld is met of de bestaande historie of met de nieuwe historie of
     *         allebei.
     */
    public boolean isGevuld() {
        return bestaandeHistorieRij != null || nieuweHistorieRij != null;
    }

    /**
     * Bepaalt een nieuwe historie context als er nog geen omringende context is.
     * 
     * @param omringendeHistorieContext
     *            de evt. omringende context.
     * @param oudeEntiteit
     *            de bestaande (historische) entiteit
     * @param nieuweEntiteit
     *            de nieuwe (historische) entiteit
     * @return als de oudeEntiteit en/of nieuweEntiteit niet null zijn en de {@link FormeleHistorie} interface
     *         implementeren, dan een nieuw context. Als de omringende context niet null is en deze is gevuld, dan wordt
     *         deze context terug gegeven. Als deze situaties niet van toepassing dan wordt er een leeg context terug
     *         gegeven.
     * 
     */
    public static HistorieContext bepaalNieuweHistorieContext(
        final HistorieContext omringendeHistorieContext,
        final Object oudeEntiteit,
        final Object nieuweEntiteit)
    {
        final HistorieContext result;

        if (omringendeHistorieContext != null && omringendeHistorieContext.isGevuld()) {
            result = omringendeHistorieContext;
        } else if (oudeEntiteit instanceof FormeleHistorie && nieuweEntiteit instanceof FormeleHistorie) {
            result = new HistorieContext((FormeleHistorie) oudeEntiteit, (FormeleHistorie) nieuweEntiteit);
        } else if (nieuweEntiteit instanceof FormeleHistorie && oudeEntiteit == null) {
            result = new HistorieContext(null, (FormeleHistorie) nieuweEntiteit);
        } else if (oudeEntiteit instanceof FormeleHistorie && nieuweEntiteit == null) {
            result = new HistorieContext((FormeleHistorie) oudeEntiteit, null);
        } else {
            result = LEGE_HISTORIE_CONTEXT;
        }

        return result;
    }
}
