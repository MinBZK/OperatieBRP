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
 * IPAdresBasis.
 * @version ${metaregister.version}.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class IPAdresBasis extends AbstractStatischAttribuutType<String> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private IPAdres() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public IPAdres(final String waarde) {
        super(waarde);
    }
}
