/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Immutable;

/**
 * Een overeenkomst tussen Afnemers van de BRP enerzijds, en de eigenaar van de BRP anderzijds, op basis waarvan
 * Afnemers persoonsgegevens vanuit de BRP mag ontvangen.
 *
 * Een abonnement is uiteindelijk gebaseerd op een autorisatiebesluit, die bepaalt dat partijen (lees: afnemers)
 * gegevens mogen ontvangen. Gegevens over de wijze waarop de afnemers deze gegevens mogen ontvangen, welke
 * persoonsgegevens het precies betreft (welk deel van de populatie maar ook wel deel van de gegevens) wordt vastgelegd
 * bij het Abonnement. Een enkel abonnement kan door meerdere partijen (lees: afnemer) afgenomen worden.
 *
 * AutAut: Van Dynamische stamtabel naar Dynamisch gezet omdat de generator anders een logische identiteit wil hebben.
 * Toen genereerde de XSD generator een Container voor dit ding. Dus terug naar Dynamische stamtabel en logische
 * ideniteit op ID gemaakt.
 *
 * 1. Het objecttype Abonnement was als 'dynamisch' gekarakteriseerd, voor de bijhouding gedraagt dit zich als
 * stamgegeven. Eerder is besloten de karakterisering van objecttypen te doen 'vanuit het schema waarin het zich
 * bevindt'. Uit dat oogpunt is abonnement dynamisch. Voor de huidige code is dat echter een probleem. Daarom is
 * (pragmatisch) besloten om voor nu abonnement als 'stamgegeven' te karakteriseren. De bredere discussie - wat is
 * precies het criterium om iets als stamgegeven te zien of als dynamisch en hoe verhoudt dit zich met de verschillende
 * koppelvlakken en schema's - wordt uitgesteld tot het moment dat daar zinvol over kan worden nagedacht: nu is er één
 * geval waarin het 'makkelijker' is als een objecttype wordt gekarakteriseerd als stamgegeven in plaats van dynamisch,
 * en is simpelweg aanpassen van het 'type' hierin het meest eenvoudig.
 *
 * Potentiele regels (nader uit te werken) Onder een abonnement mag ten hoogste één Dienst van de Soort 'Mutatielevering
 * op basis van afnemerindicatie ' of 'Mutatielevering op basis van doelbinding' voorkomen. Een Dienst van de Soort
 * 'Onderhouden afnemerindicatie' mag alleen voorkomen onder een abonnement als er ook een Dienst van de Soort
 * 'Mutatielevering op basis van afnemerindicatie' voorkomt. Een Abonnement mag alleen meerdere geldige Toegangen tot
 * Abonnement hebben als er geen dienst van de Soorten 'Mutatielevering op basis van Afnemerindicatie', 'Mutatielevering
 * op basis van Doelbinding', 'Selectie' of 'Attendering' voorkomt onder dat Abonnement. (ofwel: alleen synchrone
 * diensten zijn mogelijk) (Noot: 'what if' bv de SNG een selectie zou willen draaien? Dat kan dan dus niet. Er zou een
 * abonnenement onder één gerechtsdeurwaarder ingericht moeten worden die dan de selectie afnemeemt)) Een Dienst van de
 * Soort 'Selectie met plaatsen afnemerindicatie' mag alleen voorkomen onder een abonnement als er ook een Dienst van de
 * Soort 'Mutatielevering op basis van afnemerindicatie' voorkomt. De indicatie 'met verwijderen afnemerindicatie' en
 * 'met plaatsen afnemerindicatie' mag alleen voorkomen bij een Dienst waarvan de Soort Dienst 'Selectie' of
 * 'Attendering' is. Of algemener: De indicatie 'met verwijderen afnemerindicatie' en 'met plaatsen afnemerindicatie'
 * mag alleen voorkomen indien er ook een Dienst van de Soort 'Mutatielevering op basis van Afnemerindicatie' aanwezig
 * is bij hetzelfde abonnement. (is deze echt nodig of hanteren we GiGo?) De 'Nadere populatiebeperking' van een Dienst
 * mag alleen ingevuld zijn indien de Soort Dienst 'Selectie' of 'Attendering' is. De geldigheidsperiode van een Dienst
 * dient binnen de geldigheidsperiode van het bovenliggende abonnement te vallen. De geldigheidsperiode van een Toegang
 * Abonnement dient binnen de geldigheidsperiode van het bovenliggende abonnement te vallen. Een Partij die een Toegang
 * Abonnement heeft, dient ook een koppeling aan een autorisatiebesluit te hebben die dezelfde (of ruimere)
 * geldigheidsperiode heeft. (toelichting: Intermediairs kunnen geen Partij zijn in de toegang tot een abonnement,
 * alleen 'echte' Partijen die een leveringsautorisatiebesluit hebben) De Partij in Toegang Abonnement die gelijk te
 * zijn aan de Partij van het Authenticatiemiddel dat hierin gebruikt wordt. De Partij in een Authenticatiemiddel dient
 * gelijk te zijn aan de Partij die hoort bij het Certificaat van dit Authenticatiemiddel. Als een autorisatie wordt
 * toegekend voor een attribuut X, dan dient ook de autorisatie voor de groep van dat attribuut aanwezig te zijn (of
 * aangemaakt te worden) (uitdaging: hoe bepaal je in welke groep attribuut X zit? Misschien voor alle groepen initiele
 * autorisatie aanmaken?) Afnemer mag alleen geautoriseerd worden voor formele historie in een groep als hij
 * geautoriseerd is voor materiële historie van die groep. Afnemer mag alleen geautoriseerd worden voor materiele
 * historie, formele historie of verantwoordingsinformatie van een groep als hij geautoriseerd is voor tenminste één
 * gegeven in die groep. Een Abonnement moet een afleverwijze hebben.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractLeveringsautorisatie {

    @Id
    @JsonProperty
    private Integer iD;

    @Column(name = "Stelsel")
    private Stelsel stelsel;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndModelautorisatie"))
    private JaNeeAttribuut indicatieModelautorisatie;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Column(name = "Protocolleringsniveau")
    private Protocolleringsniveau protocolleringsniveau;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAliasSrtAdmHndLeveren"))
    private JaNeeAttribuut indicatieAliasSoortAdministratieveHandelingLeveren;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = PopulatiebeperkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Populatiebeperking"))
    private PopulatiebeperkingAttribuut populatiebeperking;

    @Embedded
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndPopbeperkingVolConv"))
    private NeeAttribuut indicatiePopulatiebeperkingVolledigGeconverteerd;

    @Embedded
    @AttributeOverride(name = OnbeperkteOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    private OnbeperkteOmschrijvingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractLeveringsautorisatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param stelsel stelsel van Leveringsautorisatie.
     * @param indicatieModelautorisatie indicatieModelautorisatie van Leveringsautorisatie.
     * @param naam naam van Leveringsautorisatie.
     * @param protocolleringsniveau protocolleringsniveau van Leveringsautorisatie.
     * @param indicatieAliasSoortAdministratieveHandelingLeveren indicatieAliasSoortAdministratieveHandelingLeveren van
     *            Leveringsautorisatie.
     * @param datumIngang datumIngang van Leveringsautorisatie.
     * @param datumEinde datumEinde van Leveringsautorisatie.
     * @param populatiebeperking populatiebeperking van Leveringsautorisatie.
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd indicatiePopulatiebeperkingVolledigGeconverteerd van
     *            Leveringsautorisatie.
     * @param toelichting toelichting van Leveringsautorisatie.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van Leveringsautorisatie.
     */
    protected AbstractLeveringsautorisatie(
        final Stelsel stelsel,
        final JaNeeAttribuut indicatieModelautorisatie,
        final NaamEnumeratiewaardeAttribuut naam,
        final Protocolleringsniveau protocolleringsniveau,
        final JaNeeAttribuut indicatieAliasSoortAdministratieveHandelingLeveren,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut populatiebeperking,
        final NeeAttribuut indicatiePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.stelsel = stelsel;
        this.indicatieModelautorisatie = indicatieModelautorisatie;
        this.naam = naam;
        this.protocolleringsniveau = protocolleringsniveau;
        this.indicatieAliasSoortAdministratieveHandelingLeveren = indicatieAliasSoortAdministratieveHandelingLeveren;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.populatiebeperking = populatiebeperking;
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = indicatiePopulatiebeperkingVolledigGeconverteerd;
        this.toelichting = toelichting;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;

    }

    /**
     * Retourneert ID van Leveringsautorisatie.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Stelsel van Leveringsautorisatie.
     *
     * @return Stelsel.
     */
    public final Stelsel getStelsel() {
        return stelsel;
    }

    /**
     * Retourneert Modelautorisatie? van Leveringsautorisatie.
     *
     * @return Modelautorisatie?.
     */
    public final JaNeeAttribuut getIndicatieModelautorisatie() {
        return indicatieModelautorisatie;
    }

    /**
     * Retourneert Naam van Leveringsautorisatie.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Protocolleringsniveau van Leveringsautorisatie.
     *
     * @return Protocolleringsniveau.
     */
    public final Protocolleringsniveau getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    /**
     * Retourneert Alias soort administratieve handeling leveren? van Leveringsautorisatie.
     *
     * @return Alias soort administratieve handeling leveren?.
     */
    public final JaNeeAttribuut getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Retourneert Datum ingang van Leveringsautorisatie.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Leveringsautorisatie.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Populatiebeperking van Leveringsautorisatie.
     *
     * @return Populatiebeperking.
     */
    public final PopulatiebeperkingAttribuut getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Retourneert Populatiebeperking volledig geconverteerd? van Leveringsautorisatie.
     *
     * @return Populatiebeperking volledig geconverteerd?.
     */
    public final NeeAttribuut getIndicatiePopulatiebeperkingVolledigGeconverteerd() {
        return indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van Leveringsautorisatie.
     *
     * @return Toelichting.
     */
    public final OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van Leveringsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    public final JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

}
