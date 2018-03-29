/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * De AdministratieveHandelingAntwoordElement uit het bijhoudingsbericht.
 */
@XmlElementen(enumType = AdministratieveHandelingElementSoort.class, methode = "getSoort")
public final class AdministratieveHandelingAntwoordElement extends AbstractBmrObjecttype {

    private final AdministratieveHandelingElementSoort soort;
    private final StringElement partijCode;
    private final DatumTijdElement tijdstipRegistratie;
    @XmlChildList(listElementType = PersoonGegevensElement.class)
    private final List<PersoonGegevensElement> bezienVanuitPersonen;
    @XmlChildList(listElementType = GedeblokkeerdeMeldingElement.class)
    private final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen;
    @XmlChildList(listElementType = PersoonGegevensElement.class)
    private final List<PersoonGegevensElement> bijgehoudenPersonen;
    private final BijhoudingsplanElement bijhoudingsplan;

    /**
     * Maakt een AdministratieveHandelingAntwoordElement object.
     *
     * @param basisAttribuutGroep     de basis attribuutgroep
     * @param soort                   soort, mag niet null zijn
     * @param partijCode              partijCode, mag niet null zijn
     * @param tijdstipRegistratie     tijdstip registratie
     * @param bezienVanuitPersonen    de bezien vanuit personen
     * @param gedeblokkeerdeMeldingen gedeblokkeerdeMeldingen
     * @param bijgehoudenPersonen     bijgehouden personen
     * @param bijhoudingsplan         bijhoudingsplan
     */
    //
    public AdministratieveHandelingAntwoordElement(
            final Map<String, String> basisAttribuutGroep,
            final AdministratieveHandelingElementSoort soort,
            final StringElement partijCode,
            final DatumTijdElement tijdstipRegistratie,
            final List<PersoonGegevensElement> bezienVanuitPersonen,
            final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen,
            final List<PersoonGegevensElement> bijgehoudenPersonen,
            final BijhoudingsplanElement bijhoudingsplan) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(soort, "soort");
        ValidatieHelper.controleerOpNullWaarde(partijCode, "partijCode");
        this.soort = soort;
        this.partijCode = partijCode;
        this.tijdstipRegistratie = tijdstipRegistratie;
        this.bezienVanuitPersonen = initArrayList(bezienVanuitPersonen);
        this.gedeblokkeerdeMeldingen = initArrayList(gedeblokkeerdeMeldingen);
        this.bijgehoudenPersonen = initArrayList(bijgehoudenPersonen);
        this.bijhoudingsplan = bijhoudingsplan;
    }

    /**
     * Geef de waarde van soort.
     *
     * @return soort
     */
    public AdministratieveHandelingElementSoort getSoort() {
        return soort;
    }

    /**
     * Geef de waarde van partijCode.
     *
     * @return partijCode
     */
    public StringElement getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van tijdstipRegistratie.
     *
     * @return tijdstipRegistratie
     */
    public DatumTijdElement getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Geeft de lijst van bezien vanuit personen terug.
     *
     * @return de lijst van bezien vanuit personen
     */
    public List<PersoonGegevensElement> getBezienVanuitPersonen() {
        return bezienVanuitPersonen;
    }

    /**
     * Geef de waarde van gedeblokkeerdeMeldingen.
     *
     * @return gedeblokkeerdeMeldingen
     */
    public List<GedeblokkeerdeMeldingElement> getGedeblokkeerdeMeldingen() {
        return Collections.unmodifiableList(gedeblokkeerdeMeldingen);
    }

    /**
     * Geef de waarde van bijgehoudenPersonen.
     *
     * @return bijgehoudenPersonen
     */
    public List<PersoonGegevensElement> getBijgehoudenPersonen() {
        return Collections.unmodifiableList(bijgehoudenPersonen);
    }

    /**
     * Geef de waarde van het bijhoudingsplan terug.
     *
     * @return bijhoudingsplan
     */
    public BijhoudingsplanElement getBijhoudingsplan() {
        return bijhoudingsplan;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een nieuw {@link AdministratieveHandelingAntwoordElement}.
     *
     * @param administratieveHandelingElement het administratieve handeling element uit het {@link BijhoudingVerzoekBericht}
     * @param tijdstipRegistratie             het tijdstip registratie
     * @param bezienVanuitPersonen            de lijst met bezien vanuit personen
     * @param gedeblokkeerdeMeldingen         de lijst van gedeblokkeerde meldingen
     * @param bijgehoudenPersonen             de lijst van bijgehouden personen
     * @param bijhoudingsplan                 het bijhoudingsplan
     * @return een {@link AdministratieveHandelingAntwoordElement}
     */
    public static AdministratieveHandelingAntwoordElement getInstance(
            final AdministratieveHandelingElement administratieveHandelingElement,
            final ZonedDateTime tijdstipRegistratie,
            final List<PersoonGegevensElement> bezienVanuitPersonen,
            final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen,
            final List<PersoonGegevensElement> bijgehoudenPersonen,
            final BijhoudingsplanElement bijhoudingsplan) {

        return new AdministratieveHandelingAntwoordElement(
                new AttributenBuilder().objecttype("AdministratieveHandeling").build(),
                administratieveHandelingElement.getSoort(),
                administratieveHandelingElement.getPartijCode(),
                new DatumTijdElement(tijdstipRegistratie),
                bezienVanuitPersonen,
                gedeblokkeerdeMeldingen,
                bijgehoudenPersonen,
                bijhoudingsplan);
    }
}
