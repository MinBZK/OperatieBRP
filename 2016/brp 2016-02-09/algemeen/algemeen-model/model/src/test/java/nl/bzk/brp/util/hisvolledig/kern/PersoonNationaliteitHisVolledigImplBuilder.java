/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Nationaliteit.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonNationaliteitHisVolledigImplBuilder {

    private PersoonNationaliteitHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Nationaliteit.
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonNationaliteitHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final Nationaliteit nationaliteit,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonNationaliteitHisVolledigImpl(persoon, new NationaliteitAttribuut(nationaliteit));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getNationaliteit() != null) {
            hisVolledigImpl.getNationaliteit().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Nationaliteit.
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     */
    public PersoonNationaliteitHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final Nationaliteit nationaliteit) {
        this(persoon, nationaliteit, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonNationaliteitHisVolledigImplBuilder(final Nationaliteit nationaliteit, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PersoonNationaliteitHisVolledigImpl(null, new NationaliteitAttribuut(nationaliteit));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getNationaliteit() != null) {
            hisVolledigImpl.getNationaliteit().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     */
    public PersoonNationaliteitHisVolledigImplBuilder(final Nationaliteit nationaliteit) {
        this(null, nationaliteit, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PersoonNationaliteitHisVolledigImplBuilderStandaard nieuwStandaardRecord(
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
    public PersoonNationaliteitHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonNationaliteitHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonNationaliteitHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonNationaliteitHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PersoonNationaliteitStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonNationaliteitStandaardGroepBericht();
        }

        /**
         * Geef attribuut Reden verkrijging een waarde.
         *
         * @param code Reden verkrijging van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard redenVerkrijging(final Short code) {
            this.bericht.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteitAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenVerkrijgingNLNationaliteit.class,
                new RedenVerkrijgingCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Reden verkrijging een waarde.
         *
         * @param code Reden verkrijging van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard redenVerkrijging(final RedenVerkrijgingCodeAttribuut code) {
            this.bericht.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteitAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenVerkrijgingNLNationaliteit.class,
                code)));
            return this;
        }

        /**
         * Geef attribuut Reden verkrijging een waarde.
         *
         * @param redenVerkrijging Reden verkrijging van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard redenVerkrijging(final RedenVerkrijgingNLNationaliteit redenVerkrijging) {
            this.bericht.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteitAttribuut(redenVerkrijging));
            return this;
        }

        /**
         * Geef attribuut Reden verlies een waarde.
         *
         * @param code Reden verlies van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard redenVerlies(final Short code) {
            this.bericht.setRedenVerlies(new RedenVerliesNLNationaliteitAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenVerliesNLNationaliteit.class,
                new RedenVerliesCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Reden verlies een waarde.
         *
         * @param code Reden verlies van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard redenVerlies(final RedenVerliesCodeAttribuut code) {
            this.bericht.setRedenVerlies(new RedenVerliesNLNationaliteitAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                RedenVerliesNLNationaliteit.class,
                code)));
            return this;
        }

        /**
         * Geef attribuut Reden verlies een waarde.
         *
         * @param redenVerlies Reden verlies van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard redenVerlies(final RedenVerliesNLNationaliteit redenVerlies) {
            this.bericht.setRedenVerlies(new RedenVerliesNLNationaliteitAttribuut(redenVerlies));
            return this;
        }

        /**
         * Geef attribuut Bijhouding beeindigd? een waarde.
         *
         * @param indicatieBijhoudingBeeindigd Bijhouding beeindigd? van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard indicatieBijhoudingBeeindigd(final Ja indicatieBijhoudingBeeindigd) {
            this.bericht.setIndicatieBijhoudingBeeindigd(new JaAttribuut(indicatieBijhoudingBeeindigd));
            return this;
        }

        /**
         * Geef attribuut Migratie Reden opname nationaliteit een waarde.
         *
         * @param migratieRedenOpnameNationaliteit Migratie Reden opname nationaliteit van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard migratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
            this.bericht.setMigratieRedenOpnameNationaliteit(new LO3RedenOpnameNationaliteitAttribuut(migratieRedenOpnameNationaliteit));
            return this;
        }

        /**
         * Geef attribuut Migratie Reden opname nationaliteit een waarde.
         *
         * @param migratieRedenOpnameNationaliteit Migratie Reden opname nationaliteit van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard migratieRedenOpnameNationaliteit(
            final LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit)
        {
            this.bericht.setMigratieRedenOpnameNationaliteit(migratieRedenOpnameNationaliteit);
            return this;
        }

        /**
         * Geef attribuut Migratie Reden beeindigen nationaliteit een waarde.
         *
         * @param migratieRedenBeeindigenNationaliteit Migratie Reden beeindigen nationaliteit van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard migratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit)
        {
            this.bericht.setMigratieRedenBeeindigenNationaliteit(new ConversieRedenBeeindigenNationaliteitAttribuut(migratieRedenBeeindigenNationaliteit));
            return this;
        }

        /**
         * Geef attribuut Migratie Reden beeindigen nationaliteit een waarde.
         *
         * @param migratieRedenBeeindigenNationaliteit Migratie Reden beeindigen nationaliteit van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard migratieRedenBeeindigenNationaliteit(
            final ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit)
        {
            this.bericht.setMigratieRedenBeeindigenNationaliteit(migratieRedenBeeindigenNationaliteit);
            return this;
        }

        /**
         * Geef attribuut Migratie Datum einde bijhouding een waarde.
         *
         * @param migratieDatumEindeBijhouding Migratie Datum einde bijhouding van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard migratieDatumEindeBijhouding(final Integer migratieDatumEindeBijhouding) {
            this.bericht.setMigratieDatumEindeBijhouding(new DatumEvtDeelsOnbekendAttribuut(migratieDatumEindeBijhouding));
            return this;
        }

        /**
         * Geef attribuut Migratie Datum einde bijhouding een waarde.
         *
         * @param migratieDatumEindeBijhouding Migratie Datum einde bijhouding van Standaard
         * @return de groep builder
         */
        public PersoonNationaliteitHisVolledigImplBuilderStandaard migratieDatumEindeBijhouding(
            final DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding)
        {
            this.bericht.setMigratieDatumEindeBijhouding(migratieDatumEindeBijhouding);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonNationaliteitHisVolledigImplBuilder eindeRecord() {
            final HisPersoonNationaliteitModel record = new HisPersoonNationaliteitModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonNationaliteitHistorie().voegToe(record);
            return PersoonNationaliteitHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonNationaliteitHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonNationaliteitModel record = new HisPersoonNationaliteitModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonNationaliteitHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonNationaliteitHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonNationaliteitModel record) {
            if (record.getRedenVerkrijging() != null) {
                record.getRedenVerkrijging().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getRedenVerlies() != null) {
                record.getRedenVerlies().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatieBijhoudingBeeindigd() != null) {
                record.getIndicatieBijhoudingBeeindigd().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getMigratieRedenOpnameNationaliteit() != null) {
                record.getMigratieRedenOpnameNationaliteit().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getMigratieRedenBeeindigenNationaliteit() != null) {
                record.getMigratieRedenBeeindigenNationaliteit().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getMigratieDatumEindeBijhouding() != null) {
                record.getMigratieDatumEindeBijhouding().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
