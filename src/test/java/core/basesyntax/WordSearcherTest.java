package core.basesyntax;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class WordSearcherTest {
    private WordSearcher wordSearcher = new WordSearcher();

    private char[][] matrixA = {
            {'M', 'D', 'E', 'M'},
            {'B', 'A', 'C', 'Y'},
            {'M', 'A', 'A', 'R'},
            {'S', 'T', 'E', 'N'}};

    private char[][] matrixB = {
            {'S', 'E', 'S', 'T', 'Y'},
            {'U', 'R', 'U', 'E', 'K'},
            {'M', 'E', 'M', 'M', 'I'},
            {'D', 'H', 'O', 'E', 'B'},
            {'Z', 'S', 'G', 'R', 'U'}};

    private char[][] matrixC = {
            {'S', 'P', 'R', 'I'},
            {'D', 'R', 'O', 'T'},
            {'N', 'I', 'N', 'W'},
            {'T', 'A', 'G', 'Q'}};

    @Test
    public void wordSequence_uppercase_ok() {
        Assert.assertEquals("[2,0]->[2,1]->[3,1]->[3,2]->[2,2]->[1,2]->[1,1]->[0,1]->[0,2]->[0,3]->[1,3]",
                wordSearcher.findWordSequence(matrixA, "MATEACADEMY"));
        Assert.assertEquals("[0,2]->[1,2]->[2,2]->[2,3]->[3,3]->[4,3]",
                wordSearcher.findWordSequence(matrixB, "SUMMER"));
        Assert.assertEquals("[0,0]->[0,1]->[1,1]->[2,1]->[2,2]->[3,2]",
                wordSearcher.findWordSequence(matrixC, "SPRING"));
    }

    @Test
    public void wordSequence_camelCase_ok() {
        Assert.assertEquals("[2,0]->[2,1]->[3,1]->[3,2]->[2,2]->[1,2]->[1,1]->[0,1]->[0,2]->[0,3]->[1,3]",
                wordSearcher.findWordSequence(matrixA, "MATeaCADeMy"));
        Assert.assertEquals("[0,2]->[1,2]->[2,2]->[2,3]->[3,3]->[4,3]",
                wordSearcher.findWordSequence(matrixB, "sUmmER"));
        Assert.assertEquals("[0,0]->[0,1]->[1,1]->[2,1]->[2,2]->[3,2]",
                wordSearcher.findWordSequence(matrixC, "SpRiNg"));
    }

    @Test
    public void wordSequence_lowercase_ok() {
        Assert.assertEquals("[2,0]->[2,1]->[3,1]->[3,2]->[2,2]->[1,2]->[1,1]->[0,1]->[0,2]->[0,3]->[1,3]",
                wordSearcher.findWordSequence(matrixA, "mateacademy"));
        Assert.assertEquals("[0,2]->[1,2]->[2,2]->[2,3]->[3,3]->[4,3]",
                wordSearcher.findWordSequence(matrixB, "summer"));
        Assert.assertEquals("[0,0]->[0,1]->[1,1]->[2,1]->[2,2]->[3,2]",
                wordSearcher.findWordSequence(matrixC, "spring"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordSequence_invalidResult() {
        wordSearcher.findWordSequence(matrixA, "university");
        wordSearcher.findWordSequence(matrixB, "winter");
        wordSearcher.findWordSequence(matrixC, "autumn");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordSequence_tooLongWord() {
        wordSearcher.findWordSequence(matrixA, "mateacademylongtest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordSequence_digitWord() {
        wordSearcher.findWordSequence(matrixA, "1");
        wordSearcher.findWordSequence(matrixA, "3");
        wordSearcher.findWordSequence(matrixA, "6");
        wordSearcher.findWordSequence(matrixA, "7");
        wordSearcher.findWordSequence(matrixA, "8");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordSequence_specialSymbolWord() {
        wordSearcher.findWordSequence(matrixA, "!");
        wordSearcher.findWordSequence(matrixA, "?");
        wordSearcher.findWordSequence(matrixA, "^");
        wordSearcher.findWordSequence(matrixA, "@");
        wordSearcher.findWordSequence(matrixA, "#");
    }

    @Test
    public void wordSequence_isStateless() {
        Class<WordSearcher> wordSearcherClass = WordSearcher.class;
        Field[] declaredFields = wordSearcherClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            boolean isFinal = Modifier.isFinal(declaredFields[0].getModifiers());
            boolean isStatic = Modifier.isStatic(declaredFields[0].getModifiers());
            Assert.assertTrue("Let's make " + wordSearcherClass + " stateless. " +
                    "You can use only final fields and constants.", isFinal || (isFinal && isStatic));
        }
    }
}
