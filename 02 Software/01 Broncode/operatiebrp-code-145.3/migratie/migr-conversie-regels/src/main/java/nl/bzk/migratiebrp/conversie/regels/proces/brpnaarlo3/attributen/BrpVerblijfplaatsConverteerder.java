/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnverwerktDocumentAanwezigIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Verblijfplaats converteerder.
 */
@Requirement(Requirements.CCA08)
public final class BrpVerblijfplaatsConverteerder extends AbstractBrpCategorieConverteerder<Lo3VerblijfplaatsInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();
    private final BrpAttribuutConverteerder attribuutConverteerder;
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpVerblijfplaatsConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.attribuutConverteerder = attribuutConverteerder;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpCategorieConverteerder#getLogger()
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected <B extends BrpGroepInhoud> BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud> bepaalConverteerder(final B inhoud) {
        final BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud> result;

        if (inhoud instanceof BrpAdresInhoud) {
            result = (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) new AdresConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpBijhoudingInhoud) {
            result = (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) new BijhoudingConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpMigratieInhoud) {
            result = (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) new MigratieConverteerder(attribuutConverteerder);
        } else if (inhoud instanceof BrpOnverwerktDocumentAanwezigIndicatieInhoud) {
            return (BrpGroepConverteerder<B, Lo3VerblijfplaatsInhoud>) new OnverwerktDocumentAanwezigConverteerder(attribuutConverteerder);
        } else {
            throw new IllegalArgumentException("BrpVerblijfplaatsConverteerder bevat geen Groep converteerder voor: " + inhoud);
        }

        return result;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> getBrpGroepVoorVoorkomen(
            final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen,
            final BrpStapel<T> brpStapel) {
        BrpGroep<T> result = null;
        if (brpStapel != null) {
            final Long documentId = voorkomen.getDocumentatie().getId();
            for (final BrpGroep<T> brpGroep : brpStapel.getGroepen()) {
                if (documentId.equals(brpGroep.getActieInhoud().getId())) {
                    result = brpGroep;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Doet een nabewerking stap voor het vullen van datum velden in de inhoud vanuit de historie.
     * @param verblijfplaatsStapel verblijfplaatsStapel
     * @param brpMigratieStapel brp stapel voor Migratie
     * @param brpBijhoudingStapel brp stapel voor Bijhouding
     * @return verblijfplaatsStapel waarin de inhoud is aangevuld vanuit de historie.
     */
    public Lo3Stapel<Lo3VerblijfplaatsInhoud> nabewerking(
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel,
            final BrpStapel<? extends BrpGroepInhoud> brpMigratieStapel,
            final BrpStapel<? extends BrpGroepInhoud> brpBijhoudingStapel) {
        Lo3Stapel<Lo3VerblijfplaatsInhoud> result = null;
        if (verblijfplaatsStapel != null) {
            final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> lo3Categorieen = new ArrayList<>();

            for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen : verblijfplaatsStapel.getCategorieen()) {
                final BrpGroep<? extends BrpGroepInhoud> brpMigratieGroep = getBrpGroepVoorVoorkomen(voorkomen, brpMigratieStapel);
                final BrpGroep<? extends BrpGroepInhoud> brpBijhoudingGroep = getBrpGroepVoorVoorkomen(voorkomen, brpBijhoudingStapel);

                final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
                final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);

                final Lo3Datum datumInschrijving;
                final Lo3Datum datumMigratie;

                if (brpBijhoudingGroep != null) {
                    datumInschrijving = brpBijhoudingGroep.getHistorie().getDatumAanvangGeldigheid().converteerNaarLo3Datum();
                } else {
                    datumInschrijving = voorkomen.getHistorie().getIngangsdatumGeldigheid();
                }
                builder.datumInschrijving(datumInschrijving);

                if (brpMigratieGroep != null) {
                    datumMigratie = brpMigratieGroep.getHistorie().getDatumAanvangGeldigheid().converteerNaarLo3Datum();
                } else {
                    datumMigratie = voorkomen.getHistorie().getIngangsdatumGeldigheid();
                }

                Lo3Datum ingangsdatumGeldigheid = voorkomen.getHistorie().getIngangsdatumGeldigheid();

                if (lo3Inhoud.isEmigratie()) {
                    builder.vertrekUitNederland(datumMigratie);
                    ingangsdatumGeldigheid = datumMigratie.compareTo(datumInschrijving) > 0 ? datumMigratie : datumInschrijving;
                }

                if (lo3Inhoud.isImmigratie()) {
                    builder.vestigingInNederland(datumMigratie);
                }

                final Lo3Historie historie =
                        new Lo3Historie(voorkomen.getHistorie().getIndicatieOnjuist(), ingangsdatumGeldigheid, voorkomen.getHistorie().getDatumVanOpneming());

                lo3Categorieen.add(new Lo3Categorie<>(builder.build(), voorkomen.getDocumentatie(), historie, voorkomen.getLo3Herkomst()));
            }

            result = new Lo3Stapel<>(lo3Categorieen);
        }

        return result;
    }

    private static void verwerkBuitenlandsAdres(
            final Lo3VerblijfplaatsInhoud.Builder builder,
            final BrpAttribuutConverteerder converteerder,
            final BrpString buitenlandsAdresRegel1,
            final BrpString buitenlandsAdresRegel2,
            final BrpString buitenlandsAdresRegel3,
            final BrpLandOfGebiedCode landOfGebiedCode) {
        if (alleenBuitenlandsAdresRegel1(buitenlandsAdresRegel1, buitenlandsAdresRegel2, buitenlandsAdresRegel3)) {
            // DEF045
            builder.adresBuitenland1(null);
            builder.adresBuitenland2(converteerder.converteerString(buitenlandsAdresRegel1));
            builder.adresBuitenland3(null);

        } else {
            // DEF046
            builder.adresBuitenland1(converteerder.converteerString(buitenlandsAdresRegel1));
            builder.adresBuitenland2(converteerder.converteerString(buitenlandsAdresRegel2));
            builder.adresBuitenland3(converteerder.converteerString(buitenlandsAdresRegel3));
        }
        builder.landAdresBuitenland(converteerder.converteerLandCode(landOfGebiedCode));
    }

    private static boolean alleenBuitenlandsAdresRegel1(final BrpString brpRegel1, final BrpString brpRegel2, final BrpString brpRegel3) {
        final String regel1 = BrpString.unwrap(brpRegel1);
        final String regel2 = BrpString.unwrap(brpRegel2);
        final String regel3 = BrpString.unwrap(brpRegel3);

        final boolean regel1Aanwezig = regel1 != null && !regel1.isEmpty();
        final boolean regel2Aanwezig = regel2 != null && !regel2.isEmpty();
        final boolean regel3Aanwezig = regel3 != null && !regel3.isEmpty();
        return regel1Aanwezig && !regel2Aanwezig && !regel3Aanwezig;
    }

    private static boolean bepaalAanvullen(final BrpActie actie, final Lo3CategorieWrapper<Lo3VerblijfplaatsInhoud> categorieWrapper) {
        final BrpSoortActieCode soortActieCode = actie.getSoortActieCode();
        final boolean isConversie = BrpSoortActieCode.CONVERSIE_GBA.equals(soortActieCode)
                || BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST.equals(soortActieCode)
                || BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(soortActieCode);
        if (isConversie) {
            final Long documentatieId =
                    categorieWrapper.getLo3Categorie().getDocumentatie() == null ? null : categorieWrapper.getLo3Categorie().getDocumentatie().getId();
            return actie.getId().equals(documentatieId);
        }
        return false;
    }

    /**
     * Bepaal de te converteren Bijhouding groepen. Het kan zijn zo zijn dat de heenconversie een groep bijhouding is
     * geconverteerd uit categorie 7. Deze bijhouding, voortgekomen uit categorie 7, moeten we op de terugweg negeren.
     * @param bijhoudingStapel De bijhouding stapel
     * @return De bijhouding stapel zonder groepen voortgekomen uit categorie 7.
     */
    @Requirement(Requirements.CCA08_BL05)
    public BrpStapel<BrpBijhoudingInhoud> bepaalBijhoudingGroepen(final BrpStapel<BrpBijhoudingInhoud> bijhoudingStapel) {
        BrpStapel<BrpBijhoudingInhoud> result = null;
        if (bijhoudingStapel != null) {
            final List<BrpGroep<BrpBijhoudingInhoud>> gefilterdeBijhoudingGroepen = new ArrayList<>();
            for (final BrpGroep<BrpBijhoudingInhoud> bijhoudingGroep : bijhoudingStapel.getGroepen()) {
                final Lo3Herkomst lo3Herkomst = bijhoudingGroep.getActieInhoud().getLo3Herkomst();
                if (lo3Herkomst == null || !Lo3CategorieEnum.CATEGORIE_07.equals(lo3Herkomst.getCategorie())) {
                    gefilterdeBijhoudingGroepen.add(brpBijhoudingGroepFilterActieGeldigheid(bijhoudingGroep));
                }
            }
            result = new BrpStapel<>(gefilterdeBijhoudingGroepen);
        }
        return result;
    }

    private BrpGroep<BrpBijhoudingInhoud> brpBijhoudingGroepFilterActieGeldigheid(final BrpGroep<BrpBijhoudingInhoud> bijhoudingGroep) {
        final BrpGroep<BrpBijhoudingInhoud> result;
        if (bijhoudingGroep.getActieGeldigheid() == null
                || bijhoudingGroep.getActieGeldigheid().getLo3Herkomst() == null
                || !bijhoudingGroep.getActieGeldigheid().getLo3Herkomst().getCategorie().equals(Lo3CategorieEnum.CATEGORIE_07)) {
            result = bijhoudingGroep;
        } else {
            result =
                    new BrpGroep<>(
                            bijhoudingGroep.getInhoud(),
                            bijhoudingGroep.getHistorie(),
                            bijhoudingGroep.getActieInhoud(),
                            bijhoudingGroep.getActieVerval(),
                            null);
        }
        return result;
    }

    /**
     * Converteerder die weet hoe je een Lo3VerblijfplaatsInhoud rij moet aanmaken.
     * @param <T> BRP Inhoud
     */
    public abstract static class AbstractConverteerder<T extends BrpGroepInhoud> extends AbstractBrpGroepConverteerder<T, Lo3VerblijfplaatsInhoud> {
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public AbstractConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        public final Lo3VerblijfplaatsInhoud maakNieuweInhoud() {
            return new Lo3VerblijfplaatsInhoud(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Lo3AangifteAdreshoudingEnum.ONBEKEND.asElement(),
                    null);
        }
    }

    /**
     * Converteerder die weet hoe een BrpAdresInhoud omgezet moet worden naar Lo3VerblijfplaatsInhoud.
     */
    @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02, Requirements.CCA08_BL03})
    public static final class AdresConverteerder extends AbstractConverteerder<BrpAdresInhoud> {

        private static final Logger LOG = LoggerFactory.getLogger();
        private static final String PUNT = ".";
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public AdresConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Definitie({Definities.DEF023, Definities.DEF024, Definities.DEF045, Definities.DEF046})
        @Requirement({Requirements.CCA08_BL01, Requirements.CCA08_BL02, Requirements.CCA08_BL03})
        @Override
        public Lo3VerblijfplaatsInhoud vulInhoud(
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpAdresInhoud brpInhoud,
                final BrpAdresInhoud brpVorigeInhoud) {
            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);
            builder.resetAdresVelden();
            if (brpInhoud != null) {
                // CCA08_BL01 Adreshouding
                builder.aangifteAdreshouding(
                        getAttribuutConverteerder()
                                .converteerAangifteAdreshouding(brpInhoud.getRedenWijzigingAdresCode(), brpInhoud.getAangeverAdreshoudingCode()));

                // CCA08_BL02 Nederlands adres
                if (BrpValidatie.isAttribuutGevuld(brpInhoud.getGemeenteCode())) {
                    verwerkNederlandsAdres(builder, brpInhoud);
                } else {
                    // CCA08_BL03
                    verwerkBuitenlandsAdres(
                            builder,
                            getAttribuutConverteerder(),
                            brpInhoud.getBuitenlandsAdresRegel1(),
                            brpInhoud.getBuitenlandsAdresRegel2(),
                            brpInhoud.getBuitenlandsAdresRegel3(),
                            brpInhoud.getLandOfGebiedCode());
                    // Datum aanvang adres buitenland wordt gevuld in de nabewerking methode
                }
            }
            return builder.build();
        }

        private void verwerkNederlandsAdres(final Lo3VerblijfplaatsInhoud.Builder builder, final BrpAdresInhoud brpInhoud) {
            builder.aanvangAdreshouding(getAttribuutConverteerder().converteerDatum(brpInhoud.getDatumAanvangAdreshouding()));
            builder.functieAdres(getAttribuutConverteerder().converteerFunctieAdres(brpInhoud.getSoortAdresCode()));
            if (isAdresNietBekend(brpInhoud)) {
                // DEF023
                builder.straatnaam(Lo3String.wrap(PUNT));
            } else {
                // DEF024
                final Lo3String
                        identificatiecodeAdresseerbaarObject =
                        getAttribuutConverteerder().converteerString(brpInhoud.getIdentificatiecodeAdresseerbaarObject());
                final Lo3String
                        identificatiecodeNummeraanduiding =
                        getAttribuutConverteerder().converteerString(brpInhoud.getIdentificatiecodeNummeraanduiding());
                final Lo3String naamOpenbareRuimte = getAttribuutConverteerder().converteerString(brpInhoud.getNaamOpenbareRuimte());
                Lo3String woonplaatsnaam = getAttribuutConverteerder().converteerString(brpInhoud.getWoonplaatsnaam());

                final boolean woonplaatsnaamGevuld = woonplaatsnaam != null && woonplaatsnaam.isInhoudelijkGevuld();
                final boolean identificatiecodeAdresseerbaarObjectGevuld =
                        identificatiecodeAdresseerbaarObject != null && identificatiecodeAdresseerbaarObject.isInhoudelijkGevuld();
                final boolean identificatiecodeNummeraanduidingGevuld =
                        identificatiecodeNummeraanduiding != null && identificatiecodeNummeraanduiding.isInhoudelijkGevuld();
                final boolean naamOpenbareRuimteGevuld = naamOpenbareRuimte != null && naamOpenbareRuimte.isInhoudelijkGevuld();

                if (!woonplaatsnaamGevuld
                        && identificatiecodeAdresseerbaarObjectGevuld
                        && identificatiecodeNummeraanduidingGevuld
                        && naamOpenbareRuimteGevuld) {
                    woonplaatsnaam = new Lo3String(PUNT, null);
                }

                builder.identificatiecodeVerblijfplaats(identificatiecodeAdresseerbaarObject);
                builder.identificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
                builder.naamOpenbareRuimte(naamOpenbareRuimte);
                builder.straatnaam(getAttribuutConverteerder().converteerString(brpInhoud.getAfgekorteNaamOpenbareRuimte()));
                builder.gemeenteDeel(getAttribuutConverteerder().converteerString(brpInhoud.getGemeentedeel()));
                builder.huisnummer(getAttribuutConverteerder().converteerHuisnummer(brpInhoud.getHuisnummer()));
                builder.huisletter(getAttribuutConverteerder().converteerCharacter(brpInhoud.getHuisletter()));
                builder.huisnummertoevoeging(getAttribuutConverteerder().converteerString(brpInhoud.getHuisnummertoevoeging()));
                builder.postcode(getAttribuutConverteerder().converteerString(brpInhoud.getPostcode()));
                builder.woonplaatsnaam(woonplaatsnaam);
                builder.aanduidingHuisnummer(getAttribuutConverteerder().converteerAanduidingHuisnummer(brpInhoud.getLocatieTovAdres()));
                builder.locatieBeschrijving(getAttribuutConverteerder().converteerString(brpInhoud.getLocatieOmschrijving()));
            }
        }

        /**
         * Adres is niet bekend als uitsluitend gemeente en land/gebied is ingevuld.
         * @param brpInhoud BRP adres inhoud
         * @return true als alleen gemeente en land/gebied is ingevuld
         */
        @Definitie({Definities.DEF023, Definities.DEF024})
        private boolean isAdresNietBekend(final BrpAdresInhoud brpInhoud) {
            return BrpValidatie.isEenParameterGevuld(brpInhoud.getGemeenteCode(), brpInhoud.getLandOfGebiedCode())
                    && !BrpValidatie.isEenParameterGevuld(
                    brpInhoud.getAfgekorteNaamOpenbareRuimte(),
                    brpInhoud.getGemeentedeel(),
                    brpInhoud.getHuisletter(),
                    brpInhoud.getHuisnummer(),
                    brpInhoud.getHuisnummertoevoeging(),
                    brpInhoud.getIdentificatiecodeAdresseerbaarObject(),
                    brpInhoud.getIdentificatiecodeNummeraanduiding(),
                    brpInhoud.getLocatieOmschrijving(),
                    brpInhoud.getLocatieTovAdres(),
                    brpInhoud.getNaamOpenbareRuimte(),
                    brpInhoud.getPostcode(),
                    brpInhoud.getWoonplaatsnaam());
        }
    }

    /**
     * Converteerder die weet hoe een {@link BrpOnverwerktDocumentAanwezigIndicatieInhoud} omgezet moet worden naar
     * L{@link Lo3VerblijfplaatsInhoud}.
     */
    public static final class OnverwerktDocumentAanwezigConverteerder extends AbstractConverteerder<BrpOnverwerktDocumentAanwezigIndicatieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public OnverwerktDocumentAanwezigConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3VerblijfplaatsInhoud vulInhoud(
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpOnverwerktDocumentAanwezigIndicatieInhoud brpInhoud,
                final BrpOnverwerktDocumentAanwezigIndicatieInhoud brpVorige) {
            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.datumInschrijving(null);
                builder.indicatieDocument(null);
            } else {
                // datumInschrijving wordt gevuld door datumAanvangGeldigheid uit de Lo3Historie, zie #nabewerking()
                builder.datumInschrijving(null);
                builder.indicatieDocument(getAttribuutConverteerder().converteerIndicatieDocument(brpInhoud.getIndicatie()));
            }

            return builder.build();
        }
    }

    /**
     * Converteerder die weet hoe een BrpBijhoudingInhoud omgezet moet worden naar Lo3VerblijfplaatsInhoud.
     */
    @Requirement(Requirements.CCA08_BL05)
    public static final class BijhoudingConverteerder extends AbstractConverteerder<BrpBijhoudingInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public BijhoudingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3VerblijfplaatsInhoud vulInhoud(
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpBijhoudingInhoud brpInhoud,
                final BrpBijhoudingInhoud brpVorigeInhoud) {

            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);

            if (brpInhoud == null) {
                builder.gemeenteInschrijving(null);
                builder.datumInschrijving(null);
                builder.indicatieDocument(null);
            } else {
                builder.gemeenteInschrijving(getAttribuutConverteerder().converteerGemeenteCode(brpInhoud.getBijhoudingspartijCode()));
                // datumInschrijving wordt gevuld door datumAanvangGeldigheid uit de Lo3Historie, zie #nabewerking()
                builder.datumInschrijving(null);
            }

            return builder.build();
        }

        @Override
        protected boolean bepaalVoorkomenAanvullen(
                final BrpActie actie,
                final Lo3CategorieWrapper<Lo3VerblijfplaatsInhoud> categorieWrapper,
                final Lo3Datum ingang) {
            return bepaalAanvullen(actie, categorieWrapper);
        }
    }

    /**
     * Converteerder die weet hoe een BrpImmigratieInhoud omgezet moet worden naar Lo3VerblijfplaatsInhoud.
     */
    @Requirement(Requirements.CCA08_BL04)
    @Definitie({Definities.DEF074, Definities.DEF075, Definities.DEF076})
    public static final class MigratieConverteerder extends AbstractConverteerder<BrpMigratieInhoud> {
        private static final Logger LOG = LoggerFactory.getLogger();
        /**
         * Constructor.
         * @param attribuutConverteerder attribuut converteerder
         */
        public MigratieConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder#getLogger()
         */
        @Override
        protected Logger getLogger() {
            return LOG;
        }

        @Override
        public Lo3VerblijfplaatsInhoud vulInhoud(
                final Lo3VerblijfplaatsInhoud lo3Inhoud,
                final BrpMigratieInhoud brpInhoud,
                final BrpMigratieInhoud brpVorigeInhoud) {
            final Lo3VerblijfplaatsInhoud.Builder builder = new Lo3VerblijfplaatsInhoud.Builder(lo3Inhoud);
            if (brpInhoud != null) {
                if (BrpSoortMigratieCode.IMMIGRATIE.equals(brpInhoud.getSoortMigratieCode())) {
                    // DEF076
                    // Dit is een Immigratie. Deze beeindigt eventueel aanwezige emigratie-gegevens.
                    builder.resetEmigratieVelden();

                    builder.landVanwaarIngeschreven(getAttribuutConverteerder().converteerLandCode(brpInhoud.getLandOfGebiedCode()));
                    // Datum vestiging in NL wordt gevuld in de methode nabewerking
                } else {
                    // Dit is een emigratie. Deze beeindigt eventueel aanwezige immigratie-gegevens.
                    builder.resetImmigratieVelden();

                    // Als groep 13 al is ingevuld door AdresConverteerder, dan mag de MigratieConverteerder deze
                    // gegevens niet overschrijven.
                    // De AdresConverteerder mag deze gegevens wel altijd overschrijven
                    if (!Lo3Validatie.isEenParameterGevuld(
                            lo3Inhoud.getAdresBuitenland1(),
                            lo3Inhoud.getAdresBuitenland2(),
                            lo3Inhoud.getAdresBuitenland3(),
                            lo3Inhoud.getLandAdresBuitenland())) {
                        // DEF074 DEF075
                        verwerkBuitenlandsAdres(
                                builder,
                                getAttribuutConverteerder(),
                                brpInhoud.getBuitenlandsAdresRegel1(),
                                brpInhoud.getBuitenlandsAdresRegel2(),
                                brpInhoud.getBuitenlandsAdresRegel3(),
                                brpInhoud.getLandOfGebiedCode());
                    }
                }
            }

            return builder.build();
        }

        @Override
        protected boolean bepaalVoorkomenAanvullen(
                final BrpActie actie,
                final Lo3CategorieWrapper<Lo3VerblijfplaatsInhoud> categorieWrapper,
                final Lo3Datum ingang) {
            return bepaalAanvullen(actie, categorieWrapper);
        }
    }
}
