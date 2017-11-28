/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * Deze class bevat de velden voor het opslaan van formele historie zonder de
 * verantwoordingsattributen.
 */
@MappedSuperclass
public abstract class AbstractFormeleHistorieZonderVerantwoording extends AbstractEntiteit implements FormeleHistorieZonderVerantwoording {

    /** Veldnaam van datumTijdRegistratie tbv verschil verwerking. */
    public static final String DATUM_TIJD_REGISTRATIE = "datumTijdRegistratie";
    /** Veldnaam van datumTijdVerval tbv verschil verwerking. */
    public static final String DATUM_TIJD_VERVAL = "datumTijdVerval";

    @Column(name = "tsreg", nullable = false)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private Timestamp datumTijdVerval;

    /**
     * Maakt een nieuw abstracte formele historie zonder verantwoording.
     */
    public AbstractFormeleHistorieZonderVerantwoording() {
        super();
    }

    /**
     * Kopie constructor voor AbstractFormeleHistorieZonderVerantwoording.
     *
     * @param ander de andere historie waaruit gekopieerd moet worden
     */
    public AbstractFormeleHistorieZonderVerantwoording(final AbstractFormeleHistorieZonderVerantwoording ander) {
        super(ander);
        datumTijdRegistratie = ander.getDatumTijdRegistratie();
        datumTijdVerval = ander.getDatumTijdVerval();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getDatumTijdRegistratie()
     */
    @Override
    public Timestamp getDatumTijdRegistratie() {

        return Entiteit.timestamp(datumTijdRegistratie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setDatumTijdRegistratie(java.sql
     * .Timestamp)
     */
    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Entiteit.timestamp(datumTijdRegistratie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#getDatumTijdVerval()
     */
    @Override
    public Timestamp getDatumTijdVerval() {

        return Entiteit.timestamp(datumTijdVerval);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#setDatumTijdVerval(java.sql. Timestamp )
     */
    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {

        this.datumTijdVerval = Entiteit.timestamp(datumTijdVerval);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#isActueel()
     */
    @Override
    public boolean isActueel() {

        return getDatumTijdVerval() == null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.FormeleHistorie#isVervallen()
     */
    @Override
    public final boolean isVervallen() {
        return getDatumTijdVerval() != null;
    }
}
