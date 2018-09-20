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
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;

/**
 * De basis voor alle entiteiten met materiele history.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractMaterieleEnFormeleHisTabel extends AbstractFormeleHisTabel {

    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvGel"))
    protected Datum datumAanvangGeldigheid;

    @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeGel"))
    protected Datum datumEindeGeldigheid;

    @Transient
    protected BRPActie bRPActieAanpassingGeldigheid;

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

    public BRPActie getBRPActieAanpassingGeldigheid() {
        return bRPActieAanpassingGeldigheid;
    }

    public void setBRPActieAanpassingGeldigheid(final BRPActie bRPActieAanpassingGeldigheid) {
        this.bRPActieAanpassingGeldigheid = bRPActieAanpassingGeldigheid;
    }

}
