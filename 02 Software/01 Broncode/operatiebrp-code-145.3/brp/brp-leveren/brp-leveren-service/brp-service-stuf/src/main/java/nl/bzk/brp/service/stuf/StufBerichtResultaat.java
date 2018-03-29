/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Het resultaat van een stuf bericht verzoek.
 */
public final class StufBerichtResultaat {

    private final StufBerichtVerzoek verzoek;
    private Partij brpPartij;
    private String referentienummerAntwoordbericht = UUID.randomUUID().toString();
    private ZonedDateTime tijdstipVerzending = DatumUtil.nuAlsZonedDateTime();
    private final List<Melding> meldingen = Lists.newArrayList();
    private List<StufTransformatieVertaling> stufVertalingen = Lists.newArrayList();
    private Autorisatiebundel autorisatiebundel;

    /**
     * Constructor.
     * @param verzoek het stuf bericht verzoek
     */
    StufBerichtResultaat(final StufBerichtVerzoek verzoek) {
        this.verzoek = verzoek;
    }

    /**
     * Gets meldingen.
     * @return the meldingen
     */
    List<Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Gets verzoek.
     * @return the verzoek
     */
    StufBerichtVerzoek getVerzoek() {
        return verzoek;
    }

    /**
     * Gets referentienummer antwoordbericht.
     * @return the referentienummer antwoordbericht
     */
    public String getReferentienummerAntwoordbericht() {
        return referentienummerAntwoordbericht;
    }

    /**
     * Gets tijdstip verzending.
     * @return the tijdstip verzending
     */
    public ZonedDateTime getTijdstipVerzending() {
        return tijdstipVerzending;
    }

    /**
     * Gets brp partij.
     * @return the brp partij
     */
    public Partij getBrpPartij() {
        return brpPartij;
    }

    /**
     * Sets brp partij.
     * @param brpPartij the brp partij
     */
    public void setBrpPartij(final Partij brpPartij) {
        this.brpPartij = brpPartij;
    }

    /**
     * Gets stuf vertalingen.
     * @return stufVertalingen stuf vertalingen
     */
    public List<StufTransformatieVertaling> getStufVertalingen() {
        return stufVertalingen;
    }

    /**
     * Sets stuf vertalingen.
     * @param stufVertalingen stufVertalingen
     */
    public void setStufVertalingen(List<StufTransformatieVertaling> stufVertalingen) {
        this.stufVertalingen = stufVertalingen;
    }

    /**
     * Gets autorisatiebundel.
     * @return the autorisatiebundel
     */
    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    /**
     * Sets autorisatiebundel.
     * @param autorisatiebundel the autorisatiebundel
     */
    public void setAutorisatiebundel(Autorisatiebundel autorisatiebundel) {
        this.autorisatiebundel = autorisatiebundel;
    }
}
