/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;


/**
 * Builder klasse voor Persoon \ Indicatie.
 */
public abstract class PersoonIndicatieHisVolledigImplBuilder<T extends HisPersoonIndicatieModel, B extends PersoonIndicatieHisVolledigImplBuilder<T, B>> {

    private   PersoonIndicatieHisVolledigImpl<T> hisVolledigImpl;
    protected boolean                            defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon \ Indicatie.
     */
    public PersoonIndicatieHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        this(persoon, false);
    }

    /**
     * Maak een nieuwe builder aan met backreference.
     *
     * @param persoon backreference
     * @param defaultMagGeleverdWordenVoorAttributen
     *                default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = maakPersoonIndicatieHisVolledig(persoon);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     */
    public PersoonIndicatieHisVolledigImplBuilder() {
        this(false);
    }

    /**
     * Maak een nieuwe builder aan zonder backreference.
     *
     * @param defaultMagGeleverdWordenVoorAttributen
     *         default waarde voor magGeleverdWorden voor attributen.
     */
    public PersoonIndicatieHisVolledigImplBuilder(final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = maakPersoonIndicatieHisVolledig(null);
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
    }

    protected PersoonIndicatieHisVolledigImpl<T> getHisVolledigImpl() {
        return hisVolledigImpl;
    }

    protected abstract PersoonIndicatieHisVolledigImpl<T> maakPersoonIndicatieHisVolledig(
        final PersoonHisVolledigImpl persoon);

    protected abstract T
    maakHisPersoonIndicatieModel(final PersoonIndicatieStandaardGroep groep, final ActieModel actie);

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumAanvangGeldigheid datum aanvang
     * @param datumEindeGeldigheid   datum einde
     * @param datumRegistratie       datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    protected PersoonIndicatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(
        final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid, final Integer datumRegistratie)
    {
        ActieBericht actieBericht = new ActieBericht(new SoortActieAttribuut(SoortActie.DUMMY)) {

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
    public PersoonIndicatieHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new PersoonIndicatieHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public abstract PersoonIndicatieHisVolledigImpl<T> build();

    /**
     * Inner klasse builder voor de groep Standaard
     */
    public class PersoonIndicatieHisVolledigImplBuilderStandaard {

        private ActieModel                            actie;
        private PersoonIndicatieStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public PersoonIndicatieHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new PersoonIndicatieStandaardGroepBericht();
        }

        /**
         * Geef attribuut Waarde een waarde.
         *
         * @param waarde Waarde van Standaard
         * @return de groep builder
         */
        public PersoonIndicatieHisVolledigImplBuilderStandaard waarde(final Ja waarde) {
            this.bericht.setWaarde(new JaAttribuut(waarde));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        @SuppressWarnings("unchecked")
        public B eindeRecord() {
            hisVolledigImpl.getPersoonIndicatieHistorie().voegToe(maakHisPersoonIndicatieModel(bericht, actie));
            return (B) PersoonIndicatieHisVolledigImplBuilder.this;
        }

    }

}
