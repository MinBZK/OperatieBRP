/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonEUVerkiezingenHisModel extends AbstractPersoonEUVerkiezingenGroep implements
        FormeleHistorie
{

    @Id
    @SequenceGenerator(name = "hisPersAanschr", sequenceName = "Kern.seq_his_persaanschr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hisPersAanschr")
    private Long                  id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    private FormeleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonEUVerkiezingenHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonEUVerkiezingenGroep .
     * @param persoonModel .
     */
    protected AbstractPersoonEUVerkiezingenHisModel(
            final AbstractPersoonEUVerkiezingenGroep persoonEUVerkiezingenGroep,
            final PersoonModel persoonModel)
    {
        super(persoonEUVerkiezingenGroep);
        persoon = persoonModel;
        if (persoonEUVerkiezingenGroep instanceof AbstractPersoonEUVerkiezingenHisModel) {
            historie = new FormeleHistorieImpl(
                    ((AbstractPersoonEUVerkiezingenHisModel) persoonEUVerkiezingenGroep).getHistorie());
        } else {
            historie = new MaterieleHistorieImpl();
        }
    }

    public Long getId() {
        return id;
    }

    public PersoonModel getPersoon() {
        return persoon;
    }

    public FormeleHistorieImpl getHistorie() {
        return historie;
    }

    @Override
    public DatumTijd getDatumTijdRegistratie() {
        return historie.getDatumTijdRegistratie();
    }

    @Override
    public void setDatumTijdRegistratie(final DatumTijd datumTijdRegistratie) {
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
    }

    @Override
    public DatumTijd getDatumTijdVerval() {
        return historie.getDatumTijdVerval();
    }

    @Override
    public void setDatumTijdVerval(final DatumTijd datumTijdVerval) {
        historie.setDatumTijdVerval(datumTijdVerval);
    }

    @Override
    public ActieModel getActieInhoud() {
        return historie.getActieInhoud();
    }

    @Override
    public void setActieInhoud(final ActieModel actieInhoud) {
        historie.setActieInhoud(actieInhoud);
    }

    @Override
    public ActieModel getActieVerval() {
        return historie.getActieVerval();
    }

    @Override
    public void setActieVerval(final ActieModel actieVerval) {
        historie.setActieVerval(actieVerval);
    }

}
