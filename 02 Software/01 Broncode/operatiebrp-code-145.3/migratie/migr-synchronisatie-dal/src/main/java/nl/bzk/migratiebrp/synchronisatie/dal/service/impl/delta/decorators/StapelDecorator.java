/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Decorator voor {@link Stapel} die logica biedt die de entiteit niet kan bieden.
 */
public final class StapelDecorator {
    private static final String OUDER1_CATEGORIE = "02";
    private static final String OUDER2_CATEGORIE = "03";
    private static final String HUWELIJK_OF_GP_CATEGORIE = "05";
    private static final String KIND_CATEGORIE = "09";
    private static final String GEZAGSVERHOUDING_CATEGORIE = "11";
    private static final int ACTUEEL_VOORKOMEN = 0;

    private final Stapel stapel;

    /**
     * Maakt een StapelRelateerDecorator object.
     * @param stapel het object waaraan relateer functionaliteit moet worden toegevoegd
     */
    private StapelDecorator(final Stapel stapel) {
        ValidationUtils.controleerOpNullWaarden("stapel mag niet null zijn", stapel);
        this.stapel = stapel;
    }

    /**
     * @param stapel het te decoreren Stapel object
     * @return een StapelDecorator object
     */
    public static StapelDecorator decorate(final Stapel stapel) {
        final StapelDecorator result;
        if (stapel == null) {
            result = null;
        } else {
            result = new StapelDecorator(stapel);
        }
        return result;
    }

    private Set<StapelVoorkomenDecorator> decorateVoorkomens(final boolean reverseOrder) {
        final Set<StapelVoorkomenDecorator> result = new TreeSet<>(new StapelVoorkomenSorter(reverseOrder));

        for (final StapelVoorkomen voorkomen : stapel.getStapelvoorkomens()) {
            result.add(StapelVoorkomenDecorator.decorate(voorkomen));
        }

        return result;
    }

    private List<RelatieDecorator> decorateRelaties() {
        final List<RelatieDecorator> decoratedRelaties = decorateRelatiesOngesorteerd();
        Collections.sort(decoratedRelaties, RelatieDecorator.getSorteerder());
        return decoratedRelaties;
    }

    private List<RelatieDecorator> decorateRelatiesOngesorteerd() {
        return stapel.getRelaties().stream().map(RelatieDecorator::decorate).collect(Collectors.toList());
    }

    /**
     * Geef de waarde van stapel.
     * @return stapel
     */
    public Stapel getStapel() {
        return stapel;
    }

    /**
     * Geef de huidig (in de dabatase) gekoppelde voorkomens.
     * @return de voorkomens van deze stapel
     */
    public Set<StapelVoorkomenDecorator> getVoorkomens() {
        return decorateVoorkomens(false);
    }

    /**
     * Verwerkt de meegegeven voorkomens in deze stapel. Als er een huidig voorkomen niet voorkomt in de nieuwe set van
     * voorkomens, dan wordt dit voorkomen verwijderd. Als er een voorkomen uit de nieuwe set nog niet bestaat in de
     * huidige set, dan wordt deze toegevoegd. Anders wordt het huidige voorkomen overschreven met de inhoud van het
     * nieuwe voorkomen.
     * @param nieuweVoorkomens de nieuwe voorkomens
     * @param directVerwijderen indien true dan wordt het voorkomen ook daadwerkelijk verwijderd, anders word deze tegevoegd aan een lijst met te verwijderen
     * voorkomens
     * @return Set<StapelVoorkomenDecorator> lijst met te verwijderen {@link StapelVoorkomenDecorator}, indien directverwijderen is true wordt een lege lijst
     * terug gegeven.
     */
    public Set<StapelVoorkomenDecorator> setVoorkomens(final Set<StapelVoorkomenDecorator> nieuweVoorkomens, final boolean directVerwijderen) {
        final Set<StapelVoorkomenDecorator> teVerwijderenVoorKomens = new HashSet<>();
        for (final StapelVoorkomenDecorator voorkomen : getVoorkomens()) {
            if (!nieuweVoorkomens.contains(voorkomen)) {
                if (directVerwijderen) {
                    verwijderVoorkomen(voorkomen);
                } else {
                    teVerwijderenVoorKomens.add(voorkomen);
                }
            }
        }

        for (final StapelVoorkomenDecorator nieuwVoorkomen : nieuweVoorkomens) {
            StapelVoorkomenDecorator bestaandVoorkomen = null;
            for (final StapelVoorkomenDecorator voorkomen : getVoorkomens()) {
                if (voorkomen.equals(nieuwVoorkomen)) {
                    bestaandVoorkomen = voorkomen;
                    break;
                }
            }

            if (bestaandVoorkomen != null) {
                bestaandVoorkomen.werkGegevensBij(nieuwVoorkomen);
            } else {
                addVoorkomen(nieuwVoorkomen);
            }
        }
        return teVerwijderenVoorKomens;
    }

