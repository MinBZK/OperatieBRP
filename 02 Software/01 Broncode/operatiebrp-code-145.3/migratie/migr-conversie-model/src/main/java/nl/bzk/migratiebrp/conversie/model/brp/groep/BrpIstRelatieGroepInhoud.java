/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de gerelateerden inhoud voor de IST-tabellen.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpIstRelatieGroepInhoud extends AbstractBrpIstGroepInhoud {

    @Element(name = "standaardGegevens", required = true)
    private final BrpIstStandaardGroepInhoud standaardGegevens;
    /* Gerelateerde */
    @Element(name = "rubriek6210DatumIngangFamilierechtelijkeBetrekking", required = false)
    private final BrpInteger rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    @Element(name = "anummer", required = false)
    private final BrpString anummer;
    @Element(name = "bsn", required = false)
    private final BrpString bsn;
    @Element(name = "voornamen", required = false)
    private final BrpString voornamen;
    @Element(name = "predicaatCode", required = false)
    private final BrpPredicaatCode predicaatCode;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "voorvoegsel", required = false)
    private final BrpString voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final BrpCharacter scheidingsteken;
    @Element(name = "geslachtsnaamstam", required = false)
    private final BrpString geslachtsnaamstam;
    @Element(name = "datumGeboorte", required = false)
    private final BrpInteger datumGeboorte;
    @Element(name = "gemeenteCodeGeboorte", required = false)
    private final BrpGemeenteCode gemeenteCodeGeboorte;
    @Element(name = "buitenlandsePlaatsGeboorte", required = false)
    private final BrpString buitenlandsePlaatsGeboorte;
    @Element(name = "omschrijvingLocatieGeboorte", required = false)
    private final BrpString omschrijvingLocatieGeboorte;
    @Element(name = "landOfGebiedCodeGeboorte", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCodeGeboorte;
    @Element(name = "geslachtsaanduidingCode", required = false)
    private final BrpGeslachtsaanduidingCode geslachtsaanduidingCode;

    /**
     * Constructor op basis van Builder-pattern. Gebruik {@link BrpIstRelatieGroepInhoud.Builder}.
     * @param builder builder met daar in de gegevens om een BrpIstRelatieGroepInhoud te construeren
     * @see BrpIstRelatieGroepInhoud.Builder
     */
    private BrpIstRelatieGroepInhoud(final Builder builder) {
        standaardGegevens = builder.standaardGegevens;
        rubriek6210DatumIngangFamilierechtelijkeBetrekking = builder.rubriek6210DatumIngangFamilierechtelijkeBetrekking;
        anummer = builder.anummer;
        bsn = builder.bsn;
        voornamen = builder.voornamen;
        predicaatCode = builder.predicaatCode;
        adellijkeTitelCode = builder.adellijkeTitel;
        voorvoegsel = builder.voorvoegsel;
        scheidingsteken = builder.scheidingsteken;
        geslachtsnaamstam = builder.geslachtsnaamstam;
        datumGeboorte = builder.datumGeboorte;
        gemeenteCodeGeboorte = builder.gemeenteCodeGeboorte;
        buitenlandsePlaatsGeboorte = builder.buitenlandsePlaatsGeboorte;
        omschrijvingLocatieGeboorte = builder.omschrijvingLocatieGeboorte;
        landOfGebiedCodeGeboorte = builder.landOfGebiedCodeGeboorte;
        geslachtsaanduidingCode = builder.geslachtsaanduidingCode;
    }

    /**
     * Maakt een BrpIstRelatieStapelInhoud object.<BR>
     * LET OP: Niet gebruiken voor constructie van BrpIstRelatieGroepInhoud. Dit is alleen nodig voor SimpleXML
     * @param standaardGroepInhoud standaard IST gegevens
     * @param datumIngangFamilierechtelijkeBetrekking datum ingang familie rechtelijke betrekking
     * @param administratienummer anummer
     * @param burgerservicenummer bsn
     * @param voornamenPersoon voornamen
     * @param predicaat predicaatCode
     * @param adellijkeTitel adelijke titel
     * @param voorvoegselNaam voorvoegsel
     * @param scheidingstekenNaam scheidingsteken
     * @param stamGeslachtsnaam geslachtsnaam
     * @param geboorteDatum geboortdatum
     * @param geboorteGemeenteCode gemeente van geboorte
     * @param geboorteBuitenlandsePlaats buitelandse plaats van geboorte
     * @param geboorteOmschrijvingLocatie omschrijving van locatie van geboorte
     * @param geboorteLandOfGebiedCode land van geboorte
     * @param geslachtsaanduiding geslachtsaanduiding
     */
    public BrpIstRelatieGroepInhoud(
            @Element(name = "standaardGegevens", required = true) final BrpIstStandaardGroepInhoud standaardGroepInhoud,
            @Element(name = "rubriek6210DatumIngangFamilierechtelijkeBetrekking", required = false) final BrpInteger datumIngangFamilierechtelijkeBetrekking,
            @Element(name = "anummer", required = false) final BrpString administratienummer,
            @Element(name = "bsn", required = false) final BrpString burgerservicenummer,
            @Element(name = "voornamen", required = false) final BrpString voornamenPersoon,
            @Element(name = "predicaatCode", required = false) final BrpPredicaatCode predicaat,
            @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitel,
            @Element(name = "voorvoegsel", required = false) final BrpString voorvoegselNaam,
            @Element(name = "scheidingsteken", required = false) final BrpCharacter scheidingstekenNaam,
            @Element(name = "geslachtsnaamstam", required = false) final BrpString stamGeslachtsnaam,
            @Element(name = "datumGeboorte", required = false) final BrpInteger geboorteDatum,
            @Element(name = "gemeenteCodeGeboorte", required = false) final BrpGemeenteCode geboorteGemeenteCode,
            @Element(name = "buitenlandsePlaatsGeboorte", required = false) final BrpString geboorteBuitenlandsePlaats,
            @Element(name = "omschrijvingLocatieGeboorte", required = false) final BrpString geboorteOmschrijvingLocatie,
            @Element(name = "landOfGebiedCodeGeboorte", required = false) final BrpLandOfGebiedCode geboorteLandOfGebiedCode,
            @Element(name = "geslachtsaanduidingCode", required = false) final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        standaardGegevens = standaardGroepInhoud;
        anummer = administratienummer;
        bsn = burgerservicenummer;
        voornamen = voornamenPersoon;
        predicaatCode = predicaat;
        adellijkeTitelCode = adellijkeTitel;
        voorvoegsel = voorvoegselNaam;
        scheidingsteken = scheidingstekenNaam;
        geslachtsnaamstam = stamGeslachtsnaam;
        datumGeboorte = geboorteDatum;
        gemeenteCodeGeboorte = geboorteGemeenteCode;
        buitenlandsePlaatsGeboorte = geboorteBuitenlandsePlaats;
        omschrijvingLocatieGeboorte = geboorteOmschrijvingLocatie;
        landOfGebiedCodeGeboorte = geboorteLandOfGebiedCode;
        geslachtsaanduidingCode = geslachtsaanduiding;
        rubriek6210DatumIngangFamilierechtelijkeBetrekking = datumIngangFamilierechtelijkeBetrekking;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof BrpIstRelatieGroepInhoud)) {
            return false;
        }
        final BrpIstRelatieGroepInhoud castOther = (BrpIstRelatieGroepInhoud) other;
        return new EqualsBuilder().append(standaardGegevens, castOther.standaardGegevens)
                .append(rubriek6210DatumIngangFamilierechtelijkeBetrekking, castOther.rubriek6210DatumIngangFamilierechtelijkeBetrekking)
                .append(anummer, castOther.anummer)
                .append(bsn, castOther.bsn)
                .append(voornamen, castOther.voornamen)
                .append(predicaatCode, castOther.predicaatCode)
                .append(adellijkeTitelCode, castOther.adellijkeTitelCode)
                .append(voorvoegsel, castOther.voorvoegsel)
                .append(scheidingsteken, castOther.scheidingsteken)
                .append(geslachtsnaamstam, castOther.geslachtsnaamstam)
                .append(datumGeboorte, castOther.datumGeboorte)
                .append(gemeenteCodeGeboorte, castOther.gemeenteCodeGeboorte)
                .append(buitenlandsePlaatsGeboorte, castOther.buitenlandsePlaatsGeboorte)
                .append(omschrijvingLocatieGeboorte, castOther.omschrijvingLocatieGeboorte)
                .append(landOfGebiedCodeGeboorte, castOther.landOfGebiedCodeGeboorte)
                .append(geslachtsaanduidingCode, castOther.geslachtsaanduidingCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(standaardGegevens)
                .append(rubriek6210DatumIngangFamilierechtelijkeBetrekking)
                .append(anummer)
                .append(bsn)
                .append(voornamen)
                .append(predicaatCode)
                .append(adellijkeTitelCode)
                .append(voorvoegsel)
                .append(scheidingsteken)
                .append(geslachtsnaamstam)
                .append(datumGeboorte)
                .append(gemeenteCodeGeboorte)
                .append(buitenlandsePlaatsGeboorte)
                .append(omschrijvingLocatieGeboorte)
                .append(landOfGebiedCodeGeboorte)
                .append(geslachtsaanduidingCode)
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getCategorie()
     */
    @Override
    public Lo3CategorieEnum getCategorie() {
        return standaardGegevens.getCategorie();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getStapel()
     */
    @Override
    public int getStapel() {
        return standaardGegevens.getStapel();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getVoorkomen()
     */
    @Override
    public int getVoorkomen() {
        return standaardGegevens.getVoorkomen();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getStandaardGegevens()
     */
    @Override
    public BrpIstStandaardGroepInhoud getStandaardGegevens() {
        return standaardGegevens;
    }

    /**
     * Geef de waarde van rubriek6210 datum ingang familierechtelijke betrekking van BrpIstRelatieGroepInhoud.
     * @return de waarde van rubriek6210 datum ingang familierechtelijke betrekking van BrpIstRelatieGroepInhoud
     */
    public BrpInteger getRubriek6210DatumIngangFamilierechtelijkeBetrekking() {
        return rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    }

    /**
     * Geef de waarde van anummer van BrpIstRelatieGroepInhoud.
     * @return de waarde van anummer van BrpIstRelatieGroepInhoud
     */
    public BrpString getAnummer() {
        return anummer;
    }

    /**
     * Geef de waarde van bsn van BrpIstRelatieGroepInhoud.
     * @return de waarde van bsn van BrpIstRelatieGroepInhoud
     */
    public BrpString getBsn() {
        return bsn;
    }

    /**
     * Geef de waarde van voornamen van BrpIstRelatieGroepInhoud.
     * @return de waarde van voornamen van BrpIstRelatieGroepInhoud
     */
    public BrpString getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van predicaat code van BrpIstRelatieGroepInhoud.
     * @return de waarde van predicaat code van BrpIstRelatieGroepInhoud
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van adellijke titel code van BrpIstRelatieGroepInhoud.
     * @return de waarde van adellijke titel code van BrpIstRelatieGroepInhoud
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van voorvoegsel van BrpIstRelatieGroepInhoud.
     * @return de waarde van voorvoegsel van BrpIstRelatieGroepInhoud
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van BrpIstRelatieGroepInhoud.
     * @return de waarde van scheidingsteken van BrpIstRelatieGroepInhoud
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van BrpIstRelatieGroepInhoud.
     * @return de waarde van geslachtsnaamstam van BrpIstRelatieGroepInhoud
     */
    public BrpString getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geef de waarde van datum geboorte van BrpIstRelatieGroepInhoud.
     * @return de waarde van datum geboorte van BrpIstRelatieGroepInhoud
     */
    public BrpInteger getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Geef de waarde van gemeente code geboorte van BrpIstRelatieGroepInhoud.
     * @return de waarde van gemeente code geboorte van BrpIstRelatieGroepInhoud
     */
    public BrpGemeenteCode getGemeenteCodeGeboorte() {
        return gemeenteCodeGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte van BrpIstRelatieGroepInhoud.
     * @return de waarde van buitenlandse plaats geboorte van BrpIstRelatieGroepInhoud
     */
    public BrpString getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van omschrijving locatie geboorte van BrpIstRelatieGroepInhoud.
     * @return de waarde van omschrijving locatie geboorte van BrpIstRelatieGroepInhoud
     */
    public BrpString getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * Geef de waarde van land of gebied code geboorte van BrpIstRelatieGroepInhoud.
     * @return de waarde van land of gebied code geboorte van BrpIstRelatieGroepInhoud
     */
    public BrpLandOfGebiedCode getLandOfGebiedCodeGeboorte() {
        return landOfGebiedCodeGeboorte;
    }

    /**
     * Geef de waarde van geslachtsaanduiding code van BrpIstRelatieGroepInhoud.
     * @return de waarde van geslachtsaanduiding code van BrpIstRelatieGroepInhoud
     */
    public BrpGeslachtsaanduidingCode getGeslachtsaanduidingCode() {
        return geslachtsaanduidingCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("standaardGegevens", standaardGegevens)
                .append(
                        "rubriek6210DatumIngangFamilierechtelijkeBetrekking",
                        rubriek6210DatumIngangFamilierechtelijkeBetrekking)
                .append("anummer", anummer)
                .append("bsn", bsn)
                .append("voornamen", voornamen)
                .append("predicaatCode", predicaatCode)
                .append("adellijkeTitelCode", adellijkeTitelCode)
                .append("voorvoegsel", voorvoegsel)
                .append("scheidingsteken", scheidingsteken)
                .append("geslachtsnaamstam", geslachtsnaamstam)
                .append("datumGeboorte", datumGeboorte)
                .append("gemeenteCodeGeboorte", gemeenteCodeGeboorte)
                .append("buitenlandsePlaatsGeboorte", buitenlandsePlaatsGeboorte)
                .append("omschrijvingLocatieGeboorte", omschrijvingLocatieGeboorte)
                .append("landOfGebiedCodeGeboorte", landOfGebiedCodeGeboorte)
                .append("geslachtsaanduidingCode", geslachtsaanduidingCode)
                .toString();
    }

    /**
     * Builder object voor BrpIstRelatieGroepInhoud.
     */
    public static class Builder {
        private final BrpIstStandaardGroepInhoud standaardGegevens;
        private BrpInteger rubriek6210DatumIngangFamilierechtelijkeBetrekking;
        private BrpString anummer;
        private BrpString bsn;
        private BrpString voornamen;
        private BrpPredicaatCode predicaatCode;
        private BrpAdellijkeTitelCode adellijkeTitel;
        private BrpString voorvoegsel;
        private BrpCharacter scheidingsteken;
        private BrpString geslachtsnaamstam;
        private BrpInteger datumGeboorte;
        private BrpGemeenteCode gemeenteCodeGeboorte;
        private BrpString buitenlandsePlaatsGeboorte;
        private BrpString omschrijvingLocatieGeboorte;
        private BrpLandOfGebiedCode landOfGebiedCodeGeboorte;
        private BrpGeslachtsaanduidingCode geslachtsaanduidingCode;

        /**
         * Constructor met verplichte standaard gegevens.
         * @param standaardGroepInhoud de IST standaard gegevens
         */
        public Builder(final BrpIstStandaardGroepInhoud standaardGroepInhoud) {
            standaardGegevens = standaardGroepInhoud;
        }

        /**
         * zet de ingangsdatum familierechtelijke betrekking.
         * @param param ingangsdatum familierechtelijke betrekking
         * @return builder object
         */
        public final Builder rubriek6210DatumIngangFamilierechtelijkeBetrekking(final BrpInteger param) {
            rubriek6210DatumIngangFamilierechtelijkeBetrekking = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het anummer.
         * @param param anummer
         * @return builder object
         */
        public final Builder anummer(final BrpString param) {
            anummer = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het bsn.
         * @param param bsn
         * @return builder object
         */
        public final Builder bsn(final BrpString param) {
            bsn = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de voornamen.
         * @param param voornamen
         * @return builder object
         */
        public final Builder voornamen(final BrpString param) {
            voornamen = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het predicaatCode.
         * @param param predicaatCode
         * @return builder object
         */
        public final Builder predicaat(final BrpPredicaatCode param) {
            if (param != null) {
                predicaatCode = param.verwijderOnderzoek();
                if (predicaatCode != null) {
                    predicaatCode.setGeslachtsaanduiding(param.getGeslachtsaanduiding());
                }
            } else {
                predicaatCode = null;
            }
            return this;
        }

        /**
         * zet de adellijke titel.
         * @param param adellijke titel
         * @return builder object
         */
        public final Builder adellijkeTitel(final BrpAdellijkeTitelCode param) {
            if (param != null) {
                adellijkeTitel = param.verwijderOnderzoek();
                if (adellijkeTitel != null) {
                    adellijkeTitel.setGeslachtsaanduiding(param.getGeslachtsaanduiding());
                }
            } else {
                adellijkeTitel = null;
            }
            return this;
        }

        /**
         * zet de voorvoegsel.
         * @param param voorvoegsel
         * @return builder object
         */
        public final Builder voorvoegsel(final BrpString param) {
            voorvoegsel = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het scheidingsteken.
         * @param param scheidingsteken
         * @return builder object
         */
        public final Builder scheidingsteken(final BrpCharacter param) {
            scheidingsteken = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de geslachtsnaam.
         * @param param geslachtsnaamstam
         * @return builder object
         */
        public final Builder geslachtsnaamstam(final BrpString param) {
            geslachtsnaamstam = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum geboorte.
         * @param param datum geboorte
         * @return builder object
         */
        public final Builder datumGeboorte(final BrpInteger param) {
            datumGeboorte = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de gemeente van geboorte.
         * @param param gemeente van geboorte
         * @return builder object
         */
        public final Builder gemeenteCodeGeboorte(final BrpGemeenteCode param) {
            gemeenteCodeGeboorte = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de buitenlands plaats van geboorte.
         * @param param buitenlandse plaats van geboorte
         * @return builder object
         */
        public final Builder buitenlandsePlaatsGeboorte(final BrpString param) {
            buitenlandsePlaatsGeboorte = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de omschrijving locatie van geboorte.
         * @param param omschrijving locatie van geboorte
         * @return builder object
         */
        public final Builder omschrijvingLocatieGeboorte(final BrpString param) {
            omschrijvingLocatieGeboorte = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het land van geboorte.
         * @param param land/gebied van geboorte
         * @return builder object
         */
        public final Builder landOfGebiedGeboorte(final BrpLandOfGebiedCode param) {
            landOfGebiedCodeGeboorte = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de geslachtsaanduidingCode.
         * @param param geslachtsaanduidingCode
         * @return builder object
         */
        public final Builder geslachtsaanduidingCode(final BrpGeslachtsaanduidingCode param) {
            geslachtsaanduidingCode = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * @return een nieuw geconstrueerde {@link BrpIstRelatieGroepInhoud}
         */
        public final BrpIstRelatieGroepInhoud build() {
            return new BrpIstRelatieGroepInhoud(this);
        }
    }
}
