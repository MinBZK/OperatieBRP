/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaAttribuutLiteral;
import nl.bzk.brp.domain.expressie.MetaGroepLiteral;
import nl.bzk.brp.domain.expressie.MetaRecordLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Abstract klasse voor het opvragen van historische records.
 */
abstract class AbstractHisFuctie extends AbstractFunctie {

    AbstractHisFuctie() {
        super(new SimpeleSignatuur(ExpressieType.LIJST));
    }

    @Override
    public final Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Context hisContext = new Context(context);
        hisContext.definieer(ExpressieTaalConstanten.TOON_ACTUELE_GEGEVENS, BooleanLiteral.ONWAAR);
        hisContext.definieer(ExpressieTaalConstanten.ATTRIBUUTALSWAARDE, BooleanLiteral.ONWAAR);

        final LijstExpressie expressie = argumenten.get(0).evalueer(hisContext).alsLijst();
        final Persoonslijst persoonslijst = context.getProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST);
        final List<Expressie> expressieList = new LinkedList<>();
        for (Expressie expressie1 : expressie) {
            map(expressie1, expressieList, persoonslijst);
        }
        return new LijstExpressie(expressieList);
    }


    @Override
    public final ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public final boolean evalueerArgumenten() {
        return false;
    }

    protected abstract boolean magRecordTonen(final MetaRecord metaRecord, final Persoonslijst persoonslijst);

    private void map(final Expressie expressie, final List<Expressie> expressieList, final Persoonslijst persoonslijst) {
        if (expressie instanceof MetaGroepLiteral) {
            final MetaGroep metaGroep = ((MetaGroepLiteral) expressie).getMetaGroep();
            for (MetaRecord metaRecord : metaGroep.getRecords()) {
                if (magRecordTonen(metaRecord, persoonslijst)) {
                    expressieList.add(new MetaRecordLiteral(metaRecord));
                }
            }
        } else if (expressie instanceof MetaAttribuutLiteral) {
            final MetaAttribuutLiteral metaAttribuutLiteral = (MetaAttribuutLiteral) expressie;
            if (magRecordTonen(metaAttribuutLiteral.getMetaAttribuut().getParentRecord(), persoonslijst)) {
                expressieList.add(MetaAttribuutLiteral.maakAttribuutWaardeLiteral(metaAttribuutLiteral.getMetaAttribuut()));
            }
        } else {
            throw new ExpressieRuntimeException("Onverwachte expressie:" + expressie.getClass().getSimpleName());
        }
    }
}
