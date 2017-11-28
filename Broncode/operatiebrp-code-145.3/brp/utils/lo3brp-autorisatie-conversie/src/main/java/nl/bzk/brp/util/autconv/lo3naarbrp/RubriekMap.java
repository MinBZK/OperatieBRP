/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Bevat de Rubriek mapping
 */
@Component
final class RubriekMap {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int LENGTE_2 = 2;
    private final Multimap<String, AttribuutElement> rubriekAttribuutMultimap = HashMultimap.create();
    private final Multimap<Integer, GroepElement> categorieGroepMultimap = HashMultimap.create();

    private RubriekMap() {
        final ClassPathResource classPathResource = new ClassPathResource("rubriekmap.csv");
        try {
            try (InputStream is = classPathResource.getInputStream()) {
                parseStream(is);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @PostConstruct
    void logMapping() {
        for (String key : rubriekAttribuutMultimap.keySet()) {
            LOGGER.debug("rubriek {} mapt op {}", key, rubriekAttribuutMultimap.get(key));
        }
        for (Integer key : categorieGroepMultimap.keySet()) {
            LOGGER.debug("x {} mapt op {}", key, categorieGroepMultimap.get(key));
        }
    }

    private void parseStream(final InputStream is) throws IOException {
        final List<String> strings = IOUtils.readLines(is);
        for (String rubriek : strings) {
            final String[] split = StringUtils.split(rubriek, ",");
            if (split.length >= LENGTE_2) {
                final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(split[1]);
                rubriekAttribuutMultimap.put(split[0], attribuutElement);
                categorieGroepMultimap.put(Integer.parseInt(StringUtils.split(split[0], ".")[0]), attribuutElement.getGroep());
            }
        }
    }

    Collection<AttribuutElement> getAttributen(final String queryrubriek) {
        return rubriekAttribuutMultimap.get(queryrubriek);
    }

    Collection<GroepElement> getGroepen(final int categorie) {
        return categorieGroepMultimap.get(categorie);
    }
}
