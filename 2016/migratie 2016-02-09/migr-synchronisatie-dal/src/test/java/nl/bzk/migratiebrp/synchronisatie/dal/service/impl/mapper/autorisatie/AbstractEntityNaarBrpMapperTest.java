/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.util.TimestampUtil;

public class AbstractEntityNaarBrpMapperTest {

    protected static void vulFormeleHistorie(final FormeleHistorie historie) {
        historie.setDatumTijdRegistratie(TimestampUtil.maakTimestamp(1990, 0, 2, 0, 0, 0));
        historie.setActieInhoud(maakBRPActie());
    }

    private static BRPActie maakBRPActie() {
        final Partij partij = new Partij("Partij", 3458734);
        final AdministratieveHandeling administratieHandeling = new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);

        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieHandeling, partij, TimestampUtil.maakTimestamp(1990, 0, 2, 0, 0, 0));
        actie.setId(1L);

        return actie;
    }
}
