/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpOuder2GezagInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt een gezag voor ouder2.
 */
@Component
public final class Ouder2GezagMapper extends AbstractMaterieelMapper<OuderHisVolledig, HisOuderOuderlijkGezagModel, BrpOuder2GezagInhoud> {

    /**
     * Constructor.
     */
    public Ouder2GezagMapper() {
        super(ElementEnum.OUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID,
              ElementEnum.OUDER_OUDERLIJKGEZAG_DATUMEINDEGELDIGHEID,
              ElementEnum.OUDER_OUDERLIJKGEZAG_TIJDSTIPREGISTRATIE,
              ElementEnum.OUDER_OUDERLIJKGEZAG_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisOuderOuderlijkGezagModel> getHistorieIterable(final OuderHisVolledig hisVolledig) {
        return hisVolledig.getOuderOuderlijkGezagHistorie();
    }

    @Override
    public BrpOuder2GezagInhoud mapInhoud(final HisOuderOuderlijkGezagModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpOuder2GezagInhoud(BrpMapperUtil.mapBrpBoolean(
            historie.getIndicatieOuderHeeftGezag(),
            onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.OUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, true)));

    }

}
