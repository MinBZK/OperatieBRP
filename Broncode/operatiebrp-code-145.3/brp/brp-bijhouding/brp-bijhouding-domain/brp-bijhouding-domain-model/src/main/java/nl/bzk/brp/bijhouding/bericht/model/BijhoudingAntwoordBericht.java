/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Een antwoord bericht voor de bijhouding.
 */
@XmlElementen(enumType = BijhoudingBerichtSoort.class, methode = "getSoort")
public final class BijhoudingAntwoordBericht extends AbstractBmrGroep {

    private final BijhoudingBerichtSoort soort;
    private final StuurgegevensElement stuurgegevens;
    private final ResultaatElement resultaat;
    @XmlChildList(listElementType = MeldingElement.class)
    private final List<MeldingElement> meldingen;
    private final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord;
    private Long administratieveHandelingID;

    private String xml;

    /**
     * Maakt een BijhoudingVerzoekBericht object.
     *
     * @param attributen                       de attributen
     * @param soort                            het soort bijhoudingsbericht
     * @param stuurgegevens                    de stuurgegevens
     * @param resultaat                        resultaat
     * @param meldingen                        meldingen
     * @param administratieveHandelingAntwoord de administratieve handeling antwoord
     */
    public BijhoudingAntwoordBericht(
            final Map<String, String> attributen,
            final BijhoudingBerichtSoort soort,
            final StuurgegevensElement stuurgegevens,
            final ResultaatElement resultaat,
            final List<MeldingElement> meldingen,
            final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(soort, "soort");
        ValidatieHelper.controleerOpNullWaarde(stuurgegevens, "stuurgegevens");
        ValidatieHelper.controleerOpNullWaarde(administratieveHandelingAntwoord, "administratieveHandelingAntwoord");
        this.soort = soort;
        this.stuurgegevens = stuurgegevens;
        this.resultaat = resultaat;
        this.meldingen = initArrayList(meldingen);
        this.administratieveHandelingAntwoord = administratieveHandelingAntwoord;
    }

    /**
     * Geef de waarde van soort.
     *
     * @return soort
     */
    public BijhoudingBerichtSoort getSoort() {
        return soort;
    }

    /**
     * Geef de waarde van stuurgegevens.
     *
     * @return stuurgegevens
     */
    public StuurgegevensElement getStuurgegevens() {
        return stuurgegevens;
    }

    /**
     * Geef de waarde van resultaat.
     *
     * @return resultaat
     */
    public ResultaatElement getResultaat() {
        return resultaat;
    }

    /**
     * Geef de waarde van meldingen.
     *
     * @return meldingen
     */
    public List<MeldingElement> getMeldingen() {
        return Collections.unmodifiableList(meldingen);
    }

    /**
     * Geef de waarde van administratieveHandelingAntwoord.
     *
     * @return administratieveHandelingAntwoord
     */
    public AdministratieveHandelingAntwoordElement getAdministratieveHandelingAntwoord() {
        return administratieveHandelingAntwoord;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Geeft de XML representatie van dit bericht terug.
     *
     * @return de XML
     */
    public String getXml() {
        return xml;
    }

    /**
     * Zet de XML representatie van dit bericht.
     *
     * @param xml de XML
     */
    public void setXml(final String xml) {
        this.xml = xml;
    }

    /**
     * Geeft het id van de opgeslagen adminitratieve handeling terug.
     *
     * @return id administratieve handeling
     */
    public Long getAdministratieveHandelingID() {
        return administratieveHandelingID;
    }

    /**
     * Zet de ID van de administratieve handeling.
     *
     * @param administratieveHandelingID de AdministratieveHandeling
     */
    public void setAdministratieveHandelingID(final Long administratieveHandelingID) {
        this.administratieveHandelingID = administratieveHandelingID;
    }

    /**
     * Maakt een {@link BijhoudingAntwoordBericht} object.
     *
     * @param soort                            soort antwoorbericht
     * @param stuurgegevens                    stuurgegevens
     * @param resultaat                        resultaat
     * @param meldingen                        gevonden meldingen
     * @param administratieveHandelingAntwoord administratieve handeling
     * @return een {@link BijhoudingAntwoordBericht}
     */
    public static BijhoudingAntwoordBericht getInstance(
            final BijhoudingBerichtSoort soort,
            final StuurgegevensElement stuurgegevens,
            final ResultaatElement resultaat,
            final List<MeldingElement> meldingen,
            final AdministratieveHandelingAntwoordElement administratieveHandelingAntwoord) {
        return new BijhoudingAntwoordBericht(new AttributenBuilder().build(), soort, stuurgegevens, resultaat, meldingen, administratieveHandelingAntwoord);
    }
}
