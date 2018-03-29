/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De NaamgebruikElement voor een bijhoudingsbericht.
 */
@XmlElement("naamgebruik")
public final class NaamgebruikElement extends AbstractNaamElement {

    @XmlChild(volgorde = 1)
    private final StringElement code;
    @XmlChild(volgorde = 5)
    private final BooleanElement indicatieAfgeleid;
    @XmlChild(volgorde = 15)
    private final StringElement voornamen;
    @XmlChild(volgorde = 50)
    private final StringElement geslachtsnaamstam;

    /**
     * Maakt een GeslachtsnaamcomponentElement object.
     *
     * @param attributen attributen
     * @param code code
     * @param indicatieAfgeleid indicatie afgeleid
     * @param predicaatCode predicaatCode
     * @param voornamen voornamen
     * @param adellijkeTitelCode adellijkeTitelCode
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param geslachtsnaamstam geslachtsnaamstam
     */
    //
    public NaamgebruikElement(
        final Map<String, String> attributen,
        final StringElement code,
        final BooleanElement indicatieAfgeleid,
        final StringElement predicaatCode,
        final StringElement voornamen,
        final StringElement adellijkeTitelCode,
        final StringElement voorvoegsel,
        final CharacterElement scheidingsteken,
        final StringElement geslachtsnaamstam){
        super(attributen, predicaatCode, adellijkeTitelCode, voorvoegsel, scheidingsteken);
        ValidatieHelper.controleerOpNullWaarde(code, "code");
        ValidatieHelper.controleerOpNullWaarde(indicatieAfgeleid, "indicatieAfgeleid");
        this.code = code;
        this.indicatieAfgeleid = indicatieAfgeleid;
        this.voornamen = voornamen;
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van code.
     *
     * @return code
     */
    public StringElement getCode() {
        return code;
    }

    /**
     * Geef de waarde van indicatieAfgeleid.
     *
     * @return indicatieAfgeleid
     */
    public BooleanElement getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Geeft de waarde van voornamen.
     *
     * @return voornamen
     */
    public StringElement getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van geslachtsnaamstam.
     *
     * @return geslachtsnaamstam
     */
    public StringElement getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    @Override
    @Bedrijfsregel(Regel.R1677)
    @Bedrijfsregel(Regel.R1680)
    @Bedrijfsregel(Regel.R1681)
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        if (!bestaatNaamgebruikCode()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1681, this));
        }
        if (indicatieAfgeleid.getWaarde()
            && !ValidationUtils.zijnParametersAllemaalNull(
                voornamen,
                getPredicaatCode(),
                getAdellijkeTitelCode(),
                getVoorvoegsel(),
                getScheidingsteken(),
                geslachtsnaamstam)){
            meldingen.add(MeldingElement.getInstance(Regel.R1677, this));
        }
        if (!indicatieAfgeleid.getWaarde() && ValidationUtils.zijnParametersAllemaalNull(geslachtsnaamstam)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1680, this));
        }
        return meldingen;
    }

    @Override
    protected Regel getRegelVoorNietBestaandVoorvoegselScheidingstekenPaar() {
        return Regel.R2160;
    }

    @Override
    protected Regel getRegelVoorvoegselOfscheidingstekenBeideLeegOfGevuld() {
        return Regel.R2229;
    }

    @Override
    protected Regel getRegelVoorAdellijkeTitelEnPredikaatBeideAanwezig() {
        return Regel.R2162;
    }

    private boolean bestaatNaamgebruikCode() {
        return Naamgebruik.bestaatCode(code.getWaarde());
    }
}
