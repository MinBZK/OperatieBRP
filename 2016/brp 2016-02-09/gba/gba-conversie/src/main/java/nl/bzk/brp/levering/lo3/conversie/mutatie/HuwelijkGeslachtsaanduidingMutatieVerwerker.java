/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.HuwelijkGeslachtsaanduidingMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpHuwelijkConverteerder.GeslachtsaanduidingConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in huwelijk/geslachtsaanduiding.
 */
@Component
public final class HuwelijkGeslachtsaanduidingMutatieVerwerker
        extends AbstractMaterieelMutatieVerwerker<Lo3HuwelijkOfGpInhoud, BrpGeslachtsaanduidingInhoud, HisPersoonGeslachtsaanduidingModel>
{
    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     */
    @Autowired
    protected HuwelijkGeslachtsaanduidingMutatieVerwerker(
        final HuwelijkGeslachtsaanduidingMapper mapper,
        final GeslachtsaanduidingConverteerder converteerder)
    {
        super(mapper, converteerder, ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING);
    }

}
