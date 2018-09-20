/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Date;
import java.util.List;

import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
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
     * Haal een gelimiteerde person(en) met adres op aan de hand van de persoon id.
     *
     * @param persId de persoon id
     * @return een logische persoon of null als de persoon niet gevonden is.
     */
    Persoon haalPersoonMetAdres(final Long persId);

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
     * Adres, betrokkenen en indicaties worden hier reeds geprefetched.
     *
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return een lijst van logische personen
     */
    List<Persoon> haalPersonenMetWoonAdresOpViaBurgerservicenummer(String bsn);

    /**
     * Deze methode zoekt met volldige adres behalve postcode. Null waardes worden ook meegenomen in de zoek opdracht.
     * <code>null</code> resulteert in de query "is null".
     *
     * @param naamOpenbareRuimte adres
     * @param huisnummer huisnummer
     * @param huisletter huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @param locatieOmschrijving locatieomschrijving
     * @param locatietovAdres locatieTovAdres
     * @param woonplaats woonplaats
     * @return lijst van personen
     */
    List<Persoon> haalPersonenMetWoonAdresOpViaVolledigAdres(final String naamOpenbareRuimte, final String huisnummer,
            final String huisletter, final String huisnummertoevoeging, final String locatieOmschrijving,
            final String locatietovAdres, final Plaats woonplaats);

    /**
     * Zoek op op IdentificatiecodeNummeraanduiding van adres.
     *
     * @param identificatiecodeNummeraanduiding opIdentificatiecodeNummeraanduiding van adres.
     * @return lijst van personen
     */
    List<Persoon> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final String identificatiecodeNummeraanduiding);

    /**
     * Haalt een lijst op met gelimiteerde personen op basis van de opgegeven adres gegevens.
     * Wanneer huisletter of huisnummertoevoeging niet worden opgegeven wordt ook gezocht naar een persoon met een
     * adres zonder huisletter of huisnummertoevoeging.
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
     * Opslaan nieuw persoon ten behoeve van eerste inschrijving. De id van de opgeslagen persoon wordt geretourneerd.
     *
     * @param persoon het persoon die opgeslagen dient te worden.
     * @param datumAanvangGeldigheid Datum waarop inschrijving ingaat.
     * @param tijdstipRegistratie tijdstip waarop de registratie plaatsvindt.
     * @return de id van de opgeslagen persoon.
     */
    PersistentPersoon opslaanNieuwPersoon(final Persoon persoon, final Integer datumAanvangGeldigheid,
                    final Date tijdstipRegistratie);

    /**
     * Werk de bijhoudingsgemeente bij voor de persoon met de gegeven bsn.
     *
     * @param bsn bsn van de persoon dat bijgewerkt moet worden
     * @param bijhoudingsGemeente gegevens waarmee bijgewerkt moet worden
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param tijdstipRegistratie tijdstip van registratie
     */
    void werkbijBijhoudingsGemeente(String bsn, PersoonBijhoudingsGemeente bijhoudingsGemeente,
            final Integer datumAanvangGeldigheid, final Date tijdstipRegistratie);

    /**
     * Controleer of een BSN al in gebruik is.
     *
     * @param bsn De te controleren bsn.
     * @return true indien bsn al in gebruik is.
     */
    boolean isBSNAlIngebruik(final String bsn);

    /**
     * Werk de C-laag bij voor PersistentPersoon.
     *
     * @param ppersoon De zojuist toegevoegde persoon.
     * @param persoon Oorspronkelijke persoon uit de business laag.
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum van aanvang geldigheid
     */
    void werkHistorieBij(final PersistentPersoon ppersoon, final Persoon persoon,
                         final PersistentActie actie, final Integer datumAanvangGeldigheid);

}
