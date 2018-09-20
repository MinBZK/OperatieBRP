/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de reisdocumenten.
 */
@Component
public final class ReisdocumentenMapper extends AbstractMultipleMapper<PersoonReisdocumentHisVolledig, HisPersoonReisdocumentModel, BrpReisdocumentInhoud>
{
    @Inject
    private ReisdocumentMapper reisdocumentMapper;

    @Override
    protected Set<? extends PersoonReisdocumentHisVolledig> getSet(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getReisdocumenten();
    }

    @Override
    protected ReisdocumentMapper getSingleMapper() {
        return reisdocumentMapper;
    }
}
