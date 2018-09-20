/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import org.springframework.stereotype.Repository;


/** Repository voor de {@link PersistentMdl} class. */
@Repository
public interface PersoonMdlRepository {

    /**
     * Controleer of een BSN al in gebruik is.
     *
     * @param bsn De te controleren bsn.
     * @return true indien bsn al in gebruik is.
     */
    boolean isBSNAlIngebruik(final Burgerservicenummer bsn);

    /**
     * Zoek een persoon op basis van een bsn.
     *
     * @param bsn Burgerservicenummer van de te zoeken persoon.
     * @return De gevonden persoon.
     */
    PersoonMdl findByBurgerservicenummer(Burgerservicenummer bsn);

    /**
     * Haal een gelimiteerde person(en) met adres op aan de hand van de persoon id.
     *
     * @param persId de persoon id
     * @return een logische persoon of null als de persoon niet gevonden is.
     */
    PersoonMdl haalPersoonMetAdres(final Long persId);

    /**
     * Haalt (compleet) persoon op op basis van zijn burgerservicenummer. Alle collecties worden hier reeds
     * geprefetched.
     *
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return het logische persoon
     */
    PersoonMdl haalPersoonOpMetBurgerservicenummer(Burgerservicenummer bsn);

    /**
     * Haalt een lijst op met gelimiteerde personen op, op basis van zijn burgerservicenummer.
     * Adres, betrokkenen en indicaties worden hier reeds geprefetched.
     *
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return een lijst van logische personen
     */
    List<PersoonMdl> haalPersonenMetWoonAdresOpViaBurgerservicenummer(Burgerservicenummer bsn);

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
    List<PersoonMdl> haalPersonenMetWoonAdresOpViaVolledigAdres(final NaamOpenbareRuimte naamOpenbareRuimte,
            final Huisnummer huisnummer, final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging,
            final LocatieOmschrijving locatieOmschrijving, final LocatieTovAdres locatietovAdres,
            final Plaats woonplaats);

    /**
     * Zoek op op IdentificatiecodeNummeraanduiding van adres.
     *
     * @param identificatiecodeNummeraanduiding opIdentificatiecodeNummeraanduiding van adres.
     * @return lijst van personen
     */
    List<PersoonMdl> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummerAanduiding identificatiecodeNummeraanduiding);

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
    List<PersoonMdl> haalPersonenOpMetAdresViaPostcodeHuisnummer(final Postcode postcode, final Huisnummer huisnummer,
            final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging);

//    /**
//     * Opslaan nieuw persoon ten behoeve van eerste inschrijving. De id van de opgeslagen persoon wordt geretourneerd.
//     *
//     * @param persoon het persoon die opgeslagen dient te worden.
//     * @param datumAanvangGeldigheid Datum waarop inschrijving ingaat.
//     * @param tijdstipRegistratie tijdstip waarop de registratie plaatsvindt.
//     * @return de id van de opgeslagen persoon.
//     */
//    PersoonMdl opslaanNieuwPersoon(final Persoon persoon, final Datum datumAanvangGeldigheid,
//                    final DatumTijd tijdstipRegistratie);
//
//    /**
//     * Werk de bijhoudingsgemeente bij voor de persoon met de gegeven bsn.
//     *
//     * @param bsn bsn van de persoon dat bijgewerkt moet worden
//     * @param bijhoudingsGemeente gegevens waarmee bijgewerkt moet worden
//     * @param datumAanvangGeldigheid datum aanvang geldigheid
//     * @param tijdstipRegistratie tijdstip van registratie
//     */
//    void werkbijBijhoudingsGemeente(Burgerservicenummer bsn, PersoonBijhoudingsGemeenteGroep bijhoudingsGemeente,
//            final Datum datumAanvangGeldigheid, final DatumTijd tijdstipRegistratie);
//
//    /**
//     * Werk de C-laag bij voor PersistentPersoon.
//     *
//     * @param ppersoon De zojuist toegevoegde persoon.
//     * @param persoon Oorspronkelijke persoon uit de business laag.
//     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
//     * @param datumAanvangGeldigheid datum van aanvang geldigheid
//     */
//    void werkHistorieBij(final PersoonMdl ppersoon, final Persoon persoon,
//                         final ActieMdl actie, final Datum datumAanvangGeldigheid);

}
