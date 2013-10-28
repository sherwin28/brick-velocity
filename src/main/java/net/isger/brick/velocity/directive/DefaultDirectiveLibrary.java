package net.isger.brick.velocity.directive;

import java.util.Arrays;
import java.util.List;

public class DefaultDirectiveLibrary implements DirectiveLibrary {

    public List<Class<?>> getDirectiveClasses() {
        return Arrays.asList(new Class<?>[] {});
    }

}
