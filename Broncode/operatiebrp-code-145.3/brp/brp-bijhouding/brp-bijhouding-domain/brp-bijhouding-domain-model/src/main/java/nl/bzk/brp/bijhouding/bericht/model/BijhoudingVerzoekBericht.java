/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;

/**
 * Interface voor het bijhoudingsverzoek bericht.
 */
@XmlElementen(enumType = BijhoudingBerichtSoort.class, methode = "getSoort")
public interface BijhoudingVerzoekBericht extends BmrGroep {
    /**
     * Geef de waarde van soort.
     * @return soort
     */
    BijhoudingBerichtSoort getSoort();

    /**
     * Geef de waarde van stuurgegevens.
     * @return stuurgegevens
     */
    StuurgegevensElement getStuurgegevens();

    /**
     * Geef de waarde van parameters.
     * @return parameters
     */
    ParametersElement getParameters();

    /**
     * Geef de waarde van administratieveHandeling.
     * @return administratieveHandeling
     */
    AdministratieveHandelingElement getAdministratieveHandeling();

    /**
     * Geef de waarde van oinWaardeOndertekenaar.
     * @return oinWaardeOndertekenaar
     */
    String getOinWaardeOndertekenaar();

    /**
     * Zet de waarde van oinWaardeOndertekenaar.
     * @param oinWaardeOndertekenaar oinWaardeOndertekenaar
     */
    void setOinWaardeOndertekenaar(String oinWaardeOndertekenaar);

    /**
     * Geef de waarde van oinWaardeTransporteur.
     * @return oinWaardeTransporteur
     */
    String getOinWaardeTransporteur();

    /**
     * Zet de waarde van oinWaardeTransporteur.
     * @param oinWaardeTransporteur oinWaardeTransporteur
     */
    void setOinWaardeTransporteur(String oinWaardeTransporteur);

    /**
     * Geeft de XML representatie van dit bericht terug.
     * @return de XML
     */
    String getXml();

    /**
     * Zet de XML representatie van dit bericht.
     * @param xml de XML
     */
    void setXml(String xml);

    /**
     * Check of het BijhoudingsBericht in prevalidatie modus zit.
     * @return true of false.
     */
    boolean isPrevalidatie();

    /**
     * Geeft het tijdstip ontvangst terug.
     * @return het tijdstip ontvangst
     */
    DatumTijdElement getTijdstipOntvangst();

    /**
     * Geeft de datum ontvangs terug.
     * @return de datum ontvangst
     */
    DatumElement getDatumOntvangst();

    /**
     * Voegt een objectSleutelIndex toe aan het bericht.
     * @param objectSleutelIndex objectSleutelIndex
     */
    void setObjectSleutelIndex(ObjectSleutelIndex objectSleutelIndex);

    /**
     * Laad voor alle objectsleutels de bijbehorende RootEntiteit en geef de meldingen terug voor alle ongeldige
     * objectsleutels.
     * @return de lijst met meldingen van ongeldige objectsleutels
     */
    List<MeldingElement> laadEntiteitenVoorObjectSleutels();

    /**
     * Geeft de entiteit die hoort bij het gegeven type en objectsleutel of null als deze niet bestaat.
     * @param entiteitType het entiteit type
     * @param objectSleutel de objectsleutel
     * @param <T> het type entiteit
     * @return de entiteit
     */
    <T extends RootEntiteit> T getEntiteitVoorObjectSleutel(Class<T> entiteitType, String objectSleutel);

    /**
     * Geeft de entiteit die hoort bij het gegeven type en database id of null als deze niet bestaat.
     * @param entiteitType het entiteit type
     * @param databaseId de database id
     * @param <T> het type entiteit
     * @return de entiteit
     */
    <T extends RootEntiteit> T getEntiteitVoorId(Class<T> entiteitType, Number databaseId);

    /**
     * Voegt een root entiteit toe aan de {@link ObjectSleutelIndex}.
     * @param rootEntiteit de root entiteit die moet worden toegevoegd
     * @return de oude root entiteit met hetzelfde id wanneer deze aanwezig is in de index, anders null
     * @throws IllegalArgumentException als {@link ObjectSleutelIndex#isInitialized()} false is, of als {@link RootEntiteit#getId()} null is
     * @see ObjectSleutelIndex#voegToe(Element)
     */
    void voegToeAanObjectSleutelIndex(RootEntiteit rootEntiteit);

    /**
     * Geeft het {@link Partij} object terug adhv het zendendePartij element uit de stuurgegevens.
     * @return zendendePartij Partij
     */
    Partij getZendendePartij();

    /**
     * Vervangt de {@link RootEntiteit} in de index die aangewezen wordt door de gegeven database id met de nieuwe
     * {@link RootEntiteit}.
     * @param entiteitType het type root entiteit
     * @param databaseId de database id waaronder de root entiteit te vinden is in de {@link ObjectSleutelIndex}
     * @param nieuweRootEntiteit de nieuwe {@link RootEntiteit}
     * @param <T> het type van de {@link RootEntiteit}
     */
    <T extends RootEntiteit> void vervangEntiteitMetId(Class<T> entiteitType, Number databaseId, T nieuweRootEntiteit);

    /**
     * Voegt een mapping toe van de gegeven objectsleutel op een persoon in de objectsleutel index.
     * @param objectSleutel de nieuwe objectsleutel
     * @param entiteitType het type van de entiteit waarnaar de nieuwe sleutel gaat verwijzen
     * @param databaseId het id van de entiteit waarnaar deze sleutel gaat verwijzen
     * @param <T> het type entiteit
     * @throws IllegalArgumentException wanneer voor het gegeven type al een objectsleutel mapping bestaat in deze index
     * @throws IllegalArgumentException wanneer er geen persoon in de index voorkomt voor het gegeven type en database id
     */
    <T extends RootEntiteit> void voegObjectSleutelToe(String objectSleutel, Class<T> entiteitType, Number databaseId);

    /**
     * Voegt de referentie elementen toe aan het bericht.
     * @param referenties de lijst met referentie elementen
     */
    void setReferenties(List<BmrGroepReferentie> referenties);

    /**
     * Controleert of alle referenties in het bericht verwijzen naar een bestaand element uit het bericht.
     * @return de lijst met meldingen
     */
    List<MeldingElement> controleerReferentiesInBericht();

    /**
     * Registreert alle {@link PersoonElement} bij de bijhorende {@link BijhoudingPersoon}.
     */
    void registreerPersoonElementenBijBijhoudingPersonen();

    /**
     * Geeft een set met de te archiveren personen terug.
     * @return de {@link Set} met de te archiveren {@link BijhoudingPersoon} terug.
     */
    Set<BijhoudingPersoon> getTeArchiverenPersonen();

    /**
     * Registreert een element waarvoor de postConstruct moet worden aangeroepen.
     *
     * @param element het element
     */
    void registreerPostConstructAanroep(final Element element);
}
