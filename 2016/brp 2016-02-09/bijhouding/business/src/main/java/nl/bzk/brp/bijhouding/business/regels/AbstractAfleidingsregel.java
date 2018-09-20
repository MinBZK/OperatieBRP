/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;

import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Een abstracte implementatie van een afleidingsregel, getypeerd op het soort
 * root object waar hij op werkt.
 *
 * @param <M> het type root object model
 */
@Configurable
public abstract class AbstractAfleidingsregel<M extends HisVolledigRootObject> implements Afleidingsregel {

    /** Lege lijst constante om te gebruiken als return waarde voor geen verdere afleidingen (en geen meldingen). */
    protected static final AfleidingResultaat GEEN_VERDERE_AFLEIDINGEN = new AfleidingResultaat();

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    private final Set<PersoonHisVolledigImpl> extraBijgehoudenPersonen;
    private final Set<PersoonHisVolledigImpl>     extraAangemaakteNietIngeschrevenen;

    private final M          model;
    private final ActieModel actie;

    /**
     * Constructor.
     *
     * @param model het model
     * @param actie de actie
     */
    public AbstractAfleidingsregel(final M model, final ActieModel actie) {
        this.model = model;
        this.actie = actie;
        this.extraBijgehoudenPersonen = new HashSet<>();
        this.extraAangemaakteNietIngeschrevenen = new HashSet<>();
    }

    protected final M getModel() {
        return model;
    }

    protected final ActieModel getActie() {
        return actie;
    }

    protected final ReferentieDataRepository getReferentieDataRepository() {
        return referentieDataRepository;
    }

    @Override
    public final Set<PersoonHisVolledigImpl> getExtraBijgehoudenPersonen() {
        return this.extraBijgehoudenPersonen;
    }

    @Override
    public final Set<PersoonHisVolledigImpl> getExtraAangemaakteNietIngeschrevenen() {
        return this.extraAangemaakteNietIngeschrevenen;
    }

    /**
     * Voeg een extra bijgehouden persoon toe.
     *
     * @param persoon persoon
     */
    protected final void voegExtraBijgehoudenPersoonToe(final PersoonHisVolledigImpl persoon) {
        this.extraBijgehoudenPersonen.add(persoon);
    }

    /**
     * Voeg een extra aangemaakt persoon toe.
     *
     * @param persoon persoon
     */
    protected final void voegExtraAangemaakteNietIngeschreveneToe(final PersoonHisVolledigImpl persoon) {
        if (persoon.getSoort().getWaarde() != SoortPersoon.NIET_INGESCHREVENE) {
            throw new IllegalArgumentException(
                "Alleen niet ingeschrevenen mogen extra worden aangemaakt en toegevoegd.");
        }
        this.extraAangemaakteNietIngeschrevenen.add(persoon);
    }

    /**
     * Maak een lijst van vervolg afleidingsregels.
     *
     * @param afleidingsregels de regels
     * @return de regels als list
     */
    protected final AfleidingResultaat vervolgAfleidingen(final Afleidingsregel ... afleidingsregels) {
        final AfleidingResultaat afleidingResultaat = new AfleidingResultaat();
        for (final Afleidingsregel afleidingsregel : afleidingsregels) {
            afleidingResultaat.voegVervolgAfleidingToe(afleidingsregel);
        }
        return afleidingResultaat;
    }

}
