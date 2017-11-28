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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het gegeven in onderzoek element uit het bijhoudingsbericht.
 */
@XmlElement("onderzoek")
public final class OnderzoekGroepElement extends AbstractBmrGroep {

    private final DatumElement datumAanvang;
    private final DatumElement datumEinde;
    private final StringElement omschrijving;
    private final StringElement statusNaam;

    /**
     * Maak een OnderzoekGroepElement object.
     * @param attributen de attributen
     * @param datumAanvang datum aanvang
     * @param datumEinde datum einde
     * @param omschrijving omschrijving
     * @param statusNaam status naam
     */
    public OnderzoekGroepElement(final Map<String, String> attributen, final DatumElement datumAanvang, final DatumElement datumEinde,
                                 final StringElement omschrijving, final StringElement statusNaam) {
        super(attributen);
        this.datumAanvang = datumAanvang;
        this.datumEinde = datumEinde;
        this.omschrijving = omschrijving;
        this.statusNaam = statusNaam;
    }

    /**
     * Geef de waarde van datumAanvang.
     * @return datumAanvang
     */
    public DatumElement getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Geef de waarde van datumEinde.
     * @return datumEinde
     */
    public DatumElement getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    public StringElement getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van statusNaam.
     * @return statusNaam
     */
    public StringElement getStatusNaam() {
        return statusNaam;
    }

    /**
     * Geef de {@link StatusOnderzoek} die hoort bij de {@link #getStatusNaam()} of null als er geen match is.
     * @return de StatusOnderzoek van deze groep
     */
    public StatusOnderzoek getStatusOnderzoek() {
        for (final StatusOnderzoek statusOnderzoek : StatusOnderzoek.values()) {
            if (statusOnderzoek.getNaam().equals(BmrAttribuut.getWaardeOfNull(statusNaam))) {
                return statusOnderzoek;
            }
        }
        return null;
    }

    @Bedrijfsregel(Regel.R2595)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        List<MeldingElement> meldingen = new ArrayList<>();
        if (getDatumAanvang() != null && getDatumAanvang().getWaarde() > getVerzoekBericht().getDatumOntvangst().getWaarde()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2595, this));
        }
        return meldingen;
    }

    /**
     * Helper methode voor het maken van een OnderzoekGroepElement object.
     * @param communicatieId communicatieId
     * @param datumAanvang datumAanvang
     * @param datumEinde datumEinde
     * @param omschrijving omschrijving
     * @param statusNaam statusNaam
     * @return een OnderzoekGroepElement object
     */
    public static OnderzoekGroepElement getInstance(final String communicatieId, final Integer datumAanvang, final Integer datumEinde,
                                                    final String omschrijving, final String statusNaam) {
        final DatumElement datumAanvangElement = datumAanvang == null ? null : new DatumElement(datumAanvang);
        final DatumElement datumEindeElement = datumEinde == null ? null : new DatumElement(datumEinde);
        return new OnderzoekGroepElement(new AttributenBuilder().communicatieId(communicatieId).build(), datumAanvangElement, datumEindeElement,
                StringElement.getInstance(omschrijving), StringElement.getInstance(statusNaam));
    }
}
