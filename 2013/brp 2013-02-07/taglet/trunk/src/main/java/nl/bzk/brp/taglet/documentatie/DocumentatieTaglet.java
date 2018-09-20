/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.taglet.documentatie;

import com.sun.javadoc.Doc;
import com.sun.javadoc.MemberDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import nl.bzk.brp.taglet.model.Referentie;


/**
 * Definitie van de bedrijfsregel javadoc tag.
 */
public class DocumentatieTaglet implements Taglet {

    static {
        DocumentatieExportUtils.reset();
    }

    /**
     * This method is called by the maven-javadoc-plugin using reflection.
     *
     * @param tagletMap
     *            alle geregistreerde taglets
     */
    public static void register(final Map<String, Taglet> tagletMap) {
        DocumentatieTaglet tag = new DocumentatieTaglet();
        tagletMap.put(tag.getName(), tag);
    }

    @Override
    public String getName() {
        return "brp.documentatie";
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
        List<Referentie> ruleRefs = new ArrayList<Referentie>();
        for (Tag tag : tags) {
            ruleRefs.addAll(extractRuleRefs(tag, tag.holder()));
        }
        DocumentatieExportUtils.append(ruleRefs);
        return formatAsText(ruleRefs);
    }

    /**
     * Lees alle bedrijfsregel referenties uit de tag. Er kunnen meerdere
     * referenties staan in 1 tag. Meerdere referenties zijn gescheiden met
     * komma's.
     *
     * @param tag
     *            de uit te lezen tag
     * @param doc
     *            de locatie van de javadoc waarin de tag voorkomt
     * @return alle bedrijfsregels die in de gegeven tag staan
     */
    private List<Referentie> extractRuleRefs(final Tag tag, final Doc doc) {
        List<Referentie> result = new ArrayList<Referentie>();
        StringTokenizer tokens = new StringTokenizer(tag.text(), ",");
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if (token != null) {
                String ruleId = token.trim();
                if (ruleId.length() != 0) {
                    result.add(createRuleRef(ruleId, tag.position(), doc));
                }
            }
        }
        return result;
    }

    /**
     * Creeer een bedrijfsregel referentie.
     *
     * @param ruleId
     *            de id van de bedrijfsregel
     * @param position
     *            de regel en kolom waar deze bedrijfsregel referentie voorkomt
     * @param doc
     *            de locatie van de javadoc waarin de tag voorkomt
     * @return nieuwe bedrijfsregel referentie
     */
    private Referentie createRuleRef(final String ruleId, final SourcePosition position, final Doc doc) {
        Referentie result = new Referentie();
        result.setReferentie(ruleId);
        result.setPackageName(getPackageName(doc));
        result.setTypeName(getTypeName(doc));
        result.setMemberName(getMemberName(doc));
        result.setLine(position.line());
        result.setColumn(position.column());
        return result;
    }

    /**
     * @param doc
     *            een class, interface, methode, enz. waar de javadoc voorkomt
     * @return de naam van de package waarin de javadoc voorkomt
     */
    private String getPackageName(final Doc doc) {
        String result;
        if (doc instanceof ProgramElementDoc) {
            ProgramElementDoc typeOrMember = (ProgramElementDoc) doc;
            result = typeOrMember.containingPackage().name();
        } else {
            result = doc.name();
        }
        return result;
    }

    /**
     * @param doc
     *            een class, interface, methode, enz. waar de javadoc voorkomt
     * @return de kote naam van de class of interface waarin de javadoc voorkomt
     */
    private String getTypeName(final Doc doc) {
        String result;
        if (doc instanceof MemberDoc) {
            MemberDoc member = (MemberDoc) doc;
            result = member.containingClass().name();
        } else if (doc instanceof ProgramElementDoc) {
            result = doc.name();
        } else {
            result = "";
        }
        return result;
    }

    /**
     * @param doc
     *            een class, interface, methode, enz. waar de javadoc voorkomt
     * @return de kote naam van de member waarin de javadoc voorkomt of leeg als
     *         het geen member is.
     */
    private String getMemberName(final Doc doc) {
        String result;
        if (doc instanceof MemberDoc) {
            result = doc.name();
        } else {
            result = "";
        }
        return result;
    }

    /**
     * @param referenties
     *            Lijst met bedrijfsregels
     * @return CSV string met alle referentie naar documentatie
     */
    private String formatAsText(final List<Referentie> referenties) {
        String result;
        if (referenties.isEmpty()) {
            result = null;
        } else {
            result = "<DT><B>Zie ook:</B><DD>" + formateerDocumentatiePerRegel(referenties);
        }
        return result;
    }

    /**
     * @param referenties
     *            Lijst met referenties naar documentatie
     * @return documentatie per regel
     */
    private String formateerDocumentatiePerRegel(final List<Referentie> referenties) {
        StringBuilder result = new StringBuilder();
        for (Referentie referentie : referenties) {
            if (result.length() != 0) {
                result.append("<br> ");
            }
            result.append(referentie.getReferentie());
        }
        return result.toString();
    }

}
