/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroep;


/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter ��nmalig wijzigen, zoals
 * reden vervallen.
 * Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 * RvdP 26 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 13:50:44 CET 2012.
 */
public class PersoonReisdocumentStandaardGroepBericht extends AbstractPersoonReisdocumentStandaardGroepBericht
        implements PersoonReisdocumentStandaardGroep
{

}
