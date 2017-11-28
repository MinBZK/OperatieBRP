/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaGroepLiteral;
import nl.bzk.brp.domain.expressie.MetaRecordLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;

/**
 * Functie voor het selecteren van materieel historische gegevens van laatste materiele record.
 */
@Component
@FunctieKeyword("HISM_LAATSTE")
final class HismLaatsteFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    HismLaatsteFunctie() {
        super(new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE));
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie expressie = argumenten.get(0);

        MetaGroepLiteral metaGroepLiteral = null;
        if (expressie instanceof MetaGroepLiteral) {
            metaGroepLiteral = (MetaGroepLiteral) expressie;
        } else if (expressie instanceof LijstExpressie
                && ((LijstExpressie) expressie).size() == 1) {
            final Expressie enkeleWaarde = expressie.alsLijst().geefEnkeleWaarde();
            if (enkeleWaarde instanceof MetaGroepLiteral) {
                metaGroepLiteral = (MetaGroepLiteral) enkeleWaarde;
            }
        }

        if (metaGroepLiteral == null) {
            throw new ExpressieRuntimeException("HISM_LAATSTE verwacht een groep als argument!");
        }

        final Persoonslijst persoonslijst = context.
                getProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST);
        final MetaGroep metaGroep = metaGroepLiteral.getMetaGroep();
        return persoonslijst.getMaterieelLaatsteHistorieRecord(metaGroep)
                .map(metaRecord -> LijstExpressie.ensureList(new MetaRecordLiteral(metaRecord)))
                .orElse(new LijstExpressie());
    }
}
