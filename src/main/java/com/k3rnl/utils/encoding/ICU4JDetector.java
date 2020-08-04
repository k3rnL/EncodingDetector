package com.k3rnl.utils.encoding;

import com.ibm.icu.text.CharsetDetector;

import java.nio.charset.Charset;

/**
 * Implements IBM encoding detector http://site.icu-project.org/home
 */
class ICU4JDetector extends EncodingDetector {

    static DetectorResult guessEncoding(byte[] data) {
        CharsetDetector charsetDetector = new CharsetDetector();
        charsetDetector.setText(data);
        charsetDetector.enableInputFilter(true);

        String encoding = charsetDetector.detect().getName();
        String result = new String(data, Charset.forName(encoding)).split("\n")[0];

        return new DetectorResult(ICU4JDetector.class.getName(), encoding, countErrors(result), result);
    }

}
