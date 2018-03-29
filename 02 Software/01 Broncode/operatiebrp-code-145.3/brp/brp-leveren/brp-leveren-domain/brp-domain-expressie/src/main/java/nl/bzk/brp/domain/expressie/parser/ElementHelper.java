/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import nl.bzk.brp.domain.expressie.element.ElementExpressie;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import org.apache.commons.lang3.StringUtils;

/**
 * Parser delegate.
 */
final class ElementHelper {

    private ElementHelper() {

    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param context de evaluatiecontext
     * @return het resultaat
     */
    static Expressie visitElement(final BRPExpressietaalParser.ElementContext ctx, final Context context) {
        final String object;
        final String path = ctx.element_path().getText();
        final String[] splitted = path.split("\\.");
        //controleer of identifier vewijst naar declaratie
        final boolean variabele = context.isDeclaratie(path);
        if (variabele) {
            return new VariabeleExpressie(path);
        }
        final String potentialObject = splitted[0];
        final ExpressieType objectType = context.zoekType(potentialObject);
        if (objectType == null) {
            object = ExpressieTaalConstanten.CONTEXT_PERSOON;
        } else {
            object = potentialObject;
        }
        final String attribuutNaam;
        if (objectType == null) {
            attribuutNaam = path;
        } else {
            attribuutNaam = StringUtils.replace(path, potentialObject + ".", "");
        }
        return new ElementExpressie(object, attribuutNaam);
    }
}
