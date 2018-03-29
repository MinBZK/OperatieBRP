/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Antwoord op ad hoc zoek vraag.
 */
public class AdHocZoekPersoonAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AdHocZoekPersoonAntwoordType adHocZoekPersoonAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AdHocZoekPersoonAntwoordBericht() {
        this(new AdHocZoekPersoonAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param adHocZoekPersoonAntwoordType het adHocZoekPersoonAntwoord type
     */
    public AdHocZoekPersoonAntwoordBericht(final AdHocZoekPersoonAntwoordType adHocZoekPersoonAntwoordType) {
        super("adHocZoekPersoonAntwoord");
        this.adHocZoekPersoonAntwoordType = adHocZoekPersoonAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAdHocZoekPersoonAntwoord(adHocZoekPersoonAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * @return de foutreden
     */
    public AdHocZoekAntwoordFoutReden getFoutreden() {
        return adHocZoekPersoonAntwoordType.getFoutreden();
    }

    /**
     * Zet foutreden.
     * @param adHocZoekAntwoordFoutReden de foutreden
     */
    public void setFoutreden(final AdHocZoekAntwoordFoutReden adHocZoekAntwoordFoutReden) {
        adHocZoekPersoonAntwoordType.setFoutreden(adHocZoekAntwoordFoutReden);
    }

    /**
     * @return de gevonden persoon
     */
    public String getInhoud() {
        return adHocZoekPersoonAntwoordType.getInhoud();
    }

    /**
     * Zet de gevonden persoon als lo3 tekst.
     * @param teletexString de gevonden persoon
     */
    public void setInhoud(final String teletexString) {
        adHocZoekPersoonAntwoordType.setInhoud(teletexString);
    }
}
