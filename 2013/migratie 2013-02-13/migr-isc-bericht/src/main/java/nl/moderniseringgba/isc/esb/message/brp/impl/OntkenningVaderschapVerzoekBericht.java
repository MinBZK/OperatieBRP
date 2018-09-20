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
import nl.moderniseringgba.isc.esb.message.brp.generated.OntkenningVaderschapVerzoekType;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.w3c.dom.Document;

/**
 * Bericht voor ontkenning vaderschap na geboorte.
 */
public final class OntkenningVaderschapVerzoekBericht extends BrpBijhoudingVerzoekBericht {
    private static final long serialVersionUID = 1L;

    private transient BrpPersoonslijst brpPersoonslijst;

    private OntkenningVaderschapVerzoekType ontkenningVaderschapVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public OntkenningVaderschapVerzoekBericht() {
        ontkenningVaderschapVerzoekType = new OntkenningVaderschapVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een ontkenning vaderschap
     * verzoek te maken.
     * 
     * @param ontkenningVaderschapVerzoekType
     *            het ontkenningVaderschapVerzoek type
     */
    public OntkenningVaderschapVerzoekBericht(final OntkenningVaderschapVerzoekType ontkenningVaderschapVerzoekType) {
        this.ontkenningVaderschapVerzoekType = ontkenningVaderschapVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "OntkenningVaderschapVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc308";
    }

    @Override
    public OntkenningVaderschapAntwoordBericht maakAntwoordBericht() {
        final OntkenningVaderschapAntwoordBericht antwoord = new OntkenningVaderschapAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    @Override
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(ontkenningVaderschapVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        ontkenningVaderschapVerzoekType.setIscGemeenten(setBrpGemeente(
                ontkenningVaderschapVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * @return Iscgemeente.lo3Gemeente
     */
    @Override
    public BrpGemeenteCode getLo3Gemeente() {
        return super.getLo3Gemeente(ontkenningVaderschapVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.lo3Gemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    @Override
    public void setLo3Gemeente(final BrpGemeenteCode gemeente) {
        ontkenningVaderschapVerzoekType.setIscGemeenten(setLo3Gemeente(
                ontkenningVaderschapVerzoekType.getIscGemeenten(), gemeente));
    }

    /**
     * Geeft de registratiegemeente die op het bericht staat terug.
     * 
     * @return De registratiegemeente of null in het geval dat er geen ontkenning vaderschap type beschikbaar is.
     */
    @Override
    public String getRegistratiegemeente() {
        return ontkenningVaderschapVerzoekType != null ? ontkenningVaderschapVerzoekType.getBronnen().getBron()
                .get(0).getDocument().getPartijCode().getValue() : null;
    }

    /**
     * Geeft het aktenummer dat op het bericht staat terug.
     * 
     * @return Het aktenummer of null in het geval dat er geen ontkenning vaderschap type beschikbaar is.
     */
    @Override
    public String getAktenummer() {
        return ontkenningVaderschapVerzoekType != null ? ontkenningVaderschapVerzoekType.getBronnen().getBron()
                .get(0).getDocument().getAktenummer().getValue() : null;
    }

    /**
     * Geeft de ingangsdatum die op het bericht staat terug.
     * 
     * @return De ingangsdatum of null in het geval dat er geen ontkenning vaderschap type beschikbaar is.
     */
    @Override
    public BigInteger getIngangsdatumGeldigheid() {
        return ontkenningVaderschapVerzoekType != null ? ontkenningVaderschapVerzoekType.getDatumAanvangGeldigheid()
                : null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createOntkenningVaderschapVerzoek(ontkenningVaderschapVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            ontkenningVaderschapVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller()
                            .unmarshal(document, OntkenningVaderschapVerzoekType.class).getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een OntkenningVaderschapVerzoek bericht.", e);
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

        if (brpPersoonslijst == null && ontkenningVaderschapVerzoekType != null) {
            try {
                converteerNaarBrpPersoonslijst();
            } catch (final BerichtInhoudException e) {
                brpPersoonslijst = null;
            }
        }

        return brpPersoonslijst;
    }

    /* ************************************************************************************************************* */

    /**
     * Stelt op basis van het beschikbare ontkenning vaderschap type een BrpPersoonslijst op.
     * 
     * @throws BerichtInhoudException
     *             Indien de inhoud niet correct is.
     */
    @Override
    public void converteerNaarBrpPersoonslijst() throws BerichtInhoudException {
        if (ontkenningVaderschapVerzoekType != null) {
            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(ontkenningVaderschapVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), ontkenningVaderschapVerzoekType
                            .getTijdstipOntlening().longValue(), ontkenningVaderschapVerzoekType.getBronnen()
                            .getBron().get(0), ontkenningVaderschapVerzoekType.getKind().getPersoon(), null, null);
        }
    }
}
