/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.historisch.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.basis.MaterieleHistorie;
import nl.bzk.copy.model.basis.MaterieleHistorieImpl;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonVoornaamStandaardGroep;
import nl.bzk.copy.model.objecttype.operationeel.ActieModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonVoornaamModel;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonVoornaamHisModel extends AbstractPersoonVoornaamStandaardGroep implements
        MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "HisPersVoornaam", sequenceName = "Kern.seq_His_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersVoornaam")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PersVoornaam")
    @NotNull
    private PersoonVoornaamModel persoonVoornaam;

    @Embedded
    private MaterieleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonVoornaamHisModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonVoornaamStandaardGroep PersoonVoornaamStandaardGroepBasis
     * @param persoonVoornaamModel          .
     */
    protected AbstractPersoonVoornaamHisModel(
            final AbstractPersoonVoornaamStandaardGroep persoonVoornaamStandaardGroep,
            final PersoonVoornaamModel persoonVoornaamModel)
    {
        super(persoonVoornaamStandaardGroep);
        this.persoonVoornaam = persoonVoornaamModel;
        if (persoonVoornaamStandaardGroep instanceof AbstractPersoonVoornaamHisModel) {
            historie = new MaterieleHistorieImpl(
                    ((AbstractPersoonVoornaamHisModel) persoonVoornaamStandaardGroep).getHistorie()
            );
        } else {
            historie = new MaterieleHistorieImpl();
        }
    }

    public Long getId() {
        return id;
    }

    public PersoonVoornaamModel getPersoonVoornaam() {
        return persoonVoornaam;
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
