/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Key welke gebruikt wordt om een GgoBrpGroep te identificeren.
 */
public class GgoBrpVoorkomen extends GgoVoorkomen {
    private static final long serialVersionUID = 1L;

    private int brpStapelNr;
    private GgoBrpActie actieInhoud;
    private GgoBrpActie actieVerval;
    private GgoBrpActie actieGeldigheid;
    private final Set<GgoBrpOnderzoek> onderzoeken;

    /**
     * Constructor.
     */
    public GgoBrpVoorkomen() {
        super();
        setInhoud(new LinkedHashMap<String, String>());
        onderzoeken = new LinkedHashSet<>();
    }

    /**
     * Geef de waarde van brp stapel nr.
     *
     * @return the brpStapelNr
     */
    public final int getBrpStapelNr() {
        return brpStapelNr;
    }

    /**
     * Zet de waarde van brp stapel nr.
     *
     * @param brpStapelNr
     *            the brpStapelNr to set
     */
    public final void setBrpStapelNr(final int brpStapelNr) {
        this.brpStapelNr = brpStapelNr;
    }

    /**
     * Geef de waarde van actie inhoud.
     *
     * @return the actieInhoud
     */
    public final GgoBrpActie getActieInhoud() {
        return actieInhoud;
    }

    /**
     * Zet de waarde van actie inhoud.
     *
     * @param actieInhoud
     *            the actieInhoud to set
     */
    public final void setActieInhoud(final GgoBrpActie actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    /**
     * Geef de waarde van actie verval.
     *
     * @return the actieVerval
     */
    public final GgoBrpActie getActieVerval() {
        return actieVerval;
    }

    /**
     * Zet de waarde van actie verval.
     *
     * @param actieVerval
     *            the actieVerval to set
     */
    public final void setActieVerval(final GgoBrpActie actieVerval) {
        this.actieVerval = actieVerval;
    }

    /**
     * Geef de waarde van actie geldigheid.
     *
     * @return the actieGeldigheid
     */
    public final GgoBrpActie getActieGeldigheid() {
        return actieGeldigheid;
    }

    /**
     * Zet de waarde van actie geldigheid.
     *
     * @param actieGeldigheid
     *            the actieGeldigheid to set
     */
    public final void setActieGeldigheid(final GgoBrpActie actieGeldigheid) {
        this.actieGeldigheid = actieGeldigheid;
    }

    /**
     * Geef de waarde van onderzoeken.
     *
     * @return the onderzoeken
     */
    public final Set<GgoBrpOnderzoek> getOnderzoeken() {
        return onderzoeken;
    }

    /**
     * @param onderzoek
     *            Het toe te voegen onderzoek
     */
    public final void addOnderzoek(final GgoBrpOnderzoek onderzoek) {
        onderzoeken.add(onderzoek);
    }
}
