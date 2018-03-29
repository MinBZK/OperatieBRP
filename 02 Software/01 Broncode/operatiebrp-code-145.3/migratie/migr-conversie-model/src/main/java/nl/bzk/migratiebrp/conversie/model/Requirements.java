/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Enumeratie van alle te implementeren requirements.
 */
public enum Requirements {

    /**
     * Conversiepatroon gebeurtenisplaats.
     */
    CAP001("CAP001", "Conversiepatroon gebeurtenisplaats"),
    /**
     * Plaats.
     */
    CAP001_BL01("CAP001-BL01", "Plaats"),
    /**
     * Land.
     */
    CAP001_BL02("CAP001-BL02", "Land"),
    /**
     * Plaats.
     */
    CAP001_LB01("CAP001-LB01", "Plaats"),
    /**
     * Land.
     */
    CAP001_LB02("CAP001-LB02", "Land"),
    /**
     * Datumformaat.
     */
    CAP002("CAP002", "Datumformaat"),

    /**
     * ========================= == CBA == =========================
     */

    /**
     * Brondocumenten en akten.
     */
    CBA001_LB01("CBA001-LB01", "Brondocumenten en akten"),

    /**
     * Brondocumenten en akten.
     */
    CBA001_BL01("CBA001-BL01", "Brondocumenten en akten"),

    /**
     * ========================= == CCA == =========================
     */

    /**
     * Nationaliteit.
     */
    CCA04("CCA04", "Nationaliteit"),
    /**
     * Nationaliteit.
     */
    CCA04_BL01("CCA04-BL01", "Nationaliteit"),
    /**
     * Geprivilegieerde.
     */
    CCA04_BL02("CCA04-BL02", "Geprivilegieerde"),
    /**
     * Staatloos (was Statenloos).
     */
    CCA04_BL03("CCA04-BL03", "Staatloos"),
    /**
     * Nationaliteit.
     */
    CCA04_LB01("CCA04-LB01", "Nationaliteit"),
    /**
     * Geprivilegieerde.
     */
    CCA04_LB02("CCA04-LB02", "Geprivilegieerde"),
    /**
     * Inschrijving.
     */
    CCA07("CCA07", "Inschrijving"),
    /**
     * Inschrijving.
     */
    CCA07_BL01("CCA07-BL01", "Inschrijving"),
    /**
     * Persoonskaart.
     */
    CCA07_BL02("CCA07-BL02", "Persoonskaart"),
    /**
     * Laatste wijziging.
     */
    CCA07_BL03("CCA07-BL03", "Laatste wijziging"),
    /**
     * Verstrekkingsbeperking.
     */
    CCA07_BL04("CCA07-BL04", "Verstrekkingsbeperking"),
    /**
     * Opschorting.
     */
    CCA07_BL05("CCA07-BL05", "Opschorting"),
    /**
     * Verificatie.
     */
    CCA07_BL06("CCA07-BL06", "Verificatie"),
    /**
     * Inschrijving.
     */
    CCA07_LB01("CCA07-LB01", "Inschrijving"),
    /**
     * Persoonskaart.
     */
    CCA07_LB02("CCA07-LB02", "Persoonskaart"),
    /**
     * Laatste wijziging.
     */
    CCA07_LB03("CCA07-LB03", "Laatste wijziging"),
    /**
     * Verstrekkingsbeperking.
     */
    CCA07_LB04("CCA07-LB04", "Verstrekkingsbeperking"),
    /**
     * Opschorting.
     */
    CCA07_LB05("CCA07-LB05", "Opschorting / bijhouding"),

    /**
     * Verblijfplaats.
     */
    CCA08("CCA08", "Verblijfplaats"),
    /**
     * Adreshouding.
     */
    CCA08_BL01("CCA08-BL01", "Adreshouding"),
    /**
     * Nederlandse adres.
     */
    CCA08_BL02("CCA08-BL02", "Nederlandse adres"),
    /**
     * Buitenlands adres.
     */
    CCA08_BL03("CCA08-BL03", "Buitenlands adres"),
    /**
     * Immigratie.
     */
    CCA08_BL04("CCA08-BL04", "Immigratie"),
    /**
     * Bijhoudingsgemeente.
     */
    CCA08_BL05("CCA08-BL05", "Bijhoudingsgemeente"),
    /**
     * Adreshouding.
     */
    CCA08_LB01("CCA08-LB01", "Adreshouding"),
    /**
     * Nederlandse adres.
     */
    CCA08_LB02("CCA08-LB02", "Nederlandse adres"),
    /**
     * Buitenlands adres.
     */
    CCA08_LB03("CCA08-LB03", "Buitenlands adres"),
    /**
     * Immigratie.
     */
    CCA08_LB04("CCA08-LB04", "Immigratie"),
    /**
     * Bijhoudingsgemeente.
     */
    CCA08_LB05("CCA08-LB05", "Bijhoudingsgemeente"),
    /**
     * Conversie Verblijfsrecht.
     */
    CCA10("CCA10", "Conversie Verblijfsrecht"),
    /**
     * Conversie gezagsverhouding.
     */
    CCA11("CCA11", "Conversie gezagsverhouding"),
    /**
     * Conversie Reisdocumenten.
     */
    CCA12("CCA12", "Conversie Reisdocumenten"),
    /**
     * Kiesrecht.
     */
    CCA13("CCA13", "Kiesrecht"),
    /**
     * NL kiesrecht.
     */
    CCA13_LB01("CCA13-LB01", "NL kiesrecht"),
    /**
     * EU Verkiezingen.
     */
    CCA13_LB02("CCA13-LB02", "EU Verkiezingen"),
    /**
     * NL kiesrecht.
     */
    CCA13_BL01("CCA13-BL01", "NL kiesrecht"),
    /**
     * EU Verkiezingen.
     */
    CCA13_BL02("CCA13-BL02", "EU Verkiezingen"),