    /**
     * Verwerkt de meegegeven voorkomens en relaties in deze stapel. Als er een huidig voorkomen niet voorkomt in de
     * nieuwe set van voorkomens, dan wordt dit voorkomen verwijderd. Als er een voorkomen uit de nieuwe set nog niet
     * bestaat in de huidige set, dan wordt deze toegevoegd. Anders wordt het huidige voorkomen overschreven met de
     * inhoud van het nieuwe voorkomen.
     * @param nieuweVoorkomens de nieuwe voorkomens
     * @param nieuweRelaties nieuwe relaties
     * @param voorkomensDirectVerwijderen indien true worden voorkomens direct verwijderd
     * @return Set<StapelVoorkomenDecorator> indien voorkomensDirectVerwijderen als false word meegegeven word een lijst met te verwijderen Voorkomens
     * teruggegeven. (anders een lege Lijst)
     */
    public Set<StapelVoorkomenDecorator> setVoorkomensEnRelaties(
            final Set<StapelVoorkomenDecorator> nieuweVoorkomens,
            final List<RelatieDecorator> nieuweRelaties,
            final boolean voorkomensDirectVerwijderen) {
        final Set<StapelVoorkomenDecorator> teVerwijderenVoorkomens = setVoorkomens(nieuweVoorkomens, voorkomensDirectVerwijderen);

        for (final RelatieDecorator relatie : nieuweRelaties) {
            relatie.addStapel(this);
        }
        return teVerwijderenVoorkomens;
    }

    /**
     * verwijder een voorkomen van een stapel.
     * @param voorkomen te verwijderen voorkomen
     */
    public void verwijderVoorkomen(final StapelVoorkomenDecorator voorkomen) {
        stapel.getStapelvoorkomens().remove(voorkomen.getVoorkomen());
    }

    private void addVoorkomen(final StapelVoorkomenDecorator nieuwVoorkomen) {
        final StapelVoorkomen voorkomen = nieuwVoorkomen.kopieer().getVoorkomen();
        stapel.addStapelVoorkomen(voorkomen);
    }

    /**
     * Geef de waarde van categorie.
     * @return de categorie van deze stapel
     */
    public String getCategorie() {
        return stapel.getCategorie();
    }

    /**
     * Geef de waarde van stapel nummer.
     * @return het stapelnummer van deze stapel
     */
    public int getStapelNummer() {
        return stapel.getVolgnummer();
    }

    /**
     * Geef de waarde van actueel categorienummer.
     * @return Het actuele categorienummer waar deze stapel bij hoort
     */
    public int getActueelCategorienummer() {
        try {
            return Lo3CategorieEnum.bepaalActueleCategorie(Lo3CategorieEnum.getLO3Categorie(getCategorie())).getCategorieAsInt();
        } catch (final Lo3SyntaxException e) {
            throw new IllegalStateException("Onbekende categorie in IST stapel.", e);
        }
    }

    /**
     * Geeft aan of de stapel een stapel uit LO3 Categorie Kind (09) betreft.
     * @return true als het een kind stapel is
     */
    public boolean isKindStapel() {
        return KIND_CATEGORIE.equals(stapel.getCategorie());
    }

    /**
     * Geeft aan of de stapel een stapel uit LO3 Categorie Ouder1/Ouder2 (02/03) betreft.
     * @return true als het een ouder stapel is
     */
    public boolean isOuderStapel() {
        return isOuder1Stapel() || isOuder2Stapel();
    }

    /**
     * Geeft aan of de stapel een stapel uit LO3 Categorie Ouder1 (Categorie 02) betreft.
     * @return true als het een ouder1 stapel is
     */
    public boolean isOuder1Stapel() {
        return OUDER1_CATEGORIE.equals(stapel.getCategorie());
    }

