/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.geefmedebewoners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Util klasse voor het converteren van {@link nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek.Identificatiecriteria
 * Identificatiecriteria} naar {@link nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek.ZoekCriteria}.
 */
final class ZoekCriteriaConverteerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Map<ZoekCriteriaType, List<Pair<ZoekCriteriaElement, Field>>> ZOEK_CRITERIA_NAAR_VELD_LIJST = Maps.newHashMap();

    static {
        for (final Field veld : GeefMedebewonersVerzoek.Identificatiecriteria.class.getDeclaredFields()) {
            final ZoekCriteriaElement zoekCriteriaElement = veld.getAnnotation(ZoekCriteriaElement.class);
            if (zoekCriteriaElement != null) {
                if (!ZOEK_CRITERIA_NAAR_VELD_LIJST.containsKey(zoekCriteriaElement.type())) {
                    ZOEK_CRITERIA_NAAR_VELD_LIJST.put(zoekCriteriaElement.type(), Lists.newArrayList());
                }
                ZOEK_CRITERIA_NAAR_VELD_LIJST.get(zoekCriteriaElement.type()).add(Pair.of(zoekCriteriaElement, veld));
                veld.setAccessible(true);
            }
        }
    }

    private ZoekCriteriaConverteerUtil() {

    }

    /**
     * @param identificatiecriteria identificatiecriteria
     * @param zoekCriteriaType zoekCriteriaType
     */
    static Collection<ZoekPersoonGeneriekVerzoek.ZoekCriteria> converteerIdentificatieCriteria(
            final GeefMedebewonersVerzoek.Identificatiecriteria identificatiecriteria, final ZoekCriteriaType zoekCriteriaType) {
        final Set<ZoekPersoonGeneriekVerzoek.ZoekCriteria> zoekCriteriaSet = Sets.newHashSet();

        final List<Pair<ZoekCriteriaElement, Field>> zoekCriteriaElementVeldParen = ZOEK_CRITERIA_NAAR_VELD_LIJST.get(zoekCriteriaType);
        for (final Pair<ZoekCriteriaElement, Field> paar : zoekCriteriaElementVeldParen) {
            final String element = paar.getLeft().element().getNaam();
            final Field veld = paar.getRight();
            final ZoekPersoonGeneriekVerzoek.ZoekCriteria zoekCriteria = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
            zoekCriteria.setElementNaam(element);
            try {
                final Object waarde = veld.get(identificatiecriteria);
                if (waarde != null) {
                    zoekCriteria.setWaarde((String) waarde);
                    // Ontwerpbeslissing: niet slim zoeken voor Geef Medebewoners.
                    zoekCriteria.setZoekOptie(Zoekoptie.EXACT);
                    zoekCriteriaSet.add(zoekCriteria);
                }
            } catch (final IllegalAccessException e) {
                LOGGER.error("Fout bij het converteren van element {}. Element wordt genegeerd.", element, e);
            }
        }
        return zoekCriteriaSet;
    }
}
