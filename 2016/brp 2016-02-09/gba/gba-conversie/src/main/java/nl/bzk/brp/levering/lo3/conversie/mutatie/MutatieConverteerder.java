/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;

import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mutatiebepaling.
 */
@Component("lo3_mutatieConverteerder")
public final class MutatieConverteerder implements Converteerder {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private MutatieVisitor mutatieVisitor;

    @Override
    public List<Lo3CategorieWaarde> converteer(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling,
        final ConversieCache conversieCache)
    {
        List<Lo3CategorieWaarde> resultaat = conversieCache.getMutatieCategorien();

        if (resultaat == null) {
            LOGGER.debug("converteer(persoon={}, administratieveHandeling={}, cache={})", persoon, administratieveHandeling, conversieCache);

            // Bepaal mutaties
            resultaat = mutatieVisitor.visit(persoon, administratieveHandeling);

            // Set resultaat
            conversieCache.setMutatieCategorien(resultaat);
        }

        return resultaat;
    }

}
