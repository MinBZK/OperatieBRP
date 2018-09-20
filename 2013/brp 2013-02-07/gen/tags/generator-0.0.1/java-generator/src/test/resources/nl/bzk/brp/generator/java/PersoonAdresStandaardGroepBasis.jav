package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.attribuuttype.AanduidingBijHuisnummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;


/**
 * Interface voor groep "Standaard" van objecttype "Persoon \ Adres".
 */
public interface PersoonAdresStandaardGroepBasis extends Groep {

    /**
     * Aanduiding van het soort adres.
     *
     * @return Functie adres
     */
    FunctieAdres getSoort();

    /**
     * Reden waarom het adres is gewijzigd.
     *
     * @return Reden wijziging adres
     */
    RedenWijzigingAdres getRedenWijziging();

    /**
     * De hoedanigheid van de persoon die aangifte van verblijf en adres of van adreswijziging heeft gedaan, ten opzichte van de Persoon wiens adres is aangegeven.
     *
     * @return Aangever adreshouding
     */
    AangeverAdreshouding getAangeverAdreshouding();

    /**
     * Datum waarop de adreshouding aanvangt.
     *
     * @return Datum (evt. deels onbekend)
     */
    Datum getDatumAanvangAdreshouding();

    /**
     * Generalisatie van de in de BAG gedefinieerde objecttypen Verblijfsobject, Standplaats en Ligplaats.
     *
     * @return Aanduiding adresseerbaar object
     */
    AanduidingAdresseerbaarObject getAdresseerbaarObject();

    /**
     * De unieke aanduiding van een door het bevoegde gemeentelijk orgaan als zodanig toegekende aanduiding van een adresseerbaar object.
     *
     * @return Identificatiecode nummeraanduiding
     */
    IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding();

    /**
     * De Nederlandse gemeente waarin het adresseerbaar object zich bevindt.
     *
     * @return Partij
     */
    Partij getGemeente();

    /**
     * Een door het bevoegde gemeentelijke orgaan toegekende benaming toegekend aan een als openbare ruimte aangewezen buitenruimte die binnen één woonplaats is gelegen.
     *
     * @return Naam openbare ruimte
     */
    NaamOpenbareRuimte getNaamOpenbareRuimte();

    /**
     * De op basis van de NEN5822:2002 standaard of volgens de BOCO norm ingekorte naam van de Openbare ruimte.
     *
     * @return Afgekorte naam openbare ruimte
     */
    AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte();

    /**
     * Aanduiding die een niet unieke straatnaam in een gemeente uniek maakt.
     *
     * @return Gemeentedeel
     */
    Gemeentedeel getGemeentedeel();

    /**
     * De numerieke aanduiding zoals deze door het gemeentebestuur aan het object is toegekend dan wel een door of namens het bevoegde gemeentelijke orgaan ten aanzien van een adresseerbaar object toegekende nummering.
     *
     * @return Huisnummer
     */
    Huisnummer getHuisnummer();

    /**
     * Een door het bevoegde gemeentelijke orgaan ten aanzien van een adresseerbaar object toegekende toevoeging aan een huisnummer in de vorm van een alfanumeriek teken.
     *
     * @return Huisletter
     */
    Huisletter getHuisletter();

    /**
     * Een door het bevoegde gemeentelijke orgaan ten aanzien van een adresseerbaar object toegekende nadere toevoeging aan een huisnummer of een combinatie van huisnummer en huisletter.
     *
     * @return Huisnummertoevoeging
     */
    Huisnummertoevoeging getHuisnummertoevoeging();

    /**
     * De code behorende bij een bepaalde combinatie van een naam van een woonplaats, naam van een openbare ruimte en een huisnummer.
     *
     * @return Postcode
     */
    Postcode getPostcode();

    /**
     * Een door de gemeenteraad als zodanig aangewezen gedeelte van het gemeentelijk grondgebied, waarbinnen het adresseerbare object is gelegen.
     *
     * @return Plaats
     */
    Plaats getWoonplaats();

    /**
     * Geeft aan waar het adres zich bevindt ten opzichte van het geregistreerde adres.
     *
     * @return Aanduiding bij huisnummer
     */
    AanduidingBijHuisnummer getLocatietovAdres();

    /**
     * Bevat een omschrijving van de woonlocatie van de persoon.
     *
     * @return Locatie omschrijving
     */
    LocatieOmschrijving getLocatieOmschrijving();

    /**
     * 1e adresregel voor buitenlandse adres.
     *
     * @return Adresregel
     */
    Adresregel getBuitenlandsAdresRegel1();

    /**
     * 2e adresregel voor buitenlandse adres.
     *
     * @return Adresregel
     */
    Adresregel getBuitenlandsAdresRegel2();

    /**
     * 3e adresregel voor buitenlandse adres.
     *
     * @return Adresregel
     */
    Adresregel getBuitenlandsAdresRegel3();

    /**
     * 4e adresregel voor buitenlandse adres.
     *
     * @return Adresregel
     */
    Adresregel getBuitenlandsAdresRegel4();

    /**
     * 5e adresregel voor buitenlandse adres.
     *
     * @return Adresregel
     */
    Adresregel getBuitenlandsAdresRegel5();

    /**
     * 6e adresregel voor buitenlandse adres.
     *
     * @return Adresregel
     */
    Adresregel getBuitenlandsAdresRegel6();

    /**
     * Land waar het adres zich bevindt. (Opgegeven door de ingeschrevene bij vertrek naar het buitenland).
     *
     * @return Land
     */
    Land getLand();

    /**
     * De datum waarop de persoon naar het buitenland is geacht te zijn vertrokken.
     *
     * @return Datum (evt. deels onbekend)
     */
    Datum getDatumVertrekUitNederland();

}
