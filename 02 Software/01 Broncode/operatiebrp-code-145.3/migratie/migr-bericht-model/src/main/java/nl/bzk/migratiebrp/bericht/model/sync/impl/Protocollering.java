/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.time.LocalDateTime;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ProtocolleringType;
import nl.bzk.migratiebrp.bericht.model.xml.XmlConversieUtils;

/**
 * Bericht voor protocollering.
 */
public class Protocollering {

    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private final ProtocolleringType protocolleringType;

    /**
     * constructor.
     * @param activiteitId activiteit id
     */
    public Protocollering(final long activiteitId) {
        this(OBJECT_FACTORY.createProtocolleringType());
        setActiviteitId(activiteitId);
    }

    /**
     * Contructor met bestaand protocolleringType.
     * @param protocolleringType bestaand protocolleringType
     */
    public Protocollering(final ProtocolleringType protocolleringType) {
        this.protocolleringType = protocolleringType;
    }

    /**
     * Geef activiteit id.
     * @return activiteit id
     */
    public final long getActiviteitId() {
        return protocolleringType.getActiviteitId();
    }

    /**
     * Zet activiteit id.
     * @param id activiteit id
     */
    public final void setActiviteitId(final long id) {
        protocolleringType.setActiviteitId(id);
    }

    /**
     * Geef persoon id.
     * @return persoon id
     */
    public final Long getPersoonId() {
        return protocolleringType.getPersoonId();
    }

    /**
     * Zet persoon id.
     * @param id persoon id
     */
    public final void setPersoonId(final Long id) {
        protocolleringType.setPersoonId(id);
    }

    /**
     * Geef code van nadere bijhoudingsaard.
     * @return nadere bijhoudingsaard code
     */
    public final String getNadereBijhoudingsaardCode() {
        return protocolleringType.getNadereBijhoudingsaardCode();
    }

    /**
     * Geef nadere bijhoudingsaard code.
     * @param code nadere bijhoudingsaard code
     */
    public final void setNadereBijhoudingsaardCode(final String code) {
        protocolleringType.setNadereBijhoudingsaardCode(code);
    }

    /**
     * Geef toegang leveringsautorisatie id.
     * @return toegang leveringsautorisatie id
     */
    public final Integer getToegangLeveringsautorisatieId() {
        return protocolleringType.getToegangLeveringsautorisatieId();
    }

    /**
     * Zet toegang leveringsautorisatie id.
     * @param id toegang leveringsautorisatie id
     */
    public final void setToegangLeveringsautorisatieId(final Integer id) {
        protocolleringType.setToegangLeveringsautorisatieId(id);
    }

    /**
     * Geef aantal gevonden toegang leveringsautorisaties.
     * @return aantal
     */
    public final int getToegangLeveringsautorisatieCount() {
        return protocolleringType.getToegangLeveringsautorisatieCount();
    }

    /**
     * Zet toegang leveringsautorisatie aantal.
     * @param count aantal
     */
    public final void setToegangLeveringsautorisatieCount(final int count) {
        protocolleringType.setToegangLeveringsautorisatieCount(count);
    }

    /**
     * Geef dienst id.
     * @return dienst id
     */
    public final Integer getDienstId() {
        return protocolleringType.getDienstId();
    }

    /**
     * Zet dienst id.
     * @param id dienst id
     */
    public final void setDienstId(final Integer id) {
        protocolleringType.setDienstId(id);
    }

    /**
     * Geef start tijdstip.
     * @return start datum
     */
    public final LocalDateTime getStartTijdstip() {
        return XmlConversieUtils.converteerXmlNaarLocalDateTime(protocolleringType.getStartTijdstip());
    }

    /**
     * Zet start tijdstip.
     * @param date start datum
     */
    public final void setStartTijdstip(final LocalDateTime date) {
        protocolleringType.setStartTijdstip(XmlConversieUtils.converteerDateNaarXml(date));
    }

    /**
     * Geef laatste actie tijdstip.
     * @return laatste actie datum
     */
    public final LocalDateTime getLaatsteActieTijdstip() {
        return XmlConversieUtils.converteerXmlNaarLocalDateTime(protocolleringType.getLaatsteActieTijdstip());
    }

    /**
     * Zet laatste actie tijdstip.
     * @param date laatste actie datum
     */
    public final void setLaatsteActieTijdstip(final LocalDateTime date) {
        protocolleringType.setLaatsteActieTijdstip(XmlConversieUtils.converteerDateNaarXml(date));
    }

    /**
     * Geef het ProtocolleringType terug.
     * @return het ProtocolleringType
     */
    public final ProtocolleringType getProtocolleringType() {
        return protocolleringType;
    }
}
