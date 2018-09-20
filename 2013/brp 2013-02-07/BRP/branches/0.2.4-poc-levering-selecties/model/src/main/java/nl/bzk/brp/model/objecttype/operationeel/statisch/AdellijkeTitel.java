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

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/**
 * Adellijke titels.
 *
 */
@Entity
@Table(schema = "Kern", name = "AdellijkeTitel")
@Access(AccessType.FIELD)
public class AdellijkeTitel extends AbstractStatischObjectType implements Externalizable {

    /* Addelijke titel heeft een id, code, mannelijk en vrouwelijke aanschrijving */
    @Id
    @Column(name = "id")
    private Short              adellijkeTitelId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naamMannelijk"))
    private Naam               naamMannelijk;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naamVrouwelijk"))
    private Naam               naamVrouwelijk;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private AdellijkeTitelCode adellijkeTitelCode;

    public Short getAdellijkeTitelId() {
        return adellijkeTitelId;
    }

    public Naam getNaamMannelijk() {
        return naamMannelijk;
    }

    public void setNaamMannelijk(final Naam naamMannelijk) {
        this.naamMannelijk = naamMannelijk;
    }

    public Naam getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

    public void setNaamVrouwelijk(final Naam naamVrouwelijk) {
        this.naamVrouwelijk = naamVrouwelijk;
    }

    public AdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    public void setAdellijkeTitelCode(final AdellijkeTitelCode adellijkeTitelCode) {
        this.adellijkeTitelCode = adellijkeTitelCode;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(adellijkeTitelId);
        ExternalWriterService.schrijfWaarde(out, naamMannelijk);
        ExternalWriterService.schrijfWaarde(out, naamVrouwelijk);
        ExternalWriterService.schrijfWaarde(out, adellijkeTitelCode);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.adellijkeTitelId = (Short) in.readObject();
        this.naamMannelijk = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
        this.naamVrouwelijk = (Naam) ExternalReaderService.leesWaarde(in, Naam.class);
        this.adellijkeTitelCode = (AdellijkeTitelCode) ExternalReaderService.leesWaarde(in, AdellijkeTitelCode.class);
    }
}
