/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.VerblijfsrechtMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpVerblijfstitelConverteerder.VerblijfstitelConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/verblijfsrecht.
 */
@Component
public final class PersoonVerblijfsrechtMutatieVerwerker
        extends AbstractFormeelMutatieVerwerker<Lo3VerblijfstitelInhoud, BrpVerblijfsrechtInhoud, HisPersoonVerblijfsrechtModel>
{

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     * @param historieNabewerking
     *            historie nabewerking
     */
    @Autowired
    protected PersoonVerblijfsrechtMutatieVerwerker(
        final VerblijfsrechtMapper mapper,
        final VerblijfstitelConverteerder converteerder,
        final PersoonVerblijfsrechtHistorieNabewerking historieNabewerking)
    {
        super(mapper, converteerder, historieNabewerking, ElementEnum.PERSOON_VERBLIJFSRECHT);
    }

}
