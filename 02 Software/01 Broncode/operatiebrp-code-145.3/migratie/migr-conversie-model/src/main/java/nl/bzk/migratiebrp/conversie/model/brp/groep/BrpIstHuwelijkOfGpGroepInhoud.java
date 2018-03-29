/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BrpIstHuwelijkOfGpGroepInhoud
 */
public final class BrpIstHuwelijkOfGpGroepInhoud extends AbstractBrpIstGroepInhoud {
    @Element(name = "standaardGegevens", required = true)
    private final BrpIstStandaardGroepInhoud standaardGegevens;
    @Element(name = "relatie", required = true)
    private final BrpIstRelatieGroepInhoud relatie;

    /* Categorie huwelijk / geregistreerd partnerschap. */
    @Element(name = "datumAanvang", required = false)
    private final BrpInteger datumAanvang;
    @Element(name = "gemeenteCodeAanvang", required = false)
    private final BrpGemeenteCode gemeenteCodeAanvang;
    @Element(name = "buitenlandsePlaatsAanvang", required = false)
    private final BrpString buitenlandsePlaatsAanvang;
    @Element(name = "omschrijvingLocatieAanvang", required = false)
    private final BrpString omschrijvingLocatieAanvang;
    @Element(name = "landOfGebiedCodeAanvang", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCodeAanvang;
    @Element(name = "redenEindeRelatieCode", required = false)
    private final BrpRedenEindeRelatieCode redenEindeRelatieCode;
    @Element(name = "datumEinde", required = false)
    private final BrpInteger datumEinde;
    @Element(name = "gemeenteCodeEinde", required = false)
    private final BrpGemeenteCode gemeenteCodeEinde;
    @Element(name = "buitenlandsePlaatsEinde", required = false)
    private final BrpString buitenlandsePlaatsEinde;
    @Element(name = "omschrijvingLocatieEinde", required = false)
    private final BrpString omschrijvingLocatieEinde;
    @Element(name = "landOfGebiedCodeEinde", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCodeEinde;
    @Element(name = "soortRelatieCode", required = false)
    private final BrpSoortRelatieCode soortRelatieCode;

    /**
     * Maakt een BrpIstHuwelijkOfGpStapelInhoud object.<br>
     * LET OP: Niet gebruiken, is alleen voor het (de)serializeren van de XML-representatie. Maak gebruik van de
     * Builder.
     * @param standaardGroepInhoud standaard IST gegevens
     * @param relatieInhoud relatie inhoud
     * @param aanvangDatum datum aanvang (85.10)
     * @param aanvangGemeenteCode gemeente van aanvang
     * @param aanvangBuitenlandsePlaats buitenlandse plaats van aanvang
     * @param aanvangOmschrijvingLocatie omschrijving locatie aanvang
     * @param aanvangLandOfGebiedCode land aanvang
     * @param redenEindeRelatie reden beeindiging relatie
     * @param eindeDatum datum einde
     * @param eindeGemeenteCode gemeente einde
     * @param eindeBuitenlandsePlaats buitenlandse plaats einde
     * @param eindeOmschrijvingLocatie omschrijving locatie einde
     * @param eindeLandOfGebiedCode land einde
     * @param soortRelatie soort relatie
     */
    public BrpIstHuwelijkOfGpGroepInhoud(
            @Element(name = "standaardGegevens", required = true) final BrpIstStandaardGroepInhoud standaardGroepInhoud,
            @Element(name = "relatie", required = true) final BrpIstRelatieGroepInhoud relatieInhoud,
            @Element(name = "datumAanvang", required = false) final BrpInteger aanvangDatum,
            @Element(name = "gemeenteCodeAanvang", required = false) final BrpGemeenteCode aanvangGemeenteCode,
            @Element(name = "buitenlandsePlaatsAanvang", required = false) final BrpString aanvangBuitenlandsePlaats,
            @Element(name = "omschrijvingLocatieAanvang", required = false) final BrpString aanvangOmschrijvingLocatie,
            @Element(name = "landOfGebiedCodeAanvang", required = false) final BrpLandOfGebiedCode aanvangLandOfGebiedCode,
            @Element(name = "redenEindeRelatieCode", required = false) final BrpRedenEindeRelatieCode redenEindeRelatie,
            @Element(name = "datumEinde", required = false) final BrpInteger eindeDatum,
            @Element(name = "gemeenteCodeEinde", required = false) final BrpGemeenteCode eindeGemeenteCode,
            @Element(name = "buitenlandsePlaatsEinde", required = false) final BrpString eindeBuitenlandsePlaats,
            @Element(name = "omschrijvingLocatieEinde", required = false) final BrpString eindeOmschrijvingLocatie,
            @Element(name = "landOfGebiedCodeEinde", required = false) final BrpLandOfGebiedCode eindeLandOfGebiedCode,
            @Element(name = "soortRelatieCode", required = false) final BrpSoortRelatieCode soortRelatie) {
        standaardGegevens = standaardGroepInhoud;
        relatie = relatieInhoud;
        datumAanvang = aanvangDatum;
        gemeenteCodeAanvang = aanvangGemeenteCode;
        buitenlandsePlaatsAanvang = aanvangBuitenlandsePlaats;
        omschrijvingLocatieAanvang = aanvangOmschrijvingLocatie;
        landOfGebiedCodeAanvang = aanvangLandOfGebiedCode;
        redenEindeRelatieCode = redenEindeRelatie;
        datumEinde = eindeDatum;
        gemeenteCodeEinde = eindeGemeenteCode;
        buitenlandsePlaatsEinde = eindeBuitenlandsePlaats;
        omschrijvingLocatieEinde = eindeOmschrijvingLocatie;
        landOfGebiedCodeEinde = eindeLandOfGebiedCode;
        soortRelatieCode = soortRelatie;
    }

    private BrpIstHuwelijkOfGpGroepInhoud(final Builder builder) {
        standaardGegevens = builder.standaardGegevens;
        relatie = builder.relatie;
        datumAanvang = builder.datumAanvang;
        gemeenteCodeAanvang = builder.gemeenteCodeAanvang;
        buitenlandsePlaatsAanvang = builder.buitenlandsePlaatsAanvang;
        omschrijvingLocatieAanvang = builder.omschrijvingLocatieAanvang;
        landOfGebiedCodeAanvang = builder.landOfGebiedAanvang;
        redenEindeRelatieCode = builder.redenEindeRelatieCode;
        datumEinde = builder.datumEinde;
        gemeenteCodeEinde = builder.gemeenteCodeEinde;
        buitenlandsePlaatsEinde = builder.buitenlandsePlaatsEinde;
        omschrijvingLocatieEinde = builder.omschrijvingLocatieEinde;
        landOfGebiedCodeEinde = builder.landOfGebiedEinde;
        soortRelatieCode = builder.soortRelatieCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof BrpIstHuwelijkOfGpGroepInhoud)) {
            return false;
        }
        final BrpIstHuwelijkOfGpGroepInhoud castOther = (BrpIstHuwelijkOfGpGroepInhoud) other;
        return new EqualsBuilder().append(standaardGegevens, castOther.standaardGegevens)
                .append(relatie, castOther.relatie)
                .append(datumAanvang, castOther.datumAanvang)
                .append(gemeenteCodeAanvang, castOther.gemeenteCodeAanvang)
                .append(buitenlandsePlaatsAanvang, castOther.buitenlandsePlaatsAanvang)
                .append(omschrijvingLocatieAanvang, castOther.omschrijvingLocatieAanvang)
                .append(landOfGebiedCodeAanvang, castOther.landOfGebiedCodeAanvang)
                .append(redenEindeRelatieCode, castOther.redenEindeRelatieCode)
                .append(datumEinde, castOther.datumEinde)
                .append(gemeenteCodeEinde, castOther.gemeenteCodeEinde)
                .append(buitenlandsePlaatsEinde, castOther.buitenlandsePlaatsEinde)
                .append(omschrijvingLocatieEinde, castOther.omschrijvingLocatieEinde)
                .append(landOfGebiedCodeEinde, castOther.landOfGebiedCodeEinde)
                .append(soortRelatieCode, castOther.soortRelatieCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(standaardGegevens)
                .append(relatie)
                .append(datumAanvang)
                .append(gemeenteCodeAanvang)
                .append(buitenlandsePlaatsAanvang)
                .append(omschrijvingLocatieAanvang)
                .append(landOfGebiedCodeAanvang)
                .append(redenEindeRelatieCode)
                .append(datumEinde)
                .append(gemeenteCodeEinde)
                .append(buitenlandsePlaatsEinde)
                .append(omschrijvingLocatieEinde)
                .append(landOfGebiedCodeEinde)
                .append(soortRelatieCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("standaardGegevens", standaardGegevens)
                .append("relatie", relatie)
                .append("datumAanvang", datumAanvang)
                .append("gemeenteCodeAanvang", gemeenteCodeAanvang)
                .append("buitenlandsePlaatsAanvang", buitenlandsePlaatsAanvang)
                .append("omschrijvingLocatieAanvang", omschrijvingLocatieAanvang)
                .append("landOfGebiedCodeAanvang", landOfGebiedCodeAanvang)
                .append("redenBeeindigingRelatieCode", redenEindeRelatieCode)
                .append("datumEinde", datumEinde)
                .append("gemeenteCodeEinde", gemeenteCodeEinde)
                .append("buitenlandsePlaatsEinde", buitenlandsePlaatsEinde)
                .append("omschrijvingLocatieEinde", omschrijvingLocatieEinde)
                .append("landOfGebiedCodeEinde", landOfGebiedCodeEinde)
                .append("soortRelatieCode", soortRelatieCode)
                .toString();
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
     * Geef de waarde van relatie van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van relatie van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpIstRelatieGroepInhoud getRelatie() {
        return relatie;
    }

    /**
     * Geef de waarde van datum aanvang van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van datum aanvang van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpInteger getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Geef de waarde van gemeente code aanvang van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van gemeente code aanvang van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpGemeenteCode getGemeenteCodeAanvang() {
        return gemeenteCodeAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van buitenlandse plaats aanvang van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpString getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van omschrijving locatie aanvang van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpString getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van land of gebied code aanvang van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van land of gebied code aanvang van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpLandOfGebiedCode getLandOfGebiedCodeAanvang() {
        return landOfGebiedCodeAanvang;
    }

    /**
     * Geef de waarde van reden einde relatie code van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van reden einde relatie code van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpRedenEindeRelatieCode getRedenEindeRelatieCode() {
        return redenEindeRelatieCode;
    }

    /**
     * Geef de waarde van datum einde van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van datum einde van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpInteger getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van gemeente code einde van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van gemeente code einde van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpGemeenteCode getGemeenteCodeEinde() {
        return gemeenteCodeEinde;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van buitenlandse plaats einde van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpString getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie einde van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van omschrijving locatie einde van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpString getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van land of gebied code einde van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van land of gebied code einde van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpLandOfGebiedCode getLandOfGebiedCodeEinde() {
        return landOfGebiedCodeEinde;
    }

    /**
     * Geef de waarde van soort relatie code van BrpIstHuwelijkOfGpGroepInhoud.
     * @return de waarde van soort relatie code van BrpIstHuwelijkOfGpGroepInhoud
     */
    public BrpSoortRelatieCode getSoortRelatieCode() {
        return soortRelatieCode;
    }

    /**
     * Builder object voor BrpIstRelatieGroepInhoud.
     */
    public static class Builder {
        private final BrpIstStandaardGroepInhoud standaardGegevens;
        private final BrpIstRelatieGroepInhoud relatie;
        private BrpInteger datumAanvang;
        private BrpGemeenteCode gemeenteCodeAanvang;
        private BrpString buitenlandsePlaatsAanvang;
        private BrpString omschrijvingLocatieAanvang;
        private BrpLandOfGebiedCode landOfGebiedAanvang;
        private BrpRedenEindeRelatieCode redenEindeRelatieCode;
        private BrpInteger datumEinde;
        private BrpGemeenteCode gemeenteCodeEinde;
        private BrpString buitenlandsePlaatsEinde;
        private BrpString omschrijvingLocatieEinde;
        private BrpLandOfGebiedCode landOfGebiedEinde;
        private BrpSoortRelatieCode soortRelatieCode;

        /**
         * Constructor met verplichte velden categorie, stapel en voorkomen.
         * @param standaardGroepInhoud {@link BrpIstStandaardGroepInhoud} inhoud met de standaard IST gegevens
         * @param relatieInhoud {@link BrpIstRelatieGroepInhoud} inhoud van de relatie gedeelte van dit huweljk/GP
         */
        public Builder(final BrpIstStandaardGroepInhoud standaardGroepInhoud, final BrpIstRelatieGroepInhoud relatieInhoud) {
            standaardGegevens = standaardGroepInhoud;
            relatie = relatieInhoud;
        }

        /**
         * zet de datum aanvang.
         * @param param datum aanvang
         * @return builder object
         */
        public final Builder datumAanvang(final BrpInteger param) {
            datumAanvang = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de gemeentecode aanvang.
         * @param param gemeente aanvang
         * @return builder object
         */
        public final Builder gemeenteCodeAanvang(final BrpGemeenteCode param) {
            gemeenteCodeAanvang = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de buitenlandse plaats aanvang.
         * @param param buitenlandse plaats aanvang
         * @return builder object
         */
        public final Builder buitenlandsePlaatsAanvang(final BrpString param) {
            buitenlandsePlaatsAanvang = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de omschrijving locatie aanvang.
         * @param param omschrijving locatie aanvang
         * @return builder object
         */
        public final Builder omschrijvingLocatieAanvang(final BrpString param) {
            omschrijvingLocatieAanvang = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het land/gebied aanvang.
         * @param param land aanvang
         * @return builder object
         */
        public final Builder landOfGebiedAanvang(final BrpLandOfGebiedCode param) {
            landOfGebiedAanvang = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de reden beeindiging relatie code.
         * @param param reden beeindiging relatie
         * @return builder object
         */
        public final Builder redenBeeindigingRelatieCode(final BrpRedenEindeRelatieCode param) {
            redenEindeRelatieCode = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de datum einde.
         * @param param datum einde
         * @return builder object
         */
        public final Builder datumEinde(final BrpInteger param) {
            datumEinde = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de gemeente einde.
         * @param param gemeente einde
         * @return builder object
         */
        public final Builder gemeenteCodeEinde(final BrpGemeenteCode param) {
            gemeenteCodeEinde = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de buitenlandse plaats einde.
         * @param param buitenlandse plaats einde
         * @return builder object
         */
        public final Builder buitenlandsePlaatsEinde(final BrpString param) {
            buitenlandsePlaatsEinde = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet de omschrijving locatie einde.
         * @param param omschrijving locatie einde
         * @return builder object
         */
        public final Builder omschrijvingLocatieEinde(final BrpString param) {
            omschrijvingLocatieEinde = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het land/gebied einde.
         * @param param land einde
         * @return builder object
         */
        public final Builder landOfGebiedEinde(final BrpLandOfGebiedCode param) {
            landOfGebiedEinde = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * zet het soort relatie code.
         * @param param soort relatie code
         * @return builder object
         */
        public final Builder soortRelatieCode(final BrpSoortRelatieCode param) {
            soortRelatieCode = param == null ? null : param.verwijderOnderzoek();
            return this;
        }

        /**
         * @return een nieuw geconstrueerde {@link BrpIstHuwelijkOfGpGroepInhoud}
         */
        public final BrpIstHuwelijkOfGpGroepInhoud build() {
            return new BrpIstHuwelijkOfGpGroepInhoud(this);
        }
    }
}
