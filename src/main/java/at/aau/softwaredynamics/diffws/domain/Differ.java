package at.aau.softwaredynamics.diffws.domain;

public abstract class Differ {

    public Differ(String src, String dst) {

    }

    public abstract DiffResult diff();
}
