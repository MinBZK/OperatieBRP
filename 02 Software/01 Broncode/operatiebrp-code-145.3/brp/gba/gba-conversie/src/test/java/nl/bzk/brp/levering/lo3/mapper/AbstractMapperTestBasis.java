/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.logMetaObject;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.levering.lo3.decorator.Persoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

public class AbstractMapperTestBasis {

    protected <T extends BrpGroepInhoud> BrpStapel<T> doMapping(final AbstractMapper<T> mapper, final MetaObject.Builder builder) {
        // Map naar generieke model
        final MetaObject metaObject = builder.build();

        logMetaObject(metaObject);

        // Voer mapper uit
        return mapper.map(metaObject, new OnderzoekMapperImpl(new Persoon(metaObject)));
    }

    protected <T extends BrpGroepInhoud> List<BrpStapel<T>> doMapping(final AbstractMultipleMapper<T> mapper, final MetaObject.Builder builder) {
        // Map naar generieke model
        final MetaObject metaObject = builder.build();

        logMetaObject(metaObject);

        // Voer mapper uit
        return mapper.map(metaObject, new OnderzoekMapperImpl(new Persoon(metaObject)));
    }
}
