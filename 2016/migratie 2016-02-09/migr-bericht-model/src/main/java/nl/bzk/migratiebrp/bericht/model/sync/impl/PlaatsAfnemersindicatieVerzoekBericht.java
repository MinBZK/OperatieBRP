/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PlaatsAfnemersindicatieVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Plaats afnemersindicatie verzoek.
 */
public final class PlaatsAfnemersindicatieVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final PlaatsAfnemersindicatieVerzoekType plaatsAfnemersindicatieVerzoekType;

    /**
     * Default constructor.
     */
    public PlaatsAfnemersindicatieVerzoekBericht() {
        this(new PlaatsAfnemersindicatieVerzoekType());
    }

    /**
     * JAXB constructor.
     *
     * @param plaatsAfnemersindicatieVerzoekType
     *            Het plaats afnemersindicatie verzoek type {@link PlaatsAfnemersindicatieVerzoekType}
     */
    public PlaatsAfnemersindicatieVerzoekBericht(final PlaatsAfnemersindicatieVerzoekType plaatsAfnemersindicatieVerzoekType) {
        super("PlaatsAfnemersindicatieVerzoek");
        this.plaatsAfnemersindicatieVerzoekType = plaatsAfnemersindicatieVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createPlaatsAfnemersindicatieVerzoek(plaatsAfnemersindicatieVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van persoon id.
     *
     * @return persoon id
     */
    public Integer getPersoonId() {
        return plaatsAfnemersindicatieVerzoekType.getPersoonId();
    }

    /**
     * Geef de waarde van toegang leveringsautorisatie id.
     *
     * @return toegang leveringsautorisatie id
     */
    public Integer getToegangLeveringsautorisatieId() {
        return plaatsAfnemersindicatieVerzoekType.getToegangLeveringsautorisatieId();
    }

    /**
     * Geef de waarde van dienst id.
     *
     * @return dienst id
     */
    public Integer getDienstId() {
        return plaatsAfnemersindicatieVerzoekType.getDienstId();
    }

    /**
     * Geef de waarde van referentie.
     *
     * @return referentie
     */
    public String getReferentie() {
        return plaatsAfnemersindicatieVerzoekType.getReferentie();
    }

    /**
     * Zet de waarde van persoon id.
     *
     * @param persoonId
     *            persoon id
     */
    public void setPersoonId(final Integer persoonId) {
        plaatsAfnemersindicatieVerzoekType.setPersoonId(persoonId);
    }

    /**
     * Zet de waarde van toegang leveringsautorisatie id.
     *
     * @param toegangleveringsautorisatieId
     *            toegang leveringsautorisatie id
     */
    public void setToegangLeveringsautorisatieId(final int toegangleveringsautorisatieId) {
        plaatsAfnemersindicatieVerzoekType.setToegangLeveringsautorisatieId(toegangleveringsautorisatieId);
    }

    /**
     * Zet de waarde van dienst id.
     *
     * @param dienstId
     *            dienst id
     */
    public void setDienstId(final Integer dienstId) {
        plaatsAfnemersindicatieVerzoekType.setDienstId(dienstId);
    }

    /**
     * Zet de waarde van referentie.
     *
     * @param referentie
     *            referentie
     */
    public void setReferentie(final String referentie) {
        plaatsAfnemersindicatieVerzoekType.setReferentie(referentie);
    }

}
