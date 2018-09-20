/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;


/**
 * Vorm van historie: formeel. Motivatie: 'onderzoek' is een construct om vast te leggen dat een bepaald gegeven onderwerp is van onderzoek. Hierbij is het
 * in principe alleen relevant of een gegeven NU in onderzoek is. Verder is het voldoende om te weten of tijdens een bepaalde levering een gegeven wel of
 * niet als 'in onderzoek' stond geregistreerd. NB: de gegevens over het onderzoek zelf staan niet in de BRP, maar in bijvoorbeeld de zaaksystemen. Omdat
 * formele historie dus volstaat, wordt de materi�le historie onderdrukt. RvdP 17 jan 2012.
 */
public interface OnderzoekStandaardGroep extends OnderzoekStandaardGroepBasis {

}
