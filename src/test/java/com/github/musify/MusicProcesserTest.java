package com.github.musify;

import com.github.musify.domain.model.MusicProcesser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MusicProcesserTest {

    private MusicProcesser processer;

    @BeforeEach
    void setUp() {
        processer = new MusicProcesser("");
    }

    @Test
    void testPreviousCharRegex() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String methodName = "appendPrevious";
        final String previousCharacter = "a";
        final Method declaredMethod = processer.getClass().getDeclaredMethod(methodName, String.class, String.class);
        declaredMethod.setAccessible(true);
        String[] testCharacters = {"?", ",", " ", ".", "!", "\n", ";", "3", "j", "A"};
        for (String testCharacter : testCharacters)
            assertFalse((Boolean) declaredMethod.invoke(processer, previousCharacter, testCharacter));
        testCharacters = new String[]{"c", "$", "g"};
        for (String testCharacter : testCharacters)
            assertTrue((Boolean) declaredMethod.invoke(processer, previousCharacter, testCharacter));
    }
}