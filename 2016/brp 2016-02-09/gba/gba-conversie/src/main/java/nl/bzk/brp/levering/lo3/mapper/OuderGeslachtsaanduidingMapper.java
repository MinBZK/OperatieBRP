/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt de geslachtsaanduiding (van een ouder).
 */
@Component
public final class OuderGeslachtsaanduidingMapper extends AbstractGeslachtsaanduidingMapper {

    /**
     * Constructor.
     */
    public OuderGeslachtsaanduidingMapper() {
        super(ElementEnum.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID,
              ElementEnum.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID,
              ElementEnum.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE,
              ElementEnum.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL,
              ElementEnum.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE);
    }
}
