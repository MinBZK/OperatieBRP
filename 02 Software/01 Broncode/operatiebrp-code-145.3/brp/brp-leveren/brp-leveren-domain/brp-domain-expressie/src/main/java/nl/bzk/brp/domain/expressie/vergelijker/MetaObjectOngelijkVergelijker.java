/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import org.springframework.stereotype.Component;

/**
 * {@link OperatorType#ONGELIJK} vergelijker voor {@link ExpressieType#BRP_METAOBJECT} expressies.
 */
@Component
@VergelijkerConfig(operator = OperatorType.ONGELIJK, typeSupport = ExpressieType.BRP_METAOBJECT)
final class MetaObjectOngelijkVergelijker implements Vergelijker<MetaObjectLiteral, MetaObjectLiteral> {

    @Override
    public Expressie apply(final MetaObjectLiteral l, final MetaObjectLiteral r) {
        return !l.getMetaObject().deepEquals(r.getMetaObject()) ? BooleanLiteral.WAAR : BooleanLiteral.ONWAAR;
    }
}
