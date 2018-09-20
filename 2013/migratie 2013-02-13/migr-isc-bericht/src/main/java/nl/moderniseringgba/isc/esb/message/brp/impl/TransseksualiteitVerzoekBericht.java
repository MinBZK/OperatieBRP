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
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.TransseksualiteitVerzoekType;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor geslachtsnaamwijziging.
 */
public final class TransseksualiteitVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private TransseksualiteitVerzoekType transseksualiteitVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public TransseksualiteitVerzoekBericht() {
        transseksualiteitVerzoekType = new TransseksualiteitVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een TransseksualiteitVerzoek te
     * maken.
     * 
     * @param transseksualiteitVerzoekType
     *            het transseksualiteitVerzoek type
     */
    public TransseksualiteitVerzoekBericht(final TransseksualiteitVerzoekType transseksualiteitVerzoekType) {
        this.transseksualiteitVerzoekType = transseksualiteitVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "TransseksualiteitVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public TransseksualiteitAntwoordBericht maakAntwoordBericht() {
        final TransseksualiteitAntwoordBericht antwoord = new TransseksualiteitAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(transseksualiteitVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        transseksualiteitVerzoekType.setIscGemeenten(setBrpGemeente(transseksualiteitVerzoekType.getIscGemeenten(),
                gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(transseksualiteitVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        transseksualiteitVerzoekType.setIscGemeenten(setLo3Gemeente(transseksualiteitVerzoekType.getIscGemeenten(),
                gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen transseksualiteitVerzoekType type beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return transseksualiteitVerzoekType != null ? transseksualiteitVerzoekType.getBronnen().getBron().get(0)
                .getDocument().getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen transseksualiteitVerzoekType type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return transseksualiteitVerzoekType != null ? transseksualiteitVerzoekType.getBronnen().getBron().get(0)
                .getDocument().getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen transseksualiteitVerzoekType type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return transseksualiteitVerzoekType != null ? transseksualiteitVerzoekType.getDatumAanvangGeldigheid() : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createTransseksualiteitVerzoek(transseksualiteitVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            transseksualiteitVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, TransseksualiteitVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een TransseksualiteitVerzoek bericht.", e);
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

        if (brpPersoonslijst == null && transseksualiteitVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare transseksualiteitVerzoekType een BrpPersoonslijst op.
     * 
     */
    @Override
    public void converteerNaarBrpPersoonslijst() {
        try {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(transseksualiteitVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), transseksualiteitVerzoekType
                            .getTijdstipOntlening().longValue(), transseksualiteitVerzoekType.getBronnen().getBron()
                            .get(0), transseksualiteitVerzoekType.getPersoon(), null, null);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }

    }
}
