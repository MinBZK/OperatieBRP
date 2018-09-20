/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.predikaat.MagHistorieTonenPredikaat;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.apache.commons.collections.functors.OrPredicate;
import org.apache.commons.collections.functors.TruePredicate;


/**
 * Abstract super klasse voor alle his volledig predikaat views. Bevat data en logica die generiek is voor alle dergelijke views.
 */
public abstract class AbstractHisVolledigPredikaatView {

    // Handmatig toegevoegd: TEAMBRP-1597
    private final List<Predicate> predikaten = new ArrayList<>();

    /**
     * Vlaggetje dat bepaalt of dit object zichtbaar moet zijn in de output, bijv. een XML bericht.
     */
    private boolean zichtbaar = true;

    // Handmatig toegevoegd: TEAMBRP-1597

    /**
     * Instantieert een nieuwe abstract his volledig predikaat view met een predikaat.
     *
     * @param predikaat het predikaat
     */
    protected AbstractHisVolledigPredikaatView(final Predicate predikaat) {
        if (predikaat != null) {
            predikaten.add(predikaat);
        }
    }

    // Handmatig toegevoegd: TEAMBRP-1597

    /**
     * Geeft het predikaat terug van het type {@link OrPredicate}.
     *
     * @return het predikaat
     */
    public Predicate getPredikaat() {
        if (predikaten.isEmpty()) {
            predikaten.add(TruePredicate.getInstance());
        }

        return new AllPredicate(predikaten.toArray(new Predicate[predikaten.size()]));
    }

    /**
     * Haal een predikaat van een bepaald type uit de lijst van predikaten.
     * @param klasse de klasse van het predikaat dat gewenst is.
     * @param <T> het type van het gewenste predikaat.
     * @return het gevonden predikaat en anders null.
     */
    public final <T extends Predicate> T getPredikaatVanType(Class<T> klasse) {
        for (final Predicate predikaat : predikaten) {
            if (predikaat instanceof AllPredicate) {
                final AllPredicate allPredicate = (AllPredicate) predikaat;
                for (Predicate predicate : allPredicate.getPredicates()) {
                    if (klasse.isInstance(predicate)) {
                        return klasse.cast(predicate);
                    }
                }
            } else if (klasse.isInstance(predikaat)) {
                return klasse.cast(predikaat);
            }
        }
        return null;
    }

    protected final Predicate getHistorischPredikaat() {
        final MagHistorieTonenPredikaat predikaat = getPredikaatVanType(MagHistorieTonenPredikaat.class);
        return predikaat != null ? new MagHistorieTonenPredikaat(predikaat.getDienstbundelGroepen(), false) : TruePredicate.getInstance();
    }

    // Handmatig toegevoegd: TEAMBRP-1597

    /**
     * Voegt een predikaat toe.
     *
     * @param predikaat het toe te voegen predikaat
     */
    public void voegPredikaatToe(final Predicate predikaat) {
        predikaten.add(predikaat);
    }

    /**
     * Vlaggetje dat bepaalt of dit object zichtbaar moet zijn in de output, bijv. een XML bericht.
     *
     * @return wel of niet zichtbaar
     */
    public boolean isZichtbaar() {
        return zichtbaar;
    }

    /**
     * Vlaggetje dat bepaalt of dit object zichtbaar moet zijn in de output, bijv. een XML bericht.
     *
     * @param zichtbaar wel of niet zichtbaar
     */
    public void setZichtbaar(final boolean zichtbaar) {
        this.zichtbaar = zichtbaar;
    }
}
