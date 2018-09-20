/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.UniqueSequence;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert de BrpRelatie. Deze relatie heeft associaties met een huwelijk en een of meerdere
 * betrokkenheid stapels. Deze associaties reflecteren de relaties in de BRP A-laag (zonder historie).
 *
 */
public final class BrpRelatie {

    private final BrpSoortRelatieCode soortRelatieCode;
    private final BrpSoortBetrokkenheidCode rolCode;
    private final BrpBetrokkenheid ikBetrokkenheid;
    private final List<BrpBetrokkenheid> betrokkenheden;
    private final BrpStapel<BrpRelatieInhoud> relatieStapel;
    private final BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
    private final BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
    private final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGpStapel;
    private final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel;
    private final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel;

    /**
     * Maakt een BrpRelatie object.
     *
     * @param soortRelatieCode
     *            de soort relatie code, mag niet null zijn
     * @param rolCode
     *            de IK-betrokkenheid rol, mag niet null zijn
     * @param ikBetrokkenheid
     *            de ik betrokkenheid van deze relatie
     * @param betrokkenheden
     *            de betrokkenheden, mag niet null zijn en moet minimaal 1 betrokkenheid bevatten
     * @param relatieStapel
     *            stapel de stapel met inhoudelijk BRP relatie groepen. Deze mag null zijn.
     * @param istOuder1Stapel
     *            stapel met IST gegevens voor de relatie voor ouder1
     * @param istOuder2Stapel
     *            stapel met IST gegevens voor de relatie voor ouder2
     * @param istHuwelijkOfGpStapel
     *            stapel met IST gegevens voor de relatie voor huwelijk/gp
     * @param istKindStapel
     *            stapel met IST gegevens voor de relatie voor kind
     * @param istGezagsverhoudingStapel
     *            stapel met IST gegevens voor de relatie voor gezagsverhouding
     * @throws IllegalArgumentException
     *             als groepen een lege lijst is of als betrokkenheidStapels minder dan 1 of meer dan 4 stapels bevat
     * @throws NullPointerException
     *             als soortRelatieCode, ikBetrokkenheidStapel, betrokkenheidStapels of groepen null is
     */
    public BrpRelatie(
        @Element(name = "soortRelatieCode") final BrpSoortRelatieCode soortRelatieCode,
        @Element(name = "rolCode") final BrpSoortBetrokkenheidCode rolCode,
        @Element(name = "ikBetrokkenheid") final BrpBetrokkenheid ikBetrokkenheid,
        @ElementList(name = "betrokkenheden", entry = "betrokkenheid", type = BrpBetrokkenheid.class,
                required = false) final List<BrpBetrokkenheid> betrokkenheden,
        @Element(name = "relatieStapel", required = false) final BrpStapel<BrpRelatieInhoud> relatieStapel,
        @Element(name = "istOuder1Stapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel,
        @Element(name = "istOuder2Stapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel,
        @Element(name = "istHuwelijkOfGpStapel", required = false) final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGpStapel,
        @Element(name = "istKindStapel", required = false) final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel,
        @Element(name = "istGezagsverhoudingStapel", required = false) final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel)
    {
        if (soortRelatieCode == null) {
            throw new NullPointerException("soortRelatieCode mag niet null zijn");
        }
        if (rolCode == null) {
            throw new NullPointerException("rolCode mag niet null zijn");
        }
        if (ikBetrokkenheid == null) {
            throw new NullPointerException("ikBetrokkenheid mag niet null zijn");
        }

        this.soortRelatieCode = soortRelatieCode;
        this.rolCode = rolCode;
        this.ikBetrokkenheid = ikBetrokkenheid;
        this.betrokkenheden = new ArrayList<>(betrokkenheden);
        this.relatieStapel = relatieStapel;
        this.istOuder1Stapel = istOuder1Stapel;
        this.istOuder2Stapel = istOuder2Stapel;
        this.istHuwelijkOfGpStapel = istHuwelijkOfGpStapel;
        this.istKindStapel = istKindStapel;
        this.istGezagsverhoudingStapel = istGezagsverhoudingStapel;
    }

    private BrpRelatie(final Builder builder) {
        this(builder.soortRelatieCode,
             builder.rolCode,
             builder.ikBetrokkenheid,
             builder.betrokkenheden,
             builder.relatieStapel,
             builder.istOuder1Stapel,
             builder.istOuder2Stapel,
             builder.istHuwelijkOfGpStapel,
             builder.istKindStapel,
             builder.istGezagsverhoudingStapel);
    }

    /**
     * Valideer alle relaties in de relatiestapel.
     */
    public void valideer() {
        if (relatieStapel != null) {
            for (final BrpGroep<BrpRelatieInhoud> relatie : relatieStapel) {
                relatie.getInhoud().valideer();
            }
        }
    }

    /**
     * Geef de waarde van soort relatie code.
     *
     * @return the soortRelatieCode
     */
    @Element(name = "soortRelatieCode")
    public BrpSoortRelatieCode getSoortRelatieCode() {
        return soortRelatieCode;
    }

    /**
     * Geef de waarde van rol code.
     *
     * @return the rolCode
     */
    @Element(name = "rolCode")
    public BrpSoortBetrokkenheidCode getRolCode() {
        return rolCode;
    }

    /**
     * Geef de waarde van ikBetrokkenheid.
     *
     * @return the ikBetrokkenheid
     */
    @Element(name = "ikBetrokkenheid")
    public BrpBetrokkenheid getIkBetrokkenheid() {
        return ikBetrokkenheid;
    }

    /**
     * Geef de waarde van betrokkenheden.
     *
     * @return the betrokkenheden
     */
    @ElementList(name = "betrokkenheden", entry = "betrokkenheid", type = BrpBetrokkenheid.class, required = false)
    public List<BrpBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Geef de waarde van relatie stapel.
     *
     * @return the relatieStapel
     */
    @Element(name = "relatieStapel", required = false)
    public BrpStapel<BrpRelatieInhoud> getRelatieStapel() {
        return relatieStapel;
    }

    /**
     * Geef de waarde van ist ouder1 stapel.
     *
     * @return ist ouder1 stapel
     */
    @Element(name = "istOuder1Stapel", required = false)
    public BrpStapel<BrpIstRelatieGroepInhoud> getIstOuder1Stapel() {
        return istOuder1Stapel;
    }

    /**
     * Geef de waarde van ist ouder2 stapel.
     *
     * @return ist ouder2 stapel
     */
    @Element(name = "istOuder2Stapel", required = false)
    public BrpStapel<BrpIstRelatieGroepInhoud> getIstOuder2Stapel() {
        return istOuder2Stapel;
    }

    /**
     * Geef de waarde van ist huwelijk of gp stapel.
     *
     * @return ist huwelijk of gp stapel
     */
    @Element(name = "istHuwelijkOfGpStapel", required = false)
    public BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> getIstHuwelijkOfGpStapel() {
        return istHuwelijkOfGpStapel;
    }

    /**
     * Geef de waarde van ist kind stapel.
     *
     * @return ist kind stapel
     */
    @Element(name = "istKindStapel", required = false)
    public BrpStapel<BrpIstRelatieGroepInhoud> getIstKindStapel() {
        return istKindStapel;
    }

    /**
     * Geef de waarde van ist gezagsverhouding stapel.
     *
     * @return ist gezagsverhouding stapel
     */
    @Element(name = "istGezagsverhoudingStapel", required = false)
    public BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> getIstGezagsverhoudingStapel() {
        return istGezagsverhoudingStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpRelatie)) {
            return false;
        }
        final BrpRelatie castOther = (BrpRelatie) other;
        return new EqualsBuilder().append(soortRelatieCode, castOther.soortRelatieCode)
                                  .append(rolCode, castOther.rolCode)
                                  .append(betrokkenheden, castOther.betrokkenheden)
                                  .append(relatieStapel, castOther.relatieStapel)
                                  .append(istOuder1Stapel, castOther.istOuder1Stapel)
                                  .append(istOuder2Stapel, castOther.istOuder2Stapel)
                                  .append(istHuwelijkOfGpStapel, castOther.istHuwelijkOfGpStapel)
                                  .append(istKindStapel, castOther.istKindStapel)
                                  .append(istGezagsverhoudingStapel, castOther.istGezagsverhoudingStapel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortRelatieCode)
                                    .append(rolCode)
                                    .append(betrokkenheden)
                                    .append(relatieStapel)
                                    .append(istOuder1Stapel)
                                    .append(istOuder2Stapel)
                                    .append(istHuwelijkOfGpStapel)
                                    .append(istKindStapel)
                                    .append(istGezagsverhoudingStapel)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("soortRelatieCode", soortRelatieCode)
                                                                          .append("rolCode", rolCode)
                                                                          .append("betrokkenheden", betrokkenheden)
                                                                          .append("relatieStapel", relatieStapel)
                                                                          .append("istOuder1Stapel", istOuder1Stapel)
                                                                          .append("istOuder2Stapel", istOuder2Stapel)
                                                                          .append("istHuwelijkOfGpStapels", istHuwelijkOfGpStapel)
                                                                          .append("istKindStapels", istKindStapel)
                                                                          .append("istGezagsverhoudingStapel", istGezagsverhoudingStapel)
                                                                          .toString();
    }

