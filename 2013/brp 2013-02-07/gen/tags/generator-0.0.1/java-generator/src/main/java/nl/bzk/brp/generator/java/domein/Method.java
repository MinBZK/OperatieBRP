/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.ArrayList;
import java.util.List;


public class Method {

    protected String          visibility;

    protected String          returnType;

    protected String          returnTypeDescription;

    protected String          name;

    protected String          body;

    protected List<String>    annotations = new ArrayList<String>();

    protected List<Identifier> parameters  = new ArrayList<Identifier>();

    private String            javaDoc;

    public Method(final String naam) {
        this.name = naam;
    }

    public Method(final String returns, final String naam) {
        this.name = naam;
        this.returnType = returns;
        this.returnTypeDescription = returns;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(final String returnType) {
        this.returnType = returnType;
    }

    public String getReturnTypeDescription() {
        return returnTypeDescription;
    }

    public void setReturnTypeDescription(final String returnTypeDescription) {
        this.returnTypeDescription = returnTypeDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<String> annotations) {
        this.annotations = annotations;
    }

    public List<Identifier> getParameters() {
        return parameters;
    }

    public void addParameter(final String parameter) {
        this.parameters.add(new Identifier(parameter));
    }

    /**
     * @return de javadoc tekst
     */
    public String getJavaDoc() {
        return javaDoc;
    }

    /**
     * @param javaDoc zet de tekst als javadoc.
     */
    public void setJavaDoc(final String tekst) {
        this.javaDoc = tekst;
    }

}
