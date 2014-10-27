package org.springframework.cloud.aws.sample.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alain Sahli
 */
@RestController
public class SourceCodeController {

    private final SourceCodeProvider sourceCodeProvider;

    @Autowired
    public SourceCodeController(SourceCodeProvider sourceCodeProvider) {
        this.sourceCodeProvider = sourceCodeProvider;
    }

    @RequestMapping(value = "/source", method = RequestMethod.GET)
    public SourceFile getSourceFile(@RequestParam String path) {
        Assert.isTrue(StringUtils.hasText(path), "path cannot be empty");

        return this.sourceCodeProvider.getSourceFileContent(path);
    }

}
