/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;

/**
 * De basis DTO voor bevraging resultaten.
 */
public class BevragingResultaat {

    private final List<Melding> meldingen = Lists.newLinkedList();
    private Autorisatiebundel autorisatiebundel;
    private VerwerkPersoonBericht bericht;
    private Partij brpPartij;


    /**
     * Gets autorisatiebundel.
     * @return the autorisatiebundel
     */
    public final Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    /**
     * Sets autorisatiebundel.
     * @param autorisatiebundel the autorisatiebundel
     */
    public final void setAutorisatiebundel(final Autorisatiebundel autorisatiebundel) {
        this.autorisatiebundel = autorisatiebundel;
    }

    /**
     * Gets bericht.
     * @return the bericht
     */
    public final VerwerkPersoonBericht getBericht() {
        return bericht;
    }

    /**
     * Sets bericht.
     * @param bericht the bericht
     */
    public final void setBericht(final VerwerkPersoonBericht bericht) {
        this.bericht = bericht;
    }

    /**
     * Gets meldingen.
     * @return the meldingen
     */
    public final List<Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Gets brp partij.
     * @return the brp partij
     */
    public final Partij getBrpPartij() {
        return brpPartij;
    }

    /**
     * Sets brp partij.
     * @param brpPartij the brp partij
     */
    public final void setBrpPartij(final Partij brpPartij) {
        this.brpPartij = brpPartij;
    }
}
