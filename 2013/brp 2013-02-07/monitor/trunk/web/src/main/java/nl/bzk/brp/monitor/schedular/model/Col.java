/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular.model;

/**
 * Kolom definitie.
 *
 */
public class Col {

    private String id;
    private String label;
    private String type;

    /**
     * Constructor van een kolom.
     *
     * @param id id van de kolom
     * @param label label van de kolom
     * @param type type van de kolom
     */
    public Col(final String id, final String label, final String type) {
        this.id = id;
        this.label = label;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
