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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class OuderHisVolledigImplBuilder {

    private OuderHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;
    private ActieModel verantwoording;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public OuderHisVolledigImplBuilder(
        final RelatieHisVolledigImpl relatie,
        final PersoonHisVolledigImpl persoon,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new OuderHisVolledigImpl(relatie, persoon);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     */
    public OuderHisVolledigImplBuilder(final RelatieHisVolledigImpl relatie, final PersoonHisVolledigImpl persoon) {
        this(relatie, persoon, false);
    }

    /**
     * Start een nieuw Ouderschap record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Ouderschap groep builder
     */
    public OuderHisVolledigImplBuilderOuderschap nieuwOuderschapRecord(
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
        return nieuwOuderschapRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Ouderschap record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Ouderschap groep builder
     */
    public OuderHisVolledigImplBuilderOuderschap nieuwOuderschapRecord(final ActieModel actie) {
        return new OuderHisVolledigImplBuilderOuderschap(actie);
    }

    /**
     * Start een nieuw Ouderlijk gezag record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid datum einde
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Ouderlijk gezag groep builder
     */
    public OuderHisVolledigImplBuilderOuderlijkGezag nieuwOuderlijkGezagRecord(
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
        return nieuwOuderlijkGezagRecord(new ActieModel(actieBericht, null));
    }

    /**
     * Start een nieuw Ouderlijk gezag record, aan de hand van een actie.
     *
     * @param actie actie
     * @return Ouderlijk gezag groep builder
     */
    public OuderHisVolledigImplBuilderOuderlijkGezag nieuwOuderlijkGezagRecord(final ActieModel actie) {
        return new OuderHisVolledigImplBuilderOuderlijkGezag(actie);
    }

    /**
     * Verantwoording voor de betrokkenheid historie.
     *
     * @param actie verantwoording voor de historie records
     * @return de betrokkenheid builder.
     */
    public OuderHisVolledigImplBuilder metVerantwoording(final ActieModel actie) {
        this.verantwoording = actie;
        return this;
    }

    /**
     * Bouw het his volledig betrokkenheid object. Relatie, persoon, verantwoording kunnen null zijn. Dit stelt de
     * ontwikkelaar in staat specifieke scenario's als 'ontbrekende/onbekende ouder' te ondersteunen.
     *
     * @return het his volledig object
     */
    public OuderHisVolledigImpl build() {
        if (this.hisVolledigImpl.getRelatie() != null) {
            hisVolledigImpl.getRelatie().getBetrokkenheden().add(hisVolledigImpl);
        }
        if (this.hisVolledigImpl.getPersoon() != null) {
            hisVolledigImpl.getPersoon().getBetrokkenheden().add(hisVolledigImpl);
        }
        if (this.verantwoording != null) {
            hisVolledigImpl.getBetrokkenheidHistorie().voegToe(new HisBetrokkenheidModel(hisVolledigImpl, this.verantwoording));
        }
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Ouderschap
     *
     */
    public class OuderHisVolledigImplBuilderOuderschap {

        private ActieModel actie;
        private OuderOuderschapGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public OuderHisVolledigImplBuilderOuderschap(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new OuderOuderschapGroepBericht();
        }

        /**
         * Geef attribuut Ouder? een waarde.
         *
         * @param indicatieOuder Ouder? van Ouderschap
         * @return de groep builder
         */
        public OuderHisVolledigImplBuilderOuderschap indicatieOuder(final Ja indicatieOuder) {
            this.bericht.setIndicatieOuder(new JaAttribuut(indicatieOuder));
            return this;
        }

        /**
         * Geef attribuut Ouder uit wie het kind is geboren? een waarde.
         *
         * @param indicatieOuderUitWieKindIsGeboren Ouder uit wie het kind is geboren? van Ouderschap
         * @return de groep builder
         */
        public OuderHisVolledigImplBuilderOuderschap indicatieOuderUitWieKindIsGeboren(final Boolean indicatieOuderUitWieKindIsGeboren) {
            this.bericht.setIndicatieOuderUitWieKindIsGeboren(new JaNeeAttribuut(indicatieOuderUitWieKindIsGeboren));
            return this;
        }

        /**
         * Geef attribuut Ouder uit wie het kind is geboren? een waarde.
         *
         * @param indicatieOuderUitWieKindIsGeboren Ouder uit wie het kind is geboren? van Ouderschap
         * @return de groep builder
         */
        public OuderHisVolledigImplBuilderOuderschap indicatieOuderUitWieKindIsGeboren(final JaNeeAttribuut indicatieOuderUitWieKindIsGeboren) {
            this.bericht.setIndicatieOuderUitWieKindIsGeboren(indicatieOuderUitWieKindIsGeboren);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public OuderHisVolledigImplBuilder eindeRecord() {
            final HisOuderOuderschapModel record = new HisOuderOuderschapModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getOuderOuderschapHistorie().voegToe(record);
            return OuderHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public OuderHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisOuderOuderschapModel record = new HisOuderOuderschapModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getOuderOuderschapHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return OuderHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisOuderOuderschapModel record) {
            if (record.getIndicatieOuder() != null) {
                record.getIndicatieOuder().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getIndicatieOuderUitWieKindIsGeboren() != null) {
                record.getIndicatieOuderUitWieKindIsGeboren().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

    /**
     * Inner klasse builder voor de groep Ouderlijk gezag
     *
     */
    public class OuderHisVolledigImplBuilderOuderlijkGezag {

        private ActieModel actie;
        private OuderOuderlijkGezagGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public OuderHisVolledigImplBuilderOuderlijkGezag(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new OuderOuderlijkGezagGroepBericht();
        }

        /**
         * Geef attribuut Ouder heeft gezag? een waarde.
         *
         * @param indicatieOuderHeeftGezag Ouder heeft gezag? van Ouderlijk gezag
         * @return de groep builder
         */
        public OuderHisVolledigImplBuilderOuderlijkGezag indicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
            this.bericht.setIndicatieOuderHeeftGezag(new JaNeeAttribuut(indicatieOuderHeeftGezag));
            return this;
        }

        /**
         * Geef attribuut Ouder heeft gezag? een waarde.
         *
         * @param indicatieOuderHeeftGezag Ouder heeft gezag? van Ouderlijk gezag
         * @return de groep builder
         */
        public OuderHisVolledigImplBuilderOuderlijkGezag indicatieOuderHeeftGezag(final JaNeeAttribuut indicatieOuderHeeftGezag) {
            this.bericht.setIndicatieOuderHeeftGezag(indicatieOuderHeeftGezag);
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public OuderHisVolledigImplBuilder eindeRecord() {
            final HisOuderOuderlijkGezagModel record = new HisOuderOuderlijkGezagModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getOuderOuderlijkGezagHistorie().voegToe(record);
            return OuderHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public OuderHisVolledigImplBuilder eindeRecord(final Integer id) {
            final HisOuderOuderlijkGezagModel record = new HisOuderOuderlijkGezagModel(hisVolledigImpl, bericht, actie, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getOuderOuderlijkGezagHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return OuderHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisOuderOuderlijkGezagModel record) {
            if (record.getIndicatieOuderHeeftGezag() != null) {
                record.getIndicatieOuderHeeftGezag().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
