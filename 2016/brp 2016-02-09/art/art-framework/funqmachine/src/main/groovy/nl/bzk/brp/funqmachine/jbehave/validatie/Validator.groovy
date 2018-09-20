package nl.bzk.brp.funqmachine.jbehave.validatie

import com.google.common.base.Optional

/**
 * Validatie interface.
 */
interface Validator<T> {
    /**
     * Valideer een input.
     * @param input de input om te valideren
     * @return Optional. Heeft {@code true} of {@code false} voor het
     * resultaat of is absent als het niet kan worden bepaald
     */
    Optional<Boolean> valideer(T input);
}
