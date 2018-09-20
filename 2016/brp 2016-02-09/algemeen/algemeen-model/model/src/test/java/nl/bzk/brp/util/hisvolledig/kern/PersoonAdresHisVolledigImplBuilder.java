/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Adres.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonAdresHisVolledigImplBuilder {

    private PersoonAdresHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Adres.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonAdresHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PersoonAdresHisVolledigImpl(persoon);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Adres.
     */
    public PersoonAdresHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        this(persoon, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonAdresHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PersoonAdresHisVolledigImpl(null);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public PersoonAdresHisVolledigImplBuilder() {
        this(null, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PersoonAdresHisVolledigImplBuilderStandaard nieuwStandaardRecord(
        final Integer datumAanvangGeldigheid,
        final Integer datumEindeGeldigheid,
        final Integer datumRegistratie)
    {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumAanvangGeldigheid != null) {
            actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumAanvangGeldigheid));
        }
        if (datumEindeGeldigheid != null) {
            actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(datumEindeGeldigheid));
        }
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwStandaardRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Standaard record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Standaard groep builder
     */
    public PersoonAdresHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonAdresHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonAdresHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonAdresHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PersoonAdresStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonAdresHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonAdresStandaardGroepBericht();
        }

        /**
         * Geef attribuut Soort een waarde.
         *
         * @param soort Soort van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard soort(final FunctieAdres soort) {
            this.bericht.setSoort(new FunctieAdresAttribuut(soort));
            return this;
        }

        /**
         * Geef attribuut Reden wijziging een waarde.
         *
         * @param code Reden wijziging van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard redenWijziging(final String code) {
            this.bericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenWijzigingVerblijf.class,
                new RedenWijzigingVerblijfCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Reden wijziging een waarde.
         *
         * @param code Reden wijziging van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard redenWijziging(final RedenWijzigingVerblijfCodeAttribuut code) {
            this.bericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenWijzigingVerblijf.class,
                code)));
            return this;
        }

        /**
         * Geef attribuut Reden wijziging een waarde.
         *
         * @param redenWijziging Reden wijziging van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard redenWijziging(final RedenWijzigingVerblijf redenWijziging) {
            this.bericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(redenWijziging));
            return this;
        }

        /**
         * Geef attribuut Aangever adreshouding een waarde.
         *
         * @param code Aangever adreshouding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard aangeverAdreshouding(final String code) {
            this.bericht.setAangeverAdreshouding(new AangeverAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                Aangever.class,
                new AangeverCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Aangever adreshouding een waarde.
         *
         * @param code Aangever adreshouding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard aangeverAdreshouding(final AangeverCodeAttribuut code) {
            this.bericht.setAangeverAdreshouding(new AangeverAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Aangever.class, code)));
            return this;
        }

        /**
         * Geef attribuut Aangever adreshouding een waarde.
         *
         * @param aangeverAdreshouding Aangever adreshouding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard aangeverAdreshouding(final Aangever aangeverAdreshouding) {
            this.bericht.setAangeverAdreshouding(new AangeverAttribuut(aangeverAdreshouding));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang adreshouding een waarde.
         *
         * @param datumAanvangAdreshouding Datum aanvang adreshouding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard datumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
            this.bericht.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(datumAanvangAdreshouding));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang adreshouding een waarde.
         *
         * @param datumAanvangAdreshouding Datum aanvang adreshouding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard datumAanvangAdreshouding(final DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshouding) {
            this.bericht.setDatumAanvangAdreshouding(datumAanvangAdreshouding);
            return this;
        }

        /**
         * Geef attribuut Identificatiecode adresseerbaar object een waarde.
         *
         * @param identificatiecodeAdresseerbaarObject Identificatiecode adresseerbaar object van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard identificatiecodeAdresseerbaarObject(final String identificatiecodeAdresseerbaarObject) {
            this.bericht.setIdentificatiecodeAdresseerbaarObject(new IdentificatiecodeAdresseerbaarObjectAttribuut(identificatiecodeAdresseerbaarObject));
            return this;
        }

        /**
         * Geef attribuut Identificatiecode adresseerbaar object een waarde.
         *
         * @param identificatiecodeAdresseerbaarObject Identificatiecode adresseerbaar object van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard identificatiecodeAdresseerbaarObject(
            final IdentificatiecodeAdresseerbaarObjectAttribuut identificatiecodeAdresseerbaarObject)
        {
            this.bericht.setIdentificatiecodeAdresseerbaarObject(identificatiecodeAdresseerbaarObject);
            return this;
        }

        /**
         * Geef attribuut Identificatiecode nummeraanduiding een waarde.
         *
         * @param identificatiecodeNummeraanduiding Identificatiecode nummeraanduiding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard identificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
            this.bericht.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduidingAttribuut(identificatiecodeNummeraanduiding));
            return this;
        }

        /**
         * Geef attribuut Identificatiecode nummeraanduiding een waarde.
         *
         * @param identificatiecodeNummeraanduiding Identificatiecode nummeraanduiding van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard identificatiecodeNummeraanduiding(
            final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding)
        {
            this.bericht.setIdentificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
            return this;
        }

        /**
         * Geef attribuut Gemeente een waarde.
         *
         * @param code Gemeente van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard gemeente(final Short code) {
            this.bericht.setGemeente(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, new GemeenteCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Gemeente een waarde.
         *
         * @param code Gemeente van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard gemeente(final GemeenteCodeAttribuut code) {
            this.bericht.setGemeente(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, code)));
            return this;
        }

        /**
         * Geef attribuut Gemeente een waarde.
         *
         * @param gemeente Gemeente van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard gemeente(final Gemeente gemeente) {
            this.bericht.setGemeente(new GemeenteAttribuut(gemeente));
            return this;
        }

        /**
         * Geef attribuut Naam openbare ruimte een waarde.
         *
         * @param naamOpenbareRuimte Naam openbare ruimte van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard naamOpenbareRuimte(final String naamOpenbareRuimte) {
            this.bericht.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut(naamOpenbareRuimte));
            return this;
        }

        /**
         * Geef attribuut Naam openbare ruimte een waarde.
         *
         * @param naamOpenbareRuimte Naam openbare ruimte van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard naamOpenbareRuimte(final NaamOpenbareRuimteAttribuut naamOpenbareRuimte) {
            this.bericht.setNaamOpenbareRuimte(naamOpenbareRuimte);
            return this;
        }

        /**
         * Geef attribuut Afgekorte naam openbare ruimte een waarde.
         *
         * @param afgekorteNaamOpenbareRuimte Afgekorte naam openbare ruimte van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard afgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
            this.bericht.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut(afgekorteNaamOpenbareRuimte));
            return this;
        }

        /**
         * Geef attribuut Afgekorte naam openbare ruimte een waarde.
         *
         * @param afgekorteNaamOpenbareRuimte Afgekorte naam openbare ruimte van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard afgekorteNaamOpenbareRuimte(
            final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte)
        {
            this.bericht.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
            return this;
        }

        /**
         * Geef attribuut Gemeentedeel een waarde.
         *
         * @param gemeentedeel Gemeentedeel van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard gemeentedeel(final String gemeentedeel) {
            this.bericht.setGemeentedeel(new GemeentedeelAttribuut(gemeentedeel));
            return this;
        }

        /**
         * Geef attribuut Gemeentedeel een waarde.
         *
         * @param gemeentedeel Gemeentedeel van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard gemeentedeel(final GemeentedeelAttribuut gemeentedeel) {
            this.bericht.setGemeentedeel(gemeentedeel);
            return this;
        }

        /**
         * Geef attribuut Huisnummer een waarde.
         *
         * @param huisnummer Huisnummer van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard huisnummer(final Integer huisnummer) {
            this.bericht.setHuisnummer(new HuisnummerAttribuut(huisnummer));
            return this;
        }

        /**
         * Geef attribuut Huisnummer een waarde.
         *
         * @param huisnummer Huisnummer van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard huisnummer(final HuisnummerAttribuut huisnummer) {
            this.bericht.setHuisnummer(huisnummer);
            return this;
        }

        /**
         * Geef attribuut Huisletter een waarde.
         *
         * @param huisletter Huisletter van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard huisletter(final String huisletter) {
            this.bericht.setHuisletter(new HuisletterAttribuut(huisletter));
            return this;
        }

        /**
         * Geef attribuut Huisletter een waarde.
         *
         * @param huisletter Huisletter van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard huisletter(final HuisletterAttribuut huisletter) {
            this.bericht.setHuisletter(huisletter);
            return this;
        }

        /**
         * Geef attribuut Huisnummertoevoeging een waarde.
         *
         * @param huisnummertoevoeging Huisnummertoevoeging van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard huisnummertoevoeging(final String huisnummertoevoeging) {
            this.bericht.setHuisnummertoevoeging(new HuisnummertoevoegingAttribuut(huisnummertoevoeging));
            return this;
        }

        /**
         * Geef attribuut Huisnummertoevoeging een waarde.
         *
         * @param huisnummertoevoeging Huisnummertoevoeging van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard huisnummertoevoeging(final HuisnummertoevoegingAttribuut huisnummertoevoeging) {
            this.bericht.setHuisnummertoevoeging(huisnummertoevoeging);
            return this;
        }

        /**
         * Geef attribuut Postcode een waarde.
         *
         * @param postcode Postcode van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard postcode(final String postcode) {
            this.bericht.setPostcode(new PostcodeAttribuut(postcode));
            return this;
        }

        /**
         * Geef attribuut Postcode een waarde.
         *
         * @param postcode Postcode van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard postcode(final PostcodeAttribuut postcode) {
            this.bericht.setPostcode(postcode);
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam een waarde.
         *
         * @param woonplaatsnaam Woonplaatsnaam van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard woonplaatsnaam(final String woonplaatsnaam) {
            this.bericht.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(woonplaatsnaam));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam een waarde.
         *
         * @param woonplaatsnaam Woonplaatsnaam van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard woonplaatsnaam(final NaamEnumeratiewaardeAttribuut woonplaatsnaam) {
            this.bericht.setWoonplaatsnaam(woonplaatsnaam);
            return this;
        }

        /**
         * Geef attribuut Locatie ten opzichte van adres een waarde.
         *
         * @param locatieTenOpzichteVanAdres Locatie ten opzichte van adres van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard locatieTenOpzichteVanAdres(final LocatieTenOpzichteVanAdres locatieTenOpzichteVanAdres) {
            this.bericht.setLocatieTenOpzichteVanAdres(new LocatieTenOpzichteVanAdresAttribuut(locatieTenOpzichteVanAdres));
            return this;
        }

        /**
         * Geef attribuut Locatieomschrijving een waarde.
         *
         * @param locatieomschrijving Locatieomschrijving van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard locatieomschrijving(final String locatieomschrijving) {
            this.bericht.setLocatieomschrijving(new LocatieomschrijvingAttribuut(locatieomschrijving));
            return this;
        }

        /**
         * Geef attribuut Locatieomschrijving een waarde.
         *
         * @param locatieomschrijving Locatieomschrijving van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard locatieomschrijving(final LocatieomschrijvingAttribuut locatieomschrijving) {
            this.bericht.setLocatieomschrijving(locatieomschrijving);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 1 een waarde.
         *
         * @param buitenlandsAdresRegel1 Buitenlands adres regel 1 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
            this.bericht.setBuitenlandsAdresRegel1(new AdresregelAttribuut(buitenlandsAdresRegel1));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 1 een waarde.
         *
         * @param buitenlandsAdresRegel1 Buitenlands adres regel 1 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel1(final AdresregelAttribuut buitenlandsAdresRegel1) {
            this.bericht.setBuitenlandsAdresRegel1(buitenlandsAdresRegel1);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 2 een waarde.
         *
         * @param buitenlandsAdresRegel2 Buitenlands adres regel 2 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
            this.bericht.setBuitenlandsAdresRegel2(new AdresregelAttribuut(buitenlandsAdresRegel2));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 2 een waarde.
         *
         * @param buitenlandsAdresRegel2 Buitenlands adres regel 2 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel2(final AdresregelAttribuut buitenlandsAdresRegel2) {
            this.bericht.setBuitenlandsAdresRegel2(buitenlandsAdresRegel2);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 3 een waarde.
         *
         * @param buitenlandsAdresRegel3 Buitenlands adres regel 3 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
            this.bericht.setBuitenlandsAdresRegel3(new AdresregelAttribuut(buitenlandsAdresRegel3));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 3 een waarde.
         *
         * @param buitenlandsAdresRegel3 Buitenlands adres regel 3 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel3(final AdresregelAttribuut buitenlandsAdresRegel3) {
            this.bericht.setBuitenlandsAdresRegel3(buitenlandsAdresRegel3);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 4 een waarde.
         *
         * @param buitenlandsAdresRegel4 Buitenlands adres regel 4 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
            this.bericht.setBuitenlandsAdresRegel4(new AdresregelAttribuut(buitenlandsAdresRegel4));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 4 een waarde.
         *
         * @param buitenlandsAdresRegel4 Buitenlands adres regel 4 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel4(final AdresregelAttribuut buitenlandsAdresRegel4) {
            this.bericht.setBuitenlandsAdresRegel4(buitenlandsAdresRegel4);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 5 een waarde.
         *
         * @param buitenlandsAdresRegel5 Buitenlands adres regel 5 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
            this.bericht.setBuitenlandsAdresRegel5(new AdresregelAttribuut(buitenlandsAdresRegel5));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 5 een waarde.
         *
         * @param buitenlandsAdresRegel5 Buitenlands adres regel 5 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel5(final AdresregelAttribuut buitenlandsAdresRegel5) {
            this.bericht.setBuitenlandsAdresRegel5(buitenlandsAdresRegel5);
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 6 een waarde.
         *
         * @param buitenlandsAdresRegel6 Buitenlands adres regel 6 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
            this.bericht.setBuitenlandsAdresRegel6(new AdresregelAttribuut(buitenlandsAdresRegel6));
            return this;
        }

        /**
         * Geef attribuut Buitenlands adres regel 6 een waarde.
         *
         * @param buitenlandsAdresRegel6 Buitenlands adres regel 6 van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard buitenlandsAdresRegel6(final AdresregelAttribuut buitenlandsAdresRegel6) {
            this.bericht.setBuitenlandsAdresRegel6(buitenlandsAdresRegel6);
            return this;
        }

        /**
         * Geef attribuut Land/gebied een waarde.
         *
         * @param code Land/gebied van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard landGebied(final Short code) {
            this.bericht.setLandGebied(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, new LandGebiedCodeAttribuut(
                code))));
            return this;
        }

        /**
         * Geef attribuut Land/gebied een waarde.
         *
         * @param code Land/gebied van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard landGebied(final LandGebiedCodeAttribuut code) {
            this.bericht.setLandGebied(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, code)));
            return this;
        }

        /**
         * Geef attribuut Land/gebied een waarde.
         *
         * @param landGebied Land/gebied van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard landGebied(final LandGebied landGebied) {
            this.bericht.setLandGebied(new LandGebiedAttribuut(landGebied));
            return this;
        }

        /**
         * Geef attribuut Persoon aangetroffen op adres? een waarde.
         *
         * @param indicatiePersoonAangetroffenOpAdres Persoon aangetroffen op adres? van Standaard
         * @return de groep builder
         */
        public PersoonAdresHisVolledigImplBuilderStandaard indicatiePersoonAangetroffenOpAdres(final Nee indicatiePersoonAangetroffenOpAdres) {
            this.bericht.setIndicatiePersoonAangetroffenOpAdres(new NeeAttribuut(indicatiePersoonAangetroffenOpAdres));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonAdresHisVolledigImplBuilder eindeRecord() {
            final HisPersoonAdresModel record = new HisPersoonAdresModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAdresHistorie().voegToe(record);
            return PersoonAdresHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonAdresHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonAdresModel record = new HisPersoonAdresModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAdresHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonAdresHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonAdresModel record) {
            if (record.getSoort() != null) {
                record.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getRedenWijziging() != null) {
                record.getRedenWijziging().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAangeverAdreshouding() != null) {
                record.getAangeverAdreshouding().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumAanvangAdreshouding() != null) {
                record.getDatumAanvangAdreshouding().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIdentificatiecodeAdresseerbaarObject() != null) {
                record.getIdentificatiecodeAdresseerbaarObject().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIdentificatiecodeNummeraanduiding() != null) {
                record.getIdentificatiecodeNummeraanduiding().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGemeente() != null) {
                record.getGemeente().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getNaamOpenbareRuimte() != null) {
                record.getNaamOpenbareRuimte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAfgekorteNaamOpenbareRuimte() != null) {
                record.getAfgekorteNaamOpenbareRuimte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGemeentedeel() != null) {
                record.getGemeentedeel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getHuisnummer() != null) {
                record.getHuisnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getHuisletter() != null) {
                record.getHuisletter().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getHuisnummertoevoeging() != null) {
                record.getHuisnummertoevoeging().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getPostcode() != null) {
                record.getPostcode().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getWoonplaatsnaam() != null) {
                record.getWoonplaatsnaam().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLocatieTenOpzichteVanAdres() != null) {
                record.getLocatieTenOpzichteVanAdres().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLocatieomschrijving() != null) {
                record.getLocatieomschrijving().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel1() != null) {
                record.getBuitenlandsAdresRegel1().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel2() != null) {
                record.getBuitenlandsAdresRegel2().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel3() != null) {
                record.getBuitenlandsAdresRegel3().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel4() != null) {
                record.getBuitenlandsAdresRegel4().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel5() != null) {
                record.getBuitenlandsAdresRegel5().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsAdresRegel6() != null) {
                record.getBuitenlandsAdresRegel6().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLandGebied() != null) {
                record.getLandGebied().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatiePersoonAangetroffenOpAdres() != null) {
                record.getIndicatiePersoonAangetroffenOpAdres().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
