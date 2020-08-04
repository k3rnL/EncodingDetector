package com.k3rnl.utils.encoding;

import com.glaforge.i18n.io.CharsetToolkit;

import java.nio.charset.Charset;

import static com.glaforge.i18n.io.CharsetToolkit.getDefaultSystemCharset;

/**
 * Implement the CharsetToolKit detector http://www.xiruss.org/xiruss-docs/api/com/glaforge/i18n/io/CharsetToolkit.html
 */
class CharsetToolkitDetector extends EncodingDetector {

    static DetectorResult guessEncoding(byte[] data) {
        CharsetToolkit toolkit = new CharsetToolkit(data);
        toolkit.setDefaultCharset(getDefaultSystemCharset());

        Charset encoding = toolkit.guessEncoding();
        String result = new String(data, encoding);

        return new DetectorResult(CharsetToolkit.class.getName(), encoding.name(), countErrors(result), result);
    }

}
