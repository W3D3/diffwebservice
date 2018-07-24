package at.aau.softwaredynamics.diffws;

import at.aau.softwaredynamics.diffws.domain.DiffResult;
import at.aau.softwaredynamics.diffws.domain.Differ;
import at.aau.softwaredynamics.diffws.domain.Metrics;
import at.aau.softwaredynamics.diffws.domain.Result;
import difflib.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class MyersDiff extends Differ {

    private final String src;
    private final String dst;

    public MyersDiff(String src, String dst) {
        super(src, dst);
        this.src = src;
        this.dst = dst;
    }

    public static List<String> stringToLines(String source) {
        return Arrays.asList(source.split("(\r?\n)"));
    }

    @Override
    public DiffResult diff() {
        //TODO implement!
        return null;
    }
}