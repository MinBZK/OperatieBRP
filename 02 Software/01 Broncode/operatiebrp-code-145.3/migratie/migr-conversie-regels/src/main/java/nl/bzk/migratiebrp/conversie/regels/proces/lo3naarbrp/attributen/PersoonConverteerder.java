/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

/**
 * Deze class bevat de functionaliteit om de LO3 persoonsgegevens te converteren naar de BRP groepen.
 */
public class PersoonConverteerder extends Converteerder {

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder Lo3AttribuutConverteerder
     */
    public PersoonConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        super(lo3AttribuutConverteerder);
    }

    /**
     * Converteert de LO3 elementen naar het BRP model.
     * @param lo3Persoonslijst de volledige LO3 persoonslijst
     * @param isDummyPL of de PL een dummy is of niet.
     * @param tussenPersoonslijstBuilder Hybride persoonslijst van BRP inhoud met Lo3 geschiedenis om het tussen resultaat in op te slaan.
     */
    public final void converteer(
            final Lo3Persoonslijst lo3Persoonslijst,
            final boolean isDummyPL,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = lo3Persoonslijst.getPersoonStapel();
        final TussenRelatie meestRecentHuwelijkOfGp = bepaalTeGebruikenHuwelijkOfGpCategorie(tussenPersoonslijstBuilder.build().getRelaties());
        final TussenBetrokkenheid tussenBetrokkenheid = meestRecentHuwelijkOfGp == null ? null : meestRecentHuwelijkOfGp.getBetrokkenheden().get(0);

        final List<TussenGroep<BrpGeboorteInhoud>> geboorten = new ArrayList<>();
        final List<TussenGroep<BrpNaamgebruikInhoud>> naamgebruiken = new ArrayList<>();
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNamen = new ArrayList<>();
        final List<TussenGroep<BrpIdentificatienummersInhoud>> identificatienummers = new ArrayList<>();
        final List<TussenGroep<BrpGeslachtsaanduidingInhoud>> geslachten = new ArrayList<>();
        final List<TussenGroep<BrpNummerverwijzingInhoud>> nummerverwijzingen = new ArrayList<>();

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : persoonStapel) {
            final Lo3PersoonInhoud persoonInhoud = categorie.getInhoud();
            final Lo3Historie historie = categorie.getHistorie();
            final Lo3Documentatie documentatie = categorie.getDocumentatie();
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

            final BrpNaamgebruikInhoud naamgebruikInhoud =
                    migreerNaamgebruik(persoonInhoud, tussenBetrokkenheid, isDummyPL, categorie.getLo3Herkomst().isLo3ActueelVoorkomen());
            if (naamgebruikInhoud != null) {
                naamgebruiken.add(new TussenGroep<>(naamgebruikInhoud, historie, documentatie, herkomst));
            }

            geboorten.add(migreerGeboorte(categorie));
            samengesteldeNamen.add(migreerSamengesteldeNaam(categorie));
            identificatienummers.add(migreerIdentificatienummers(categorie));
            geslachten.add(migreerGeslachtsaanduiding(categorie));

            final TussenGroep<BrpNummerverwijzingInhoud> nummerverwijzingGroep = migreerNummerverwijzing(categorie);
            if (nummerverwijzingGroep != null) {
                nummerverwijzingen.add(nummerverwijzingGroep);
            }
        }

        if (!naamgebruiken.isEmpty()) {
            tussenPersoonslijstBuilder.naamgebruikStapel(new TussenStapel<>(naamgebruiken));
        }

        tussenPersoonslijstBuilder.geboorteStapel(new TussenStapel<>(geboorten));
        tussenPersoonslijstBuilder.samengesteldeNaamStapel(new TussenStapel<>(samengesteldeNamen));
        tussenPersoonslijstBuilder.identificatienummerStapel(new TussenStapel<>(identificatienummers));
        tussenPersoonslijstBuilder.geslachtsaanduidingStapel(new TussenStapel<>(geslachten));
        if (!nummerverwijzingen.isEmpty()) {
            tussenPersoonslijstBuilder.nummerverwijzingStapel(new TussenStapel<>(nummerverwijzingen));
        }
    }

    @Requirement(Requirements.CEL0410)
    private TussenGroep<BrpGeslachtsaanduidingInhoud> migreerGeslachtsaanduiding(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakGeslachtsaanduidingInhoud(
                inhoud.getGeslachtsaanduiding(),
                persoon.getHistorie(),
                persoon.getDocumentatie(),
                persoon.getLo3Herkomst());
    }

    @Requirement(Requirements.CGR01_LB01)
    private TussenGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakIdentificatieGroep(
                inhoud.getANummer(),
                inhoud.getBurgerservicenummer(),
                persoon.getHistorie(),
                persoon.getDocumentatie(),
                persoon.getLo3Herkomst());
    }

    @Requirement({Requirements.CEL0220_LB01, Requirements.CEL0230_LB01, Requirements.CEL0240_LB01})
    private TussenGroep<BrpSamengesteldeNaamInhoud> migreerSamengesteldeNaam(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakSamengesteldeNaamGroep(
                inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoornamen(),
                inhoud.getVoorvoegselGeslachtsnaam(),
                inhoud.getGeslachtsnaam(),
                persoon.getHistorie(),
                persoon.getDocumentatie(),
                persoon.getLo3Herkomst(),
                true);
    }

    @Requirement({Requirements.CEL6110, Requirements.CEL0220_LB01, Requirements.CEL0230_LB01})
    @Definitie(Definities.DEF016)
    private BrpNaamgebruikInhoud migreerNaamgebruik(
            final Lo3PersoonInhoud persoonInhoud,
            final TussenBetrokkenheid tussenBetrokkenheid,
            final boolean isDummyPL,
            final boolean isActueelVoorkomen) {
        final BrpNaamgebruikCode aanduidingNaamgebruik;
        if (!isDummyPL) {
            aanduidingNaamgebruik = getLo3AttribuutConverteerder().converteerLo3AanduidingNaamgebruikCode(persoonInhoud.getAanduidingNaamgebruikCode());
        } else {
            aanduidingNaamgebruik = null;
        }

        final BrpNaamgebruikInhoud.Builder builder = maakBuilderVoorNaamgebruik(aanduidingNaamgebruik, persoonInhoud, tussenBetrokkenheid, isActueelVoorkomen);
        builder.voornamen(getLo3AttribuutConverteerder().converteerString(persoonInhoud.getVoornamen()));

        final BrpAdellijkeTitelCode adellijkeTitel =
                getLo3AttribuutConverteerder().converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(
                        persoonInhoud.getAdellijkeTitelPredikaatCode());
        final BrpPredicaatCode predicaatCode =
                getLo3AttribuutConverteerder().converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredicaatCode(persoonInhoud.getAdellijkeTitelPredikaatCode());
        if (isAdellijkeTitel(adellijkeTitel)) {
            builder.adellijkeTitelCode(adellijkeTitel);
        } else if (isPredicaat(predicaatCode)) {
            builder.predicaatCode(predicaatCode);
        }
        return builder.build();
    }

    private BrpNaamgebruikInhoud.Builder maakBuilderVoorNaamgebruik(
            final BrpNaamgebruikCode aanduidingNaamgebruik,
            final Lo3PersoonInhoud persoonInhoud,
            final TussenBetrokkenheid tussenBetrokkenheid,
            final boolean isActueelVoorkomen) {
        final Achternaam eigenAchternaam = new Achternaam(persoonInhoud);
        final Achternaam partnerAchternaam = new Achternaam(tussenBetrokkenheid);

        final BrpNaamgebruikCode naamgebruik;
        if (aanduidingNaamgebruik == null
                || (!BrpNaamgebruikCode.equalsWaarde(BrpNaamgebruikCode.E, aanduidingNaamgebruik) && partnerAchternaam.geslachtsnaam.isEmpty())
                || !isActueelVoorkomen) {
            // Aanuiding is van dummy PL (null) of naamgebruik is niet een E, maar er is geen huwelijk of GP gevonden,
            // dan naamgebruik hetzelfde als E.
            naamgebruik = BrpNaamgebruikCode.E;
        } else {
            naamgebruik = aanduidingNaamgebruik;
        }

        final String koppelteken = "-";
        final String geslachtsnaam;
        final Set<Lo3Onderzoek> onderzoeken = new HashSet<>();
        if (BrpNaamgebruikCode.equalsWaarde(BrpNaamgebruikCode.P, naamgebruik)) {
            // Partner naam
            geslachtsnaam = partnerAchternaam.geslachtsnaam;
            voegOnderzoekToe(onderzoeken, partnerAchternaam.onderzoek);
        } else if (BrpNaamgebruikCode.equalsWaarde(BrpNaamgebruikCode.V, naamgebruik)) {
            // geslachtsnaam partner Voor eigen geslachtsnaam
            geslachtsnaam =
                    partnerAchternaam.geslachtsnaam
                            + koppelteken
                            + eigenAchternaam.voorvoegsel
                            + eigenAchternaam.scheidingsteken
                            + eigenAchternaam.geslachtsnaam;
            voegOnderzoekToe(onderzoeken, eigenAchternaam.onderzoek);
            voegOnderzoekToe(onderzoeken, partnerAchternaam.onderzoek);
        } else if (BrpNaamgebruikCode.equalsWaarde(BrpNaamgebruikCode.N, naamgebruik)) {
            // geslachtsnaam partner Na eigen geslachtsnaam
            geslachtsnaam =
                    eigenAchternaam.geslachtsnaam
                            + koppelteken
                            + partnerAchternaam.voorvoegsel
                            + partnerAchternaam.scheidingsteken
                            + partnerAchternaam.geslachtsnaam;
            voegOnderzoekToe(onderzoeken, eigenAchternaam.onderzoek);
            voegOnderzoekToe(onderzoeken, partnerAchternaam.onderzoek);
        } else {
            // Eigen naam
            geslachtsnaam = eigenAchternaam.geslachtsnaam;
            voegOnderzoekToe(onderzoeken, eigenAchternaam.onderzoek);
        }

        final BrpNaamgebruikInhoud.Builder builder =
                new BrpNaamgebruikInhoud.Builder(
                        aanduidingNaamgebruik,
                        new BrpBoolean(true, null),
                        new BrpNaamgebruikGeslachtsnaamstam(geslachtsnaam, onderzoeken));

        vulVoorvoegselEnScheidingsteken(eigenAchternaam, partnerAchternaam, naamgebruik, builder);
        return builder;
    }

    private void voegOnderzoekToe(final Set<Lo3Onderzoek> onderzoeken, final Lo3Onderzoek onderzoek) {
        if (onderzoek != null) {
            onderzoeken.add(onderzoek);
        }
    }

    private void vulVoorvoegselEnScheidingsteken(
            final Achternaam eigenAchternaam,
            final Achternaam partnerAchternaam,
            final BrpNaamgebruikCode naamgebruik,
            final BrpNaamgebruikInhoud.Builder builder) {
        final String voorvoegsel;
        final Character scheidingsteken;

        final Lo3Onderzoek teGebruikenOnderzoek;
        if (BrpNaamgebruikCode.equalsWaarde(BrpNaamgebruikCode.P, naamgebruik) || BrpNaamgebruikCode.equalsWaarde(BrpNaamgebruikCode.V, naamgebruik)) {
            voorvoegsel = partnerAchternaam.voorvoegsel.isEmpty() ? null : partnerAchternaam.voorvoegsel;
            scheidingsteken = partnerAchternaam.scheidingsteken.isEmpty() ? null : partnerAchternaam.scheidingsteken.charAt(0);
            teGebruikenOnderzoek = partnerAchternaam.onderzoek;
        } else {
            voorvoegsel = eigenAchternaam.voorvoegsel.isEmpty() ? null : eigenAchternaam.voorvoegsel;
            scheidingsteken = eigenAchternaam.scheidingsteken.isEmpty() ? null : eigenAchternaam.scheidingsteken.charAt(0);
            teGebruikenOnderzoek = eigenAchternaam.onderzoek;
        }

        if (voorvoegsel != null) {
            builder.voorvoegsel(new BrpString(voorvoegsel, teGebruikenOnderzoek));
        }
        if (scheidingsteken != null) {
            builder.scheidingsteken(new BrpCharacter(scheidingsteken, teGebruikenOnderzoek));
        }
    }

    @Definitie(Definities.DEF014)
    private static boolean isAdellijkeTitel(final BrpAdellijkeTitelCode code) {
        return BrpValidatie.isAttribuutGevuld(code);
    }

    @Definitie(Definities.DEF015)
    private static boolean isPredicaat(final BrpPredicaatCode code) {
        return BrpValidatie.isAttribuutGevuld(code);
    }

    @Requirement({Requirements.CGR03_LB01, Requirements.CGR03_LB02, Requirements.CGR03_LB03})
    private TussenGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakGeboorteGroep(
                inhoud.getGeboorteGemeenteCode(),
                inhoud.getGeboorteLandCode(),
                inhoud.getGeboortedatum(),
                persoon.getHistorie(),
                persoon.getDocumentatie(),
                persoon.getLo3Herkomst());
    }

    private TussenGroep<BrpNummerverwijzingInhoud> migreerNummerverwijzing(final Lo3Categorie<Lo3PersoonInhoud> persoon) {
        final Lo3PersoonInhoud inhoud = persoon.getInhoud();
        return getUtils().maakNummerverwijzingGroep(
                inhoud.getVorigANummer(),
                inhoud.getVolgendANummer(),
                persoon.getHistorie(),
                persoon.getDocumentatie(),
                persoon.getLo3Herkomst());
    }

    private TussenRelatie bepaalTeGebruikenHuwelijkOfGpCategorie(final List<TussenRelatie> relaties) {
        final List<TussenRelatie> huwelijkOfGpRelaties =
                relaties.stream().filter(relatie -> relatie.isRelatieHuwelijkOfGp() && !relatie.getRelatieStapel().isStapelMetAlleenLegeInhoud()).collect(
                        Collectors.toList());

        huwelijkOfGpRelaties.sort((o1, o2) -> {
            final Integer o1DatumAanvang = zoekRelatieDatumAanvang(o1);
            final Integer o2DatumAanvang = zoekRelatieDatumAanvang(o2);

            final Integer datumEindeO1 = zoekRelatieDatumEinde(o1);
            final Integer datumEindeO2 = zoekRelatieDatumEinde(o2);

            if (datumEindeO2 - datumEindeO1 == 0) {
                return o2DatumAanvang - o1DatumAanvang;
            }
            return datumEindeO2 - datumEindeO1;
        });

        return huwelijkOfGpRelaties.isEmpty() ? null : huwelijkOfGpRelaties.get(0);
    }

    private int zoekRelatieDatumAanvang(final TussenRelatie tussenRelatie) {
        Integer result = null;
        for (final TussenGroep<BrpRelatieInhoud> groep : tussenRelatie.getRelatieStapel()) {
            final BrpRelatieInhoud inhoud = groep.getInhoud();
            if (inhoud.getDatumAanvang() != null) {
                result = inhoud.getDatumAanvang().getWaarde();
                break;
            }
        }
        return result == null ? Integer.MAX_VALUE : result;
    }

    private int zoekRelatieDatumEinde(final TussenRelatie tussenRelatie) {
        Integer result = null;
        for (final TussenGroep<BrpRelatieInhoud> groep : tussenRelatie.getRelatieStapel()) {
            final BrpRelatieInhoud inhoud = groep.getInhoud();
            if (inhoud.getDatumEinde() != null) {
                result = inhoud.getDatumEinde().getWaarde();
                break;
            }
        }
        return result == null ? Integer.MAX_VALUE : result;
    }

    /**
     * Interne class om een achternaam te representeren.
     */
    private class Achternaam {
        private final String geslachtsnaam;
        private final String scheidingsteken;
        private final String voorvoegsel;
        private Lo3Onderzoek onderzoek;

        /**
         * Constructor waar mee deze class wordt gevuld met een {@link Lo3HuwelijkOfGpInhoud}.
         * @param tussenBetrokkenheid betrokkenheid in het migratie tussen model.
         */
        Achternaam(final TussenBetrokkenheid tussenBetrokkenheid) {
            if (tussenBetrokkenheid != null) {
                final BrpSamengesteldeNaamInhoud samengesteldeNaamInhoud = tussenBetrokkenheid.getSamengesteldeNaamStapel().get(0).getInhoud();

                geslachtsnaam = getWaarde(samengesteldeNaamInhoud.getGeslachtsnaamstam());
                voorvoegsel = getWaarde(samengesteldeNaamInhoud.getVoorvoegsel());
                scheidingsteken = getWaarde(samengesteldeNaamInhoud.getScheidingsteken());
                onderzoek =
                        bepaalOnderzoek(
                                samengesteldeNaamInhoud.getGeslachtsnaamstam(),
                                samengesteldeNaamInhoud.getVoorvoegsel(),
                                samengesteldeNaamInhoud.getScheidingsteken());
            } else {
                geslachtsnaam = "";
                voorvoegsel = "";
                scheidingsteken = "";
                onderzoek = null;
            }
        }

        /**
         * Constructor waar mee deze class wordt gevuld meteen {@link Lo3PersoonInhoud}.
         * @param inhoud een persoon inhoud
         */
        Achternaam(final Lo3PersoonInhoud inhoud) {
            final VoorvoegselScheidingstekenPaar eigenVoorvoegselScheidingstekenPaar =
                    getLo3AttribuutConverteerder().converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud.getVoorvoegselGeslachtsnaam());
            geslachtsnaam = getWaarde(inhoud.getGeslachtsnaam());
            voorvoegsel = getWaarde(eigenVoorvoegselScheidingstekenPaar.getVoorvoegsel());
            scheidingsteken = getWaarde(eigenVoorvoegselScheidingstekenPaar.getScheidingsteken());
            onderzoek = bepaalOnderzoek(inhoud.getGeslachtsnaam(), inhoud.getVoorvoegselGeslachtsnaam());
        }

        private String getWaarde(final Lo3String lo3String) {
            if (lo3String == null || !lo3String.isInhoudelijkGevuld()) {
                return "";
            }
            return lo3String.getWaarde();
        }

        private String getWaarde(final BrpAttribuutMetOnderzoek brpAttribuutMetOnderzoek) {
            if (brpAttribuutMetOnderzoek == null || !brpAttribuutMetOnderzoek.isInhoudelijkGevuld()) {
                return "";
            }
            return String.valueOf(brpAttribuutMetOnderzoek.getWaarde());
        }

        private Lo3Onderzoek bepaalOnderzoek(final Lo3Element... attributenMetOnderzoek) {
            Lo3Onderzoek lo3Onderzoek = null;
            for (final Lo3Element attribuutMetOnderzoek : attributenMetOnderzoek) {
                if (attribuutMetOnderzoek != null && attribuutMetOnderzoek.getOnderzoek() != null) {
                    lo3Onderzoek = attribuutMetOnderzoek.getOnderzoek();
                    break;
                }
            }
            return lo3Onderzoek;
        }

        private Lo3Onderzoek bepaalOnderzoek(final BrpAttribuutMetOnderzoek... attributenMetOnderzoek) {
            Lo3Onderzoek lo3Onderzoek = null;
            for (final BrpAttribuutMetOnderzoek attribuutMetOnderzoek : attributenMetOnderzoek) {
                if (attribuutMetOnderzoek != null && attribuutMetOnderzoek.getOnderzoek() != null) {
                    lo3Onderzoek = attribuutMetOnderzoek.getOnderzoek();
                    break;
                }
            }
            return lo3Onderzoek;
        }
    }
}
