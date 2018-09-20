/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.validatie.constraint.Volgnummers;


/**
 * Validator die controleert of in een groep de Volgnummers van aaneensluitend genummerd zijn beginnend met de waarde “1”.
 */
public class VolgnummersValidator implements ConstraintValidator<Volgnummers, Collection<? extends BrpObject>> {

    @Override
    public final void initialize(final Volgnummers constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final Collection<? extends BrpObject> collectie, final ConstraintValidatorContext context) {
        final boolean resultaat;

        if (collectie != null && collectie.size() > 0) {
            final List<Integer> volgnummers = converteerObjectTypesNaarIntegers(collectie);

            resultaat =
                heeftUniekeVolgnummers(volgnummers) && heeftVolgnummerEen(volgnummers)
                    && heeftOpeenvolgendeVolgnummers(volgnummers);
        } else {
            resultaat = true;
        }

        return resultaat;
    }

    /**
     * Converteert object types naar integers.
     *
     * @param collectie collectie objecttypes
     * @return lijst integers
     */
    private List<Integer> converteerObjectTypesNaarIntegers(final Collection<? extends BrpObject> collectie) {
        final List<Integer> volgnummers = new ArrayList<>();
        final Object object = collectie.toArray()[0];

        if (object instanceof PersoonVoornaam) {
            for (final BrpObject objectType : collectie) {
                volgnummers.add(((PersoonVoornaam) objectType).getVolgnummer().getWaarde());
            }
        } else if (object instanceof PersoonGeslachtsnaamcomponent) {
            for (final BrpObject objectType : collectie) {
                volgnummers.add(((PersoonGeslachtsnaamcomponent) objectType).getVolgnummer().getWaarde());
            }
        } else {
            throw new UnsupportedOperationException("Objectype " + object.getClass().getName()
                + " wordt niet ondersteund.");
        }

        return volgnummers;
    }

    /**
     * Controleert of lijst unieke volgnummers heeft.
     *
     * @param volgnummers volgnummers
     * @return true als lijst unieke volgnummers heeft
     */
    private boolean heeftUniekeVolgnummers(final List<Integer> volgnummers) {
        boolean resultaat = true;

        final Map<Integer, Integer> volgnrs = new HashMap<>();

        for (final Integer v : volgnummers) {
            if (volgnrs.containsKey(v)) {
                resultaat = false;
                break;
            } else {
                volgnrs.put(v, v);
            }
        }

        return resultaat;
    }

    /**
     * Controleert of lijst volgnummer 1 heeft.
     *
     * @param volgnummers volgnummers
     * @return true als lijst volgnummer 1 bevat
     */
    private boolean heeftVolgnummerEen(final List<Integer> volgnummers) {
        boolean resultaat = false;

        for (final Integer v : volgnummers) {
            if (v == 1) {
                resultaat = true;
                break;
            }
        }

        return resultaat;
    }

    /**
     * Conctroleert of lijst opeenvolgende volgnummers heeft.
     *
     * @param volgnummers volgnummers
     * @return true als volgnummers opeenvolgend zijn.
     */
    private boolean heeftOpeenvolgendeVolgnummers(final List<Integer> volgnummers) {
        boolean resultaat = true;
        final SortedSet<Integer> volgnr = new TreeSet<>();

        for (final Integer v : volgnummers) {
            volgnr.add(v);
        }

        final Object[] array = volgnr.toArray();

        for (int i = 0; i < array.length - 1; i++) {
            final Integer getal1 = (Integer) array[i];
            final Integer getal2 = (Integer) array[i + 1];

            if (!((getal1 + 1) == getal2)) {
                resultaat = false;
                break;
            }
        }

        return resultaat;
    }
}
