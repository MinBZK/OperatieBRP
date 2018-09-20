/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Naam;
import nl.bzk.copy.model.attribuuttype.PredikaatCode;
import nl.bzk.copy.model.basis.AbstractStatischObjectType;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Wrapper voor de predikaten.
 */
@Entity
@Table(schema = "Kern", name = "Predikaat")
@Access(AccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class Predikaat extends AbstractStatischObjectType {

    /* predikaat objecten hebben een id, code, mannelijke en vrouwelijke aanschrijftitel */
    @Id
    private Short id;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "code"))
    private PredikaatCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naamMannelijk"))
    private Naam naamMannelijk;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "naamVrouwelijk"))
    private Naam naamVrouwelijk;

    public Short getId() {
        return id;
    }

    public PredikaatCode getCode() {
        return code;
    }

    public void setCode(final PredikaatCode code) {
        this.code = code;
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

    public void setId(final Short id) {
        this.id = id;
    }
}
