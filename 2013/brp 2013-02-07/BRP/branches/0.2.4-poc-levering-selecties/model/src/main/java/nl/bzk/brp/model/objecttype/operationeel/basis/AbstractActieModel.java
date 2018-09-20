/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Ontleningstoelichting;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.logisch.Verdrag;
import nl.bzk.brp.model.objecttype.logisch.basis.ActieBasis;
import nl.bzk.brp.model.objecttype.operationeel.BronModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;

/**
 * Implementatie voor objecttype actie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractActieModel extends AbstractDynamischObjectType implements ActieBasis, Externalizable {

	@Id
	@SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIE")
	@Column(name = "id")
    @JsonProperty
	private Long id;

	@ManyToOne
	@JoinColumn(name = "partij")
	@NotNull
    @JsonProperty
	private Partij partij;

	@Transient
	// @ManyToOne
	// @JoinColumn(name = "verdrag")
	private Verdrag verdrag;

	@Embedded
	@AttributeOverride(name = "waarde", column = @Column(name = "tijdstipontlening"))
    @JsonProperty
	private DatumTijd tijdstipOntlening;

	@Embedded
	@AttributeOverride(name = "waarde", column = @Column(name = "tijdstipreg"))
	@NotNull
    @JsonProperty
	private DatumTijd tijdstipRegistratie;

	@Column(name = "srt")
	@Enumerated
	@NotNull
    @JsonProperty
	private SoortActie soort;

	@Transient
	// TODO: datumAanvangGeldigheid of de db is verouderd of deze attribuut kan
	// weg.
	private Datum datumAanvangGeldigheid;
	@Transient
	// TODO: datumEindeGeldigheid of de db is verouderd of deze attribuut kan
	// weg.
	private Datum datumEindeGeldigheid;
	@Transient
	// TODO: ontleningstoelichting of de db is verouderd of deze attribuut kan
	// weg.
	private Ontleningstoelichting ontleningstoelichting;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Actie")
	@OrderBy("id")
    @JsonProperty
	private List<BronModel> bronnen;

	/**
	 * Copy constructor. Om een model object te construeren uit een web object.
	 *
	 * @param actie
	 *            Object type dat gekopieerd dient te worden.
	 */
	protected AbstractActieModel(final ActieBasis actie) {
		super(actie);
		partij = actie.getPartij();
		// TODO verdrag is nog niet gebouwd.
		// verdrag = actie.getVerdrag();
		tijdstipOntlening = actie.getTijdstipRegistratie();
		tijdstipRegistratie = actie.getTijdstipRegistratie();
		soort = actie.getSoort();
	}

	/** Standaard (lege) constructor. */
	protected AbstractActieModel() {
	}

	public Long getId() {
		return id;
	}

	@Override
	public Partij getPartij() {
		return partij;
	}

	@Override
	public Verdrag getVerdrag() {
		return verdrag;
	}

	@Override
	public DatumTijd getTijdstipOntlening() {
		return tijdstipOntlening;
	}

	@Override
	public DatumTijd getTijdstipRegistratie() {
		return tijdstipRegistratie;
	}

	@Override
	public SoortActie getSoort() {
		return soort;
	}

	@Override
	public Datum getDatumAanvangGeldigheid() {
		return datumAanvangGeldigheid;
	}

	@Override
	public Datum getDatumEindeGeldigheid() {
		return datumEindeGeldigheid;
	}

	@Override
	public Ontleningstoelichting getOntleningstoelichting() {
		return ontleningstoelichting;
	}

	@Override
	public List<BronModel> getBronnen() {
		if (null != bronnen) {
			return Collections.unmodifiableList(bronnen);
		}
		return null;
	}

	/**
	 * .
	 *
	 * @param bron
	 *            .
	 */
	public void voegBronnenToe(final BronModel bron) {
		if (null == bronnen) {
			bronnen = new ArrayList<BronModel>();
		}
		bronnen.add(bron);
	}

	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(getBronnen() != null ? getBronnen().size() : 0);
		if (getBronnen() != null) {
			for (BronModel bron : getBronnen()) {
				bron.writeExternal(out);
			}
		}
		out.writeObject(getId());
		ExternalWriterService.schrijfNullableObject(out, getPartij());
		out.writeObject(getSoort() != null ? getSoort().toString() : null);
		out.writeObject(getTijdstipOntlening() != null ? getTijdstipOntlening().getWaarde().getTime() : null);
		out.writeObject(getTijdstipRegistratie() != null ? getTijdstipRegistratie().getWaarde().getTime() : null);
	}

	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		int listSize = ExternalReaderService.leesInteger(in);
		if (listSize > 0) {
			bronnen = new ArrayList<BronModel>();

			for (int i = 0; i < listSize; i++) {
				BronModel bronModel = new BronModel();
				bronModel.readExternal(in);
				bronnen.add(bronModel);
			}
		}

		id = ExternalReaderService.leesLong(in);
		partij = (Partij) ExternalReaderService.leesNullableObject(in, Partij.class);
		soort = (SoortActie) ExternalReaderService.leesEnum(in, SoortActie.class);
		tijdstipOntlening = (DatumTijd) ExternalReaderService.leesWaarde(in, DatumTijd.class);
		tijdstipRegistratie = (DatumTijd) ExternalReaderService.leesWaarde(in, DatumTijd.class);
	}
}
