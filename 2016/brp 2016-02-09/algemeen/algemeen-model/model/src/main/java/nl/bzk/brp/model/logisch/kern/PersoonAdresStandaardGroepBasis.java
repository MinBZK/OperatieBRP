/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Voor de modellering van buitenlands adres waren enkele opties: - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel: Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht) RNI heeft een actie gestart om te kijken of
 * binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet lukken. (Voorlopig) nog geen optie. - Adres
 * per regel opnemen. - Adresregels in een aparte tabel. Is ook mogelijk mits aantal regels beperkt wordt. Uiteindelijk
 * is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels geplaatst kunnen
 * worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het maximale aantal
 * karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI inzake de maximale
 * grootte van internationale adressen.
 *
 * 2. De groep standaard is NIET verplicht: in geval van VOW wordt een actueel adres beëindigd, en is er dus géén sprake
 * van een 'actueel' record in de C/D laag.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonAdresStandaardGroepBasis extends Groep {

    /**
     * Retourneert Soort van Standaard.
     *
     * @return Soort.
     */
    FunctieAdresAttribuut getSoort();

    /**
     * Retourneert Reden wijziging van Standaard.
     *
     * @return Reden wijziging.
     */
    RedenWijzigingVerblijfAttribuut getRedenWijziging();

    /**
     * Retourneert Aangever adreshouding van Standaard.
     *
     * @return Aangever adreshouding.
     */
    AangeverAttribuut getAangeverAdreshouding();

    /**
     * Retourneert Datum aanvang adreshouding van Standaard.
     *
     * @return Datum aanvang adreshouding.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangAdreshouding();

    /**
     * Retourneert Identificatiecode adresseerbaar object van Standaard.
     *
     * @return Identificatiecode adresseerbaar object.
     */
    IdentificatiecodeAdresseerbaarObjectAttribuut getIdentificatiecodeAdresseerbaarObject();

    /**
     * Retourneert Identificatiecode nummeraanduiding van Standaard.
     *
     * @return Identificatiecode nummeraanduiding.
     */
    IdentificatiecodeNummeraanduidingAttribuut getIdentificatiecodeNummeraanduiding();

    /**
     * Retourneert Gemeente van Standaard.
     *
     * @return Gemeente.
     */
    GemeenteAttribuut getGemeente();

    /**
     * Retourneert Naam openbare ruimte van Standaard.
     *
     * @return Naam openbare ruimte.
     */
    NaamOpenbareRuimteAttribuut getNaamOpenbareRuimte();

    /**
     * Retourneert Afgekorte naam openbare ruimte van Standaard.
     *
     * @return Afgekorte naam openbare ruimte.
     */
    AfgekorteNaamOpenbareRuimteAttribuut getAfgekorteNaamOpenbareRuimte();

    /**
     * Retourneert Gemeentedeel van Standaard.
     *
     * @return Gemeentedeel.
     */
    GemeentedeelAttribuut getGemeentedeel();

    /**
     * Retourneert Huisnummer van Standaard.
     *
     * @return Huisnummer.
     */
    HuisnummerAttribuut getHuisnummer();

    /**
     * Retourneert Huisletter van Standaard.
     *
     * @return Huisletter.
     */
    HuisletterAttribuut getHuisletter();

    /**
     * Retourneert Huisnummertoevoeging van Standaard.
     *
     * @return Huisnummertoevoeging.
     */
    HuisnummertoevoegingAttribuut getHuisnummertoevoeging();

    /**
     * Retourneert Postcode van Standaard.
     *
     * @return Postcode.
     */
    PostcodeAttribuut getPostcode();

    /**
     * Retourneert Woonplaatsnaam van Standaard.
     *
     * @return Woonplaatsnaam.
     */
    NaamEnumeratiewaardeAttribuut getWoonplaatsnaam();

    /**
     * Retourneert Locatie ten opzichte van adres van Standaard.
     *
     * @return Locatie ten opzichte van adres.
     */
    LocatieTenOpzichteVanAdresAttribuut getLocatieTenOpzichteVanAdres();

    /**
     * Retourneert Locatieomschrijving van Standaard.
     *
     * @return Locatieomschrijving.
     */
    LocatieomschrijvingAttribuut getLocatieomschrijving();

    /**
     * Retourneert Buitenlands adres regel 1 van Standaard.
     *
     * @return Buitenlands adres regel 1.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel1();

    /**
     * Retourneert Buitenlands adres regel 2 van Standaard.
     *
     * @return Buitenlands adres regel 2.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel2();

    /**
     * Retourneert Buitenlands adres regel 3 van Standaard.
     *
     * @return Buitenlands adres regel 3.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel3();

    /**
     * Retourneert Buitenlands adres regel 4 van Standaard.
     *
     * @return Buitenlands adres regel 4.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel4();

    /**
     * Retourneert Buitenlands adres regel 5 van Standaard.
     *
     * @return Buitenlands adres regel 5.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel5();

    /**
     * Retourneert Buitenlands adres regel 6 van Standaard.
     *
     * @return Buitenlands adres regel 6.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel6();

    /**
     * Retourneert Land/gebied van Standaard.
     *
     * @return Land/gebied.
     */
    LandGebiedAttribuut getLandGebied();

    /**
     * Retourneert Persoon aangetroffen op adres? van Standaard.
     *
     * @return Persoon aangetroffen op adres?.
     */
    NeeAttribuut getIndicatiePersoonAangetroffenOpAdres();

}
