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
import nl.bzk.brp.levering.lo3.mapper.PersoonPersoonskaartMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.PersoonskaartConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/persoonkaart.
 */
@Component
public final class PersoonPersoonskaartMutatieVerwerker extends AbstractFormeelMutatieVerwerker<Lo3InschrijvingInhoud, BrpPersoonskaartInhoud> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected PersoonPersoonskaartMutatieVerwerker(final PersoonPersoonskaartMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new PersoonskaartConverteerder(attribuutConverteerder), attribuutConverteerder, null, PersoonPersoonskaartMapper.GROEP_ELEMENT, LOGGER);
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final MetaRecord identiteitRecord, final MetaRecord record, final Lo3Documentatie documentatie) {
        // Geen documentatie voor categorie 07 (anders gaat groep 88 fout)
        return null;
    }
}
