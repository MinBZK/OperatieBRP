/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonSamengesteldeNaamHisModel extends AbstractPersoonSamengesteldeNaamGroep implements
        MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "hisPerssamenGesteldenaam", sequenceName = "Kern.seq_his_perssamengesteldenaam")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hisPerssamenGesteldenaam")
    @Column(name = "ID")
    private Long                  id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    private MaterieleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonSamengesteldeNaamHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonSamengesteldeNaamGroep .
     * @param persoonModel .
     */
    protected AbstractPersoonSamengesteldeNaamHisModel(
            final AbstractPersoonSamengesteldeNaamGroep persoonSamengesteldeNaamGroep,
            final PersoonModel persoonModel)
    {
        super(persoonSamengesteldeNaamGroep);
        persoon = persoonModel;
        if (persoonSamengesteldeNaamGroep instanceof AbstractPersoonSamengesteldeNaamHisModel) {
            historie = new MaterieleHistorieImpl(
                    ((AbstractPersoonSamengesteldeNaamHisModel) persoonSamengesteldeNaamGroep).getHistorie()
            );
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

    public void setPersoon(final PersoonModel persoon) {
		this.persoon = persoon;
	}

	public MaterieleHistorieImpl getHistorie() {
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

    @Override
    public Datum getDatumAanvangGeldigheid() {
        return historie.getDatumAanvangGeldigheid();
    }

    @Override
    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
    }

    @Override
    public Datum getDatumEindeGeldigheid() {
        return historie.getDatumEindeGeldigheid();
    }

    @Override
    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
    }

    @Override
    public ActieModel getActieAanpassingGeldigheid() {
        return historie.getActieAanpassingGeldigheid();
    }

    @Override
    public void setActieAanpassingGeldigheid(final ActieModel actieAanpassingGeldigheid) {
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
    }
}
