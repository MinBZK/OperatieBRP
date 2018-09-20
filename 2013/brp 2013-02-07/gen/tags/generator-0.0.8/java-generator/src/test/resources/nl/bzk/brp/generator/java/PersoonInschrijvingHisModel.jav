package nl.bzk.brp.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonInschrijvingHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van {@link AbstractPersoonInschrijvingHisModel}.
 */
@Entity
@Table(schema = "kern", name = "His_PersInschr")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonInschrijvingHisModel extends
        AbstractPersoonInschrijvingHisModel
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonInschrijvingHisModel() {
        super();
    }

    /**
     * Constructor.
     *
     * @param groep
     * @param persoon
     */
    public PersoonInschrijvingHisModel(
            final AbstractPersoonInschrijvingGroep groep,
            final PersoonModel persoon)
    {
        super(groep, persoon);
    }

    public PersoonInschrijvingHisModel kopieer() {
        return new PersoonInschrijvingHisModel(this, getPersoon());
    }
}
