/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.JAXBException;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.AbstractBrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.BrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.GeboorteVerzoekType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjecttypeBron;
import nl.moderniseringgba.isc.esb.message.brp.generated.ViewOuder;
import nl.moderniseringgba.isc.esb.message.util.BrpPersoonslijstUtils;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Document;

/**
 * GeboortVerzoekbericht.
 */
public final class GeboorteVerzoekBericht extends AbstractBrpBericht implements BrpVerzoekBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BRP_PERSOONSLIJST = "brpPersoonslijst";

    private transient BrpPersoonslijst brpPersoonslijst;

    private GeboorteVerzoekType geboorteVerzoekType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public GeboorteVerzoekBericht() {
        geboorteVerzoekType = new GeboorteVerzoekType();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * 
     * @param geboorteVerzoekType
     *            het geboorteVerzoek type
     */
    public GeboorteVerzoekBericht(final GeboorteVerzoekType geboorteVerzoekType) {
        this.geboorteVerzoekType = geboorteVerzoekType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de Brp Persoonslijst terug.
     * 
     * @return De brp persoonslijst.
     */
    public BrpPersoonslijst getBrpPersoonslijst() {

        if (brpPersoonslijst == null && geboorteVerzoekType != null) {
            converteerNaarBrpPersoonslijst();
        }

        return brpPersoonslijst;
    }

    public void setBrpPersoonslijst(final BrpPersoonslijst brpPersoonslijst) {
        this.brpPersoonslijst = brpPersoonslijst;
    }

    @Override
    public String getBerichtType() {
        return "GeboorteVerzoek";
    }

    @Override
    public String getStartCyclus() {
        return "uc306";
    }

    @Override
    public GeboorteAntwoordBericht maakAntwoordBericht() {
        final GeboorteAntwoordBericht antwoord = new GeboorteAntwoordBericht();
        antwoord.setCorrelationId(getMessageId());
        return antwoord;
    }

    /* ************************************************************************************************************* */

    /**
     * @return Iscgemeente.brpGemeente
     */
    public BrpGemeenteCode getBrpGemeente() {
        return super.getBrpGemeente(geboorteVerzoekType.getIscGemeenten());
    }

    /**
     * Zet IscGemeente.brpGemeente.
     * 
     * @param gemeente
     *            gemeente
     */
    public void setBrpGemeente(final BrpGemeenteCode gemeente) {
        geboorteVerzoekType.setIscGemeenten(setBrpGemeente(geboorteVerzoekType.getIscGemeenten(), gemeente));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef het eerste aktenummer in de bronnen.
     * 
     * @return aktenummer (of null)
     */
    public String getAktenummer() {
        for (final ObjecttypeBron bron : geboorteVerzoekType.getBronnen().getBron()) {
            return bron.getDocument().getAktenummer().getValue();
        }
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpBerichtFactory.SINGLETON.elementToString(new ObjectFactory()
                .createGeboorteVerzoek(geboorteVerzoekType));
    }

    @Override
    public void parse(final Document document) throws BerichtInhoudException {
        try {
            geboorteVerzoekType =
                    BrpBerichtFactory.SINGLETON.getUnmarshaller().unmarshal(document, GeboorteVerzoekType.class)
                            .getValue();
        } catch (final JAXBException e) {
            throw new BerichtInhoudException(
                    "Onbekende fout tijdens het unmarshallen van een GeboorteVerzoek bericht.", e);
        }
    }

    /**
     * Stelt op basis van het beschikbare geboorteVerzoekType een BrpPersoonslijst op.
     * 
     */
    public void converteerNaarBrpPersoonslijst() {
        try {

            final ViewOuder ouder1 =
                    geboorteVerzoekType.getFamilierechtelijkeBetrekking().getBetrokkenheden().getOuder().get(0);

            ViewOuder ouder2 = null;
            if (geboorteVerzoekType.getFamilierechtelijkeBetrekking().getBetrokkenheden().getOuder().size() > 1) {
                ouder2 = geboorteVerzoekType.getFamilierechtelijkeBetrekking().getBetrokkenheden().getOuder().get(1);

            }

            brpPersoonslijst =
                    BrpPersoonslijstUtils.converteerNaarBrpPersoonslijst(geboorteVerzoekType
                            .getDatumAanvangGeldigheid().intValue(), geboorteVerzoekType.getTijdstipOntlening()
                            .longValue(), geboorteVerzoekType.getBronnen().getBron().get(0), geboorteVerzoekType
                            .getFamilierechtelijkeBetrekking().getBetrokkenheden().getKind().get(0).getPersoon(),
                            ouder1, ouder2);
        } catch (final NullPointerException exception) {
            brpPersoonslijst = null;
        }

    }

    /* ************************************************************************************************************* */

    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        is.defaultReadObject();

        if (is.available() > 0) {
            brpPersoonslijst = PersoonslijstDecoder.decodeBrpPersoonslijst(is);
        }
    }

    private void writeObject(final ObjectOutputStream os) throws IOException {
        // perform the default serialization for all non-transient, non-static fields
        os.defaultWriteObject();

        if (brpPersoonslijst != null) {
            PersoonslijstEncoder.encodePersoonslijst(brpPersoonslijst, os);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(BRP_PERSOONSLIJST, brpPersoonslijst).toString();
    }

}
