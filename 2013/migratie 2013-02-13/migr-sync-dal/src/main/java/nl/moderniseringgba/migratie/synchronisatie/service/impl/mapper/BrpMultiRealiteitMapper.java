/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegelHistorie;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

/**
 * MultiRealiteit mapper.
 */
@Component
public final class BrpMultiRealiteitMapper {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Verwerk MR in een stapel.
     * 
     * @param <I>
     *            BRP groep inhoud type
     * @param stapel
     *            stapel
     * @param multiRealiteit
     *            MR-regel
     * @return aangepaste stapel (of null bij volledige MR)
     */
    public <I extends BrpGroepInhoud> BrpStapel<I> verwerk(
            final BrpStapel<I> stapel,
            final MultiRealiteitRegel multiRealiteit) {
        if (multiRealiteit == null || stapel == null) {
            return stapel;
        }

        final List<MRPeriode> mrPeriodes = bepaalPeriodes(multiRealiteit);

        final List<BrpGroep<I>> result = new ArrayList<BrpGroep<I>>();

        for (final MRPeriode periode : mrPeriodes) {
            for (final BrpGroep<I> groep : stapel) {
                verwerkGroep(groep, periode, result);
            }
        }

        return result.isEmpty() ? null : new BrpStapel<I>(result);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<MRPeriode> bepaalPeriodes(final MultiRealiteitRegel multiRealiteit) {
        final List<MRPeriode> result = new ArrayList<MRPeriode>();

        for (final MultiRealiteitRegelHistorie mrRegel : multiRealiteit.getHisMultirealiteitregels()) {
            result.add(new MRPeriode(BrpMapperUtil.mapBrpDatumTijd(mrRegel.getDatumTijdRegistratie()), BrpMapperUtil
                    .mapBrpDatumTijd(mrRegel.getDatumTijdVerval()), BrpMapper.mapActie(mrRegel.getActieInhoud()),
                    BrpMapper.mapActie(mrRegel.getActieVerval())));
        }

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private <I extends BrpGroepInhoud> void verwerkGroep(
            final BrpGroep<I> groep,
            final MRPeriode mrPeriode,
            final List<BrpGroep<I>> result) {
        LOG.debug("verwerkGroep: groep.historie: {}", groep.getHistorie());
        LOG.debug("verwerkGroep: mrPeriode: {}", mrPeriode);

        if (mrPeriode.ligtVolledigOver(groep.getHistorie())) {
            LOG.debug("Volledig negeren");
            // Groep volledig negeren
        } else if (mrPeriode.ligtOverBegin(groep.getHistorie())) {
            LOG.debug("Overschrijf registratie");
            result.add(maakGroepEnOverschrijfRegistratie(groep, mrPeriode.getDtVerval(), mrPeriode.getActieVerval()));

        } else if (mrPeriode.ligtOverEinde(groep.getHistorie())) {
            LOG.debug("Overschrijf verval");
            result.add(maakGroepEnOverschrijfVerval(groep, mrPeriode.getDtRegistratie(), mrPeriode.getActieInhoud()));

        } else if (mrPeriode.ligtIn(groep.getHistorie())) {
            LOG.debug("Knap en overschrijf registratie en verval");
            result.add(maakGroepEnOverschrijfVerval(groep, mrPeriode.getDtRegistratie(), mrPeriode.getActieInhoud()));
            result.add(maakGroepEnOverschrijfRegistratie(groep, mrPeriode.getDtVerval(), mrPeriode.getActieVerval()));
        } else {
            LOG.debug("Geen overlap");
            result.add(groep);
        }
    }

    private <I extends BrpGroepInhoud> BrpGroep<I> maakGroepEnOverschrijfRegistratie(
            final BrpGroep<I> basis,
            final BrpDatumTijd dtRegistratie,
            final BrpActie actieInhoud) {
        final BrpHistorie basisHistorie = basis.getHistorie();
        final BrpHistorie historie =
                new BrpHistorie(basisHistorie.getDatumAanvangGeldigheid(), basisHistorie.getDatumEindeGeldigheid(),
                        dtRegistratie, basisHistorie.getDatumTijdVerval());

        return new BrpGroep<I>(basis.getInhoud(), historie, actieInhoud, basis.getActieVerval(),
                basis.getActieGeldigheid());
    }

    private <I extends BrpGroepInhoud> BrpGroep<I> maakGroepEnOverschrijfVerval(
            final BrpGroep<I> basis,
            final BrpDatumTijd dtVerval,
            final BrpActie actieVerval) {

        final BrpHistorie basisHistorie = basis.getHistorie();
        final BrpHistorie historie =
                new BrpHistorie(basisHistorie.getDatumAanvangGeldigheid(), basisHistorie.getDatumEindeGeldigheid(),
                        basisHistorie.getDatumTijdRegistratie(), dtVerval);

        return new BrpGroep<I>(basis.getInhoud(), historie, basis.getActieInhoud(), actieVerval,
                basis.getActieGeldigheid());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Periode.
     */
    private static final class MRPeriode {
        private final BrpDatumTijd dtRegistratie;
        private final BrpDatumTijd dtVerval;

        private final BrpActie actieInhoud;
        private final BrpActie actieVerval;

        /**
         * @param dtRegistratie
         * @param dtVerval
         * @param actieInhoud
         * @param actieVerval
         */
        public MRPeriode(
                final BrpDatumTijd dtRegistratie,
                final BrpDatumTijd dtVerval,
                final BrpActie actieInhoud,
                final BrpActie actieVerval) {

            this.dtRegistratie = dtRegistratie;
            this.dtVerval = dtVerval;

            if (this.dtVerval != null && dtRegistratie.compareTo(dtVerval) > 0) {
                throw new AssertionError("Ongeldig MR periode; dtRegistratie ligt na dtVerval.");
            }

            this.actieInhoud = actieInhoud;
            this.actieVerval = actieVerval;
        }

        public BrpDatumTijd getDtRegistratie() {
            return dtRegistratie;
        }

        public BrpDatumTijd getDtVerval() {
            return dtVerval;
        }

        public BrpActie getActieInhoud() {
            return actieInhoud;
        }

        public BrpActie getActieVerval() {
            return actieVerval;
        }

        /**
         * @param historie
         * @return
         */
        public boolean ligtVolledigOver(final BrpHistorie historie) {
            // deze registratie is kleiner dan of gelijk aan historie.registratie
            // EN deze verval is leeg of groter dan of gelijk aan historie.verval
            return lessOrEqual(dtRegistratie, historie.getDatumTijdRegistratie())
                    && greaterOrEqual(dtVerval, historie.getDatumTijdVerval());
        }

        /**
         * @param historie
         * @return
         */
        public boolean ligtOverBegin(final BrpHistorie historie) {
            // deze registratie is kleiner dan of gelijk aan historie.registratie
            // EN deze verval is leeg of groter dan of gelijk aan historie.registratie
            return lessOrEqual(dtRegistratie, historie.getDatumTijdRegistratie())
                    && greaterOrEqual(dtVerval, historie.getDatumTijdRegistratie());
        }

        /**
         * @param historie
         * @return
         */
        public boolean ligtOverEinde(final BrpHistorie historie) {
            // deze registratie is kleiner dan historie.verval
            // EN deze verval is leeg of groter dan of gelijk aan historie.verval
            return lessOrEqual(dtRegistratie, historie.getDatumTijdVerval())
                    && greaterOrEqual(dtVerval, historie.getDatumTijdVerval());
        }

        /**
         * @param historie
         * @return
         */
        public boolean ligtIn(final BrpHistorie historie) {
            // deze registratie is kleiner dan historie.verval
            // EN deze verval is groter dan historie.registratie
            return lessOrEqual(dtRegistratie, historie.getDatumTijdVerval())
                    && greaterOrEqual(dtVerval, historie.getDatumTijdRegistratie());
        }

        /**
         * Is 'deze' minder of gelijk aan 'die'.
         * 
         * @param deze
         *            deze
         * @param die
         *            die
         * @return true, als 'deze' minder of gelijk is aan 'die'
         */
        private static boolean lessOrEqual(final BrpDatumTijd deze, final BrpDatumTijd die) {
            final boolean result;

            if (die == null) {
                result = true;
            } else if (deze == null) {
                result = false;
            } else {
                result = deze.compareTo(die) <= 0;
            }

            return result;
        }

        /**
         * Is 'deze' meer of gelijk aan 'die'
         * 
         * @param deze
         *            deze
         * @param die
         *            die
         * @return true, als 'deze' meer of gelijk is aan 'die'
         */

        private static boolean greaterOrEqual(final BrpDatumTijd deze, final BrpDatumTijd die) {
            final boolean result;

            if (deze == null) {
                result = true;
            } else if (die == null) {
                result = false;
            } else {
                result = deze.compareTo(die) >= 0;
            }

            return result;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                    .append("dtRegistratie", dtRegistratie).append("dtVerval", dtVerval)
                    .append("actieInhoud", actieInhoud).append("actieVerval", actieVerval).toString();
        }
    }

}