    /**
     * Geeft aan of de stapel een stapel uit LO3 Categorie Ouder2 (Categorie 03) betreft.
     * @return true als het een ouder1 stapel is
     */
    public boolean isOuder2Stapel() {
        return OUDER2_CATEGORIE.equals(stapel.getCategorie());
    }

    /**
     * Geeft aan of de stapel een stapel uit LO3 Categorie Huwelijk/Geregistreerd partnerschap (05) betreft.
     * @return true als het een ouder stapel is
     */
    public boolean isHuwelijkOfGpStapel() {
        return HUWELIJK_OF_GP_CATEGORIE.equals(stapel.getCategorie());
    }

    /**
     * Geeft aan of de stapel een stapel uit LO3 Categorie Gezagsverhouding (11) betreft.
     * @return true als het een gezagsverhouding stapel is
     */
    public boolean isGezagsverhoudingStapel() {
        return GEZAGSVERHOUDING_CATEGORIE.equals(stapel.getCategorie());
    }

    /**
     * Geeft het actuele voorkomen terug van deze stapel.
     * @return het actuele voorkomen van deze stapel
     */
    public StapelVoorkomenDecorator getActueelVoorkomen() {
        StapelVoorkomenDecorator resultaat = null;
        for (final StapelVoorkomen stapelVoorkomen : stapel.getStapelvoorkomens()) {
            if (stapelVoorkomen.getVolgnummer() == ACTUEEL_VOORKOMEN) {
                resultaat = StapelVoorkomenDecorator.decorate(stapelVoorkomen);
                break;
            }
        }
        return resultaat;
    }

