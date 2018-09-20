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
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonInschrijvingGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingHisModel extends
        AbstractPersoonInschrijvingGroep implements FormeleHistorie
{

    @Id
    @SequenceGenerator(name = "hisPersInschr", sequenceName = "Kern.seq_His_PersInschr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hisPersInschr")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    private FormeleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonInschrijvingHisModel() {
        super();
    }

    /**
     * Constructor.
     *
     * @param groep
     * @param persoonModel
     */
    protected AbstractPersoonInschrijvingHisModel(
            final AbstractPersoonInschrijvingGroep groep,
            final PersoonModel persoonModel)
    {
        super(groep);
        persoon = persoonModel;
        if (groep instanceof AbstractPersoonInschrijvingHisModel) {
            historie =
                new FormeleHistorieImpl(
                        ((AbstractPersoonInschrijvingHisModel) groep).getHistorie());
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
