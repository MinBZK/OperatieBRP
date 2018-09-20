/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie ${metaregister.version}.
 *
 */
package nl.bzk.brp.model.attribuuttype.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractStatischAttribuutType;

/**
 * DatumBasis.
 * @version ${metaregister.version}.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class DatumBasis extends AbstractStatischAttribuutType<Integer> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private Datum() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public Datum(final Integer waarde) {
        super(waarde);
    }
}
