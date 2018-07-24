package at.aau.softwaredynamics.diffws.service;

import at.aau.softwaredynamics.diffws.domain.DiffResult;
import at.aau.softwaredynamics.diffws.domain.Differ;

public class ClassificationService {

    private Differ differ;

    public ClassificationService(Differ differ) {
        this.differ = differ;
    }

    public DiffResult classify() {
        return this.differ.diff();
    }
}
