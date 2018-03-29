/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Het resultaat van het een afnemerindicatieverzoek.
 */
final class OnderhoudResultaat {

    private final AfnemerindicatieVerzoek verzoek;
    private final List<Melding> meldingList = Lists.newLinkedList();
    private Autorisatiebundel autorisatiebundel;
    private Partij brpPartij;
    private Persoonslijst persoonslijst;
    private ZonedDateTime tijdstipRegistratie = DatumUtil.nuAlsZonedDateTime();
    private String referentienummerAntwoordbericht = UUID.randomUUID().toString();
    private ZonedDateTime tijdstipVerzending = DatumUtil.nuAlsZonedDateTime();


    /**
     * Constructor.
     * @param verzoek het verzoek
     */
    OnderhoudResultaat(final AfnemerindicatieVerzoek verzoek) {
        this.verzoek = verzoek;
    }

    /**
     * @return het verzoek
     */
    public AfnemerindicatieVerzoek getVerzoek() {
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
     * @return de persoon waarvoor de afnemerindicatie geplaatst wordt
     */
    public Persoonslijst getPersoonslijst() {
        return persoonslijst;
    }

    /**
     * Zet de persoon waarvoor de afnemerindicatie geplaatst wordt.
     * @param persoonslijst de persoon
     */
    public void setPersoonslijst(final Persoonslijst persoonslijst) {
        this.persoonslijst = persoonslijst;
    }

    /**
     * @return lijst met melding die mogelijk zijn opgetreden tijden het afhandelen van het verzoek.
     */
    public List<Melding> getMeldingList() {
        return meldingList;
    }

    /**
     * Geeft het referentienummer van het antwoord bericht.
     * @return referentienummerAntwoordbericht
     */
    public String getReferentienummerAntwoordbericht() {
        return referentienummerAntwoordbericht;
    }

    /**
     * Geeft het tijdstip van verzending.
     * @return tijdstip van verzending
     */
    public ZonedDateTime getTijdstipVerzending() {
        return tijdstipVerzending;
    }

    /**
     * @param tijdstipVerzending het tijdstip van verzending
     */
    public void setTijdstipVerzending(final ZonedDateTime tijdstipVerzending) {
        this.tijdstipVerzending = tijdstipVerzending;
    }

    /**
     * Geeft het tijdstip van registratie.
     * @return tijdstip van registratie
     */
    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * @param tijdstipRegistratie het tijdstip plaatsen afnemerindicatie
     */
    public void setTijdstipRegistratie(final ZonedDateTime tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    public Partij getBrpPartij() {
        return brpPartij;
    }

    public void setBrpPartij(final Partij brpPartij) {
        this.brpPartij = brpPartij;
    }
}
