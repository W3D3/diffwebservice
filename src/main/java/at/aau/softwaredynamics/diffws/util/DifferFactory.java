package at.aau.softwaredynamics.diffws.util;

import at.aau.softwaredynamics.diffws.domain.Differ;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by thomas on 12.12.2016.
 */
public class DifferFactory  {
    public Class<? extends Differ> defaultMatcherType;

    public DifferFactory(Class<? extends Differ> defaultMatcherType) {
        this.defaultMatcherType = defaultMatcherType;
    }

    public Differ createDiffer(String src, String dst) {
        return createDiffer(defaultMatcherType, src, dst);
    }

    public Differ createDiffer(Class<? extends Differ> type, String src, String dst)  {
        try {
            Constructor<?> constructor = type.getConstructor(String.class, String.class);
            return (Differ) constructor.newInstance(src, dst);
        } catch (
                InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException
                        | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
