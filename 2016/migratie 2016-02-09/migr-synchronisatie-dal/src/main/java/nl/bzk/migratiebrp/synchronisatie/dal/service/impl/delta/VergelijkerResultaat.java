/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;

/**
 * VergelijkResultaat objecten bevatten een lijst met verschillen die het resultaat zijn van de vergelijking van twee
 * objecten.
 */
public interface VergelijkerResultaat {

    /**
     * Voegt een verschil toe aan het resultaat. Als er al een verschil bestaat dat een gelijke sleutel heeft dan wordt
     * deze vervangen. Betreft dit een verschil op <code>BRPActie</code> dan wordt de sleutel aangepast waarbij
     * <code>veld</code> en <code>eigenaarSleutel</code> op <code>null</code> wordt gezet.
     *
     * @param verschil het verschil dat wordt toegevoegd of vervangen
     */
    void voegToeOfVervangVerschil(final Verschil verschil);

    /**
     * Voegt alle verschillen toe aan deze VergelijkerResultaat.
     *
     * @param verschillen de toe te voegen verschillen
     */
    void voegVerschillenToe(Collection<Verschil> verschillen);

    /**
     * Voegt alle actie/herkomst mappings toen aan deze VergelijkerResultaat.
     *
     * @param actieHerkomstMap de toe te voegen actie/herkomst mappings
     */
    void voegActieHerkomstMapInhoudToe(Map<BRPActie, Lo3Voorkomen> actieHerkomstMap);

    /**
     * Verwijderd het gegeven verschil uit dit resultaat als het hierin voorkomt.
     *
     * @param verschil het verschil dat uit het resultaat verwijderd moet worden
     * @return true als het verschil is verwijderd, anders false
     */
    boolean verwijderVerschil(Verschil verschil);

    /**
     * @return de verschillen
     */
    Set<Verschil> getVerschillen();

    /**
     * @param sleutel de sleutel
     * @return true als er een verschil al bestaat met de gegeven sleutel in dit vergelijker resultaat, anders false
     * @see #zoekVerschil(Sleutel)
     */
    boolean bevatSleutel(Sleutel sleutel);

    /**
     * @param sleutel de sleutel
     * @return het verschil binnen dit resultaat waarvan de sleutel gelijk is aan de gegeven sleutel, als deze niet
     * gevonden wordt dan wordt null geretourneerd
     */
    Verschil zoekVerschil(Sleutel sleutel);

    /**
     * Zoekt een verschil op in de map met verschillen op basis van de opgegeven sleutel en verschiltype. Het gevonden
     * verschil wordt niet verwijderd uit de map met verschillen.
     *
     * @param zoekSleutel  sleutel waar de sleutel van het gezochte verschil aan moet voldoen.
     * @param verschiltype {@link nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType} waarop gefilterd wordt
     * @return de map-entry met het verschil dat gevonden is door deze zoekactie
     */
    Verschil zoekVerschil(final Sleutel zoekSleutel, final VerschilType verschiltype);

    /**
     * @return true als de set met verschillen leeg is, anders false
     */
    boolean isLeeg();

    /**
     * Maakt een mapping tussen de oude actie en de herkomst die bij de nieuwe actie hoort. De mapping wordt alleen
     * aangemaakt als de gegeven object van het type BRPActie zijn.
     *
     * @param oudeActie      de oude actie
     * @param nieuweHerkomst de nieuwe herkomst
     */
    void koppelHerkomstAanActie(final BRPActie oudeActie, final Lo3Voorkomen nieuweHerkomst);

    /**
     * Gets the de lijst met nieuwe LO3 voorkomens (LO3-herkomsten) om aan de oude acties te koppelen.
     *
     * @return the de lijst met nieuwe LO3 voorkomens (LO3-herkomsten) om aan de oude acties te koppelen
     */
    Map<BRPActie, Lo3Voorkomen> getActieHerkomstMap();

    /**
     * Legt een koppeling vast tussen een actie in de nieuwe Persoonslijst, en een actie in de al opgeslagen
     * Persoonslijst.
     *
     * @param nieuweActie     de actie in de nieuwe Persoonslijst
     * @param opgeslagenActie de actie in de opgeslagen Persoonslijst
     */
    void koppelNieuweActieMetBestaandeActie(final BRPActie nieuweActie, final BRPActie opgeslagenActie);

    /**
     * Bewaar een link tussen de actie en het voorkomen waarin die actie een rol speelt.
     *
     * @param actie     de actie
     * @param voorkomen het voorkomen
     */
    void koppelVoorkomenAanActie(BRPActie actie, FormeleHistorie voorkomen);

