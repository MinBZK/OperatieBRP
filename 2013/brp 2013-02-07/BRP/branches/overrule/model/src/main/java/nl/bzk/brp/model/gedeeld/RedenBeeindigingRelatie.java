/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Gegevens model object voor Reden beeindiging relatie.
 */
@Entity
@Table(schema = "kern", name = "rdnbeeindrelatie")
public class RedenBeeindigingRelatie {

    @Id
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "oms")
    private String omschrijving;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