    /**
     * ========================= == CEL == =========================
     */

    /**
     * Conversie Voornamen.
     */
    CEL0210("CEL0210", "Conversie Voornamen"),
    /**
     * Voornamen.
     */
    CEL0210_BL01("CEL0210-BL01", "Voornamen"),
    /**
     * Voornamen.
     */
    CEL0210_LB01("CEL0210-LB01", "Voornamen"),

    /**
     * adellijke titel/predikaat.
     */
    CEL0220("CEL0220", "adellijke titel/predikaat"),
    /**
     * Adellijke titel/predikaat.
     */
    CEL0220_LB01("CEL0220-LB01", "Adellijke titel/predikaat"),
    /**
     * Adellijke titel/predikaat.
     */
    CEL0220_BL01("CEL0220-BL01", "Adellijke titel/predikaat"),
    /**
     * Conversie voorvoegsel.
     */
    CEL0230("CEL0230", "Conversie voorvoegsel"),
    /**
     * Voorvoegsel en scheidingsteken.
     */
    CEL0230_BL02("CEL0230-BL02", "Voorvoegsel en scheidingsteken"),
    /**
     * Voorvoegsel geslachtsnaam en scheidingsteken.
     */
    CEL0230_LB01("CEL0230-LB01", "Voorvoegsel geslachtsnaam en scheidingsteken"),
    /**
     * Conversie geslachtsnaam.
     */
    CEL0240("CEL0240", "Conversie geslachtsnaam"),
    /**
     * Geslachtsnaam.
     */
    CEL0240_BL01("CEL0240-BL01", "Geslachtsnaam"),
    /**
     * Geslachtsnaam.
     */
    CEL0240_LB01("CEL0240-LB01", "Geslachtsnaam"),

    /**
     * Conversie Geslachtsaanduiding.
     */
    CEL0410("CEL0410", "Conversie Geslachtsaanduiding"),
    /**
     * Conversie Naamgebruik.
     */
    CEL6110("CEL6110", "Conversie Naamgebruik"),

    /**
     * ========================= == CGR == =========================
     */

    /**
     * Identificatienummers.
     */
    CGR01("CGR01", "Identificatienummers"),
    /**
     * identificatienummers.
     */
    CGR01_LB01("CGR01-LB01", "identificatienummers"),
    /**
     * identificatienummers.
     */
    CGR01_BL01("CGR01-BL01", "identificatienummers"),

    /**
     * Conversie Geboorte.
     */
    CGR03("CGR03", "Conversie Geboorte"),
    /**
     * Geboortedatum.
     */
    CGR03_BL01("CGR03-BL01", "Geboortedatum"),
    /**
     * Geboorteland.
     */
    CGR03_BL03("CGR03-BL03", "Geboorteland"),
    /**
     * Geboorteplaats.
     */
    CGR03_BL02("CGR03-BL02", "Geboorteplaats"),
    /**
     * Geboortedatum.
     */
    CGR03_LB01("CGR03-LB01", "Geboortedatum"),
    /**
     * Geboorteplaats.
     */
    CGR03_LB02("CGR03-LB02", "Geboorteplaats"),
    /**
     * Geboorteland.
     */
    CGR03_LB03("CGR03-LB03", "Geboorteland"),

