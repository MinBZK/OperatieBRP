/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Afnemerindicatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonAfnemerindicatieHisVolledigImplBuilder {

    private PersoonAfnemerindicatieHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Afnemerindicatie.
     * @param afnemer afnemer van Persoon \ Afnemerindicatie.
     * @param leveringsautorisatie leveringsautorisatie van Persoon \ Afnemerindicatie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonAfnemerindicatieHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final Partij afnemer,
        final Leveringsautorisatie leveringsautorisatie,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new PersoonAfnemerindicatieHisVolledigImpl(persoon, new PartijAttribuut(afnemer), new LeveringsautorisatieAttribuut(leveringsautorisatie));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getAfnemer() != null) {
            hisVolledigImpl.getAfnemer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLeveringsautorisatie() != null) {
            hisVolledigImpl.getLeveringsautorisatie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Afnemerindicatie.
     * @param afnemer afnemer van Persoon \ Afnemerindicatie.
     * @param leveringsautorisatie leveringsautorisatie van Persoon \ Afnemerindicatie.
     */
    public PersoonAfnemerindicatieHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final Partij afnemer,
        final Leveringsautorisatie leveringsautorisatie)
    {
        this(persoon, afnemer, leveringsautorisatie, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param afnemer afnemer van Persoon \ Afnemerindicatie.
     * @param leveringsautorisatie leveringsautorisatie van Persoon \ Afnemerindicatie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonAfnemerindicatieHisVolledigImplBuilder(
        final Partij afnemer,
        final Leveringsautorisatie leveringsautorisatie,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new PersoonAfnemerindicatieHisVolledigImpl(null, new PartijAttribuut(afnemer), new LeveringsautorisatieAttribuut(leveringsautorisatie));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getAfnemer() != null) {
            hisVolledigImpl.getAfnemer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLeveringsautorisatie() != null) {
            hisVolledigImpl.getLeveringsautorisatie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param afnemer afnemer van Persoon \ Afnemerindicatie.
     * @param leveringsautorisatie leveringsautorisatie van Persoon \ Afnemerindicatie.
     */
    public PersoonAfnemerindicatieHisVolledigImplBuilder(final Partij afnemer, final Leveringsautorisatie leveringsautorisatie) {
        this(null, afnemer, leveringsautorisatie, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van een actie.
     *
     * @param dienst dienst
     * @return Standaard groep builder
     */
    public PersoonAfnemerindicatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Dienst dienst) {
        return new PersoonAfnemerindicatieHisVolledigImplBuilderStandaard(dienst);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonAfnemerindicatieHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonAfnemerindicatieHisVolledigImplBuilderStandaard {

        private Dienst                                       dienst;
        private PersoonAfnemerindicatieStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param dienst dienst
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilderStandaard(final Dienst dienst) {
            this.dienst = dienst;
            this.bericht = new PersoonAfnemerindicatieStandaardGroepBericht();
        }

        /**
         * Geef attribuut Datum aanvang materi�le periode een waarde.
         *
         * @param datumAanvangMaterielePeriode Datum aanvang materi�le periode van Standaard
         * @return de groep builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilderStandaard datumAanvangMaterielePeriode(final Integer datumAanvangMaterielePeriode) {
            this.bericht.setDatumAanvangMaterielePeriode(new DatumEvtDeelsOnbekendAttribuut(datumAanvangMaterielePeriode));
            return this;
        }

        /**
         * Geef attribuut Datum aanvang materi�le periode een waarde.
         *
         * @param datumAanvangMaterielePeriode Datum aanvang materi�le periode van Standaard
         * @return de groep builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilderStandaard datumAanvangMaterielePeriode(
            final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode)
        {
            this.bericht.setDatumAanvangMaterielePeriode(datumAanvangMaterielePeriode);
            return this;
        }

        /**
         * Geef attribuut Datum einde volgen een waarde.
         *
         * @param datumEindeVolgen Datum einde volgen van Standaard
         * @return de groep builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilderStandaard datumEindeVolgen(final Integer datumEindeVolgen) {
            this.bericht.setDatumEindeVolgen(new DatumAttribuut(datumEindeVolgen));
            return this;
        }

        /**
         * Geef attribuut Datum einde volgen een waarde.
         *
         * @param datumEindeVolgen Datum einde volgen van Standaard
         * @return de groep builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilderStandaard datumEindeVolgen(final DatumAttribuut datumEindeVolgen) {
            this.bericht.setDatumEindeVolgen(datumEindeVolgen);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilder eindeRecord() {
            HisPersoonAfnemerindicatieModel record = new HisPersoonAfnemerindicatieModel(hisVolledigImpl, bericht, dienst, DatumTijdAttribuut.nu());
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAfnemerindicatieHistorie().voegToe(record);
            return PersoonAfnemerindicatieHisVolledigImplBuilder.this;
        }

        /**
         * anjaw: Handmatige toevoeging voor funqmachine.
         *
         * Beeindig het record.
         *
         * @param tijdstipRegistratie tijdstip registratie
         * @return his volledig builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilder eindeRecord(final DatumTijdAttribuut tijdstipRegistratie) {
            HisPersoonAfnemerindicatieModel record = new HisPersoonAfnemerindicatieModel(hisVolledigImpl, bericht, dienst, tijdstipRegistratie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAfnemerindicatieHistorie().voegToe(record);
            return PersoonAfnemerindicatieHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonAfnemerindicatieHisVolledigImplBuilder eindeRecord(final Long id) {
            HisPersoonAfnemerindicatieModel record = new HisPersoonAfnemerindicatieModel(hisVolledigImpl, bericht, dienst, DatumTijdAttribuut.nu());
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonAfnemerindicatieHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonAfnemerindicatieHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonAfnemerindicatieModel record) {
            if (record.getDatumAanvangMaterielePeriode() != null) {
                record.getDatumAanvangMaterielePeriode().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getDatumEindeVolgen() != null) {
                record.getDatumEindeVolgen().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
