/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map ouder van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpOuderMapper extends AbstractBrpMapper<BetrokkenheidOuderHistorie, BrpOuderInhoud> {

    @Override
    protected BrpOuderInhoud mapInhoud(final BetrokkenheidOuderHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpBoolean indicatieOuder =
                BrpBoolean.wrap(historie.getIndicatieOuder(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, true));
        final BrpBoolean indicatieAdresgevendeOuder =
                BrpBoolean.wrap(
                    historie.getIndicatieOuderUitWieKindIsGeboren(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, true));

        return new BrpOuderInhoud(indicatieOuder, indicatieAdresgevendeOuder);
    }
}