    /**
     * Buider class voor BrpRelatie.
     */
    public static class Builder {
        private final BrpSoortRelatieCode soortRelatieCode;
        private final BrpSoortBetrokkenheidCode rolCode;
        private final Map<Long, BrpActie> actieCache;
        private BrpBetrokkenheid ikBetrokkenheid;
        private List<BrpBetrokkenheid> betrokkenheden = new ArrayList<>();
        private BrpStapel<BrpRelatieInhoud> relatieStapel;
        private BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
        private BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
        private BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGpStapel;
        private BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel;
        private BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel;

        /**
         * Constructor voor Builder met verplichte velden soortRelatieCode en rolCode.
         *
         * @param soortRelatieCode
         *            soort relatie code
         * @param rolCode
         *            rol code
         * @param actieCache
         *            cache waar actie en actieId opgeslagen wordt
         */
        public Builder(final BrpSoortRelatieCode soortRelatieCode, final BrpSoortBetrokkenheidCode rolCode, final Map<Long, BrpActie> actieCache) {
            this.soortRelatieCode = soortRelatieCode;
            this.rolCode = rolCode;
            this.actieCache = actieCache;
        }

        /**
         * Maakt en vul een Builder op basis van een bestaande BrpRelatie.
         *
         * @param relatie
         *            BrpRelatie die gebruikt wordt om de waarden van de Builder te vullen
         * @param actieCache
         *            cache waar actie en actieId opgeslagen wordt
         */
        public Builder(final BrpRelatie relatie, final Map<Long, BrpActie> actieCache) {
            soortRelatieCode = relatie.getSoortRelatieCode();
            rolCode = relatie.getRolCode();
            ikBetrokkenheid = relatie.getIkBetrokkenheid();
            betrokkenheden = new ArrayList<>(relatie.getBetrokkenheden());
            relatieStapel = relatie.getRelatieStapel();
            istOuder1Stapel = relatie.getIstOuder1Stapel();
            istOuder2Stapel = relatie.getIstOuder2Stapel();
            istHuwelijkOfGpStapel = relatie.getIstHuwelijkOfGpStapel();
            istKindStapel = relatie.getIstKindStapel();
            istGezagsverhoudingStapel = relatie.getIstGezagsverhoudingStapel();
            this.actieCache = actieCache;
        }

