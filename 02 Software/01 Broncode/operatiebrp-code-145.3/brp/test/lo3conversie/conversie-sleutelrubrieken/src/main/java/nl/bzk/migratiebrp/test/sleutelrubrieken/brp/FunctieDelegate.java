/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken.brp;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.functie.Functie;
import nl.bzk.brp.domain.expressie.signatuur.Signatuur;

/**
 * Delegate voor het uitvoeren van functies.
 */
public class FunctieDelegate implements Functie {

    private final Logger log;
    private final Functie delegate;

    /**
     * Constructor.
     *
     * @param delegate
     *            De delegate om te gebruiken.
     * @param log
     *            De logger.
     */
    public FunctieDelegate(final Functie delegate, final Logger log) {
        this.delegate = delegate;
        this.log = log;
    }

    @Override
    public List<Expressie> init(final List<Expressie> argumenten) {
        log.info("init({})", argumenten);
        return delegate.init(argumenten);
    }

    @Override
    public Signatuur getSignatuur() {
        log.info("getSignatuur()");
        return delegate.getSignatuur();
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        log.info("getType({},{})", argumenten, context);
        return delegate.getType(argumenten, context);
    }

    @Override
    public boolean evalueerArgumenten() {
        log.info("evalueerArgumenten()");
        return delegate.evalueerArgumenten();
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        log.info("pasToe({},{})", argumenten, context);
        return delegate.evalueer(argumenten, context);
    }

    @Override
    public String getKeyword() {
        return delegate.getKeyword();
    }
}
