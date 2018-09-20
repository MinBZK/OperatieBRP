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
import nl.moderniseringgba.isc.esb.message.brp.generated.VoornaamswijzigingVerzoekType;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor geslachtsnaamwijziging.
 */
public final class VoornaamswijzigingVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private VoornaamswijzigingVerzoekType voornaamsWijzigingVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public VoornaamswijzigingVerzoekBericht() {
        voornaamsWijzigingVerzoekType = new VoornaamswijzigingVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een VoornaamswijzigingVerzoek te
     * maken.
     * 
     * @param voornaamsWijzigingVerzoekType
     *            het voornaamsWijzigingVerzoek type
     */
    public VoornaamswijzigingVerzoekBericht(final VoornaamswijzigingVerzoekType voornaamsWijzigingVerzoekType) {
        this.voornaamsWijzigingVerzoekType = voornaamsWijzigingVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "VoornaamswijzigingVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public VoornaamswijzigingAntwoordBericht maakAntwoordBericht() {
        final VoornaamswijzigingAntwoordBericht antwoord = new VoornaamswijzigingAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(voornaamsWijzigingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        voornaamsWijzigingVerzoekType.setIscGemeenten(setBrpGemeente(voornaamsWijzigingVerzoekType.getIscGemeenten(),
                gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(voornaamsWijzigingVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        voornaamsWijzigingVerzoekType.setIscGemeenten(setLo3Gemeente(voornaamsWijzigingVerzoekType.getIscGemeenten(),
                gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen voornaamswijzigingVerzoek type beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return voornaamsWijzigingVerzoekType != null ? voornaamsWijzigingVerzoekType.getBronnen().getBron().get(0)
                .getDocument().getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen voornaamswijzigingVerzoek type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return voornaamsWijzigingVerzoekType != null ? voornaamsWijzigingVerzoekType.getBronnen().getBron().get(0)
                .getDocument().getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen voornaamswijzigingVerzoek type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return voornaamsWijzigingVerzoekType != null ? voornaamsWijzigingVerzoekType.getDatumAanvangGeldigheid()
                : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createVoornaamswijzigingVerzoek(voornaamsWijzigingVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            voornaamsWijzigingVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, VoornaamswijzigingVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een VoornaamswijzigingVerzoek bericht.", e);
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

        if (brpPersoonslijst == null && voornaamsWijzigingVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare voornaamswijzigingVerzoek een BrpPersoonslijst op.
     * 
     */
    @Override
    public void converteerNaarBrpPersoonslijst() {
        try {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(voornaamsWijzigingVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), voornaamsWijzigingVerzoekType
                            .getTijdstipOntlening().longValue(), voornaamsWijzigingVerzoekType.getBronnen().getBron()
                            .get(0), voornaamsWijzigingVerzoekType.getPersoon(), null, null);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }
    }
}
