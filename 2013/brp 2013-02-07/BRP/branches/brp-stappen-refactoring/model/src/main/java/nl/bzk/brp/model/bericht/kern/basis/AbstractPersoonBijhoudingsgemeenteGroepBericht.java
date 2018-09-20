/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsgemeenteGroepBasis;


/**
 * Vorm van historie: beiden.
 * Motivatie voor de materi�le tijdslijn: de bijhoudingsgemeente kan op een eerder moment dan technisch verwerkt de
 * verantwoordelijke gemeente zijn (geworden). Of te wel: formele tijdslijn kan anders liggen dan materi�le tijdslijn.
 * Voor het OOK bestaan van datum inschrijving: zie modelleringsbeslissing aldaar. RvdP 10 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonBijhoudingsgemeenteGroepBericht extends AbstractGroepBericht implements
        PersoonBijhoudingsgemeenteGroepBasis
{

    private String bijhoudingsgemeenteCode;
    private Partij bijhoudingsgemeente;
    private Datum  datumInschrijvingInGemeente;
    private JaNee  indicatieOnverwerktDocumentAanwezig;

    /**
     *
     *
     * @return
     */
    public String getBijhoudingsgemeenteCode() {
        return bijhoudingsgemeenteCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     *
     *
     * @param bijhoudingsgemeenteCode
     */
    public void setBijhoudingsgemeenteCode(final String bijhoudingsgemeenteCode) {
        this.bijhoudingsgemeenteCode = bijhoudingsgemeenteCode;
    }

    /**
     * Zet Bijhoudingsgemeente van Bijhoudingsgemeente.
     *
     * @param bijhoudingsgemeente Bijhoudingsgemeente.
     */
    public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }

    /**
     * Zet Datum inschrijving in gemeente van Bijhoudingsgemeente.
     *
     * @param datumInschrijvingInGemeente Datum inschrijving in gemeente.
     */
    public void setDatumInschrijvingInGemeente(final Datum datumInschrijvingInGemeente) {
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
    }

    /**
     * Zet Onverwerkt document aanwezig? van Bijhoudingsgemeente.
     *
     * @param indicatieOnverwerktDocumentAanwezig Onverwerkt document aanwezig?.
     */
    public void setIndicatieOnverwerktDocumentAanwezig(final JaNee indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }

}