        /**
         * zet de betrokkenheden.
         *
         * @param param
         *            lijst van betrokkenheden de relatie
         * @return de builder
         */
        public final Builder betrokkenheden(final List<BrpBetrokkenheid> param) {
            betrokkenheden = param;
            return this;
        }

        /**
         * zet de relatiestapel.
         *
         * @param param
         *            details van deze relatie
         * @return de builder
         */
        public final Builder relatieStapel(final BrpStapel<BrpRelatieInhoud> param) {
            relatieStapel = param;
            return this;
        }

        /**
         * zet de IST-gegevens van ouder1.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor ouder1
         * @return de builder
         */
        public final Builder istOuder1Stapel(final BrpStapel<BrpIstRelatieGroepInhoud> param) {
            istOuder1Stapel = param;
            return this;
        }

        /**
         * zet de IST-gegevens van ouder2.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor ouder2
         * @return de builder
         */
        public final Builder istOuder2Stapel(final BrpStapel<BrpIstRelatieGroepInhoud> param) {
            istOuder2Stapel = param;
            return this;
        }

        /**
         * zet de IST-gegevens van huwelijk/gp.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor huwelijk/gp
         * @return de builder
         */
        public final Builder istHuwelijkOfGpStapel(final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> param) {
            istHuwelijkOfGpStapel = param;
            return this;
        }

        /**
         * zet de IST-gegevens van kind.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor kind
         * @return de builder
         */
        public final Builder istKindStapel(final BrpStapel<BrpIstRelatieGroepInhoud> param) {
            istKindStapel = param;
            return this;
        }

        /**
         * zet de IST-gegevens van gezagsverhouding.
         *
         * @param param
         *            stapel met IST-gegevens van deze relatie voor gezagsverhouding
         * @return de builder
         */
        public final Builder istGezagsverhoudingStapel(final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> param) {
            istGezagsverhoudingStapel = param;
            return this;
        }

