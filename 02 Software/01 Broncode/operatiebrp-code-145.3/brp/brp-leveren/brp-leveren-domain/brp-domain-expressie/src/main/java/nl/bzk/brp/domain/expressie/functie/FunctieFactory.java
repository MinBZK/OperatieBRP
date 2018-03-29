/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * Factory voor Functieberekening implementaties.
 */
public final class FunctieFactory {

    private static final FunctieFactory INSTANCE = new FunctieFactory();
    private final Map<String, Functie> keywordMapping = Maps.newHashMap();

    /**
     * Constructor.
     */
    private FunctieFactory() {
        try (final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan(FunctieFactory.class.getPackage().getName());
            context.refresh();
            final Collection<Functie> beansOfType = context.getBeansOfType(Functie.class).values();
            for (Functie functie : beansOfType) {
                final FunctieKeyword value = functie.getClass().getAnnotation(FunctieKeyword.class);
                Assert.notNull(value, "Functie keyword mag niet null zijn.");
                keywordMapping.putIfAbsent(value.value(), functie);
            }
        }
    }

    /**
     * Geeft de Functieberekening obv een Keyword.
     * @param keyword de key voor het opvragen van een Functieberekening
     * @return de gevonden Functieberekening
     */
    public static Functie geefFunctie(final String keyword) {
        final Functie functie = INSTANCE.keywordMapping.get(keyword);
        if (functie == null) {
            throw new ExpressieRuntimeException("Functieberekening niet gevonden voor key: " + keyword);
        }
        return functie;
    }
}
