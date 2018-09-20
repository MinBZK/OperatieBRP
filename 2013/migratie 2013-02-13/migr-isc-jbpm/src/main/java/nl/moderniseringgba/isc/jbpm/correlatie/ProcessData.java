/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.correlatie;

/**
 * Process data.
 */
public final class ProcessData {
    private final Long processInstanceId;
    private final Long tokenId;
    private final Long nodeId;
    private final String counterName;
    private final Integer counterValue;

    private final String bronGemeente;
    private final String doelGemeente;

    /**
     * Consutrctor.
     * 
     * @param processInstanceId
     *            process instance id
     * @param tokenId
     *            token id
     * @param nodeId
     *            node id
     * @param counterName
     *            counter name
     * @param counterValue
     *            counter value
     * @param bronGemeente
     *            bron gemeente
     * @param doelGemeente
     *            doel gemeente
     */
    public ProcessData(
            final Long processInstanceId,
            final Long tokenId,
            final Long nodeId,
            final String counterName,
            final Integer counterValue,
            final String bronGemeente,
            final String doelGemeente) {
        super();
        this.processInstanceId = processInstanceId;
        this.tokenId = tokenId;
        this.nodeId = nodeId;
        this.counterName = counterName;
        this.counterValue = counterValue;
        this.bronGemeente = bronGemeente;
        this.doelGemeente = doelGemeente;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public String getCounterName() {
        return counterName;
    }

    public Integer getCounterValue() {
        return counterValue;
    }

    public String getBronGemeente() {
        return bronGemeente;
    }

    public String getDoelGemeente() {
        return doelGemeente;
    }

    @Override
    public String toString() {
        return "ProcessData [processInstanceId=" + processInstanceId + ", tokenId=" + tokenId + ", nodeId=" + nodeId
                + ", counterName=" + counterName + ", counterValue=" + counterValue + ", bronGemeente="
                + bronGemeente + ", doelGemeente=" + doelGemeente + "]";
    }

}
