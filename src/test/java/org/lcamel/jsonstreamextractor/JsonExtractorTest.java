/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.lcamel.jsonstreamextractor;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class JsonExtractorTest {
    @Test
    void testExtractArrayObjects() throws IOException {
        try (InputStream is = new ByteArrayInputStream(
                " { \"foo\": 3 }, { \"bar\": 4 } } ]".getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream baos = new ByteArrayOutputStream(10000)) {
            JsonExtractor.extractArrayOfObjects(is, baos);
            assertArrayEquals(baos.toByteArray(), "{\"foo\":3}\n{\"bar\":4}\n".getBytes(StandardCharsets.UTF_8));
        }
    }
}
