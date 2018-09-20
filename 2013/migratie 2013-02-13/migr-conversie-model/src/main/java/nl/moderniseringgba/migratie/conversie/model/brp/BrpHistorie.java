/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de BRP Groep Historie.
 * 
 * Deze class immutable en threadsafe.
 * 
 * 
 * 
 * 
 */
public final class BrpHistorie {

    @Element(name = "datumAanvangGeldigheid", required = false)
    private final BrpDatum datumAanvangGeldigheid;
    @Element(name = "datumEindeGeldigheid", required = false)
    private final BrpDatum datumEindeGeldigheid;
    @Element(name = "datumTijdRegistratie", required = false)
    private final BrpDatumTijd datumTijdRegistratie;
    @Element(name = "datumTijdVerval", required = false)
    private final BrpDatumTijd datumTijdVerval;

    /**
     * Maakt een BrpHistorie object.
     * 
     * @param datumAanvangGeldigheid
     *            datum aanvang, mag null zijn
     * @param datumEindeGeldigheid
     *            datum eindige geldigheid, mag null zijn
     * @param datumTijdRegistratie
     *            datum en tijd van de registratie, mag niet null zijn
     * @param datumTijdVerval
     *            datum en tijd van verval, mag null zijn
     * @throws NullPointerException
     *             als datumAanvangGeldigheid of datumTijdRegistratie null is
     */
    public BrpHistorie(
            @Element(name = "datumAanvangGeldigheid", required = false) final BrpDatum datumAanvangGeldigheid,
            @Element(name = "datumEindeGeldigheid", required = false) final BrpDatum datumEindeGeldigheid,
            @Element(name = "datumTijdRegistratie", required = false) final BrpDatumTijd datumTijdRegistratie,
            @Element(name = "datumTijdVerval", required = false) final BrpDatumTijd datumTijdVerval) {

        if (datumTijdRegistratie == null) {
            throw new NullPointerException("datumTijdRegistratie moet gevuld zijn.");
        }
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        this.datumTijdRegistratie = datumTijdRegistratie;
        this.datumTijdVerval = datumTijdVerval;
    }

    /**
     * @return true als de vervaldatum gezet is.
     */
    public boolean isVervallen() {
        return datumTijdVerval != null;
    }

    /**
     * @return de datumTijdRegistratie
     */
    public BrpDatumTijd getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * @return de datumAanvangGeldigheid
     */
    public BrpDatum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * @return de datumEindeGeldigheid of null
     */
    public BrpDatum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * @return de datumTijdVerval of null
     */
    public BrpDatumTijd getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * @param vervalDatum
     *            de datum waarop de groep is komen te vervallen
     * @return een BrpHistorie waarbij de historie is vervallen op de opgegeven vervalDatum
     * @throws IllegalStateException
     *             als de historie al vervallen was.
     * @see #isVervallen()
     */
    public BrpHistorie laatVervallen(final BrpDatumTijd vervalDatum) {
        if (isVervallen()) {
            throw new IllegalStateException("De BrpHistorie is al vervallen.");
        }
        return new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, vervalDatum);
    }

    /**
     * Bepaal of deze historie qua geldigheid overlap heeft met de meegegeven historie.
     * 
     * @param andere
     *            de andere historie
     * @return true als er overlap in geldigheid bestaat tussen deze historie en de andere historie
     */
    public boolean geldigheidOverlapt(final BrpHistorie andere) {
        final BrpDatum andereAanvang = andere.getDatumAanvangGeldigheid();
        final BrpDatum anderEinde = andere.getDatumEindeGeldigheid();

        final boolean anderStartGelijkOfEerderEnHeeftOverlap =
                andereAanvang.getDatum() <= datumAanvangGeldigheid.getDatum()
                        && (anderEinde == null || anderEinde.getDatum() > datumAanvangGeldigheid.getDatum());
        final boolean anderStartGelijkOfLaterEnHeeftOverlap =
                andereAanvang.getDatum() >= datumAanvangGeldigheid.getDatum()
                        && (datumEindeGeldigheid == null || datumEindeGeldigheid.getDatum() > andereAanvang
                                .getDatum());
        return anderStartGelijkOfEerderEnHeeftOverlap || anderStartGelijkOfLaterEnHeeftOverlap;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpHistorie)) {
            return false;
        }
        final BrpHistorie castOther = (BrpHistorie) other;
        return new EqualsBuilder().append(datumAanvangGeldigheid, castOther.datumAanvangGeldigheid)
                .append(datumEindeGeldigheid, castOther.datumEindeGeldigheid)
                .append(datumTijdRegistratie, castOther.datumTijdRegistratie)
                .append(datumTijdVerval, castOther.datumTijdVerval).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumAanvangGeldigheid).append(datumEindeGeldigheid)
                .append(datumTijdRegistratie).append(datumTijdVerval).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumAanvangGeldigheid", datumAanvangGeldigheid)
                .append("datumEindeGeldigheid", datumEindeGeldigheid)
                .append("datumTijdRegistratie", datumTijdRegistratie).append("datumTijdVerval", datumTijdVerval)
                .toString();
    }

    /** Builder. */
    public static final class Builder {
        private BrpDatum datumAanvangGeldigheid;
        private BrpDatum datumEindeGeldigheid;
        private BrpDatumTijd datumTijdRegistratie;
        private BrpDatumTijd datumTijdVerval;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initieel gevulde builder.
         * 
         * @param inhoud
         *            initiele vulling
         */
        public Builder(final BrpHistorie inhoud) {
            datumAanvangGeldigheid = inhoud.datumAanvangGeldigheid;
            datumEindeGeldigheid = inhoud.datumEindeGeldigheid;
            datumTijdRegistratie = inhoud.datumTijdRegistratie;
            datumTijdVerval = inhoud.datumTijdVerval;
        }

        /**
         * @return inhoud
         */
        public BrpHistorie build() {
            return new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie,
                    datumTijdVerval);
        }

        /**
         * @param datumAanvangGeldigheid
         *            the datumAanvangGeldigheid to set
         */
        public void setDatumAanvangGeldigheid(final BrpDatum datumAanvangGeldigheid) {
            this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        }

        /**
         * @param datumEindeGeldigheid
         *            the datumEindeGeldigheid to set
         */
        public void setDatumEindeGeldigheid(final BrpDatum datumEindeGeldigheid) {
            this.datumEindeGeldigheid = datumEindeGeldigheid;
        }

        /**
         * @param datumTijdRegistratie
         *            the datumTijdRegistratie to set
         */
        public void setDatumTijdRegistratie(final BrpDatumTijd datumTijdRegistratie) {
            this.datumTijdRegistratie = datumTijdRegistratie;
        }

        /**
         * @param datumTijdVerval
         *            the datumTijdVerval to set
         */
        public void setDatumTijdVerval(final BrpDatumTijd datumTijdVerval) {
            this.datumTijdVerval = datumTijdVerval;
        }

    }
}
