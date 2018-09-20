/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.util.List;

/**
 * Model voor een Brp Relatie met Betrokkenheden.
 */
public class GgoBrpRelatie extends GgoBrpVoorkomen {
    private static final long serialVersionUID = 1L;

    private GgoStapel relatieInhoud;
    private GgoStapel relatieAfgeleidAdministratief;
    private List<GgoBetrokkenheid> betrokkenheden;
    private List<GgoStapel> istInhoud;

    /**
     * Constructor.
     */
    public GgoBrpRelatie() {
        super();
    }

    /**
     * Geef de waarde van relatie inhoud.
     *
     * @return the relatieInhoud
     */
    public final GgoStapel getRelatieInhoud() {
        return relatieInhoud;
    }

    /**
     * Zet de waarde van relatie inhoud.
     *
     * @param relatieInhoud
     *            the relatieInhoud to set
     */
    public final void setRelatieInhoud(final GgoStapel relatieInhoud) {
        this.relatieInhoud = relatieInhoud;
    }

    /**
     * Geef de waarde van relatie afgeleid administratief.
     *
     * @return de Relatie \ Afgeleid Administratief stapel
     */
    public final GgoStapel getRelatieAfgeleidAdministratief() {
        return relatieAfgeleidAdministratief;
    }

    /**
     * Zet de waarde van relatie afgeleid administratief.
     *
     * @param relatieAfgeleidAdministratief
     *            de Relatie \ Afgeleid Administratief stapel
     */
    public final void setRelatieAfgeleidAdministratief(final GgoStapel relatieAfgeleidAdministratief) {
        this.relatieAfgeleidAdministratief = relatieAfgeleidAdministratief;
    }

    /**
     * Geef de waarde van betrokkenheden.
     *
     * @return the betrokkenheden
     */
    public final List<GgoBetrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Zet de waarde van betrokkenheden.
     *
     * @param betrokkenheden
     *            the betrokkenheden to set
     */
    public final void setBetrokkenheden(final List<GgoBetrokkenheid> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    /**
     * Geef de waarde van ist inhoud.
     *
     * @return the istInhoud
     */
    public final List<GgoStapel> getIstInhoud() {
        return istInhoud;
    }

    /**
     * Zet de waarde van ist inhoud.
     *
     * @param istInhoud
     *            the istInhoud to set
     */
    public final void setIstInhoud(final List<GgoStapel> istInhoud) {
        this.istInhoud = istInhoud;
    }
}
