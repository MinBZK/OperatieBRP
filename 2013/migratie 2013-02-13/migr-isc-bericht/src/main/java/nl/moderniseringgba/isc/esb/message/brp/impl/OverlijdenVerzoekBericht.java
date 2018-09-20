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
import nl.moderniseringgba.isc.esb.message.brp.generated.OverlijdenVerzoekType;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor overlijden verzoek.
 */
public final class OverlijdenVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private OverlijdenVerzoekType overlijdenVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public OverlijdenVerzoekBericht() {
        overlijdenVerzoekType = new OverlijdenVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een OverlijdenVerzoekType te
     * maken.
     * 
     * @param overlijdenVerzoekType
     *            het overlijdenVerzoek type
     */
    public OverlijdenVerzoekBericht(final OverlijdenVerzoekType overlijdenVerzoekType) {
        this.overlijdenVerzoekType = overlijdenVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "OverlijdenVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public OverlijdenAntwoordBericht maakAntwoordBericht() {
        final OverlijdenAntwoordBericht antwoord = new OverlijdenAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(overlijdenVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        overlijdenVerzoekType.setIscGemeenten(setBrpGemeente(overlijdenVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(overlijdenVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        overlijdenVerzoekType.setIscGemeenten(setLo3Gemeente(overlijdenVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen overlijden verzoek type beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return overlijdenVerzoekType != null ? overlijdenVerzoekType.getBronnen().getBron().get(0).getDocument()
                .getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen overlijden verzoek type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return overlijdenVerzoekType != null ? overlijdenVerzoekType.getBronnen().getBron().get(0).getDocument()
                .getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen overlijden verzoek type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return overlijdenVerzoekType != null ? overlijdenVerzoekType.getDatumAanvangGeldigheid() : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createOverlijdenVerzoek(overlijdenVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            overlijdenVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, OverlijdenVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een OverlijdenVerzoek bericht.", e);
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

        if (brpPersoonslijst == null && overlijdenVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare overlijden verzoek type een BrpPersoonslijst op.
     * 
     */
    @Override
    public void converteerNaarBrpPersoonslijst() {
        try {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(overlijdenVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), overlijdenVerzoekType.getTijdstipOntlening()
                            .longValue(), overlijdenVerzoekType.getBronnen().getBron().get(0), overlijdenVerzoekType
                            .getOverledenPersoon(), null, null);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }
    }
}
