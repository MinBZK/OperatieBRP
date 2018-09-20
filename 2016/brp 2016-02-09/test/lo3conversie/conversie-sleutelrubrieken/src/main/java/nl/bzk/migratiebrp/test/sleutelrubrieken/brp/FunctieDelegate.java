/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken.brp;

import java.util.List;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.functies.Functieberekening;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.migratiebrp.util.common.logging.Logger;

/**
 * Delegate voor het uitvoeren van functies.
 */
public class FunctieDelegate implements Functieberekening {

    private final Logger log;
    private final Functieberekening delegate;

    /**
     * Constructor.
     * 
     * @param delegate
     *            De delegate om te gebruiken.
     * @param log
     *            De logger.
     */
    public FunctieDelegate(final Functieberekening delegate, final Logger log) {
        this.delegate = delegate;
        this.log = log;
    }

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        log.info("vulDefaultArgumentenIn({})", argumenten);
        return delegate.vulDefaultArgumentenIn(argumenten);
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
    public ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        log.info("getTypeVanElementen({},{})", argumenten, context);
        return delegate.getTypeVanElementen(argumenten, context);
    }

    @Override
    public boolean evalueerArgumenten() {
        log.info("evalueerArgumenten()");
        return delegate.evalueerArgumenten();
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        log.info("pasToe({},{})", argumenten, context);
        return delegate.pasToe(argumenten, context);
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        log.info("berekenBijOptimalisatie()");
        return delegate.berekenBijOptimalisatie();
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        log.info("optimaliseer({},{})", argumenten, context);
        return delegate.optimaliseer(argumenten, context);
    }

}
