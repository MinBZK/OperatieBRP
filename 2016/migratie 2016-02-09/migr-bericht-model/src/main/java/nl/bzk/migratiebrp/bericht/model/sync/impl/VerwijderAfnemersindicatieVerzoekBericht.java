/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Verwijder afnemersindicatie verzoek.
 */
public final class VerwijderAfnemersindicatieVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final VerwerkAfnemersindicatieVerzoekType verwerkAfnemersindicatieVerzoekType;

    /**
     * Default constructor.
     */
    public VerwijderAfnemersindicatieVerzoekBericht() {
        this(new VerwerkAfnemersindicatieVerzoekType());
    }

    /**
     * JAXB constructor.
     *
     * @param verwerkAfnemersindicatieVerzoekType
     *            Het verwerk afnemersindicatie verzoek type {@link VerwerkAfnemersindicatieVerzoekType}
     */
    public VerwijderAfnemersindicatieVerzoekBericht(final VerwerkAfnemersindicatieVerzoekType verwerkAfnemersindicatieVerzoekType) {
        super("VerwijderAfnemersindicatieVerzoek");
        this.verwerkAfnemersindicatieVerzoekType = verwerkAfnemersindicatieVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createVerwijderAfnemersindicatieVerzoek(verwerkAfnemersindicatieVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van persoon id.
     *
     * @return persoon id
     */
    public Integer getPersoonId() {
        return verwerkAfnemersindicatieVerzoekType.getPersoonId();
    }

    /**
     * Geef de waarde van toegang leveringsautorisatie id.
     *
     * @return toegang leveringsautorisatie id
     */
    public Integer getToegangLeveringsautorisatieId() {
        return verwerkAfnemersindicatieVerzoekType.getToegangLeveringsautorisatieId();
    }

    /**
     * Geef de waarde van dienst id.
     *
     * @return dienst id
     */
    public Integer getDienstId() {
        return verwerkAfnemersindicatieVerzoekType.getDienstId();
    }

    /**
     * Zet de waarde van persoon id.
     *
     * @param persoonId
     *            persoon id
     */
    public void setPersoonId(final Integer persoonId) {
        verwerkAfnemersindicatieVerzoekType.setPersoonId(persoonId);
    }

    /**
     * Zet de waarde van toegang leveringsautorisatie id.
     *
     * @param toegangleveringsautorisatieId
     *            toegang leveringsautorisatie id
     */
    public void setToegangLeveringsautorisatieId(final int toegangleveringsautorisatieId) {
        verwerkAfnemersindicatieVerzoekType.setToegangLeveringsautorisatieId(toegangleveringsautorisatieId);
    }

    /**
     * Zet de waarde van dienst id.
     *
     * @param dienstId
     *            dienst id
     */
    public void setDienstId(final Integer dienstId) {
        verwerkAfnemersindicatieVerzoekType.setDienstId(dienstId);
    }

}
