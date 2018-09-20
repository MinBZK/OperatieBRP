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
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsaanduidingHisModel extends AbstractPersoonGeslachtsaanduidingGroep
		implements MaterieleHistorie, Externalizable {

	@Id
	@SequenceGenerator(name = "HisPersGeslachtsaand", sequenceName = "Kern.seq_His_PersGeslachtsaand")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "HisPersGeslachtsaand")
	@JsonProperty
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Pers")
	private PersoonModel persoon;

	@Embedded
	@JsonProperty
	private MaterieleHistorieImpl historie;

	/**
	 * Default constructor tbv hibernate.
	 */
	protected AbstractPersoonGeslachtsaanduidingHisModel() {
		super();
	}

	/**
	 * .
	 *
	 * @param persoonModel
	 *            .
	 * @param persoonGeslachtsaanduidingGroep
	 *            .
	 */
	protected AbstractPersoonGeslachtsaanduidingHisModel(
			final AbstractPersoonGeslachtsaanduidingGroep persoonGeslachtsaanduidingGroep,
			final PersoonModel persoonModel) {
		super(persoonGeslachtsaanduidingGroep);
		persoon = persoonModel;
		if (persoonGeslachtsaanduidingGroep instanceof AbstractPersoonGeslachtsaanduidingHisModel) {
			historie = new MaterieleHistorieImpl(
					((AbstractPersoonGeslachtsaanduidingHisModel) persoonGeslachtsaanduidingGroep).getHistorie());
		} else {
			historie = new MaterieleHistorieImpl();
		}
	}

	public Long getId() {
		return id;
	}

	public MaterieleHistorieImpl getHistorie() {
		return historie;
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

		ActieModel actieAanpassingGeldigheid = (ActieModel) ExternalReaderService.leesNullableObject(in, ActieModel.class);
		ActieModel actieInhoud = (ActieModel) ExternalReaderService.leesNullableObject(in, ActieModel.class);
		ActieModel actieVerval = (ActieModel) ExternalReaderService.leesNullableObject(in, ActieModel.class);
		Datum datumAanvangGeldigheid = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
		Datum datumEindeGeldigheid = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
		DatumTijd datumTijdRegistratie = (DatumTijd) ExternalReaderService.leesWaarde(in, DatumTijd.class);
		DatumTijd datumTijdVerval = (DatumTijd) ExternalReaderService.leesWaarde(in, DatumTijd.class);

		if (historie != null) {
			setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
			setActieInhoud(actieInhoud);
			setActieVerval(actieVerval);
			setDatumAanvangGeldigheid(datumAanvangGeldigheid);
			setDatumEindeGeldigheid(datumEindeGeldigheid);
			setDatumTijdRegistratie(datumTijdRegistratie);
			setDatumTijdVerval(datumTijdVerval);
		}
	}
}
