package org.springframework.cloud.aws.sample.source;

/**
 * @author Alain Sahli
 */
interface SourceCodeProvider {

    SourceFile getSourceFileContent(String path);

}
