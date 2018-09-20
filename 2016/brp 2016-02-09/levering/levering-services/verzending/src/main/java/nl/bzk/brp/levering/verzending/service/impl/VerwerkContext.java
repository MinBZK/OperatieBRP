/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import java.util.concurrent.Future;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;

/**
 *
 */
public final class VerwerkContext {

    private final Integer          partijCode;
    private final String           queueNaam;
    private       Future           future;
    private       Dispatch<Source> cachedWebserviceClient;

    /**
     * ?
     */
    private int berichtenAantal;

    /**
     * aantal berichten succesvol verwerkt
     */
    private int  succes;
    /**
     * aantal berichten foutief verwerkt
     */
    private int  error;
    /**
     * totale verwerkingstijd
     */
    private long totaalTijd;

    private int  aantalVerzendingen;
    private long verzendTijdTotaal;


    private int  aantalProtocolleringen;
    private long protocolleerTijdTotaal;

    private int  aantalArchiveringen;
    private long archiveerTijdTotaal;

    /**
     *
     * @param partijCode code van de partij
     */
    public VerwerkContext(final Integer partijCode) {
        this.partijCode = partijCode;
        this.queueNaam = Partij.PREFIX_AFNEMER_QUEUE_NAAM + partijCode;
    }

    public Integer getPartijCode() {
        return partijCode;
    }

    public String getQueueNaam() {
        return queueNaam;
    }

    public Future getFuture() {
        return future;
    }

    void setFuture(final Future future) {
        this.future = future;
    }

    public int getBerichtenAantal() {
        return berichtenAantal;
    }

    public void setBerichtenAantal(final Integer berichtenAantal) {
        this.berichtenAantal = berichtenAantal;
    }


    /**
     * @return de client stub voor het versturen naar de webservice
     */
    public Dispatch<Source> getCachedWebserviceClient() {
        return cachedWebserviceClient;
    }

    /**
     * @param cachedWebserviceClient zet de client stub voo het versturen naar de webservice
     */
    public void setCachedWebserviceClient(final Dispatch<Source> cachedWebserviceClient) {
        this.cachedWebserviceClient = cachedWebserviceClient;
    }


    public void addVerzendTijd(final long tijd) {
        aantalVerzendingen++;
        verzendTijdTotaal += tijd;
    }

    public void addError() {
        error++;
    }

    public void addSucces(final long tijd) {
        succes++;
        totaalTijd += tijd;
    }


    public long getVerzendTijdTotaal() {
        return verzendTijdTotaal;
    }

    public Integer getError() {
        return error;
    }

    public Integer getSucces() {
        return succes;
    }

    public long getTotaalTijd() {
        return totaalTijd;
    }

    /**
     *
     */
    void maakLeeg() {
        this.future = null;
        this.berichtenAantal = 0;
    }


    @Override
    public String toString() {
        return queueNaam;
    }

    public void addProtocolleerTijd(final long tijd) {
        aantalProtocolleringen++;
        protocolleerTijdTotaal += tijd;
    }

    public int getAantalProtocolleringen() {
        return aantalProtocolleringen;
    }

    public void addArchiveerTijd(final long tijd) {
        aantalArchiveringen++;
        archiveerTijdTotaal += tijd;
    }

    public long getProtocolleerTijdTotaal() {
        return protocolleerTijdTotaal;
    }

    public int getAantalArchiveringen() {
        return aantalArchiveringen;
    }

    public long getArchiveerTijdTotaal() {
        return archiveerTijdTotaal;
    }

    public int getAantalVerzendingen() {
        return aantalVerzendingen;
    }
}
