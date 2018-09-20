/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;

/**
 * Cache met daarin de gegevens die nodig zijn voor de verschillende processen (
 * {@link nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.DeltaProces}) binnen het delta bepalen.
 */
public final class DeltaBepalingContext {
    private final AdministratieveHandeling administratieveHandelingGekoppeldAanActies;
    private final Persoon nieuwePersoon;
    private final Persoon bestaandePersoon;
    private final Lo3Bericht lo3Bericht;
    private final BRPActie actieVervalTbvLeveringMuts;

    private final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = new HashMap<>();
    private final Collection<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = new LinkedHashSet<>();
    private final Collection<DeltaEntiteitPaar> deltaEntiteitPaarSet = new LinkedHashSet<>();
    private AdministratieveHandeling administratieveHandelingVoorAnummerWijziging;
    private ConsolidatieData actieConsolidatieData;
    private boolean isAnummerWijziging;

    /**
     * Constructor voor de cache.
     *
     * @param nieuwPersoon
     *            nieuwe versie van persoon in-memory
     * @param bestaandPersoon
     *            huidige versie van persoon uit de database
     * @param lo3Bericht
     *            lo3 bericht waarop de nieuwePersoon persoonslijst is gebaseerd
     * @param isAnummerWijziging
     *            boolean waarmee aangegeven wordt of het een a-nummer wijziging betreft
     */
    public DeltaBepalingContext(final Persoon nieuwPersoon, final Persoon bestaandPersoon, final Lo3Bericht lo3Bericht, final boolean isAnummerWijziging) {
        this.nieuwePersoon = nieuwPersoon;
        this.bestaandePersoon = bestaandPersoon;
        this.lo3Bericht = lo3Bericht;
        this.isAnummerWijziging = isAnummerWijziging;

        administratieveHandelingGekoppeldAanActies = nieuwPersoon.getAdministratieveHandeling();
        actieVervalTbvLeveringMuts =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    administratieveHandelingGekoppeldAanActies,
                    administratieveHandelingGekoppeldAanActies.getPartij(),
                    administratieveHandelingGekoppeldAanActies.getDatumTijdRegistratie());
    }

    /**
     * @return een lijst van administratieve handelingen.
     */
    public Collection<AdministratieveHandeling> getAdministratieveHandelingen() {
        final List<AdministratieveHandeling> administratieveHandelingen = new ArrayList<>();
        administratieveHandelingen.add(administratieveHandelingGekoppeldAanActies);
        if (administratieveHandelingVoorAnummerWijziging != null) {
            administratieveHandelingen.add(0, administratieveHandelingVoorAnummerWijziging);
        }
        return administratieveHandelingen;
    }

    /**
     * @return een String met comma gescheiden alle administratieve handelingen.
     */
    public String getAdministratieveHandelingenAlsString() {
        final StringBuilder builder = new StringBuilder("");
        for (AdministratieveHandeling handeling : getAdministratieveHandelingen()) {
            builder.append(builder.length() > 0 ? ", " : "");
            builder.append(handeling.getSoort().getNaam());
        }
        return builder.toString();
    }

    /**
     * Wijzigt bij de administratieve handeling die aan de acties gekoppeld wordt het soort naar
     * {@link SoortAdministratieveHandeling#GBA_INFRASTRUCTURELE_WIJZIGING}. Als het soort niet aangepast mag worden,
     * zal deze methode niets doen.
     */
    public void setBijhoudingInfrastructureleWijziging() {
        if (magBijhoudingAanpassen()) {
            administratieveHandelingGekoppeldAanActies.setSoort(SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING);
        }
    }

    /**
     * Wijzigt bij de administratieve handeling die aan de acties gekoppeld wordt het soort naar
     * {@link SoortAdministratieveHandeling#GBA_BIJHOUDING_OVERIG}. Als het soort niet aangepast mag worden, zal deze
     * methode niets doen. Als het soort {@link SoortAdministratieveHandeling#GBA_A_NUMMER_WIJZIGING} is, dan wordt er
     * een extra administratieve handeling aangemaakt (zie
     * {@link #maakExtraAdministratieveHandelingVoorAnummerWijziging()}.
     */
    public void setBijhoudingOverig() {
        if (magBijhoudingAanpassen()) {
            if (isBijhoudingAnummerWijziging()) {
                maakExtraAdministratieveHandelingVoorAnummerWijziging();
            }
            administratieveHandelingGekoppeldAanActies.setSoort(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG);
        }
    }

    /**
     * Wijzigt bij de administratieve handeling die aan de acties gekoppeld wordt het soort naar
     * {@link SoortAdministratieveHandeling#GBA_AFVOEREN_PL}. Als het soort
     * {@link SoortAdministratieveHandeling#GBA_A_NUMMER_WIJZIGING} is, dan wordt er een extra administratieve handeling
     * aangemaakt (zie {@link #maakExtraAdministratieveHandelingVoorAnummerWijziging()}.
     */
    public void setBijhoudingAfgevoerd() {
        if (isBijhoudingAnummerWijziging()) {
            maakExtraAdministratieveHandelingVoorAnummerWijziging();
        }
        administratieveHandelingGekoppeldAanActies.setSoort(SoortAdministratieveHandeling.GBA_AFVOEREN_PL);
    }

    /**
     * Wijzigt bij de administratieve handeling die aan de acties gekoppeld wordt het soort naar
     * {@link SoortAdministratieveHandeling#GBA_A_NUMMER_WIJZIGING}. Als de extra administratieve handeling voor
     * a-nummer wijziging al bestaat, dan wordt de wijziging niet doorgevoerd.
     */
    public void setBijhoudingAnummerWijziging() {
        if (administratieveHandelingVoorAnummerWijziging == null) {
            if (isBijhoudingInfrastructureleWijziging()) {
                maakExtraAdministratieveHandelingVoorAnummerWijziging();
                setBijhoudingOverig();
            } else if (isBijhoudingOverig() || isBijhoudingAfvoerenPersoonslijstWijziging()) {
                maakExtraAdministratieveHandelingVoorAnummerWijziging();
            } else {
                administratieveHandelingGekoppeldAanActies.setSoort(SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING);
            }
        }
    }

    private boolean magBijhoudingAanpassen() {
        return !administratieveHandelingGekoppeldAanActies.getSoort().equals(SoortAdministratieveHandeling.GBA_AFVOEREN_PL)
               && !administratieveHandelingGekoppeldAanActies.getSoort().equals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG);
    }

    /**
     * @return true als de administratieve handeling die aan de acties gekoppeld wordt het soort
     *         {@link SoortAdministratieveHandeling#GBA_BIJHOUDING_OVERIG} is
     */
    public boolean isBijhoudingOverig() {
        return SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG.equals(administratieveHandelingGekoppeldAanActies.getSoort());
    }

    /**
     * @return true als de administratieve handeling die aan de acties gekoppeld wordt het soort
     *         {@link SoortAdministratieveHandeling#GBA_BIJHOUDING_ACTUEEL} is
     */
    public boolean isBijhoudingActueel() {
        return SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL.equals(administratieveHandelingGekoppeldAanActies.getSoort());
    }

    /**
     * @return true als de administratieve handeling die aan de acties gekoppeld wordt het soort
     *         {@link SoortAdministratieveHandeling#GBA_A_NUMMER_WIJZIGING} is
     */
    private boolean isBijhoudingAnummerWijziging() {
        return SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING.equals(administratieveHandelingGekoppeldAanActies.getSoort());
    }

    /**
     * @return true als de administratieve handeling die aan de acties gekoppeld wordt het soort
     *         {@link SoortAdministratieveHandeling#GBA_INFRASTRUCTURELE_WIJZIGING} is
     */
    private boolean isBijhoudingInfrastructureleWijziging() {
        return SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING.equals(administratieveHandelingGekoppeldAanActies.getSoort());
    }

    /**
     * @return true als de administratieve handeling die aan de acties gekoppeld wordt het soort
     *         {@link SoortAdministratieveHandeling#GBA_AFVOEREN_PL} is
     */
    private boolean isBijhoudingAfvoerenPersoonslijstWijziging() {
        return SoortAdministratieveHandeling.GBA_AFVOEREN_PL.equals(administratieveHandelingGekoppeldAanActies.getSoort());
    }

    /**
     * Geef de waarde van nieuwe persoon.
     *
     * @return nieuwe persoon
     */
    public Persoon getNieuwePersoon() {
        return nieuwePersoon;
    }

    /**
     * Geef de waarde van bestaande persoon.
     *
     * @return bestaande persoon
     */
    public Persoon getBestaandePersoon() {
        return bestaandePersoon;
    }

    /**
     * Geef de waarde van lo3 bericht.
     *
     * @return lo3 bericht
     */
    public Lo3Bericht getLo3Bericht() {
        return lo3Bericht;
    }

    /**
     * Geef de waarde van actie herkomst map.
     *
     * @return actie herkomst map
     */
    public Map<BRPActie, Lo3Voorkomen> getActieHerkomstMap() {
        return actieHerkomstMap;
    }

    /**
     * Voegt de actie/herkomst map inhoud toe aan de bestaande map actie/herkomst.
     *
     * @param actieHerkomstMapInhoud
     *            de inhoud van de actie/herkomst map
     */
    public void addActieHerkomstMapInhoud(final Map<BRPActie, Lo3Voorkomen> actieHerkomstMapInhoud) {
        actieHerkomstMap.putAll(actieHerkomstMapInhoud);
    }

    /**
     * Geeft de {@link BRPActie} voor actie verval tbv levering mutaties terug.
     *
     * @return een BRPActie op basis van de administratieve handeling van de nieuwe persoon.
     */
    public BRPActie getActieVervalTbvLeveringMuts() {
        return actieVervalTbvLeveringMuts;
    }

    /**
     * Voegt de verzamelde data om acties te consilideren toe aan de bestaande data. Als er nog geen verzamelde data is,
     * dan wordt de aangeboden data opgeslagen.
     *
     * @param data
     *            de nieuwe verzamelde data.
     */
    public void addAllActieConsolidatieData(final ConsolidatieData data) {
        if (actieConsolidatieData == null) {
            actieConsolidatieData = data;
        } else {
            actieConsolidatieData.addConsoldatieData(data);
        }
    }

    /**
     * Geeft de consolidatie actie data.
     *
     * @return de consolidatie actie data
     */
    public ConsolidatieData getActieConsolidatieData() {
        return actieConsolidatieData;
    }

    /**
     * Geef de verzameling van delta root entiteit overeenkomsten.
     *
     * @return de verzameling van delta root entiteit overeenkomsten
     */
    public Collection<DeltaRootEntiteitMatch> getDeltaRootEntiteitMatches() {
        return deltaRootEntiteitMatches;
    }

    /**
     * Voegt de {@link DeltaRootEntiteitMatch} objecten die zijn gevonden toe aan de context. Als er al bestaande
     * matches zijn, dan worden deze overschreven.
     *
     * @param deltaRootEntiteitMatch
     *            de set van {@link DeltaRootEntiteitMatch}
     */
    public void setDeltaRootEntiteitMatches(final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatch) {
        deltaRootEntiteitMatches.clear();
        deltaRootEntiteitMatches.addAll(deltaRootEntiteitMatch);
    }

    /**
     * Voegt de inhoud van de collectie van {@link DeltaEntiteitPaar} toe aan de bestaande collectie van
     * {@link DeltaEntiteitPaar}.
     *
     * @param deltaEntiteitPaarSetInhoud
     *            de inhoud van de collectie
     */
    public void addDeltaEntiteitPaarSetInhoud(final Collection<DeltaEntiteitPaar> deltaEntiteitPaarSetInhoud) {
        deltaEntiteitPaarSet.addAll(deltaEntiteitPaarSetInhoud);
    }

    /**
     * @return een unmodifiable collectie van {@link DeltaEntiteitPaar}.
     */
    public Collection<DeltaEntiteitPaar> getDeltaEntiteitPaarSet() {
        return Collections.unmodifiableCollection(deltaEntiteitPaarSet);
    }

    /**
     * Verbreek de {@link DeltaEntiteitPaar} voor de meegegeven {@link FormeleHistorie}.
     *
     * @param historie
     *            de waar het {@link DeltaEntiteitPaar} verbroken moet worden
     */
    public void verbreekDeltaEntiteitPaar(final FormeleHistorie historie) {
        for (final Iterator<DeltaEntiteitPaar> deltaEntiteitPaarIterator = deltaEntiteitPaarSet.iterator(); deltaEntiteitPaarIterator.hasNext();) {
            final DeltaEntiteitPaar paar = deltaEntiteitPaarIterator.next();
            if (paar.getBestaand() == historie) {
                deltaEntiteitPaarIterator.remove();
            }
        }
    }

    /**
     * Markeer de bestaande delta entiteit binnen een {@link DeltaEntiteitPaar} dat deze een M-rij gaat worden.
     * 
     * @param bestaandeRij
     *            de bestaande entiteit wat een M-rij gaat worden
     */
    public void markeerBestaandeRijAlsMRij(final FormeleHistorie bestaandeRij) {
        for (final DeltaEntiteitPaar entiteitPaar : deltaEntiteitPaarSet) {
            if (entiteitPaar.getBestaand() == bestaandeRij) {
                entiteitPaar.markeerBestaandAlsMRij();
            }
        }
    }

    /**
     * @return true als er alleen wijzigingen zijn geconstateerd op de eigen persoon. false als er bv ook wijzigingen
     *         zijn op de relatie gegevens.
     */
    public boolean heeftAlleenPersoonsWijzigingen() {
        boolean result = true;
        for (final DeltaRootEntiteitMatch match : deltaRootEntiteitMatches) {
            final VergelijkerResultaat vergelijkerResultaat = match.getVergelijkerResultaat();
            if (!match.isEigenPersoon() && !(vergelijkerResultaat == null || vergelijkerResultaat.isLeeg())) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * @return de match van de eigen persoon.
     */
    public DeltaRootEntiteitMatch getEigenPersoonMatch() {
        DeltaRootEntiteitMatch match = null;
        for (final DeltaRootEntiteitMatch mogelijkeMatch : deltaRootEntiteitMatches) {
            if (mogelijkeMatch.isEigenPersoon()) {
                match = mogelijkeMatch;
            }
        }

        return match;
    }

    /**
     * @return true als het volgens de synchronisatie server (zie PlVerwerkerSynchronisatie) een a-nummer wijziging is.
     */
    public boolean isAnummerWijziging() {
        return isAnummerWijziging;
    }

    /**
     * Maakt een extra administratieve handeling met soort {@link SoortAdministratieveHandeling#GBA_A_NUMMER_WIJZIGING}
     * aan. Als deze al aangemaakt is, zal dit niet opnieuw gedaan worden.
     */
    private void maakExtraAdministratieveHandelingVoorAnummerWijziging() {
        if (administratieveHandelingVoorAnummerWijziging == null) {
            administratieveHandelingVoorAnummerWijziging =
                    new AdministratieveHandeling(
                        administratieveHandelingGekoppeldAanActies.getPartij(),
                        SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING);
            final Timestamp datumTijdRegistratie = administratieveHandelingGekoppeldAanActies.getDatumTijdRegistratie();

            // Om de tsReg uniek te maken, tel er 1ms bij op
            administratieveHandelingVoorAnummerWijziging.setDatumTijdRegistratie(new Timestamp(datumTijdRegistratie.getTime() - 1));
        }
    }

    /**
     * @return Geeft de administratieve handeling terug die aan de acties gekoppeld moet worden
     */
    public AdministratieveHandeling getAdministratieveHandelingGekoppeldAanActies() {
        return administratieveHandelingGekoppeldAanActies;
    }
}
