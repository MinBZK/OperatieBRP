/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;

/**
 */
final class DienstbundelGroepAttribuutParser {

    private static final String ALLE = "*";

    DienstbundelGroepAttribuutParser() {

    }

    /**
     * @param groepDsl groepDsl
     * @param attribuutDslWaarde attribuutDslWaarde
     */
    void parse(final Dienstbundel groepDsl, final String attribuutDslWaarde) {

        boolean genereerAlleAttributen = false;
        final Set<AttribuutElement> inserts = new LinkedHashSet<>();
        final Set<AttribuutElement> deletes = new LinkedHashSet<>();

        if (attribuutDslWaarde != null) {
            final String[] waarden = attribuutDslWaarde.split(",");
            final Set<String> set = new HashSet<>(Arrays.asList(waarden));
            if (set.contains(ALLE)) {
                genereerAlleAttributen = true;
                set.remove(ALLE);
            }

            for (String waarde : set) {
                waarde = waarde.trim();
                if (waarde.startsWith("!")) {
                    deletes.add(ElementHelper.getAttribuutElement(waarde.substring(1)));
                } else {
                    if (!genereerAlleAttributen) {
                        // negeer losse attributen indien ook * opgegeven is
                        inserts.add(ElementHelper.getAttribuutElement(waarde));
                    }
                }
            }
        }

        //post-check, voor alle aangewezig attributen moeten groepen bestaan
        final Set<Integer> alleGroepen = Sets.newHashSet();
        for (final DienstbundelGroep dienstbundelGroep : groepDsl.getDienstbundelGroepSet()) {
            alleGroepen.add(dienstbundelGroep.getGroep().getId());
        }
        final Set<AttribuutElement> alleAttributen = new HashSet<>();
        alleAttributen.addAll(inserts);
        alleAttributen.addAll(deletes);
        for (final AttribuutElement attr : alleAttributen) {
            if (!alleGroepen.contains(attr.getGroepId())) {
                throw new IllegalStateException(String.format("Autorisatie voor attribuut '%s' kan niet "
                                + "ingeregeld worden " + "omdat groep '%s' ontbreekt", attr.getNaam(),
                        ElementHelper.getGroepElement(attr.getGroepId()).getNaam()));
            }
        }

        for (final DienstbundelGroep dienstbundelGroep : groepDsl.getDienstbundelGroepSet()) {
            for (final AttribuutElement attribuutElement : ElementHelper.getAttributen()) {
                if (!geldigElement(attribuutElement, dienstbundelGroep.getGroep()) || deletes.contains(attribuutElement)) {
                    continue;
                }
                if (genereerAlleAttributen || inserts.contains(attribuutElement)) {
                    final DienstbundelGroepAttribuut groepAttribuut = new DienstbundelGroepAttribuut(dienstbundelGroep, attribuutElement.getElement());
                    dienstbundelGroep.getDienstbundelGroepAttribuutSet().add(groepAttribuut);
                }
            }
        }
    }

    private static boolean geldigElement(final AttribuutElement attribuutElement, final Element groepElement) {

        if (attribuutElement.getGroep().getElement() != groepElement) {
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
}
