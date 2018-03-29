/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.selectie;

import java.sql.Timestamp;
import java.util.ListIterator;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 * DSL parser voor een {@link Selectierun}
 */
public class SelectierunParser {

    /**
     * sectienaam Selectietaak.
     */
    private static final String SECTIE_SELECTIERUN= "Selectierun";

    private SelectierunParser() {}

    /**
     * @param regelIterator de totale lijst van te parsen regels
     */
    static Selectierun parse(final ListIterator<DslSectie> regelIterator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(SECTIE_SELECTIERUN);

        final Timestamp tsstart = sectie.geefDatumInt("tsstart")
                .map(integer -> DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(
                        DatumUtil.vanIntegerNaarLocalDate(integer).atStartOfDay(DatumUtil.BRP_ZONE_ID)))
                .orElseThrow(() -> new IllegalArgumentException("tsstart is verplicht"));
        final Selectierun selectierun = new Selectierun(tsstart);
        //optionals
        sectie.geefDatumInt("tsgereed").map(integer -> DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(
                DatumUtil.vanIntegerNaarLocalDate(integer).atStartOfDay(DatumUtil.BRP_ZONE_ID))).ifPresent(selectierun::setTijdstipGereed);

        return selectierun;
    }
}
