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
        List<String> original = Arrays.asList(src.split("\\r?\\n"));
        List<String> revised = Arrays.asList(dst.split("\\r?\\n"));
        // Compute diff. Get the Patch object. Patch is the container for computed deltas.
        Patch patch = DiffUtils.diff(original, revised);

        ArrayList<Result> diffList = new ArrayList<>();

        int i = 0;


        for (Object o : patch.getDeltas()) {
            Result diffInfo = new Result();
            Delta delta = (Delta) o;
            System.out.println(delta.toString());


            List<String> deltaOriginalLines = delta.getOriginal().getLines();
            List<String> deltaRevisedLines = delta.getRevised().getLines();

            diffInfo.setSrcEndLineOffset(deltaOriginalLines.get(deltaOriginalLines.size() - 1).length());

            diffInfo.setDstEndLineOffset(deltaRevisedLines.get(deltaRevisedLines.size() - 1).length());


            if (delta.getType().equals(Delta.TYPE.CHANGE)) {
                diffInfo.setActionType("UPDATE");
            } else if (delta.getType().equals(Delta.TYPE.INSERT)) {
                diffInfo.setActionType("INSERT");
            } else if (delta.getType().equals(Delta.TYPE.DELETE)) {
                diffInfo.setActionType("DELETE");
            } else {
                diffInfo.setActionType("UNKNOWN");
            }


            diffInfo.setSrcId(i);
            diffInfo.setSrcStartLine(delta.getOriginal().getPosition() + 1);
            diffInfo.setSrcEndLine(delta.getOriginal().size() - 1 + delta.getOriginal().getPosition() + 1);


            diffInfo.setDstId(i);
            diffInfo.setDstStartLine(delta.getRevised().getPosition() + 1);

            if (diffInfo.getActionType().equals("DELETE")) {
                diffInfo.setDstEndLine(delta.getRevised().size() + delta.getRevised().getPosition() + 1);
            } else {
                diffInfo.setDstEndLine(delta.getRevised().size() - 1 + delta.getRevised().getPosition() + 1);
            }


            diffList.add(diffInfo);

            System.out.println("ActionType: " + diffInfo.getActionType());

            System.out.println("srcID: " + diffInfo.getSrcId());
            System.out.println("srcStartLine: " + diffInfo.getSrcStartLine());
            System.out.println("srcStartLineOffset: " + diffInfo.getSrcStartLineOffset());
            System.out.println("srcEndline: " + diffInfo.getSrcEndLine());
            System.out.println("srcEndlineOffset: " + diffInfo.getSrcEndLineOffset());

            System.out.println("dstStartLine: " + diffInfo.getDstStartLine());
            System.out.println("dstStartLineOffset: " + diffInfo.getDstStartLineOffset());
            System.out.println("dstEndline: " + diffInfo.getDstEndLine());
            System.out.println("dstEndlineOffset: " + diffInfo.getDstEndLineOffset());
            System.out.println("dstID: " + diffInfo.getDstId());

            i++;
        }
        return new DiffResult(new Metrics(), diffList);
    }
}