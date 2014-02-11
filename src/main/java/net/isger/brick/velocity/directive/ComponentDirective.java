package net.isger.brick.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import net.isger.brick.velocity.VelocityConstants;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;

public class ComponentDirective extends AbstractDirective {

    public boolean render(InternalContextAdapter context, Writer writer,
            Node node) throws IOException, ResourceNotFoundException,
            ParseErrorException, MethodInvocationException {
        VelocityEngine engine = getEngine(context);
        String compPath = (String) engine
                .getProperty(VelocityConstants.KEY_COMPONENT_PATH);
        if (compPath == null) {
            compPath = VelocityConstants.COMPONENT_PATH;
        }
        // Map<Object, Object> properties = createPropertyMap(context, node);
        Template template = engine.getTemplate(compPath + "/"
                + getTemplatePath());
        template.merge(context, writer);
        writer.flush();
        return false;
    }

    protected String getTemplatePath() {
        return getName();
    }

}
