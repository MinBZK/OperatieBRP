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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Een bron uit het bijhoudingsbericht.
 */
@XmlElement("bron")
public final class BronElement extends AbstractBmrObjecttype {

    private final DocumentElement document;
    private final StringElement rechtsgrondCode;

    /**
     * Maakt een BronElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param document document
     * @param rechtsgrondCode rechtsgrondCode
     */
    public BronElement(final Map<String, String> basisAttribuutGroep, final DocumentElement document, final StringElement rechtsgrondCode) {
        super(basisAttribuutGroep);
        this.document = document;
        this.rechtsgrondCode = rechtsgrondCode;
    }

    /**
     * Geef de waarde van document.
     * @return document
     */
    public DocumentElement getDocument() {
        return document;
    }

    /**
     * Geef de waarde van rechtsgrondCode.
     * @return rechtsgrondCode
     */
    public StringElement getRechtsgrondCode() {
        return rechtsgrondCode;
    }

    /**
     * Geeft de rechtsgrond horende bij de {@link #getRechtsgrondCode}.
     * @return de rechtsrond of null als {@link #getRechtsgrondCode} null is of als er geen rechtsgrond gevonden kan worden voor deze code
     */
    public Rechtsgrond getRechtsgrond() {
        if (rechtsgrondCode == null) {
            return null;
        } else {
            return getDynamischeStamtabelRepository().getRechtsgrondByCode(BmrAttribuut.getWaardeOfNull(rechtsgrondCode));
        }
    }

    @Bedrijfsregel(Regel.R2430)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> results = new ArrayList<>();
        if (rechtsgrondCode != null && getRechtsgrond() == null) {
            results.add(MeldingElement.getInstance(Regel.R2430, this));
        }
        return results;
    }

    /**
     * Controleert of een rechtsgrond die geïdentificeerd wordt met de rechtsgrondCode ongeldig is op de gegeven peildatum.
     * @param peildatum de peildatum
     * @return true als er een rechtsgrond bestaat voor de rechtsgrondCode en deze rechtsgrond ongeldig is op de gegeven peildatum, anders false
     */
    boolean isRechtsgrondOngeldigOpPeildatum(final int peildatum) {
        final Rechtsgrond rechtsgrond = getRechtsgrond();
        return rechtsgrond != null && !DatumUtil
                .valtDatumBinnenPeriode(peildatum, rechtsgrond.getDatumAanvangGeldigheid(), rechtsgrond.getDatumEindeGeldigheid());
    }
}
