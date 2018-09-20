/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt een relatie (van een huwelijk).
 */
@Component
public final class HuwelijkRelatieMapper extends AbstractRelatieMapper {

    /**
     * Constructor.
     */
    public HuwelijkRelatieMapper() {
        super(ElementEnum.HUWELIJK_TIJDSTIPREGISTRATIE,
              ElementEnum.HUWELIJK_TIJDSTIPVERVAL,
              ElementEnum.HUWELIJK_DATUMAANVANG,
              ElementEnum.HUWELIJK_GEMEENTEAANVANGCODE,
              ElementEnum.HUWELIJK_WOONPLAATSNAAMAANVANG,
              ElementEnum.HUWELIJK_BUITENLANDSEPLAATSAANVANG,
              ElementEnum.HUWELIJK_BUITENLANDSEREGIOAANVANG,
              ElementEnum.HUWELIJK_LANDGEBIEDAANVANGCODE,
              ElementEnum.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG,
              ElementEnum.HUWELIJK_REDENEINDECODE,
              ElementEnum.HUWELIJK_DATUMEINDE,
              ElementEnum.HUWELIJK_GEMEENTEEINDECODE,
              ElementEnum.HUWELIJK_WOONPLAATSNAAMEINDE,
              ElementEnum.HUWELIJK_BUITENLANDSEPLAATSEINDE,
              ElementEnum.HUWELIJK_BUITENLANDSEREGIOEINDE,
              ElementEnum.HUWELIJK_LANDGEBIEDEINDECODE,
              ElementEnum.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE);
    }
}
