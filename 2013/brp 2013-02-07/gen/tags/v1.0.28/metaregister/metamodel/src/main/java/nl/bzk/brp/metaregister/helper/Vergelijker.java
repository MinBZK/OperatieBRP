/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.helper;

import java.lang.reflect.Method;
import java.util.Comparator;

import nl.bzk.brp.metaregister.model.Regel;
import nl.bzk.brp.metaregister.model.Tuple;


public class Vergelijker implements Comparator<Object> {

    @Override
    public final int compare(final Object o1, final Object o2) {
    	// Java 7 fix: de TreeMap (en ook TreeSet omdat die intern een TreeMap gebruikt)
    	// doet bij een put(key, value) direct een compare(key, key), oftewel een compare
    	// met exact hetzelfde object. Deze situatie zorgde voor een crash, vanwege gelijke identiteit.
    	if (o1 == o2) {
    		return 0;
    	}
    	
        int resultaat;
        if (o1 instanceof Regel) {
            resultaat = ((Regel) o1).getNaam().compareTo(((Regel) o2).getNaam());
        } else if (o1 instanceof Tuple) {
            resultaat = ((Tuple)o1).getVolgnummerT().compareTo(((Tuple)o2).getVolgnummerT());
        } else {
            resultaat = getVolgnummer(o1) - getVolgnummer(o2);
        }
        if (resultaat == 0) {
            throw new RuntimeException("er kunnen geen gelijkwaardige objecten bestaan in hibernate sorted sets");
        }
        return resultaat;
    }

    private int getVolgnummer(final Object o) {
        int resultaat;
        Integer volgnummer;
        try {
            Method method = o.getClass().getMethod("getVolgnummer");
            volgnummer = (Integer) method.invoke(o);
        } catch (NoSuchMethodException e) {
            volgnummer = 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (volgnummer == null) {
            resultaat = Integer.MIN_VALUE;
        } else {
            resultaat = volgnummer.intValue();
        }
        return resultaat;
    }

}
