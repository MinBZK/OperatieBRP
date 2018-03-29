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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De SamengesteldeNaamElement voor een bijhoudingsbericht.
 */
@XmlElement("samengesteldeNaam")
public final class SamengesteldeNaamElement extends AbstractNaamElement {
    @XmlChild(volgorde = 5)
    private final BooleanElement indicatieNamenreeks;
    @XmlChild(volgorde = 15)
    private final StringElement voornamen;
    @XmlChild(volgorde = 50)
    private final StringElement geslachtsnaamstam;

    /**
     * Maakt een SamengesteldeNaamElement object.
     * @param attributen attributen
     * @param indicatieNamenreeks indicatieNamenreeks
     * @param predicaatCode predicaatCode
     * @param voornamen voornamen
     * @param adellijkeTitelCode adellijkeTitelCode
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param geslachtsnaamstam geslachtsnaamstam
     */
    //
    public SamengesteldeNaamElement(
            final Map<String, String> attributen,
            final BooleanElement indicatieNamenreeks,
            final StringElement predicaatCode,
            final StringElement voornamen,
            final StringElement adellijkeTitelCode,
            final StringElement voorvoegsel,
            final CharacterElement scheidingsteken,
            final StringElement geslachtsnaamstam) {
        super(attributen, predicaatCode, adellijkeTitelCode, voorvoegsel, scheidingsteken);
        this.indicatieNamenreeks = indicatieNamenreeks;
        this.geslachtsnaamstam = geslachtsnaamstam;
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van indicatieNamenreeks.
     * @return indicatieNamenreeks
     */
    public BooleanElement getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Geeft de waarde van voornamen.
     * @return voornamen
     */
    public StringElement getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van geslachtsnaamstam.
     * @return geslachtsnaamstam
     */
    public StringElement getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    @Bedrijfsregel(Regel.R1810)
    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        if (getVoorkomenSleutel() != null) {
            // De enige keer dat er een voorkomen sleutel wordt gebruikt is bij correctie verval. Dan moet er niet gecontroleerd worden
            return VALIDATIE_OK;
        }
        final List<MeldingElement> meldingen = new ArrayList<>();

        if (indicatieNamenreeks.getWaarde() && getVoorvoegsel() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1810, this));
        }

        return meldingen;
    }

    @Override
    protected Regel getRegelVoorNietBestaandVoorvoegselScheidingstekenPaar() {
        return Regel.R1676;
    }

    @Override
    protected Regel getRegelVoorvoegselOfscheidingstekenBeideLeegOfGevuld() {
        return Regel.R1672;
    }

    @Override
    protected Regel getRegelVoorAdellijkeTitelEnPredikaatBeideAanwezig() {
        return Regel.R1673;
    }

    /**
     * Maakt een SamengesteldeNaamElement o.b.v. een {@link PersoonSamengesteldeNaamHistorie} voorkomen, of null als dit voorkomen null is.
     * @param voorkomen het voorkomen waarvan de gegevens gebruikt moeten worden om dit element te maken, mag null zijn
     * @param verzoekBericht het verzoek bericht waar dit element deel vanuit moet gaan maken
     * @return het nieuwe element
     */
    public static SamengesteldeNaamElement getInstance(final PersoonSamengesteldeNaamHistorie voorkomen, final BijhoudingVerzoekBericht verzoekBericht) {
        if (voorkomen == null) {
            return null;
        } else {
            final BooleanElement indicatieNamenreeksElement;
            final StringElement geslachtsnaamstamElement;
            final StringElement voornamenElement;
            final StringElement predicaatCodeElement;
            final StringElement adellijkeTitelCodeElement;
            final StringElement voorvoegselElement;
            final CharacterElement scheidingstekenElement;

            //verplichte elementen
            indicatieNamenreeksElement = new BooleanElement(voorkomen.getIndicatieNamenreeks());
            geslachtsnaamstamElement = new StringElement(voorkomen.getGeslachtsnaamstam());
            //optionele elementen
            voornamenElement = voorkomen.getVoornamen() == null ? null : new StringElement(voorkomen.getVoornamen());
            predicaatCodeElement = voorkomen.getPredicaat() == null ? null : new StringElement(voorkomen.getPredicaat().getCode());
            adellijkeTitelCodeElement = voorkomen.getAdellijkeTitel() == null ? null : new StringElement(voorkomen.getAdellijkeTitel().getCode());
            voorvoegselElement = voorkomen.getVoorvoegsel() == null ? null : new StringElement(voorkomen.getVoorvoegsel());
            scheidingstekenElement = voorkomen.getScheidingsteken() == null ? null : new CharacterElement(voorkomen.getScheidingsteken());

            final SamengesteldeNaamElement
                    result =
                    new SamengesteldeNaamElement(new AttributenBuilder().build(), indicatieNamenreeksElement, predicaatCodeElement, voornamenElement,
                            adellijkeTitelCodeElement, voorvoegselElement, scheidingstekenElement, geslachtsnaamstamElement);
            result.setVerzoekBericht(verzoekBericht);
            return result;
        }
    }
}
