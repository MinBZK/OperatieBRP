/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.InschrijvingMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.InschrijvingConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/inschrijving.
 */
@Component
public final class PersoonInschrijvingMutatieVerwerker
        extends AbstractFormeelMutatieVerwerker<Lo3InschrijvingInhoud, BrpInschrijvingInhoud, HisPersoonInschrijvingModel>
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
    protected PersoonInschrijvingMutatieVerwerker(final InschrijvingMapper mapper, final InschrijvingConverteerder converteerder) {
        super(mapper, converteerder, null, ElementEnum.PERSOON_INSCHRIJVING);
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final HisPersoonInschrijvingModel brpHistorie, final Lo3Documentatie documentatie) {
        // Geen documentatie voor categorie 07 (anders gaat groep 88 fout)
        return null;
    }

}
