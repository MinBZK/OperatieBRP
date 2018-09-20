/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.basis.Groep;


/**
 * Voor de modellering van buitenlands adres waren enkele opties:
 * - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel:
 * Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht)
 * RNI heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet
 * lukken. (Voorlopig) nog geen optie.
 * - Adres per regel opnemen.
 * - Adresregels in een aparte tabel.
 * Is ook mogelijk mits aantal regels beperkt wordt.
 * Uiteindelijk is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels
 * geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het
 * maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen.
 * RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 *
 *
 *
 */
public interface PersoonAdresStandaardGroepBasis extends Groep {

    /**
     * Retourneert Soort van Standaard.
     *
     * @return Soort.
     */
    FunctieAdres getSoort();

    /**
     * Retourneert Reden wijziging van Standaard.
     *
     * @return Reden wijziging.
     */
    RedenWijzigingAdres getRedenWijziging();

    /**
     * Retourneert Aangever adreshouding van Standaard.
     *
     * @return Aangever adreshouding.
     */
    AangeverAdreshouding getAangeverAdreshouding();

    /**
     * Retourneert Datum aanvang adreshouding van Standaard.
     *
     * @return Datum aanvang adreshouding.
     */
    Datum getDatumAanvangAdreshouding();

    /**
     * Retourneert Adresseerbaar object van Standaard.
     *
     * @return Adresseerbaar object.
     */
    AanduidingAdresseerbaarObject getAdresseerbaarObject();

    /**
     * Retourneert Identificatiecode nummeraanduiding van Standaard.
     *
     * @return Identificatiecode nummeraanduiding.
     */
    IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding();

    /**
     * Retourneert Gemeente van Standaard.
     *
     * @return Gemeente.
     */
    Partij getGemeente();

    /**
     * Retourneert Naam openbare ruimte van Standaard.
     *
     * @return Naam openbare ruimte.
     */
    NaamOpenbareRuimte getNaamOpenbareRuimte();

    /**
     * Retourneert Afgekorte Naam Openbare Ruimte van Standaard.
     *
     * @return Afgekorte Naam Openbare Ruimte.
     */
    AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte();

    /**
     * Retourneert Gemeentedeel van Standaard.
     *
     * @return Gemeentedeel.
     */
    Gemeentedeel getGemeentedeel();

    /**
     * Retourneert Huisnummer van Standaard.
     *
     * @return Huisnummer.
     */
    Huisnummer getHuisnummer();

    /**
     * Retourneert Huisletter van Standaard.
     *
     * @return Huisletter.
     */
    Huisletter getHuisletter();

    /**
     * Retourneert Huisnummertoevoeging van Standaard.
     *
     * @return Huisnummertoevoeging.
     */
    Huisnummertoevoeging getHuisnummertoevoeging();

    /**
     * Retourneert Postcode van Standaard.
     *
     * @return Postcode.
     */
    Postcode getPostcode();

    /**
     * Retourneert Woonplaats van Standaard.
     *
     * @return Woonplaats.
     */
    Plaats getWoonplaats();

    /**
     * Retourneert Locatie t.o.v. adres van Standaard.
     *
     * @return Locatie t.o.v. adres.
     */
    AanduidingBijHuisnummer getLocatietovAdres();

    /**
     * Retourneert Locatie omschrijving van Standaard.
     *
     * @return Locatie omschrijving.
     */
    LocatieOmschrijving getLocatieOmschrijving();

    /**
     * Retourneert Datum vertrek uit Nederland van Standaard.
     *
     * @return Datum vertrek uit Nederland.
     */
    Datum getDatumVertrekUitNederland();

    /**
     * Retourneert Buitenlands adres regel 1 van Standaard.
     *
     * @return Buitenlands adres regel 1.
     */
    Adresregel getBuitenlandsAdresRegel1();

    /**
     * Retourneert Buitenlands adres regel 2 van Standaard.
     *
     * @return Buitenlands adres regel 2.
     */
    Adresregel getBuitenlandsAdresRegel2();

    /**
     * Retourneert Buitenlands adres regel 3 van Standaard.
     *
     * @return Buitenlands adres regel 3.
     */
    Adresregel getBuitenlandsAdresRegel3();

    /**
     * Retourneert Buitenlands adres regel 4 van Standaard.
     *
     * @return Buitenlands adres regel 4.
     */
    Adresregel getBuitenlandsAdresRegel4();

    /**
     * Retourneert Buitenlands adres regel 5 van Standaard.
     *
     * @return Buitenlands adres regel 5.
     */
    Adresregel getBuitenlandsAdresRegel5();

    /**
     * Retourneert Buitenlands adres regel 6 van Standaard.
     *
     * @return Buitenlands adres regel 6.
     */
    Adresregel getBuitenlandsAdresRegel6();

    /**
     * Retourneert Land van Standaard.
     *
     * @return Land.
     */
    Land getLand();

    /**
     * Retourneert Persoon niet aangetroffen op adres? van Standaard.
     *
     * @return Persoon niet aangetroffen op adres?.
     */
    JaNee getIndicatiePersoonNietAangetroffenOpAdres();

}
