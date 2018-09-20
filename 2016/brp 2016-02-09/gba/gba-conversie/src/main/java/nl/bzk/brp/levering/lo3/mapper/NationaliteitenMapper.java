/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de nationaliteiten.
 */
@Component
public final class NationaliteitenMapper extends
        AbstractMultipleMapper<PersoonNationaliteitHisVolledig, HisPersoonNationaliteitModel, BrpNationaliteitInhoud>
{
    @Inject
    private NationaliteitMapper nationaliteitMapper;

    @Override
    protected Set<? extends PersoonNationaliteitHisVolledig> getSet(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getNationaliteiten();
    }

    @Override
    protected NationaliteitMapper getSingleMapper() {
        return nationaliteitMapper;
    }
}
