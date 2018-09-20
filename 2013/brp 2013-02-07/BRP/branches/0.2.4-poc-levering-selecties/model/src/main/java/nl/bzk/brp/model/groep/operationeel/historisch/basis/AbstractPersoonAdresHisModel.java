/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/** . */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresHisModel extends AbstractPersoonAdresStandaardGroep implements
    MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "hisPersAdres", sequenceName = "Kern.seq_his_PersAdres")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hisPersAdres")
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PersAdres")
    @NotNull
    private PersoonAdresModel persoonAdres;

    @Embedded
    @JsonProperty
    private MaterieleHistorieImpl historie;

    /** Default constructor tbv hibernate. */
    protected AbstractPersoonAdresHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonAdresStandaardGroep .
     * @param persoonAdresModel .
     */
    protected AbstractPersoonAdresHisModel(
        final AbstractPersoonAdresStandaardGroep persoonAdresStandaardGroep,
        final PersoonAdresModel persoonAdresModel)
    {
        super(persoonAdresStandaardGroep);
        persoonAdres = persoonAdresModel;
        if (persoonAdresStandaardGroep instanceof AbstractPersoonAdresHisModel) {
            historie =
                new MaterieleHistorieImpl(
                    ((AbstractPersoonAdresHisModel) persoonAdresStandaardGroep).getHistorie());

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

    public PersoonAdresModel getPersoonAdres() {
        return persoonAdres;
    }

    public void setPersoonAdres(final PersoonAdresModel persoonAdres) {
		this.persoonAdres = persoonAdres;
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
        ExternalWriterService.schrijfNullableObject(out, historie);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.historie = (MaterieleHistorieImpl) ExternalReaderService.leesNullableObject(in, MaterieleHistorieImpl.class);
    }

}
