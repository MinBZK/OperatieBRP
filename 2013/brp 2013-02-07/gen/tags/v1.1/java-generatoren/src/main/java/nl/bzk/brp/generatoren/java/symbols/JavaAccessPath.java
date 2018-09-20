/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

/**
 * Created with IntelliJ IDEA.
 * User: maels
 * Date: 31-01-13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class JavaAccessPath {
    private String methodCall;
    private JavaAccessPath next;

    /**
     * Constructor.
     *
     * @param methodCall Signatuur van methode-aanroep.
     * @param path       Pad dat volgt op deze aanroep.
     */
    public JavaAccessPath(final String methodCall, final JavaAccessPath path) {
        this.methodCall = methodCall;
        this.next = path;
    }

    /**
     * Constructor.
     *
     * @param methodCall Signatuur van methode-aanroep.
     */
    public JavaAccessPath(final String methodCall) {
        this(methodCall, null);
    }

    /**
     * /**
     * Voegt een pad toen aan dit object.
     *
     * @param path Pad om toe te voegen.
     */
    public void voegToe(final JavaAccessPath path) {
        if (next == null) {
            next = path;
        } else {
            next.voegToe(path);
        }
    }

    /**
     * Geeft TRUE als dit object de laatste aanroep in de keten is (en dus niet een object maar een basistype
     * teruggeeft.
     *
     * @return TRUE als deze aanroep de laatste is.
     */
    public boolean isLaatsteAanroep() {
        return (next == null);
    }

    public String getMethodCall() {
        return methodCall;
    }

    public JavaAccessPath getNext() {
        return next;
    }

    @Override
    public String toString() {
        if (isLaatsteAanroep()) {
            return methodCall;
        } else {
            return methodCall + "." + next.toString();
        }
    }
}
