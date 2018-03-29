/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.apache.commons.lang3.StringUtils;

/**
 * Representatie van dienstbundelgroep in de dsl.
 */
final class DienstbundelGroepParser {


    private static final String                    STER         = "*";
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

    private DienstbundelParser dienstbundel;
    private DienstbundelGroep            dienstbundelGroep;

    /**
     * Constructor.
     *
     * @param idGenerator de id generator.
     */
    DienstbundelGroepParser(final IdGenerator idGenerator) {
    }

    /**
     * @param autorisatieData autorisatieData
     */
    public void collect(final AutorisatieData autorisatieData) {
        autorisatieData.getDienstbundelGroepEntities().add(dienstbundelGroep);
    }

    /**
     * Parsed de dienstbundelgroepattribuut
     *
     * @param dienstbundel de parent dienstbundel
     * @param groepDsl     de groepen
     * @param idGenerator  de id generator
     * @return een geparsde dienst
     */
    public static List<DienstbundelGroepParser> parse(final DienstbundelParser dienstbundel, final String groepDsl,
                                                      final IdGenerator idGenerator)
    {
        if (groepDsl == null) {
            return Collections.emptyList();
        }
        final String[] groepDslWaarden = StringUtils.split(groepDsl, ",");

        //DienstbundelGroepParseerResultaat sterGroep = null;
        final Set<GroepElement> excludeList = Sets.newHashSet();
        final Map<GroepElement, DienstbundelGroepParser> groepDslList = Maps.newHashMap();
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
                throw new IllegalArgumentException("Onbekende groep:" + elementNaam);
            } else {
                element = ALLE_GROEPEN.get(elementNaam);
                if (excludeGroep) {
                    excludeList.add(element);
                } else {
                    final DienstbundelGroepParser groep = new DienstbundelGroepParser(idGenerator);
                    groep.dienstbundelGroep = new DienstbundelGroep(dienstbundel.getDienstbundel(), Element.parseId(element.getId().intValue()),
                        formeel, materieel, verantwoording);
                    groep.dienstbundelGroep.setId(idGenerator.getDienstbundelGroepId().incrementAndGet());
                    groep.dienstbundel = dienstbundel;
                    groepDslList.put(element, groep);
                }
            }
        }

        //als er een * groep bestaat, voeg dan de overige groep toe indien deze niet reeds bestaat
        if (sterGroep) {
            for (GroepElement groepEnum : ALLE_GROEPEN.values()) {
                if (!groepDslList.containsKey(groepEnum) && !excludeList.contains(groepEnum)) {
                    final DienstbundelGroepParser groep = new DienstbundelGroepParser(idGenerator);
                    groep.dienstbundelGroep = new DienstbundelGroep(dienstbundel.getDienstbundel(), Element.parseId(groepEnum.getId().intValue()),
                        formeel, materieel, verantwoording);
                    groep.dienstbundelGroep.setId(idGenerator.getDienstbundelGroepId().incrementAndGet());
                    groep.dienstbundel = dienstbundel;
                    groepDslList.put(groepEnum, groep);
                    groepDslList.put(groepEnum, groep);
                }
            }
        }
        return Lists.newArrayList(groepDslList.values());
    }

    DienstbundelGroep getDienstbundelGroep() {
        return dienstbundelGroep;
    }

    public int getId() {
        return dienstbundelGroep.getId();
    }

    GroepElement getElement() {
        return ElementHelper.getGroepElement(dienstbundelGroep.getGroep().getId());
    }
}
