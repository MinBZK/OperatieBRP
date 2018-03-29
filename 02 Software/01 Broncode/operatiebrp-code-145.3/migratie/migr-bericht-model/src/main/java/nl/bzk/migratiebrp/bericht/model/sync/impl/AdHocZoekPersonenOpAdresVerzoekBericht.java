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
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekPersonenOpAdresVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdresFunctieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Zoek persoon op basis van zoekcriteria.
 */
public class AdHocZoekPersonenOpAdresVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie implements AdHocZoekVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private final AdHocZoekPersonenOpAdresVerzoekType adHocZoekPersonenOpAdresVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AdHocZoekPersonenOpAdresVerzoekBericht() {
        this(new AdHocZoekPersonenOpAdresVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param adHocZoekPersonenOpAdresVerzoekType het zoekPersoonAdresVerzoek type
     */
    public AdHocZoekPersonenOpAdresVerzoekBericht(final AdHocZoekPersonenOpAdresVerzoekType adHocZoekPersonenOpAdresVerzoekType) {
        super("adHocZoekPersonenOpAdresVerzoek");
        this.adHocZoekPersonenOpAdresVerzoekType = adHocZoekPersonenOpAdresVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public final String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAdHocZoekPersonenOpAdresVerzoek(adHocZoekPersonenOpAdresVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de adres functie op het bericht terug.
     * @return De adres functie op het bericht.
     */
    public final AdresFunctieType getAdresFunctie() {
        return adHocZoekPersonenOpAdresVerzoekType.getAdresfunctie();
    }

    /**
     * Zet de adres functie op het bericht.
     * @param adresFunctieType De te zetten adres functie.
     */
    public final void setAdresfunctie(final AdresFunctieType adresFunctieType) {
        adHocZoekPersonenOpAdresVerzoekType.setAdresfunctie(adresFunctieType);
    }

    /**
     * Geeft de partijCode op het bericht terug.
     * @return De partijCode op het bericht.
     */
    @Override
    public final String getPartijCode() {
        return adHocZoekPersonenOpAdresVerzoekType.getPartijCode();
    }

    /**
     * Zet de partijCode op het bericht.
     * @param partijCode De te zetten partijCode.
     */
    public final void setPartijCode(final String partijCode) {
        adHocZoekPersonenOpAdresVerzoekType.setPartijCode(partijCode);
    }

    /**
     * Geeft het identificatie type op het bericht terug.
     * @return Het identificatie type op het bericht.
     */
    public final IdentificatieType getIdentificatie() {
        return adHocZoekPersonenOpAdresVerzoekType.getIdentificatie();
    }

    /**
     * Zet het type identificatie op het bericht.
     * @param identificatieType Het te zetten identificatie type.
     */
    public final void setIdentificatie(final IdentificatieType identificatieType) {
        adHocZoekPersonenOpAdresVerzoekType.setIdentificatie(identificatieType);
    }

    /**
     * Geeft de persoon identificerende gegevens op het bericht terug.
     * @return De persoon identificerende gegevens op het bericht.
     */
    public final String getIdentificerendeGegevens() {
        return adHocZoekPersonenOpAdresVerzoekType.getIdentificerendeGegevens();
    }

    /**
     * Zet de identificerende gegevens op het bericht.
     * @param identificerendeGegevens De te zetten identificerende gegevens.
     */
    public final void setIdentificerendeGegevens(final String identificerendeGegevens) {
        adHocZoekPersonenOpAdresVerzoekType.setIdentificerendeGegevens(identificerendeGegevens);
    }

    /**
     * Geeft de gevraagde rubrieken op het bericht terug.
     * @return De gevraagde rubrieken op het bericht.
     */
    @Override
    public final List<String> getGevraagdeRubrieken() {
        return adHocZoekPersonenOpAdresVerzoekType.getGevraagdeRubrieken();
    }

    /**
     * Geeft soort dienst op het bericht terug.
     * @return De dienst ZOEK_PERSOON_OP_ADRESGEGEVENS.
     */
    @Override
    public SoortDienstType getSoortDienst() {
        return SoortDienstType.ZOEK_PERSOON_OP_ADRESGEGEVENS;
    }

}
