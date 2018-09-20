/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Een van toepassing zijnde combinatie van Partij en Rol.
 * <p/>
 * Een Partij kan ��n of meer Rollen vervullen. Elke Partij/Rol combinatie wordt vastgelegd. Dit betekent dat als bijvoorbeeld de partij "Gemeente X" zowel
 * de rol van "Afnemer" als van "Bijhoudingsorgaan" vervult, er twee exemplaren zijn van Partij/Rol: ��n voor elke combinatie.
 */
@Entity
@Table(schema = "Kern", name = "PartijRol")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PartijRol extends AbstractPartijRol {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected PartijRol() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partij partij van PartijRol.
     * @param rol rol van PartijRol.
     * @param datumIngang datumIngang van PartijRol.
     * @param datumEinde datumEinde van PartijRol.
     */
    public PartijRol(final Partij partij, final Rol rol, final DatumAttribuut datumIngang, final DatumAttribuut datumEinde) {
        super(partij, rol, datumIngang, datumEinde);
    }

}
