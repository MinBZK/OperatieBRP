/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLConnection;

import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.VariableMapperWrapper;

import javax.faces.component.UIComponent;
import javax.faces.FacesException;
import javax.el.ELException;
import javax.el.VariableMapper;
import javax.el.ValueExpression;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.file.def.FileDefinition;

import org.jboss.gravel.common.annotation.TldTag;
import org.jboss.gravel.common.annotation.TldAttribute;

/**
 *
 */
@TldTag (
    name = "includeProcessFile",
    description = "Include a process file.",
    attributes = {
        @TldAttribute (
            name = "process",
            description = "The process definition from which the file should be read.",
            required = true,
            deferredType = ProcessDefinition.class
        ),
        @TldAttribute (
            name = "file",
            description = "The name of a file within the process to include.",
            required = true
        )
    }
)
public final class IncludeProcessFileHandler extends TagHandler {
    private final TagAttribute processAttribute;
    private final TagAttribute fileAttribute;

    public IncludeProcessFileHandler(final TagConfig config) {
        super(config);
        processAttribute = getRequiredAttribute("process");
        fileAttribute = getRequiredAttribute("file");
    }

    public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, ELException {
        // now include the nested form
        final ValueExpression fileExpression = fileAttribute.getValueExpression(ctx, String.class);
        final ValueExpression processExpression = processAttribute.getValueExpression(ctx, ProcessDefinition.class);

        final String file = (String) fileExpression.getValue(ctx);
        if (file == null || file.length() == 0) {
            return;
        }
        final ProcessDefinition processDefinition = (ProcessDefinition) processExpression.getValue(ctx);
        if (processDefinition == null) {
            throw new TagException(tag, "Value for process attribute is null");
        }
        final FileDefinition fileDefinition = processDefinition.getFileDefinition();
        if (fileDefinition == null) {
            throw new TagException(tag, "Process has a null fileDefinition property");
        }
        if (! fileDefinition.hasFile(file)) {
            throw new TagException(tag, "Process does not contain file '" + file + "'");
        }
        VariableMapper orig = ctx.getVariableMapper();
        final VariableMapperWrapper newVarMapper = new VariableMapperWrapper(orig);
        ctx.setVariableMapper(newVarMapper);
        try {
            final StringBuffer buffer = new StringBuffer();
            buffer.append(processDefinition.getId());
            buffer.append("/");
            buffer.append(file);
            nextHandler.apply(ctx, parent);
            ctx.includeFacelet(parent, new URL("par", "", 0, buffer.toString(), new FileDefinitionURLStreamHandler(fileDefinition, file)));
        } finally {
            ctx.setVariableMapper(orig);
        }
    }

    private static final class FileDefinitionURLStreamHandler extends URLStreamHandler {
        private final FileDefinition fileDefinition;
        private final String src;

        public FileDefinitionURLStreamHandler(final FileDefinition fileDefinition, final String src) {
            this.fileDefinition = fileDefinition;
            this.src = src;
        }

        protected URLConnection openConnection(URL url) {
            return new IncludeProcessFileHandler.FileDefinitionURLConnection(url, fileDefinition, src);
        }
    }

    private static final class FileDefinitionURLConnection extends URLConnection {
        private final FileDefinition fileDefinition;
        private final String src;

        protected FileDefinitionURLConnection(final URL url, final FileDefinition fileDefinition, final String src) {
            super(url);
            this.fileDefinition = fileDefinition;
            this.src = src;
        }

        public void connect() {
        }

        public InputStream getInputStream() throws FileNotFoundException {
            final InputStream inputStream = fileDefinition.getInputStream(src);
            if (inputStream == null) {
                throw new FileNotFoundException("File '" + src + "' not found in process file definition");
            }
            return inputStream;
        }
    }
}
