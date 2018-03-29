/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;

/**
 */
final class DienstbundelGroepAttribuutParser {

    private static final String ALLE = "*";
    private final IdGenerator idGenerator;

    private DienstbundelParser dslDienstbundel;
    private boolean genereerAlleAttributen;
    private final Set<AttribuutElement> inserts = new LinkedHashSet<>();
    private final Set<AttribuutElement> deletes = new LinkedHashSet<>();


    /**
     * Constructor.
     * @param idGenerator de id generator
     */
    private DienstbundelGroepAttribuutParser(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    /**
     * @param collector collector
     */
    public void collect(final AutorisatieData collector) {
        for (final DienstbundelGroepParser dienstbundelGroepParser : dslDienstbundel.getGroepen()) {
            for (final AttribuutElement attribuutElement : ElementHelper.getAttributen()) {
                if (!geldigElement(attribuutElement, dienstbundelGroepParser.getElement())) {
                    continue;
                }
                if (deletes.contains(attribuutElement)) {
                    continue;
                }
                if (genereerAlleAttributen || inserts.contains(attribuutElement)) {
                    final DienstbundelGroepAttribuut dienstbundelGroepAttribuut = new DienstbundelGroepAttribuut(
                            dienstbundelGroepParser.getDienstbundelGroep(),
                            Element.parseId(attribuutElement.getId()));
                    dienstbundelGroepAttribuut.setId(idGenerator.getDienstbundelGroepAttrId().incrementAndGet());
                    collector.getDienstbundelGroepAttrEntities().add(dienstbundelGroepAttribuut);
                }
            }
        }
    }

    private boolean geldigElement(final AttribuutElement attribuutElement, final GroepElement groepElement) {

        if (attribuutElement.getGroep() != groepElement) {
            return false;
        }
        switch (attribuutElement.getNaam()) {

            case "ErkenningOngeborenVrucht.SoortCode":
            case "FamilierechtelijkeBetrekking.SoortCode":
            case "GeregistreerdPartnerschap.SoortCode":
            case "Huwelijk.SoortCode":
            case "HuwelijkGeregistreerdPartnerschap.SoortCode":
            case "NaamskeuzeOngeborenVrucht.SoortCode":
                return false;
            default:
                if (attribuutElement.getAutorisatie() == null) {
                    return true;
                }

                switch (attribuutElement.getAutorisatie()) {
                    case STRUCTUUR:
                    case NIET_VERSTREKKEN:
                    case VIA_GROEPSAUTORISATIE:
                        return false;
                    default:
                        return true;
                }
        }
    }

    /**
     * @param groepDsl groepDsl
     * @param attribuutDslWaarde attribuutDslWaarde
     * @param idGenerator idGenerator
     * @return dienstbundelGroepAttribuutParseerResultaat
     */
    static List<DienstbundelGroepAttribuutParser> parse(final DienstbundelParser groepDsl,
                                                        final String attribuutDslWaarde, final IdGenerator idGenerator) {
        final DienstbundelGroepAttribuutParser attrDsl = new DienstbundelGroepAttribuutParser(idGenerator);
        attrDsl.dslDienstbundel = groepDsl;

        if (attribuutDslWaarde != null) {
            final String[] waarden = attribuutDslWaarde.split(",");
            final Set<String> set = new HashSet<>(Arrays.asList(waarden));
            if (set.contains(ALLE)) {
                attrDsl.genereerAlleAttributen = true;
                set.remove(ALLE);
            }

            for (String waarde : set) {
                waarde = waarde.trim();
                if (waarde.startsWith("!")) {
                    attrDsl.deletes.add(ElementHelper.getAttribuutElement(waarde.substring(1)));
                } else {
                    if (!attrDsl.genereerAlleAttributen) {
                        // negeer losse attributen indien ook * opgegeven is
                        attrDsl.inserts.add(ElementHelper.getAttribuutElement(waarde));
                    }
                }
            }
        }

        //post-check, voor alle aangewezig attributen moeten groepen bestaan
        final Set<Integer> alleGroepen = new HashSet<>();
        for (final DienstbundelGroepParser dienstbundelGroepParser : groepDsl.getGroepen()) {
            alleGroepen.add(dienstbundelGroepParser.getElement().getId());
        }
        final Set<AttribuutElement> alleAttributen = new HashSet<>();
        alleAttributen.addAll(attrDsl.inserts);
        alleAttributen.addAll(attrDsl.deletes);
        for (final AttribuutElement attr : alleAttributen) {
            if (!alleGroepen.contains(attr.getGroepId())) {
                throw new IllegalArgumentException(String
                        .format("Autorisatie voor attribuut '%s' kan niet ingeregeld worden omdat groep '%s' ontbreekt", attr.getNaam(),
                                ElementHelper.getGroepElement(attr.getGroepId()).getNaam()));
            }
        }

        return Collections.singletonList(attrDsl);

    }
}
