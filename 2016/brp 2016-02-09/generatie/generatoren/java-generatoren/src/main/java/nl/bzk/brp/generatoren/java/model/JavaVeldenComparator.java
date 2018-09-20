/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.Comparator;

/**
 * Comparator om Java velden (class variabelen) te sorteren volgens de checkstyle regels.
 * Class (static) variables. First the public class variables, then the protected, then package level (no access modifier), and then the private.
 * Instance variables. First the public class variables, then the protected, then package level (no access modifier), and then the private.
 */
public class JavaVeldenComparator implements Comparator<JavaVeld> {

    private static final Integer PUBLIC_ACCESS_MODIFIER_VALUE = 1;
    private static final Integer PROTECTED_ACCESS_MODIFIER_VALUE = 2;
    private static final Integer PACKAGE_LEVEL_ACCESS_MODIFIER_VALUE = 3;
    private static final Integer PRIVATE_ACCESS_MODIFIER_VALUE = 4;

    @Override
    public int compare(final JavaVeld veld1, final JavaVeld veld2) {
        if (veld1 == null && veld2 == null) {
            return 0;
        }

        if (veld1 == null) {
            return -1;
        }

        if (veld2 == null) {
            return 1;
        }

        int resultaat = vergelijkStatic(veld1, veld2);

        if (resultaat == 0) {
            resultaat = vergelijkAccessModifier(veld1, veld2);
        }
        if (resultaat == 0) {
            resultaat = 1;
        }
        return resultaat;
    }

    private Integer vergelijkAccessModifier(final JavaVeld veld1, final JavaVeld veld2) {
        return bepaalZwaarteAccessModifierVoorVeld(veld1).compareTo(bepaalZwaarteAccessModifierVoorVeld(veld2));
    }

    private Integer bepaalZwaarteAccessModifierVoorVeld(final JavaVeld veld) {
        if (veld.isPublic()) {
            return PUBLIC_ACCESS_MODIFIER_VALUE;
        } else if (veld.isPrivate()) {
            return PRIVATE_ACCESS_MODIFIER_VALUE;
        } else if (veld.isPackageLevelAccess()) {
            return PACKAGE_LEVEL_ACCESS_MODIFIER_VALUE;
        } else if (veld.isProtected()) {
            return PROTECTED_ACCESS_MODIFIER_VALUE;
        }
        throw new IllegalStateException("Kan access modifier van veld niet bepalen!");
    }

    private int vergelijkStatic(final JavaVeld veld1, final JavaVeld veld2) {
        if (veld1.isStatic() && veld2.isStatic()) {
            return 0;
        }

        if (veld1.isStatic()) {
            return -1;
        }

        if (veld2.isStatic()) {
            return 1;
        }

        //Allebei niet static:
        return 0;
    }
}
