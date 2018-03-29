/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.AdHocZoekVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersoonVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Zoek persoon op basis van zoekcriteria.
 */
public class AdHocZoekPersoonVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie implements AdHocZoekVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private final AdHocZoekPersoonVerzoekType adHocZoekPersoonVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AdHocZoekPersoonVerzoekBericht() {
        this(new AdHocZoekPersoonVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param zoekPersoonVerzoekType het zoekPersoonVerzoek type
     */
    public AdHocZoekPersoonVerzoekBericht(final AdHocZoekPersoonVerzoekType zoekPersoonVerzoekType) {
        super("adHocZoekPersoonVerzoek");
        this.adHocZoekPersoonVerzoekType = zoekPersoonVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public final String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAdHocZoekPersoonVerzoek(adHocZoekPersoonVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de partijCode op het bericht terug.
     * @return De partijCode op het bericht.
     */
    @Override
    public final String getPartijCode() {
        return adHocZoekPersoonVerzoekType.getPartijCode();
    }

    /**
     * Zet de partijCode op het bericht.
     * @param partijCode De te zetten partijCode.
     */
    public final void setPartijCode(final String partijCode) {
        adHocZoekPersoonVerzoekType.setPartijCode(partijCode);
    }

    /**
     * Geeft de persoon identificerende gegevens op het bericht terug.
     * @return De persoon identificerende gegevens op het bericht.
     */
    public final String getPersoonIdentificerendeGegevens() {
        return adHocZoekPersoonVerzoekType.getPersoonIdentificerendeGegevens();
    }

    /**
     * Zet de persoon identificerende gegevens op het bericht.
     * @param persoonIdentificerendeGegevens De te zetten persoon identificerende gegevens.
     */
    public final void setPersoonIdentificerendeGegevens(final String persoonIdentificerendeGegevens) {
        adHocZoekPersoonVerzoekType.setPersoonIdentificerendeGegevens(persoonIdentificerendeGegevens);
    }

    /**
     * Geeft de gevraagde rubrieken op het bericht terug.
     * @return De gevraagde rubrieken op het bericht.
     */
    @Override
    public final List<String> getGevraagdeRubrieken() {
        return adHocZoekPersoonVerzoekType.getGevraagdeRubrieken();
    }

    /**
     * Zet de soort van de dienst op het bericht.
     * @param soortDienst De te zetten soort van de dienst op het bericht.
     */
    public final void setSoortDienst(final SoortDienstType soortDienst) {
        adHocZoekPersoonVerzoekType.setSoortDienst(soortDienst);
    }

    /**
     * Geeft de soort van de dienst op het bericht terug.
     * @return De soort van de dienst op het bericht.
     */
    @Override
    public final SoortDienstType getSoortDienst() {
        return adHocZoekPersoonVerzoekType.getSoortDienst();
    }
}
