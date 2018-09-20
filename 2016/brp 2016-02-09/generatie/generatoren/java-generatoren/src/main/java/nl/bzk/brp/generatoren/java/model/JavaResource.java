/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

public class JavaResource {

	private String naam;
	private String folder;
	private String contents;

    /**
     * Constructor.
     *
     * @param naam Naam van deze resource.
     * @param folder Volledige naam van de fodler waar deze resource in moet komen te staan.
     */
    public JavaResource(final String naam, final String folder) {
    	this.naam = naam;
    	this.folder = folder;
    }

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
