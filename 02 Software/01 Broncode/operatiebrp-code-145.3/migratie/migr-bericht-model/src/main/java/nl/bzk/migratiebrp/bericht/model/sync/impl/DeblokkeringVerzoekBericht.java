/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Deblokkering bericht: verwijderen persoonsaanduiding in BRP.
 */
public final class DeblokkeringVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final DeblokkeringVerzoekType deblokkeringVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public DeblokkeringVerzoekBericht() {
        this(new DeblokkeringVerzoekType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param deblokkeringVerzoekType het deblokkeringVerzoek type
     */
    public DeblokkeringVerzoekBericht(final DeblokkeringVerzoekType deblokkeringVerzoekType) {
        super("DeblokkeringVerzoek");
        this.deblokkeringVerzoekType = deblokkeringVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createDeblokkeringVerzoek(deblokkeringVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer dat op het bericht staat.
     * @return Het A-nummer dat op het bericht staat.
     */
    public String getANummer() {
        return deblokkeringVerzoekType.getANummer();
    }

    /**
     * Zet het A-nummer op het bericht.
     * @param aNummer Het te zetten A-nummer.
     */
    public void setANummer(final String aNummer) {
        deblokkeringVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het process ID dat op het bericht staat.
     * @return Het process ID
     */
    public String getProcessId() {
        return deblokkeringVerzoekType.getProcessId();
    }

    /**
     * Zet het proces ID op het bericht.
     * @param processId Het te zetten proces ID.
     */
    public void setProcessId(final String processId) {
        deblokkeringVerzoekType.setProcessId(processId);
    }

    /**
     * Geeft de registratiegemeente van de blokkering dat op het bericht staat.
     * @return De registratiegemeente van de blokkering
     */
    public String getGemeenteRegistratie() {
        return deblokkeringVerzoekType.getGemeenteRegistratie();
    }

    /**
     * Zet de registratiegemeente van de blokkering op het bericht.
     * @param gemeenteRegistratie De te zetten registratiegemeente.
     */
    public void setGemeenteRegistratie(final String gemeenteRegistratie) {
        deblokkeringVerzoekType.setGemeenteRegistratie(gemeenteRegistratie);
    }

}
