/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 */
public final class DienstbundelGroepAttrDsl implements SQLWriter {

    private static final Map<String, ElementEnum> ALLE_ATTRIBUTEN = new HashMap<String, ElementEnum>() {
        {
            for (ElementEnum elementEnum : ElementEnum.values()) {
                if (elementEnum.getSoort() == SoortElement.ATTRIBUUT) {
                    put(elementEnum.getNaam(), elementEnum);
                }
            }
        }
    };
    private static final  String                   ALLE            = "*";

    private DienstbundelDsl dienstbundelDsl;
    private boolean genereerAlleAttributen;
    private final Set<ElementEnum> inserts = new LinkedHashSet<>();
    private final Set<ElementEnum> deletes = new LinkedHashSet<>();

    @Override
    public void toSQL(final Writer writer) throws IOException {

        if (genereerAlleAttributen) {
            for (DienstbundelGroepDsl groep : dienstbundelDsl.getGroepen()) {
                writer.write(String.format("insert into autaut.dienstbundelgroepattr (dienstbundelgroep, attr) " +
                                "select %d, id from kern.element where groep = %d " +
                                "and expressie is not null " +
                                "and elementnaam NOT IN ('Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') " +
                                "and naam NOT IN ('ErkenningOngeborenVrucht.SoortCode', 'FamilierechtelijkeBetrekking.SoortCode', 'GeregistreerdPartnerschap.SoortCode', 'Huwelijk.SoortCode', 'HuwelijkGeregistreerdPartnerschap.SoortCode', 'NaamskeuzeOngeborenVrucht.SoortCode') " +
                                "and autorisatie NOT IN (2,7);%n",
                        groep.id, groep.element.getId()));

            }
        } else {
            for (ElementEnum insert : inserts) {
                writer.write(String.format("insert into autaut.dienstbundelgroepattr (dienstbundelgroep, attr) " +
                                "values ((select id from autaut.dienstbundelgroep where dienstbundel = %d and groep = %d), %d);%n",
                        dienstbundelDsl.getId(), insert.getGroepId(), insert.getId()));
            }
        }

        for (ElementEnum delete : deletes) {
            writer.write(String.format("delete from autaut.dienstbundelgroepattr where dienstbundelgroep = " +
                    "(select id from autaut.dienstbundelgroep where dienstbundel = %d and groep = %d) and attr = %d;%n",
                    dienstbundelDsl.getId(), delete.getGroepId(), delete.getId()));
        }

    }

    /**
     *
     * @param groepDsl
     * @param attribuutDslWaarde
     * @return
     */
    static List<DienstbundelGroepAttrDsl> parse(final DienstbundelDsl groepDsl, final String attribuutDslWaarde) {
        final DienstbundelGroepAttrDsl attrDsl = new DienstbundelGroepAttrDsl();
        attrDsl.dienstbundelDsl = groepDsl;

        if (attribuutDslWaarde != null) {
            final String[] waarden = attribuutDslWaarde.split(",");
            final Set<String> set = new HashSet<>(Arrays.asList(waarden));
            if (set.contains(ALLE)) {
                attrDsl.genereerAlleAttributen = true;
                set.remove("*");
            }

            for (String waarde : set) {
                waarde = waarde.trim();
                if (waarde.startsWith("!")) {
                    attrDsl.deletes.add(ALLE_ATTRIBUTEN.get(waarde.substring(1)));
                } else {
                    if (attrDsl.genereerAlleAttributen) {
                        // negeer losse attributen indien ook * opgegeven is
                    } else {
                        attrDsl.inserts.add(ALLE_ATTRIBUTEN.get(waarde));
                    }
                }
            }
        }

        //post-check, voor alle aangewezig attributen moeten groepen bestaan
        final Set<ElementEnum> alleGroepen = new HashSet<>();
        for (final DienstbundelGroepDsl dienstbundelGroepDsl : groepDsl.getGroepen()) {
            alleGroepen.add(dienstbundelGroepDsl.element);
        }
        final Set<ElementEnum> alleAttributen = new HashSet<>();
        alleAttributen.addAll(attrDsl.inserts);
        alleAttributen.addAll(attrDsl.deletes);
        for (final ElementEnum attr : alleAttributen) {
            if (!alleGroepen.contains(attr.getGroep())) {
                throw new IllegalArgumentException(String.format("Autorisatie voor attribuut '%s' kan niet ingeregeld worden " +
                        "omdat groep '%s' ontbreekt", attr.getNaam(), attr.getGroep().getNaam()));
            }
        }


        return Collections.singletonList(attrDsl);

    }
}
