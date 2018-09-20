/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeboorteGroep;
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
public abstract class AbstractPersoonGeboorteHisModel extends AbstractPersoonGeboorteGroep implements FormeleHistorie
{

    @Id
    @SequenceGenerator(name = "HisPersGeboorte", sequenceName = "Kern.seq_His_PersGeboorte")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HisPersGeboorte")
    @JsonProperty
    private Long                id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonModel persoon;

    @Embedded
    @JsonProperty
    private FormeleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeboorteHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeboorteGroep .
     * @param persoonModel .
     */
    protected AbstractPersoonGeboorteHisModel(final AbstractPersoonGeboorteGroep persoonGeboorteGroep,
                                              final PersoonModel persoonModel)
    {
        super(persoonGeboorteGroep);
        persoon = persoonModel;
        if (persoonGeboorteGroep instanceof AbstractPersoonGeboorteHisModel) {
            historie = new FormeleHistorieImpl(
                    ((AbstractPersoonGeboorteHisModel) persoonGeboorteGroep).getHistorie()
            );
        } else {
            historie = new FormeleHistorieImpl();
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

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        super.writeExternal(out);
        ExternalWriterService.schrijfNullableObject(out, historie);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        historie = (FormeleHistorieImpl) ExternalReaderService
                .leesNullableObject(in, FormeleHistorieImpl.class);
    }
}
