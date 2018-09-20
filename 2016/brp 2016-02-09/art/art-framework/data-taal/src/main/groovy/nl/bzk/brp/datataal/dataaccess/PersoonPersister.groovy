package nl.bzk.brp.datataal.dataaccess
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
/**
 * Interface voor het opslaan van een {@link PersoonHisVolledigImpl}.
 */
interface PersoonPersister {

    /**
     * Sla een persoon op.
     *
     * @param persoon de persoon om op te slaan
     * @return de opgeslagen instantie, potentieel een ander object dan de parameter
     */
    PersoonHisVolledigImpl slaOp(PersoonHisVolledigImpl persoon);
}
