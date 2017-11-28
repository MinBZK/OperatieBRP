/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Het resultaat van een vrij bericht verzoek.
 */
public class VrijBerichtResultaat {

    //ROOD-2279 Deze structuur is hetzelfde voor alle synchrone resultaten
    private final VrijBerichtVerzoek verzoek;
    private Partij brpPartij;
    private String referentienummerAntwoordbericht = UUID.randomUUID().toString();
    private ZonedDateTime tijdstipVerzending = DatumUtil.nuAlsZonedDateTime();
    private final List<Melding> meldingen = Lists.newArrayList();

    /**
     * Constructor.
     * @param verzoek het vrije bericht verzoek
     */
    VrijBerichtResultaat(final VrijBerichtVerzoek verzoek) {
        this.verzoek = verzoek;
    }

    /**
     * Gets meldingen.
     * @return the meldingen
     */
  public  List<Melding> getMeldingen() {
        return meldingen;
    }

    /**
     * Gets verzoek.
     * @return the verzoek
     */
    VrijBerichtVerzoek getVerzoek() {
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
}
