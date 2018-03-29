/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.validatie;

import java.util.ArrayList;
import java.util.List;

/**
 * Class waarmee de {@link MetaTag} wordt gevalideerd.
 */
final class MetaTagValidator implements Validator<MetaTag> {
    private static final List<String> TOEGESTANE_META_TAGS = new ArrayList<>();
    private static final List<String> TOEGESTANE_STATUS_WAARDEN = new ArrayList<>();

    static {
        TOEGESTANE_META_TAGS.add("@auteur");
        TOEGESTANE_META_TAGS.add("@regels");
        TOEGESTANE_META_TAGS.add("@sprintnummer");
        TOEGESTANE_META_TAGS.add("@epic");
        TOEGESTANE_META_TAGS.add("@jiraIssue");
        TOEGESTANE_META_TAGS.add("@sleutelwoorden");
        TOEGESTANE_META_TAGS.add("@soapEndpoint");
        TOEGESTANE_META_TAGS.add("@soapNamespace");
        TOEGESTANE_META_TAGS.add("@component");
        TOEGESTANE_META_TAGS.add("@usecase");

        TOEGESTANE_STATUS_WAARDEN.add("onderhanden");
        TOEGESTANE_STATUS_WAARDEN.add("bug");
        TOEGESTANE_STATUS_WAARDEN.add("backlog");
        TOEGESTANE_STATUS_WAARDEN.add("review");
        TOEGESTANE_STATUS_WAARDEN.add("klaar");
        TOEGESTANE_STATUS_WAARDEN.add("uitgeschakeld");
    }

    /**
     * Valideerd de meegegeven {@link MetaTag}.
     * 
     * @param metaTag
     *            de {@link MetaTag}
     * @return true als de valide is.
     */
    public boolean valideer(final MetaTag metaTag) {
        final boolean isMetaTagValide;
        if (metaTag.isStatusTag()) {
            isMetaTagValide = TOEGESTANE_STATUS_WAARDEN.contains(metaTag.getWaarde().toLowerCase());
        } else {
            isMetaTagValide = TOEGESTANE_META_TAGS.contains(metaTag.getNaam());
        }
        return isMetaTagValide;
    }
}
