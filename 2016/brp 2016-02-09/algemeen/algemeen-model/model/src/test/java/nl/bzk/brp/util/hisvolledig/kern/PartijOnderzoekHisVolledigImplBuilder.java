/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoekAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PartijOnderzoekStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartijOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Partij \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PartijOnderzoekHisVolledigImplBuilder {

    private PartijOnderzoekHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param partij partij van Partij \ Onderzoek.
     * @param onderzoek onderzoek van Partij \ Onderzoek.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PartijOnderzoekHisVolledigImplBuilder(
        final Partij partij,
        final OnderzoekHisVolledigImpl onderzoek,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PartijOnderzoekHisVolledigImpl(new PartijAttribuut(partij), onderzoek);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Onderzoek.
     * @param onderzoek onderzoek van Partij \ Onderzoek.
     */
    public PartijOnderzoekHisVolledigImplBuilder(final Partij partij, final OnderzoekHisVolledigImpl onderzoek) {
        this(partij, onderzoek, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param partij partij van Partij \ Onderzoek.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PartijOnderzoekHisVolledigImplBuilder(final Partij partij, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PartijOnderzoekHisVolledigImpl(new PartijAttribuut(partij), null);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Onderzoek.
     */
    public PartijOnderzoekHisVolledigImplBuilder(final Partij partij) {
        this(partij, null, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PartijOnderzoekHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
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
    public PartijOnderzoekHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PartijOnderzoekHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PartijOnderzoekHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PartijOnderzoekHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PartijOnderzoekStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PartijOnderzoekHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PartijOnderzoekStandaardGroepBericht();
        }

        /**
         * Geef attribuut Rol een waarde.
         *
         * @param rol Rol van Standaard
         * @return de groep builder
         */
        public PartijOnderzoekHisVolledigImplBuilderStandaard rol(final SoortPartijOnderzoek rol) {
            this.bericht.setRol(new SoortPartijOnderzoekAttribuut(rol));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PartijOnderzoekHisVolledigImplBuilder eindeRecord() {
            final HisPartijOnderzoekModel record = new HisPartijOnderzoekModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPartijOnderzoekHistorie().voegToe(record);
            return PartijOnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PartijOnderzoekHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPartijOnderzoekModel record = new HisPartijOnderzoekModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPartijOnderzoekHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PartijOnderzoekHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPartijOnderzoekModel record) {
            if (record.getRol() != null) {
                record.getRol().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
