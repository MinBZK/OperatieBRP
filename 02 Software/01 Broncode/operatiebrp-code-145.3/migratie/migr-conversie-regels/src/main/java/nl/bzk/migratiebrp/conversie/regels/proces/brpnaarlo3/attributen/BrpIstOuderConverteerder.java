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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder.Lo3GemeenteLand;

/**
 * Converteert een Brp Ist ouder stapel naar een {@link Lo3OuderInhoud}.
 */
public class BrpIstOuderConverteerder extends BrpIstAbstractConverteerder {
    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpIstOuderConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        super(attribuutConverteerder);
    }

    /**
     * Converteert de IST Ouder naar LO3.
     * @param brpStapel de {@link BrpStapel} met een {@link BrpIstRelatieGroepInhoud}
     * @return een {@link Lo3Stapel} met een {@link Lo3OuderInhoud}
     */
    public final Lo3Stapel<Lo3OuderInhoud> converteerOuder(final BrpStapel<BrpIstRelatieGroepInhoud> brpStapel) {
        if (brpStapel == null) {
            return null;
        }

        final List<Lo3Categorie<Lo3OuderInhoud>> voorkomens = new ArrayList<>();
        for (final BrpGroep<BrpIstRelatieGroepInhoud> groep : brpStapel.getGroepen()) {
            final BrpIstRelatieGroepInhoud inhoud = groep.getInhoud();
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();

            // 1-op-1 conversie
            builder.anummer(getAttribuutConverteerder().converteerAdministratieNummer(inhoud.getAnummer()));
            builder.burgerservicenummer(getAttribuutConverteerder().converteerBurgerservicenummer(inhoud.getBsn()));
            builder.geslachtsnaam(getAttribuutConverteerder().converteerString(inhoud.getGeslachtsnaamstam()));
            builder.voornamen(getAttribuutConverteerder().converteerString(inhoud.getVoornamen()));

            // Conversie mbhv converteerder
            builder.voorvoegselGeslachtsnaam(getAttribuutConverteerder().converteerVoorvoegsel(inhoud.getVoorvoegsel(), inhoud.getScheidingsteken()));
            builder.geslachtsaanduiding(getAttribuutConverteerder().converteerGeslachtsaanduiding(inhoud.getGeslachtsaanduidingCode()));

            // Conversie van datums
            builder.familierechtelijkeBetrekking(getAttribuutConverteerder().converteerDatum(inhoud.getRubriek6210DatumIngangFamilierechtelijkeBetrekking()));
            builder.geboortedatum(getAttribuutConverteerder().converteerDatum(inhoud.getDatumGeboorte()));

            // Conversie specifiek
            vulGeboortePlaatsLand(builder, inhoud);
            vulAdellijkeTitelPredikaat(builder, inhoud);

            final BrpIstStandaardGroepInhoud standaardGegevens = inhoud.getStandaardGegevens();

            final Lo3Categorie<Lo3OuderInhoud> voorkomen =
                    new Lo3Categorie<>(
                            builder.build(),
                            maakDocumentatie(standaardGegevens),
                            maakOnderzoek(standaardGegevens),
                            maakHistorie(standaardGegevens),
                            maakHerkomst(standaardGegevens));

            final Lo3Categorie<Lo3OuderInhoud> voorkomenMetOnderzoek = toevoegenOnderzoekAanElementen(voorkomen);
            voorkomens.add(voorkomenMetOnderzoek);
        }

        Collections.sort(voorkomens, LO3_HERKOMST_COMPARATOR);

        return new Lo3Stapel<>(voorkomens);
    }

    private void vulGeboortePlaatsLand(final Lo3OuderInhoud.Builder builder, final BrpIstRelatieGroepInhoud inhoud) {
        final Lo3GemeenteLand gemeenteLand = converteerGeboortePlaatsLand(inhoud);
        builder.geboorteGemeenteCode(gemeenteLand.getGemeenteCode());
        builder.geboorteLandCode(gemeenteLand.getLandCode());
    }

    private void vulAdellijkeTitelPredikaat(final Lo3OuderInhoud.Builder builder, final BrpIstRelatieGroepInhoud inhoud) {
        builder.adellijkeTitelPredikaatCode(getAttribuutConverteerder().converteerAdellijkeTitelPredikaatCode(
                inhoud.getAdellijkeTitelCode(),
                inhoud.getPredicaatCode()));

    }

    private Lo3Categorie<Lo3OuderInhoud> toevoegenOnderzoekAanElementen(final Lo3Categorie<Lo3OuderInhoud> voorkomen) {
        final Lo3Onderzoek onderzoek = voorkomen.getOnderzoek();
        final Lo3OuderInhoud inhoud = voorkomen.getInhoud();
        final Lo3Herkomst herkomst = voorkomen.getLo3Herkomst();
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder();

        builder.anummer(toevoegenOnderzoek(inhoud.getaNummer(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0110));
        builder.burgerservicenummer(toevoegenOnderzoek(inhoud.getBurgerservicenummer(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0120));
        builder.voornamen(toevoegenOnderzoek(inhoud.getVoornamen(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0210));
        builder.adellijkeTitelPredikaatCode(toevoegenOnderzoek(inhoud.getAdellijkeTitelPredikaatCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0220));
        builder.voorvoegselGeslachtsnaam(toevoegenOnderzoek(inhoud.getVoorvoegselGeslachtsnaam(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0230));
        builder.geslachtsnaam(toevoegenOnderzoek(inhoud.getGeslachtsnaam(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0240));
        builder.geboortedatum(toevoegenOnderzoek(inhoud.getGeboortedatum(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0310));
        builder.geboorteGemeenteCode(toevoegenOnderzoek(inhoud.getGeboorteGemeenteCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0320));
        builder.geboorteLandCode(toevoegenOnderzoek(inhoud.getGeboorteLandCode(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0330));
        builder.geslachtsaanduiding(toevoegenOnderzoek(inhoud.getGeslachtsaanduiding(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_0410));
        builder.familierechtelijkeBetrekking(toevoegenOnderzoek(inhoud.getFamilierechtelijkeBetrekking(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_6210));

        final Lo3Historie historie = toevoegenOnderzoekHistorie(voorkomen.getHistorie(), onderzoek, herkomst);
        final Lo3Documentatie documentatie = toevoegenOnderzoekDocumentatie(voorkomen.getDocumentatie(), onderzoek, herkomst);
        return new Lo3Categorie<>(builder.build(), documentatie, onderzoek, historie, herkomst);
    }
}
