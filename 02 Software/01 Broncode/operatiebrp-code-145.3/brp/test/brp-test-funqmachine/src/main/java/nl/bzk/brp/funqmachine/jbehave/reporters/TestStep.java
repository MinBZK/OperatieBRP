/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.reporters;

/**
 * Implementatie voor een jbehave step in een scenario.
 */
public class TestStep {

    private String name;
    private Throwable ex;
    private Status status;

    /**
     * Geeft aan of de step succesvol was.
     * 
     * @return true als de stap geen exceptie bevat en dus succesvol.
     */
    public boolean successful() {
        return ex == null;
    }

    /**
     * Geeft aan of de stap gefaald is.
     * 
     * @return true als de stap gefaald is.
     */
    public boolean failed() {
        return !successful();
    }

    /**
     * Geeft de naam van de stap terug.
     * 
     * @return de naam
     */
    public String getName() {
        return name;
    }

    /**
     * Zet de naam van de stap.
     * 
     * @param name de naam
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Geeft de exceptie terug.
     * 
     * @return de exceptie als deze opgetreden is, anders null.
     */
    public Throwable getEx() {
        return ex;
    }

    /**
     * Zet de opgetreden exceptie.
     * 
     * @param ex de exceptie
     */
    public void setEx(Throwable ex) {
        this.ex = ex;
    }

    /**
     * Geeft de status van deze stap terug.
     * 
     * @return de {@link Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Zet de status van deze stap.
     * 
     * @param status de {@link Status}
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Mogelijke statussen voor een stap.
     */
    public enum Status {
        FAILED, ERROR, SKIPPED;
    }
}
