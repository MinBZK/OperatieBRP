/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import nl.bzk.brp.util.StamgegevenBuilder;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Document.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class DocumentHisVolledigImplBuilder {

    private DocumentHisVolledigImpl hisVolledigImpl;
    private boolean defaultMagGeleverdWordenVoorAttributen;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param soort soort van Document.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public DocumentHisVolledigImplBuilder(final SoortDocument soort, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(soort));
        this.defaultMagGeleverdWordenVoorAttributen = defaultMagGeleverdWordenVoorAttributen;
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Document.
     */
    public DocumentHisVolledigImplBuilder(final SoortDocument soort) {
        this(soort, false);
    }

    /**
     * Start een nieuw Standaard record, aan de hand van data.
     *
     * @param datumRegistratie datum registratie (geen tijd)
     * @return Standaard groep builder
     */
    public DocumentHisVolledigImplBuilderStandaard nieuwStandaardRecord(final Integer datumRegistratie) {
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
    public DocumentHisVolledigImplBuilderStandaard nieuwStandaardRecord(final ActieModel actie) {
        return new DocumentHisVolledigImplBuilderStandaard(actie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public DocumentHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Inner klasse builder voor de groep Standaard
     *
     */
    public class DocumentHisVolledigImplBuilderStandaard {

        private ActieModel actie;
        private DocumentStandaardGroepBericht bericht;

        /**
         * Constructor met actie.
         *
         * @param actie actie
         */
        public DocumentHisVolledigImplBuilderStandaard(final ActieModel actie) {
            this.actie = actie;
            this.bericht = new DocumentStandaardGroepBericht();
        }

        /**
         * Geef attribuut Identificatie een waarde.
         *
         * @param identificatie Identificatie van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard identificatie(final String identificatie) {
            this.bericht.setIdentificatie(new DocumentIdentificatieAttribuut(identificatie));
            return this;
        }

        /**
         * Geef attribuut Identificatie een waarde.
         *
         * @param identificatie Identificatie van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard identificatie(final DocumentIdentificatieAttribuut identificatie) {
            this.bericht.setIdentificatie(identificatie);
            return this;
        }

        /**
         * Geef attribuut Aktenummer een waarde.
         *
         * @param aktenummer Aktenummer van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard aktenummer(final String aktenummer) {
            this.bericht.setAktenummer(new AktenummerAttribuut(aktenummer));
            return this;
        }

        /**
         * Geef attribuut Aktenummer een waarde.
         *
         * @param aktenummer Aktenummer van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard aktenummer(final AktenummerAttribuut aktenummer) {
            this.bericht.setAktenummer(aktenummer);
            return this;
        }

        /**
         * Geef attribuut Omschrijving een waarde.
         *
         * @param omschrijving Omschrijving van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard omschrijving(final String omschrijving) {
            this.bericht.setOmschrijving(new DocumentOmschrijvingAttribuut(omschrijving));
            return this;
        }

        /**
         * Geef attribuut Omschrijving een waarde.
         *
         * @param omschrijving Omschrijving van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard omschrijving(final DocumentOmschrijvingAttribuut omschrijving) {
            this.bericht.setOmschrijving(omschrijving);
            return this;
        }

        /**
         * Geef attribuut Partij een waarde.
         *
         * @param code Partij van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard partij(final Integer code) {
            this.bericht.setPartij(new PartijAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, new PartijCodeAttribuut(code))));
            return this;
        }

        /**
         * Geef attribuut Partij een waarde.
         *
         * @param code Partij van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard partij(final PartijCodeAttribuut code) {
            this.bericht.setPartij(new PartijAttribuut(StamgegevenBuilder.bouwDynamischStamgegeven(Partij.class, code)));
            return this;
        }

        /**
         * Geef attribuut Partij een waarde.
         *
         * @param partij Partij van Standaard
         * @return de groep builder
         */
        public DocumentHisVolledigImplBuilderStandaard partij(final Partij partij) {
            this.bericht.setPartij(new PartijAttribuut(partij));
            return this;
        }

        /**
         * Beeindig het record.
         *
         * @return his volledig builder
         */
        public DocumentHisVolledigImplBuilder eindeRecord() {
            final HisDocumentModel record = new HisDocumentModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getDocumentHistorie().voegToe(record);
            return DocumentHisVolledigImplBuilder.this;
        }

        /**
         * Beeindig het record.
         *
         * @param id id van het historie record
         * @return his volledig builder
         */
        public DocumentHisVolledigImplBuilder eindeRecord(final Long id) {
            final HisDocumentModel record = new HisDocumentModel(hisVolledigImpl, bericht, actie);
            zetMagGeleverdWordenVlaggetjes(record);
            hisVolledigImpl.getDocumentHistorie().voegToe(record);
            ReflectionTestUtils.setField(record, "iD", id);
            return DocumentHisVolledigImplBuilder.this;
        }

        /**
         * Zet van alle attributen de isMagGeleverdWorden vlag naar de default waarde waarmee deze ImplBuilder is
         * geinstantieerd.
         *
         * @param record Het his record
         */
        private void zetMagGeleverdWordenVlaggetjes(final HisDocumentModel record) {
            if (record.getIdentificatie() != null) {
                record.getIdentificatie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getAktenummer() != null) {
                record.getAktenummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getOmschrijving() != null) {
                record.getOmschrijving().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
            if (record.getPartij() != null) {
                record.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
            }
        }

    }

}
