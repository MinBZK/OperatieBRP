/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.PersoonAfgeleidAdministratiefMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.PersoonAfgeleidAdministratiefConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaaties in persoon/afgeleid administratief.
 */
@Component
public final class PersoonAfgeleidAdministratiefMutatieVerwerker
        extends AbstractFormeelMutatieVerwerker<Lo3InschrijvingInhoud, BrpPersoonAfgeleidAdministratiefInhoud> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder converteerder
     */
    @Inject
    protected PersoonAfgeleidAdministratiefMutatieVerwerker(
            final PersoonAfgeleidAdministratiefMapper mapper,
            final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new PersoonAfgeleidAdministratiefConverteerder(attribuutConverteerder), attribuutConverteerder, null,
                PersoonAfgeleidAdministratiefMapper.GROEP_ELEMENT, LOGGER);
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final MetaRecord identiteitRecord, final MetaRecord record, final Lo3Documentatie documentatie) {
        // Geen documentatie voor categorie 07 (anders gaat groep 88 fout)
        return null;
    }
}
