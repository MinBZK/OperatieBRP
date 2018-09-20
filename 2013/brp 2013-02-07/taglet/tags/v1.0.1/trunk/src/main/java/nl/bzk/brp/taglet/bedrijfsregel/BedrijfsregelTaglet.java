/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.taglet.bedrijfsregel;

import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * Definitie van de bedrijfsregel javadoc tag.
 */
public class BedrijfsregelTaglet implements Taglet {

    static {
        BedrijfsregelExportUtils.reset();
    }

    /**
     * This method is called by the maven-javadoc-plugin using reflection.
     * @param tagletMap alle geregistreerde taglets
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        BedrijfsregelTaglet tag = new BedrijfsregelTaglet();
        tagletMap.put(tag.getName(), tag);
    }

    @Override
    public String getName() {
        return "brp.bedrijfsregel";
    }

    @Override
    public boolean inConstructor() {
        return true;
    }

    @Override
    public boolean inField() {
        return true;
    }

    @Override
    public boolean inMethod() {
        return true;
    }

    @Override
    public boolean inOverview() {
        return false;
    }

    @Override
    public boolean inPackage() {
        return true;
    }

    @Override
    public boolean inType() {
        return true;
    }

    @Override
    public boolean isInlineTag() {
        return false;
    }

    @Override
    public String toString(final Tag tag) {
        /*
         * This method is only called for inline tags
         */
        return null;
    }

    @Override
    public String toString(final Tag[] tags) {
        List<BedrijfsregelReference> ruleRefs = new ArrayList<BedrijfsregelReference>();
        for (Tag tag : tags) {
            if (tag.holder() instanceof ProgramElementDoc) {
                ruleRefs.addAll(extractRuleRefs(tag, (ProgramElementDoc) tag.holder()));
            }
        }
        BedrijfsregelExportUtils.append(ruleRefs);
        return formatAsText(ruleRefs);
    }

    /**
     * Lees alle bedrijfsregel referenties uit de tag.
     * Er kunnen meerdere referenties staan in 1 tag.
     * Meerdere referenties zijn gescheiden met komma's.
     * @param tag de uit te lezen tag
     * @param typeOrMember de locatie van de javadoc waarin de tag voorkomt
     * @return alle bedrijfsregels die in de gegeven tag staan
     */
    private List<BedrijfsregelReference> extractRuleRefs(final Tag tag, final ProgramElementDoc typeOrMember) {
        List<BedrijfsregelReference> result = new ArrayList<BedrijfsregelReference>();
        StringTokenizer tokens = new StringTokenizer(tag.text(), ",");
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token != null) {
                String ruleId = token.trim();
                if (ruleId.length() != 0) {
                    result.add(createRuleRef(ruleId, tag.position(), typeOrMember));
                }
            }
        }
        return result;
    }

    /**
     * Creeer een bedrijfsregel referentie.
     * @param ruleId de id van de bedrijfsregel
     * @param position de regel en kolom waar deze bedrijfsregel referentie voorkomt
     * @param typeOrMember de locatie van de javadoc waarin de tag voorkomt
     * @return nieuwe bedrijfsregel referentie
     */
    private BedrijfsregelReference createRuleRef(
            final String ruleId, final SourcePosition position, final ProgramElementDoc typeOrMember)
    {
        BedrijfsregelReference result = new BedrijfsregelReference();
        result.setRuleId(ruleId);
        result.setPackageName(typeOrMember.containingPackage().name());
        result.setTypeName(getTypeName(typeOrMember));
        result.setMemberName(getMemberName(typeOrMember));
        result.setLine(position.line());
        result.setColumn(position.column());
        return result;
    }

    /**
     * @param typeOrMember een class, interface, methode, enz. waar de javadoc voorkomt
     * @return de kote naam van de class of interface waarin de javadoc voorkomt
     */
    private String getTypeName(final ProgramElementDoc typeOrMember) {
        String result;
        if (typeOrMember instanceof MemberDoc) {
            MemberDoc member = (MemberDoc) typeOrMember;
            result = member.containingClass().name();
        } else {
            result = typeOrMember.name();
        }
        return result;
    }

    /**
     * @param typeOrMember een class, interface, methode, enz. waar de javadoc voorkomt
     * @return de kote naam van de member waarin de javadoc voorkomt of leeg als het geen member is.
     */
    private String getMemberName(final ProgramElementDoc typeOrMember) {
        String result;
        if (typeOrMember instanceof MemberDoc) {
            result = typeOrMember.name();
        } else {
            result = "";
        }
        return result;
    }

    /**
     * @param ruleRefs Lijst met bedrijfsregels
     * @return CSV string met alle bedrijfsregels
     */
    private String formatAsText(final List<BedrijfsregelReference> ruleRefs) {
        String result;
        if (ruleRefs.isEmpty()) {
            result = null;
        } else if (ruleRefs.size() == 1) {
            result = "<DT><B>Bedrijfsregel:</B><DD>" + formatAsCsv(ruleRefs);
        } else {
            result = "<DT><B>Bedrijfsregels:</B><DD>" + formatAsCsv(ruleRefs);
        }
        return result;
    }

    /**
     * @param ruleRefs Lijst met referenties naar bedrijfsregels
     * @return rule id's as comma separated values
     */
    private String formatAsCsv(final List<BedrijfsregelReference> ruleRefs) {
        StringBuilder result = new StringBuilder();
        for (BedrijfsregelReference ruleRef : ruleRefs) {
            if (result.length() != 0) {
                result.append(", ");
            }
            result.append(ruleRef.getRuleId());
        }
        return result.toString();
    }

}
