package com.guney.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestMethodOrder(MethodOrderer.MethodName.class)
//@TestMethodOrder(MethodOrderer.Random.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void BeforeEach() {
        demoUtils = new DemoUtils();
        //System.out.println("@BeforeEach executes before the execution of each test method");
    }

    @AfterEach
    void AfterEach() {
        demoUtils = new DemoUtils();
        //System.out.println("@AfterEach executes after the execution of each test method \n");
    }

    @BeforeAll
    static void BeforeAll() {
        //System.out.println("@BeforeAll executes only once before all test methods execution in the class\n");
    }

    @AfterAll
    static void AfterAll() {
        //System.out.println("@AfterAll executes only once after all test methods execution in the class");
    }

    @Test
    @DisplayName("01 - Equals or Not Equals")
    void testEqualsAndNotEquals() {

        assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
    }

    @Test
    @DisplayName("02 - Null or Not Null")
    void testNullAndNotNull() {
        String str1 = null;
        String str2 = "guney";

        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

    @Test
    @DisplayName("03 - Same and Not Same")
    void testSameAndNotSame() {
        String str = "guney";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Object should refer to same object");
        assertNotSame(str, demoUtils.getAcademy(), "Object should not refer to same object");
    }

    @Test
    @DisplayName("04 - True and False")
    void testTrueAndFalse() {
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "This should return true");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "This should return false");
    }

    @Test
    @DisplayName("05 - Array Equals")
    void testArrayEquals() {
        String[] stringArray = {"A", "B", "C"};

        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be the same");
    }

    @Test
    @DisplayName("06 - Iterable Equals")
    void testIterableEquals() {
        List<String> theList = List.of("gu", "N", "ey");

        assertIterableEquals(theList, demoUtils.getAcademyInList(), "Expected list should be same as actual list");
    }

    @Test
    @DisplayName("07 - Lines Match")
    void testLinesMatch() {
        List<String> theList = List.of("gu", "N", "ey");

        assertLinesMatch(theList, demoUtils.getAcademyInList(), "Lines should match");
    }

    @Test
    @DisplayName("08 - Throws and Does Not Throw")
    void testThrowsAndDoesNotThrow() {

        assertThrows(Exception.class, () -> {
            demoUtils.throwException(-1);
        }, "Should throw exception");

        assertDoesNotThrow(() -> {
            demoUtils.throwException(1);
        }, "Should not throw exception");
    }

    @Test
    @DisplayName("09 - Timeout")
    void testTimeout() {

        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
                    demoUtils.checkTimeout();
                },
                "Method should execute in 3 seconds");
    }
}






