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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.springframework.stereotype.Component;

/**
 * IST Gezagsverhouding converteerder.
 */
@Component
public final class BrpIstGezagsverhoudingConverteerder extends BrpIstAbstractConverteerder<BrpIstGezagsVerhoudingGroepInhoud> {

    @Override
    public Lo3Stapel<Lo3GezagsverhoudingInhoud> converteer(final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpStapel) {
        if (brpStapel == null) {
            return null;
        }
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> voorkomens = new ArrayList<>();
        for (final BrpGroep<BrpIstGezagsVerhoudingGroepInhoud> groep : brpStapel.getGroepen()) {
            final BrpIstGezagsVerhoudingGroepInhoud inhoud = groep.getInhoud();

            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();

            // Conversie mbhv converteerder
            builder.indicatieCurateleregister(getConverteerder().converteerIndicatieCurateleRegister(inhoud.getIndicatieOnderCuratele()));

            // Conversie specifiek
            vulIndicatieGezagMinderjarige(builder, inhoud);
            final BrpIstStandaardGroepInhoud standaardGegevens = inhoud.getStandaardGegevens();
            final Lo3Categorie<Lo3GezagsverhoudingInhoud> voorkomen =
                    new Lo3Categorie<>(
                        builder.build(),
                        maakDocumentatie(standaardGegevens),
                        maakOnderzoek(standaardGegevens),
                        maakHistorie(standaardGegevens),
                        maakHerkomst(standaardGegevens));
            final Lo3Categorie<Lo3GezagsverhoudingInhoud> voorkomenMetOnderzoek = toevoegenOnderzoekAanElementen(voorkomen);
            voorkomens.add(voorkomenMetOnderzoek);
        }

        Collections.sort(voorkomens, LO3_HERKOMST_COMPARATOR);

        return new Lo3Stapel<>(voorkomens);
    }

    private void vulIndicatieGezagMinderjarige(final Lo3GezagsverhoudingInhoud.Builder builder, final BrpIstGezagsVerhoudingGroepInhoud inhoud) {

        // mogelijke waarden: 1, 2, D, 1D, 2D, 12
        final boolean ouder1Gezag = Boolean.TRUE.equals(BrpBoolean.unwrap(inhoud.getIndicatieOuder1HeeftGezag()));
        final boolean ouder2Gezag = Boolean.TRUE.equals(BrpBoolean.unwrap(inhoud.getIndicatieOuder2HeeftGezag()));
        final boolean derdeGezag = Boolean.TRUE.equals(BrpBoolean.unwrap(inhoud.getIndicatieDerdeHeeftGezag()));

        if (ouder1Gezag || ouder2Gezag || derdeGezag) {
            final StringBuilder code = new StringBuilder();

            if (ouder1Gezag) {
                code.append("1");
            }
            if (ouder2Gezag) {
                code.append("2");
            }
            if (derdeGezag) {
                code.append("D");
            }

            final List<Lo3Onderzoek> onderzoeken = new ArrayList<>();
            if (inhoud.getIndicatieOuder1HeeftGezag() != null && inhoud.getIndicatieOuder1HeeftGezag().getOnderzoek() != null) {
                onderzoeken.add(inhoud.getIndicatieOuder1HeeftGezag().getOnderzoek());
            } else if (inhoud.getIndicatieOuder2HeeftGezag() != null && inhoud.getIndicatieOuder2HeeftGezag().getOnderzoek() != null) {
                onderzoeken.add(inhoud.getIndicatieOuder2HeeftGezag().getOnderzoek());
            } else if (inhoud.getIndicatieDerdeHeeftGezag() != null && inhoud.getIndicatieDerdeHeeftGezag().getOnderzoek() != null) {
                onderzoeken.add(inhoud.getIndicatieDerdeHeeftGezag().getOnderzoek());
            }
            builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(code.toString(), Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken)));
        }
    }

    private Lo3Categorie<Lo3GezagsverhoudingInhoud> toevoegenOnderzoekAanElementen(final Lo3Categorie<Lo3GezagsverhoudingInhoud> voorkomen) {
        final Lo3Onderzoek onderzoek = voorkomen.getOnderzoek();
        final Lo3GezagsverhoudingInhoud inhoud = voorkomen.getInhoud();
        final Lo3Herkomst herkomst = voorkomen.getLo3Herkomst();
        final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();

        builder.indicatieGezagMinderjarige(toevoegenOnderzoek(inhoud.getIndicatieGezagMinderjarige(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_3210));
        builder.indicatieCurateleregister(toevoegenOnderzoek(inhoud.getIndicatieCurateleregister(), onderzoek, herkomst, Lo3ElementEnum.ELEMENT_3310));

        final Lo3Historie historie = toevoegenOnderzoekHistorie(voorkomen.getHistorie(), onderzoek, herkomst);
        final Lo3Documentatie documentatie = toevoegenOnderzoekDocumentatie(voorkomen.getDocumentatie(), onderzoek, herkomst);
        return new Lo3Categorie<>(builder.build(), documentatie, onderzoek, historie, herkomst);
    }
}
