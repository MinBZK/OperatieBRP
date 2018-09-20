/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public enum SetOfModel {

    SET('S'), MODEL('M'), BEIDE('B');

    private static ThreadLocal<Set<SetOfModel>> laagThreadLocal = new ThreadLocal<Set<SetOfModel>>();

    static {
        laagThreadLocal.set(EnumSet.of(MODEL, BEIDE));
    }

    private char code;

    private SetOfModel(final char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public static Set<SetOfModel> getWaardes() {
        return laagThreadLocal.get();
    }

    public static void setWaardes(final SetOfModel... waardes) {
        getWaardes().clear();
        for (SetOfModel waarde : waardes) {
            getWaardes().add(waarde);
        }
    }

    public static Set<Character> getCodes() {
        Set<Character> resultaat = new HashSet<Character>();
        for (SetOfModel waarde : getWaardes()) {
            resultaat.add(waarde.getCode());
        }
        return resultaat;
    }

}
