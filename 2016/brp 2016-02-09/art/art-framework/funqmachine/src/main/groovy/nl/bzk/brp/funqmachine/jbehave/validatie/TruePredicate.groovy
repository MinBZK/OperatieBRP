package nl.bzk.brp.funqmachine.jbehave.validatie

import com.google.common.base.Predicate

/**
 * Predicate dat altijd "Waar" teruggeeft.
 */
class TruePredicate implements Predicate<Object> {

    @Override
    boolean apply(Object input) {
        return true
    }
}
