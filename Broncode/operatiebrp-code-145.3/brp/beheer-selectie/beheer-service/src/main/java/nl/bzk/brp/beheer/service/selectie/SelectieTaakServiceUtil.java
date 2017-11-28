/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;

/**
 * Util klasse.
 */
final class SelectieTaakServiceUtil {

    /**
     * Map met een vertaling van {@link EenheidSelectieInterval} naar {@link ChronoUnit}.
     */
    static final Map<EenheidSelectieInterval, ChronoUnit> EENHEID_SELECTIE_INTERVAL_CHRONO_UNIT_MAP = Maps.newHashMap();
    /**
     * Map met alle foutieve eindstatussen die een selectietaak kan hebben.
     */
    static final Set<SelectietaakStatus> STATUS_FOUTIEF = Sets.newHashSet();
    /**
     * Map met alle onverwerkte eindstatussen die een selectietaak kan hebben.
     */
    static final Set<SelectietaakStatus> STATUS_ONVERWERKT = Sets.newHashSet();

    static {
        EENHEID_SELECTIE_INTERVAL_CHRONO_UNIT_MAP.put(EenheidSelectieInterval.DAG, ChronoUnit.DAYS);
        EENHEID_SELECTIE_INTERVAL_CHRONO_UNIT_MAP.put(EenheidSelectieInterval.MAAND, ChronoUnit.MONTHS);

        STATUS_FOUTIEF.add(SelectietaakStatus.GEANNULEERD);
        STATUS_FOUTIEF.add(SelectietaakStatus.UITVOERING_MISLUKT);
        STATUS_FOUTIEF.add(SelectietaakStatus.UITVOERING_AFGEBROKEN);
        STATUS_FOUTIEF.add(SelectietaakStatus.AFGEKEURD);
        STATUS_FOUTIEF.add(SelectietaakStatus.NIET_GELEVERD);

        STATUS_ONVERWERKT.add(SelectietaakStatus.IN_TE_PLANNEN);
        STATUS_ONVERWERKT.add(SelectietaakStatus.GEPLAND);
    }

    private SelectieTaakServiceUtil() {

    }
}
