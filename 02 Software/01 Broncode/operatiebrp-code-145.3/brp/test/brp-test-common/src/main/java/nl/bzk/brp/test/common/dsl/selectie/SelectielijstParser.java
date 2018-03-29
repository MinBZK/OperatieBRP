/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.selectie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 * DSL parser tbv directory structuur met selectielijsten per dienst.
 */
public class SelectielijstParser {

    public static final String DIENST_SLEUTEL = "dienstSleutel";
    public static final String SRT_IDENTIFICATIE = "soortIdentificatie";
    public static final String IDENTIFICATIENRS = "identificatienummers";
    public static final String SELECTIETAAK = "selectietaak";

    public static Map<DienstSleutel, Map<String, List<String>>> parse(final List<DslSectie> sectieLijst, final Function<DienstSleutel, Dienst> dienstResolver) {
        Map<DienstSleutel, Map<String, List<String>>> dienstSelectielijstMap = new HashMap<>();
        for (DslSectie sectie : sectieLijst) {
            List<String> selectielijst = new ArrayList<>();
            Map<String, List<String>> selectielijstMap = new HashMap<>();
            final DienstSleutel dienstSleutel = sectie.geefStringValue(DIENST_SLEUTEL)
                    .map(DienstSleutel::new)
                    .orElseThrow(() -> new IllegalArgumentException("dienstSleutel is verplicht: " + sectie.toString()));
            sectie.geefStringValue(IDENTIFICATIENRS).ifPresent(lijst -> selectielijst.addAll(Arrays.asList(lijst.split(","))));
            sectie.geefStringValue(SRT_IDENTIFICATIE).ifPresent(s -> selectielijstMap.put(s, selectielijst));
            dienstSelectielijstMap.put(dienstSleutel, selectielijstMap);
        }
        return dienstSelectielijstMap;
    }
}
