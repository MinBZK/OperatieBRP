/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpGroepReferentieExpressie;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Default implementatie van interface GroepSolver.
 */
public final class DefaultGroepSolver implements GroepSolver {

    private static final DefaultGroepSolver INSTANCE = new DefaultGroepSolver();

    /**
     * Constructor. Private voor singleton.
     */
    private DefaultGroepSolver() {
    }

    /**
     * Geeft de singleton DefaultSolver.
     *
     * @return Singleton DefaultSolver.
     */
    public static DefaultGroepSolver getInstance() {
        return INSTANCE;
    }

    @Override
    public Expressie bepaalGroep(final BrpObject brpObject, final ExpressieGroep groep) {
        final Expressie gevondenGroepen;

        if (brpObject instanceof ModelPeriode) {
            // Als het brpObject historie heeft (of kan hebben), moeten alle 'historische' groepen worden opgehaald.
            // Dit is te zien aan de implementatie van tagging interface ModelPeriode.
            final List<Groep> groepen = groep.getHistorischeGroepen(brpObject);
            if (groepen != null) {
                final List<Expressie> elementen = new ArrayList<>();
                // Alle gevonden groepen moeten omgezet worden naar een expressie (een BrpGroepReferentieExpressie).
                for (final Groep groepType : groepen) {
                    elementen.add(new BrpGroepReferentieExpressie(groepType));
                }
                gevondenGroepen = new LijstExpressie(elementen);
            } else {
                gevondenGroepen = new FoutExpressie(EvaluatieFoutCode.GROEP_NIET_GEVONDEN, groep.getSyntax());
            }
        } else {
            final Groep groepType = groep.getGroep(brpObject);
            if (groepType != null) {
                gevondenGroepen = new BrpGroepReferentieExpressie(groepType);
            } else {
                gevondenGroepen = new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, groep.getSyntax());
            }
        }
        return gevondenGroepen;
    }
}
