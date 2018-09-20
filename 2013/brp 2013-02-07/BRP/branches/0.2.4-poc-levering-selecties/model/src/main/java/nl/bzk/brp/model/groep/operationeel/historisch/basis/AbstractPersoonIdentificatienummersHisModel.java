/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonIdentificatienummersGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatienummersHisModel extends AbstractPersoonIdentificatienummersGroep
        implements MaterieleHistorie, Externalizable
{

    @Id
    @SequenceGenerator(name = "HisPersIdentificatieNrs", sequenceName = "Kern.seq_His_PersIDs")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HisPersIdentificatieNrs")
    @JsonProperty
    private Long                  id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel          persoon;

    @Embedded
    @JsonProperty
    private MaterieleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonIdentificatienummersHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonIdentificatienummersGroep .
     * @param persoonModel .
     */
    protected AbstractPersoonIdentificatienummersHisModel(
            final AbstractPersoonIdentificatienummersGroep persoonIdentificatienummersGroep,
            final PersoonModel persoonModel)
    {
        super(persoonIdentificatienummersGroep);
        persoon = persoonModel;
        if (persoonIdentificatienummersGroep instanceof AbstractPersoonIdentificatienummersHisModel) {
            historie =
                new MaterieleHistorieImpl(
                        ((AbstractPersoonIdentificatienummersHisModel) persoonIdentificatienummersGroep)
                                .getHistorie());

        } else {
            historie = new MaterieleHistorieImpl();
        }
    }

    MaterieleHistorieImpl getHistorie() {
        return historie;
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

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		super.writeExternal(out);

		out.writeObject(getId());
		ExternalWriterService.schrijfNullableObject(out, getHistorie());

		ExternalWriterService.schrijfNullableObject(out, getActieAanpassingGeldigheid());
    	ExternalWriterService.schrijfNullableObject(out, getActieInhoud());
    	ExternalWriterService.schrijfNullableObject(out, getActieVerval());

    	out.writeObject(getDatumAanvangGeldigheid() != null ? getDatumAanvangGeldigheid().getWaarde() : null);
		out.writeObject(getDatumEindeGeldigheid() != null ? getDatumEindeGeldigheid().getWaarde() : null);
		out.writeObject(getDatumTijdRegistratie() != null ? getDatumTijdRegistratie().getWaarde().getTime() : null);
		out.writeObject(getDatumTijdVerval() != null ? getDatumTijdVerval().getWaarde().getTime() : null);

	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);

		id = ExternalReaderService.leesLong(in);
		historie = (MaterieleHistorieImpl) ExternalReaderService.leesNullableObject(in, MaterieleHistorieImpl.class);

		setActieAanpassingGeldigheid((ActieModel) ExternalReaderService.leesNullableObject(in, ActieModel.class));
		setActieInhoud((ActieModel) ExternalReaderService.leesNullableObject(in, ActieModel.class));
		setActieVerval((ActieModel) ExternalReaderService.leesNullableObject(in, ActieModel.class));

		setDatumAanvangGeldigheid((Datum) ExternalReaderService.leesWaarde(in, Datum.class));
		setDatumEindeGeldigheid((Datum) ExternalReaderService.leesWaarde(in, Datum.class));
		setDatumTijdRegistratie((DatumTijd) ExternalReaderService.leesWaarde(in, DatumTijd.class));
		setDatumTijdVerval((DatumTijd) ExternalReaderService.leesWaarde(in, DatumTijd.class));


	}
}
