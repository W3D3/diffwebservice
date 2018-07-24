package at.aau.softwaredynamics.diffws.rest;

import at.aau.softwaredynamics.diffws.domain.DiffResult;
import at.aau.softwaredynamics.diffws.domain.Differ;
import at.aau.softwaredynamics.diffws.service.ClassificationService;
import at.aau.softwaredynamics.diffws.util.DifferFactory;
import at.aau.softwaredynamics.diffws.util.MatcherRegistry;

import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static java.lang.Math.toIntExact;

@RestController
@RequestMapping("v1/changes")
public class ClassificationController {
    private final MatcherRegistry matcherRegistry;
    private DifferFactory differFactory;

    public ClassificationController() {
        this.matcherRegistry = new MatcherRegistry();

    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public DiffResult classify(@RequestBody String payload) {
        HashMap<String, Object> inputMap = (HashMap<String, Object>) new BasicJsonParser().parseMap(payload);

        //Assuming src and dst objects are base64 encoded
        String src = new String(Base64.decodeBase64(inputMap.get("src").toString().getBytes()));
        String dst = new String(Base64.decodeBase64(inputMap.get("dst").toString().getBytes()));
        Integer matcherId = toIntExact((Long) inputMap.get("matcher"));

        // get matcher class
        Class<? extends Differ> differClass = matcherRegistry.getMatcherTypeFor(matcherId);

        this.differFactory = new DifferFactory(differClass);

        ClassificationService service = new ClassificationService(differFactory.createDiffer(src, dst));

        return service.classify();
    }

}
