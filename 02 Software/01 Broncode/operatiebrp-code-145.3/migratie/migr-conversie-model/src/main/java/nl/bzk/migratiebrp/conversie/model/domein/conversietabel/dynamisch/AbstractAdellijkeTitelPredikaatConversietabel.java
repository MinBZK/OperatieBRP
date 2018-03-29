/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * De conversietabel voor de converie van 'LO3 Adellijke Titel/Predikaat'-code naar 'BRP Adellijke Titel,
 * Predikaat'-paar en vice versa.
 */
public abstract class AbstractAdellijkeTitelPredikaatConversietabel extends
        AbstractConversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> {

    private final List<Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> conversieLijst;

    /**
     * Maakt een AdellijkeTitelPredikaatConversietabel object.
     * @param conversieLijst de lijst met titel conversies
     */
    public AbstractAdellijkeTitelPredikaatConversietabel(final List<Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> conversieLijst) {
        super(conversieLijst);
        this.conversieLijst = conversieLijst;
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekLo3(final Lo3AdellijkeTitelPredikaatCode input) {
        if (input == null) {
            return null;
        } else {
            return input.getOnderzoek();
        }
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekBrp(final AdellijkeTitelPredikaatPaar input) {
        if (input == null) {
            return null;
        } else {
            final List<Lo3Onderzoek> onderzoeken = new ArrayList<>();
            if (input.getAdellijkeTitel() != null && input.getAdellijkeTitel().getOnderzoek() != null) {
                onderzoeken.add(input.getAdellijkeTitel().getOnderzoek());
            }
            if (input.getPredikaat() != null && input.getPredikaat().getOnderzoek() != null) {
                onderzoeken.add(input.getPredikaat().getOnderzoek());
            }
            if (input.getGeslachtsaanduiding() != null && input.getGeslachtsaanduiding().getOnderzoek() != null) {
                onderzoeken.add(input.getGeslachtsaanduiding().getOnderzoek());
            }
            return Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        }
    }

    @Override
    protected final Lo3AdellijkeTitelPredikaatCode voegOnderzoekToeLo3(final Lo3AdellijkeTitelPredikaatCode input, final Lo3Onderzoek onderzoek) {
        final Lo3AdellijkeTitelPredikaatCode resultaat;
        if (!Lo3Validatie.isElementGevuld(input)) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat = new Lo3AdellijkeTitelPredikaatCode(null, onderzoek);
            }
        } else {
            resultaat = new Lo3AdellijkeTitelPredikaatCode(input.getWaarde(), onderzoek);
        }

        return resultaat;
    }

    @Override
    protected final AdellijkeTitelPredikaatPaar voegOnderzoekToeBrp(final AdellijkeTitelPredikaatPaar input, final Lo3Onderzoek onderzoek) {
        final AdellijkeTitelPredikaatPaar resultaat;

        if (input == null || !(BrpValidatie.isAttribuutGevuld(input.getAdellijkeTitel()) || BrpValidatie.isAttribuutGevuld(input.getPredikaat()))) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat =
                        new AdellijkeTitelPredikaatPaar(
                                new BrpCharacter(null, onderzoek),
                                new BrpCharacter(null, onderzoek),
                                new BrpGeslachtsaanduidingCode(null, onderzoek));
            }
        } else {
            return new AdellijkeTitelPredikaatPaar(BrpCharacter.wrap(BrpCharacter.unwrap(input.getAdellijkeTitel()), onderzoek), BrpCharacter.wrap(
                    BrpCharacter.unwrap(input.getPredikaat()),
                    onderzoek), new BrpGeslachtsaanduidingCode(input.getGeslachtsaanduiding().getWaarde(), onderzoek));
        }

        return resultaat;
    }

    /**
     * Controleert of de bijzondere situatie LB035 van toepassing is op de combinatie adellijktitel/predicaat en
     * geslachtsaanduiding.
     * @param adellijkeTitelPredikaatCode de adellijketitel of predicaat
     * @param geslachtsaanduiding de geslachtsaanduiding
     * @return true als de combinatie van adellijkeTitelPredikaatCode en geslachtsaanduiding niet voorkomt in de conversietabel
     */
    public final boolean isBijzondereSituatieLB035VanToepassing(
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3Geslachtsaanduiding geslachtsaanduiding) {
        boolean resultaat = true;
        for (final Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> entry : conversieLijst) {
            if (AbstractLo3Element.equalsWaarde(entry.getKey(), adellijkeTitelPredikaatCode)
                    && entry.getValue().getGeslachtsaanduiding().getWaarde().equals(geslachtsaanduiding.getWaarde())) {
                resultaat = false;
                break;
            }
        }
        return resultaat;
    }
}
