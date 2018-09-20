/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;

/**
 * Util klasse voor persoon indicatie.
 */
public final class IndicatieUtil {

    /**
     * Niet instantieerbaar.
     */
    private IndicatieUtil() {
    }

    /**
     * Bepaald de historie van de persoon indicatie.
     * @param hisIndicatie De verzameling met indicatie historie.
     * @param clazz De klasse waarvoor de indicatie historie wordt bepaald.
     * @return Verzameling van historie van de persoon indicatie van de meegegeven klasse.
     * @param <T> Type van de historie.
     */
    public static <T extends HisPersoonIndicatieModel> Historie<T> bepaalIndicatieHistorie(
        final Historie<HisPersoonIndicatieModel> hisIndicatie,
        final Class<T> clazz)
    {
        final MaterieleHistorie<T> result = new MaterieleHistorie<>();

        result.setInhoud(bepaalIndicatieSet(hisIndicatie.getInhoud(), clazz));
        //result.setGeldigheid(bepaalIndicatieSet(hisIndicatie.getGeldigheid(), clazz));
        result.setVerval(bepaalIndicatieSet(hisIndicatie.getVerval(), clazz));
        result.setVervalTbvMutatie(bepaalIndicatieSet(hisIndicatie.getVervalTbvMutatie(), clazz));

        return result;
    }

    private static <T extends HisPersoonIndicatieModel> Set<T> bepaalIndicatieSet(final Set<HisPersoonIndicatieModel> indicatieSet, final Class<T> clazz) {
        final Set<T> result = new HashSet<>();

        for (final HisPersoonIndicatieModel indicatie : indicatieSet) {
            if (clazz.isAssignableFrom(indicatie.getClass())) {
                result.add(clazz.cast(indicatie));
            }
        }

        return result;
    }

}
