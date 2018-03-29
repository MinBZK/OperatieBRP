/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.apache.commons.lang3.StringUtils;

/**
 * Representatie van dienstbundelgroep in de dsl.
 */
final class DienstbundelGroepParser {


    private static final String STER = "*";
    private static final Map<String, GroepElement> ALLE_GROEPEN = new HashMap<>();

    static {
        final Set<String> excludes =
                Sets.newHashSet("Persoon.Cache.Standaard", "PartijenInOnderzoek.Standaard", "PartijRol.Standaard", "Ouder.OuderlijkGezag");
        for (final GroepElement groepElement : ElementHelper.getGroepen()) {
            if (excludes.contains(groepElement.getNaam())) {
                continue;
            }
            ALLE_GROEPEN.put(groepElement.getNaam(), groepElement);
        }
    }

    DienstbundelGroepParser() {}

    /**
     * Parsed de dienstbundelgroepattribuut
     * @param dienstbundel de parent dienstbundel
     * @param groepDsl de groepen
     */

     void parse(final Dienstbundel dienstbundel, final String groepDsl) {
        if (groepDsl == null) {
            return;
        }
        final String[] groepDslWaarden = StringUtils.split(groepDsl, ",");

        final Set<GroepElement> excludeList = Sets.newHashSet();
        final Map<GroepElement, DienstbundelGroep> groepDslList = Maps.newHashMap();
        boolean sterGroep = false;
        boolean materieel = false;
        boolean formeel = false;
        boolean verantwoording = false;
        for (final String groepDslWaarde : groepDslWaarden) {

            String elementNaam = StringUtils.trimToNull(groepDslWaarde);

            boolean excludeGroep = false;

            if (elementNaam.startsWith("!")) {
                excludeGroep = true;
                elementNaam = elementNaam.substring(1);
            }

            if (elementNaam.contains("/")) {
                final String[] split = StringUtils.split(elementNaam, "/");
                materieel = split[1].contains("M");
                formeel = split[1].contains("F");
                verantwoording = split[1].contains("V");
                elementNaam = StringUtils.trimToNull(split[0]);
            }

            final GroepElement element;
            if (STER.equals(elementNaam)) {
                sterGroep = true;
            } else if (!ALLE_GROEPEN.containsKey(elementNaam)) {
                throw new AssertionError("Onbekende groep:" + elementNaam);
            } else {
                element = ALLE_GROEPEN.get(elementNaam);
                if (excludeGroep) {
                    excludeList.add(element);
                } else {
                    final DienstbundelGroep dienstbundelGroep =
                            new DienstbundelGroep(dienstbundel, element.getElement(), formeel, materieel, verantwoording);
                    groepDslList.put(element, dienstbundelGroep);
                }
            }
        }

        //als er een * groep bestaat, voeg dan de overige groep toe indien deze niet reeds bestaat
        if (sterGroep) {
            for (GroepElement groepEnum : ALLE_GROEPEN.values()) {
                if (!groepDslList.containsKey(groepEnum) && !excludeList.contains(groepEnum)) {
                    final DienstbundelGroep dienstbundelGroep
                            = new DienstbundelGroep(dienstbundel, groepEnum.getElement(), formeel, materieel, verantwoording);
                    dienstbundel.getDienstbundelGroepSet().add(dienstbundelGroep);
                    groepDslList.put(groepEnum, dienstbundelGroep);
                }
            }
        }
    }
}
