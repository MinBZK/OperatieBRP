/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

/**
 * Converter van String naar Character.
 */
public final class CharacterConverter extends AbstractConverter<String, Character> {

    @Override
    public Character convert(final String value) {
        return value != null ? value.charAt(0) : null;
    }
}
