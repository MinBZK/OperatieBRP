/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class DienstbundelGroepDsl implements SQLWriter {

    private static final AtomicInteger ID_GEN = new AtomicInteger();
    private static final String STER = "*";
    private static final Map<String, ElementEnum> ALLE_GROEPEN = new HashMap<String, ElementEnum>() {
        {
            for (ElementEnum elementEnum : ElementEnum.values()) {
                if (elementEnum.getSoort() == SoortElement.GROEP) {
                    if ((elementEnum.name().toLowerCase().endsWith("_identiteit") && AutAutDSL.IDENTITEIT_GROEPEN != null
                        && !AutAutDSL.IDENTITEIT_GROEPEN.contains(elementEnum.getNaam()))
                        || elementEnum == ElementEnum.PERSOON_CACHE_STANDAARD
                        || elementEnum == ElementEnum.PARTIJENINONDERZOEK_STANDAARD
                        || elementEnum == ElementEnum.PARTIJROL_STANDAARD) {
                        continue;
                    }
                    put(elementEnum.getNaam(), elementEnum);
                }
            }
        }
    };

    final int id = ID_GEN.incrementAndGet();
    ElementEnum element;
    boolean formeel;
    boolean materieel;
    boolean verantwoording;
    DienstbundelDsl dienstbundel;

    @Override
    public void toSQL(final Writer writer) throws IOException {
        writer.write(String.format("insert into autaut.dienstbundelgroep (id, dienstbundel, groep, indformelehistorie, " +
                "indmaterielehistorie, indverantwoording) " +
                "values (%d, %d, %d, %s, %s, %s);%n", id, dienstbundel.getId(), element.getId(), formeel, materieel, verantwoording));
    }

    /**
     * Parsed de dienstbundelgroepattribuut
     * @param dienstbundel de parent dienstbundel
     * @param groepDsl de groepen
     * @return een geparsde dienst
     */
    public static List<DienstbundelGroepDsl> parse(final DienstbundelDsl dienstbundel, final String groepDsl) {
        if (groepDsl == null) {
            return Collections.emptyList();
        }
        final String[] groepDslWaarden = StringUtils.split(groepDsl, ",");

        DienstbundelGroepDsl sterGroep = null;
        final Set<ElementEnum> excludeList = new HashSet<>();
        final Map<ElementEnum, DienstbundelGroepDsl> groepDslList = new HashMap<>();
        for (final String groepDslWaarde : groepDslWaarden) {

            String elementNaam = StringUtils.trimToNull(groepDslWaarde);
            boolean materieel = false;
            boolean formeel = false;
            boolean verantwoording = false;
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

            final DienstbundelGroepDsl groep = new DienstbundelGroepDsl();
            groep.materieel = materieel;
            groep.formeel = formeel;
            groep.verantwoording = verantwoording;
            groep.dienstbundel = dienstbundel;
            if (STER.equals(elementNaam)) {
                sterGroep = groep;
            } else if (!ALLE_GROEPEN.containsKey(elementNaam)) {
                throw new IllegalArgumentException("Onbekende groep:" + elementNaam);
            } else {
                groep.element = ALLE_GROEPEN.get(elementNaam);
                if (excludeGroep) {
                    excludeList.add(groep.element);
                } else {
                    groepDslList.put(groep.element, groep);
                }
            }
        }

        //als er een * groep bestaat, voeg dan de overige groep toe indien deze niet reeds bestaat
        if (sterGroep != null) {
            for (ElementEnum groepEnum : ALLE_GROEPEN.values()) {
                if (!groepDslList.containsKey(groepEnum) && !excludeList.contains(groepEnum)) {
                    final DienstbundelGroepDsl groep = new DienstbundelGroepDsl();
                    groep.element = groepEnum;
                    groep.materieel = sterGroep.materieel;
                    groep.formeel = sterGroep.formeel;
                    groep.verantwoording = sterGroep.verantwoording;
                    groep.dienstbundel = dienstbundel;
                    groepDslList.put(groepEnum, groep);
                }
            }
        }
        return new ArrayList<>(groepDslList.values());
    }
}