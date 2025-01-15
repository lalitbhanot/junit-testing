package com.lalitbhanot.demo;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DemoUtilsTest {
    DemoUtils demoUtils ;
@BeforeAll
static void setUpBeforeAll(){
    System.out.println("BeforeAll Running...") ;
}
    @BeforeEach
    void setUpBeforeEach(){
        demoUtils = new DemoUtils();
        System.out.println("BeforeEach Running...") ;
    }
    @AfterEach
    void setUpAfterEach(){
        System.out.println("AfterEach Running...") ;
    }
    @AfterAll
    static void setAfterAll(){
        System.out.println("AfterAll Running...") ;
    }

    @Test
    void testIterableNotEquals() {
        List<String> list = List.of("luv", "2", "code");
        assertIterableEquals(list, demoUtils.getAcademyInList());
        System.out.println("5 Executed the Test testIterableNotEquals....") ;
    }

    @Test
    void testArraysEqualsNotEquals() {
    String[] str = {"A","B","C"} ;
        assertArrayEquals(str, demoUtils.getFirstThreeLettersOfAlphabet());
        System.out.println("4 Executed the Test testArraysEqualsNotEquals....") ;
    }

    @DisplayName("true and false ")
    @Test
    void testTrueNotFalse() {
        int i= 20 ;
        int j= 10 ;
        assertTrue(demoUtils.isGreater(i,j));
        assertFalse(demoUtils.isGreater(j,i));
        System.out.println("3 Executed the Test testTrueNotFalse.....") ;
    }

    @DisplayName("same and not same")
    @Test
    void testSameNotSame() {
        String str = "testStr" ;
        assertSame(demoUtils.getAcademy(),demoUtils.getAcademyDuplicate());
        assertNotSame(str,demoUtils.getAcademy());
        System.out.println("2 Executed the Test testSameNotSame......") ;
    }


    @DisplayName("equals and not equals ")
    @Test
    void testEqualsNotEquals() {
        assertEquals(6, demoUtils.add(3,3),"3+3=6");
        assertNotEquals(6, demoUtils.add(2,3));
        System.out.println("1 Executed the Test testEqualsNotEquals....") ;
    }


}
