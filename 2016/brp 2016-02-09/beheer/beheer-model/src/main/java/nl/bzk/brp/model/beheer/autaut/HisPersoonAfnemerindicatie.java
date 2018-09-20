/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;

/**
 *
 *
 */
@Entity(name = "beheer.HisPersoonAfnemerindicatie")
@Table(schema = "AutAut", name = "His_PersAfnemerindicatie")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class HisPersoonAfnemerindicatie implements FormeleHistorieModel, FormeelHistorisch {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONAFNEMERINDICATIE", sequenceName = "AutAut.seq_His_PersAfnemerindicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONAFNEMERINDICATIE")
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PersAfnemerindicatie")
    private PersoonAfnemerindicatie persoonAfnemerindicatie;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    private DatumTijdAttribuut datumTijdRegistratie;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsVerval"))
    private DatumTijdAttribuut datumTijdVerval;

    @Embedded
    @AttributeOverride(name = NadereAanduidingVervalAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NadereAandVerval"))
    private NadereAanduidingVervalAttribuut nadereAanduidingVerval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DienstInh")
    private Dienst dienstInhoud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DienstVerval")
    private Dienst dienstVerval;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvMaterielePeriode"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeVolgen"))
    private DatumAttribuut datumEindeVolgen;

    /**
     * Retourneert ID van His Persoon \ Afnemerindicatie.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Afnemerindicatie van His Persoon \ Afnemerindicatie.
     *
     * @return Persoon \ Afnemerindicatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PersoonAfnemerindicatie getPersoonAfnemerindicatie() {
        return persoonAfnemerindicatie;
    }

    /**
     * Retourneert Datum/tijd registratie van His Persoon \ Afnemerindicatie.
     *
     * @return Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Retourneert Datum/tijd verval van His Persoon \ Afnemerindicatie.
     *
     * @return Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * Retourneert Nadere aanduiding verval van His Persoon \ Afnemerindicatie.
     *
     * @return Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NadereAanduidingVervalAttribuut getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * Retourneert Dienst inhoud van His Persoon \ Afnemerindicatie.
     *
     * @return Dienst inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Dienst getDienstInhoud() {
        return dienstInhoud;
    }

    /**
     * Retourneert Dienst verval van His Persoon \ Afnemerindicatie.
     *
     * @return Dienst verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Dienst getDienstVerval() {
        return dienstVerval;
    }

    /**
     * Retourneert Datum aanvang materiële periode van His Persoon \ Afnemerindicatie.
     *
     * @return Datum aanvang materiële periode.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Retourneert Datum einde volgen van His Persoon \ Afnemerindicatie.
     *
     * @return Datum einde volgen.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Zet ID van His Persoon \ Afnemerindicatie.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Long pID) {
        this.iD = pID;
    }

    /**
     * Zet Persoon \ Afnemerindicatie van His Persoon \ Afnemerindicatie.
     *
     * @param pPersoonAfnemerindicatie Persoon \ Afnemerindicatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPersoonAfnemerindicatie(final PersoonAfnemerindicatie pPersoonAfnemerindicatie) {
        this.persoonAfnemerindicatie = pPersoonAfnemerindicatie;
    }

    /**
     * Zet Datum/tijd registratie van His Persoon \ Afnemerindicatie.
     *
     * @param pDatumTijdRegistratie Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdRegistratie(final DatumTijdAttribuut pDatumTijdRegistratie) {
        this.datumTijdRegistratie = pDatumTijdRegistratie;
    }

    /**
     * Zet Datum/tijd verval van His Persoon \ Afnemerindicatie.
     *
     * @param pDatumTijdVerval Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdVerval(final DatumTijdAttribuut pDatumTijdVerval) {
        this.datumTijdVerval = pDatumTijdVerval;
    }

    /**
     * Zet Nadere aanduiding verval van His Persoon \ Afnemerindicatie.
     *
     * @param pNadereAanduidingVerval Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNadereAanduidingVerval(final NadereAanduidingVervalAttribuut pNadereAanduidingVerval) {
        this.nadereAanduidingVerval = pNadereAanduidingVerval;
    }

    /**
     * Zet Dienst inhoud van His Persoon \ Afnemerindicatie.
     *
     * @param pDienstInhoud Dienst inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstInhoud(final Dienst pDienstInhoud) {
        this.dienstInhoud = pDienstInhoud;
    }

    /**
     * Zet Dienst verval van His Persoon \ Afnemerindicatie.
     *
     * @param pDienstVerval Dienst verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstVerval(final Dienst pDienstVerval) {
        this.dienstVerval = pDienstVerval;
    }

    /**
     * Zet Datum aanvang materiële periode van His Persoon \ Afnemerindicatie.
     *
     * @param pDatumAanvangMaterielePeriode Datum aanvang materiële periode.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangMaterielePeriode(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = pDatumAanvangMaterielePeriode;
    }

    /**
     * Zet Datum einde volgen van His Persoon \ Afnemerindicatie.
     *
     * @param pDatumEindeVolgen Datum einde volgen.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeVolgen(final DatumAttribuut pDatumEindeVolgen) {
        this.datumEindeVolgen = pDatumEindeVolgen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public FormeleHistorieModel getFormeleHistorie() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public Verwerkingssoort getVerwerkingssoort() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public boolean isMagGeleverdWorden() {
        return false;
    }

}
