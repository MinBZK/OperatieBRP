/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.util;

import com.google.common.base.Predicate;

/**
 * Een java import predicate, te gebruiken voor groepering voor een correcte import volgorde.
 */
public class JavaImportPredicate implements Predicate<String> {

    private final boolean matchJava;
    private final boolean matchJavax;
    private final boolean beforeJava;

    /**
     * Constructor.
     *
     * @param matchJava startsWith 'java.'
     * @param matchJavax startsWith 'javax.'
     */
    public JavaImportPredicate(final boolean matchJava, final boolean matchJavax) {
        this(matchJava, matchJavax, false);
    }

    /**
     * Constructor.
     *
     * @param matchJava startsWith 'java.'
     * @param matchJavax startsWith 'javax.'
     * @param beforeJava input string kleiner dan 'java'
     */
    public JavaImportPredicate(final boolean matchJava, final boolean matchJavax, final boolean beforeJava) {
        this.matchJava = matchJava;
        this.matchJavax = matchJavax;
        this.beforeJava = beforeJava;
    }

    /**
     * Pas het predikaat toe. Kijk eerst naar match java, dan naar match javax en als laatste
     * of de 'overige' import klasse voor 'java' of na 'javax' zit.
     * NB: Een hypothetische import zoals 'javac.' valt hier dus buiten de boot. Aanname: komt nooit voor! :)
     *
     * @param input de fully qualified class name van de import
     * @return voldoet wel of niet aan dit predikaat
     */
    @Override
    public boolean apply(final String input) {
        boolean doesApply;
        if (this.matchJava) {
            doesApply = input.startsWith("java.");
        } else if (this.matchJavax) {
            doesApply = input.startsWith("javax.");
        } else if (this.beforeJava) {
            doesApply = "java".compareTo(input.substring(0, "java".length())) > 0;
        } else {
            doesApply = "javax".compareTo(input.substring(0, "javax".length())) < 0;
        }
        return doesApply;
    }

}
