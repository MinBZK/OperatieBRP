/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Groep;


/**
 * Vorm van historie: beiden.
 * Motivatie voor de materi�le tijdslijn: de bijhoudingsgemeente kan op een eerder moment dan technisch verwerkt de
 * verantwoordelijke gemeente zijn (geworden). Of te wel: formele tijdslijn kan anders liggen dan materi�le tijdslijn.
 * Voor het OOK bestaan van datum inschrijving: zie modelleringsbeslissing aldaar. RvdP 10 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonBijhoudingsgemeenteGroepBasis extends Groep {

    /**
     * Retourneert Bijhoudingsgemeente van Bijhoudingsgemeente.
     *
     * @return Bijhoudingsgemeente.
     */
    Partij getBijhoudingsgemeente();

    /**
     * Retourneert Datum inschrijving in gemeente van Bijhoudingsgemeente.
     *
     * @return Datum inschrijving in gemeente.
     */
    Datum getDatumInschrijvingInGemeente();

    /**
     * Retourneert Onverwerkt document aanwezig? van Bijhoudingsgemeente.
     *
     * @return Onverwerkt document aanwezig?.
     */
    JaNee getIndicatieOnverwerktDocumentAanwezig();

}
