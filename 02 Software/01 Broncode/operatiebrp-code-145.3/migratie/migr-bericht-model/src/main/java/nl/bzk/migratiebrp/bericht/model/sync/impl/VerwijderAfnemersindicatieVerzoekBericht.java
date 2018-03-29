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
     * @param verwerkAfnemersindicatieVerzoekType Het verwerk afnemersindicatie verzoek type {@link VerwerkAfnemersindicatieVerzoekType}
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
     * Geef de waarde van BSN.
     * @return BSN
     */
    public String getBsn() {
        return verwerkAfnemersindicatieVerzoekType.getBsn();
    }

    /**
     * Geeft de partijCode op het bericht terug.
     * @return De partijCode op het bericht.
     */
    public String getPartijCode() {
        return verwerkAfnemersindicatieVerzoekType.getPartijCode();
    }

    /**
     * Zet de waarde van BSN.
     * @param bsn BSN
     */
    public void setBsn(final String bsn) {
        verwerkAfnemersindicatieVerzoekType.setBsn(bsn);
    }

    /**
     * Zet de partijCode op het bericht.
     * @param partijCode De te zetten partijCode.
     */
    public void setPartijCode(final String partijCode) {
        verwerkAfnemersindicatieVerzoekType.setPartijCode(partijCode);
    }
}
