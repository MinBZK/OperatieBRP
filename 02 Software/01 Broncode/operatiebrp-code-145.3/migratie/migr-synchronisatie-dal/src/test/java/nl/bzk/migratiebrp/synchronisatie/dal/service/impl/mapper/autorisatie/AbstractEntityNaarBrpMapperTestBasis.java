/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.test.dal.TimestampUtil;

public class AbstractEntityNaarBrpMapperTestBasis {

    protected static void vulFormeleHistorie(final FormeleHistorie historie) {
        historie.setDatumTijdRegistratie(TimestampUtil.maakTimestamp(1990, 0, 2, 0, 0, 0));
        historie.setActieInhoud(maakBRPActie());
    }

    private static BRPActie maakBRPActie() {
        final Partij partij = new Partij("Partij", "3458734");
        final AdministratieveHandeling administratieHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING, new Timestamp(System.currentTimeMillis()));

        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieHandeling, partij, TimestampUtil.maakTimestamp(1990, 0, 2, 0, 0, 0));
        actie.setId(1L);

        return actie;
    }
}
