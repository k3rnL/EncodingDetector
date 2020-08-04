package com.k3rnl.utils.encoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Base class for encoding detectors, subclasses must implement the following *static* method :
 *  DetectorResult guessEncoding(byte[] data);
 * It is not declared as abstract in this class because Java does not allow *static abstract* method
 * and we do not want to use *new MyDetector().guessEncoding()*.
 */
public abstract class EncodingDetector {

    /**
     * This method try to guess the file encoding using different library (check the subclasses)
     * @return : The best Charset for the given file, if two encoding match the first one in the list will be returned.
     */
    public static Charset guessEncoding(File file) throws IOException {
        InputStream stream = new FileInputStream(file);
        byte[] data = new byte[4096];

        // We read only the beginning of the file, because most of the interesting characters are in the header
        if (stream.read(data, 0, 4096) < 1)
            throw new IOException("Not enough data to guess file encoding or file is empty : " + file.getAbsolutePath());

        DetectorResult[] results = new DetectorResult[] {
                CharsetToolkitDetector.guessEncoding(data),
                ICU4JDetector.guessEncoding(data),
                UniversalChardetDetector.guessEncoding(data)
        };

        DetectorResult best = Collections.min(Arrays.asList(results), Comparator.comparingInt(r -> r.errors));

        return Charset.forName(best.encoding);
    }

    /**
     * List incorrect chars that could be encountered in a String when the Charset used is not good.
     * This list is non-exhaustive and maybe don't match all the cases.
     * A way to improve it : add all non-French characters
     */
    static final HashSet<Character> NON_ALLOWED_CHARS = new HashSet<>();

    static {
        NON_ALLOWED_CHARS.add('Ã');
        NON_ALLOWED_CHARS.add('©');
        NON_ALLOWED_CHARS.add('Ă');
        NON_ALLOWED_CHARS.add('Š');
        NON_ALLOWED_CHARS.add('Ş');
        NON_ALLOWED_CHARS.add('ª');
        NON_ALLOWED_CHARS.add('�');
    }

    protected static int countErrors(String s) {
        int count = 0;
        for (Character c : s.toCharArray()) {
            if (NON_ALLOWED_CHARS.contains(c))
                count++;
        }

        return count;
    }

}
