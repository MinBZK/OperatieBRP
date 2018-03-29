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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;

/**
 * Abstracte class voor alles wat met naam te maken heeft.
 */
public abstract class AbstractNaamElement extends AbstractBmrGroep {

    @XmlChild(volgorde = 10)
    private final StringElement predicaatCode;
    @XmlChild(volgorde = 20)
    private final StringElement adellijkeTitelCode;
    @XmlChild(volgorde = 30)
    private final StringElement voorvoegsel;
    @XmlChild(volgorde = 40)
    private final CharacterElement scheidingsteken;

    /**
     * Constructor.
     *
     * @param attributen attributen
     * @param predicaatCode predicaatCode
     * @param adellijkeTitelCode adellijkeTitelCode
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     */
    protected AbstractNaamElement(
        final Map<String, String> attributen,
        final StringElement predicaatCode,
        final StringElement adellijkeTitelCode,
        final StringElement voorvoegsel,
        final CharacterElement scheidingsteken){
        super(attributen);
        this.predicaatCode = predicaatCode;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van predicaatCode.
     *
     * @return predicaatCode
     */
    public final StringElement getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geeft het {@link Predicaat} terug die bij de {@link #predicaatCode} hoort. Null als de code niet bestaat.
     *
     * @return een {@link Predicaat} of null
     */
    final Predicaat getPredicaat() {
        return Predicaat.parseCode(BmrAttribuut.getWaardeOfNull(predicaatCode));
    }

    /**
     * Geef de waarde van adellijkeTitelCode.
     *
     * @return adellijkeTitelCode
     */
    public final StringElement getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geeft het {@link AdellijkeTitel} terug die bij de {@link #adellijkeTitelCode} hoort. Null als de code niet
     * bestaat.
     *
     * @return een {@link AdellijkeTitel} of null
     */
    final AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseCode(BmrAttribuut.getWaardeOfNull(adellijkeTitelCode));
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return voorvoegsel
     */
    public final StringElement getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public final CharacterElement getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    protected final List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerAdellijkeTitelPredicaatCode(meldingen);
        controleerVoorvoegselScheidingstekenPaar(meldingen);

        meldingen.addAll(valideerSpecifiekeInhoud());
        return meldingen;
    }

    /**
     * Valideer de specifieke implementatie van deze class.
     *
     * @return de lijst van meldingen gevonden bij de specifiek implementatie
     */
    protected abstract List<MeldingElement> valideerSpecifiekeInhoud();

    /**
     * Geeft de {@link Regel} terug die gemeld moet worden voor deze controle.
     *
     * @return de regel
     */
    protected abstract Regel getRegelVoorNietBestaandVoorvoegselScheidingstekenPaar();

    /**
     * Geeft de {@link Regel} terug die gemeld moet worden voor deze controle.
     *
     * @return de regel
     */
    protected abstract Regel getRegelVoorvoegselOfscheidingstekenBeideLeegOfGevuld();

    /**
     * Geeft de {@link Regel} terug die gemeld moet worden voor deze controle.
     *
     * @return de regel
     */
    protected abstract Regel getRegelVoorAdellijkeTitelEnPredikaatBeideAanwezig();

    @Bedrijfsregel(Regel.R1672)
    @Bedrijfsregel(Regel.R1676)
    @Bedrijfsregel(Regel.R2160)
    @Bedrijfsregel(Regel.R2161)
    @Bedrijfsregel(Regel.R2228)
    @Bedrijfsregel(Regel.R2229)
    private void controleerVoorvoegselScheidingstekenPaar(final List<MeldingElement> meldingen) {
        if ((scheidingsteken == null) != (voorvoegsel == null)) {
            meldingen.add(MeldingElement.getInstance(getRegelVoorvoegselOfscheidingstekenBeideLeegOfGevuld(), this));
        }
        if (scheidingsteken != null && voorvoegsel != null) {
            final VoorvoegselSleutel paar = new VoorvoegselSleutel(scheidingsteken.getWaarde(), voorvoegsel.getWaarde());
            if (getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(paar) == null) {
                meldingen.add(MeldingElement.getInstance(getRegelVoorNietBestaandVoorvoegselScheidingstekenPaar(), this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1673)
    @Bedrijfsregel(Regel.R2162)
    @Bedrijfsregel(Regel.R2163)
    private void controleerAdellijkeTitelPredicaatCode(final List<MeldingElement> meldingen) {
        if (predicaatCode != null && adellijkeTitelCode != null) {
            meldingen.add(MeldingElement.getInstance(getRegelVoorAdellijkeTitelEnPredikaatBeideAanwezig(), this));
        }
    }
}
