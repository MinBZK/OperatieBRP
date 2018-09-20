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
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVernietigingVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor vernietiging erkenning na geboorte.
 */
public final class ErkenningVernietigingVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private ErkenningVernietigingVerzoekType erkenningVernietigingVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ErkenningVernietigingVerzoekBericht() {
        erkenningVernietigingVerzoekType = new ErkenningVernietigingVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param erkenningVernietigingVerzType
     *            het erkenningVernietigingVerzoek type
     */
    public ErkenningVernietigingVerzoekBericht(final ErkenningVernietigingVerzoekType erkenningVernietigingVerzType) {
        erkenningVernietigingVerzoekType = erkenningVernietigingVerzType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "ErkenningVernietigingVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public ErkenningVernietigingAntwoordBericht maakAntwoordBericht() {
        final ErkenningVernietigingAntwoordBericht antwoord = new ErkenningVernietigingAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(erkenningVernietigingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        erkenningVernietigingVerzoekType.setIscGemeenten(setBrpGemeente(
                erkenningVernietigingVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(erkenningVernietigingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        erkenningVernietigingVerzoekType.setIscGemeenten(setLo3Gemeente(
                erkenningVernietigingVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return erkenningVernietigingVerzoekType != null ? erkenningVernietigingVerzoekType.getBronnen().getBron()
                .get(0).getDocument().getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return erkenningVernietigingVerzoekType != null ? erkenningVernietigingVerzoekType.getBronnen().getBron()
                .get(0).getDocument().getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return erkenningVernietigingVerzoekType != null ? erkenningVernietigingVerzoekType
                .getDatumAanvangGeldigheid() : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createErkenningVernietigingVerzoek(erkenningVernietigingVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            erkenningVernietigingVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, ErkenningVernietigingVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ErkenningVernietigingVerzoek bericht.", e);
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

        if (brpPersoonslijst == null && erkenningVernietigingVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare erkenningVerzoekType een BrpPersoonslijst op.
     * 
     */
    @Override
    public void converteerNaarBrpPersoonslijst() {
        try {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(erkenningVernietigingVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), erkenningVernietigingVerzoekType
                            .getTijdstipOntlening().longValue(), erkenningVernietigingVerzoekType.getBronnen()
                            .getBron().get(0), erkenningVernietigingVerzoekType.getKind().getPersoon(), null, null);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }
    }
}
