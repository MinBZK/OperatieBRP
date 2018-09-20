/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

/**
 * Deze enumeratie bevat de keywords voor java als values, gebruik de lowercase versies...
 * 
 * @version 1.0
 */
enum JavaKeywordConstants {
    ABSTRACT("abstract"), CATCH("catch"), CLASS("class"), EXTENDS("extends"), FALSE("false"), FINAL("final"), FINALLY(
            "finally"), IMPLEMENTS("implements"), IMPORT("import"), INTERFACE("interface"), NATIVE("native"),
    NEW("new"), NULL("null"), PACKAGE("package"), DEFAULT_ACCESS(""), PRIVATE("private"), PROTECTED("protected"),
    PUBLIC("public"), RETURN("return"), STATIC("static"), STRICTFP("strictfp"), SUPER("super"), SYNCHRONIZED(
            "synchronized"), THIS("this"), THROW("throw"), THROWS("throws"), TRANSIENT("transient"), TRUE("true"), TRY(
            "try"), VOID("void"), VOLATILE("volatile");

    private String keyword;

    JavaKeywordConstants(String value) {
        keyword = value;
    }

    public String getKeyword() {
        return keyword;
    }
}
