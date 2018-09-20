/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Een overeenkomst tussen Afnemers van de BRP enerzijds, en de eigenaar van de BRP anderzijds, op basis waarvan Afnemers persoonsgegevens vanuit de BRP
 * mag ontvangen.
 * <p/>
 * Een abonnement is uiteindelijk gebaseerd op een autorisatiebesluit, die bepaalt dat partijen (lees: afnemers) gegevens mogen ontvangen. Gegevens over de
 * wijze waarop de afnemers deze gegevens mogen ontvangen, welke persoonsgegevens het precies betreft (welk deel van de populatie maar ook wel deel van de
 * gegevens) wordt vastgelegd bij het Abonnement. Een enkel abonnement kan door meerdere partijen (lees: afnemer) afgenomen worden.
 * <p/>
 * AutAut: Van Dynamische stamtabel naar Dynamisch gezet omdat de generator anders een logische identiteit wil hebben. Toen genereerde de XSD generator een
 * Container voor dit ding. Dus terug naar Dynamische stamtabel en logische ideniteit op ID gemaakt.
 * <p/>
 * 1. Het objecttype Abonnement was als 'dynamisch' gekarakteriseerd, voor de bijhouding gedraagt dit zich als stamgegeven. Eerder is besloten de
 * karakterisering van objecttypen te doen 'vanuit het schema waarin het zich bevindt'. Uit dat oogpunt is abonnement dynamisch. Voor de huidige code is
 * dat echter een probleem. Daarom is (pragmatisch) besloten om voor nu abonnement als 'stamgegeven' te karakteriseren. De bredere discussie - wat is
 * precies het criterium om iets als stamgegeven te zien of als dynamisch en hoe verhoudt dit zich met de verschillende koppelvlakken en schema's - wordt
 * uitgesteld tot het moment dat daar zinvol over kan worden nagedacht: nu is er ��n geval waarin het 'makkelijker' is als een objecttype wordt
 * gekarakteriseerd als stamgegeven in plaats van dynamisch, en is simpelweg aanpassen van het 'type' hierin het meest eenvoudig.
 * <p/>
 * Potentiele regels (nader uit te werken) Onder een abonnement mag ten hoogste ��n Dienst van de Soort 'Mutatielevering op basis van afnemerindicatie ' of
 * 'Mutatielevering op basis van doelbinding' voorkomen. Een Dienst van de Soort 'Onderhouden afnemerindicatie' mag alleen voorkomen onder een abonnement
 * als er ook een Dienst van de Soort 'Mutatielevering op basis van afnemerindicatie' voorkomt. Een Abonnement mag alleen meerdere geldige Toegangen tot
 * Abonnement hebben als er geen dienst van de Soorten 'Mutatielevering op basis van Afnemerindicatie', 'Mutatielevering op basis van Doelbinding',
 * 'Selectie' of 'Attendering' voorkomt onder dat Abonnement. (ofwel: alleen synchrone diensten zijn mogelijk) (Noot: 'what if' bv de SNG een selectie zou
 * willen draaien? Dat kan dan dus niet. Er zou een abonnenement onder ��n gerechtsdeurwaarder ingericht moeten worden die dan de selectie afnemeemt)) Een
 * Dienst van de Soort 'Selectie met plaatsen afnemerindicatie' mag alleen voorkomen onder een abonnement als er ook een Dienst van de Soort
 * 'Mutatielevering op basis van afnemerindicatie' voorkomt. De indicatie 'met verwijderen afnemerindicatie' en 'met plaatsen afnemerindicatie' mag alleen
 * voorkomen bij een Dienst waarvan de Soort Dienst 'Selectie' of 'Attendering' is. Of algemener: De indicatie 'met verwijderen afnemerindicatie' en 'met
 * plaatsen afnemerindicatie' mag alleen voorkomen indien er ook een Dienst van de Soort 'Mutatielevering op basis van Afnemerindicatie' aanwezig is bij
 * hetzelfde abonnement. (is deze echt nodig of hanteren we GiGo?) De 'Nadere populatiebeperking' van een Dienst mag alleen ingevuld zijn indien de Soort
 * Dienst 'Selectie' of 'Attendering' is. De geldigheidsperiode van een Dienst dient binnen de geldigheidsperiode van het bovenliggende abonnement te
 * vallen. De geldigheidsperiode van een Toegang Abonnement dient binnen de geldigheidsperiode van het bovenliggende abonnement te vallen. Een Partij die
 * een Toegang Abonnement heeft, dient ook een koppeling aan een autorisatiebesluit te hebben die dezelfde (of ruimere) geldigheidsperiode heeft.
 * (toelichting: Intermediairs kunnen geen Partij zijn in de toegang tot een abonnement, alleen 'echte' Partijen die een leveringsautorisatiebesluit
 * hebben) De Partij in Toegang Abonnement die gelijk te zijn aan de Partij van het Authenticatiemiddel dat hierin gebruikt wordt. De Partij in een
 * Authenticatiemiddel dient gelijk te zijn aan de Partij die hoort bij het Certificaat van dit Authenticatiemiddel. Als een autorisatie wordt toegekend
 * voor een attribuut X, dan dient ook de autorisatie voor de groep van dat attribuut aanwezig te zijn (of aangemaakt te worden) (uitdaging: hoe bepaal je
 * in welke groep attribuut X zit? Misschien voor alle groepen initiele autorisatie aanmaken?) Afnemer mag alleen geautoriseerd worden voor formele
 * historie in een groep als hij geautoriseerd is voor materi�le historie van die groep. Afnemer mag alleen geautoriseerd worden voor materiele historie,
 * formele historie of verantwoordingsinformatie van een groep als hij geautoriseerd is voor tenminste ��n gegeven in die groep. Een Abonnement moet een
 * afleverwijze hebben.
 */
