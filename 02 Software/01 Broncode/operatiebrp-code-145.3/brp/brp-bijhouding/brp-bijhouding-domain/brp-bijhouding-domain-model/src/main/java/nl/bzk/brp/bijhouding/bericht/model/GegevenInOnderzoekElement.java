/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;
import nl.bzk.brp.bijhouding.bericht.model.PersoonObjectLocator;
import nl.bzk.brp.bijhouding.bericht.model.PersoonVoorkomenLocator;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;

/**
 * Het gegeven in onderzoek element uit het bijhoudingsbericht.
 */
@XmlElement("gegevenInOnderzoek")
public final class GegevenInOnderzoekElement extends AbstractBmrObjecttype {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String OBJECTTYPE = "GegevenInOnderzoek";
    private static final Map<String, Element> ELEMENT_MAP = new HashMap<>();

    static {
        initializeElementMap(ELEMENT_MAP);
    }

    private StringElement elementNaam;
    private StringElement objectSleutelGegeven;
    private StringElement voorkomenSleutelGegeven;

    /**
     * Maak een GegevenInOnderzoekElement object.
     * @param attributen de attributen
     * @param elementNaam de element naam
     * @param objectSleutelGegeven de objectsleutel van het gegeven in onderzoek
     * @param voorkomenSleutelGegeven de voorkomensleutel van het gegeven in onderzoek
     */
    public GegevenInOnderzoekElement(final Map<String, String> attributen, final StringElement elementNaam,
                                     final StringElement objectSleutelGegeven, final StringElement voorkomenSleutelGegeven) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(elementNaam, "elementNaam");
        this.elementNaam = elementNaam;
        this.objectSleutelGegeven = objectSleutelGegeven;
        this.voorkomenSleutelGegeven = voorkomenSleutelGegeven;
    }

    /**
     * Geef de waarde van elementNaam.
     * @return elementNaam
     */
    public StringElement getElementNaam() {
        return elementNaam;
    }

    /**
     * Geef de waarde van objectSleutelGegeven.
     * @return objectSleutelGegeven
     */
    public StringElement getObjectSleutelGegeven() {
        return objectSleutelGegeven;
    }

    /**
     * Geef de waarde van voorkomenSleutelGegeven.
     * @return voorkomenSleutelGegeven
     */
    public StringElement getVoorkomenSleutelGegeven() {
        return voorkomenSleutelGegeven;
    }

    @Bedrijfsregel(Regel.R2596)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerCombinatieSleutels(meldingen);
        controleerBestaanElement(meldingen);
        return meldingen;
    }

    /**
     * Bepaalt de Element enumeratie die hoort bij dit gegeven in onderzoek element.
     * @return de Element enumeratie
     */
    Element bepaalElement() {
        return ELEMENT_MAP.get(elementNaam.getWaarde());
    }

    /**
     * Bepaalt het aangwezen Object of Voorkomen binnen de gegeven persoon.
     * @param persoon de persoon
     * @return het object of voorkomen
     */
    Entiteit bepaalObjectOfVoorkomen(final Persoon persoon) {
        Entiteit result = bepaalObject(persoon);
        if (result == null) {
            result = bepaalVoorkomen(persoon);
        }
        return result;
    }

    private void controleerCombinatieSleutels(final List<MeldingElement> meldingen) {
        if (objectSleutelGegeven != null && voorkomenSleutelGegeven != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2596, this));
        }
    }

    @Bedrijfsregel(Regel.R2612)
    private void controleerBestaanElement(final List<MeldingElement> meldingen) {
        if (bepaalElement() == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2612, this));
        }
    }

    @Bedrijfsregel(Regel.R2602)
    void controleerGeldigheidElement(final List<MeldingElement> meldingen, final int peildatum) {
        final Element element = bepaalElement();
        if (element != null && !DatumUtil.valtDatumBinnenPeriode(peildatum, element.getElementWaarde().getDatumAanvangGeldigheid(),
                element.getElementWaarde().getDatumEindeGeldigheid())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2602, this));
        }
    }

    @Bedrijfsregel(Regel.R2597)
    void controleerAangewezenObject(final List<MeldingElement> meldingen, final Persoon persoon) {
        final Element element = bepaalElement();
        if (objectSleutelGegeven != null && element != null && bepaalObject(persoon) == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2597, this));
        }
    }

    @Bedrijfsregel(Regel.R2599)
    void controleerAangewezenVoorkomen(final List<MeldingElement> meldingen, final Persoon persoon) {
        final Element element = bepaalElement();
        final FormeleHistorie voorkomen = bepaalVoorkomen(persoon);
        if (voorkomenSleutelGegeven != null && element != null && (voorkomen == null || voorkomen.isVervallen())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2599, this));
        }
    }

    private RootEntiteit bepaalObject(final Persoon persoon) {
        final Element element = bepaalElement();
        final Long objectSleutel = bepaalObjectSleutel();
        if (element == null || objectSleutel == null) {
            return null;
        } else {
            return PersoonObjectLocator.zoek(element, objectSleutel, persoon);
        }
    }

    private FormeleHistorie bepaalVoorkomen(final Persoon persoon) {
        final Element element = bepaalElement();
        final Long voorkomenSleutel = bepaalVoorkomenSleutel();
        if (element == null || voorkomenSleutel == null) {
            return null;
        } else {
            return PersoonVoorkomenLocator.zoek(element, voorkomenSleutel, persoon);
        }
    }

    private Long bepaalObjectSleutel() {
        final Element element = bepaalElement();
        if (element == null || objectSleutelGegeven == null) {
            return null;
        }
        return bepaalObjectSleutel(element, objectSleutelGegeven.getWaarde());
    }

    private Long bepaalVoorkomenSleutel() {
        Long result = null;
        if (voorkomenSleutelGegeven != null) {
            try {
                result = Long.parseLong(voorkomenSleutelGegeven.getWaarde());
            } catch (NumberFormatException e) {
                LOGGER.info("gegeven in onderzoek verwijst naar een voorkomen objectsleutel", e);
            }
        }
        return result;
    }

    /**
     * Helper methode voor het maken van een GegevenInOnderzoekElement object.
     * @param communicatieId de communicatie id
     * @param elementNaam de element naam
     * @param objectSleutelGegeven de objectsleutel
     * @param voorkomenSleutelGegeven de voorkomensleutel
     * @return een nieuw GegevenInOnderzoekElement
     */
    public static GegevenInOnderzoekElement getInstance(final String communicatieId, final String elementNaam, final String objectSleutelGegeven,
                                                        final String voorkomenSleutelGegeven) {
        return new GegevenInOnderzoekElement(new AttributenBuilder().communicatieId(communicatieId).objecttype(OBJECTTYPE).build(),
                StringElement.getInstance(elementNaam), StringElement.getInstance(objectSleutelGegeven), StringElement.getInstance(voorkomenSleutelGegeven));
    }

    private static Long bepaalObjectSleutel(final Element element, final String sleutel) {
        if (element.getAliasVan() != null) {
            return bepaalObjectSleutel(element.getAliasVan(), sleutel);
        }
        Long result = null;
        try {
            if (Element.PERSOON.equals(element)) {
                result = ApplicationContextProvider.getObjectSleutelService().maakPersoonObjectSleutel(sleutel).getDatabaseId();
            } else if (Element.RELATIE.equals(element)) {
                result = ApplicationContextProvider.getObjectSleutelService().maakRelatieObjectSleutel(sleutel).getDatabaseId();
            } else if (Element.BETROKKENHEID.equals(element)) {
                result = ApplicationContextProvider.getObjectSleutelService().maakBetrokkenheidObjectSleutel(sleutel).getDatabaseId();
            } else {
                result = Long.parseLong(sleutel);
            }
        } catch (OngeldigeObjectSleutelException | NumberFormatException e) {
            LOGGER.info("gegeven in onderzoek verwijst naar een ongeldige objectsleutel", e);
        }
        return result;
    }

    private static void initializeElementMap(final Map<String, Element> elementMapParam) {
        for (final Element element : Element.values()) {
            elementMapParam.put(element.getNaam(), element);
        }
    }
}
