/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.OnderzoekAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.OnderzoekStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartijOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class OnderzoekHisVolledigImplBuilder {

    private OnderzoekHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public OnderzoekHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new OnderzoekHisVolledigImpl();
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public OnderzoekHisVolledigImplBuilder() {
        this(false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public OnderzoekHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
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
    public OnderzoekHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new OnderzoekHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Start een nieuw Afgeleid administratief record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Afgeleid administratief groep builder
     */
    public OnderzoekHisVolledigImplBuilderAfgeleidAdministratief nieuwAfgeleidAdministratiefRecord(final Integer datumRegistratie) {
        final ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {
        };
        if (datumRegistratie != null) {
            actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(datumRegistratie).toDate()));
        }
        return nieuwAfgeleidAdministratiefRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Afgeleid administratief record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Afgeleid administratief groep builder
     */
    public OnderzoekHisVolledigImplBuilderAfgeleidAdministratief nieuwAfgeleidAdministratiefRecord(final ActieModel actie) {
        return new OnderzoekHisVolledigImplBuilderAfgeleidAdministratief(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public OnderzoekHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Gegeven in onderzoek toe. Zet tevens de back-reference van Gegeven in onderzoek.
     *
     * @param gegevenInOnderzoek een Gegeven in onderzoek
     * @return his volledig builder
     */
    public OnderzoekHisVolledigImplBuilder voegGegevenInOnderzoekToe(final GegevenInOnderzoekHisVolledigImpl gegevenInOnderzoek) {
        this.hisVolledigImpl.getGegevensInOnderzoek().add(gegevenInOnderzoek);
        ReflectionTestUtils.setField(gegevenInOnderzoek, "onderzoek", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Persoon \ Onderzoek toe. Zet tevens de back-reference van Persoon \ Onderzoek.
     *
     * @param persoonOnderzoek een Persoon \ Onderzoek
     * @return his volledig builder
     */
    public OnderzoekHisVolledigImplBuilder voegPersoonOnderzoekToe(final PersoonOnderzoekHisVolledigImpl persoonOnderzoek) {
        this.hisVolledigImpl.getPersonenInOnderzoek().add(persoonOnderzoek);
        ReflectionTestUtils.setField(persoonOnderzoek, "onderzoek", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Partij \ Onderzoek toe. Zet tevens de back-reference van Partij \ Onderzoek.
     *
     * @param partijOnderzoek een Partij \ Onderzoek
     * @return his volledig builder
     */
    public OnderzoekHisVolledigImplBuilder voegPartijOnderzoekToe(final PartijOnderzoekHisVolledigImpl partijOnderzoek) {
        this.hisVolledigImpl.getPartijenInOnderzoek().add(partijOnderzoek);
        ReflectionTestUtils.setField(partijOnderzoek, "onderzoek", this.hisVolledigImpl);
        return this;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class OnderzoekHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private OnderzoekStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public OnderzoekHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new OnderzoekStandaardGroepBericht();
        }

        /**
         * Geef attribuut Datum aanvang een waarde.
         *
         * @param datumAanvang Datum aanvang van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard datumAanvang(final Integer datumAanvang) {
            this.bericht.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang een waarde.
         *
         * @param datumAanvang Datum aanvang van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard datumAanvang(final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
            this.bericht.setDatumAanvang(datumAanvang);
            return this;
        }

        /**
         * Geef attribuut Verwachte afhandeldatum een waarde.
         *
         * @param verwachteAfhandeldatum Verwachte afhandeldatum van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard verwachteAfhandeldatum(final Integer verwachteAfhandeldatum) {
            this.bericht.setVerwachteAfhandeldatum(new DatumEvtDeelsOnbekendAttribuut(verwachteAfhandeldatum));
            return this;
        }

        /**
         * Geef attribuut Verwachte afhandeldatum een waarde.
         *
         * @param verwachteAfhandeldatum Verwachte afhandeldatum van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard verwachteAfhandeldatum(final DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum) {
            this.bericht.setVerwachteAfhandeldatum(verwachteAfhandeldatum);
            return this;
        }

        /**
         * Geef attribuut Datum einde een waarde.
         *
         * @param datumEinde Datum einde van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard datumEinde(final Integer datumEinde) {
            this.bericht.setDatumEinde(new DatumEvtDeelsOnbekendAttribuut(datumEinde));
            return this;
        }

        /**
         * Geef attribuut Datum einde een waarde.
         *
         * @param datumEinde Datum einde van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard datumEinde(final DatumEvtDeelsOnbekendAttribuut datumEinde) {
            this.bericht.setDatumEinde(datumEinde);
            return this;
        }

        /**
         * Geef attribuut Omschrijving een waarde.
         *
         * @param omschrijving Omschrijving van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard omschrijving(final String omschrijving) {
            this.bericht.setOmschrijving(new OnderzoekOmschrijvingAttribuut(omschrijving));
            return this;
        }

        /**
         * Geef attribuut Omschrijving een waarde.
         *
         * @param omschrijving Omschrijving van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard omschrijving(final OnderzoekOmschrijvingAttribuut omschrijving) {
            this.bericht.setOmschrijving(omschrijving);
            return this;
        }

        /**
         * Geef attribuut Status een waarde.
         *
         * @param status Status van Standaard
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderStandaard status(final StatusOnderzoek status) {
            this.bericht.setStatus(new StatusOnderzoekAttribuut(status));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public OnderzoekHisVolledigImplBuilder eindeRecord() {
            final HisOnderzoekModel record = new HisOnderzoekModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getOnderzoekHistorie().voegToe(record);
            return OnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public OnderzoekHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisOnderzoekModel record = new HisOnderzoekModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getOnderzoekHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return OnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisOnderzoekModel record) {
            if (record.getDatumAanvang() != null) {
                record.getDatumAanvang().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVerwachteAfhandeldatum() != null) {
                record.getVerwachteAfhandeldatum().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumEinde() != null) {
                record.getDatumEinde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getOmschrijving() != null) {
                record.getOmschrijving().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getStatus() != null) {
                record.getStatus().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Afgeleid administratief
     *
     */
    public class OnderzoekHisVolledigImplBuilderAfgeleidAdministratief {

        private ActieModel actie;
        private OnderzoekAfgeleidAdministratiefGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public OnderzoekHisVolledigImplBuilderAfgeleidAdministratief(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new OnderzoekAfgeleidAdministratiefGroepBericht();
        }

        /**
         * Geef attribuut Administratieve handeling een waarde.
         *
         * @param administratieveHandeling Administratieve handeling van Afgeleid administratief
         * @return de groep builder
         */
        public OnderzoekHisVolledigImplBuilderAfgeleidAdministratief administratieveHandeling(
            final AdministratieveHandelingBericht administratieveHandeling)
        {
            this.bericht.setAdministratieveHandeling(administratieveHandeling);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public OnderzoekHisVolledigImplBuilder eindeRecord() {
            final HisOnderzoekAfgeleidAdministratiefModel record = new HisOnderzoekAfgeleidAdministratiefModel(hisVolledigImpl, bericht, actie);
            hisVolledigImpl.getOnderzoekAfgeleidAdministratiefHistorie().voegToe(record);
            return OnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public OnderzoekHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisOnderzoekAfgeleidAdministratiefModel record = new HisOnderzoekAfgeleidAdministratiefModel(hisVolledigImpl, bericht, actie);
            hisVolledigImpl.getOnderzoekAfgeleidAdministratiefHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return OnderzoekHisVolledigImplBuilder.this;
        }

    }

}
