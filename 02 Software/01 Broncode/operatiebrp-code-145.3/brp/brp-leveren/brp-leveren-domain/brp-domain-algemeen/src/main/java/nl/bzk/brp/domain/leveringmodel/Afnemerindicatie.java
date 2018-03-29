/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.base.Objects;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Het operationele afnemerindicatie object.
 */
public final class Afnemerindicatie {

    private Long objectSleutel;
    private Long voorkomenSleutel;
    private Long persoonId;
    private int leveringsAutorisatieId;
    private String afnemerCode;
    private ZonedDateTime tijdstipRegistratie;
    private ZonedDateTime tijdstipVerval;
    private Integer datumEindeVolgen;
    private Integer datumAanvangMaterielePeriode;
    private NadereAanduidingVerval nadereAanduidingVerval;

    private Afnemerindicatie() {
    }

    /**
     * @return een nieuwe builder.
     */
    public static Builder maakBuilder() {
        return new Builder();
    }

    /**
     * @return technisch object id
     */
    public Long getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * @return technisch voorkomen id
     */
    public Long getVoorkomenSleutel() {
        return voorkomenSleutel;
    }

    /**
     * @return id van de persoon waarop de afnemerindicatie betrekking heeft,
     */
    public Long getPersoonId() {
        return persoonId;
    }

    /**
     * @return optionele nadere aanduiding verval indien vervallen
     */
    public NadereAanduidingVerval getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * @return id van de leveringsautorisatie waarvoor de indicatie geplaatst is.
     */
    public int getLeveringsAutorisatieId() {
        return leveringsAutorisatieId;
    }

    /**
     * @return de code van de afnemer waarvoor de indicatie geldt
     */
    public String getAfnemerCode() {
        return afnemerCode;
    }

    /**
     * @return tijdstipRegistratie
     */
    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * @return tijdstipVerval
     */
    public ZonedDateTime getTijdstipVerval() {
        return tijdstipVerval;
    }

    /**
     * @return de datum waarop de afnemerindicatie niet meer relevant is. Attendering obv afnemerindicatie zal bijv. niet meer triggeren.
     */
    public Integer getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Materieel beeindigde records met einddatum &lt; datumAanvangMaterielePeriode worden niet getoond in het bericht.
     * @return een datum attribuut
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Afnemerindicatie that = (Afnemerindicatie) o;
        return new EqualsBuilder()
                .append(voorkomenSleutel, that.voorkomenSleutel)
                .append(persoonId, that.persoonId)
                .append(leveringsAutorisatieId, that.leveringsAutorisatieId)
                .append(afnemerCode, that.afnemerCode).isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voorkomenSleutel, leveringsAutorisatieId, afnemerCode, persoonId);
    }

    /**
     * Builder klasse voor de afnemerindicatie.
     */
    public static final class Builder {
        private Long objectsleutel;
        private Long voorkomensleutel;
        private Long persId;
        private int levAutId;
        private String afnemercode;
        private ZonedDateTime tsRegistratie;
        private ZonedDateTime tsVerval;
        private Integer datEindeVolgen;
        private Integer datAanvangMaterielePeriode;
        private NadereAanduidingVerval nadereAandVerval;

        /**
         * @param objectSleutelBuild objectsleutel
         * @return builder
         */
        public Builder metObjectSleutel(final Long objectSleutelBuild) {
            this.objectsleutel = objectSleutelBuild;
            return this;
        }

        /**
         * @param voorkomenSleutelBuild voorkomensleutel
         * @return builder
         */
        public Builder metVoorkomenSleutel(final Long voorkomenSleutelBuild) {
            this.voorkomensleutel = voorkomenSleutelBuild;
            return this;
        }

        /**
         * @param persoonIdBuild persId
         * @return builder
         */
        public Builder metPersoonId(final Long persoonIdBuild) {
            this.persId = persoonIdBuild;
            return this;
        }

        /**
         * @param leveringsAutorisatieIdBuild leveringsAutorisatieIdBuild
         * @return builder
         */
        public Builder metLeveringsAutorisatieId(final int leveringsAutorisatieIdBuild) {
            this.levAutId = leveringsAutorisatieIdBuild;
            return this;
        }


        /**
         * @param afnemerCodeBuild afnemerCodeBuild
         * @return builder
         */
        public Builder metAfnemerCode(final String afnemerCodeBuild) {
            this.afnemercode = afnemerCodeBuild;
            return this;
        }

        /**
         * @param tijdstipRegistratieBuild tsRegistratie
         * @return builder
         */
        public Builder metTijdstipRegistratie(final ZonedDateTime tijdstipRegistratieBuild) {
            this.tsRegistratie = tijdstipRegistratieBuild;
            return this;
        }

        /**
         * @param tijdstipVervalBuild tsVerval
         * @return builder
         */
        public Builder metTijdstipVerval(final ZonedDateTime tijdstipVervalBuild) {
            this.tsVerval = tijdstipVervalBuild;
            return this;
        }

        /**
         * @param datumEindeVolgenBuild datEindeVolgen
         * @return builder
         */
        public Builder metDatumEindeVolgen(final int datumEindeVolgenBuild) {
            this.datEindeVolgen = datumEindeVolgenBuild;
            return this;
        }

        /**
         * @param datumAanvangMaterielePeriodeBuild datAanvangMaterielePeriode
         * @return builder
         */
        public Builder metDatumAanvangMaterielePeriode(final int datumAanvangMaterielePeriodeBuild) {
            this.datAanvangMaterielePeriode = datumAanvangMaterielePeriodeBuild;
            return this;
        }

        /**
         * @param nadereAanduidingVervalBuild nadereAandVerval
         * @return builder
         */
        public Builder metNadereAanduidingVerval(final NadereAanduidingVerval nadereAanduidingVervalBuild) {
            this.nadereAandVerval = nadereAanduidingVervalBuild;
            return this;
        }

        /**
         * @return bouwt de afnemerindicatie.
         */
        public Afnemerindicatie build() {
            final Afnemerindicatie afnemerindicatie = new Afnemerindicatie();
            afnemerindicatie.objectSleutel = this.objectsleutel;
            afnemerindicatie.voorkomenSleutel = this.voorkomensleutel;
            afnemerindicatie.persoonId = this.persId;
            afnemerindicatie.leveringsAutorisatieId = this.levAutId;
            afnemerindicatie.afnemerCode = this.afnemercode;
            afnemerindicatie.tijdstipRegistratie = this.tsRegistratie;
            afnemerindicatie.tijdstipVerval = this.tsVerval;
            afnemerindicatie.nadereAanduidingVerval = this.nadereAandVerval;
            afnemerindicatie.datumEindeVolgen = this.datEindeVolgen;
            afnemerindicatie.datumAanvangMaterielePeriode = this.datAanvangMaterielePeriode;
            return afnemerindicatie;
        }
    }
}
