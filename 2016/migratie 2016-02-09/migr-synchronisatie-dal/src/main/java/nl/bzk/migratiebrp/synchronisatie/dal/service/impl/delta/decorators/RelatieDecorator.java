/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingOuder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RelatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Decorator voor {@link Relatie} met daarin logica die niet in de entiteit zit.
 */
public final class RelatieDecorator extends AbstractDecorator {

    private final Relatie relatie;

    /**
     * Maakt een RelatieDecorator object.
     *
     * @param relatie
     *            het object waaraan functionaliteit moet worden toegevoegd
     */
    private RelatieDecorator(final Relatie relatie) {
        this.relatie = PersistenceUtil.getPojoFromObject(relatie);
    }

    /**
     * @param relatie
     *            het te decoreren Relatie object
     * @return een RelatieDecorator object
     */
    public static RelatieDecorator decorate(final Relatie relatie) {
        final RelatieDecorator result;
        if (relatie == null) {
            result = null;
        } else {
            result = new RelatieDecorator(relatie);
        }
        return result;
    }

    /**
     * Geeft een instantie van {@link RelatieDecoratorSorter} terug waarmee een set van {@link RelatieDecorator}
     * gesorteerd kan worden.
     *
     * @return een nieuwe instantie van een Comparator specifiek voor {@link RelatieDecorator}
     */
    public static Comparator<RelatieDecorator> getSorteerder() {
        return new RelatieDecoratorSorter();
    }

    /**
     * @return De relatie entiteit.
     */
    public Relatie getRelatie() {
        return relatie;
    }

