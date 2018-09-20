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
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapDoorMoederVerzoekType;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor ontkenning vaderschap door de moeder na geboorte.
 */
public final class OntkenningVaderschapDoorMoederVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private OntkenningVaderschapDoorMoederVerzoekType ontkenningVaderschapDoorMoederVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public OntkenningVaderschapDoorMoederVerzoekBericht() {
        ontkenningVaderschapDoorMoederVerzoekType = new OntkenningVaderschapDoorMoederVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een ontkenning vaderschap door
     * moeder verzoek te maken.
     * 
     * @param ontkenningVaderschapDoorMoederVerzoekType
     *            het ontkenningVaderschapDoorMoederVerzoek type
     */
    public OntkenningVaderschapDoorMoederVerzoekBericht(
            final OntkenningVaderschapDoorMoederVerzoekType ontkenningVaderschapDoorMoederVerzoekType) {
        this.ontkenningVaderschapDoorMoederVerzoekType = ontkenningVaderschapDoorMoederVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "OntkenningVaderschapDoorMoederVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public OntkenningVaderschapDoorMoederAntwoordBericht maakAntwoordBericht() {
        final OntkenningVaderschapDoorMoederAntwoordBericht antwoord =
                new OntkenningVaderschapDoorMoederAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(ontkenningVaderschapDoorMoederVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        ontkenningVaderschapDoorMoederVerzoekType.setIscGemeenten(setBrpGemeente(
                ontkenningVaderschapDoorMoederVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(ontkenningVaderschapDoorMoederVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        ontkenningVaderschapDoorMoederVerzoekType.setIscGemeenten(setLo3Gemeente(
                ontkenningVaderschapDoorMoederVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen ontkenning vaderschap door moeder type
     *         beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return ontkenningVaderschapDoorMoederVerzoekType != null ? ontkenningVaderschapDoorMoederVerzoekType
                .getBronnen().getBron().get(0).getDocument().getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen ontkenning vaderschap door moeder type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return ontkenningVaderschapDoorMoederVerzoekType != null ? ontkenningVaderschapDoorMoederVerzoekType
                .getBronnen().getBron().get(0).getDocument().getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen ontkenning vaderschap door moeder type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return ontkenningVaderschapDoorMoederVerzoekType != null ? ontkenningVaderschapDoorMoederVerzoekType
                .getKind().getPersoon().getGeboorte().get(0).getDatum().getValue() : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createOntkenningVaderschapDoorMoederVerzoek(ontkenningVaderschapDoorMoederVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            ontkenningVaderschapDoorMoederVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, OntkenningVaderschapDoorMoederVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een OntkenningVaderschapDoorMoederVerzoek bericht.",
                    e);
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

        if (brpPersoonslijst == null && ontkenningVaderschapDoorMoederVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare ontkenning vaderschap door moeder type een BrpPersoonslijst op.
     * 
     */
    @Override
    public void converteerNaarBrpPersoonslijst() {
        try {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(ontkenningVaderschapDoorMoederVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), ontkenningVaderschapDoorMoederVerzoekType
                            .getTijdstipOntlening().longValue(), ontkenningVaderschapDoorMoederVerzoekType
                            .getBronnen().getBron().get(0), ontkenningVaderschapDoorMoederVerzoekType.getKind()
                            .getPersoon(), null, null);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }
    }
}
