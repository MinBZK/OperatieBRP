/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Reisdocument.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonReisdocumentHisVolledigImplBuilder {

    private PersoonReisdocumentHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Reisdocument.
     * @param soort soort van Persoon \ Reisdocument.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonReisdocumentHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final SoortNederlandsReisdocument soort,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonReisdocumentHisVolledigImpl(persoon, new SoortNederlandsReisdocumentAttribuut(soort));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Reisdocument.
     * @param soort soort van Persoon \ Reisdocument.
     */
    public PersoonReisdocumentHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final SoortNederlandsReisdocument soort) {
        this(persoon, soort, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param soort soort van Persoon \ Reisdocument.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonReisdocumentHisVolledigImplBuilder(final SoortNederlandsReisdocument soort, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PersoonReisdocumentHisVolledigImpl(null, new SoortNederlandsReisdocumentAttribuut(soort));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon \ Reisdocument.
     */
    public PersoonReisdocumentHisVolledigImplBuilder(final SoortNederlandsReisdocument soort) {
        this(null, soort, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PersoonReisdocumentHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
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
    public PersoonReisdocumentHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonReisdocumentHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonReisdocumentHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonReisdocumentHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PersoonReisdocumentStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonReisdocumentStandaardGroepBericht();
        }

        /**
         * Geef attribuut Nummer een waarde.
         *
         * @param nummer Nummer van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard nummer(final String nummer) {
            this.bericht.setNummer(new ReisdocumentNummerAttribuut(nummer));
            return this;
        }

        /**
         * Geef attribuut Nummer een waarde.
         *
         * @param nummer Nummer van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard nummer(final ReisdocumentNummerAttribuut nummer) {
            this.bericht.setNummer(nummer);
            return this;
        }

        /**
         * Geef attribuut Autoriteit van afgifte een waarde.
         *
         * @param autoriteitVanAfgifte Autoriteit van afgifte van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard autoriteitVanAfgifte(final String autoriteitVanAfgifte) {
            this.bericht.setAutoriteitVanAfgifte(new AutoriteitVanAfgifteReisdocumentCodeAttribuut(autoriteitVanAfgifte));
            return this;
        }

        /**
         * Geef attribuut Autoriteit van afgifte een waarde.
         *
         * @param autoriteitVanAfgifte Autoriteit van afgifte van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard autoriteitVanAfgifte(
            final AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte)
        {
            this.bericht.setAutoriteitVanAfgifte(autoriteitVanAfgifte);
            return this;
        }

        /**
         * Geef attribuut Datum ingang document een waarde.
         *
         * @param datumIngangDocument Datum ingang document van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumIngangDocument(final Integer datumIngangDocument) {
            this.bericht.setDatumIngangDocument(new DatumEvtDeelsOnbekendAttribuut(datumIngangDocument));
            return this;
        }

        /**
         * Geef attribuut Datum ingang document een waarde.
         *
         * @param datumIngangDocument Datum ingang document van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumIngangDocument(final DatumEvtDeelsOnbekendAttribuut datumIngangDocument) {
            this.bericht.setDatumIngangDocument(datumIngangDocument);
            return this;
        }

        /**
         * Geef attribuut Datum einde document een waarde.
         *
         * @param datumEindeDocument Datum einde document van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumEindeDocument(final Integer datumEindeDocument) {
            this.bericht.setDatumEindeDocument(new DatumEvtDeelsOnbekendAttribuut(datumEindeDocument));
            return this;
        }

        /**
         * Geef attribuut Datum einde document een waarde.
         *
         * @param datumEindeDocument Datum einde document van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumEindeDocument(final DatumEvtDeelsOnbekendAttribuut datumEindeDocument) {
            this.bericht.setDatumEindeDocument(datumEindeDocument);
            return this;
        }

        /**
         * Geef attribuut Datum uitgifte een waarde.
         *
         * @param datumUitgifte Datum uitgifte van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumUitgifte(final Integer datumUitgifte) {
            this.bericht.setDatumUitgifte(new DatumEvtDeelsOnbekendAttribuut(datumUitgifte));
            return this;
        }

        /**
         * Geef attribuut Datum uitgifte een waarde.
         *
         * @param datumUitgifte Datum uitgifte van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumUitgifte(final DatumEvtDeelsOnbekendAttribuut datumUitgifte) {
            this.bericht.setDatumUitgifte(datumUitgifte);
            return this;
        }

        /**
         * Geef attribuut Datum inhouding/vermissing een waarde.
         *
         * @param datumInhoudingVermissing Datum inhouding/vermissing van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumInhoudingVermissing(final Integer datumInhoudingVermissing) {
            this.bericht.setDatumInhoudingVermissing(new DatumEvtDeelsOnbekendAttribuut(datumInhoudingVermissing));
            return this;
        }

        /**
         * Geef attribuut Datum inhouding/vermissing een waarde.
         *
         * @param datumInhoudingVermissing Datum inhouding/vermissing van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard datumInhoudingVermissing(final DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing) {
            this.bericht.setDatumInhoudingVermissing(datumInhoudingVermissing);
            return this;
        }

        /**
         * Geef attribuut Aanduiding inhouding/vermissing een waarde.
         *
         * @param code Aanduiding inhouding/vermissing van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard aanduidingInhoudingVermissing(final String code) {
            this.bericht.setAanduidingInhoudingVermissing(new AanduidingInhoudingVermissingReisdocumentAttribuut(
                StamgegevenBuilder.bouwDynamischStamgegeven(
                    AanduidingInhoudingVermissingReisdocument.class,
                    new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Aanduiding inhouding/vermissing een waarde.
         *
         * @param code Aanduiding inhouding/vermissing van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard aanduidingInhoudingVermissing(
            final AanduidingInhoudingVermissingReisdocumentCodeAttribuut code)
        {
            this.bericht.setAanduidingInhoudingVermissing(new AanduidingInhoudingVermissingReisdocumentAttribuut(
                StamgegevenBuilder.bouwDynamischStamgegeven(AanduidingInhoudingVermissingReisdocument.class, code)));
            return this;
        }

        /**
         * Geef attribuut Aanduiding inhouding/vermissing een waarde.
         *
         * @param aanduidingInhoudingVermissing Aanduiding inhouding/vermissing van Standaard
         * @return de groep builder
         */
        public PersoonReisdocumentHisVolledigImplBuilderStandaard aanduidingInhoudingVermissing(
            final AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissing)
        {
            this.bericht.setAanduidingInhoudingVermissing(new AanduidingInhoudingVermissingReisdocumentAttribuut(aanduidingInhoudingVermissing));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonReisdocumentHisVolledigImplBuilder eindeRecord() {
            final HisPersoonReisdocumentModel record = new HisPersoonReisdocumentModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonReisdocumentHistorie().voegToe(record);
            return PersoonReisdocumentHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonReisdocumentHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonReisdocumentModel record = new HisPersoonReisdocumentModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonReisdocumentHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonReisdocumentHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonReisdocumentModel record) {
            if (record.getNummer() != null) {
                record.getNummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAutoriteitVanAfgifte() != null) {
                record.getAutoriteitVanAfgifte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumIngangDocument() != null) {
                record.getDatumIngangDocument().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumEindeDocument() != null) {
                record.getDatumEindeDocument().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumUitgifte() != null) {
                record.getDatumUitgifte().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumInhoudingVermissing() != null) {
                record.getDatumInhoudingVermissing().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAanduidingInhoudingVermissing() != null) {
                record.getAanduidingInhoudingVermissing().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
