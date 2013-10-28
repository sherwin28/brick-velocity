package net.isger.brick.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

public class BrickVelocityContext extends VelocityContext {

    private String name;

    private VelocityEngine velocity;

    private ContextSecretary secretary;

    public BrickVelocityContext(VelocityEngine velocity) {
        this(velocity, null);
    }

    public BrickVelocityContext(VelocityEngine velocity, Context context) {
        super(context);
        this.velocity = velocity;
        this.secretary = new ContextSecretary();
        this.put(getName(), getSecretary());
    }

    protected String getName() {
        synchronized (this) {
            fetch: if (this.name == null || !this.name.equals("Context")) {
                Class<?> clazz = this.getClass();
                if (clazz == BrickVelocityContext.class) {
                    this.name = "Brick";
                    break fetch;
                }
                this.name = clazz.getSimpleName().replaceFirst("Context$", "");
            }
        }
        return this.name;
    }

    public VelocityEngine getVelocityEngine() {
        return velocity;
    }

    public ContextSecretary getSecretary() {
        return secretary;
    }

    public Object internalGet(String key) {
        Object result;
        if (super.internalContainsKey(key)) {
            result = super.internalGet(key);
        } else {
            result = ContextSecretary.mirror(key);
        }
        return result;
    }

}
