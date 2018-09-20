/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;


/**
 * De groep zoekcriteria heeft als vrijwel enige groep een 'meervoudsvorm'. Een enkelvoudsvorm zou 'Zoekingang' zijn geweest. Echter, de aanduiding
 * 'zoekcriteria' is al langer in gebruik binnen het programma. Om die reden wordt voor de meervoudsvorm gekozen. Merk op dat Zoekcriterium een onjuiste
 * term zou zijn, omdat de groep gaat over meer dan ��n zoekcriterium. (Vergelijk bijv. samengestelde naam: daar slaat deze naam wel op ALLE elementen).
 * RvdP 11 maart 2013.
 */
public interface VraagZoekcriteriaGroep extends VraagZoekcriteriaGroepBasis {

}
