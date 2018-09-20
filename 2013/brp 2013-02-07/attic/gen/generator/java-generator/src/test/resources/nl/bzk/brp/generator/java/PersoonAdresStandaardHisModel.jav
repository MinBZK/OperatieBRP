package nl.bzk.brp.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractPersoonAdresStandaardHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;


/**
 * User implementatie van {@link AbstractPersoonAdresStandaardHisModel}.
 */
@Entity
@Table(schema = "kern", name = "His_PersAdres")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonAdresStandaardHisModel extends
        AbstractPersoonAdresStandaardHisModel
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonAdresStandaardHisModel() {
        super();
    }

    /**
     * Constructor.
     *
     * @param groep
     * @param persoonAdres
     */
    public PersoonAdresStandaardHisModel(
            final AbstractPersoonAdresStandaardGroep groep,
            final PersoonAdresModel persoonAdres)
    {
        super(groep, persoonAdres);
    }

    public PersoonAdresStandaardHisModel kopieer() {
        return new PersoonAdresStandaardHisModel(this, getPersoonAdres());
    }
}
