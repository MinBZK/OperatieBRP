/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.logisch.kern.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOpschortingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;


/** Repository voor de {@link PersoonModel} class. */
@Repository
public interface PersoonRepository {

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
    PersoonModel findByBurgerservicenummer(Burgerservicenummer bsn);

    /**
     * Haal een persoon op met de technische sleutel.
     *
     * @param iD de technische sleutel an de persoon
     * @return de gevonden persoon.
     */
    PersoonModel findPersoonMetId(Integer iD);

    /**
     * Haal een gelimiteerde person(en) met adres op aan de hand van de persoon id.
     *
     * @param persId de persoon id
     * @return een logische persoon of null als de persoon niet gevonden is.
     */
    PersoonModel haalPersoonMetAdres(final Integer persId);

    PersoonModel haalPersoonOpMetAdresViaBetrokkenheid(BetrokkenheidModel betrokkenheid);

    /**
     * Haal een gelimiteerde persoon op (zonder adres, relatie, indicatie).
     *
     * @param persId de persoon id
     * @return een logische persoon of null als de persoon niet gevonden is.
     */
    PersoonModel haalPersoonSimpel(final Integer persId);

    /**
     * Haalt (compleet) persoon op op basis van zijn burgerservicenummer. Alle collecties worden hier reeds
     * geprefetched.
     *
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return het logische persoon
     */
    PersoonModel haalPersoonOpMetBurgerservicenummer(Burgerservicenummer bsn);

    /**
     * Haalt een lijst op met gelimiteerde personen op, op basis van zijn burgerservicenummer.
     * Adres, betrokkenen en indicaties worden hier reeds geprefetched.
     *
     * @param bsn burgerservicenummer van de persoon die opgehaald dient te worden.
     * @return een lijst van logische personen
     */
    List<PersoonModel> haalPersonenMetWoonAdresOpViaBurgerservicenummer(Burgerservicenummer bsn);

    /**
     * Deze methode zoekt met volldige adres behalve postcode. Null waardes worden ook meegenomen in de zoek opdracht.
     * <code>null</code> resulteert in de query "is null".
     *
     * @param naamOpenbareRuimte adres
     * @param huisnummer huisnummer
     * @param huisletter huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @param woonplaats woonplaats
     * @param gemeente gemeente
     * @return lijst van personen
     */
    List<PersoonModel> haalPersonenMetWoonAdresOpViaVolledigAdres(final NaamOpenbareRuimte naamOpenbareRuimte,
        final Huisnummer huisnummer, final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging,
        final Plaats woonplaats, final Partij gemeente);

    /**
     * Zoek op op IdentificatiecodeNummeraanduiding van adres.
     *
     * @param identificatiecodeNummeraanduiding opIdentificatiecodeNummeraanduiding van adres.
     * @return lijst van personen
     */
    List<PersoonModel> haalPersonenMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
        final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding);

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
    List<PersoonModel> haalPersonenOpMetAdresViaPostcodeHuisnummer(final Postcode postcode, final Huisnummer huisnummer,
        final Huisletter huisletter, final Huisnummertoevoeging huisnummertoevoeging);

    /**
     * Opslaan nieuw persoon ten behoeve van eerste inschrijving. De id van de opgeslagen persoon wordt geretourneerd.
     *
     * @param persoon het persoon die opgeslagen dient te worden.
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum waarop inschrijving ingaat.
     * @return de opgeslagen persoon.
     */
    PersoonModel opslaanNieuwPersoon(final PersoonModel persoon, final ActieModel actie,
        final Datum datumAanvangGeldigheid);

    /**
     * Werk de bijhoudingsgemeente bij voor de persoon met de gegeven bsn.
     *
     * @param bsn bsn van de persoon die bijgewerkt moet worden
     * @param bijhoudingsgemeente gegevens waarmee bijgewerkt moet worden
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    void werkbijBijhoudingsgemeente(final Burgerservicenummer bsn,
        final PersoonBijhoudingsgemeenteGroep bijhoudingsgemeente,
        final ActieModel actie, final Datum datumAanvangGeldigheid);

    /**
     * Werk bij de aanschrijvingsgroep van een persoon.
     *
     * @param persoon de persoon die bijgewerkt moet worden.
     * @param aanschrijving de nieuwe aanschrijving
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    void werkbijNaamGebruik(final PersoonModel persoon, final PersoonAanschrijvingGroep aanschrijving,
        final ActieModel actie, final Datum datumAanvangGeldigheid);

    /**
     * Werk bij de overlijdengroep (+ eventueel de bijbehorende opschorting groep).
     *
     * @param persoon de persoon die bijgewerkt moet worden.
     * @param overlijden de nieuwe overlijden groep.
     * @param opschorting de opschorting die bij deze overlijden groep
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    void werkbijOverlijden(final PersoonModel persoon, final PersoonOverlijdenGroep overlijden,
        final PersoonOpschortingGroep opschorting,
        final ActieModel actie, final Datum datumAanvangGeldigheid);

}
