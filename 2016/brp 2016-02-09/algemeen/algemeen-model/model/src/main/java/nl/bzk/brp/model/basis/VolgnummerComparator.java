/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import org.hibernate.proxy.HibernateProxy;


/**
 * Een {@link Comparator} implementatie die objecten die de {@link VolgnummerBevattend} interface implementeren en dus op volgnummer kunnen worden
 * gesorteerd.
 */
@Regels(Regel.VR00092)
public final class VolgnummerComparator implements Comparator<VolgnummerBevattend>, Serializable {

    /**
     * Vergelijkt de twee opgegeven argumenten op volgnummer. Retourneert een negatief getal, nul of een positief getal indien het eerste argument kleiner
     * dan, gelijk of groter is dan het tweede argument.
     * <p/>
     * Merk op dat deze comparator een volgorde retourneert die (mogelijk) inconsistent is met de equals methode.
     *
     * @param obj1 het eerste object in de vergelijking.
     * @param obj2 het tweede object in de vergelijking.
     * @return een negatief getal, nul of een positief getal indien het eerste argument kleiner dan, gelijk of groter is dan het tweede argument.
     */
    @Override
    public int compare(final VolgnummerBevattend obj1, final VolgnummerBevattend obj2) {
        if (volgnummerHeeftGeenWaarde(obj1) || volgnummerHeeftGeenWaarde(obj2)) {
            throw new IllegalArgumentException("Missend object of volgnummer, waardoor deze niet te vergelijken is.");
        }
        if (!getClass(obj1).equals(getClass(obj2))) {
            throw new IllegalArgumentException("Ongelijke types van objecten mogen niet worden vergeleken met deze comparator (obj1: "
                                                    + obj1.getClass().getName() + ", obj2: " + obj2.getClass().getName() + ").");
        }

        return obj1.getVolgnummer().getWaarde().compareTo(obj2.getVolgnummer().getWaarde());
    }

    /**
     * Geef de klasse van het object (unwrap hibernate proxy classes).
     *
     * @param obj object
     * @return klasse
     */
    private Class<?> getClass(final VolgnummerBevattend obj) {
    	if (obj instanceof HibernateProxy) {
            return ((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation().getClass();
        } else {
        	return obj.getClass();
        }
	}

	/**
     * Controleert of er geen waarde gezet is.
     *
     * @param volgnummerBevattend Het volgnummer.
     * @return True als er een waarde is, anders false.
     */
    private boolean volgnummerHeeftGeenWaarde(final VolgnummerBevattend volgnummerBevattend) {
        return volgnummerBevattend == null || volgnummerBevattend.getVolgnummer() == null || volgnummerBevattend.getVolgnummer().getWaarde() == null;
    }

}
