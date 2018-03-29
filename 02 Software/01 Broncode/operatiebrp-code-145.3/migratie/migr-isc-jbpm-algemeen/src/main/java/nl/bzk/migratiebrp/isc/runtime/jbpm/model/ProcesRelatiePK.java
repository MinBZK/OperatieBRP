/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;

import org.jbpm.graph.exe.ProcessInstance;

/**
 * Proces relatie PK.
 */

public class ProcesRelatiePK implements Serializable {
    private static final long serialVersionUID = 1L;

    private ProcessInstance processInstanceEen;
    private ProcessInstance processInstanceTwee;

    /**
     * Geeft de eerste proces instantie.
     * @return de eerste proces instantie.
     */
    public ProcessInstance getProcessInstanceEen() {
        return processInstanceEen;
    }

    /**
     * Zet de eerste proces instantie.
     * @param processInstanceEen de te zetten eerste proces instantie
     */
    public void setProcessInstanceEen(final ProcessInstance processInstanceEen) {
        this.processInstanceEen = processInstanceEen;
    }

    /**
     * Geeft de tweede proces instantie.
     * @return de tweede proces instantie
     */
    public ProcessInstance getProcessInstanceTwee() {
        return processInstanceTwee;
    }

    /**
     * Zet de tweede proces instantie.
     * @param processInstanceTwee De te zetten tweede proces instantie
     */
    public void setProcessInstanceTwee(final ProcessInstance processInstanceTwee) {
        this.processInstanceTwee = processInstanceTwee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (processInstanceEen == null ? 0 : (int) processInstanceEen.getId());
        result = prime * result + (processInstanceTwee == null ? 0 : (int) processInstanceTwee.getId());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcesRelatiePK other = (ProcesRelatiePK) obj;
        if (processInstanceEen == null) {
            if (other.processInstanceEen != null) {
                return false;
            }
        } else if (processInstanceEen.getId() != processInstanceTwee.getId()) {
            return false;
        }
        if (processInstanceTwee == null) {
            if (other.processInstanceTwee != null) {
                return false;
            }
        } else if (processInstanceTwee.getId() != other.processInstanceTwee.getId()) {
            return false;
        }
        return true;
    }
}
