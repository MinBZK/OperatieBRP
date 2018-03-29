/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.notificatie;

import java.io.Serializable;
import java.util.Arrays;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;

/**
 * Standaard notificatie bericht.
 * @param <T> type
 */
public abstract class AbstractNotificatieBericht<T extends Serializable> extends AbstractBericht implements NotificatieBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private final String berichtType;
    private final String startCyclus;

    private T inhoud;
    private String ontvangendePartij;
    private String verzendendePartij;

    /**
     * Constructor.
     * @param berichtType berichtnummer van dit bericht
     * @param startCyclus cyclus die dit bericht kan starten
     */
    protected AbstractNotificatieBericht(final String berichtType, final String startCyclus, final T inhoud) {
        this.berichtType = berichtType;
        this.startCyclus = startCyclus;
        this.inhoud = inhoud;
    }

    @Override
    public final String getBerichtType() {
        return berichtType;
    }

    @Override
    public final String getStartCyclus() {
        return startCyclus;
    }

    public String getOntvangendePartij() {
        return ontvangendePartij;
    }

    public void setOntvangendePartij(String ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
    }

    public String getVerzendendePartij() {
        return verzendendePartij;
    }

    public void setVerzendendePartij(String verzendendePartij) {
        this.verzendendePartij = verzendendePartij;
    }

    /**
     * Parsed de inhoud van het bericht.
     * @param inhoud De inhoud van het bericht
     * @return JAXB representatie van de bericht inhoud
     * @throws JAXBException in het geval de inhoud niet geconverteerd kon worden
     */
    public abstract JAXBElement<T> parse(final String inhoud) throws JAXBException;

    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(verzendendePartij, ontvangendePartij), null);
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Retourneert inhoud.
     * @return inhoud
     */
    public final T getInhoud() {
        return inhoud;
    }

    /**
     * Zet de waarde van inhoud.
     * @param inhoud de te zetten inhoud
     */
    public void setInhoud(final T inhoud) {
        this.inhoud = inhoud;
    }
}
