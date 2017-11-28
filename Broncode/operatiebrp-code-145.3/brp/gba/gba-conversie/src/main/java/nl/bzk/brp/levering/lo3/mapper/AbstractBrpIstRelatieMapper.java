/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;

/**
 * Mapped IST stapels van een Persoon op de IST relatie (ouder1, ouder2 en kind) stapel van de BrpPersoonslijst.
 */
abstract class AbstractBrpIstRelatieMapper extends AbstractBrpIstMapper<BrpIstRelatieGroepInhoud> {

    @Override
    protected BrpIstRelatieGroepInhoud mapBrpIstGroepInhoud(final StapelVoorkomen voorkomen) {
        return mapBrpRelatieGroepInhoud(voorkomen);
    }

}
