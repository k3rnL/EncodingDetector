package com.k3rnl.utils.encoding;

import org.mozilla.universalchardet.UniversalDetector;

import java.nio.charset.Charset;

/**
 * Implement the Mozilla encoding detector https://github.com/albfernandez/juniversalchardet
 */
class UniversalChardetDetector extends EncodingDetector {

    static DetectorResult guessEncoding(byte[] data) {
        UniversalDetector universalDetector = new UniversalDetector(null);
        universalDetector.handleData(data, 0, data.length);
        universalDetector.dataEnd();

        String encoding = universalDetector.getDetectedCharset();
        String text = new String(data, Charset.forName(encoding)).split("\n")[0];

        return new DetectorResult(UniversalChardetDetector.class.getName(), encoding, countErrors(text), text);
    }

}
