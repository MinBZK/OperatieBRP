/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Verblijfstitel;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVerblijfstitelGroepBasis;


/**
 * 1. Vorm van historie: beiden (zowel materi�le als formele historie).
 * Het historiepatroon bij verblijfsrecht is bijzonder. De datum aanvang verblijfsrecht wordt aangeleverd door de IND,
 * en komt logischerwijs overeen met datum aanvang geldigheid.
 * De datum VOORZIEN einde kan in de toekomst liggen, en wijkt derhalve af van een 'normale' datum einde geldigheid, die
 * meestal in het verleden zal liggen.
 * Vanwege aanlevering vanuit migratie (met een andere granulariteit voor historie) kan datum aanvang geldigheid
 * afwijken van de datum aanvang verblijfsrecht.
 *
 *
 *
 */
public abstract class AbstractPersoonVerblijfstitelGroepBericht extends AbstractGroepBericht implements
        PersoonVerblijfstitelGroepBasis
{

    private String         verblijfstitelCode;
    private Verblijfstitel verblijfstitel;
    private Datum          datumAanvangVerblijfstitel;
    private Datum          datumVoorzienEindeVerblijfstitel;

    /**
     * Retourneert Verblijfstitel van Verblijfstitel.
     *
     * @return Verblijfstitel.
     */
    public String getVerblijfstitelCode() {
        return verblijfstitelCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verblijfstitel getVerblijfstitel() {
        return verblijfstitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvangVerblijfstitel() {
        return datumAanvangVerblijfstitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVoorzienEindeVerblijfstitel() {
        return datumVoorzienEindeVerblijfstitel;
    }

    /**
     * Zet Verblijfstitel van Verblijfstitel.
     *
     * @param verblijfstitelCode Verblijfstitel.
     */
    public void setVerblijfstitelCode(final String verblijfstitelCode) {
        this.verblijfstitelCode = verblijfstitelCode;
    }

    /**
     * Zet Verblijfstitel van Verblijfstitel.
     *
     * @param verblijfstitel Verblijfstitel.
     */
    public void setVerblijfstitel(final Verblijfstitel verblijfstitel) {
        this.verblijfstitel = verblijfstitel;
    }

    /**
     * Zet Datum aanvang verblijfstitel van Verblijfstitel.
     *
     * @param datumAanvangVerblijfstitel Datum aanvang verblijfstitel.
     */
    public void setDatumAanvangVerblijfstitel(final Datum datumAanvangVerblijfstitel) {
        this.datumAanvangVerblijfstitel = datumAanvangVerblijfstitel;
    }

    /**
     * Zet Datum voorzien einde verblijfstitel van Verblijfstitel.
     *
     * @param datumVoorzienEindeVerblijfstitel Datum voorzien einde verblijfstitel.
     */
    public void setDatumVoorzienEindeVerblijfstitel(final Datum datumVoorzienEindeVerblijfstitel) {
        this.datumVoorzienEindeVerblijfstitel = datumVoorzienEindeVerblijfstitel;
    }

}
