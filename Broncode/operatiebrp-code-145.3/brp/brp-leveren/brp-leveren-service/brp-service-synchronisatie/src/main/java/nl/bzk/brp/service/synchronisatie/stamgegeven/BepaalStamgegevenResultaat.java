/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;

/**
 * Het resultaatobject tbv het valideren van het synchroniseer stamgegeven verzoek.
 * <p>
 */
public final class BepaalStamgegevenResultaat {

    private final List<Melding> meldingList = Lists.newArrayList();
    private final SynchronisatieVerzoek verzoek;
    private Autorisatiebundel autorisatiebundel;
    private Partij brpPartij;
    private StamtabelGegevens stamgegevens;

    /**
     * Constructor.
     * @param verzoek het verzoek
     */
    public BepaalStamgegevenResultaat(final SynchronisatieVerzoek verzoek) {
        this.verzoek = verzoek;
    }

    /**
     * @return lijst meldingen die eventueel zijn opgetreden bij het verwerken van het verzoek
     */
    public List<Melding> getMeldingList() {
        return meldingList;
    }

    /**
     * @return het verzoek
     */
    public SynchronisatieVerzoek getVerzoek() {
        return verzoek;
    }


    /**
     * @return de autorisatie
     */
    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }


    /**
     * Zet de autorisatie.
     * @param autorisatiebundel de autorisatie
     */
    public void setAutorisatiebundel(final Autorisatiebundel autorisatiebundel) {
        this.autorisatiebundel = autorisatiebundel;
    }

    /**
     * @return lijst met stamgegevens
     */
    public StamtabelGegevens getStamgegevens() {
        return stamgegevens;
    }

    /**
     * Zet de stamgegevens behorende bij de tabel.
     * @param stamgegevens de stamgegevens
     */
    public void setStamgegevens(final StamtabelGegevens stamgegevens) {
        this.stamgegevens = stamgegevens;
    }

    public Partij getBrpPartij() {
        return brpPartij;
    }

    public void setBrpPartij(final Partij brpPartij) {
        this.brpPartij = brpPartij;
    }
}
