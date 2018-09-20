/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie ${metaregister.version}.
 *
 */
package nl.bzk.brp.model.attribuuttype.basis;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.basis.AbstractStatischAttribuutType;

/**
 * IPAdresBasis.
 * @version ${metaregister.version}.
 */
@Embeddable
public abstract class IPAdresBasis extends AbstractStatischAttribuutType<String> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private IPAdresBasis() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public IPAdresBasis(final String waarde) {
        super(waarde);
    }
}