        /**
         * Maakt een nieuwe {@link BrpRelatie} object aan met de opgegeven gegevens.
         *
         * @return een nieuwe {@link BrpRelatie}
         */
        public final BrpRelatie build() {
            if (relatieStapel == null || ikBetrokkenheid == null) {
                werkIdentiteitStapelsBijVoorBetrokkenhedenEnRelatie();
            }
            return new BrpRelatie(this);
        }

        /**
         * Vanuit de Sortering werd in eerste instantie de build aangeroepen. Dit veroorzaakte echter ook een mismatch
         * in de timestamp gezet bij het bijwerken van de identiteit.
         *
         * @return een nieuwe {@link BrpRelatie}
         */
        public final BrpRelatie buildZonderBijwerkenIdentiteit() {
            return new BrpRelatie(this);
        }

        private void werkIdentiteitStapelsBijVoorBetrokkenhedenEnRelatie() {
            final BrpStapel<BrpIdentiteitInhoud> identiteitStapel;
            if (BrpSoortBetrokkenheidCode.PARTNER.getWaarde().equals(rolCode.getWaarde())) {
                identiteitStapel =
                        new BrpStapel<>(Collections.singletonList(maakIdentiteitVoorkomenKopie(getOudsteVoorkomen(relatieStapel.getGroepen()))));
                betrokkenheden = voegIdentiteitToeAanBetrokkenheden(betrokkenheden, identiteitStapel);
            } else if (BrpSoortBetrokkenheidCode.OUDER.getWaarde().equals(rolCode.getWaarde())
                       || BrpSoortBetrokkenheidCode.KIND.getWaarde().equals(rolCode.getWaarde()))
            {
                if (betrokkenheden.isEmpty()) {
                    identiteitStapel = new BrpStapel<>(Collections.singletonList(maakIdentiteitVoorOuder()));
                } else {
                    betrokkenheden = bepaalIdentiteitVoorOuderBetrokkenheden(betrokkenheden);
                    identiteitStapel = bepaaldOudsteIdentiteitStapel(betrokkenheden);
                }
            } else {
                throw new IllegalStateException("Identiteitstapel kan niet worden bepaald voor onbekende rol: " + rolCode);
            }

            if ((relatieStapel == null || relatieStapel.isEmpty()) && identiteitStapel != null && !identiteitStapel.isEmpty()) {
                final BrpGroep<BrpIdentiteitInhoud> identiteitGroep = identiteitStapel.get(0);
                final BrpRelatieInhoud relatieInhoud = new BrpRelatieInhoud();
                relatieStapel = new BrpStapel<>(Collections.singletonList(maakVoorkomenKopieZonderVerval(identiteitGroep, relatieInhoud)));
            }

            if (ikBetrokkenheid == null) {
                ikBetrokkenheid = new BrpBetrokkenheid(rolCode, null, null, null, null, null, null, identiteitStapel);
            }
        }

        private BrpStapel<BrpIdentiteitInhoud> bepaaldOudsteIdentiteitStapel(final List<BrpBetrokkenheid> bronBetrokkenheden) {
            final List<BrpGroep<BrpIdentiteitInhoud>> identiteitGroepen = new ArrayList<>();
            for (final BrpBetrokkenheid bronBetrokkenheid : bronBetrokkenheden) {
                identiteitGroepen.addAll(bronBetrokkenheid.getIdentiteitStapel().getGroepen());
            }
            final BrpGroep<BrpIdentiteitInhoud> oudsteVoorkomen = getOudsteVoorkomen(identiteitGroepen);
            final BrpStapel<BrpIdentiteitInhoud> result;
            if (oudsteVoorkomen != null) {
                result = new BrpStapel<>(Collections.singletonList(oudsteVoorkomen));
            } else {
                result = null;
            }
            return result;
        }

        private List<BrpBetrokkenheid> bepaalIdentiteitVoorOuderBetrokkenheden(final List<BrpBetrokkenheid> doelBetrokkenheden) {
            final List<BrpBetrokkenheid> results = new ArrayList<>();
            for (final BrpBetrokkenheid doelBetrokkenheid : doelBetrokkenheden) {
                if (doelBetrokkenheid.getOuderStapel() != null) {
                    final BrpStapel<BrpIdentiteitInhoud> identiteitStapelVoorBetrokkenheid =
                            new BrpStapel<>(
                                Collections.singletonList(
                                    maakIdentiteitVoorkomenKopie(getOudsteVoorkomen(doelBetrokkenheid.getOuderStapel().getGroepen()))));
                    results.add(new BrpBetrokkenheid.Builder(doelBetrokkenheid).identiteitStapel(identiteitStapelVoorBetrokkenheid).build());
                }
            }
            return results;
        }

