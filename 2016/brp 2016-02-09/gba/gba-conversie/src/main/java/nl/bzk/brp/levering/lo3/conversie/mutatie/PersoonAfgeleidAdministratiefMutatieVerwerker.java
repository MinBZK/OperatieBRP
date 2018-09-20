/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.PersoonAfgeleidAdministratiefMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.PersoonAfgeleidAdministratiefConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaaties in persoon/afgeleid administratief.
 */
@Component
public final class PersoonAfgeleidAdministratiefMutatieVerwerker
        extends AbstractFormeelMutatieVerwerker<Lo3InschrijvingInhoud, BrpPersoonAfgeleidAdministratiefInhoud, HisPersoonAfgeleidAdministratiefModel>
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
    protected PersoonAfgeleidAdministratiefMutatieVerwerker(
        final PersoonAfgeleidAdministratiefMapper mapper,
        final PersoonAfgeleidAdministratiefConverteerder converteerder)
    {
        super(mapper, converteerder, null, ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF);
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final HisPersoonAfgeleidAdministratiefModel brpHistorie, final Lo3Documentatie documentatie) {
        // Geen documentatie voor categorie 07 (anders gaat groep 88 fout)
        return null;
    }
}
