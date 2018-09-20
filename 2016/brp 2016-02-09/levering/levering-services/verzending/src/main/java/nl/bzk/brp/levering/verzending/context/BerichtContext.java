/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.context;

import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;

/**
 */
public final class BerichtContext {

    private final Message        jmsBericht;
    private final VerwerkContext verwerkContext;

    private SynchronisatieBerichtGegevens synchronisatieBerichtGegevens;
    private BerichtModel                  berichtArchiefModel;


    /**
     * BerichtContext constructor voor het te verwerken bericht.
     *
     * @param verwerkContext verwerkContext
     * @param jmsBericht     het te verwerken bericht
     */
    public BerichtContext(final VerwerkContext verwerkContext, final Message jmsBericht) {
        this.jmsBericht = jmsBericht;
        this.verwerkContext = verwerkContext;
    }

    /**
     * @return id van de leveringsautorisatie
     * @throws JMSException als de jms property niet uitgelezen kan worden
     */
    public String getLeveringsautorisatieId() throws JMSException {
        return jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID);
    }

    /**
     * @return code van de ontvangende partij
     * @throws JMSException als de jms property niet uitgelezen kan worden
     */
    public String getOntvangendePartijCode() throws JMSException {
        return jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_PARTIJ_CODE);
    }

    /**
     * @return de uri om het bericht naar te verzenden
     * @throws JMSException als de jms property niet uitgelezen kan worden
     */
    public String getBrpAfleverURI() throws JMSException {
        return jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_BRP_AFLEVER_URI);
    }

    /**
     * @return het protocolleringsniveau van het te verwerken bericht
     * @throws JMSException als de jms property niet uitgelezen kan worden
     */
    public Protocolleringsniveau getProtocolleringNiveau() throws JMSException {
        final String property = jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_PROTOCOLLERINGNIVEAU);
        if (property != null) {
            return Protocolleringsniveau.valueOf(property);
        }
        return Protocolleringsniveau.GEEN_BEPERKINGEN;
    }

    /**
     * @return het te versturen xml bericht
     * @throws JMSException als de jms property niet uitgelezen kan worden
     */
    public String getBerichtXML() throws JMSException {
        return jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT);
    }

    public VerwerkContext getVerwerkContext() {
        return verwerkContext;
    }

    public SynchronisatieBerichtGegevens getSynchronisatieBerichtGegevens() {
        return synchronisatieBerichtGegevens;
    }

    public void setSynchronisatieBerichtGegevens(final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens) {
        this.synchronisatieBerichtGegevens = synchronisatieBerichtGegevens;
    }

    public BerichtModel getBerichtArchiefModel() {
        return berichtArchiefModel;
    }

    public void setBerichtArchiefModel(final BerichtModel berichtArchiefModel) {
        this.berichtArchiefModel = berichtArchiefModel;
    }

    public Message getJmsBericht() {
        return jmsBericht;
    }

}
