/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Antwoord op ad hoc zoek vraag adres.
 */
public class AdHocZoekPersonenOpAdresAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AdHocZoekPersonenOpAdresAntwoordType adHocZoekPersonenOpAdresAntwoordType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AdHocZoekPersonenOpAdresAntwoordBericht() {
        this(new AdHocZoekPersonenOpAdresAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param adHocZoekPersonenOpAdresAntwoordType het adHocZoekPersonenOpAdresAntwoordType type
     */
    public AdHocZoekPersonenOpAdresAntwoordBericht(final AdHocZoekPersonenOpAdresAntwoordType adHocZoekPersonenOpAdresAntwoordType) {
        super("adHocZoekPersonenOpAdresAntwoord");
        this.adHocZoekPersonenOpAdresAntwoordType = adHocZoekPersonenOpAdresAntwoordType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAdHocZoekPersonenOpAdresAntwoord(adHocZoekPersonenOpAdresAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * @return de foutreden
     */
    public AdHocZoekAntwoordFoutReden getFoutreden() {
        return adHocZoekPersonenOpAdresAntwoordType.getFoutreden();
    }

    /**
     * Zet foutreden.
     * @param adHocZoekAntwoordFoutReden de foutreden
     */
    public void setFoutreden(final AdHocZoekAntwoordFoutReden adHocZoekAntwoordFoutReden) {
        adHocZoekPersonenOpAdresAntwoordType.setFoutreden(adHocZoekAntwoordFoutReden);
    }

    /**
     * @return de gevonden persoon
     */
    public String getInhoud() {
        return adHocZoekPersonenOpAdresAntwoordType.getInhoud();
    }

    /**
     * Zet de gevonden persoon als lo3 tekst.
     * @param teletexString de gevonden persoon
     */
    public void setInhoud(final String teletexString) {
        adHocZoekPersonenOpAdresAntwoordType.setInhoud(teletexString);
    }
}
