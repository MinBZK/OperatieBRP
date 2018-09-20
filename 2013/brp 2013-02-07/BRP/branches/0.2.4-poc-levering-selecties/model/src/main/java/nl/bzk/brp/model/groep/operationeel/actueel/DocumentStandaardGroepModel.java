/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.attribuuttype.Aktenummer;
import nl.bzk.brp.model.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.model.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.model.groep.logisch.DocumentStandaardGroep;
import nl.bzk.brp.model.groep.logisch.basis.DocumentStandaardGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractDocumentStandaardGroepModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;
/**
 *
 * .
 */
@SuppressWarnings("serial")
@Embeddable
public class DocumentStandaardGroepModel extends AbstractDocumentStandaardGroepModel implements DocumentStandaardGroep, Externalizable {

    /**
     * .
     */
    public DocumentStandaardGroepModel() {
        super();
    }
    /**
     * .
     * @param that .
     */
    public DocumentStandaardGroepModel(final DocumentStandaardGroepBasis that) {
        super(that);
    }

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		ExternalWriterService.schrijfWaarde(out, getAktenummer());
		ExternalWriterService.schrijfWaarde(out, getIdentificatie());
		ExternalWriterService.schrijfWaarde(out, getOmschrijving());
        ExternalWriterService.schrijfNullableObject(out, getPartij());
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		setAktenummer((Aktenummer) ExternalReaderService.leesWaarde(in, Aktenummer.class));
		setIdentificatie((DocumentIdentificatie) ExternalReaderService.leesWaarde(in, DocumentIdentificatie.class));
		setOmschrijving((DocumentOmschrijving) ExternalReaderService.leesWaarde(in, DocumentOmschrijving.class));

		setPartij((Partij) ExternalReaderService.leesNullableObject(in, Partij.class));
	}
}
