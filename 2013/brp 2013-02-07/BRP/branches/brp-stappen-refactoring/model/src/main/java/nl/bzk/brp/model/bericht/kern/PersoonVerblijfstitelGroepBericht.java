/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonVerblijfstitelGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfstitelGroep;


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
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.1.15.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-28 16:34:59.
 * Gegenereerd op: Wed Nov 28 16:37:17 CET 2012.
 */
public class PersoonVerblijfstitelGroepBericht extends AbstractPersoonVerblijfstitelGroepBericht implements
        PersoonVerblijfstitelGroep
{

}
