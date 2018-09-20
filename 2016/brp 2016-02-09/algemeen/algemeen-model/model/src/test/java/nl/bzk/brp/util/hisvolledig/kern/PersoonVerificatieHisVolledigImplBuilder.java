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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerificatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerificatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Verificatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonVerificatieHisVolledigImplBuilder {

    private PersoonVerificatieHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param geverifieerde geverifieerde van Persoon \ Verificatie.
     * @param partij partij van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonVerificatieHisVolledigImplBuilder(
        final PersoonHisVolledigImpl geverifieerde,
        final Partij partij,
        final NaamEnumeratiewaardeAttribuut soort,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonVerificatieHisVolledigImpl(geverifieerde, new PartijAttribuut(partij), soort);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geverifieerde geverifieerde van Persoon \ Verificatie.
     * @param partij partij van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     */
    public PersoonVerificatieHisVolledigImplBuilder(
        final PersoonHisVolledigImpl geverifieerde,
        final Partij partij,
        final NaamEnumeratiewaardeAttribuut soort)
    {
        this(geverifieerde, partij, soort, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param partij partij van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonVerificatieHisVolledigImplBuilder(
        final Partij partij,
        final NaamEnumeratiewaardeAttribuut soort,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonVerificatieHisVolledigImpl(null, new PartijAttribuut(partij), soort);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     */
    public PersoonVerificatieHisVolledigImplBuilder(final Partij partij, final NaamEnumeratiewaardeAttribuut soort) {
        this(null, partij, soort, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PersoonVerificatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
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
    public PersoonVerificatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonVerificatieHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonVerificatieHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonVerificatieHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PersoonVerificatieStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonVerificatieHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonVerificatieStandaardGroepBericht();
        }

        /**
         * Geef attribuut Datum een waarde.
         *
         * @param datum Datum van Standaard
         * @return de groep builder
         */
        public PersoonVerificatieHisVolledigImplBuilderStandaard datum(final Integer datum) {
            this.bericht.setDatum(new DatumEvtDeelsOnbekendAttribuut(datum));
            return this;
        }

        /**
         * Geef attribuut Datum een waarde.
         *
         * @param datum Datum van Standaard
         * @return de groep builder
         */
        public PersoonVerificatieHisVolledigImplBuilderStandaard datum(final DatumEvtDeelsOnbekendAttribuut datum) {
            this.bericht.setDatum(datum);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonVerificatieHisVolledigImplBuilder eindeRecord() {
            final HisPersoonVerificatieModel record = new HisPersoonVerificatieModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonVerificatieHistorie().voegToe(record);
            return PersoonVerificatieHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonVerificatieHisVolledigImplBuilder eindeRecord(final Long id) {
            final HisPersoonVerificatieModel record = new HisPersoonVerificatieModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonVerificatieHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonVerificatieHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonVerificatieModel record) {
            if (record.getDatum() != null) {
                record.getDatum().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
