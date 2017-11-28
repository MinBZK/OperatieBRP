/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.vergelijker;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.OperatorType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Factoryklasse voor {@link Vergelijker} instanties.
 */
public final class VergelijkerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final VergelijkerFactory INSTANCE = new VergelijkerFactory();
    private final Map<String, Vergelijker<Expressie, Expressie>> vergelijkerMap = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    private VergelijkerFactory() {

        try (final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan(VergelijkerFactory.class.getPackage().getName());
            context.refresh();
            final Collection<Vergelijker> vergelijkers = context.getBeansOfType(Vergelijker.class).values();
            for (final Vergelijker<Expressie, Expressie> vergelijker : vergelijkers) {
                mapVergelijker(vergelijker);
            }
        }

        vergelijkerMap.keySet().stream().sorted().forEach(LOGGER::debug);
    }

    /**
     * Factory method voor een {@link BiFunction} waarbij geldt dat het type expressie
     * in de vergelijking gelijk is..
     * @param expressieType het type expressie dat vergeleken wordt
     * @param operatorType het type operator in de vergelijking
     * @return een vergelijker
     */
    public static Vergelijker<Expressie, Expressie> get(final ExpressieType expressieType, final OperatorType operatorType) {
       return get(expressieType, expressieType, operatorType);
    }

    /**
     * Factory method voor een {@link BiFunction} waarbij geldt dat het type expressie operands
     * kan worden opgegeven.
     *
     * @param expressieTypeLinks het {@link ExpressieType} van de linker {@link Expressie}
     * @param expressieTypeRechts het {@link ExpressieType} van de rechter {@link Expressie}
     * @param operatorType het type operator in de vergelijking
     * @return een vergelijker
     */
    public static Vergelijker<Expressie, Expressie> get(final ExpressieType expressieTypeLinks,
                                                        final ExpressieType expressieTypeRechts, final OperatorType operatorType) {
        if (expressieTypeLinks == ExpressieType.NULL  || expressieTypeRechts == ExpressieType.NULL) {
            return INSTANCE.vergelijkerMap.get(maakKey(ExpressieType.NULL, ExpressieType.NULL, operatorType));
        }
        final Vergelijker<Expressie, Expressie> vergelijker = INSTANCE.vergelijkerMap.get(maakKey(expressieTypeLinks, expressieTypeRechts, operatorType));
        if (vergelijker == null) {
            throw new ExpressieRuntimeException(String.format("Operand vergelijking niet mogelijk "
                    + "[links: %s, rechts: %s]", expressieTypeLinks, expressieTypeRechts));
        }
        return vergelijker;
    }


    private static String maakKey(final ExpressieType expressieTypeLinks, final ExpressieType expressieTypeRechts,
                                  final OperatorType operatorType) {
        return expressieTypeLinks.name() + "_" + operatorType.name() + "_" + expressieTypeRechts.name();
    }

    private void mapVergelijker(final Vergelijker<Expressie, Expressie> vergelijker) {
        final VergelijkerConfig vergelijkerConfig = vergelijker.getClass().getAnnotation(VergelijkerConfig.class);
        Assert.notNull(vergelijkerConfig, "@Operatie annotatie ontbreekt");
        for (final ExpressieType expressieTypeList : vergelijkerConfig.typeSupport()) {
            for (final ExpressieType expressieTypeList2 : vergelijkerConfig.typeSupport()) {
                vergelijkerMap.put(maakKey(expressieTypeList, expressieTypeList2, vergelijkerConfig.operator()), vergelijker);
            }
        }
    }
}
