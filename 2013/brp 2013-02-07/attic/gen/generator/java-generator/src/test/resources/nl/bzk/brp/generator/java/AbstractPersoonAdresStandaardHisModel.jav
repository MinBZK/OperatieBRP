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

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresStandaardHisModel extends
        AbstractPersoonAdresStandaardGroep implements MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "hisPersAdres", sequenceName = "Kern.seq_His_PersAdres")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hisPersAdres")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PersAdres")
    private PersoonAdresModel persoonAdres;

    @Embedded
    private MaterieleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresStandaardHisModel() {
        super();
    }

    /**
     * Constructor.
     *
     * @param groep
     * @param persoonAdresModel
     */
    protected AbstractPersoonAdresStandaardHisModel(
            final AbstractPersoonAdresStandaardGroep groep,
            final PersoonAdresModel persoonAdresModel)
    {
        super(groep);
        persoonAdres = persoonAdresModel;
        if (groep instanceof AbstractPersoonAdresStandaardHisModel) {
            historie =
                new MaterieleHistorieImpl(
                        ((AbstractPersoonAdresStandaardHisModel) groep).getHistorie());
        } else {
            historie = new MaterieleHistorieImpl();
        }
    }

    public Long getId() {
        return id;
    }

    public PersoonAdresModel getPersoonAdres() {
        return persoonAdres;
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
