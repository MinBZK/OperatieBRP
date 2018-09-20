/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.ggo.viewer.builder.brp.GgoBrpBuilder;
import nl.bzk.migratiebrp.ggo.viewer.builder.lo3.GgoLo3Builder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoResultaat;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoResultaatIndex;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.bzk.migratiebrp.ggo.viewer.util.NaamUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import org.springframework.stereotype.Component;

/**
 * Bouwt de GgoPersoonslijstGroep met alle onderliggende componenten.
 */
@Component
public class GgoPersoonslijstGroepBuilder {
    @Inject
    private GgoLo3Builder ggoLo3Builder;
    @Inject
    private GgoBrpBuilder ggoBrpBuilder;

    /**
     * Bouwt de GgoPersoonslijstGroep met alle onderliggende componenten.
     *
     * @param lo3Persoonslijst
     *            De te verwerken Lo3Persoonslijst
     * @param brpPersoonslijst
     *            De te verwerken BrpPersoonslijst
     * @param brpPersoon
     *            De te verwerken entitymodel persoonslijst
     *
     * @param teruggeconverteerd
     *            De te verwerken teruggeconverteerde Lo3Persoonslijst
     * @param verschillen
     *            De op te nemen matches
     * @param meldingen
     *            De op te nemen meldingen/signaleringen van BCM en Conversie
     * @param foutLog
     *            De foutlog meldingen welke zijn opgetreden tijdens de verwerking
     * @return Een nieuwe GgoPersoonslijstGroep
     */
    public final GgoPersoonslijstGroep build(
        final Lo3Persoonslijst lo3Persoonslijst,
        final BrpPersoonslijst brpPersoonslijst,
        final Persoon brpPersoon,
        final Lo3Persoonslijst teruggeconverteerd,
        final List<GgoVoorkomenVergelijking> verschillen,
        final List<GgoFoutRegel> meldingen,
        final List<GgoFoutRegel> foutLog)
    {
        // Build GGO Model
        final List<GgoStapel> ggoLo3PL = ggoLo3Builder.build(lo3Persoonslijst);
        final List<GgoStapel> ggoLo3PLTerugConversie = ggoLo3Builder.build(teruggeconverteerd);
        final List<GgoStapel> ggoBrpPL = ggoBrpBuilder.build(brpPersoonslijst, brpPersoon);

        // Bepaal overzicht index stats
        final List<GgoResultaatIndex> resultaatIndex = bepaalResultaatIndex(lo3Persoonslijst, brpPersoonslijst, meldingen, verschillen);

        // Resultaat
        final Long aNummer = lo3Persoonslijst != null ? lo3Persoonslijst.getActueelAdministratienummer() : null;
        return new GgoPersoonslijstGroep(
            aNummer,
            NaamUtil.createVolledigeNaam(lo3Persoonslijst),
            ggoLo3PL,
            ggoLo3PLTerugConversie,
            ggoBrpPL,
            verschillen,
            meldingen,
            resultaatIndex,
            foutLog);
    }

    /**
     * Bepaalt het resultaat van de conversie en voegt deze aan de index.
     *
     * @param lo3Persoonslijst
     *            De LO3 persoonslijst.
     * @param brpPersoonslijst
     *            De BRP persoonslijst.
     * @param foutRegels
     *            De foutregels.
     * @param verschillen
     *            De verschillen
     * @return Volledigenaam.
     */
    protected final List<GgoResultaatIndex> bepaalResultaatIndex(
        final Lo3Persoonslijst lo3Persoonslijst,
        final BrpPersoonslijst brpPersoonslijst,
        final List<GgoFoutRegel> foutRegels,
        final List<GgoVoorkomenVergelijking> verschillen)
    {
        final List<GgoResultaatIndex> resultaatIndex = new ArrayList<>();

        // inlezen pl
        if (lo3Persoonslijst == null) {
            resultaatIndex.add(new GgoResultaatIndex(foutRegels.size(), GgoResultaat.INLEZEN_GBA_PL_NIET_GESLAAGD));
        } else {
            // conversie
            if (brpPersoonslijst == null) {
                resultaatIndex.add(new GgoResultaatIndex(foutRegels.size(), GgoResultaat.CONVERSIE_NIET_GESLAAGD));
            } else {
                resultaatIndex.add(new GgoResultaatIndex(foutRegels.size(), GgoResultaat.CONVERSIE_GESLAAGD));
            }

            // terugconversie
            if (brpPersoonslijst != null) {
                if (verschillen == null) {
                    resultaatIndex.add(new GgoResultaatIndex(0, GgoResultaat.TERUGCONVERSIE_NIET_GESLAAGD));
                } else {
                    resultaatIndex.add(new GgoResultaatIndex(verschillen.size(), GgoResultaat.TERUGCONVERSIE_GESLAAGD));
                }
            }
        }

        return resultaatIndex;
    }
}
