/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;

/**
 * Het async berichtresultaat.
 */
final class MaakSynchronisatieBerichtResultaat {

    private final SynchronisatieVerzoek verzoek;
    private Autorisatiebundel autorisatiebundel;
    private Partij zendendePartij;
    private Persoonslijst persoonslijst;
    private final List<Melding> meldingList = Lists.newLinkedList();

    /**
     * Constructor.
     * @param verzoek het synchronisatie verzoek
     */
    MaakSynchronisatieBerichtResultaat(final SynchronisatieVerzoek verzoek) {
        this.verzoek = verzoek;
    }

    /**
     * @return de autorisatie
     */
    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    /**
     * @param autorisatiebundel de autorisatiebundel
     */
    public void setAutorisatiebundel(final Autorisatiebundel autorisatiebundel) {
        this.autorisatiebundel = autorisatiebundel;
    }

    /**
     * @return de zendende partij
     */
    public Partij getZendendePartij() {
        return zendendePartij;
    }

    /**
     * @param zendendePartij de zendende partij
     */
    public void setZendendePartij(final Partij zendendePartij) {
        this.zendendePartij = zendendePartij;
    }

    /**
     * @return de persoon die gesynchroniseerd wordt
     */
    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    /**
     * @param persoonslijst de persoon die gesynchroniseerd wordt
     */
    public void setPersoonslijst(final Persoonslijst persoonslijst) {
        this.persoonslijst = persoonslijst;
    }

    /**
     * @return het synchronisatieverzoek
     */
    public SynchronisatieVerzoek getVerzoek() {
        return verzoek;
    }

    /**
     * @return de lijst met meldingen
     */
    public List<Melding> getMeldingList() {
        return meldingList;
    }
}
