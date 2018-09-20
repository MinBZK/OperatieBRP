/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingssituatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.BijhoudingsplanPersoonBasis;

/**
 * Personen die betrokken zijn bij de verwerking van een bijhoudingsplan.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBijhoudingsplanPersoonBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        BijhoudingsplanPersoonBasis
{

    private static final Integer META_ID = 21487;
    private BijhoudingsplanBericht bijhoudingsplan;
    private PersoonBericht persoon;
    private PartijAttribuut bijhoudingspartij;
    private BijhoudingssituatieAttribuut situatie;

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingsplanBericht getBijhoudingsplan() {
        return bijhoudingsplan;
    }

    /**
     * {@inheritDoc}
     */
    //Handmatige override
    //@Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    //Handmatige override
    //@Override
    public PartijAttribuut getBijhoudingspartij() {
        return bijhoudingspartij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingssituatieAttribuut getSituatie() {
        return situatie;
    }

    /**
     * Zet Bijhoudingsplan van Bijhoudingsplan \ Persoon.
     *
     * @param bijhoudingsplan Bijhoudingsplan.
     */
    public void setBijhoudingsplan(final BijhoudingsplanBericht bijhoudingsplan) {
        this.bijhoudingsplan = bijhoudingsplan;
    }

    /**
     * Zet Persoon van Bijhoudingsplan \ Persoon.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Bijhoudingspartij van Bijhoudingsplan \ Persoon.
     *
     * @param bijhoudingspartij Bijhoudingspartij.
     */
    public void setBijhoudingspartij(final PartijAttribuut bijhoudingspartij) {
        this.bijhoudingspartij = bijhoudingspartij;
    }

    /**
     * Zet Situatie van Bijhoudingsplan \ Persoon.
     *
     * @param situatie Situatie.
     */
    public void setSituatie(final BijhoudingssituatieAttribuut situatie) {
        this.situatie = situatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }

}
