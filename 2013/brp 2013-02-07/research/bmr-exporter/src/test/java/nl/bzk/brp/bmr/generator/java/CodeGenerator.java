/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * 
 */
package nl.bzk.brp.bmr.generator.java;

import java.io.File;
import java.io.IOException;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;

/**
 * 
 */
public class CodeGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JCodeModel cm = new JCodeModel();
		JDefinedClass dc;
		try {
			dc = cm._class("ModelEntity");

			JMethod m = dc.method(0, int.class, "doSomething");
			m.body()._return(JExpr.lit(5));

			File file = new File("./src-gen/");
			file.mkdirs();

			cm.build(file);
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
