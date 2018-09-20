/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Verblijfstitel;
import nl.bzk.brp.model.basis.Groep;


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
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonVerblijfstitelGroepBasis extends Groep {

    /**
     * Retourneert Verblijfstitel van Verblijfstitel.
     *
     * @return Verblijfstitel.
     */
    Verblijfstitel getVerblijfstitel();

    /**
     * Retourneert Datum aanvang verblijfstitel van Verblijfstitel.
     *
     * @return Datum aanvang verblijfstitel.
     */
    Datum getDatumAanvangVerblijfstitel();

    /**
     * Retourneert Datum voorzien einde verblijfstitel van Verblijfstitel.
     *
     * @return Datum voorzien einde verblijfstitel.
     */
    Datum getDatumVoorzienEindeVerblijfstitel();

}