        private List<BrpBetrokkenheid> voegIdentiteitToeAanBetrokkenheden(
            final List<BrpBetrokkenheid> doelBetrokkenheden,
            final BrpStapel<BrpIdentiteitInhoud> toeTeVoegenIdentiteitStapel)
        {
            final List<BrpBetrokkenheid> results = new ArrayList<>();
            for (final BrpBetrokkenheid doelBetrokkenheid : doelBetrokkenheden) {
                results.add(new BrpBetrokkenheid.Builder(doelBetrokkenheid).identiteitStapel(toeTeVoegenIdentiteitStapel).build());
            }
            return results;
        }

        private <T extends BrpGroepInhoud> BrpGroep<BrpIdentiteitInhoud> maakIdentiteitVoorkomenKopie(final BrpGroep<T> voorkomen) {
            return maakVoorkomenKopieZonderVerval(voorkomen, BrpIdentiteitInhoud.IDENTITEIT);
        }

        private <T extends BrpGroepInhoud> BrpGroep<T> maakVoorkomenKopieZonderVerval(
            final BrpGroep<? extends BrpGroepInhoud> oudVoorkomen,
            final T nieuweInhoud)
        {
            final BrpHistorie.Builder historieBuilder = new BrpHistorie.Builder(oudVoorkomen.getHistorie());
            historieBuilder.setDatumTijdVerval(null);
            BrpActie nieuweActieGeldigheid = oudVoorkomen.getActieGeldigheid();
            historieBuilder.setDatumAanvangGeldigheid(null);
            historieBuilder.setDatumEindeGeldigheid(null);
            nieuweActieGeldigheid = null;
            return new BrpGroep<>(nieuweInhoud, historieBuilder.build(), oudVoorkomen.getActieInhoud(), null, nieuweActieGeldigheid);
        }

        private BrpGroep<BrpIdentiteitInhoud> maakIdentiteitVoorOuder() {
            final BrpDatumTijd nu = new BrpDatumTijd(new Date(System.currentTimeMillis()));
            final BrpHistorie.Builder historieBuilder = new BrpHistorie.Builder();
            historieBuilder.setDatumTijdRegistratie(nu);

            final BrpActie.Builder actieInhoudBuilder = new BrpActie.Builder();

            actieInhoudBuilder.id(UniqueSequence.next());
            actieInhoudBuilder.datumTijdRegistratie(nu);
            actieInhoudBuilder.partijCode(BrpPartijCode.MIGRATIEVOORZIENING);
            actieInhoudBuilder.soortActieCode(BrpSoortActieCode.CONVERSIE_GBA);

            final BrpActie actieInhoud = actieInhoudBuilder.build();
            actieCache.put(actieInhoud.getId(), actieInhoud);

            return new BrpGroep<>(BrpIdentiteitInhoud.IDENTITEIT, historieBuilder.build(), actieInhoud, null, null);
        }

        private <T extends BrpGroepInhoud> BrpGroep<T> getOudsteVoorkomen(final List<BrpGroep<T>> groepen) {
            BrpGroep<T> result = null;
            for (final BrpGroep<T> voorkomen : groepen) {
                // Als het een ouder relatie/betrokkenheid is, dan is volgorde op basis van categorie. Anders op
                // tsReg
                if (result == null) {
                    result = voorkomen;
                } else if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING.equals(soortRelatieCode)
                           && BrpSoortBetrokkenheidCode.KIND.equals(rolCode)
                           && result.getActieInhoud().getLo3Herkomst() != null
                           && voorkomen.getActieInhoud().getLo3Herkomst() != null)
                {
                    final Lo3CategorieEnum resultCategorie =
                            Lo3CategorieEnum.bepaalActueleCategorie(result.getActieInhoud().getLo3Herkomst().getCategorie());
                    final Lo3CategorieEnum voorkomenCategorie =
                            Lo3CategorieEnum.bepaalActueleCategorie(voorkomen.getActieInhoud().getLo3Herkomst().getCategorie());
                    if (resultCategorie.getCategorieAsInt() > voorkomenCategorie.getCategorieAsInt()) {
                        result = voorkomen;
                    }
                } else {
                    final BrpHistorie resultHistorie = result.getHistorie();
                    final BrpHistorie voorkomenHistorie = voorkomen.getHistorie();
                    final int tsRegCompareResult = resultHistorie.getDatumTijdRegistratie().compareTo(voorkomenHistorie.getDatumTijdRegistratie());
                    if (tsRegCompareResult > 0) {
                        result = voorkomen;
                    }
                }
            }
            return result;
        }
    }
}
