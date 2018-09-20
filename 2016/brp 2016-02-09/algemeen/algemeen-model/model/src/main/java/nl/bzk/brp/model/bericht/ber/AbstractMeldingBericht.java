/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.MeldingBasis;

/**
 * Het optreden van een soort melding naar aanleiding van het controleren van een Regel.
 *
 * Vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, en het kunnen garanderen van een
 * correcte werking van de BRP, worden inkomende berichten gecontroleerd door bepaalde wetmatigheden te controleren:
 * Regels. Als een Regel iets constateert, zal dat leiden tot een specifieke soort melding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractMeldingBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, MeldingBasis {

    private static final Integer META_ID = 6194;
    private RegelAttribuut regel;
    private SoortMeldingAttribuut soort;
    private MeldingtekstAttribuut melding;

    /**
     * {@inheritDoc}
     */
    @Override
    public RegelAttribuut getRegel() {
        return regel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMeldingAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MeldingtekstAttribuut getMelding() {
        return melding;
    }

    /**
     * Zet Regel van Melding.
     *
     * @param regel Regel.
     */
    public void setRegel(final RegelAttribuut regel) {
        this.regel = regel;
    }

    /**
     * Zet Soort van Melding.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortMeldingAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Melding van Melding.
     *
     * @param melding Melding.
     */
    public void setMelding(final MeldingtekstAttribuut melding) {
        this.melding = melding;
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
