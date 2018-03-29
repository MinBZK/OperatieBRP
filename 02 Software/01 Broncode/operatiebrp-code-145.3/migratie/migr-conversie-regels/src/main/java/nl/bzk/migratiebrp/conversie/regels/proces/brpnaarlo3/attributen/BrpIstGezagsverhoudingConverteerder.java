/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
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

/**
 * IST Gezagsverhouding converteerder.
 */
public final class BrpIstGezagsverhoudingConverteerder extends BrpIstAbstractConverteerder {

    /**
     * Constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpIstGezagsverhoudingConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        super(attribuutConverteerder);
    }

    /**
     * Converteert de IST Gezagsverhouding naar LO3.
     * @param brpStapel de {@link BrpStapel} met een {@link BrpIstGezagsVerhoudingGroepInhoud}
     * @return een {@link Lo3Stapel} met een {@link Lo3GezagsverhoudingInhoud}
     */
    public Lo3Stapel<Lo3GezagsverhoudingInhoud> converteerGezagsverhouding(final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> brpStapel) {
        if (brpStapel == null) {
            return null;
        }
        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> voorkomens = new ArrayList<>();
        for (final BrpGroep<BrpIstGezagsVerhoudingGroepInhoud> groep : brpStapel.getGroepen()) {
            final BrpIstGezagsVerhoudingGroepInhoud inhoud = groep.getInhoud();

            final Lo3GezagsverhoudingInhoud.Builder builder = new Lo3GezagsverhoudingInhoud.Builder();

            // Conversie mbhv converteerder
            builder.indicatieCurateleregister(getAttribuutConverteerder().converteerIndicatieCurateleRegister(inhoud.getIndicatieOnderCuratele()));

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

        voorkomens.sort(LO3_HERKOMST_COMPARATOR);

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

            final List<Lo3Onderzoek> onderzoeken = verzamelOnderzoeken(inhoud);
            builder.indicatieGezagMinderjarige(new Lo3IndicatieGezagMinderjarige(code.toString(), Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken)));
        }
    }

    private List<Lo3Onderzoek> verzamelOnderzoeken(final BrpIstGezagsVerhoudingGroepInhoud inhoud) {
        final List<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        if (bevatOnderzoek(inhoud.getIndicatieOuder1HeeftGezag())) {
            onderzoeken.add(inhoud.getIndicatieOuder1HeeftGezag().getOnderzoek());
        } else if (bevatOnderzoek(inhoud.getIndicatieOuder2HeeftGezag())) {
            onderzoeken.add(inhoud.getIndicatieOuder2HeeftGezag().getOnderzoek());
        } else if (bevatOnderzoek(inhoud.getIndicatieDerdeHeeftGezag())) {
            onderzoeken.add(inhoud.getIndicatieDerdeHeeftGezag().getOnderzoek());
        }
        return onderzoeken;
    }

    private boolean bevatOnderzoek(final BrpBoolean brpBoolean) {
        return brpBoolean != null && brpBoolean.getOnderzoek() != null;
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
