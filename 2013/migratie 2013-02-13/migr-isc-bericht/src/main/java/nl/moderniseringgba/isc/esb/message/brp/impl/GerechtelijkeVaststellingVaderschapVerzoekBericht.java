/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.GerechtelijkeVaststellingVaderschapVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor gerechtelijke vaststelling vaderschap.
 */
public final class GerechtelijkeVaststellingVaderschapVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private GerechtelijkeVaststellingVaderschapVerzoekType gerechtelijkeVaststellingVaderschapVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public GerechtelijkeVaststellingVaderschapVerzoekBericht() {
        gerechtelijkeVaststellingVaderschapVerzoekType = new GerechtelijkeVaststellingVaderschapVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een ontkenning vaderschap door
     * moeder verzoek te maken.
     * 
     * @param gerechtelijkeVaststellingVaderschapVerzoekType
     *            het gerechtelijkeVaststellingVaderschapVerzoek type
     */
    public GerechtelijkeVaststellingVaderschapVerzoekBericht(
            final GerechtelijkeVaststellingVaderschapVerzoekType gerechtelijkeVaststellingVaderschapVerzoekType) {
        this.gerechtelijkeVaststellingVaderschapVerzoekType = gerechtelijkeVaststellingVaderschapVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "GerechtelijkeVaststellingVaderschapVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public GerechtelijkeVaststellingVaderschapAntwoordBericht maakAntwoordBericht() {
        final GerechtelijkeVaststellingVaderschapAntwoordBericht antwoord =
                new GerechtelijkeVaststellingVaderschapAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(gerechtelijkeVaststellingVaderschapVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        gerechtelijkeVaststellingVaderschapVerzoekType.setIscGemeenten(setBrpGemeente(
                gerechtelijkeVaststellingVaderschapVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(gerechtelijkeVaststellingVaderschapVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        gerechtelijkeVaststellingVaderschapVerzoekType.setIscGemeenten(setLo3Gemeente(
                gerechtelijkeVaststellingVaderschapVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen gerechtelijke vaststelling vaderschap type
     *         beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return gerechtelijkeVaststellingVaderschapVerzoekType != null ? gerechtelijkeVaststellingVaderschapVerzoekType
                .getBronnen().getBron().get(0).getDocument().getPartijCode().getValue()
                : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen gerechtelijke vaststelling vaderschap type beschikbaar
     *         is.
     */
    @Override
    public String getAktenummer() {
        return gerechtelijkeVaststellingVaderschapVerzoekType != null ? gerechtelijkeVaststellingVaderschapVerzoekType
                .getBronnen().getBron().get(0).getDocument().getAktenummer().getValue()
                : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen gerechtelijke vaststelling vaderschap type beschikbaar
     *         is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return gerechtelijkeVaststellingVaderschapVerzoekType != null ? gerechtelijkeVaststellingVaderschapVerzoekType
                .getVader().getOuderschap().get(0).getDatumAanvang().getValue()
                : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createGerechtelijkeVaststellingVaderschapVerzoek(gerechtelijkeVaststellingVaderschapVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            gerechtelijkeVaststellingVaderschapVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, GerechtelijkeVaststellingVaderschapVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een GerechtelijkeVaststellingVaderschapVerzoek "
                            + "bericht.", e);
        }
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de Brp Persoonslijst terug.
     * 
     * @return De brp persoonslijst.
     */
    @Override
    public BrpPersoonslijst getBrpPersoonslijst() {

        if (brpPersoonslijst == null && gerechtelijkeVaststellingVaderschapVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare gerechtelijke vaststelling vaderschap verzoek type een BrpPersoonslijst op.
     * 
     */
    @Override
    public void converteerNaarBrpPersoonslijst() {
        try {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(
                            gerechtelijkeVaststellingVaderschapVerzoekType.getDatumAanvangGeldigheid().intValue(),
                            gerechtelijkeVaststellingVaderschapVerzoekType.getTijdstipOntlening().longValue(),
                            gerechtelijkeVaststellingVaderschapVerzoekType.getBronnen().getBron().get(0),
                            gerechtelijkeVaststellingVaderschapVerzoekType.getKind().getPersoon(),
                            gerechtelijkeVaststellingVaderschapVerzoekType.getVader(), null);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }
    }
}
