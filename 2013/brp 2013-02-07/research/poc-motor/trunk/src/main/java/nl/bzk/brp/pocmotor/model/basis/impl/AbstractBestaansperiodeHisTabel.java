/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.basis.impl;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractBestaansperiodeHisTabel extends AbstractHisTabel {

    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvGel"))
    protected Datum datumAanvangGeldigheid;

    @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeGel"))
    protected Datum datumEindeGeldigheid;

    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }


}
