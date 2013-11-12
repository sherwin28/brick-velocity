package net.isger.brick.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import net.isger.brick.velocity.BrickVelocityContext;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

public class AbstractDirective extends Directive {

    private String name;

    public String getName() {
        synchronized (this) {
            if (this.name == null || !this.name.equals("Directive")) {
                this.name = "x"
                        + this.getClass().getSimpleName()
                                .replaceFirst("Directive$", "");
            }
        }
        return this.name;
    }

    public int getType() {
        return LINE;
    }

    public boolean render(InternalContextAdapter context, Writer writer,
            Node node) throws IOException, ResourceNotFoundException,
            ParseErrorException, MethodInvocationException {
        // TODO 找到该指令的模板
        VelocityEngine velocity;
        if (context instanceof BrickVelocityContext) {
            velocity = ((BrickVelocityContext) context).getVelocityEngine();
        } else {
            velocity = (VelocityEngine) context.get("brick");
        }
        String compPath = (String) velocity.getProperty("brick.component.path");
        if (compPath == null) {
            compPath = "/component";
        }
        Template template = velocity.getTemplate(compPath + "/" + name);
        template.merge(context, writer);
        writer.flush();
        return true;
    }

}
