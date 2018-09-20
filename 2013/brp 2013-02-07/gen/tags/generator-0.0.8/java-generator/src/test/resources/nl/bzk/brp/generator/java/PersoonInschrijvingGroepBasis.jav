package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * Interface voor groep "Inschrijving" van objecttype "Persoon".
 */
public interface PersoonInschrijvingGroepBasis extends Groep {

    /**
     * Datum vanaf wanneer persoon als ingeschrevene in de BRP dient te worden beschouwd.
     *
     * @return Datum (evt. deels onbekend)
     */
    Datum getDatumInschrijving();

    /**
     * Technische versienummer voor synchronisatie doeleinden.
     *
     * @return Versienummer
     */
    Versienummer getVersienummer();

    /**
     * Verwijzing naar een eerdere registratie van dezelfde persoon.
     *
     * @return Persoon
     */
    PersoonModel getVorigePersoon();

    /**
     * Verwijzing naar een latere registratie van dezelfde persoon.
     *
     * @return Persoon
     */
    PersoonModel getVolgendePersoon();

}
