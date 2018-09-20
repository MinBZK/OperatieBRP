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

import nl.bzk.brp.model.groep.operationeel.actueel.DocumentStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.Document;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractDocumentModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortDocument;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 *
 * .
 *
 */
@Entity
@Table(schema = "Kern", name = "Doc")
public class DocumentModel extends AbstractDocumentModel implements Document, Externalizable {

	/**
	 * .
	 */
	public DocumentModel() {
		super();
	}

	/**
	 * .
	 *
	 * @param that
	 *            .
	 */
	public DocumentModel(final Document that) {
		super(that);
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		// Geen setter voor bronnen en id
//		for (BronModel bron : getBronnen()) {
//			bron.writeExternal(out);
//		}
//		out.writeLong(getId().longValue());
		ExternalWriterService.schrijfNullableObject(out, getSoort());
		ExternalWriterService.schrijfNullableObject(out, getStandaard());
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		setSoort((SoortDocument) ExternalReaderService.leesNullableObject(in, SoortDocument.class));
		setStandaard((DocumentStandaardGroepModel) ExternalReaderService.leesNullableObject(in, DocumentStandaardGroepModel.class));
	}
}
