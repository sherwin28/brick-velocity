package net.isger.brick.velocity.directive;

import java.util.HashMap;
import java.util.Map;

import net.isger.brick.velocity.BrickVelocityContext;
import net.isger.brick.velocity.VelocityConstants;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

public abstract class AbstractDirective extends Directive {

    private static final String DEFAULT_PREFIX = "x";

    private String name;

    public String getName() {
        synchronized (this) {
            if (this.name == null || !this.name.equals("Directive")) {
                this.name = DEFAULT_PREFIX + getBeanName();
            }
        }
        return this.name;
    }

    protected String getBeanName() {
        return this.getClass().getSimpleName().replaceFirst("Directive$", "");
    }

    public int getType() {
        return LINE;
    }

    protected VelocityEngine getEngine(InternalContextAdapter context) {
        VelocityEngine engine;
        if (context instanceof BrickVelocityContext) {
            engine = ((BrickVelocityContext) context).getVelocityEngine();
        } else {
            Context inContext = context.getInternalUserContext();
            if (inContext instanceof BrickVelocityContext) {
                engine = ((BrickVelocityContext) inContext).getVelocityEngine();
            } else {
                engine = (VelocityEngine) context
                        .get(VelocityConstants.KEY_ENGINE);
            }
        }
        return engine;
    }

    @SuppressWarnings("unchecked")
    protected Map<Object, Object> createPropertyMap(
            InternalContextAdapter context, Node node)
            throws ParseErrorException, MethodInvocationException {
        Map<Object, Object> propertyMap;
        int children = node.jjtGetNumChildren();
        if (getType() == BLOCK) {
            children--;
        }
        Node firstChild = null;
        Object firstValue = null;
        if (children == 1 && null != (firstChild = node.jjtGetChild(0))
                && null != (firstValue = firstChild.value(context))
                && firstValue instanceof Map) {
            propertyMap = (Map<Object, Object>) firstValue;
        } else {
            propertyMap = new HashMap<Object, Object>();
            for (int index = 0, length = children; index < length; index++) {
                this.putProperty(propertyMap, context, node.jjtGetChild(index));
            }
        }
        return propertyMap;
    }

    protected void putProperty(Map<Object, Object> propertyMap,
            InternalContextAdapter context, Node node)
            throws ParseErrorException, MethodInvocationException {
        String param = node.value(context).toString();
        int idx = param.indexOf("=");
        if (idx != -1) {
            String property = param.substring(0, idx);
            String value = param.substring(idx + 1);
            propertyMap.put(property, value);
        } else {
            throw new ParseErrorException(
                    "#"
                            + this.getName()
                            + " arguments must include an assignment operator!  For example #tag( Component \"template=mytemplate\" ).  #tag( TextField \"mytemplate\" ) is illegal!");
        }
    }
}
