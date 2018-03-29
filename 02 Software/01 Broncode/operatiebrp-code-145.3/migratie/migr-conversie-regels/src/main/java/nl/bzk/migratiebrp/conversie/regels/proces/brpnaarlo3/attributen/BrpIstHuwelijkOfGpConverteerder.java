/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;

/**
 * IST Huwelijk converteerder.
 */
public final class BrpIstHuwelijkOfGpConverteerder extends BrpIstAbstractConverteerder {

    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpIstHuwelijkOfGpConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        super(attribuutConverteerder);
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> converteerHuwelijkOfGp(final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel) {
        if (brpStapel == null) {
            return null;
        }
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> voorkomens = new ArrayList<>();
        for (final BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> groep : brpStapel.getGroepen()) {
            final BrpIstHuwelijkOfGpGroepInhoud inhoud = groep.getInhoud();
            final BrpIstRelatieGroepInhoud relatieInhoud = inhoud.getRelatie();
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();

            // 1-op-1 conversie
            builder.aNummer(getAttribuutConverteerder().converteerAdministratieNummer(relatieInhoud.getAnummer()));
            builder.burgerservicenummer(getAttribuutConverteerder().converteerBurgerservicenummer(relatieInhoud.getBsn()));
            builder.geslachtsnaam(getAttribuutConverteerder().converteerString(relatieInhoud.getGeslachtsnaamstam()));
            builder.voornamen(getAttribuutConverteerder().converteerString(relatieInhoud.getVoornamen()));

            // Conversie mbhv converteerder
            builder.voorvoegselGeslachtsnaam(
                    getAttribuutConverteerder().converteerVoorvoegsel(relatieInhoud.getVoorvoegsel(), relatieInhoud.getScheidingsteken()));
            builder.geslachtsaanduiding(getAttribuutConverteerder().converteerGeslachtsaanduiding(relatieInhoud.getGeslachtsaanduidingCode()));

            builder.redenOntbindingHuwelijkOfGpCode(getAttribuutConverteerder().converteerRedenOntbindingHuwelijk(inhoud.getRedenEindeRelatieCode()));
            builder.soortVerbintenis(getAttribuutConverteerder().converteerSoortVerbintenis(inhoud.getSoortRelatieCode()));

            // Conversie van datums
            builder.datumOntbindingHuwelijkOfGp(getAttribuutConverteerder().converteerDatum(inhoud.getDatumEinde()));
            builder.datumSluitingHuwelijkOfAangaanGp(getAttribuutConverteerder().converteerDatum(inhoud.getDatumAanvang()));
            builder.geboortedatum(getAttribuutConverteerder().converteerDatum(relatieInhoud.getDatumGeboorte()));

            // Conversie specifiek
            vulAdellijkeTitelPredikaat(builder, relatieInhoud);
            vulGeboortePlaatsLand(builder, relatieInhoud);
            vulOntbindingPlaatsLand(builder, inhoud);
            vulSluitingPlaatsLand(builder, inhoud);

            final BrpIstStandaardGroepInhoud standaardGegevens = inhoud.getStandaardGegevens();

            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> voorkomen =
                    new Lo3Categorie<>(
                            builder.build(),
                            maakDocumentatie(standaardGegevens),
                            maakOnderzoek(standaardGegevens),
                            maakHistorie(standaardGegevens),
                            maakHerkomst(standaardGegevens));

            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> voorkomenMetOnderzoek = toevoegenOnderzoekAanElementen(voorkomen);
            voorkomens.add(voorkomenMetOnderzoek);
        }

        voorkomens.sort(LO3_HERKOMST_COMPARATOR);

        return new Lo3Stapel<>(voorkomens);
    }

    private void vulGeboortePlaatsLand(final Lo3HuwelijkOfGpInhoud.Builder builder, final BrpIstRelatieGroepInhoud relatieInhoud) {
        final Lo3GemeenteLand gemeenteLand = converteerGeboortePlaatsLand(relatieInhoud);
        builder.geboorteGemeenteCode(gemeenteLand.getGemeenteCode());
        builder.geboorteLandCode(gemeenteLand.getLandCode());
    }

    private void vulOntbindingPlaatsLand(final Lo3HuwelijkOfGpInhoud.Builder builder, final BrpIstHuwelijkOfGpGroepInhoud inhoud) {
        final Lo3GemeenteLand gemeenteLand =
                converteerPlaatsLand(
                        inhoud.getGemeenteCodeEinde(),
                        inhoud.getBuitenlandsePlaatsEinde(),
                        inhoud.getLandOfGebiedCodeEinde(),
                        inhoud.getOmschrijvingLocatieEinde());
        builder.gemeenteCodeOntbindingHuwelijkOfGp(gemeenteLand.getGemeenteCode());
        builder.landCodeOntbindingHuwelijkOfGp(gemeenteLand.getLandCode());
    }

