/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt een relatie (van een geregistreerd partnerschap).
 */
@Component
public final class GeregistreerdPartnerschapRelatieMapper extends AbstractRelatieMapper {

    /**
     * Constructor.
     */
    public GeregistreerdPartnerschapRelatieMapper() {
        super(ElementEnum.GEREGISTREERDPARTNERSCHAP_TIJDSTIPREGISTRATIE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_TIJDSTIPVERVAL,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_DATUMEINDE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE,
              ElementEnum.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE);
    }
}
