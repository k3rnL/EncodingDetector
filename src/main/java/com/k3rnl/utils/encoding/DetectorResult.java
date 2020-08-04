package com.k3rnl.utils.encoding;

/**
 * Hold the result of an EncodingDetector.
 */
class DetectorResult {
    public final String detector;
    public final String encoding;
    public final String result;
    public final int errors;

    DetectorResult(String detector, String encoding, int errors, String result) {
        this.detector = detector;
        this.encoding = encoding;
        this.errors = errors;
        this.result = result;
    }
}