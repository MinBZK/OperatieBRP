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
import nl.moderniseringgba.isc.esb.message.brp.generated.ErkenningVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor erkenning na geboorte.
 */
public final class ErkenningVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private ErkenningVerzoekType erkenningVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public ErkenningVerzoekBericht() {
        erkenningVerzoekType = new ErkenningVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param erkenningVerzoekType
     *            het erkenningVerzoek type
     */
    public ErkenningVerzoekBericht(final ErkenningVerzoekType erkenningVerzoekType) {
        this.erkenningVerzoekType = erkenningVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "ErkenningVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public ErkenningAntwoordBericht maakAntwoordBericht() {
        final ErkenningAntwoordBericht antwoord = new ErkenningAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(erkenningVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        erkenningVerzoekType.setIscGemeenten(setBrpGemeente(erkenningVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(erkenningVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        erkenningVerzoekType.setIscGemeenten(setLo3Gemeente(erkenningVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return erkenningVerzoekType != null ? erkenningVerzoekType.getBronnen().getBron().get(0).getDocument()
                .getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return erkenningVerzoekType != null ? erkenningVerzoekType.getBronnen().getBron().get(0).getDocument()
                .getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen erkenningverzoek type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return erkenningVerzoekType != null ? erkenningVerzoekType.getErkenner().getOuderschap().get(0)
                .getDatumAanvang().getValue() : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createErkenningVerzoek(erkenningVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            erkenningVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, ErkenningVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een ErkenningVerzoek bericht.", e);
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

        if (brpPersoonslijst == null && erkenningVerzoekType != null) {
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
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(erkenningVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), erkenningVerzoekType.getTijdstipOntlening()
                            .longValue(), erkenningVerzoekType.getBronnen().getBron().get(0), erkenningVerzoekType
                            .getKind().getPersoon(), null, erkenningVerzoekType.getErkenner());
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }
    }
}
