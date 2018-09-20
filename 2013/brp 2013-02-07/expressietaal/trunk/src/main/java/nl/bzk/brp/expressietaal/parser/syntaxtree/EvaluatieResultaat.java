/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

/**
 * Resultaat van een evaluatie. Als de evaluatie succesvol was, is getExpressie ongelijk aan NULL en is de collectie
 * van fouten leeg.
 */
public class EvaluatieResultaat {

    private final Expressie expressie;
    private final EvaluatieFout fout;

    /**
     * Constructor.
     *
     * @param expressie Resultaat van de evaluatie.
     */
    public EvaluatieResultaat(final Expressie expressie) {
        this.expressie = expressie;
        this.fout = null;
    }

    /**
     * Constructor.
     *
     * @param fout Een gevonden fout.
     */
    private EvaluatieResultaat(final EvaluatieFout fout) {
        this.expressie = null;
        this.fout = fout;
    }

    /**
     * Constructor.
     *
     * @param foutCode   Code van een gevonden fout.
     * @param informatie Informatie over de gevonden fout.
     */
    public EvaluatieResultaat(final EvaluatieFoutCode foutCode, final String informatie) {
        this(new EvaluatieFout(foutCode, informatie));
    }

    /**
     * Constructor.
     *
     * @param foutCode Code van een gevonden fout.
     */
    public EvaluatieResultaat(final EvaluatieFoutCode foutCode) {
        this(foutCode, "");
    }

    public Expressie getExpressie() {
        return expressie;
    }

    public EvaluatieFout getFout() {
        return fout;
    }

    /**
     * Geeft aan of de evaluatie succesvol is verlopen. Succesvol betekent: geen evaluatiefouten en de expressie is
     * geëvalueerd tot constante waarde.
     *
     * @return TRUE als de evaluatie succesvol is uitgevoerd.
     */
    public boolean succes() {
        return getFout() == null && getExpressie() != null
                && (getExpressie().isConstanteWaarde() || getExpressie().isVariabele());
    }

    /**
     * Geeft aan of het evaluatieresultaat een boolean waarde heeft opgeleverd (van belang voor selecties e.d.).
     *
     * @return TRUE als de evaluatie een boolean waarde heeft opgeleverd.
     */
    public boolean isBooleanWaarde() {
        return succes() && (getExpressie().getType() == ExpressieType.BOOLEAN);
    }

    /**
     * Geeft de boolean waarde die expressie heeft opgeleverd terug, indien de expressie succesvol is geëvalueerd naar
     * een boolean waarde. Zo niet, dan wordt FALSE teruggegeven.
     *
     * @return Resultaat van de evaluatie als boolean.
     */
    public boolean getBooleanWaarde() {
        return isBooleanWaarde() && ((BooleanLiteralExpressie) getExpressie()).getValue();
    }
}
