/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.math.BigInteger;
import java.util.Arrays;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AnummerWijzigingNotificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * A-nummer wijziging notificatie.
 */
public final class AnummerWijzigingNotificatie extends AbstractSyncBericht {
    private static final long serialVersionUID = 1L;

    private final AnummerWijzigingNotificatieType notificatie;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AnummerWijzigingNotificatie() {
        this(new AnummerWijzigingNotificatieType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param notificatie
     *            het notificatie type
     */
    public AnummerWijzigingNotificatie(final AnummerWijzigingNotificatieType notificatie) {
        super("AnummerWijzigingNotificatie", "uc311");
        this.notificatie = notificatie;
    }

    @Override
    public String format() throws BerichtInhoudException {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAnummerWijzigingNotificatie(notificatie));
    }

    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, null, Arrays.asList(notificatie.getNieuwANummer(), notificatie.getOudANummer()));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef brongemeente.
     *
     * @return brongemeente
     */
    public String getBronGemeente() {
        return notificatie.getBronGemeente();
    }

    /**
     * Set brongemeenet.
     *
     * @param bronGemeente
     *            brongemeente
     */
    public void setBronGemeente(final String bronGemeente) {
        notificatie.setBronGemeente(bronGemeente);
    }

    /**
     * Geef datum ingang geldigheid.
     *
     * @return datum ingang geldigheid
     */
    public Integer getDatumIngangGeldigheid() {
        return asInteger(notificatie.getDatumIngangGeldigheid());
    }

    /**
     * Zet datum ingang geldigheid.
     *
     * @param datum
     *            datum ingang geldigheid
     */
    public void setDatumIngangGeldigheid(final int datum) {
        notificatie.setDatumIngangGeldigheid(BigInteger.valueOf(datum));
    }

    /**
     * Geef oud a-nummer.
     *
     * @return oud a-nummer
     */
    public Long getOudAnummer() {
        return asLong(notificatie.getOudANummer());
    }

    /**
     * Zet oud a-nummer.
     *
     * @param anummer
     *            oud a-nummer
     */
    public void setOudAnummer(final long anummer) {
        notificatie.setOudANummer(Long.toString(anummer));
    }

    /**
     * Geef nieuw a-nummer.
     *
     * @return nieuw a-nummer
     */
    public Long getNieuwAnummer() {
        return asLong(notificatie.getNieuwANummer());
    }

    /**
     * Zet nieuw a-nummer.
     *
     * @param anummer
     *            nieuw a-nummer
     */
    public void setNieuwAnummer(final long anummer) {
        notificatie.setNieuwANummer(Long.toString(anummer));
    }
}
