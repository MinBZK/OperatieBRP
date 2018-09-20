/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * De basis voor materiele history.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractMaterieleEnFormeleHistorie extends AbstractFormeleHistorie {

    @Column(name = "DatAanvGel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "DatEindeGel")
    private Integer datumEindeGeldigheid;

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

}
