/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 * stamgegeven soort document.
 */
@Entity
@Table(schema = "Kern", name = "SrtDoc")
@Access(value = AccessType.FIELD)
public class SoortDocument extends AbstractStatischObjectType implements Externalizable {
    @Id
    private Short                  iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private Naam   naam;

    @Column(name = "CategorieSrtDoc")
    private CategorieSoortDocument categorieSoortDocument;

//    /**
//     * Constructor is private, want de BRP zal geen stamgegevens beheren.
//     *
//     */
//    private SoortDocument() {
//    }

    /**
     * Retourneert Naam van Soort document.
     *
     * @return Naam.
     */
    public Naam getNaam() {
        return naam;
    }

    /**
     * Retourneert Categorie soort document van Soort document.
     *
     * @return Categorie soort document.
     */
    public CategorieSoortDocument getCategorieSoortDocument() {
        return categorieSoortDocument;
    }

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		ExternalWriterService.schrijfEnum(out, getCategorieSoortDocument());
		ExternalWriterService.schrijfWaarde(out, getNaam());
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		categorieSoortDocument = (CategorieSoortDocument) ExternalReaderService.leesEnum(in, CategorieSoortDocument.class);
		naam = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
	}

}
