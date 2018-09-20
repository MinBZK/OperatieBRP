/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import org.springframework.stereotype.Component;

/**
 * Mapped IST stapels van een Persoon op de IST Ouder2 stapel van de BrpPersoonslijst.
 * 
 */
@Component
public final class BrpIstOuder2Mapper extends AbstractBrpIstRelatieMapper {

    /**
     * Mapped van de set met stapels de categorie 3 stapels op een BrpStapel met BrpIstRelatieGroepInhoud.
     * 
     * @param stapels
     *            de set met IST stapels
     * @return een stapel van BrpIstRelatieGroepInhoud
     */
    public BrpStapel<BrpIstRelatieGroepInhoud> map(final Set<Stapel> stapels) {
        final List<BrpStapel<BrpIstRelatieGroepInhoud>> result = super.mapStapels(stapels);
        if (result == null || result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Lo3CategorieEnum getActueleCategorie() {
        return Lo3CategorieEnum.CATEGORIE_03;
    }

}
