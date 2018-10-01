package at.aau.softwaredynamics.diffws;

import at.aau.softwaredynamics.diffws.domain.DiffResult;
import at.aau.softwaredynamics.diffws.domain.Differ;
import at.aau.softwaredynamics.diffws.domain.Metrics;
import at.aau.softwaredynamics.diffws.domain.Result;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyersDiff implements Differ {

    private final String src;
    private final String dst;

    public MyersDiff(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

//    public static List<String> stringToLines(String source) {
//        return Arrays.asList(source.split("(\r?\n)"));
//    }

    @Override
    public DiffResult diff() {
        List<String> original = Arrays.asList(src.split("\\r?\\n"));
        List<String> revised = Arrays.asList(dst.split("\\r?\\n"));
        // Compute diff. Get the Patch object. Patch is the container for computed deltas.
        Patch patch = DiffUtils.diff(original, revised);

        ArrayList<Result> diffList = new ArrayList<>();

        int i = 0;

        for (Delta delta : (List<Delta>) patch.getDeltas()) {
            Result diffInfo = new Result();

            List<String> deltaOriginalLines = delta.getOriginal().getLines();
            List<String> deltaRevisedLines = delta.getRevised().getLines();

            // set an offset if necessary for src and dst
            if(deltaOriginalLines.size() > 0) {
                diffInfo.setSrcEndLineOffset(deltaOriginalLines.get(deltaOriginalLines.size() - 1).length());
            }
            if(deltaRevisedLines.size() > 0) {
                diffInfo.setDstEndLineOffset(deltaRevisedLines.get(deltaRevisedLines.size() - 1).length());
            }

            // rename action type to DiffViz naming conventions
            switch (delta.getType()) {
                case CHANGE:
                    diffInfo.setActionType("UPDATE");
                    break;
                case DELETE:
                    diffInfo.setActionType("DELETE");
                    break;
                case INSERT:
                    diffInfo.setActionType("INSERT");
                    break;
                default:
                    diffInfo.setActionType("META");
            }

            // Note here that we can set arbitrary values in src for INSERTS and in dst for DELETES because
            // this data will be ignored anyway. Would not recommend though.

            diffInfo.setSrcId(i);
            diffInfo.setSrcStartLine(delta.getOriginal().getPosition() + 1);
            diffInfo.setSrcEndLine(delta.getOriginal().size() - 1 + delta.getOriginal().getPosition() + 1);

            diffInfo.setDstId(i);
            diffInfo.setDstStartLine(delta.getRevised().getPosition() + 1);
            diffInfo.setDstEndLine(Math.max(delta.getRevised().size() - 1, 0) + delta.getRevised().getPosition() + 1);

            diffList.add(diffInfo);
            i++;
        }
        return new DiffResult(new Metrics(), diffList);
    }
}