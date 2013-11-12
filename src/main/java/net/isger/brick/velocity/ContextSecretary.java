package net.isger.brick.velocity;

public class ContextSecretary {

    private LayoutBean layout;

    public ContextSecretary() {
    }

    public LayoutBean getLayout() {
        return layout;
    }

    public void setLayout(LayoutBean layout) {
        this.layout = layout;
    }

    public static Object mirror(String name) {
        return mirror(name, false);
    }

    public static Object mirror(String name, boolean isCreate) {
        // TODO 待优化，建立Key索引机制
        Class<?> clazz = null;
        cast: try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            if (name.indexOf(".") == -1) {
                try {
                    clazz = Class.forName("java.lang." + name);
                    break cast;
                } catch (ClassNotFoundException ee) {
                }
            }
            return clazz;
        }
        if (isCreate) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
            }
        }
        return clazz;
    }
}