    /**
     * Conversie Overlijden.
     */
    CGR08("CGR08", "Conversie Overlijden"),
    /**
     * Datum overlijden.
     */
    CGR08_BL01("CGR08-BL01", "Datum overlijden"),
    /**
     * Plaats overlijden.
     */
    CGR08_BL02("CGR08-BL02", "Plaats overlijden"),
    /**
     * Land overlijden.
     */
    CGR08_BL03("CGR08-BL03", "Land overlijden"),
    /**
     * Datum overlijden.
     */
    CGR08_LB01("CGR08-LB01", "Datum overlijden"),
    /**
     * Plaats overlijden.
     */
    CGR08_LB02("CGR08-LB02", "Plaats overlijden"),
    /**
     * Land overlijden.
     */
    CGR08_LB03("CGR08-LB03", "Land overlijden"),
    /**
     * Buitenlands persoonsnummer.
     */
    CGR73_LB01("CGR73-LB01", "Buitenlands persoonsnummer"),
    /**
     * Buitenlands persoonsnummer.
     */
    CGR73_BL01("CGR73-BL01", "Buitenlands persoonsnummer"),

    /*
     * ========================= == CHP == =========================
     */

    /**
     * Hoofdlijnen Conversie van Historie.
     */
    CHP001("CHP001", "Hoofdlijnen Conversie van Historie"),
    /**
     * Stap 1: Converteren inhoud.
     */
    CHP001_LB10("CHP001-LB10", "Stap 1: Converteren inhoud"),

    /**
     * Stap 2: Converteren historiestructuur.
     */
    CHP001_LB2X("CHP001-LB2x", "Stap 2: Converteren historiestructuur"),
    /**
     * Converteren historiestructuur. Bron is een LO3-categorie waar geen lege rijen kunnen voorkomen. Doel is een
     * BRP-groep die zowel formele als materiële historie heeft.
     */
    CHP001_LB21("CHP001-LB21", "Converteren historiestructuur. Geen lege LO3 rijen, BRP groep met zowel formele als materiële historie"),
    /**
     * Converteren historiestructuur. Bron is een LO3 categorie waar geen lege rijen kunnen voorkomen. Doel is een BRP
     * groep die uitsluitend formele historie heeft.
     */
    CHP001_LB22("CHP001-LB22", "Converteren historiestructuur. Geen lege LO3 rijen, BRP groep met uitsluitend formele historie"),
    /**
     * Converteren historiestructuur. Bron is een LO3 categorie waar lege rijen kunnen voorkomen. Doel is een BRP groep
     * die zowel formele als materiële historie heeft
     */
    CHP001_LB23("CHP001-LB23", "Converteren historiestructuur. Mogelijk lege LO3 rijen, " + "BRP groep met zowel formele als materiële historie"),
    /**
     * Converteren historiestructuur. Bron is een LO3 categorie waar lege rijen kunnen voorkomen. Doel is een BRP groep
     * die uitsluitend formele historie heeft
     */
    CHP001_LB24("CHP001-LB24", "Converteren historiestructuur. Mogelijk lege LO3 rijen, BRP groep met uitsluitend formele historie"),

    /**
     * ========================= == CR == =========================
     */
    /**
     * Familierechtelijke betrekking.
     */
    CR001("CR001", "Familierechtelijke betrekking"),
    /**
     * Huwelijk / geregistreerd partnerschap.
     */
    CR002("CR002", "Huwelijk / geregistreerd partnerschap"),

    /**
     * ========================= == CRP == =========================
     */
    /**
     * Conversiepatroon voor het leggen van relaties.
     */
    CRP001("CRP001", "Conversiepatroon voor het leggen van relaties"),
    /**
     * Stap 1, Converteer de relatie naar BRP structuur.
     */
    CRP001_LB01("CRP001-LB01", "Converteer de relatie naar BRP structuur"),
    /**
     * Stap 2, Bepaal of er afwijkende persoonsgegevens zijn, bezien vanuit de gerelateerde.
     */
    CRP001_LB02("CRP001-LB02", "Bepaal of er afwijkende persoonsgegevens zijn, bezien vanuit de gerelateerde"),
    /**
     * Stap 3, Bepaling of voor de relatie Mono- of Multi-realiteit geldt.
     */
    CRP001_LB03("CRP001-LB03", "Bepaling of voor de relatie Mono- of Multi-realiteit geldt"),
    /**
     * Stap 1, opvragen van relatiegegevens die vanuit persoon onder conversie geldig zijn.
     */
    CRP001_BL01("CRP001-BL01", "Stap 1, opvragen van relatiegegevens die vanuit persoon onder conversie geldig zijn"),
    /**
     * Stap 2, opvragen persoonsgegevens gerelateerde perso(o)n(en).
     */
    CRP001_BL02("CRP001-BL02", "Stap 2, opvragen persoonsgegevens gerelateerde perso(o)n(en)");

    private final String code;
    private final String omschrijving;

    /**
     * Constructor.
     * @param code Code
     * @param omschrijving Omschrijving
     */
    Requirements(final String code, final String omschrijving) {
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("code", code).append("omschrijving", omschrijving).toString();
    }

}
