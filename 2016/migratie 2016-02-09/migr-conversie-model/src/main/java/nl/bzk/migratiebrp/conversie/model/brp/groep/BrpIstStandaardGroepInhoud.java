/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de standaard inhoud voor de IST-tabellen.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpIstStandaardGroepInhoud extends AbstractBrpIstGroepInhoud {

    /* Identiteit */
    @Element(name = "categorie", required = true)
    private final Lo3CategorieEnum categorie;
    @Element(name = "stapel", required = true)
    private final int stapel;
    @Element(name = "voorkomen", required = true)
    private final int voorkomen;

    /* Standaard */
    @Element(name = "soortDocument", required = false)
    private final BrpSoortDocumentCode soortDocument;
    @Element(name = "aktenummer", required = false)
    private final BrpString aktenummer;
    @Element(name = "partij", required = false)
    private final BrpPartijCode partij;
    @Element(name = "rubriek8220DatumDocument", required = false)
    private final BrpInteger rubriek8220DatumDocument;
    @Element(name = "documentOmschrijving", required = false)
    private final BrpString documentOmschrijving;
    @Element(name = "rubriek8310AanduidingGegevensInOnderzoek", required = false)
    private final BrpInteger rubriek8310AanduidingGegevensInOnderzoek;
    @Element(name = "rubriek8320DatumIngangOnderzoek", required = false)
    private final BrpInteger rubriek8320DatumIngangOnderzoek;
    @Element(name = "rubriek8330DatumEindeOnderzoek", required = false)
    private final BrpInteger rubriek8330DatumEindeOnderzoek;
    @Element(name = "rubriek8410OnjuistOfStrijdigOpenbareOrde", required = false)
    private final BrpCharacter rubriek8410OnjuistOfStrijdigOpenbareOrde;
    @Element(name = "rubriek8510IngangsdatumGeldigheid", required = false)
    private final BrpInteger rubriek8510IngangsdatumGeldigheid;
    @Element(name = "rubriek8610DatumVanOpneming", required = false)
    private final BrpInteger rubriek8610DatumVanOpneming;

    /**
     * Constructor met de verplichte velden voor de BrpIstStapelInhoud.
     *
     * @param categorienummer
     *            categorie nummer
     * @param stapelnummer
     *            stapel nummer binnen de categorie
     * @param voorkomennummer
     *            voorkomen nummer binnen de stapel
     * @param soortDocumentCode
     *            het soort document
     * @param nummerAkte
     *            het aktenummer
     * @param partijCode
     *            de partij die het document heeft uitgegeven
     * @param datumDocument
     *            datum van het document
     * @param omschrijvingDocument
     *            omschrijving van het document
     * @param aanduidingGegevensInOnderzoek
     *            aanduiding welke gegevens in onderzoek zijn
     * @param datumIngangOnderzoek
     *            datum wanneer het onderzoek is gestart
     * @param datumEindeOnderzoek
     *            datum wanneer het onderzoek klaar was
     * @param onjuistOfStrijdigOpenbareOrde
     *            indicatie onjuis of strijdig met openbare orde
     * @param ingangsdatumGeldigheid
     *            datum ingang geldigheid
     * @param datumVanOpneming
     *            datum van opneming
     */
    public BrpIstStandaardGroepInhoud(
        @Element(name = "categorie", required = true) final Lo3CategorieEnum categorienummer,
        @Element(name = "stapel", required = true) final int stapelnummer,
        @Element(name = "voorkomen", required = true) final int voorkomennummer,
        @Element(name = "soortDocument", required = false) final BrpSoortDocumentCode soortDocumentCode,
        @Element(name = "aktenummer", required = false) final BrpString nummerAkte,
        @Element(name = "partij", required = false) final BrpPartijCode partijCode,
        @Element(name = "rubriek8220DatumDocument", required = false) final BrpInteger datumDocument,
        @Element(name = "documentOmschrijving", required = false) final BrpString omschrijvingDocument,
        @Element(name = "rubriek8310AanduidingGegevensInOnderzoek", required = false) final BrpInteger aanduidingGegevensInOnderzoek,
        @Element(name = "rubriek8320DatumIngangOnderzoek", required = false) final BrpInteger datumIngangOnderzoek,
        @Element(name = "rubriek8330DatumEindeOnderzoek", required = false) final BrpInteger datumEindeOnderzoek,
        @Element(name = "rubriek8410OnjuistOfStrijdigOpenbareOrde", required = false) final BrpCharacter onjuistOfStrijdigOpenbareOrde,
        @Element(name = "rubriek8510IngangsdatumGeldigheid", required = false) final BrpInteger ingangsdatumGeldigheid,
        @Element(name = "rubriek8610DatumVanOpneming", required = false) final BrpInteger datumVanOpneming)
    {
        categorie = categorienummer;
        stapel = stapelnummer;
        voorkomen = voorkomennummer;
        soortDocument = soortDocumentCode;
        aktenummer = nummerAkte;
        partij = partijCode;
        rubriek8220DatumDocument = datumDocument;
        documentOmschrijving = omschrijvingDocument;
        rubriek8310AanduidingGegevensInOnderzoek = aanduidingGegevensInOnderzoek;
        rubriek8320DatumIngangOnderzoek = datumIngangOnderzoek;
        rubriek8330DatumEindeOnderzoek = datumEindeOnderzoek;
        rubriek8410OnjuistOfStrijdigOpenbareOrde = onjuistOfStrijdigOpenbareOrde;
        rubriek8510IngangsdatumGeldigheid = ingangsdatumGeldigheid;
        rubriek8610DatumVanOpneming = datumVanOpneming;
    }

    /**
     * Constructor op basis van Builder-pattern. Gebruik {@link BrpIstStandaardGroepInhoud.Builder}.
     *
     * @param builder
     *            builder met daar in de gegevens om de elementen van een BrpIstAbstractGroepInhoud te vullen
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud.Builder
     */
    private BrpIstStandaardGroepInhoud(final BrpIstStandaardGroepInhoud.Builder builder) {
        categorie = builder.categorie;
        stapel = builder.stapel;
        voorkomen = builder.voorkomen;
        soortDocument = builder.soortDocument;
        aktenummer = builder.aktenummer;
        partij = builder.partij;
        rubriek8220DatumDocument = builder.rubriek8220DatumDocument;
        documentOmschrijving = builder.documentOmschrijving;
        rubriek8310AanduidingGegevensInOnderzoek = builder.rubriek8310AanduidingGegevensInOnderzoek;
        rubriek8320DatumIngangOnderzoek = builder.rubriek8320DatumIngangOnderzoek;
        rubriek8330DatumEindeOnderzoek = builder.rubriek8330DatumEindeOnderzoek;
        rubriek8410OnjuistOfStrijdigOpenbareOrde = builder.rubriek8410OnjuistOfStrijdigOpenbareOrde;
        rubriek8510IngangsdatumGeldigheid = builder.rubriek8510IngangsdatumGeldigheid;
        rubriek8610DatumVanOpneming = builder.rubriek8610DatumVanOpneming;
    }

    /* equals methode wordt overridden in BrpIstRelatieGroepInhoud en BrpIstGezagsverhoudingGroepInhoud */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof BrpIstStandaardGroepInhoud)) {
            return false;
        }
        final BrpIstStandaardGroepInhoud castOther = (BrpIstStandaardGroepInhoud) other;
        return new EqualsBuilder().append(categorie, castOther.categorie)
                                  .append(stapel, castOther.stapel)
                                  .append(voorkomen, castOther.voorkomen)
                                  .append(soortDocument, castOther.soortDocument)
                                  .append(aktenummer, castOther.aktenummer)
                                  .append(partij, castOther.partij)
                                  .append(rubriek8220DatumDocument, castOther.rubriek8220DatumDocument)
                                  .append(documentOmschrijving, castOther.documentOmschrijving)
                                  .append(rubriek8310AanduidingGegevensInOnderzoek, castOther.rubriek8310AanduidingGegevensInOnderzoek)
                                  .append(rubriek8320DatumIngangOnderzoek, castOther.rubriek8320DatumIngangOnderzoek)
                                  .append(rubriek8330DatumEindeOnderzoek, castOther.rubriek8330DatumEindeOnderzoek)
                                  .append(rubriek8410OnjuistOfStrijdigOpenbareOrde, castOther.rubriek8410OnjuistOfStrijdigOpenbareOrde)
                                  .append(rubriek8510IngangsdatumGeldigheid, castOther.rubriek8510IngangsdatumGeldigheid)
                                  .append(rubriek8610DatumVanOpneming, castOther.rubriek8610DatumVanOpneming)
                                  .isEquals();
    }

    /* equals methode wordt overridden in BrpIstRelatieGroepInhoud en BrpIstGezagsverhoudingGroepInhoud */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorie)
                                    .append(stapel)
                                    .append(voorkomen)
                                    .append(soortDocument)
                                    .append(aktenummer)
                                    .append(partij)
                                    .append(rubriek8220DatumDocument)
                                    .append(documentOmschrijving)
                                    .append(rubriek8310AanduidingGegevensInOnderzoek)
                                    .append(rubriek8320DatumIngangOnderzoek)
                                    .append(rubriek8330DatumEindeOnderzoek)
                                    .append(rubriek8410OnjuistOfStrijdigOpenbareOrde)
                                    .append(rubriek8510IngangsdatumGeldigheid)
                                    .append(rubriek8610DatumVanOpneming)
                                    .toHashCode();
    }

    /* toString methode wordt overridden in BrpIstRelatieGroepInhoud en BrpIstGezagsverhoudingGroepInhoud */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("categorie", categorie)
                                                                          .append("stapel", stapel)
                                                                          .append("voorkomen", voorkomen)
                                                                          .append("soortDocument", soortDocument)
                                                                          .append("aktenummer", aktenummer)
                                                                          .append("partij", partij)
                                                                          .append("rubriek8220DatumDocument", rubriek8220DatumDocument)
                                                                          .append("documentOmschrijving", documentOmschrijving)
                                                                          .append(
                                                                              "rubriek8310AanduidingGegevensInOnderzoek",
                                                                              rubriek8310AanduidingGegevensInOnderzoek)
                                                                          .append("rubriek8320DatumIngangOnderzoek", rubriek8320DatumIngangOnderzoek)
                                                                          .append("rubriek8330DatumEindeOnderzoek", rubriek8330DatumEindeOnderzoek)
                                                                          .append(
                                                                              "rubriek8410OnjuistOfStrijdigOpenbareOrde",
                                                                              rubriek8410OnjuistOfStrijdigOpenbareOrde)
                                                                          .append("rubriek8510IngangsdatumGeldigheid", rubriek8510IngangsdatumGeldigheid)
                                                                          .append("rubriek8610DatumVanOpneming", rubriek8610DatumVanOpneming)
                                                                          .toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getCategorie()
     */
    @Override
    public Lo3CategorieEnum getCategorie() {
        return categorie;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getStapel()
     */
    @Override
    public int getStapel() {
        return stapel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getVoorkomen()
     */
    @Override
    public int getVoorkomen() {
        return voorkomen;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpIstGroepInhoud#getStandaardGegevens()
     */
    @Override
    public BrpIstStandaardGroepInhoud getStandaardGegevens() {
        return this;
    }

    /**
     * Geef de waarde van soort document.
     *
     * @return soort document
     */
    public BrpSoortDocumentCode getSoortDocument() {
        return soortDocument;
    }

    /**
     * Geef de waarde van aktenummer.
     *
     * @return aktenummer
     */
    public BrpString getAktenummer() {
        return aktenummer;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public BrpPartijCode getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van rubriek8220 datum document.
     *
     * @return rubriek8220 datum document
     */
    public BrpInteger getRubriek8220DatumDocument() {
        return rubriek8220DatumDocument;
    }

    /**
     * Geef de waarde van document omschrijving.
     *
     * @return document omschrijving
     */
    public BrpString getDocumentOmschrijving() {
        return documentOmschrijving;
    }

    /**
     * Geef de waarde van rubriek8310 aanduiding gegevens in onderzoek.
     *
     * @return rubriek8310 aanduiding gegevens in onderzoek
     */
    public BrpInteger getRubriek8310AanduidingGegevensInOnderzoek() {
        return rubriek8310AanduidingGegevensInOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8320 datum ingang onderzoek.
     *
     * @return rubriek8320 datum ingang onderzoek
     */
    public BrpInteger getRubriek8320DatumIngangOnderzoek() {
        return rubriek8320DatumIngangOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8330 datum einde onderzoek.
     *
     * @return rubriek8330 datum einde onderzoek
     */
    public BrpInteger getRubriek8330DatumEindeOnderzoek() {
        return rubriek8330DatumEindeOnderzoek;
    }

    /**
     * Geef de waarde van rubriek8410 onjuist of strijdig openbare orde.
     *
     * @return rubriek8410 onjuist of strijdig openbare orde
     */
    public BrpCharacter getRubriek8410OnjuistOfStrijdigOpenbareOrde() {
        return rubriek8410OnjuistOfStrijdigOpenbareOrde;
    }

    /**
     * Geef de waarde van rubriek8510 ingangsdatum geldigheid.
     *
     * @return rubriek8510 ingangsdatum geldigheid
     */
    public BrpInteger getRubriek8510IngangsdatumGeldigheid() {
        return rubriek8510IngangsdatumGeldigheid;
    }

    /**
     * Geef de waarde van rubriek8610 datum van opneming.
     *
     * @return rubriek8610 datum van opneming
     */
    public BrpInteger getRubriek8610DatumVanOpneming() {
        return rubriek8610DatumVanOpneming;
    }

    /**
     * Builder object voor BrpIstStandaardGroepInhoud. Alle attributen van deze inhoud worden zonder onderzoek
     * opgeslagen.
     */
    public static final class Builder {
        /* Identiteit */
        private final Lo3CategorieEnum categorie;
        private final int stapel;
        private final int voorkomen;

        /* Standaard */
        private BrpSoortDocumentCode soortDocument;
        private BrpString aktenummer;
        private BrpPartijCode partij;
        private BrpInteger rubriek8220DatumDocument;
        private BrpString documentOmschrijving;
        private BrpInteger rubriek8310AanduidingGegevensInOnderzoek;
        private BrpInteger rubriek8320DatumIngangOnderzoek;
        private BrpInteger rubriek8330DatumEindeOnderzoek;
        private BrpCharacter rubriek8410OnjuistOfStrijdigOpenbareOrde;
        private BrpInteger rubriek8510IngangsdatumGeldigheid;
        private BrpInteger rubriek8610DatumVanOpneming;

        /**
         * Constructor met verplichte velden categorie, stapel en voorkomen.
         *
         * @param categorienummer
         *            categorie nummer
         * @param stapelnummer
         *            stapel nummer
         * @param voorkomennummer
         *            voorkomen nummer
         */
        public Builder(final Lo3CategorieEnum categorienummer, final int stapelnummer, final int voorkomennummer) {
            categorie = categorienummer;
            stapel = stapelnummer;
            voorkomen = voorkomennummer;
        }

        /**
         * zet het soort document.
         *
         * @param param
         *            soort document
         * @return builder object
         */
        public Builder soortDocument(final BrpSoortDocumentCode param) {
            soortDocument = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het aktenummer.
         *
         * @param param
         *            aktenummer
         * @return builder object
         */
        public Builder aktenummer(final BrpString param) {
            aktenummer = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de partij.
         *
         * @param param
         *            partij
         * @return builder object
         */
        public Builder partij(final BrpPartijCode param) {
            partij = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum van het document (82.20).
         *
         * @param param
         *            datum document (82.20)
         * @return builder object
         */
        public Builder rubriek8220DatumDocument(final BrpInteger param) {
            rubriek8220DatumDocument = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de omschrijving van het document.
         *
         * @param param
         *            omschrijving van het document
         * @return builder object
         */
        public Builder documentOmschrijving(final BrpString param) {
            documentOmschrijving = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de aanduiding gegevens in onderzoek.
         *
         * @param param
         *            aanduiding gegevens in onderzoek
         * @return builder object
         */
        public Builder rubriek8310AanduidingGegevensInOnderzoek(final BrpInteger param) {
            rubriek8310AanduidingGegevensInOnderzoek = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum ingang onderzoek.
         *
         * @param param
         *            datum ingang onderzoek
         * @return builder object
         */
        public Builder rubriek8320DatumIngangOnderzoek(final BrpInteger param) {
            rubriek8320DatumIngangOnderzoek = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum einde onderzoek.
         *
         * @param param
         *            datum einde onderzoek
         * @return builder object
         */
        public Builder rubriek8330DatumEindeOnderzoek(final BrpInteger param) {
            rubriek8330DatumEindeOnderzoek = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het de indicatie onjuist / strijdig met openbare orde.
         *
         * @param param
         *            indicatie / strijdig met openbare orde
         * @return builder object
         */
        public Builder rubriek8410OnjuistOfStrijdigOpenbareOrde(final BrpCharacter param) {
            rubriek8410OnjuistOfStrijdigOpenbareOrde = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum ingang geldigheid.
         *
         * @param param
         *            datum ingang geldigheid
         * @return builder object
         */
        public Builder rubriek8510IngangsdatumGeldigheid(final BrpInteger param) {
            rubriek8510IngangsdatumGeldigheid = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum van opneming.
         *
         * @param param
         *            datum van opneming
         * @return builder object
         */
        public Builder rubriek8610DatumVanOpneming(final BrpInteger param) {
            rubriek8610DatumVanOpneming = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * Bouwt een instantie.
         *
         * @return soort object
         */
        public BrpIstStandaardGroepInhoud build() {
            return new BrpIstStandaardGroepInhoud(this);
        }
    }
}
