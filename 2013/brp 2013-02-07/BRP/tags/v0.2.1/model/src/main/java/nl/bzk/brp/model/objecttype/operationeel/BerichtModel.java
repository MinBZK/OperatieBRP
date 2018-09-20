/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Berichtdata;
import nl.bzk.brp.model.objecttype.logisch.Bericht;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractBerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Richting;


/**
 * Een bericht voor berichtarchivering.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Ber", schema = "Ber")
public class BerichtModel extends AbstractBerichtModel implements Bericht {

    /**
     * Default constructor. Vereist voor Hibernate.
     */
    @SuppressWarnings("unused")
    protected BerichtModel() {
        super();
    }

    /**
     * .
     *
     * @param richting Richting
     * @param data Berichtdata
     */
    public BerichtModel(final Richting richting, final Berichtdata data) {
        super(richting, data);
    }

}
