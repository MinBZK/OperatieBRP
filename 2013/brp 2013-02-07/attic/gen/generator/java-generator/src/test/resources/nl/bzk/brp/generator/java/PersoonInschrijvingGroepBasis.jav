package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.ANummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
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

    /**
     * BSN van een eerdere inschrijving van dezelfde persoon in het geval dat er van een persoon meer dan één inschrijving is geweest.
     *
     * @return Burgerservicenummer
     */
    Burgerservicenummer getVorigeBSN();

    /**
     * BSN van een latere inschrijving van dezelfde persoon in het geval dat er van een persoon meer dan één inschrijving is geweest.
     *
     * @return Burgerservicenummer
     */
    Burgerservicenummer getVolgendeBSN();

    /**
     * A-nummer van een eerdere inschrijving van dezelfde persoon in het geval dat er van een persoon meer dan één inschrijving is geweest.
     *
     * @return A-nummer
     */
    ANummer getVorigeANummer();

    /**
     * A-nummer van een latere inschrijving van dezelfde persoon in het geval dat er van een persoon meer dan één inschrijving is geweest.
     *
     * @return A-nummer
     */
    ANummer getVolgendeANummer();

}