    /**
     * Geeft de betrokkenheden van deze relatie terug als een set van {@link BetrokkenheidDecorator}.
     *
     * @return de betrokkenheden van deze relatie
     */
    public Set<BetrokkenheidDecorator> getBetrokkenheden() {
        final Set<BetrokkenheidDecorator> betrokkenheden = new HashSet<>();
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            betrokkenheden.add(BetrokkenheidDecorator.decorate(betrokkenheid));
        }
        return betrokkenheden;
    }

    /**
     * Verwijdert de meegegeven IST-stapel bij de relatie.
     *
     * @param stapel
     *            de IST-stapel die verwijderd moet worden
     */
    public void removeStapel(final StapelDecorator stapel) {
        relatie.removeStapel(stapel.getStapel());
    }

    /**
     * Voegt een meegegeven IST-stapel toe aan de relatie.
     *
     * @param stapel
     *            de IST-stapel die toegevoegd gaat worden.
     */
    public void addStapel(final StapelDecorator stapel) {
        relatie.addStapel(stapel.getStapel());
    }

    /**
     * Controleert of de zoekBetrokkenheid voorkomt in de set van betrokkenheden van deze relatie.
     *
     * @param zoekBetrokkenheid
     *            de betrokkenheid die gezocht wordt
     * @return true als de betrokkenheid voorkomt
     */
    public boolean bevatBetrokkenheid(final BetrokkenheidDecorator zoekBetrokkenheid) {
        boolean result = false;
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            if (betrokkenheid.equals(zoekBetrokkenheid.getBetrokkenheid())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Geeft de andere betrokkenheid terug als aan onderstaande condities wordt voldaan.
     * <UL>
     * <LI>de relatie van het type {@link SoortRelatie#FAMILIERECHTELIJKE_BETREKKING} en de ik-betrokkenheid het type
     * {@link SoortBetrokkenheid#OUDER} is</LI>
     * <LI>de relatie van het type {@link SoortRelatie#HUWELIJK} of {@link SoortRelatie#GEREGISTREERD_PARTNERSCHAP} is</LI>
     * </UL>
     *
     * @param ikBetrokkenheidDecorator
     *            de betrokkenheid van de persoonslijst die door deltabepaling wordt beschouwd.
     * @return de andere betrokkenheid in deze relatie
     * @throws IllegalStateException
     *             als er aan bovenstaande condities niet voldaan wordt
     */
    public BetrokkenheidDecorator getAndereBetrokkenheid(final BetrokkenheidDecorator ikBetrokkenheidDecorator) {
        final SoortRelatie soortRelatie = relatie.getSoortRelatie();

        if (SoortRelatie.GEREGISTREERD_PARTNERSCHAP.equals(soortRelatie)
            || SoortRelatie.HUWELIJK.equals(soortRelatie)
            || SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.equals(soortRelatie)
            && SoortBetrokkenheid.OUDER.equals(ikBetrokkenheidDecorator.getSoortBetrokkenheid()))
        {
            BetrokkenheidDecorator result = null;
            for (final Betrokkenheid relatieBetrokkenheid : relatie.getBetrokkenheidSet()) {
                if (!matchedIkBetrokkenheid(ikBetrokkenheidDecorator, relatieBetrokkenheid)) {
                    result = BetrokkenheidDecorator.decorate(relatieBetrokkenheid);
                    break;
                }
            }

            return result;
        } else {
            throw new IllegalStateException("Er zijn meer dan 1 andere betrokkenheden in deze relatie.");
        }
    }

    private boolean matchedIkBetrokkenheid(final BetrokkenheidDecorator ikBetrokkenheidDecorator, final Betrokkenheid andereBetrokkenheid) {
        final Long ikAnummer = ikBetrokkenheidDecorator.getPersoonDecorator().getPersoon().getAdministratienummer();
        final Long administratienummer;
        if (andereBetrokkenheid.getPersoon() != null) {
            administratienummer = andereBetrokkenheid.getPersoon().getAdministratienummer();
        } else {
            administratienummer = null;
        }
        return ikAnummer.equals(administratienummer);
    }

    /**
     * Haalt de andere betrokkenheden bij een relatie op.
     * 
     * @param ikBetrokkenheid
     *            de betrokkenheid van de persoonslijst die door deltabepaling wordt beschouwd.
     * @param aanduidingOuder
     *            de aanduiding ouder waar de betrokkenheid toe behoort.
     * @return de andere betrokkenheden in deze relatie
     */
    public Set<BetrokkenheidDecorator> getAndereOuderBetrokkenheden(final BetrokkenheidDecorator ikBetrokkenheid, final AanduidingOuder aanduidingOuder) {
        final Set<BetrokkenheidDecorator> betrokkenheden = new HashSet<>();
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {

            if (!matchedIkBetrokkenheid(ikBetrokkenheid, betrokkenheid)) {
                if (betrokkenheid.getAanduidingOuder() == null) {
                    throw new IllegalStateException("Ouder betrokkenheid moet een aanduiding ouder bevatten");
                }

                final AanduidingOuder betrokkenheidOuderAanduiding = betrokkenheid.getAanduidingOuder().getOuderAanduiding();
                if (betrokkenheidOuderAanduiding.equals(aanduidingOuder)) {
                    betrokkenheden.add(BetrokkenheidDecorator.decorate(betrokkenheid));
                }
            }
        }
        return betrokkenheden;
    }

    /**
     * Geeft de betrokkenheid terug waarmee de persoon aan deze relatie is gekoppeld.
     *
     * @param persoonDecorator
     *            de persoon die in deze betrokkenheid mogelijk is gekoppeld
     * @return de betrokkenheid voor de opgegeven persoon, of null als deze niet is gevonden.
     */
    public BetrokkenheidDecorator getBetrokkenheid(final PersoonDecorator persoonDecorator) {
        BetrokkenheidDecorator result = null;
        for (final BetrokkenheidDecorator betrokkenheid : getBetrokkenheden()) {
            final PersoonDecorator persoon = betrokkenheid.getPersoonDecorator();
            if (persoon != null && persoon.isSameAs(persoonDecorator)) {
                result = betrokkenheid;
                break;
            }
        }
        return result;
    }

    private boolean isOntbonden() {
        return relatie.getDatumEinde() != null && relatie.getRedenBeeindigingRelatie() != null;
    }

    private boolean isHuwelijkOfGp() {
        final boolean result;
        switch (relatie.getSoortRelatie()) {
            case HUWELIJK:
            case GEREGISTREERD_PARTNERSCHAP:
                result = true;
                break;
            default:
                result = false;
        }
        return result;
    }

    /**
     * Kijkt of er een match is tussen deze relatie en de relatie waarmee gezocht wordt. De match wordt gedaan op de
     * sluitingsgegevens en, mits ingevuld in de zoekRelatie, de ontbindinggegevens.
     *
     * @param zoekRelatieDecorator
     *            de relatie waarmee een match gezocht wordt.
     * @return true als deze relatie matched met de zoekRelatie.
     */
    public boolean matches(final RelatieDecorator zoekRelatieDecorator) {
        return matches(zoekRelatieDecorator, false);
    }

    /**
     * Kijkt of er een match is tussen deze relatie en de relatie waarmee gezocht wordt. De match wordt gedaan op de
     * sluitingsgegevens en, mits ingevuld in de zoekRelatie of als de parameter exactMatch is gevuld, de
     * ontbindinggegevens.
     *
     * @param zoekRelatieDecorator
     *            de relatie waarmee een match gezocht wordt.
     * @param exactMatch
     *            true als ook altijd de ontbindingsgegevens gecontroleerd moeten worden
     * @return true als deze relatie matched met de zoekRelatie.
     */
    public boolean matches(final RelatieDecorator zoekRelatieDecorator, final boolean exactMatch) {
        final Relatie zoekRelatie = zoekRelatieDecorator.relatie;

        final String soortRelatie = getSoortRelatieWaarde(relatie.getSoortRelatie());
        final String andereSoortRelatie = getSoortRelatieWaarde(zoekRelatie.getSoortRelatie());
        final Short gemeenteAanvang = getGemeenteWaarde(relatie.getGemeenteAanvang());
        final Short andereGemeenteAanvang = getGemeenteWaarde(zoekRelatie.getGemeenteAanvang());

        final Short landOfGebiedAanvang = getLandOfGebiedWaarde(relatie.getLandOfGebiedAanvang());
        final Short andereLandOfGebiedAanvang = getLandOfGebiedWaarde(zoekRelatie.getLandOfGebiedAanvang());
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(gemeenteAanvang, andereGemeenteAanvang)
                     .append(landOfGebiedAanvang, andereLandOfGebiedAanvang)
                     .append(relatie.getBuitenlandsePlaatsAanvang(), zoekRelatie.getBuitenlandsePlaatsAanvang())
                     .append(relatie.getBuitenlandseRegioAanvang(), zoekRelatie.getBuitenlandseRegioAanvang())
                     .append(relatie.getDatumAanvang(), zoekRelatie.getDatumAanvang())
                     .append(relatie.getOmschrijvingLocatieAanvang(), zoekRelatie.getOmschrijvingLocatieAanvang())
                     .append(relatie.getWoonplaatsnaamAanvang(), zoekRelatie.getWoonplaatsnaamAanvang())
                     .append(soortRelatie, andereSoortRelatie);

        if (equalsBuilder.isEquals() && (exactMatch || isOntbonden())) {
            final Short gemeenteEinde = getGemeenteWaarde(relatie.getGemeenteEinde());
            final Short andereGemeenteEinde = getGemeenteWaarde(zoekRelatie.getGemeenteEinde());
            final Short landOfGebiedEinde = getLandOfGebiedWaarde(relatie.getLandOfGebiedEinde());
            final Short andereLandOfGebiedEinde = getLandOfGebiedWaarde(zoekRelatie.getLandOfGebiedEinde());
            final Character redenBeeindigingRelatie = getRedenEindeWaarde(relatie.getRedenBeeindigingRelatie());
            final Character andereRedenBeeindigingRelatie = getRedenEindeWaarde(zoekRelatie.getRedenBeeindigingRelatie());

            equalsBuilder.append(gemeenteEinde, andereGemeenteEinde)
                         .append(landOfGebiedEinde, andereLandOfGebiedEinde)
                         .append(redenBeeindigingRelatie, andereRedenBeeindigingRelatie)
                         .append(relatie.getBuitenlandsePlaatsEinde(), zoekRelatie.getBuitenlandsePlaatsEinde())
                         .append(relatie.getBuitenlandseRegioEinde(), zoekRelatie.getBuitenlandseRegioEinde())
                         .append(relatie.getDatumEinde(), zoekRelatie.getDatumEinde())
                         .append(relatie.getOmschrijvingLocatieEinde(), zoekRelatie.getOmschrijvingLocatieEinde())
                         .append(relatie.getWoonplaatsnaamEinde(), zoekRelatie.getWoonplaatsnaamEinde());

        }
        return equalsBuilder.isEquals();
    }

    /**
     * Zoekt in de meegegeven set van relaties een matchende relatie.
     * 
     * @param relaties
     *            de set van relaties waar een mogelijk match in zit.
     * @return de relatie waar een match in zit
     */
    public RelatieDecorator zoekMatchendeRelatie(final List<RelatieDecorator> relaties) {
        RelatieDecorator result = null;
        for (final RelatieDecorator mogelijkeMatchendeRelatie : relaties) {
            if (this.matches(mogelijkeMatchendeRelatie)) {
                if (result != null) {
                    throw new IllegalStateException("Meer relaties matchen voor de gezochte relatie");
                }
                result = mogelijkeMatchendeRelatie;
            }
        }

        return result;
    }

    /**
     * Laat de relatie en de betrokkenheden en personen die daar achter 'hangen' vervallen. Met uitzondering van de
     * persoon waar de deltabepaling voor bezig is.
     * 
     * @param actieVervalTbvLeveringMuts
     *            de actie die gebruikt wordt voor de levering mutatie
     * @param bestaandePersoon
     *            de persoon waar deltabepaling voor bezig is
     * @param aanduidingOuder
     *            de aanduiding voor welke ouder de relatie gaat vervallen. Null als het geen ouder relatie betreft.
     */
    public void laatVervallen(final BRPActie actieVervalTbvLeveringMuts, final PersoonDecorator bestaandePersoon, final AanduidingOuder aanduidingOuder) {
        final BetrokkenheidDecorator ikBetrokkenheid = getBetrokkenheid(bestaandePersoon);
        if (aanduidingOuder == null) {
            for (final RelatieHistorie historie : relatie.getRelatieHistorieSet()) {
                historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
            }

            ikBetrokkenheid.laatVervallen(actieVervalTbvLeveringMuts, bestaandePersoon);
        }

        for (final Betrokkenheid relatieBetrokkenheid : relatie.getBetrokkenheidSet()) {
            if (!matchedIkBetrokkenheid(ikBetrokkenheid, relatieBetrokkenheid)
                && (aanduidingOuder == null || aanduidingOuder.equals(relatieBetrokkenheid.getAanduidingOuder().getOuderAanduiding())))
            {
                BetrokkenheidDecorator.decorate(relatieBetrokkenheid).laatVervallen(actieVervalTbvLeveringMuts, bestaandePersoon);
            }
        }
    }

    /**
     * Sorteert de RelatieDecorator objecten op basis van datum aanvang.
     */
    private static class RelatieDecoratorSorter implements Comparator<RelatieDecorator>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final RelatieDecorator o1, final RelatieDecorator o2) {
            final Relatie relatie1 = o1.getRelatie();
            final Relatie relatie2 = o2.getRelatie();

            final int result;
            if (o1.isHuwelijkOfGp() && o2.isHuwelijkOfGp()) {
                final Integer datumAanvang1 = relatie1.getDatumAanvang();
                final Integer datumAanvang2 = relatie2.getDatumAanvang();
                final Integer datumEinde1 = relatie1.getDatumEinde();
                final Integer datumEinde2 = relatie2.getDatumEinde();

                // We sorteren van de meest recente naar de oudste tussen de relaties.
                // Als datumEinde is ingevuld van relatieA en datumEinde van relatieB niet, dan wordt relatieA boven
                // relatieB verwacht. Geldt ook andersom.
                // Als van beide relaties de einddatum wel of niet is gevuld, dan wordt er gekeken naar de datumAanvang.

                if (datumEinde1 == null && datumEinde2 == null) {
                    // Beide relaties zijn niet beeindigd.
                    result = datumAanvang1 - datumAanvang2;
                } else if (datumEinde1 == null) {
                    // datumEinde2 is gevuld
                    result = -1;
                } else if (datumEinde2 == null) {
                    // datumEinde1 is gevuld
                    result = 1;
                } else {
                    // Beide einddatums zijn ingevuld
                    if (datumEinde1.equals(datumEinde2)) {
                        // Einddatum zijn gelijk, kijken naar datumAanvang
                        // Als beide datumAanvang gelijk zijn, dan kijken naar reden beeindiging relatie
                        if (datumAanvang1.equals(datumAanvang2)) {
                            result = compareRedenBeeindigingRelatie(relatie1, relatie2);
                        } else {
                            result = datumAanvang1 - datumAanvang2;
                        }
                    } else {
                        result = datumEinde1 - datumEinde2;
                    }
                }

            } else {
                result = 1;
            }
            return result;
        }

        private int compareRedenBeeindigingRelatie(final Relatie relatie1, final Relatie relatie2) {
            // Als beide relaties geen reden beeindiging relatie heeft, dan zijn ze gelijk.
            final int result;
            final RedenBeeindigingRelatie redenBeeindigingRelatie1 = relatie1.getRedenBeeindigingRelatie();
            final RedenBeeindigingRelatie redenBeeindigingRelatie2 = relatie2.getRedenBeeindigingRelatie();

            if (redenBeeindigingRelatie1 == null && redenBeeindigingRelatie2 == null) {
                result = 0;
            } else if (redenBeeindigingRelatie1 == null) {
                result = -1;
            } else if (redenBeeindigingRelatie2 == null) {
                result = 1;
            } else {
                result = redenBeeindigingRelatie1.getCode() - redenBeeindigingRelatie2.getCode();
            }
            return result;
        }
    }
}
