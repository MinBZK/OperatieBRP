/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Parser geoptimaliseerd voor het opvragen van een enum o.b.v. een id of code of naam.
 *
 * @param <E> Het concrete enumeratie-type.
 */
public class EnumMetNaamParser<E extends Enumeratie> extends EnumParser<E> {
    private final Map<String, E> enumaratiesOpNaam;

    /**
     * Maakt een concrete enumparser aan voor het meegegeven enumeratietype.
     *
     * @param clazz Het enumeratietype
     */
    public EnumMetNaamParser(final Class<E> clazz) {
        super(clazz);
        enumaratiesOpNaam = maakIndexVanEnumeratieOpNaam(clazz);
    }

    private Map<String, E> maakIndexVanEnumeratieOpNaam(final Class<E> clazz) {
        final Map<String, E> result = new HashMap<>();
        for (final E sge : clazz.getEnumConstants()) {
            result.put(sge.getNaam(), sge);
        }
        return result;
    }

    /**
     * Geeft een enumeratiewaarde van type {@link E} terug o.b.v. de naam.
     *
     * @param naam De naam van de enumeratie. Mag null zijn, in dat geval wordt ook null
     *        geretourneerd.
     * @return Een enumeratiewaarde van type {@link E}, of null als het argument naam null is
     * @throws IllegalArgumentException als de enumeratiewaarde met bijbehorende code niet gevonden
     *         kon worden.
     */
    public final E parseNaam(final String naam) {
        if (naam == null) {
            return null;
        }
        final E result = enumaratiesOpNaam.get(naam);
        if (result == null) {
            throw new IllegalArgumentException(String.format(getFoutmelding(), "code", naam));
        }
        return result;
    }
}
