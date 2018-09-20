/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;

/**
 * Enumeratie van alle BRP groepen. Gebruikt om labels van de BRP groepen vast te leggen welke in de viewer getoond
 * kunnen worden.
 */
public enum GgoBrpGroepEnum {
    /**
     * Actie inhoud.
     */
    ACTIE_INHOUD("Actie inhoud", Element.DOCUMENT_ACTIEINHOUD),
    /**
     * ActieBron.
     */
    ACTIE_BRON("Actie / Bron", Element.ACTIEBRON),
    /**
     * AdministratieveHandeling.
     */
    ADMINISTRATIEVE_HANDELING("Administratieve handeling", Element.ADMINISTRATIEVEHANDELING),
    /**
     * Naamgebruik.
     */
    NAAMGEBRUIK("Naamgebruik", Element.PERSOON_NAAMGEBRUIK),
    /**
     * Adres.
     */
    ADRES("Adres", Element.PERSOON_ADRES, Element.PERSOON_ADRES_STANDAARD),
    /**
     * Persoon Afgeleid Administratief.
     */
    PERSOON_AFGELEID_ADMINISTRATIEF("Persoon Afgeleid administratief", Element.PERSOON_AFGELEIDADMINISTRATIEF),
    /**
     * Behandeld Als Nederlander Indicatie.
     */
    BEHANDELD_ALS_NEDERLANDER_IND("Behandeld als Nederlander", (Element) null),
    /**
     * Signalering met betrekking tot verstrekken reisdocument.
     */
    SIGNALERING_MBT_VERSTREKKEN_REISDOCUMENT("Signalering met betrekking tot verstrekken reisdocument", (Element) null),
    /**
     * Bijhouding.
     */
    BIJHOUDING("Bijhouding", Element.PERSOON_BIJHOUDING),
    /**
     * Bijzondere Verblijfsrechtelijke Positie.
     */
    BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("Bijzondere verblijfsrechtelijke positie", (Element) null),
    /**
     * Derde Heeft Gezag Indicatie.
     */
    DERDE_HEEFT_GEZAG_IND("Gezag derde", (Element) null),
    /**
     * Document.
     */
    DOCUMENT("Document", Element.DOCUMENT, Element.DOCUMENT_STANDAARD),
    /**
     * Europese Verkiezingen.
     */
    DEELNAME_EU_VERKIEZINGEN("Deelname EU verkiezingen", Element.PERSOON_DEELNAMEEUVERKIEZINGEN),
    /**
     * Geboorte.
     */
    GEBOORTE("Geboorte", Element.PERSOON_GEBOORTE, Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE, Element.GERELATEERDEOUDER_PERSOON_GEBOORTE, Element.GERELATEERDEKIND_PERSOON_GEBOORTE),
    /**
     * Geslachtsaanduiding.
     */
    GESLACHTSAANDUIDING("Geslachtsaanduiding", Element.PERSOON_GESLACHTSAANDUIDING, Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING, Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING),
    /**
     * Geslachtsnaamcomponent.
     */
    GESLACHTSNAAMCOMPONENT("Geslachtsnaamcomponent", Element.PERSOON_GESLACHTSNAAMCOMPONENT, Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD),
    /**
     * Identificatienummers.
     */
    IDENTIFICATIENUMMERS("Identificatienummers", Element.PERSOON_IDENTIFICATIENUMMERS,
            Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS, Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS,
            Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS, Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS),
    /**
     * Migratie.
     */
    MIGRATIE("Migratie", Element.PERSOON_MIGRATIE),
    /**
     * Inschrijving.
     */
    INSCHRIJVING("Inschrijving", Element.PERSOON_INSCHRIJVING),
    /**
     * Nationaliteit.
     */
    NATIONALITEIT("Nationaliteit", Element.PERSOON_NATIONALITEIT, Element.PERSOON_NATIONALITEIT_STANDAARD),
    /**
     * Nummerverwijzing.
     */
    NUMMERVERWIJZING("Nummerverwijzing", Element.PERSOON_NUMMERVERWIJZING),
    /**
     * Onder Curatele Indicatie.
     */
    ONDER_CURATELE_IND("Onder curatele", (Element) null),
    /**
     * Ouder.
     */
    OUDER("Ouderschap", Element.OUDER_OUDERSCHAP),
    /**
     * Ouderlijk Gezag.
     */
    OUDERLIJK_GEZAG("Ouderlijk gezag", Element.OUDER_OUDERLIJKGEZAG),
    /**
     * Overlijden.
     */
    OVERLIJDEN("Overlijden", Element.PERSOON_OVERLIJDEN),
    /**
     * Persoonskaart.
     */
    PERSOONSKAART("Persoonskaart", Element.PERSOON_PERSOONSKAART),
    /**
     * Reisdocument.
     */
    REISDOCUMENT("Reisdocument", Element.PERSOON_REISDOCUMENT, Element.PERSOON_REISDOCUMENT_STANDAARD),
    /**
     * Samengestelde Naam.
     */
    SAMENGESTELDE_NAAM("Samengestelde naam", Element.PERSOON_SAMENGESTELDENAAM, Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM,
            Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM, Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM,
            Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM),
    /**
     * Staatloos Indicatie (was Statenloos Indicatie).
     */
    STAATLOOS_IND("Staatloos", (Element) null),
    /**
     * Uitsluiting Kiesrecht.
     */
    UITSLUITING_KIESRECHT("Uitsluiting kiesrecht", Element.PERSOON_UITSLUITINGKIESRECHT),
    /**
     * Vastgesteld Niet Nederlander Indicatie.
     */
    VASTGESTELD_NIET_NEDERLANDER_IND("Vastgesteld niet Nederlander", (Element) null),
    /**
     * Verblijfsrecht.
     */
    VERBLIJFSRECHT("Verblijfsrecht", Element.PERSOON_VERBLIJFSRECHT),
    /**
     * Verificatie.
     */
    VERIFICATIE("Verificatie", Element.PERSOON_VERIFICATIE, Element.PERSOON_VERIFICATIE_STANDAARD),
    /**
     * Verstrekkingsbeperking.
     */
    VERSTREKKINGSBEPERKING("Verstrekkingsbeperking", Element.PERSOON_VERSTREKKINGSBEPERKING),
    /**
     * Voornaam.
     */
    VOORNAAM("Voornaam", Element.PERSOON_VOORNAAM, Element.PERSOON_VOORNAAM_STANDAARD),
    /**
     * Relatie Inhoud.
     */
    RELATIE_INHOUD("Relatie inhoud", Element.RELATIE, Element.GEREGISTREERDPARTNERSCHAP_STANDAARD, Element.HUWELIJK_STANDAARD),

    /**
     * Gerelateerde.
     */
    GERELATEERDE("Gerelateerde", (Element) null);

    private final String label;
    private final Element[] elementen;

    private GgoBrpGroepEnum(final String label, final Element... elementen) {
        this.label = label;
        this.elementen = elementen;
    }

    /**
     * Vind het juiste GgoBrpGroepEnum voor het gegeven Element, zodat het label ervan kan worden getoond.
     *
     * @param element
     *            Het Element
     * @return Het GgoBrpGroepEnum
     */
    public static GgoBrpGroepEnum findByElement(final Element element) {
        for (final GgoBrpGroepEnum en : GgoBrpGroepEnum.values()) {
            for (final Element obj : en.elementen) {
                if (element.equals(obj)) {
                    return en;
                }
            }
        }

        return null;
    }

    /**
     * Geef de waarde van label.
     *
     * @return de label
     */
    public String getLabel() {
        return label;
    }
}
