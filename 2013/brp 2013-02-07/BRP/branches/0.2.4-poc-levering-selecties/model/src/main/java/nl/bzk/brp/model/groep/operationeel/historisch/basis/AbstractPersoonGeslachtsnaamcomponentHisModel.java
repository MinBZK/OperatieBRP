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
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentHisModel extends
    AbstractPersoonGeslachtsnaamcomponentStandaardGroep implements MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "HisPersGeslnaamcomp", sequenceName = "Kern.seq_His_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HisPersGeslnaamcomp")
    @JsonProperty
    private Long                             id;

    @ManyToOne
    @JoinColumn(name = "PersGeslnaamcomp")
    @NotNull
    @JsonProperty
    private PersoonGeslachtsnaamcomponentModel persoonGeslachtsnaamcomponent;

    @Embedded
    @JsonProperty
    private MaterieleHistorieImpl            historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonGeslachtsnaamcomponentHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonGeslachtsnaamcomponentGroep .
     * @param geslachtsnaamcomponentModel .
     */
    protected AbstractPersoonGeslachtsnaamcomponentHisModel(
        final AbstractPersoonGeslachtsnaamcomponentStandaardGroep persoonGeslachtsnaamcomponentGroep,
        final PersoonGeslachtsnaamcomponentModel geslachtsnaamcomponentModel)
    {
        super(persoonGeslachtsnaamcomponentGroep);
        persoonGeslachtsnaamcomponent = geslachtsnaamcomponentModel;
        if (persoonGeslachtsnaamcomponentGroep instanceof AbstractPersoonGeslachtsnaamcomponentHisModel) {
            historie = new MaterieleHistorieImpl(
                    ((AbstractPersoonGeslachtsnaamcomponentHisModel) persoonGeslachtsnaamcomponentGroep).getHistorie()
            );
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

    public PersoonGeslachtsnaamcomponentModel getPersoonGeslachtsnaamcomponent() {
        return persoonGeslachtsnaamcomponent;
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
        out.writeObject(id);
        ExternalWriterService.schrijfNullableObject(out, persoonGeslachtsnaamcomponent);
        ExternalWriterService.schrijfNullableObject(out, historie);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.id = (Long) in.readObject();
        this.persoonGeslachtsnaamcomponent = (PersoonGeslachtsnaamcomponentModel) ExternalReaderService
                .leesNullableObject(in, PersoonGeslachtsnaamcomponentModel.class);
        this.historie = (MaterieleHistorieImpl) ExternalReaderService.leesNullableObject(in, ActieModel.class);
    }
}