    /**
     * Geeft aan of het gezochte voorkomen in de stapel voorkomt. Controle wordt gedaan op inhoudelijk velden op de
     * groepen 81, 82, 83, 84 en 86 na.
     * @param gezochtStapelVoorkomen het gezochte voorkomen
     * @return true als het voorkomen in de stapel voorkomt
     */
    public boolean bevatStapelVoorkomen(final StapelVoorkomenDecorator gezochtStapelVoorkomen) {
        boolean result = false;
        for (final StapelVoorkomenDecorator voorkomen : getVoorkomens()) {
            if (gezochtStapelVoorkomen.isInhoudelijkGelijkAan(voorkomen, false)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("Categorie %1$s, stapel %2$s", getCategorie(), getStapelNummer());
    }

    /**
     * * Geef de huidig (in de dabatase) gekoppelde relaties. Elke keer als deze methode wordt aangeroepen wordt er een
     * nieuwe lijst samen gesteld met opnieuw decorated relaties.
     * @return alle relaties als @{link RelatieDecorator}
     */
    public List<RelatieDecorator> getRelaties() {
        return decorateRelaties();
    }

    /**
     * Controleert of de meegegven set van voorkomens een match is met de voorkomens van deze stapel. Als het aantal
     * voorkomens niet overeenkomt of als de voorkomens inhoudelijk niet overeenkomen, dan is het geen match.
     * @param andereVoorkomens set met voorkomens van de nieuwe persoonslijst
     * @param gelijkVolgnr true als volgnummer gelijk moet zijn. Indien false, dan mag volgnr in de andere stapel max 1 hoger zijn.
     * @return true als het aantal voorkomens gelijk is aan het aantal voorkomens van de stapel en deze ook inhoudelijk gelijk zijn.
     */
    public boolean isVoorkomenSetMatch(final Set<StapelVoorkomenDecorator> andereVoorkomens, final boolean gelijkVolgnr) {
        final Set<StapelVoorkomenDecorator> eigenVoorkomens = getVoorkomens();
        boolean result = eigenVoorkomens.size() == andereVoorkomens.size();

        for (final StapelVoorkomenDecorator voorkomen : eigenVoorkomens) {
            final StapelVoorkomenDecorator matchingVoorkomen = getMatchingVoorkomen(andereVoorkomens, voorkomen);
            if (matchingVoorkomen == null) {
                result = false;
            } else {
                if ((gelijkVolgnr && matchingVoorkomen.getVolgnummer() != voorkomen.getVolgnummer())
                        || (!gelijkVolgnr && matchingVoorkomen.getVolgnummer() - 1 != voorkomen.getVolgnummer())) {
                    result = false;
                }
            }
            if (!result) {
                break;
            }
        }

        return result;
    }

    private StapelVoorkomenDecorator getMatchingVoorkomen(final Set<StapelVoorkomenDecorator> voorkomenSet, final StapelVoorkomenDecorator zoekVoorkomen) {
        StapelVoorkomenDecorator result = null;
        for (final StapelVoorkomenDecorator voorkomen : voorkomenSet) {
            if (voorkomen.isInhoudelijkGelijkAan(zoekVoorkomen, true)) {
                result = voorkomen;
                break;
            }
        }

        return result;
    }

    private StapelVoorkomenDecorator getVoorkomen(final StapelVoorkomenDecorator zoekVoorkomen) {
        StapelVoorkomenDecorator result = null;
        for (final StapelVoorkomenDecorator voorkomen : getVoorkomens()) {
            if (voorkomen.equals(zoekVoorkomen)) {
                result = voorkomen;
                break;
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stapel.getCategorie()).append(stapel.getVolgnummer()).toHashCode();
    }

    @Override
    public boolean equals(final Object anderObject) {
        if (this == anderObject) {
            return true;
        }
        if (!(anderObject instanceof StapelDecorator)) {
            return false;
        }
        final StapelDecorator ander = (StapelDecorator) anderObject;
        final Stapel stapel2 = ander.stapel;

        return new EqualsBuilder().append(stapel.getCategorie(), stapel2.getCategorie())
                .append(stapel.getVolgnummer(), stapel2.getVolgnummer())
                .isEquals();
    }

    /**
     * Controleert of de betrokkenheid in een relatie voorkomt die aan deze stapel is gekoppeld.
     * @param betrokkenheid de betrokkenheid waarvan vastgesteld moet worden of deze in de relatie voorkomt binnen deze stapel
     * @return true als de betrokkenheid voorkomt in een relatie binnen deze stapel.
     */
    public boolean bevatRelatie(final BetrokkenheidDecorator betrokkenheid) {
        boolean result = false;
        for (final RelatieDecorator relatie : getRelaties()) {
            if (relatie.bevatBetrokkenheid(betrokkenheid)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Voegt een nieuw actueel voorkomen toe aan de set van voorkomens.
     * @param nieuwActueelVoorkomen het nieuw toe te voegen actuele voorkomen.
     * @param teVerwijderenVoorkomens set met voorkomens welke gemarkeerd staan om te worden verwijderd
     */
    public void voegNieuwActueelVoorkomenToe(
            final StapelVoorkomenDecorator nieuwActueelVoorkomen,
            final Set<StapelVoorkomenDecorator> teVerwijderenVoorkomens) {
        final Map<Integer, StapelVoorkomenDecorator> teVerwijderenVolgnummers = new HashMap<>();
        for (StapelVoorkomenDecorator teVerwijderenVoorkomen : teVerwijderenVoorkomens) {
            teVerwijderenVolgnummers.put(teVerwijderenVoorkomen.getVolgnummer(), teVerwijderenVoorkomen);
        }
        // Er komt een nieuwe actueel voorkomen bij, de rest moet verplaatst worden.
        // Verplaatsen is het handigst om het oudste voorkomen eerst te verplaatsen.
        final Set<StapelVoorkomenDecorator> bestaandeVoorkomens = decorateVoorkomens(true);
        StapelVoorkomenDecorator vorigeStapel = null;
        for (final StapelVoorkomenDecorator bestaandeVoorkomen : bestaandeVoorkomens) {
            if (!teVerwijderenVolgnummers.containsKey(bestaandeVoorkomen.getVolgnummer())) {
                if (vorigeStapel == null) {
                    final StapelVoorkomen voorkomen =
                            new StapelVoorkomen(stapel, bestaandeVoorkomen.getVolgnummer() + 1, bestaandeVoorkomen.getAdministratieveHandeling());
                    stapel.addStapelVoorkomen(voorkomen);
                    vorigeStapel = StapelVoorkomenDecorator.decorate(voorkomen);
                }
                vorigeStapel.werkGegevensBij(bestaandeVoorkomen);
                final Integer vorigVolgnummer = vorigeStapel.getVolgnummer();
                if (teVerwijderenVolgnummers.containsKey(vorigVolgnummer)) {
                    teVerwijderenVoorkomens.remove(teVerwijderenVolgnummers.get(vorigVolgnummer));
                }
            }
            vorigeStapel = bestaandeVoorkomen;
        }

        // Alle bestaande voorkomens zijn nu verplaatst, de actuele kan toegevoegd worden
        final StapelVoorkomenDecorator actueleVoorkomen = getVoorkomen(nieuwActueelVoorkomen);
        if (actueleVoorkomen == null) {
            addVoorkomen(nieuwActueelVoorkomen);
        } else {
            actueleVoorkomen.werkGegevensBij(nieuwActueelVoorkomen);
        }
    }

    /**
     * Koppelt een relatie aan een stapel.
     * @param relatie de relatie die toegevoegd wordt
     */
    public void koppelRelatie(final RelatieDecorator relatie) {
        stapel.addRelatie(relatie.getRelatie());
    }

    /**
     * Ontkoppelt een relatie bij een stapel.
     * @param relatie de relatie die verwijderd wordt
     */
    public void ontkoppelRelatie(final RelatieDecorator relatie) {
        stapel.removeRelatie(relatie.getRelatie());
    }

    /**
     * Ontkoppelt alle relaties van deze stapel.
     */
    public void ontkoppelRelaties() {
        for (final RelatieDecorator relatie : decorateRelatiesOngesorteerd()) {
            stapel.removeRelatie(relatie.getRelatie());
        }
    }

    /**
     * Geeft de andere relaties, dan de meegegeven relaties, terug die gekoppeld zijn aan de stapel.
     * @param matchingRelaties bestaande relaties uit deze stapel zodat de andere relaties goed geidentificeerd kan worden
     * @param exactMatch true als de relatie exact moet matchen. False indien de ontbinding genegeerd kan worden als de relatie waar tegen gematched wordt niet
     * ontbonden is.
     * @return de andere relaties van deze stapel of een lege lijst.
     */
    public Set<RelatieDecorator> getOverigeRelaties(final Set<RelatieDecorator> matchingRelaties, final boolean exactMatch) {
        final Set<RelatieDecorator> overigeRelaties = new HashSet<>();
        for (final RelatieDecorator relatie : getRelaties()) {
            if (!isMatchingRelatie(relatie, matchingRelaties, exactMatch)) {
                overigeRelaties.add(relatie);
            }
        }
        return overigeRelaties;
    }

    private boolean isMatchingRelatie(final RelatieDecorator relatie, final Set<RelatieDecorator> matchingRelaties, final boolean exactMatch) {
        boolean result = false;
        for (final RelatieDecorator matchingRelatie : matchingRelaties) {
            if (relatie.matches(matchingRelatie, exactMatch)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Geeft de relatie terug die bij de stapel hoort. Deze methode kan alleen iets terug geven als de stapel een ouder
     * of kind stapel is.
     * @return de relatie van deze stapel
     * @throws IllegalStateException Als de stapel bij een huwelijk/geregistreerd partnerschap hoort. Hier kunnen meerdere relaties aan de stapel hangen.
     */
    public RelatieDecorator getRelatie() {
        if (isHuwelijkOfGpStapel()) {
            throw new IllegalStateException("Er kunnen meer relaties aan deze stapel hangen.");
        }
        return getRelaties().iterator().next();
    }

    /**
     * Sorteert de stapelvoorkomens op basis van volgnummer van nieuw naar oud.
     */
    private static class StapelVoorkomenSorter implements Comparator<StapelVoorkomenDecorator>, Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean reverseOrder;

        /**
         * Constructor.
         * @param reverseOrder Indicator of de volgorde omgekeerd is.
         */
        StapelVoorkomenSorter(final boolean reverseOrder) {
            this.reverseOrder = reverseOrder;
        }

        @Override
        public int compare(final StapelVoorkomenDecorator o1, final StapelVoorkomenDecorator o2) {
            final int resultaat;
            if (reverseOrder) {
                resultaat = o2.getVolgnummer() - o1.getVolgnummer();
            } else {
                resultaat = o1.getVolgnummer() - o2.getVolgnummer();
            }
            return resultaat;
        }
    }
}
