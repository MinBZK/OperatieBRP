/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

/**
 * <p>Overview Maakbericht.</p>
 * <p>
 * Bericht Model module bevat classes om het bericht model te construeren uit het metamodel en persoonsberichtgegevens uit de maak bericht module. Hiermee
 * wordt een model gemaakt dat door de writers in nl.bzk.brp.domain.berichtmodel.writer opgepakt kan worden om een XML bericht te construeren.
 * <p>
 * Het berichtmodel kent de volgende hoofd bericht classes:
 * {@link  nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht}, verantwoordelijk voor berichten die personen bevatten (e.g. MutieleveringBerichten,
 * GeefDetailsPersoon).
 * {@link  nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht}
 * {@link  nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht}
 * {@link  nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht}
 *
 * De berichten worden gebouwd volgens het builder patroon. Dit gebeurd in de dienst specifieke services die als orchestrator fungeren.
 */
package nl.bzk.brp.domain.berichtmodel;
