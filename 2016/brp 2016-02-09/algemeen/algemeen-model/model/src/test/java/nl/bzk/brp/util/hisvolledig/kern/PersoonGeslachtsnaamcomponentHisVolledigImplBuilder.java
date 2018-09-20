/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Persoon \ Geslachtsnaamcomponent.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonGeslachtsnaamcomponentHisVolledigImplBuilder {

    private PersoonGeslachtsnaamcomponentHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Geslachtsnaamcomponent.
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final VolgnummerAttribuut volgnummer,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoon, volgnummer);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getVolgnummer() != null) {
            hisVolledigImpl.getVolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Geslachtsnaamcomponent.
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     */
    public PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final VolgnummerAttribuut volgnummer) {
        this(persoon, volgnummer, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(final VolgnummerAttribuut volgnummer, final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new PersoonGeslachtsnaamcomponentHisVolledigImpl(null, volgnummer);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getVolgnummer() != null) {
            hisVolledigImpl.getVolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param volgnummer volgnummer van Persoon \ Geslachtsnaamcomponent.
     */
    public PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(final VolgnummerAttribuut volgnummer) {
        this(null, volgnummer, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard nieuwStandaardRecord(
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
    public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonGeslachtsnaamcomponentHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private PersoonGeslachtsnaamcomponentStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        }

        /**
         * Geef attribuut Predicaat een waarde.
         *
         * @param code Predicaat van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard predicaat(final String code) {
            this.bericht.setPredicaat(new PredicaatAttribuut(
                StamgegevenBuilder.bouwDynamischStamgegeven(Predicaat.class, new PredicaatCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Predicaat een waarde.
         *
         * @param code Predicaat van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard predicaat(final PredicaatCodeAttribuut code) {
            this.bericht.setPredicaat(new PredicaatAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Predicaat.class, code)));
            return this;
        }

        /**
         * Geef attribuut Predicaat een waarde.
         *
         * @param predicaat Predicaat van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard predicaat(final Predicaat predicaat) {
            this.bericht.setPredicaat(new PredicaatAttribuut(predicaat));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel een waarde.
         *
         * @param code Adellijke titel van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard adellijkeTitel(final String code) {
            this.bericht.setAdellijkeTitel(new AdellijkeTitelAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(
                AdellijkeTitel.class,
                new AdellijkeTitelCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel een waarde.
         *
         * @param code Adellijke titel van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard adellijkeTitel(final AdellijkeTitelCodeAttribuut code) {
            this.bericht.setAdellijkeTitel(new AdellijkeTitelAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(AdellijkeTitel.class, code)));
            return this;
        }

        /**
         * Geef attribuut Adellijke titel een waarde.
         *
         * @param adellijkeTitel Adellijke titel van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard adellijkeTitel(final AdellijkeTitel adellijkeTitel) {
            this.bericht.setAdellijkeTitel(new AdellijkeTitelAttribuut(adellijkeTitel));
            return this;
        }

        /**
         * Geef attribuut Voorvoegsel een waarde.
         *
         * @param voorvoegsel Voorvoegsel van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard voorvoegsel(final String voorvoegsel) {
            this.bericht.setVoorvoegsel(new VoorvoegselAttribuut(voorvoegsel));
            return this;
        }

        /**
         * Geef attribuut Voorvoegsel een waarde.
         *
         * @param voorvoegsel Voorvoegsel van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard voorvoegsel(final VoorvoegselAttribuut voorvoegsel) {
            this.bericht.setVoorvoegsel(voorvoegsel);
            return this;
        }

        /**
         * Geef attribuut Scheidingsteken een waarde.
         *
         * @param scheidingsteken Scheidingsteken van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard scheidingsteken(final String scheidingsteken) {
            this.bericht.setScheidingsteken(new ScheidingstekenAttribuut(scheidingsteken));
            return this;
        }

        /**
         * Geef attribuut Scheidingsteken een waarde.
         *
         * @param scheidingsteken Scheidingsteken van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard scheidingsteken(final ScheidingstekenAttribuut scheidingsteken) {
            this.bericht.setScheidingsteken(scheidingsteken);
            return this;
        }

        /**
         * Geef attribuut Stam een waarde.
         *
         * @param stam Stam van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard stam(final String stam) {
            this.bericht.setStam(new GeslachtsnaamstamAttribuut(stam));
            return this;
        }

        /**
         * Geef attribuut Stam een waarde.
         *
         * @param stam Stam van Standaard
         * @return de groep builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilderStandaard stam(final GeslachtsnaamstamAttribuut stam) {
            this.bericht.setStam(stam);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilder eindeRecord() {
            final HisPersoonGeslachtsnaamcomponentModel record = new HisPersoonGeslachtsnaamcomponentModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonGeslachtsnaamcomponentHistorie().voegToe(record);
            return PersoonGeslachtsnaamcomponentHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public PersoonGeslachtsnaamcomponentHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisPersoonGeslachtsnaamcomponentModel record = new HisPersoonGeslachtsnaamcomponentModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getPersoonGeslachtsnaamcomponentHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return PersoonGeslachtsnaamcomponentHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisPersoonGeslachtsnaamcomponentModel record) {
            if (record.getPredicaat() != null) {
                record.getPredicaat().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAdellijkeTitel() != null) {
                record.getAdellijkeTitel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getVoorvoegsel() != null) {
                record.getVoorvoegsel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getScheidingsteken() != null) {
                record.getScheidingsteken().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getStam() != null) {
                record.getStam().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
