package com.lalitbhanot.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

    @DisplayName("Divisible by three")
    @Test
    @Order(1)
    void testDivisibleByThree() {
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.compute(3));
    }
    @DisplayName("Divisible by five")
    @Test
    @Order(2)
    void testDivisibleByFive(){
        String expected  = "Buzz" ;
        assertEquals(expected,FizzBuzz.compute(5)) ;
}

    @DisplayName("Divisible by three and  five")
    @Test
    @Order(3)
    void testDivisibleByThreeAndFive(){
        String expected  = "FizzBuzz" ;
        assertEquals(expected,FizzBuzz.compute(15)) ;
    }

    @DisplayName(" Not divisible by three and  five")
    @Test
    @Order(4)
    void testNotDivisibleByThreeAndFive(){
        String expected  = "1" ;
        assertEquals(expected,FizzBuzz.compute(1)) ;
    }

    @DisplayName("Testing with small data files ")
    @ParameterizedTest(name="value={0},expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @Order(5)
    void parameterizedTestSmallTestData(int value, String expected){
    assertEquals(expected,FizzBuzz.compute(value));
    }



    @DisplayName("Testing with medium data files ")
    @ParameterizedTest(name="value={0},expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @Order(6)
    void parameterizedTestMediumTestData(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value));
    }

    @DisplayName("Testing with large data files ")
    @ParameterizedTest(name="value={0},expected={1}")
    @CsvFileSource(resources = "/large-test-data.csv")
    @Order(7)
    void parameterizedTestLargeTestData(int value, String expected){
        assertEquals(expected,FizzBuzz.compute(value));
    }


}
