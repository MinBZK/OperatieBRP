/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Deze class bevat de velden voor het opslaan van materiele historie.
 */
@MappedSuperclass
@SuppressWarnings("checkstyle:designforextension")
public abstract class AbstractMaterieleHistorie extends AbstractFormeleHistorie implements MaterieleHistorie {

    /** Veldnaam van actieAanpassingGeldigheid tbv verschil verwerking. */
    public static final String ACTIE_AANPASSING_GELDIGHEID = "actieAanpassingGeldigheid";
    /** Veldnaam van datumAanvangGeldigheid tbv verschil verwerking. */
    public static final String DATUM_AANVANG_GELDIGHEID = "datumAanvangGeldigheid";
    /** Veldnaam van datumEindeGeldigheid tbv verschil verwerking. */
    public static final String DATUM_EINDE_GELDIGHEID = "datumEindeGeldigheid";

    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;

    @Column(name = "dateindegel")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Integer datumEindeGeldigheid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieaanpgel")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private BRPActie actieAanpassingGeldigheid;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie#getDatumAanvangGeldigheid()
     */
    @Override
    public Integer getDatumAanvangGeldigheid() {

        return datumAanvangGeldigheid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie#setDatumAanvangGeldigheid(java
     * .lang.Integer)
     */
    @Override
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie#getDatumEindeGeldigheid()
     */
    @Override
    public Integer getDatumEindeGeldigheid() {

        return datumEindeGeldigheid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie#setDatumEindeGeldigheid(java.lang
     * .Integer)
     */
    @Override
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {

        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie#getActieAanpassingGeldigheid()
     */
    @Override
    public BRPActie getActieAanpassingGeldigheid() {

        return actieAanpassingGeldigheid;
    }

    @Override
    public void setActieAanpassingGeldigheid(final BRPActie actieAanpassingGeldigheid) {

        this.actieAanpassingGeldigheid = actieAanpassingGeldigheid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie#isActueel()
     */
    @Override
    public final boolean isActueel() {
        return super.isActueel() && getDatumEindeGeldigheid() == null;
    }
}
