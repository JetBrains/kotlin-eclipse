package org.jetbrains.kotlin.ui.tests.editors.formatter;

import org.jetbrains.kotlin.testframework.editor.AfterSuffixPosition;
import org.junit.Test;

public class KotlinFileAnnotationsFormatTest extends KotlinFormatActionTestCase {
    public KotlinFileAnnotationsFormatTest() {
        super(AfterSuffixPosition.BEFORE_DOT);
    }

    @Override
    protected String getTestDataRelativePath() {
        return "../common_testData/ide/formatter/fileAnnotations";
    }
    
    @Test
    public void beforeDeclaration() {
        doAutoTest();
    }

    @Test
    public void beforeImportList() {
        doAutoTest();
    }

    @Test
    public void beforePackage() {
        doAutoTest();
    }

    @Test
    public void inEmptyFile() {
        doAutoTest();
    }

    @Test
    public void manyLinesFromFileBegin() {
        doAutoTest();
    }
}
