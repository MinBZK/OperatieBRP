/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.NaamskeuzeOngeborenVruchtHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Naamskeuze ongeboren vrucht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class NaamskeuzeOngeborenVruchtHisVolledigImplBuilder {

    private NaamskeuzeOngeborenVruchtHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public NaamskeuzeOngeborenVruchtHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new NaamskeuzeOngeborenVruchtHisVolledigImpl();
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public NaamskeuzeOngeborenVruchtHisVolledigImplBuilder() {
        this(false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
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
    public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public NaamskeuzeOngeborenVruchtHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private RelatieStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new RelatieStandaardGroepBericht();
        }

        /**
         * Geef attribuut Datum aanvang een waarde.
         *
         * @param datumAanvang Datum aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard datumAanvang(final Integer datumAanvang) {
            this.bericht.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang een waarde.
         *
         * @param datumAanvang Datum aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard datumAanvang(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
            this.bericht.setDatumAanvang(datumAanvang);
            return this;
        }

        /**
         * Geef attribuut Gemeente aanvang een waarde.
         *
         * @param code Gemeente aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard gemeenteAanvang(final Short code) {
            this.bericht.setGemeenteAanvang(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, new GemeenteCodeAttribuut(
                code))));
            return this;
        }

        /**
         * Geef attribuut Gemeente aanvang een waarde.
         *
         * @param code Gemeente aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard gemeenteAanvang(final GemeenteCodeAttribuut code) {
            this.bericht.setGemeenteAanvang(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, code)));
            return this;
        }

        /**
         * Geef attribuut Gemeente aanvang een waarde.
         *
         * @param gemeenteAanvang Gemeente aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard gemeenteAanvang(final Gemeente gemeenteAanvang) {
            this.bericht.setGemeenteAanvang(new GemeenteAttribuut(gemeenteAanvang));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam aanvang een waarde.
         *
         * @param woonplaatsnaamAanvang Woonplaatsnaam aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard woonplaatsnaamAanvang(final String woonplaatsnaamAanvang) {
            this.bericht.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(woonplaatsnaamAanvang));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam aanvang een waarde.
         *
         * @param woonplaatsnaamAanvang Woonplaatsnaam aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard woonplaatsnaamAanvang(final NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang) {
            this.bericht.setWoonplaatsnaamAanvang(woonplaatsnaamAanvang);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats aanvang een waarde.
         *
         * @param buitenlandsePlaatsAanvang Buitenlandse plaats aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
            this.bericht.setBuitenlandsePlaatsAanvang(new BuitenlandsePlaatsAttribuut(buitenlandsePlaatsAanvang));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats aanvang een waarde.
         *
         * @param buitenlandsePlaatsAanvang Buitenlandse plaats aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandsePlaatsAanvang(
            final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang)
        {
            this.bericht.setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio aanvang een waarde.
         *
         * @param buitenlandseRegioAanvang Buitenlandse regio aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
            this.bericht.setBuitenlandseRegioAanvang(new BuitenlandseRegioAttribuut(buitenlandseRegioAanvang));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio aanvang een waarde.
         *
         * @param buitenlandseRegioAanvang Buitenlandse regio aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandseRegioAanvang(final BuitenlandseRegioAttribuut buitenlandseRegioAanvang)
        {
            this.bericht.setBuitenlandseRegioAanvang(buitenlandseRegioAanvang);
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie aanvang een waarde.
         *
         * @param omschrijvingLocatieAanvang Omschrijving locatie aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard omschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
            this.bericht.setOmschrijvingLocatieAanvang(new LocatieomschrijvingAttribuut(omschrijvingLocatieAanvang));
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie aanvang een waarde.
         *
         * @param omschrijvingLocatieAanvang Omschrijving locatie aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard omschrijvingLocatieAanvang(
            final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang)
        {
            this.bericht.setOmschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
            return this;
        }

        /**
         * Geef attribuut Land/gebied aanvang een waarde.
         *
         * @param code Land/gebied aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard landGebiedAanvang(final Short code) {
            this.bericht.setLandGebiedAanvang(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                LandGebied.class,
                new LandGebiedCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Land/gebied aanvang een waarde.
         *
         * @param code Land/gebied aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard landGebiedAanvang(final LandGebiedCodeAttribuut code) {
            this.bericht.setLandGebiedAanvang(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, code)));
            return this;
        }

        /**
         * Geef attribuut Land/gebied aanvang een waarde.
         *
         * @param landGebiedAanvang Land/gebied aanvang van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard landGebiedAanvang(final LandGebied landGebiedAanvang) {
            this.bericht.setLandGebiedAanvang(new LandGebiedAttribuut(landGebiedAanvang));
            return this;
        }

        /**
         * Geef attribuut Reden einde een waarde.
         *
         * @param code Reden einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard redenEinde(final String code) {
            this.bericht.setRedenEinde(new RedenEindeRelatieAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenEindeRelatie.class,
                new RedenEindeRelatieCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Reden einde een waarde.
         *
         * @param code Reden einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard redenEinde(final RedenEindeRelatieCodeAttribuut code) {
            this.bericht.setRedenEinde(new RedenEindeRelatieAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(RedenEindeRelatie.class, code)));
            return this;
        }

        /**
         * Geef attribuut Reden einde een waarde.
         *
         * @param redenEinde Reden einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard redenEinde(final RedenEindeRelatie redenEinde) {
            this.bericht.setRedenEinde(new RedenEindeRelatieAttribuut(redenEinde));
            return this;
        }

        /**
         * Geef attribuut Datum einde een waarde.
         *
         * @param datumEinde Datum einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard datumEinde(final Integer datumEinde) {
            this.bericht.setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(datumEinde));
            return this;
        }

        /**
         * Geef attribuut Datum einde een waarde.
         *
         * @param datumEinde Datum einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard datumEinde(final DatumEvtDeelsOnbekendAttribuut datumEinde) {
            this.bericht.setDatumEinde(datumEinde);
            return this;
        }

        /**
         * Geef attribuut Gemeente einde een waarde.
         *
         * @param code Gemeente einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard gemeenteEinde(final Short code) {
            this.bericht.setGemeenteEinde(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                Gemeente.class,
                new GemeenteCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Gemeente einde een waarde.
         *
         * @param code Gemeente einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard gemeenteEinde(final GemeenteCodeAttribuut code) {
            this.bericht.setGemeenteEinde(new GemeenteAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Gemeente.class, code)));
            return this;
        }

        /**
         * Geef attribuut Gemeente einde een waarde.
         *
         * @param gemeenteEinde Gemeente einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard gemeenteEinde(final Gemeente gemeenteEinde) {
            this.bericht.setGemeenteEinde(new GemeenteAttribuut(gemeenteEinde));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam einde een waarde.
         *
         * @param woonplaatsnaamEinde Woonplaatsnaam einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard woonplaatsnaamEinde(final String woonplaatsnaamEinde) {
            this.bericht.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(woonplaatsnaamEinde));
            return this;
        }

        /**
         * Geef attribuut Woonplaatsnaam einde een waarde.
         *
         * @param woonplaatsnaamEinde Woonplaatsnaam einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard woonplaatsnaamEinde(final NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde) {
            this.bericht.setWoonplaatsnaamEinde(woonplaatsnaamEinde);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats einde een waarde.
         *
         * @param buitenlandsePlaatsEinde Buitenlandse plaats einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
            this.bericht.setBuitenlandsePlaatsEinde(new BuitenlandsePlaatsAttribuut(buitenlandsePlaatsEinde));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse plaats einde een waarde.
         *
         * @param buitenlandsePlaatsEinde Buitenlandse plaats einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandsePlaatsEinde(final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde)
        {
            this.bericht.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio einde een waarde.
         *
         * @param buitenlandseRegioEinde Buitenlandse regio einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandseRegioEinde(final String buitenlandseRegioEinde) {
            this.bericht.setBuitenlandseRegioEinde(new BuitenlandseRegioAttribuut(buitenlandseRegioEinde));
            return this;
        }

        /**
         * Geef attribuut Buitenlandse regio einde een waarde.
         *
         * @param buitenlandseRegioEinde Buitenlandse regio einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard buitenlandseRegioEinde(final BuitenlandseRegioAttribuut buitenlandseRegioEinde) {
            this.bericht.setBuitenlandseRegioEinde(buitenlandseRegioEinde);
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie einde een waarde.
         *
         * @param omschrijvingLocatieEinde Omschrijving locatie einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard omschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
            this.bericht.setOmschrijvingLocatieEinde(new LocatieomschrijvingAttribuut(omschrijvingLocatieEinde));
            return this;
        }

        /**
         * Geef attribuut Omschrijving locatie einde een waarde.
         *
         * @param omschrijvingLocatieEinde Omschrijving locatie einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard omschrijvingLocatieEinde(
            final LocatieomschrijvingAttribuut omschrijvingLocatieEinde)
        {
            this.bericht.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);
            return this;
        }

        /**
         * Geef attribuut Land/gebied einde een waarde.
         *
         * @param code Land/gebied einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard landGebiedEinde(final Short code) {
            this.bericht.setLandGebiedEinde(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                LandGebied.class,
                new LandGebiedCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Land/gebied einde een waarde.
         *
         * @param code Land/gebied einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard landGebiedEinde(final LandGebiedCodeAttribuut code) {
            this.bericht.setLandGebiedEinde(new LandGebiedAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(LandGebied.class, code)));
            return this;
        }

        /**
         * Geef attribuut Land/gebied einde een waarde.
         *
         * @param landGebiedEinde Land/gebied einde van Standaard
         * @return de groep builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilderStandaard landGebiedEinde(final LandGebied landGebiedEinde) {
            this.bericht.setLandGebiedEinde(new LandGebiedAttribuut(landGebiedEinde));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilder eindeRecord() {
            final HisRelatieModel record = new HisRelatieModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getRelatieHistorie().voegToe(record);
            return NaamskeuzeOngeborenVruchtHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public NaamskeuzeOngeborenVruchtHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisRelatieModel record = new HisRelatieModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getRelatieHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return NaamskeuzeOngeborenVruchtHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisRelatieModel record) {
            if (record.getDatumAanvang() != null) {
                record.getDatumAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGemeenteAanvang() != null) {
                record.getGemeenteAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getWoonplaatsnaamAanvang() != null) {
                record.getWoonplaatsnaamAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsePlaatsAanvang() != null) {
                record.getBuitenlandsePlaatsAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandseRegioAanvang() != null) {
                record.getBuitenlandseRegioAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getOmschrijvingLocatieAanvang() != null) {
                record.getOmschrijvingLocatieAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLandGebiedAanvang() != null) {
                record.getLandGebiedAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getRedenEinde() != null) {
                record.getRedenEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumEinde() != null) {
                record.getDatumEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getGemeenteEinde() != null) {
                record.getGemeenteEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getWoonplaatsnaamEinde() != null) {
                record.getWoonplaatsnaamEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandsePlaatsEinde() != null) {
                record.getBuitenlandsePlaatsEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getBuitenlandseRegioEinde() != null) {
                record.getBuitenlandseRegioEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getOmschrijvingLocatieEinde() != null) {
                record.getOmschrijvingLocatieEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getLandGebiedEinde() != null) {
                record.getLandGebiedEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
