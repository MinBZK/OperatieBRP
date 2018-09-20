/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.util.DatumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * Proxy view voor {@link PersoonVolledig} die de informatie terug geeft die op een
 * peildatum bekend is van de persoon.
 */
public abstract class PersoonVolledigView implements Persoon {

    private final PersoonVolledig persoon;
    private final Datum peilDatum;

    /**
     * Decoreer een {@link PersoonVolledig} instantie met de peildatum vandaag.
     *
     * @param persoon de persoonVolledig instantie die de informatie bevat
     */
    public PersoonVolledigView(final PersoonVolledig persoon) {
        this(persoon, DatumUtil.vandaag());
    }

    /**
     * Decoreer een {@link PersoonVolledig} instantie met de gegeven peildatum.
     *
     * @param persoon   de persoonVolledig instantie die de informatie bevat
     * @param peilDatum de peildatum waarop informatie wordt opgehaald
     */
    public PersoonVolledigView(final PersoonVolledig persoon, final Datum peilDatum) {
        this.persoon = persoon;
        this.peilDatum = peilDatum;
    }

    @Override
    public SoortPersoon getSoort() {
        return persoon.getSoort();
    }

    @Override
    public PersoonGeslachtsaanduidingGroep getGeslachtsaanduiding() {
        HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
                (HisPersoonGeslachtsaanduidingModel) CollectionUtils
                        .find(persoon.getGeslachtsaanduiding(), geldigOp(peilDatum));

        return geslachtsaanduiding.getPersoon().getGeslachtsaanduiding();
    }

    @Override
    public Collection<? extends PersoonAdres> getAdressen() {
        Collection<HisPersoonAdresModel> hisAdressen =
                CollectionUtils.select(persoon.getAdressen(), bekendOp(peilDatum));

        Set<PersoonAdresModel> adressen = new HashSet<PersoonAdresModel>(hisAdressen.size());
        for (HisPersoonAdresModel hisModel : hisAdressen) {
            adressen.add(new PersoonAdresModel());
        }

        return adressen;
    }

    /**
     * Geef een {@link Predicate} die voor een {@link MaterieleHistorie} instantie geldigheid op een datum checkt.
     *
     * @param datum de datum waarop geldigheid wordt gecheckt
     * @return een predicaat
     */
    public static GeldigOp geldigOp(final Datum datum) {
        return new GeldigOp(datum);
    }

    /**
     * Geef een {@link Predicate} die voor een {@link FormeleHistorie} instantie geldigheid op een datum checkt.
     *
     * @param datum de datum waarop geldigheid wordt gecheckt
     * @return een predicaat
     */
    public static BekendOp bekendOp(final Datum datum) {
        return new BekendOp(datum);
    }

    private static class BekendOp implements Predicate {
        private final Datum peilDatum;

        /**
         * Constructor.
         *
         * @param moment de datum waarop geldigheid wordt gecontroleerd
         */
        public BekendOp(final Datum moment) {
            peilDatum = moment;
        }

        @Override
        public boolean evaluate(final Object object) {
            FormeleHistorie formeleHistorie = (FormeleHistorie) object;

            Datum aanvangDatum = DatumUtil.dateToDatum(formeleHistorie.getDatumTijdRegistratie().getWaarde());
            Datum vervalDatum = DatumUtil.dateToDatum(formeleHistorie.getDatumTijdVerval().getWaarde());

            return (vervalDatum == null || vervalDatum.na(peilDatum))
                    && (aanvangDatum.voor(peilDatum) || aanvangDatum.getWaarde().equals(peilDatum.getWaarde()));
        }
    }

    private static class GeldigOp implements Predicate {
        private final Datum peilDatum;

        /**
         * Constructor.
         *
         * @param datum de datum waarop geldigheid wordt gecontroleerd
         */
        public GeldigOp(final Datum datum) {
            this.peilDatum = datum;
        }

        @Override
        public boolean evaluate(final Object object) {
            MaterieleHistorie materieleHistorie = (MaterieleHistorie) object;

            return (materieleHistorie.getDatumAanvangGeldigheid().voor(peilDatum)
                    || peilDatum.getWaarde().equals(materieleHistorie.getDatumAanvangGeldigheid().getWaarde()))
                    && (materieleHistorie.getDatumEindeGeldigheid() == null
                    || materieleHistorie.getDatumEindeGeldigheid().na(peilDatum));
        }
    }

}
