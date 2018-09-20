/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import org.springframework.test.util.ReflectionTestUtils;

public final class PersoonViewUtil {

    private PersoonViewUtil() {
        // Private constructor
    }

    /**
     * Zet alle mag geleverd worden vlaggen op attributen.
     *
     * @param persoonView persoon view
     */
    public static void zetAlleMagGeleverdWordenVlaggen(final PersoonHisVolledigView persoonView) {
        for (final HistorieEntiteit historieEntiteit : persoonView.getTotaleLijstVanHisElementenOpPersoonsLijst()) {
            zetVlaggen(historieEntiteit);
        }

        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonView.getBetrokkenheden()) {
            betrokkenheidHisVolledigView.setMagLeveren(true);
            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            relatie.setMagLeveren(true);
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                final BetrokkenheidHisVolledigView betrokkenheid = (BetrokkenheidHisVolledigView) betrokkenheidHisVolledig;
                betrokkenheid.setMagLeveren(true);
                final PersoonHisVolledig persoon = betrokkenheid.getPersoon();
                final PersoonHisVolledigView persoonHisVolledigView = (PersoonHisVolledigView) persoon;
                persoonHisVolledigView.setMagLeveren(true);
            }
        }

        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoonView.getOnderzoeken()) {
            for (final HisOnderzoekModel hisOnderzoekModel : persoonOnderzoekHisVolledig.getOnderzoek().getOnderzoekHistorie()) {
                zetVlaggen(hisOnderzoekModel);
            }
            for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : persoonOnderzoekHisVolledig.getOnderzoek().getGegevensInOnderzoek()) {
                if (gegevenInOnderzoekHisVolledig.getElement() != null) {
                    gegevenInOnderzoekHisVolledig.getElement().setMagGeleverdWorden(true);
                }
                if (gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven() != null) {
                    gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven().setMagGeleverdWorden(true);
                }
                if (gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven() != null) {
                    gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven().setMagGeleverdWorden(true);
                }
            }
        }
    }

    private static void zetVlaggen(final HistorieEntiteit historieEntiteit) {
        for (final Field field : getInheritedFields(historieEntiteit.getClass())) {
            field.setAccessible(true);
            if (Attribuut.class.isAssignableFrom(field.getType())) {
                final Attribuut attribuut = (Attribuut) ReflectionTestUtils.getField(historieEntiteit, field.getName());
                zetMagGeleverdWordenVlagOpAttribuut(attribuut);
            }
        }
    }

    /**
     * Zet mag geleverd worden vlag op attribuut.
     *
     * @param attribuut het attribuut
     */
    public static void zetMagGeleverdWordenVlagOpAttribuut(final Attribuut attribuut) {
        if (attribuut != null) {
            ReflectionTestUtils.setField(attribuut, "magGeleverdWorden", true);
        }
    }

    /**
     * Bepaalt alle velden, inclusief de velden in eventuele superklasses.
     *
     * @param type De klasse.
     * @return De velden.
     */
    private static List<Field> getInheritedFields(final Class<?> type) {
        final List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
