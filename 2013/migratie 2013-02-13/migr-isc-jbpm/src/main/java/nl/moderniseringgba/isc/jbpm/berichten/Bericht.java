/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.berichten;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Bericht.
 */
public final class Bericht {

    private Long id;
    private Timestamp tijdstip;
    private String kanaal;
    private Direction richting;
    private String messageId;
    private String correlationId;
    private String bericht;
    private String naam;
    private Long processInstanceId;
    private Integer herhaling;
    private String bronGemeente;
    private String doelGemeente;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Timestamp getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getKanaal() {
        return kanaal;
    }

    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    public Direction getRichting() {
        return richting;
    }

    public void setRichting(final Direction richting) {
        this.richting = richting;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(final Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getHerhaling() {
        return herhaling;
    }

    public void setHerhaling(final Integer herhaling) {
        this.herhaling = herhaling;
    }

    public String getBronGemeente() {
        return bronGemeente;
    }

    public void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    public String getDoelGemeente() {
        return doelGemeente;
    }

    public void setDoelGemeente(final String doelGemeente) {
        this.doelGemeente = doelGemeente;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Bericht)) {
            return false;
        }
        final Bericht castOther = (Bericht) other;
        return new EqualsBuilder().append(id, castOther.id).append(tijdstip, castOther.tijdstip)
                .append(kanaal, castOther.kanaal).append(richting, castOther.richting)
                .append(messageId, castOther.messageId).append(correlationId, castOther.correlationId)
                .append(bericht, castOther.bericht).append(naam, castOther.naam)
                .append(processInstanceId, castOther.processInstanceId).append(herhaling, castOther.herhaling)
                .append(bronGemeente, castOther.bronGemeente).append(doelGemeente, castOther.doelGemeente).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(tijdstip).append(kanaal).append(richting).append(messageId)
                .append(correlationId).append(bericht).append(naam).append(processInstanceId).append(herhaling)
                .append(bronGemeente).append(doelGemeente).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("id", id).append("tijdstip", tijdstip).append("kanaal", kanaal).append("richting", richting)
                .append("messageId", messageId).append("correlationId", correlationId).append("bericht", bericht)
                .append("naam", naam).append("processInstanceId", processInstanceId).append("herhaling", herhaling)
                .append("bronGemeente", bronGemeente).append("doelGemeente", doelGemeente).toString();
    }

}