    private void vulSluitingPlaatsLand(final Lo3HuwelijkOfGpInhoud.Builder builder, final BrpIstHuwelijkOfGpGroepInhoud inhoud) {
        final Lo3GemeenteLand gemeenteLand =
                converteerPlaatsLand(
                        inhoud.getGemeenteCodeAanvang(),
                        inhoud.getBuitenlandsePlaatsAanvang(),
                        inhoud.getLandOfGebiedCodeAanvang(),
                        inhoud.getOmschrijvingLocatieAanvang());
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(gemeenteLand.getGemeenteCode());
        builder.landCodeSluitingHuwelijkOfAangaanGp(gemeenteLand.getLandCode());
    }

    private void vulAdellijkeTitelPredikaat(final Lo3HuwelijkOfGpInhoud.Builder builder, final BrpIstRelatieGroepInhoud relatieInhoud) {
        builder.adellijkeTitelPredikaatCode(getAttribuutConverteerder().converteerAdellijkeTitelPredikaatCode(
                relatieInhoud.getAdellijkeTitelCode(),
                relatieInhoud.getPredicaatCode()));
    }

    /**
     * Geeft de lijst van stapels terug met {@link Lo3HuwelijkOfGpInhoud}.
     * @param brpStapels brp IST stapels voor huwelijk of geregistreerd partnerschap
     * @return een gevuld lijst van stapels of null als er geen voorkomens zijn.
     */
    public List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> converteerHuwelijkOfGpStapels(final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> brpStapels) {
        if (brpStapels == null) {
            return Collections.emptyList();
        }

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> stapels = new ArrayList<>();
        for (final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> brpStapel : brpStapels) {
            stapels.add(converteerHuwelijkOfGp(brpStapel));
        }
        return stapels;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> toevoegenOnderzoekAanElementen(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> voorkomen) {
        final Lo3Onderzoek onderzoek = voorkomen.getOnderzoek();
        final Lo3HuwelijkOfGpInhoud inhoud = voorkomen.getInhoud();
        final Lo3Herkomst herkomst = voorkomen.getLo3Herkomst();
        final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder();

        builder.aNummer(toevoegenOnderzoek(inhoud.getaNummer(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0110));
        builder.burgerservicenummer(toevoegenOnderzoek(inhoud.getBurgerservicenummer(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0120));
        builder.voornamen(toevoegenOnderzoek(inhoud.getVoornamen(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0210));
        builder.adellijkeTitelPredikaatCode(toevoegenOnderzoek(inhoud.getAdellijkeTitelPredikaatCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0220));
        builder.voorvoegselGeslachtsnaam(toevoegenOnderzoek(inhoud.getVoorvoegselGeslachtsnaam(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0230));
        builder.geslachtsnaam(toevoegenOnderzoek(inhoud.getGeslachtsnaam(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0240));
        builder.geboortedatum(toevoegenOnderzoek(inhoud.getGeboortedatum(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0310));
        builder.geboorteGemeenteCode(toevoegenOnderzoek(inhoud.getGeboorteGemeenteCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0320));
        builder.geboorteLandCode(toevoegenOnderzoek(inhoud.getGeboorteLandCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0330));
        builder.geslachtsaanduiding(toevoegenOnderzoek(inhoud.getGeslachtsaanduiding(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0410));
        builder.datumSluitingHuwelijkOfAangaanGp(toevoegenOnderzoek(
                inhoud.getDatumSluitingHuwelijkOfAangaanGp(),
                onderzoek,
                herkomst,
                Lo3ElementEnum.ELEMENT_0610));
        builder.gemeenteCodeSluitingHuwelijkOfAangaanGp(toevoegenOnderzoek(
                inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                onderzoek,
                herkomst,
                Lo3ElementEnum.ELEMENT_0620));
        builder.landCodeSluitingHuwelijkOfAangaanGp(toevoegenOnderzoek(
                inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(),
                onderzoek,
                herkomst,
                Lo3ElementEnum.ELEMENT_0630));
        builder.datumOntbindingHuwelijkOfGp(toevoegenOnderzoek(inhoud.getDatumOntbindingHuwelijkOfGp(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0710));
        builder.gemeenteCodeOntbindingHuwelijkOfGp(toevoegenOnderzoek(
                inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(),
                onderzoek,
                herkomst,
                Lo3ElementEnum.ELEMENT_0720));
        builder.landCodeOntbindingHuwelijkOfGp(toevoegenOnderzoek(
                inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                onderzoek,
                herkomst,
                Lo3ElementEnum.ELEMENT_0730));
        builder.redenOntbindingHuwelijkOfGpCode(toevoegenOnderzoek(
                inhoud.getRedenOntbindingHuwelijkOfGpCode(),
                onderzoek,
                herkomst,
                Lo3ElementEnum.ELEMENT_0740));
        builder.soortVerbintenis(toevoegenOnderzoek(inhoud.getSoortVerbintenis(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_1510));

        final Lo3Historie historie = toevoegenOnderzoekHistorie(voorkomen.getHistorie(), onderzoek, herkomst);
        final Lo3Documentatie documentatie = toevoegenOnderzoekDocumentatie(voorkomen.getDocumentatie(), onderzoek, herkomst);
        return new Lo3Categorie<>(builder.build(), documentatie, onderzoek, historie, herkomst);
    }
}
