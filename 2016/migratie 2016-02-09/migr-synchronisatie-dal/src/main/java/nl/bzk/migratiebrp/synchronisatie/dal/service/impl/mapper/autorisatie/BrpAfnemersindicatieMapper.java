/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;

/**
 * Map Afnemersindicatie van het BRP database model naar het BRP conversie model.
 */
public final class BrpAfnemersindicatieMapper extends AbstractBrpMapper<PersoonAfnemerindicatieHistorie, BrpAfnemersindicatieInhoud> {

    @Override
    protected BrpAfnemersindicatieInhoud mapInhoud(final PersoonAfnemerindicatieHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpDatum datumAanvang = BrpMapperUtil.mapDatum(historie.getDatumAanvangMaterielePeriode());
        final BrpDatum datumEindeVolgen = BrpMapperUtil.mapDatum(historie.getDatumEindeVolgen());

        return new BrpAfnemersindicatieInhoud(datumAanvang, datumEindeVolgen, false);
    }
}
