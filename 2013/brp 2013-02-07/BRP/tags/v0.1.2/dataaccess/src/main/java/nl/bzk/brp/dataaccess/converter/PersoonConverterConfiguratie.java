/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * .
 *
 */
public class PersoonConverterConfiguratie {
    private final List<PersoonConverterGroep> groepen = new ArrayList<PersoonConverterGroep>();

    /**
     * default constructor.
     */
    public PersoonConverterConfiguratie() {

    }

    /**
     * constructor met een lijst van groepen.
     * @param grp de lijst van groepen
     */
    public PersoonConverterConfiguratie(final PersoonConverterGroep... grp) {
        if (null != grp) {
            groepen.addAll(Arrays.asList(grp));
        }
    }

    public List<PersoonConverterGroep> getGroepen() {
        return groepen;
    }

    /**
     * Geeft aan of een groep geconverteerd dient te worden.
     * Merk op dat als de lijst leeg is, alle groepen geconverteerd dienen te worden.
     * @param groep de gevraagde groep
     * @return true als geconteerd moet worden, false anders
     */
    public boolean isConverteerbaar(final PersoonConverterGroep groep) {
        if (groepen.isEmpty()) {
            // default is alles aan, zodra je een definieert, moet je alle definieren.
            return true;
        } else {
            return groepen.contains(groep);
        }
    }
}
