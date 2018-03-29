/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Taak gerelateerde gegevens.
 */

public class TaakGerelateerd implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private TaskInstance taskInstance;
    private String administratienummer;

    /**
     * Geeft het ID.
     * @return het ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het ID.
     * @param id Het te zetten ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft de task instantie.
     * @return de task instantie
     */
    public TaskInstance getTaskInstance() {
        return taskInstance;
    }

    /**
     * Zet de task instantie.
     * @param taskInstance De te zetten task instantie
     */
    public void setTaskInstance(final TaskInstance taskInstance) {
        this.taskInstance = taskInstance;
    }

    /**
     * Geeft het administratienummer.
     * @return het administratienummer
     */
    public String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Zet het administratienummer.
     * @param administratienummer het te zetten administratienummer
     */
    public void setAdministratienummer(final String administratienummer) {
        this.administratienummer = administratienummer;
    }

}
