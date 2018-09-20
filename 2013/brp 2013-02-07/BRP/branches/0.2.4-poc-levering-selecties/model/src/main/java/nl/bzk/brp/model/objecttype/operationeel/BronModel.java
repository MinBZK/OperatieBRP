/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.logisch.Bron;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractBronModel;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 * .
 *
 */
@Entity
@Table(schema = "Kern", name = "Bron")
public class BronModel extends AbstractBronModel implements Bron, Externalizable {

    /**
     * .
     */
    public BronModel() {
        super();
    }

    /**
     * .
     * @param that .
     * @param actieModel .
     */
    public BronModel(final Bron that, final ActieModel actieModel) {
        super(that, actieModel);
    }

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		// Actie wordt niet geschreven (einde van blob)
		ExternalWriterService.schrijfNullableObject(out, getDocument());
		out.writeObject(getId());
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		setDocument((DocumentModel) ExternalReaderService.leesNullableObject(in, DocumentModel.class));
		setId(ExternalReaderService.leesInteger(in));
	}

}
