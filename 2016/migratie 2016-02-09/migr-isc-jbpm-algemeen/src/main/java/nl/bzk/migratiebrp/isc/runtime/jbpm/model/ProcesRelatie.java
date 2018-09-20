/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;

import org.jbpm.graph.exe.ProcessInstance;

/**
 * Relatie tussen twee processen.
 */
@SuppressWarnings("checkstyle:designforextension")
public class ProcesRelatie implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProcesRelatiePK id = new ProcesRelatiePK();

    /**
     * Geeft het ID.
     *
     * @return het ID
     */
    public ProcesRelatiePK getId() {
        return id;
    }

    /**
     * Zet het ID.
     *
     * @param id
     *            Het te zetten ID.
     */
    public void setId(final ProcesRelatiePK id) {
        this.id = id;
    }

    /**
     * Geef process instance 1 (uit id).
     *
     * @return process instance 1 (uit id).
     */
    public ProcessInstance getProcessInstanceEen() {
        return id.getProcessInstanceEen();
    }

    /**
     * Zet process instance 1 (in id).
     *
     * @param processInstanceEen
     *            process instance
     */
    public void setProcessInstanceEen(final ProcessInstance processInstanceEen) {
        id.setProcessInstanceEen(processInstanceEen);
    }

    /**
     * Geef process instance 2 (uit id).
     *
     * @return process instance 2 (uit id).
     */
    public ProcessInstance getProcessInstanceTwee() {
        return id.getProcessInstanceTwee();
    }

    /**
     * Zet process instance 2 (in id).
     *
     * @param processInstanceTwee
     *            process instance
     */
    public void setProcessInstanceTwee(final ProcessInstance processInstanceTwee) {
        id.setProcessInstanceTwee(processInstanceTwee);
    }

}