    /**
     * Bewaar een link tussen het voorkomen in de nieuwe of opgeslagen persoonslijst en de stapels in de nieuwe
     * persoonslijst en in de opgeslagen persoonslijst waar dit voorkomen bij hoort.
     *
     * @param voorkomen het voorkomen
     * @param stapels   de stapels waar dit voorkomen bij hoort in de nieuwe en de opgeslagen persoonslijst
     */
    void koppelStapelMatchAanVoorkomen(FormeleHistorie voorkomen, DeltaStapelMatch stapels);

    /**
     * @return de actie data die verzameld is tbv het consolideren van acties
     */
    ActieConsolidatieData getActieConsolidatieData();

    /**
     * Geeft de verschillen terug die voldoen aan het meegegeven verschilType en veldnaam en een sleutel hebben voor een
     * IST-stapel.
     *
     * @param verschilType verschilType dat het verschil moet bevatten.
     * @param veldnaam     het veldnaam waar op gezocht moet worden, kan null zijn als het veldnaam niet ingevuld is bij het
     *                     verscihl
     * @return de set van verschillen die een verschilType hebben die voldoen aan het verschilType dat meegegeven is en
     * een sleutel heeft die voor een stapel is.
     */
    Set<Verschil> getVerschillen(VerschilType verschilType, final String veldnaam);

    /**
     * Geeft de verschillen terug die voldoen aan het meegegeven verschilType, veldnaam en sleuteltype.
     *
     * @param verschilType       verschilType dat het verschil moet bevatten.
     * @param isVoorkomenSleutel true als de sleutel van het verschil een sleutel voor een stapelvoorkomen moet zijn. false als de
     *                           sleutel een sleutel voor een stapel moet zijn
     * @param veldnaam           het veldnaam waar op gezocht moet worden, kan null zijn als het veldnaam niet ingevuld is bij het
     *                           verscihl
     * @return de set van verschillen die een verschilType hebben die voldoen aan het verschilType en sleuteltype dat
     * meegegeven is.
     */
    Set<Verschil> getVerschillen(VerschilType verschilType, boolean isVoorkomenSleutel, final String veldnaam);

    /**
     * Geeft een verschil terug die voldoet aan het meegegeven verschilType.
     *
     * @param verschilType verschilType dat het verschil moet bevatten.
     * @return een verschil die een verschilType heeft die voldoet aan het verschilType dat meegegeven is.
     */
    Verschil getVerschil(VerschilType verschilType);

    /**
     * Geeft de verschilgroepen terug die gevonden zijn.
     *
     * @return een lijst van verschillen per BRP groep.
     */
    List<VerschilGroep> getVerschilGroepen();

    /**
     * Vervangt de huidige gevonden verschillen door de verschillen per groep in deze lijst.
     *
     * @param getransformeerdeVerschilGroepen de verschillen die de huidige verschillen gaan vervangen.
     */
    void vervangVerschillen(List<VerschilGroep> getransformeerdeVerschilGroepen);

    /**
     * Verwijdert alle gevonden verschillen voor de meegegeven historie.
     *
     * @param historie de historie waarvoor alle verschillen verwijderd moeten worden
     */
    void verwijderVerschillenVoorHistorie(FormeleHistorie historie);

    /**
     * Maakt een paar van de bestaande en nieuwe delta entiteiten.
     *
     * @param bestaandEntiteit bestaand {@link DeltaEntiteit}
     * @param nieuweEntiteit   nieuw {@link DeltaEntiteit}
     */
    void addDeltaEntiteitPaar(DeltaEntiteit bestaandEntiteit, DeltaEntiteit nieuweEntiteit);

    /**
     * @return geeft de set van {@link DeltaEntiteitPaar} terug. Deze collectie is 'unmodifiable'.
     */
    Set<DeltaEntiteitPaar> getDeltaEntiteitPaarSet();

    /**
     * Zoekt een of meerdere verschillen die voldoen aan de {@link EntiteitSleutel} maar waarbij de sleuteldelen worden genegeerd.
     * @param entiteitSleutel de entiteitsleutel waarmee gezocht wordt
     * @return een (lege) lijst van verschillen
     */
    List<Verschil> zoekVerschillen(EntiteitSleutel entiteitSleutel);

    /**
     * @return true als er alleen verschillen zijn gedetecteerd die betrekking hebben op een infrastructurele wijziging.
     */
    boolean bevatAlleenInfrastructureleWijzigingen();

    /**
     * @return true als er alleen verschillen zijn gedetecteerd die betrekking hebben op een a-nummer wijziging.
     */
    boolean bevatAlleenAnummerWijzigingen();
}
