/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.historisch.basis;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.basis.MaterieleHistorie;
import nl.bzk.copy.model.basis.MaterieleHistorieImpl;
import nl.bzk.copy.model.groep.operationeel.AbstractPersoonGeslachtsaanduidingGroep;
import nl.bzk.copy.model.objecttype.operationeel.ActieModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsaanduidingHisModel extends AbstractPersoonGeslachtsaanduidingGroep
        implements MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "HisPersGeslachtsaand", sequenceName = "Kern.seq_His_PersGeslachtsaand")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersGeslachtsaand")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
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
     * @param persoonModel .
     * @param persoonGeslachtsaanduidingGroep
     *                     .
     */
    protected AbstractPersoonGeslachtsaanduidingHisModel(
            final AbstractPersoonGeslachtsaanduidingGroep persoonGeslachtsaanduidingGroep,
            final PersoonModel persoonModel)
    {
        super(persoonGeslachtsaanduidingGroep);
        this.persoon = persoonModel;
        if (persoonGeslachtsaanduidingGroep instanceof AbstractPersoonGeslachtsaanduidingHisModel) {
            historie =
                    new MaterieleHistorieImpl(
                            ((AbstractPersoonGeslachtsaanduidingHisModel) persoonGeslachtsaanduidingGroep)
                                    .getHistorie());
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
