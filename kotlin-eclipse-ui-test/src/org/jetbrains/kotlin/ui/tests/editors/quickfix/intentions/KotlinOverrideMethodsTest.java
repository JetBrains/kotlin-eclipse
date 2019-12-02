package org.jetbrains.kotlin.ui.tests.editors.quickfix.intentions;

import org.jetbrains.kotlin.testframework.utils.EndLineCheckingTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EndLineCheckingTestRunner.class)
public class KotlinOverrideMethodsTest extends KotlinOverrideMembersTestCase {
    @Test
    public void testEscapeIdentifiers() {
        doTest("common_testData/ide/codeInsight/overrideImplement/escapeIdentifiers.kt");
    }
    
    @Test
    public void testOverrideNonUnitFunction() {
        doTest("common_testData/ide/codeInsight/overrideImplement/overrideNonUnitFunction.kt");
    }
    
    @Test
    public void testOverrideUnitFunction() {
        doTest("common_testData/ide/codeInsight/overrideImplement/overrideUnitFunction.kt");
    }
    
    @Test
    public void testVarArgs() {
        doTest("common_testData/ide/codeInsight/overrideImplement/varArgs.kt");
    }
}
