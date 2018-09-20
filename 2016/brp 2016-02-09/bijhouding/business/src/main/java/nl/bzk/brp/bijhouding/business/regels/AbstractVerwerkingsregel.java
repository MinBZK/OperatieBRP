/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Een abstracte implementatie van een verwerkingsregel, getypeerd op het soort
 * object types waar hij op werkt.
 *
 * @param <B> het bericht object type
 * @param <M> het model object type
 */
@Configurable
public abstract class AbstractVerwerkingsregel<B extends BerichtEntiteit, M extends HisVolledigImpl> implements Verwerkingsregel {
    @Inject
    private ReferentieDataRepository referentieDataRepository;

    private final B bericht;
    private final M model;
    private final ActieModel actie;
    private final List<Afleidingsregel> afleidingsregels;

    /**
     * Constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public AbstractVerwerkingsregel(final B bericht, final M model, final ActieModel actie) {
        this.bericht = bericht;
        this.model = model;
        this.actie = actie;
        this.afleidingsregels = new ArrayList<>();
    }

    protected final B getBericht() {
        return bericht;
    }

    protected final M getModel() {
        return model;
    }

    protected final ActieModel getActie() {
        return actie;
    }

    /**
     * Geeft de afleidingsregels terug.
     * Deze methode niet overriden in subklasses (vandaar final), gebruik een aanroep van
     * <code>voegAfleidingsregelToe</code> in een override van <code>verzamelAfleidingsregels</code>.
     *
     * @return de afleidingsregels
     */
    @Override
    public final List<Afleidingsregel> getAfleidingsregels() {
        return this.afleidingsregels;
    }

    /**
     * Voeg een afleidingsregel toe.
     *
     * @param afleidingsregel de regel
     */
    @Override
    public final void voegAfleidingsregelToe(final Afleidingsregel afleidingsregel) {
        this.afleidingsregels.add(afleidingsregel);
    }

    protected final ReferentieDataRepository getReferentieDataRepository() {
        return this.referentieDataRepository;
    }

    @Override
    public void verrijkBericht() {
        // Default lege implementatie voor verrijk bericht, om de verwerkers niet
        // 'aan te moedigen' aan berichtverrijking te doen. :)
        // Override deze methode in een subklasse verwerker indien verrijking echt nodig is.
    }

    @Override
    public void verzamelAfleidingsregels() {
        // Default lege implementatie. Meestal geen afleidingsregels nodig.
        // Override in subklasse indien toch nodig.
    }
}
