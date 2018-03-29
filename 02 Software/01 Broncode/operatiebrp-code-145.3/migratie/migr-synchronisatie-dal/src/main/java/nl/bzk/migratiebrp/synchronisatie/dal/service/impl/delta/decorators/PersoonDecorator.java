/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Decorator voor {@link Persoon} met daarin logica die niet in de entiteit zit.
 */
public final class PersoonDecorator {

    private static final StapelSorter STAPEL_SORTER = new StapelSorter();

    private final Persoon persoon;

    private boolean checkGedaanOpVerval;

    /**
     * Maakt een PersoonDecorator object.
     * @param persoon het object waaraan functionaliteit moet worden toegevoegd
     */
    private PersoonDecorator(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * @param stapel het te decoreren Persoon object
     * @return een PersoonDecorator object
     */
    public static PersoonDecorator decorate(final Persoon stapel) {
        final PersoonDecorator result;
        if (stapel == null) {
            result = null;
        } else {
            result = new PersoonDecorator(Entiteit.convertToPojo(stapel));
        }
        return result;
    }

    private Set<StapelDecorator> decorateStapels() {
        final Set<StapelDecorator> result = new TreeSet<>(STAPEL_SORTER);

        for (final Stapel stapel : persoon.getStapels()) {
            result.add(StapelDecorator.decorate(stapel));
        }

        return result;
    }

    private Set<BetrokkenheidDecorator> decorateBetrokkenheden() {
        final Set<BetrokkenheidDecorator> result = new HashSet<>();
        for (final Betrokkenheid betrokkenheid : persoon.getBetrokkenheidSet()) {
            result.add(BetrokkenheidDecorator.decorate(betrokkenheid));
        }

        return result;
    }

    /**
     * Geeft de IST-stapels terug die betrekking hebben op de kind categorieen.
     * @return de IST-stapels die betrekking hebben op de kind categorieen.
     */
    public Set<StapelDecorator> getKindStapels() {
        final Set<StapelDecorator> kindStapels = new TreeSet<>(STAPEL_SORTER);
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.isKindStapel()) {
                kindStapels.add(stapel);
            }
        }
        return kindStapels;
    }

    /**
     * Geeft de IST-stapels terug die betrekking hebben op de huwelijk/geregistreerd partnerschap categorieen.
     * @return de IST-stapels die betrekking hebben op de huwelijk/geregistreerd partnerschap categorieen.
     */
    public Set<StapelDecorator> getHuwelijkOfGpStapels() {
        final Set<StapelDecorator> huwelijkOfGpStapels = new TreeSet<>(STAPEL_SORTER);
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.isHuwelijkOfGpStapel()) {
                huwelijkOfGpStapels.add(stapel);
            }
        }
        return huwelijkOfGpStapels;
    }

    /**
     * Geeft de IST-stapel terug die betrekking heeft op de ouder1 (cat02) categorie.
     * @return de IST-stapel terug die betrekking heeft op de ouder1 (cat02) categorie.
     */
    public StapelDecorator getOuder1Stapel() {
        StapelDecorator resultaat = null;
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.isOuder1Stapel()) {
                resultaat = stapel;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Geeft de IST-stapel terug die betrekking heeft op de gezagsverhouding (cat11) categorie.
     * @return de IST-stapel terug die betrekking heeft op de gezagsverhouding (cat11) categorie.
     */
    public StapelDecorator getGezagsverhoudingStapel() {
        StapelDecorator resultaat = null;
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.isGezagsverhoudingStapel()) {
                resultaat = stapel;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Geeft de IST-stapel terug die betrekking heeft op de ouder2 (cat03) categorie.
     * @return de IST-stapel terug die betrekking heeft op de ouder2 (cat03) categorie.
     */
    public StapelDecorator getOuder2Stapel() {
        StapelDecorator resultaat = null;
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.isOuder2Stapel()) {
                resultaat = stapel;
                break;
            }
        }
        return resultaat;
    }

    /**
     * /** Geeft de (IST) stapels terug die geen betrekking hebben op de kind-categorieen of huwelijk/GP categorieen.
     * @return de kindstapels.
     */
    public Set<StapelDecorator> getOverigeStapels() {
        final Set<StapelDecorator> overigeStapels = new TreeSet<>(STAPEL_SORTER);
        for (final StapelDecorator stapel : getStapels()) {
            if (!stapel.isKindStapel() && !stapel.isHuwelijkOfGpStapel() && !stapel.isOuderStapel()) {
                overigeStapels.add(stapel);
            }
        }

        return overigeStapels;
    }

    /**
     * Geef de waarde van IST-stapels.
     * @return de IST-stapels van deze persoon
     */
    public Set<StapelDecorator> getStapels() {
        return decorateStapels();
    }

    /**
     * Maak een nieuwe IST stapel aan die gekoppeld is aan deze persoon.
     * @param categorie de categorie voor de IST stapel
     * @param stapelNummer het stapelnummer voor de IST stapel
     * @return de nieuwe IST stapel
     */
    public StapelDecorator maakStapel(final String categorie, final int stapelNummer) {
        final Stapel stapel = new Stapel(persoon, categorie, stapelNummer);
        persoon.addStapel(stapel);

        return StapelDecorator.decorate(stapel);
    }

    /**
     * Verwijdert de stapels die leeg zijn van deze persoon. Ook de koppeling van deze stapel naar alle relaties wordt
     * dan verwijderd.
     * @param teVerwijderenStapels Set van stapels die gemarkeerd zijn om te verwijderen
     */
    public void verwijderStapels(final Set<StapelDecorator> teVerwijderenStapels) {
        for (final StapelDecorator stapel : getStapels()) {
            if (teVerwijderenStapels.contains(stapel)) {
                persoon.removeStapel(stapel.getStapel());
                for (final RelatieDecorator relatie : stapel.getRelaties()) {
                    relatie.removeStapel(stapel);
                }
            }
        }
    }

    /**
     * Voegt een IST-stapel toe aan de persoon.
     * @param stapel de IST-stapel die toegevoegd moet worden
     */
    public void addStapel(final StapelDecorator stapel) {
        persoon.addStapel(stapel.getStapel());
    }

    /**
     * Zoekt een stapel adhv de meegegeven categorie en volgnummer. Ook een check om te kijken of de meegeven stapel al
     * bestaat bij de persoon.
     * @param categorie categorie van de stapel
     * @param stapelNummer volgnummer van de stapel
     * @return de stapel die voldoet aan {@link StapelDecorator#equals(Object)} methode.
     */
    public StapelDecorator zoekStapel(final String categorie, final int stapelNummer) {
        StapelDecorator result = null;
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.getCategorie().equals(categorie) && stapel.getStapelNummer() == stapelNummer) {
                result = stapel;
                break;
            }
        }
        return result;
    }

    /**
     * Zoekt een stapel adhv de meegegeven stapel op. Ook een check om te kijken of de meegeven stapel al bestaat bij de
     * persoon.
     * @param verschilStapel de stapel die gezocht wordt op de persoonslijst
     * @return de stapel die voldoet aan {@link StapelDecorator#equals(Object)} methode.
     */
    public StapelDecorator zoekStapel(final StapelDecorator verschilStapel) {
        StapelDecorator result = null;
        for (final StapelDecorator stapel : getStapels()) {
            if (stapel.equals(verschilStapel)) {
                result = stapel;
                break;
            }
        }
        return result;
    }

    /**
     * Geeft de betrokkenheid terug die bij de opgegeven stapel hoort. Hiervoor wordt er gekeken wat voor stapel het is
     * (kind, ouder of partner) en deze informatie wordt gebruikt mbv de {@link SoortBetrokkenheid} om de juiste
     * betrokkenheid op te zoeken.
     * @param stapel de stapel waar de betreffende betrokkenheid opgezocht moet worden
     * @return de betrokkenheid als deze gevonden wordt voor de stapel, anders null.
     */
    public Set<BetrokkenheidDecorator> zoekIkBetrokkenhedenVoorStapel(final StapelDecorator stapel) {
        final SoortBetrokkenheid soortBetrokkenheid;
        if (stapel.isKindStapel()) {
            soortBetrokkenheid = SoortBetrokkenheid.OUDER;
        } else if (stapel.isOuderStapel()) {
            soortBetrokkenheid = SoortBetrokkenheid.KIND;
        } else {
            soortBetrokkenheid = SoortBetrokkenheid.PARTNER;
        }

        final Set<BetrokkenheidDecorator> result = new HashSet<>();
        for (final BetrokkenheidDecorator betrokkenheid : getBetrokkenheidSet()) {
            if (betrokkenheid.getSoortBetrokkenheid().equals(soortBetrokkenheid) && stapel.bevatRelatie(betrokkenheid)) {
                result.add(betrokkenheid);
            }
        }
        return result;
    }

    /**
     * Geeft alle relaties terug als {@link RelatieDecorator}.
     * @return alle relaties als @{link RelatieDecorator}
     */
    public Set<RelatieDecorator> getRelaties() {
        final Set<RelatieDecorator> relaties = new HashSet<>();
        for (final Relatie relatie : persoon.getRelaties()) {
            relaties.add(RelatieDecorator.decorate(relatie));
        }

        return relaties;
    }

    /**
     * Geeft de persoon terug waar deze decorator voor gemaakt is.
     * @return de undecorated persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Controleert of de meegegeven {@link PersoonDecorator} dezelfde {@link Persoon} object heeft als deze instantie.
     * @param persoonDecorator de {@link PersoonDecorator} object waarmee vergeleken gaat worden
     * @return true als beide decorators dezelfde {@link Persoon} object bevatten
     */
    public boolean isSameAs(final PersoonDecorator persoonDecorator) {
        return persoon == persoonDecorator.persoon;
    }

    /**
     * Geeft de administratieve handeling terug die aan de persoon gekoppeld is.
     * @return de administratieve handeling die aan de persoon gekoppeld is
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return persoon.getAdministratieveHandeling();
    }

    /**
     * Geeft de set van betrokkenheden terug die aan deze persoon gekoppeld zijn.
     * @return de set van betrokkenheden
     */
    public Set<BetrokkenheidDecorator> getBetrokkenheidSet() {
        return decorateBetrokkenheden();
    }

    /**
     * Voegt een set van betrokkenheden toe aan de persoon.
     * @param nieuweBetrokkenheden de set van betrokkenheden die toegevoegd moet worden aan de persoon
     */
    public void addBetrokkenheden(final Set<BetrokkenheidDecorator> nieuweBetrokkenheden) {
        for (final BetrokkenheidDecorator betrokkenheid : nieuweBetrokkenheden) {
            persoon.addBetrokkenheid(betrokkenheid.getBetrokkenheid());
        }
    }

    /**
     * Verwijderd een set van betrokkenheden van de persoon.
     * @param verwijderdeBetrokkenheden de set van betrokkenheid die verwijderd moet worden bij de persoon
     */
    public void verwijderBetrokkenheden(final Set<BetrokkenheidDecorator> verwijderdeBetrokkenheden) {
        for (final BetrokkenheidDecorator betrokkenheidDecorator : verwijderdeBetrokkenheden) {
            verwijderBetrokkenheid(betrokkenheidDecorator);
        }
    }

    /**
     * Verwijderd een betrokkenheid van de persoon.
     * @param verwijderdeBetrokkenheid de betrokkenheid die verwijderd moet worden bij de persoon
     */
    public void verwijderBetrokkenheid(final BetrokkenheidDecorator verwijderdeBetrokkenheid) {
        final Betrokkenheid betrokkenheid = verwijderdeBetrokkenheid.getBetrokkenheid();
        verwijderdeBetrokkenheid.removePersoon();
        persoon.removeBetrokkenheid(betrokkenheid);
    }

    /**
     * Geeft de relatie terug waarmee de ouders van de persoon aan zijn persoonslijst is gekoppeld. De relatie is aan de
     * persoon gekoppeld dmv een {$link Betrokkenheid}. Het gaat om een ouder als de soort van een {@link Betrokkenheid}
     * {@link SoortBetrokkenheid#KIND} .
     * @return de relatie waarmee de ouders aan de persoonlijst is gekoppeld of null als deze relatie niet gevonden wordt.
     */
    public Relatie getOuderRelatie() {
        Relatie resultaat = null;
        for (final BetrokkenheidDecorator betrokkenheidDecorator : getBetrokkenheidSet()) {
            if (SoortBetrokkenheid.KIND.equals(betrokkenheidDecorator.getSoortBetrokkenheid())) {
                resultaat = betrokkenheidDecorator.getRelatie().getRelatie();
            }
        }
        return resultaat;
    }

    /**
     * @return true als de persoon van het soort {@link SoortPersoon#INGESCHREVENE} is.
     */
    public boolean isIngeschrevene() {
        return persoon.getSoortPersoon().equals(SoortPersoon.INGESCHREVENE);
    }

    /**
     * Laat de persoon vervallen. De volgende groepen worden hierbij vervallen
     * <UL>
     * <LI>{@link PersoonGeboorteHistorie}</LI>
     * <LI>{@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie}</LI>
     * <LI>{@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie}</LI>
     * <LI>{@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie}</LI>
     * </UL>
     * @param actieVervalTbvLeveringMuts de actie verval tbv levering mutatie
     * @throws IllegalStateException als de controle of de persoon vervallen kan worden, niet is uitgevoerd.
     */
    public void laatVervallen(final BRPActie actieVervalTbvLeveringMuts) {
        if (!checkGedaanOpVerval) {
            throw new IllegalStateException("Controle of persoon kan worden vervallen niet uitgevoerd");
        }
        // Geboortestapel
        for (final PersoonGeboorteHistorie historie : persoon.getPersoonGeboorteHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }

        // samengestelde naam stapel
        for (final PersoonSamengesteldeNaamHistorie historie : persoon.getPersoonSamengesteldeNaamHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }

        // identificatienummer stapel
        for (final PersoonIDHistorie historie : persoon.getPersoonIDHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }

        // geslachtsaanduiding stapel
        for (final PersoonGeslachtsaanduidingHistorie historie : persoon.getPersoonGeslachtsaanduidingHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }
    }

    /**
     * Controleert of de persoon kan worden vervallen. Dit is het geval als de persoon niet het soort
     * {@link SoortPersoon#INGESCHREVENE} is.
     * @return true als de persoon kan worden vervallen
     */
    public boolean kanVervallen() {
        checkGedaanOpVerval = true;
        return !persoon.getSoortPersoon().equals(SoortPersoon.INGESCHREVENE);
    }

    /**
     * Sorteert de stapel op basis van het categorienummer van klein naar groot (dus 02, 03, 05, 09, 11). Als het
     * categorienummer gelijk is, dan wordt er verder gesorteerd op de volgnummer (0, 1, 2, etc).
     */
    private static class StapelSorter implements Comparator<StapelDecorator>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final StapelDecorator o1, final StapelDecorator o2) {
            final int result;
            final int stapelCat1 = o1.getActueelCategorienummer();
            final int stapelCat2 = o2.getActueelCategorienummer();

            if (stapelCat1 == stapelCat2) {
                result = o1.getStapelNummer() - o2.getStapelNummer();
            } else {
                result = stapelCat1 - stapelCat2;
            }
            return result;
        }
    }
}
