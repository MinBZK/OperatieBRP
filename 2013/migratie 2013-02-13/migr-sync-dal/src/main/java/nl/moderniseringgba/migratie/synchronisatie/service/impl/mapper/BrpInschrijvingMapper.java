/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonInschrijvingHistorie;

import org.springframework.stereotype.Component;

/**
 * Map inschrijving van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpInschrijvingMapper extends BrpMapper<PersoonInschrijvingHistorie, BrpInschrijvingInhoud> {

    @Override
    protected BrpInschrijvingInhoud mapInhoud(final PersoonInschrijvingHistorie historie) {
        final Persoon vorige = historie.getVorigePersoon();
        final Persoon volgende = historie.getVolgendePersoon();
        // @formatter:off
        return new BrpInschrijvingInhoud(
                vorige == null ? null : BrpMapperUtil.mapLong(vorige.getAdministratienummer()), 
                volgende == null ? null : BrpMapperUtil.mapLong(volgende.getAdministratienummer()), 
                BrpMapperUtil.mapDatum(historie.getDatumInschrijving()), 
                BrpMapperUtil.mapInt(historie.getVersienummer())
            );
        // @formatter:on
    }

}
