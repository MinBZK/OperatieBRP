/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpVerbintenisInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een verbintenis.
 */
@Component
public abstract class AbstractVerbintenisMapper extends AbstractFormeelMapper<RelatieHisVolledig, HisRelatieModel, BrpVerbintenisInhoud> {

    private final ElementEnum soort;

    /**
     * Constructor.
     *
     * @param tijdstipRegistratie
     *            element voor tijdstip registratie
     * @param tijdstipVerval
     *            element voor tijdstip verval
     * @param soort
     *            element voor soort relatie
     */
    public AbstractVerbintenisMapper(final ElementEnum tijdstipRegistratie, final ElementEnum tijdstipVerval, final ElementEnum soort) {
        super(tijdstipRegistratie, tijdstipVerval);
        this.soort = soort;
    }

    @Override
    protected final Iterable<HisRelatieModel> getHistorieIterable(final RelatieHisVolledig hisVolledig) {
        return hisVolledig.getRelatieHistorie();
    }

    @Override
    public final BrpVerbintenisInhoud mapInhoud(final HisRelatieModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpVerbintenisInhoud(
            BrpMapperUtil.mapBrpSoortRelatieCode(
                historie.getRelatie().getSoort(),
                onderzoekMapper.bepaalOnderzoek(historie.getRelatie().getID(), soort, true)));
    }
}
