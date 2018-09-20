/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Tagging interface voor boven alle XxxView klasses.
 * <p/>
 * Extend model indentificeerbaar, zodat altijd een id opgevraagd kan worden. Let op: het return type is een Number, omdat er verschillende types voorkomen
 * (Short / Int / Long). Als je iets generiek wilt vergelijken, kan dat bijvoorbeeld zo: getID().longValue() == anderNummer
 */
public interface ModelMoment extends ModelIdentificeerbaar<Number> {

}