@Table(schema = "AutAut", name = "Levsautorisatie")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Leveringsautorisatie extends AbstractLeveringsautorisatie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @OneToMany(fetch = FetchType.EAGER)//handmatige wijziging)
    @JoinColumn(name = "Levsautorisatie", referencedColumnName = "id")
    private Set<Dienstbundel> dienstbundels;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Leveringsautorisatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param stelsel                                            stelsel van Leveringsautorisatie.
     * @param indicatieModelautorisatie                          indicatieModelautorisatie van Leveringsautorisatie.
     * @param naam                                               naam van Leveringsautorisatie.
     * @param protocolleringsniveau                              protocolleringsniveau van Leveringsautorisatie.
     * @param indicatieAliasSoortAdministratieveHandelingLeveren indicatieAliasSoortAdministratieveHandelingLeveren van Leveringsautorisatie.
     * @param datumIngang                                        datumIngang van Leveringsautorisatie.
     * @param datumEinde                                         datumEinde van Leveringsautorisatie.
     * @param populatiebeperking                                 populatiebeperking van Leveringsautorisatie.
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd   indicatiePopulatiebeperkingVolledigGeconverteerd van Leveringsautorisatie.
     * @param toelichting                                        toelichting van Leveringsautorisatie.
     * @param indicatieGeblokkeerd                               indicatieGeblokkeerd van Leveringsautorisatie.
     */
    protected Leveringsautorisatie(
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
        super(stelsel,
            indicatieModelautorisatie,
            naam,
            protocolleringsniveau,
            indicatieAliasSoortAdministratieveHandelingLeveren,
            datumIngang,
            datumEinde,
            populatiebeperking,
            indicatiePopulatiebeperkingVolledigGeconverteerd,
            toelichting,
            indicatieGeblokkeerd);
    }

    @Override
    public Integer getID() {
        return super.getID();
    }

    // Util methode: DELTA-1696

    /**
     * Geeft aan of dit abonnement "geldig" is op gegeven peilDatum. Dat wil zeggen {@code peilDatum} is op of na de datumIngang van het abonnement en
     * datumEinde van het abonnement is niet ingevuld _of_ {@code peilDatum} is voor datumEinde.
     *
     * @param peilDatum de peildatum
     * @return {@code true} als het abonnement geldig is op peildatum, anders {@code false}
     */
    public boolean isGeldigOp(final DatumAttribuut peilDatum) {
        return this.getDatumIngang() != null && this.getDatumIngang().voorOfOp(peilDatum)
            && (this.getDatumEinde() == null || this.getDatumEinde().na(peilDatum));
    }

    public boolean isGeblokkeerd() {
        return getIndicatieGeblokkeerd() != null && getIndicatieGeblokkeerd().getWaarde() == Ja.J;
    }

    public void setDienstbundels(final Dienstbundel... dienstbundels) {
        this.dienstbundels = new HashSet<>(Arrays.asList(dienstbundels));
    }

    public Set<Dienstbundel> getDienstbundels() {
        return dienstbundels;
    }

    public Dienst geefDienst(final SoortDienst soortDienst) {
        final Collection<Dienst> diensten = geefDiensten(soortDienst);
        if (diensten.isEmpty()) {
            LOGGER.warn("Geen dienst gevonden ({}) voor leveringautorisatie met id {}.",
                soortDienst, getID());
            return null;
        } else if (diensten.size() > 1) {
            LOGGER.warn("Meerdere diensten gevonden ({}) voor leveringautorisatie met id {}.",
                soortDienst, getID());
        }
        return diensten.iterator().next();
    }

    /**
     * Geef alle diensten
     *
     * @return alle diensten
     */
    public Collection<Dienst> geefDiensten() {
        return geefDiensten(null);
    }

    /**
     * Haalt geldige diensten op, dat wil zeggen diensten waarvan de datumEinde niet voor vandaag valt, of waar datumEinde leeg is.
     *
     * @param dienstFilter De soort diensten.
     * @return De diensten, of een lege lijst indien geen gevonden.
     */
    public Collection<Dienst> geefDiensten(final SoortDienst... dienstFilter) {
        final Set<Dienst> diensten = new HashSet<>();
        if (dienstbundels != null) {
            for (Dienstbundel dienstbundel : dienstbundels) {
                for (Dienst dienst : dienstbundel.getDiensten()) {
                    boolean voldoetAanFilter = dienstFilter == null || dienstFilter.length == 0;
                    if (dienstFilter != null) {
                        for (SoortDienst soortDienst : dienstFilter) {
                            if (soortDienst == dienst.getSoort()) {
                                voldoetAanFilter = true;
                                break;
                            }
                        }
                    }
                    if (voldoetAanFilter) {
                        diensten.add(dienst);
                    }
                }
            }
        }
        return diensten;
    }

    /**
     * Geeft de populatiebeperking expressie. Deze waarde is nooit leeg. Voor null waarden in de database wordt WAAR geretourneert, in alle andere gevallen
     * wordt de expressie zelf geretourneert
     *
     * @return een expressie String
     */
    public final String getPopulatiebeperkingExpressieString() {
        final PopulatiebeperkingAttribuut populatiebeperkingAttribuut = super.getPopulatiebeperking();
        String expressieWaarde = "WAAR";
        if (populatiebeperkingAttribuut != null && !StringUtils.isBlank(populatiebeperkingAttribuut.getWaarde())) {
            expressieWaarde = populatiebeperkingAttribuut.getWaarde();
        }
        return expressieWaarde;
    }

    public final boolean heeftDienstbundels() {
        return dienstbundels != null && !dienstbundels.isEmpty();
    }
}
