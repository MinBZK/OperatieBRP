/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.berekening;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Factoryklasse voor het vinden van berekenfuncties
 */
public final class BerekeningFactory {

    private static final BerekeningFactory INSTANCE = new BerekeningFactory();
    private final List<Berekening<Expressie, Expressie>> rekenlijst = Lists.newLinkedList();

    private BerekeningFactory() {
        try (final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan(BerekeningFactory.class.getPackage().getName());
            context.refresh();
            final Collection<Berekening> vergelijkers = context.getBeansOfType(Berekening.class).values();
            for (final Berekening<Expressie, Expressie> vergelijker : vergelijkers) {
                final BerekeningConfig berekeningConfig = vergelijker.getClass().getAnnotation(BerekeningConfig.class);
                Assert.notNull(berekeningConfig, "@Operatie annotatie ontbreekt");
                rekenlijst.add(vergelijker);
            }
        }
    }

    /**
     * Factory method voor een BerekeningBiFunction.
     * @param expressieType het type expressie dat vergeleken wordt (linkeroperand)
     * @param expressieTypeRechts het type expressie dat vergeleken wordt (rechteroperand)
     * @param operatorType het type operator in de vergelijking
     * @return berekening functie
     */
    public static Berekening<Expressie, Expressie> get(final ExpressieType expressieType, final ExpressieType expressieTypeRechts,
                                                       final OperatorType operatorType) {

        for (Berekening<Expressie, Expressie> function : INSTANCE.rekenlijst) {
            final BerekeningConfig berekeningConfig = function.getClass().getAnnotation(BerekeningConfig.class);
            if (berekeningConfig.operator() == operatorType
                    && berekeningConfig.typeLinks() == expressieType && berekeningConfig.typeRechts() == expressieTypeRechts) {
                return function;
            }
        }
        return null;
    }

}
