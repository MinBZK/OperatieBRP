/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Adellijke titels.
 */
@Entity
@Table(schema = "Kern", name = "AdellijkeTitel")
@Access(AccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class AdellijkeTitel extends AbstractStatischObjectType {

    /* Addelijke titel heeft een id, code, mannelijk en vrouwelijke aanschrijving */
    @Id
    @Column(name = "id")
    private Short adellijkeTitelId;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naamMannelijk"))
    private Naam naamMannelijk;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naamVrouwelijk"))
    private Naam naamVrouwelijk;

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

    public void setAdellijkeTitelId(final Short adellijkeTitelId) {
        this.adellijkeTitelId = adellijkeTitelId;
    }
}
