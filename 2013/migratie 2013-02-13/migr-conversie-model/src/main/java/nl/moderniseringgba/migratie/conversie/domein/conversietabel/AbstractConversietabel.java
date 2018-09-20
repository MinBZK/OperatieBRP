/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;

/**
 * Bevat conversie mapping functionaliteit.
 * 
 * @param <L>
 *            de LO3 waarde
 * @param <B>
 *            de BRP waarde
 */
public abstract class AbstractConversietabel<L, B> implements Conversietabel<L, B> {

    private final Map<L, B> mapLo3NaarBrp;
    private final Map<B, L> mapBrpNaarLo3;

    /**
     * Maakt een AbstractConversietabel object.
     * 
     * @param conversieLijst
     *            de lijst met SoortNlReisdocument conversies.
     */
    public AbstractConversietabel(final List<Map.Entry<L, B>> conversieLijst) {
        mapLo3NaarBrp = new HashMap<L, B>();
        mapBrpNaarLo3 = new HashMap<B, L>();

        for (final Map.Entry<L, B> conversie : conversieLijst) {
            mapLo3NaarBrp.put(conversie.getKey(), conversie.getValue());
            mapBrpNaarLo3.put(conversie.getValue(), conversie.getKey());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final B converteerNaarBrp(final L input) {
        B result = null;
        if (input != null) {
            if (!mapLo3NaarBrp.containsKey(input)) {
                throw FoutmeldingUtil.getValidatieExceptie(String.format(
                        "Er is geen mapping naar BRP voor LO3 waarde '%s' in conversietabel %s", input, this
                                .getClass().getSimpleName()), Precondities.PRE054);
            }
            result = mapLo3NaarBrp.get(input);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final L converteerNaarLo3(final B input) {
        L result = null;
        if (input != null) {
            if (!mapBrpNaarLo3.containsKey(input)) {
                throw FoutmeldingUtil.getValidatieExceptie(String.format(
                        "Er is geen mapping naar LO3 voor BRP waarde '%s' in conversietabel %s", input, this
                                .getClass().getSimpleName()), Precondities.PRE054);
            }
            result = mapBrpNaarLo3.get(input);
        }
        return result;
    }

    @Override
    public final boolean valideerLo3(final L input) {
        return input == null || mapLo3NaarBrp.containsKey(input);
    }

    @Override
    public final boolean valideerBrp(final B input) {
        return input == null || mapBrpNaarLo3.containsKey(input);
    }

}
