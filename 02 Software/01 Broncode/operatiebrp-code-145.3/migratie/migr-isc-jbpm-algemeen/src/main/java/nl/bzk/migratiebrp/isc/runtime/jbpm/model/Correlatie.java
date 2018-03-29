/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import org.jbpm.graph.exe.ProcessInstance;

/**
 * Correlatie gegevens.
 */

public class Correlatie {
    private String messageId;
    private String kanaal;
    private String verzendendePartij;
    private String ontvangendePartij;
    private ProcessInstance processInstance;
    private Long tokenId;
    private Long nodeId;

    /**
     * Geeft het messageId.
     * @return het messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Zet het messageId.
     * @param messageId Het te zetten messageId
     */
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * Geeft het kanaal.
     * @return het kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Zet het kanaal.
     * @param kanaal Het kanaal
     */
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Geeft de verzendende partij.
     * @return de verzendende partij
     */
    public String getVerzendendePartij() {
        return verzendendePartij;
    }

    /**
     * Zet de verzendende partij.
     * @param verzendendePartij De te zetten verzendende partij
     */
    public void setVerzendendePartij(final String verzendendePartij) {
        this.verzendendePartij = verzendendePartij;
    }

    /**
     * Geeft de ontvangende partij.
     * @return de ontvangende partij
     */
    public String getOntvangendePartij() {
        return ontvangendePartij;
    }

    /**
     * Zet de ontvangende partij.
     * @param ontvangendePartij De te zetten ontvangende partij
     */
    public void setOntvangendePartij(final String ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
    }

    /**
     * Geeft de proces instantie.
     * @return de proces instantie
     */
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    /**
     * Zet de proces instantie.
     * @param processInstance De te zetten proces instantie
     */
    public void setProcessInstance(final ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    /**
     * Geeft het tokenId.
     * @return het tokenId
     */
    public Long getTokenId() {
        return tokenId;
    }

    /**
     * Zet het tokenId.
     * @param tokenId Het te zetten tokenId
     */
    public void setTokenId(final Long tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * Geeft het nodeId.
     * @return het nodeId
     */
    public Long getNodeId() {
        return nodeId;
    }

    /**
     * Zet het nodeId.
     * @param nodeId Het te zetten nodeId
     */
    public void setNodeId(final Long nodeId) {
        this.nodeId = nodeId;
    }

}
