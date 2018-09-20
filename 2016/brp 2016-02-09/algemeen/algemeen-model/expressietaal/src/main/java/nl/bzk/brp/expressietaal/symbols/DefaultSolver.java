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
import nl.bzk.brp.expressietaal.expressies.literals.BrpAttribuutReferentieExpressie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Default implementatie van interface AttributeSolver.
 */
public final class DefaultSolver implements AttributeSolver {

    private static final DefaultSolver INSTANCE = new DefaultSolver();

    /**
     * Constructor. Private voor singleton.
     */
    private DefaultSolver() {
    }


    /**
     * Geeft de singleton DefaultSolver.
     *
     * @return Singleton DefaultSolver.
     */
    public static DefaultSolver getInstance() {
        return INSTANCE;
    }

    @Override
    public Expressie bepaalWaarde(final BrpObject brpObject, final ExpressieAttribuut attribute) {
        Expressie resultaat = attribute.getAttribuutWaarde(brpObject);
        if (resultaat == null) {
            // Attribuut is niet gevonden. Resultaat is dus een foutexpressie.
            resultaat = new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribute.getSyntax());
        }
        return resultaat;
    }

    @Override
    public Expressie bepaalAttribuut(final BrpObject brpObject, final ExpressieAttribuut attribute) {
        Expressie gevondenAttributen;

        if (brpObject instanceof ModelPeriode) {
            // Als het brpObject historie heeft (of kan hebben), moeten alle 'historische' attributen worden opgehaald.
            // Dit is te zien aan de implementatie van tagging interface ModelPeriode.
            final List<Attribuut> attributen = attribute.getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                // Alle gevonden attributen moeten omgezet worden naar een expressie (een
                // BrpAttribuutReferentieExpressie).
                for (Attribuut attribuutType : attributen) {
                    elementen.add(new BrpAttribuutReferentieExpressie(attribuutType));
                }
                gevondenAttributen = new LijstExpressie(elementen);
            } else {
                gevondenAttributen =
                    new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribute.getSyntax());
            }
        } else {
            final Attribuut attribuutType = attribute.getAttribuut(brpObject);
            if (attribuutType != null) {
                gevondenAttributen = new BrpAttribuutReferentieExpressie(attribuutType);
            } else {
                gevondenAttributen =
                    new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribute.getSyntax());
            }
        }
        return gevondenAttributen;
    }
}
