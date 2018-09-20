/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.validatie.constraint.Datum;

/**
 * De basis voor materiele history.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractMaterieleEnFormeleHistorieEntiteit extends AbstractFormeleHistorieEntiteit {

    @Column(name = "DatAanvGel")
    @Datum
    private Integer datumAanvangGeldigheid;

    @Column(name = "DatEindeGel")
    @Datum
    private Integer datumEindeGeldigheid;

    @OneToOne
    @JoinColumn(name = "ActieAanpGel")
    private PersistentActie actieAanpassingGeldigheid;

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

    public PersistentActie getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    public void setActieAanpassingGeldigheid(final PersistentActie actieAanpassingGeldigheid) {
        this.actieAanpassingGeldigheid = actieAanpassingGeldigheid;
    }

    /**
     * Maakt een diepe kopie van dit object.
     * @return Een diepe kopie van dit object.
     * @throws CloneNotSupportedException Indien de kopieer actie niet geimplementeerd is.
     */
    public AbstractMaterieleEnFormeleHistorieEntiteit clone() throws CloneNotSupportedException {
        return (AbstractMaterieleEnFormeleHistorieEntiteit) super.clone();
    }

}
