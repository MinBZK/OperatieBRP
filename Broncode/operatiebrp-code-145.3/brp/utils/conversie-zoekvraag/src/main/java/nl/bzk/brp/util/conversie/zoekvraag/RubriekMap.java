/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * Bevat de Rubriek mapping
 */
final class RubriekMap {

    private static final int LENGTE_2 = 2;
    private final Multimap<String, AttribuutElement> rubriekAttribuutMultimap = HashMultimap.create();
    private final Multimap<Integer, GroepElement> categorieGroepMultimap = HashMultimap.create();

    RubriekMap() {
        final ClassPathResource classPathResource = new ClassPathResource("rubriekmap.csv");
        try {
            try (InputStream is = classPathResource.getInputStream()) {
                parseStream(is);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    Set<AttribuutElement> converteerRubriek(final String maskerItem) {

        final String rubriekPadded = StringUtils.leftPad(maskerItem, 6, "0");
        final int rubrieknaamX = Integer.parseInt(rubriekPadded.substring(0, 2));
        final int rubrieknaamY = Integer.parseInt(rubriekPadded.substring(2, 4));
        final int rubrieknaamZ = Integer.parseInt(rubriekPadded.substring(4, 6));
        final int categorie = rubrieknaamX > 50 ? rubrieknaamX - 50 : rubrieknaamX;
        final boolean rubriekMetVerantwoording = rubrieknaamY == 72 || rubrieknaamY == 81 || rubrieknaamY == 82 || rubrieknaamY == 86;
        Set<AttribuutElement> attribuutList = Sets.newHashSet();
        if (rubriekMetVerantwoording) {
            final Collection<GroepElement> groepen = getGroepen(categorie);
            for (GroepElement groepElement : groepen) {
                for (AttribuutElement attribuutElement : groepElement.getAttributenInGroep()) {
                    if (!attribuutElement.isVerantwoording()) {
                        attribuutList.add(attribuutElement);
                    }
                }
            }
        } else {
            final String queryrubriek = String.format("%02d.%02d.%02d", categorie, rubrieknaamY, rubrieknaamZ);
            final Collection<AttribuutElement> attributen = getAttributen(queryrubriek);
            for (AttribuutElement attribuutElement : attributen) {
                attribuutList.add(attribuutElement);
            }
        }
        return attribuutList;
    }

    Collection<AttribuutElement> getAttributen(final String queryrubriek) {
        return rubriekAttribuutMultimap.get(queryrubriek);
    }

    Collection<GroepElement> getGroepen(final int categorie) {
        return categorieGroepMultimap.get(categorie);
    }

    private void parseStream(final InputStream is) throws IOException {
        final List<String> strings = IOUtils.readLines(is, StandardCharsets.UTF_8);
        for (String rubriek : strings) {
            final String[] split = StringUtils.split(rubriek, ",");
            if (split.length >= LENGTE_2) {
                final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(split[1]);
                rubriekAttribuutMultimap.put(split[0], attribuutElement);
                categorieGroepMultimap.put(Integer.parseInt(StringUtils.split(split[0], ".")[0]), attribuutElement.getGroep());
            }
        }
    }
}
