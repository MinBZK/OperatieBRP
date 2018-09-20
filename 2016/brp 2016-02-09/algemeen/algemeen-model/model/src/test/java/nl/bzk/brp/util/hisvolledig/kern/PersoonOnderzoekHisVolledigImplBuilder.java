/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoekAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOnderzoekStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonOnderzoekHisVolledigImplBuilder {

    private PersoonOnderzoekHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     * @param onderzoek onderzoek van Persoon \ Onderzoek.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonOnderzoekHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final OnderzoekHisVolledigImpl onderzoek,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonOnderzoekHisVolledigImpl(persoon, onderzoek);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     * @param onderzoek onderzoek van Persoon \ Onderzoek.
     */
    public PersoonOnderzoekHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final OnderzoekHisVolledigImpl onderzoek) {
        this(persoon, onderzoek, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonOnderzoekHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PersoonOnderzoekHisVolledigImpl(persoon, null);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Onderzoek.
     */
    public PersoonOnderzoekHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        this(persoon, null, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PersoonOnderzoekHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
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
    public PersoonOnderzoekHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonOnderzoekHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonOnderzoekHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonOnderzoekHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PersoonOnderzoekStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonOnderzoekHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonOnderzoekStandaardGroepBericht();
        }

        /**
         * Geef attribuut Rol een waarde.
         *
         * @param rol Rol van Standaard
         * @return de groep builder
         */
        public PersoonOnderzoekHisVolledigImplBuilderStandaard rol(final SoortPersoonOnderzoek rol) {
            this.bericht.setRol(new SoortPersoonOnderzoekAttribuut(rol));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonOnderzoekHisVolledigImplBuilder eindeRecord() {
            final HisPersoonOnderzoekModel record = new HisPersoonOnderzoekModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonOnderzoekHistorie().voegToe(record);
            return PersoonOnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonOnderzoekHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonOnderzoekModel record = new HisPersoonOnderzoekModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonOnderzoekHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonOnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonOnderzoekModel record) {
            if (record.getRol() != null) {
                record.getRol().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
