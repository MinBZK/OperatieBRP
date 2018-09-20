/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;

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

    /**
     * Null object voor de IST groepen die geen historie bezitten.
     */
    public static final BrpHistorie NULL_HISTORIE = new BrpHistorie(null, null, BrpDatumTijd.NULL_DATUM_TIJD, null, null);

    @Element(name = "datumAanvangGeldigheid", required = false)
    private final BrpDatum datumAanvangGeldigheid;
    @Element(name = "datumEindeGeldigheid", required = false)
    private final BrpDatum datumEindeGeldigheid;
    @Element(name = "datumTijdRegistratie", required = true)
    private final BrpDatumTijd datumTijdRegistratie;
    @Element(name = "datumTijdVerval", required = false)
    private final BrpDatumTijd datumTijdVerval;
    @Element(name = "nadereAanduidingVerval", required = false)
    private final BrpCharacter nadereAanduidingVerval;

    /**
     * Maakt een BrpHistorie object voor zowel materiele als formele historie.
     * 
     * @param datumAanvangGeldigheid
     *            datum aanvang, mag null zijn
     * @param datumEindeGeldigheid
     *            datum eindige geldigheid, mag null zijn
     * @param datumTijdRegistratie
     *            datum en tijd van de registratie, mag niet null zijn
     * @param datumTijdVerval
     *            datum en tijd van verval, mag null zijn
     * @param nadereAanduidingVerval
     *            nadere aanduiding verval ('O' bij een onjuiste rij), mag null zijn
     * @throws NullPointerException
     *             als datumTijdRegistratie null is
     */
    public BrpHistorie(
        @Element(name = "datumAanvangGeldigheid", required = false) final BrpDatum datumAanvangGeldigheid,
        @Element(name = "datumEindeGeldigheid", required = false) final BrpDatum datumEindeGeldigheid,
        @Element(name = "datumTijdRegistratie", required = true) final BrpDatumTijd datumTijdRegistratie,
        @Element(name = "datumTijdVerval", required = false) final BrpDatumTijd datumTijdVerval,
        @Element(name = "nadereAanduidingVerval", required = false) final BrpCharacter nadereAanduidingVerval)
    {

        if (datumTijdRegistratie == null) {
            throw new NullPointerException("datumTijdRegistratie moet gevuld zijn.");
        }
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        this.datumTijdRegistratie = datumTijdRegistratie;
        this.datumTijdVerval = datumTijdVerval;
        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    /**
     * Maak een BrpHistorie object voor formele historie.
     * 
     * @param datumTijdRegistratie
     *            datum en tijd van de registratie, mag niet null zijn
     * @param datumTijdVerval
     *            datum en tijd van verval, mag null zijn
     * @param nadereAanduidingVerval
     *            nadere aanduiding verval ('O' bij een onjuiste rij), mag null zijn
     */
    public BrpHistorie(final BrpDatumTijd datumTijdRegistratie, final BrpDatumTijd datumTijdVerval, final BrpCharacter nadereAanduidingVerval) {
        this(null, null, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);
    }

    /**
     * Geef de vervallen.
     *
     * @return true als de vervaldatum gezet is.
     */
    public boolean isVervallen() {
        return datumTijdVerval != null;
    }

    /**
     * Geef de waarde van datum tijd registratie.
     *
     * @return de datumTijdRegistratie
     */
    public BrpDatumTijd getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid.
     *
     * @return de datumAanvangGeldigheid
     */
    public BrpDatum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid.
     *
     * @return de datumEindeGeldigheid of null
     */
    public BrpDatum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Geef de waarde van datum tijd verval.
     *
     * @return de datumTijdVerval of null
     */
    public BrpDatumTijd getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * Geef de waarde van nadere aanduiding verval.
     *
     * @return de nadereAanduidingVerval ('O') of null
     */
    public BrpCharacter getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * Geeft aan of de historie een actuele rij betreft.
     * 
     * @return true als deze historie een actuele rij is
     */
    public boolean isActueel() {
        return (datumTijdVerval == null || !datumTijdVerval.isInhoudelijkGevuld())
               && (datumEindeGeldigheid == null || !datumEindeGeldigheid.isInhoudelijkGevuld());
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
        return new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, vervalDatum, null);
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
                andereAanvang.getWaarde() <= datumAanvangGeldigheid.getWaarde()
                        && (anderEinde == null || anderEinde.getWaarde() > datumAanvangGeldigheid.getWaarde());
        final boolean anderStartGelijkOfLaterEnHeeftOverlap =
                andereAanvang.getWaarde() >= datumAanvangGeldigheid.getWaarde()
                        && (datumEindeGeldigheid == null || datumEindeGeldigheid.getWaarde() > andereAanvang.getWaarde());
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
                                  .append(datumTijdVerval, castOther.datumTijdVerval)
                                  .append(nadereAanduidingVerval, castOther.nadereAanduidingVerval)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumAanvangGeldigheid)
                                    .append(datumEindeGeldigheid)
                                    .append(datumTijdRegistratie)
                                    .append(datumTijdVerval)
                                    .append(nadereAanduidingVerval)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("datumAanvangGeldigheid", datumAanvangGeldigheid)
                                                                          .append("datumEindeGeldigheid", datumEindeGeldigheid)
                                                                          .append("datumTijdRegistratie", datumTijdRegistratie)
                                                                          .append("datumTijdVerval", datumTijdVerval)
                                                                          .append("nadereAanduidingVerval", nadereAanduidingVerval)
                                                                          .toString();
    }

    /** Builder. */
    public static final class Builder {
        private BrpDatum datumAanvangGeldigheid;
        private BrpDatum datumEindeGeldigheid;
        private BrpDatumTijd datumTijdRegistratie;
        private BrpDatumTijd datumTijdVerval;
        private BrpCharacter nadereAanduidingVerval;

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
            nadereAanduidingVerval = inhoud.nadereAanduidingVerval;
        }

        /**
         * @return inhoud
         */
        public BrpHistorie build() {
            return new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);
        }

        /**
         * Zet de waarde van datum aanvang geldigheid.
         *
         * @param datumAanvangGeldigheid
         *            the datumAanvangGeldigheid to set
         */
        public void setDatumAanvangGeldigheid(final BrpDatum datumAanvangGeldigheid) {
            this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        }

        /**
         * Zet de waarde van datum einde geldigheid.
         *
         * @param datumEindeGeldigheid
         *            the datumEindeGeldigheid to set
         */
        public void setDatumEindeGeldigheid(final BrpDatum datumEindeGeldigheid) {
            this.datumEindeGeldigheid = datumEindeGeldigheid;
        }

        /**
         * Zet de waarde van datum tijd registratie.
         *
         * @param datumTijdRegistratie
         *            the datumTijdRegistratie to set
         */
        public void setDatumTijdRegistratie(final BrpDatumTijd datumTijdRegistratie) {
            this.datumTijdRegistratie = datumTijdRegistratie;
        }

        /**
         * Zet de waarde van datum tijd verval.
         *
         * @param datumTijdVerval
         *            the datumTijdVerval to set
         */
        public void setDatumTijdVerval(final BrpDatumTijd datumTijdVerval) {
            this.datumTijdVerval = datumTijdVerval;
        }

        /**
         * Zet de waarde van nadere aanduiding verval.
         *
         * @param nadereAanduidingVerval
         *            the nadereAanduidingVerval to set
         */
        public void setNadereAanduidingVerval(final BrpCharacter nadereAanduidingVerval) {
            this.nadereAanduidingVerval = nadereAanduidingVerval;
        }
    }
}
