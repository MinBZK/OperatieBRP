/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.stereotype.Repository;


/** Repository voor de {@link PersistentPersoon} class. */
@Repository
public interface PersoonRepository {

    /**
     * Zoek een persoon op basis van een bsn.
     *
     * @param bsn Burgerservicenummer van de te zoeken persoon.
     * @return De gevonden persoon.
     */
    PersistentPersoon findByBurgerservicenummer(String bsn);

    /**
     * Haal een gelimiteerde person(en) op die kandidaat vader zou kunnen zijn aan de hand van de BSN van
     * de moeder van het kind.
     * @param bsn de bsn van de moeder van het kind.
     * @return de persoons gegevens van de potentiele vader(s)
     */
    List<Persoon> haalPotentieleVaderViaBsnMoeder(final String bsn);

    /**
     * Haalt (compleet) persoon op op basis van zijn burgerservicenummer. Alle collecties worden hier reeds
     * geprefetched.
     *
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return het logische persoon
     */
    Persoon haalPersoonOpMetBurgerservicenummer(String bsn);

    /**
     * Haalt een lijst op met gelimiteerde personen op, op basis van zijn burgerservicenummer.
     * Adres, betrokkenen en indicaties
     * worden hier reeds geprefetched.
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return een lijst van logische personen
     */
    List<Persoon> haalPersonenMetAdresOpViaBurgerservicenummer(String bsn);

    /**
     * Haalt een lijst op met gelimiteerde personen op basis van de opgegeven adres gegevens.
     * Wanneer huisletter of huisnummertoevoeging niet worden opgegeven wordt ook gezocht naar een persoon met een adres
     * zonder huisletter of huisnummertoevoeging.
     *
     * @param postcode postcode
     * @param huisnummer huisnummer
     * @param huisletter huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @return een lijst van personen
     */
    List<Persoon> haalPersonenOpMetAdresViaPostcodeHuisnummer(final String postcode, final String huisnummer,
            final String huisletter, final String huisnummertoevoeging);

    /**
     * Opslaan nieuw persoon ten behoeve van eerste inschrijving.
     *
     * @param persoon het persoon dat opgeslagen dient te worden.
     * @param datumAanvangGeldigheid Datum waarop inschrijving ingaat.
     */
    void opslaanNieuwPersoon(Persoon persoon, final Integer datumAanvangGeldigheid);

    /**
     * Controleer of een BSN al in gebruik is.
     *
     * @param bsn De te controleren bsn.
     * @return true indien bsn al in gebruik is.
     */
    boolean isBSNAlIngebruik(final String bsn);
}
