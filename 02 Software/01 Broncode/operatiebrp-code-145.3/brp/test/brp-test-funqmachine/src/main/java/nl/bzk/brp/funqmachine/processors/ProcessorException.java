/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors;

import nl.bzk.brp.funqmachine.jbehave.context.StepResult;

/**
 * Exception voor als er iets fout gaat in de processors.
 */
public class ProcessorException extends RuntimeException {

    private final boolean negeerExceptie;

    /**
     * Constructor voor deze exceptie met alleen de oorspronkelijke oorzaak.
     * @param cause de oorspronkelijke oorzaak
     */
    public ProcessorException(final Exception cause) {
        this(cause, false);
    }

    /**
     * Constructor met alleen een melding.
     * @param message de melding
     */
    public ProcessorException(final String message) {
        this(message, false);
    }

    /**
     * Constructor voor deze exceptie met alleen de oorspronkelijke oorzaak.
     * @param cause de oorspronkelijke oorzaak
     * @param negeerExceptie indicatie dat deze exceptie genegeerd moet worden en alleen van invloed moet zijn op het {@link StepResult}
     */
    public ProcessorException(final Exception cause, final boolean negeerExceptie) {
        super(cause);
        this.negeerExceptie = negeerExceptie;
    }

    /**
     * Constructor met alleen een melding.
     * @param message de melding
     * @param negeerExceptie indicatie dat deze exceptie genegeerd moet worden en alleen van invloed moet zijn op het {@link StepResult}
     */
    public ProcessorException(final String message, final boolean negeerExceptie) {
        super(message);
        this.negeerExceptie = negeerExceptie;
    }

    /**
     * indicatie dat deze exceptie genegeerd moet worden en alleen van invloed moet zijn op het {@link StepResult}
     * @return true als deze exceptie genegeerd moet worden ander false
     */
    public boolean negeerExceptie() {
        return negeerExceptie;
    }
}
