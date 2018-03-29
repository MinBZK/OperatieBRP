/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.jbehave;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.selectie.SelectielijstParser;
import nl.bzk.brp.test.common.dsl.selectie.SelectietaakParser;
import org.jbehave.core.model.ExamplesTable;

/**
 * Converteert een {@link ExamplesTable} naar een lijst van {@link DslSectie}s.
 */
public class ExamplesTable2DslSectie {

    private static final Set<String> HEADERS = Sets.newHashSet(
            SelectietaakParser.DIENST_SLEUTEL,
            SelectietaakParser.DATPLANNING,
            SelectietaakParser.INDAG,
            SelectietaakParser.PEILMOMFORMEELRESULTAAT,
            SelectietaakParser.PEILMOMMATERIEELRESULTAAT,
            SelectietaakParser.PEILMOMMATERIEEL,
            SelectietaakParser.INDICATIE_LIJST_GEBRUIKEN,
            SelectietaakParser.STATUS,
            SelectietaakParser.TAAK_ID,
            SelectielijstParser.DIENST_SLEUTEL,
            SelectielijstParser.SRT_IDENTIFICATIE,
            SelectielijstParser.IDENTIFICATIENRS,
            SelectielijstParser.SELECTIETAAK
    );

    /**
     * Converteert een {@link ExamplesTable} naar een lijst van {@link DslSectie}s.
     */
    public static List<DslSectie> convert(ExamplesTable examplestable) {
        final List<String> headers = examplestable.getHeaders();
        headers.stream().filter(s -> !HEADERS.contains(s)).findAny().ifPresent(s -> {
            throw new IllegalArgumentException("Ongeldige headers");
        });
        final List<Map<String, String>> rows = examplestable.getRows();
        final List<DslSectie> dslSectieList = Lists.newArrayListWithExpectedSize(rows.size());
        for (Map<String, String> row : rows) {
            final DslSectie dslSectie = new DslSectie(SelectietaakParser.SECTIE_TAAK);
            for (String header : headers) {
                final String value = row.get(header);
                dslSectie.addRegel(header, value);
            }
            dslSectieList.add(dslSectie);
        }
        return dslSectieList;
    }
}
